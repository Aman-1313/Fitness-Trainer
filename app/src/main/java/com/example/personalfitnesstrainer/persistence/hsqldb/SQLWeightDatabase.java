package com.example.personalfitnesstrainer.persistence.hsqldb;

import com.example.personalfitnesstrainer.objects.FitnessGoal;
import com.example.personalfitnesstrainer.objects.Profile;
import com.example.personalfitnesstrainer.objects.WeightRecord;
import com.example.personalfitnesstrainer.persistence.WeightDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLWeightDatabase implements WeightDatabase {
    private final String dbPath;
    private final String myTable;

    public SQLWeightDatabase(String path, String table) {
        dbPath = path;
        myTable = table;
    }

    private WeightRecord fromResultSet(ResultSet rs) throws SQLException {
        return new WeightRecord(rs.getString(1), rs.getLong(2), rs.getDouble(3));
    }

    private Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public void add(String user, long time, double weight) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("insert into " + myTable + " values(?, ?, ?)");
            st.setString(1, user);
            st.setLong(2, time);
            st.setDouble(3, weight);
            st.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WeightRecord getWeight(String user, long time) {
        WeightRecord result = null;
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select * from " + myTable + " where email=? and recordTime=?");
            st.setString(1, user);
            st.setLong(2, time);

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
    public WeightRecord remove(String user, long time) {
        WeightRecord result = getWeight(user, time);
        if (result != null) {
            try (Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement("delete from " + myTable + " where email=? and recordTime=?");
                st.setString(1, user);
                st.setLong(2, time);
                st.executeUpdate();
            }
            catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public List<WeightRecord> getUserWeights(String user) {
        List<WeightRecord> result = new ArrayList<>();

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select * from " + myTable + " where email=? order by recordTime");
            st.setString(1, user);

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
    public WeightRecord getMostRecent(String user) {
        WeightRecord result = null;
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select top 1 * from " + myTable + " where email=? order by recordTime desc");
            st.setString(1, user);

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
}
