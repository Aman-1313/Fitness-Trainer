package com.example.personalfitnesstrainer.persistence.hsqldb;

import com.example.personalfitnesstrainer.objects.Exercise;
import com.example.personalfitnesstrainer.objects.FitnessGoal;
import com.example.personalfitnesstrainer.objects.Profile;
import com.example.personalfitnesstrainer.persistence.ExerciseDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLExerciseDatabase implements ExerciseDatabase {
    private final String dbPath;
    private final String myTable;

    public SQLExerciseDatabase(String path, String table) {
        dbPath = path;
        myTable = table;
    }

    private Exercise fromResultSet(ResultSet rs) throws SQLException {
        return new Exercise(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
    }

    private Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public void add(String name, String category, String subcategory, double calsPerMin) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("insert into " + myTable + " values(?, ?, ?, ?)");
            st.setString(1, name);
            st.setString(2, category);
            st.setString(3, subcategory);
            st.setDouble(4, calsPerMin);

            st.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Exercise get(String name) {
        Exercise result = null;
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select * from " + myTable + " where name=?");
            st.setString(1, name);

            final ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = fromResultSet(rs);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Exercise> getAll() {
        List<Exercise> result = new ArrayList<>();
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select * from " + myTable + " order by category, subcategory, name");

            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                result.add(fromResultSet(rs));
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<String> getCategories() {
        List<String> result = new ArrayList<>();
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select distinct category from " + myTable + " order by category");

            final ResultSet rs = st.executeQuery();
            while(rs.next()) {
                result.add(rs.getString(1));
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<Exercise> getInCategory(String category) {
        List<Exercise> result = new ArrayList<>();
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select * from " + myTable + " where category=? order by category, subcategory, name");
            st.setString(1, category);

            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                result.add(fromResultSet(rs));
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<String> getSubcategories() {
        List<String> result = new ArrayList<>();
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select distinct subcategory from " + myTable + " order by subcategory");

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

    @Override
    public List<String> getSubcategories(String category) {
        List<String> result = new ArrayList<>();
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select distinct subcategory from " + myTable + " where category=? order by subcategory");
            st.setString(1, category);

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

    @Override
    public List<Exercise> getInSubcategory(String subcategory) {
        List<Exercise> result = new ArrayList<>();
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select * from " + myTable + " where subcategory=? order by category, subcategory, name");
            st.setString(1, subcategory);

            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                result.add(fromResultSet(rs));
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
