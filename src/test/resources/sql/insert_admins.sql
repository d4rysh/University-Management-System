INSERT INTO users (user_id, first_name, last_name, password, gender, date_of_birth, email)
VALUES (10, 'Bob', 'Brown', 'adminpass', 'MALE', '1980-01-01', 'admin@example.com');

INSERT INTO users (user_id, first_name, last_name, password, gender, date_of_birth, email)
VALUES (11, 'Alice', 'Green', 'adminpass2', 'FEMALE', '1985-02-02', 'alice.admin@example.com');

INSERT INTO admins (user_id)
VALUES (10);

INSERT INTO admins (user_id)
VALUES (11);
