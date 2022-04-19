package com.mindolph.mfx.dialog;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Dialog builder that creates file open/save dialog and  dir open dialog.
 *
 * @author allen
 */
public class FileDialogBuilder extends BaseDialogBuilder<FileDialogBuilder> {

    private File initDir;

    private String initFileName;

    private FileChooser.ExtensionFilter[] extensionFilters;

    private FileDialogType fileDialogType;

    public FileDialogBuilder initDir(File initDir) {
        this.initDir = initDir;
        return this;
    }

    /**
     * For Save File Dialog.
     *
     * @param initFileName
     * @return
     */
    public FileDialogBuilder initFileName(String initFileName) {
        this.initFileName = initFileName;
        return this;
    }

    public FileDialogBuilder extensionFilters(FileChooser.ExtensionFilter... extensionFilters) {
        this.extensionFilters = extensionFilters;
        return this;
    }

    /**
     *
     * @param fileDialogType
     * @return
     * @see FileDialogType
     */
    public FileDialogBuilder fileDialogType(FileDialogType fileDialogType) {
        this.fileDialogType = fileDialogType;
        return this;
    }

    public File buildAndShow() {
        File selectedFile = null;
        if (fileDialogType == FileDialogType.SAVE_FILE || fileDialogType == FileDialogType.OPEN_FILE) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(super.title);
            fileChooser.setInitialDirectory(this.initDir);
            fileChooser.setInitialFileName(initFileName);
            if (extensionFilters != null) {
                fileChooser.getExtensionFilters().addAll(extensionFilters);
            }
            if (fileDialogType == FileDialogType.SAVE_FILE) {
                selectedFile = fileChooser.showSaveDialog(super.owner);
            }
            else if (fileDialogType == FileDialogType.OPEN_FILE) {
                selectedFile = fileChooser.showOpenDialog(super.owner);
            }
        }
        else if (fileDialogType == FileDialogType.OPEN_DIR) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle(super.title);
            dirChooser.setInitialDirectory(initDir);
            selectedFile = dirChooser.showDialog(super.owner);
        }
        else {
            throw new RuntimeException("Choose a dialog type");
        }
        if (selectedFile == null) {
            return null;
        }
        return selectedFile;
    }

    public enum FileDialogType {
        OPEN_FILE, SAVE_FILE, OPEN_DIR
    }
}
