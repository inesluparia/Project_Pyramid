package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.ProjectMapper;
import com.example.projectpyramid.data_access.UserMapper;
import com.example.projectpyramid.domain.entities.Project;

import java.util.ArrayList;

public class ProjectServices {

    ProjectMapper projectMapper = new ProjectMapper();
    //UserMapper userMapper = new UserMapper();

    public ArrayList<Project> getProjectsFromUserId(int userId) throws Exception {
    return projectMapper.getProjectsFromUserId(userId);
    }

/*
    public String getAuthorNameFromProj(int authorId){
        return UserMapper.getUserName(authorId);
    }


*/
}
