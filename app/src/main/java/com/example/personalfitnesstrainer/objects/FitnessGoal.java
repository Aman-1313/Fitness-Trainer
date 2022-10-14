package com.example.personalfitnesstrainer.objects;

// String type: the type of goal, such as weight loss, or exercise reps (must be unique).
// String subtype: optional additional goal type. For example: if the type is "reps", the subtype could be the name of
    // the exercise the user wants to achieve a number of reps in.
// double amount: could be the weight the user wants to achieve, or the number of reps, etc.
public class FitnessGoal {
    private final String type, subtype;
    private final double amount;

    public FitnessGoal(String type, String subtype, double amount) {
        this.type = type;
        this.subtype = subtype;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public double getAmount() {
        return amount;
    }
}
