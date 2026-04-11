package com.mindolph.mfx.control;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import org.swiftboot.util.I18nHelper;

import java.io.IOException;
import java.net.URL;

/**
 * The base control inherit from {@link Control}
 *
 * @author mindolph.com@gmail.com
 */
public class BaseControl extends Control {

    public BaseControl(String fxmlResourcePath) {
        URL fxmUrl = getClass().getResource(fxmlResourcePath);
        if (fxmUrl == null) {
            throw new RuntimeException(String.format("The FXML file does not exist: %s", fxmlResourcePath));
        }
        FXMLLoader loader = new FXMLLoader(fxmUrl);
        loader.setResources(I18nHelper.getInstance().getResourceBundle());
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
