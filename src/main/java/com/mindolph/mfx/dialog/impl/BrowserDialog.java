package com.mindolph.mfx.dialog.impl;

import com.mindolph.mfx.dialog.BaseDialogController;
import com.mindolph.mfx.dialog.CustomDialogBuilder;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.net.URL;
import java.util.function.Consumer;

/**
 * Dialog opens a URL with WebView.
 *
 * @author allen
 */
public class BrowserDialog extends BaseDialogController<String> {

    private final URL url;
    private final WebView webView;

    public BrowserDialog(Window owner, URL url) {
        this.url = url;
        webView = new WebView();
        dialog = new CustomDialogBuilder<String>()
                .owner(owner)
                .fxContent(webView)
                .build();
        dialog.initModality(Modality.NONE);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> {
            window.hide();
        });
    }

    @Override
    public void show(Consumer<String> consumer) {
        webView.getEngine().load(this.url.toString());
        dialog.setTitle(this.url.toString());
        dialog.show();
        if (consumer != null) consumer.accept(String.format("url %s loaded", this.url));
    }
}
