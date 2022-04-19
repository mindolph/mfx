package com.mindolph.mfx.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author allen
 */
public class FxUtils {

    /**
     * Load dialog without specifying a controller instance, which means the fxml must has bond the controller.
     *
     * @param fxmlResource
     * @param title
     * @throws IOException
     */
    public static void loadDialog(URL fxmlResource, String title) throws IOException {
        loadDialog(fxmlResource, null, title, 600, 480);
    }

    /**
     * Load dialog with specifying a controller instance.
     * @param fxmlResource
     * @param controller
     * @param title
     * @throws IOException
     */
    public static void loadDialog(URL fxmlResource, Object controller, String title) throws IOException {
        loadDialog(fxmlResource, controller, title, 600, 480);
    }

    /**
     * Load dialog with specifying a controller instance, width and height.
     *
     * @param fxmlResource
     * @param controller
     * @param title
     * @param width
     * @param height
     * @throws IOException
     */
    public static void loadDialog(URL fxmlResource, Object controller, String title, int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResource);
        if (controller != null) loader.setController(controller);
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent, width, height);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.out.println("Close dialog");
                stage.close();
            }
        });
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
