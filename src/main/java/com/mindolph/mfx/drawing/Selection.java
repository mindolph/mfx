package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.constant.StrokeType;
import com.mindolph.mfx.util.RectangleUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 3.0
 */
public class Selection implements Drawable {

    private static final Logger log = LoggerFactory.getLogger(Selection.class);
    protected Drawable drawable;

    protected Rectangle2D bounds;

    protected Rectangle2D absoluteBounds;

    protected Layer layer;

    public Selection(Drawable drawable) {
        this.drawable = drawable;
    }

    public void draw(Graphics g, Context context) {
        g.setStroke(2, StrokeType.DASHES);
        if (log.isDebugEnabled()) log.debug("draw bounds: " + RectangleUtils.rectangleInStr(this.absoluteBounds));
        g.drawRect(this.absoluteBounds, Color.PURPLE, null);
        g.setStroke(1, StrokeType.SOLID);
    }

    public Layer getLayer() {
        return layer;
    }

    @Override
    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public void updateBounds(Context c) {
//        if (drawable != null) {
//            double ext = 4f;
//            this.absoluteBounds = new Rectangle2D(
//                    drawable.getAbsoluteBounds().getMinX() + c.safeScale(bounds.getMinX() - ext, 0),
//                    drawable.getAbsoluteBounds().getMinY() + c.safeScale(bounds.getMinY() + ext, 0),
//                    c.safeScale(bounds.getWidth() + ext * 2, 1),
//                    c.safeScale(bounds.getHeight() + ext * 2, 1));
//        }
//        else {

        this.absoluteBounds = c.scale(RectangleUtils.enlarge(this.drawable.getAbsoluteBounds(), 5f));
//        }
    }

    public Rectangle2D getBounds() {
        return bounds;
    }

    public Rectangle2D getAbsoluteBounds() {
        return absoluteBounds;
    }

    @Override
    public Shape getShape() {
        return null;
    }
}
