package com.mindolph.mfx.control;

import com.mindolph.mfx.listener.PausableChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControlsDemo implements Initializable {

    @FXML
    private ChoiceBox<Choice> choiceBox;
    @FXML
    private MChoiceBox<String> mChoiceBox;
    @FXML
    private ComboBox<Pair<String, Choice>> comboBox;

    static String FORBIDDEN_ITEM_NAME = "forbidden item";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // test the MChoicBox features.
        this.initMChoiceBox();
        // test change choices dynamically.
        this.initChoiceBox();
        // test ComboBox
        this.initComboBox();
    }

    private PausableChangeListener<Choice> choiceBoxChangeListener;


    private void initMChoiceBox() {
        mChoiceBox.setConverter(new DefaultStringConverter());
        mChoiceBox.getItems().add(null); // non-select item
        mChoiceBox.getItems().add("aaa");
        mChoiceBox.getItems().add("bbb");
        mChoiceBox.getItems().add("ccc");
        mChoiceBox.getItems().add(FORBIDDEN_ITEM_NAME);
        mChoiceBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (mChoiceBox.isBouncing()) {
                System.out.println("Ignore if bouncing");
                return;
            }
            System.out.printf("selected %s from %s%n", newValue, oldValue);
            if (FORBIDDEN_ITEM_NAME.equals(newValue)) {
                System.out.printf("Bounce to last selected item %n");
                mChoiceBox.bounce();
            }
            System.out.println();
        });
    }

    @FXML
    private void onUnSelectMChoiceBox() {
        mChoiceBox.unselect();
    }


    private void initChoiceBox() {
        choiceBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Choice object) {
                return object == null ? "" : object.getValue();
            }

            @Override
            public Choice fromString(String string) {
                return null;
            }
        });
        // test the pausable change listener.
        choiceBoxChangeListener = PausableChangeListener.wrap((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            System.out.println(newValue.getValue());
        });
        choiceBox.valueProperty().addListener(choiceBoxChangeListener);
        choiceBox.getItems().addAll(new Choice("A"), new Choice("B"), new Choice("C"));
    }

    @FXML
    public void onUpdateChoice() {
        // test changing choices dynamically (with pausable change listener).
        choiceBox.getItems().forEach(item -> {
            item.setValue(item.getValue() + "X");
        });
        choiceBoxChangeListener.pause();
        List<Choice> oldItems = choiceBox.getItems().stream().toList();
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(oldItems);
        choiceBoxChangeListener.resume();
    }

    private void initComboBox() {
        comboBox.setConverter(new StringConverter<>() {

            @Override
            public String toString(Pair<String, Choice> object) {
                return object == null ? "" : object.getValue().getValue();
            }

            @Override
            public Pair<String, Choice> fromString(String string) {
                return null;
            }
        });
        comboBox.setCellFactory(param -> {
            return new ComboBoxListCell<>() {
                @Override
                public void updateItem(Pair<String, Choice> item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    }
                    else {
                        setText(item.getValue().getValue());
                    }
                }
            };
        });
        List.of("A", "B", "C").forEach(item -> {
            comboBox.getItems().add(new Pair<>(item, new Choice(item)));
        });
    }

    @FXML
    private void onUpdateComboBoxItems() {
        comboBox.getItems().forEach(item -> {
            item.getValue().setValue(item.getValue().getValue() + "X");
        });
        List<Pair<String, Choice>> oldItems = comboBox.getItems().stream().toList();
        comboBox.getItems().clear();
        comboBox.getItems().addAll(oldItems);
    }


    public static class Choice {
        String value;

        public Choice(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
