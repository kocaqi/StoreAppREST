package com.localweb.storeapp.repository;

import com.localweb.thelogin.thelogin.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleByName(String name);

}
