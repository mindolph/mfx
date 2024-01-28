package com.mindolph.mfx.control;

/**
 *
 */
public class EditCheckData {
    String content;
    boolean selected;

    public EditCheckData(String content, boolean selected) {
        this.content = content;
        this.selected = selected;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
