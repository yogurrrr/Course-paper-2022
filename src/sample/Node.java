package sample;

import javafx.geometry.Point2D;

public class Node {
    //цвет
    private int colour;
    //порядковый номер
    private int number;
    //координаты
    private Point2D coords;
    //наличие соседей
    protected boolean[] neighbours = new boolean[100];

    Node (Point2D coords, int number){
        this.coords = coords;
        this.number = number;
        this.colour = -1;
        for (int i = 0; i < 100; ++i){
            neighbours[i] = false;
        }
        neighbours[number] = true;
    }

    void setColour(int colour){this.colour = colour;}
    int getColour(){return colour;}
    void setNumber(int number){this.number = number;}
    int getNumber(){return number;}
    void setCoords(Point2D coords){this.coords = coords;}
    Point2D getCoords(){return coords;}


//    Node(boolean mas[]){
//        for (int i = 0; i < mas.length; ++i){
//            neighbours[i] = mas[i];
//        }
//
//        colour = -1;
//    }
}

