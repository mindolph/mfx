package com.mindolph.mfx.util;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.apache.commons.lang3.StringUtils;

/**
 * @author mindolph.com@gmail.com
 */
public class FontUtils {

    public static Font awtFontToFxFont(java.awt.Font font) {
        FontPosture posture = FontPosture.REGULAR;
        FontWeight weight = FontWeight.NORMAL;
        if (font.isItalic()) {
            posture = FontPosture.ITALIC;
        }
        if ((font.getStyle() & java.awt.Font.BOLD) != 0) {
            weight = FontWeight.BOLD;
        }
        return Font.font(font.getFamily(), weight, posture, font.getSize());
    }

    public static java.awt.Font fxFontToAwtFont(Font font) {
        int bold = java.awt.Font.PLAIN;
        int italic = java.awt.Font.PLAIN;
        FontWeight fontWeight = FontWeight.findByName(font.getStyle());
        FontPosture fontPosture = FontPosture.findByName(font.getStyle());
        if (fontWeight.equals(FontWeight.BOLD)) {
            bold = java.awt.Font.BOLD;
        }
        if (fontPosture == FontPosture.ITALIC) {
            italic = java.awt.Font.ITALIC;
        }
        return new java.awt.Font(font.getFamily(), bold | italic, (int) font.getSize());
    }

    public static String fontToCssStyle(Font font) {
        String style = """
                -fx-font-family: %s;
                -fx-font-weight: %s;
                -fx-font-posture: %s;
                -fx-font-size: %f
                """;
        return style.formatted(font.getFamily(), fontWeight(font), fontPosture(font.getStyle()), font.getSize());
    }

    /**
     * Get the weight of the font.
     *
     * @param font
     * @return
     */
    public static FontWeight fontWeight(Font font) {
        return fontWeight(font.getStyle());
    }

    /**
     * Get the weight of the font style.
     *
     * @param style
     * @return
     */
    public static FontWeight fontWeight(String style) {
        return StringUtils.containsIgnoreCase(style, FontWeight.BOLD.name()) ? FontWeight.BOLD : FontWeight.NORMAL;
    }

    /**
     * Get the posture of the font.
     *
     * @param font
     * @return
     */
    public static FontPosture fontPosture(Font font) {
        return fontPosture(font.getStyle());
    }

    /**
     * Get the posture of the font style.
     *
     * @param style
     * @return
     */
    public static FontPosture fontPosture(String style) {
        return StringUtils.containsIgnoreCase(style, FontPosture.ITALIC.name()) ? FontPosture.ITALIC : FontPosture.REGULAR;
    }

    public static boolean isItalic(Font font) {
        return FontPosture.ITALIC == fontPosture(font);
    }

    public static boolean isBold(Font font) {
        return FontWeight.BOLD == fontWeight(font);
    }

    public static Font newFontWithWeight(Font font, FontWeight fontWeight) {
        return Font.font(font.getFamily(), fontWeight, fontPosture(font), font.getSize());
    }

    public static Font newFontWithPosture(Font font, FontPosture fontPosture) {
        return Font.font(font.getFamily(), fontWeight(font), fontPosture, font.getSize());
    }

    public static Font newFontWithWeightAndPosture(Font font, FontWeight fontWeight, FontPosture fontPosture) {
        return Font.font(font.getFamily(), fontWeight, fontPosture, font.getSize());
    }

    public static Font newFontWithSize(Font font, double fontSize) {
        return Font.font(font.getFamily(), fontWeight(font), fontPosture(font), fontSize);
    }

    public static String fontToString(Font font) {
        final String strStyle;
        if (FontUtils.isBold(font)) {
            strStyle = FontUtils.isItalic(font) ? "bolditalic" : "bold";
        }
        else {
            strStyle = FontUtils.isItalic(font) ? "italic" : "regular";
        }
        return String.format("%s, %s, %d", font.getFamily(), strStyle, Double.valueOf(font.getSize()).intValue());
    }

}
