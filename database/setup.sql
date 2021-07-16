DROP DATABASE IF EXISTS `tailoringstore`;

DROP USER IF EXISTS 'tailoringstore'@'localhost';
CREATE USER 'tailoringstore'@'localhost' IDENTIFIED BY 'password';

CREATE DATABASE `tailoringstore` CHARACTER SET UTF8mb4 COLLATE utf8mb4_bin;
GRANT ALL PRIVILEGES ON `tailoringstore`.* TO 'tailoringstore'@'localhost';

FLUSH PRIVILEGES;
USE `tailoringstore`;

CREATE TABLE `users` (
    username VARCHAR(32) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    firstName VARCHAR(32) NOT NULL,
    lastName VARCHAR(32) NOT NULL,
    dateOfBirth VARCHAR(10) NOT NULL,
    gender VARCHAR(8) NOT NULL,
    email VARCHAR(64) NOT NULL,
    contactNumber VARCHAR(16) NOT NULL,
    isTailor BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (username)
);
