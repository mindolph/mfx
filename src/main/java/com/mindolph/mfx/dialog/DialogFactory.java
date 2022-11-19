package com.mindolph.mfx.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Optional;

/**
 * @author allen
 */
public class DialogFactory {

    public static final String TITLE_INFORMATION = "Information";
    public static final String TITLE_WARNING = "Warning";
    public static final String TITLE_ERROR = "Error";

    // Default window to set all dialog's owner.
    public static Window DEFAULT_WINDOW;

    public static void infoDialog(String msg) {
        AlertBuilder alertBuilder = new AlertBuilder();
        alertBuilder.owner(DEFAULT_WINDOW)
                .type(Alert.AlertType.INFORMATION)
                .title(TITLE_INFORMATION)
                .content(msg)
                .build().showAndWait();
    }

    public static void warnDialog(String msg) {
        AlertBuilder alertBuilder = new AlertBuilder();
        alertBuilder.owner(DEFAULT_WINDOW)
                .type(Alert.AlertType.WARNING)
                .title(TITLE_WARNING)
                .content(msg)
                .build().showAndWait();
    }

    public static void errDialog(String msg) {
        AlertBuilder alertBuilder = new AlertBuilder();
        alertBuilder.owner(DEFAULT_WINDOW)
                .type(Alert.AlertType.ERROR)
                .title(TITLE_ERROR)
                .content(msg)
                .build().showAndWait();
    }

    public static File openFileDialog(Window window, File initDir) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initDir);
        return fileChooser.showOpenDialog(window);
    }

    /**
     *
     * @param window
     * @param initDir
     * @param extensionFilters like new FileChooser.ExtensionFilter("TrueType Font(*.ttf)", "*.ttf"))
     * @return
     */
    public static File openFileDialog(Window window, File initDir, FileChooser.ExtensionFilter... extensionFilters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initDir);
        if (extensionFilters != null && extensionFilters.length > 0) {
            for (FileChooser.ExtensionFilter extensionFilter : extensionFilters) {
                if (extensionFilter != null) {
                    fileChooser.getExtensionFilters().add(extensionFilter);
                }
            }
        }
        return fileChooser.showOpenDialog(window);
    }

    public static File openSaveFileDialog(Window window, File initDir) {
        return openSaveFileDialog(window, initDir, "", null);
    }

    public static File openSaveFileDialog(Window window, File initDir, String initialFileName, FileChooser.ExtensionFilter... extensionFilters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.setInitialDirectory(initDir);
        fileChooser.setInitialFileName(initialFileName);
        if (extensionFilters != null && extensionFilters.length > 0) {
            for (FileChooser.ExtensionFilter extensionFilter : extensionFilters) {
                if (extensionFilter != null) {
                    fileChooser.getExtensionFilters().add(extensionFilter);
                }
            }
        }
        return fileChooser.showSaveDialog(window);
    }

    public static File openDirDialog(Window window, File initDir) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(initDir);
        return dirChooser.showDialog(window);
    }


    public static boolean okCancelConfirmDialog(String msg) {
        return okCancelConfirmDialog("Confirm", msg);
    }

    public static boolean okCancelConfirmDialog(String title, String msg) {
        Alert alert = new AlertBuilder().owner(DEFAULT_WINDOW)
                .type(Alert.AlertType.CONFIRMATION)
                .title(title)
                .content(msg)
                .buttons(ButtonType.OK, ButtonType.CANCEL)
                .build();
        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.isPresent() && ButtonType.OK == buttonType.get();
    }

    public static boolean yesNoConfirmDialog(String msg) {
        return yesNoConfirmDialog("Confirm", msg);
    }

    public static boolean yesNoConfirmDialog(String title, String msg) {
        Alert alert = new AlertBuilder().owner(DEFAULT_WINDOW)
                .type(Alert.AlertType.CONFIRMATION)
                .title(title)
                .content(msg)
                .buttons(ButtonType.YES, ButtonType.NO)
                .build();
        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.isPresent() && ButtonType.YES == buttonType.get();
    }

    public static Boolean yesNoCancelConfirmDialog(String msg) {
        return yesNoCancelConfirmDialog("Confirm", msg);
    }

    public static Boolean yesNoCancelConfirmDialog(String title, String msg) {
        Alert alert = new AlertBuilder().owner(DEFAULT_WINDOW)
                .type(Alert.AlertType.CONFIRMATION)
                .title(title)
                .content(msg)
                .buttons(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL)
                .build();
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent()) {
            if (buttonType.get() == ButtonType.CANCEL) {
                return null;
            }
            return ButtonType.YES == buttonType.get();
        }
        return null;
    }

}
