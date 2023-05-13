package com.mindolph.mfx.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import org.apache.commons.lang3.StringUtils;
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
 * @author allen
 * @since 1.2
 * @see AlertBuilder
 */
public class ConfirmDialogBuilder extends BaseDialogBuilder<ConfirmDialogBuilder> {
    private static final Logger log = LoggerFactory.getLogger(ConfirmDialogBuilder.class);
    private ButtonType positiveType;
    private ButtonType negativeType;
    private ButtonType cancelType;
    private ButtonType defaultButton;

    private ButtonType currentButton;

    public ConfirmDialogBuilder() {
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

    public boolean showAndWait() {
        List<ButtonType> buttons = Stream.of(positiveType, negativeType, cancelType).filter(Objects::nonNull).toList();
        log.debug("%d buttons".formatted(buttons.size()));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, buttons.toArray(new ButtonType[]{}));
        alert.setTitle(DEFAULT_DLG_TITLE.equals(title) ? "Confirm": title);
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
        return buttonType.isPresent() &&
                (ButtonType.FINISH == buttonType.get()
                        ||ButtonType.YES == buttonType.get()
                        || ButtonType.OK == buttonType.get());
    }

}
