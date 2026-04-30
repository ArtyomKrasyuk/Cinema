package com.example.CinemaService.controllers;

import com.example.CinemaService.dto.GenresResponseDTO;
import com.example.CinemaService.services.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public GenresResponseDTO getGenres(){
        return new GenresResponseDTO(genreService.getGenres());
    }
}
