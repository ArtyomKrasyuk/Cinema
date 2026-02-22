package com.example.PaymentService.events;

import java.math.BigDecimal;

public record ProcessPaymentEvent(
        String cardNumber,
        String expiration,
        String code,
        long orderId,
        BigDecimal price
) {
}
