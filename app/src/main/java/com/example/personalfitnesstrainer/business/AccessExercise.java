package com.example.personalfitnesstrainer.business;

import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.objects.Exercise;
import com.example.personalfitnesstrainer.objects.ScheduledExercise;
import com.example.personalfitnesstrainer.persistence.ActivityDatabase;
import com.example.personalfitnesstrainer.persistence.ExerciseDatabase;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class AccessExercise {

    private final ExerciseDatabase exerciseDatabase;
    private final ActivityDatabase activityDatabase;
    private final AccessProfile profiles;

    public AccessExercise(){
        exerciseDatabase = Services.getExerciseDatabase();
        activityDatabase = Services.getActivityDatabase();
        profiles = new AccessProfile();
    }

    /* get user's scheduled exercise for a specific date
     *  Parameter: (1)userName = username/email
     *             (2)startTime = the exercise begin time
     *
     *  Return ScheduledExercise object, null if user does not have any scheduled exercise on that specific date
     * */
    public ScheduledExercise getCertainDateExercise(String userName , Calendar startTime){
        return activityDatabase.get(userName, TimeConverter.calendarToLong(startTime));
    }

    /* get user's scheduled exercise for a specific date
     *  Parameter: (1)userName = username/email
     *             (2)startTime = the exercises begin range time
     *             (3)endTime = the exercises end range time
     *  Return ScheduledExercise List, null if user does not have any scheduled exercises on that specific date range
     * */
    public List<ScheduledExercise> getCertainDateRangeExercises(String userName , Calendar startTime , Calendar endTime){

        return activityDatabase.getUserActivities(
                userName,
                TimeConverter.calendarToLong(startTime),
                TimeConverter.calendarToLong(endTime)
        );
    }

    /* Auto assignment an exercise on specific time for user
    *  Parameter: (1)userName = username/email
    *             (2)startTime = the exercise begin time
    *
    *  Return String: Name of the selected exercise, or null if none could be assigned
    * */
    public String assignExerciseOnDay(String userName, Calendar startTime){
        String result = null;
        if (profiles.validUser(userName)) {
            String goal =  profiles.getGoal(userName);
            List<Exercise> exercises;

            if (goal.equals("Weight Loss") || goal.equals("Running Distance")) {
                exercises = exerciseDatabase.getInCategory("Cardio");
            }
            else if (goal.equals("Weight Gain") || goal.equals("Exercise Reps")) {
                exercises = exerciseDatabase.getInCategory("Anaerobic");
            }
            else {
                exercises = exerciseDatabase.getAll();
            }

            // choose a random exercise from the list as the result
            int exerciseIndex = (int) (Math.random() * exercises.size());
            result = exercises.get(exerciseIndex).getName();

            // make the activity end 1 hour after the start
            Calendar endTime = (Calendar)startTime.clone();
            endTime.add(Calendar.HOUR, 1);

            activityDatabase.add(userName, TimeConverter.calendarToLong(startTime),
                    TimeConverter.calendarToLong(endTime), false, result);
        }
        return result;
    }

    /* Auto assignment an exercise on specific date range for user
     *  Parameter : (1)userName = username/email
     *              (2)startTime = the exercise begin time
     *              (3)range = amount of day user want to assign exercises for, should be larger than 0
     *  Return boolean: (1)True = auto assign successful
     *                  (2)False = auto assign failed(could be caused by invalid userName , or invalid range)
     * */
    public boolean assignExercisesInRange(String userName, Calendar startTime , int range) {
        boolean userExists = profiles.validUser(userName);
        if (userExists) {
            Calendar currTime = (Calendar) startTime.clone();
            for (int i = 0; i < range; i++) {
                assignExerciseOnDay(userName, currTime);
                currTime.add(Calendar.DATE, 1);
            }
        }
        return userExists;
    }

    /* add a new scheduled exercise on certain date for user
     *  Parameter : (1)userName = username/email
     *              (2)exerciseName = specific exercise name that user want to do
     *              (3)startTime = the exercise begin time
     *              (4)endTime = the exercise end time
     *  Return boolean: (1)True = exercise added successful
     *                  (2)False = exercise added failed(could be caused by invalid userName , invalid time)
     * */
    public boolean addExercise(String userName , String exerciseName, Calendar startTime , Calendar endTime){
        boolean valid = profiles.validUser(userName) && validExerciseName(exerciseName) && startTime.compareTo(endTime) < 0;
        if (valid)
            activityDatabase.add(userName, TimeConverter.calendarToLong(startTime),
                    TimeConverter.calendarToLong(endTime), false, exerciseName);
        return valid;
    }

    /* Delete a scheduled exercise on certain date for user
     *  Parameter : (1)userName = username/email
     *              (2)startTime = the exercise begin time
     *  Return boolean: (1)True = auto assign successful
     *                  (2)False = auto assign failed(should be caused by invalid userName)
     * */
    public boolean removeExercise(String userName , Calendar startTime){
        return activityDatabase.remove(userName, TimeConverter.calendarToLong(startTime)) != null;
    }

    /* update a scheduled exercise status on certain date for user to completed
     *  Parameter : (1)userName = username/email
     *              (2)startTime = the exercise begin time
     *  Return boolean: (1)True = status update successful
     *                  (2)False = status update failed(should be caused by invalid userName , or invalid date)
     * */
    public boolean setExerciseIsComplete(String userName , Calendar startTime) {
        return activityDatabase.changeIsComplete(userName, TimeConverter.calendarToLong(startTime), true) != null;
    }

    /*  check scheduled exercise status on certain date for user whether is completed or not completed
     *  Parameter : (1)userName = username/email
     *              (2)startTime = the exercise begin time
     *  Return boolean: (1)True = user's exercise status is complete
     *                  (2)False = user's exercise status is incomplete ,or user not found ,or invalid date
     * */
    public boolean isUserCompleteExercise(String userName , Calendar startTime){
        ScheduledExercise toCheck = activityDatabase.get(userName, TimeConverter.calendarToLong(startTime));
        return toCheck != null && toCheck.isComplete();
    }

    /*  check is a exercise valid exercise
     *  Parameter : (1)exerciseName = exercise name
     *
     *  Return boolean: (1)True = exercise name is valid
     *                  (2)False = exercise name is not valid
     * */
    public boolean validExerciseName(String exerciseName){
        return exerciseDatabase.get(exerciseName) != null;
    }

    /* Check if user is available at the given time
     * Parameter : (1) username - username/email of user to check
     *             (2) time - time to check availability for
     * Returns   : boolean - True if user exists and does not have any existing ScheduledExercises
     *                       at the given time. False if user does not exist, or has an activity
     *                       scheduled at that time.
     * */
    public boolean isUserAvailable(String username, Calendar time) {
        return profiles.validUser(username) && activityDatabase.get(username, TimeConverter.calendarToLong(time)) == null;
    }
}
