-- liquibase formatted sql

--changeset artyomkrasyuk:1
CREATE TABLE IF NOT EXISTS hall_type (
    hall_type_id bigint primary key generated always as identity,
    title varchar(255) unique,
    factor double precision
);

--changeset artyomkrasyuk:2
ALTER TABLE hall ADD hall_type_id bigint REFERENCES hall_type(hall_type_id);

--changeset artyomkrasyuk:3
INSERT INTO hall_type(title, factor) VALUES('2D', 1);
INSERT INTO hall_type(title, factor) VALUES('3D', 1.2);
INSERT INTO hall_type(title, factor) VALUES('IMAX', 1.5);