package com.example.projectpyramid.controller;

import com.example.projectpyramid.domain.entities.*;
import com.example.projectpyramid.domain.services.ClientServices;
import com.example.projectpyramid.domain.services.ProjectServices;
import com.example.projectpyramid.domain.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@Controller
public class HomeController {

    UserServices userServices = new UserServices();
    ProjectServices projectServices = new ProjectServices();
    ClientServices clientServices = new ClientServices();

    @GetMapping("/")
    public String renderIndex() {
        return "index";
    }

//    @GetMapping("/logintest")
//    @ResponseBody
//    public String loginTest() throws Exception {
//        User user = userServices.login("Andersand", "1234");
//        return user.getUserName();
//    }

    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        return "errorpage.html";
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

    @GetMapping("/log-ud")
    public String logUd(WebRequest request) {
        request.setAttribute("userId", null, WebRequest.SCOPE_SESSION);
        return "index.html";
    }

    @PostMapping("/createuser")
    public String createUser(WebRequest request, Model model) throws Exception {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password1");
        String confirmPassword = request.getParameter("password2");
        User user = userServices.createUser(name, username, password, confirmPassword);
        model.addAttribute("name", user.getFullName());
        return "success";
    }

    @GetMapping("/createproject")
    public String projectCreation(Model model) {
        ArrayList<Client> clients = clientServices.getClients();
        model.addAttribute("clients", clients);
        return "createproject.html";
    }

    @PostMapping("/map-project")
    public String createProject(WebRequest request, Model model) throws Exception {
        // Requests data from html inputs to use in createProject() method
        String userId = (String) request.getAttribute("userId", WebRequest.SCOPE_SESSION);
        String clientId = request.getParameter("client");
        String projectName = request.getParameter("project-name");
        String description = request.getParameter("description");

        int projectId = projectServices.createProject(userId, projectName, description, clientId);
        //Save project in model
        Project project = projectServices.getProjectFromId(projectId);
        model.addAttribute("project", project);
        //Save projectID in session
        request.setAttribute("projectId", projectId, WebRequest.SCOPE_SESSION);
        return "createtask.html";
    }

    @PostMapping("/add-task")
    public String createTask(WebRequest request, Model model) throws Exception {
        // Requests data from html inputs to use in addPhase() method
        String taskName = request.getParameter("name");
        String taskDescription = request.getParameter("description");
        int projId = (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);

        projectServices.addTask(taskName, taskDescription, projId);
        // Get project information to show created phases that are designated to the new project
        Project project = projectServices.getProjectFromId(projId);
        model.addAttribute("project", project);
        return "createtask.html";
    }

    @PostMapping("/add-subTask")
    public String createSubTask(WebRequest request, Model model) throws Exception {
        int projId = (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);
        //int intProjId = Integer.parseInt(projId);
        ArrayList<Task> tasks = projectServices.getTasks(projId);
        model.addAttribute("tasks", tasks);
        String name = request.getParameter("name");
        String taskId = request.getParameter("task");
        String durationInManHours = request.getParameter("duration");
        String description = request.getParameter("description");
        projectServices.addSubTask(name, taskId, durationInManHours, description);

        Project project = projectServices.getProjectFromId(projId);
        model.addAttribute("project", project);
        return "createtask.html";
    }

    @GetMapping("/edit-project")
    public String editProjectContent(/*@RequestParam("id") int projectId,*/ WebRequest request, Model model) throws Exception {
        int projectId = (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);
        request.setAttribute("projectId", projectId, WebRequest.SCOPE_SESSION);

        boolean projectBoo = false;
        Project project = projectServices.getProjectFromId(projectId);
        model.addAttribute("project", project);
        model.addAttribute("projectBoo", projectBoo);
        return "editproject";
    }

    @GetMapping("/fill-project-form")
    public String fillProjectForm(Model model, WebRequest request) throws Exception {
        Integer projId = (Integer) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);
        boolean projectBoo = true;
        Project project = projectServices.getProjectFromId(projId);
        model.addAttribute("project", project);
        model.addAttribute("projectBoo", projectBoo);
        return "editproject";
    }

    @GetMapping("/fill-task-form")
    public String fillTaskForm(@RequestParam("id") int taskId, Model model, WebRequest request) throws Exception {
        int projId = (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);
        Project project = projectServices.getProjectFromId(projId);
        model.addAttribute("project", project);
        Task task = projectServices.getTask(taskId);
        model.addAttribute("task", task);
        return "editproject";
    }

    @GetMapping("/fill-subtask-form")
    public String fillSubTaskForm(@RequestParam("id") int subtaskId, Model model, WebRequest request) throws Exception {
        int projId = (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);
        Project project = projectServices.getProjectFromId(projId);
        model.addAttribute("project", project);

        SubTask subTask = projectServices.getSubtask(subtaskId);
        model.addAttribute("subtask", subTask);

        return "editproject";
    }


    @PostMapping("/update-project")
    public String updateProject(WebRequest request) {
        int projectId = (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);
        String projectName = request.getParameter("project-name");
        String description = request.getParameter("description");
        projectServices.updateProject(projectName, description, projectId);

        return "redirect:edit-project";
    }

    @PostMapping("/update-task")
    public String updateTask(@RequestParam("id") int taskId, WebRequest request) {
        String taskName = request.getParameter("name");
        String taskDescription = request.getParameter("description");

        projectServices.updateTask(taskName, taskDescription, taskId);
        return "redirect:edit-project";
    }

    @PostMapping("/update-subtask")
    public String updateSubTask(@RequestParam("id") int subtaskId, WebRequest request) {
        String name = request.getParameter("name");
        String durationInManHours = request.getParameter("duration");
        String description = request.getParameter("description");

        projectServices.updateSubTask(name, description, durationInManHours, subtaskId);
        return "redirect:edit-project";
    }

    @GetMapping("/userpage")
    public String userPage(WebRequest request, Model model) throws Exception {
        String userId = (String) request.getAttribute("userId", WebRequest.SCOPE_SESSION);
        String name = (String) request.getAttribute("name", WebRequest.SCOPE_SESSION);
        //evt lave en metode updateProjects() som kalder på de nedestående linjer
        ArrayList<Project> projects = projectServices.getProjectsFromUserId(userId);
        model.addAttribute("projects", projects);
        model.addAttribute("name", name);
        return "userpage";
    }

    @GetMapping("/project")
    public String projectPage(@RequestParam("id") int projectId, Model model, WebRequest request) throws Exception {
        request.setAttribute("projectId", projectId, WebRequest.SCOPE_SESSION);
        Project project = projectServices.getProjectFromId(projectId);
        model.addAttribute("project", project);
        model.addAttribute("programmers", 4);
        model.addAttribute("totalCost", projectServices.getTotalCost(projectId));
        model.addAttribute("totalManHours", projectServices.getTotalManHours(projectId));
        model.addAttribute("completionDate", projectServices.getCompletionDate(projectId));
        return "project";
    }

    @GetMapping("/myprojects")
    public String myProjects() {
        return "myprojects";
    }

    @GetMapping("/projectlist")
    public String allProjects() {
        return "allprojects";
    }

}

