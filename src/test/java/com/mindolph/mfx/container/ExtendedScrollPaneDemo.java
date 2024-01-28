package com.mindolph.mfx.container;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExtendedScrollPaneDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/container/extended_scroll_pane_demo.fxml"));
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class MainDemoLauncher {

        public static void main(String[] args) {
            Application.launch(ExtendedScrollPaneDemo.class, args);
        }
    }

}