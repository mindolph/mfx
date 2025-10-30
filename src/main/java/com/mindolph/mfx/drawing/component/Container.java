package com.mindolph.mfx.drawing.component;

import com.mindolph.mfx.drawing.Context;
import com.mindolph.mfx.drawing.Graphics;
import com.mindolph.mfx.drawing.Layer;
import com.mindolph.mfx.drawing.constant.ExtendDirection;
import com.mindolph.mfx.util.RectangleUtils;
import javafx.geometry.Rectangle2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @since 3.0
 */
public class Container extends Component {

    private static final Logger log = LoggerFactory.getLogger(Container.class);

    protected Collection<Component> children;

    protected Set<ExtendDirection> extendDirections = ExtendDirection.RIGHT_DOWN;

    public Container() {
        super();
    }

    public Container(Rectangle2D bounds) {
        super(bounds);
    }

    public Container(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public Container(Rectangle2D bounds, Anchor anchor) {
        super(bounds);
        this.anchor = anchor;
    }

    public Container(double x, double y, double width, double height, Anchor anchor) {
        super(x, y, width, height);
        this.anchor = anchor;
    }


    public void add(Component child) {
        if (this.children == null) {
            this.children = new LinkedHashSet<>();
        }
        this.children.add(child);
        child.parent = this;

        // add to container requires to add to it's layer too.
        if (super.layer != null && !super.layer.contains(child)) {
            super.layer.add(child);
        }

        // adjust bounds if subcomponents bounds is larger.
        this.measureBounds();
    }

    public void addAll(Component... components) {
        if (components == null) {
            throw new IllegalArgumentException("components must not be null");
        }
        Arrays.stream(components).forEach(this::add);
    }

    public void remove(Component child) {
        if (this.children != null) {
            if (super.layer != null) {
                // the removed child must have been added to the same layer either.
                if (!super.layer.remove(child)) {
                    log.warn("Child component to be moved %s is not in the layer: %s".formatted(child.getId(), super.layer.getName()));
                }
            }
            if (this.children.remove(child)) {
                child.parent = null;
//                this.measureBounds();
            }
        }
    }

    private void measureBounds() {
        double extendedWith = Math.max(this.bounds.getWidth(), Component.calcMaxExtentWidth(this.children));
        double extendedHeight = Math.max(this.bounds.getHeight(), Component.calcMaxExtentHeight(this.children));
        this.bounds = new Rectangle2D(
                this.extendDirections.contains(ExtendDirection.LEFT) ? this.bounds.getMaxX() - extendedWith : this.bounds.getMinX(),
                this.extendDirections.contains(ExtendDirection.UP) ? this.bounds.getMaxY() - extendedHeight : this.bounds.getMinY(),
                extendedWith,
                extendedHeight
        );
        if (log.isDebugEnabled())
            log.debug("[%s] Measured bounds: %s".formatted(id, RectangleUtils.rectangleInStr(this.bounds)));
    }

    public void updateBounds(Context c) {
        super.updateBounds(c);
        // subcomponent's bounds need to be re-calculated with parent component.
        if (children != null) {
            for (Component child : children) {
                child.updateBounds(c);
            }
        }
        if (log.isDebugEnabled())
            log.debug("[%s] Updated absolute bounds: %s".formatted(id, RectangleUtils.rectangleInStr(this.absoluteBounds)));
    }

    /**
     * Used for {@link Group} and {@link Layer}
     *
     * @param g
     * @param c
     */
    public void drawChildren(Graphics g, Context c) {
        if (this.children != null) {
            for (Component child : this.children) {
                child.draw(g, c);
            }
        }
    }

//    private double calcMaxSubComponentExtentWidth() {
//        return children.stream().map(c -> c.getMinX() + c.getWidth()).reduce(0.0, Double::max);
//    }
//
//    private double calcMaxSubComponentExtentHeight() {
//        return children.stream().map(c -> c.getMinY() + c.getHeight()).reduce(0.0, Double::max);
//    }

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
        return List.copyOf(this.children);
    }

    public void setExtendDirections(Set<ExtendDirection> extendDirections) {
        this.extendDirections = extendDirections;
    }
}
