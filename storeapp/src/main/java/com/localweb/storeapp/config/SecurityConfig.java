package com.localweb.storeapp.config;

import com.localweb.storeapp.security.JWTAuthenticationEntryPoint;
import com.localweb.storeapp.security.JWTAuthenticationFilter;
import com.localweb.storeapp.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final MyUserDetailsService userDetailsService;

	private final JWTAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	public SecurityConfig(MyUserDetailsService userDetailsService, JWTAuthenticationEntryPoint authenticationEntryPoint) {
		this.userDetailsService = userDetailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	public SecurityConfig(boolean disableDefaults, MyUserDetailsService userDetailsService, JWTAuthenticationEntryPoint authenticationEntryPoint) {
		super(disableDefaults);
		this.userDetailsService = userDetailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter(){
		return new JWTAuthenticationFilter();
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

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
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
				.antMatchers("/api").hasAnyRole("ADMIN", "OPERATOR")
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/users/**").hasRole("ADMIN")
				.antMatchers("/api/clients/**").hasAnyRole("ADMIN", "OPERATOR")
				.antMatchers("/api/products/**").hasRole("ADMIN")
				.antMatchers("/api/orders/**").hasAnyRole("ADMIN", "OPERATOR")
				.antMatchers("/v2/api-docs/**").permitAll()
				.antMatchers("/swagger-ui/**").permitAll()
				.antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/swagger-ui.html/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.usernameParameter("email")
				.passwordParameter("password")
			.and()
				.logout()
				.logoutSuccessUrl("/login");
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}

