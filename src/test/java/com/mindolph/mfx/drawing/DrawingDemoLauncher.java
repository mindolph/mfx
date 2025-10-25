package com.mindolph.mfx.drawing;

import com.mindolph.mfx.basic.BasicDemo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawingDemoLauncher extends Application implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/drawing/drawing_demo.fxml"));
        Scene scene = new Scene(root, 1080, 800);
        primaryStage.setTitle("Hello Drawing");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class DemoLauncher {
        public static void main(String[] args) {
            Application.launch(DrawingDemoLauncher.class, args);
        }
    }
}
