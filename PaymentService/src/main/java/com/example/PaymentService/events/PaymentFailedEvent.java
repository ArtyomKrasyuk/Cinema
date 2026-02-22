package com.example.PaymentService.events;

public record PaymentFailedEvent(
        Long orderId
) {
}
