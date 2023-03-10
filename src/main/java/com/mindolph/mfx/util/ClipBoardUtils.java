package com.mindolph.mfx.util;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * 
 * @author allen
 */
public class ClipBoardUtils {

    /**
     * Push text to system clipboard.
     *
     * @param text
     */
    public static void textToClipboard(String text) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        try {
            clipboardContent.putString(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clipboard.setContent(clipboardContent);
    }

    /**
     * Retrieve text from system clipboard.
     * @return
     */
    public static String textFromClipboard(){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        return clipboard.getString();
    }
}
