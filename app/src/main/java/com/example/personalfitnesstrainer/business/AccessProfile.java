package com.example.personalfitnesstrainer.business;

import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.objects.FitnessGoal;
import com.example.personalfitnesstrainer.objects.Profile;
import com.example.personalfitnesstrainer.business.EmailChanger;
import com.example.personalfitnesstrainer.persistence.ProfileDatabase;

public class AccessProfile {

    private ProfileDatabase profiledatabase;

    public AccessProfile() {
        profiledatabase= Services.getProfileDatabase();
    }//end constructor

    public AccessProfile(final ProfileDatabase profiledatabase){
        this.profiledatabase = profiledatabase;
    }
    //return true if the username and password is correct
    public boolean loginVerifier(String userNameInput, String passwordInput) {
        boolean result = false;
        Profile currentUser = null;
        if(profiledatabase.get(userNameInput) != null){
            currentUser = profiledatabase.get(userNameInput);
        }
        if (currentUser != null && currentUser.checkPassword(passwordInput)) {
            result = true;
        }
        return result;
    }//end method

    //return true if user exists
    public boolean validUser(String userName){
        boolean result = false;
        if(profiledatabase.get(userName) != null){
            result = true;
        }
        return result;
    }

    //checking if the format of the new email is correct
    //return true if it is correct
    public boolean checkUserNameFormat(String userNameInput) {
        int position0= 0;
        int position1= 0;
        boolean test0 = false;
        boolean test1 = false;
        boolean test2 = false;
        for(int i=0; i<userNameInput.length(); i++){
            if(userNameInput.charAt(i) == '@'){
                position0 = i;
                test0 = true;
            }
            else if(userNameInput.charAt(i) == '.') {
                position1 = i;
                test1 = true;
            }
        }//end for
        if( test0 && test1 && position0 < position1){
             test2 = true;
        }//end if
        return test2;
    }//end method


    //checking if the user input password correct or not
    //if password is empty or contain space bar should be incorrect
    public boolean checkPasswordFormat(String userInputPassword){
        boolean result = false;
        if(userInputPassword.length() > 0 && !userInputPassword.contains(" ")){
            result = true;
        }
        return result;
    }


    //return true if the email has being correctly set. otherwise return false
    public boolean setUserEmail(String oldEmail , String newEmail){
        boolean result = false;
        Profile currentUser = null;
        if(profiledatabase.get(oldEmail) != null && checkUserNameFormat(newEmail)){
            currentUser = profiledatabase.get(oldEmail);
            EmailChanger.change(currentUser.getUsername() , newEmail);
            result = true;
        }
        return result;
    }

    //setting the user password
    //return true if it implemented successfully
    public boolean setPassword(String userName , String newPassword){
        boolean result = false;
        //Profile currentUser = null;
        if(profiledatabase.get(userName) != null && checkPasswordFormat(newPassword)){
            //currentUser = profiledatabase.get(userName);
            //currentUser.setPassword(newPassword);
            profiledatabase.updatePassword(userName,newPassword);
            result = true;
        }
        return result;
    }

    //setting user height
    //return true if it implemented successfully
    //return false if user provide a invalid height(e.g. -10cm)
    public boolean setHeight(String userName , double newHeight){
        boolean result = false;
        Profile currentUser = null;
        if(profiledatabase.get(userName) != null && newHeight > 0){
            currentUser = profiledatabase.get(userName);
            currentUser.setHeight(newHeight);
            profiledatabase.updateProfile(currentUser);
            result = true;
        }
        return result;
    }

    //return -1 if user does not exist or database not initialized yet
    public double getHeight(String userName){
        double result = -1;
        Profile currentUser = null;
        if(profiledatabase.get(userName) != null){
            currentUser = profiledatabase.get(userName);
            result=currentUser.getHeight();
        }
        return result;
    }

    //return user goal in string format
    //return empty string if user does not exist or database not initialized yet
    public String getGoal(String userName){
        String result = "";
        Profile currentUser = null;
        if(profiledatabase.get(userName) != null){
            currentUser = profiledatabase.get(userName);
            result = currentUser.getGoal().getType();
        }
        return result;
    }

    //return true if the new user goal has set successfully
    public boolean setGoal(String userName , FitnessGoal newGoal){
        boolean result = false;
        Profile currentUser = null;
        if(profiledatabase.get(userName) != null){
            currentUser = profiledatabase.get(userName);
            currentUser.setGoal(newGoal);
            profiledatabase.updateProfile(currentUser);
            result=true;
        }
        return result;
    }

    //return user firstName in string format
    //return empty string if user does not exist or database not initialized yet
    public String getFirstName(String userName){
        String result = "";
        Profile currentUser = null;
        if(profiledatabase.get(userName) != null){
            currentUser = profiledatabase.get(userName);
            result = currentUser.getFirstName();
        }
        return result;
    }

    //return true if the new user firstName has set successfully
    public boolean setFirstName(String userName , String newFirstName){
        boolean result = false;
        Profile currentUser = null;
        if(profiledatabase.get(userName) != null && newFirstName.length() > 0){
            currentUser = profiledatabase.get(userName);
            currentUser.setFirstName(newFirstName);
            profiledatabase.updateProfile(currentUser);
            result = true;
        }

        return result;
    }

    //return the avatar name in string
    //return empty string if user not exist or database not initialized yet
    public String getAvatarName(String userName){
        String result = "";
        Profile currentUser = null;
        if(profiledatabase.get(userName) != null){
            currentUser = profiledatabase.get(userName);
            result = currentUser.getAvatarName();
        }
        return result;
    }

    //return true if avatar has updated successfully
    public boolean setAvatarName(String userName , String newAvatarName){
        boolean result = false;
        Profile currentUser = null;
        if(profiledatabase.get(userName) != null){
            currentUser = profiledatabase.get(userName);
            currentUser.setAvatarName(newAvatarName);
            profiledatabase.updateProfile(currentUser);
            result = true;
        }
        return result;
    }
}//end class

