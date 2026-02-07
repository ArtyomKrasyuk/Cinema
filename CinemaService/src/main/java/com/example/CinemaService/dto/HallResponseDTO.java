package com.example.CinemaService.dto;

import java.util.Set;

public record HallResponseDTO(
     long hallId,
     long cinemaId,
     int number,
     Set<SeatResponseDTO> seats,
     String hallType
) {
}
