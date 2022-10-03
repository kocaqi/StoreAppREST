package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.exception.ResourceNotFoundException;
import com.localweb.storeapp.payload.entityDTO.ClientDTO;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    public ClientDTO create(ClientDTO clientDTO){
        //convert DTO to entity
        Client client = mapToEntity(clientDTO);
        Client newClient = clientRepository.save(client);

        //convert entity to DTO
        return mapToDTO(newClient);
    }

    public Response<ClientDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Client> clients = clientRepository.findAll(pageable);

        List<Client> clientList = clients.getContent();

        List<ClientDTO> content= clientList.stream().map(this::mapToDTO).collect(Collectors.toList());

        Response<ClientDTO> postResponse = new Response<>();
        postResponse.setContent(content);
        postResponse.setPageNo(clients.getNumber());
        postResponse.setPageSize(clients.getSize());
        postResponse.setTotalElements(clients.getTotalElements());
        postResponse.setTotalPages(clients.getTotalPages());
        postResponse.setLast(clients.isLast());

        return postResponse;
    }

    private ClientDTO mapToDTO(Client newClient){
        /*ClientDTO clientResponse = new ClientDTO();
        clientResponse.setId(newClient.getId());
        clientResponse.setFirstName(newClient.getFirstName());
        clientResponse.setLastName(newClient.getLastName());
        clientResponse.setEmail(newClient.getEmail());
        clientResponse.setTheUser(newClient.getTheUser());
        clientResponse.setOrders(newClient.getOrders());
        clientResponse.setDateCreated(newClient.getDateCreated());
        clientResponse.setDateUpdated(newClient.getDateUpdated());*/
        return modelMapper.map(newClient, ClientDTO.class);
    }

    private Client mapToEntity(ClientDTO clientDTO){
        /*Client client = new Client();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setTheUser(clientDTO.getTheUser());
        client.setOrders(clientDTO.getOrders());
        client.setDateCreated(clientDTO.getDateCreated());
        client.setDateUpdated(clientDTO.getDateUpdated());*/
        return modelMapper.map(clientDTO, Client.class);
    }

    public ClientDTO getById(int id) {
        Client client = clientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Client", "id", id));
        return mapToDTO(client);
    }

    public ClientDTO update(ClientDTO clientDTO, int id) {
        Client client = clientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Client", "id", id));
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setDateUpdated(LocalDate.now());
        Client updatedClient = clientRepository.save(client);

        return mapToDTO(updatedClient);
    }
}