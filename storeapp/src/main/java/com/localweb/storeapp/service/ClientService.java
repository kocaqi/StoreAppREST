package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public Client getClient(int id) {
        return clientRepository.getClientById(id);
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public List<Client> findAllByUser(User user) {
        return clientRepository.findAllByTheUser(user);
    }
}