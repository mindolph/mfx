package com.mindolph.mfx.drawing;

import javafx.geometry.Rectangle2D;

public interface Drawable {

    void draw(Graphics g, Context context);

    Layer getLayer();

    void updateBounds(Context c);

    Rectangle2D getBounds();

    Rectangle2D getAbsoluteBounds();
}
