package com.example.personalfitnesstrainer.persistence;

import com.example.personalfitnesstrainer.objects.FitnessGoal;
import com.example.personalfitnesstrainer.objects.Profile;

import java.util.List;

// stores the following information for each user (see also Profile.java):
    // email/username (key, String)
    // password (String)
    // first name (String)
    // height (double)
    // fitness goal (FitnessGoal, see GoalDatabase and FitnessGoal for more info)
    // avatar name (String, rest of data for avatar is in AvatarDatabase)
// Note: the user's weights are stored in the WeightDatabase, where the most recent one can be retrieved to get the
// user's current weight (see WeightDatabase)
public interface ProfileDatabase {

    // add a new profile to the database with existing FitnessGoal object
    void add(String username, String password, String firstName, double height, FitnessGoal goal, String avatarName);
    // add a new profile to the database with data to create a FitnessGoal object.
        // goalSubtype can be null if not applicable
    void add(String username, String password, String firstName, double height, String goalType, String goalSubtype, double goalAmount, String avatarName);
    // get the profile with the given username/email from the database, or null if it does not exist
    Profile get(String username);
    // change the username of a profile in the database
    Profile updateUsername(String oldEmail, String newEmail);
    // given a profile object, update the corresponding profile's information in the database
    Profile updateProfile(Profile p);
    // remove a profile from the database, return a reference to the removed profile (or null if no profile was found)
    Profile remove(String username);
    // get a list of the username/email (key) for every profile in the database
    List<String> getUsernames();
    // return a list of every profile in the database
    List<Profile> getAll();
    // change the password
    Profile updatePassword(String username, String password);
}
