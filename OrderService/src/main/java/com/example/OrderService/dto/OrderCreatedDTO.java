package com.example.OrderService.dto;

import java.math.BigDecimal;

public record OrderCreatedDTO(
        Long orderId,
        String expiresAt,
        BigDecimal price
) {
}
