package com.mindolph.mfx.util;

import javafx.geometry.Rectangle2D;

/**
 * @author allen
 */
public class RectangleUtils {

    public static String rectangleInStr(Rectangle2D rectangle2D) {
        if (rectangle2D == null) {
            return null;
        }
        return String.format("(%.2f, %.2f) (%.1f x %.1f)", rectangle2D.getMinX(), rectangle2D.getMinY(),
                rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public static String sizeInStr(Rectangle2D rectangle2D) {
        if (rectangle2D == null) {
            return null;
        }
        return String.format("(%.1f x %.1f)", rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public static boolean sizeEquals(Rectangle2D r1, Rectangle2D r2) {
        if (r1 == null && r2 == null) {
            throw new RuntimeException("At least one rectangle is not null");
        }
        if (r1 == null || r2 == null) {
            return false;
        }
        return r1.getWidth() == r2.getWidth() && r1.getHeight() == r2.getHeight();
    }

    public static Rectangle2D newWithX(Rectangle2D r, double x) {
        return new Rectangle2D(x, r.getMinY(), r.getWidth(), r.getHeight());
    }

    public static Rectangle2D newWithY(Rectangle2D r, double y) {
        return new Rectangle2D(r.getMinX(), y, r.getWidth(), r.getHeight());
    }

    public static Rectangle2D newWithWidth(Rectangle2D bounds, double width) {
        return new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, bounds.getHeight());
    }

    public static Rectangle2D newWithHeight(Rectangle2D bounds, double height) {
        return new Rectangle2D(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), height);
    }

    public static Rectangle2D newZero() {
        return new Rectangle2D(0, 0, 0, 0);
    }

    public static double centerX(Rectangle2D r) {
        return r.getMinX() + (r.getWidth() / 2);
    }

    public static double centerY(Rectangle2D r) {
        return r.getMinY() + (r.getHeight() / 2);
    }

    public static Rectangle2D copy(Rectangle2D r) {
        return new Rectangle2D(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
    }

    public static boolean isZero(Rectangle2D r) {
        return r.getMinY() == 0 && r.getMinX() == 0 && r.getWidth() == 0 && r.getHeight() == 0;
    }
}
