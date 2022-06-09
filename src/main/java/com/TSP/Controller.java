package com.TSP;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane canvasPane = new AnchorPane();

    private Point2D canvasPanePointCoords = new Point2D(0,0);

    @FXML
    private Button resetButton;

    @FXML
    private Button startButton;

    @FXML
    private Button distanceInputButton;

    @FXML
    private TextField distanceInputField;

    @FXML
    private AnchorPane distanceInputWindow;

    private Stage stage = new Stage();

    private void showDistanceInputWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("dialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 250);
        stage.setTitle("Distance input");
        stage.setScene(scene);
        stage.show();
    }

    private void closeDistanceInputWindow() {
        stage.close();
    }

    private ArrayList<Point2D> pointsArray = new ArrayList<>();

    private int newCitiesCount = 1; //в дальнейшем буду передавать его как bound для ProblemSolver

    private Point2D prevPointClicked = new Point2D(0,0);

    private int getCity(Point2D point) {
        int i;
        for (i = 0; i < pointsArray.size(); ++i) {
            if (pointsArray.get(i).distance(point) < 24)
                break;
        }
        return i + 1;
    }

    private int repPointClicksCounter = 0;

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
                    if (pointsArray.get(i).distance(canvasPanePointCoords) > 24)
                        ++counter;
                }
                if (counter == pointsArray.size())
                    flag = 1;
            }
            if (flag == 1 && newCitiesCount < CitiesCollection.size) { //если в месте клика нет точки
                pointsArray.add(canvasPanePointCoords);
                Circle point = new Circle(canvasPanePointCoords.getX(), canvasPanePointCoords.getY(), 8, Color.CADETBLUE);
                Label label = new Label(Integer.toString(newCitiesCount));
                label.setLayoutX(canvasPanePointCoords.getX()+5);
                label.setLayoutY(canvasPanePointCoords.getY()+5);
                canvasPane.getChildren().add(point);
                canvasPane.getChildren().add(label);
                System.out.println("Added new city: (" + canvasPanePointCoords.getX() + " ; " + canvasPanePointCoords.getY() + ")");
                newCitiesCount++;
            } else if (flag == 0) { //если в месте клика уже есть точка
                if (pointsArray.size() > 1) { //если пред. клик был не в эту же точку и имеется больше 1 точки
                    repPointClicksCounter++;
                    if (repPointClicksCounter == 2) {
                        for (int i = 0; i < pointsArray.size(); ++i) {
                            if (pointsArray.get(i).distance(canvasPanePointCoords) < 24 || pointsArray.get(i).distance(prevPointClicked) < 24)
                                ++counter2;
                        }
                        repPointClicksCounter = 0;
                    }
                    if (counter2 == 2 && prevPointClicked.distance(canvasPanePointCoords) > 24) { //если пред. клик и текущий клик - города
                        try {
                            showDistanceInputWindow();
                        }
                        catch (IOException e) {
                            System.out.println("Wrong input!");
                        }
                        //*********удалить в случае работы диалогового окна*************
/*                        Scanner in = new Scanner(System.in);
                        int tempInput = in.nextInt();
                            CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), tempInput);
                            Line line = new Line();
                            line.setStartX(prevPointClicked.getX());
                            line.setStartY(prevPointClicked.getY());
                            line.setEndX(canvasPanePointCoords.getX());
                            line.setEndY(canvasPanePointCoords.getY());
                            canvasPane.getChildren().add(line);*/
                        //**************************************************************
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
    void onInputButtonClicked(MouseEvent event) {
        closeDistanceInputWindow();
        String input = distanceInputField.getText();
        if (!(input.isEmpty())) {
            if (Integer.parseInt(input) > 0) {
                CitiesCollection.addNewRoad(getCity(prevPointClicked), getCity(canvasPanePointCoords), Integer.parseInt(input));
                Line line = new Line();

                line.setStartX(prevPointClicked.getX()); //координаты почему-то равны нулю
                line.setStartY(prevPointClicked.getY());
                line.setEndX(canvasPanePointCoords.getX());
                line.setEndY(canvasPanePointCoords.getY());

/*              line.setStartX(2);
                line.setStartY(2);
                line.setEndX(200);
                line.setEndY(200);*/
                canvasPane.getChildren().add(line); //не рисует линию, даже если захардкодить координаты
            }
        }
    }

    @FXML
    void initialize() {
/*        canvasPane = new AnchorPane();
        stage = new Stage();
        CitiesCollection.createCitiesCollection();
        pointsArray = new ArrayList<>();
        canvasPanePointCoords = new Point2D(0,0);
        newCitiesCount = 1;
        prevPointClicked = new Point2D(0,0);
        repPointClicksCounter = 0;*/
    }

}
