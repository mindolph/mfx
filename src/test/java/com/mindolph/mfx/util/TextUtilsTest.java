package com.mindolph.mfx.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mindolph.com@gmail.com
 */
class TextUtilsTest {

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
}