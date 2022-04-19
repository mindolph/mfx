package com.mindolph.mfx.util;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author allen
 */
public class KeyEventUtils {

    /**
     * Whether any modifier key is down.
     *
     * @param keyEvent
     * @return
     */
    public static boolean isModifierKeyDown(KeyEvent keyEvent) {
        return keyEvent.isAltDown() || keyEvent.isShiftDown() || keyEvent.isMetaDown() || keyEvent.isControlDown();
    }

    /**
     * Whether direction keys is down.
     *
     * @param keyEvent
     * @return
     */
    public static boolean isDirectionKeyDown(KeyEvent keyEvent) {
        return keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.RIGHT
                || keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN;
    }

}
