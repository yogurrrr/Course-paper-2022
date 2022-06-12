package com.assignmentproblem;

import java.util.ArrayList;
import java.util.Collections;

public class Agent {

    private Character id;

    public Character getId() {
        return id;
    }

    public void setId(Character id) {
        this.id = id;
    }

    private final ArrayList<Integer> taskCost;

    public Agent(Character id, int tasksNumber) {
        this.taskCost = new ArrayList<>(Collections.nCopies(tasksNumber, 0));
        this.id = id;
    }

    public ArrayList<Integer> getTaskCost() {
        return taskCost;
    }

    public void setTaskCostByIndex(int index, Integer newValue) {
        taskCost.set(index, newValue);
    }
}
