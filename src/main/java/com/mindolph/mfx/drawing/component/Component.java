package com.mindolph.mfx.drawing.component;


import com.mindolph.mfx.drawing.*;
import com.mindolph.mfx.drawing.constant.StrokeType;
import com.mindolph.mfx.util.RectangleUtils;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.*;

/**
 * Anything drawing on canvas is Component, any component has its parent component and subcomponents.
 * The bounds of a component is always relative to its parent component unless no parent at all.
 * @since 2.0
 */
public class Component implements Drawable {

    protected Serializable id;

    protected Layer layer;

    protected Component parent;

    protected Collection<Component> children;

    /**
     * Relative to parent unless no parent.
     */
    protected Rectangle2D bounds;

    protected Rectangle2D absoluteBounds;

    protected boolean activated;

    public Component() {
    }

    public Component(Rectangle2D bounds) {
        this.bounds = bounds;
        this.absoluteBounds = bounds;
    }

    public Component(double x, double y, double width, double height) {
        this.bounds = new Rectangle2D(x, y, width, height);
        this.absoluteBounds = new Rectangle2D(x, y, width, height);
    }

//    public Component(Layer layer, Rectangle2D bounds) {
//        this.layer = layer;
//        this.bounds = bounds;
//        this.absoluteBounds = bounds; // TODO
//        this.layer.add(this);
//    }
//
//    public Component(Layer layer, double x, double y, double width, double height) {
//        this.layer = layer;
//        this.bounds = new Rectangle2D(x, y, width, height);
//        this.absoluteBounds = new Rectangle2D(x, y, width, height);
//        this.layer.add(this);
//    }


    @Override
    public void draw(Graphics g, Context context) {
        if (context.isDebugMode()) {
            g.setStroke(1, StrokeType.DASHES);
            g.drawRect(this.absoluteBounds, Color.RED, null);
            g.setStroke(1, StrokeType.SOLID);
        }
    }

    public void add(Component child) {
        if (this.children == null) {
            this.children = new LinkedHashSet<>();
        }
        this.children.add(child);
        child.parent = this;

        // adjust bounds if subcomponents bounds is larger.
        this.bounds = new Rectangle2D(this.bounds.getMinX(), this.bounds.getMinY(),
                Math.max(this.bounds.getWidth(), this.calcMaxSubComponentWidth()),
                Math.max(this.bounds.getHeight(), this.calcMaxSubComponentHeight())
        );
    }

    public void updateBounds(Context c) {
        if (parent != null) {
            this.absoluteBounds = new Rectangle2D(parent.absoluteBounds.getMinX() + c.safeScale(bounds.getMinX(), 0),
                    parent.absoluteBounds.getMinY() + c.safeScale(bounds.getMinY(), 0),
                    c.safeScale(bounds.getWidth(), 1), c.safeScale(bounds.getHeight(), 1));
        }
        else {
            this.absoluteBounds = c.safeScale(this.bounds, 1);
        }
        // subcomponent's bounds need to be re-calculated with parent component.
        if (children != null) {
            for (Component child : children) {
                child.updateBounds(c);
            }
        }
    }

    public void moveTo(Point2D point) {
        this.moveTo(point.getX(), point.getY());
    }

    public void moveTo(double x, double y) {
        this.bounds = RectangleUtils.newWithXY(this.bounds, x, y);
    }

    public void moveTo(Rectangle2D bounds) {
        this.bounds = bounds;
    }

    public void moveTo(double x, double y, double width, double height) {
        this.bounds = new Rectangle2D(x, y, width, height);
    }

    private double calcMaxSubComponentWidth() {
        Optional<Component> max = children.stream().max(Comparator.comparingDouble(Component::getWidth));
        return max.map(Component::getWidth).orElse(0.0);
    }

    private double calcMaxSubComponentHeight() {
        Optional<Component> max = children.stream().max(Comparator.comparingDouble(Component::getHeight));
        return max.map(Component::getHeight).orElse(0.0);
    }

    @Override
    public Serializable getId() {
        return id;
    }

    @Override
    public void setId(Serializable id) {
        this.id = id;
    }

    @Override
    public Rectangle2D getBounds() {
        return bounds;
    }

    @Override
    public Rectangle2D getAbsoluteBounds() {
        return absoluteBounds;
    }

    public Point2D getMinPoint() {
        return new Point2D(getBounds().getMinX(), getBounds().getMinY());
    }


    public Point2D getMaxPoint() {
        return new Point2D(getBounds().getMaxX(), getBounds().getMaxY());
    }

    public Point2D getAbsoluteMinPoint() {
        return new Point2D(absoluteBounds.getMinX(), absoluteBounds.getMinY());
    }


    public Point2D getAbsoluteMaxPoint() {
        return new Point2D(absoluteBounds.getMaxX(), absoluteBounds.getMaxY());
    }

    public double getMinX() {
        return getBounds().getMinX();
    }

    public double getMinY() {
        return getBounds().getMinY();
    }

    public double getMaxX() {
        return getBounds().getMaxX();
    }

    public double getMaxY() {
        return getBounds().getMaxY();
    }

    public double getWidth() {
        return bounds.getWidth();
    }

    public double getHeight() {
        return bounds.getHeight();
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public Component getParent() {
        return parent;
    }

    public Collection<Component> getChildren() {
        return children;
    }

    @Override
    public boolean isActivated() {
        return activated;
    }

    @Override
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * TODO performance optimization needed.
     *
     * @return
     */
    public Collection<Component> getDescendants() {
        Collection<Component> got = new ArrayList<>();
        if (children != null) {
            for (Component child : children) {
                got.add(child);
                got.addAll(child.getDescendants());
            }
        }
        return got;
    }

//    public void setContext(Context context) {
//        this.context = context;
//    }
}
