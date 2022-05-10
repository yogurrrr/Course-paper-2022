package com.assignmentproblem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm {
    public static ObservableList<ObservableList<Integer>> solveProblem(ObservableList<Agent> agents) {
        int agentsNumber = agents.size();
        int tasksNumber = agents.get(0).getTaskCost().size();
        int lowerDimension = agentsNumber, biggerDimension = tasksNumber;
        if (agentsNumber >= tasksNumber) {
            lowerDimension = tasksNumber;
            biggerDimension = agentsNumber;
        }
        ObservableList<ObservableList<Integer>> matrix = FXCollections.observableArrayList();
        for (int i = 0; i < lowerDimension; ++i) {
            matrix.set(i, agents.get(i).getTaskCost());
        }
        // массив паросочетания, индекс -- столбец, значение -- строка
        List<Integer> matching = new ArrayList<>(biggerDimension + 1);
        // потенциалы строк и столбцов
        List<Integer> rowPotential = new ArrayList<>(lowerDimension);
        List<Integer> columnPotential = new ArrayList<>(biggerDimension);
        List<Integer> way = new ArrayList<>(biggerDimension);

        Collections.fill(rowPotential, 0);
        Collections.fill(columnPotential, 0);
        Collections.fill(matching, -1);
        Collections.fill(way, -1);

        for (int currentRow = 0; currentRow < lowerDimension; ++currentRow) {
            // вспомогательные минимумы
            List<Integer> columnMinimum = new ArrayList<>(biggerDimension);
            Collections.fill(columnMinimum, Integer.MAX_VALUE);
            List<Boolean> visitedColumn = new ArrayList<>(biggerDimension);
            Collections.fill(visitedColumn, false);
            int currentColumn = biggerDimension; // фиктивный столбец (из него начинает работу алгоритм)
            do {
                visitedColumn.set(currentColumn, true);
                int foundRow = matching.get(currentColumn);
                int delta = Integer.MAX_VALUE;
                int newColumn = -1;
                for (int column = 0; column < biggerDimension; ++column) {
                    // пытаемся найти лучшее (минимальное) новое ребро из посещённых строк в непосещённые столбцы
                    if (!visitedColumn.get(column)) {
                        int newValue = matrix.get(foundRow).get(column) - rowPotential.get(column) - columnPotential.get(column);
                        if (newValue < columnMinimum.get(column)) {
                            columnMinimum.set(column, newValue);
                            way.set(column, currentColumn);
                        }
                        if (columnMinimum.get(column) < delta) {
                            delta = columnMinimum.get(column);
                            newColumn = column;
                        }
                    }
                }
                // пересчитываем потенциал
                for (int column = 0; column <= biggerDimension; ++column) {
                    if (visitedColumn.get(column)) {
                        columnPotential.set(column, columnPotential.get(column) - delta);
                        rowPotential.set(matching.get(column), rowPotential.get(matching.get(column)) - delta);
                    }
                    else {
                        columnMinimum.set(column, columnMinimum.get(column) - delta);
                    }
                }
                currentColumn = newColumn;
            } while (matching.get(currentColumn) != -1);
            // чередуем рёбра вдоль найденной увеличивающей цепи
            do {
                int previousColumn = way.get(currentColumn);
                matching.set(currentColumn, matching.get(previousColumn));
                currentColumn = previousColumn;
            } while (currentColumn != biggerDimension);
        }
        //TODO
        // взаимодействие с контроллером
        // поиск багов
        return matrix;
    }
}
