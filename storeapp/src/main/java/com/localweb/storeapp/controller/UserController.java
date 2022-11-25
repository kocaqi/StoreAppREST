package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.payload.entityDTO.UserDTO;
import com.localweb.storeapp.payload.saveDTO.UserSaveDTO;
import com.localweb.storeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create user
    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserSaveDTO userSaveDTO){
        return new ResponseEntity<>(userService.create(userSaveDTO), HttpStatus.CREATED);
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
    public ResponseEntity<UserDTO> getById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    //update user
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserSaveDTO userSaveDTO, @PathVariable(name = "id") long id){
        return new ResponseEntity<>(userService.update(userSaveDTO, id), HttpStatus.OK);
    }

    @PutMapping("/{userId}/addRole/{roleId}")
    public ResponseEntity<UserDTO> addRole(@PathVariable(name = "userId") long userId,
                                           @PathVariable(name = "roleId") long roleId) {
        return new ResponseEntity<>(userService.addRole(userId, roleId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/removeRole/{roleId}")
    public ResponseEntity<UserDTO> removeRole(@PathVariable(name = "userId") long userId,
                                           @PathVariable(name = "roleId") long roleId) {
        return new ResponseEntity<>(userService.removeRole(userId, roleId), HttpStatus.OK);
    }
}
