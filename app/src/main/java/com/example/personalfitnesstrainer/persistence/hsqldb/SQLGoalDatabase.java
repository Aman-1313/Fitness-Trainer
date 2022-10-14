package com.example.personalfitnesstrainer.persistence.hsqldb;

import com.example.personalfitnesstrainer.objects.FitnessGoal;
import com.example.personalfitnesstrainer.objects.Profile;
import com.example.personalfitnesstrainer.persistence.GoalDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLGoalDatabase implements GoalDatabase {
    private final String dbPath;
    private final String myTable;

    public SQLGoalDatabase(String path, String table) {
        dbPath = path;
        myTable = table;
    }

    private Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public void add(String goalType) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("insert into " + myTable + " values(?)");
            st.setString(1, goalType);
            st.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getAll() {
        List<String> result = new ArrayList<>();
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select * from " + myTable + " order by name");
            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
