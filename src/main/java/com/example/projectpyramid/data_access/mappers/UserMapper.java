package com.example.projectpyramid.data_access.mappers;
import com.example.projectpyramid.data_access.DBManager;
import com.example.projectpyramid.data_access.Mapper;
import com.example.projectpyramid.domain.entities.User;

import java.sql.*;

public class UserMapper implements Mapper<User> {

    /*method that recieves a User object, inserts it in the DB and returns the generated userId
    * */
    public int insert(User user) throws Exception {
        String query = "INSERT INTO users (fullname, username, password) VALUES (?, ?, ?)";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int userId = resultSet.getInt(1);
            return userId;

        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new Exception("Username is already taken.");
        }
    }

    public void update(User user) throws Exception {
        String query = "UPDATE users SET fullname = ?, username = ?, password = ? WHERE id = ?";
        Connection connection = DBManager.getConnection();
        boolean wasSuccessful = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());

            wasSuccessful = preparedStatement.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("An Exception occured:");
            ex.printStackTrace();
        } finally {
            connection.clearWarnings();
            connection.close();
        }
    }

    public void delete(User user) {
        String query = "DELETE FROM users WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // TODO handle this exception.
        }
    }

    /*method that recieves login credentials and returns the userId if successfull*/
    public int login(String userName, String password) throws Exception {
        Connection connection = DBManager.getConnection();
        try {
            String query = "SELECT id, fullname FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String fullName = resultSet.getString("fullname");
                int userId = resultSet.getInt("id");
                return userId;
            } else {
                throw new Exception("Could not validate user");
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public User get(int userId) throws Exception {
        try {
            Connection connection = DBManager.getConnection();
            String query = "SELECT username, fullname FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String fullName = resultSet.getString("fullname");
                String userName = resultSet.getString("username");
                return new User(userId, fullName, userName);
            } else {
                throw new Exception("Could not find user");
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
