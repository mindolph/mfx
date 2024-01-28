package com.mindolph.mfx.util;

import javafx.geometry.*;

import java.awt.*;

/**
 * Some useful utils for converting awt geometry classes to fx geometry classes and vice versa.
 *
 * @author mindolph.com@gmail.com
 */
public class AwtConvertUtils {
    public static Rectangle2D awtRectangle2D2Rectangle2D(java.awt.geom.Rectangle2D rect) {
        if (rect == null) {
            return null;
        }
        return new Rectangle2D(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
    }

    public static Rectangle2D awtRectangle2Rectangle2D(Rectangle rect) {
        if (rect == null) {
            return null;
        }
        return new Rectangle2D(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
    }

    public static Rectangle rectangle2D2AwtRectangle(Rectangle2D rect) {
        if (rect == null) {
            return null;
        }
        return new Rectangle((int) rect.getMinX(), (int) rect.getMinY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    public static Dimension2D awtDimension2Dimension2D(Dimension dim) {
        if (dim == null) {
            return null;
        }
        return new Dimension2D(dim.getWidth(), dim.getHeight());
    }

    public static Rectangle boundsToAwtRectangle(Bounds bounds) {
        return new Rectangle(0, 0, (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    public static Bounds awtDimension2DToBounds(java.awt.geom.Dimension2D dim) {
        return new BoundingBox(0, 0, dim.getWidth(), dim.getHeight());
    }

    public static Bounds awtDimensionToBounds(Dimension dim) {
        return new BoundingBox(0, 0, dim.getWidth(), dim.getHeight());
    }

    public static Dimension boundsToAwtDimension(Bounds bounds) {
        return new Dimension((int) bounds.getWidth(), (int) bounds.getHeight());
    }

    public static Point toAwtPoint(double x, double y) {
        return new Point((int) x, (int) y);
    }

    public static Point point2DtoAwtPoint(Point2D p) {
        return new Point((int) p.getX(), (int) p.getY());
    }
}
