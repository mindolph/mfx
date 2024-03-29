package com.mindolph.mfx.util;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Scale image in different ways from {@link BufferedImage } or {@link File}.
 *
 * @author mindolph.com@gmail.com
 */
public class ImageConverter {

    private BufferedImage image;

    private File file;

    private InputStream input;

    private Double scale;

    // if both width and height are smaller than maxSize, than the original image returns.
    private Double maxSize;

    private Double width;

    private Double height;

    /**
     * Convert image from {@link BufferedImage} or {@link File} to a new {@link BufferedImage}
     *
     * @return
     * @throws IOException
     */
    public BufferedImage convert() throws IOException {
        BufferedImage image;
        if (this.image != null) {
            image = this.image;
        }
        else if (file != null && file.exists()) {
            image = ImageIO.read(file);
        }
        else if (input != null) {
            image = ImageIO.read(input);
        }
        else {
            throw new RuntimeException("No image source specified");
        }
        int scaledWidth = 0;
        int scaledHeight = 0;
        BufferedImage scaledImage;

        if (scale != null) {
            scaledWidth = (int) (image.getWidth() * scale);
            scaledHeight = (int) (image.getHeight() * scale);
            scaledImage = scaleImage(image, scaledWidth, scaledHeight);
        }
        else if (maxSize != null) {
            if (image.getWidth() < maxSize && image.getHeight() < maxSize) {
                scaledImage = image;
            }
            else {
                double scale = Math.min(maxSize / image.getWidth(), maxSize / image.getHeight());
                scaledWidth = (int) (image.getWidth() * scale);
                scaledHeight = (int) (image.getHeight() * scale);
                scaledImage = scaleImage(image, scaledWidth, scaledHeight);
            }
        }
        else if (width != null && height != null) {
            scaledWidth = width.intValue();
            scaledHeight = height.intValue();
            scaledImage = scaleImage(image, scaledWidth, scaledHeight);
        }
        else {
            throw new RuntimeException("No conversion specified");
        }
        return scaledImage;
    }

    private BufferedImage scaleImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        graphics.dispose();
        return bufferedImage;
    }

    /**
     * Input image
     *
     * @param awtImage
     * @return
     */
    public ImageConverter image(BufferedImage awtImage) {
        this.image = awtImage;
        return this;
    }

    /**
     * Read image from file.
     *
     * @param file
     * @return
     */
    public ImageConverter file(File file) {
        this.file = file;
        return this;
    }

    /**
     * Read image from input stream.
     *
     * @param input
     * @return
     */
    public ImageConverter input(InputStream input) {
        this.input = input;
        return this;
    }

    /**
     * Scale image
     *
     * @param scale
     * @return
     */
    public ImageConverter scale(Double scale) {
        this.scale = scale;
        return this;
    }

    /**
     * Limit the image size on either width or height.
     *
     * @param maxSize
     * @return
     */
    public ImageConverter maxSize(Double maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    /**
     * new width of image.
     *
     * @param width
     * @return
     */
    public ImageConverter width(Double width) {
        this.width = width;
        return this;
    }

    /**
     * new height of image.
     *
     * @param height
     * @return
     */
    public ImageConverter height(Double height) {
        this.height = height;
        return this;
    }
}
