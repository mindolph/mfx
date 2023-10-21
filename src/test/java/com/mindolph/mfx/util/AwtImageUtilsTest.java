package com.mindolph.mfx.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author allen
 */
public class AwtImageUtilsTest {

    @Test
    public void testBase64ToImage() throws IOException {
        BufferedImage bufferedImage = AwtImageUtils.base64ToImage(BASE64_IMAGE);
        Assertions.assertNotNull(bufferedImage);
    }

    @Test
    public void testScaledImage() throws IOException {
        try (InputStream resourceAsStream = FxImageUtils.class.getResourceAsStream("/utils/test_image_convert.png")) {
            if (resourceAsStream == null) {
                throw new IOException("Resource not found: ");
            }
            BufferedImage originalImage = ImageIO.read(resourceAsStream);
            int newWidth = (int) (originalImage.getWidth() / 1.5);
            int newHeight = (int) (originalImage.getHeight() / 1.5);
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

            File outFile = outputFile("test_image_scaled_to_png.png");
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            ImageIO.write(bufferedImage, "png", fileOutputStream);
            System.out.println(outFile);
        }
    }

    @Test
    public void testImageToJpegBytes() throws IOException {
        try (InputStream resourceAsStream = FxImageUtils.class.getResourceAsStream("/utils/test_image_convert.png")) {
            if (resourceAsStream == null) {
                throw new IOException("Resource not found: ");
            }
            BufferedImage bufferedImage = ImageIO.read(resourceAsStream);

            File outFile = outputFile("test_image_to_bytes.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            ImageIO.write(bufferedImage, "jpg", fileOutputStream);
            System.out.println(outFile);
        }
    }

    @Test
    public void testImageToPngBytes() throws IOException {
        try (InputStream resourceAsStream = FxImageUtils.class.getResourceAsStream("/utils/test_image_convert.png")) {
            if (resourceAsStream == null) {
                throw new IOException("Resource not found: ");
            }
            BufferedImage bufferedImage = ImageIO.read(resourceAsStream);
            byte[] bytes = AwtImageUtils.imageToBytes(bufferedImage);
            File outFile = outputFile("test_image_to_png_bytes.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            System.out.println(outFile);
        }
    }

    @Test
    public void testImageToPngBase64() throws IOException {
        try (InputStream resourceAsStream = FxImageUtils.class.getResourceAsStream("/utils/test_image_convert.png")) {
            if (resourceAsStream == null) {
                throw new IOException("Resource not found: ");
            }
            BufferedImage bufferedImage = ImageIO.read(resourceAsStream);
            String base64 = AwtImageUtils.imageToBase64(bufferedImage);
            String html = TEMPLATE.replace("${base64_data}", base64);
            File outFile = outputFile("test_image_to_png_base64.html");
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            fileOutputStream.write(html.getBytes());
            fileOutputStream.close();
            System.out.println(outFile);
        }
    }

    @Test
    public void testImageFileToBase64Directly() throws IOException {
        try (InputStream resourceAsStream = FxImageUtils.class.getResourceAsStream("/utils/test_image_convert.png")) {
            if (resourceAsStream == null) {
                throw new IOException("Resource not found: ");
            }
            byte[] bytes = Base64.encodeBase64(resourceAsStream.readAllBytes());
            String base64 = new String(bytes);
            String html = TEMPLATE.replace("${base64_data}", base64);
            File outFile = outputFile("test_image_file_to_base64.html");
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            fileOutputStream.write(html.getBytes());
            fileOutputStream.close();
            System.out.println(outFile);
        }
    }


    private File outputFile(String fileName) {
        return new File(SystemUtils.getJavaIoTmpDir(), fileName);
    }


    String TEMPLATE = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Title</title>
            </head>
            <body>
            <img src="data:image/png;base64,${base64_data}"/>
            </body>
            </html>
            """;

    String BASE64_IMAGE = "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";
}
