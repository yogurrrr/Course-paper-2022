package com.assignmentproblem;


import java.util.ArrayList;
import java.util.Collections;

public class Agent {

    private ArrayList<Integer> taskCost;

    public Agent(ArrayList<Integer> tasks) {
        this.taskCost = tasks;
    }

    public Agent(int tasksNumber) {
        this.taskCost = new ArrayList<>(Collections.nCopies(tasksNumber, 0));
    }

    public ArrayList<Integer> getTaskCost() {
        return taskCost;
    }

    public void setTaskCost(ArrayList<Integer> taskCost) {
        this.taskCost = taskCost;
    }

    public void setTaskCostByIndex(int index, Integer newValue) {
        taskCost.set(index, newValue);
    }
}
