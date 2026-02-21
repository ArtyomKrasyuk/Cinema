package com.example.OrderService.mappers;

import com.example.OrderService.dto.OrderRequestDTO;
import com.example.OrderService.dto.OrderResponseDTO;
import com.example.OrderService.dto.OrderSeatResponseDTO;
import com.example.OrderService.models.Order;
import com.example.OrderService.models.OrderSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Autowired
    protected OrderSeatMapper orderSeatMapper;

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "time", source = "time", qualifiedByName = "setTime")
    @Mapping(target = "state", expression = "java(com.example.OrderService.models.OrderState.CREATED)")
    @Mapping(target = "seats", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    public abstract Order toEntity(OrderRequestDTO dto);

    @Mapping(target = "time", expression = "java(order.getTime().toString())")
    @Mapping(target = "state", expression = "java(order.getState().toString())")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "setSeats")
    public abstract OrderResponseDTO toDto(Order order);

    @Named("setTime")
    public Timestamp setTime(String time){
        return Timestamp.valueOf(time);
    }

    @Named("setSeats")
    public List<OrderSeatResponseDTO> setSeats(Set<OrderSeat> seats){
        return seats.stream().map(orderSeatMapper::toDto).toList();
    }
}
