package com.mindolph.mfx.util;

import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @since 2.0.1
 */
public class ControlUtils {

    /**
     * Make the Control (or it's descendant) instance escapable.
     *
     * @param action
     * @param control
     */
    public static void escapableControl(Runnable action, Control control) {
        control.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                action.run();
            }
        });
    }

    /**
     * Make the Control (or it's descendant) instances escapable.
     *
     * @param action
     * @param controls
     */
    public static void escapableControls(Runnable action, Control... controls) {
        for (Control control : controls) {
            control.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    action.run();
                }
            });
        }
    }
}
