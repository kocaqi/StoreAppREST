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
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api").hasAnyRole("ADMIN", "OPERATOR")
				.antMatchers("/api/users/**").hasAnyRole("ADMIN")
				.antMatchers("/api/clients/**").hasAnyRole("ADMIN", "OPERATOR")
				.antMatchers("/api/products/**").hasAnyRole("ADMIN")
				.antMatchers("/api/orders/**").hasAnyRole("ADMIN", "OPERATOR")
				.anyRequest().authenticated()
			.and()
				.httpBasic()
			.and()
				.formLogin()
				.usernameParameter("email").passwordParameter("password")
			.and()
				.logout().logoutSuccessUrl("/login");
	}

}

