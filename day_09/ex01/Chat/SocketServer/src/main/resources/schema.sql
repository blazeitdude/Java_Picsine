DROP DATABASE IF EXISTS chatroom WITH (FORCE);
CREATE DATABASE chatroom;

CREATE TABLE IF NOT EXISTS chatroom_user (
    user_id BIGINT NOT NULL,
    chatroom_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    user_login VARCHAR(60) NOT NULL UNIQUE,
    user_pass VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS chatrooms (
    chatroom_id BIGSERIAL PRIMARY KEY,
    chatroom_name VARCHAR(200) NOT NULL UNIQUE,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS messages (
    message_id BIGSERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    chatroom_id BIGINT,
    message TEXT NOT NULL,
    message_date TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users (user_id),
    FOREIGN KEY (chatroom_id) REFERENCES chatrooms (chatroom_id)
);

ALTER TABLE chatroom_user ADD FOREIGN KEY (chatroom_id) REFERENCES chatrooms (chatroom_id);
ALTER TABLE chatroom_user ADD FOREIGN KEY (user_id) REFERENCES users (user_id);