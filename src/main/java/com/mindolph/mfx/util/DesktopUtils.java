package com.mindolph.mfx.util;

import org.apache.commons.lang3.SystemUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author mindolph.com@gmail.com
 */
public class DesktopUtils {


    /**
     * Open URL in browser, support Windows and Linux/Unix
     *
     * @param url
     */
    public static void openURL(String url) {
        String osName = System.getProperty("os.name");
        // System.out.println(osName);
        try {
            if (osName.startsWith("Mac OS")) {
//                Class fileMgr = Class.forName("com.apple.eio.FileManager");
//                Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
//                openURL.invoke(null, new Object[]{url});
                try {
                    Process process = Runtime.getRuntime().exec("open " + url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (osName.startsWith("Windows")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            }

            else { //assume Unix or Linux
                String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"};
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++) {
                    if (Runtime.getRuntime().exec(new String[]{"which", browsers[count]}).waitFor() == 0) {
                        browser = browsers[count];
                    }
                }
                if (browser == null) {
                    throw new Exception("Could not find web browser");
                }
                else {
                    Runtime.getRuntime().exec(new String[]{browser, url});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Open file or folder in system, support Linux and macOS for now.
     *
     * @param file
     * @param isOpenFolder if true, open the folder of the file.
     */
    public static void openInSystem(File file, boolean isOpenFolder) {
        File openFile;
        if (file.isFile()) {
            if (isOpenFolder) {
                openFile = file.getParentFile();
            }
            else {
                openFile = file;
            }
        }
        else {
            openFile = file;
        }
        if (SystemUtils.IS_OS_LINUX) {
            try {
                if (Runtime.getRuntime().exec(new String[]{"which", "xdg-open"}).getInputStream().read() != -1) {
                    Process process = Runtime.getRuntime().exec(new String[]{"xdg-open", openFile.getPath()});
//                    System.out.println(process.info().toString());
//                    System.out.println(IoUtils.readAllToString(process.getErrorStream()));
//                    System.out.println(process.isAlive());
//                    CompletableFuture<Process> processCompletableFuture = process.onExit();
//                    if (processCompletableFuture.isDone()){
//                        System.out.println("Done");
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else {
            if (!Desktop.isDesktopSupported()) {
                throw new RuntimeException("Not supported");
            }
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(openFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
