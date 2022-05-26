package com.TSP;

import java.util.ArrayList;

public class CitiesCollection {

    public ArrayList<City> cities;
    public int citiesCount;

    CitiesCollection() {
        ArrayList<City> cities = new ArrayList<>();
    }

    public void add(double x, double y) {
        City tempCity = new City(x, y);
        cities.add(tempCity);
    }


    public void remove (City tempCity) {
         cities.remove(tempCity);
    }

    public void clear () {
         cities.clear();
    }

        public void printShortestRoute(Route shortestRoute) {
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Shortest route found so far: " + shortestRoute);
            System.out.println("w/ total distance: " + shortestRoute.calculateTotalDistance());
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        }
}
