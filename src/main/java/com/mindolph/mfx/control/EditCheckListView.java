package com.mindolph.mfx.control;

import com.mindolph.mfx.dialog.impl.TextDialogBuilder;
import com.mindolph.mfx.util.FxmlUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * An editable list view with checkbox items.
 *
 * @author allen
 */
public class EditCheckListView extends VBox implements Initializable {

    private final static Logger log = LoggerFactory.getLogger(EditCheckListView.class);

    @FXML
    private ListView<EditCheckData> listView;

    @FXML
    private HBox hbEditBar;

    private Consumer<ObservableList<EditCheckData>> changeListener;

    private final BooleanProperty editable = new SimpleBooleanProperty(true);

    private String tooltip;

    public EditCheckListView() {
        FxmlUtils.loadUri("/control/edit_check_list_view.fxml", this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        editable.addListener((observableValue, aBoolean, t1) -> hbEditBar.setVisible(t1));

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<EditCheckData> call(ListView<EditCheckData> lv) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(EditCheckData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            CheckBox cb = new CheckBox();
                            cb.setText(null);
                            cb.setSelected(item.isSelected());
                            cb.selectedProperty().addListener((observable, oldValue, newValue) -> {
                                item.setSelected(newValue);
                                changeListener.accept(lv.getItems());
                            });
                            Label lb = new Label();
                            lb.setText(item.getContent());
                            HBox hb = new HBox();
                            hb.getChildren().add(cb);
                            hb.getChildren().add(lb);
                            hb.setSpacing(4);
                            setGraphic(hb);
                        }
                    }
                };
            }
        });
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (editable.get()) {
                    event.consume();
                    Node node = (Node) event.getSource();
                    editItem(node);
                }
            }
        });
    }

    private void editItem(Node node) {
        EditCheckData selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Optional<String> opt = new TextDialogBuilder()
                    .width(300)
                    .owner(node.getScene().getWindow())
                    .content(tooltip)
                    .defaultValue(selectedItem.getContent())
                    .text(selectedItem.getContent())
                    .build().showAndWait();
            if (opt.isPresent() && StringUtils.isNotBlank(opt.get())) {
                log.debug(opt.get());
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                EditCheckData item = listView.getItems().get(selectedIndex);
                item.setContent(opt.get());
                changeListener.accept(listView.getItems());
                listView.refresh();
            } else {
                log.debug("illegal content");
            }
        }
    }

    @FXML
    public void onBtnAdd(ActionEvent event) {
        Node node = (Node) event.getSource();
        Optional<String> opt = new TextDialogBuilder()
                .width(300)
                .owner(node.getScene().getWindow())
                .content(tooltip)
                .build().showAndWait();
        if (opt.isPresent()) {
            String s = opt.get();
            listView.getItems().add(new EditCheckData(opt.get(), true));
            changeListener.accept(listView.getItems());
        }
    }

    @FXML
    public void onBtnRemove() {
        listView.getItems().removeAll(listView.getSelectionModel().getSelectedItems());
        changeListener.accept(listView.getItems());
    }

    public ObservableList<EditCheckData> getItems() {
        return listView.getItems();
    }

    public ObjectProperty<ObservableList<EditCheckData>> itemsProperty() {
        return listView.itemsProperty();
    }

    public void setItems(ObservableList<EditCheckData> items) {
        this.listView.setItems(items);
    }

    public boolean isEditable() {
        return editable.get();
    }

    public BooleanProperty editableProperty() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable.set(editable);
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public Consumer<ObservableList<EditCheckData>> getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(Consumer<ObservableList<EditCheckData>> changeListener) {
        this.changeListener = changeListener;
    }


}
