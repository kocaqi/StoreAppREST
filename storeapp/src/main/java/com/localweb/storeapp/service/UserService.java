package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Role;
import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.exception.ResourceNotFoundException;
import com.localweb.storeapp.payload.entityDTO.UserDTO;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.repository.RoleRepository;
import com.localweb.storeapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO create(UserDTO userDTO){
        //convert DTO to entity
        User user = mapToEntity(userDTO);
        user.setDateCreated(LocalDate.now());
        user.setDateUpdated(LocalDate.now());
        user.setEnabled(1);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        //user.setRoles(userDTO.getRolesIds());

        List<Integer> roles = userDTO.getRolesIds();
        for (int roleId : roles) {
            Role role = roleRepository.findById(roleId).orElseThrow(()->new UsernameNotFoundException("Role with not found!"));
            user.addRole(role);
        }

        User newUser = userRepository.save(user);

        //convert entity to DTO
        return mapToDTO(newUser);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()->new UsernameNotFoundException("User with email "+email+" not found!"));
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
        return modelMapper.map(newUser, UserDTO.class);
    }

    private User mapToEntity(UserDTO userDTO){
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
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setDateUpdated(LocalDate.now());
        user.getRoles().clear();
        List<Integer> roles = userDTO.getRolesIds();
        for (int roleId : roles) {
            Role role = roleRepository.findById(roleId).orElseThrow(()->new UsernameNotFoundException("Role with not found!"));
            user.addRole(role);
        }
        User updatedUser = userRepository.save(user);

        return mapToDTO(updatedUser);
    }
}

