package com.mindolph.mfx.drawing.component;

import com.mindolph.mfx.drawing.Context;
import com.mindolph.mfx.drawing.Graphics;
import com.mindolph.mfx.drawing.constant.TextAlign;
import com.mindolph.mfx.util.BoundsUtils;
import com.mindolph.mfx.util.FontUtils;
import com.mindolph.mfx.util.RectangleUtils;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Text that extends or shrink the bounds.
 * This component does not allow child components.
 */
public class TextComp extends Component {

    private static final Logger log = LoggerFactory.getLogger(TextComp.class);
    public static final String LINE_SEPARATOR = "\n";

    protected Font font = Font.getDefault();
    protected Font scaledFont = Font.getDefault();
    protected double scaledFontSize;
    protected Color color = Color.BLACK;
    private TextAlign textAlign = TextAlign.LEFT;

    // the bounds of text is scaled.
    protected Text text;

    // the bounds of lines text is scaled.
    private List<Text> lines;

    public TextComp() {
        super();
    }

    public TextComp(Rectangle2D bounds) {
        super(bounds);
    }

    public TextComp(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public void updateText(String text) {
        this.updateText(text, this.font);
    }

    public void updateText(String text, Font font) {
        if (StringUtils.isNotBlank(text)) {
            log.debug("[%s] Update text '%s'".formatted(id, text));
            this.font = font;
            this.text = new Text(text);
            this.text.setFont(font);
            String[] texts = StringUtils.split(text, LINE_SEPARATOR);
            this.lines = Arrays.stream(texts).map(l -> {
                Text t = new Text(l);
                t.setFont(font);
                return t;
            }).toList();
            Bounds textBounds = this.text.getLayoutBounds();
            log.debug("[%s] Text layout bounds %s with font %s".formatted(id, BoundsUtils.boundsInString(textBounds), scaledFont));
            super.bounds = RectangleUtils.newWithWidthHeight(super.bounds, textBounds.getWidth(), textBounds.getHeight());
            log.debug("[%s] TextComp bounds: %s".formatted(id, RectangleUtils.rectangleAllInStr(super.bounds)));
            log.debug("  %s - %s".formatted(super.bounds.getMaxX(), super.bounds.getMaxY()));
        }
    }

    @Override
    public void updateBounds(Context c) {
        this.scaledFontSize = c.safeScale(font.getSize());
        this.scaledFont = FontUtils.newFontWithSize(font, scaledFontSize);
        // re-update text because the text bounds might be scaled.
        updateText(this.text.getText());

        double maxWidth = 0.0d;
        double maxHeight = 0.0d;
        if (lines == null || lines.isEmpty() || StringUtils.isBlank(lines.getFirst().getText())) {
            // force to set bounds as one letter if text is blank.
            Text text = new Text("X");
            Bounds bounds = text.getBoundsInLocal();
            maxWidth = bounds.getWidth();
            maxHeight = bounds.getHeight();
        }
        else {
            Bounds bounds = text.getBoundsInLocal();
            maxWidth = Math.max(bounds.getWidth(), maxWidth);
            maxHeight += bounds.getHeight();
        }
        // TODO consider parent bounds
        if (parent != null) {
            this.absoluteBounds = new Rectangle2D(
                    parent.absoluteBounds.getMinX() + c.safeScale(super.getMinX(), 0),
                    parent.absoluteBounds.getMinY() + c.safeScale(super.getMinY(), 0),
                    maxWidth, maxHeight
            );
        }
        else {
            super.absoluteBounds = new Rectangle2D(
                    c.safeScale(super.getMinX(), 0), c.safeScale(super.getMinY(), 0),
                    maxWidth, maxHeight);
        }
        log.debug("[%s] Text Bounds: %s".formatted(id, RectangleUtils.rectangleInStr(this.absoluteBounds)));
//        super.updateBounds(c);
    }

    @Override
    public void draw(Graphics g, Context c) {
        if (log.isDebugEnabled())
            log.debug("[%s] Draw TextComp: %s(%d) %s".formatted(id, StringUtils.abbreviate(this.text.getText(), 20), this.text.getText().length(), RectangleUtils.rectangleInStr(this.absoluteBounds)));
        super.draw(g, c);
        double posy = this.absoluteBounds.getMinY() + this.scaledFontSize;
        g.setFont(this.scaledFont);
        for (Text l : this.lines) {
            double posx = getPosx(l);
            g.drawString(l.getText(), posx, posy, getColor());
            posy += l.getBoundsInLocal().getHeight();
        }
    }

    protected double getPosx(Text l) {
        double posx;
        switch (this.textAlign) {
            case LEFT -> {
                posx = this.absoluteBounds.getMinX();
            }
            case CENTER -> {
                posx = this.absoluteBounds.getMinX() + (this.absoluteBounds.getWidth() - l.getBoundsInLocal().getWidth()) / 2;
            }
            case RIGHT -> {
                posx = this.absoluteBounds.getMinX() + (this.absoluteBounds.getWidth() - l.getBoundsInLocal().getWidth());
            }
            default -> throw new Error("unsupported text alignment");
        }
        return posx;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TextAlign getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(TextAlign textAlign) {
        this.textAlign = textAlign;
    }

    public String getText() {
        return this.text.getText();
    }

    public Font getFont() {
        return font;
    }
}
