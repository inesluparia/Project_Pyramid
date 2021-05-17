package com.example.projectpyramid.data_access;

import com.example.projectpyramid.domain.entities.Task;

import java.sql.*;
import java.util.ArrayList;

public class TaskMapper {

    public void addTask(Task task) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO phases (name, project_id, description) VALUES (?, ?, ?)";
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
        String query = "SELECT id, name, description FROM phases WHERE project_id = ?";
        Connection connection = DBManager.getConnection();

        try {
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

}
