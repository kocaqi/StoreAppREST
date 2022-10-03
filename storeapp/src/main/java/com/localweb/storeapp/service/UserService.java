package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.exception.ResourceNotFoundException;
import com.localweb.storeapp.payload.entityDTO.UserDTO;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService{

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO create(UserDTO userDTO){
        //convert DTO to entity
        User user = mapToEntity(userDTO);
        User newUser = userRepository.save(user);

        //convert entity to DTO
        return mapToDTO(newUser);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()->new UsernameNotFoundException("User with email "+email+" not found!" ));
    }

    public Response<UserDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> users = userRepository.findAll(pageable);

        List<User> userList = users.getContent();

        List<UserDTO> content= userList.stream().map(this::mapToDTO).collect(Collectors.toList());

        Response<UserDTO> response = new Response<>();
        response.setContent(content);
        response.setPageNo(users.getNumber());
        response.setPageSize(users.getSize());
        response.setTotalElements(users.getTotalElements());
        response.setTotalPages(users.getTotalPages());
        response.setLast(users.isLast());

        return response;
    }

    private UserDTO mapToDTO(User newUser){
        UserDTO userDTO = modelMapper.map(newUser, UserDTO.class);
        /*UserDTO userResponse = new UserDTO();
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
        userResponse.setOrders(newUser.getOrders());*/
        return userDTO;
    }

    private User mapToEntity(UserDTO userDTO){
        /*User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setDateCreated(userDTO.getDateCreated());
        user.setDateUpdated(userDTO.getDateUpdated());
        user.setRoles(userDTO.getRoles());
        user.setEnabled(userDTO.getEnabled());
        user.setClients(userDTO.getClients());
        user.setOrders(userDTO.getOrders());*/
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO getById(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User", "id", id));
        return mapToDTO(user);
    }

    public UserDTO update(UserDTO userDTO, int id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User", "id", id));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setDateUpdated(LocalDate.now());
        user.setRoles(userDTO.getRoles());
        User updatedUser = userRepository.save(user);

        return mapToDTO(updatedUser);
    }
}

