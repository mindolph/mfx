package com.mindolph.mfx.drawing.component;

import com.mindolph.mfx.drawing.Context;
import com.mindolph.mfx.drawing.Drawable;
import com.mindolph.mfx.drawing.Graphics;
import com.mindolph.mfx.drawing.connector.Connector;
import com.mindolph.mfx.util.RectangleUtils;
import javafx.geometry.Rectangle2D;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Group of components.
 *
 * @since 3.0
 */
public class Group extends Component {

    protected Collection<Drawable> components;

    public Group() {
    }

    public Group(Rectangle2D bounds) {
        super(bounds);
    }

    public static Group of(Drawable... drawables) {
        if (drawables == null || drawables.length == 0) {
            throw new IllegalArgumentException("Elements must not be empty");
        }
        if (Arrays.stream(drawables).anyMatch(drawable -> {
            if (drawable instanceof Component component) {
                return component.getParent() != null;
            }
            return false;
        })) {
            throw new IllegalStateException("Elements have parent can't be grouped");
        }
        Rectangle2D unionRect = Arrays.stream(drawables).map(Drawable::getBounds).reduce(null
                , RectangleUtils::union);
        Group group = new Group(unionRect);
        group.addAll(drawables);
        for (Drawable drawable : drawables) {
            if (drawable instanceof Component component) {
                component.setGroup(group);
            }
            else if (drawable instanceof Connector connector) {
                connector.setGroup(group);
            }
        }
        return group;
    }

    // TBD
    public void add(Drawable drawable) {
        if (components == null) {
            components = new LinkedList<>();
        }
        components.add(drawable);
    }

    // TBD
    public void addAll(Drawable... drawables) {
        if (components == null) {
            components = new LinkedList<>();
        }
        components.addAll(List.of(drawables));
    }

//    public Group(Rectangle2D bounds, Layer layer) {
//        super(layer, bounds);
//    }


    @Override
    public void draw(Graphics g, Context context) {
        components.forEach(component -> component.draw(g, context));
    }

    @Override
    public void updateBounds(Context c) {
        super.updateBounds(c);
        // must update grouped components.
        components.forEach(component -> component.updateBounds(c));
    }

    public Collection<Drawable> getComponents() {
        return components;
    }

}
