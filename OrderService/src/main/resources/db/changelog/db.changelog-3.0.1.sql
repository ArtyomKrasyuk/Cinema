--liquibase formatted sql

--changeset artyomkrasyuk:1
ALTER TABLE order_seat ADD seat_row INTEGER;