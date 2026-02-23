package com.example.OrderService.dto;

public record OrderSeatRequestDTO(
        long seatId,
        int seatNumber,
        long showtimeId
) {
}
