package com.mindolph.mfx.container;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static com.mindolph.mfx.util.DimensionUtils.dimensionInStr;


/**
 * @author mindolph.com@gmail.com
 */
public class ExtendedScrollPaneDemoController implements Initializable {

    @FXML
    Canvas canvas;
    @FXML
    ExtendedScrollPane scrollPane;
    @FXML
    Label label;

    private double scale = 1.0f;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawBackground();
        // Scale by some shortcuts.
        scrollPane.setOnKeyPressed(event -> {
            Dimension2D oldDim = new Dimension2D(canvas.getWidth(), canvas.getHeight());
            if (event.getCode() == KeyCode.EQUALS) {
                scale = scale + 0.2;
            }
            else if (event.getCode() == KeyCode.MINUS) {
                scale = scale - 0.2;
            }
            else {
                return;
            }
            canvas.setWidth(1000 * scale);
            canvas.setHeight(1000 * scale);
            drawBackground();
            Dimension2D newDim = new Dimension2D(canvas.getWidth(), canvas.getHeight());
            System.out.printf("Canvas dimension changed from %s to %s%n", dimensionInStr(oldDim), dimensionInStr(newDim));
            //scrollPane.scrollInCenter(oldDim, newDim); this operation no longer in the customized ScrollPane.
        });
        scrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.printf("Scroll horizontal changed from %.3f to %.3f%n", oldValue.doubleValue(), newValue.doubleValue());
            updateLabel(scrollPane.getContent().getLayoutBounds(), scrollPane.getViewportBounds(), scrollPane.getHvalue(), scrollPane.getVvalue());
        });
        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.printf("Scroll vertical changed from %.3f to %.3f%n", oldValue.doubleValue(), newValue.doubleValue());
            updateLabel(scrollPane.getContent().getLayoutBounds(), scrollPane.getViewportBounds(), scrollPane.getHvalue(), scrollPane.getVvalue());
        });
    }

    private void updateLabel(Bounds contentBounds, Bounds viewportBounds, double scrollH, double scrollV) {
        label.setText(String.format("Content: %.1fx%.1f, viewport: %.1fx%.1f, scroll: %.4f,%.4f:",
                contentBounds.getWidth(), contentBounds.getHeight(),
                viewportBounds.getHeight(), viewportBounds.getHeight(),
                scrollH, scrollV));
    }

    private void drawBackground() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 1000 * scale, 1000 * scale);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        int GRID_SIZE = 100;
        for (int i = 0; i < 10; i++) {
            // draw vertical line
            double offset = (i + 1) * GRID_SIZE * scale;
            gc.strokeLine(offset, 0, offset, 1000 * scale);
            // draw horizontal line
            gc.strokeLine(0, offset, 1000 * scale, offset);
            // draw text
            gc.strokeText(String.valueOf(i + 1), offset, gc.getFont().getSize());
            gc.strokeText(String.valueOf(i + 1), 0, offset);
        }
    }

}
