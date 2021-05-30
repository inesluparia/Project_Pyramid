package com.example.projectpyramid.data_access.mappers;
import com.example.projectpyramid.data_access.DBManager;
import com.example.projectpyramid.data_access.Mapper;
import com.example.projectpyramid.domain.entities.*;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectMapper implements Mapper<Project> {

    ClientMapper clientMapper = new ClientMapper();
    UserMapper userMapper = new UserMapper();

    /**
     * Inserts a project into the database.
     *
     * @param project The project to be inserted into the database.
     * @return The id of the newly inserted project, 0 if unable to insert.
     * @throws SQLIntegrityConstraintViolationException (this is very unlikely as id
     * is the only unique key in this table. Ids are autogenerated by the database).
     */
    public int insert(@NotNull Project project) throws SQLIntegrityConstraintViolationException {
        String query = "INSERT INTO projects (name, author_id, client_id, description) VALUES (?, ?, ?, ?)";
        Connection connection = DBManager.getConnection();
        int projectId = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setInt(2, project.getAuthor().getId());
            preparedStatement.setInt(3, project.getClient().getId());
            preparedStatement.setString(4, project.getDescription());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                projectId = resultSet.getInt(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return projectId;
    }

    /**
     * Updates a project's changes to the database.
     *
     * @param project The project that has been changed.
     */
    public void update(@NotNull Project project) {
        String query = "UPDATE projects SET author_id = ?, client_id = ? name = ?, description = ?, is_active = ? WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, project.getAuthor().getId());
            preparedStatement.setInt(2, project.getClient().getId());
            preparedStatement.setString(3, project.getName());
            preparedStatement.setString(4, project.getDescription());
            preparedStatement.setBoolean(5, project.getIsActive());
            preparedStatement.setInt(6, project.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes a project and all its tasks and subtasks from the database.
     * TODO unit test to ensure tasks and subtasks gets deleted as well.
     *
     * @param id The project to be deleted from the database.
     */
    public void delete(int id) {
        String query = "DELETE FROM projects WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Finds a project from its project id.
     *
     * @param projectId The id of the project to find.
     * @return The found project, null if not found.
     */
    public Project findById(int projectId) {
        String query = "SELECT name, client_id, is_active, description, author_id FROM projects WHERE id=?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, projectId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int authorId = resultSet.getInt("author_id");
                User author = userMapper.findById(authorId);

                int clientId = resultSet.getInt("client_id");
                Client client = clientMapper.findById(clientId);

                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                // TODO implement isActive in Project.java's constructor.
                // boolean isActive = resultSet.getInt("is_active") != 0;

                return new Project(projectId, author, client, name, description);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Finds all projects by a specific author.
     *
     * @param userId The author id of the projects to find.
     * @return List of found projects, empty if none found.
     */
    public ArrayList<Project> findAllByUserId(int userId) {
        String query = "SELECT id, client_id, name, description FROM projects WHERE author_id = ?";
        Connection con = DBManager.getConnection();
        ArrayList<Project> projects = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int projectId = resultSet.getInt("id");
                User author = userMapper.findById(userId);

                int clientId = resultSet.getInt("client_id");
                Client client = clientMapper.findById(clientId);

                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                // TODO implement isActive in Project.java's constructor.
                // boolean isActive = resultSet.getInt("is_active") != 0;

                projects.add(new Project(projectId, author, client, name, description));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return projects;
    }

    // <editor-fold desc="Deprecated methods">

    /**
     * @deprecated and replaced by {@link ProjectMapper#update(Project)}
     */
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

    // </editor-fold>
}
