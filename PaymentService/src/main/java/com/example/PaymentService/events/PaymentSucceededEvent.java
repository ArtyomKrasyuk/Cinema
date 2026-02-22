package com.example.PaymentService.events;

public record PaymentSucceededEvent(
        Long orderId
) {
}
