INSERT INTO users (user_id, first_name, last_name, password, gender, email)
VALUES (4, 'Alice', 'White', 'example3', 'Female', 'alice3@example.com');

INSERT INTO departments (department_id, department_name)
VALUES (6, 'History Department');

INSERT INTO teachers (user_id, department_id)
VALUES (4, 6);

INSERT INTO courses (course_id, course_name, course_description, teacher_id)
VALUES (1, 'Math', 'Basic mathematics', 4);