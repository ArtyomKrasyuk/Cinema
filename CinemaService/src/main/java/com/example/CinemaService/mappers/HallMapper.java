package com.example.CinemaService.mappers;

import com.example.CinemaService.dto.HallRequestDTO;
import com.example.CinemaService.dto.HallResponseDTO;
import com.example.CinemaService.dto.HallWithoutSeatsResponseDTO;
import com.example.CinemaService.dto.SeatResponseDTO;
import com.example.CinemaService.models.Cinema;
import com.example.CinemaService.models.Hall;
import com.example.CinemaService.models.Seat;
import com.example.CinemaService.repos.CinemaRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public abstract class HallMapper {
    protected SeatMapper seatMapper;
    protected CinemaRepository cinemaRepository;

    public HallMapper(SeatMapper seatMapper, CinemaRepository cinemaRepository){
        this.seatMapper = seatMapper;
        this.cinemaRepository = cinemaRepository;
    }

    @Mapping(target = "cinemaId", expression = "java(hall.getCinema().getCinemaId())")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "setSeats")
    public abstract HallResponseDTO toDto(Hall hall);

    @Mapping(target = "cinemaId", expression = "java(hall.getCinema().getCinemaId())")
    public abstract HallWithoutSeatsResponseDTO toDtoWithoutSeats(Hall hall);

    @Mapping(target = "hallId", ignore = true)
    @Mapping(target = "cinema", source = "cinemaId", qualifiedByName = "setCinema")
    @Mapping(target = "seats", ignore = true)
    @Mapping(target = "showtimes", ignore = true)
    public abstract Hall toEntity(HallRequestDTO dto);

    @Mapping(target = "hallId", ignore = true)
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
}
