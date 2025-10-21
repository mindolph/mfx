package com.mindolph.mfx.drawing;

import java.io.Serializable;

/**
 * @since 3.0
 */
public abstract class BaseComponent implements Drawable {
    protected Serializable id;
    protected boolean activated;

    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
