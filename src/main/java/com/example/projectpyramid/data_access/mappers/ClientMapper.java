package com.example.projectpyramid.data_access.mappers;
import com.example.projectpyramid.data_access.DBManager;
import com.example.projectpyramid.data_access.Mapper;
import com.example.projectpyramid.domain.entities.Client;
import com.example.projectpyramid.domain.entities.Project;
import com.example.projectpyramid.domain.entities.User;

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
    public int insert(Client client) throws SQLIntegrityConstraintViolationException {
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
    public void update(Client client) {
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
     * @param client The client to be deleted from the database.
     */
    public void delete(Client client) {
        String query = "DELETE FROM clients WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, client.getId());
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
        String query = "SELECT * FROM projects WHERE id = ?";
        Connection connection = DBManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, clientId);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int cvr = resultSet.getInt("cvr");

                return new Client(name, cvr);
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
    public List<Client> findAll() {
        String query = "SELECT * FROM clients";
        Connection connection = DBManager.getConnection();
        ArrayList<Client> clients = new ArrayList<>();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int cvr = resultSet.getInt("cvr");
                clients.add(new Client(name, cvr));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clients;
    }

    // <editor-fold desc="Deprecated methods">

    /**
     * @deprecated and replaced by {@link ClientMapper#findById(int)}
     */
    public Client getClientFromId(int clientId) {
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
                client.setId(clientId);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return client;
    }

    /**
     * @deprecated and replaced by {@link ClientMapper#findAll()}
     */
    public ArrayList<Client> getClients() {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * FROM clients";
            ResultSet resultSet = con.createStatement().executeQuery(SQL);
            ArrayList<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                Client client = new Client();
                String clientName = resultSet.getString("name");
                client.setName(clientName);
                int cvr = resultSet.getInt("cvr");
                client.setCvr(cvr);
                int id = resultSet.getInt("id");
                client.setId(id);
                clients.add(client);
            }
            return clients;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    // </editor-fold>
}
