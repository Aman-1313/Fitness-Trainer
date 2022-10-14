package com.example.personalfitnesstrainer;

import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.business.AccessProfile;
import com.example.personalfitnesstrainer.objects.FitnessGoal;

import org.junit.Test;
import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;

public class TestAccessProfile {

    @Test
    public void testLoginVerifierWithExistUser(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        String testUserPassword = "password";
        AccessProfile testProfile = new AccessProfile();
        assertTrue(testProfile.loginVerifier(testUserName , testUserPassword));
    }

    @Test
    public void testLoginVerifierWithNotExistUser(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "notExist@example.abc";
        String testUserPassword = "NotExistPassword";
        AccessProfile testProfile = new AccessProfile();
        assertFalse(testProfile.loginVerifier(testUserName , testUserPassword));
    }

    @Test
    public void testLoginVerifierWithIncorrectPassword(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        String testUserName = "sample@example.abc";
        String testUserPassword = "NotExistPassword";
        AccessProfile testProfile = new AccessProfile();
        assertFalse(testProfile.loginVerifier(testUserName , testUserPassword));
    }

    @Test
    public void testCheckUserNameFormat(){
        AccessProfile testProfile = new AccessProfile();
        String testUserName = "sample@example.abc";
        assertTrue(testProfile.checkUserNameFormat(testUserName));

        String testUserName2 = "sampleexample.abc";
        assertFalse(testProfile.checkUserNameFormat(testUserName2));

        String testUserName3 = "sample@exampleabc";
        assertFalse(testProfile.checkUserNameFormat(testUserName3));

        String testUserName4 = "sampleexampleabc";
        assertFalse(testProfile.checkUserNameFormat(testUserName4));

    }

    @Test
    public void testCheckPasswordFormat(){
        AccessProfile testProfile = new AccessProfile();
        String testPassword = "password";
        assertTrue(testProfile.checkPasswordFormat(testPassword));

        String testPassword2 = "pass word";
        assertFalse(testProfile.checkPasswordFormat(testPassword2));
    }

    @Test
    public void testSetUserEmail(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testOldUserName = "sample@example.abc";
        String testNewUserName = "new@example.abc";
        assertTrue(testProfile.setUserEmail(testOldUserName , testNewUserName));

        String testNotExistEmail = "notExist@example.abc";
        assertFalse(testProfile.setUserEmail(testNotExistEmail , testNewUserName));

        String testWrongFormatEmail = "Wrongformat.email@abc";
        assertFalse(testProfile.setUserEmail(testWrongFormatEmail , testNewUserName));
        assertFalse(testProfile.setUserEmail(testNewUserName , testWrongFormatEmail));
    }

    @Test
    public void testSetPassword(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testUserName = "sample@example.abc";
        String testPassword = "password";
        assertTrue(testProfile.setPassword(testUserName , testPassword));

        String testWrongFormatPassword = "pass word";
        assertFalse(testProfile.setPassword(testUserName , testWrongFormatPassword));

        String testWrongFormatEmail = "Wrongformat.email@abc";
        assertFalse(testProfile.setPassword(testWrongFormatEmail , testPassword));
        assertFalse(testProfile.setPassword(testWrongFormatEmail , testWrongFormatPassword));
    }

    @Test
    public void testSetHeight(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testUserName = "sample@example.abc";
        double testHeight = 190.0;
        assertTrue(testProfile.setHeight(testUserName , testHeight));

        double testIncorrectHeight = -10.0;
        assertFalse(testProfile.setHeight(testUserName , testIncorrectHeight));
    }

    @Test
    public void testGetHeight(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testUserName = "sample@example.abc";
        assertTrue(testProfile.getHeight(testUserName) == 178.0);

        String testNotExistEmail = "NotExist@email.abc";
        assertTrue(testProfile.getHeight(testNotExistEmail) == -1.0);

        String testWrongFormatEmail = "Wrongformat.email@abc";
        assertTrue(testProfile.getHeight(testWrongFormatEmail) == -1.0);
    }

    @Test
    public void testGetGoal(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testUserName = "sample@example.abc";
        assertTrue(testProfile.getGoal(testUserName).equals("Weight Loss"));

        String testNotExistEmail = "NotExist@email.abc";
        assertTrue(testProfile.getGoal(testNotExistEmail).equals(""));

        String testWrongFormatEmail = "Wrongformat.email@abc";
        assertTrue(testProfile.getGoal(testWrongFormatEmail).equals(""));
    }

    @Test
    public void testSetGoal(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testUserName = "sample@example.abc";
        FitnessGoal testFitnessGoal = new FitnessGoal("Weight Gain" , "-" , 10.0);
        assertTrue(testProfile.setGoal(testUserName , testFitnessGoal));
        assertTrue(testProfile.getGoal(testUserName).equals("Weight Gain"));

        String testNotExistEmail = "NotExist@email.abc";
        assertFalse(testProfile.setGoal(testNotExistEmail , testFitnessGoal));

        String testWrongFormatEmail = "Wrongformat.email@abc";
        assertFalse(testProfile.setGoal(testWrongFormatEmail , testFitnessGoal));
    }

    @Test
    public void testGetFirstName(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testUserName = "sample@example.abc";
        assertTrue(testProfile.getFirstName(testUserName).equals("Bob"));

        String testNotExistEmail = "NotExist@email.abc";
        assertTrue(testProfile.getFirstName(testNotExistEmail).equals(""));

        String testWrongFormatEmail = "Wrongformat.email@abc";
        assertTrue(testProfile.getFirstName(testWrongFormatEmail).equals(""));
    }

    @Test
    public void testSetFirstName(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testUserName = "sample@example.abc";
        String testFirstName = "Jeff";
        assertTrue(testProfile.setFirstName(testUserName , testFirstName));

        String testWrongFirstName = "";
        assertFalse(testProfile.setFirstName(testUserName , testWrongFirstName));

        String testNotExistEmail = "NotExist@email.abc";
        assertFalse(testProfile.setFirstName(testNotExistEmail , testFirstName));

        String testWrongFormatEmail = "Wrongformat.email@abc";
        assertFalse(testProfile.setFirstName(testWrongFormatEmail , testFirstName));
    }

    @Test
    public void testGetAvatarName(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testUserName = "sample@example.abc";
        assertTrue(testProfile.getAvatarName(testUserName).equals("avatar_1"));

        String testNotExistEmail = "NotExist@email.abc";
        assertTrue(testProfile.getAvatarName(testNotExistEmail).equals(""));

        String testWrongFormatEmail = "Wrongformat.email@abc";
        assertTrue(testProfile.getAvatarName(testWrongFormatEmail).equals(""));
    }

    @Test
    public void testSetAvatarName(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testAvatarName = "avatar_1";
        String testUserName = "sample@example.abc";
        assertTrue(testProfile.setAvatarName(testUserName,testAvatarName));

        String testNotExistEmail = "NotExist@email.abc";
        assertFalse(testProfile.setAvatarName(testNotExistEmail,testAvatarName));

        String testWrongFormatEmail = "Wrongformat.email@abc";
        assertFalse(testProfile.setAvatarName(testWrongFormatEmail,testAvatarName));
    }

    @Test
    public void testValidUser(){
        Services.resetDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        AccessProfile testProfile = new AccessProfile();
        String testUserName = "sample@example.abc";
        assertTrue(testProfile.validUser(testUserName));

        String testNotExistEmail = "NotExist@email.abc";
        assertFalse(testProfile.validUser(testNotExistEmail));

        String testWrongFormatEmail = "Wrongformat.email@abc";
        assertFalse(testProfile.validUser(testWrongFormatEmail));
    }
}//end class
