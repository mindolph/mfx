package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Container;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class CircleComp extends Container {
    public CircleComp() {
    }

    public CircleComp(Rectangle2D bounds) {
        super(bounds);
    }

    public CircleComp(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

//    public Circle(Layer layer, Rectangle2D bounds) {
//        super(layer, bounds);
//    }
//
//    public Circle(Layer layer, double x, double y, double width, double height) {
//        super(layer, x, y, width, height);
//    }

    @Override
    public void draw(Graphics g, Context c) {
        super.draw(g, c);
        g.drawOval(  super.absoluteBounds.getMinX(),
                  super.absoluteBounds.getMinY(),
                  super.absoluteBounds.getWidth(),
                  super.absoluteBounds.getHeight(),
                Color.RED, Color.YELLOW);
//        g.drawOval(c.safeScale(super.absoluteBounds.getMinX()),
//                c.safeScale(super.absoluteBounds.getMinY()),
//                c.safeScale(super.absoluteBounds.getWidth()),
//                c.safeScale(super.absoluteBounds.getHeight()),
//                Color.RED, Color.YELLOW);
//        g.draw(super.shape, Color.RED, Color.YELLOW);
    }

    @Override
    protected Shape updateShape() {
        double centerX = super.absoluteBounds.getMaxX() - super.absoluteBounds.getMinX();
        double centerY = super.absoluteBounds.getMaxY() - super.absoluteBounds.getMinY();
        double radius = super.absoluteBounds.getWidth() / 2;
        return new javafx.scene.shape.Circle(centerX, centerY, radius);
    }
}
