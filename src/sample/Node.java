package sample;

import javafx.geometry.Point2D;

public class Node {
    int color;
    int number;
    Point2D coords;
    boolean neighbours[] = new boolean[100];
    Node (Point2D coords, int number){
        this.coords = coords;
        this.number = number;
        this.color = -1;
        for (int i = 0; i < 100; ++i){
            neighbours[i] = false;
        }
        neighbours[number] = true;
    }
    Node(boolean mas[]){
        for (int i = 0; i < mas.length; ++i){
            neighbours[i] = mas[i];
        }

        color = -1;
    }
}

