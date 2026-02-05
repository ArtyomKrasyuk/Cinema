package com.example.CinemaService.dto;

public record ShowtimeResponseDTO(
        long showtimeId,
        CinemaResponseDTO cinema,
        long hallId,
        String movieTitle,
        String time,
        int basePrice
) {
}
