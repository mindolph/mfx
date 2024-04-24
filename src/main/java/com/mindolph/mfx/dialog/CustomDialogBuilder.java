package com.mindolph.mfx.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swiftboot.util.ClasspathResourceUtils;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Build a {@link Dialog} by customizing its content with FXML and controller.
 * The controller must inherit from {@link BaseDialogController} and the FXML MUST not be defined with controller.
 * if use {@code fxContent()} to load dialog content, the FXML file will be ignored.
 * example:
 * <pre>
 *     Dialog&lt;String&gt; dialog = new CustomDialogBuilder&lt;String&gt;()
 *      .owner(window)
 *      .title("dialog")
 *      .fxmlUri("dialog.fxml")
 *      .buttons(ButtonType.OK, ButtonType.CANCEL)
 *      .icon(ButtonType.OK, iconOk)
 *      .resizable(true)
 *      .build();
 * </pre>
 *
 * @param <T> type of default value
 * @author mindolph.com@gmail.com
 * @see BaseDialogController
 */
public class CustomDialogBuilder<T> extends BaseInputDialogBuilder<T, CustomDialogBuilder<T>> {

    private static final Logger log = LoggerFactory.getLogger(CustomDialogBuilder.class);

    /**
     * Controller of this dialog, if specified, the FXML file must be defined without controller.
     */
    private BaseDialogController<T> controller;

    /**
     * Path of FXML file to load the content of dialog, if it contains controller, the controller() must not be called.
     * The uri should not start with "/"
     */
    private String fxmlUri;

    /**
     * JavaFX component as dialog content.
     */
    private Node fxContent;

    /**
     * Specify controller for dialog, only works when no controller specified in FXML file.
     *
     * @param controller
     * @return
     */
    public CustomDialogBuilder<T> controller(BaseDialogController<T> controller) {
        this.controller = controller;
        return this;
    }

    /**
     * URI of FXML file.
     *
     * @param fxmlUri
     * @return
     */
    public CustomDialogBuilder<T> fxmlUri(String fxmlUri) {
        this.fxmlUri = fxmlUri;
        return this;
    }

    /**
     * Use JavaFX component as dialog content.
     *
     * @param fxContent
     * @return
     */
    public CustomDialogBuilder<T> fxContent(Node fxContent) {
        this.fxContent = fxContent;
        return this;
    }

    /**
     * Build and return the {@link Dialog} object.
     *
     * @return
     */
    public Dialog<T> build() {
        Dialog<T> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.setResizable(resizable);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        dialog.setWidth(width);
        dialog.setHeight(height);
        if (!buttonTypes.isEmpty()) dialog.getDialogPane().getButtonTypes().addAll(buttonTypes);
        for (ButtonType buttonType : dialog.getDialogPane().getButtonTypes()) {
            Button btn = (Button) dialog.getDialogPane().lookupButton(buttonType);
            btn.setGraphic(super.buttonIconMap.get(buttonType));
            if (defaultButton != null) {
                btn.setDefaultButton(defaultButton == buttonType);
            }
        }

        for (ButtonType buttonType : super.buttonHandlerMap.keySet()) {
            Button btn = (Button) dialog.getDialogPane().lookupButton(buttonType);
            btn.addEventFilter(ActionEvent.ACTION, event -> {
                log.trace("Clicked button: " + buttonType);
                event.consume();
                Consumer<Dialog<T>> callback = buttonHandlerMap.get(buttonType);
                if (callback != null) {
                    callback.accept(dialog);
                }
            });
        }

        // 把按钮的选择结果转换成对话框需要的结果
        log.trace("Set result converter for dialog");
        dialog.setResultConverter(buttonType -> {
            if (buttonType == null) {
                return defaultValue;
            }
            else {
                log.debug(String.format("Button: %s", buttonType));
                if (buttonType == ButtonType.OK || buttonType == ButtonType.YES
                        || buttonType == ButtonType.APPLY || buttonType == ButtonType.NEXT
                        || buttonType == ButtonType.FINISH || buttonType == ButtonType.CLOSE) {
                    log.debug("Positive button clicked, use edited result");
                    this.controller.setNegative(false);
                    return controller.getResult();
                }
                else {
                    log.debug("Negative button clicked, use original value as result");
                    this.controller.setNegative(true);
                    return defaultValue; // return back default value if negative.
                }
            }
        });

        // user defined UI
        try {
            Node node;
            if (fxContent != null) {
                dialog.getDialogPane().setContent(fxContent);
            }
            else if (StringUtils.isNotBlank(fxmlUri)) {
                URL uri = ClasspathResourceUtils.getResourceURI(fxmlUri);
                if (uri == null) {
                    throw new RuntimeException("can't find fxml resource: %s".formatted(fxmlUri));
                }
                FXMLLoader loader = new FXMLLoader(uri);
                if (loader.getController() == null && controller != null) loader.setController(controller);
                node = loader.load();
                dialog.getDialogPane().setContent(node);
            }
            else {
                throw new RuntimeException("Dialog can't be initialized, set fxml or content node");
            }
            // Handling key pressed and released events is used to avoid parent dialog being closed
            // when press ESC on a child dialog(or alert). it seems a bug which was claimed already fixed:
            // https://bugs.openjdk.java.net/browse/JDK-8131151
            dialog.getDialogPane().setOnKeyPressed(keyEvent -> {
                log.trace("Key pressed pressed: " + keyEvent.getCode());
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    keyEvent.consume();
                    log.trace("Close the dialog");
                    dialog.close();
                }
            });
            dialog.getDialogPane().setOnKeyReleased(keyEvent -> {
                log.trace("Key released: " + keyEvent.getCode());
            });
            dialog.getDialogPane().getScene().getWindow().sizeToScene();// 不工作，大概是因为此时content还没加载完成
            return dialog;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create dialog", e);
        }
    }
}
