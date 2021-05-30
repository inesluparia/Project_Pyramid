package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.mappers.ClientMapper;
import com.example.projectpyramid.domain.entities.Client;

import java.util.ArrayList;

public class ClientServices {

    ClientMapper clientMapper;

    public ClientServices(){
        clientMapper = new ClientMapper();
    }

    public Client getClientFromId(int clientId) {
       return clientMapper.findById(clientId);
    }

    public ArrayList<Client> getClients(){
        return clientMapper.findAll();
    }
}
