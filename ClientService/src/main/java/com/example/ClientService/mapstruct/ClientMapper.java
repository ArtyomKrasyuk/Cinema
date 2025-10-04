package com.example.ClientService.mapstruct;

import com.example.ClientService.dto.ClientRequestDTO;
import com.example.ClientService.dto.ClientResponseDTO;
import com.example.ClientService.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "role", expression = "java(com.example.ClientService.enums.Role.ROLE_CLIENT)")
    Client toEntity(ClientRequestDTO dto);
    ClientResponseDTO toDTO(Client client);
}
