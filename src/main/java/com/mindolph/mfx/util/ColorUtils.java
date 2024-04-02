package com.mindolph.mfx.util;

import javafx.scene.paint.Color;

/**
 * @author mindolph.com@gmail.com
 */
public class ColorUtils {

    public static Color awtColorToFxColor(java.awt.Color awtColor) {
        if (awtColor == null) return null;
        int r = awtColor.getRed();
        int g = awtColor.getGreen();
        int b = awtColor.getBlue();
        int a = awtColor.getAlpha();
        double opacity = a / 255.0;
        return Color.rgb(r, g, b, opacity);
    }

    public static java.awt.Color fxColorToAwtColor(Color fxColor) {
        if (fxColor == null) return null;
        int r = (int) (fxColor.getRed() * 255);
        int g = (int) (fxColor.getGreen() * 255);
        int b = (int) (fxColor.getBlue() * 255);
        int a = (int) (fxColor.getOpacity() * 255);
        return new java.awt.Color(r, g, b, a);
    }

    /**
     * @param fxColor
     * @param opacity 0-1
     * @return
     */
    public static Color colorWithOpacity(Color fxColor, double opacity) {
        if (fxColor == null) return null;
        return new Color(fxColor.getRed(), fxColor.getGreen(), fxColor.getBlue(), opacity);
//        int r = (int) (fxColor.getRed() * 255);
//        int g = (int) (fxColor.getGreen() * 255);
//        int b = (int) (fxColor.getBlue() * 255);
//        return Color.rgb(r, g, b, opacity);
    }

    public static Color colorFromRgba(int value) {
        int r = (value >>> 24) & 0xFF;
        int g = (value >>> 16) & 0xFF;
        int b = (value >> 8) & 0xFF;
        int a = value & 0xFF;
        return Color.rgb(r, g, b, a / 255f);
    }

    public static int colorRgba(Color c) {
        int r = (int) Math.round(c.getRed() * 255);
        int g = (int) Math.round(c.getGreen() * 255);
        int b = (int) Math.round(c.getBlue() * 255);
        int a = (int) Math.round(c.getOpacity() * 255);
        return (r << 24) | (g << 16) | (b << 8) | a;
    }
}
