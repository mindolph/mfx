package com.mindolph.mfx.dialog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DialogDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/dialog/dialog_demo.fxml"));
        Scene scene = new Scene(root, 600, 380);
        primaryStage.setTitle("Hello Dialogs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class DialogDemoLauncher {
        public static void main(String[] args) {
            Application.launch(DialogDemo.class, args);
        }
    }

}