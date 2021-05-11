package com.example.projectpyramid.domain.entities;

public class User {
    private int id;
    private String fullName;
    private String userName;


    public User() { }

    public User(String fullName, String userName, int id) {
        this.fullName = fullName;
        this.userName = userName;
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
