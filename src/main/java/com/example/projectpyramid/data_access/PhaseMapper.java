package com.example.projectpyramid.data_access;

import com.example.projectpyramid.domain.entities.Phase;
import com.example.projectpyramid.domain.entities.Task;

import java.sql.*;
import java.util.ArrayList;

public class PhaseMapper {

    public void addPhase(Phase phase) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO phases (name, project_id, description) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, phase.getName());
            ps.setInt(2, phase.getProjectId());
            ps.setString(3, phase.getDescription());
            ps.executeUpdate();
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
