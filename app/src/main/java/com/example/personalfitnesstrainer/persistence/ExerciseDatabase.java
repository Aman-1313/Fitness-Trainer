package com.example.personalfitnesstrainer.persistence;

import com.example.personalfitnesstrainer.objects.Exercise;

import java.util.List;

// Stores the types of exercise that a user can choose from to add to their fitness schedule.
// Entries should be built when the app is launched. For the database that stores the activities in each user's Fitness
// Planner and Fitness Record, see ActivityDatabase.

// Information stored (see also Exercise.java):
    // Exercise name (String, key)
    // Category (String, ex: strength, cardio, flexibility)
    // Subcategory (String, ex: for a strength exercise, the subcategory could be the main muscle group it works out)
    // Calories burned per minute (double)
public interface ExerciseDatabase {

    // Add an exercise to the database.
    void add(String name, String category, String subcategory, double calsPerMin);

    // Get the exercise with the given name (returns null for invalid names).
    Exercise get(String name);

    // Get the list of every exercise in the database.
    List<Exercise> getAll();

    // Get the names of all exercise categories.
    List<String> getCategories();

    // Get all exercises in a given category.
    List<Exercise> getInCategory(String category);

    // Get the names of all subcategories.
    List<String> getSubcategories();

    // Get the names of all subcategories in a given category.
    List<String> getSubcategories(String category);

    // Get all exercises in a given subcategory.
    List<Exercise> getInSubcategory(String subcategory);
}
