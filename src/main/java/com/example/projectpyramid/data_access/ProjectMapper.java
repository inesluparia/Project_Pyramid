package com.example.projectpyramid.data_access;
import com.example.projectpyramid.domain.entities.*;

import java.sql.*;
import java.util.ArrayList;

public class ProjectMapper {

    ClientMapper clientMapper = new ClientMapper();
    UserMapper userMapper = new UserMapper();

    public int createProject(String name, int userId, int clientId, String description) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO projects (name, author_id, client_id, description) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, userId);
            ps.setInt(3, clientId);
            ps.setString(4, description);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int projectId = rs.getInt(1);
            return projectId;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }




    public Project getProject(int projectId) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT name, client_id, is_active, description, author_id FROM projects WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int authorId = resultSet.getInt("author_id");
                User author = userMapper.getUser(authorId);
                int clientId = resultSet.getInt("client_id");
                Client client = clientMapper.getClientFromId(clientId);
                String projectName = resultSet.getString("name");
                boolean isActive = resultSet.getInt("is_active") != 0;
                String description = resultSet.getString("description");
                Project project = new Project(projectId, author, client, projectName, description);
                project.setIsActive(isActive);
                return project;
            }
        } catch (SQLException ex) {
            // FIXME either handle exception or something else, bc this cascades throughout lots of other code.
            throw new Exception(ex.getMessage());
        }
        return null;
    }

    public ArrayList<Project> getProjectsFromUserId(int id) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT id, name, client_id, is_active, description FROM projects WHERE author_id =?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Project> projects = new ArrayList<>();
            while (resultSet.next()) {
                int projectId = resultSet.getInt("id");
                User author = userMapper.getUser(id);
                // TODO: Get projects phases and tasks and save them in phases list through a getProject() call.
                int clientId = resultSet.getInt("client_id");
                Client client = clientMapper.getClientFromId(clientId);
                String projectName = resultSet.getString("name");
                boolean isActive = resultSet.getInt("is_active") != 0;
                String description = resultSet.getString("description");
                // TODO: Once all mappers and services has been implemented, set author and client appropriately.
                Project project = new Project(projectId, author, client, projectName, description);
                project.setIsActive(isActive);
                projects.add(project);
            }
            return projects;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public ArrayList<Phase> getPhases(int projectId) {
        String query = "SELECT id, name, description FROM phases WHERE project_id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, projectId);
            ResultSet results = preparedStatement.executeQuery();

            ArrayList<Phase> phases = new ArrayList<>();
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                String description = results.getString("description");

                phases.add(new Phase(id, projectId, name, description));
            }

            return phases;

        } catch (SQLException ex) {
            return null;
        }
    }
}
