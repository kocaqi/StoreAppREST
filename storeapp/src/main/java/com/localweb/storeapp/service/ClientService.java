package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.service.exception.ResourceNotFoundException;
import com.localweb.storeapp.payload.entityDTO.ClientDTO;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.repository.ClientRepository;
import com.localweb.storeapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ClientDTO create(ClientDTO clientDTO, Principal principal) {
        //convert DTO to entity
        Client client = modelMapper.map(clientDTO, Client.class);
        client.setDateCreated(LocalDate.now());
        client.setDateUpdated(LocalDate.now());
        String email = principal.getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));
        client.setTheUser(user);
        Client newClient = clientRepository.save(client);

        //convert entity to DTO
        return modelMapper.map(newClient, ClientDTO.class);
    }

    public Response<ClientDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Client> clients = clientRepository.findAll(pageable);

        List<Client> clientList = clients.getContent();

        List<ClientDTO> content = clientList.stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toList());

        Response<ClientDTO> postResponse = new Response<>();
        postResponse.setContent(content);
        postResponse.setPageNo(clients.getNumber());
        postResponse.setPageSize(clients.getSize());
        postResponse.setTotalElements(clients.getTotalElements());
        postResponse.setTotalPages(clients.getTotalPages());
        postResponse.setLast(clients.isLast());

        return postResponse;
    }

    public ClientDTO getById(int id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        return modelMapper.map(client, ClientDTO.class);
    }

    public ClientDTO update(ClientDTO clientDTO, int id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setDateUpdated(LocalDate.now());
        Client updatedClient = clientRepository.save(client);

        return modelMapper.map(updatedClient, ClientDTO.class);
    }
}