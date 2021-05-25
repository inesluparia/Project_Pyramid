package com.example.projectpyramid.domain.entities;

public class User {

    private int id;
    private String fullname;
    private String username;
    private String password;

    public User() { }

    public User(int id, String fullname, String username) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
    }

    public User(String fullname, String username, String password) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
