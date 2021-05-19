package com.example.projectpyramid.data_access;

import com.example.projectpyramid.domain.entities.SubTask;

import java.sql.*;
import java.util.ArrayList;

public class SubTaskMapper {

    public void insertSubTask(SubTask subTask) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO subtasks (name, task_id, description, duration) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subTask.getName());
            ps.setInt(2, subTask.getTaskId());
            ps.setString(3, subTask.getDescription());
            ps.setInt(4, subTask.getDurationInManHours());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    public ArrayList<SubTask> getSubTasks(int taskId ){
        String query = "SELECT id, name, description, duration FROM subtasks WHERE task_id = ?";
        Connection connection = DBManager.getConnection();
        ArrayList<SubTask> subTasks = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, taskId );
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                int duration = results.getInt("duration");
                String description = results.getString("description");

                subTasks.add(new SubTask(id, taskId , duration, name, description));
            }

            return subTasks;

        } catch (SQLException ex) {
            return null;
        }
    }

    public SubTask getSubtask(int subTaskId) {

        try {
            String query = "SELECT name, description, duration, task_id FROM subtasks WHERE id = ?";
            Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, subTaskId);
            ResultSet results = preparedStatement.executeQuery();

            results.next();
            String name = results.getString("name");
            String description = results.getString("description");
            int durationInManHours = results.getInt("duration");
            int taskId = results.getInt("task_id");
            SubTask subTask = new SubTask(subTaskId, taskId, durationInManHours, name, description);
            return subTask;

        } catch (SQLException ex) {
            return null;
        }
    }


    public void update(String name, String description, int durationInManHours, int id) {
        String query = "UPDATE subtasks SET name = ?, description = ?, duration = ? WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, durationInManHours);
            ps.setInt(4, id);
            ps.executeUpdate();

            //wasSuccessful = ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("An Exception occured:");
            ex.printStackTrace();
        }
    }
}
