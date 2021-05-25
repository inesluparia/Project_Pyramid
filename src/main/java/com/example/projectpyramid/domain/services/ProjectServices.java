package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.DBManager;
import com.example.projectpyramid.data_access.mappers.*;
import com.example.projectpyramid.domain.entities.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectServices {

    private final int costPerHour = 250;
    private int programmers = 4;
    ProjectMapper projectMapper = new ProjectMapper();
    TaskMapper taskMapper = new TaskMapper();
    SubTaskMapper subTaskMapper = new SubTaskMapper();

    public Project createProject(User author, Client client, String name, String description) throws Exception {

        if (description.length() > 255)
            throw new Exception("Description is too long");

        Project project = new Project(author, client, name, description);
        project.setId(projectMapper.insert(project));
        return project.getId() > 0 ? project : null;
    }

    public Task addTask(int projectId, String name, String description) throws SQLIntegrityConstraintViolationException, DBManager.DatabaseConnectionException {
        Task task = new Task(projectId, name, description);
        task.setId(taskMapper.insert(task));
        return task.getId() > 0 ? task : null;
    }

    public SubTask addSubTask(int taskId, String name, String description, int durationInManHours) throws SQLIntegrityConstraintViolationException, DBManager.DatabaseConnectionException {
        SubTask subTask = new SubTask(taskId, name, description, durationInManHours);
        subTask.setId(subTaskMapper.insert(subTask));
        return subTask.getId() > 0 ? subTask : null;
    }

    public ArrayList<Project> getProjectsFromUserId(int userId) throws DBManager.DatabaseConnectionException {
        ArrayList<Project> projects = (ArrayList<Project>) projectMapper.findAllByUserId(userId);

        for (Project p : projects) {
            populateProject(p);
        }
        return projects;
    }

    public ArrayList<Task> getTasks(int projectId) throws DBManager.DatabaseConnectionException {
        return (ArrayList<Task>) taskMapper.findAllByProjectId(projectId);
    }

    public Task getTask(int taskId) throws DBManager.DatabaseConnectionException {
        return taskMapper.findById(taskId);
    }

    // Adds lists of tasks and subtasks to the returned project before forwarding it to controller.
    private void populateProject(Project project) throws DBManager.DatabaseConnectionException {
        ArrayList<Task> tasks = (ArrayList<Task>) taskMapper.findAllByProjectId(project.getId());
        for (Task task : tasks) {
            ArrayList<SubTask> subTasks = (ArrayList<SubTask>) subTaskMapper.findAllByTaskId(task.getId());
            task.setSubTasks(subTasks);
        }

        project.setTasks(tasks);
    }

    public Project getProjectFromId(int projectId) throws DBManager.DatabaseConnectionException {
        Project project = projectMapper.findById(projectId);
        populateProject(project);
        return project;
    }

    public int getTotalManHours(int projectId) throws DBManager.DatabaseConnectionException {
        int totalAmountOfManHours = 0;
        Project project = getProjectFromId(projectId);
        ArrayList<Task> tasks = project.getTasks();

        for (Task task : tasks) {
            ArrayList<SubTask> subTasks = task.getSubTasks();
            for (SubTask subTask : subTasks) {
                totalAmountOfManHours += subTask.getDurationInManHours();
            }
        }

        return totalAmountOfManHours;
    }

    public int getTotalCost(int projectId) throws DBManager.DatabaseConnectionException {
        int totalDuration = getTotalManHours(projectId);
        return totalDuration * costPerHour;
    }

//    public String getTotalCalenderTime(int projectId) throws Exception {
//        //a month has in average 4.35 weeks and therefore 152.25 working hours
//        //a week has 35 working hours
//        // a day has 7 working hours
//
//        //int manHours= getTotalManHours(projectId);
//        double manHours = 600;
//        int months = (int) (manHours / 152.25);
//        int weeks = (int) ((manHours % 152.25) / 35);
//        int days = (int) (((manHours % 152.25) % 35) / 7);
//        return "There are " + totalEmployees + " programmers assigned to this project\n" +
//                "This project will take approximately " + months + " months, " + weeks +
//                " weeks and " + days + " working days to be completed.";
//    }

    public LocalDate getCompletionDate(int projectId) throws Exception {
        LocalDate date = LocalDate.now();
        int manHours = getTotalManHours(projectId);
        //a month has in average 4.35 weeks and therefore 152.25 working hours
        //a week has 35 working hours
        // a day has 7 working hours
        int months = (int) (manHours / 152.25);
        int weeks = (int) ((manHours % 152.25) / 35);
        int days = (int) (((manHours % 152.25) % 35) / 7);
        date.plusMonths(months);
        date.plusWeeks(weeks);
        date.plusDays(days);
        return date;
    }

    public void updateProject(int id, User author, Client client, String name, String description) throws DBManager.DatabaseConnectionException {
        projectMapper.update(new Project(id, author, client, name, description));
    }

    public void updateTask(int id, int projectId, String name, String description) throws DBManager.DatabaseConnectionException {
        taskMapper.update(new Task(id, projectId, name, description));
    }

    public void updateSubTask(int id, int taskId, String name, String description, int durationInManHours) throws DBManager.DatabaseConnectionException {
        subTaskMapper.update(new SubTask(id, taskId, name, description, durationInManHours));
    }

    public SubTask getSubtask(int subtaskId) throws DBManager.DatabaseConnectionException {
        return subTaskMapper.findById(subtaskId);
    }
}
