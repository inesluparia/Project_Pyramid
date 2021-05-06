package com.example.projectpyramid.Domain;

public class Project {

    private String author, projectname, description;
    private double duration;

    public Project(){

    }

    public Project( String author, String projectname, String description, double duration){

        this.author = author;
        this.projectname = projectname;
        this.description =  description;
        this.duration = duration;

    }

    //Getters
    public String getAuthor(){

        return this. author;
    }

    public String getProjectname(){

        return this.projectname;
    }

    public double getDuration(){

        return this.duration;
    }

    //Setters
    public void setProjectname(String newProjectName){

        this.projectname = newProjectName;
    }

    public void setDuration(double newDuration){

        this.duration = newDuration;
    }


}
