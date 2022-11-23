package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.payload.entityDTO.ClientDTO;
import com.localweb.storeapp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //create client
    @PostMapping("/create")
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO clientDTO,
                                                Principal principal){
        return new ResponseEntity<>(clientService.create(clientDTO, principal), HttpStatus.CREATED);
    }

    //get all clients
    @GetMapping
    public Response<ClientDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return clientService.getAll(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/search")
    public List<ClientDTO> searchBy(@RequestParam String keyword){
        return clientService.searchBy(keyword);
    }

    //get client by id
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(clientService.getById(id), HttpStatus.OK);
    }

    //update client
    @PutMapping("/update/{id}")
    public ResponseEntity<ClientDTO> update(@Valid @RequestBody ClientDTO clientDTO, @PathVariable(name = "id") long id){
        ClientDTO clientResponse = clientService.update(clientDTO, id);
        return new ResponseEntity<>(clientResponse, HttpStatus.OK);
    }
}
