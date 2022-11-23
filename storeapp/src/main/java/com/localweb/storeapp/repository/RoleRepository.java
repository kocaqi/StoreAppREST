package com.localweb.storeapp.repository;

import com.localweb.storeapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    Role findRoleByName(String name);

    Optional<Role> findRoleById(long id);
}
