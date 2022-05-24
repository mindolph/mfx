package com.mindolph.mfx.util;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * @author allen
 */
public class BoundsUtils {

    public static Bounds clone(Bounds b) {
        return new BoundingBox(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
    }

    public static String boundsSizeInString(Bounds bounds) {
        return String.format("%.1f x %.1f", bounds.getWidth(), bounds.getHeight());
    }

    public static String boundsInString(Bounds bounds) {
        return String.format("(%.1f, %.1f) (%.1f x %.1f)", bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
    }

    public static Bounds newWithWidth(Bounds bounds, double width) {
        return new BoundingBox(bounds.getMinX(), bounds.getMinY(), width, bounds.getHeight());
    }

    public static Bounds newWithHeight(Bounds bounds, double height) {
        return new BoundingBox(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), height);
    }
}
