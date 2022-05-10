package com.assignmentproblem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Agent {

    private ObservableList<Integer> taskCost;

    public Agent(ObservableList<Integer> tasks) {
        this.taskCost = tasks;
    }

    public Agent(int tasksNumber) {
        List<Integer> zeros = new ArrayList<>();
        for (int i = 0; i < tasksNumber; ++i) {
            zeros.add(0);
        }
        this.taskCost = FXCollections.observableArrayList(zeros);
    }

    public ObservableList<Integer> getTaskCost() {
        return taskCost;
    }

    public void setTaskCost(ObservableList<Integer> taskCost) {
        this.taskCost = taskCost;
    }

    public void setTaskCostByIndex(int index, Integer newValue) {
        taskCost.set(index, newValue);
    }
}
