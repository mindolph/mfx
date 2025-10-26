package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.Anchor;
import com.mindolph.mfx.drawing.component.Container;
import com.mindolph.mfx.drawing.component.Group;
import com.mindolph.mfx.drawing.component.TextComp;
import com.mindolph.mfx.drawing.connector.BezierConnector;
import com.mindolph.mfx.drawing.constant.ExtendDirection;
import com.mindolph.mfx.drawing.constant.TextAlign;
import com.mindolph.mfx.util.FontUtils;
import com.mindolph.mfx.util.RectangleUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.controlsfx.control.CheckListView;
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
    private RadioButton rbBasic;
    @FXML
    private RadioButton rbComplex;
    @FXML
    private CheckListView<Layer> layerList;
    @FXML
    Button btnZoomIn;
    @FXML
    Button btnZoomOut;
    @FXML
    Button btnEnlarge;
    @FXML
    private Canvas canvas;
    @FXML
    private CheckBox ckbDebug;
    DemoContext context = new DemoContext();
    LayerCanvas layerCanvas;
    Rectangle2D canvasBounds;
    Graphics g;
    final Layer l1 = new Layer("L1");
    final Layer l2 = new Layer("L2");
    final Layer l3 = new Layer("L3");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context.setDebugMode(false);
        context.setTest(false);
        ckbDebug.selectedProperty().addListener((observable, oldValue, newValue) -> {
            context.setDebugMode(newValue);
            this.draw();
        });


        ToggleGroup group = new ToggleGroup();

        rbBasic.setToggleGroup(group);
        rbComplex.setToggleGroup(group);

        rbBasic.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.initBasic();
                this.draw();
            }
        });
        rbComplex.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.initComplex();
                this.draw();
            }
        });

        // init the canvas
        canvasBounds = new Rectangle2D(0, 0, 800, 600);
        g = new CanvasGraphicsWrapper(canvas, canvasBounds);
        layerCanvas = new LayerCanvas(g, context, List.of(l1, l2));

