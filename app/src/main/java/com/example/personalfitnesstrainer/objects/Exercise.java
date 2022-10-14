package com.example.personalfitnesstrainer.objects;

// Stores information about each type of exercise:
    // Exercise name (String, key)
    // Category (String, ex: strength, cardio, flexibility)
    // Subcategory (String, ex: for a strength exercise, the subcategory could be the main muscle group it works out)
    // Calories burned per minute (double)
public class Exercise {
    private final String name, category, subcategory;
    private final double caloriesPerMinute;

    public Exercise(String name, String category, String subcategory, double calsPerMin) {
        this.name = name;
        this.category = category;
        this.subcategory = subcategory;
        this.caloriesPerMinute = calsPerMin;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public double getCaloriesPerMinute() {
        return caloriesPerMinute;
    }
}