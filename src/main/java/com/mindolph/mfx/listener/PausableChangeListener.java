package com.mindolph.mfx.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Wrap (use static method wrap()) the ChangeListener instance that you meant to add to any property,
 * instead, add this wrapped instance to the property.
 * call pause()/resume() to pause/resume the event handling.
 * NOTE: This is an experimental feature.
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

    /**
     * Wrap a {@link ChangeListener} as {@link PausableChangeListener}
     * @param listener
     * @return
     * @param <T>
     */
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

    /**
     * Invoke to pause listening change event.
     */
    public void pause() {
        paused = true;
    }

    /**
     * Invoke to resume listening change event.
     */
    public void resume() {
        paused = false;
    }
}