//        rbBasic.setSelected(true);
        rbComplex.setSelected(true);

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
                    if (element instanceof TextComp tc) {
                        tc.setFont(FontUtils.newFontWithSize(tc.getFont(), 25));
                        tc.updateText(tc.getText());
                    }
                }
                Selection selection = new Selection(element);
                layerCanvas.add(selection);
            }
            layerCanvas.updateAllBounds(); // TODO
            this.draw();
        });
    }


    private TextComp textCompToEnlarge;
    private ChildComp childCompToEnlarge;

    @FXML
    public void onEnlarge() {
        // enlarge subcomponent to extend the parent
        if (textCompToEnlarge != null) {
            if ("This is sub text".equals(textCompToEnlarge.getText())) {
                textCompToEnlarge.updateText("This is enlarged sub text");
            }
            else {
//                textCompToEnlarge.setFont(FontUtils.newFontWithSize(textCompToEnlarge.getFont(), textCompToEnlarge.getFont().getSize() * 1.2));
                Container parent = textCompToEnlarge.getParent();
                Font font = textCompToEnlarge.getFont();
                parent.remove(textCompToEnlarge);
                textCompToEnlarge = new TextComp(25, 25, 0, 0);
                textCompToEnlarge.updateText("This is enlarged sub text");
                textCompToEnlarge.setFont(FontUtils.newFontWithSize(font, font.getSize() * 1.2));
                parent.add(textCompToEnlarge);
            }
            layerCanvas.updateAllBounds();
        }
        this.draw();
    }

    public void initBasic() {
        layerCanvas.clear();
        layerCanvas.addLayers(l1, l2, l3);

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
        TextComp textComp = new TextComp(0, 300, 0, 0);
        textComp.updateText("Text component that extends the bounds.\nmultiple line also can be handled");
        textComp.setTextAlign(TextAlign.CENTER);
        layerCanvas.add(l2, textComp);

        layerCanvas.updateAllBounds();

        this.initLayoutList();
    }

    public void initComplex() {
        layerCanvas.clear();
        layerCanvas.addLayers(l1, l2, l3);
        double baseX = 0;
        double baseY = 0;
        // #6 group
        RectangleComp rootContainer = new RectangleComp(baseX + 200, baseY + 200, 200, 80);
        RectangleComp childContainer1 = new RectangleComp(baseX + 500, baseY + 100, 200, 80);
        RectangleComp childContainer2 = new RectangleComp(baseX + 500, baseY + 200, 200, 80);
        RectangleComp childContainer3 = new RectangleComp(baseX + 500, baseY + 300, 200, 80);
        RectangleComp childContainer4 = new RectangleComp(baseX + 500, baseY + 400, 200, 80);
        RectangleComp childContainerLeft = new RectangleComp(baseX + 50, baseY + 200, 100, 80);
        this.childCompToEnlarge = new ChildComp(25, 20, 40, 40, Color.PURPLE);
        this.textCompToEnlarge = new TextComp(25, 70, 175, 20);

        ChildComp childOfChild1 = new ChildComp(40, 30, Color.PURPLE);
        ChildComp childOfChild2 = new ChildComp(40, 30, Color.PURPLE);
        ChildComp childOfChild3 = new ChildComp(250, 30, Color.PURPLE);
        ChildComp childOfChild4_1 = new ChildComp(250, 30, Color.PURPLE); // extends the parent
        ChildComp childOfChild4_2 = new ChildComp(30, 30, Color.PURPLE); // dock to right of the parent.
        ChildComp childOfChild4_3 = new ChildComp(30, 30, Color.PURPLE); // dock to bottom of the parent.
        ChildComp childOfChildLeft = new ChildComp(200, 30, Color.PURPLE); // dock and extends parent from direction right to left.
        childOfChild1.setAnchor(new Anchor(null, 15.0, 15.0, null));
        childOfChild2.setAnchor(new Anchor(15.0, null, 15.0, null));
        childOfChild3.setAnchor(new Anchor(15.0, null, 15.0, null));
        childOfChild4_1.setAnchor(new Anchor(15.0, 15.0, 55.0, 55.0));
        childOfChild4_2.setAnchor(new Anchor(null, null, 15.0, null));
        childOfChild4_3.setAnchor(new Anchor(null, null, null, 15.0));
        childOfChildLeft.setAnchor(new Anchor(15.0, 15.0, 55.0, 55.0));

        // == relations ==
        // root
        textCompToEnlarge.updateText("This is sub text");
        rootContainer.addAll(textCompToEnlarge, childCompToEnlarge);
        CircleComp circleComp = new CircleComp(375, 220, 40, 40);
//        BezierConnector connector_2 = new BezierConnector(container, c3, new Point2D(200, 40), new Point2D(50, 100));
//        Group group = Group.of(container, c1_2, connector_2);
        Group groupRoot = Group.of(rootContainer, circleComp);
        // top right anchor to parent
        childContainer1.add(childOfChild1);
        // left right anchor to parent
        childContainer2.add(childOfChild2);
        // left right anchor to parent and extend the parent
        childContainer3.add(childOfChild3);
        // 2 sub components
        childContainer4.addAll(childOfChild4_1, childOfChild4_2, childOfChild4_3);
        // group them
        Group groupLevel1 = Group.of(childContainer1, childContainer2, childContainer3, childContainer4);

        // group left components
        childContainerLeft.setExtendDirections(ExtendDirection.LEFT_DOWN);
        childContainerLeft.add(childOfChildLeft);
        Group groupLeft = Group.of(childContainerLeft);
        layerCanvas.addAll(l2, groupRoot, groupLevel1, groupLeft);

        // TODO the connector must be defined after grouped components, this should be changed.
        BezierConnector rootToChild1 = new BezierConnector(rootContainer, childContainer1, 200, 40, 0, 40);
        BezierConnector rootToChild2 = new BezierConnector(rootContainer, childContainer2, 200, 40, 0, 40);
        BezierConnector rootToChild3 = new BezierConnector(rootContainer, childContainer3, 200, 40, 0, 40);
        BezierConnector rootToChild4 = new BezierConnector(rootContainer, childContainer4, 200, 40, 0, 40);
        layerCanvas.addAll(l1, rootToChild1, rootToChild2, rootToChild3, rootToChild4);

//        RectangleComp rectGroupWithGroup = new RectangleComp(450, 400, 100, 50);
//        Group groupOfGroup = Group.of(group, rectGroupWithGroup);
//        layerCanvas.add(l3, groupOfGroup);

        layerCanvas.updateAllBounds();

        this.initLayoutList();
    }

    private void initLayoutList() {
        layerList.getItems().clear();
        layerList.setCellFactory(listView -> new CheckBoxListCell<>(item -> layerList.getItemBooleanProperty(item)) {
            @Override
            public void updateItem(Layer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                }
                else {
                    setText(item.getName());

                }
            }
        });
        for (Layer layer : layerCanvas.getLayers()) {
            layerList.getItems().add(layer);
        }
        layerList.getItems().add(layerCanvas.getSelectionLayer());
    }


    private void draw() {
        // # drawing
        this.canvas.getGraphicsContext2D().clearRect(canvasBounds.getMinX(), canvasBounds.getMinY(), canvasBounds.getWidth(), canvasBounds.getHeight());
        layerCanvas.drawLayers();
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

}
