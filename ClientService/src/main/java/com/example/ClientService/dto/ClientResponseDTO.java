package com.example.ClientService.dto;

public record ClientResponseDTO(
        Long client_id,
        String username,
        String login
) {
}
