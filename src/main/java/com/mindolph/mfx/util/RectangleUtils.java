package com.mindolph.mfx.util;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import org.apache.commons.lang3.Range;

/**
 * @author mindolph.com@gmail.com
 */
public class RectangleUtils {

    public static String rectangleInStr(Rectangle2D rectangle2D) {
        if (rectangle2D == null) {
            return null;
        }
        return String.format("(%.2f, %.2f) (%.1f x %.1f)", rectangle2D.getMinX(), rectangle2D.getMinY(),
                rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    /**
     * Include maxX and maxY
     *
     * @param rectangle2D
     * @return
     */
    public static String rectangleAllInStr(Rectangle2D rectangle2D) {
        if (rectangle2D == null) {
            return null;
        }
        return String.format("(%.2f, %.2f) (%.1f, %.1f) (%.1f x %.1f)", rectangle2D.getMinX(), rectangle2D.getMinY(),
                rectangle2D.getMaxX(), rectangle2D.getMaxY(), rectangle2D.getWidth(), rectangle2D.getHeight());
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

    public static Point2D centerPoint(Rectangle2D b) {
        return new Point2D(b.getMaxX() - b.getMinX(), b.getMaxY() - b.getMinY());
    }

    public static Rectangle2D newWithX(Rectangle2D r, double x) {
        return new Rectangle2D(x, r.getMinY(), r.getWidth(), r.getHeight());
    }

    public static Rectangle2D newWithY(Rectangle2D r, double y) {
        return new Rectangle2D(r.getMinX(), y, r.getWidth(), r.getHeight());
    }

    public static Rectangle2D newWithXY(Rectangle2D r, double x, double y) {
        return new Rectangle2D(x, y, r.getWidth(), r.getHeight());
    }

    public static Rectangle2D newWithWidth(Rectangle2D bounds, double width) {
        return new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, bounds.getHeight());
    }

    public static Rectangle2D newWithHeight(Rectangle2D bounds, double height) {
        return new Rectangle2D(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), height);
    }

    public static Rectangle2D newWithWidthHeight(Rectangle2D bounds, double width, double height) {
        return new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height);
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

    public static Rectangle2D intersect(Rectangle2D r1, Rectangle2D r2) {
        if (r1 == null || r2 == null || !r1.intersects(r2)) {
            return null;
        }
        Range<Double> interX = Range.of(r1.getMinX(), r1.getMaxX()).intersectionWith(Range.of(r2.getMinX(), r2.getMaxX()));
        Range<Double> interY = Range.of(r1.getMinY(), r1.getMaxY()).intersectionWith(Range.of(r2.getMinY(), r2.getMaxY()));
        return new Rectangle2D(
                interX.getMinimum(),
                interY.getMinimum(),
                interX.getMaximum() - interX.getMinimum(),
                interY.getMaximum() - interY.getMinimum()
        );
    }

    /**
     * Union two {@code Rectangle}s even they do not intersect.
     *
     * @param r1
     * @param r2
     * @return
     */
    public static Rectangle2D union(Rectangle2D r1, Rectangle2D r2) {
        if (r1 == null && r2 == null) {
            return null;
        }
        if (r1 == null) return r2;
        if (r2 == null) return r1;
        return new Rectangle2D(
                Math.min(r1.getMinX(), r2.getMinX()),
                Math.min(r1.getMinY(), r2.getMinY()),
                Math.abs(r2.getMaxX() - r1.getMinX()),
                Math.abs(r2.getMaxY() - r1.getMinY())
        );
    }

    public static Rectangle2D enlarge(Rectangle2D r, double more) {
        return new Rectangle2D(
                r.getMinX() - more,
                r.getMinY() - more,
                r.getWidth() + more * 2,
                r.getHeight() + more * 2
        );
    }

    public static Rectangle2D enlarge(Rectangle2D r, double minX, double minY, double maxX, double maxY) {
        return new Rectangle2D(
                r.getMinX() - minX,
                r.getMinY() - minY,
                r.getWidth() + minX + maxX,
                r.getHeight() + minY + maxY
        );
    }

    public static Rectangle2D enlarge(Rectangle2D r, double leftRight, double topBottom) {
        return new Rectangle2D(
                r.getMinX() - leftRight,
                r.getMinY() - topBottom,
                r.getWidth() + leftRight * 2,
                r.getHeight() + topBottom * 2
        );
    }
}
