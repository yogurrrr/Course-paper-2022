package com.assignmentproblem;

import java.util.ArrayList;

public class DataMatrix {
    private static ArrayList<Agent> matrix;

    public static void makeNewMatrix() {
        matrix = new ArrayList<>();
    }

    public static ArrayList<Agent> getMatrix() {
        return matrix;
    }

    public static void setVoidMatrix(int agentsNumber, int tasksNumber) {
        for (int i = 0; i < agentsNumber; ++i) {
            matrix.add(new Agent(tasksNumber));
        }
    }

    public static void setNewTaskCost(int agentIndex, int taskIndex, int value) {
        matrix.get(agentIndex).getTaskCost().set(taskIndex, value);
    }

    public static void clear() {
        matrix.clear();
    }
}
