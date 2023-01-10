-- liquibase formatted sql
-- changeset author:author id:001-store-and-worker

create table worker
(
    id     serial  not null primary key,
    name   text    not null,
    driver boolean not null default false
);

create table store
(
    id      serial       not null primary key,
    name    varchar(300) not null unique,
    address text         null
);
