package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Container;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class RectangleComp extends Container {

    public RectangleComp() {
    }

    public RectangleComp(Rectangle2D bounds) {
        super(bounds);
    }

    public RectangleComp(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics g, Context c) {
        super.draw(g, c);
        g.drawRect(super.absoluteBounds.getMinX(),
                super.absoluteBounds.getMinY(),
                super.absoluteBounds.getWidth(),
                super.absoluteBounds.getHeight(),
                Color.RED, Color.GREEN);
        super.drawChildren(g, c);
//        g.drawRect(c.safeScale(super.absoluteBounds.getMinX()),
//                c.safeScale(super.absoluteBounds.getMinY()),
//                c.safeScale(super.absoluteBounds.getWidth()),
//                c.safeScale(super.absoluteBounds.getHeight()),
//                Color.RED, Color.GREEN);
    }

    @Override
    protected Shape updateShape() {
        return super.updateShape();
    }
}
