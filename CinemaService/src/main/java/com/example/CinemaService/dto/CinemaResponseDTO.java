package com.example.CinemaService.dto;

import java.util.Set;

public record CinemaResponseDTO(
    Long cinemaId,
    String title,
    String address,
    Set<HallWithoutSeatsResponseDTO> halls
) {
}
