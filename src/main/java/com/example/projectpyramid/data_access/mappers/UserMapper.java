package com.example.projectpyramid.data_access.mappers;
import com.example.projectpyramid.data_access.DBManager;
import com.example.projectpyramid.data_access.Mapper;
import com.example.projectpyramid.domain.entities.User;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class UserMapper implements Mapper<User> {

    /**
     * Inserts a user into the database.
     *
     * @param user The user to be inserted into the database.
     * @return The id of the newly inserted user, 0 if unable to insert.
     * @throws SQLIntegrityConstraintViolationException If the username is already taken.
     */
    public int insert(@NotNull User user) throws SQLIntegrityConstraintViolationException, DBManager.DatabaseConnectionException {
        String query = "INSERT INTO users (fullname, username, password) VALUES (?, ?, ?)";
        int userId = 0;

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFullname());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                userId = resultSet.getInt(1);

        } catch (SQLIntegrityConstraintViolationException ex) {
            throw ex;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userId;
    }

    /**
     * Updates a user's changes to the database.
     *
     * @param user The user that has been changed.
     */
    public void update(@NotNull User user) throws DBManager.DatabaseConnectionException {
        String query = "UPDATE users SET fullname = ?, username = ?, password = ? WHERE id = ?";

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFullname());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes a user from the database.
     * TODO unit test to ensure authored projects' author_id gets set to null.
     *
     * @param user The user to be deleted from the database.
     */
    public void delete(@NotNull User user) throws DBManager.DatabaseConnectionException {
        String query = "DELETE FROM users WHERE id = ?";

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Finds a user from their user id.
     *
     * @param userId The id of the user to find.
     * @return The found user, null if not found.
     */
    public User findById(int userId) throws DBManager.DatabaseConnectionException {
        String query = "SELECT username, fullname FROM users WHERE id = ?";

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String fullName = resultSet.getString("fullname");
                String userName = resultSet.getString("username");
                return new User(userId, fullName, userName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Attempts to authenticate a user login.
     *
     * @param username The username of the user logging in.
     * @param password The password of the user logging in.
     * @return Id of the user logging in, 0 if unable to authenticate.
     */
    public int login(String username, String password) throws DBManager.DatabaseConnectionException {
        String query = "SELECT id FROM users WHERE username = ? AND password = ?";
        int userId = 0;

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                userId = resultSet.getInt("id");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userId;
    }
}
