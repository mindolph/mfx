package com.mindolph.mfx.drawing.component;

import com.mindolph.mfx.drawing.Context;
import com.mindolph.mfx.drawing.Graphics;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * Display {@link Image}.
 *
 * @since 3.1
 */
public class ImageComp extends Component {

    private final Image image;

    public ImageComp(Image image) {
        super();
        this.image = image;
        this.bounds = new Rectangle2D(0, 0, image.getWidth(), image.getHeight());
    }

    public ImageComp(Rectangle2D bounds, Image image) {
        super(bounds);
        this.image = image;
    }

    public ImageComp(double x, double y, double width, double height, Image image) {
        super(x, y, width, height);
        this.image = image;
    }

    public ImageComp(double x, double y, Image image) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
    }

    public ImageComp(double width, double height, Anchor anchor, Image image) {
        super(width, height, anchor);
        this.image = image;
    }

    @Override
    public void draw(Graphics g, Context context) {
        super.draw(g, context);
        g.drawImage(this.image, super.absoluteBounds);
    }

    public Image getImage() {
        return image;
    }
}
