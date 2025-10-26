package com.mindolph.mfx.drawing;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

/**
 * @since 2.0
 */
public interface Context {

    default double scale(double value) {
        return this.getScale() * value;
    }

    default Double scale(Double value) {
        if (value == null) return null;
        return this.scale(value.doubleValue());
    }

    default Rectangle2D scale(Rectangle2D r) {
        if (r == null) return null;
        return new Rectangle2D(
                scale(r.getMinX()), scale(r.getMinY()),
                scale(r.getWidth()), scale(r.getHeight()));
    }

    /**
     * Scale value, minimal is 1.
     *
     * @param value
     * @return
     */
    default double safeScale(double value) {
        return safeScale(value, 1);
    }

    default double safeScale(Double value) {
        return safeScale(value, 1);
    }

    default double safeScale(Double value, double minimal) {
        if (value == null) {
            return minimal;
        }
        double result = this.getScale() * value;
        return Double.compare(result, minimal) >= 0 ? result : minimal;
    }

    default double safeScale(double value, double minimal) {
        double result = this.getScale() * value;
        return Double.compare(result, minimal) >= 0 ? result : minimal;
    }

    default Rectangle2D safeScale(Rectangle2D r, double minimal) {
        Rectangle2D scaled = new Rectangle2D(
                safeScale(r.getMinX(), minimal), safeScale(r.getMinY(), minimal),
                safeScale(r.getWidth(), minimal), safeScale(r.getHeight(), minimal));
        return scaled;
    }

    default Point2D safeScale(Point2D p, double minimal) {
        Point2D scaled = new Point2D(
                safeScale(p.getY(), minimal), safeScale(p.getY(), minimal));
        return scaled;
    }

    boolean isDebugMode();

    void setDebugMode(boolean debugMode);

    double getScale();

    void setScale(double scale);
}
