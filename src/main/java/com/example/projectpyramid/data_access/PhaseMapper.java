package com.example.projectpyramid.data_access;

import com.example.projectpyramid.domain.entities.Phase;

import java.sql.*;
import java.util.ArrayList;

public class PhaseMapper {

    public void addPhase(Phase phase){

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
