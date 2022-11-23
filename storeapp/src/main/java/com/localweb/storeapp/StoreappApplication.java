package com.localweb.storeapp;

import com.localweb.storeapp.entity.Role;
import com.localweb.storeapp.entity.User;
import com.localweb.storeapp.repository.RoleRepository;
import com.localweb.storeapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class StoreappApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(StoreappApplication.class, args);
	}

	RoleRepository roleRepository;
	UserRepository userRepository;

	@Autowired
	public StoreappApplication(RoleRepository roleRepository, UserRepository userRepository){
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public void run(String... args) {
		Role admin = new Role();
		admin.setName("ROLE_ADMIN");
		Role checkAdmin = roleRepository.findRoleByName(admin.getName());
		if(checkAdmin==null)
			roleRepository.save(admin);

		Role operator = new Role();
		operator.setName("ROLE_OPERATOR");
		Role checkOperator = roleRepository.findRoleByName((operator.getName()));
		if(checkOperator==null)
			roleRepository.save(operator);

		List<Role> roles = new ArrayList<>();
		roles.add(admin);

		User user = new User();
		user.setEnabled(1);
		user.setEmail("string");
		user.setPassword(new BCryptPasswordEncoder().encode("string"));
		user.setFirstName("string");
		user.setLastName("string");
		user.setDateCreated(LocalDate.now());
		user.setDateUpdated(LocalDate.now());
		user.setRoles(roles);
		User checkUser = userRepository.findByEmail("string");
		if(checkUser==null)
			userRepository.save(user);
	}
}
