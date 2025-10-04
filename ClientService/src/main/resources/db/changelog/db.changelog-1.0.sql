--liquibase formatted sql

--changeset artyomkrasyuk:1
CREATE TABLE IF NOT EXISTS client (
  client_id bigint primary key generated always as identity,
  username varchar(255),
  login VARCHAR(255) unique,
  password text,
  role VARCHAR(255)
);