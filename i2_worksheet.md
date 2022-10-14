# Iteration 2 Worksheet
## Paying off Technical Debt

1. Updating Profile Information
    - When we implemented changing user profile information, it was before we implemented our database layer using SQL. As a result, we updated profile information by retrieving the Profile object from the fake database and updating the fields in that object. However, in the real database, this Profile object is temporary and the changes to it are not reflected in the database.
    - To solve this issue, we created a method in our ProfileDatabase interface, which had to be implemented in the real and fake database. The method accepts a Profile object, and updates the corresponding database entry with that object’s information.
    - We believe this was inadvertent, reckless debt, because we did not consider that the Profile objects returned by the SQL database would have to be temporary, and would not be backed by the database.
    - Here are links to the method in the [interface,](https://code.cs.umanitoba.ca/winter-2022-a01/group-14/personal-fitness-trainer/-/commit/a0263025497bb7eb29fc9ae041b33460431de20f#896c550777f6d78db504541e775fc57c09bc93ba_28_28) the [fake implementation,](https://code.cs.umanitoba.ca/winter-2022-a01/group-14/personal-fitness-trainer/-/commit/a0263025497bb7eb29fc9ae041b33460431de20f#65d54c925c734b795729f03a7862905e32e2ff30_50_50) and the [real implementation.](https://code.cs.umanitoba.ca/winter-2022-a01/group-14/personal-fitness-trainer/-/commit/a0263025497bb7eb29fc9ae041b33460431de20f#edd83e426168354ba32cf83a602ee232d5d45a28_117_118)

2. Initializing the Fake Database with CSV Files 
    - To store sample data for the fake database, we included it in .csv files and added methods to our Services.java class that initialized the database by parsing the .csv files. This resulted in technical debt due to the way that the SQL database has to be initialized. Because the SQL database should only be initialized with sample data if no database exists in the device’s file system, we decided to do it the same way as the sample project, where the sample data is in a script and the script is only copied to the device if a matching file does not exist.
    - The difference in how the fake and real databases are initialized means that when we add new sample data to the project, it needs to be added to pft_db.script and the correct .csv file.
    - We think this is inadvertent, prudent debt. It is inadvertent because we did not realize our way of initializing the real database without overwriting an existing one would prevent us from using .csv files to initialize the real database. It is prudent because we can get around this by including duplicate sample data in two different files, but if we could go back and do it differently, we would probably write code to initialize the fake database by parsing an SQL script instead of .csv files.
    - Here is sample data being [added to a .csv file,](https://code.cs.umanitoba.ca/winter-2022-a01/group-14/personal-fitness-trainer/-/commit/3f877e2522b20a6bdb2fcc2bacf1ed6c0409d63c#276f9cd0906b36c798574151c9aeebc5591a12ba_1_1) and that same data being [added to pft_db.script.](https://code.cs.umanitoba.ca/winter-2022-a01/group-14/personal-fitness-trainer/-/commit/3f877e2522b20a6bdb2fcc2bacf1ed6c0409d63c#890cc42565ed0b154242f043d619be56a03b5e31_29_25)

## SOLID

[Here is the issue we opened for group 13's project.](https://code.cs.umanitoba.ca/winter-2022-a01/group-13/unnamed-budgeting-app/-/issues/39)

## Retrospective

- After writing the retrospective for our previous iteration we realized the importance of estimating the time for features and user stories. For example, [in this issue for iteration 2,](#14) you can see the time estimate we added. Previously, we only wrote our estimate in the file instead of properly adding it to the issue on GitLab, such as [here](#13).
- We did a fairly good job on our first iteration in writing tests, so we did not really change our testing procedures.
- We did a better job of using GitLab to assign work to different group members by making an issue for each developer task and assigning those, instead of assigning user stories. An example of one of these Developer Task issues is #36

## Design Patterns

- We used a singleton pattern for our [Services](https://code.cs.umanitoba.ca/winter-2022-a01/group-14/personal-fitness-trainer/-/blob/main/app/src/main/java/com/example/personalfitnesstrainer/app/Services.java) class. We used this pattern to ensure that the entire application used the same instance of each database class, preventing desynchronized data in different parts of the app.

## Iteration 1 Feedback Fixes:
- There do not appear to be any issues on our project that were opened by a grader or another group for iteration 1.
- We fixed issues mentioned in our feedback by the grader by closing issues for iteration 2 when they were completed, eliminating TODOs in our code, and updating the architecture file to more accurately reflect the state of the application.
