package com.mindolph.mfx.container;

import com.mindolph.mfx.util.ScrollUtils;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Advanced Scroll Pane extends JavaFX's {@link ScrollPane},
 * features:
 * scroll to a specific position (in pixel).
 * Animated scroll to a specific position (in pixel).
 * Get the scroll position in X or Y (in pixel).
 * <p>
 * Usage:
 *
 * @author allen
 */
public class ExtendedScrollPane extends ScrollPane {

    private final Logger log = LoggerFactory.getLogger(ExtendedScrollPane.class);

    public ExtendedScrollPane() {
    }

    public ExtendedScrollPane(Node content) {
        super(content);
    }

    /**
     * Scroll from current position to target position in coordinate.
     *
     * @param scrollPos
     * @return
     */
    public String scrollTo(Point2D scrollPos) {
        return ScrollUtils.scrollTo(this, scrollPos.getX(), scrollPos.getY());
    }

    /**
     * Scroll from current position to target position in coordinate.
     *
     * @param x
     * @param y
     * @return
     */
    public String scrollTo(double x, double y) {
        return ScrollUtils.scrollTo(this, x, y);
    }

    /**
     * Scroll in animation from current position to target position in coordinate.
     *
     * @param to       The coordinate to which scroll.
     * @param callback
     */
    public void scrollAnimate(Point2D to, Runnable callback) {
        Bounds viewportBounds = this.getViewportBounds();
        Bounds layoutBounds = this.getContent().getLayoutBounds();
        double h = to.getX() / (layoutBounds.getWidth() - viewportBounds.getWidth());
        double v = to.getY() / (layoutBounds.getHeight() - viewportBounds.getHeight());
        scrollAnimate(h, v, callback);
    }

    /**
     * Scroll in animation from current position to target position
     *
     * @param h        to horizontal
     * @param v        to vertical
     * @param callback
     */
    public void scrollAnimate(double h, double v, Runnable callback) {
        double oh = getHvalue();
        double ov = getVvalue();
        new Thread(() -> {
            int frames = 15;
            double stepH = (h - oh) / frames;
            double stepV = (v - ov) / frames;
            for (int i = 0; i < frames; i++) {
                double toH = oh + stepH * (i + 1);
                double toV = ov + stepV * (i + 1);
                Platform.runLater(() -> {
                    setHvalue(toH);
                    setVvalue(toV);
                });
                try {
                    Thread.sleep(150 / frames);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            callback.run();
        }).start();
    }

    /**
     * @return 0 if the layout x is smaller than viewport width.
     */
    public double getScrollX() {
        double width = getContent().getLayoutBounds().getWidth();
        double vbWidth = getViewportBounds().getWidth();
        return width > vbWidth ? this.getHvalue() * (width - vbWidth) : 0;
    }

    /**
     * @return 0 if the layout x is smaller than viewport height.
     */
    public double getScrollY() {
        double height = getContent().getLayoutBounds().getHeight();
        double vbHeight = getViewportBounds().getHeight();
        return height > vbHeight ? this.getVvalue() * (height - vbHeight) : 0;
    }
}
