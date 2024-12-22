create table course_events (
   id uuid not null,
    weekday int4 not null,
    course_id uuid not null,
    timetable_id uuid,
    primary key (id)
);

create table course_lecturers (
   fk_course_id uuid not null,
    fk_employee_id uuid not null
);

create table course_relations (
   id uuid not null,
    relation_type varchar(255) not null,
    fk_course_a_id uuid not null,
    fk_course_b_id uuid not null,
    primary key (id)
);

create table course_timeslots (
   id uuid not null,
    weekday varchar(255) not null,
    fk_course_id uuid,
    fk_timeslot_id uuid not null,
    primary key (id)
);

create table courses (
   id uuid not null,
    abbreviation varchar(255),
    block_size int4 not null,
    casid varchar(255),
    description varchar(255),
    name varchar(255),
    slots_per_week int4 not null,
    weeks_per_semester int4 not null,
    fk_room_type_id uuid,
    fk_timetable_id uuid not null,
    primary key (id)
);

create table course_types (
   id uuid not null,
    name varchar(255),
    primary key (id)
);

create table degrees (
   id uuid not null,
    name varchar(255),
    semester_amount int4 not null,
    short_name varchar(255),
    study_regulation varchar(255),
    fk_room_type_id uuid,
    fk_timetable_id uuid not null,
    primary key (id)
);

create table degreesemester_course (
   fk_degreesemester_id uuid not null,
    fk_course_id uuid not null
);

create table degree_semesters (
   id uuid not null,
    attendees int4 not null,
    extension_name varchar(255),
    semester_number int4 not null,
    fk_degree_id uuid,
    fk_timetable_id uuid not null,
    primary key (id)
);

create table employees (
   id uuid not null,
    abbreviation varchar(255),
    firstname varchar(255) not null,
    lastname varchar(255) not null,
    fk_employee_type_id uuid not null,
    fk_timetable_id uuid not null,
    primary key (id)
);

create table employee_types (
   id uuid not null,
    name varchar(255),
    primary key (id)
);

create table roomcombinations (
   id uuid not null,
    fk_event_id uuid not null,
    primary key (id)
);

create table roomcombinations_rooms (
   fk_roomcombination_id uuid not null,
    fk_rooms_id uuid not null
);

create table rooms (
   id uuid not null,
    abbreviation varchar(255),
    capacity int4 not null,
    identifier varchar(255),
    name varchar(255),
    fk_room_type_id uuid not null,
    fk_timetable_id uuid not null,
    primary key (id)
);

create table room_types (
   id uuid not null,
    name varchar(255),
    primary key (id)
);

create table school_types (
   id uuid not null,
    name varchar(255),
    primary key (id)
);

create table semester_types (
   id uuid not null,
    name varchar(255),
    primary key (id)
);

create table special_events (
   id uuid not null,
    end_date date not null,
    special_event_type varchar(255) not null,
    start_date date not null,
    fk_timetable_id uuid not null,
    primary key (id)
);

create table timeslots (
   id uuid not null,
    end_time time not null,
    index int4 not null,
    start_time time not null,
    fk_timetable_id uuid not null,
    primary key (id)
);

create table timetables (
   id uuid not null,
    end_date date not null,
    name varchar(255),
    number_of_weeks int4 not null,
    start_date date not null,
    fk_semester_type_id uuid not null,
    primary key (id)
);

