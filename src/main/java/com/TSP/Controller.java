package com.TSP;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;

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
    private Button resetButton;

    @FXML
    private Button startButton;

    private ArrayList<Point2D> pointsArray;

    private int newCitiesCount; //в дальнейшем буду передавать его как bound для ProblemSolver

    private Point2D prevPointClicked;

    private int getCity(Point2D point) {
        int i;
        for (i = 0; i < pointsArray.size(); ++i) {
            if (pointsArray.get(i).distance(point) < 20)
                break;
        }
        return i + 1;
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
            double pointX = event.getSceneX();
            double pointY = event.getSceneY();
            Point2D canvasPanePointCoords = canvasPane.sceneToLocal(pointX, pointY);
            int counter = 0; int counter2 = 0;
            int flag = 0;
            if (pointsArray.size() == 0)
                flag = 1;
            else {
                for (int i = 0; i < pointsArray.size(); ++i) {
                    if (pointsArray.get(i).distance(canvasPanePointCoords) > 20)
                        ++counter;
                }
                if (counter == pointsArray.size())
                    flag = 1;
            }
            if (flag == 1 && newCitiesCount < CitiesCollection.size) { //если в месте клика нет точки
                pointsArray.add(canvasPanePointCoords);
                Circle point = new Circle(canvasPanePointCoords.getX(), canvasPanePointCoords.getY(), 7, Color.RED);
                Label label = new Label(Integer.toString(newCitiesCount));
                label.setLayoutX(canvasPanePointCoords.getX()-3.5);
                label.setLayoutY(canvasPanePointCoords.getY()-9);
                canvasPane.getChildren().add(point);
                canvasPane.getChildren().add(label);
                System.out.println("Added new city: (" + canvasPanePointCoords.getX() + " ; " + canvasPanePointCoords.getY() + ")");
                newCitiesCount++;
            } else if (flag == 0) { //если в месте клика уже есть точка
                if (prevPointClicked != canvasPanePointCoords && pointsArray.size() > 1) { //если пред. клик был не в эту же точку
                    for (int i = 0; i < pointsArray.size(); ++i) {
                        if (pointsArray.get(i).distance(canvasPanePointCoords) < 20 || pointsArray.get(i).distance(prevPointClicked) < 20)
                            ++counter2;
                    }
                    if (counter2 == 2) { //если пред. клик и текущий клик - города
                        //прописать защиту от соединения с последним кликнутым городом
                        System.out.println("Введите расстояние между городами " + getCity(prevPointClicked) + " и " + getCity(canvasPanePointCoords));
                        //сделать появление диалогового окна вместо ввода в консоли
                        Scanner in = new Scanner(System.in);
                        int temp_input = in.nextInt();
                        CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), temp_input);
                        Line line = new Line();
                        line.setStartX(prevPointClicked.getX());
                        line.setStartY(prevPointClicked.getY());
                        line.setEndX(canvasPanePointCoords.getX());
                        line.setEndY(canvasPanePointCoords.getY());
                        canvasPane.getChildren().add(line);
                    }
                }
            }
        prevPointClicked = canvasPanePointCoords;
    }

    @FXML
    void onStartButtonClicked(MouseEvent event) {
        CitiesCollection.output();
        ProblemSolver.findShortestRoute(CitiesCollection.matrix, newCitiesCount).printRoute();
    }

    @FXML
    void onResetButtonClicked(MouseEvent event) {
        prevPointClicked.subtract(prevPointClicked);
    }

    @FXML
    void initialize() {
        CitiesCollection.createCitiesCollection();
        pointsArray = new ArrayList<>();
        newCitiesCount = 1;
        prevPointClicked = new Point2D(0,0);
    }

}
