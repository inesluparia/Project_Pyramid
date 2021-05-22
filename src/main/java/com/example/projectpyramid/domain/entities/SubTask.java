package com.example.projectpyramid.domain.entities;

public class SubTask {

    private int id;
    private int taskId;
    private int durationInManHours;
    private String name;
    private String description;

    public SubTask() { }

    public SubTask(int id, int taskId, String name, String description, int durationInManHours) {
        this.id = id;
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.durationInManHours = durationInManHours;
    }

    public SubTask(int taskId, String name, String description, int durationInManHours) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.durationInManHours = durationInManHours;
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getTaskId() {
        return taskId;
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

    public void setTaskId(int phaseId) {
        this.taskId = phaseId;
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
