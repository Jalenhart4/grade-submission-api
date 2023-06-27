-- This SQL script populates the h2 database tables with sample data at runtime.

-- Students table
insert into student (name, birth_date) values ('Roy Cropper', '2003-07-31'), ('Randolph Branson', '2001-03-01'), ('Elicia Ashley', '2003-05-20'),('Kat Ellington', '2002-11-06');

-- Courses table
insert into course (subject, code, description) values ('Gym', 'GYM101', 'In this course we teach physical education.'), ('Math', 'MATH201', 'In this course we go over the fundamentals of algebra.'), ('Biology','BIO101', 'In this course we introduce the fundamentals of biology.'), ('History', 'HIST250', 'In this course we review the history of ancient civilizations');

-- Course and student associative table
insert into course_student values (1,1), (1,2), (1,3), (1,4);
insert into course_student values (2,1), (2,2), (2,3), (2,4);
insert into course_student values (3,1), (3,2), (3,3), (3,4);
insert into course_student values (4,1), (4,2), (4,3), (4,4);

-- Grades table
insert into grade (score, course_id, student_id) values ('A+', 1, 1), ('C', 2, 1), ('A', 3, 1), ('B+', 4, 1);
insert into grade (score, course_id, student_id) values ('B', 1, 2), ('A-', 2, 2), ('C+', 3, 2), ('A-', 4, 2);
insert into grade (score, course_id, student_id) values ('A+', 1, 3), ('A+', 2, 3), ('A+', 3, 3), ('B+', 4, 3);
insert into grade (score, course_id, student_id) values ('C', 1, 4), ('B-', 2, 4), ('A', 3, 4), ('A+', 4, 4);