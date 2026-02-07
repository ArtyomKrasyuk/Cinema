package com.example.CinemaService.mappers;

import com.example.CinemaService.dto.HallTypeRequestDTO;
import com.example.CinemaService.dto.HallTypeResponseDTO;
import com.example.CinemaService.models.HallType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class HallTypeMapper {

    @Mapping(target = "hallTypeId", ignore = true)
    @Mapping(target = "halls", ignore = true)
    public abstract HallType toEntity(HallTypeRequestDTO dto);

    public abstract HallTypeResponseDTO toDto(HallType hall);

    @Mapping(target = "hallTypeId", ignore = true)
    @Mapping(target = "halls", ignore = true)
    public abstract void update(HallTypeRequestDTO dto, @MappingTarget HallType hallType);
}
