package com.example.projectpyramid.controller;

import com.example.projectpyramid.data_access.UserMapper;
import com.example.projectpyramid.domain.entities.Client;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.User;
import com.example.projectpyramid.domain.services.ClientServices;
import com.example.projectpyramid.domain.services.ProjectServices;
import com.example.projectpyramid.domain.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@Controller
public class HomeController {
    UserServices userServices = new UserServices();
    ProjectServices projectServices = new ProjectServices();
    ClientServices clientServices = new ClientServices();


    @GetMapping("/")
    public String renderIndex() {
        return "index.html";
    }

    @GetMapping("/logintest")
    @ResponseBody
    public String loginTest() throws Exception {
        User user = userServices.login("Andersand", "1234");
        return user.getUserName();
    }

    @PostMapping("/login")
    public String login(WebRequest request) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userServices.login(username, password);
        String user_Id = String.valueOf(user.getId());
        request.setAttribute("userId", user_Id, WebRequest.SCOPE_SESSION);
        request.setAttribute("name", user.getFullName(), WebRequest.SCOPE_SESSION);

        return "redirect:userpage";
    }

    @PostMapping("/createuser")
    public String createUser(WebRequest request, Model model) throws Exception {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        User user = userServices.createUser(name, username, password1, password2);
        model.addAttribute("name", user.getFullName());
        return "success.html";
    }

    @GetMapping("/createproject")
    public String projectCreation(Model model){
        ArrayList<Client> clients = clientServices.getClients();
        model.addAttribute("clients", clients);

        return "createproject.html";
    }

            
    @PostMapping("/map-project")
    public String createProject(WebRequest request, Model model) {
        //liggende på web requested
        ArrayList<Client> clients = clientServices.getClients();
        model.addAttribute("clients", clients);
        model.getAttribute("clients");

        //TODO get userid from the session
        String userId = request.getParameter("userId");
        String projectName = request.getParameter("project-name");
        String description = request.getParameter("description");
        String phase = request.getParameter("phase");
        String clientId = request.getParameter("client");

       // Project project = projectServices.createProject(null, userId, clientId, projectName, description );


        return "createphase.html";
    }

    @PostMapping("/add-phase")
    public String createPhase(WebRequest request){
    String name = request.getParameter("name");
    String description = request.getParameter("description");

    // TODO get project id
    //Project project = projectServices.addPhase(name, description, );
        return "createtask.html";
    }

    @PostMapping("/createtask")
    public String createTask() {
        return "success.html";
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
    public String projectPage(@RequestParam("id") String projectId, WebRequest request, Model model) throws Exception {
    Project project = projectServices.getProjectFromId(Integer.parseInt(projectId));
    model.addAttribute("project", project);
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