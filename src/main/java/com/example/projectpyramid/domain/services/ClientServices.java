package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.ClientMapper;

public class ClientServices {

    ClientMapper clientMapper = new ClientMapper();

    public String getClientName(int clientId){
        return clientMapper.getClientName(clientId);
    }

}
