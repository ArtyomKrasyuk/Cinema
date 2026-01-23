package com.example.CinemaService.services;

import com.example.CinemaService.repos.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
}
