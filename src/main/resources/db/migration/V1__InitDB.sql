create sequence hibernate_sequence start 1 increment 1;

create table cabinets (
    number int4 not null,
    department varchar(255),
    floor int4 not null,
    primary key (number)
);

create table computers (
    name varchar(255) not null,
    cabinet int4 not null,
    ip_address varchar(255),
    login varchar(255),
    password varchar(255),
    processor varchar(255),
    ram varchar(255),
    primary key (name)
);

create table printers (
    id int8 not null,
    cabinet int4 not null,
    cartridge varchar(255),
    connection_type varchar(255),
    ip_address varchar(255),
    name varchar(255),
    primary key (id)
);

create table users (
    id int8 not null,
    cabinet int4 not null,
    department varchar(255),
    name varchar(255),
    pc_name varchar(255),
    position varchar(255),
    primary key (id)
);

