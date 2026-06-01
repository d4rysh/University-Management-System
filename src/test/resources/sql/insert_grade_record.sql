INSERT INTO users (user_id, first_name, last_name, password, gender, email)
VALUES (2, 'Bob', 'Brown', 'pass123', 'MALE', 'bob@example.com');

INSERT INTO students (user_id, group_id)
VALUES (2, NULL);

INSERT INTO users (user_id, first_name, last_name, password, gender, email)
VALUES (3, 'John', 'Smith', 'pass456', 'MALE', 'john@example.com');

INSERT INTO departments (department_id, department_name)
VALUES (1, 'Mathematics Department');

INSERT INTO teachers (user_id, department_id)
VALUES (3, 1);

INSERT INTO courses (course_id, course_name, course_description, teacher_id)
VALUES (1, 'Math', 'Basic mathematics', 3);

INSERT INTO grade_records (grade_id, student_id, course_id, grade, teacher_id, exam_date)
VALUES (1, 2, 1, 95.0, 3, '2025-05-01'),
       (2, 2, 1, 88.0, 3, '2025-06-01');
