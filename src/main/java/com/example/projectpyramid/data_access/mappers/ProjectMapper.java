package com.example.projectpyramid.data_access.mappers;
import com.example.projectpyramid.data_access.DBManager;
import com.example.projectpyramid.domain.entities.*;

import java.sql.*;
import java.util.ArrayList;

public class ProjectMapper {

    ClientMapper clientMapper = new ClientMapper();
    UserMapper userMapper = new UserMapper();

    public int insertProject(String name, int userId, int clientId, String description) throws Exception {
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
            resultSet.next();
            int authorId = resultSet.getInt("author_id");
            User author = userMapper.get(authorId);
            int clientId = resultSet.getInt("client_id");
            Client client = clientMapper.getClientFromId(clientId);
            String projectName = resultSet.getString("name");
            boolean isActive = resultSet.getInt("is_active") != 0;
            String description = resultSet.getString("description");
            Project project = new Project(projectId, author, client, projectName, description);
            project.setIsActive(isActive);
            return project;
        } catch (SQLException ex) {
            // FIXME either handle exception or something else, because this cascades throughout lots of other code.
            throw new Exception(ex.getMessage());

        }
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
                User author = userMapper.get(id);
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


    public void update(String name, String description, int id) {
        String query = "UPDATE projects SET name = ?, description = ? WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("An Exception occured:");
            ex.printStackTrace();
        }
    }
}
