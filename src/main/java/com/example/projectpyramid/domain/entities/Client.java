package com.example.projectpyramid.domain.entities;

public class Client {

    private int id;
    private String name;
    private int cvr;


    public void setId(int clientId) {
        this.id = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCvr(int cvr) {
        this.cvr = cvr;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCvr() {
        return cvr;
    }
}
