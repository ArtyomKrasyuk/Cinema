--liquibase formatted sql

--changeset artyomkrasyuk:1
ALTER TABLE orders ADD expires_at TIMESTAMP(6) WITHOUT TIME ZONE;