package com.example.projectpyramid.data_access;
import com.example.projectpyramid.domain.entities.User;

import java.sql.*;

public class UserMapper {

    // FIXME insert password as well, rn it defaults to 1234.
    public boolean insert(User user) throws Exception {
        String query = "INSERT INTO users (fullname, username, password) VALUES (?, ?, 1234)";
        Connection connection = DBManager.getConnection();
        boolean wasSuccessful = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getUserName());
            wasSuccessful = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("An Exception occured:");
            ex.printStackTrace();
        } finally {
            connection.clearWarnings();
            connection.close();
        }
        return wasSuccessful;
    }

    // TODO update password as well.
    public boolean update(User user) throws Exception {
        String query = "UPDATE users SET fullname = ?, username = ? WHERE id = ?";
        Connection connection = DBManager.getConnection();
        boolean wasSuccessful = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setInt(3, user.getId());

            wasSuccessful = preparedStatement.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("An Exception occured:");
            ex.printStackTrace();
        } finally {
            connection.clearWarnings();
            connection.close();
        }

        return wasSuccessful;
    }

    public boolean delete(User user) {
        String query = "DELETE FROM users WHERE id = ?;";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    public User login(String userName, String password) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT id, fullname FROM users "
                    + "WHERE username=? AND password=?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String fullName = rs.getString("fullname");
                int userId = rs.getInt("id");
                return new User(userId, fullName, userName);
            } else {
                throw new Exception("Could not validate user");
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public User getUser(int userId) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT username, fullname FROM users "
                    + "WHERE id=?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String fullName = rs.getString("fullname");
                String userName = rs.getString("username");
                return new User(userId, fullName, userName);
            } else {
                throw new Exception("Could not find user");
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    // TODO handle username not being unique.
    public User createUser(String name, String userName, String password) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO users (fullname, username, password) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, userName);
            ps.setString(3, password);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int userId = rs.getInt(1);
            return new User(userId, name, userName);
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
