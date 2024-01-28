package com.mindolph.mfx.util;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author mindolph.com@gmail.com
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

    public static BufferedImage base64ToImage(String base64) throws IOException {
        byte[] bytes = Base64.decodeBase64(base64);
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }

    public static void writeImageAsPng(BufferedImage bufferedImage, OutputStream outputStream) throws IOException {
        ImageIO.write(bufferedImage, "png", outputStream);
    }

    public static void writeImageAsPng(BufferedImage bufferedImage, File file) throws IOException {
        ImageIO.write(bufferedImage, "png", file);
    }

}
