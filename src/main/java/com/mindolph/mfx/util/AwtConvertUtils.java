package com.mindolph.mfx.util;

import javafx.geometry.*;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.image.PixelGrabber;

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


    /**
     * Convert awt {@link java.awt.Image} to javafx {@link javafx.scene.image.Image}.
     *
     * @param image
     * @return
     * @throws InterruptedException
     */
    public static javafx.scene.image.Image fxImageToAwtImage(java.awt.Image image) throws InterruptedException {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        int[] pixels = new int[width * height];
        PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
        grabber.grabPixels();
        WritableImage fxImage = new WritableImage(width, height);
        PixelWriter pw = fxImage.getPixelWriter();
        pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
        return fxImage;
    }

}
