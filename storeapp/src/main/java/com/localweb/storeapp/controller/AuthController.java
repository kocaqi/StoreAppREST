package com.localweb.storeapp.controller;

import com.localweb.storeapp.config.UserPrincipal;
import com.localweb.storeapp.payload.JWTAuthResponse;
import com.localweb.storeapp.payload.loginAndSignupDTO.LoginDTO;
import com.localweb.storeapp.security.JWTProvider;
import com.localweb.storeapp.service.UserService;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTProvider provider;
    private final UserPrincipal principal;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTProvider provider, UserPrincipal principal, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.provider = provider;
        this.principal = principal;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDTO loginDTO) throws Exception{
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        String token = provider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = provider.generateRefreshToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

}
