package com.example.CinemaService.dto;

import java.util.List;

public record MovieResponseDTO(
        long movieId,
        String title,
        List<String> genres,
        int duration,
        String poster,
        String description
) {
}
