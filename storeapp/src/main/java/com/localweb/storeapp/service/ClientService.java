package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.payload.ClientDTO;
import com.localweb.storeapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDTO create(ClientDTO clientDTO){
        //convert DTO to entity
        Client client = mapToEntity(clientDTO);
        Client newClient = clientRepository.save(client);

        //convert entity to DTO
        ClientDTO clientResponse = mapToDTO(newClient);

        return clientResponse;
    }

    public List<ClientDTO> getAll(){
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(client -> mapToDTO(client)).collect(Collectors.toList());
    }

    private ClientDTO mapToDTO(Client newClient){
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

    private Client mapToEntity(ClientDTO clientDTO){
        Client client = new Client();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setTheUser(clientDTO.getTheUser());
        client.setOrders(clientDTO.getOrders());
        client.setDateCreated(clientDTO.getDateCreated());
        client.setDateUpdated(clientDTO.getDateUpdated());
        return client;
    }

}