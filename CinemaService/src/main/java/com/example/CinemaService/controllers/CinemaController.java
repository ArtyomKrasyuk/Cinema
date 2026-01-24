package com.example.CinemaService.controllers;

import com.example.CinemaService.dto.CinemaRequestDTO;
import com.example.CinemaService.dto.CinemaResponseDTO;
import com.example.CinemaService.services.CinemaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
@AllArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @GetMapping
    public List<CinemaResponseDTO> findAll(){
        return cinemaService.findAll();
    }

    @GetMapping("/{cinemaId}")
    public CinemaResponseDTO findById(@PathVariable long cinemaId){
        return cinemaService.findById(cinemaId);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CinemaRequestDTO dto){
        cinemaService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{cinemaId}")
    public ResponseEntity<?> update(@PathVariable long cinemaId, @RequestBody CinemaRequestDTO dto){
        cinemaService.update(dto, cinemaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cinemaId}")
    public ResponseEntity<?> deleteById(@PathVariable long cinemaId){
        cinemaService.deleteById(cinemaId);
        return ResponseEntity.ok().build();
    }
}
