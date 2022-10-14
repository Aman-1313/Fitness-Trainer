package com.example.personalfitnesstrainer.persistence;

import com.example.personalfitnesstrainer.objects.ScheduledExercise;

import java.util.List;

// Stores the activities in each user's Fitness Planner and Fitness Record. This database only stores the start and end
// time of each activity, and logic code should handle whether an exercise appears in the Planner (ex: if the start time
// is in the future) or Record (ex: if the start time is in the past).

// Information stored for each activity (see also ScheduledExercise.java):
    // Username/email (String, part of composite key)
    // Activity start time (long, part of composite key)
    // Activity end time (long)
    // Did the user complete the activity (boolean)
    // Exercise name (see Exercise.java and ExerciseDatabase.java for more info)
// Note: the exercise name can be used to get the rest of the information about the exercise from ExerciseDatabase
public interface ActivityDatabase {

    // add an activity to the database
    void add(String user, long start, long end, boolean isComplete, String exerciseName);

    // get an activity from the database (returns null for invalid keys)
    ScheduledExercise get(String user, long start);

    // remove an activity from the database, return a reference to it (or null if none was found)
    ScheduledExercise remove(String user, long start);

    // change the completion status of a ScheduledExercise in the database
    // return a reference to the affected ScheduledExercise, or null if none exists
    ScheduledExercise changeIsComplete(String user, long start, boolean isComplete);

    // Get a list of all activities for the given user, sorted from oldest to most recent. Returns an empty list if user
    // has no activities in the database.
    List<ScheduledExercise> getUserActivities(String user);

    // Get a list of all activities for the given user whose start times are within the given range, sorted from oldest
    // to most recent. Returns an empty list if user has no activities in the database.
    // To include only a lower bound, set latest to Long.MAX_VALUE. To include only an upper bound, set earliest <= 0.
    List<ScheduledExercise> getUserActivities(String user, long earliest, long latest);
}
