package com.example.projectpyramid.data_access;

import com.example.projectpyramid.domain.entities.Task;

import java.sql.*;
import java.util.ArrayList;

public class TaskMapper {

    public void addTask(Task task) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO tasks (name, phase_id, description, duration) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getName());
            ps.setInt(2, task.getPhaseId());
            ps.setString(3, task.getDescription());
            ps.setInt(4, task.getDurationInManHours());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    public ArrayList<Task> getTasks(int phaseId){
        String query = "SELECT id, name, description, duration FROM tasks WHERE phase_id = ?";
        Connection connection = DBManager.getConnection();
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, phaseId);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                int duration = results.getInt("duration");
                String description = results.getString("description");

                tasks.add(new Task(id, phaseId, duration, name, description));
            }

            return tasks;

        } catch (SQLException ex) {
            return null;
        }
    }

}
