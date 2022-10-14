package com.example.personalfitnesstrainer.persistence;

import com.example.personalfitnesstrainer.objects.WeightRecord;

import java.util.List;

// Stores all weights recorded by each user (see also WeightRecord.java):
    // email/username (String, part of composite key)
    // timestamp (long, part of composite key)
    // weight (double)
// The timestamp is intended to be the value returned by System.currentTimeMillis (number of milliseconds
// since 01-01-1970) to give a unique timestamp for each weight recorded by a particular user.
// The weight will be measured in kilograms.
public interface WeightDatabase {

    // record a weight for a user
    void add(String user, long time, double weight);
    // get a WeightRecord for a given user at a given time (returns null if key is invalid)
    WeightRecord getWeight(String user, long time);
    // remove a weight record for a given user at a given time (returns null if key is invalid)
    WeightRecord remove(String user, long time);
    // get all weights for a given user, sorted oldest to newest (returns an empty List if no records for given user)
    List<WeightRecord> getUserWeights(String user);
    // get most recent weight for a given user (returns null if no records for given user)
    WeightRecord getMostRecent(String user);
}
