package com.TSP;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private Button resetButton;

    @FXML
    private AnchorPane canvasPane = new AnchorPane();

    private Point2D canvasPanePointCoords;

    @FXML
    private Button startButton;

    private ArrayList<Point2D> pointsArray;

    private int newCitiesCount = 1; //в дальнейшем буду передавать его как bound для ProblemSolver

    private Point2D prevPointClicked;

    private int getCity(Point2D point) {
        int i;
        for (i = 0; i < pointsArray.size(); ++i) {
            if (pointsArray.get(i).distance(point) < 32)
                break;
        }
        return i + 1;
    }

    private Point2D getCityCoords(Point2D point) {
        int i;
        for (i = 0; i < pointsArray.size(); ++i) {
            if (pointsArray.get(i).distance(point) < 32)
                break;
        }
        return pointsArray.get(i);
    }

    private int repPointClicksCounter;

    @FXML
    void onMouseClicked(MouseEvent event) {
            double pointX = event.getSceneX();
            double pointY = event.getSceneY();
            canvasPanePointCoords = canvasPane.sceneToLocal(pointX, pointY);
            int counter = 0; int counter2 = 0;
            int flag = 0;
            if (pointsArray.size() == 0)
                flag = 1;
            else {
                for (int i = 0; i < pointsArray.size(); ++i) {
                    if (pointsArray.get(i).distance(canvasPanePointCoords) > 32)
                        ++counter;
                }
                if (counter == pointsArray.size())
                    flag = 1;
            }
            if (flag == 1 && newCitiesCount < CitiesCollection.size) { //если в месте клика нет точки
                addPoint();
                disableButtons();
            } else if (flag == 0) { //если в месте клика уже есть точка
                if (pointsArray.size() > 1) { //если пред. клик был не в эту же точку и имеется больше 1 точки
                    repPointClicksCounter++;
                    if (repPointClicksCounter == 2) {
                        for (int i = 0; i < pointsArray.size(); ++i) {
                            if (pointsArray.get(i).distance(canvasPanePointCoords) < 32 || pointsArray.get(i).distance(prevPointClicked) < 32)
                                ++counter2;
                        }
                        repPointClicksCounter = 0;
                    }
                    if (counter2 == 2 && prevPointClicked.distance(canvasPanePointCoords) > 32 && CitiesCollection.matrix[getCity(prevPointClicked)][getCity(canvasPanePointCoords)] == 0) { //если пред. клик и текущий клик - города
                        enableButtons();
                    } else {
                        prevPointClicked = canvasPanePointCoords;
                        disableButtons();
                    }
                }
            } else disableButtons();
    }

    private Line[] lineArray = new Line[(CitiesCollection.size*(CitiesCollection.size-1)/2)+1];
    private Circle[] circleArray = new Circle[CitiesCollection.size];
    private Label[]  labelArray = new Label[CitiesCollection.size];

    @FXML
    void initialize() {
        pointsArray = new ArrayList<>();
        canvasPanePointCoords = new Point2D(0,0);
        newCitiesCount = 1;
        prevPointClicked = new Point2D(0,0);
        repPointClicksCounter = 0;
        disableButtons();
        startButton.setDisable(true);
    }

    @FXML
    void onStartButtonClicked(MouseEvent event) {
        CitiesCollection.output();
        try {
            ProblemSolver.findShortestRoute(CitiesCollection.matrix, newCitiesCount).printRoute();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong input!");
        }
    }

    @FXML
    void onResetButtonClicked(MouseEvent event) {
        int k = 0;
        for (k = 1; k <= lineArray.length; ++k) {
            canvasPane.getChildren().remove(lineArray[k - 1]);
        }
        for (k = 1; k < circleArray.length; ++k) {
            canvasPane.getChildren().remove(circleArray[k]);
            canvasPane.getChildren().remove(labelArray[k]);
        }
        CitiesCollection.removeAll();
        initialize();
    }

    private void addPoint() {
        pointsArray.add(canvasPanePointCoords);
        Circle point = new Circle(canvasPanePointCoords.getX(), canvasPanePointCoords.getY(), 8, Color.CADETBLUE);
        Label label = new Label(Integer.toString(newCitiesCount));
        label.setLayoutX(canvasPanePointCoords.getX()+5);
        label.setLayoutY(canvasPanePointCoords.getY()+5);
        canvasPane.getChildren().add(point);
        canvasPane.getChildren().add(label);
        circleArray[newCitiesCount] = point;
        labelArray[newCitiesCount] = label;
        newCitiesCount++;
    }

    private void addLine() {
        if (CitiesCollection.roadsCount > 0) { startButton.setDisable(false); }
        getCityCoords(prevPointClicked);
        getCityCoords(canvasPanePointCoords);
        Line line = new Line();
        lineArray[CitiesCollection.roadsCount] = line;
        line.setStartX(getCityCoords(prevPointClicked).getX());
        line.setStartY(getCityCoords(prevPointClicked).getY());
        line.setEndX(getCityCoords(canvasPanePointCoords).getX());
        line.setEndY(getCityCoords(canvasPanePointCoords).getY());
        canvasPane.getChildren().add(line);
    }

    private void disableButtons() {
        button1.setDisable(true);
        button2.setDisable(true);
        button3.setDisable(true);
        button4.setDisable(true);
        button5.setDisable(true);
        button6.setDisable(true);
        button7.setDisable(true);
        button8.setDisable(true);
        button9.setDisable(true);
    }

    private void enableButtons() {
        button1.setDisable(false);
        button2.setDisable(false);
        button3.setDisable(false);
        button4.setDisable(false);
        button5.setDisable(false);
        button6.setDisable(false);
        button7.setDisable(false);
        button8.setDisable(false);
        button9.setDisable(false);
    }

    @FXML
    void onButton1Clicked(MouseEvent event) {
        CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), 1);
        addLine();
        disableButtons();
    }

    @FXML
    void onButton2Clicked(MouseEvent event) {
        CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), 2);
        addLine();
        disableButtons();
    }

    @FXML
    void onButton3Clicked(MouseEvent event) {
        CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), 3);
        addLine();
        disableButtons();
    }

    @FXML
    void onButton4Clicked(MouseEvent event) {
        CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), 4);
        addLine();
        disableButtons();
    }

    @FXML
    void onButton5Clicked(MouseEvent event) {
        CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), 5);
        addLine();
        disableButtons();
    }

    @FXML
    void onButton6Clicked(MouseEvent event) {
        CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), 6);
        addLine();
        disableButtons();
    }

    @FXML
    void onButton7Clicked(MouseEvent event) {
        CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), 7);
        addLine();
        disableButtons();
    }

    @FXML
    void onButton8Clicked(MouseEvent event) {
        CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), 8);
        addLine();
        disableButtons();
    }

    @FXML
    void onButton9Clicked(MouseEvent event) {
        CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), 9);
        addLine();
        disableButtons();
    }

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

}
