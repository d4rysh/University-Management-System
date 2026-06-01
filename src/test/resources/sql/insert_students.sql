INSERT INTO groups (group_id, group_name)
VALUES (200, 'AA-95');

INSERT INTO users (user_id, first_name, last_name, password, gender, date_of_birth, email)
VALUES (20, 'Alice', 'Green', 'pass123', 'FEMALE', '2000-05-01', 'student1@example.com');

INSERT INTO users (user_id, first_name, last_name, password, gender, date_of_birth, email)
VALUES (21, 'Bob', 'Brown', 'pass456', 'MALE', '2001-06-15', 'student2@example.com');

INSERT INTO students (user_id, group_id)
VALUES (20, 200);

INSERT INTO students (user_id, group_id)
VALUES (21, 200);

INSERT INTO courses (course_id, course_name, course_description, teacher_id)
VALUES (300, 'Math', 'Basic Mathematics', NULL);

INSERT INTO lessons (lesson_id, date, course_id, teacher_id, classroom_id, group_id)
VALUES (400, '2025-05-01', 300, NULL, NULL, 200);
