package com.example.CinemaService.dto;

import java.util.Set;

public record HallRequestDTO(
    long cinemaId,
    int number,
    Set<SeatRequestDTO> seats
) {
}
