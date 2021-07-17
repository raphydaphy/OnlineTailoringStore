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
    PRIMARY KEY (subcategoryId)
) ENGINE=InnoDB;

CREATE TABLE `dressTypes` (
    `dressTypeId` INT NOT NULL AUTO_INCREMENT,
    `dressTypeName` VARCHAR(64) NOT NULL,
    `subcategoryId` INT NOT NULL,
    PRIMARY KEY(dressTypeId),
    FOREIGN KEY(subcategoryId) REFERENCES subcategories(subcategoryId) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `tailorDressTypes` (
    `dressTypeId` INT NOT NULL AUTO_INCREMENT,
    `tailorUsername` VARCHAR(32) NOT NULL,
    `enabled` BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY(dressTypeId, tailorUsername),
    FOREIGN KEY (tailorUsername) REFERENCES users(username) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `securityQuestionPrompts` (
    `promptId` INT NOT NULL AUTO_INCREMENT,
    `question` VARCHAR(64) NOT NULL,
    PRIMARY KEY (promptId)
) ENGINE=InnoDB;

INSERT INTO `securityQuestionPrompts` (question) VALUES
("In what city where you born?"),
("What is the name of your favorite pet?"),
("What is your mother's maiden name?"),
("What high school did you attend?"),
("What was the make of your first car?"),
("What was your favorite food as a child?"),
("Where did you meet your spouse?"),
("How old were you when you had your first kiss?"),
("What was your childhood nickname?"),
("What street did you live on in third grade?"),
("What was the name of the company of your first job?"),
("What is the name of a college you applied to but didn't attend?"),
("In what city or town was your first job?"),
("In what city does your nearest sibling live?"),
("What was the name of your favorite stuffed animal?");

CREATE TABLE `securityQuestions` (
    `username` VARCHAR(32) NOT NULL,
    `promptId` INT NOT NULL,
    `answer` VARCHAR(64) NOT NULL,
    PRIMARY KEY(username, promptId),
    FOREIGN KEY(username) REFERENCES users(username),
    FOREIGN KEY(promptID) REFERENCES securityQuestionPrompts(promptId)
) ENGINE=InnoDB;