package com.example.projectpyramid.controller;
import com.example.projectpyramid.data_access.UserMapper;
import com.example.projectpyramid.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    UserMapper userMapper = new UserMapper();

    @GetMapping("/")
    public String renderIndex(){
        return "index.html";
    }


    @GetMapping("/logintest")
    @ResponseBody
    public String loginTest() throws Exception {
        User user = userMapper.login("Andersand","1234");
        return user.getUserName();
    }

    @GetMapping("/login")
    public String login(){
        return "login method";
    }

    @GetMapping("/createuser")
    public String createUser(){
        return "createuser.html";
    }

    @GetMapping("/createproject")
    public String createProject(){
        return "createproject.html";
    }

    @GetMapping("/userpage")
    public String userPage(){
        return "userpage.html";
    }

    @GetMapping("/project")
    public String projectPage(){
        return "project.html";
    }

    @GetMapping("/myprojects")
    public String myProjects(){
        return "myprojects.html";
    }

    @GetMapping("/projectlist")
    public String allProjects(){
        return "allprojects.html";
    }
}
