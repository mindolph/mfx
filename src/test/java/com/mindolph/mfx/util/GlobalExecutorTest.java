package com.mindolph.mfx.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class GlobalExecutorTest {

    @Test
    public void submitCompletable() {
        CompletableFuture<Void> completableFuture = GlobalExecutor.submitCompletable(() -> {
            System.out.println("working...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        completableFuture.thenApply(aVoid -> {
            System.out.println("done");
            return aVoid;
        });

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
