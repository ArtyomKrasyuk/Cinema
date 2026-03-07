package com.example.CinemaService.dto;

public record SeatWithFactorResponseDTO(
        long seatId,
        String type,
        int row,
        int number,
        double factor
) {
}
