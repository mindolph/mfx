package com.mindolph.mfx.drawing.component;

import com.mindolph.mfx.drawing.Context;
import com.mindolph.mfx.drawing.Graphics;
import javafx.geometry.Rectangle2D;

import java.util.*;

/**
 * @since 3.0
 */
public class Container extends Component {

    protected Collection<Component> children;

    public Container() {
    }

    public Container(Rectangle2D bounds) {
        super(bounds);
    }

    public Container(double x, double y, double width, double height) {
        super(x, y, width, height);
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

    protected void drawChildren(Graphics g, Context c) {
        if (this.children != null) {
            for (Component child : this.children) {
                child.draw(g, c);
            }
        }
    }

    public void updateBounds(Context c) {
        super.updateBounds(c);
        // subcomponent's bounds need to be re-calculated with parent component.
        if (children != null) {
            for (Component child : children) {
                child.updateBounds(c);
            }
        }
    }

    private double calcMaxSubComponentWidth() {
        Optional<Component> max = children.stream().max(Comparator.comparingDouble(Component::getWidth));
        return max.map(Component::getWidth).orElse(0.0);
    }

    private double calcMaxSubComponentHeight() {
        Optional<Component> max = children.stream().max(Comparator.comparingDouble(Component::getHeight));
        return max.map(Component::getHeight).orElse(0.0);
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
                if (child instanceof Container cc) {
                    got.addAll(cc.getDescendants());
                }
            }
        }
        return got;
    }

    public Collection<Component> getChildren() {
        return children;
    }
}
