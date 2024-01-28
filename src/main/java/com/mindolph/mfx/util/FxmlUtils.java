package com.mindolph.mfx.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author mindolph.com@gmail.com
 */
public class FxmlUtils {

    public static Stage loadUriToStage(String fxmlResourceUriPath ) {
        URL fxmUrl = FxmlUtils.class.getResource(fxmlResourceUriPath);
        if (fxmUrl == null) {
            throw new RuntimeException(String.format("The FXML file is not exist: %s", fxmlResourceUriPath));
        }
        FXMLLoader loader = new FXMLLoader(fxmUrl);
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            return stage;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static FXMLLoader loadUri(String fxmlResourceUriPath, Object rootAndController) {
        URL fxmUrl = FxmlUtils.class.getResource(fxmlResourceUriPath);
        if (fxmUrl == null) {
            throw new RuntimeException(String.format("The FXML file is not exist: %s", fxmlResourceUriPath));
        }
        FXMLLoader loader = new FXMLLoader(fxmUrl);
        loader.setRoot(rootAndController);
        loader.setController(rootAndController);
        try {
            loader.load();
            return loader;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
