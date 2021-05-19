package com.example.projectpyramid.domain.entities;

public class SubTask {

    private int id;
    private int taskId;
    private int durationInManHours;
    private String name;
    private String description;

    public SubTask(int id, int taskId, int durationInManHours, String name, String description) {
        this.id = id;
        this.taskId = taskId;
        this.durationInManHours = durationInManHours;
        this.name = name;
        this.description = description;
    }

    public SubTask(String name, int subTaskId, int durationInManHours, String description) {
        this.name = name;
        this.taskId = subTaskId;
        this.durationInManHours = durationInManHours;
        this.description = description;
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
