package com.mindolph.mfx;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import org.swiftboot.util.I18nHelper;

/**
 * @author mindolph.com@gmail.com
 */
public abstract class BaseController {

    protected I18nHelper i18n = I18nHelper.getInstance();

    protected void disable(Node... nodes) {
        for (Node node : nodes) {
            node.setDisable(true);
        }
    }

    protected void enable(Node... nodes) {
        for (Node node : nodes) {
            node.setDisable(false);
        }
    }

    protected void show(Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(true);
        }
    }

    protected void hide(Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(false);
        }
    }

    protected void select(ToggleButton... buttons){
        for (ToggleButton button : buttons) {
            button.setSelected(true);
        }
    }

    protected void deselect(ToggleButton... buttons){
        for (ToggleButton button : buttons) {
            button.setSelected(false);
        }
    }

    protected void select(CheckBox... buttons){
        for (CheckBox checkBox : buttons) {
            checkBox.setSelected(true);
        }
    }

    protected void deselect(CheckBox... buttons){
        for (CheckBox checkBox : buttons) {
            checkBox.setSelected(false);
        }
    }
}
