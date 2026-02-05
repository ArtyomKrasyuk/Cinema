package com.example.CinemaService.dto;

public record ShowtimeRequestDTO(
        long hallId,
        String movieTitle,
        String time,
        int basePrice
) {
}
