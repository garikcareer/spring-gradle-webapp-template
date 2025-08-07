CREATE SCHEMA IF NOT EXISTS `company`;

CREATE TABLE company
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(40) UNIQUE NOT NULL,
    location VARCHAR(100)       NOT NULL
);

CREATE INDEX idx_name ON company (name);

INSERT INTO company (name, location)
VALUES ('Company1', 'New York'),
       ('Company2', 'Los Angeles'),
       ('Company3', 'Chicago'),
       ('Company4', 'San Diego'),
       ('Company5', 'Seattle'),
       ('Company6', 'Houston'),
       ('Company7', 'San Francisco'),
       ('Company8', 'Dallas'),
       ('Company9', 'Boston'),
       ('Company10', 'Miami');