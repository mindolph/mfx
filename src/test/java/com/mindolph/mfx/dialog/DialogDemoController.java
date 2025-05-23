package com.mindolph.mfx.dialog;

import com.mindolph.mfx.dialog.impl.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author mindolph.com@gmail.com
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
    public void onChoice(ActionEvent event) {
        Dialog<String> dialog = new ChoiceDialogBuilder<String>()
                .owner(getWindow(event))
                .title("Choice Dialog").content("Select One")
                .choice("a").choice("b").choice("c").build();
        Optional<String> r = dialog.showAndWait();
        r.ifPresent(System.out::println);
    }

    @FXML
    public void onOptions(ActionEvent event) {
        Dialog<List<Boolean>> dialog = new OptionsDialogBuilder()
                .owner(getWindow(event))
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
    public void onRadios(ActionEvent event) {
        Dialog<Integer> dialog = new RadioDialogBuilder<Integer>()
                .owner(getWindow(event))
                .title("Radio Dialog")
                .content("Select One")
                .option(1, "Radio 1")
                .option(2, "Radio 2")
                .defaultValue(2)
                .build();
        Optional<Integer> r = dialog.showAndWait();
        if (r.isPresent()) {
            System.out.println("#" + r.get());
        }
        else {
            System.out.println("No radio selected");
        }
    }

    @FXML
    public void onProgress(ActionEvent event) {
        SimpleProgressDialog simpleProgressDialog = new SimpleProgressDialog(getWindow(event),
                "do something...", "do something message...");
        simpleProgressDialog.show(s -> System.out.println("Canceled"));
    }

    @FXML
    public void onOpenFile(ActionEvent event) {
        Node node = (Node) event.getSource();
        File file = DialogFactory.openFileDialog(getWindow(event), SystemUtils.getUserHome());
        System.out.println(file);
    }

    @FXML
    public void onSaveFile(ActionEvent event) {
        Node node = (Node) event.getSource();
        File file = DialogFactory.openSaveFileDialog(getWindow(event), SystemUtils.getUserHome(),
                "FileToSave", new FileChooser.ExtensionFilter("Java File", "*.java"));
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

    String text = """
            A mind map is a diagram used to visually organize information into a hierarchy, showing relationships among pieces of the whole.[1] It is often created around a single concept, drawn as an image in the center of a blank page, to which associated representations of ideas such as images, words and parts of words are added. Major ideas are connected directly to the central concept, and other ideas branch out from those major ideas.
                        
            Mind maps can also be drawn by hand, either as "notes" during a lecture, meeting or planning session, for example, or as higher quality pictures when more time is available. Mind maps are considered to be a type of spider diagram.[2] A similar concept in the 1970s was "idea sun bursting".[3]\s
                        
                        
                        
                        
                        
            end
            """;

    @FXML
    public void onTextBlock(ActionEvent event) {
        Node source = (Node) event.getSource();
        TextBlockDialog textBlockDialog = new TextBlockDialog(source.getScene().getWindow(),
                "Text Block Demo", text, true);
        textBlockDialog.show(s -> {
            System.out.println("done with: " + s);
            text = s;
        });
    }

    @FXML
    public void onTextBlockReadonly(ActionEvent event) {
        Node source = (Node) event.getSource();
        TextBlockDialog textBlockDialog = new TextBlockDialog(source.getScene().getWindow(),
                "Text Block Readonly Demo", text, true);
        textBlockDialog.showAndWait();
    }

    @FXML
    public void onTextBlockMessage(ActionEvent event) {
        Node source = (Node) event.getSource();
        MessageTextBlockDialog messageTextBlockDialog = new MessageTextBlockDialog(source.getScene().getWindow(),
                "Text Message Block Demo", "Message Content\n second line", text, false);
        messageTextBlockDialog.showAndWait();
    }

    @FXML
    public void onClosing() {
        ClosingDialog dialog = new ClosingDialog();
        dialog.showAndWait();
    }

    @FXML
    public void onCustom(ActionEvent event) {
        Dialog<Boolean> dialog = new CustomDialogBuilder<Boolean>()
                .owner(getWindow(event))
                .title("Custom Dialog")
                .buttons(ButtonType.OK, ButtonType.CANCEL)
                .button(new ButtonType("I'v done", ButtonBar.ButtonData.OK_DONE), booleanDialog -> {
                    booleanDialog.setResult(true);
                    booleanDialog.close();
                })
                .defaultButton(ButtonType.CANCEL)
                .controller(new BaseDialogController<Boolean>() {
                    @FXML
                    public void onButton() {
                        result = true;
                    }
                })
                .defaultValue(false)
                .fxmlUri("dialog/custom_dialog.fxml").build();
        Optional<Boolean> r = dialog.showAndWait();
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
    public void onCustomWithSwingContent(ActionEvent event) {
        Platform.runLater(() -> {
            Dialog<String> dialog = new CustomDialogBuilder<String>()
                    .owner(getWindow(event))
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
    public void onCustomSwingContentDialog(ActionEvent event) {
        new CustomSwingDialog().show(param -> {
            System.out.println(param);
        });
    }

    @FXML
    public void onOnlyOneDialog(ActionEvent event) {
        CustomDialog customDialog = new CustomDialog("First Dialog");
    }

    @FXML
    public void onDialogWithBigImage(ActionEvent event) {
        try {
            Image image = new Image(this.getClass().getResourceAsStream("/dialog/topgun_rotate.jpg"));
            Dialog<Void> dialog = new Dialog<>();
            dialog.initOwner(getWindow(event));
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

    @FXML
    public void onOkCancelDialog(ActionEvent event) {
        Boolean result = new ConfirmDialogBuilder().owner(getWindow(event)).content("Demo of ok/cancel dialog")
                .ok().cancel().asDefault().showAndWait();
        System.out.println(result);
    }

    @FXML
    public void onYesNoDialog(ActionEvent event) {
        // the ok() will be replaced by yes(), the first asDefault() will be overridden by second one.
        Boolean result = new ConfirmDialogBuilder().owner(getWindow(event))
                .title("Yes/No/Cancel").content("Demo of yes/no/cancel dialog")
                .ok().yes().no().asDefault().cancel().asDefault().showAndWait();
        System.out.println(result);
    }

    @FXML
    public void onPositiveNegativeDialog(ActionEvent event) {
        Boolean result = new ConfirmDialogBuilder().owner(getWindow(event))
                .title("Positive/Negative").content("Demo of Positive/Negative dialog")
                .positive("I'm in").asDefault().negative("I quit").asDefault().showAndWait();
        System.out.println(result);
    }


    @FXML
    public void onCustomAlertDialog(ActionEvent event) {
        Alert alert = new AlertBuilder().owner(getWindow(event))
                .title("Custom Alert").content("custom alert dialog")
                .buttons(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL)
                .defaultButton(ButtonType.CANCEL).build();
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(System.out::println);
    }

    @FXML
    public void onMultiConfirmDialog() {
        DialogFactory.MultiConfirmation confirm = DialogFactory.multiConfirmDialog("Multi Confirm", "Confirmation for multi operations");
        System.out.println(confirm);
    }
    
    private Window getWindow(Event event) {
        return ((Node) event.getSource()).getScene().getWindow();
    }
}
