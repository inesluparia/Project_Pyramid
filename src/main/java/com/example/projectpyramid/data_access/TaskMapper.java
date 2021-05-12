package com.example.projectpyramid.data_access;

import com.example.projectpyramid.domain.entities.Task;

import java.sql.*;
import java.util.ArrayList;

public class TaskMapper {

    public ArrayList<Task> getTasks(int phaseId){
        String query = "SELECT id, name, description FROM tasks WHERE phase_id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, phaseId);
            ResultSet results = preparedStatement.executeQuery();

            ArrayList<Task> tasks = new ArrayList<>();
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                int duration = results.getInt("duration");
                String description = results.getString("description");

                tasks.add(new Task(phaseId, id, duration, name, description));
            }

            return tasks;

        } catch (SQLException ex) {
            return null;
        }
    }

}
