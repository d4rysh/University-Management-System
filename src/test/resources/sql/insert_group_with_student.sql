INSERT INTO users (user_id, first_name, last_name, password, gender, email)
VALUES (2, 'Bob', 'Brown', 'pass123', 'Male', 'bob@example.com');

INSERT INTO groups (group_id, group_name)
VALUES (1, 'AA-95');

INSERT INTO students (student_id, group_id)
VALUES (2, 1);
