package com.example.projectpyramid.domain.entities;

public class Client {

    private int id;
    private String name;
    private int cvr;

    // Getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCvr() {
        return cvr;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCvr(int cvr) {
        this.cvr = cvr;
    }
}
