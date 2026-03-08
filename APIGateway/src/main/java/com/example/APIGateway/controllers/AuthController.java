package com.example.APIGateway.controllers;

import com.example.APIGateway.dto.AuthDTO;
import com.example.APIGateway.dto.RefreshToken;
import com.example.APIGateway.dto.RegistrationDTO;
import com.example.APIGateway.service.AuthService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/auth")
    public String auth(@RequestBody AuthDTO authDTO){
        return authService.authenticateUser(authDTO.login(), authDTO.password());
    }

    @PostMapping("/reg")
    public String auth(@RequestBody RegistrationDTO registrationDTO){
        String uuid = authService.registerUser(registrationDTO);
        return authService.authenticateUser(registrationDTO.login(), registrationDTO.password());
    }

    @PostMapping("/refresh")
    public String refresh(@RequestBody RefreshToken refreshToken){
        return authService.refreshToken(refreshToken.token());
    }

    @GetMapping("/test/admin")
    @PreAuthorize("hasRole('admin')")
    public Mono<?> testAdmin(){
        return Mono.empty();
    }

    @GetMapping("/test/client")
    @PreAuthorize("hasRole('client')")
    public Mono<?> testClient(){
        return Mono.empty();
    }
}
