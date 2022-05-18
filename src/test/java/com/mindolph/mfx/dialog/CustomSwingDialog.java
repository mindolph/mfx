package com.mindolph.mfx.dialog;

import com.mindolph.mfx.swing.ExtendableSwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;

import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * @author allen
 */
public class CustomSwingDialog extends BaseDialogController<String> implements Initializable {

    @FXML
    private ExtendableSwingNode extendableSwingNode;

    private final JButton jButton;

    public CustomSwingDialog() {
        jButton = new JButton("custom swing component dialog");
        jButton.addActionListener(e -> {
            result = "This is a custom swing dialog";
        });
        dialog = new CustomDialogBuilder<String>()
                .title("Custom Dialog").content("This is a custom dialog with swing content")
                .buttons(ButtonType.OK, ButtonType.CANCEL)
                .swingContent(jButton)
                .controller(CustomSwingDialog.this)
                .build();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void show(Consumer<String> consumer) {
        Optional<String> optResult = dialog.showAndWait();
        consumer.accept(optResult.orElse(null));
    }
}
