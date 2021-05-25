package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.DBManager;
import com.example.projectpyramid.data_access.mappers.ClientMapper;
import com.example.projectpyramid.domain.entities.Client;

import java.util.ArrayList;

public class ClientServices {

    ClientMapper clientMapper = new ClientMapper();

    public Client getClientFromId(int clientId) throws DBManager.DatabaseConnectionException {
       return clientMapper.findById(clientId);
    }

    public ArrayList<Client> getClients() throws DBManager.DatabaseConnectionException {
        return (ArrayList<Client>) clientMapper.findAll();
    }
}
