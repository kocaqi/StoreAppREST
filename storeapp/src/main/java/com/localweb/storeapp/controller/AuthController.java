package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.JWTAuthResponse;
import com.localweb.storeapp.payload.loginAndSignupDTO.LoginDTO;
import com.localweb.storeapp.security.JWTProvider;
import com.localweb.storeapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider provider;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JWTProvider provider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.provider = provider;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDTO loginDTO) throws Exception{
        return authService.authenticate(loginDTO);
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<JWTAuthResponse> refreshToken() {
        return authService.refreshToken();
    }

}
