package com.mindolph.mfx.util;

import javafx.application.Platform;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Utils for async operations on JavaFx platform.
 *
 * @author mindolph.com@gmail.com
 */
public class AsyncUtils {

    /**
     * Simply run in a thread and call fxRunner when it's done.
     *
     * @param asyncRunner The runner that runs in a new thread.
     * @param fxRunner The runner that runs in JavaFX thread.
     */
    public static void fxAsync(Runnable asyncRunner, Runnable fxRunner) {
        new Thread(() -> {
            try {
                asyncRunner.run();
                Platform.runLater(fxRunner);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Run in a thread and call fxRunner with return value when it's done.
     *
     * @param asyncRunner The runner that runs in a new thread.
     * @param fxRunner The runner that runs in JavaFX thread.
     * @param <T> The object that returns from asyncRunner to fxRunner
     */
    public static <T> void fxAsync(Supplier<T> asyncRunner, Consumer<T> fxRunner) {
        new Thread(() -> {
            try {
                T o = asyncRunner.get();
                Platform.runLater(() -> {
                    fxRunner.accept(o);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
