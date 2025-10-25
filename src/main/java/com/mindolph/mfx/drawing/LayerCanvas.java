package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Component;
import com.mindolph.mfx.drawing.connector.Connector;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * High level canvas that manages layers to draw different components.
 *
 * @since 2.0
 */
public class LayerCanvas {

    private final Graphics g;
    private final Context context;
    private final List<Layer> layers;
    private final Layer baseLayer = new Layer("Base Layer");
    private final Layer selectionLayer = new Layer("Selection Layer");

    private final List<Drawable> allDrawables;
    private final List<Selection> selections;

    public LayerCanvas(Graphics g, Context context) {
        this.g = g;
        this.context = context;
        this.layers = new LinkedList<>();
        this.layers.add(baseLayer);
        this.allDrawables = new LinkedList<>();
        this.selections = new LinkedList<>();
    }

    public LayerCanvas(Graphics g, Context context, List<Layer> layers) {
        this.g = g;
        this.context = context;
        this.layers = new LinkedList<>();
        this.layers.add(baseLayer);
        this.layers.addAll(layers);
        this.allDrawables = new LinkedList<>();
        this.selections = new LinkedList<>();
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public void addLayers(Layer... ls) {
        layers.addAll(List.of(ls));
    }

    public void add(Drawable drawable) {
        baseLayer.add(drawable);
        allDrawables.add(drawable);
    }

    public void add(Layer layer, Drawable drawable) {
        if (!layers.contains(layer)) {
            layers.add(layer);
        }
        layer.add(drawable);
        allDrawables.add(drawable);
    }

    public void addAll(Layer layer, Drawable... drawables) {
        if (!layers.contains(layer)) {
            layers.add(layer);
        }
        layer.addAll(drawables);
        allDrawables.addAll(List.of(drawables));
    }

    public void add(Selection selection) {
        selectionLayer.add(selection);
        selections.add(selection);
    }

    public void clear() {
        layers.forEach(Layer::removeAll);
        baseLayer.removeAll();
        selectionLayer.removeAll();
        layers.clear();
        allDrawables.clear();
        selections.clear();
    }

//    public void add(Layer layer) {
//        if (!layers.contains(layer)) {
//            layers.add(layer);
//        }
//    }

    public void moveTo(Drawable drawable, Point2D point) {
        if (!allDrawables.contains(drawable)) {
            throw new IllegalStateException("Can't move drawable that is not in this canvas");
        }
        if (drawable instanceof Component c) {
            c.moveTo(point);
            c.updateBounds(this.context);
        }
    }

    public void moveTo(Drawable drawable, double x, double y) {
        if (!allDrawables.contains(drawable)) {
            throw new IllegalStateException("Can't move drawable that is not in this canvas");
        }
        if (drawable instanceof Component c) {
            c.moveTo(x, y);
            c.updateBounds(this.context);
        }
    }

    public void moveTo(Drawable drawable, Rectangle2D rectangle) {
        if (!allDrawables.contains(drawable)) {
            throw new IllegalStateException("Can't move drawable that is not in this canvas");
        }
        if (drawable instanceof Component c) {
            c.moveTo(rectangle);
            c.updateBounds(this.context);
        }
    }

    public void moveTo(Drawable drawable, double x, double y, double width, double height) {
        if (!allDrawables.contains(drawable)) {
            throw new IllegalStateException("Can't move drawable that is not in this canvas");
        }
        if (drawable instanceof Component c) {
            c.moveTo(x, y, width, height);
            c.updateBounds(this.context);
        }
    }

    public void clearActivation() {
        layers.forEach(Layer::clearActivation);
        selectionLayer.removeAll();
    }

    /**
     * Activate components by predication.
     *
     * @param predicate
     */
    public List<Drawable> select(Predicate<Drawable> predicate) {
        Stream<Drawable> selectStream = allDrawables.stream().filter(predicate);
        List<Drawable> select = new LinkedList<>();
        selectStream.forEach(drawable -> {
            // TBD?
            if (drawable instanceof BaseComponent c) {
                c.setActivated(true);
            }
            select.add(drawable);
        });
        return select;
    }

    /**
     * Deactivate components by predication.
     *
     * @param predicate
     * @return
     */
    public List<Drawable> unSelect(Predicate<Drawable> predicate) {
        Stream<Drawable> unselectStream = allDrawables.stream().filter(predicate);
        List<Drawable> unselect = new LinkedList<>();
        unselectStream.forEach(drawable -> {
            // TBD?
            if (drawable instanceof BaseComponent c) {
                c.setActivated(false);
            }
            unselect.add(drawable);
        });
        return unselect;
    }

    public void updateAllBounds() {
        // update bounds of all components.
        for (Drawable drawable : allDrawables.stream().filter(d -> d instanceof Component).toList()) {
            drawable.updateBounds(this.context);
        }
        // update bounds of all connectors.
        for (Drawable drawable : allDrawables.stream().filter(d -> d instanceof Connector).toList()) {
            drawable.updateBounds(this.context);
        }
        // update bounds of all selections.
        selections.forEach(selection -> {
            selection.updateBounds(this.context);
        });
    }

    /**
     * Draw all elements by layer order.
     */
    public void drawLayers() {
        if (this.context.isDebugMode()) {
            g.drawRect(g.getClipBounds(), Color.BLUE, null);
        }
        for (Layer layer : layers) {
            layer.draw(this.g, this.context);
        }
        selectionLayer.draw(this.g, this.context);
    }
    
    public List<Drawable> getElements() {
        return layers.stream().flatMap((Function<Layer, Stream<Drawable>>) layer -> layer.getElements().stream()).toList();
    }

    public List<Drawable> getElements(Point2D point) {
        List<Drawable> drawables = new LinkedList<>();
        for (Layer layer : layers) {
            drawables.addAll(layer.getElements(point));
        }
        return drawables;
    }

    public List<Selection> getSelections() {
        return selections.stream().toList();
    }

    public Layer getBaseLayer() {
        return baseLayer;
    }

    public Layer getSelectionLayer() {
        return selectionLayer;
    }

    public List<Layer> getLayers() {
        return layers;
    }
}
