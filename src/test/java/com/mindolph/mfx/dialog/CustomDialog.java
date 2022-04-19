package com.mindolph.mfx.dialog;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;

import java.util.Optional;

/**
 * @author allen
 */
public class CustomDialog extends BaseDialogController<String> {

    public CustomDialog(String origin) {
        super(origin);
        dialog = new CustomDialogBuilder<String>()
                .title("Custom Dialog").content("自定义对话框（封装模式）")
                .buttons(ButtonType.OK, ButtonType.CANCEL)
                .controller(CustomDialog.this)
                .defaultValue(origin)
                .fxmlUri("dialog/custom_dialog.fxml").build();
    }

    @FXML
    public void onButton(Event event) {
        result = "Button clicked";
    }

    @Override
    public void show(Callback<String, Void> callback) {
        Optional<String> s = dialog.showAndWait();
        s.ifPresent(callback::call);
    }
}
