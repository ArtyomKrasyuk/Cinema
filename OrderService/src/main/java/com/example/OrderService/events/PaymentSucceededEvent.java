package com.example.OrderService.events;

public record PaymentSucceededEvent(
        Long orderId
) {
}
