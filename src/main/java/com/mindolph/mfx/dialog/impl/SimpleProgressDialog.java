package com.mindolph.mfx.dialog.impl;

import com.mindolph.mfx.dialog.BaseDialogController;
import com.mindolph.mfx.dialog.CustomDialogBuilder;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Intermediate progress bar dialog.
 *
 */
public class SimpleProgressDialog extends BaseDialogController<String> {

    private final Label lblMessage;
    private final ProgressBar progressBar;
    private final VBox vbox;

    public SimpleProgressDialog(Window owner, String title, String message) {
        this.vbox = new VBox();
        this.vbox.setSpacing(8f);
        lblMessage = new Label(message);
        progressBar = new ProgressBar();
        progressBar.setMinWidth(320);
        progressBar.setMinHeight(20);
        this.vbox.getChildren().add(lblMessage);
        this.vbox.getChildren().add(progressBar);
        dialog = new CustomDialogBuilder<String>()
                .owner(owner)
                .title(title)
                .width(640)
                .height(20)
                .fxContent(this.vbox)
                .controller(this)
                .buttons(ButtonType.CANCEL)
                .build();
        dialog.initModality(Modality.NONE);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
    }

}
