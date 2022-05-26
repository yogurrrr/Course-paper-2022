package com.TSP;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Pane canvasPane;

    @FXML
    private Button startButton;

    @FXML
    void onMouseClicked(MouseEvent event) {
        double pointX = event.getSceneX();
        double pointY = event.getSceneY();
        Point2D canvasPanePointCoords = canvasPane.sceneToLocal(pointX, pointY);
        Circle point = new Circle(canvasPanePointCoords.getX(), canvasPanePointCoords.getY(), 5, Color.RED);
        canvasPane.getChildren().add(point);
    }

    @FXML
    void onStartButtonClicked(MouseEvent event) {

    }

    @FXML
    void initialize() {

    }

}
