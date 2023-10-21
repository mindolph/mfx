package com.mindolph.mfx.dialog;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * test closing a dialog of a parent dialog.
 *
 * @author allen
 */
public class ClosingDialog extends BaseDialogController<String> {

    private static final Logger log = LoggerFactory.getLogger(ClosingDialog.class);

    public ClosingDialog() {
        dialog = new CustomDialogBuilder<String>()
                .title("Closing Dialog").content("Close dialog with another dialog")
                .buttons(ButtonType.OK, ButtonType.CANCEL)
                .controller(ClosingDialog.this)
                .fxmlUri("dialog/closing_dialog.fxml").build();
        dialog.getDialogPane().setOnKeyPressed(keyEvent -> {
            log.debug("Key pressed pressed: " + keyEvent.getCode());
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                keyEvent.consume();
                dialog.close();
            }
        });
        dialog.getDialogPane().setOnKeyReleased(keyEvent -> {
            log.debug("Key released: " + keyEvent.getCode());
        });
        dialog.setOnCloseRequest(dialogEvent -> {
            log.debug("Closing dialog on close request");
        });
    }

    @FXML
    public void onButton(Event event) {
        //DialogFactory.infoDialog("close me with ESC");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "close me with ESC");
        alert.initOwner(dialog.getDialogPane().getScene().getWindow());
        alert.showAndWait();
    }
}
