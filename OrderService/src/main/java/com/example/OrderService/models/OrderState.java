package com.example.OrderService.models;

public enum OrderState {
    CREATED,
    PROCESSING,
    CONFIRMED,
    CANCELED,
    EXPIRED,
    PAYMENT_FAILED
}
