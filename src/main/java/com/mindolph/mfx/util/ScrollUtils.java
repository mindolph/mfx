package com.mindolph.mfx.util;

import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;

/**
 * @author mindolph.com@gmail.com
 */
public class ScrollUtils {

    public static String scrollTo(ScrollPane scrollPane, double x, double y) {
        Bounds viewportBounds = scrollPane.getViewportBounds();
        Bounds layoutBounds = scrollPane.getContent().getLayoutBounds();
        double h = x / (layoutBounds.getWidth() - viewportBounds.getWidth());
        double v = y / (layoutBounds.getHeight() - viewportBounds.getHeight());
        scrollPane.setHvalue(h);
        scrollPane.setVvalue(v);
        return String.format("%.4f, %.4f", h, v);
    }
}
