package com.example.projectpyramid.domain.entities;

public class Task {

    private int id;
    private int phaseId;
    private int durationInManHours;
    private String name;
    private String description;

    public Task(int id, int phaseId, int durationInManHours, String name, String description) {
        this.id = id;
        this.phaseId = phaseId;
        this.durationInManHours = durationInManHours;
        this.name = name;
        this.description = description;
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public int getDurationInManHours() {
        return durationInManHours;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    public void setDurationInManHours(int durationInManHours) {
        this.durationInManHours = durationInManHours;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
