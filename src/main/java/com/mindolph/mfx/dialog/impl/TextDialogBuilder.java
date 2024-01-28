package com.mindolph.mfx.dialog.impl;

import com.mindolph.mfx.dialog.BaseInputDialogBuilder;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;

/**
 * Builder for dialog with one line text.
 *
 * @author mindolph.com@gmail.com
 */
public class TextDialogBuilder extends BaseInputDialogBuilder<String, TextDialogBuilder> {

    private String text;

    public TextDialogBuilder text(String text) {
        this.text = text;
        return this;
    }

    @Override
    public Dialog<String> build() {
        TextInputDialog dialog = new TextInputDialog(super.defaultValue);
        dialog.initOwner(owner);
        dialog.setTitle(title);
        dialog.setContentText(content);
        dialog.setHeaderText(header);
        dialog.getEditor().setText(text);
        dialog.setWidth(width);
        dialog.setHeight(height);
        dialog.getDialogPane().setPrefWidth(width);
        dialog.getDialogPane().setPrefHeight(height);
        return dialog;
    }
}
