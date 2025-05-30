create database aston;
use aston;

CREATE TABLE person
(
    id int auto_increment primary key,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    age int NOT NULL
);

CREATE TABLE car
(
    id int auto_increment primary key,
    brand VARCHAR(100) NOT NULL,
    colour VARCHAR(100) NOT NULL,
    personId int,
    FOREIGN KEY (personId) REFERENCES person (id) ON DELETE CASCADE
);

CREATE TABLE appartment
(
    id int auto_increment primary key,
    city VARCHAR(100) NOT NULL,
    roomAmount int NOT NULL,
    personId int,
    FOREIGN KEY (personId) REFERENCES person (id) ON DELETE CASCADE
);