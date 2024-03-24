package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Component;
import com.mindolph.mfx.drawing.connector.Connector;

import java.util.LinkedList;
import java.util.List;

/**
 * High level canvas that manages layers to draw different components.
 */
public class LayerCanvas {

    private final Graphics g;
    private final Context context;
    private final List<Layer> layers;
    private final Layer baseLayer = new Layer("Base Layer");

    private final List<Drawable> allDrawables;

    public LayerCanvas(Graphics g, Context context) {
        this.g = g;
        this.context = context;
        this.layers = new LinkedList<>();
        this.layers.add(baseLayer);
        this.allDrawables = new LinkedList<>();
    }

    public LayerCanvas(Graphics g, Context context, List<Layer> layers) {
        this.g = g;
        this.context = context;
        this.layers = new LinkedList<>();
        this.layers.add(baseLayer);
        this.layers.addAll(layers);
        this.allDrawables = new LinkedList<>();
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

//    public void add(Layer layer) {
//        if (!layers.contains(layer)) {
//            layers.add(layer);
//        }
//    }

    public void updateAllBounds() {
        for (Drawable drawable : allDrawables.stream().filter(d -> d instanceof Component).toList()) {
            drawable.updateBounds(this.context);
        }
        for (Drawable drawable : allDrawables.stream().filter(d -> d instanceof Connector).toList()) {
            drawable.updateBounds(this.context);
        }
    }

    public void drawLayers() {
        for (Layer layer : layers) {
            layer.draw(this.g, this.context);
        }
    }

}
