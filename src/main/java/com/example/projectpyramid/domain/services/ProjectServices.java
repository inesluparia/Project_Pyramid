package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.mappers.TaskMapper;
import com.example.projectpyramid.data_access.mappers.ProjectMapper;
import com.example.projectpyramid.data_access.mappers.SubTaskMapper;
import com.example.projectpyramid.domain.entities.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectServices {

    private final int costPerHour = 250;
    private int programmers = 4;
    private ProjectMapper projectMapper;
    private TaskMapper taskMapper;
    private SubTaskMapper subTaskMapper;

    public ProjectServices() {
        projectMapper = new ProjectMapper();
        taskMapper = new TaskMapper();
        subTaskMapper = new SubTaskMapper();
    }

    public int getProgrammers() {
        return programmers;
    }

    public int createProject(User author, Client client, String name, String description) throws Exception {

        if (description.length() > 255)
            throw new Exception("Description is too long");

        Project project = new Project(author, client, name, description);
        project.setId(projectMapper.insert(project));
        return project.getId();
    }

    public Task addTask(String name, String description, int projectId) throws Exception {
        Task task = new Task(projectId, name, description);
        task.setId(taskMapper.insert(task));
        return task.getId() > 0 ? task : null;
    }

    public SubTask addSubTask(String name, String taskId, String durationInManHours, String description) throws Exception {
        int intTaskId = Integer.parseInt(taskId);
        int intDurationInManHours = Integer.parseInt(durationInManHours);


        SubTask subTask = new SubTask(intTaskId, name, description, intDurationInManHours);
        subTaskMapper.insert(subTask);

        return subTask.getId() > 0 ? subTask : null;
    }

    public ArrayList<Project> getProjectsFromUserId(int userId) throws Exception {
        ArrayList<Project> projects = projectMapper.findAllByUserId(userId);

        //ArrayList<Project> projects = (ArrayList<Project>) projectMapper.findAllByUserId(userId);

        for (Project p : projects) {
            populateProject(p);
        }

        return projects;
    }

    public ArrayList<Task> getTasks(int projectId) {
        ArrayList<Task> tasks = taskMapper.findAllByProjectId(projectId);

        return tasks;
    }

    public Task getTask(int taskId) {
        // Task task = taskMapper.getTask(taskId);
        Task task = taskMapper.findById(taskId);

        return task;
    }

    //Adds lists of tasks and subtasks to the returned project before forwarding it to controller
    private void populateProject(Project project) {

        ArrayList<Task> tasks = taskMapper.findAllByProjectId(project.getId());
        for (Task task : tasks) {
            ArrayList<SubTask> subTasks = subTaskMapper.findAllByTaskId(task.getId());
            task.setSubTasks(subTasks);
        }
        project.setTasks(tasks);
    }

    public Project getProjectFromId(int projectId) {
        Project project = projectMapper.findById(projectId);
        populateProject(project);
        return project;
    }

    public int getTotalManHours(int projectId) {
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

    public int getTotalCost(int manHours) {
        if (manHours < 0)
            throw new IllegalArgumentException("man hours cannot be negative");
        return manHours * costPerHour;
    }

    public LocalDate getCompletionDate(int manHours) {
        LocalDate date = LocalDate.now();
        //a week has 35 working hours
        // a day has 7 working hours
        LocalDate result = date.plusWeeks(manHours / 35);
        LocalDate finalResult = result.plusDays((manHours % 35) / 7);
        return finalResult;
    }

    // Need HomeController edits to implement new method
    public void updateTask(String taskName, String taskDescription, int taskId) {
        taskMapper.update(taskName, taskDescription, taskId);
    }

    // Need HomeController edits to implement new method
    public void updateProject(String projectName, String description, int projectId) {
        projectMapper.update(projectName, description, projectId);
    }

    // Need HomeController edits to implement new method
    public void updateSubTask(String name, String description, String durationInManHours, int subtaskId) {
        int intDurationInManHours = Integer.parseInt(durationInManHours);
        subTaskMapper.update(name, description, intDurationInManHours, subtaskId);
    }

    public SubTask getSubtask(int subtaskId) {
        return subTaskMapper.findById(subtaskId);
    }

    public void deleteTask(int taskId) {
        taskMapper.delete(taskId);
    }

    public void deleteSubtask(int subTaskId) {
        subTaskMapper.delete(subTaskId);
    }

    public void deleteProject(int projectId) {
        projectMapper.delete(projectId);
    }

}