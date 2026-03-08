package com.example.OrderService.dto;

public record OrderSeatResponseDTO(
        long orderSeatId,
        long seatId,
        int seatNumber,
        int seatRow,
        long showtimeId,
        String state
) {
}
