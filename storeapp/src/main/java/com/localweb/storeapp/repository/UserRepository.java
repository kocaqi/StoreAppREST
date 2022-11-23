package com.localweb.storeapp.repository;

import com.localweb.storeapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
	
	Optional<User> findUserByEmail(String email);
    User findByEmail(String email);

    Optional<User> findUserById(long id);
}

