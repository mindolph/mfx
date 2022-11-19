package com.mindolph.mfx.dialog.impl;

import com.mindolph.mfx.dialog.BaseDialogController;
import com.mindolph.mfx.dialog.CustomDialogBuilder;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Dialog with message and a text block which can be hide or show.
 *
 */
public class MessageTextBlockDialog extends BaseDialogController<Void> {
    private final VBox vbox;
    private final Label labelMsg;
    private final Button btnContent;
    private final TextArea textArea;

    public MessageTextBlockDialog(Window owner, String title, String message, String content, boolean expanded) {
        super(null);
        this.vbox = new VBox();
        this.vbox.setSpacing(8f);

        this.textArea = new TextArea(content);
        this.textArea.setMinHeight(100);
        this.textArea.setPrefWidth(120);
        this.textArea.setEditable(false);
        VBox.setVgrow(this.textArea, Priority.ALWAYS);

        this.labelMsg = new Label(message);
        this.labelMsg.setWrapText(true);
        this.labelMsg.setPadding(new Insets(8));

        this.btnContent = new Button();
        this.btnContent.setOnAction(actionEvent -> {
            showHideTextArea(!textArea.isVisible());
        });

        this.vbox.getChildren().add(labelMsg);
        this.vbox.getChildren().add(btnContent);
        showHideTextArea(expanded);

        CustomDialogBuilder<Void> dialogBuilder = new CustomDialogBuilder<Void>()
                .owner(owner)
                .title(title)
                .resizable(false) // resizable dialog makes hide/show text block breaks
                .width(640)
                .fxContent(vbox)
                .controller(this)
                .buttons(ButtonType.CLOSE);

        dialog = dialogBuilder.build();
        dialog.initModality(Modality.NONE);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> {
            window.hide();
        });

        Platform.runLater(textArea::requestFocus);
    }

    private void showHideTextArea(boolean show) {
        vbox.setPrefWidth(560); // prevent dialog too wide from text area.
        textArea.setVisible(show);
        textArea.setDisable(!show);
        if (show) {
            this.btnContent.setText("Hide Detail");
            this.vbox.getChildren().add(textArea);
            if (dialog != null) {
                dialog.setHeight(400);
            }
        } else {
            this.btnContent.setText("Show Detail");
            this.vbox.getChildren().remove(textArea);
            if (dialog != null) {
                dialog.setHeight(dialog.getHeight() - this.textArea.getHeight());
            }
        }
    }
}
