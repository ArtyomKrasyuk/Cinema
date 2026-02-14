--liquibase formatted sql

--changeset artyomkrasyuk:1
CREATE TABLE IF NOT EXISTS orders (
    order_id BIGSERIAL PRIMARY KEY,
    client_id UUID,
    showtime_id BIGINT,
    movie_title VARCHAR(255),
    cinema_title VARCHAR(255),
    hall_number INTEGER,
    time TIMESTAMP(6) WITHOUT TIME ZONE,
    price NUMERIC(10,2),
    state VARCHAR(255)
);

--changeset artyomkrasyuk:2
CREATE TABLE IF NOT EXISTS order_seat (
    order_seat_id BIGSERIAL PRIMARY KEY,
    seat_id BIGINT,
    seat_number INTEGER,
    order_id BIGINT REFERENCES orders(order_id)
);