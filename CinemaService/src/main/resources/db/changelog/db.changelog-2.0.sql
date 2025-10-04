--liquibase formatted sql

--changeset artyomkrasyuk:1
CREATE TABLE IF NOT EXISTS cinema (
  cinema_id bigint primary key generated always as identity,
  title varchar(255) unique
);

--changeset artyomkrasyuk:2
CREATE TABLE IF NOT EXISTS seat_type (
  seat_type_id bigint primary key generated always as identity,
  title varchar(255) unique,
  factor double precision
);

--changeset artyomkrasyuk:3
CREATE TABLE IF NOT EXISTS genre (
  genre_id bigint primary key generated always as identity,
  title varchar(255) unique
);

--changeset artyomkrasyuk:4
CREATE TABLE IF NOT EXISTS movie (
  movie_id bigint primary key generated always as identity,
  title varchar(255) unique,
  duration integer,
  poster varchar(2048),
  description text
);

--changeset artyomkrasyuk:5
CREATE TABLE IF NOT EXISTS movie_genre (
  movie_id bigint references movie(movie_id),
  genre_id bigint references genre(genre_id),
  primary key(movie_id, genre_id)
);

--changeset artyomkrasyuk:6
CREATE TABLE IF NOT EXISTS hall (
  hall_id bigint primary key generated always as identity,
  number integer,
  cinema_id bigint references cinema(cinema_id)
);

--changeset artyomkrasyuk:7
CREATE TABLE IF NOT EXISTS seat (
  seat_id bigint primary key generated always as identity,
  seat_type_id bigint references seat_type(seat_type_id),
  hall_id bigint references hall(hall_id)
);

--changeset artyomkrasyuk:8
CREATE TABLE IF NOT EXISTS showtime (
  showtime_id bigint primary key generated always as identity,
  hall_id bigint references hall(hall_id),
  movie_id bigint references movie(movie_id),
  time timestamp(6) without time zone,
  base_price integer
);