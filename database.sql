CREATE TABLE department(
    dep TEXT NOT NULL,
    full_name TEXT NOT NULL,
    PRIMARY KEY(dep)
);

INSERT INTO department(dep,full_name) VALUES('CSE','Computer Science and Engineering');
INSERT INTO department(dep,full_name) VALUES('EE','Electrical Engineering');
INSERT INTO department(dep,full_name) VALUES('ME','Mechanical Engineering');
INSERT INTO department(dep,full_name) VALUES('CE','Civil Engineering');
INSERT INTO department(dep,full_name) VALUES('CHE','Chemical Engineering');
CREATE TABLE course_type(
    c_type TEXT NOT NULL,
    PRIMARY KEY(c_type)
);

INSERT INTO course_type(c_type) values('PC');
INSERT INTO course_type(c_type) values('PE');
INSERT INTO course_type(c_type) values('CP');
INSERT INTO course_type(c_type) values('GC');
INSERT INTO course_type(c_type) values('GE');
INSERT INTO course_type(c_type) values('SC');
INSERT INTO course_type(c_type) values('SE');
INSERT INTO course_type(c_type) values('HC');
INSERT INTO course_type(c_type) values('HE');
INSERT INTO course_type(c_type) values('OC');
INSERT INTO course_type(c_type) values('OE');
INSERT INTO course_type(c_type) values('NN');

CREATE TABLE institue_session(
    session_name TEXT NOT NULL,
    start_dat TEXT NOT NULL,
    end_dat TEXT NOT NULL,
    current_status TEXT NOT null,
    PRIMARY KEY(session_name)
);

CREATE TABLE program(
    pg TEXT NOT NULL,
    PRIMARY KEY(pg)
);

-- 

INSERT INTO program(pg) VALUES('B.Tech');
INSERT INTO program(pg) VALUES('M.Tech');
INSERT INTO program(pg) VALUES('Phd');

CREATE TABLE course_catalog( --course catalog
    course_id TEXT NOT NULL,
    course_name TEXT NOT NULL,
    credit TEXT NOT NULL,
    L_T_P TEXT NOT NULL,
    prerequisites TEXT NOT NULL,
    PRIMARY KEY(course_id)
);

INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS201','Data Structure','4','3-1-2','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS203','Digital Logic Design','4','3-1-2','CS201');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('MA201','Differential Equations','3','3-1-2','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('EE201','Signals and Systems','3','3-1-2','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('NS111','NCC/NSO/NSS','1','0-0-1','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('HS201','Economics','4','3-1-0','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('GE107','Tinkering Lab','4','0-0-3','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS202','Complier Design','4','3-1-2','CS201, GE107, HS201');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS204','Computer Architecture','4','3-1-2','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('MA202','Probability and Statistics','3','3-1-2','MA201');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('BM101','Biology for Engineers','3','3-1-2','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS301','Introduction to Databases Systems','4','3-1-2','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS302','Analysis and Design of Algorithms','4','3-1-2','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS303','Operating Systems','4','3-1-2','CS201, CS204');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('HS104','Professional Ethics','1','3-1-2','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS304','Computer Networks','4','3-1-2','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS305','Software Engineering','4','3-1-2','CS201');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS306','Theory of Computation','3','3-1-2','CS202');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CP301','Development Engineering Project','3','3-1-2','NA');
INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) VALUES('CS307','Machine Learning','10','3-1-2','NA');

CREATE TABLE batch(
    year TEXT NOT NULL,
    program TEXT NOT NULL,
    batch_status TEXT NOT NULL,
    PRIMARY KEY(year,program),
    FOREIGN KEY(program) REFERENCES program(pg)
);

CREATE TABLE institute_status(
    batch TEXT NOT NULL,
    program TEXT NOT NULL,
    can_add TEXT NOT NULL,
    can_drop TEXT NOT NULL,
    PRIMARY KEY(batch,program),
    FOREIGN KEY(batch,program) REFERENCES batch(year,program)
);
CREATE TABLE credit_limit_1st_year(
    batch TEXT NOT NULL,
    program TEXT NOT NULL,
    session_number TEXT NOT NULL,
    credit_limit TEXT NOT NULL,
    PRIMARY key (batch,program,session_number),
    FOREIGN KEY(batch,program) REFERENCES batch(year,program)
);

CREATE TABLE batch_session(
    batch TEXT NOT NULL,
    program TEXT NOT NULL,
    session_name TEXT NOT NULL,
    session_status TEXT NOT NULL,
    assign_date TEXT NOT NULL,
    PRIMARY KEY(batch,program,session_name),
    FOREIGN KEY(session_name) REFERENCES institue_session(session_name),
    FOREIGN KEY(batch,program) REFERENCES batch(year,program)

);


CREATE TABLE program_requirement(
    program TEXT NOT NULL,
    batch TEXT NOT NULL,
    PC TEXT NOT NULL,
    PE TEXT NOT NULL,
    CP TEXT NOT NULL,
    GC TEXT NOT NULL,
    GE TEXT NOT NULL,
    SC TEXT NOT NULL,
    SE TEXT NOT NULL,
    HC TEXT NOT NULL,
    HE TEXT NOT NULL,
    OC TEXT NOT NULL,
    OE TEXT NOT NULL,
    NN TEXT NOT NULL,
    PRIMARY KEY(program,batch),
    FOREIGN KEY(batch,program) REFERENCES batch(year,program)

);

