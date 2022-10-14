package com.example.personalfitnesstrainer.persistence;

import java.util.List;

// Stores a list of strings, each representing a unique type of fitness goal the user can choose from.

// When a user chooses a fitness goal, logic code will be responsible for constructing a new FitnessGoal record with the
// correct type from this database as well as the desired amount (ex: if the goal type is "weight", the FitnessGoal will
// contain that type as well as the actual weight the user wants to achieve). Each user's FitnessGoal is stored in their
// profile (see ProfileDatabase and Profile).
public interface GoalDatabase {

    // add a new type of goal to the database
    void add(String goalType);

    // return a list of all goal types
    List<String> getAll();
}
