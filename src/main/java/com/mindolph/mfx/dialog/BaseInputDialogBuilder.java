package com.mindolph.mfx.dialog;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Callback;

import java.util.*;

/**
 * @param <T> type of default value
 * @param <R> type of actual builder
 * @author allen
 */
public abstract class BaseInputDialogBuilder<T, R> extends BaseDialogBuilder<R> {

    protected T defaultValue;
    protected List<ButtonType> buttonTypes = new ArrayList<>();
    protected Map<ButtonType, Callback> buttonHandlerMap = new HashMap<>();
    protected Map<ButtonType, Node> buttonIconMap = new HashMap<>();

    /**
     * Default value will be returned if negative happens.
     *
     * @param defaultValue
     * @return
     */
    public R defaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return (R) this;
    }

    /**
     * Define buttons for the dialog.
     *
     * @param buttonTypes
     * @return
     */
    public R buttons(ButtonType... buttonTypes) {
        Collections.addAll(this.buttonTypes, buttonTypes);
        return (R) this;
    }

    /**
     * A customized button and it's callback.
     *
     * @param buttonType
     * @param callback
     * @return
     */
    public R button(ButtonType buttonType, Callback callback) {
        Collections.addAll(this.buttonTypes, buttonType);
        buttonHandlerMap.put(buttonType, callback);
        return (R) this;
    }

    /**
     * Add icon node to dialog button.
     *
     * @param buttonType
     * @param icon
     * @return
     */
    public R icon(ButtonType buttonType, Node icon) {
        buttonIconMap.put(buttonType, icon);
        return (R) this;
    }

    public abstract Dialog<T> build();
}
