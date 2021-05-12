package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.UserMapper;
import com.example.projectpyramid.domain.entities.User;

public class UserServices {

    UserMapper userMapper = new UserMapper();


    public User login(String userName, String password) throws Exception{
        User user = userMapper.login(userName, password);
        return user;
    }

    public User createUser(String name, String userName, String pass1, String pass2) throws Exception {
        // if passwords match, email is valid and username is not empty....
        if (!pass1.equals(pass2)) {   throw new Exception("Passwords do not match");        }
        if (userName.equals("")) {   throw new Exception("User name field can not be empty"); }
        //TODO evt. check with database if userName is unique.
        return userMapper.createUser(name, userName, pass1);
    }

    //TODO
    public void changePassword(String oldPassword, String pass1, String pass2){}
}