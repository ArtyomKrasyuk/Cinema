package com.example.OrderService.dto;

import java.util.List;

public record ReservedSeatsResponseDTO(
        List<Long> seatIds
) {
}
