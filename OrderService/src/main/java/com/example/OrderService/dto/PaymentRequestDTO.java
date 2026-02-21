package com.example.OrderService.dto;

public record PaymentRequestDTO(
        String cardNumber,
        String expiration,
        String code,
        long orderId
) {
}
