--liquibase formatted sql

--changeset artyomkrasyuk:1
CREATE TABLE IF NOT EXISTS payment (
    payment_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    order_id BIGINT,
    card_number VARCHAR(255),
    price NUMERIC(10,2),
    payment_status VARCHAR(255)
);