CREATE SCHEMA IF NOT EXISTS springdoc;

USE springdoc;

DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
     user_id char(36) NOT NULL PRIMARY KEY,
     first_name varchar(50) NOT NULL,
     last_name varchar(50) NOT NULL,
     language enum('DE', 'FR', 'IT', 'EN'),
     role enum('STANDARD', 'PREMIUM', 'EMPLOYEE'),
     active boolean);