package com.example.personalfitnesstrainer.business;

import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.objects.WeightRecord;
import com.example.personalfitnesstrainer.persistence.WeightDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AccessWeight{
    private WeightDatabase weightdatabase;
    private AccessProfile profiles;

    public AccessWeight(){
        weightdatabase = Services.getWeightDatabase();
        profiles = new AccessProfile();
    }//end constructor

    public AccessWeight(final WeightDatabase weightdatabase){
        this.weightdatabase = weightdatabase;
        profiles = new AccessProfile();
    }

    //return time in string format if user exists
    //return empty string if user does not exists
    public String getMostRecentTime(String userName){
        String result = "";
        long latestTime = -1;
        WeightRecord userRecord;
        if(profiles.validUser(userName) && weightdatabase.getMostRecent(userName) != null){
            latestTime = weightdatabase.getMostRecent(userName).getTime();
            Date timeD = new Date(latestTime * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String convertedTime = sdf.format(timeD);
            result = convertedTime;
        }
        return result;
    }

    //return weight if user exists
    //return -1 if the user does not exists
    public double getMostRecentWeight(String userName){
        double result = -1.0;
        if(profiles.validUser(userName)){
            WeightRecord userRecord;
            userRecord = weightdatabase.getMostRecent(userName);
            if(userRecord != null){
                result = userRecord.getWeight();
            }
        }
        return result;
    }
    public List<WeightRecord> getUserWeights(String user){
        return weightdatabase.getUserWeights(user);
    }

    //return true if the weight record has successfully inserted
    //return false if the weight record has failed to insert
    public boolean insertNewWeightRecord(String userName , long time , double weight){
        boolean result = false;
        if(profiles.validUser(userName) && validWeight(weight) && validTime(time)){
            if(weightdatabase != null){
                weightdatabase.add(userName, time, weight);
                result = true;
            }
        }
        return result;
    }//end method

    //Not a correct version , edit Required
    //need to check if insert date and time is latest than the most recent record in database.
    public boolean validTime(long time){

        boolean result = false;
        if(time > 0){
            result = true;
        }
        return result;
    }//end method

    //return true if weight is at least larger than 0
    public boolean validWeight(double weight){
        boolean result = false;
        if(weight > 0.0){
            result = true;
        }
        return result;
    }//end method
}//end class
