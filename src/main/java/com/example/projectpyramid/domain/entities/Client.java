package com.example.projectpyramid.domain.entities;

public class Client {

    private int id;
    private String name;
    private int cvr;

    public Client() { }

    public Client(int id, String name, int cvr) {
        this.id = id;
        this.name = name;
        this.cvr = cvr;
    }

    public Client(String name, int cvr) {
        this.name = name;
        this.cvr = cvr;
    }

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
