package com.example.CinemaService.dto;

import java.util.Set;

public record HallWithFactorResponseDTO(
        long hallId,
        int number,
        Set<SeatWithFactorResponseDTO> seats,
        double hallTypeFactor,
        String hallType
) {
}
