package com.example.CinemaService.services;

import com.example.CinemaService.dto.CinemaRequestDTO;
import com.example.CinemaService.dto.CinemaResponseDTO;
import com.example.CinemaService.mappers.CinemaMapper;
import com.example.CinemaService.models.Cinema;
import com.example.CinemaService.repos.CinemaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;

    public long save(CinemaRequestDTO dto){
        return cinemaRepository.save(cinemaMapper.toEntity(dto)).getCinemaId();
    }

    public List<CinemaResponseDTO> findAll(){
        return cinemaRepository.findAll().stream().map(cinemaMapper::toDto).toList();
    }

    public CinemaResponseDTO findById(long cinemaId){
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new RuntimeException("Кинотеатр с id " + cinemaId + "не найден"));
        return cinemaMapper.toDto(cinema);
    }

    public void update(CinemaRequestDTO dto, long cinemaId){
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new RuntimeException("Кинотеатр с id " + cinemaId + "не найден"));
        cinemaMapper.update(dto, cinema);
        cinemaRepository.save(cinema);
    }

    public void deleteById(long cinemaId){
        cinemaRepository.deleteById(cinemaId);
    }
}
