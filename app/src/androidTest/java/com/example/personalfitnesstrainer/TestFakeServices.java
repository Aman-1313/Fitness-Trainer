package com.example.personalfitnesstrainer;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.objects.Profile;
import com.example.personalfitnesstrainer.objects.WeightRecord;
import com.example.personalfitnesstrainer.persistence.ActivityDatabase;
import com.example.personalfitnesstrainer.persistence.ExerciseDatabase;
import com.example.personalfitnesstrainer.persistence.GoalDatabase;
import com.example.personalfitnesstrainer.persistence.ProfileDatabase;
import com.example.personalfitnesstrainer.persistence.WeightDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.List;

// tests for Services.java, using fake database implementations
public class TestFakeServices {
    private static final String SAMPLE_USER = "sample@example.abc";

    @Test
    public void testFakeExerciseDatabase() {
        Services.initFake(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertNotNull(Services.getExerciseDatabase());
        ExerciseDatabase db = Services.getExerciseDatabase();

        // check name and calories per minute for some exercises
        assertEquals(8.8184904, db.get("Push Ups").getCaloriesPerMinute(), 0.01);
        assertEquals(8.8184904, db.get("Pull Ups").getCaloriesPerMinute(), 0.01);
        assertEquals(19.6666667, db.get("Running").getCaloriesPerMinute(), 0.01);
        assertEquals(2.2046226, db.get("Crunches").getCaloriesPerMinute(), 0.01);

        // check size of getAll list
        assertEquals(4, db.getAll().size());
        // check categories
        assertTrue(db.getCategories().contains("Anaerobic"));
        assertTrue(db.getCategories().contains("Cardio"));
        // check results of getting anaerobic exercises
        assertEquals(3, db.getInCategory("Anaerobic").size());
        // check list of all subcategories
        assertEquals(3, db.getSubcategories().size());
        // check list of subcategories in a category
        assertEquals(2, db.getSubcategories("Anaerobic").size());
        // check list of exercises in a subcategory
        assertEquals(2, db.getInSubcategory("Upper Body").size());
    }

    @Test
    public void testFakeGoalDatabase() {
        Services.initFake(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertNotNull(Services.getGoalDatabase());
        GoalDatabase db = Services.getGoalDatabase();

        // check goals
        assertTrue(db.getAll().contains("Weight Loss"));
        assertTrue(db.getAll().contains("Weight Gain"));
        assertTrue(db.getAll().contains("Exercise Reps"));
    }

    @Test
    public void testFakeProfileDatabase() {
        Services.initFake(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertNotNull(Services.getProfileDatabase());
        ProfileDatabase db = Services.getProfileDatabase();

        // get user from sample data
        assertNotNull(db.get(SAMPLE_USER));
        // get invalid user
        assertNull(db.get("aaaaaa"));
        // get list of usernames
        assertEquals(1, db.getUsernames().size());
        assertTrue(db.getUsernames().contains(SAMPLE_USER));
        // get all users
        assertEquals(1, db.getAll().size());
        // try removing a user
        Profile p = db.remove(SAMPLE_USER);
        // make sure we can't retrieve the user any more
        assertNull(db.get(p.getUsername()));
        // make sure there are no users
        assertEquals(0, db.getUsernames().size());
        assertEquals(0, db.getAll().size());

        // changing the username is tested when we test EmailChanger.java
    }

    @Test
    public void testFakeWeightDatabase() {
        Services.initFake(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertNotNull(Services.getWeightDatabase());
        WeightDatabase db = Services.getWeightDatabase();

        // try getting a weight
        assertNotNull(db.getWeight(SAMPLE_USER, 1645400916000L));
        // check the value of a weight
        assertEquals(78.9, db.getWeight(SAMPLE_USER, 1645561008000L).getWeight(), 0.01);
        // get list of all weights for user
        List<WeightRecord> allWeights = db.getUserWeights(SAMPLE_USER);
        assertNotNull(allWeights);
        assertEquals(4, allWeights.size());
        // ensure they are sorted correctly, by time
        long prevTime = allWeights.get(0).getTime();
        for (int i = 1; i < 4; i++) {
            assertTrue(allWeights.get(i).getTime() > prevTime);
            prevTime = allWeights.get(i).getTime();
        }
        // get most recent weight, make sure it has the last time
        assertEquals(prevTime, db.getMostRecent(SAMPLE_USER).getTime());
    }
}
