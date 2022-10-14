package com.example.personalfitnesstrainer.app;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.personalfitnesstrainer.persistence.ActivityDatabase;
import com.example.personalfitnesstrainer.persistence.ExerciseDatabase;
import com.example.personalfitnesstrainer.persistence.GoalDatabase;
import com.example.personalfitnesstrainer.persistence.ProfileDatabase;
import com.example.personalfitnesstrainer.persistence.WeightDatabase;
import com.example.personalfitnesstrainer.persistence.fakeDB.FakeActivityDatabase;
import com.example.personalfitnesstrainer.persistence.fakeDB.FakeExerciseDatabase;
import com.example.personalfitnesstrainer.persistence.fakeDB.FakeGoalDatabase;
import com.example.personalfitnesstrainer.persistence.fakeDB.FakeProfileDatabase;
import com.example.personalfitnesstrainer.persistence.fakeDB.FakeWeightDatabase;
import com.example.personalfitnesstrainer.persistence.hsqldb.SQLActivityDatabase;
import com.example.personalfitnesstrainer.persistence.hsqldb.SQLExerciseDatabase;
import com.example.personalfitnesstrainer.persistence.hsqldb.SQLGoalDatabase;
import com.example.personalfitnesstrainer.persistence.hsqldb.SQLProfileDatabase;
import com.example.personalfitnesstrainer.persistence.hsqldb.SQLWeightDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

// Stores a reference to each database for the application.
// Calling init from the main Activity when the app is launched will
// create the databases and fill them with the app's sample data.

public class Services {
    private static final String
            DB_CATALOG = "pft_db",
            DB_DIR = "database",
            SAMPLE_EXT = ".csv",

            ACTIVITY_NAME = "activities",
            EXERCISE_NAME = "exercises",
            GOAL_NAME = "goals",
            PROFILE_NAME = "profiles",
            WEIGHT_NAME = "weights",

            ACTIVITY_PATH = "database/activities.csv",
            EXERCISE_PATH = "database/exercises.csv",
            GOAL_PATH = "database/goals.csv",
            PROFILE_PATH = "database/profiles.csv",
            WEIGHT_PATH = "database/weights.csv"
    ;

    private static String dbPath = null;

    private static ActivityDatabase activity = null;
    private static ExerciseDatabase exercise = null;
    private static GoalDatabase goal = null;
    private static ProfileDatabase profile = null;
    private static WeightDatabase weight = null;

    // initialize stub implementation of database layer
    public static void initFake(Context appContext) {
        activity = new FakeActivityDatabase();
        exercise = new FakeExerciseDatabase();
        goal = new FakeGoalDatabase();
        profile = new FakeProfileDatabase();
        weight = new FakeWeightDatabase();

        buildDatabases(appContext);
    }

    // initialize HSQLDB implementation of database layer
    public static void init(Context appContext) {
        copyDatabaseToDevice(appContext, false);

        // create instances of the databases
        activity = new SQLActivityDatabase(dbPath, ACTIVITY_NAME);
        exercise = new SQLExerciseDatabase(dbPath, EXERCISE_NAME);
        goal = new SQLGoalDatabase(dbPath, GOAL_NAME);
        profile = new SQLProfileDatabase(dbPath, PROFILE_NAME);
        weight = new SQLWeightDatabase(dbPath, WEIGHT_NAME);
    }

    public static void resetDatabase(Context appContext) {
        copyDatabaseToDevice(appContext, true);

        // create instances of the databases
        activity = new SQLActivityDatabase(dbPath, ACTIVITY_NAME);
        exercise = new SQLExerciseDatabase(dbPath, EXERCISE_NAME);
        goal = new SQLGoalDatabase(dbPath, GOAL_NAME);
        profile = new SQLProfileDatabase(dbPath, PROFILE_NAME);
        weight = new SQLWeightDatabase(dbPath, WEIGHT_NAME);
    }

    private static void copyDatabaseToDevice(Context appContext, boolean force) {
        String[] assetNames;
        File dataDir = appContext.getDir(DB_DIR, Context.MODE_PRIVATE);
        AssetManager assetMan = appContext.getAssets();

        try {
            assetNames = assetMan.list(DB_DIR);

            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_DIR + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDir, appContext, force);
            dbPath = dataDir.toString() + "/" + DB_CATALOG;
        }
        catch (IOException ioe) {
            throw new RuntimeException("Exception while copying database files to device:\n" + ioe.getMessage(), ioe);
        }
    }

    private static void copyAssetsToDirectory (String[] assets, File directory, Context appContext, boolean force)
            throws IOException
    {
        AssetManager assetMan = appContext.getAssets();

        for (String a : assets) {
            String[] components = a.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists() || force) {
                InputStreamReader in = new InputStreamReader(assetMan.open(a));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

    private static void buildDatabases(Context appContext) {
        buildActivityDatabase(appContext);
        buildExerciseDatabase(appContext);
        buildGoalDatabase(appContext);
        buildProfileDatabase(appContext);
        buildWeightDatabase(appContext);
    }

    public static synchronized ActivityDatabase getActivityDatabase() {
        return activity;
    }

    public static synchronized ExerciseDatabase getExerciseDatabase() {
        return exercise;
    }

    public static synchronized GoalDatabase getGoalDatabase() {
        return goal;
    }

    public static synchronized ProfileDatabase getProfileDatabase() {
        return profile;
    }

    public static synchronized WeightDatabase getWeightDatabase() {
        return weight;
    }

    private static void buildActivityDatabase(Context appContext) {
        BufferedReader sampleData;

        try {
            sampleData = new BufferedReader(new InputStreamReader(appContext.getAssets().open(ACTIVITY_PATH)));

            String line = sampleData.readLine();

            while (line != null) {
                String[] tokens = line.split(",");
                getActivityDatabase().add(tokens[0], Long.parseLong(tokens[1]), Long.parseLong(tokens[2]), Boolean.parseBoolean(tokens[3]), tokens[4]);

                line = sampleData.readLine();
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void buildExerciseDatabase(Context appContext) {
        BufferedReader sampleData;

        try {
            sampleData = new BufferedReader(new InputStreamReader(appContext.getAssets().open(EXERCISE_PATH)));

            String line = sampleData.readLine();

            while (line != null) {
                String[] tokens = line.split(",");
                getExerciseDatabase().add(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]));

                line = sampleData.readLine();
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void buildGoalDatabase(Context appContext) {
        BufferedReader sampleData;

        try {
            sampleData = new BufferedReader(new InputStreamReader(appContext.getAssets().open(GOAL_PATH)));

            String line = sampleData.readLine();

            while (line != null) {
                getGoalDatabase().add(line);
                line = sampleData.readLine();
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void buildProfileDatabase(Context appContext) {
        BufferedReader sampleData;

        try {
            sampleData = new BufferedReader(new InputStreamReader(appContext.getAssets().open(PROFILE_PATH)));

            String[] tokens = sampleData.readLine().split(",");

            getProfileDatabase().add(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]),
                    tokens[4], tokens[5], Double.parseDouble(tokens[6]), tokens[7]);

        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void buildWeightDatabase(Context appContext) {
        BufferedReader sampleData;

        try {
            sampleData = new BufferedReader(new InputStreamReader(appContext.getAssets().open(WEIGHT_PATH)));

            String line = sampleData.readLine();

            while (line != null) {
                String[] tokens = line.split(",");
                getWeightDatabase().add(tokens[0], Long.parseLong(tokens[1]), Double.parseDouble(tokens[2]));

                line = sampleData.readLine();
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
