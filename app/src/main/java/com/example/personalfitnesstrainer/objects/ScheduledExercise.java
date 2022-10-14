package com.example.personalfitnesstrainer.objects;

// Represents an activity that can appear in the user's Fitness Planner or Fitness Record.
// Information stored for each activity:
    // Username/email of the profile that this activity belongs to
    // Starting time of activity
    // Ending time for activity
    // Whether the user has completed the activity
    // The name of the exercise being done for this activity (see Exercise.java for more info)

// The combination of the username and start time must be unique (a user cannot have two activities scheduled to start
// at the same time).

// The times are intended to be the number of milliseconds since 01-01-1970, which is the same time format returned by
// System.currentTimeMillis. This allows the date to be stored in one number, which logic code can convert into human-
// readable formats like DD/MM/YYYY. It also makes comparing the time of the activity to the current time very easy.

// Purpose of boolean isComplete:
// In the Fitness Record, the user can select whether they were able to complete each activity that they had planned.
// This will help the user reflect on how well their schedule worked and may be used to recommend that the user changes
// their schedule if they did not complete a certain exercise.

public class ScheduledExercise {
    private final String user;
    private long start, end;
    private boolean isComplete;
    private String exerciseName;

    public ScheduledExercise(String username, long startTime, long endTime, boolean complete, String exercise) {
        user = username;
        start = startTime;
        end = endTime;
        isComplete = complete;
        exerciseName = exercise;
    }

    public String getUser() {
        return user;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }
}
