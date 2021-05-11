package com.example.projectpyramid.domain.entities;

import java.util.ArrayList;

public class Phase {

    private String name;
    private String description;
    private int phaseId;
    private int projectId;
    private ArrayList<Task> tasks;


    public Phase(String name, String description, int phaseId, int projectId) {
        this.name = name;
        this.description = description;
        this.phaseId = phaseId;
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

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
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
