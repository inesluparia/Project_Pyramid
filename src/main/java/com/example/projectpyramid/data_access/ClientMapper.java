package com.example.projectpyramid.data_access;

import com.example.projectpyramid.domain.entities.Client;
import com.example.projectpyramid.domain.entities.Project;

import java.sql.*;
import java.util.ArrayList;

public class ClientMapper {

    public Client getClient(int clientId) {
        Client client = new Client();
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT name, cvr FROM clients WHERE id =?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String clientName = resultSet.getString("name");
                client.setName(clientName);
                int cvr = resultSet.getInt("cvr");
                client.setCvr(cvr);
                client.setClientId(clientId);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } return client;
    }

    public String getClientName(int id){
        return null;
    }

}
