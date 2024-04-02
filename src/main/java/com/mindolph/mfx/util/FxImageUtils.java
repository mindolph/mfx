package com.mindolph.mfx.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author mindolph.com@gmail.com
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

    /**
     * Convert JavaFX Image object to bytes(by converting to BufferedImage first)
     *
     * @param image
     * @return
     * @throws IOException
     */
    public static byte[] imageToBytes(Image image) throws IOException {
        return AwtImageUtils.imageToBytes(SwingFXUtils.fromFXImage(image, null));
    }

    /**
     * Convert JavaFX Image object to base64 string (by converting to BufferedImage first)
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static String imageToBase64(Image img) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
        return AwtImageUtils.imageToBase64(bufferedImage);
    }

    /**
     * Resize JavaFX Image as WritableImage.
     *
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

    /**
     * Write JavaFX Image as PNG file to output stream.
     *
     * @param image
     * @param outputStream
     * @throws IOException
     */
    public static void writeImage(Image image, OutputStream outputStream) throws IOException {
        AwtImageUtils.writeImageAsPng(SwingFXUtils.fromFXImage(image, null), outputStream);
    }

    /**
     * Write JavaFX Image as PNG file.
     *
     * @param image
     * @param file
     * @throws IOException
     */
    public static void writeImage(Image image, File file) throws IOException {
        AwtImageUtils.writeImageAsPng(SwingFXUtils.fromFXImage(image, null), file);
    }

}
