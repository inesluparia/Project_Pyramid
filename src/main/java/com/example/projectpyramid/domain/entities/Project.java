package com.example.projectpyramid.domain.entities;

public class Project {

    private int projectId;
    private String author, client, projectName, description;

    public Project(){

    }

    public Project( int projectId,
                    String author,
                    String client,
                    String projectName,
                    String description){

        this.projectId = projectId;
        this.author = author;
        this.client = client;
        this.projectName = projectName;
        this.description =  description;

    }

    //Getters
    public int getProjectId(){

        return projectId;
    }

    public String getAuthor(){

        return this.author;
    }

    public String getClient(){

        return this.client;
    }

    public String getDescription(){

        return this.description;
    }

    public String getProjectname(){

        return this.projectName;
    }

    //Setters
    public void setAuthor(String newAuthor){

        this.author = newAuthor;
    }

    public void setDescription(String editDescription){

        this.description = editDescription;
    }

    public void setProjectname(String newProjectName){

        this.projectName = newProjectName;
    }


}
