package com.TSP;

public class CitiesCollection {

    public static int size;
    public static int[][] matrix;

    public static void createCitiesCollection()
    {
        size = 4;
        matrix = new int[size][size];
    }

    public static void addNewRoad(int city, int neighbour, int distance) {
        matrix[city][neighbour]=distance;
        matrix[neighbour][city]=distance;
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
