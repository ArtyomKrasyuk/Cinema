package com.example.CinemaService.mappers;

import com.example.CinemaService.dto.HallRequestDTO;
import com.example.CinemaService.dto.HallResponseDTO;
import com.example.CinemaService.dto.HallWithoutSeatsResponseDTO;
import com.example.CinemaService.dto.SeatResponseDTO;
import com.example.CinemaService.models.Cinema;
import com.example.CinemaService.models.Hall;
import com.example.CinemaService.models.HallType;
import com.example.CinemaService.models.Seat;
import com.example.CinemaService.repos.CinemaRepository;
import com.example.CinemaService.repos.HallTypeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public abstract class HallMapper {
    @Autowired
    protected SeatMapper seatMapper;
    @Autowired
    protected CinemaRepository cinemaRepository;
    @Autowired
    protected HallTypeRepository hallTypeRepository;

    @Mapping(target = "cinemaId", expression = "java(hall.getCinema().getCinemaId())")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "setSeats")
    @Mapping(target = "hallType", expression = "java(hall.getHallType().getTitle())")
    public abstract HallResponseDTO toDto(Hall hall);

    @Mapping(target = "cinemaId", expression = "java(hall.getCinema().getCinemaId())")
    @Mapping(target = "hallType", expression = "java(hall.getHallType().getTitle())")
    public abstract HallWithoutSeatsResponseDTO toDtoWithoutSeats(Hall hall);

    @Mapping(target = "hallId", ignore = true)
    @Mapping(target = "cinema", source = "cinemaId", qualifiedByName = "setCinema")
    @Mapping(target = "hallType", source = "hallType", qualifiedByName = "setHallType")
    @Mapping(target = "seats", ignore = true)
    @Mapping(target = "showtimes", ignore = true)
    public abstract Hall toEntity(HallRequestDTO dto);

    @Mapping(target = "hallId", ignore = true)
    @Mapping(target = "hallType", source = "hallType", qualifiedByName = "setHallType")
    @Mapping(target = "cinema", ignore = true)
    @Mapping(target = "seats", ignore = true)
    @Mapping(target = "showtimes", ignore = true)
    public abstract void update(HallRequestDTO dto, @MappingTarget Hall hall);

    @Named("setCinema")
    public Cinema setCinema(long cinemaId){
        return cinemaRepository.findById(cinemaId).orElseThrow(() -> new RuntimeException("Кинотеатр с id " + cinemaId + "не найден"));
    }

    @Named("setSeats")
    public Set<SeatResponseDTO> setSeats(Set<Seat> seats){
        return seats.stream().map(seatMapper::toDto).collect(Collectors.toSet());
    }

    @Named("setHallType")
    public HallType setHallType(String hallType){
        return hallTypeRepository.findByTitle(hallType).orElseThrow(() -> new RuntimeException("Не найден тип зала " + hallType));
    }
}
