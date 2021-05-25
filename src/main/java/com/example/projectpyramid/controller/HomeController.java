package com.example.projectpyramid.controller;

import com.example.projectpyramid.data_access.DBManager;
import com.example.projectpyramid.domain.entities.*;
import com.example.projectpyramid.domain.services.ClientServices;
import com.example.projectpyramid.domain.services.ProjectServices;
import com.example.projectpyramid.domain.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

@Controller
public class HomeController {

    UserServices userServices = new UserServices();
    ProjectServices projectServices = new ProjectServices();
    ClientServices clientServices = new ClientServices();

    @GetMapping("/")
    public String renderIndex() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) throws Exception {
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute User user, Model model, HttpSession session) throws Exception {
        int userId = userServices.login(user.getUsername(), user.getPassword());
        session.setAttribute("user_id", userId);

        return "redirect:/projects";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@ModelAttribute User user, Model model, HttpSession session) throws Exception {
        int userId = userServices.createUser(user.getFullname(), user.getUsername(), user.getPassword());
        session.setAttribute("user_id", userId);
        return "redirect:/projects";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.setAttribute("user_id", null);
        return "/";
    }

    @GetMapping("/projects")
    public String showProjects(Model model, HttpSession session) throws Exception {

        int userId = 0;

        try {
            if (session.getAttribute("user_id") != null) {
                userId = (int) session.getAttribute("user_id");
            }
        } catch (NumberFormatException ignored) { }

        User user = userServices.getUserFromId(userId);

        if (user == null)
            throw new Exception("401 Unauthorized");

        model.addAttribute("user", user);
        model.addAttribute("projects", projectServices.getProjectsFromUserId(user.getId()));

        return "userpage";
    }

    @GetMapping("/projects/{id}")
    public String showProject(@PathVariable int id, Model model, HttpSession session, HttpServletResponse response) throws Exception {

        if (session.getAttribute("user_id") == null)
            throw new Exception("401 Unauthorized");

        int userId = (int) session.getAttribute("user_id");

        Project project = projectServices.getProjectFromId(id);

        if (project == null)
            throw new Exception("404 Not Found");

        if (userId != project.getAuthor().getId())
            throw new Exception("403 Forbidden");

        model.addAttribute("project", project);
        return "project";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public void handleConflict() {

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {

    }

//    @GetMapping("/projects/{id}")
//    public String projectPage(@PathVariable("id") int id, Model model) throws Exception {
//        Project project = projectServices.getProjectFromId(id);
//        model.addAttribute(project);
//        return "project";
//    }

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
//    @PostMapping("/login")
//    public String login(WebRequest request) throws Exception {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        int userId = userServices.login(username, password);
//        request.setAttribute("userId", userId, WebRequest.SCOPE_SESSION);
//        return "redirect:userpage";
//    }

    /**
     * This method runs after log-in validation,
     * the getProjectsFromUserId method from projectServices is called
     * and a list of the users projects is saved in the model
     * before returning userpage
     * @param request
     * @param model
     * @return userpage
     * @throws Exception
     */
    @GetMapping("/userpage")
    public String userPage(WebRequest request, Model model) throws Exception {
//        int userId = (int) request.getAttribute("userId", WebRequest.SCOPE_SESSION);
//        request.setAttribute("name", userServices.getUserFromId(userId).getFullName(), WebRequest.SCOPE_SESSION);
//        ArrayList<Project> projects = projectServices.getProjectsFromUserId(userId);
//        model.addAttribute("projects", projects);
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
        return "index";
    }

    @GetMapping("/createproject")
    public String projectCreation(Model model) throws Exception {
        ArrayList<Client> clients = clientServices.getClients();
        model.addAttribute("clients", clients);
        return "createproject";
    }

    @PostMapping("/map-project")
    public String createProject(WebRequest request, Model model) throws Exception {
        // Requests data from html inputs to use in createProject() method
        int userId = Integer.parseInt((String) request.getAttribute("userId", WebRequest.SCOPE_SESSION));
        int clientId = Integer.parseInt(request.getParameter("client"));
        String name = request.getParameter("project-name");
        String description = request.getParameter("description");

        User author = userServices.getUserFromId(userId);
        Client client = clientServices.getClientFromId(clientId);

        Project project = projectServices.createProject(author, client, name, description);
        saveProjectToModel(model, project.getId());

        return "createtask";
    }

    @PostMapping("/add-task")
    public String createTask(WebRequest request, Model model) throws Exception {
        // Requests data from html inputs to use in addPhase() method
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int projectId = getProjectIdFromSession(request);

        projectServices.addTask(projectId, name, description);
        // Get project information to show created phases that are designated to the new project
        saveProjectToModel(model, projectId);
        return "createtask";
    }

    @PostMapping("/add-subTask")
    public String createSubTask(WebRequest request, Model model) throws Exception {
        int projectId = getProjectIdFromSession(request);
        //int intProjId = Integer.parseInt(projId);
        ArrayList<Task> tasks = projectServices.getTasks(projectId);
        model.addAttribute("tasks", tasks);
        String name = request.getParameter("name");
        int taskId = Integer.parseInt(request.getParameter("task"));
        int durationInManHours = Integer.parseInt(request.getParameter("duration"));
        String description = request.getParameter("description");
        projectServices.addSubTask(taskId, name, description, durationInManHours);

        saveProjectToModel(model, projectId);

        return "createtask";
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
    public String updateProject(WebRequest request) throws Exception {
        int projectId = getProjectIdFromSession(request);
        int userId = Integer.parseInt(request.getParameter("userId"));
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        String name = request.getParameter("project-name");
        String description = request.getParameter("description");

        User author = userServices.getUserFromId(userId);
        Client client = clientServices.getClientFromId(clientId);

        projectServices.updateProject(projectId, author, client, name, description);

        return "redirect:edit-project";
    }

    @PostMapping("/update-task")
    public String updateTask(@RequestParam("id") int taskId, WebRequest request) throws DBManager.DatabaseConnectionException {
        String taskName = request.getParameter("name");
        String taskDescription = request.getParameter("description");
        int projectId = getProjectIdFromSession(request);

        projectServices.updateTask(projectId, taskId, taskName, taskDescription);
        return "redirect:edit-project";
    }

    @PostMapping("/update-subtask")
    public String updateSubTask(@RequestParam("id") int id, WebRequest request) throws DBManager.DatabaseConnectionException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int durationInManHours = Integer.parseInt(request.getParameter("duration"));

        projectServices.updateSubTask(id, taskId, name, description, durationInManHours);
        return "redirect:edit-project";
    }

    @GetMapping("/error")
    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage() + "\n\n" + exception.getClass());
        exception.printStackTrace();

        return "error";
    }

    @GetMapping("/myprojects")
    public String myProjects() {
        return "projects";
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