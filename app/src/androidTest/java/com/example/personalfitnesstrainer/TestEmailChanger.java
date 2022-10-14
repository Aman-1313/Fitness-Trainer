package com.example.personalfitnesstrainer;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.business.EmailChanger;
import com.example.personalfitnesstrainer.objects.ScheduledExercise;
import com.example.personalfitnesstrainer.objects.WeightRecord;
import com.example.personalfitnesstrainer.persistence.ProfileDatabase;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class TestEmailChanger {
    @Test
    public void testEmailChanger() {
        // reset data so sample data is present
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        ProfileDatabase profileDatabase = Services.getProfileDatabase();
        // get first user in the database to test with
        assertFalse(profileDatabase.getUsernames().isEmpty());
        String oldName = profileDatabase.getUsernames().get(0);

        // try changing to invalid emails (should return
        // a profile which still has the old name):

        // with no '@' or '.'
        assertEquals(oldName, EmailChanger.change(oldName, "abcdef").getUsername());
        // with a '.' but no '@'
        assertEquals(oldName, EmailChanger.change(oldName, "abc.def").getUsername());
        // with a '@' but no '.'
        assertEquals(oldName, EmailChanger.change(oldName, "abc@def").getUsername());
        // with a '.' before the '@'
        assertEquals(oldName, EmailChanger.change(oldName, "ab.cd@ef").getUsername());
        // with a '@' immediately followed by a '.'
        assertEquals(oldName, EmailChanger.change(oldName, "abc@.def").getUsername());
        // with no characters before the '@'
        assertEquals(oldName, EmailChanger.change(oldName, "@abc.def").getUsername());
        // with no characters after the '.'
        assertEquals(oldName, EmailChanger.change(oldName, "abc@def.").getUsername());

        // try changing to a pre-existing username
        profileDatabase.add("bob@aol.com", "123qwe", "Bob", 1.0, null, null);
        // should return profile with unchanged name
        assertEquals(oldName, EmailChanger.change(oldName, "bob@aol.com").getUsername());

        // try changing bob's username to something with minimal characters
        // should work
        assertEquals("b@a.c", EmailChanger.change("bob@aol.com", "b@a.c").getUsername());

        // try changing the username of a non-existing user.
        // should return null
        assertNull(EmailChanger.change("notreal@bogus.fake", "baloney@hoax.ruse"));

        // get number of weight records and activity records for user with oldName
        int numActivities = Services.getActivityDatabase().getUserActivities(oldName).size();
        int numWeights = Services.getWeightDatabase().getUserWeights(oldName).size();
        assertFalse(numActivities == 0 || numWeights == 0);
        // change to valid new username
        String newName = "abc@123.net";
        assertEquals(newName, EmailChanger.change(oldName, newName).getUsername());

        // ensure we can get the same records for the user, with updated username
        List<ScheduledExercise> activities = Services.getActivityDatabase().getUserActivities(newName);
        List<WeightRecord> weights = Services.getWeightDatabase().getUserWeights(newName);
        assertNotNull(activities);
        assertNotNull(weights);
        // make sure number of items is the same
        assertEquals(numActivities, activities.size());
        assertEquals(numWeights, weights.size());

        // make sure the names are updated
        for (ScheduledExercise u : activities) {
            assertEquals(newName, u.getUser());
        }
        for (WeightRecord w : weights) {
            assertEquals(newName, w.getUsername());
        }

        // ensure we cannot get the records with the old name anymore
        assertEquals(0, Services.getActivityDatabase().getUserActivities(oldName).size());
        assertEquals(0, Services.getWeightDatabase().getUserWeights(oldName).size());
    }
}
