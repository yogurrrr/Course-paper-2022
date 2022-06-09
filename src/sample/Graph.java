package sample;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Graph {
    public int nodesId;
    public int clickedNode;
    public ArrayList<Node> myNodes;

    Graph(){
        myNodes = new ArrayList<Node>(100);
        nodesId = 0;
        clickedNode = -1;
    }
    int checkIfPointExists(Point2D point){
        if (myNodes.size() == 0){
            return -1;
        }
        for (int i = 0; i < myNodes.size(); ++i){
            double localX = myNodes.get(i).coords.getX();
            double localY = myNodes.get(i).coords.getY();
            localX-= point.getX();
            localY-= point.getY();
            if (Math.abs(localX) < 10 && Math.abs(localY) < 10){
                if (clickedNode == -1){
                    clickedNode = i;
                }
                else {
                    //createLine(i);
                }
                return i;
            }
        }
        return -1;
    }

    void createLine(int curnode) {
        if (myNodes.size() == 0) {
            System.out.println("size = 0");
        }
//        System.out.println("line between node " + myNodes.get(clickedNode).number + " and " +  myNodes.get(curnode).number);
//        Line line = new Line();
//        line.setStartX(myNodes.get(clickedNode).coords.getX());
//        line.setStartY(myNodes.get(clickedNode).coords.getY());
//        line.setEndX(myNodes.get(curnode).coords.getX());
//        line.setEndY(myNodes.get(curnode).coords.getY());
        //GraphPane
        //clickedNode = -1;
    }

}
