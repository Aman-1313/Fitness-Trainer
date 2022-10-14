package com.example.personalfitnesstrainer;

import org.junit.Test;
import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.business.AccessWeight;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAccessWeight {

    @Test
    public void testInsertWeightWithCorrectInfo(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testTime = Long.parseLong("1645561008050");
        double testWeight = 78.22;
        AccessWeight testAccessWeight = new AccessWeight();
        assertTrue(testAccessWeight.insertNewWeightRecord(testUserName , testTime , testWeight));
    }

    @Test
    public void testInsertWeightWithIncorrectTime(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testTime = -100;
        double testWeight = 78.22;
        AccessWeight testAccessWeight = new AccessWeight();
        assertFalse(testAccessWeight.insertNewWeightRecord(testUserName , testTime , testWeight));
    }

    @Test
    public void testInsertWeightWithIncorrectWeight(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testTime = Long.parseLong("1645561008050");
        double testWeight = -78.22;
        AccessWeight testAccessWeight = new AccessWeight();
        assertFalse(testAccessWeight.insertNewWeightRecord(testUserName , testTime , testWeight));
    }

    @Test
    public void testInsertWeightWithIncorrectEmail(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "Incorrect@example.abc";
        long testTime = Long.parseLong("1645561008050");
        double testWeight = 78.22;
        AccessWeight testAccessWeight = new AccessWeight();
        assertFalse(testAccessWeight.insertNewWeightRecord(testUserName , testTime , testWeight));
    }

    @Test
    public void testGetLatestWeight(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testTime = Long.parseLong("1645561008050");
        double testWeight = 78.22;
        AccessWeight testAccessWeight = new AccessWeight();
        testAccessWeight.insertNewWeightRecord(testUserName , testTime , testWeight);
        double checkWeight = testAccessWeight.getMostRecentWeight(testUserName);

        assertTrue(checkWeight == testWeight);
    }

    @Test
    public void testGetLatestWeightWithNotExistUser(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "NotExists@example.abc";
        double testWeight = 78.22;
        AccessWeight testAccessWeight = new AccessWeight();
        double checkWeight = testAccessWeight.getMostRecentWeight(testUserName);
        assertTrue(checkWeight == -1.0);
    }

    @Test
    public void testGetLatestTime(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        long testTime = Long.parseLong("1645561008050");
        Date timeD = new Date(testTime * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String convertedTestTime = sdf.format(timeD);
        double testWeight = 78.22;
        AccessWeight testAccessWeight = new AccessWeight();
        testAccessWeight.insertNewWeightRecord(testUserName , testTime , testWeight);
        String checkTime = testAccessWeight.getMostRecentTime(testUserName);

        assertTrue(convertedTestTime.equals(checkTime));
    }

    @Test
    public void testGetLatestTimeWithNotExistUser(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "NotExist@example.abc";
        AccessWeight testAccessWeight = new AccessWeight();
        String checkTime = testAccessWeight.getMostRecentTime(testUserName);

        assertTrue(checkTime.equals(""));
    }
}//end class
