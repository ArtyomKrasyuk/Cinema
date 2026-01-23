package com.example.CinemaService.dto;

public record SeatResponseDTO(
    long seatId,
    String type,
    int row,
    int number
) {
}
