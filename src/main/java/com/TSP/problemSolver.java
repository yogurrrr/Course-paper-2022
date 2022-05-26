package com.TSP;

import java.util.ArrayList;
import java.util.Arrays;

public class problemSolver {

    public Route findShortestRoute(ArrayList<City> cities) {
        ArrayList<City> shortestRouteCities = new ArrayList<City>(cities.size());
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Initial Route ==> " + Arrays.toString(cities.toArray()));
        System.out.println("w/ total distance: " + new Route(cities).calculateTotalDistance());
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        City city = cities.get((int)(cities.size() * Math.random()));
        updateRoutes(shortestRouteCities, cities, city);
        while (cities.size() >= 1) {
            city = getNextCity(cities, city);
            updateRoutes(shortestRouteCities, cities, city);
        }
        return new Route(shortestRouteCities);
    }

    private void updateRoutes(ArrayList<City> shortestRouteCities, ArrayList<City> cities, City city) {
        shortestRouteCities.add(city);
        cities.remove(city);
        System.out.println("Cities in shortest route ==> " + Arrays.toString(shortestRouteCities.toArray()));
        System.out.println("Remaining cities ==> " + Arrays.toString(cities.toArray()) + "\n");
    }

    private City getNextCity(ArrayList<City> cities, City city) {
        return cities.stream().min((city1, city2) -> {
            int flag = 0;
            if (city1.MeasureDistance(city) < city2.MeasureDistance(city)) flag = -1;
            else if (city1.MeasureDistance(city) > city2.MeasureDistance(city)) flag = 1;
            return flag;
        }).get();
    }
}