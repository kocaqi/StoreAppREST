package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.Product;
import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.payload.ClientDTO;
import com.localweb.storeapp.payload.ProductDTO;
import com.localweb.storeapp.repository.ClientRepository;
import com.localweb.storeapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDTO createClient(ClientDTO clientDTO){
        //convert DTO to entity
        Client client = new Client();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setTheUser(clientDTO.getTheUser());
        client.setOrders(clientDTO.getOrders());
        client.setDateCreated(clientDTO.getDateCreated());
        client.setDateUpdated(clientDTO.getDateUpdated());

        Client newClient = clientRepository.save(client);

        //convert entity to DTO
        ClientDTO clientResponse = new ClientDTO();
        clientResponse.setId(newClient.getId());
        clientResponse.setFirstName(newClient.getFirstName());
        clientResponse.setLastName(newClient.getLastName());
        clientResponse.setEmail(newClient.getEmail());
        clientResponse.setTheUser(newClient.getTheUser());
        clientResponse.setOrders(newClient.getOrders());
        clientResponse.setDateCreated(newClient.getDateCreated());
        clientResponse.setDateUpdated(newClient.getDateUpdated());

        return clientResponse;
    }

}