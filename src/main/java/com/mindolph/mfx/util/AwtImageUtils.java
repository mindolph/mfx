package com.mindolph.mfx.util;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author allen
 */
public class AwtImageUtils {


    /**
     * Convert to png base64.
     *
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static String imageToBase64(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream imageBuffer = new ByteArrayOutputStream(1024);
        ImageIO.write(bufferedImage, "png", imageBuffer);
        return new String(Base64.encodeBase64(imageBuffer.toByteArray()));
    }
}
