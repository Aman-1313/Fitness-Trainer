package com.example.personalfitnesstrainer.objects;

// Stores a weight recorded by a user, that user's username/email, and the time of recording.
    // email/username (String, part of composite key)
    // timestamp (long, part of composite key)
    // weight (double)
// The timestamp is intended to be the value returned by System.currentTimeMillis (number of milliseconds
// since 01-01-1970) to give a unique timestamp for each weight recorded by a particular user.
// The weight will be measured in kilograms.
public class WeightRecord {
    private final String username;
    private final long time;
    private final double weight;

    public WeightRecord(String username, long time, double weight) {
        this.username = username;
        this.time = time;
        this.weight = weight;
    }

    public String getUsername() {
        return username;
    }

    public long getTime() {
        return time;
    }

    public double getWeight() {
        return weight;
    }
}
