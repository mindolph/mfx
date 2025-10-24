package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Container;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * for testing.
 */
public class RectangleComp extends Container {

    private Color fill = Color.GREEN;

    public RectangleComp(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public RectangleComp(double x, double y, double width, double height, Color fill) {
        super(x, y, width, height);
        this.fill = fill;
    }

    @Override
    public void draw(Graphics g, Context c) {
        super.draw(g, c);
        g.drawRect(super.absoluteBounds.getMinX(),
                super.absoluteBounds.getMinY(),
                super.absoluteBounds.getWidth(),
                super.absoluteBounds.getHeight(),
                Color.RED, this.fill);
    }

    @Override
    protected Shape updateShape() {
        return super.updateShape();
    }
}
