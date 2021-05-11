package com.example.projectpyramid.domain.entities;

public class Project {

    private int id;
    private User author;
    private Client client;
    private String projectName, description;
    private boolean isActive;

    public Project() {
        isActive = false;
    }

    public Project(int id,
                   User author,
                   Client client,
                   String projectName,
                   String description) {
        this.id = id;
        this.author = author;
        this.client = client;
        this.projectName = projectName;
        this.description =  description;
    }

    //Getters

    public int getId() {
        return this.id;
    }

    public User getAuthor() {
        return this.author;
    }

    public Client getClient() {
        return this.client;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    //Setters

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
