package com.example.projectpyramid.data_access;
import com.example.projectpyramid.domain.entities.Phase;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.Task;

import java.sql.*;
import java.util.ArrayList;

public class ProjectMapper {

    public void createProject(Project project){}

    public void addPhase(Phase phase){}

    public void addTask(Task task){}

    public ArrayList<Project> getProjectsFromUserId(int id) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT id, name, client_id, is_active, description FROM project WHERE author_id =?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Project> projects = new ArrayList<>();
            while (resultSet.next()) {
                int projectId = resultSet.getInt("id");

                // TODO: Get author's User object from its service or mapper.

                int clientId = resultSet.getInt("client_id");
                // TODO: Get Client object from its service or mapper.

                String projectName = resultSet.getString("name");
                boolean isActive = resultSet.getInt("is_active") != 0;
                String description = resultSet.getString("description");

                // TODO: Once all mappers and services has been implemented, set author and client appropriately.
                Project project = new Project(projectId, null, null, projectName, description);
                project.setIsActive(isActive);

                projects.add(project);
            }

            return projects;

        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }

    }
}
