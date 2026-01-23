package com.example.CinemaService.services;

import com.example.CinemaService.dto.SeatRequestDTO;
import com.example.CinemaService.mappers.SeatMapper;
import com.example.CinemaService.models.Hall;
import com.example.CinemaService.models.Seat;
import com.example.CinemaService.repos.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    public void saveSet(Set<SeatRequestDTO> seats, Hall hall){
        seatRepository.saveAll(seats.stream().map(seat -> seatMapper.toEntity(seat, hall)).toList());
    }

    public void deleteSet(Set<Seat> seats){
        seatRepository.deleteAll(seats);
    }
}
