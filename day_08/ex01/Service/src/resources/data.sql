DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    email    VARCHAR NOT NULL
    );


INSERT INTO users (email)
VALUES ('1@mail.ru');
INSERT INTO users (email)
VALUES ('2@mail.ru');
INSERT INTO users (email)
VALUES ('3@mail.ru');
INSERT INTO users (email)
VALUES ('4@mail.ru');
INSERT INTO users (email)
VALUES ('5@mail.ru');