package com.example.CinemaService.controllers;

import com.example.CinemaService.dto.HallRequestDTO;
import com.example.CinemaService.dto.HallResponseDTO;
import com.example.CinemaService.services.HallService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/halls")
@AllArgsConstructor
public class HallController {
    private final HallService hallService;

    @GetMapping("/{hallId}")
    public HallResponseDTO findById(@PathVariable long hallId){
        return hallService.findById(hallId);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody HallRequestDTO dto){
        hallService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{hallId}")
    public ResponseEntity<?> update(@PathVariable long hallId, @RequestBody HallRequestDTO dto){
        hallService.update(dto, hallId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{hallId}")
    public ResponseEntity<?> deleteById(@PathVariable long hallId){
        hallService.deleteById(hallId);
        return ResponseEntity.ok().build();
    }
}
