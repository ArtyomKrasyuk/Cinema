package com.example.OrderService.events;

public record PaymentFailedEvent(
        Long orderId
) {
}
