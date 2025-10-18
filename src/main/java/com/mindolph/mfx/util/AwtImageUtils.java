package com.mindolph.mfx.util;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
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

    /**
     * Add comment to image and save to output file.
     *
     * @param image image to add comment and save
     * @param formatName 'jpeg' or 'png'
     * @param output output file that image save to
     * @param comment comment to be added to saved image file
     */
    public static void addCommentToImageAndSave(BufferedImage image, String formatName, File output, String comment) {
        ImageWriter writer = ImageIO.getImageWritersByFormatName(formatName).next();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            writer.setOutput(ios);

            ImageWriteParam params = writer.getDefaultWriteParam();
            IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), params);

            IIOMetadataNode textEntry = new IIOMetadataNode("TextEntry");
            textEntry.setAttribute("keyword", "Description");
            textEntry.setAttribute("value", comment);
            textEntry.setAttribute("encoding", "UTF-8");
            textEntry.setAttribute("language", "en");
            textEntry.setAttribute("compression", "none");

            IIOMetadataNode text = new IIOMetadataNode("Text");
            text.appendChild(textEntry);

            IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
            root.appendChild(text);

            metadata.mergeTree("javax_imageio_1.0", root);

            writer.write(null, new IIOImage(image, null, metadata), params);
        } catch (Exception e) {
            throw new RuntimeException("Add comment to image fail.", e);
        } finally {
            writer.dispose();
        }
    }

}
