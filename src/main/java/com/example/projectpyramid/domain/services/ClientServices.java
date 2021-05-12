package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.ClientMapper;
import com.example.projectpyramid.domain.entities.Client;

import java.util.ArrayList;

public class ClientServices {

    ClientMapper clientMapper = new ClientMapper();

    // TODO: Implement getClientFromId();
    public Client getClientFromId(int clientId) {
       return clientMapper.getClientFromId(clientId);
    }


    public ArrayList<Client> getClients(){
        return clientMapper.getClients();
    }



}
