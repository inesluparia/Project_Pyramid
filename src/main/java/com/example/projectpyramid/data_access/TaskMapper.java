package com.example.projectpyramid.data_access;

import com.example.projectpyramid.domain.entities.Task;
import com.example.projectpyramid.domain.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class TaskMapper {

    public void insertTask(Task task) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO tasks (name, project_id, description) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getName());
            ps.setInt(2, task.getProjectId());
            ps.setString(3, task.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    public ArrayList<Task> getTasks(int projectId) {

        try {
            String query = "SELECT id, name, description FROM tasks WHERE project_id = ?";
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, projectId);
            ResultSet results = preparedStatement.executeQuery();
            ArrayList<Task> tasks = new ArrayList<>();
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                String description = results.getString("description");

                tasks.add(new Task(id, projectId, name, description));
            }

            return tasks;

        } catch (SQLException ex) {
            return null;
        }
    }

    public Task getTask(int taskId) {

        try {
            String query = "SELECT name, description, project_id FROM tasks WHERE id = ?";
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, taskId);
            ResultSet results = preparedStatement.executeQuery();

            results.next();
            int projectId = results.getInt("project_id");
            String name = results.getString("name");
            String description = results.getString("description");

            Task task = new Task(taskId, projectId, name, description);
            return task;

        } catch (SQLException ex) {
            return null;
        }
    }


    public void update(String name, String description, int id) {
        String query = "UPDATE tasks SET name = ?, description = ? WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, id);
            ps.executeUpdate();

            //wasSuccessful = ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("An Exception occured:");
            ex.printStackTrace();
        }
    }
}