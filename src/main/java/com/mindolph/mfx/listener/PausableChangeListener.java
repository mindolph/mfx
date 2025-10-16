package com.mindolph.mfx.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Wrap (use static method wrap()) the ChangeListener instance that you meant to add to any property,
 * instead, add this wrapped instance to the property.
 * call pause()/resume() to pause/resume the event handling.
 * NOTE: This is a experimental feature.
 *
 * @param <T>
 * @since 3.0
 */
public class PausableChangeListener<T> implements ChangeListener<T> {

    private boolean paused = false;

    private final ChangeListener<T> listener;

    private PausableChangeListener(ChangeListener<T> listener) {
        this.listener = listener;
    }

    public static <T> PausableChangeListener<T> wrap(ChangeListener<T> listener) {
        return new PausableChangeListener<>(listener);
    }

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        if (paused) {
            return;
        }
        if (listener != null) {
            listener.changed(observable, oldValue, newValue);
        }
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }
}
