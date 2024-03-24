package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Component;
import com.mindolph.mfx.drawing.connector.Connector;
import com.mindolph.mfx.util.PointUtils;
import com.mindolph.mfx.util.RectangleUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Layer that determine drawing order.
 */
public class Layer {

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

    public boolean contains(Drawable drawable) {
        return drawables.contains(drawable);
    }

    public void draw(Graphics g, Context c) {
        for (Drawable drawable : drawables) {
            if (drawable instanceof Component) {
                System.out.printf("Draw %s %s%n", drawable, RectangleUtils.rectangleInStr(drawable.getAbsoluteBounds()));
            }
            else if (drawable instanceof Connector cnt) {
                System.out.printf("Draw %s %s %s%n", drawable, PointUtils.pointInStr(cnt.getAbsolutePointFrom()), PointUtils.pointInStr(cnt.getAbsolutePointTo()));
            }
            if (g.getClipBounds().intersects(drawable.getAbsoluteBounds())) {
                drawable.draw(g, c);
            }
        }
    }

}
