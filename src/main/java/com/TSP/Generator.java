package com.TSP;

public class Generator {

    private int citiesCount;

    Generator(int newCitiesCount) {
        this.citiesCount = newCitiesCount;
    }

    public void generateRoads() { //генератор дорог в графе
        for (int i = 1; i < citiesCount; ++i) {
            for (int j = 1; j < citiesCount; ++j) {
                if (i != j && CitiesCollection.getMatrix()[i][j] == 0) { CitiesCollection.addNewRoad(i,j, ((int)(Math.random() * 9) + 1)); }
            }
        }
    }
}