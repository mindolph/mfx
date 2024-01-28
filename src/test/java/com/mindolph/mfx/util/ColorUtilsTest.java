package com.mindolph.mfx.util;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static com.mindolph.mfx.util.ColorUtils.*;

/**
 * @author mindolph.com@gmail.com
 */
class ColorUtilsTest {

    @Test
    public void test() {
        Color color = colorFromRgba(0x0102037F);
        int rgba = colorRgba(color);
        System.out.println(color);
        System.out.println(Integer.toHexString(rgba));
        System.out.println(fxColorToAwtColor(Color.rgb(0, 0, 0)));
        System.out.println(fxColorToAwtColor(Color.rgb(255, 255, 255)));
    }
}