package com.mindolph.mfx.control;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;

import java.io.IOException;
import java.net.URL;

/**
 * The base control inherit from {@link Control}
 *
 * @author allen
 */
public class BaseControl extends Control {

    public BaseControl(String fxmlResourcePath) {
        URL fxmUrl = getClass().getResource(fxmlResourcePath);
        if (fxmUrl == null) {
            throw new RuntimeException(String.format("The FXML file does not exist: %s", fxmlResourcePath));
        }
        FXMLLoader loader = new FXMLLoader(fxmUrl);
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
