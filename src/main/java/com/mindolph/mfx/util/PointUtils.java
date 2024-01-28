package com.mindolph.mfx.util;

import javafx.geometry.Point2D;

/**
 * @author mindolph.com@gmail.com
 */
public class PointUtils {

    public static String pointInStr(Point2D point2D) {
        if (point2D == null) {
            return null;
        }
        return String.format("(%.2f, %.2f)", point2D.getX(), point2D.getY());
    }

    public static String pointInStr(double x, double y) {
        return String.format("(%.2f, %.2f)",x, y);
    }
}
