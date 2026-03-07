package com.example.CinemaService.dto;

public record ShowtimeWithHallResponseDTO(
        long showtimeId,
        HallWithFactorResponseDTO hall,
        String movieTitle,
        String cinemaTitle,
        String cinemaAddress,
        String time,
        int basePrice
) {
}
