package com.mindolph.mfx.drawing.component;

import com.mindolph.mfx.drawing.Context;
import com.mindolph.mfx.drawing.Graphics;
import javafx.geometry.Rectangle2D;
import javafx.scene.text.Font;

/**
 * Font icon component.
 *
 * @since 3.1
 */
public class FontIconComp extends TextComp {

    public FontIconComp(Font iconFont) {
        super();
        super.font = iconFont;
    }

    public FontIconComp(Rectangle2D bounds, Font iconFont) {
        super(bounds);
        super.font = iconFont;
    }

    public FontIconComp(double x, double y, double width, double height, Font iconFont) {
        super(x, y, width, height);
        super.font = iconFont;
    }

    @Override
    public void draw(Graphics g, Context c) {
        super.draw(g, c);
        double posy = this.absoluteBounds.getMinY() + super.scaledFontSize;
        g.drawFontIcon(super.scaledFont, super.text.getText(), getPosx(this.text), posy, getColor());
    }
}
