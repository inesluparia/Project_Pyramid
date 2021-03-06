package com.example.projectpyramid.data_access.mappers;

import com.example.projectpyramid.data_access.DBManager;
import com.example.projectpyramid.data_access.Mapper;
import com.example.projectpyramid.domain.entities.Client;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.Task;
import com.example.projectpyramid.domain.entities.User;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskMapper implements Mapper<Task> {

    /**
     * Inserts a task into the database.
     *
     * @param task The task to be inserted into the database.
     * @return The id of the newly inserted task, 0 if unable to insert.
     * @throws SQLIntegrityConstraintViolationException (this is very unlikely as id
     * is the only unique key in this table. Ids are autogenerated by the database).
     */
    public int insert(@NotNull Task task) throws SQLIntegrityConstraintViolationException, DBManager.DatabaseConnectionException {
        String query = "INSERT INTO tasks (project_id, name, description) VALUES (?, ?, ?, ?)";
        int taskId = 0;

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, task.getProjectId());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                taskId = resultSet.getInt(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.getMessage();
        }

        return taskId;
    }

    /**
     * Updates a task's changes to the database.
     *
     * @param task The task that has been changed.
     */
    public void update(@NotNull Task task) throws DBManager.DatabaseConnectionException {
        String query = "UPDATE tasks SET project_id = ?, name = ?, description = ? WHERE id = ?";

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, task.getProjectId());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setInt(4, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes a task and all its subtasks from the database.
     * TODO unit test to ensure subtasks gets deleted as well.
     *
     * @param task The task to be deleted from the database.
     */
    public void delete(@NotNull Task task) throws DBManager.DatabaseConnectionException {
        String query = "DELETE FROM tasks WHERE id = ?";

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Finds a task from its task id.
     *
     * @param taskId The id of the task to find.
     * @return The found task, null if not found.
     */
    public Task findById(int taskId) throws DBManager.DatabaseConnectionException {
        String query = "SELECT * FROM projects WHERE id = ?";

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, taskId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int projectId = resultSet.getInt("project_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                return new Task(taskId, projectId, name, description);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Finds all tasks in a specific project.
     *
     * @param projectId The project id of the tasks to find.
     * @return List of found tasks, empty if none found.
     */
    public List<Task> findAllByProjectId(int projectId) throws DBManager.DatabaseConnectionException {
        String query = "SELECT id, name, description FROM tasks WHERE project_id = ?";
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                tasks.add(new Task(taskId, projectId, name, description));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    // <editor-fold desc="Deprecated methods">

    // </editor-fold Deprecated methods>
}
