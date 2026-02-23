package com.example.OrderService.dto;

public record OrderSeatResponseDTO(
        long orderSeatId,
        long seatId,
        int seatNumber,
        long showtimeId,
        String state
) {
}
