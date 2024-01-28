package com.mindolph.mfx.swing;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.Parent;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @author mindolph.com@gmail.com
 */
public class ExtendableSwingNode extends SwingNode {

    public ExtendableSwingNode() {
        super();
    }

    @Override
    public void setContent(JComponent content) {
        super.setContent(content);
        this.setAutoResizeByContent();
    }

    /**
     * The window will be resized when the content of the node changes.
     */
    public void setAutoResizeByContent() {
        Parent parent = this.getParent();
        if (parent!=null) {
            this.getContent().addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    // force the parent JavaFX window resize and center.
                    Platform.runLater(() -> {
                        parent.getScene().getWindow().sizeToScene();
                        parent.getScene().getWindow().centerOnScreen();
                    });
                }
            });
        }
    }

}
