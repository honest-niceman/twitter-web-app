INSERT INTO "user" (id, first_name, last_name, username, password, bio, role)
VALUES (1, 'John', 'Doe', 'username', 'password', 'bio', 'USER');

INSERT INTO post (id, date, text, user_id)
VALUES (2, NOW(), 'Post1', 1);

INSERT INTO post (id, date, text, user_id)
VALUES (3, NOW(), 'Post2', 1);

INSERT INTO post (id, date, text, user_id)
VALUES (4, NOW(), 'Post3', 1);
