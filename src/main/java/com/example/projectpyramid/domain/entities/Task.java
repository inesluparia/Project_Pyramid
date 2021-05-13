package com.example.projectpyramid.domain.entities;

public class Task {

    private int id;
    private int phaseId;
    private int durationInManHours;
    private String name;
    private String description;

    public Task(int taskId, int phaseId, int durationInHours, String name, String description) {
        this.id = taskId;
        this.phaseId = phaseId;
        this.durationInManHours = durationInHours;
        this.name = name;
        this.description = description;
    }

    // Getters

    public int getId() {
        return this.id;
    }

    public int getPhaseId() {
        return this.phaseId;
    }

    public int getDurationInManHours() {
        return this.durationInManHours;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    public void setDurationInHours(int durationInHours) {
        this.durationInManHours = durationInHours;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
