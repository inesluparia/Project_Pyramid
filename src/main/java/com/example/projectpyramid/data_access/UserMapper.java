package com.example.projectpyramid.data_access;
import com.example.projectpyramid.domain.entities.User;

import java.sql.*;

public class UserMapper {

    public static String getUserName(int authorId) {
        return null;
    }


    public void insert(User user) {

    }

    public void update(User user) {

    }

    public void delete(User user) { String query = "DELETE FROM users WHERE id =" + user.getId() +";"; }


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
                    + "WHERE username=? AND password=?";
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
                    + "WHERE id=?";
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

