package com.example.CinemaService.mappers;

import com.example.CinemaService.dto.SeatRequestDTO;
import com.example.CinemaService.dto.SeatResponseDTO;
import com.example.CinemaService.models.Hall;
import com.example.CinemaService.models.Seat;
import com.example.CinemaService.models.SeatType;
import com.example.CinemaService.repos.HallRepository;
import com.example.CinemaService.repos.SeatTypeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class SeatMapper {
    protected SeatTypeRepository seatTypeRepository;
    protected HallRepository hallRepository;

    public SeatMapper(SeatTypeRepository seatTypeRepository, HallRepository hallRepository){
        this.seatTypeRepository = seatTypeRepository;
        this.hallRepository = hallRepository;
    }

    @Mapping(target = "type", expression = "java(seat.getSeatType().getTitle())")
    public abstract SeatResponseDTO toDto(Seat seat);

    @Mapping(target = "seatId", ignore = true)
    @Mapping(target = "hall", source = "hall")
    @Mapping(target = "seatType", source = "type", qualifiedByName = "setSeatType")
    public abstract Seat toEntity(SeatRequestDTO dto, Hall hall);

    @Mapping(target = "seatId", ignore = true)
    @Mapping(target = "hall", ignore = true)
    @Mapping(target = "seatType", source = "type", qualifiedByName = "setSeatType")
    public abstract void update(SeatRequestDTO dto, @MappingTarget Seat seat);

    @Named("setSeatType")
    public SeatType setSeatType(String type){
        return seatTypeRepository.findByTitle(type).orElseThrow(() -> new RuntimeException("Не найден тип места с названием " + type));
    }

    @Named("setHall")
    public Hall setHall(long hallId){
        return hallRepository.findById(hallId).orElseThrow(() -> new RuntimeException("Не найден зал с id " + hallId));
    }
}
