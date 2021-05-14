package com.example.projectpyramid.domain.entities;

import java.util.ArrayList;

public class Phase {

    private int id;
    private int projectId;
    private String name;
    private String description;
    private ArrayList<Task> tasks;

    public Phase(int id, int projectId, String name, String description) {
        this.id = id;
        this.projectId = projectId;
        tasks = new ArrayList<>();
    }

    public Phase(String name, String description, int projectId) {
        this.name = name;
        this.description = description;
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.description = description;
        this.tasks = new ArrayList<>();
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}