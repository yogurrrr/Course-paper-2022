package com.TSP;

public class ProblemSolver {

    public static Route findShortestRoute(int[][] matrix, int bound) {
        int size = bound; //если матрица заполнена не полностью
        int[] attendanceArray = new int[size];
        int attendedCitiesCount = 0;
        int i = 0, j = 0;
        for (i = 1; i < size; ++i)
            attendanceArray[i] = 0;
        Route shortestRoute = new Route(size-1);
        int minDist = 999999;
        int minJ = 0;

        shortestRoute.addCity(1, 0);
        attendanceArray[1] = 1;
        attendedCitiesCount++;

        i = 1;
        while (attendedCitiesCount != size - 1) {
            for (j = 1; j < size; ++j) {
                if (attendanceArray[j] == 0 && i != j) { //если еще не были в городе и не на диагонали
                    if (matrix[i][j] < minDist) {
                        minDist = matrix[i][j];
                        minJ = j;
                    }
                }
            }
            shortestRoute.addCity(minJ, matrix[i][minJ]);
            attendanceArray[minJ] = 1;
            attendedCitiesCount++;
            minDist = 999999;
            i = minJ;
        }
        return shortestRoute;
    }
}