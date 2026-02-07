package com.example.CinemaService.controllers;

import com.example.CinemaService.dto.HallTypeRequestDTO;
import com.example.CinemaService.dto.HallTypeResponseDTO;
import com.example.CinemaService.services.HallTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hall-types")
@AllArgsConstructor
public class HallTypeController {
    private final HallTypeService hallTypeService;

    @GetMapping
    public List<HallTypeResponseDTO> findAll(){
        return hallTypeService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody HallTypeRequestDTO dto){
        hallTypeService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{hallTypeId}")
    public ResponseEntity<?> update(@PathVariable long hallTypeId, @RequestBody HallTypeRequestDTO dto){
        hallTypeService.update(dto, hallTypeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{hallTypeId}")
    public ResponseEntity<?> deleteById(@PathVariable long hallTypeId){
        hallTypeService.deleteById(hallTypeId);
        return ResponseEntity.ok().build();
    }
}
