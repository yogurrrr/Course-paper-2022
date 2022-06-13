package com.TSP;

public class Main {

    public static void main(String[] args) {
        CitiesCollection.createCitiesCollection(8);
        CitiesCollection.addNewRoad(1 , 2, 1);
        CitiesCollection.addNewRoad(3 , 2, 2);
        CitiesCollection.addNewRoad(1, 4, 4);
        CitiesCollection.addNewRoad(1, 5, 5);
        CitiesCollection.output();
        ProblemSolver.findShortestRoute(CitiesCollection.matrix, 6).printRoute();
    }
}
