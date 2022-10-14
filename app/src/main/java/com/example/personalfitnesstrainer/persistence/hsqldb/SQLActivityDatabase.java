package com.example.personalfitnesstrainer.persistence.hsqldb;

import com.example.personalfitnesstrainer.objects.ScheduledExercise;
import com.example.personalfitnesstrainer.persistence.ActivityDatabase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLActivityDatabase implements ActivityDatabase {
    private final String dbPath;
    private final String myTable;

    public SQLActivityDatabase(String path, String table) {
        dbPath = path;
        myTable = table;
    }

    private ScheduledExercise fromResultSet(ResultSet rs) throws SQLException {
        return new ScheduledExercise(
                rs.getString(1),    // email
                rs.getLong(2),      // start
                rs.getLong(3),      // end
                rs.getBoolean(4),   // isComplete
                rs.getString(5)     // exerciseName
        );
    }

    private Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public void add(String user, long start, long end, boolean isComplete, String exerciseName) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("insert into " + myTable + " values(" +
                    "?, ?, ?, ?, ?)"
            );
            st.setString(1, user);
            st.setLong(2, start);
            st.setLong(3, end);
            st.setBoolean(4, isComplete);
            st.setString(5, exerciseName);

            st.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ScheduledExercise get(String user, long start) {
        ScheduledExercise result = null;

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select * from " + myTable +
                    " where email=? AND startTime=?"
            );
            st.setString(1, user);
            st.setLong(2, start);

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
    public ScheduledExercise remove(String user, long start) {
        ScheduledExercise result = get(user, start);
        if (result != null) {
            try (Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement(
                        "delete from " + myTable + " where email=? AND startTime=?"
                );
                st.setString(1, user);
                st.setLong(2, start);

                st.executeUpdate();
            }
            catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public ScheduledExercise changeIsComplete(String user, long start, boolean isComplete) {
        ScheduledExercise toChange = get(user, start);
        if (toChange != null) {
            try (Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement(
                        "update " + myTable + " set complete=? where email=? AND startTime=?"
                );
                st.setBoolean(1, isComplete);
                st.setString(2, user);
                st.setLong(3, start);

                st.executeUpdate();
            }
            catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        // call get again so information is updated
        return get(user, start);
    }

    @Override
    public List<ScheduledExercise> getUserActivities(String user) {
        return getUserActivities(user, 0, Long.MAX_VALUE);
    }

    @Override
    public List<ScheduledExercise> getUserActivities(String user, long earliest, long latest) {
        List<ScheduledExercise> result = new ArrayList<>();

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement(
                    "select * from " + myTable + " where email=? AND startTime BETWEEN ? and ? " +
                            "order by startTime"
            );
            st.setString(1, user);
            st.setLong(2, earliest);
            st.setLong(3, latest);

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
