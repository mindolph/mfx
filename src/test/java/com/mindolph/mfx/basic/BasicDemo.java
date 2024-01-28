package com.mindolph.mfx.basic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BasicDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/basic/basic_demo.fxml"));
        Scene scene = new Scene(root, 600, 380);
        primaryStage.setTitle("Hello JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class DemoLauncher {
        public static void main(String[] args) {
            Application.launch(BasicDemo.class, args);
        }
    }

}