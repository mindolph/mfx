package com.mindolph.mfx.drawing;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class LineConnector extends Connector{
    public LineConnector(Component from, Component to) {
        super(from, to);
    }

    public LineConnector(Component from, Component to, Point2D pointFrom, Point2D pointTo) {
        super(from, to, pointFrom, pointTo);
    }

    public LineConnector(Component from, Component to, double x1, double y1, double x2, double y2) {
        super(from, to, x1, y1, x2, y2);
    }

    public LineConnector(Layer layer, Component from, Component to, Point2D pointFrom, Point2D pointTo) {
        super(layer, from, to, pointFrom, pointTo);
    }

    public LineConnector(Layer layer, Component from, Component to) {
        super(layer, from, to);
    }

    @Override
    public void draw(Graphics g, Context context) {
        super.draw(g, context);
        g.drawLine(absolutePointFrom, absolutePointTo, Color.BLACK);
    }
}
