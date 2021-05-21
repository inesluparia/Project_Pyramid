package com.example.projectpyramid.domain.entities;

public class User {

    private int id;
    private String fullName;
    private String userName;
    private String password;

    public User(int id, String fullName, String userName) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
    }

    public User(String fullName, String userName, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
