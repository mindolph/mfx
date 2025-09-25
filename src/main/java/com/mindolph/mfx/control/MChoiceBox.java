package com.mindolph.mfx.control;

import javafx.scene.control.ChoiceBox;

/**
 * Handling the non-select item.
 *
 * @param <T>
 * @since 3.0
 */
public class MChoiceBox<T> extends ChoiceBox<T> {

    /**
     * Whether selected a valid item, excludes the null item.
     *
     * @return
     */
    public boolean hasSelected() {
        return !getSelectionModel().isEmpty() && getSelectionModel().getSelectedItem() != null;
    }

    public void unselect() {
        getSelectionModel().clearSelection();
    }



}
