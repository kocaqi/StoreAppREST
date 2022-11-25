package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Role;
import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.payload.entityDTO.RoleDTO;
import com.localweb.storeapp.payload.entityDTO.UserDTO;
import com.localweb.storeapp.payload.saveDTO.UserSaveDTO;
import com.localweb.storeapp.repository.RoleRepository;
import com.localweb.storeapp.repository.UserRepository;
import com.localweb.storeapp.search.SearchCriteria;
import com.localweb.storeapp.search.Specify;
import com.localweb.storeapp.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public UserDTO create(UserSaveDTO userSaveDTO) {
        //convert DTO to entity
        User user = modelMapper.map(userSaveDTO, User.class);
        user.setDateCreated(LocalDate.now());
        user.setDateUpdated(LocalDate.now());
        user.setEnabled(1);
        user.setPassword(passwordEncoder.encode(userSaveDTO.getPassword()));
        //user.setRoles(userSaveDTO.getRoles());

        /*List<RoleDTO> roles = userSaveDTO.getRoles();
        for (RoleDTO roleDTO : roles) {
            //Role role = roleRepository.findRoleById(roleId).orElseThrow(() -> new UsernameNotFoundException("Role with not found!"));
            Role role = modelMapper.map(roleDTO, Role.class);
            user.addRole(role);
        }*/

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

    public UserDTO getById(long id) {
        User user = userRepository.findUserById(id).orElseThrow(()->new ResourceNotFoundException("User", "id", id));
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO update(UserSaveDTO userSaveDTO, long id) {
        User user = userRepository.findUserById(id).orElseThrow(()->new ResourceNotFoundException("User", "id", id));
        user.setFirstName(userSaveDTO.getFirstName());
        user.setLastName(userSaveDTO.getLastName());
        user.setEmail(userSaveDTO.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userSaveDTO.getPassword()));
        user.setDateUpdated(LocalDate.now());
        user.getRoles().clear();
        List<RoleDTO> roles = userSaveDTO.getRoles();
        for (RoleDTO roleDTO : roles) {
            //Role role = roleRepository.findRoleById(roleId).orElseThrow(() -> new UsernameNotFoundException("Role with not found!"));
            Role role = modelMapper.map(roleDTO, Role.class);
            user.addRole(role);
        }
        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public UserDTO addRole(long userId, long roleId) {
        User user = userRepository.findUserById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        Role role = roleRepository.findRoleById(roleId).orElseThrow(()->new ResourceNotFoundException("User", "id", roleId));
        user.addRole(role);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    public UserDTO removeRole(long userId, long roleId) {
        User user = userRepository.findUserById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        Role role = roleRepository.findRoleById(roleId).orElseThrow(()->new ResourceNotFoundException("Role", "id", roleId));
        //user.removeRole(role);
        List<Role> roles = user.getRoles();
        roles.remove(role);
        user.setRoles(roles);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    public List<UserDTO> searchBy(String keyword) {
        Specify<User> specifyByFirstName = new Specify<>(new SearchCriteria("firstName", ":", keyword));
        Specify<User> specifyByLastName = new Specify<>(new SearchCriteria("lastName", ":", keyword));
        Specify<User> specifyByEmail = new Specify<>(new SearchCriteria("email", ":", keyword));

        List<User> users = userRepository.findAll(Specification
                .where(specifyByFirstName).and(specifyByLastName).and(specifyByEmail));

        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }
}

