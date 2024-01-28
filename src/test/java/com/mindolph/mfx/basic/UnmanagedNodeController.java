package com.mindolph.mfx.basic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.apache.commons.lang3.RandomUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author mindolph.com@gmail.com
 */
public class UnmanagedNodeController implements Initializable {

    @FXML
    StackPane stackPane;

    @FXML
    Button button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stackPane.setManaged(false);
        button.setPrefSize(400, 100);
        button.setMinSize(200, 50);
        button.setLayoutX(RandomUtils.nextDouble(100, 400));
        button.setLayoutY(RandomUtils.nextDouble(100, 400));
        button.setOnAction(actionEvent -> {
            button.setMinWidth(button.getMinWidth() + 10);
        });
    }
}
