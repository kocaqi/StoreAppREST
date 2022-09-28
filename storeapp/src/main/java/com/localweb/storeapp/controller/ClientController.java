package com.localweb.storeapp.controller;

import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.payload.ClientDTO;
import com.localweb.storeapp.payload.ProductDTO;
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
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO clientDTO,
                                                Principal principal){
        clientDTO.setDateCreated(LocalDate.now());
        clientDTO.setDateUpdated(LocalDate.now());
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        clientDTO.setTheUser(user);
        return new ResponseEntity<>(clientService.create(clientDTO), HttpStatus.CREATED);
    }

    //get all clients
    @GetMapping
    public List<ClientDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                  @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy){
        return clientService.getAll(pageNo, pageSize, sortBy);
    }

    //get client by id
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(clientService.getById(id), HttpStatus.OK);
    }

    //update client
    @PutMapping("/update/{id}")
    public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO clientDTO, @PathVariable(name = "id") int id){
        ClientDTO clientResponse = clientService.update(clientDTO, id);
        return new ResponseEntity<>(clientResponse, HttpStatus.OK);
    }
}
