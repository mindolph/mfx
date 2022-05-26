package com.mindolph.mfx.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author allen
 */
public class UrlUtils {

    /**
     * Check whether the URL is valid.
     *
     * @param url
     * @return
     */
    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException e) {
            return false;
        }
    }
}
