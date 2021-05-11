package com.example.projectpyramid.domain.entities;

public class Task {

    private int phaseId;
    private int taskId;
    private int durationInHours;
    private String name;
    private String description;

    public Task(int phaseId, int taskId, int durationInHours, String name, String description) {
        this.phaseId = phaseId;
        this.taskId = taskId;
        this.durationInHours = durationInHours;
        this.name = name;
        this.description = description;
    }




}
