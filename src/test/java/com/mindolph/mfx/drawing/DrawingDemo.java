package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.TextComp;
import com.mindolph.mfx.drawing.connector.BezierConnector;
import com.mindolph.mfx.drawing.constant.TextAlign;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 2.0
 */
public class DrawingDemo implements Initializable {

    @FXML
    Button btnZoomIn;
    @FXML
    Button btnZoomOut;
    @FXML
    private Canvas canvas;
    DemoContext context = new DemoContext();
    LayerCanvas layerCanvas;
    Rectangle2D canvasBounds;
    Graphics g;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context.setDebugMode(true);
        context.setTest(false);
        this.init();
        this.draw();

        // detecting mouse click and find drawable elements under the mouse.
        canvas.setOnMouseClicked(mouseEvent -> {
            Point2D mousePoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            List<Drawable> elements = layerCanvas.getElements(mousePoint);
            for (Drawable element : elements) {
                System.out.println(element);
            }
        });
    }

    @FXML
    public void onZoomIn() {
        context.setScale(context.getScale() + 0.2f);
        layerCanvas.updateAllBounds();
        this.draw();
    }

    @FXML
    public void onZoomOut() {
        context.setScale(context.getScale() - 0.2f);
        layerCanvas.updateAllBounds();
        this.draw();
    }

    private void init() {
        final Layer l1 = new Layer("L1");
        final Layer l2 = new Layer("L2");

        canvasBounds = new Rectangle2D(0, 0, 600, 400);
        g = new CanvasGraphicsWrapper(canvas, canvasBounds);
        layerCanvas = new LayerCanvas(g, context, List.of(l1, l2));

        // parent with child without layer
        Circle c0 = new Circle(0, 0, 50, 50);
        Triangle t = new Triangle(0, 0, 50, 50);
        c0.add(t);
        layerCanvas.add(c0);


        // #0 Single Triangle on layer 1
        Triangle t0 = new Triangle(0, 200, 100, 100);
        layerCanvas.add(l1, t0);

        // #1 3-levels relationship on layer2
        Circle c1 = new Circle(50, 50, 100, 100);
        Triangle t1 = new Triangle(25, 25, 50, 50);
        Circle c111 = new Circle(0, 10, 30, 30);

        c1.add(t1);
        t1.add(c111);
        layerCanvas.add(l2, c1);

        // #2 parent and child on different layers.
        Circle c2 = new Circle(300, 50, 100, 100);
        Triangle t2 = new Triangle(0, 0, 200, 200);
        c2.add(t2);
        layerCanvas.add(l2, c2);

        // #3 connector on default layer.
//        LineConnector connector = new LineConnector(c1, c2, 100, 50, 200, 100);
        BezierConnector connector = new BezierConnector(c1, c2, 100, 50, 0, 100);
        layerCanvas.add(connector);

        // #4 out of clip bounds of canvas, this should NOT display.
        Circle c3 = new Circle(601, 50, 100, 100);
        layerCanvas.add(l2, c3);

        // #5 text component
        TextComp textComp = new TextComp(0, 300, 200, 20);
        textComp.updateText("Text component that extends the bounds.\nmultiple line also can be handled");
        textComp.setTextAlign(TextAlign.CENTER);
        layerCanvas.add(l2, textComp);
        layerCanvas.updateAllBounds();
    }

    private void draw() {
        // # drawing
        this.canvas.getGraphicsContext2D().clearRect(canvasBounds.getMinX(), canvasBounds.getMinY(), canvasBounds.getWidth(), canvasBounds.getHeight());
        this.g.drawRect(canvasBounds, Color.BLUE, null);
        layerCanvas.drawLayers();
    }
}
