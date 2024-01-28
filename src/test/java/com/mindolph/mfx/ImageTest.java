package com.mindolph.mfx;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.Test;
import org.swiftboot.util.ClasspathResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.InputStream;

/**
 * @author mindolph.com@gmail.com
 */
public class ImageTest {

    @Test
    public void testScaleImageFromInputStream() {
        try {
            InputStream inputStream = ClasspathResourceUtils.openResourceStream("assets/icon.png");
            Image scaledImage = new Image(inputStream, 100, 100, false, true);
            System.out.println(scaledImage.getWidth());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testScaleImageFromImage() {
        try {
            int width = 100;
            int height = 100;
            InputStream is = ClasspathResourceUtils.openResourceStream("assets/icon.png");
            BufferedImage read = ImageIO.read(is);
            int[] pixels = new int[width * height];
            PixelGrabber grabber = new PixelGrabber(read.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH),
                    0, 0, width, height, pixels, 0, width);
            grabber.grabPixels();
            WritableImage fxImage = new WritableImage(width, height);
            PixelWriter pw = fxImage.getPixelWriter();
            pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
            System.out.println(fxImage.getWidth());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
