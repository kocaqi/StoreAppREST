package com.localweb.storeapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return authenticationProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").hasAnyRole("ADMIN", "OPERATOR")
				.antMatchers("/users/**").hasAnyRole("ADMIN")
				.antMatchers("/clients/**").hasAnyRole("ADMIN", "OPERATOR")
				.antMatchers("/products/**").hasAnyRole("ADMIN")
				.antMatchers("/orders/**").hasAnyRole("ADMIN", "OPERATOR")
			.and()
				.formLogin().loginPage("/login")
				.loginProcessingUrl("/authenticateTheUser").permitAll()
				.usernameParameter("email").passwordParameter("password")
			.and()
				.logout().logoutSuccessUrl("/login")
			.and()
				.exceptionHandling().accessDeniedPage("/access-denied");
	}

}

