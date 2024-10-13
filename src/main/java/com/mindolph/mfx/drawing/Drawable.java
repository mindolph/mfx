package com.mindolph.mfx.drawing;

import javafx.geometry.Rectangle2D;

/**
 * for drawable elements
 *
 * @since 2.0
 */
public interface Drawable {

    void draw(Graphics g, Context context);

    Layer getLayer();

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

    void setActivated(boolean activated);

    boolean isActivated();
}
