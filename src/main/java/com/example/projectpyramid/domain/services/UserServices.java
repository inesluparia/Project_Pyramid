package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.UserMapper;
import com.example.projectpyramid.domain.entities.User;

public class UserServices {

    UserMapper userMapper = new UserMapper();

    public User login(String userName, String password) throws Exception{
        User user = userMapper.login(userName, password);
        return user;
    }

    public User createUser(String fullName, String userName, String password, String confirmPassword) throws Exception {

        if (!confirmPassword.equals(password))
            throw new Exception("Passwords do not match");

        if (userName.isEmpty())
            throw new Exception("User name field can not be empty");

        // If passwords match and username is not empty...

        // TODO evt. check with database if userName is unique.
        return userMapper.createUser(fullName, userName, password);
    }

    // TODO implement changePassword() method.
    public void changePassword(String oldPassword, String password, String confirmPassword) {

    }
}