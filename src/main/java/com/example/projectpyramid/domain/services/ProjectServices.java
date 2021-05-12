package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.PhaseMapper;
import com.example.projectpyramid.data_access.ProjectMapper;
import com.example.projectpyramid.data_access.TaskMapper;
import com.example.projectpyramid.data_access.UserMapper;
import com.example.projectpyramid.domain.entities.Phase;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.Task;

import java.util.ArrayList;

public class ProjectServices {

    ProjectMapper projectMapper = new ProjectMapper();
    PhaseMapper phaseMapper = new PhaseMapper();
    TaskMapper taskMapper = new TaskMapper();

    public ArrayList<Project> getProjectsFromUserId(String userId) throws Exception {
       int intUserId = Integer.parseInt(userId);
        ArrayList<Project> projects = projectMapper.getProjectsFromUserId(intUserId);
        for (Project p : projects){
            populateProject(p);
        }
        return projects;
    }

    //Adds lists of phases and tasks to the returned project before forwarding it to controller
    private void populateProject(Project project) {
        ArrayList<Phase> phases =  phaseMapper.getPhases(project.getId());
        for (Phase phase : phases) {
            ArrayList<Task> tasks = taskMapper.getTasks(phase.getPhaseId());
            phase.setTasks(tasks);
        }
        project.setPhases(phases);
    }

    public Project getProjectFromId(int projectId) throws Exception {
      Project project = projectMapper.getProject(projectId);
      populateProject(project);
      return project;
    }

/*
    public String getAuthorNameFromProj(int authorId){
        return UserMapper.getUserName(authorId);
    }
*/

    public int getTotalDurationInHours(int projectId) throws Exception {
        int totalDuration = 0;
        Project project = getProjectFromId(projectId);
        ArrayList<Phase> phases = project.getPhases();
        for (Phase phase : phases) {
            ArrayList<Task> tasks = phase.getTasks();
            for (Task task : tasks) {
                totalDuration += task.getDurationInHours();
            }
        }

        return totalDuration;
    }

    public int getTotalCost(int projectId) throws Exception {
        int totalDuration = getTotalDurationInHours(projectId);
        int costPerHour = 2;
        return totalDuration * costPerHour;
    }
}
