package com.mindolph.mfx.util;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

/**
 * @author mindolph.com@gmail.com
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

    public static Point2D centerPoint(Bounds b) {
        return new Point2D(b.getCenterX(), b.getCenterY());
    }

    public static Bounds newWithWidth(Bounds bounds, double width) {
        return new BoundingBox(bounds.getMinX(), bounds.getMinY(), width, bounds.getHeight());
    }

    public static Bounds newWithHeight(Bounds bounds, double height) {
        return new BoundingBox(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), height);
    }

    public static Bounds newZero() {
        return new BoundingBox(0, 0, 0, 0);
    }

    public static Bounds fromPoint(Point2D startPoint, double width, double height) {
        return new BoundingBox(startPoint.getX(), startPoint.getY(), width, height);
    }

    /**
     *
     * @param bounds
     * @return
     * @since 2.0
     */
    public static boolean isInvisible(Bounds bounds) {
        return bounds.isEmpty() || bounds.getWidth() == 0 || bounds.getHeight() == 0;
    }
}
