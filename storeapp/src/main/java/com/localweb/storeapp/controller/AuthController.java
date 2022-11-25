package com.localweb.storeapp.controller;

import com.localweb.storeapp.payload.JWTAuthResponse;
import com.localweb.storeapp.payload.loginAndSignupDTO.LoginDTO;
import com.localweb.storeapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
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
