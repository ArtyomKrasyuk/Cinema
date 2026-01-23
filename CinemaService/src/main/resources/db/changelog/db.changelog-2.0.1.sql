--liquibase formatted sql

--changeset artyomkrasyuk:1
ALTER TABLE cinema ADD address varchar(255);
ALTER TABLE seat ADD row integer;
ALTER TABLE seat ADD number integer;
