package com.example.personalfitnesstrainer.persistence.fakeDB;// fake database implementation of ProfileDatabase

import com.example.personalfitnesstrainer.objects.FitnessGoal;
import com.example.personalfitnesstrainer.objects.Profile;
import com.example.personalfitnesstrainer.persistence.ProfileDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FakeProfileDatabase implements ProfileDatabase {
    private final HashMap<String, Profile> data;

    public FakeProfileDatabase() {
        data = new HashMap<>();
    }

    // Add a new profile to the database. Logic should prevent adding one with an existing username
    @Override
    public void add(String username, String password, String firstName, double height, FitnessGoal goal, String avatarName) {
        data.put(username, new Profile(username, password, firstName, height, goal, avatarName));
    }

    // add a new profile to the database with data to create a FitnessGoal object.
    // goalSubtype can be null if not applicable
    @Override
    public void add(String username, String password, String firstName, double height, String goalType, String goalSubtype, double goalAmount, String avatarName) {
        data.put(username, new Profile(username, password, firstName, height, new FitnessGoal(goalType, goalSubtype, goalAmount), avatarName));
    }

    // get the profile with the given username/email from the database, or null if it does not exist
    @Override
    public Profile get(String username) {
        return data.get(username);
    }

    @Override
    public Profile updateUsername(String oldEmail, String newEmail) {
        Profile p = remove(oldEmail);
        if (p == null)
            return null;

        p.setUsername(newEmail);
        data.put(newEmail, p);

        return p;
    }

    @Override
    public Profile updateProfile(Profile p) {
        Profile toUpdate = data.get(p.getUsername());
        if (toUpdate != null) {
            toUpdate.setAvatarName(p.getAvatarName());
            toUpdate.setFirstName(p.getFirstName());
            toUpdate.setGoal(p.getGoal());
            toUpdate.setHeight(p.getHeight());
        }
        return toUpdate;
    }

    // remove a profile from the database, return a reference to the removed profile (or null if no profile was found)
    @Override
    public Profile remove(String username) {
        return data.remove(username);
    }

    // Get all names in the database.
    @Override
    public List<String> getUsernames() {
        return new ArrayList<>(data.keySet());
    }

    // Get all names in the database.
    @Override
    public List<Profile> getAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Profile updatePassword(String username, String password) {
        Profile p = remove(username);
        if (p == null)
            return null;

        p.setPassword(password);
        data.put(username, p);

        return p;
    }
}
