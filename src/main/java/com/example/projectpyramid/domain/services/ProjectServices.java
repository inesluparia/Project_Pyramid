package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.PhaseMapper;
import com.example.projectpyramid.data_access.ProjectMapper;
import com.example.projectpyramid.data_access.SubTaskMapper;
import com.example.projectpyramid.domain.entities.Phase;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.SubTask;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectServices {

    private final int costPerHour = 250;
    private int programmers = 4;
    ProjectMapper projectMapper = new ProjectMapper();
    PhaseMapper phaseMapper = new PhaseMapper();
    SubTaskMapper subTaskMapper = new SubTaskMapper();


    public int createProject(String userId, String name, String description, String clientId) throws Exception {
        int userIdInt = Integer.parseInt(userId);
        int clientIdInt = Integer.parseInt(clientId);


        // TODO er det nødvendigt at lave et objekt af projekt til metoden?
       return  projectMapper.createProject(name, userIdInt, clientIdInt, description);

    }


    public Phase addPhase(String name, String description, int projectId) throws Exception {
        Phase phase = new Phase(name, description, projectId);
        phaseMapper.addPhase(phase);
        return phase;
    }


   public SubTask addSubTask(String name, String phaseId, String durationInManHours, String description) throws Exception {
       int intPhaseId = Integer.parseInt(phaseId);
       int intDurationInManHours = Integer.parseInt(durationInManHours);

       SubTask subTask = new SubTask(name, intPhaseId, intDurationInManHours, description);
       subTaskMapper.addSubTask(subTask);
       return subTask;
   }


    public ArrayList<Project> getProjectsFromUserId(String userId) throws Exception {
        int intUserId = Integer.parseInt(userId);
        ArrayList<Project> projects = projectMapper.getProjectsFromUserId(intUserId);
        for (Project p : projects) {
            populateProject(p);
        }
        return projects;
    }

    public ArrayList<Phase> getPhases(int projectId) {
        ArrayList<Phase> phases = phaseMapper.getPhases(projectId);

        return phases;
    }

    //Adds lists of phases and tasks to the returned project before forwarding it to controller
    private void populateProject(Project project) {
        ArrayList<Phase> phases = phaseMapper.getPhases(project.getId());
        for (Phase phase : phases) {
            ArrayList<SubTask> subTasks = subTaskMapper.getSubTasks(phase.getId());
            phase.setSubTasks(subTasks);
        }
        project.setPhases(phases);
    }

    public Project getProjectFromId(int projectId) throws Exception {
        Project project = projectMapper.getProject(projectId);
        populateProject(project);
        return project;
    }


    public int getTotalManHours(int projectId) throws Exception {
        int total= 0;
        Project project = getProjectFromId(projectId);
        ArrayList<Phase> phases = project.getPhases();

        //FIXME: en dobbelt for-løkke? fy-ha
        for (Phase phase : phases) {
            ArrayList<SubTask> subTasks = phase.getSubTasks();
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

}