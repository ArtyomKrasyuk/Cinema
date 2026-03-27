package com.example.APIGateway.controllers;

import com.example.APIGateway.dto.*;
import com.example.APIGateway.service.AuthService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @PutMapping("/profile")
    @PreAuthorize("hasRole('client')")
    public Mono<?> updateUserProfile(
            @RequestBody UserProfileRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt
    ){
        authService.updateUserProfile(jwt.getSubject(), dto.firstName(), dto.lastName());
        return Mono.empty();
    }

    @PutMapping("/password")
    @PreAuthorize("hasRole('client')")
    public Mono<?> updatePassword(
            @RequestBody UserPasswordRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt
    ){
        authService.changePasswordSecurely(
                jwt.getSubject(),
                jwt.getClaimAsString("preferred_username"),
                dto.oldPassword(),
                dto.newPassword()
        );
        return Mono.empty();
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