create table users (
   id uuid not null,
    display_name varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

create table week_events_rooms (
   week_event_id uuid not null,
    room_id uuid not null
);

create table week_events_timeslots (
   week_event_id uuid not null,
    timeslot_id uuid not null
);

create table worktimes (
   id uuid not null,
    weekday varchar(255) not null,
    fk_employee_id uuid,
    fk_timeslot_id uuid not null,
    primary key (id)
);

alter table course_relations
   add constraint UKl7x9raotlw28mqgk7oke7qxbw unique (relation_type, fk_course_a_id, fk_course_b_id);

alter table courses
   add constraint UKakrmvnqqghuncsig8sd2wfq0r unique (fk_timetable_id, casid);

alter table course_types
   add constraint UKovgh66bavcgkuh88gsebdexjk unique (name);

alter table employees
   add constraint UKcjxhtq02d4uti3dsybw7fcr6n unique (fk_timetable_id, abbreviation);

alter table employee_types
   add constraint UKrl6vfmhhwmdmufp2scrjnj7mp unique (name);

alter table rooms
   add constraint UKmdohenrdph391nmrwunoehpyc unique (fk_timetable_id, identifier);

alter table room_types
   add constraint UKb70k1tp1aa52elkkxht660u36 unique (name);

alter table school_types
   add constraint UK6bi4x3c9chk0aitqjq7kfl9hw unique (name);

alter table semester_types
   add constraint UK4m1r77usgewdcpsad1kgouey9 unique (name);

alter table timeslots
   add constraint UK75eefm0emowsbiqkpnl690lp4 unique (fk_timetable_id, index);

alter table users
   add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

alter table course_events
   add constraint FK9ivtlh0wbaw8fayk0pbdsgtiw
   foreign key (course_id)
   references courses;

alter table course_events
   add constraint FKpf6kask75ucr4xde3bl1wh0q7
   foreign key (timetable_id)
   references timetables;

alter table course_lecturers
   add constraint FKdu5yu1nafbbtwv7d3fy4pvnh3
   foreign key (fk_employee_id)
   references employees;

alter table course_lecturers
   add constraint FKng1jvjmdw5y9sm9hx5y8yv1nk
   foreign key (fk_course_id)
   references courses;

alter table course_relations
   add constraint FK4dokgm2ygkf6hnkq39qoa0kci
   foreign key (fk_course_a_id)
   references courses;

alter table course_relations
   add constraint FKatnm3v9rea5r9hqhlk7irvawr
   foreign key (fk_course_b_id)
   references courses;

alter table course_timeslots
   add constraint FKg36dckkcxye3my27pps3nnr80
   foreign key (fk_course_id)
   references courses;

alter table course_timeslots
   add constraint FKlis9veuh83ikct4e38a3dn5nt
   foreign key (fk_timeslot_id)
   references timeslots;

alter table courses
   add constraint FK68aqexjbca39jxtnj7i4jav59
   foreign key (fk_room_type_id)
   references course_types;

alter table courses
   add constraint FKduxpyovdue3qluanbfxkl9f25
   foreign key (fk_timetable_id)
   references timetables;

alter table degrees
   add constraint FK4sq2h7tgad2i0cp9y4vdvgp2j
   foreign key (fk_room_type_id)
   references school_types;

alter table degrees
   add constraint FKhvdokv184expnnryi3bgtm3j4
   foreign key (fk_timetable_id)
   references timetables;

alter table degreesemester_course
   add constraint FKgoyvxr4edkke6fp3qc7c38blj
   foreign key (fk_course_id)
   references courses;

alter table degreesemester_course
   add constraint FKn48tvo7sc5w7m87xmdnnwubc
   foreign key (fk_degreesemester_id)
   references degree_semesters;

alter table degree_semesters
   add constraint FKjijtb3w08368m12kw07yqfhus
   foreign key (fk_degree_id)
   references degrees;

alter table degree_semesters
   add constraint FKtn23bn0xajqkm9v5aq2di3b9s
   foreign key (fk_timetable_id)
   references timetables;

alter table employees
   add constraint FKlhkwlyjfyyp43p0a4ddy16p4n
   foreign key (fk_employee_type_id)
   references employee_types;

alter table employees
   add constraint FK5tupjc08qgmvystdximd7wnqu
   foreign key (fk_timetable_id)
   references timetables;

alter table roomcombinations
   add constraint FKgvi2ufwlqmvi75dltbfft24va
   foreign key (fk_event_id)
   references courses;

alter table roomcombinations_rooms
   add constraint FK4hos9w928yog2sx3vka3g0hsb
   foreign key (fk_rooms_id)
   references rooms;

alter table roomcombinations_rooms
   add constraint FK1gkw9rsj89s6x4ibt10kqu67q
   foreign key (fk_roomcombination_id)
   references roomcombinations;

alter table rooms
   add constraint FKdf39hi52lc2mw2yto0stk1r1g
   foreign key (fk_room_type_id)
   references room_types;

alter table rooms
   add constraint FKl3mlv910gj7yqkq8iqvu67lf2
   foreign key (fk_timetable_id)
   references timetables;

alter table special_events
   add constraint FKdkkhr8b7i4dlsukqs9k8iivpx
   foreign key (fk_timetable_id)
   references timetables;

alter table timeslots
   add constraint FKjwmripm2velb1gbijrruemfom
   foreign key (fk_timetable_id)
   references timetables;

alter table timetables
   add constraint FKlijmpt280vox1siag3soua6ab
   foreign key (fk_semester_type_id)
   references semester_types;

alter table week_events_rooms
   add constraint FKdr5q75fijx97s0300nemvk4ve
   foreign key (room_id)
   references rooms;

alter table week_events_rooms
   add constraint FKlyg0l79yhptnyg9lrumvciiw3
   foreign key (week_event_id)
   references course_events;

alter table week_events_timeslots
   add constraint FKm57mdpn4w93o92tncb41yrgh2
   foreign key (timeslot_id)
   references timeslots;

alter table week_events_timeslots
   add constraint FK6wjhqj3412qwmag2idi19b8ud
   foreign key (week_event_id)
   references course_events;

alter table worktimes
   add constraint FKcjil9c13wmtx6sjxyajfssmdm
   foreign key (fk_employee_id)
   references employees;

alter table worktimes
   add constraint FK1me2yf9lmn79f6b25dvq9ct0h
   foreign key (fk_timeslot_id)
   references timeslots;
