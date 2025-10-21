package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Container;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

public class TriangleComp extends Container {

    public TriangleComp() {
    }

    public TriangleComp(Rectangle2D bounds) {
        super(bounds);
    }

    public TriangleComp(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

//    public Triangle(Layer layer, Rectangle2D bounds) {
//        super(layer, bounds);
//    }
//
//    public Triangle(Layer layer, double x, double y, double width, double height) {
//        super(layer, x, y, width, height);
//    }

    @Override
    public void draw(Graphics g, Context c) {
        super.draw(g, c);
        Point2D minPoint = super.getAbsoluteMinPoint();
        Point2D maxPoint = super.getAbsoluteMaxPoint();
        g.drawLine(minPoint, new Point2D(super.absoluteBounds.getMaxX(), minPoint.getY() + super.absoluteBounds.getHeight() / 2), Color.GREEN);
        g.drawLine(minPoint, new Point2D(super.absoluteBounds.getMinX(), super.absoluteBounds.getMaxY()), Color.GREEN);
        g.drawLine(super.absoluteBounds.getMaxX(), minPoint.getY() + super.absoluteBounds.getHeight() / 2, super.absoluteBounds.getMinX(), maxPoint.getY(), Color.GREEN);
    }

}
