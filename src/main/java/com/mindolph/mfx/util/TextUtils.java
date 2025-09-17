package com.mindolph.mfx.util;

import javafx.geometry.Bounds;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mindolph.com@gmail.com
 */
public class TextUtils {

    /**
     * Count how many indents exists, TAB counts as 1, spaces in minSpaceSize counts as 1 either.
     *
     * @param str
     * @param minSpaceSize
     * @return
     */
    public static int countIndent(String str, int minSpaceSize) {
        Pattern p = Pattern.compile("(?<INDENT>\\s*?)(\\S+[\\s\\S]*)");
        Matcher matcher = p.matcher(str);
        if (matcher.matches()) {
            String indent = matcher.group("INDENT");
            return StringUtils.countMatches(indent, "\t") + StringUtils.countMatches(indent, StringUtils.repeat(' ', minSpaceSize));
        }
        return 0;
    }

    /**
     * Count how many characters in starting of text.
     *
     * @param str
     * @param c
     * @return
     */
    public static int countInStarting(String str, char c) {
        char[] chars = str.toCharArray();
        int count = 0;
        for (char aChar : chars) {
            if (aChar != c) {
                return count;
            }
            count++;
        }
        return count;
    }

    /**
     * Count how many sub strings in starting of text.
     *
     * @param str
     * @param sub
     * @return
     */
    public static int countInStarting(String str, String sub) {
        if (StringUtils.isAnyEmpty(str, sub)) {
            return 0;
        }
        char[] chars = str.toCharArray();
        char[] subChars = sub.toCharArray();
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            char c = subChars[i % subChars.length];
            if (aChar != c) {
                return count;
            }
            if ((i % subChars.length + 1) == subChars.length) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculate bounds of text in specified font.
     *
     * @param s
     * @param font
     * @return
     * @since 1.3.1
     */
    public static Bounds calculateTextBounds(String s, Font font) {
        Text text = new Text(s);
        text.setFont(font);
        return text.getLayoutBounds();
    }

    /**
     * Get the blank head of text including white space and tab.
     *
     * @param text
     * @return
     */
    public static String subStringBlankHead(String text) {
        Pattern pattern = Pattern.compile("^\\s+");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return StringUtils.EMPTY;
    }

    /**
     * Delete all line breaks (Win, *nix like) in text.
     *
     * @param text
     * @return
     */
    public static String deleteLineBreaks(String text) {
        return StringUtils.replaceEach(text, new String[]{"\r\n", "\n"}, new String[]{StringUtils.EMPTY, StringUtils.EMPTY});
    }

    /**
     * Replace all line breaks (Win, *nix like) in text with one white space.
     *
     * @param text
     * @return
     */
    public static String replaceLineBreaksWithWhitespace(String text) {
        return StringUtils.replaceEach(text, new String[]{"\r\n", "\n"}, new String[]{" ", " "});
    }


    /**
     * @param text
     * @return
     */
    public static String convertToWindows(String text) {
        if (Strings.CS.contains(text, "\r\n")) {
            return text;
        }
        return RegExUtils.replaceAll(text, "\n", "\r\n");
    }

    /**
     * @param text
     * @return
     */
    public static String convertFromWindows(String text) {
        if (!Strings.CS.contains(text, "\r\n")) {
            return text;
        }
        return RegExUtils.replaceAll(text, "\r\n", "\n");
    }
}
