package com.mindolph.mfx.util;

import javafx.geometry.Bounds;
import javafx.scene.text.Font;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mindolph.com@gmail.com
 */
class TextUtilsTest {

    @Test
    void calculateTextBounds() {
        Bounds bound = TextUtils.calculateTextBounds("Hello World", Font.getDefault());
        System.out.println(BoundsUtils.boundsInString(bound));
    }

    @Test
    void deleteLineBreaks() {
        Assertions.assertEquals("xxxyyyzzz", TextUtils.deleteLineBreaks("xxx\nyyy\r\nzzz"));
        Assertions.assertEquals("xxxyyyzzz", TextUtils.deleteLineBreaks("xxx\n\n\nyyyzzz"));
        Assertions.assertEquals("xxxyyyzzz", TextUtils.deleteLineBreaks("xxx\n\n\nyyy\r\n\r\nzzz"));
        Assertions.assertEquals("xxxyyyzzz", TextUtils.deleteLineBreaks("xxx\n\r\n\nyyy\r\n\n\r\nzzz"));
    }


    @Test
    void subStringBlankHead() {
    }

    @Test
    void replaceLineBreaksWithWhitespace() {
        String test = "xxx\nyyy\r\nzzz";
        Assertions.assertEquals("xxx yyy zzz", TextUtils.replaceLineBreaksWithWhitespace(test));
    }

    @Test
    void countInStartingStr() {
        String test = "abccc";
        Assertions.assertEquals(1, TextUtils.countInStarting(test, "ab"));
        test = "abababccc";
        Assertions.assertEquals(3, TextUtils.countInStarting(test, "ab"));
        test = "aaabbb";
        Assertions.assertEquals(3, TextUtils.countInStarting(test, "a"));
        //
        Assertions.assertEquals(0, TextUtils.countInStarting(test, ""));
    }


    @Test
    public void countInStartingChar() {
        Assertions.assertEquals(0, TextUtils.countInStarting("aaabbb", 'x'));
        Assertions.assertEquals(3, TextUtils.countInStarting("aaabbb", 'a'));
        Assertions.assertEquals(3, TextUtils.countInStarting("aaabbbaaa", 'a'));
    }

    @Test
    public void countIndent() {
        // standard(4)
        Assertions.assertEquals(0, TextUtils.countIndent(" str", 4));
        Assertions.assertEquals(1, TextUtils.countIndent("\tstr", 4));
        Assertions.assertEquals(1, TextUtils.countIndent("    str", 4));
        Assertions.assertEquals(2, TextUtils.countIndent("    \tstr", 4));
        Assertions.assertEquals(2, TextUtils.countIndent("        str", 4));
        Assertions.assertEquals(2, TextUtils.countIndent("\t\tstr", 4));
        Assertions.assertEquals(3, TextUtils.countIndent("    \t    str", 4));
        Assertions.assertEquals(3, TextUtils.countIndent("\t    \tstr", 4));
        Assertions.assertEquals(1, TextUtils.countIndent("    str ", 4));
        Assertions.assertEquals(1, TextUtils.countIndent("    str \t", 4));

        //
        Assertions.assertEquals(2, TextUtils.countIndent("    str", 2));
        Assertions.assertEquals(4, TextUtils.countIndent("    str", 1));
        Assertions.assertEquals(6, TextUtils.countIndent("\t    \tstr", 1));
    }
}