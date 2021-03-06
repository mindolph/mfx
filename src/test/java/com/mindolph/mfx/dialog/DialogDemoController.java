package com.mindolph.mfx.dialog;

import com.mindolph.mfx.dialog.impl.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author allen
 */
public class DialogDemoController {

    @FXML
    public void onText() {
        Dialog<String> dialog = new TextDialogBuilder()
                .title("Text Dialog").content("Input text")
                .build();
        Optional<String> r = dialog.showAndWait();
        r.ifPresent(System.out::println);
    }

    @FXML
    public void onChoice() {
        Dialog<String> dialog = new ChoiceDialogBuilder<String>()
                .title("Choice Dialog").content("Select One")
                .choice("a").choice("b").choice("c").build();
        Optional<String> r = dialog.showAndWait();
        r.ifPresent(System.out::println);
    }

    @FXML
    public void onOptions() {
        Dialog<List<Boolean>> dialog = new OptionsDialogBuilder()
                .title("My Options").content("Choose my options")
                .option("my option 1")
                .option("my option 2")
                .defaultValue(Arrays.asList(true, false))
                .build();
        Optional<List<Boolean>> integers = dialog.showAndWait();
        if (integers.isPresent()) {
            System.out.println("#" + integers.get().size());
            List<Boolean> get = integers.get();
            for (int i = 0; i < get.size(); i++) {
                Boolean aBoolean = get.get(i);
                System.out.printf("#%d %s%n", i, aBoolean);
            }
        }
        else {
            System.out.println("X");
        }
    }

    @FXML
    public void onOpenFile(ActionEvent event) {
        Node node = (Node) event.getSource();
        File file = DialogFactory.openFileDialog(node.getScene().getWindow(), SystemUtils.getUserHome());
        System.out.println(file);
    }

    @FXML
    public void onBrowse(ActionEvent event) {
        try {
            URL url = new URL("http://bing.com");
            Node source = (Node) event.getSource();
            BrowserDialog browserDialog = new BrowserDialog(source.getScene().getWindow(), url);
            browserDialog.show(param -> {
                System.out.println("done browse: " + param);
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    String text = "This is a demo for\n TextBlockDialog";

    @FXML
    public void onTextBlock(ActionEvent event) {
        Node source = (Node) event.getSource();
        TextBlockDialog textBlockDialog = new TextBlockDialog(source.getScene().getWindow(),
                "Text Block Demo", text, false);
        textBlockDialog.show(s -> {
            System.out.println("done with: " + s);
            text = s;
        });
    }

    @FXML
    public void onTextBlockReadonly(ActionEvent event){
        Node source = (Node) event.getSource();
        TextBlockDialog textBlockDialog = new TextBlockDialog(source.getScene().getWindow(),
                "Text Block Readonly Demo", text, true);
        textBlockDialog.showAndWait();
    }

    @FXML
    public void onClosing() {
        ClosingDialog dialog = new ClosingDialog();
        dialog.showAndWait();
    }

    @FXML
    public void onCustom() {
        Dialog<String> dialog = new CustomDialogBuilder<String>()
                .title("Custom Dialog").content("????????????????????????????????????")
                .buttons(ButtonType.OK, ButtonType.CANCEL)
                .controller(new BaseDialogController<String>() {
                    @FXML
                    public void onButton() {
                        result = "Button clicked";
                    }
                })
                .fxmlUri("dialog/custom_dialog.fxml").build();
        Optional<String> r = dialog.showAndWait();
        r.ifPresent(System.out::println);
    }

    @FXML
    public void onCustomDialog() {
        CustomDialog customDialog = new CustomDialog("origin value");
//        String result = customDialog.show();
//        System.out.println(result);
        customDialog.show(r -> {
            System.out.println(r);
        });
    }

    @FXML
    public void onCustomWithSwingContent() {
        Platform.runLater(() -> {
            Dialog<String> dialog = new CustomDialogBuilder<String>()
                    .title("Custom Swing Dialog").content("This is a custom Swing dialog")
                    .buttons(ButtonType.OK, ButtonType.CANCEL)
                    .fxmlUri("dialog/custom_swing_dialog.fxml") // load a fxml with
                    .controller(new CustomWithSwingDialog())
                    .build();
            Optional<String> optResult = dialog.showAndWait();
            System.out.println(optResult.orElse(null));
        });
    }

    @FXML
    public void onCustomSwingContentDialog() {
        new CustomSwingDialog().show(param -> {
            System.out.println(param);
        });
    }

    @FXML
    public void onOnlyOneDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initModality(Modality.NONE);
//        Dialog<Void> dialog = new CustomDialogBuilder<Void>()
//                .title("Only One Dialog")
//                .buttons(ButtonType.CLOSE)
//                .build();
//        DialogPreventor.getIns().showDialog(dialog);
//        DialogPreventor.getIns().closeDialog(dialog);
    }

    @FXML
    public void onDialogWithBigImage() {
        try {
            Image image = new Image(this.getClass().getResourceAsStream("/dialog/topgun_rotate.jpg"));
            Dialog<Void> dialog = new Dialog<>();
            dialog.setResizable(true);
            dialog.setTitle("Dialog With Big Image");
            dialog.setHeaderText("To test the memory usage");
            dialog.setContentText("To test the memory usage");
            dialog.getDialogPane().setContent(new ImageView(image));
            dialog.setResultConverter(param -> null);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.getDialogPane().setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    keyEvent.consume();
                    dialog.close();
                }
            });
            dialog.show();
//            System.gc();
//            Dialog<String> dialog = new CustomDialogBuilder<String>()
//                    .title("Custom Dialog With Big Image").content("To test the memory usage")
//                    .buttons(ButtonType.OK)
//                    .controller(new BaseDialogController<String>() {
//                        @FXML
//                        public void onButton() {
//                            result = "Button clicked";
//                        }
//                    })
//                    .fxContent(new ImageView(image))
//                    .build();
//            dialog.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
