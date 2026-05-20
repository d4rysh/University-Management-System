CREATE TYPE gender as ENUM ('Male', 'Female', 'Other');

CREATE TABLE users (
user_id SERIAL PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
password VARCHAR(70) NOT NULL,
gender gender NOT NULL,
date_of_birth DATE,
email VARCHAR(500) UNIQUE NOT NULL
);

CREATE TABLE groups (
group_id SERIAL PRIMARY KEY,
group_name VARCHAR(20) NOT NULL
);

CREATE TABLE departments (
department_id SERIAL PRIMARY KEY,
department_name VARCHAR(50) NOT NULL
);

CREATE TABLE students (
student_id INT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
group_id INT REFERENCES groups(group_id) ON DELETE SET NULL
);

CREATE TABLE teachers (
teacher_id INT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
department_id INT REFERENCES departments(department_id) ON DELETE SET NULL
);

CREATE TABLE courses (
course_id SERIAL PRIMARY KEY,
course_name VARCHAR(50) NOT NULL,
course_description VARCHAR(250) NOT NULL,
teacher_id INT REFERENCES teachers(teacher_id)
);

CREATE TABLE courses_departments (
department_id INT,
course_id INT,
PRIMARY KEY (department_id, course_id),
FOREIGN KEY (department_id) REFERENCES departments(department_id) ON DELETE CASCADE,
FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

CREATE TABLE admins (
admin_id INT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE messages (
message_id SERIAL PRIMARY KEY,
sender_id INT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
receiver_id INT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
content VARCHAR(1000) NOT NULL,
timestamp TIMESTAMP NOT NULL
);

CREATE TABLE classrooms (
classroom_id SERIAL PRIMARY KEY,
room_number INT NOT NULL
);

CREATE TABLE timeslots (
timeslot_id SERIAL PRIMARY KEY,
slot_name VARCHAR(50) NOT NULL,
start_time TIME NOT NULL,
end_time TIME NOT NULL
);

CREATE TABLE lessons (
lesson_id SERIAL PRIMARY KEY,
date DATE NOT NULL,
course_id INT REFERENCES courses(course_id) ON DELETE CASCADE,
teacher_id INT REFERENCES teachers(teacher_id) ON DELETE SET NULL,
classroom_id INT REFERENCES classrooms(classroom_id) ON DELETE SET NULL,
timeslot_id INT REFERENCES timeslots(timeslot_id) ON DELETE SET NULL,
group_id INT REFERENCES groups(group_id) ON DELETE SET NULL
);

CREATE TABLE grade_records (
grade_id SERIAL PRIMARY KEY,
student_id INT REFERENCES students(student_id) ON DELETE CASCADE,
course_id INT REFERENCES courses(course_id) ON DELETE CASCADE,
grade DECIMAL(4, 1) NOT NULL,
teacher_id INT REFERENCES teachers(teacher_id) ON DELETE SET NULL,
exam_date DATE NOT NULL
);