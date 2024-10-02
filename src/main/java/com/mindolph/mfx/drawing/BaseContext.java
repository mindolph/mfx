package com.mindolph.mfx.drawing;

/**
 * Base context class for drawing on canvas.
 *
 * @since 2.0
 */
public class BaseContext implements Context {

    private boolean debugMode;

    private double scale = 1.0f;

    @Override
    public boolean isDebugMode() {
        return debugMode;
    }

    @Override
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
    }
}
