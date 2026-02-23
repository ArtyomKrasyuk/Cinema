--liquibase formatted sql

--changeset artyomkrasyuk:1
CREATE TABLE IF NOT EXISTS orders (
    order_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    client_id UUID,
    showtime_id BIGINT,
    movie_title VARCHAR(255),
    cinema_title VARCHAR(255),
    hall_number INTEGER,
    time TIMESTAMP(6) WITHOUT TIME ZONE,
    price NUMERIC(10,2),
    state VARCHAR(255),
    expires_at TIMESTAMP(6) WITHOUT TIME ZONE
);

--changeset artyomkrasyuk:2
CREATE TABLE IF NOT EXISTS order_seat (
    order_seat_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    seat_id BIGINT,
    seat_number INTEGER,
    showtime_id BIGINT,
    state VARCHAR(255),
    order_id BIGINT REFERENCES orders(order_id)
);

--changeset artyomkrasyuk:3
CREATE UNIQUE INDEX unique_active_seat
    ON order_seat (seat_id, showtime_id)
    WHERE state IN ('CREATED', 'PROCESSING', 'CONFIRMED');