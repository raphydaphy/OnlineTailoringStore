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
    dateOfBirth DATE NOT NULL,
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
    FOREIGN KEY (responseUsername) REFERENCES users(username) ON DELETE SET NULL
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
    FOREIGN KEY(username) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY(promptID) REFERENCES securityQuestionPrompts(promptId) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `tailorShops` (
    `tailorUsername` VARCHAR(32) NOT NULL,
    `shopName` VARCHAR(64) NOT NULL,
    `shopAddress` VARCHAR(64) NOT NULL,
    `shopContactNumber` VARCHAR(16) NOT NULL,
    `shopWorkingHours` VARCHAR(32) NOT NULL,
    `availableServices` VARCHAR(64) NOT NULL,
    `courierAvailable` BOOLEAN NOT NULL,
    PRIMARY KEY (tailorUsername),
    FOREIGN KEY (tailorUsername) REFERENCES users(username) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `patterns` (
    `patternId` INT NOT NULL AUTO_INCREMENT,
    `tailorUsername` VARCHAR(32) NOT NULL,
    `categoryId` INT NOT NULL,
    `dressTypeId` INT NOT NULL,
    `name` VARCHAR(64) NOT NULL,
    `description` TEXT NOT NULL,
    `cost` FLOAT(8, 2) NOT NULL,
    `imageData` MEDIUMBLOB NOT NULL,
    PRIMARY KEY (patternId),
    FOREIGN KEY (tailorUsername) REFERENCES users(username),
    FOREIGN KEY (categoryId) REFERENCES categories(categoryId) ON DELETE CASCADE,
    FOREIGN KEY (dressTypeId) REFERENCES dressTypes(dressTypeId) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `orderStatus` (
    `orderStatusId` INT NOT NULL,
    `orderStatus` VARCHAR(32) NOT NULL,
    PRIMARY KEY (orderStatusId)
) ENGINE=InnoDB;

INSERT INTO `orderStatus` (orderStatusId, orderStatus) VALUES
(1, 'Order Placed'), (2, 'Order Accepted'), (3, 'Order Complete');

CREATE TABLE `orders` (
    `orderId` INT NOT NULL AUTO_INCREMENT,
    `customerUsername` VARCHAR(32) NOT NULL,
    `tailorUsername` VARCHAR(32) NOT NULL,
    `patternId` INT NOT NULL,
    `placedDate` DATE NOT NULL DEFAULT CURRENT_DATE,
    `expectedDeliveryDate` DATE NOT NULL,
    `deliveredDate` DATE,
    `orderStatusId` INT NOT NULL,
    `amount` FLOAT(8, 2),
    `courier` BOOLEAN NOT NULL DEFAULT FALSE,
    `orderNotes` TEXT NOT NULL,
    `topFabric` VARCHAR(64) NOT NULL,
    `topMaterial` VARCHAR(64) NOT NULL,
    `topDuration` VARCHAR(64) NOT NULL,
    `topLength` VARCHAR(64) NOT NULL,
    `topQuantity` VARCHAR(64) NOT NULL,
    `neck` VARCHAR(64) NOT NULL,
    `waist` VARCHAR(64) NOT NULL,
    `chest` VARCHAR(64) NOT NULL,
    `shoulderLength` VARCHAR(64) NOT NULL,
    `bottomFabric` VARCHAR(64) NOT NULL,
    `bottomMaterial` VARCHAR(64) NOT NULL,
    `bottomDuration` VARCHAR(64) NOT NULL,
    `bottomLength` VARCHAR(64) NOT NULL,
    `bottomQuantity` VARCHAR(64) NOT NULL,
    `hip` VARCHAR(64) NOT NULL,
    `kneeLength` VARCHAR(64) NOT NULL,
    PRIMARY KEY (orderId),
    FOREIGN KEY (customerUsername) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (tailorUsername) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (patternId) REFERENCES patterns(patternId) ON DELETE CASCADE,
    FOREIGN KEY (orderStatusID) REFERENCES orderStatus(orderStatusId) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `payments` (
    `orderId` INT NOT NULL,
    `cardName` VARCHAR(64) NOT NULL,
    `cardNumber` VARCHAR(64) NOT NULL,
    `cardExpiry` VARCHAR(10) NOT NULL,
    `cardCvv` VARCHAR(5) NOT NULL,
    `paid` BOOLEAN NOT NULL,
    PRIMARY KEY (orderId),
    FOREIGN KEY (orderId) REFERENCES orders(orderId) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `reviews` (
    `orderId` INT NOT NULL,
    `stars` TINYINT NOT NULL,
    `review` TEXT NOT NULL,
    PRIMARY KEY (orderId),
    FOREIGN KEY (orderId) REFERENCES orders(orderId) ON DELETE CASCADE
) ENGINE=InnoDB;