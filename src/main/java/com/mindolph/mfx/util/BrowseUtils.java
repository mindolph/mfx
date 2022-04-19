package com.mindolph.mfx.util;

import com.mindolph.mfx.dialog.impl.BrowserDialog;
import javafx.stage.Window;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Utilize system browser or internal browser.
 *
 * @author allen
 */
public class BrowseUtils {

    private static final Logger log = LoggerFactory.getLogger(BrowseUtils.class);

    private static void showURL(Window window, URL url) {
        BrowserDialog browserDialog = new BrowserDialog(window, url);
        browserDialog.show(param -> {
            System.out.println("done browse: " + param);
            return null;
        });
    }

    private static void showURLExternal(URL url) {
        if (Desktop.isDesktopSupported()) {
            final Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(url.toURI());
                } catch (Exception x) {
                    log.error("Can't browse URL in Desktop", x); 
                }
            }
            else if (SystemUtils.IS_OS_LINUX) {
                final Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("xdg-open " + url); 
                } catch (IOException e) {
                    log.error("Can't browse URL under Linux", e); 
                }
            }
            else if (SystemUtils.IS_OS_MAC) {
                final Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("open " + url); 
                } catch (IOException e) {
                    log.error("Can't browse URL on MAC", e); 
                }
            }
        }
    }

    public static boolean browseURI(Window window, URI uri, boolean preferInsideBrowserIfPossible) {
        try {
            if (preferInsideBrowserIfPossible) {
                showURL(window, uri.toURL());
            }
            else {
                showURLExternal(uri.toURL());
            }
            return true;
        } catch (MalformedURLException ex) {
            log.error("MalformedURLException", ex); 
            return false;
        }
    }
}
