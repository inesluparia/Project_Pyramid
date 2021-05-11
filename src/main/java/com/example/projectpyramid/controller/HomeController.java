package com.example.projectpyramid.controller;

import com.example.projectpyramid.data_access.UserMapper;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.User;
import com.example.projectpyramid.domain.services.ProjectServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Objects;

@Controller
public class HomeController {
    UserMapper userMapper = new UserMapper();
    ProjectServices projectServices = new ProjectServices();


    @GetMapping("/")
    public String renderIndex() {
        return "index.html";
    }


    @GetMapping("/logintest")
    @ResponseBody
    public String loginTest() throws Exception {
        User user = userMapper.login("Andersand", "1234");
        return user.getUserName();
    }

    @PostMapping("/login")
    public String login(WebRequest request) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userMapper.login(username, password);
        request.setAttribute("userId", user.getId(), WebRequest.SCOPE_SESSION);
        request.setAttribute("name", user.getFullName(), WebRequest.SCOPE_SESSION);
        return "redirect:userpage";
    }

    @GetMapping("/createuser")
    public String createUser() {
        return "createuser.html";
    }

    @GetMapping("/createproject")
    public String createProject() {
        return "createproject.html";
    }

    @GetMapping("/userpage")
    public String userPage(WebRequest request, Model model) throws Exception {
        String userId = (String) request.getAttribute("userId", WebRequest.SCOPE_SESSION);
        String name = (String) request.getAttribute("name", WebRequest.SCOPE_SESSION);
        //evt lave en metode updateProjects() som kalder på de nedestående linjer
        ArrayList<Project> projects = projectServices.getProjectsFromUserId(userId);
        model.addAttribute("projects", projects);
        model.addAttribute("name", name);
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