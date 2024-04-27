package com.mindolph.mfx.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.mindolph.mfx.dialog.DialogFactory.DEFAULT_WINDOW;

/**
 * Dialog builder to build confirmation dialog simply.
 * If you want more customization, use {@link AlertBuilder}
 *
 * @author mindolph.com@gmail.com
 * @see AlertBuilder
 * @since 1.2
 */
public class ConfirmDialogBuilder extends BaseDialogBuilder<ConfirmDialogBuilder> {
    private static final Logger log = LoggerFactory.getLogger(ConfirmDialogBuilder.class);
    private ButtonType positiveType;
    private ButtonType negativeType;
    private ButtonType cancelType;
    private ButtonType defaultButton;

    // for building process
    private ButtonType currentButton;

    public ConfirmDialogBuilder() {
    }

    public ConfirmDialogBuilder positive(String text) {
        positiveType = new ButtonType(text, ButtonData.OK_DONE);
        currentButton = positiveType;
        return this;
    }

    public ConfirmDialogBuilder negative(String text) {
        negativeType = new ButtonType(text, ButtonData.CANCEL_CLOSE);
        currentButton = positiveType;
        return this;
    }

    public ConfirmDialogBuilder finish() {
        positiveType = ButtonType.APPLY;
        currentButton = positiveType;
        return this;
    }

    public ConfirmDialogBuilder yes() {
        positiveType = ButtonType.YES;
        currentButton = positiveType;
        return this;
    }

    public ConfirmDialogBuilder ok() {
        positiveType = ButtonType.OK;
        currentButton = positiveType;
        return this;
    }

    public ConfirmDialogBuilder no() {
        negativeType = ButtonType.NO;
        currentButton = negativeType;
        return this;
    }

    public ConfirmDialogBuilder cancel() {
        cancelType = ButtonType.CANCEL;
        currentButton = cancelType;
        return this;
    }

    public ConfirmDialogBuilder close() {
        cancelType = ButtonType.CLOSE;
        currentButton = cancelType;
        return this;
    }

    public ConfirmDialogBuilder asDefault() {
        defaultButton = currentButton;
        return this;
    }

    /**
     *
     * @return positive buttons return True, negative buttons return False, cancel or close buttons return null.
     */
    public Boolean showAndWait() {
        List<ButtonType> buttons = Stream.of(positiveType, negativeType, cancelType).filter(Objects::nonNull).toList();
        log.debug("%d buttons".formatted(buttons.size()));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, buttons.toArray(new ButtonType[]{}));
        alert.setTitle(DEFAULT_DLG_TITLE.equals(title) ? "Confirm" : title);
        alert.setContentText(content);
        alert.setHeaderText(header);
        alert.initOwner(DEFAULT_WINDOW);
        for (ButtonType bt : alert.getDialogPane().getButtonTypes()) {
            if (defaultButton != null) {
                Button btn = (Button) alert.getDialogPane().lookupButton(bt);
                btn.setDefaultButton(defaultButton == bt);
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

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent()) {
            ButtonData buttonData = buttonType.get().getButtonData();
            if (buttonData == ButtonData.CANCEL_CLOSE) {
                return null;
            }
            return ButtonData.FINISH == buttonData
                    || ButtonData.YES == buttonData
                    || ButtonData.OK_DONE == buttonData
                    || ButtonData.RIGHT == buttonData
                    || ButtonData.NEXT_FORWARD == buttonData
                    || ButtonData.APPLY == buttonData;
        }
        else {
            return null;
        }
    }

}
