INSERT INTO users (login, password)
VALUES ('Zoomer', '12345');
INSERT INTO users (login, password)
VALUES ('Boomer', 'qwerty');
INSERT INTO users (login, password)
VALUES ('Doomer', 'password');
INSERT INTO users (login, password)
VALUES ('Groomer', 'asdfg');
INSERT INTO users (login, password)
VALUES ('Foomer', 'zxcvbn');

INSERT INTO chatrooms (name, owner_id)
VALUES ('General', 1);
INSERT INTO chatrooms (name, owner_id)
VALUES ('ft_memes', 2);
INSERT INTO chatrooms (name, owner_id)
VALUES ('Java', 3);
INSERT INTO chatrooms (name, owner_id)
VALUES ('Piscine', 4);
INSERT INTO chatrooms (name, owner_id)
VALUES ('Help', 5);

INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (1 , 1);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (1 , 2);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (1 , 3);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (1 , 4);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (1 , 5);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (2 , 1);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (2 , 2);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (2 , 3);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (2 , 4);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (2 , 5);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (3 , 1);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (3 , 2);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (3 , 3);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (3 , 4);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (3 , 5);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (4 , 1);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (4 , 2);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (4 , 3);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (4 , 4);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (4 , 5);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (5 , 1);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (5 , 2);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (5 , 3);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (5 , 4);
INSERT INTO users_chatrooms (user_id, chatroom_id) VALUES (5 , 5);

INSERT INTO messages (author, room, text, date_time)
VALUES (1, 5, 'How cook cookies?', '2022-04-25 20:00:01');
INSERT INTO messages (author, room, text, date_time)
VALUES (2, 4, 'Day05 is hard', '2022-04-01 08:00:01');
INSERT INTO messages (author, room, text, date_time)
VALUES (3, 3, 'Java is greater than C++', '2022-01-01 10:30:02');
INSERT INTO messages (author, room, text, date_time)
VALUES (4, 2, 'lol kek cheburek', '1970-01-01 15:45:04');
INSERT INTO messages (author, room, text, date_time)
VALUES (5, 1, 'Hellow World!', '1970-01-01 00:00:05');