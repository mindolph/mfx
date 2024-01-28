package com.mindolph.mfx.dialog;

import com.mindolph.mfx.util.FxImageUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * @author mindolph.com@gmail.com
 */
public class CustomDialog extends BaseDialogController<String> {

    ButtonType customButtonType = new ButtonType("Custom Button", ButtonBar.ButtonData.LEFT);

    @FXML
    private Label label;

    public CustomDialog(String origin) {
        super(origin);
        ImageView imageView;
        try {
            imageView = new ImageView(FxImageUtils.readImageFromResource("/assets/icon.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog = new CustomDialogBuilder<String>()
                .title("Custom Dialog").content("自定义对话框（封装模式）")
                .buttons(ButtonType.OK, ButtonType.CANCEL)
                .button(customButtonType, () -> {
                    System.out.println("this is a custom button");
                    label.setText("custom button clicked");
                })
                .icon(customButtonType, imageView)
                .controller(CustomDialog.this)
                .defaultValue(origin)
                .fxmlUri("dialog/custom_dialog.fxml").build();
        dialog.setOnCloseRequest(dialogEvent -> {
            if (!confirmClosing("Content has been changed, are you sure to close the dialog")) {
                dialogEvent.consume(); // keep the dialog open
            }
        });
    }

    @FXML
    public void onButton(Event event) {
        result = "Button clicked";
    }

}
