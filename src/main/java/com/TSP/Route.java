package com.TSP;

public class Route {

    public int[] cities;
    public int citiesCount;
    public int routeLength;

    Route(int maxCitiesCount) {
        cities = new int[maxCitiesCount];
        citiesCount = 0;
        routeLength = 0;
    }

    public void addCity(int city, int distToPrevCity) {
        cities[citiesCount++] = city;
        routeLength += distToPrevCity;
    }

    public void printRoute() {
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Route: ");
        for (int i = 0; i < citiesCount; ++i)
            System.out.print(cities[i] + " ");
        System.out.println("\nWith total distance: " + this.routeLength);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
    }
}
