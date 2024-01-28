package com.mindolph.mfx.util;

import javafx.geometry.Dimension2D;

/**
 * @author mindolph.com@gmail.com
 */
public class DimensionUtils {

    public static String dimensionInStr(Dimension2D dimension2D) {
        if (dimension2D == null) {
            return null;
        }
        return String.format("(%.1f x %.1f)", dimension2D.getWidth(), dimension2D.getHeight());
    }

    public static Dimension2D copy(Dimension2D dim) {
        return new Dimension2D(dim.getWidth(), dim.getHeight());
    }

    public static Dimension2D newZero() {
        return new Dimension2D(0, 0);
    }
}
