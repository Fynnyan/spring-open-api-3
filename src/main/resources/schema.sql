CREATE SCHEMA IF NOT EXISTS springdoc;

USE springdoc;

DROP TABLE IF EXISTS INVOICES;
DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
     user_id char(36) NOT NULL PRIMARY KEY,
     first_name varchar(50) NOT NULL,
     last_name varchar(50) NOT NULL,
     language enum('DE', 'FR', 'IT', 'EN'),
     role enum('STANDARD', 'PREMIUM', 'EMPLOYEE'),
     active boolean);

CREATE TABLE INVOICES (
    invoice_id CHAR(36) NOT NULL PRIMARY KEY,
    category ENUM('STANDARD', 'SUBSCRIPTION') NOT NULL DEFAULT 'STANDARD',
    user_id CHAR(36) NOT NULL,
    title VARCHAR(100) NOT NULL DEFAULT '',
    price DOUBLE NOT NULL DEFAULT 0,
    payed BOOLEAN NOT NULL ,
    dueDate TIMESTAMP NOT NULL,
    creationDate TIMESTAMP NOT NULL,
    month INT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS(user_id) ON DELETE NO ACTION
);
