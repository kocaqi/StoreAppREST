package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.payload.UserDTO;
import com.localweb.storeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService{

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDTO create(UserDTO userDTO){
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

    public List<UserDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> users = userRepository.findAll(pageable);

        List<User> userList = users.getContent();

        return userList.stream().map(this::mapToDTO).collect(Collectors.toList());
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

    public UserDTO getById(int id) {
        User user = userRepository.findUserById(id);
        UserDTO userDTO = mapToDTO(user);
        return userDTO;
    }

    public UserDTO update(UserDTO userDTO, int id) {
        User user = userRepository.findUserById(id);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setDateUpdated(LocalDate.now());
        user.setRoles(userDTO.getRoles());
        User updatedUser = userRepository.save(user);

        UserDTO userResponse = mapToDTO(updatedUser);
        return userResponse;
    }
}

