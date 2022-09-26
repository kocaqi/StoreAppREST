package com.localweb.storeapp.service;

import com.localweb.thelogin.thelogin.dao.RoleRepository;
import com.localweb.thelogin.thelogin.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    //@Transactional
    public Role findRoleByName(String name){
        return roleRepository.findRoleByName(name);
    }

    public void save(Role roleByName) {
        roleRepository.save(roleByName);
    }
}
