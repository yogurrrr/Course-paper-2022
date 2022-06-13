package com.TSP;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.scene.control.Alert;

public class Controller {

    @FXML
    private AnchorPane canvasPane = new AnchorPane();

    private Point2D canvasPanePointCoords;

    @FXML
    private Button startButton;

    @FXML
    private Button generateButton;

    private static ArrayList<Point2D> pointsArray;

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
            if (pointsArray.size() == 0) { flag = 1; }
            else {
                for (int i = 0; i < pointsArray.size(); ++i) {
                    if (pointsArray.get(i).distance(canvasPanePointCoords) > 32) { ++counter; }
                }
                if (counter == pointsArray.size()) { flag = 1; }
            }
            if (flag == 1 && newCitiesCount < CitiesCollection.getSize()) { //если в месте клика нет точки
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
                    if (counter2 == 2 && prevPointClicked.distance(canvasPanePointCoords) > 32 && CitiesCollection.getMatrix()[getCity(prevPointClicked)][getCity(canvasPanePointCoords)] == 0) { //если пред. клик и текущий клик - города
                        enableButtons();
                    } else {
                        prevPointClicked = canvasPanePointCoords;
                        disableButtons();
                    }
                }
            } else disableButtons();
    }

    private Line[] lineArray = new Line[(CitiesCollection.getSize()*(CitiesCollection.getSize()-1)/2)+1]; //максимальное количество ребер в полном графе
    private Circle[] circleArray = new Circle[CitiesCollection.getSize()];
    private Label[]  labelArray = new Label[CitiesCollection.getSize()];

    @FXML
    void initialize() {
        pointsArray = new ArrayList<>();
        canvasPanePointCoords = new Point2D(0,0);
        newCitiesCount = 1;
        prevPointClicked = new Point2D(0,0);
        repPointClicksCounter = 0;
        disableButtons();
        startButton.setDisable(true);
        generateButton.setDisable(true);
        localRoadsCount = 0;
    }

    @FXML
    void onStartButtonClicked(MouseEvent event) {
        CitiesCollection.output(newCitiesCount);
        Route resRoute = new Route(); int flag = 0;
        try {
           resRoute = ProblemSolver.findShortestRoute(CitiesCollection.getMatrix(), newCitiesCount);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("A route was not found :(");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Make sure that the first city has neighbours and the matrix does not contain unconnected clusters of cities!");
            alert.showAndWait();
            flag = 1;
        }
        if (flag == 0) { resRoute.printRoute(); }
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

    private int localRoadsCount;

    @FXML
    void onGenerateButtonClicked(MouseEvent event) {
        Generator generator = new Generator(newCitiesCount);
        generator.generateRoads();
        generateButton.setDisable(true);
        startButton.setDisable(false);
        disableButtons();
        for (int i = 1; i < newCitiesCount; ++i) {
            for (int j = 1; j < i; ++j) {
                //по номерам i-1 и j-1 получать координаты начала и конца линии из pointsArray
                Line line = new Line();
                lineArray[localRoadsCount++] = line;
                line.setStartX(pointsArray.get(i-1).getX());
                line.setStartY(pointsArray.get(i-1).getY());
                line.setEndX(pointsArray.get(j-1).getX());
                line.setEndY(pointsArray.get(j-1).getY());
                canvasPane.getChildren().add(line);
            }
        }
    }

    private void addPoint() {
        pointsArray.add(canvasPanePointCoords);
        Circle point = new Circle(canvasPanePointCoords.getX(), canvasPanePointCoords.getY(), 8, Color.CADETBLUE);
        if (newCitiesCount == 1) { point.setFill(Color.RED); }
        Label label = new Label(Integer.toString(newCitiesCount));
        label.setLayoutX(canvasPanePointCoords.getX()+5);
        label.setLayoutY(canvasPanePointCoords.getY()+5);
        canvasPane.getChildren().add(point);
        canvasPane.getChildren().add(label);
        circleArray[newCitiesCount] = point;
        labelArray[newCitiesCount] = label;
        newCitiesCount++;
        if (newCitiesCount > 2 && CitiesCollection.getRoadsCount() == 0) { generateButton.setDisable(false); }
    }

    private void addLine() {
        if (CitiesCollection.getRoadsCount() > 0) { startButton.setDisable(false); }
        getCityCoords(prevPointClicked);
        getCityCoords(canvasPanePointCoords);
        Line line = new Line();
        lineArray[CitiesCollection.getRoadsCount()] = line;
        line.setStartX(getCityCoords(prevPointClicked).getX());
        line.setStartY(getCityCoords(prevPointClicked).getY());
        line.setEndX(getCityCoords(canvasPanePointCoords).getX());
        line.setEndY(getCityCoords(canvasPanePointCoords).getY());
        canvasPane.getChildren().add(line);
        generateButton.setDisable(true);
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
