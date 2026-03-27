package com.example.APIGateway.dto;

public record UserPasswordRequestDTO(
        String oldPassword,
        String newPassword
) {
}
