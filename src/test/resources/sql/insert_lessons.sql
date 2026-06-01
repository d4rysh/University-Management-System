INSERT INTO users (user_id, first_name, last_name, password, gender, email)
VALUES (2, 'Bob', 'Brown', 'pass123', 'MALE', 'bob@example.com');

INSERT INTO groups (group_id, group_name)
VALUES (1, 'AA-95');

INSERT INTO students (user_id, group_id)
VALUES (2, 1);

INSERT INTO users (user_id, first_name, last_name, password, gender, email)
VALUES (3, 'John', 'Smith', 'pass456', 'MALE', 'john@example.com');

INSERT INTO departments (department_id, department_name)
VALUES (1, 'Mathemathics Department');

INSERT INTO teachers (user_id, department_id)
VALUES (3, 1);

INSERT INTO courses (course_id, course_name, course_description, teacher_id)
VALUES (1, 'Math', 'Basic mathematics', 3);

INSERT INTO classrooms (classroom_id, room_number)
VALUES (1, 101);

INSERT INTO timeslots (slot_name, start_time, end_time)
VALUES ('First lesson', '09:00', '10:00');

INSERT INTO lessons (lesson_id, date, course_id, teacher_id, classroom_id, group_id)
VALUES (1, '2025-05-01', 1, 3, 1, 1);