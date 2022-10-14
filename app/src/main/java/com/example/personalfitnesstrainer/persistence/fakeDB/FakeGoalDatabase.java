package com.example.personalfitnesstrainer.persistence.fakeDB;// fake database implementation of GoalDatabase

import com.example.personalfitnesstrainer.persistence.GoalDatabase;

import java.util.ArrayList;
import java.util.List;

public class FakeGoalDatabase implements GoalDatabase {

    private final ArrayList<String> goalTypes;

    public FakeGoalDatabase() {
        goalTypes = new ArrayList<>();
    }

    @Override
    public void add(String goalType) {
        if (!goalTypes.contains(goalType))
            goalTypes.add(goalType);
    }

    @Override
    public List<String> getAll() {
        return new ArrayList<>(goalTypes);
    }
}
