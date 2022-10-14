# Architecture

The following packages are present in the PersonalFitnessTrainer app:

## activities

This package contains the app's Android Activities. It includes:

- Avatar.java
    - The interface for changing the user's profile picture. Accessible from the home page.
- Homepage.java
    - This is where the user is brought after signing into the app. They can see a summary of their progress, and access the other parts of the app.
- MainActivity.java (login page)
    - The first thing the user sees when starting the app
    - The user enters their email and password before being taken to the home page (see [readme](README.md#L21) for sample account credentials)
    - This activity also calls Services.init() on startup to initialize the app's databases
- Timepicker.java
    - This is used to select a time of day for an activity when the user schedules one in the fitness planner.
- UpdateAccount.java
    - When a user changes their email or password from the profile page, this class calls logic code to validate the new information and update it if it is valid


## app

This package contains one class, Services.java. This class is a singleton responsible for initializing and returning references to the app's the databases. The method init() creates an instance of each type of database, and copies the SQL database script to the device if none is present. The method initFake() creates instances of the fake database implementations, and fills them with the sample data. The other methods return synchronized references to the databases that were initialized.
All business classes which access a database do it via Services, but it is not included in the architecture diagram. This is because it is easier to understand the relationships between the business classes and databases by directly connecting them in the diagram.

## business

This package constitutes the logic layer of the application. It contains the following classes:

- AccessExercise.java 
    - This class is used by the UI to access the information stored in the ActivityDatabase and ExerciseDatabase. It accesses the databases with Services.getActivityDatabase() and .getExerciseDatabase().
    - Aside from basic access, it also contains logic to automatically recommended an exercise for the user based on their fitness goal
- AccessProfile.java
    - This class is used by the UI to access the information stored in a ProfileDatabase. This includes checking the login information provided by the user and providing or modifying a user's information.
    - Whenever this class accesses the app's instance of ProfileDatabase, it retrieves it from Services.getProfileDatabase()
- AccessWeight.java
    - This class is used by the UI to access the information in a WeightDatabase. This includes allowing a user to record their weight, retrieving the user's most recent recorded weight, and retrieving all of the user's weights.
    - Whenever this class accesses the app's instance of ProfileDatabase, it retrieves it from Services.getWeightDatabase()
- EmailChanger.java
    - This class handles the logic for updating a user's email address. The user's email is used as a key in multiple databases to associate data with a user, so the method in this class will update all of the entries containing the user's email.
    - This class also checks the new email given by the user for validity, and does not change the user's email if it is invalid.
- TimeConverter.java
    - This class converts time stamps from the human-readable format presented in the UI to the UNIX Epoch timestamps used by the database, and vice versa.

## fragments

This package contains the app's Android Fragments. It includes:

- Home.java
    - Responsible for displaying the Homepage activity when the user selects the "Home" button
- Plan.java
    - This is the interface for the activity planner. The user can review what exercises they have scheduled, and add activities to or remove activities from the schedule.
- Profile.java
    - Brought up when the user selects the "Profile" button. Displays the user's profile information, and allows the user to update it.

## objects

Each class corresponds to one of the databases in the persistence package. See below.

## persistence

This package contains the interfaces for the app's databases. It also includes two subpackages, containing the fake implementations and HSQLDB implementations of the interfaces described below.

- ActivityDatabase.java
    - This contains the ScheduledExercises in the user's activity planner. The fitness record is not implemented yet, but will use this database as well.
    - The key for each entry is the user's email, and the time for the activity. When a user schedules an activity, it will be added to this database and appear in their planner. Once the time for the activity has passed, our plan is for it to appear in the fitness record, where previous activities will be displayed.
- ExerciseDatabase.java
    - This contains the exercises that the user can select from for their own fitness activities (i.e. they are referenced by ScheduledExercises). The contents are never modified.
- GoalDatabase.java
    - This database contains the types of fitness goal that the user can select from.
    - When a user selects a fitness goal, one of the types from this database is added to their profile, along with the amount associated with the goal (for example, if the goal type is "Weight loss", the amount would be the users desired weight).
- ProfileDatabase.java
    - Contains the profiles for each user registered with the app. A sample account is provided in the readme.
- WeightDatabase.java
    - Weight tracking is not implemented yet, but our plan is:
    - When the user records their weight, an entry will be made in this database with the weight and the time it was recorded.
    - The most recent entry will be used in the Profile page to show the user their current weight.
    - When the user's progress record is implemented, the user will be able to see all entries over time to get a sense of how their weight has changed while using the app.

# Architecture Diagram

```
                            ┌────────┐  ┌──────────┐
                            │Homepage│  │ Profile  │       ┌────┬──────────┐
                 ┌────────┐ └─┬────┬─┘  │(Activity)│       │Plan│Timepicker│
      User       │  Main  │   │Home│    └┬─────────┘       └──┬─┴──────────┘
   Interface     │Activity│  ┌┴────┴┐    │                    │
                 │(Login) │  │Avatar│    │        ┌───────┐   │
                 └────┬───┘  └┬─────┘    │        │Update │   │
                      │       │          │        │Account│   │
───────────────       │       │ ┌────────┘        └───┬───┘   │
                      └───┐   │ │                     │       └───────┐
                          │   │ │                     │               │
                          │   │ │                     │               │
                         ┌┴───┴─┴┐                ┌───┴───┐       ┌───┴────┐
   Business              │Access │                │ Email │       │ Access │
                         │Profile│              ┌─┤Changer│       │Exercise│
                         └─┬───┬─┘              │ └─┬─────┘       └─┬──┬───┘
                           │   │                │   │               │  │
───────────────     ┌──────┘   └───┐    ┌───────┘   │    ┌──────────┘  │
                    │              │    │           │    │             │
                    │              │    │           │    │             │
               ┌────┴───┐        ┌─┴────┴─┐       ┌─┴────┴─┐      ┌────┴───┐
  Persistence  │  Goal  │        │Profile │       │Activity│      │Exercise│
               │Database│        │Database│       │Database│      │Database│
               └───┬────┘        └───┬────┘       └───┬────┘      └───┬────┘
                   │                 │                │               │
───────────────    │                 │                │               │
                   │                 │                │               │
                ┌──┴────┐        ┌───┴────┐      ┌────┴────┐      ┌───┴────┐
Domain-Specific │Fitness│        │Profile │      │Scheduled│      │Exercise│
    Objects     │ Goal  │        │(Object)│      │Exercise │      └────────┘
                └───────┘        └────────┘      └─────────┘
```
