package com.example.projectpyramid.data_access;
import com.example.projectpyramid.domain.entities.Phase;
import com.example.projectpyramid.domain.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class UserMapper {

    public static String getUserName(int authorId) throws Exception{

        String query = "SELECT author FROM users WHERE id = ?;";
        Connection connection = DBManager.getConnection();
        String username = "";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, authorId);
            resultSet = preparedStatement.getResultSet();
            username = resultSet.getString("username");
        } catch (SQLException ex) {
            System.out.println("An Exception occured:");
            ex.printStackTrace();
        }finally{
            connection.clearWarnings();
            connection.close();
        }

        return username;
    }


    public boolean insert(User user) throws Exception{

        String query = "INSERT INTO users(fullname, username, password) VALUES(?,?,?) " +
                "WHERE username = ?;";
        Connection connection = DBManager.getConnection();
        boolean queryComplete = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getId());
            queryComplete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("An Exception occured:");
            ex.printStackTrace();
        }finally{
            connection.clearWarnings();
            connection.close();
        }

        return queryComplete;

    }

    public boolean update(User user) throws Exception{

        String query = "UPDATE usr FROM users WHERE id = ?;";
        Connection connection = DBManager.getConnection();
        boolean queryComplete = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getId());
            queryComplete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("An Exception occured:");
            ex.printStackTrace();
        }finally {
            connection.clearWarnings();
            connection.close();
        }

        return queryComplete;
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


/*
        public String getUserName(String userId) throws Exception {
            try {
                Connection con = DBManager.getConnection();
                String SQL = "SELECT username FROM user_data WHERE user_id=?";
                PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, userId);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String userName = rs.getString("username");
                return userName;

            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
*/
/*
    public User findByName(String name) {

    }*/

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
                int id = rs.getInt("id");
                User user = new User(fullName, userName, id);
                return user;
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
                User user = new User(fullName, userName, userId);
                return user;
            } else {
                throw new Exception("Could not find user");
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

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
            int id = rs.getInt(1);
            User user = new User(name, userName, id);
            return user;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

}

