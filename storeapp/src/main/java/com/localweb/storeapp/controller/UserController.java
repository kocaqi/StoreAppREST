package com.localweb.storeapp.controller;

import com.localweb.storeapp.entity.Role;
import com.localweb.storeapp.payload.UserDTO;
import com.localweb.storeapp.service.RoleService;
import com.localweb.storeapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        userDTO.setDateCreated(LocalDate.now());
        userDTO.setDateUpdated(LocalDate.now());
        userDTO.setEnabled(1);
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }
}
