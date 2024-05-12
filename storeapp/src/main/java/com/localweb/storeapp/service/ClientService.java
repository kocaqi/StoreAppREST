package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.payload.entityDTO.ClientDTO;
import com.localweb.storeapp.payload.saveDTO.ClientSaveDTO;
import com.localweb.storeapp.repository.ClientRepository;
import com.localweb.storeapp.repository.UserRepository;
import com.localweb.storeapp.search.SearchCriteria;
import com.localweb.storeapp.search.Specify;
import com.localweb.storeapp.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public ClientDTO create(ClientSaveDTO clientSaveDTO, Principal principal) {
        //convert DTO to entity
        Client client = modelMapper.map(clientSaveDTO, Client.class);
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

    public ClientDTO getById(long id) {
        Client client = clientRepository.findClientById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        return modelMapper.map(client, ClientDTO.class);
    }

    public ClientDTO update(ClientSaveDTO clientSaveDTO, long id) {
        Client client = clientRepository.findClientById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        client.setFirstName(clientSaveDTO.getFirstName());
        client.setLastName(clientSaveDTO.getLastName());
        client.setEmail(clientSaveDTO.getEmail());
        client.setDateUpdated(LocalDate.now());
        Client updatedClient = clientRepository.save(client);

        return modelMapper.map(updatedClient, ClientDTO.class);
    }

    public List<ClientDTO> searchBy(String keyword) {
        Specify<Client> specifyByFirstName = new Specify<>(new SearchCriteria("firstName", ":", keyword));
        Specify<Client> specifyByLastName = new Specify<>(new SearchCriteria("lastName", ":", keyword));
        Specify<Client> specifyByEmail = new Specify<>(new SearchCriteria("email", ":", keyword));

        List<Client> clients = clientRepository.findAll(Specification
                .where(specifyByFirstName).and(specifyByLastName).and(specifyByEmail));

        return clients.stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toList());
    }
}

