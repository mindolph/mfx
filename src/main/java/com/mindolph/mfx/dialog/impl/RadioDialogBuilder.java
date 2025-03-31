package com.mindolph.mfx.dialog.impl;

import com.mindolph.mfx.dialog.BaseDialogController;
import com.mindolph.mfx.dialog.BaseInputDialogBuilder;
import com.mindolph.mfx.dialog.CustomDialogBuilder;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder for dialog with {@link RadioButton}s.
 *
 * @author mindolph.com@gmail.com
 * @since 2.0.1
 */
public class RadioDialogBuilder<T> extends BaseInputDialogBuilder<T, RadioDialogBuilder<T>> {

    private final List<Pair<T, String>> options = new ArrayList<>();

    public RadioDialogBuilder() {
    }

    public RadioDialogBuilder<T> option(T value, String label) {
        options.add(new Pair<>(value, label));
        return this;
    }

    public RadioDialogBuilder<T> options(Pair<T, String>... option) {
        Collections.addAll(options, option);
        return this;
    }

    public RadioDialogBuilder<T> options(List<Pair<T, String>> options) {
        this.options.addAll(options);
        return this;
    }

    @Override
    public Dialog<T> build() {
        VBox vBox = new VBox();
        vBox.setSpacing(8);
        ToggleGroup group = new ToggleGroup();
        for (Pair<T, String> option : options) {
            RadioButton button = new RadioButton(option.getValue());
            button.setToggleGroup(group);
            button.setUserData(option.getKey());
            if (option.getKey().equals(defaultValue)) {
                button.setSelected(true);
            }
            vBox.getChildren().add(button);
        }
        Dialog<T> dialog = new CustomDialogBuilder<T>()
                .owner(owner)
                .title(title).content(content)
                .buttons(ButtonType.OK, ButtonType.CANCEL)
                .fxContent(vBox)
                .controller(new BaseDialogController<>() {
                    @Override
                    public T getResult() {
                        for (Node child : vBox.getChildren()) {
                            if (child instanceof RadioButton cb) {
                                if (cb.isSelected()) {
                                    return (T) cb.getUserData();
                                }
                            }
                        }
                        return null;
                    }
                })
                .build();
        return dialog;
    }


}
