package com.example.projectpyramid.domain.entities;

public class Task {

    private int id;
    private int phaseId;
    private int durationInHours;
    private String name;
    private String description;

    public Task(int id, int phaseId, int durationInHours, String name, String description) {
        this.id = id;
        this.phaseId = phaseId;
        this.durationInHours = durationInHours;
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

    public int getDurationInHours() {
        return this.durationInHours;
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
        this.durationInHours = durationInHours;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
