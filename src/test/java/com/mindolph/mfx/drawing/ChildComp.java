package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Anchor;
import com.mindolph.mfx.drawing.component.Component;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

public class ChildComp extends Component {

    private Color fill = Color.GREEN;

    public ChildComp() {
        super();
    }

    /**
     * No anchor
     * @param x
     * @param y
     * @param width
     * @param height
     * @param fill
     */
    public ChildComp(double x, double y, double width, double height, Color fill) {
        super(x, y, width, height);
        this.fill = fill;
    }

    /**
     * Anchor to right side by default.
     *
     * @param width
     * @param height
     * @param fill
     */
    public ChildComp(double width, double height, Color fill) {
        super(width, height, new Anchor(null, null, 10.0, null));
        this.fill = fill;
    }


    @Override
    public void draw(Graphics g, Context c) {
        super.draw(g, c);
        g.drawRect(super.absoluteBounds.getMinX(),
                super.absoluteBounds.getMinY(),
                super.absoluteBounds.getWidth(),
                super.absoluteBounds.getHeight(),
                this.fill, this.fill);
    }
}
