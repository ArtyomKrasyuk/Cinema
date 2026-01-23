package com.example.CinemaService.dto;

public record HallWithoutSeatsResponseDTO(
    long hallId,
    long cinemaId,
    int number
) {
}
