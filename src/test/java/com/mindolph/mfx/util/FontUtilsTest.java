package com.mindolph.mfx.util;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.junit.jupiter.api.Test;

import static com.mindolph.mfx.util.FontUtils.fontPosture;
import static com.mindolph.mfx.util.FontUtils.fontWeight;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mindolph.com@gmail.com
 */
class FontUtilsTest {

    @Test
    void test() {
        Font font = Font.font("Serif", FontWeight.BOLD, FontPosture.ITALIC, 88);
        System.out.println("Font: " + font);
        System.out.println(font.getName());
        System.out.println(font.getFamily());
        System.out.println(font.getStyle());
        System.out.println(fontPosture(font));
        System.out.println(fontWeight(font));
    }
}