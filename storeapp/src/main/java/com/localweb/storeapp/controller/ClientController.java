package com.localweb.storeapp.controller;

import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.payload.ClientDTO;
import com.localweb.storeapp.payload.ProductDTO;
import com.localweb.storeapp.payload.UserDTO;
import com.localweb.storeapp.service.ClientService;
import com.localweb.storeapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    ClientService clientService;
    UserService userService;

    public ClientController(ClientService clientService, UserService userService) {
        this.clientService = clientService;
        this.userService = userService;
    }

    //create client
    @PostMapping("/create")
    public ResponseEntity<ClientDTO> createUser(@RequestBody ClientDTO clientDTO,
                                                Principal principal){
        clientDTO.setDateCreated(LocalDate.now());
        clientDTO.setDateUpdated(LocalDate.now());
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        clientDTO.setTheUser(user);
        return new ResponseEntity<>(clientService.createClient(clientDTO), HttpStatus.CREATED);
    }

    //get all clients
    @GetMapping
    public List<ClientDTO> getAllClients(){
        return clientService.getAllClients();
    }
}
