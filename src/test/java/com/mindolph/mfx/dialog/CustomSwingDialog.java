package com.mindolph.mfx.dialog;

import com.mindolph.mfx.swing.ExtendableSwingNode;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;

import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * @author mindolph.com@gmail.com
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
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(jButton);
        dialog = new CustomDialogBuilder<String>()
                .title("Custom Dialog").content("This is a custom dialog with swing content")
                .width(400).height(150)
                .buttons(ButtonType.OK, ButtonType.CANCEL)
                .fxContent(swingNode)
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
