package com.example.CinemaService.services;

import com.example.CinemaService.dto.HallRequestDTO;
import com.example.CinemaService.dto.HallResponseDTO;
import com.example.CinemaService.mappers.HallMapper;
import com.example.CinemaService.models.Hall;
import com.example.CinemaService.repos.HallRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class HallService {
    private final HallRepository hallRepository;
    private final HallMapper hallMapper;
    private final SeatService seatService;

    public HallResponseDTO findById(long hallId){
        return hallMapper.toDto(hallRepository.findById(hallId).orElseThrow(() -> new RuntimeException("Зал с id " + hallId + "не найден")));
    }

    public long save(HallRequestDTO dto){
        Hall hall = hallRepository.save(hallMapper.toEntity(dto));
        seatService.saveList(dto.seats(), hall);
        return hall.getHallId();
    }

    public void update(HallRequestDTO dto, long hallId){
        Hall hall = hallRepository.findById(hallId).orElseThrow(() -> new RuntimeException("Зал с id " + hallId + "не найден"));
        hallMapper.update(dto, hall);
        hall = hallRepository.save(hall);
        seatService.deleteSet(hall.getSeats());
        seatService.saveList(dto.seats(), hall);
    }

    public void deleteById(long hallId){
        hallRepository.deleteById(hallId);
    }
}
