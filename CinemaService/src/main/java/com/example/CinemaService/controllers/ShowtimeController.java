package com.example.CinemaService.controllers;

import com.example.CinemaService.dto.ShowtimeRequestDTO;
import com.example.CinemaService.dto.ShowtimeResponseDTO;
import com.example.CinemaService.dto.ShowtimeWithMinPriceResponseDTO;
import com.example.CinemaService.dto.ShowtimeWithMovieResponseDTO;
import com.example.CinemaService.services.ShowtimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
@AllArgsConstructor
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @GetMapping
    public List<ShowtimeResponseDTO> findAll(){
        return showtimeService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ShowtimeRequestDTO dto){
        showtimeService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{showtimeId}")
    public ResponseEntity<?> update(@PathVariable long showtimeId, @RequestBody ShowtimeRequestDTO dto){
        showtimeService.update(dto, showtimeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<?> delete(@PathVariable long showtimeId){
        showtimeService.deleteById(showtimeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/movies")
    public List<ShowtimeWithMovieResponseDTO> findShowtimesWithMovies(){
        return showtimeService.findShowtimesWithMovies();
    }

    @GetMapping("/price")
    public List<ShowtimeWithMinPriceResponseDTO> findShowtimesWithMinPrice(){
        return showtimeService.findShowtimesWithMinPrice();
    }

    @GetMapping("/price/{movieId}")
    public List<ShowtimeWithMinPriceResponseDTO> findShowtimesWithMinPriceByMovie(@PathVariable long movieId){
        return showtimeService.findShowtimesWithMinPrice();
    }
}
