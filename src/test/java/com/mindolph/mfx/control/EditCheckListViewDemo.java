package com.mindolph.mfx.control;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCheckListViewDemo implements Initializable {

    @FXML
    private EditCheckListView editCheckListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editCheckListView.setTooltip("this is a tooltip");
        ObservableList<EditCheckData> items = editCheckListView.getItems();
        items.add(new EditCheckData("Option 1", true));
        items.add(new EditCheckData("Option 2", true));
        items.add(new EditCheckData("Option 3", true));
    }
}
