package com.mindolph.mfx.dialog;

import com.mindolph.mfx.swing.ExtendableSwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author allen
 */
public class CustomWithSwingDialog extends BaseDialogController<String> implements Initializable {

    @FXML
    private ExtendableSwingNode extendableSwingNode;

    private JButton jButton = new JButton("This is a custom dialog with swing content");

    public CustomWithSwingDialog() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println();
        jButton.addActionListener(e -> {
            result = "This is a custom dialog with swing content";
        });
        extendableSwingNode.setContent(jButton);
    }


}
