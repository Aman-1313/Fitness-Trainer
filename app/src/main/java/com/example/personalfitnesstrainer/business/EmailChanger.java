package com.example.personalfitnesstrainer.business;

import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.objects.Profile;
import com.example.personalfitnesstrainer.objects.ScheduledExercise;
import com.example.personalfitnesstrainer.objects.WeightRecord;
import com.example.personalfitnesstrainer.persistence.ActivityDatabase;
import com.example.personalfitnesstrainer.persistence.WeightDatabase;

import java.util.List;

public class EmailChanger {
    // Changes every occurrence in the database of an old email to a new email.
    // Returns a reference to the Profile with the updated name.
    // Note for the real database implementation, some of this code is redundant due to the use
    // of ON UPDATE CASCADE. However, it will still run and won't negatively affect anything.
    public static Profile change(String oldEmail, String newEmail) {
        // do not allow changing to an existing username
        // do not allow changing to an invalid email
        if (Services.getProfileDatabase().getUsernames().contains(newEmail)
            || !validName(newEmail))
            return Services.getProfileDatabase().get(oldEmail);

        // change username if ProfileDatabase contains a user with the old email
        Profile user = Services.getProfileDatabase().updateUsername(oldEmail, newEmail);
        // if no user found, return null
        if (user == null)
            return null;

        // change all entries in ActivityDatabase
        ActivityDatabase activityDatabase = Services.getActivityDatabase();
        List<ScheduledExercise> activities = activityDatabase.getUserActivities(oldEmail);

        for (ScheduledExercise a : activities) {
            activityDatabase.remove(oldEmail, a.getStart());
            activityDatabase.add(newEmail, a.getStart(), a.getEnd(), a.isComplete(), a.getExerciseName());
        }

        // change all entries in WeightDatabase
        WeightDatabase weightDatabase = Services.getWeightDatabase();
        List<WeightRecord> weights = weightDatabase.getUserWeights(oldEmail);

        for (WeightRecord w : weights) {
            weightDatabase.remove(oldEmail, w.getTime());
            weightDatabase.add(newEmail, w.getTime(), w.getWeight());
        }

        return user;
    }

    private static boolean validName(String s) {
        // make sure there's an @ symbol in the email address
        int i = s.indexOf('@');
        // there must be at least 1 char before the '@'
        // there must be a '.' at least 2 chars after the '@'
        // there must be at least 1 char after the '.'
        return i > 0 && (s.indexOf('.', i + 2) > 0) && (s.indexOf('.', i + 2) < s.length() - 1);
    }
}
