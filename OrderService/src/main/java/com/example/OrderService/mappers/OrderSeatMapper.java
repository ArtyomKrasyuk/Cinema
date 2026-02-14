package com.example.OrderService.mappers;

import com.example.OrderService.dto.OrderSeatRequestDTO;
import com.example.OrderService.dto.OrderSeatResponseDTO;
import com.example.OrderService.models.OrderSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderSeatMapper {

    @Mapping(target = "orderSeatId", ignore = true)
    @Mapping(target = "order", ignore = true)
    public abstract OrderSeat toEntity(OrderSeatRequestDTO dto);

    public abstract OrderSeatResponseDTO toDto(OrderSeat orderSeat);
}
