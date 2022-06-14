package sample;


import javafx.geometry.Point2D;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
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

                System.out.println("line between node " + myGraph.myNodes.get(myGraph.clickedNode).getNumber() + " and " +  myGraph.myNodes.get(curnode).getNumber());
                Line line = new Line();
                myGraph.myNodes.get(myGraph.clickedNode).neighbours[curnode] = true;
                myGraph.myNodes.get(curnode).neighbours[myGraph.clickedNode] = true;
                line.setStartX(myGraph.myNodes.get(myGraph.clickedNode).getCoords().getX());
                line.setStartY(myGraph.myNodes.get(myGraph.clickedNode).getCoords().getY());
                line.setEndX(myGraph.myNodes.get(curnode).getCoords().getX());
                line.setEndY(myGraph.myNodes.get(curnode).getCoords().getY());
                GraphPane.getChildren().add(line);
                myGraph.clickedNode = -1;
            }
            System.out.println("point already exists!");

        }
        else {

            myGraph.clickedNode = -1;
            Point2D point = GraphPane.sceneToLocal(pointX, pointY);
            Circle circle = new Circle(point.getX(), point.getY(), 10, Color.BLACK);

            if (myGraph.myNodes.size() < 100) {
                circles[circlesCount] = circle;
                circlesCount++;
                GraphPane.getChildren().add(circle);
                Node newNode = new Node(point, myGraph.getNodesId());
                myGraph.incrementNodesId();
                myGraph.myNodes.add(newNode);
                System.out.println("node created, id - " + newNode.getNumber());
            }

        }
    }


    @FXML
    void initialize() {

        createNodeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Node[] nodes = new Node[myGraph.getNodesId()];
                for (int i = 0; i < myGraph.getNodesId(); ++i){
                    nodes[i] = myGraph.myNodes.get(i);
                    //ниже строчка нужна для очищения всех цветов если пользователь захочет дорисовать граф
                    nodes[i].setColour(-1);
                }
                int numberofconnections = myGraph.getNodesId();


                //расскраска графа
                boolean newColour = false;
                int color = 0;
                for (int i = 0; i < nodes.length; ++i){
                    if (newColour == true){ color++;}
                    newColour = false;
                    if (nodes[i].getColour() != -1){continue;} //если вершина уже расскрашена
                    boolean[] connStr = Arrays.copyOfRange(nodes[i].neighbours, 0, numberofconnections);//nodes[i].neighbours;
                    for(int j =i+1; j < numberofconnections; ++j){
                        if (connStr[j] == false){ //если соседями не являются
                            if (nodes[j].getColour() == -1){ // если не расскрашены
                                for(int q = 0; q < numberofconnections; ++q){ // складывание строк
                                    newColour = true;
                                    if (nodes[i].neighbours[q] == true || nodes[j].neighbours[q] == true){
                                        connStr[q] = true;
                                    }
                                }
                                nodes[i].setColour(color);
                                nodes[j].setColour(color);
                            }
                        }
                    }
                    if (nodes[i].getColour() == -1){//если так и не расскрасили ноду, то даем ей новый цвет
                        newColour = true;
                        nodes[i].setColour(color);
                    }
                }

                int[] tmp = new int[100];
                //String str = new String();

                for (int i =0; i < nodes.length; ++i){
                    tmp[i] = nodes[i].getColour();
                    if (nodes[i].getColour() == 0){
                        circles[i].setFill(Color.BLACK);
                    }
                    else if( nodes[i].getColour() == 1){
                        circles[i].setFill(Color.RED);
                    }
                    else  if (nodes[i].getColour() == 2){
                        circles[i].setFill(Color.GREEN);
                    }
                    else  if (nodes[i].getColour() == 3){
                        circles[i].setFill(Color.BLUE);
                    }
                    else if (nodes[i].getColour() == 4){
                        circles[i].setFill(Color.YELLOW);
                    }
                    else if (nodes[i].getColour() == 5){
                        circles[i].setFill(Color.ORANGE);
                    }
                    else if (nodes[i].getColour() == 6){
                        circles[i].setFill(Color.PURPLE);
                    }
                    else {
                        circles[i].setFill(Color.BROWN);
                    }

                    System.out.println(nodes[i].getColour());
                }

            }
        });
        //assert createNodeButton != null : "fx:id=\"createNodeButton\" was not injected: check your FXML file 'sample.fxml'.";

    }

}


