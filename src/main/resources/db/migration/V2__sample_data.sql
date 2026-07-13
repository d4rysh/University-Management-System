INSERT INTO users (user_id, first_name, last_name, password, gender, date_of_birth, email)
VALUES (1, 'Bob', 'Brown', 'adminpass', 'MALE', '1996-01-01', 'admin@example.com'),
 (2, 'Mary', 'White', 'maryy0', 'FEMALE', '2006-02-08', 'maryw@example.com'),
 (3, 'Olga', 'Williams', 'olg23', 'FEMALE', '2005-04-21', 'olwil@example.com'),
 (4, 'Mark', 'Smith', '34m61', 'MALE', '1998-01-01', 'mark0s@example.com'),
 (5, 'Jesse', 'Pinkman', 'w4ssup', 'MALE', '1984-09-24', 'jess888@example.com'),
 (6, 'Katy', 'Perry', 'katycat6', 'FEMALE', '2005-06-14', 'katy54@example.com'),
 (7, 'Alex', 'White', 'akmb6', 'MALE', '1999-09-13', 'alexo0@example.com'),
 (8, 'George', 'Ash', '4ssh099', 'MALE', '2007-12-21', 'ashgeorge@example.com');

INSERT INTO groups (group_id, group_name)
VALUES (1, 'YH-98'),
 (2, 'BH-78');

INSERT INTO departments (department_id, department_name)
VALUES (1, 'Cybersecurity'),
 (2, 'Psychology');

INSERT INTO students (user_id, group_id)
VALUES (2, 1),
 (3, 1),
 (6, 2),
 (8, 2);


INSERT INTO teachers (user_id, department_id)
VALUES (4, 1),
 (7, 2),
 (5, 2);

INSERT INTO admins (user_id)
VALUES (1);

INSERT INTO courses (course_id, course_name, course_description, teacher_id)
VALUES (1, 'Introduction to Cyber Defense', 'Essential security concepts and principles.', 4),
(2, 'Network Security Fundamentals', 'Basics of securing networks, firewalls, and protocols.', 4),
(3, 'Linear Algebra', 'Vectors, matrices, linear transformations and their applications in computing.', 4),
(4, 'Mathematical Analysis', 'Limits, derivatives, integrals and fundamental concepts of calculus.', 4),
(5, 'Philosophy', 'Basic philosophical concepts and critical thinking.', 5),
--psychology
(6, 'Introduction to Psychology', 'Basics of human behavior and mental processes.', 5),
(7, 'Cognitive Psychology', 'Memory, perception and thinking processes.', 7),
(8, 'Social Psychology', 'Behavior of people in social groups.', 5);

INSERT INTO courses_departments (department_id, course_id)
VALUES (1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 3),
(2, 5);

INSERT INTO messages (message_id, sender_id, receiver_id, content, timestamp)
VALUES (1, 4, 6, 'Hi, Katy! I''m writing to remind you about Linear Algebra practical assignment that was due last week. I''m still waiting for your submission.', '2026-03-12 09:00:00'),
(2, 6, 4, 'Hi, Mr. Mark! Thanks for the reminder. I will complete the task in a few days.', '2026-03-12 16:34:20'),
(3, 2, 3, 'How are you doing? Have you already seen our new Philosophy task? It is really difficult.', '2026-05-09 16:34:20'),
(4, 3, 2, 'Mary, i am doing well, wbu? Yeah, i saw it. I think it is quite challenging, but I will try to finish it soon.', '2026-05-10 12:54:00');

INSERT INTO classrooms (classroom_id, room_number)
VALUES (1, 14),
(2, 26),
(3, 12),
(4, 19);

INSERT INTO timeslots (slot_name, start_time, end_time)
VALUES ('First lesson', '08:30:00', '09:50:00'),
('Second lesson', '10:00:00', '11:20:00'),
('Third lesson', '11:30:00', '12:50:00'),
('Fourth lesson', '13:00:00', '14:20:00'),
('Fifth lesson', '14:30:00', '15:50:00'),
('Sixth lesson', '16:00:00', '17:20:00'),
('Seventh lesson', '17:30:00', '18:50:00');

INSERT INTO lessons (lesson_id, date, course_id, teacher_id, classroom_id, slot_name, group_id)
VALUES (1, '2026-04-01', 8, 5, 2, 'Fourth lesson', 2),
(2, '2026-04-02', 4, 4, 3, 'Second lesson', 1),
(3, '2026-04-02', 3, 4, 3, 'First lesson', 2),
(4, '2026-04-26', 5, 5, 4, 'Sixth lesson', 2);

INSERT INTO grade_records (grade_id, student_id, course_id, grade, teacher_id, exam_date)
VALUES (1, 3, 7, 88.0, 7, '2026-05-27'),
(2, 2, 4, 98.0, 4, '2026-05-29');