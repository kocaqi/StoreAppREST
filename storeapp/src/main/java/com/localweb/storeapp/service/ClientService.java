package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.payload.ClientDTO;
import com.localweb.storeapp.payload.UserDTO;
import com.localweb.storeapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<ClientDTO> getAll(int pageNo, int pageSize){

        PageRequest pageable = PageRequest.of(pageNo, pageSize);
        Page<Client> users = clientRepository.findAll(pageable);

        List<Client> clientList = users.getContent();

        return clientList.stream().map(client -> mapToDTO(client)).collect(Collectors.toList());
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

    public ClientDTO getById(int id) {
        Client client = clientRepository.findClientById(id);
        ClientDTO clientDTO = mapToDTO(client);
        return clientDTO;
    }

    public ClientDTO update(ClientDTO clientDTO, int id) {
        Client client = clientRepository.findClientById(id);
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setDateUpdated(LocalDate.now());
        Client updatedClient = clientRepository.save(client);

        return mapToDTO(updatedClient);
    }
}