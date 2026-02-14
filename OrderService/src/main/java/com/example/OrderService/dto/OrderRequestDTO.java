package com.example.OrderService.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDTO(
       long showtimeId,
       String movieTitle,
       String cinemaTitle,
       int hallNumber,
       String time,
       BigDecimal price,
       List<OrderSeatRequestDTO> seats
) {
}
