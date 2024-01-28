package com.mindolph.mfx.dialog;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builder for Alert dialog.
 *
 * @author mindolph.com@gmail.com
 */
public class AlertBuilder extends BaseDialogBuilder<AlertBuilder> {

    private static final Logger log = LoggerFactory.getLogger(AlertBuilder.class);

    private Alert.AlertType type;
    private ButtonType[] buttonTypes;
    private Node expand;

    /**
     * @see javafx.scene.control.Alert.AlertType
     * @param type
     * @return
     */
    public AlertBuilder type(Alert.AlertType type) {
        this.type = type;
        return this;
    }

    /**
     * @see ButtonType
     * @param buttonTypes
     * @return
     */
    public AlertBuilder buttons(ButtonType... buttonTypes) {
        this.buttonTypes = buttonTypes;
        return this;
    }

    /**
     * Expand alert with customized node.
     *
     * @param expand
     * @return
     */
    public AlertBuilder expand(Node expand) {
        this.expand = expand;
        return this;
    }

    public Alert build() {
        Alert alert = new Alert(type, content, buttonTypes);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.getDialogPane().setExpandableContent(expand);
        alert.initOwner(owner);
        for (ButtonType buttonType : alert.getDialogPane().getButtonTypes()) {
            Button btn = (Button) alert.getDialogPane().lookupButton(buttonType);
            if (defaultButton != null) {
                btn.setDefaultButton(defaultButton == buttonType);
            }
        }
        alert.getDialogPane().setOnKeyPressed(keyEvent -> {
            log.debug("Alert key pressed: " + keyEvent.getText());
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                keyEvent.consume();
                alert.close();
            }
        });
        alert.setOnCloseRequest(dialogEvent -> {
            log.debug("Alert on close request");
        });
        return alert;
    }

}
