package com.example.projectpyramid.domain.entities;

import java.util.ArrayList;

public class Project {

    private int id;
    private User author;
    private Client client;
    private String name, description;
    private boolean isActive;
    private ArrayList<Task> tasks;

    public Project(int id, User author, Client client, String name, String description) {
        this.id = id;
        this.author = author;
        this.client = client;
        this.name = name;
        this.description = description;
        this.tasks = new ArrayList<>();
    }

    public Project(User author, Client client, String name, String description) {
        this.author = author;
        this.client = client;
        this.name = name;
        this.description = description;
    }

    //Getters

    public int getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Client getClient() {
        return client;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
