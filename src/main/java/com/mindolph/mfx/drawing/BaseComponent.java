package com.mindolph.mfx.drawing;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;

/**
 * @since 3.0
 */
public abstract class BaseComponent implements Drawable {
    protected Serializable id;
    protected boolean activated = false;

    public BaseComponent() {
        this.id = RandomStringUtils.secure().nextAlphabetic(8);
    }

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
