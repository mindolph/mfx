package com.mindolph.mfx.util;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * @since 2.0.1
 */
public class PaneUtils {

    /**
     * Make the Pane (or it's descendant) instance escapable.
     *
     * @param action after Escape key pressed.
     * @param pane
     */
    public static void escapablePane(Runnable action, Pane pane) {
        focusablePane(pane);
        pane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                action.run();
            }
        });
    }

    /**
     * Make the Pane (or it's descendant) instances escapable.
     *
     * @param action after Escape key pressed.
     * @param panes
     */
    public static void escapablePanes(Runnable action, Pane... panes) {
        focusablePanes(panes);
        for (Pane pane : panes) {
            escapablePane(action, pane);
        }
    }

    /**
     * Make the Pane (or it's descendant) instance focusable.
     * NOTE: setOnMouseClicked() on this pane instance will break the focusable.
     *
     * @param pane
     * @return
     */
    public static Pane focusablePane(Pane pane) {
        pane.setOnMouseClicked(event -> {
            if (event.getSource() instanceof Pane n) {
                n.requestFocus();
            }
        });
        pane.setFocusTraversable(true);
        return pane;
    }

    /**
     * Make the Pane (or it's descendant) instances focusable.
     *
     * @param panes
     */
    public static void focusablePanes(Pane... panes) {
        for (Pane pane : panes) {
            focusablePane(pane);
        }
    }
}
