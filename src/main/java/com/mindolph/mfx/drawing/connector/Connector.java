package com.mindolph.mfx.drawing.connector;

import com.mindolph.mfx.drawing.*;
import com.mindolph.mfx.drawing.component.Component;
import com.mindolph.mfx.drawing.constant.StrokeType;
import com.mindolph.mfx.util.RectangleUtils;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

/**
 *
 * @since 2.0
 */
public class Connector implements Drawable {

    protected Layer layer;

    protected Component from;

    protected Component to;

    /**
     * If component 'from' is not null, the point is related to the component, otherwise is non-related.
     */
    protected Point2D pointFrom;
    protected Point2D absolutePointFrom;

    /**
     * If component 'to' is not null, the point is related to the component, otherwise is non-related.
     */
    protected Point2D pointTo;
    protected Point2D absolutePointTo;

    protected boolean activated;

    public Connector(Component from, Component to) {
        this.from = from;
        this.to = to;
    }

    public Connector(Component from, Component to, Point2D pointFrom, Point2D pointTo) {
        this.from = from;
        this.to = to;
        this.pointFrom = pointFrom;
        this.pointTo = pointTo;
        this.updateFrom(1f);
        this.updateTo(1f);
    }

    public Connector(Component from, Component to, double x1, double y1, double x2, double y2) {
        this.from = from;
        this.to = to;
        this.pointFrom = new Point2D(x1, y1);
        this.pointTo = new Point2D(x2, y2);
        this.updateFrom(1f);
        this.updateTo(1f);
    }

//    public Connector(Layer layer, Component from, Component to, Point2D pointFrom, Point2D pointTo) {
//        this.layer = layer;
//        this.from = from;
//        this.to = to;
//        this.pointFrom = pointFrom;
//        this.pointTo = pointTo;
//        this.updateFrom(1f);
//        this.updateTo(1f);
//    }
//
//    public Connector(Layer layer, Component from, Component to) {
//        this.layer = layer;
//        this.from = from;
//        this.to = to;
//    }

    private void updateFrom(double scale) {
        double x = pointFrom.getX();
        double y = pointFrom.getY();
        if (from == null) {
            this.absolutePointFrom = new Point2D(x * scale, y * scale);
        }
        else {
            // x, y are relative
            if (x > from.getWidth() || y > from.getHeight()) {
                throw new RuntimeException("point %s,%s is not within the bounds(%s) of component".formatted(x, y, RectangleUtils.rectangleInStr(from.getBounds())));
            }
            Rectangle2D b = from.getAbsoluteBounds();
            this.absolutePointFrom = new Point2D(b.getMinX() + x * scale, b.getMinY() + y * scale);
        }
    }

    private void updateTo(double scale) {
        double x = pointTo.getX();
        double y = pointTo.getY();
        if (to == null) {
            this.absolutePointTo = new Point2D(x * scale, y * scale);
        }
        else {
            // x, y are relative
            if (x > to.getWidth() || y > to.getHeight()) {
                throw new RuntimeException("point %s,%s is not within the bounds(%s) of component".formatted(x, y, RectangleUtils.rectangleInStr(to.getBounds())));
            }
            Rectangle2D b = to.getAbsoluteBounds();
            System.out.println("## " + RectangleUtils.rectangleInStr(b));
            this.absolutePointTo = new Point2D(b.getMinX() + x * scale, b.getMinY() + y * scale);
        }
    }

    @Override
    public void updateBounds(Context c) {
        updateFrom(c.getScale());
        updateTo(c.getScale());
    }

    @Override
    public void draw(Graphics g, Context context) {
        if (context.isDebugMode()) {
            g.setStroke(1, StrokeType.DASHES);
            g.drawRect(this.getAbsoluteBounds(), Color.RED, null);
            g.setStroke(1, StrokeType.SOLID);
        }
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public Rectangle2D getBounds() {
        // TODO  to be a util method.
        double minx = Math.min(pointFrom.getX(), pointTo.getX());
        double width = Math.max(pointFrom.getX(), pointTo.getX()) - minx;
        double miny = Math.min(pointFrom.getY(), pointTo.getY());
        double height = Math.max(pointFrom.getY(), pointTo.getY()) - miny;
        return new Rectangle2D(minx, miny, width, height);
    }

    @Override
    public Rectangle2D getAbsoluteBounds() {
        double minx = Math.min(absolutePointFrom.getX(), absolutePointTo.getX());
        double width = Math.max(absolutePointFrom.getX(), absolutePointTo.getX()) - minx;
        double miny = Math.min(absolutePointFrom.getY(), absolutePointTo.getY());
        double height = Math.max(absolutePointFrom.getY(), absolutePointTo.getY()) - miny;
        return new Rectangle2D(minx, miny, width, height);
    }

    public Point2D getAbsolutePointFrom() {
        return absolutePointFrom;
    }

    public Point2D getAbsolutePointTo() {
        return absolutePointTo;
    }

    @Override
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean isActivated() {
        return activated;
    }
}
