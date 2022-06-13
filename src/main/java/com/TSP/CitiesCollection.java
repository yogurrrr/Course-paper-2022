package com.TSP;

public class CitiesCollection {

    private static int size;
    private static int[][] matrix;
    private static int roadsCount;

    public static int getSize() {
        return size;
    }

    public static int[][] getMatrix() {
        return matrix;
    }

    public static int getRoadsCount() {
        return roadsCount;
    }

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

    public static void output(int bound)
    {
        System.out.println("Adjacency matrix:");
        for (int i = 1; i < bound; ++i) {
            for (int j = 1; j < bound; ++j)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }
}
