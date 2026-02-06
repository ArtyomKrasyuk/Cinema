package com.example.CinemaService.dto;

public record ShowtimeWithMovieResponseDTO(
        long showtimeId,
        MovieResponseDTO movie,
        String time
) {
}
