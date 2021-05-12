package com.example.projectpyramid.domain.entities;

import java.util.ArrayList;

public class Phase {

    private String name;
    private String description;
    private int id;
    private int projectId;
    private ArrayList<Task> tasks;


    public Phase(String name, String description, int phaseId, int projectId) {
        this.name = name;
        this.description = description;
        this.id = phaseId;
        this.projectId = projectId;
        tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int phaseId) {
        this.id = phaseId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
