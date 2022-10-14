package com.example.personalfitnesstrainer.persistence.fakeDB;

import com.example.personalfitnesstrainer.objects.Exercise;
import com.example.personalfitnesstrainer.persistence.ExerciseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// fake database implementation of ExerciseDatabase

public class FakeExerciseDatabase implements ExerciseDatabase {

    private final HashMap<String, Exercise> data;

    public FakeExerciseDatabase() {
        data = new HashMap<>();
    }

    // Add an exercise to the database.
    @Override
    public void add(String name, String category, String subcategory, double calsPerMin) {
        data.put(name, new Exercise(name, category, subcategory, calsPerMin));
    }

    // Get the exercise with the given name (returns null for invalid names).
    @Override
    public Exercise get(String name) {
        return data.get(name);
    }

    // Get the list of every exercise in the database.
    @Override
    public List<Exercise> getAll() {
        return new ArrayList<>(data.values());
    }

    // Get the names of all exercise categories.
    @Override
    public List<String> getCategories() {
        List<String> result = new ArrayList<>();

        for (Exercise e : data.values()) {
            if (!result.contains(e.getCategory()))
                result.add(e.getCategory());
        }

        return result;
    }

    // Get all exercises in a given category.
    @Override
    public List<Exercise> getInCategory(String category) {
        ArrayList<Exercise> result = new ArrayList<>();

        for (Exercise e : data.values()) {
            if (e.getCategory().equals(category))
                result.add(e);
        }

        return result;
    }

    // Get the names of all subcategories.
    @Override
    public List<String> getSubcategories() {
        List<String> result = new ArrayList<>();

        for (Exercise e : data.values()) {
            if (!result.contains(e.getSubcategory()))
                result.add(e.getSubcategory());
        }

        return result;
    }

    // Get the names of all subcategories in a given category.
    @Override
    public List<String> getSubcategories(String category) {
        List<String> result = new ArrayList<>();

        for (Exercise e : data.values()) {
            if (e.getCategory().equals(category) && !result.contains(e.getSubcategory()))
                result.add(e.getSubcategory());
        }

        return result;
    }

    // Get all exercises in a given subcategory.
    @Override
    public List<Exercise> getInSubcategory(String subcategory) {
        ArrayList<Exercise> result = new ArrayList<>();

        for (Exercise e : data.values()) {
            if (e.getSubcategory().equals(subcategory))
                result.add(e);
        }

        return result;
    }
}
