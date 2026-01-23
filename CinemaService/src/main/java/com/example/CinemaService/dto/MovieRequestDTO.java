package com.example.CinemaService.dto;

import java.util.List;

public record MovieRequestDTO(
     String title,
     List<String> genres,
     int duration,
     String poster,
     String description
) {
}
