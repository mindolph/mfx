package com.mindolph.mfx.dialog.impl;

import com.mindolph.mfx.dialog.BaseDialogController;
import com.mindolph.mfx.dialog.CustomDialogBuilder;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Dialog shows text block.
 *
 * @author allen
 */
public class TextBlockDialog extends BaseDialogController<String> {

    private final String title;
    private final String text;
    private final TextArea textArea;

    public TextBlockDialog(Window owner, String title, String text, boolean readonly) {
        super(text);
        this.title = title;
        this.text = text;
        textArea = new TextArea();
        textArea.setText(text);
        textArea.setPrefWidth(640);
        CustomDialogBuilder<String> dialogBuilder = new CustomDialogBuilder<String>()
                .owner(owner)
                .title(title)
                .resizable(true)
                .width(640).height(480)
                .fxContent(textArea)
                .controller(this);
        if (readonly) {
            dialogBuilder.buttons(ButtonType.CLOSE);
            textArea.setEditable(false);
        }
        else {
            dialogBuilder
                    .defaultValue(text)
                    .buttons(ButtonType.OK, ButtonType.CANCEL);
            textArea.setEditable(true);
            textArea.textProperty().addListener((observableValue, s, newText) -> result = newText);
        }

        dialog = dialogBuilder.build();
        dialog.initModality(Modality.NONE);
        dialog.setOnCloseRequest(event -> {
            if (!readonly) {
                if (!confirmClosing("Text has been changed, are you sure to close the dialog")) {
                    event.consume();
                }
            }
        });

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> {
            window.hide();
        });

        Platform.runLater(textArea::requestFocus);
    }
}
