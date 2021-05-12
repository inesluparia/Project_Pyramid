package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.ProjectMapper;
import com.example.projectpyramid.data_access.UserMapper;
import com.example.projectpyramid.domain.entities.Phase;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.Task;

import java.util.ArrayList;

public class ProjectServices {

    ProjectMapper projectMapper = new ProjectMapper();
    //UserMapper userMapper = new UserMapper();

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
        ArrayList<Phase> phases =  projectMapper.getPhases(project.getId());
        for (Phase phase : phases) {
            ArrayList<Task> tasks = projectMapper.getTasks(phase.getPhaseId());
            phase.setTasks(tasks);
        }
        project.setPhases(phases);
    }

    public Project getProject(int projectId) throws Exception {
      Project project = projectMapper.getProject(projectId);
      populateProject(project);
      return project;
    }

/*
    public String getAuthorNameFromProj(int authorId){
        return UserMapper.getUserName(authorId);
    }
*/

    public int getTotalProjectDuration(int projectId) throws Exception {
        int totalProjectDuration = 0;
        Project project = getProject(projectId);
        ArrayList<Phase> phases = project.getPhases();
        for (Phase phase : phases) {
            ArrayList<Task> tasks = phase.getTasks();
            for (Task task : tasks) {
                totalProjectDuration += task.getDurationInHours();
            }
        }

        return totalProjectDuration;
    }
}
