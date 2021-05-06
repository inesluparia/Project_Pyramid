package com.example.projectpyramid.DataAccess;

import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

    private static String user;
    private static String password;
    private static String url;
    private static Connection connection = null;
    public static Connection getConnection() {

        if (connection != null) return connection;
        System.out.println("Lukasz" + System.getProperty("user.dir"));
        System.out.println("Whats up, Im here");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Im also here");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
        try (InputStream input = new FileInputStream("./src/main/resources/application.properties")) {
            System.out.println("Did you try something else");
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("url").toString();
            user = properties.getProperty("user").toString();
            password = properties.getProperty("password").toString();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            System.out.println("well at least I reach this point");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}