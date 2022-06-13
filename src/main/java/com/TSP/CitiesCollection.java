package com.TSP;

public class CitiesCollection {

    public static int size;
    public static int[][] matrix;
    public static int roadsCount;

    public static void createCitiesCollection(int maxCitiesCount)
    {
        size = maxCitiesCount;
        matrix = new int[size][size];
        roadsCount = 0;
    }

    public static void addNewRoad(int city, int neighbour, int distance) {
        matrix[city][neighbour]=distance;
        matrix[neighbour][city]=distance;
        ++roadsCount;
    }

    public static void removeAll() {
        for (int i = 1; i < size; ++i) {
            for (int j = 1; j < size; ++j) {
                matrix[i][j] = 0;
            }
        }
        roadsCount = 0;
    }

    public static void output()
    {
        System.out.println("Adjacency matrix:");
        for (int i = 1; i < size; ++i) {
            for (int j = 1; j < size; ++j)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }
}
