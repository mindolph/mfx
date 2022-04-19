package com.mindolph.mfx.dialog.impl;

import com.mindolph.mfx.dialog.BaseInputDialogBuilder;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author allen
 */
public class ChoiceDialogBuilder<T> extends BaseInputDialogBuilder<T, ChoiceDialogBuilder<T>> {

    List<T> choices = new ArrayList<>();

    public ChoiceDialogBuilder<T> choice(T choice) {
        if (!choices.contains(choice)) {
            choices.add(choice);
        }
        return this;
    }

    @Override
    public ChoiceDialog<T> build() {
        ChoiceDialog<T> dialog = new ChoiceDialog<>(super.defaultValue, choices);
        dialog.initOwner(owner);
        dialog.setTitle(title);
        dialog.setContentText(content);
        dialog.setHeaderText(header);
        return dialog;
    }
}
