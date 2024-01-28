package com.mindolph.mfx.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

import static javax.imageio.ImageWriteParam.MODE_COPY_FROM_METADATA;

/**
 * @author mindolph.com@gmail.com
 */
public class ConvertImageTest {



    @Test
    public void testConvertNoQualityLost() throws IOException {
        try (InputStream resourceAsStream = FxImageUtils.class.getResourceAsStream("/utils/test_image_convert.png")) {
            if (resourceAsStream == null) {
                throw new IOException("Resource not found: ");
            }
            File outFile = new File(SystemUtils.getUserHome(), "img_converted.jpg");
            System.out.println(outFile);
            FileOutputStream bous = new FileOutputStream(outFile);
            this.writeImageNoCompression(resourceAsStream, bous, "png");
        }
    }

    private void writeImageNoCompression(InputStream inputStream, OutputStream outputStream, String outType) throws IOException {
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);

        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(outType);
        ImageReader reader = readers.next();
        reader.setInput(imageInputStream);
        IIOImage iioImage = reader.readAll(0, null);
        ImageWriter writer = ImageIO.getImageWriter(reader);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(MODE_COPY_FROM_METADATA);

        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        writer.setOutput(imageOutputStream);
        writer.write(null, iioImage, param);
    }

    @Test
    public void testConvertQualityLoss() throws IOException {
        try (InputStream resourceAsStream = FxImageUtils.class.getResourceAsStream("/utils/test_image_convert.png")) {
            if (resourceAsStream == null) {
                throw new IOException("Resource not found: ");
            }
            BufferedImage bufferedImage = ImageIO.read(resourceAsStream);
            File outFile = new File(SystemUtils.getUserHome(), "img_converted2.jpg");
            ImageIO.write(bufferedImage,"jpg", outFile);
        }
    }

    @Test
    public void testConverterNoQualityLoss() throws IOException {
        try (InputStream resourceAsStream = FxImageUtils.class.getResourceAsStream("/utils/test_image_convert.png")) {
            if (resourceAsStream == null) {
                throw new IOException("Resource not found: ");
            }
            Image image = new Image(resourceAsStream);
            BufferedImage bufferedImage = new ImageConverter().image(SwingFXUtils.fromFXImage(image, null)).maxSize(720d).convert();
            File outFile = new File(SystemUtils.getUserHome(), "img_converted3.jpg");
            ImageIO.write(bufferedImage,"png", outFile);
        }
    }
}
