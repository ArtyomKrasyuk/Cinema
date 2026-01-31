package com.example.CinemaService.services;

import com.example.CinemaService.dto.SeatRequestDTO;
import com.example.CinemaService.mappers.SeatMapper;
import com.example.CinemaService.models.Hall;
import com.example.CinemaService.models.Seat;
import com.example.CinemaService.repos.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    public void saveList(List<SeatRequestDTO> seats, Hall hall){
        seatRepository.saveAll(seats.stream().map(seat -> seatMapper.toEntity(seat, hall)).toList());
    }

    public void deleteSet(Set<Seat> seats){
        seatRepository.deleteAllInBatch(seats);
    }
}
