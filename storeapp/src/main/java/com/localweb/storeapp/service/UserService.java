package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Role;
import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.service.exception.ResourceNotFoundException;
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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO create(UserDTO userDTO) {
        //convert DTO to entity
        User user = modelMapper.map(userDTO, User.class);
        user.setDateCreated(LocalDate.now());
        user.setDateUpdated(LocalDate.now());
        user.setEnabled(1);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        //user.setRoles(userDTO.getRolesIds());

        List<Integer> roles = userDTO.getRolesIds();
        for (int roleId : roles) {
            Role role = roleRepository.findById(roleId).orElseThrow(() -> new UsernameNotFoundException("Role with not found!"));
            user.addRole(role);
        }

        User newUser = userRepository.save(user);

        //convert entity to DTO
        return modelMapper.map(newUser, UserDTO.class);
    }

    public Response<UserDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> users = userRepository.findAll(pageable);

        List<User> userList = users.getContent();

        List<UserDTO> content = userList.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());

        Response<UserDTO> response = new Response<>();
        response.setContent(content);
        response.setPageNo(users.getNumber());
        response.setPageSize(users.getSize());
        response.setTotalElements(users.getTotalElements());
        response.setTotalPages(users.getTotalPages());
        response.setLast(users.isLast());

        return response;
    }

    public UserDTO getById(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User", "id", id));
        return modelMapper.map(user, UserDTO.class);
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

        return modelMapper.map(updatedUser, UserDTO.class);
    }
}

