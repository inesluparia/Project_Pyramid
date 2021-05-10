package com.example.projectpyramid.domain.entities;

public class Project {

    private int projectId, authorId, clientId;
    //private Client client;
    private String projectName, description;
    private boolean isActive;

    public Project(){
    isActive = false;
    }

    public Project( int projectId,
                    int authorId,
                    int clientId,
                    String projectName,
                    String description){

        this.projectId = projectId;
        this.authorId = authorId;
        this.clientId = clientId;
        this.projectName = projectName;
        this.description =  description;
    }

    //Getters
    public int getProjectId(){

        return projectId;
    }

    public String getDescription(){

        return this.description;
    }

    public String getProjectName(){

        return this.projectName;
    }

    //Setters
    public void setActive(){
        isActive = true;
    }

    public void setDescription(String editDescription){

        this.description = editDescription;
    }

    public void setProjectName(String newProjectName){

        this.projectName = newProjectName;
    }


}
