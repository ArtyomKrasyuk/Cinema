package com.example.CinemaService.mappers;

import com.example.CinemaService.dto.CinemaRequestDTO;
import com.example.CinemaService.dto.CinemaResponseDTO;
import com.example.CinemaService.dto.HallWithoutSeatsResponseDTO;
import com.example.CinemaService.models.Cinema;
import com.example.CinemaService.models.Hall;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CinemaMapper {
    protected HallMapper hallMapper;

    public CinemaMapper(HallMapper hallMapper){
        this.hallMapper = hallMapper;
    }

    @Mapping(target = "cinemaId", ignore = true)
    @Mapping(target = "halls", ignore = true)
    public abstract Cinema toEntity(CinemaRequestDTO dto);

    @Mapping(target = "halls", source = "halls", qualifiedByName = "setHalls")
    public abstract CinemaResponseDTO toDto(Cinema cinema);

    @Mapping(target = "cinemaId", ignore = true)
    @Mapping(target = "halls", ignore = true)
    public abstract void update(CinemaRequestDTO dto, @MappingTarget Cinema cinema);

    @Named("setHalls")
    public Set<HallWithoutSeatsResponseDTO> setHalls(Set<Hall> halls){
        return halls.stream().map(hallMapper::toDtoWithoutSeats).collect(Collectors.toSet());
    }
}
