package com.mindolph.mfx.drawing.component;

import com.mindolph.mfx.drawing.Context;
import com.mindolph.mfx.drawing.Graphics;
import com.mindolph.mfx.drawing.constant.TextAlign;
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

    private Font font = Font.getDefault();
    private Font scaledFont = Font.getDefault();
    private Color color = Color.BLACK;
    private TextAlign textAlign;

    // the bounds of text is scaled.
    private Text text;

    // the bounds of lines text is scaled.
    private List<Text> lines;

    private double scaledFontSize;

    public TextComp() {
    }

    public TextComp(Rectangle2D bounds) {
        super(bounds);
    }

    public TextComp(double x, double y, double width, double height) {
        super(x, y, width, height);
    }


    public void updateText(String text) {
        if (StringUtils.isNotBlank(text)) {
            this.text = new Text(text);
            this.text.setFont(getFont());
            String[] texts = StringUtils.split(text, LINE_SEPARATOR);
            this.lines = Arrays.stream(texts).map(text1 -> {
                Text t = new Text(text1);
                t.setFont(scaledFont);
                return t;
            }).toList();
        }
    }

    @Override
    public void updateBounds(Context c) {
        this.scaledFont = FontUtils.newFontWithSize(getFont(), scaledFontSize);
        this.scaledFontSize = c.safeScale(getFont().getSize());

        // re-update text because the text bounds might be scaled.
        updateText(this.text.getText());

        double maxWidth = 0.0d;
        double maxHeight = 0.0d;
        if (lines == null || lines.isEmpty() || StringUtils.isBlank(lines.get(0).getText())) {
            // force to set bounds as one letter if topic is blank.
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
        super.absoluteBounds = new Rectangle2D(c.safeScale(super.getMinX()), c.safeScale(super.getMinY()), maxWidth, maxHeight);
        log.debug(RectangleUtils.rectangleInStr(this.absoluteBounds));
//        super.updateBounds(c);

    }

    @Override
    public void draw(Graphics g, Context context) {
        super.draw(g, context);
        double posy = this.absoluteBounds.getMinY() + this.scaledFontSize;
        g.setFont(this.scaledFont);
        for (Text l : this.lines) {
            double posx = getPosx(l);
            g.drawString(l.getText(), posx, posy, getColor());
            posy += l.getBoundsInLocal().getHeight();
        }
    }

    private double getPosx(Text l) {
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

    @Override
    public void add(Component child) {
        // skip the implementation
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
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

}
