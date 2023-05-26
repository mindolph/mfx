package com.mindolph.mfx.container;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Left or right side tab pane.
 *
 * @author allen
 */
public class SideTabPane extends TabPane {
    private final Logger log = LoggerFactory.getLogger(SideTabPane.class);

    private final Map<Tab, Label> tabLabelMap = new LinkedHashMap<>();

    private final ObjectProperty<Dimension2D> tabMinDimension = new SimpleObjectProperty<>(new Dimension2D(100, 40));
    private final ObjectProperty<Dimension2D> tabMaxDimension = new SimpleObjectProperty<>(new Dimension2D(150, 80));
    private final ObjectProperty<HorizontalDirection> direction = new SimpleObjectProperty<>(HorizontalDirection.LEFT);

    public SideTabPane() {
        this.init();
    }

    public SideTabPane(Tab... tabs) {
        super(tabs);
        this.init();
    }

    private void init() {
        // the width and height are reversed because the tab is rotated.
        tabMinDimension.addListener((observable, oldValue, newDim) -> {
            super.setTabMinHeight(newDim.getWidth());
            super.setTabMinWidth(newDim.getHeight());
            if (super.getTabMaxHeight() == Double.MAX_VALUE || super.getTabMaxHeight() < newDim.getWidth())
                super.setTabMaxHeight(newDim.getWidth());
            if (super.getTabMaxWidth() == Double.MAX_VALUE || super.getTabMaxWidth() < newDim.getHeight())
                super.setTabMaxWidth(newDim.getHeight());
        });
        tabMaxDimension.addListener((observable, oldValue, newDim) -> {
            super.setTabMaxHeight(newDim.getWidth());
            super.setTabMaxWidth(newDim.getHeight());
        });
        direction.addListener((observable, oldValue, newValue) -> {
            updateTabsDirection(newValue);
        });
        Platform.runLater(() -> {
            for (Tab tab : getTabs()) {
                log.debug("tab " + tab.getText());
                Label lb = new Label(tab.getText());
                lb.setId(tab.getText());
                StackPane sp = new StackPane(new Group(lb));
                tab.setGraphic(sp);
                tab.setText("");
                tabLabelMap.put(tab, lb);
            }
            updateTabsDirection(getDirection());
        });
    }

    private void updateTabsDirection(HorizontalDirection direction) {
        log.debug("init side tabs");
        double rotateDegree = 0;
        if (direction == HorizontalDirection.LEFT) {
            this.setSide(Side.LEFT);
            rotateDegree = 90;
        }
        else if (direction == HorizontalDirection.RIGHT) {
            this.setSide(Side.RIGHT);
            rotateDegree = 270;
        }
        this.setRotateGraphic(true);
        for (Tab tab : getTabs()) {
            Label label = tabLabelMap.get(tab);
            if (label != null) label.setRotate(rotateDegree);
        }
    }

    public void setTabText(Tab tab, String text) {
        Label label = tabLabelMap.get(tab);
        label.setText(text);
    }

    public void setTabIcon(Tab tab, Node node) {
        Label label = tabLabelMap.get(tab);
        if (label != null)
            label.setGraphic(node);
    }

    public HorizontalDirection getDirection() {
        return direction.get();
    }

    public ObjectProperty<HorizontalDirection> directionProperty() {
        return direction;
    }

    public void setDirection(HorizontalDirection direction) {
        this.direction.set(direction);
    }

    public Dimension2D getTabMinDimension() {
        return tabMinDimension.get();
    }

    public ObjectProperty<Dimension2D> tabMinDimensionProperty() {
        return tabMinDimension;
    }

    public void setTabMinDimension(Dimension2D tabMinDimension) {
        this.tabMinDimension.set(tabMinDimension);
    }

    public Dimension2D getTabMaxDimension() {
        return tabMaxDimension.get();
    }

    public ObjectProperty<Dimension2D> tabMaxDimensionProperty() {
        return tabMaxDimension;
    }

    public void setTabMaxDimension(Dimension2D tabMaxDimension) {
        this.tabMaxDimension.set(tabMaxDimension);
    }

}
