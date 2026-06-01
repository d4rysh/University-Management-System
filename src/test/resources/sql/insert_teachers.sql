INSERT INTO departments (department_id, department_name)
VALUES (100, 'Mathematics');

INSERT INTO departments (department_id, department_name)
VALUES (101, 'Physics');

INSERT INTO users (user_id, first_name, last_name, password, gender, date_of_birth, email)
VALUES (10, 'Alice', 'Green', 'pass123', 'FEMALE', '1980-05-01', 'teacher1@example.com');

INSERT INTO users (user_id, first_name, last_name, password, gender, date_of_birth, email)
VALUES (11, 'Bob', 'Brown', 'pass456', 'MALE', '1985-06-15', 'teacher2@example.com');

INSERT INTO teachers (user_id, department_id)
VALUES (10, 100);

INSERT INTO teachers (user_id, department_id)
VALUES (11, 100);

INSERT INTO groups (group_id, group_name)
VALUES (200, 'AA-95');

INSERT INTO courses (course_id, course_name, course_description, teacher_id)
VALUES (300, 'Math', 'Basic Mathematics', 10);

INSERT INTO courses (course_id, course_name, course_description, teacher_id)
VALUES (301, 'Physics', 'Intro to Physics', 11);

INSERT INTO lessons (lesson_id, date, course_id, teacher_id, classroom_id, group_id)
VALUES (400, '2025-05-01', 301, 11, NULL, 200);
