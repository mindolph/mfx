package com.mindolph.mfx.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ControlsDemo implements Initializable {

    @FXML
    private MChoiceBox<String> mChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mChoiceBox.setConverter(new DefaultStringConverter());
        mChoiceBox.getItems().add(null);
        mChoiceBox.getItems().add("aaa");
        mChoiceBox.getItems().add("bbb");
        mChoiceBox.getItems().add("ccc");
        mChoiceBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println(newValue);
            System.out.println(mChoiceBox.hasSelected());
        });
    }
}
