package com.example.projectpyramid.controller;

import com.example.projectpyramid.domain.entities.*;
import com.example.projectpyramid.domain.services.ClientServices;
import com.example.projectpyramid.domain.services.ProjectServices;
import com.example.projectpyramid.domain.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Controller
public class EditProjectController {

    UserServices userServices;
    ProjectServices projectServices;
    ClientServices clientServices;

    EditProjectController() {
        userServices = new UserServices();
        projectServices = new ProjectServices();
        clientServices = new ClientServices();
    }

    @GetMapping("/edit-project")
    public String editProjectContent(WebRequest request, Model model) throws Exception {
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


    public int getProjectIdFromSession(WebRequest request) {
        return (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);
    }

    private void saveProjectToModel(Model model, int projectId) throws Exception {
        Project project = projectServices.getProjectFromId(projectId);
        model.addAttribute("project", project);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("type") String type, @RequestParam("id") int id, Model model, WebRequest request) throws Exception {
        switch (type) {
            case "subtask":
                projectServices.deleteSubtask(id);
                break;
            case "task":
                projectServices.deleteTask(id);
                break;
            case "project": {
                projectServices.deleteProject(id);
                model.addAttribute("message", "Project was successfully deleted.");
                return "userpage";
            }
            default:
                model.addAttribute("deleteFail", "Element could not be deleted");
        }
        // if is succeeded
        model.addAttribute("deleteSuccess", "Element was successfully deleted");

        int projectId = getProjectIdFromSession(request);
        saveProjectToModel(model, projectId);
                return "editproject";
    }

}