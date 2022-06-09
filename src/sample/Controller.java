package sample;


import javafx.geometry.Point2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane GraphPane;

    @FXML
    private Button createNodeButton;

    @FXML
    private TextArea textfield;

    //@FXML
    //private Label output;

    Graph myGraph = new Graph();
    Circle[] circles = new Circle[100];
    int circlesCount = 0;

    @FXML
    void OnMouseClicked(MouseEvent event) {

        double pointX = event.getSceneX();
        double pointY = event.getSceneY();
        Point2D newPoint = GraphPane.sceneToLocal(pointX, pointY);
        System.out.println(pointX + "\n" +  pointY);
        int curnode = myGraph.checkIfPointExists(newPoint);
        if(curnode != -1){
            if (myGraph.clickedNode != curnode){

                System.out.println("line between node " + myGraph.myNodes.get(myGraph.clickedNode).number + " and " +  myGraph.myNodes.get(curnode).number);
                Line line = new Line();
                myGraph.myNodes.get(myGraph.clickedNode).neighbours[curnode] = true;
                myGraph.myNodes.get(curnode).neighbours[myGraph.clickedNode] = true;
                line.setStartX(myGraph.myNodes.get(myGraph.clickedNode).coords.getX());
                line.setStartY(myGraph.myNodes.get(myGraph.clickedNode).coords.getY());
                line.setEndX(myGraph.myNodes.get(curnode).coords.getX());
                line.setEndY(myGraph.myNodes.get(curnode).coords.getY());
                GraphPane.getChildren().add(line);
                myGraph.clickedNode = -1;
            }
            System.out.println("point already exists!");

        }
        else {

            myGraph.clickedNode = -1;
            Point2D point = GraphPane.sceneToLocal(pointX, pointY);
            Circle circle = new Circle(point.getX(), point.getY(), 10, Color.BLACK);
            circles[circlesCount] = circle;
            circlesCount++;
            GraphPane.getChildren().add(circle);
            Node newNode = new Node(point, myGraph.nodesId);
            myGraph.nodesId++;
            myGraph.myNodes.add(newNode);
            System.out.println("node created, id - " + newNode.number);

        }
    }





    @FXML
    void initialize() {

        createNodeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

//                ObservableList<CharSequence> mas = textfield.getParagraphs();  //получаю ввод пользователя
//                Node[] nodes = new Node[mas.size()];
//                int nodeIt = 0;
//                final int numberofconnections = mas.get(0).length();
//                for(int i = 0; i < mas.size(); ++i){ //обхожу каждую строчку
//                    boolean[] connections = new boolean[numberofconnections];
//                    for (int j = 0; j <numberofconnections; ++j){
//                        if (mas.get(i).charAt(j) == '1'){connections[j] = true;}
//                        else {connections[j] = false;}
//                    }
//                    Node t = new Node(connections);
//                    nodes[nodeIt] = t;
//                    nodeIt++;
//                }
                Node[] nodes = new Node[myGraph.nodesId];
                for (int i = 0; i < myGraph.nodesId; ++i){
                    nodes[i] = myGraph.myNodes.get(i);
                    //ниже строчка нужна для очищения всех цветов если пользователь захочет дорисовать граф
                    nodes[i].color = -1;
                }
                int numberofconnections = myGraph.nodesId;



                //расскраска графа
                int color = 0;
                for (int i = 0; i < nodes.length; ++i){
                    color++;
                    if (nodes[i].color != -1){continue;} //если вершина уже расскрашена
                    boolean[] connStr = Arrays.copyOfRange(nodes[i].neighbours, 0, numberofconnections);//nodes[i].neighbours;
                    for(int j =i+1; j < numberofconnections; ++j){
                        if (connStr[j] == false){ //если соседями не являются
                            if (nodes[j].color == -1){ // если не расскрашены
                                for(int q = 0; q < numberofconnections; ++q){ // складывание строк
                                    if (nodes[i].neighbours[q] == true || nodes[j].neighbours[q] == true){
                                        connStr[q] = true;
                                    }
                                }
                                nodes[i].color = color;
                                nodes[j].color = color;
                            }
                        }
                    }
                    if (nodes[i].color == -1){nodes[i].color = color;} //если так и не расскрасили ноду, то даем ей новый цвет
                }

                int[] tmp = new int[100];
                String str = new String();


                for (int i =0; i < nodes.length; ++i){
                    tmp[i] = nodes[i].color;
                    if (nodes[i].color == 0){
                        circles[i].setFill(Color.BLACK);
                    }
                    else if( nodes[i].color == 1){
                        circles[i].setFill(Color.RED);
                    }
                    else  if (nodes[i].color == 2){
                        circles[i].setFill(Color.GREEN);
                    }
                    else  if (nodes[i].color == 3){
                        circles[i].setFill(Color.BLUE);
                    }
                    else if (nodes[i].color == 4){
                        circles[i].setFill(Color.BLACK);
                    }
                    else if (nodes[i].color == 5){
                        circles[i].setFill(Color.ORANGE);
                    }
                    else if (nodes[i].color == 6){
                        circles[i].setFill(Color.PURPLE);
                    }

                    System.out.println(nodes[i].color);
                }
//                output.add
//                output.setText(String.valueOf(tmp));
                //System.out.println(textfield.getParagraphs());
            }
        });
        assert createNodeButton != null : "fx:id=\"createNodeButton\" was not injected: check your FXML file 'sample.fxml'.";

    }

}


