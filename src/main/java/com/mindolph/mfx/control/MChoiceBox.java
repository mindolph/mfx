package com.mindolph.mfx.control;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

/**
 * Features:
 * Handling the non-select item.</br>
 * Bouncing selection: listen the selection change event, if any conditions that need to cancel the selection, call {@code bounce()}.  eg:
 * <pre>
 * choiceBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
 *   if (FORBIDDEN_ITEM_NAME.equals(newValue)) {
 *     choiceBox.bounce();
 *   }
 * });
 * </pre>
 *
 * @param <T>
 * @since 3.0
 */
public class MChoiceBox<T> extends ChoiceBox<T> {

    private T lastOldValue;
    private boolean isBouncing = false;

    public MChoiceBox() {
        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isBouncing) {
                return;
            }
            lastOldValue = oldValue;
        });
    }

    public MChoiceBox(ObservableList<T> items) {
        super(items);
        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isBouncing) {
                return; // avoid recursive.
            }
            lastOldValue = oldValue;
        });
    }

    public void bounce() {
        isBouncing = true;
        Platform.runLater(() -> {
            setValue(lastOldValue); // bounce to last old value.
            isBouncing = false;
        });
    }

    public boolean isBouncing() {
        return isBouncing;
    }

    /**
     * Whether selected a valid item, excludes the null item.
     *
     * @return
     */
    public boolean hasSelected() {
        return !getSelectionModel().isEmpty() && getSelectionModel().getSelectedItem() != null;
    }

    /**
     * Select the invalid item if exists.
     * NOTE: the JFX doesn't support select the inlaid item programming for now.
     */
    public void unselect() {
        int i = super.getItems().indexOf(null);
        if (i >= 0 && i < getItems().size()) {
            super.getSelectionModel().select(i);
        }
    }

}
