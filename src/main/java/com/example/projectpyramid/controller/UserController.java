package com.example.projectpyramid.controller;

import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.services.ProjectServices;
import com.example.projectpyramid.domain.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@Controller
public class UserController {

    UserServices userServices = new UserServices();
    ProjectServices projectServices = new ProjectServices();


    @GetMapping("/logout")
    public String logUd(WebRequest request) {
        request.setAttribute("userId", null, WebRequest.SCOPE_SESSION);
        return "index.html";
    }



    /**
     * User logs in to create a new project or see existing projects.
     * Login method in userServices is called for validation.
     * The returned users name and ID are then saved in the model
     * to redirect to the userpage.
     * @param request WebRequest.class from the Spring framework
     * @return redirect:userpage
     * @throws Exception Because of interaction with MySQL DB
     * @see com.example.projectpyramid.data_access.mappers.UserMapper#login(String, String)
     */
    @PostMapping("/login")
    public String login(WebRequest request) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int userId = userServices.login(username, password);
        request.setAttribute("userId", userId, WebRequest.SCOPE_SESSION);
        return "redirect:userpage";
    }

    /**
     * This method runs after log-in validation,
     * the getProjectsFromUserId method from projectServices is called
     * and a list of the users projects is saved in the model
     * before returning userpage.html
     * @param request
     * @param model
     * @return userpage.html
     * @throws Exception
     */
    @GetMapping("/userpage")
    public String userPage(WebRequest request, Model model) throws Exception {
        int userId = (int) request.getAttribute("userId", WebRequest.SCOPE_SESSION);
        request.setAttribute("name", userServices.getUserFromId(userId).getFullName(), WebRequest.SCOPE_SESSION);
        ArrayList<Project> projects = projectServices.getProjectsFromUserId(userId);
        model.addAttribute("projects", projects);
        return "userpage";
    }

    @PostMapping("/createuser")
    public String createUser(WebRequest request, Model model) throws Exception {
        String username = request.getParameter("username");
        //pseudocode-check if empty in DB
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        int userId = userServices.createUser(name, username, password);
        if (userId == 0)
            model.addAttribute("errorMessage", "User could not be created");
        else
            model.addAttribute("success", "User was successfully created");
        return "index.html";
    }
}
