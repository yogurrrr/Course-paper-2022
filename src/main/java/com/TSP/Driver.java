//package com.TSP;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class Driver {
//
//    ArrayList<City> initialCities = new ArrayList<City>(Arrays.asList(
//            new City("Boston",1,-2),
//            new City("Houston",2, -4),
//            new City("Austin",5, -2),
//            new City("SF",1, -5),
//            new City("Denver",5, -3),
//            new City("LA",1,-9),
//            new City("Chicago",3, -4),
//            new City("NY",2, -2),
//            new City("Dallas",1, 0),
//            new City("Seattle",0, -7)
//    ));
//
//    public static void main(String[] args) {
//        ArrayList<City> cities = new ArrayList<City>();
//        Driver driver = new Driver();
//        cities.addAll(driver.initialCities);
//        driver.printShortestRoute(new problemSolver().findShortestRoute(cities));
//    }
//
//    public void printShortestRoute(Route shortestRoute) {
//        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("Shortest route found so far: " + shortestRoute);
//        System.out.println("w/ total distance: " + shortestRoute.calculateTotalDistance());
//        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
//    }
//}
