package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Group;
import com.mindolph.mfx.drawing.component.TextComp;
import com.mindolph.mfx.drawing.connector.BezierConnector;
import com.mindolph.mfx.drawing.constant.TextAlign;
import com.mindolph.mfx.util.RectangleUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 2.0
 */
public class DrawingDemo implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(DrawingDemo.class);

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
            layerCanvas.clearActivation();
            for (Drawable element : elements) {
                if (log.isDebugEnabled())
                    log.debug("Select component: " + RectangleUtils.rectangleInStr(element.getAbsoluteBounds()));
                if (element instanceof BaseComponent c) {
                    c.setActivated(true);
                }
                Selection selection = new Selection(element);
                layerCanvas.add(selection);
            }
            layerCanvas.updateAllBounds(); // TODO
            this.draw();
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
        final Layer l3 = new Layer("L3");

        // init the canvas
        canvasBounds = new Rectangle2D(0, 0, 800, 600);
        g = new CanvasGraphicsWrapper(canvas, canvasBounds);
        layerCanvas = new LayerCanvas(g, context, List.of(l1, l2));

        // parent with child without layer
        CircleComp c0 = new CircleComp(0, 0, 50, 50);
        TriangleComp t = new TriangleComp(0, 0, 50, 50);
        c0.add(t);
        layerCanvas.add(c0);


        // #0 Single Triangle on layer 1
        TriangleComp t0 = new TriangleComp(0, 200, 100, 100);
        layerCanvas.add(l1, t0);

        // #1 3-levels relationship on layer2
        CircleComp c1 = new CircleComp(50, 50, 100, 100);
        TriangleComp t1 = new TriangleComp(25, 25, 50, 50);
        CircleComp c111 = new CircleComp(0, 10, 30, 30);

        c1.add(t1);
        t1.add(c111);
        layerCanvas.add(l2, c1);

        // #2 parent and child on different layers.
        CircleComp c2 = new CircleComp(300, 50, 100, 100);
        TriangleComp t2 = new TriangleComp(0, 0, 200, 200);
        c2.add(t2);
        layerCanvas.add(l2, c2);

        // #3 connector on default layer.
//        LineConnector connector = new LineConnector(c1, c2, 100, 50, 200, 100);
        BezierConnector connector = new BezierConnector(c1, c2, 100, 50, 0, 100);
        layerCanvas.add(connector);

        // #4 out of clip bounds of canvas, this should NOT display.
        CircleComp c3 = new CircleComp(601, 50, 100, 100);
        layerCanvas.add(l2, c3);

        // #5 text component
        TextComp textComp = new TextComp(0, 300, 200, 20);
        textComp.updateText("Text component that extends the bounds.\nmultiple line also can be handled");
        textComp.setTextAlign(TextAlign.CENTER);
        layerCanvas.add(l2, textComp);

        // #6 group
        RectangleComp container = new RectangleComp(50, 400, 200, 80);
        TextComp subText = new TextComp(25, 25, 200, 20);
        subText.updateText("This is some text");
        container.add(subText);
        CircleComp c1_2 = new CircleComp(250, 420, 40, 40);
        BezierConnector connector_2 = new BezierConnector(container, c3, new Point2D(200, 40), new Point2D(50, 100));
        Group group = Group.of(container, c1_2, connector_2);
        layerCanvas.add(l3, group);

        layerCanvas.updateAllBounds();
    }

    private void draw() {
        // # drawing
        this.canvas.getGraphicsContext2D().clearRect(canvasBounds.getMinX(), canvasBounds.getMinY(), canvasBounds.getWidth(), canvasBounds.getHeight());
        layerCanvas.drawLayers();
    }
}
