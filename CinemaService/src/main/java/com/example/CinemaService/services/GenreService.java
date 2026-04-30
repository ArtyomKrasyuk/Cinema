package com.example.CinemaService.services;

import com.example.CinemaService.models.Genre;
import com.example.CinemaService.repos.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<String> getGenres(){
        return genreRepository.findAll().stream().map(Genre::getTitle).toList();
    }
}
