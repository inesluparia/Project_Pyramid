package com.example.projectpyramid.data_access.mappers;
import com.example.projectpyramid.data_access.DBManager;
import com.example.projectpyramid.data_access.Mapper;
import com.example.projectpyramid.domain.entities.Client;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.User;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientMapper implements Mapper<Client> {

    /**
     * Inserts a client into the database.
     *
     * @param client The client to be inserted into the database.
     * @return The id of the newly inserted client, 0 if unable to insert.
     * @throws SQLIntegrityConstraintViolationException If name or cvr already exists.
     */
    public int insert(@NotNull Client client) throws SQLIntegrityConstraintViolationException {
        String query = "INSERT INTO clients (name, cvr) VALUES (?, ?)";
        Connection connection = DBManager.getConnection();
        int clientId = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setInt(2, client.getCvr());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                clientId = resultSet.getInt(1);

        } catch (SQLIntegrityConstraintViolationException ex) {
            throw ex;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clientId;
    }

    /**
     * Updates a client's changes to the database.
     *
     * @param client The client that has been changed.
     */
    public void update(@NotNull Client client) {
        String query = "UPDATE clients SET name = ?, cvr = ? WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setInt(2, client.getCvr());
            preparedStatement.setInt(3, client.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes a client and all its projects from the database.
     *
     * @param id The client to be deleted from the database.
     */
    public void delete(int id) {
        String query = "DELETE FROM clients WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Finds a client from their client id.
     *
     * @param clientId The id of the client to find.
     * @return The found client, null if not found.
     */
    public Client findById(int clientId) {
        String query = "SELECT name, cvr FROM clients WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, clientId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int cvr = resultSet.getInt("cvr");

                return new Client(clientId, name, cvr);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Returns all clients from the database.
     *
     * @return List of found clients, empty if none found.
     */
    public ArrayList<Client> findAll() {
        String query = "SELECT * FROM clients";
        Connection connection = DBManager.getConnection();
        ArrayList<Client> clients = new ArrayList<>();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int cvr = resultSet.getInt("cvr");
                int id = resultSet.getInt("id");
                clients.add(new Client(id, name, cvr));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clients;
    }
}
