package com.TSP;

public class City {

    private double x;
    private double y;

    City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double MeasureDistance(City city) {
        double deltaX = (city.getX() - this.getX());
        double deltaY = (city.getY() - this.getY());
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

}