CREATE TABLE student(
    entry_no  TEXT NOT NULL,
    student_name  TEXT NOT NULL,
    department TEXT NOT NULL,
    batch TEXT NOT NULL,
    program TEXT NOT NULL,
    phone  TEXT,
    email  TEXT,
    PRIMARY KEY(entry_no),
    FOREIGN KEY(department) REFERENCES department(dep),
    FOREIGN KEY(batch,program) REFERENCES batch(year,program)

);


CREATE TABLE student_user(
    username TEXT NOT NULL,
    pass  TEXT NOT NULL,
    PRIMARY KEY(username),
    FOREIGN KEY(username) REFERENCES student(entry_no)
);



-- command to insert department


CREATE TABLE faculty(
    faculty_id  TEXT NOT NULL,
    faculty_name   TEXT NOT NULL,
    department TEXT NOT NULL,
    phone   TEXT,
    email   TEXT,
    PRIMARY KEY(faculty_id),
    FOREIGN KEY(department) REFERENCES department(dep)

);

CREATE TABLE faculty_user(
    username  TEXT NOT NULL,
    pass   TEXT NOT NULL,
    PRIMARY KEY(username),
    FOREIGN KEY(username) REFERENCES faculty(faculty_id)
);





CREATE TABLE academic(
    admin_id  TEXT NOT NULL,
    admin_name   TEXT NOT NULL,
    phone   TEXT,
    email   TEXT,
    PRIMARY KEY(admin_id)
);
INSERT INTO academic(admin_id,admin_name,phone,email) VALUES('Staff_Dean','BOSS','1234567890','asdfg@gmail.com');
INSERT INTO academic(admin_id,admin_name,phone,email) VALUES('admin','academic','1234567890','asdfg@gmail.com');

CREATE TABLE academic_user(
    username  TEXT NOT NULL,
    pass   TEXT NOT NULL,
    PRIMARY KEY(username),
    FOREIGN KEY(username) REFERENCES academic(admin_id)
);
INSERT INTO academic_user(username,pass) VALUES('Staff_Dean','12345');
INSERT INTO academic_user(username,pass) VALUES('admin','admin');
-- command to insert dean and a normal academic user

CREATE TABLE special_access( -- who have permission to change catalog
    username TEXT NOT NULL,
    PRIMARY KEY (username)
);


INSERT INTO special_access(username) VALUES('Staff_Dean');



CREATE TABLE float_courses(
    course_id TEXT NOT NULL,
    course_name TEXT NOT NULL,
    instructor_id TEXT NOT NULL,
    instructor_name TEXT NOT NULL,
    department TEXT NOT NULL,
    L_T_P TEXT NOT NULL,
    credit TEXT NOT NULL,
    prerequisites TEXT NOT NULL,
    cg_criteria TEXT NOT NULL,
    current_session TEXT NOT NULL,
    current_status TEXT NOT NULL,
    PRIMARY KEY(course_id,instructor_id,current_session,current_status),
    FOREIGN KEY(course_id) REFERENCES course_catalog(course_id),
    FOREIGN KEY(instructor_id) REFERENCES faculty(faculty_id),
    FOREIGN KEY(department) REFERENCES department(dep)

);



CREATE TABLE course_curriculam(
    batch TEXT NOT NULL,
    program TEXT NOT NULL,
    department TEXT NOT NULL,
    course_id TEXT NOT NULL,
    course_name TEXT NOT NULL,
    course_type TEXT NOT NULL,
    credit TEXT NOT NULL,
    L_T_P TEXT NOT NULL,
    prerequisites TEXT NOT NULL,
    PRIMARY KEY(batch,program,course_id,department),
    FOREIGN KEY(course_id) REFERENCES course_catalog(course_id),
    FOREIGN KEY(department) REFERENCES department(dep)
);



-- CREATE TABLE course_2020csb1130(
--     course_id TEXT NOT NULL,
--     course_name TEXT NOT NULL,
--     course_type TEXT NOT NULL, 
--     instructor_name TEXT NOT NULL,
--     instructor_id TEXT NOT NULL,
--     L_T_P TEXT NOT NULL,
--     credit TEXT NOT NULL,
--     current_session TEXT NOT NULL,
--     grade TEXT,
--     current_status TEXT NOT NULL,
--     PRIMARY KEY(course_id,current_session),
--     FOREIGN KEY(course_id) REFERENCES course_catalog(course_id),
--     FOREIGN KEY(instructor_id) REFERENCES faculty(faculty_id)
-- );

-- CREATE TABLE enroll_course_id_faculty_id_session (
--     entry_no TEXT NOT NULL,
--     stu_name TEXT NOT NULL,
--     enroll_status TEXT NOT NULL,
--     grade TEXT,
--     PRIMARY KEY(entry_no),
--     FOREIGN KEY(entry_no) REFERENCES student(entry_no)
-- )






-- ----------------------------------------- NOt creted now
-- CREATE TABLE faculty_status(
--     department text not null,
--     can_float text not null,
-- );

-- -----------------------------------------

