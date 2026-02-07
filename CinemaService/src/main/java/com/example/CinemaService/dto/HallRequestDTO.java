package com.example.CinemaService.dto;

import java.util.List;

public record HallRequestDTO(
    long cinemaId,
    int number,
    List<SeatRequestDTO> seats,
    String hallType
) {
}
