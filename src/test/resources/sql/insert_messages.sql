INSERT INTO users (user_id, first_name, last_name, password, gender, email)
VALUES (2, 'Bob', 'Brown', 'pass123', 'MALE', 'bob@example.com');

INSERT INTO users (user_id, first_name, last_name, password, gender, email)
VALUES (3, 'John', 'Smith', 'pass456', 'MALE', 'john@example.com');

INSERT INTO messages (message_id, sender_id, receiver_id, content, timestamp)
VALUES (1, 2, 3, 'Hello John! Reminder about homework.', '2025-05-01 09:00:00');

INSERT INTO messages (message_id, sender_id, receiver_id, content, timestamp)
VALUES (2, 3, 2, 'Hi, Bob. Thanks for the reminder!', '2025-05-01 11:00:00');

