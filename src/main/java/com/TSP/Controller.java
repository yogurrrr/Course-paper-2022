package com.TSP;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Point2D;
import javafx.stage.Stage;

public class Controller {

    @FXML
    public static AnchorPane canvasPane;

    private static ArrayList<Point2D> pointsArray;

    private int newCitiesCount; //в дальнейшем буду передавать его как bound для ProblemSolver

    public static Point2D canvasPanePointCoords;

    public static Point2D prevPointClicked;

    public static int getCity(Point2D point) {
            int i;
            for (i = 0; i < pointsArray.size(); ++i) {
                if (pointsArray.get(i).distance(point) < 24)
                    break;
            }
            return i + 1;
    }

    private int repPointClicksCounter;

    private static Stage stage;

    private static void showDistanceInputWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("dialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 250);
        stage.setTitle("Distance input");
        stage.setScene(scene);
        stage.show();
    }

    public static void closeDistanceInputWindow() {
        stage.close();
    }

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
    void initialize() {
        CitiesCollection.createCitiesCollection();
        pointsArray = new ArrayList<>();
        canvasPane = new AnchorPane();
        prevPointClicked = new Point2D(0,0);
        canvasPanePointCoords = new Point2D(0,0);
        newCitiesCount = 1;
        repPointClicksCounter = 0;
        stage = new Stage();
    }

}
