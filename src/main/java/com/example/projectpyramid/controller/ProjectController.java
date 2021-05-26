package com.example.projectpyramid.controller;

import com.example.projectpyramid.domain.entities.Client;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.Task;
import com.example.projectpyramid.domain.entities.User;
import com.example.projectpyramid.domain.services.ClientServices;
import com.example.projectpyramid.domain.services.ProjectServices;
import com.example.projectpyramid.domain.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@Controller
public class ProjectController {

    ClientServices clientServices = new ClientServices();
    UserServices userServices = new UserServices();
    ProjectServices projectServices = new ProjectServices();


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

    @GetMapping("/projectlist")
    public String allProjects() {
        return "allprojects";
    }

    @GetMapping("/createproject")
    public String projectCreation(Model model) {
        ArrayList<Client> clients = clientServices.getClients();
        model.addAttribute("clients", clients);
        return "createproject.html";
    }

    @PostMapping("/createproject")
    public String createProject(WebRequest request, Model model) throws Exception {
        int userId = (int) request.getAttribute("userId", WebRequest.SCOPE_SESSION);
        User author = userServices.getUserFromId(userId);
        String clientId = request.getParameter("client");
        Client client = clientServices.getClientFromId(Integer.parseInt(clientId));
        String name = request.getParameter("project-name");
        String description = request.getParameter("description");
        int projectId = projectServices.createProject(author, client, name, description);
        request.setAttribute("projectId", projectId, WebRequest.SCOPE_SESSION);

        saveProjectToModel(model, projectId);
        return "create-elements.html";
    }

    @GetMapping("/create-elements")
    public String createTask(Model model, WebRequest request) throws Exception {
        int projectId = getProjectIdFromSession(request);
        saveProjectToModel(model, projectId);
        return "create-elements";
    }

    @PostMapping("/add-task")
    public String createTask(WebRequest request, Model model) throws Exception {
        String taskName = request.getParameter("name");
        String taskDescription = request.getParameter("description");
        int projectId = getProjectIdFromSession(request);
        projectServices.addTask(taskName, taskDescription, projectId);
        saveProjectToModel(model, projectId);
        return "create-elements.html";
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
        return "create-elements.html";
    }

    public int getProjectIdFromSession(WebRequest request){
        return (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);
    }

    private void saveProjectToModel(Model model, int projectId) throws Exception {
        Project project = projectServices.getProjectFromId(projectId);
        model.addAttribute("project", project);
    }

    public void saveProjectEstimationsToModel(Model model, int projectId) throws Exception{
        model.addAttribute("programmers", projectServices.getProgrammers());
        int manHours = projectServices.getTotalManHours(projectId);
        model.addAttribute("totalManHours", manHours);
        model.addAttribute("totalCost", projectServices.getTotalCost(manHours));
        model.addAttribute("completionDate", projectServices.getCompletionDate(manHours));
    }
}
