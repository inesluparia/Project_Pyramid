package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.mappers.TaskMapper;
import com.example.projectpyramid.data_access.mappers.ProjectMapper;
import com.example.projectpyramid.data_access.mappers.SubTaskMapper;
import com.example.projectpyramid.domain.entities.Task;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.SubTask;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectServices {

    private final int costPerHour = 250;
    private int programmers = 4;
    ProjectMapper projectMapper = new ProjectMapper();
    TaskMapper taskMapper = new TaskMapper();
    SubTaskMapper subTaskMapper = new SubTaskMapper();

    public int createProject(int userId, String name, String description, String clientId) throws Exception {
        if (description.length() > 255) {
            throw new Exception("Description is too long");
        }

        //int userIdInt = Integer.parseInt(userId);
        int clientIdInt = Integer.parseInt(clientId);

        return  projectMapper.insertProject(name, userId, clientIdInt, description);
    }

    public Task addTask(String name, String description, int projectId) throws Exception {
        Task task = new Task(projectId, name, description);
        taskMapper.insertTask(task);
        return task;
    }

    public SubTask addSubTask(String name, String taskId, String durationInManHours, String description) throws Exception {
        int intTaskId = Integer.parseInt(taskId);
        int intDurationInManHours = Integer.parseInt(durationInManHours);

        SubTask subTask = new SubTask(intTaskId, name, description, intDurationInManHours);
        subTaskMapper.insertSubTask(subTask);

        return subTask;
    }

    public ArrayList<Project> getProjectsFromUserId(int userId) throws Exception {
        ArrayList<Project> projects = projectMapper.getProjectsFromUserId(userId);

        for (Project p : projects) {
            populateProject(p);
        }

        return projects;
    }

    public ArrayList<Task> getTasks(int projectId) {
        ArrayList<Task> tasks = taskMapper.getTasks(projectId);

        return tasks;
    }

    public Task getTask(int taskId) {
        Task task = taskMapper.getTask(taskId);
        return task;
    }

    //Adds lists of tasks and subtasks to the returned project before forwarding it to controller
    private void populateProject(Project project) {
        ArrayList<Task> tasks = taskMapper.getTasks(project.getId());
        for (Task task : tasks) {
            ArrayList<SubTask> subTasks = subTaskMapper.getSubTasks(task.getId());
            task.setSubTasks(subTasks);
        }
        project.setTasks(tasks);
    }

    public Project getProjectFromId(int projectId) throws Exception {
        Project project = projectMapper.getProject(projectId);
        populateProject(project);
        return project;
    }


    public int getTotalManHours(int projectId) throws Exception {
        int total= 0;
        Project project = getProjectFromId(projectId);
        ArrayList<Task> tasks = project.getTasks();
        for (Task task : tasks) {
            ArrayList<SubTask> subTasks = task.getSubTasks();
            for (SubTask subTask : subTasks) {
                total += subTask.getDurationInManHours();
            }
        }
        return total;
    }

    public int getTotalCost(int projectId) throws Exception {
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
        int manHours= getTotalManHours(projectId);
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

    public void updateTask(String taskName, String taskDescription, int taskId) {
        taskMapper.update(taskName, taskDescription, taskId);
    }

    public void updateProject(String projectName, String description, int projectId) {
        projectMapper.update(projectName, description, projectId);
    }

    public void updateSubTask(String name, String description, String durationInManHours, int subtaskId) {
        int intDurationInManHours = Integer.parseInt(durationInManHours);
    subTaskMapper.update(name, description, intDurationInManHours, subtaskId);
    }

    public SubTask getSubtask(int subtaskId) {
        return subTaskMapper.getSubtask(subtaskId);
    }
}