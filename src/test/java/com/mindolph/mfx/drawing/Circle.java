package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Component;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

public class Circle extends Component {
    public Circle() {
    }

    public Circle(Rectangle2D bounds) {
        super(bounds);
    }

    public Circle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

//    public Circle(Layer layer, Rectangle2D bounds) {
//        super(layer, bounds);
//    }
//
//    public Circle(Layer layer, double x, double y, double width, double height) {
//        super(layer, x, y, width, height);
//    }

    @Override
    public void draw(Graphics g, Context c) {
        super.draw(g, c);
        g.drawOval(super.absoluteBounds.getMinX(), super.absoluteBounds.getMinY(),
                super.absoluteBounds.getWidth(), super.absoluteBounds.getHeight(),
                Color.RED, Color.YELLOW);
    }
}
