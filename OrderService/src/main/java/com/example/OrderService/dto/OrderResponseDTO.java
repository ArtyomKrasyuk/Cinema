package com.example.OrderService.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(
        long orderId,
        UUID clientId,
        long showtimeId,
        String movieTitle,
        String cinemaTitle,
        int hallNumber,
        String time,
        BigDecimal price,
        String state,
        List<OrderSeatResponseDTO> seats
) {
}
