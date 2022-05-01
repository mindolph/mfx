package com.mindolph.mfx.dialog.impl;

import com.mindolph.mfx.dialog.BaseDialogController;
import com.mindolph.mfx.dialog.BaseInputDialogBuilder;
import com.mindolph.mfx.dialog.CustomDialogBuilder;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder for dialog with {@link CheckBox}es.
 *
 * @author allen
 * @see CheckBox
 */
public class OptionsDialogBuilder extends BaseInputDialogBuilder<List<Boolean>, OptionsDialogBuilder> {

    /**
     * Options for user to choose.
     */
    private final List<String> options = new ArrayList<>();

    public OptionsDialogBuilder() {
    }

    public OptionsDialogBuilder option(String text) {
        options.add(text);
        return this;
    }

    public OptionsDialogBuilder options(String... options) {
        Collections.addAll(this.options, options);
        return this;
    }

    public OptionsDialogBuilder options(List<String> options) {
        this.options.addAll(options);
        return this;
    }

    @Override
    public Dialog<List<Boolean>> build() {
        VBox vBox = new VBox();
        vBox.setSpacing(8);
        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            CheckBox checkBox = new CheckBox(option);
            if (defaultValue != null) {
                checkBox.setSelected(defaultValue.get(i));
            }
            vBox.getChildren().add(checkBox);
        }
        Dialog<List<Boolean>> dialog = new CustomDialogBuilder<List<Boolean>>()
                .owner(owner)
                .title(title).content(content)
                .buttons(ButtonType.OK, ButtonType.CANCEL)
                .fxContent(vBox)
                .controller(new BaseDialogController<List<Boolean>>() {
                    @Override
                    public List<Boolean> getResult() {
                        List<Boolean> ret = new ArrayList<>(options.size());
                        for (Node child : vBox.getChildren()) {
                            ret.add(((CheckBox) child).isSelected());
                        }
                        return ret;
                    }
                })
                .build();
        return dialog;
    }

}
