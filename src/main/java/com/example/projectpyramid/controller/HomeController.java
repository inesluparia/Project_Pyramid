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

    /**
     * index or home endpoint that presents the user with a log-in form and a create-user form
     * @return index.html
     */
    @GetMapping("/")
    public String renderIndex() {
        return "index";
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

    @GetMapping("/createproject")
    public String projectCreation(Model model) {
        ArrayList<Client> clients = clientServices.getClients();
        model.addAttribute("clients", clients);
        return "createproject.html";
    }

    @PostMapping("/map-project")
    public String createProject(WebRequest request, Model model) throws Exception {
        // Requests data from html inputs to use in createProject() method
        int userId = (int) request.getAttribute("userId", WebRequest.SCOPE_SESSION);
        String clientId = request.getParameter("client");
        String projectName = request.getParameter("project-name");
        String description = request.getParameter("description");

        int projectId = projectServices.createProject(userId, projectName, description, clientId);
        //Save project in model
        request.setAttribute("projectId", projectId, WebRequest.SCOPE_SESSION);

        Project project = projectServices.getProjectFromId(projectId);
        saveProjectToModel(model, projectId);
        return "createtask.html";
    }

    @PostMapping("/add-task")
    public String createTask(WebRequest request, Model model) throws Exception {
        // Requests data from html inputs to use in addPhase() method
        String taskName = request.getParameter("name");
        String taskDescription = request.getParameter("description");
        int projectId = getProjectIdFromSession(request);

        projectServices.addTask(taskName, taskDescription, projectId);
        // Get project information to show created phases that are designated to the new project
        saveProjectToModel(model, projectId);
        return "createtask.html";
    }

    @PostMapping("/add-subTask")
    public String createSubTask(WebRequest request, Model model) throws Exception {
        int projectId = getProjectIdFromSession(request);
        //int intProjId = Integer.parseInt(projId);
        ArrayList<Task> tasks = projectServices.getTasks(projectId);
        model.addAttribute("tasks", tasks);
        String name = request.getParameter("name");
        String taskId = request.getParameter("task");
        String durationInManHours = request.getParameter("duration");
        String description = request.getParameter("description");
        projectServices.addSubTask(name, taskId, durationInManHours, description);

        saveProjectToModel(model, projectId);

        return "createtask.html";
    }

    @GetMapping("/edit-project")
    public String editProjectContent(/*@RequestParam("id") int projectId,*/ WebRequest request, Model model) throws Exception {
        int projectId = getProjectIdFromSession(request);
        request.setAttribute("projectId", projectId, WebRequest.SCOPE_SESSION);

        boolean projectBoo = false;
        model.addAttribute("projectBoo", projectBoo);
        saveProjectToModel(model, projectId);
        return "editproject";
    }

    @GetMapping("/fill-project-form")
    public String fillProjectForm(Model model, WebRequest request) throws Exception {
        int projectId = getProjectIdFromSession(request);
        boolean projectBoo = true;
        saveProjectToModel(model, projectId);
        model.addAttribute("projectBoo", projectBoo);
        return "editproject";
    }

    @GetMapping("/fill-task-form")
    public String fillTaskForm(@RequestParam("id") int taskId, Model model, WebRequest request) throws Exception {
        int projectId = getProjectIdFromSession(request);
        saveProjectToModel(model, projectId);

        Task task = projectServices.getTask(taskId);
        model.addAttribute("task", task);
        return "editproject";
    }

    @GetMapping("/fill-subtask-form")
    public String fillSubTaskForm(@RequestParam("id") int subtaskId, Model model, WebRequest request) throws Exception {
        int projectId = getProjectIdFromSession(request);
        saveProjectToModel(model, projectId);


        SubTask subTask = projectServices.getSubtask(subtaskId);
        model.addAttribute("subtask", subTask);

        return "editproject";
    }

    @PostMapping("/update-project")
    public String updateProject(WebRequest request) {
        int projectId = getProjectIdFromSession(request);
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

    /**
     * After user selects a specific project he is redirected to this endpoint.
     * the project ID from the session is used to call saveProjectToModel and
     * saveProjectEstimationsToModel methods.
     * and a project Object is saved
     * @param projectId
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/project")
    public String projectPage(@RequestParam("id") int projectId, Model model, WebRequest request) throws Exception {
        request.setAttribute("projectId", projectId, WebRequest.SCOPE_SESSION);
        saveProjectToModel(model, projectId);
        saveProjectEstimationsToModel(model, projectId);
        return "project";
    }

    @GetMapping("/log-ud")
    public String logUd(WebRequest request) {
        request.setAttribute("userId", null, WebRequest.SCOPE_SESSION);
        return "index.html";
    }

    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage() + "\n\n" + exception.getClass());
        return "errorpage.html";
    }

    @GetMapping("/myprojects")
    public String myProjects() {
        return "myprojects";
    }

    @GetMapping("/projectlist")
    public String allProjects() {
        return "allprojects";
    }

    public int getProjectIdFromSession(WebRequest request){
        return (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);
    }

    private void saveProjectToModel(Model model, int projectId) throws Exception {
        Project project = projectServices.getProjectFromId(projectId);
        model.addAttribute("project", project);
    }

    public void saveProjectEstimationsToModel(Model model, int projectId) throws Exception{
        model.addAttribute("programmers", 4);
        model.addAttribute("totalCost", projectServices.getTotalCost(projectId));
        model.addAttribute("totalManHours", projectServices.getTotalManHours(projectId));
        model.addAttribute("completionDate", projectServices.getCompletionDate(projectId));
    }
}