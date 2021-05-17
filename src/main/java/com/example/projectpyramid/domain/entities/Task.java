package com.example.projectpyramid.domain.entities;

import java.util.ArrayList;

public class Task {

    private int id;
    private int projectId;
    private String name;
    private String description;
    private ArrayList<SubTask> subTasks;

    public Task(int id, int projectId, String name, String description) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.subTasks = new ArrayList<>();
    }

    public Task(String name, String description, int projectId) {
        this.name = name;
        this.description = description;
        this.projectId = projectId;
    }

/*

    public void setName(String name) {
        this.name = name;
        this.description = description;
        this.tasks = new ArrayList<>();
    }
*/

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

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
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

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }
}