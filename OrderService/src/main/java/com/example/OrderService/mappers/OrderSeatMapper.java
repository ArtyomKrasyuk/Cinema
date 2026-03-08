package com.example.OrderService.mappers;

import com.example.OrderService.dto.OrderSeatRequestDTO;
import com.example.OrderService.dto.OrderSeatResponseDTO;
import com.example.OrderService.models.Order;
import com.example.OrderService.models.OrderSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderSeatMapper {

    @Mapping(target = "orderSeatId", ignore = true)
    @Mapping(target = "order", source = "order")
    @Mapping(target = "seatId", source = "dto.seatId")
    @Mapping(target = "showtimeId", source = "dto.showtimeId")
    @Mapping(target = "seatNumber", source = "dto.seatNumber")
    @Mapping(target = "seatRow", source = "dto.seatRow")
    @Mapping(target = "state", expression = "java(com.example.OrderService.models.OrderState.CREATED)")
    public abstract OrderSeat toEntity(OrderSeatRequestDTO dto, Order order);

    @Mapping(target = "state", expression = "java(orderSeat.getState().toString())")
    public abstract OrderSeatResponseDTO toDto(OrderSeat orderSeat);
}
