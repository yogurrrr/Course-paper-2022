package com.TSP;

import java.util.ArrayList;

public class Route {

    private ArrayList<Integer> cities;
    private int citiesCount;
    private int routeLength;

    Route() {
        cities = new ArrayList<>();
        citiesCount = 0;
        routeLength = 0;
    }

    public void addCity(int city, int distToPrevCity) {
        cities.add(citiesCount++, city);
        routeLength += distToPrevCity;
    }

    public void printRoute() {
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Route: ");
        for (int i = 0; i < citiesCount; ++i)
            System.out.print(cities.get(i) + " ");
        System.out.println("\nWith total distance: " + this.routeLength);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
    }
}
