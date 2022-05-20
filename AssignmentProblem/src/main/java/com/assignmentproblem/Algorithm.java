package com.assignmentproblem;


import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class Algorithm {
    public static ArrayList<Integer> solveProblem(ArrayList<Agent> agents) {
        int agentsNumber = agents.size();
        int tasksNumber = agents.get(0).getTaskCost().size();
        // массив паросочетания, индекс -- столбец, значение -- строка
        ArrayList<Integer> matching = new ArrayList<>(Collections.nCopies(tasksNumber + 1, -1));
        // потенциалы строк и столбцов
        ArrayList<Integer> rowPotential = new ArrayList<>(Collections.nCopies(agentsNumber, 0));
        ArrayList<Integer> columnPotential = new ArrayList<>(Collections.nCopies(tasksNumber + 1, 0));
        // список для восстановления пути, индекс -- столбец, значение -- предшествующий столбец
        ArrayList<Integer> way = new ArrayList<>(Collections.nCopies(tasksNumber, -1));

        for (int currentRow = 0; currentRow < agentsNumber; ++currentRow) {
            // вспомогательные минимумы
            ArrayList<Integer> columnMinimum = new ArrayList<>(Collections.nCopies(tasksNumber, Integer.MAX_VALUE));
            ArrayList<Boolean> visitedColumn = new ArrayList<>(Collections.nCopies(tasksNumber + 1, false));
            matching.set(tasksNumber, currentRow);
            int currentColumn = tasksNumber; // фиктивный столбец (из него начинает работу алгоритм)
            do {
                visitedColumn.set(currentColumn, true);
                int foundRow = matching.get(currentColumn);
                int delta = Integer.MAX_VALUE;
                int newColumn = -1;
                for (int column = 0; column < tasksNumber; ++column) {
                    // пытаемся найти лучшее (минимальное) новое ребро из посещённой строки в непосещённые столбцы
                    if (!visitedColumn.get(column)) {
                        int newColumnMinimumValue = DataMatrix.getMatrix().get(foundRow).getTaskCost().get(column) -
                                rowPotential.get(foundRow) - columnPotential.get(column);
                        if (newColumnMinimumValue < columnMinimum.get(column)) {
                            columnMinimum.set(column, newColumnMinimumValue);
                            way.set(column, currentColumn);
                        }
                        // delta -- наименьшее знвчение изменения потенциала, при котором появится новое жёсткое ребро
                        if (columnMinimum.get(column) < delta) {
                            delta = columnMinimum.get(column);
                            newColumn = column;
                        }
                    }
                }
                // пересчитываем потенциал и вспомогательные минимумы
                for (int column = 0; column <= tasksNumber; ++column) {
                    // если колонка была посещена обходом
                    if (visitedColumn.get(column)) {
                        // пересчитываем потенциал для этой колонки и соответсвующей строки в паросочетании
                        columnPotential.set(column, columnPotential.get(column) - delta);
                        rowPotential.set(matching.get(column), rowPotential.get(matching.get(column)) + delta);
                    } else {
                        // иначе уменьшаем вспомогательный минимум на delta
                        columnMinimum.set(column, columnMinimum.get(column) - delta);
                    }
                }
                // новая колонка -- та, в которой вспомогательный минимум равен delta
                currentColumn = newColumn;
            } while (matching.get(currentColumn) != -1);
            // чередуем рёбра вдоль найденной увеличивающей цепи
            do {
                int previousColumn = way.get(currentColumn);
                matching.set(currentColumn, matching.get(previousColumn));
                currentColumn = previousColumn;
            } while (currentColumn != tasksNumber);
        }
        ArrayList<Integer> output = new ArrayList<>(matching);
        output.add(-columnPotential.get(tasksNumber));
        return output;
    }
}
