DROP DATABASE IF EXISTS `tailoringstore`;

DROP USER IF EXISTS 'tailoringstore'@'localhost';
CREATE USER 'tailoringstore'@'localhost' IDENTIFIED BY 'password';

CREATE DATABASE `tailoringstore` CHARACTER SET UTF8mb4 COLLATE utf8mb4_bin;
GRANT ALL PRIVILEGES ON `tailoringstore`.* TO 'tailoringstore'@'localhost';

FLUSH PRIVILEGES;
USE `tailoringstore`;

CREATE TABLE SPRING_SESSION (
    SESSION_ID CHAR(36),
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (SESSION_ID)
);

CREATE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (LAST_ACCESS_TIME);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
    SESSION_ID CHAR(36),
    ATTRIBUTE_NAME VARCHAR(200),
    ATTRIBUTE_BYTES BLOB NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_ID) REFERENCES SPRING_SESSION(SESSION_ID) ON DELETE CASCADE
);

CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (SESSION_ID);

CREATE TABLE `users` (
    username VARCHAR(32) NOT NULL,
    passwordHash VARCHAR(255) NOT NULL,
    firstName VARCHAR(32) NOT NULL,
    lastName VARCHAR(32) NOT NULL,
    dateOfBirth VARCHAR(10) NOT NULL,
    gender VARCHAR(8) NOT NULL,
    email VARCHAR(64) NOT NULL,
    contactNumber VARCHAR(16) NOT NULL,
    category ENUM("customer", "tailor", "admin") NOT NULL DEFAULT "customer",
    PRIMARY KEY (username)
) ENGINE=InnoDB;

CREATE TABLE `tickets` (
    `ticketId` INT NOT NULL AUTO_INCREMENT,
    `issue` VARCHAR(64) NOT NULL,
    `description` TEXT NOT NULL,
    `ticketDate` DATE NOT NULL DEFAULT CURRENT_DATE,
    `username` VARCHAR(32) NOT NULL,
    `response` TEXT,
    `responseUsername` VARCHAR(32),
    `closed` BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (ticketId),
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (responseUsername) REFERENCES users(username)
) ENGINE=InnoDB;

CREATE TABLE `categories` (
    `categoryId` INT NOT NULL AUTO_INCREMENT,
    `categoryName` VARCHAR(64) NOT NULL,
    PRIMARY KEY (categoryId)
) ENGINE=InnoDB;

CREATE TABLE `subcategories` (
    `subcategoryId` INT NOT NULL AUTO_INCREMENT,
    `subcategoryName`VARCHAR(64) NOT NULL,
    `categoryId` INT NOT NULL,
    PRIMARY KEY (subcategoryId),
    FOREIGN KEY (categoryId) REFERENCES categories(categoryId)
) ENGINE=InnoDB;