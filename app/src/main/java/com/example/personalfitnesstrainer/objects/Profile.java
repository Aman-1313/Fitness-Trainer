package com.example.personalfitnesstrainer.objects;

// stores the following information for a user:
    // email/username (String, must be unique)
    // password (String)
    // first name (String)
    // height (double)
    // fitness goal (FitnessGoal, see GoalDatabase and FitnessGoal for more info)
    // avatar name (String, rest of data for avatar is in AvatarDatabase)
// Note: the user's weights are stored in the WeightDatabase, where the most recent one can be retrieved to get the
// user's current weight (see WeightDatabase)
public class Profile {
    private String email;
    private String password;
    private String firstName;
    private double height;
    private FitnessGoal goal;
    private String avatar;

    public Profile(String username, String password, String firstName, double height, FitnessGoal goal, String avatarName) {
        email = username;
        this.password = password;
        this.firstName = firstName;
        this.height = height;
        this.goal = goal;
        avatar = avatarName;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        email = username;
    }

    // password can't be directly accessed, only checked against what the user inputs
    public boolean checkPassword(String guess) {
        return password.equals(guess);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public FitnessGoal getGoal() {
        return goal;
    }

    public void setGoal(FitnessGoal goal) {
        this.goal = goal;
    }

    public String getAvatarName() {
        return avatar;
    }

    public void setAvatarName(String avatarName) {
        avatar = avatarName;
    }
}
