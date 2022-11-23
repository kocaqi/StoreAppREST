package com.localweb.storeapp.config;

import com.localweb.storeapp.security.JWTAuthenticationEntryPoint;
import com.localweb.storeapp.security.JWTAuthenticationFilter;
import com.localweb.storeapp.security.JWTProvider;
import com.localweb.storeapp.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JWTAuthenticationEntryPoint authenticationEntryPoint;
	private final JWTProvider provider;
	private final MyUserDetailsService userDetailsService;

	@Autowired
	public SecurityConfig(JWTAuthenticationEntryPoint authenticationEntryPoint, JWTProvider provider, MyUserDetailsService userDetailsService) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.provider = provider;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/api").permitAll()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/users/**").hasAnyRole("ADMIN")
				.antMatchers("/api/clients/**").hasAnyRole("ADMIN", "OPERATOR")
				.antMatchers("/api/products/**").hasAnyRole("ADMIN")
				.antMatchers("/api/orders/**").hasAnyRole("ADMIN", "OPERATOR")
				.antMatchers("/v2/api-docs/**").permitAll()
				.antMatchers("/swagger-ui/**").permitAll()
				.antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/swagger-ui.html/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/api/orders", true)
			.and()
				.logout()
				.logoutSuccessUrl("/login");
		http.addFilterBefore(new JWTAuthenticationFilter(provider, userDetailsService), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}

