package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.UserDTO;
import com.localweb.storeapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create user
    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO){
        userDTO.setDateCreated(LocalDate.now());
        userDTO.setDateUpdated(LocalDate.now());
        userDTO.setEnabled(1);
        return new ResponseEntity<>(userService.create(userDTO), HttpStatus.CREATED);
    }

    //get all users
    @GetMapping
    public List<UserDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy){
        return userService.getAll(pageNo, pageSize, sortBy);
    }

    //get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    //update user
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable(name = "id") int id){
        UserDTO userResponse = userService.update(userDTO, id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
