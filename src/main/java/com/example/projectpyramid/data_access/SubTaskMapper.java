package com.example.projectpyramid.data_access;

import com.example.projectpyramid.domain.entities.SubTask;

import java.sql.*;
import java.util.ArrayList;

public class SubTaskMapper {

    public void addSubTask(SubTask subTask) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO tasks (name, phase_id, description, duration) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subTask.getName());
            ps.setInt(2, subTask.getSubTaskId());
            ps.setString(3, subTask.getDescription());
            ps.setInt(4, subTask.getDurationInManHours());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    public ArrayList<SubTask> getSubTasks(int phaseId){
        String query = "SELECT id, name, description, duration FROM tasks WHERE phase_id = ?";
        Connection connection = DBManager.getConnection();
        ArrayList<SubTask> subTasks = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, phaseId);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                int duration = results.getInt("duration");
                String description = results.getString("description");

                subTasks.add(new SubTask(id, phaseId, duration, name, description));
            }

            return subTasks;

        } catch (SQLException ex) {
            return null;
        }
    }

}
