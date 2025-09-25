package com.mindolph.mfx;

import com.mindolph.mfx.util.FxmlUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author mindolph.com@gmail.com
 */
public class MfxDemo extends Application implements Initializable {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/mfx_demo.fxml"));
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Hello MFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onSideTabPane(ActionEvent event){
        FxmlUtils.loadUriToStage("/container/side_tab_pane_demo.fxml").show();
    }

    @FXML
    public void onEditCheckListView() {
        FxmlUtils.loadUriToStage("/control/edit_check_list_view_demo.fxml").show();
    }

    @FXML
    public void onDrawingDemo() {
        FxmlUtils.loadUriToStage("/drawing/drawing_demo.fxml").show();
    }

    @FXML
    public void onControlsDemo() {
        FxmlUtils.loadUriToStage("/control/controls_demo.fxml").show();
    }

    public static class MfxDemoLauncher {

        public static void main(String[] args) {
            Application.launch(MfxDemo.class, args);
        }
    }
}
