package com.example.APIGateway.controllers;

import com.example.APIGateway.dto.AuthDTO;
import com.example.APIGateway.dto.RegistrationDTO;
import com.example.APIGateway.service.AuthService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(
                          AuthService authService
    ) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public AccessTokenResponse auth(@RequestBody AuthDTO authDTO) {
        return authService.authenticateUser(authDTO.login(), authDTO.password());
    }

    @PostMapping("/reg")
    public AccessTokenResponse auth(@RequestBody RegistrationDTO registrationDTO) {
        String uuid = authService.registerUser(registrationDTO);
        return authService.authenticateUser(registrationDTO.login(), registrationDTO.password());
    }

    @GetMapping("/api/test/admin")
    public String testAdmin(){
        return "Hello admin";
    }

    @GetMapping("/api/test/client")
    public String testClient(){
        return "Hello client";
    }
}
