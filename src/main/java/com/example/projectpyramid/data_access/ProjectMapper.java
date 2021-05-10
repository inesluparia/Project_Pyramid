package com.example.projectpyramid.data_access;
import com.example.projectpyramid.domain.entities.Phase;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.Task;

import java.sql.*;
import java.util.ArrayList;

public class ProjectMapper {

    public void createProject(Project project){}

    public void addPhase(Phase phase){}

    public void addTask(Task task){}

    public ArrayList<Project> getProjectsFromUserId(int id) throws Exception {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT id, name, client_id, is_active, description FROM project WHERE author_id =?";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            ArrayList<Project> projects = new ArrayList<>();
            while (rs.next()) {
                int projId = rs.getInt("id");
                String name = rs.getString("name");
                int clientId = rs.getInt("client_id");
                int is_active = rs.getInt("is_active");
                String desc = rs.getString("description");
                Project project = new Project(projId, id, clientId, name, desc);
                if (is_active == 1) {
                    project.setActive();
                }
                projects.add(project);
            }
            return projects;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }

    }
}
