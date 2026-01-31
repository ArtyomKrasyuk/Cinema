package com.example.CinemaService.controllers;

import com.example.CinemaService.dto.MovieRequestDTO;
import com.example.CinemaService.dto.MovieResponseDTO;
import com.example.CinemaService.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<MovieResponseDTO> findAll(){
        return movieService.findAll();
    }

    @GetMapping("/{movieId}")
    public MovieResponseDTO findById(@PathVariable long movieId){
        return movieService.findById(movieId);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MovieRequestDTO dto){
        movieService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<?> update(@PathVariable long movieId, @RequestBody MovieRequestDTO dto){
        movieService.update(dto, movieId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> delete(@PathVariable long movieId){
        movieService.deleteById(movieId);
        return ResponseEntity.ok().build();
    }
}
