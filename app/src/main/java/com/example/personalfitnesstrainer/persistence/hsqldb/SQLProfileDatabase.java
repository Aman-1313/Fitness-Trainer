package com.example.personalfitnesstrainer.persistence.hsqldb;

import com.example.personalfitnesstrainer.objects.FitnessGoal;
import com.example.personalfitnesstrainer.objects.Profile;
import com.example.personalfitnesstrainer.persistence.ProfileDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class SQLProfileDatabase implements ProfileDatabase {
    private final String dbPath;
    private final String myTable;

    public SQLProfileDatabase(String path, String table) {
        dbPath = path;
        myTable = table;
    }

    private Profile fromResultSet(ResultSet rs) throws SQLException {
        // create fitness goal, if one exists
        FitnessGoal fg = null;
        if (rs.getString(5) != null) {
            fg = new FitnessGoal(rs.getString(5), rs.getString(6), rs.getDouble(7));
        }
        return new Profile (
                rs.getString(1),    // email
                rs.getString(2),    // password
                rs.getString(3),    // name
                rs.getDouble(4),    // height
                fg,                    // fitness goal
                rs.getString(8)     // avatar filename
        );
    }

    private Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public void add(String username, String password, String firstName, double height, FitnessGoal goal, String avatarName) {
        String gType = null, gSub = null;
        double gAmt = 0.0;

        if (goal != null) {
            gType = goal.getType();
            gSub = goal.getSubtype();
            gAmt = goal.getAmount();
        }

        add(username, password, firstName, height, gType, gSub, gAmt, avatarName);
    }

    @Override
    public void add(String username, String password, String firstName, double height, String goalType, String goalSubtype, double goalAmount, String avatarName) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("insert into " + myTable + " values(" +
                    "?, ?, ?, ?, ?, ?, ?, ?)"
            );
            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, firstName);
            st.setDouble(4, height);
            st.setString(5, goalType);
            st.setString(6, goalSubtype);
            st.setDouble(7, goalAmount);
            st.setString(8, avatarName);

            st.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Profile get(String username) {
        Profile result = null;

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select * from " + myTable + " where email=?");
            st.setString(1, username);

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
    public Profile updateUsername(String oldEmail, String newEmail) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("update " + myTable + " set email=? where email=?");
            st.setString(1, newEmail);
            st.setString(2, oldEmail);
            st.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return get(newEmail);
    }

    @Override
    public Profile updateProfile(Profile p) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("update " + myTable + " set " +
                    "name=?, height=?, goalType=?, goalSubtype=?, goalAmount=?, avatar=? where email=?");

            st.setString(1, p.getFirstName());
            st.setDouble(2, p.getHeight());
            // update fitness goal, set everything to null if there is no goal
            FitnessGoal fg = p.getGoal();
            if (fg != null) {
                st.setString(3, fg.getType());
                st.setString(4, fg.getSubtype());
                st.setDouble(5, fg.getAmount());
            }
            else {
                st.setNull(3, Types.VARCHAR);
                st.setNull(4, Types.VARCHAR);
                st.setNull(5, Types.DOUBLE);
            }
            st.setString(6, p.getAvatarName());
            st.setString(7, p.getUsername());

            st.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // return entry
        return get(p.getUsername());
    }

    @Override
    public Profile remove(String username) {
        Profile result = get(username);

        if (result != null) {
            try (Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement("delete from " + myTable + " where email=?");
                st.setString(1, username);
                st.executeUpdate();
            }
            catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    @Override
    public List<String> getUsernames() {
        List<String> result = new ArrayList<>();

        for (Profile p : getAll()) {
            result.add(p.getUsername());
        }

        return result;
    }

    @Override
    public List<Profile> getAll() {
        List<Profile> result = new ArrayList<>();

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("select * from " + myTable + " order by email asc");

            ResultSet rs = st.executeQuery();
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
    public Profile updatePassword(String username, String password) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("update " + myTable + " set password=? where email=?");
            st.setString(1, password);
            st.setString(2, username);
            st.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return get(username);
    }
}
