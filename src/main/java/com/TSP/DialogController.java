package com.TSP;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class DialogController {

    @FXML
    private TextField distanceInputField;

    private Point2D prev;

    private Point2D canv;

    @FXML
    void onInputButtonClicked(MouseEvent event) {
        Controller.closeDistanceInputWindow();
        String input = distanceInputField.getText();
        System.out.println(prev + " " + canv);
        if (!(input.isEmpty())) {
            if (Integer.parseInt(input) > 0) {
                CitiesCollection.addNewRoad(Controller.getCity(prev), Controller.getCity(canv), Integer.parseInt(input));
                Line line = new Line();
                line.setStartX(prev.getX());
                line.setStartY(prev.getY());
                line.setEndX(canv.getX());
                line.setEndY(canv.getY());
                Controller.canvasPane.getChildren().add(line); //не рисует линию
            }
        }
    }

    @FXML
    void initialize() {
        prev = Controller.prevPointClicked;
        canv = Controller.canvasPanePointCoords;
    }
}
