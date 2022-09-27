package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.payload.UserDTO;
import com.localweb.storeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService{

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO){
        //convert DTO to entity
        User user = mapToEntity(userDTO);
        User newUser = userRepository.save(user);

        //convert entity to DTO
        UserDTO userResponse = mapToDTO(newUser);

        return userResponse;
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> mapToDTO(user)).collect(Collectors.toList());
    }

    private UserDTO mapToDTO(User newUser){
        UserDTO userResponse = new UserDTO();
        userResponse.setId(newUser.getId());
        userResponse.setFirstName(newUser.getFirstName());
        userResponse.setLastName(newUser.getLastName());
        userResponse.setEmail(newUser.getEmail());
        userResponse.setPassword(newUser.getPassword());
        userResponse.setDateCreated(newUser.getDateCreated());
        userResponse.setDateUpdated(newUser.getDateUpdated());
        userResponse.setRoles(newUser.getRoles());
        userResponse.setEnabled(newUser.getEnabled());
        userResponse.setClients(newUser.getClients());
        userResponse.setOrders(newUser.getOrders());
        return userResponse;
    }

    private User mapToEntity(UserDTO userDTO){
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setDateCreated(userDTO.getDateCreated());
        user.setDateUpdated(userDTO.getDateUpdated());
        user.setRoles(userDTO.getRoles());
        user.setEnabled(userDTO.getEnabled());
        user.setClients(userDTO.getClients());
        user.setOrders(userDTO.getOrders());
        return user;
    }
}

