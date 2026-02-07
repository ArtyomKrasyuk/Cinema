package com.example.CinemaService.dto;

public record ShowtimeWithMinPriceResponseDTO(
        long showtimeId,
        CinemaResponseDTO cinema,
        long hallId,
        String movieTitle,
        String time,
        int basePrice,
        int minPrice
) {
}
