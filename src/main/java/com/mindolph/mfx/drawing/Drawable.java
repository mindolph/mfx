package com.mindolph.mfx.drawing;

import com.mindolph.mfx.util.RectangleUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Shape;

/**
 * for drawable elements
 *
 * @since 2.0
 */
public interface Drawable {

    static Rectangle2D intersect(Rectangle2D rectangle1, Rectangle2D rectangle2) {
        return RectangleUtils.intersect(rectangle1, rectangle2);
    }

    static Rectangle2D union(Drawable drawable1, Drawable drawable2) {
        return RectangleUtils.union(drawable1.getBounds(), drawable2.getBounds());
    }

    void draw(Graphics g, Context context);

    Layer getLayer();

    void setLayer(Layer layer);

    /**
     * Update bounds from context, like scaling etc.
     *
     * @param c
     */
    void updateBounds(Context c);

    /**
     * Relative bounds to parent unless no parent.
     */
    Rectangle2D getBounds();

    /**
     * Absolute bounds.
     *
     * @return
     */
    Rectangle2D getAbsoluteBounds();

    Shape getShape();

}
