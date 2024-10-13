package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Component;
import com.mindolph.mfx.drawing.connector.Connector;
import com.mindolph.mfx.util.PointUtils;
import com.mindolph.mfx.util.RectangleUtils;
import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Layer that determine drawing order.
 *
 * @since 2.0
 */
public class Layer {

    private static final Logger log = LoggerFactory.getLogger(Layer.class);

    private final String name;

    private final List<Drawable> drawables;

    public Layer(String name) {
        this.name = name;
        this.drawables = new LinkedList<>();
    }

    public void add(Drawable drawable) {
        drawables.add(drawable);
        if (drawable instanceof Component c) {
            drawables.addAll(c.getDescendants());
        }
    }

    public void remove(Drawable drawable) {
        drawables.remove(drawable);
    }

    public void removeAll() {
        drawables.clear();
    }

    public boolean contains(Drawable drawable) {
        return drawables.contains(drawable);
    }

    public void clearActivation() {
        for (Drawable drawable : drawables) {
            drawable.setActivated(false);
        }
    }

    public void draw(Graphics g, Context c) {
        for (Drawable drawable : drawables) {
            if (drawable instanceof Component) {
                if (log.isTraceEnabled())
                    log.trace(String.format("Draw %s %s%n", drawable, RectangleUtils.rectangleInStr(drawable.getAbsoluteBounds())));
            }
            else if (drawable instanceof Connector cnt) {
                if (log.isTraceEnabled())
                    log.trace(String.format("Draw %s %s %s%n", drawable, PointUtils.pointInStr(cnt.getAbsolutePointFrom()), PointUtils.pointInStr(cnt.getAbsolutePointTo())));
            }
            if (g.getClipBounds().intersects(drawable.getAbsoluteBounds())) {
                drawable.draw(g, c);
            }
            else {
                log.debug("no intersection found");
            }
        }
    }

    public Drawable getDrawable(int index) {
        return drawables.get(index);
    }

    public List<Drawable> getElements(Point2D point) {
        List<Drawable> elements = new LinkedList<>();
        for (Drawable drawable : drawables) {
            if (drawable.getAbsoluteBounds().contains(point)) {
                elements.add(drawable);
            }
        }
        return elements;
    }
}
