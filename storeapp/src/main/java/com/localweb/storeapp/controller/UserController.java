package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.entityDTO.UserDTO;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create user
    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO){
        userDTO.setDateCreated(LocalDate.now());
        userDTO.setDateUpdated(LocalDate.now());
        userDTO.setEnabled(1);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return new ResponseEntity<>(userService.create(userDTO), HttpStatus.CREATED);
    }

    //get all users
    @GetMapping
    public Response<UserDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                           @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                           @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                           @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return userService.getAll(pageNo, pageSize, sortBy, sortDir);
    }

    //get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    //update user
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO, @PathVariable(name = "id") int id){
        UserDTO userResponse = userService.update(userDTO, id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
