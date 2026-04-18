package com.example.OrderService.events;

public record RefundFailedEvent(
        long orderId
) {
}
