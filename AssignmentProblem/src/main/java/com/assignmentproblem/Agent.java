package com.assignmentproblem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Agent {

    private ObservableList<SimpleIntegerProperty> taskCost;

    public Agent(ObservableList<SimpleIntegerProperty> tasks) {
        this.taskCost = tasks;
    }

    public Agent(int tasksNumber) {
        List<SimpleIntegerProperty> zeros = new ArrayList<>();
        for (int i = 0; i < tasksNumber; ++i) {
            zeros.add(new SimpleIntegerProperty(0));
        }
        this.taskCost = FXCollections.observableArrayList(zeros);
    }

    public ObservableList<SimpleIntegerProperty> getTaskCost() {
        return taskCost;
    }

    public void setTaskCost(ObservableList<SimpleIntegerProperty> taskCost) {
        this.taskCost = taskCost;
    }
}
