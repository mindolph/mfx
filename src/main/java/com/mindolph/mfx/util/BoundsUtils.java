package com.mindolph.mfx.util;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

/**
 * @author mindolph.com@gmail.com
 */
public class BoundsUtils {

    /**
     * Deep clone a Bounds.
     *
     * @param b
     * @return
     */
    public static Bounds clone(Bounds b) {
        return new BoundingBox(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
    }

    /**
     * Display bounds size in string.
     *
     * @param bounds
     * @return
     */
    public static String boundsSizeInString(Bounds bounds) {
        return String.format("%.1f x %.1f", bounds.getWidth(), bounds.getHeight());
    }

    /**
     * Display bounds in string.
     *
     * @param bounds
     * @return
     */
    public static String boundsInString(Bounds bounds) {
        return String.format("(%.1f, %.1f) (%.1f x %.1f)", bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
    }

    /**
     * Get center Point from a Bounds.
     *
     * @param b
     * @return
     */
    public static Point2D centerPoint(Bounds b) {
        return new Point2D(b.getCenterX(), b.getCenterY());
    }

    /**
     * New bounds with specified width.
     *
     * @param bounds
     * @param width
     * @return
     */
    public static Bounds newWithWidth(Bounds bounds, double width) {
        return new BoundingBox(bounds.getMinX(), bounds.getMinY(), width, bounds.getHeight());
    }

    /**
     * New bounds with specified height.
     *
     * @param bounds
     * @param height
     * @return
     */
    public static Bounds newWithHeight(Bounds bounds, double height) {
        return new BoundingBox(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), height);
    }

    /**
     * New bounds with zero x, y, width and height.
     *
     * @return
     */
    public static Bounds newZero() {
        return new BoundingBox(0, 0, 0, 0);
    }

    /**
     * New bounds with specified width and height.
     *
     * @param startPoint
     * @param width
     * @param height
     * @return
     */
    public static Bounds fromPoint(Point2D startPoint, double width, double height) {
        return new BoundingBox(startPoint.getX(), startPoint.getY(), width, height);
    }

    /**
     * Whether bounds width and height are not zero.
     *
     * @param bounds
     * @return
     * @since 2.0
     */
    public static boolean isInvisible(Bounds bounds) {
        return bounds.isEmpty() || bounds.getWidth() == 0 || bounds.getHeight() == 0;
    }
}
