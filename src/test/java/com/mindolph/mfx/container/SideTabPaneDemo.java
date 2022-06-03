package com.mindolph.mfx.container;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Dimension2D;
import javafx.geometry.HorizontalDirection;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author allen
 */
public class SideTabPaneDemo implements Initializable {

    @FXML
    private SideTabPane leftSideTabPane;
    @FXML
    private SideTabPane rightSideTabPane;
    @FXML
    private Tab tab1;
    @FXML
    private Tab tab2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftSideTabPane.setTabMinDimension(new Dimension2D(180, 30));
        leftSideTabPane.setTabMaxDimension(new Dimension2D(180, 30));
        rightSideTabPane.setDirection(HorizontalDirection.RIGHT);
        rightSideTabPane.setTabMinDimension(new Dimension2D(180, 30));
    }

    @FXML
    public void onBtnUpdate(ActionEvent event) {
        leftSideTabPane.setTabText(tab1, "New Title");
    }

}
