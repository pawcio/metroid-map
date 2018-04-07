package pl.kwako.metroid_map.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class MetroidMapController {
    @FXML
    private StackPane mainPane;
    @FXML
    private Canvas mapCanvas;

    private Image background;

    public void initialize() {
        background = new Image(getClass().getClassLoader().getResourceAsStream("MetroidCompleateMapBG.png"));

        mainPane.prefWidthProperty().addListener((observableValue, oldValue, newValue) -> {
            mapCanvas.setWidth(newValue.doubleValue());
            draw(mapCanvas.getGraphicsContext2D());
        });

        mainPane.prefHeightProperty().addListener((observableValue, oldValue, newValue) -> {
            mapCanvas.setHeight(newValue.doubleValue());
            draw(mapCanvas.getGraphicsContext2D());
        });
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0.0, 0.0, mapCanvas.getWidth(), mapCanvas.getHeight());
        gc.drawImage(background, 0.0, 0.0, mapCanvas.getWidth(), mapCanvas.getHeight());
    }
}
