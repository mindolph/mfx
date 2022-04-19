package com.mindolph.mfx.dialog.impl;

import com.mindolph.mfx.dialog.BaseDialogController;
import com.mindolph.mfx.dialog.CustomDialogBuilder;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * Dialog shows text block.
 *
 * @author allen
 */
public class TextBlockDialog extends BaseDialogController<String> {

    private final String title;
    private final String text;
    private final TextArea textArea;

    public TextBlockDialog(Window owner, String title, String text) {
        this.title = title;
        this.text = text;
        textArea = new TextArea();
        dialog = new CustomDialogBuilder<String>()
                .owner(owner)
                .fxContent(textArea)
                .build();
        dialog.initModality(Modality.NONE);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> {
            window.hide();
        });
    }

    @Override
    public void show(Callback<String, Void> callback) {
        textArea.setText(text);
        dialog.setTitle(title);
        dialog.show();
        if (callback != null) callback.call(null);
    }
}
