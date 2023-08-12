package com.mindolph.mfx.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author allen
 */
public class FxImageUtils {

    /**
     * Read {@code Image} from resource file.
     *
     * @param resourcePath resource path with '/' at the head.
     * @return
     * @throws IOException
     */
    public static Image readImageFromResource(String resourcePath) throws IOException {
        InputStream resourceAsStream = FxImageUtils.class.getResourceAsStream(resourcePath);
        if (resourceAsStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return new Image(resourceAsStream);
    }

    public static byte[] imageToBytes(Image image) throws IOException {
        return AwtImageUtils.imageToBytes(SwingFXUtils.fromFXImage(image, null));
    }

    /**
     * @param img
     * @return
     * @throws IOException
     */
    public static String imageToBase64(Image img) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
        return AwtImageUtils.imageToBase64(bufferedImage);
    }

    /**
     * @param image
     * @param ratio
     * @return
     */
    public static Image resize(Image image, double ratio) {
        int width = (int) (image.getWidth() * ratio);
        int height = (int) (image.getHeight() * ratio);
        Canvas c = new Canvas();
        c.setWidth(width);
        c.setHeight(height);
        GraphicsContext gra = c.getGraphicsContext2D();
        gra.drawImage(image, 0, 0, width, height);
        SnapshotParameters params = new SnapshotParameters();
        return c.snapshot(params, null);
    }

    public static void writeImage(Image image, OutputStream outputStream) throws IOException {
        AwtImageUtils.writeImageAsPng(SwingFXUtils.fromFXImage(image, null), outputStream);
    }

    public static void writeImage(Image image, File file) throws IOException {
        AwtImageUtils.writeImageAsPng(SwingFXUtils.fromFXImage(image, null), file);
    }

    /**
     * Convert awt {@link java.awt.Image} to javafx {@link Image}.
     * TODO move to another class for image converting.
     *
     * @param image
     * @return
     * @throws InterruptedException
     */
    public static Image fromAwtImage(java.awt.Image image) throws InterruptedException {
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
