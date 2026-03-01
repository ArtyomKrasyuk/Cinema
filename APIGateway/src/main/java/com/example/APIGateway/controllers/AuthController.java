package com.example.APIGateway.controllers;

import com.example.APIGateway.dto.AuthDTO;
import com.example.APIGateway.dto.RefreshToken;
import com.example.APIGateway.dto.RegistrationDTO;
import com.example.APIGateway.service.AuthService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> refresh(@RequestBody RefreshToken refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken.token()));
    }

    @GetMapping("/api/test/admin")
    public ResponseEntity<?> testAdmin(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/test/client")
    public ResponseEntity<?> testClient(){
        return ResponseEntity.ok().build();
    }
}
