package com.assignmentproblem;

import java.util.Random;

public class InputDataGenerator {
    public static void generate(int agentsNumber, int tasksNumber) {
        Random rand = new Random();
        int upperBound = 30;
        int lowerBound = 1;
        for (int agentIndex = 0; agentIndex < agentsNumber; ++agentIndex) {
            for (int taskIndex = 0; taskIndex < tasksNumber; ++taskIndex) {
                DataMatrix.setNewTaskCost(agentIndex, taskIndex, rand.nextInt(upperBound - lowerBound) + lowerBound);
            }
        }
    }
}
