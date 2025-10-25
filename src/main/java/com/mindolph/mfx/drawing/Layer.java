package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Component;
import com.mindolph.mfx.drawing.component.Container;
import com.mindolph.mfx.drawing.component.Group;
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
        if (drawable instanceof Container c) {
            drawables.addAll(c.getDescendants());
        }
    }

    public void addAll(Drawable... drawables) {
        for (Drawable drawable : drawables) {
            this.add(drawable);
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
            if (drawable instanceof BaseComponent c) {
                c.setActivated(false);
            }
        }
    }

    public void draw(Graphics g, Context c) {
        if (log.isTraceEnabled()) log.trace("Draw for layer %s".formatted(name));
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
                // draw children
                if (drawable instanceof Container container) {
                    container.drawChildren(g, c);
                }
            }
            else {
                if (log.isTraceEnabled()) log.trace("no intersection found");
            }
        }
    }

    public Drawable getDrawable(int index) {
        return drawables.get(index);
    }

    public List<Drawable> getElements() {
        return drawables;
    }

    public List<Drawable> getElements(Point2D point) {
        List<Drawable> elements = new LinkedList<>();
        for (Drawable drawable : drawables) {
            if (drawable.getAbsoluteBounds().contains(point)) {
                log.debug("on element: %s".formatted(drawable));
                elements.add(drawable);
            }
            // find elements in Group (TODO group of group?)
            if (drawable instanceof Group g) {
                for (Drawable component : g.getComponents()) {
                    if (component.getAbsoluteBounds().contains(point)) {
                        log.debug("on element: %s".formatted(component));
                        elements.add(component);
                    }
                }
            }
        }
        return elements;
    }

    public String getName() {
        return name;
    }
}
