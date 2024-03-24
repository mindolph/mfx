package com.mindolph.mfx.drawing.connector;

import com.mindolph.mfx.drawing.Context;
import com.mindolph.mfx.drawing.Graphics;
import com.mindolph.mfx.drawing.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class BezierConnector extends Connector {
    public BezierConnector(Component from, Component to) {
        super(from, to);
    }

    public BezierConnector(Component from, Component to, Point2D pointFrom, Point2D pointTo) {
        super(from, to, pointFrom, pointTo);
    }

    public BezierConnector(Component from, Component to, double x1, double y1, double x2, double y2) {
        super(from, to, x1, y1, x2, y2);
    }

//    public BezierConnector(Layer layer, Component from, Component to, Point2D pointFrom, Point2D pointTo) {
//        super(layer, from, to, pointFrom, pointTo);
//    }
//
//    public BezierConnector(Layer layer, Component from, Component to) {
//        super(layer, from, to);
//    }

    @Override
    public void draw(Graphics g, Context context) {
        super.draw(g, context);
        g.drawBezier(super.absolutePointFrom.getX(), super.absolutePointFrom.getY(), super.absolutePointTo.getX(), super.absolutePointTo.getY(), Color.BLACK);
    }
}
