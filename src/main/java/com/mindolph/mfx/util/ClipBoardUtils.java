package com.mindolph.mfx.util;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import java.io.File;
import java.util.List;

/**
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
     *
     * @return
     */
    public static String textFromClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        return clipboard.getString();
    }

    /**
     * Push files to system clipboard.
     *
     * @param files
     */
    public static void filesToClipboard(List<File> files) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putFiles(files);
        clipboard.setContent(clipboardContent);
    }

    /**
     * Retrieve files from system clipboard.
     * 
     * @return
     */
    public static List<File> filesFromClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        return clipboard.getFiles();
    }
}
