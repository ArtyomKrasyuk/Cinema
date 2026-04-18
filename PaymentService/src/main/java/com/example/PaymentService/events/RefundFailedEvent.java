package com.example.PaymentService.events;

public record RefundFailedEvent(
        long orderId
) {
}
