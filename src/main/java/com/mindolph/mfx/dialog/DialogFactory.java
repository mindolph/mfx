package com.mindolph.mfx.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Optional;

/**
 * @author mindolph.com@gmail.com
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
     * @param window
     * @param initDir
     * @param extensionFilters like new FileChooser.ExtensionFilter("TrueType Font(*.ttf)", "*.ttf"))
     * @return
     */
    public static File openFileDialog(Window window, File initDir, FileChooser.ExtensionFilter... extensionFilters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initDir);
        if (extensionFilters != null) {
            for (FileChooser.ExtensionFilter extensionFilter : extensionFilters) {
                if (extensionFilter != null) {
                    fileChooser.getExtensionFilters().add(extensionFilter);
                }
            }
        }
        return fileChooser.showOpenDialog(window);
    }

    /**
     * Open a file system dialog to select an existing file without file filters to save with default file name.
     *
     * @param window
     * @param initDir
     * @return
     */
    public static File openSaveFileDialog(Window window, File initDir) {
        return openSaveFileDialog(window, initDir, "", null);
    }

    /**
     * Open a file system dialog to select an existing file with filters or specify a file name to save with.
     *
     * @param window target window
     * @param initDir dir that the dialog popup with.
     * @param initialFileName initial name for file that save to.
     * @param extensionFilters file filters for limit accepted files by extension.
     * @return
     */
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

    /**
     * Confirmation dialog for multi operations,
     * like copy&paste multi files to another folder that might contain files with same name,
     * you might need to ask for overridden for each file or confirm the operation for all files.
     *
     * @param title
     * @param msg
     * @return
     */
    public static MultiConfirmation multiConfirmDialog(String title, String msg) {
        AlertBuilder builder = new AlertBuilder().owner(DEFAULT_WINDOW)
                .type(Alert.AlertType.CONFIRMATION)
                .title(title)
                .content(msg);

        ButtonType yesToAll = new ButtonType("Yest to all", ButtonBar.ButtonData.YES);
        ButtonType noToAll = new ButtonType("No to all", ButtonBar.ButtonData.NO);

        builder.buttons(ButtonType.CANCEL, ButtonType.NO, noToAll, ButtonType.YES, yesToAll)
                .defaultButton(ButtonType.CANCEL);

        Alert alert = builder.build();
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent()) {
            if (buttonType.get() == yesToAll) {
                return MultiConfirmation.YES_TO_ALL;
            }
            else if (buttonType.get() == noToAll) {
                return MultiConfirmation.NO_TO_ALL;
            }
            else if (buttonType.get() == ButtonType.YES) {
                return MultiConfirmation.YES;
            }
            else if (buttonType.get() == ButtonType.NO) {
                return MultiConfirmation.NO;
            }
        }
        return null;
    }

    public enum MultiConfirmation {
        YES, YES_TO_ALL, NO, NO_TO_ALL;

        public static boolean isPositive(MultiConfirmation multiConfirmation) {
            return YES == multiConfirmation || YES_TO_ALL == multiConfirmation;
        }

        public static boolean isNegative(MultiConfirmation multiConfirmation) {
            return NO == multiConfirmation || NO_TO_ALL == multiConfirmation;
        }
    }

}
