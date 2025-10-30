package com.mindolph.mfx.drawing;

import com.mindolph.mfx.drawing.component.*;
import com.mindolph.mfx.drawing.connector.BezierConnector;
import com.mindolph.mfx.drawing.constant.ExtendDirection;
import com.mindolph.mfx.drawing.constant.TextAlign;
import com.mindolph.mfx.util.FontUtils;
import com.mindolph.mfx.util.FxImageUtils;
import com.mindolph.mfx.util.RectangleUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.CheckListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 2.0
 */
public class DrawingDemo implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(DrawingDemo.class);

    private static final Font awesomeFont = Font.loadFont(DrawingDemo.class.getResourceAsStream("/de/jensd/fx/glyphs/fontawesome/fontawesome-webfont.ttf"), 24);

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
    Button btnShrink;
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
    final Layer l4 = new Layer("L4");
    final Layer l5 = new Layer("L5");

    private TextComp textCompToEnlarge;
    private ChildComp childCompToEnlarge;

    // avoid redundant redrawing;
    private boolean isLoading;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context.setDebugMode(false);
        context.setTest(false);
        ckbDebug.selectedProperty().addListener((observable, oldValue, newValue) -> {
            context.setDebugMode(newValue);
            this.redraw();
        });


        ToggleGroup group = new ToggleGroup();

        rbBasic.setToggleGroup(group);
        rbComplex.setToggleGroup(group);

        rbBasic.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                try {
                    this.initBasic();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.redraw();
            }
        });
        rbComplex.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                try {
                    this.initComplex();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.redraw();
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
                    log.debug("Select component: %s".formatted(RectangleUtils.rectangleInStr(element.getAbsoluteBounds())));
                if (element instanceof BaseComponent c) {
                    c.setActivated(true);
                    if (element instanceof TextComp tc) {
                        tc.updateText(tc.getText(), FontUtils.newFontWithSize(tc.getFont(), 25));
                    }
                }
                Selection selection = new Selection(element);
                layerCanvas.add(selection);
            }
            layerCanvas.updateAllBounds(); // TODO
            this.redraw();
        });

        this.initLayoutList();
    }

    public void initBasic() throws IOException {
        isLoading = true;
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

        // #5.1 child component to enlarge
        RectangleContainer childContainer = new RectangleContainer(300, 300, 0, 0);
        childContainer.setId("childContainer");
        this.childCompToEnlarge = new ChildComp(25, 25, 100, 50, Color.LIGHTGRAY);
        childContainer.add(childCompToEnlarge);
        layerCanvas.add(l4, childContainer);

        // #5.2 text component to enlarge
        RectangleContainer textContainer = new RectangleContainer(450, 300, 100, 50);
        textContainer.setId("textContainer");
        this.textCompToEnlarge = new TextComp(25, 75, 0, 0);
        this.textCompToEnlarge.setId("textCompToEnlarge");
        this.textCompToEnlarge.updateText("This is enlarged sub text");
        textContainer.add(textCompToEnlarge);
        layerCanvas.add(l5, textContainer);

        // #6 image component
        Image image = FxImageUtils.readImageFromResource("/drawing/app.png");
        ImageComp imageComp1 = new ImageComp(20, 400, 40, 40, image);
        ImageComp imageCom2 = new ImageComp(100, 400, image);
        ImageComp imageToRight = new ImageComp(50, 50, Anchor.toRight(5), image);
        ImageComp imageToLeftRight = new ImageComp(50, 50, Anchor.toLeftRight(5, 5), image);
        Container imgC1 = new Container(300, 400, 100, 100);
        Container imgC2 = new Container(300, 400, 100, 100);
        imgC1.add(imageToRight);
        imgC2.add(imageToLeftRight);
        layerCanvas.addAll(l3, imageComp1, imageCom2, imgC1, imgC2);

        // #7 font icon component
        FontIconComp fontIconComp = new FontIconComp(40, 450, 24, 24, awesomeFont);
        fontIconComp.updateText(FontAwesomeIcon.STICKY_NOTE_ALT.unicode());
        layerCanvas.addAll(l3, fontIconComp);

//        layerCanvas.getBaseLayer().setVisible(false);
//        l1.setVisible(false);
//        l2.setVisible(false);
//        l3.setVisible(false);
//        l4.setVisible(false);
//        l5.setVisible(false);
//        layerCanvas.getSelectionLayer().setVisible(false);

        layerCanvas.updateAllBounds();

        this.updateLayoutList();
        isLoading = false;
    }

    public void initComplex() throws IOException {
        isLoading = true;
        layerCanvas.clear();
        layerCanvas.addLayers(l1, l2, l3);
        double baseX = 0;
        double baseY = 0;
        // #6 group
        RectangleContainer rootContainer = new RectangleContainer(baseX + 200, baseY + 200, 200, 80);
        RectangleContainer childContainer1 = new RectangleContainer(baseX + 500, baseY + 100, 200, 80);
        RectangleContainer childContainer2 = new RectangleContainer(baseX + 500, baseY + 200, 200, 80);
        RectangleContainer childContainer3 = new RectangleContainer(baseX + 500, baseY + 300, 200, 80);
        RectangleContainer childContainer4 = new RectangleContainer(baseX + 500, baseY + 400, 200, 80);
        RectangleContainer childContainerLeft = new RectangleContainer(baseX + 50, baseY + 200, 100, 80);
        Image image = FxImageUtils.readImageFromResource("/drawing/app.png");
        ImageComp imageComp = new ImageComp(80, 20, 40, 40, image);
        FontIconComp fontIconComp = new FontIconComp(130, 20, 24, 24, awesomeFont);
        fontIconComp.updateText(FontAwesomeIcon.STICKY_NOTE_ALT.unicode());
        fontIconComp.setColor(Color.WHITE);
        this.childCompToEnlarge = new ChildComp(25, 20, 40, 40, Color.PURPLE);
        this.textCompToEnlarge = new TextComp(25, 70, 175, 20);

        ChildComp childOfChild1 = new ChildComp(40, 30, Color.PURPLE);
        ChildComp childOfChild2 = new ChildComp(40, 30, Color.PURPLE);
        ChildComp childOfChild3 = new ChildComp(250, 30, Color.PURPLE);
        ChildComp childOfChild4_1 = new ChildComp(250, 30, Color.PURPLE); // extends the parent
        ChildComp childOfChild4_2 = new ChildComp(30, 30, Color.PURPLE); // dock to right of the parent.
        ChildComp childOfChild4_3 = new ChildComp(30, 30, Color.PURPLE); // dock to bottom of the parent.
        ChildComp childOfChildLeft = new ChildComp(200, 30, Color.PURPLE); // dock and extends parent from direction right to left.
        childOfChild1.setAnchor(Anchor.toRightTop(15));
        childOfChild2.setAnchor(Anchor.toLeftRight(15));
        childOfChild3.setAnchor(Anchor.toLeftRight(15));
        childOfChild4_1.setAnchor(new Anchor(15.0, 15.0, 55.0, 55.0));
        childOfChild4_2.setAnchor(Anchor.toRight(15));
        childOfChild4_3.setAnchor(Anchor.toBottom(15));
        childOfChildLeft.setAnchor(new Anchor(15.0, 15.0, 55.0, 55.0));

        // == relations ==
        // root
        textCompToEnlarge.updateText("This is sub text");
        rootContainer.addAll(imageComp, fontIconComp, textCompToEnlarge, childCompToEnlarge);
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

        this.updateLayoutList();
        isLoading = false;
    }

    private void updateLayoutList() {
        layerList.getItems().clear();
        // init all layers.
        List<Layer> layers = layerCanvas.getLayers();
        layerList.getItems().add(layerCanvas.getBaseLayer());
        layerList.getItems().addAll(layers);
        layerList.getItems().add(layerCanvas.getSelectionLayer());
        // check on all layers by default.
        for (Layer layer : layerList.getItems()) {
            if (layer.isVisible()) {
                layerList.getCheckModel().check(layerList.getItems().indexOf(layer));
            }
        }
    }

    private void initLayoutList() {
        layerList.setCellFactory(listView -> new CheckBoxListCell<>(item -> layerList.getItemBooleanProperty(item)) {
            @Override
            public void updateItem(Layer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                }
                else {
                    setText("%s(%d)".formatted(item.getName(), item.getElements().size()));
                }
            }
        });
        Platform.runLater(() -> {
            layerList.getCheckModel().getCheckedItems().addListener((ListChangeListener<Layer>) l -> {
                if (!isLoading) {
                    while (l.next()) {
                        log.debug("Checked layers: %s".formatted(StringUtils.join(l.getAddedSubList(), " -> ")));
                        l.getAddedSubList().forEach(layer -> {
                            layer.setVisible(true);
                        });
                        log.debug("Unchecked layers: %s".formatted(StringUtils.join(l.getRemoved(), " -> ")));
                        l.getRemoved().forEach(layer -> {
                            layer.setVisible(false);
                        });
                        redraw();
                    }
                }

            });
        });
    }

    private void redraw() {
        // # drawing
        log.debug("redraw whole canvas.");
        this.canvas.getGraphicsContext2D().clearRect(canvasBounds.getMinX(), canvasBounds.getMinY(), canvasBounds.getWidth(), canvasBounds.getHeight());
        layerCanvas.drawLayers();
    }

    @FXML
    private void onRedraw() {
        this.redraw();
    }

    @FXML
    public void onEnlarge() {
        // enlarge subcomponent to extend the parent
        if (childCompToEnlarge != null) {
            Container parent = childCompToEnlarge.getParent();
            parent.remove(childCompToEnlarge);
            childCompToEnlarge = new ChildComp(50, 50, 125, 75, Color.DARKGRAY);
            parent.add(childCompToEnlarge); 
        }
        if (textCompToEnlarge != null) {
            Container parent = textCompToEnlarge.getParent();
            Font font = textCompToEnlarge.getFont();
            parent.remove(textCompToEnlarge);
            textCompToEnlarge = new TextComp(25, 70, 175, 20);
            textCompToEnlarge.setId("textCompToEnlarge-%d".formatted(parent.getDescendants().size()));
            textCompToEnlarge.updateText(StringUtils.repeat("This is enlarged sub text", 1),
                    FontUtils.newFontWithSize(font, font.getSize() * 1.5));
            parent.add(textCompToEnlarge);
            log.debug("layer components: " + StringUtils.join(parent.getLayer().getElements().stream().map(d -> {
                if (d instanceof BaseComponent c) {
                    return c.getId();
                }
                return "NO-ID";
            }), ","));
        }
        layerCanvas.updateAllBounds();
        layerList.refresh();
        this.redraw();
    }

    @FXML
    public void onShrink() {
        layerList.refresh();
        if (childCompToEnlarge != null) {
            Container parent = childCompToEnlarge.getParent();
            parent.remove(childCompToEnlarge);
            childCompToEnlarge = new ChildComp(50, 50, 50, 25, Color.DARKGRAY);
            parent.add(childCompToEnlarge);
        }
        if (textCompToEnlarge != null) {
            Container parent = textCompToEnlarge.getParent();
            Font font = textCompToEnlarge.getFont();
            parent.remove(textCompToEnlarge);
            textCompToEnlarge = new TextComp(25, 70, 50, 20);
            textCompToEnlarge.setId("textCompToEnlarge-%d".formatted(parent.getDescendants().size()));
            textCompToEnlarge.updateText(StringUtils.repeat("This is enlarged sub text", 1),
                    FontUtils.newFontWithSize(font, font.getSize() * 0.75));
            parent.add(textCompToEnlarge);
        }
        layerCanvas.updateAllBounds();
        layerList.refresh();
        this.redraw();
    }

    @FXML
    public void onZoomIn() {
        context.setScale(context.getScale() + 0.2f);
        layerCanvas.updateAllBounds();
        this.redraw();
    }

    @FXML
    public void onZoomOut() {
        context.setScale(context.getScale() - 0.2f);
        layerCanvas.updateAllBounds();
        this.redraw();
    }

}
