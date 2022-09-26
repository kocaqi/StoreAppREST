package com.localweb.storeapp.repository;

import com.localweb.thelogin.thelogin.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findUserByEmail(String email);

	User findUserByFirstName(String name);

	User getUserById(int id);

}

