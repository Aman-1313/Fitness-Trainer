package com.example.personalfitnesstrainer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.business.AccessExercise;
import com.example.personalfitnesstrainer.objects.ScheduledExercise;

import org.junit.Test;

import java.util.Calendar;
import java.util.List;

public class TestAccessExercise {

    @Test
    public void testAddExerciseWithCorrectInfo() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        String testExerciseName = "Running";
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.add(Calendar.HOUR, -1);
        Calendar testEndTime = Calendar.getInstance();
        AccessExercise testAccessExercise = new AccessExercise();

        assertTrue(testAccessExercise.addExercise(testUserName, testExerciseName, testStartTime, testEndTime));
    }

    @Test
    public void testAddExerciseWithNotExistUser() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "notExist@example.abc";
        String testExerciseName = "Running";
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.add(Calendar.HOUR, -1);
        Calendar testEndTime = Calendar.getInstance();
        AccessExercise testAccessExercise = new AccessExercise();

        assertFalse(testAccessExercise.addExercise(testUserName, testExerciseName, testStartTime, testEndTime));
    }

    @Test
    public void testAddExerciseWithIncorrectTime() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        String testExerciseName = "Running";
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.add(Calendar.HOUR, 1);
        Calendar testEndTime = Calendar.getInstance();
        AccessExercise testAccessExercise = new AccessExercise();

        assertFalse(testAccessExercise.addExercise(testUserName, testExerciseName, testStartTime, testEndTime));
    }

    @Test
    public void testAddExerciseWithIncorrectExerciseName() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        String testExerciseName = "Not Exist Exercise";
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.add(Calendar.HOUR, -1);
        Calendar testEndTime = Calendar.getInstance();
        AccessExercise testAccessExercise = new AccessExercise();

        assertFalse(testAccessExercise.addExercise(testUserName, testExerciseName, testStartTime, testEndTime));
    }

    @Test
    public void testRemoveExerciseWithCorrectInfo() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testStartTimeLong = 1649134800000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();

        assertTrue(testAccessExercise.removeExercise(testUserName, testStartTime));
    }

    @Test
    public void testRemoveExerciseWithNotExistUser() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "NotExist@example.abc";
        long testStartTimeLong = 1645844092000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();

        assertFalse(testAccessExercise.removeExercise(testUserName, testStartTime));
    }

    @Test
    public void testRemoveExerciseWithIncorrectTime() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testStartTimeLong = 1645844092880L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();

        assertFalse(testAccessExercise.removeExercise(testUserName, testStartTime));
    }

    @Test
    public void testSetExerciseHasCompletedWithCorrectInfo() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testStartTimeLong = 1649134800000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();

        assertTrue(testAccessExercise.setExerciseIsComplete(testUserName, testStartTime));
        assertTrue(testAccessExercise.isUserCompleteExercise(testUserName, testStartTime));

    }

    @Test
    public void testSetExerciseHasCompletedWithNotExistUser() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "NotExist@example.abc";
        long testStartTimeLong = 1645844092000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();

        assertFalse(testAccessExercise.setExerciseIsComplete(testUserName, testStartTime));
        assertFalse(testAccessExercise.isUserCompleteExercise(testUserName, testStartTime));

    }

    @Test
    public void testSetExerciseHasCompletedWithNotIncorrect() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "NotExist@example.abc";
        long testStartTimeLong = 1645844097700L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();

        assertFalse(testAccessExercise.setExerciseIsComplete(testUserName, testStartTime));
        assertFalse(testAccessExercise.isUserCompleteExercise(testUserName, testStartTime));
    }

    @Test
    public void testGetCertainDateExerciseWithCorrectInfo() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testStartTimeLong = 1648962000000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();
        ScheduledExercise testScheduledExercise = testAccessExercise.getCertainDateExercise(testUserName, testStartTime);
        assertTrue(testScheduledExercise != null);
        assertTrue(testScheduledExercise.getExerciseName().equals("Push Ups"));
        assertTrue(testScheduledExercise.getUser().equals(testUserName));
        assertFalse(testScheduledExercise.isComplete());
    }

    @Test
    public void testGetCertainDateExerciseWithNotExistUser() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "NotExist@example.abc";
        long testStartTimeLong = 1645844092000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();
        ScheduledExercise testScheduledExercise = testAccessExercise.getCertainDateExercise(testUserName, testStartTime);
        assertTrue(testScheduledExercise == null);
    }

    @Test
    public void testGetCertainDateExerciseWithIncorrectTime() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testStartTimeLong = 1645844097890L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();
        ScheduledExercise testScheduledExercise = testAccessExercise.getCertainDateExercise(testUserName, testStartTime);
        assertTrue(testScheduledExercise == null);
    }

    @Test
    public void testGetCertainDateRangeExercises() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testStartTimeLong = 1648962000000L;
        long testEndTimeLong = 1649275200000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        Calendar testEndTime = Calendar.getInstance();
        testEndTime.setTimeInMillis(testEndTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();
        List<ScheduledExercise> testScheduledExercise = testAccessExercise.getCertainDateRangeExercises(testUserName, testStartTime, testEndTime);
        assertTrue(testScheduledExercise != null);
        assertTrue(testScheduledExercise.size() == 3);

        assertTrue(testScheduledExercise.get(0).getExerciseName().equals("Push Ups"));
        assertTrue(testScheduledExercise.get(0).getUser().equals(testUserName));
        assertFalse(testScheduledExercise.get(0).isComplete());

        assertTrue(testScheduledExercise.get(1).getExerciseName().equals("Pull Ups"));
        assertTrue(testScheduledExercise.get(1).getUser().equals(testUserName));
        assertFalse(testScheduledExercise.get(1).isComplete());

        assertTrue(testScheduledExercise.get(2).getExerciseName().equals("Crunches"));
        assertTrue(testScheduledExercise.get(2).getUser().equals(testUserName));
        assertFalse(testScheduledExercise.get(2).isComplete());

    }

    @Test
    public void testGetCertainDateRangeExercisesWithNotExistUser() {
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "notExist@example.abc";
        long testStartTimeLong = 1645844091999L;
        long testEndTimeLong = 1645847692000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        Calendar testEndTime = Calendar.getInstance();
        testEndTime.setTimeInMillis(testEndTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();
        List<ScheduledExercise> testScheduledExercise = testAccessExercise.getCertainDateRangeExercises(testUserName, testStartTime, testEndTime);
        assertTrue(testScheduledExercise.size() == 0);

    }


    @Test
    public void testAutoAssigningOneDayExercisesWithCorrectInfo(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testStartTimeLong = 1695844092000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();
        assertTrue(testAccessExercise.assignExerciseOnDay(testUserName,testStartTime).equals("Running"));
        assertTrue(testAccessExercise.getCertainDateExercise(testUserName,testStartTime) != null);
    }

    @Test
    public void testAutoAssigningOneDayExercisesWithNotExistUser(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "notExist@example.abc";
        long testStartTimeLong = 1695844092000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();
        assertTrue(testAccessExercise.assignExerciseOnDay(testUserName,testStartTime) == null);
    }


    @Test
    public void testAutoAssigningDateRangeExercisesWithCorrectInfo(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testStartTimeLong = 1695844092000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        Calendar testEndTime = (Calendar) testStartTime.clone();
        testEndTime.add(Calendar.DATE , 7);
        AccessExercise testAccessExercise = new AccessExercise();
        assertTrue(testAccessExercise.assignExercisesInRange(testUserName,testStartTime , 7));
        List<ScheduledExercise> testScheduledExercise = testAccessExercise.getCertainDateRangeExercises(testUserName,testStartTime,testEndTime);
        assertTrue(testScheduledExercise != null);
        assertTrue(testScheduledExercise.size() == 7);
    }

    @Test
    public void testAutoAssigningDateRangeExercisesWithNotExistUser(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "NotExist@example.abc";
        long testStartTimeLong = 1695844092000L;
        Calendar testStartTime = Calendar.getInstance();
        testStartTime.setTimeInMillis(testStartTimeLong);
        AccessExercise testAccessExercise = new AccessExercise();
        assertFalse(testAccessExercise.assignExercisesInRange(testUserName,testStartTime , 7));
    }

    @Test
    public void testIsUserAvailableWithOccupied(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testTimeToCheckLong = 1648962000000L;
        long testTimeToCheckLong2 = 1649048400000L;
        long testTimeToCheckLong3 = 1649134800000L;
        Calendar testTimeToCheck = Calendar.getInstance();
        testTimeToCheck.setTimeInMillis(testTimeToCheckLong);
        Calendar testTimeToCheck2 = Calendar.getInstance();
        testTimeToCheck2.setTimeInMillis(testTimeToCheckLong2);
        Calendar testTimeToCheck3 = Calendar.getInstance();
        testTimeToCheck3.setTimeInMillis(testTimeToCheckLong3);
        AccessExercise testAccessExercise = new AccessExercise();

        assertFalse(testAccessExercise.isUserAvailable(testUserName, testTimeToCheck));
        assertFalse(testAccessExercise.isUserAvailable(testUserName, testTimeToCheck2));
        assertFalse(testAccessExercise.isUserAvailable(testUserName, testTimeToCheck3));
    }

    @Test
    public void testIsUserAvailableWithUnoccupiedTime(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testTimeToCheckLong = 1648062000700L;
        long testTimeToCheckLong2 = 1649248400800L;
        long testTimeToCheckLong3 = 1649234800800L;
        Calendar testTimeToCheck = Calendar.getInstance();
        testTimeToCheck.setTimeInMillis(testTimeToCheckLong);
        Calendar testTimeToCheck2 = Calendar.getInstance();
        testTimeToCheck2.setTimeInMillis(testTimeToCheckLong2);
        Calendar testTimeToCheck3 = Calendar.getInstance();
        testTimeToCheck3.setTimeInMillis(testTimeToCheckLong3);
        AccessExercise testAccessExercise = new AccessExercise();

        assertTrue(testAccessExercise.isUserAvailable(testUserName, testTimeToCheck));
        assertTrue(testAccessExercise.isUserAvailable(testUserName, testTimeToCheck2));
        assertTrue(testAccessExercise.isUserAvailable(testUserName, testTimeToCheck3));
    }

    @Test
    public void testIsUserAvailableWithInvalidUserName(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "NotExist@example.abc";
        long testTimeToCheckLong = 1648922000080L;
        long testTimeToCheckLong2 = 1649048400000L;
        long testTimeToCheckLong3 = 1649234800800L;
        Calendar testTimeToCheck = Calendar.getInstance();
        testTimeToCheck.setTimeInMillis(testTimeToCheckLong);
        Calendar testTimeToCheck2 = Calendar.getInstance();
        testTimeToCheck2.setTimeInMillis(testTimeToCheckLong2);
        Calendar testTimeToCheck3 = Calendar.getInstance();
        testTimeToCheck3.setTimeInMillis(testTimeToCheckLong3);
        AccessExercise testAccessExercise = new AccessExercise();

        assertFalse(testAccessExercise.isUserAvailable(testUserName, testTimeToCheck));
        assertFalse(testAccessExercise.isUserAvailable(testUserName, testTimeToCheck2));
        assertFalse(testAccessExercise.isUserAvailable(testUserName, testTimeToCheck3));
    }
}
