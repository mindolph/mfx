package com.mindolph.mfx.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author allen
 */
public class TextUtils {

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

}
