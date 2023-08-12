package com.mindolph.mfx.util;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author allen
 */
public class AwtImageUtils {

    /**
     * Convert to png bytes.
     * Note: jpg will cause the image being compressed.
     *
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static byte[] imageToBytes(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream imageBuffer = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "png", imageBuffer);
        writeImageAsPng(bufferedImage, imageBuffer);
        return imageBuffer.toByteArray();
    }

    /**
     * Convert to png base64.
     * Note: jpg will cause the image being compressed.
     *
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static String imageToBase64(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream imageBuffer = new ByteArrayOutputStream();
        writeImageAsPng(bufferedImage, imageBuffer);
        return new String(Base64.encodeBase64(imageBuffer.toByteArray()));
    }

    public static void writeImageAsPng(BufferedImage bufferedImage, OutputStream outputStream) throws IOException {
        ImageIO.write(bufferedImage, "png", outputStream);
    }

    public static void writeImageAsPng(BufferedImage bufferedImage, File file) throws IOException {
        ImageIO.write(bufferedImage, "png", file);
    }

}
