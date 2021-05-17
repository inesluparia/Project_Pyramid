package com.example.projectpyramid.domain.entities;

public class SubTask {

    private int id;
    private int subTaskId;
    private int durationInManHours;
    private String name;
    private String description;

    public SubTask(int id, int subTaskId, int durationInManHours, String name, String description) {
        this.id = id;
        this.subTaskId = subTaskId;
        this.durationInManHours = durationInManHours;
        this.name = name;
        this.description = description;
    }

    public SubTask(String name, int subTaskId, int durationInManHours, String description) {
        this.name = name;
        this.subTaskId = subTaskId;
        this.durationInManHours = durationInManHours;
        this.description = description;
    }


    // Getters

    public int getId() {
        return id;
    }

    public int getSubTaskId() {
        return subTaskId;
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

    public void setSubTaskId(int phaseId) {
        this.subTaskId = phaseId;
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
