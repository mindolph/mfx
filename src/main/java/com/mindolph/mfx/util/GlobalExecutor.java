package com.mindolph.mfx.util;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Global executor for async tasks.
 *
 * @since 3.0
 */
public class GlobalExecutor {

    public static final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public static Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

    public static <T> Future<T> submit(Runnable runnable, T result) {
        return executor.submit(runnable, result);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return executor.submit(callable);
    }


    public static CompletableFuture<Void> submitCompletable(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, executor);
    }

    public static <T> CompletableFuture<T> submitCompletable(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

}
