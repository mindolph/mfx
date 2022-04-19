package com.mindolph.mfx.dialog;

import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;

/**
 * @param <R> type of the actual builder.
 * @author allen
 */
public abstract class BaseDialogBuilder<R> {

    protected String title = "Dialog";
    protected String header;
    protected String content;
    protected Window owner;
    protected boolean resizable = false;

    public R content(String content) {
        this.content = content;
        return (R) this;
    }

    public R title(String title) {
        this.title = title;
        return (R) this;
    }

    public R title(String title, int maxLength) {
        this.title = StringUtils.abbreviate(title, maxLength);
        return (R) this;
    }

    public R header(String header) {
        this.header = header;
        return (R) this;
    }

    public R owner(Window window) {
        this.owner = window;
        return (R) this;
    }

    public R resizable(boolean resizable) {
        this.resizable = resizable;
        return (R)this;
    }
}
