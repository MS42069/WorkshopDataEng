--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1 (Debian 15.1-1.pgdg110+1)
-- Dumped by pg_dump version 15.1 (Debian 15.1-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE ONLY public.degree_semesters DROP CONSTRAINT fktn23bn0xajqkm9v5aq2di3b9s;
ALTER TABLE ONLY public.course_events DROP CONSTRAINT fkpf6kask75ucr4xde3bl1wh0q7;
ALTER TABLE ONLY public.course_lecturers DROP CONSTRAINT fkng1jvjmdw5y9sm9hx5y8yv1nk;
ALTER TABLE ONLY public.degreesemester_course DROP CONSTRAINT fkn48tvo7sc5w7m87xmdnnwubc;
ALTER TABLE ONLY public.week_events_timeslots DROP CONSTRAINT fkm57mdpn4w93o92tncb41yrgh2;
ALTER TABLE ONLY public.week_events_rooms DROP CONSTRAINT fklyg0l79yhptnyg9lrumvciiw3;
ALTER TABLE ONLY public.course_timeslots DROP CONSTRAINT fklis9veuh83ikct4e38a3dn5nt;
ALTER TABLE ONLY public.timetables DROP CONSTRAINT fklijmpt280vox1siag3soua6ab;
ALTER TABLE ONLY public.employees DROP CONSTRAINT fklhkwlyjfyyp43p0a4ddy16p4n;
ALTER TABLE ONLY public.rooms DROP CONSTRAINT fkl3mlv910gj7yqkq8iqvu67lf2;
ALTER TABLE ONLY public.timeslots DROP CONSTRAINT fkjwmripm2velb1gbijrruemfom;
ALTER TABLE ONLY public.degree_semesters DROP CONSTRAINT fkjijtb3w08368m12kw07yqfhus;
ALTER TABLE ONLY public.degrees DROP CONSTRAINT fkhvdokv184expnnryi3bgtm3j4;
ALTER TABLE ONLY public.roomcombinations DROP CONSTRAINT fkgvi2ufwlqmvi75dltbfft24va;
ALTER TABLE ONLY public.degreesemester_course DROP CONSTRAINT fkgoyvxr4edkke6fp3qc7c38blj;
ALTER TABLE ONLY public.course_timeslots DROP CONSTRAINT fkg36dckkcxye3my27pps3nnr80;
ALTER TABLE ONLY public.courses DROP CONSTRAINT fkduxpyovdue3qluanbfxkl9f25;
ALTER TABLE ONLY public.course_lecturers DROP CONSTRAINT fkdu5yu1nafbbtwv7d3fy4pvnh3;
ALTER TABLE ONLY public.week_events_rooms DROP CONSTRAINT fkdr5q75fijx97s0300nemvk4ve;
ALTER TABLE ONLY public.special_events DROP CONSTRAINT fkdkkhr8b7i4dlsukqs9k8iivpx;
ALTER TABLE ONLY public.rooms DROP CONSTRAINT fkdf39hi52lc2mw2yto0stk1r1g;
ALTER TABLE ONLY public.worktimes DROP CONSTRAINT fkcjil9c13wmtx6sjxyajfssmdm;
ALTER TABLE ONLY public.course_relations DROP CONSTRAINT fkatnm3v9rea5r9hqhlk7irvawr;
ALTER TABLE ONLY public.course_events DROP CONSTRAINT fk9ivtlh0wbaw8fayk0pbdsgtiw;
ALTER TABLE ONLY public.week_events_timeslots DROP CONSTRAINT fk6wjhqj3412qwmag2idi19b8ud;
ALTER TABLE ONLY public.courses DROP CONSTRAINT fk68aqexjbca39jxtnj7i4jav59;
ALTER TABLE ONLY public.employees DROP CONSTRAINT fk5tupjc08qgmvystdximd7wnqu;
ALTER TABLE ONLY public.degrees DROP CONSTRAINT fk4sq2h7tgad2i0cp9y4vdvgp2j;
ALTER TABLE ONLY public.roomcombinations_rooms DROP CONSTRAINT fk4hos9w928yog2sx3vka3g0hsb;
ALTER TABLE ONLY public.course_relations DROP CONSTRAINT fk4dokgm2ygkf6hnkq39qoa0kci;
ALTER TABLE ONLY public.worktimes DROP CONSTRAINT fk1me2yf9lmn79f6b25dvq9ct0h;
ALTER TABLE ONLY public.roomcombinations_rooms DROP CONSTRAINT fk1gkw9rsj89s6x4ibt10kqu67q;
ALTER TABLE ONLY public.worktimes DROP CONSTRAINT worktimes_pkey;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
ALTER TABLE ONLY public.employee_types DROP CONSTRAINT ukrl6vfmhhwmdmufp2scrjnj7mp;
ALTER TABLE ONLY public.course_types DROP CONSTRAINT ukovgh66bavcgkuh88gsebdexjk;
ALTER TABLE ONLY public.rooms DROP CONSTRAINT ukmdohenrdph391nmrwunoehpyc;
ALTER TABLE ONLY public.course_relations DROP CONSTRAINT ukl7x9raotlw28mqgk7oke7qxbw;
ALTER TABLE ONLY public.employees DROP CONSTRAINT ukcjxhtq02d4uti3dsybw7fcr6n;
ALTER TABLE ONLY public.room_types DROP CONSTRAINT ukb70k1tp1aa52elkkxht660u36;
ALTER TABLE ONLY public.courses DROP CONSTRAINT ukakrmvnqqghuncsig8sd2wfq0r;
ALTER TABLE ONLY public.users DROP CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6;
ALTER TABLE ONLY public.timeslots DROP CONSTRAINT uk75eefm0emowsbiqkpnl690lp4;
ALTER TABLE ONLY public.school_types DROP CONSTRAINT uk6bi4x3c9chk0aitqjq7kfl9hw;
ALTER TABLE ONLY public.semester_types DROP CONSTRAINT uk4m1r77usgewdcpsad1kgouey9;
ALTER TABLE ONLY public.timetables DROP CONSTRAINT timetables_pkey;
ALTER TABLE ONLY public.timeslots DROP CONSTRAINT timeslots_pkey;
ALTER TABLE ONLY public.special_events DROP CONSTRAINT special_events_pkey;
ALTER TABLE ONLY public.semester_types DROP CONSTRAINT semester_types_pkey;
ALTER TABLE ONLY public.school_types DROP CONSTRAINT school_types_pkey;
ALTER TABLE ONLY public.rooms DROP CONSTRAINT rooms_pkey;
ALTER TABLE ONLY public.roomcombinations DROP CONSTRAINT roomcombinations_pkey;
ALTER TABLE ONLY public.room_types DROP CONSTRAINT room_types_pkey;
ALTER TABLE ONLY public.employees DROP CONSTRAINT employees_pkey;
ALTER TABLE ONLY public.employee_types DROP CONSTRAINT employee_types_pkey;
ALTER TABLE ONLY public.degrees DROP CONSTRAINT degrees_pkey;
ALTER TABLE ONLY public.degree_semesters DROP CONSTRAINT degree_semesters_pkey;
ALTER TABLE ONLY public.courses DROP CONSTRAINT courses_pkey;
ALTER TABLE ONLY public.course_types DROP CONSTRAINT course_types_pkey;
ALTER TABLE ONLY public.course_timeslots DROP CONSTRAINT course_timeslots_pkey;
ALTER TABLE ONLY public.course_relations DROP CONSTRAINT course_relations_pkey;
ALTER TABLE ONLY public.course_events DROP CONSTRAINT course_events_pkey;
DROP TABLE public.worktimes;
DROP TABLE public.week_events_timeslots;
DROP TABLE public.week_events_rooms;
DROP TABLE public.users;
DROP TABLE public.timetables;
DROP TABLE public.timeslots;
DROP TABLE public.special_events;
DROP TABLE public.semester_types;
DROP TABLE public.school_types;
DROP TABLE public.rooms;
DROP TABLE public.roomcombinations_rooms;
DROP TABLE public.roomcombinations;
DROP TABLE public.room_types;
DROP TABLE public.employees;
DROP TABLE public.employee_types;
DROP TABLE public.degreesemester_course;
DROP TABLE public.degrees;
DROP TABLE public.degree_semesters;
DROP TABLE public.courses;
DROP TABLE public.course_types;
DROP TABLE public.course_timeslots;
DROP TABLE public.course_relations;
DROP TABLE public.course_lecturers;
DROP TABLE public.course_events;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: course_events; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.course_events (
    id uuid NOT NULL,
    weekday integer NOT NULL,
    course_id uuid NOT NULL,
    timetable_id uuid
);


ALTER TABLE public.course_events OWNER TO db_username;

--
-- Name: course_lecturers; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.course_lecturers (
    fk_course_id uuid NOT NULL,
    fk_employee_id uuid NOT NULL
);


ALTER TABLE public.course_lecturers OWNER TO db_username;

--
-- Name: course_relations; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.course_relations (
    id uuid NOT NULL,
    relation_type character varying(255) NOT NULL,
    fk_course_a_id uuid NOT NULL,
    fk_course_b_id uuid NOT NULL
);


ALTER TABLE public.course_relations OWNER TO db_username;

--
-- Name: course_timeslots; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.course_timeslots (
    id uuid NOT NULL,
    weekday character varying(255) NOT NULL,
    fk_course_id uuid,
    fk_timeslot_id uuid NOT NULL
);


ALTER TABLE public.course_timeslots OWNER TO db_username;

--
-- Name: course_types; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.course_types (
    id uuid NOT NULL,
    name character varying(255)
);


ALTER TABLE public.course_types OWNER TO db_username;

--
-- Name: courses; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.courses (
    id uuid NOT NULL,
    abbreviation character varying(255),
    block_size integer NOT NULL,
    casid character varying(255),
    description character varying(255),
    name character varying(255),
    slots_per_week integer NOT NULL,
    weeks_per_semester integer NOT NULL,
    fk_room_type_id uuid,
    fk_timetable_id uuid NOT NULL
);


ALTER TABLE public.courses OWNER TO db_username;

--
-- Name: degree_semesters; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.degree_semesters (
    id uuid NOT NULL,
    attendees integer NOT NULL,
    extension_name character varying(255),
    semester_number integer NOT NULL,
    fk_degree_id uuid,
    fk_timetable_id uuid NOT NULL
);


ALTER TABLE public.degree_semesters OWNER TO db_username;

--
-- Name: degrees; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.degrees (
    id uuid NOT NULL,
    name character varying(255),
    semester_amount integer NOT NULL,
    short_name character varying(255),
    study_regulation character varying(255),
    fk_room_type_id uuid,
    fk_timetable_id uuid NOT NULL
);


ALTER TABLE public.degrees OWNER TO db_username;

--
-- Name: degreesemester_course; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.degreesemester_course (
    fk_degreesemester_id uuid NOT NULL,
    fk_course_id uuid NOT NULL
);


ALTER TABLE public.degreesemester_course OWNER TO db_username;

--
-- Name: employee_types; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.employee_types (
    id uuid NOT NULL,
    name character varying(255)
);


ALTER TABLE public.employee_types OWNER TO db_username;

--
-- Name: employees; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.employees (
    id uuid NOT NULL,
    abbreviation character varying(255),
    firstname character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    fk_employee_type_id uuid NOT NULL,
    fk_timetable_id uuid NOT NULL
);


ALTER TABLE public.employees OWNER TO db_username;

--
-- Name: room_types; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.room_types (
    id uuid NOT NULL,
    name character varying(255)
);


ALTER TABLE public.room_types OWNER TO db_username;

--
-- Name: roomcombinations; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.roomcombinations (
    id uuid NOT NULL,
    fk_event_id uuid NOT NULL
);


ALTER TABLE public.roomcombinations OWNER TO db_username;

--
-- Name: roomcombinations_rooms; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.roomcombinations_rooms (
    fk_roomcombination_id uuid NOT NULL,
    fk_rooms_id uuid NOT NULL
);


ALTER TABLE public.roomcombinations_rooms OWNER TO db_username;

--
-- Name: rooms; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.rooms (
    id uuid NOT NULL,
    abbreviation character varying(255),
    capacity integer NOT NULL,
    identifier character varying(255),
    name character varying(255),
    fk_room_type_id uuid NOT NULL,
    fk_timetable_id uuid NOT NULL
);


ALTER TABLE public.rooms OWNER TO db_username;

--
-- Name: school_types; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.school_types (
    id uuid NOT NULL,
    name character varying(255)
);


ALTER TABLE public.school_types OWNER TO db_username;

--
-- Name: semester_types; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.semester_types (
    id uuid NOT NULL,
    name character varying(255)
);


ALTER TABLE public.semester_types OWNER TO db_username;

--
-- Name: special_events; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.special_events (
    id uuid NOT NULL,
    end_date date NOT NULL,
    special_event_type character varying(255) NOT NULL,
    start_date date NOT NULL,
    fk_timetable_id uuid NOT NULL
);


ALTER TABLE public.special_events OWNER TO db_username;

--
-- Name: timeslots; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.timeslots (
    id uuid NOT NULL,
    end_time time without time zone NOT NULL,
    index integer NOT NULL,
    start_time time without time zone NOT NULL,
    fk_timetable_id uuid NOT NULL
);


ALTER TABLE public.timeslots OWNER TO db_username;

--
-- Name: timetables; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.timetables (
    id uuid NOT NULL,
    end_date date NOT NULL,
    name character varying(255),
    number_of_weeks integer NOT NULL,
    start_date date NOT NULL,
    fk_semester_type_id uuid NOT NULL
);


ALTER TABLE public.timetables OWNER TO db_username;

--
-- Name: users; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    display_name character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.users OWNER TO db_username;

--
-- Name: week_events_rooms; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.week_events_rooms (
    week_event_id uuid NOT NULL,
    room_id uuid NOT NULL
);


ALTER TABLE public.week_events_rooms OWNER TO db_username;

--
-- Name: week_events_timeslots; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.week_events_timeslots (
    week_event_id uuid NOT NULL,
    timeslot_id uuid NOT NULL
);


ALTER TABLE public.week_events_timeslots OWNER TO db_username;

--
-- Name: worktimes; Type: TABLE; Schema: public; Owner: db_username
--

CREATE TABLE public.worktimes (
    id uuid NOT NULL,
    weekday character varying(255) NOT NULL,
    fk_employee_id uuid,
    fk_timeslot_id uuid NOT NULL
);


ALTER TABLE public.worktimes OWNER TO db_username;

--
-- Data for Name: course_events; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.course_events (id, weekday, course_id, timetable_id) FROM stdin;
\.


--
-- Data for Name: course_lecturers; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.course_lecturers (fk_course_id, fk_employee_id) FROM stdin;
279b0444-5ebc-42ce-a01a-d2e4d39b9874	e84b7f27-2a5c-459e-ba44-b9808e798161
19c4c340-d56b-448a-a9d3-583daa46fefc	ab832241-5ec1-431f-ae8c-6431218c18e2
97baa833-1f10-4349-85a3-c179959af8ce	cffee650-43db-4f7f-b479-d0b439e61287
5885d8cd-49c6-46e6-8a89-36b55de54829	e84b7f27-2a5c-459e-ba44-b9808e798161
d1e3dcb1-fa7c-4355-b42f-90c8378679cb	60873c6f-5448-4cc4-b180-a47f9e3f4b82
24825786-1517-44ed-a97a-843f2ab2e5cf	1becfcec-0a74-4916-9f58-1f895c07bb28
db0c3159-a02b-4e36-86e4-eb3cd4d210ee	54b44371-4415-4b45-abd7-63b86369973c
04fcd6fc-890b-421d-8137-16fe13250a42	54b44371-4415-4b45-abd7-63b86369973c
a7fd852d-5a48-40c2-9715-afeee84a39e0	d63f5f80-19aa-4d82-b825-69beb8129457
a7fd852d-5a48-40c2-9715-afeee84a39e0	bfc2ac28-44fa-479c-bb71-2f858690cdc6
9f611bd8-64ed-4cad-9d07-a8fc6184104d	d63f5f80-19aa-4d82-b825-69beb8129457
9f611bd8-64ed-4cad-9d07-a8fc6184104d	bfc2ac28-44fa-479c-bb71-2f858690cdc6
b8afdaa4-2e1c-47d8-8d15-d1019fa460d5	e84b7f27-2a5c-459e-ba44-b9808e798161
426c7cf6-bad8-4360-bb83-33e2afe1fcb6	1fcbcc1a-16ac-4788-a779-76a65b735e4d
6eeec8d4-efd9-4d38-8643-4ac432801489	0b71b641-82cb-4c00-8c51-5c522188a59e
e63a3e7d-406b-4907-b08d-9256af86a2a6	0b71b641-82cb-4c00-8c51-5c522188a59e
6f115cf8-f0f9-4cce-84ef-5e4f5bb11ba5	8352510c-ee2b-4a43-b002-62affd865904
6f115cf8-f0f9-4cce-84ef-5e4f5bb11ba5	3eb87c06-79ea-4b14-8318-741bad302e14
6f115cf8-f0f9-4cce-84ef-5e4f5bb11ba5	011c0cf9-821f-4913-b775-d7191d8b15cf
bc4e93ce-352f-493d-9089-c72c1eeaf8e4	8352510c-ee2b-4a43-b002-62affd865904
bc4e93ce-352f-493d-9089-c72c1eeaf8e4	3eb87c06-79ea-4b14-8318-741bad302e14
bc4e93ce-352f-493d-9089-c72c1eeaf8e4	011c0cf9-821f-4913-b775-d7191d8b15cf
653df30a-8013-4f51-83dd-4d94e429b18d	aeef2474-4ad6-4585-83cf-9ec480c8d0a9
b65a8082-6f76-4f30-b9bc-142d01701d8b	cffee650-43db-4f7f-b479-d0b439e61287
cdcb904d-32c2-4f88-9af8-b7ca23b5bc47	fbe60aa6-8d84-426d-adb8-c40638042c9c
cdcb904d-32c2-4f88-9af8-b7ca23b5bc47	393058cb-870a-45ee-ab55-5e47c84d10b2
c98823ee-d087-4a9e-b518-2b28faaccd5f	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
c98823ee-d087-4a9e-b518-2b28faaccd5f	54b44371-4415-4b45-abd7-63b86369973c
aedd23d3-b98f-4972-aa2e-acdcb8f2c5df	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
aedd23d3-b98f-4972-aa2e-acdcb8f2c5df	393058cb-870a-45ee-ab55-5e47c84d10b2
aedd23d3-b98f-4972-aa2e-acdcb8f2c5df	ab832241-5ec1-431f-ae8c-6431218c18e2
e70ced69-5a9d-4ac5-b110-77317e06f368	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
e70ced69-5a9d-4ac5-b110-77317e06f368	393058cb-870a-45ee-ab55-5e47c84d10b2
e70ced69-5a9d-4ac5-b110-77317e06f368	ab832241-5ec1-431f-ae8c-6431218c18e2
1e896415-6ebe-443f-b048-30725be095c3	7e598367-12d8-46b1-9817-21679731637c
05155d0d-89da-4c55-b92c-94a1387ec741	19655b1e-03fa-4c82-b2a8-42de4e84b4db
6b49d198-e1bd-4710-b4e8-d9befa664a83	11fbd50d-c9f4-4ec0-aa62-58c200b468a3
76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c	11fbd50d-c9f4-4ec0-aa62-58c200b468a3
03019641-317d-4b74-adaa-1580ff4c99bc	64847330-57d7-4e0c-bb60-f1b74673512c
af9e38a7-5fea-42ef-b6c1-cd56d0161988	703da33b-9830-4322-a1b5-2018d76a624f
3e4285d4-8e01-4483-9219-83079f9d3a77	c94019d0-8741-4f4b-a6ab-0c2439d77bde
3e4285d4-8e01-4483-9219-83079f9d3a77	ded06937-76c4-4516-864e-f14d83081e99
3e4285d4-8e01-4483-9219-83079f9d3a77	098646af-2946-4e14-b37b-aae996c5a920
3e4285d4-8e01-4483-9219-83079f9d3a77	54b44371-4415-4b45-abd7-63b86369973c
3e4285d4-8e01-4483-9219-83079f9d3a77	39b4633f-81a5-46df-944c-b29a91f38b14
3e4285d4-8e01-4483-9219-83079f9d3a77	0b71b641-82cb-4c00-8c51-5c522188a59e
1a931c05-4408-4eb6-afdc-905ebda9edc1	c94019d0-8741-4f4b-a6ab-0c2439d77bde
1a931c05-4408-4eb6-afdc-905ebda9edc1	ded06937-76c4-4516-864e-f14d83081e99
1a931c05-4408-4eb6-afdc-905ebda9edc1	098646af-2946-4e14-b37b-aae996c5a920
1a931c05-4408-4eb6-afdc-905ebda9edc1	39b4633f-81a5-46df-944c-b29a91f38b14
1a931c05-4408-4eb6-afdc-905ebda9edc1	0b71b641-82cb-4c00-8c51-5c522188a59e
1f56ef6d-19b1-47a8-972d-0a9eb17e56a7	b54d4881-1bc6-4134-9bac-2bc983148ed5
5e445afa-73c0-4500-a0d8-697acd7bf58e	587397a4-894b-4a60-ae95-af17f7c71a70
e738acf7-a61b-489a-a6f9-bda344038174	703da33b-9830-4322-a1b5-2018d76a624f
68dd3eea-cea2-4e54-b2e3-2179938239ca	c94019d0-8741-4f4b-a6ab-0c2439d77bde
68dd3eea-cea2-4e54-b2e3-2179938239ca	ded06937-76c4-4516-864e-f14d83081e99
68dd3eea-cea2-4e54-b2e3-2179938239ca	64847330-57d7-4e0c-bb60-f1b74673512c
7d48e39c-8f0c-4db0-bd68-3c4b83e2b8d1	c94019d0-8741-4f4b-a6ab-0c2439d77bde
7d48e39c-8f0c-4db0-bd68-3c4b83e2b8d1	ded06937-76c4-4516-864e-f14d83081e99
7d48e39c-8f0c-4db0-bd68-3c4b83e2b8d1	64847330-57d7-4e0c-bb60-f1b74673512c
3bcee379-cb47-43a8-84f6-933a0b3de0a4	c94019d0-8741-4f4b-a6ab-0c2439d77bde
3bcee379-cb47-43a8-84f6-933a0b3de0a4	ded06937-76c4-4516-864e-f14d83081e99
3bcee379-cb47-43a8-84f6-933a0b3de0a4	64847330-57d7-4e0c-bb60-f1b74673512c
6bbb9b79-33ba-422f-9964-a8435805fcbd	c94019d0-8741-4f4b-a6ab-0c2439d77bde
6bbb9b79-33ba-422f-9964-a8435805fcbd	ded06937-76c4-4516-864e-f14d83081e99
6bbb9b79-33ba-422f-9964-a8435805fcbd	64847330-57d7-4e0c-bb60-f1b74673512c
53dd2ec8-f3ba-46ef-8c66-42bfd18170b9	bfc2ac28-44fa-479c-bb71-2f858690cdc6
8b90882c-0ce3-48a5-8fae-24268e66fed6	d63f5f80-19aa-4d82-b825-69beb8129457
8b90882c-0ce3-48a5-8fae-24268e66fed6	bfc2ac28-44fa-479c-bb71-2f858690cdc6
71e325fb-01c5-44fd-a0de-898927b39357	4ec986fc-f097-4405-bbfa-7026fbe4de16
6f0d248f-ea9c-4f98-8b75-c7569aa198e1	8352510c-ee2b-4a43-b002-62affd865904
6f0d248f-ea9c-4f98-8b75-c7569aa198e1	3eb87c06-79ea-4b14-8318-741bad302e14
6f0d248f-ea9c-4f98-8b75-c7569aa198e1	4ec986fc-f097-4405-bbfa-7026fbe4de16
6f0d248f-ea9c-4f98-8b75-c7569aa198e1	011c0cf9-821f-4913-b775-d7191d8b15cf
6a7e42da-36f8-454a-81bf-0a8bdb23b8cf	8352510c-ee2b-4a43-b002-62affd865904
6a7e42da-36f8-454a-81bf-0a8bdb23b8cf	3eb87c06-79ea-4b14-8318-741bad302e14
6a7e42da-36f8-454a-81bf-0a8bdb23b8cf	011c0cf9-821f-4913-b775-d7191d8b15cf
c0aa58be-47d7-4f72-b017-9125856035e2	8352510c-ee2b-4a43-b002-62affd865904
c0aa58be-47d7-4f72-b017-9125856035e2	3eb87c06-79ea-4b14-8318-741bad302e14
c0aa58be-47d7-4f72-b017-9125856035e2	011c0cf9-821f-4913-b775-d7191d8b15cf
a666d323-152f-48af-be42-f59d6cc27deb	8352510c-ee2b-4a43-b002-62affd865904
a666d323-152f-48af-be42-f59d6cc27deb	3eb87c06-79ea-4b14-8318-741bad302e14
a666d323-152f-48af-be42-f59d6cc27deb	011c0cf9-821f-4913-b775-d7191d8b15cf
7c23bee8-0b73-4720-af87-28c6c180de6a	8352510c-ee2b-4a43-b002-62affd865904
7c23bee8-0b73-4720-af87-28c6c180de6a	3eb87c06-79ea-4b14-8318-741bad302e14
7c23bee8-0b73-4720-af87-28c6c180de6a	011c0cf9-821f-4913-b775-d7191d8b15cf
7d1eb5d5-dd84-4821-836b-ef6a694965f2	8352510c-ee2b-4a43-b002-62affd865904
7d1eb5d5-dd84-4821-836b-ef6a694965f2	3eb87c06-79ea-4b14-8318-741bad302e14
7d1eb5d5-dd84-4821-836b-ef6a694965f2	011c0cf9-821f-4913-b775-d7191d8b15cf
a7e6ba0f-a11c-455e-a15d-ab26528d3ffc	8352510c-ee2b-4a43-b002-62affd865904
a7e6ba0f-a11c-455e-a15d-ab26528d3ffc	3eb87c06-79ea-4b14-8318-741bad302e14
a7e6ba0f-a11c-455e-a15d-ab26528d3ffc	011c0cf9-821f-4913-b775-d7191d8b15cf
aea1a914-5bde-4a5c-92c1-27c9794cf738	8352510c-ee2b-4a43-b002-62affd865904
aea1a914-5bde-4a5c-92c1-27c9794cf738	3eb87c06-79ea-4b14-8318-741bad302e14
aea1a914-5bde-4a5c-92c1-27c9794cf738	011c0cf9-821f-4913-b775-d7191d8b15cf
a8bdcf92-25a5-49f5-b2d7-57c83ff424f4	8352510c-ee2b-4a43-b002-62affd865904
a8bdcf92-25a5-49f5-b2d7-57c83ff424f4	3eb87c06-79ea-4b14-8318-741bad302e14
a8bdcf92-25a5-49f5-b2d7-57c83ff424f4	011c0cf9-821f-4913-b775-d7191d8b15cf
1e7550cf-39bf-4394-a631-6279e4be4997	8352510c-ee2b-4a43-b002-62affd865904
1e7550cf-39bf-4394-a631-6279e4be4997	3eb87c06-79ea-4b14-8318-741bad302e14
1e7550cf-39bf-4394-a631-6279e4be4997	011c0cf9-821f-4913-b775-d7191d8b15cf
009eb4ee-d905-4f04-bb23-7fb156ee569e	8352510c-ee2b-4a43-b002-62affd865904
009eb4ee-d905-4f04-bb23-7fb156ee569e	3eb87c06-79ea-4b14-8318-741bad302e14
009eb4ee-d905-4f04-bb23-7fb156ee569e	011c0cf9-821f-4913-b775-d7191d8b15cf
a4dd8404-9c4a-4046-b857-609f4f6bda92	8352510c-ee2b-4a43-b002-62affd865904
a4dd8404-9c4a-4046-b857-609f4f6bda92	3eb87c06-79ea-4b14-8318-741bad302e14
a4dd8404-9c4a-4046-b857-609f4f6bda92	011c0cf9-821f-4913-b775-d7191d8b15cf
c7955389-a368-4f9a-9a48-cd015c38d841	8352510c-ee2b-4a43-b002-62affd865904
c7955389-a368-4f9a-9a48-cd015c38d841	3eb87c06-79ea-4b14-8318-741bad302e14
c7955389-a368-4f9a-9a48-cd015c38d841	011c0cf9-821f-4913-b775-d7191d8b15cf
a79d088c-d445-4892-90d5-065cacb9446d	8352510c-ee2b-4a43-b002-62affd865904
a79d088c-d445-4892-90d5-065cacb9446d	3eb87c06-79ea-4b14-8318-741bad302e14
a79d088c-d445-4892-90d5-065cacb9446d	011c0cf9-821f-4913-b775-d7191d8b15cf
737ba05d-dffe-4973-a606-f91a3bb8c0f9	8352510c-ee2b-4a43-b002-62affd865904
737ba05d-dffe-4973-a606-f91a3bb8c0f9	3eb87c06-79ea-4b14-8318-741bad302e14
737ba05d-dffe-4973-a606-f91a3bb8c0f9	011c0cf9-821f-4913-b775-d7191d8b15cf
7ab751ea-9048-417d-b0fa-755328a7f385	8352510c-ee2b-4a43-b002-62affd865904
7ab751ea-9048-417d-b0fa-755328a7f385	3eb87c06-79ea-4b14-8318-741bad302e14
7ab751ea-9048-417d-b0fa-755328a7f385	011c0cf9-821f-4913-b775-d7191d8b15cf
b4bebd85-6f9b-4ba2-8ed5-2e4086d6f699	8352510c-ee2b-4a43-b002-62affd865904
b4bebd85-6f9b-4ba2-8ed5-2e4086d6f699	3eb87c06-79ea-4b14-8318-741bad302e14
b4bebd85-6f9b-4ba2-8ed5-2e4086d6f699	011c0cf9-821f-4913-b775-d7191d8b15cf
f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2	a38b16b8-3e0a-4b85-89bc-754a4880780a
77f1a128-ea8f-466e-8c0d-998c2280c811	19655b1e-03fa-4c82-b2a8-42de4e84b4db
32a84dc4-3b53-437c-a78d-d5f5061f2b4d	39b4633f-81a5-46df-944c-b29a91f38b14
78301a7f-a1d9-494b-8c10-8b11baa5976e	587397a4-894b-4a60-ae95-af17f7c71a70
df82acfb-e4dc-4615-9b1f-c41dcbe4803a	b8ed5474-d7e0-4144-b11d-3cf63cf9440d
fb46ae33-946a-46d4-a4f2-29df8b040cff	52ce8f6b-8a6a-4546-875a-8178a9feb6f0
fb46ae33-946a-46d4-a4f2-29df8b040cff	4ec986fc-f097-4405-bbfa-7026fbe4de16
fb46ae33-946a-46d4-a4f2-29df8b040cff	19655b1e-03fa-4c82-b2a8-42de4e84b4db
e211f36c-60a5-47c7-9971-f16df7bf07fc	11fbd50d-c9f4-4ec0-aa62-58c200b468a3
6b1271eb-54e6-49bd-ab27-c85605ec8576	52ce8f6b-8a6a-4546-875a-8178a9feb6f0
6b1271eb-54e6-49bd-ab27-c85605ec8576	cffee650-43db-4f7f-b479-d0b439e61287
26387a0d-6d8b-41d9-9dd9-3174b23458b4	aeef2474-4ad6-4585-83cf-9ec480c8d0a9
613282ae-c93a-4cc8-b6c3-fa73f8a5326c	5e280afb-da17-4629-8fea-40d7880580d6
60c9ed1a-a37e-47cd-986f-8b46d91ffe16	703da33b-9830-4322-a1b5-2018d76a624f
8d5e74b8-7910-4ee3-b424-3697b919da65	4ec986fc-f097-4405-bbfa-7026fbe4de16
97e67f12-4879-4dc6-8759-e205bba8b0f9	52ce8f6b-8a6a-4546-875a-8178a9feb6f0
97e67f12-4879-4dc6-8759-e205bba8b0f9	4ec986fc-f097-4405-bbfa-7026fbe4de16
803f1d5e-f530-4983-b815-68238a066cbf	d231a1a5-763b-4332-8c1b-d4353e932af3
3d6d21de-bf1b-4fbb-aed0-ee57caa00988	52ce8f6b-8a6a-4546-875a-8178a9feb6f0
3d6d21de-bf1b-4fbb-aed0-ee57caa00988	cffee650-43db-4f7f-b479-d0b439e61287
9ab1875d-9ba9-4f1f-8f12-7def153646d1	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745
9ab1875d-9ba9-4f1f-8f12-7def153646d1	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f
e278ea08-a02d-4de2-ab78-d34b856c87d7	89f6ed60-6465-453c-b485-cc80b9d43e17
7e7a157e-f4fe-49f3-9876-5f6dff88ad3a	89f6ed60-6465-453c-b485-cc80b9d43e17
a80a1879-1353-406e-8d2b-74da7a59c64a	05cb8a03-d290-4afb-befb-dac6f0e50004
a80a1879-1353-406e-8d2b-74da7a59c64a	f270d266-40ec-4e7d-87c2-a39d000c2a0b
a80a1879-1353-406e-8d2b-74da7a59c64a	cffee650-43db-4f7f-b479-d0b439e61287
a80a1879-1353-406e-8d2b-74da7a59c64a	e785a4ed-af08-4b78-a450-11c1efa76288
47d63157-d363-4e90-bf84-c94e1f61c0ee	587397a4-894b-4a60-ae95-af17f7c71a70
47d63157-d363-4e90-bf84-c94e1f61c0ee	a38b16b8-3e0a-4b85-89bc-754a4880780a
47d63157-d363-4e90-bf84-c94e1f61c0ee	73b3ef2e-becd-45c3-8f7e-9531a83e53fc
47d63157-d363-4e90-bf84-c94e1f61c0ee	5e280afb-da17-4629-8fea-40d7880580d6
47d63157-d363-4e90-bf84-c94e1f61c0ee	b8ed5474-d7e0-4144-b11d-3cf63cf9440d
86961441-94bd-4771-91e3-3580d18a9f40	19655b1e-03fa-4c82-b2a8-42de4e84b4db
c5273d0a-67cd-4449-aa07-8a9fbc6b3301	6eff3b68-53f2-4b85-a10d-dd9559f3879e
397d9d65-bfca-46fd-96b2-d81390b682b2	3eb87c06-79ea-4b14-8318-741bad302e14
fb02acd0-7388-4507-b25a-49573981c9a1	c78f81ae-bc4e-44f8-9e4b-63a16362ff01
c28a9113-da92-43ba-8123-741f67dc4b89	4ec986fc-f097-4405-bbfa-7026fbe4de16
a03a4688-83e6-439a-a101-87bec382d6b0	8352510c-ee2b-4a43-b002-62affd865904
a03a4688-83e6-439a-a101-87bec382d6b0	3eb87c06-79ea-4b14-8318-741bad302e14
d1d75766-4b31-4f28-ad9f-9d7bb7f49570	8352510c-ee2b-4a43-b002-62affd865904
d1d75766-4b31-4f28-ad9f-9d7bb7f49570	3eb87c06-79ea-4b14-8318-741bad302e14
d1d75766-4b31-4f28-ad9f-9d7bb7f49570	4ec986fc-f097-4405-bbfa-7026fbe4de16
d1d75766-4b31-4f28-ad9f-9d7bb7f49570	011c0cf9-821f-4913-b775-d7191d8b15cf
bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23	229de8cf-dd38-4cf9-99e0-0b086613d2bb
0b2159c6-a703-43bb-b6a1-5392153e01d7	229de8cf-dd38-4cf9-99e0-0b086613d2bb
0b2159c6-a703-43bb-b6a1-5392153e01d7	1fcbcc1a-16ac-4788-a779-76a65b735e4d
3cf8659e-c9c2-4c9f-91d0-897951be0372	229de8cf-dd38-4cf9-99e0-0b086613d2bb
3cf8659e-c9c2-4c9f-91d0-897951be0372	1fcbcc1a-16ac-4788-a779-76a65b735e4d
75ac7b94-3abe-4833-994e-110fc95c2ab4	05cb8a03-d290-4afb-befb-dac6f0e50004
75ac7b94-3abe-4833-994e-110fc95c2ab4	1fcbcc1a-16ac-4788-a779-76a65b735e4d
f9c97c01-a1ad-4ce7-a727-303bcd2342f5	05cb8a03-d290-4afb-befb-dac6f0e50004
836ee776-ad10-4ca5-8999-0b4cc29a032b	7dce6776-3a71-45b5-aa9b-b33ff44e84be
839631b1-18ca-4d4a-8105-6ec7c0cf8ffd	f270d266-40ec-4e7d-87c2-a39d000c2a0b
f8a8bb67-a047-439e-9956-ea4822e56e98	098646af-2946-4e14-b37b-aae996c5a920
f8a8bb67-a047-439e-9956-ea4822e56e98	39b4633f-81a5-46df-944c-b29a91f38b14
f8a8bb67-a047-439e-9956-ea4822e56e98	0b71b641-82cb-4c00-8c51-5c522188a59e
14fe8296-326b-4aed-ad72-315db46d9504	1fcbcc1a-16ac-4788-a779-76a65b735e4d
14fe8296-326b-4aed-ad72-315db46d9504	4a9e587b-b205-471d-950b-fa335e9458f5
4aeb5881-7e48-470e-b134-12236311f9b8	8bba2799-8adb-488b-bee7-4bffc2bb0a0f
b3bd0f0e-83df-4b31-8f33-815e6853a3a9	1fcbcc1a-16ac-4788-a779-76a65b735e4d
86fa5fad-4673-48bf-94f7-d379e7f73676	89f6ed60-6465-453c-b485-cc80b9d43e17
c3c172d0-f316-40db-a6d2-160a54352755	89f6ed60-6465-453c-b485-cc80b9d43e17
5f84a25f-32b7-4b0f-bc74-9151f5e9e941	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d
5f84a25f-32b7-4b0f-bc74-9151f5e9e941	f270d266-40ec-4e7d-87c2-a39d000c2a0b
a95dcb15-398c-449b-8ef6-4bc0a9c4891a	f270d266-40ec-4e7d-87c2-a39d000c2a0b
68be0a27-ed84-4ac1-a8f2-5b22ef9cc442	05cb8a03-d290-4afb-befb-dac6f0e50004
733205c0-613d-49bb-aff8-50a53df081c6	1fcbcc1a-16ac-4788-a779-76a65b735e4d
733205c0-613d-49bb-aff8-50a53df081c6	4a9e587b-b205-471d-950b-fa335e9458f5
c8520c2f-475e-4f88-a3bb-284e7cc58562	098646af-2946-4e14-b37b-aae996c5a920
bf8268d8-5e12-4137-9407-be7988f0eeba	05cb8a03-d290-4afb-befb-dac6f0e50004
50402a5d-cc3a-41af-a544-46dfb70ef381	c64363a0-0a95-46c9-a914-7ec718f016cf
5d617722-8aeb-4e89-b859-01d3ec382190	5e280afb-da17-4629-8fea-40d7880580d6
d03ce83e-c104-4f7a-9aa7-62d1193d18eb	ce5137bc-0f9e-49a1-9e27-0aec555f069d
df7e7468-87c6-4728-b792-e4058c15908d	c78f81ae-bc4e-44f8-9e4b-63a16362ff01
c5611c9c-3d1a-4fae-9999-35acab706abc	ab832241-5ec1-431f-ae8c-6431218c18e2
7418423f-fa22-418e-86b9-7407485fe284	03700b08-395a-4c6b-ad70-d16d7bd2953a
10d0833d-8416-4d03-a831-5a757a9a835c	c64363a0-0a95-46c9-a914-7ec718f016cf
a65ce936-2e63-47e1-aed9-d8159f2d386b	ce5137bc-0f9e-49a1-9e27-0aec555f069d
cea04306-6f20-431d-9904-c58271cf04f7	52ce8f6b-8a6a-4546-875a-8178a9feb6f0
dba6afd0-4e82-476c-b1a4-06555587bffb	43cbec95-02a1-44b2-bf05-f55d105c75ec
b671851f-867f-4d2d-98fd-6f6c5c31c083	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd
31084145-ffa6-4861-9673-13c551ae20ab	cf515827-5393-4ff0-8b0b-17e376c00f3f
3979dd92-065c-40db-9c3a-3794752685df	73b3ef2e-becd-45c3-8f7e-9531a83e53fc
9fcd1dd4-947e-48ad-a71c-937d87f9a894	36685604-58cb-4df6-b19a-c4efb2ef3fe7
4bbc1f38-3573-469b-a204-b84123b925a3	43cbec95-02a1-44b2-bf05-f55d105c75ec
d9ded29f-0a3b-4024-b3cd-7a960ce399b7	cf515827-5393-4ff0-8b0b-17e376c00f3f
7c512d97-6114-4ef1-ba31-55abaf1f274e	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c
d249752a-4f26-45d6-b9a0-4288c97d2ee1	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c
ee549e95-aa66-4707-b957-26b96b35d068	703da33b-9830-4322-a1b5-2018d76a624f
04a38028-135f-4e88-9cfc-12066c8f1df3	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
26654263-ccb2-4fad-8ecc-342da43c3928	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
26654263-ccb2-4fad-8ecc-342da43c3928	39b4633f-81a5-46df-944c-b29a91f38b14
91283133-2877-4bc0-a90d-f4168d088349	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
91283133-2877-4bc0-a90d-f4168d088349	54b44371-4415-4b45-abd7-63b86369973c
91283133-2877-4bc0-a90d-f4168d088349	39b4633f-81a5-46df-944c-b29a91f38b14
0750aa84-1b51-45b8-aaad-056cbf1b303b	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
0750aa84-1b51-45b8-aaad-056cbf1b303b	54b44371-4415-4b45-abd7-63b86369973c
0750aa84-1b51-45b8-aaad-056cbf1b303b	39b4633f-81a5-46df-944c-b29a91f38b14
20651a53-f4c0-415c-a66f-f4cd62a6a3b2	c17fae98-f027-4d30-ad66-e0c859ad6f40
1337b436-7ff9-4b79-82a8-9981f2e66521	4109ab7a-165b-46ec-a0c0-b479d1defeca
1337b436-7ff9-4b79-82a8-9981f2e66521	8ba32227-a15a-4368-86ee-5dbee74385af
1b702945-e962-4778-b9c9-2fce7be4ab6c	138903f7-b68d-4991-a566-a6d6fe8e2902
1b702945-e962-4778-b9c9-2fce7be4ab6c	b41edde6-b90c-47f6-a8bb-143a30990513
f7fb4d07-cc8b-449a-a860-2100d2334658	c64363a0-0a95-46c9-a914-7ec718f016cf
cd5f8554-fec4-4862-80c3-caff5df01ad5	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e
64e7e3c8-c369-4913-be67-a64c7ea29d11	7e598367-12d8-46b1-9817-21679731637c
2f39f7da-a37f-4ccb-8a80-61b108cc7800	ab832241-5ec1-431f-ae8c-6431218c18e2
99a22674-5148-4e0e-85c4-409449b8c7e3	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
a8b61e71-3df5-41bb-9d40-d0fad75e7fbf	393058cb-870a-45ee-ab55-5e47c84d10b2
a8b61e71-3df5-41bb-9d40-d0fad75e7fbf	7e598367-12d8-46b1-9817-21679731637c
7532d5c1-8ffa-4806-9c04-86e7fdccd542	42b1f177-628c-4a87-bf8f-a11932fffa23
7532d5c1-8ffa-4806-9c04-86e7fdccd542	393058cb-870a-45ee-ab55-5e47c84d10b2
86c4b224-039c-49c4-8bbf-1d2c0d923fc6	393058cb-870a-45ee-ab55-5e47c84d10b2
04497442-8dc0-45b3-93c8-a59832f0f297	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
fa24d9c0-130f-499c-ba42-217e905faef6	42b1f177-628c-4a87-bf8f-a11932fffa23
fa24d9c0-130f-499c-ba42-217e905faef6	393058cb-870a-45ee-ab55-5e47c84d10b2
cafaf289-db67-424b-91fa-fa16a5270eda	393058cb-870a-45ee-ab55-5e47c84d10b2
a6c524c4-6748-414d-99dc-443680f2ba63	23f96a10-3b56-4dba-a5a1-bc8e4c071869
a6c524c4-6748-414d-99dc-443680f2ba63	393058cb-870a-45ee-ab55-5e47c84d10b2
cfba2139-68f1-4892-8b8e-607fad765801	23f96a10-3b56-4dba-a5a1-bc8e4c071869
f7ea46a8-2dd4-4a5a-88b9-9b964beb68e7	6eff3b68-53f2-4b85-a10d-dd9559f3879e
a466ef46-1050-4081-8191-f15c08a9f6db	7dce6776-3a71-45b5-aa9b-b33ff44e84be
a466ef46-1050-4081-8191-f15c08a9f6db	42b1f177-628c-4a87-bf8f-a11932fffa23
14db164d-723c-418f-a4bd-9a6631817122	b54d4881-1bc6-4134-9bac-2bc983148ed5
72d02727-c162-43b9-a3ab-9b3e8dc4cbd5	ce5137bc-0f9e-49a1-9e27-0aec555f069d
7d3e4aec-1a31-46ef-81ea-0b996f9ce96c	d63f5f80-19aa-4d82-b825-69beb8129457
7d3e4aec-1a31-46ef-81ea-0b996f9ce96c	bfc2ac28-44fa-479c-bb71-2f858690cdc6
01cce519-4690-41ea-a2d8-d32007b393b4	703da33b-9830-4322-a1b5-2018d76a624f
a48b5c93-d26c-4e45-a863-60e94af8503c	c94019d0-8741-4f4b-a6ab-0c2439d77bde
62d4edd3-b54c-4f35-aef9-d0850f792260	ce5137bc-0f9e-49a1-9e27-0aec555f069d
d0ef13bf-359f-4fff-be36-1b34a6cf72e3	fbe60aa6-8d84-426d-adb8-c40638042c9c
d0ef13bf-359f-4fff-be36-1b34a6cf72e3	aeef2474-4ad6-4585-83cf-9ec480c8d0a9
f311df7f-6876-4c1f-84a2-2e4dae62a914	6eff3b68-53f2-4b85-a10d-dd9559f3879e
6505a4c0-7b39-4751-9e60-be4db3f451f9	7dce6776-3a71-45b5-aa9b-b33ff44e84be
6505a4c0-7b39-4751-9e60-be4db3f451f9	42b1f177-628c-4a87-bf8f-a11932fffa23
99e29aa2-9c26-4d09-9206-6173b7956d7d	4a3d2b06-0f1d-44e0-8971-237e106d30ab
6c6074b4-62fc-4312-91e6-2e8a9196aca7	4a3d2b06-0f1d-44e0-8971-237e106d30ab
0784ce65-0cea-4000-858f-a5bc87d86e8f	a38b16b8-3e0a-4b85-89bc-754a4880780a
6b410de7-f666-40c0-ad19-6e7d74d0a3d7	a38b16b8-3e0a-4b85-89bc-754a4880780a
a2f4b42a-b2a8-44a6-b3dd-0c34f146e904	645a078a-04d3-41ff-b720-080dd70250b1
86cc04fa-7cf7-475b-8404-3bd2df5dfb5d	7dce6776-3a71-45b5-aa9b-b33ff44e84be
8c6942ac-e020-4a00-acc4-8c41409854ab	645a078a-04d3-41ff-b720-080dd70250b1
d9ee24ed-b9e2-41d8-b733-bc8f48b7ae0a	7dce6776-3a71-45b5-aa9b-b33ff44e84be
09be9784-f02e-4c2d-accb-fd49b529e15a	6eff3b68-53f2-4b85-a10d-dd9559f3879e
320d7753-9513-472c-ac95-cc9567a36cc0	f270d266-40ec-4e7d-87c2-a39d000c2a0b
fbd5f933-b196-48bb-b49a-f8f2b1bcf563	645a078a-04d3-41ff-b720-080dd70250b1
e9d67836-a72d-4802-a9f0-25a15e1042e5	b8ed5474-d7e0-4144-b11d-3cf63cf9440d
1c0cc7bc-a82a-4919-aed2-6d95b6a4baed	aeef2474-4ad6-4585-83cf-9ec480c8d0a9
e1482fe4-3872-4f46-a51b-9a648b9a5072	89f6ed60-6465-453c-b485-cc80b9d43e17
e65a3a55-59ca-4533-bdf2-ba8511d594ad	98883c70-e103-48b8-93ff-910511d0f86d
1c7340c1-7b05-404f-8882-39906697c69c	b8ed5474-d7e0-4144-b11d-3cf63cf9440d
2e103b9f-097b-4d2d-9ee9-8806713621ce	587397a4-894b-4a60-ae95-af17f7c71a70
885428c2-75f8-47f6-80a6-94337b9fb10f	c78f81ae-bc4e-44f8-9e4b-63a16362ff01
84a6556d-48f6-4fec-8468-7c6e9dd9c5ab	ab832241-5ec1-431f-ae8c-6431218c18e2
62d4892c-a2ea-456d-90c8-0eef9df99961	ce5137bc-0f9e-49a1-9e27-0aec555f069d
b0853a08-38a4-47f5-b9aa-83f315d74acf	b54d4881-1bc6-4134-9bac-2bc983148ed5
c497544b-bd79-4cf2-a63a-8cffacc1fe46	19655b1e-03fa-4c82-b2a8-42de4e84b4db
edcf6bf0-5a1a-41c6-ac00-56a63327adbb	587397a4-894b-4a60-ae95-af17f7c71a70
c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d	73b3ef2e-becd-45c3-8f7e-9531a83e53fc
f058e2d3-bcd6-46c2-92c0-3e0aa4e71757	b8ed5474-d7e0-4144-b11d-3cf63cf9440d
f65faea9-66ad-40ce-8488-753078555a3f	587397a4-894b-4a60-ae95-af17f7c71a70
c2380f71-ff90-46f3-8438-49f91616f6a6	7d89d743-b98b-4151-b76f-0fe067629565
f4e3291c-758b-47f4-a1d8-75680a84be0f	0b71b641-82cb-4c00-8c51-5c522188a59e
f68c63a1-4807-47c5-a6d0-063630b700f6	cffee650-43db-4f7f-b479-d0b439e61287
f83ba309-b71e-455b-8354-5486ab7a76b7	54b44371-4415-4b45-abd7-63b86369973c
f83ba309-b71e-455b-8354-5486ab7a76b7	cffee650-43db-4f7f-b479-d0b439e61287
32e48d76-6004-4a1c-bb22-27ee66e2d672	c5dd911f-43d7-4ccd-82cc-afcb1d65602e
22b88adb-3e66-4b08-86d5-c6e6ecc7399f	4ec986fc-f097-4405-bbfa-7026fbe4de16
8638f31d-dd3a-41df-8571-bcb665c48554	73b3ef2e-becd-45c3-8f7e-9531a83e53fc
e43a754b-648f-460b-88c6-0d3825b1fa76	21cf5e55-8d00-4266-8327-abc94f05e72b
94ad2b6d-975f-4746-9a3b-33c32b34a13f	e785a4ed-af08-4b78-a450-11c1efa76288
a9ca51e9-864d-4d17-b647-ff17ea2c02e6	ded06937-76c4-4516-864e-f14d83081e99
9a21bb80-9e6f-466d-bee2-96306d08b0ae	cf515827-5393-4ff0-8b0b-17e376c00f3f
69a94b81-7ed0-428b-a3c9-679a34e47e0a	fbe60aa6-8d84-426d-adb8-c40638042c9c
e64236c3-e2ea-45a1-8a28-c9c596267c97	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
e64236c3-e2ea-45a1-8a28-c9c596267c97	42b1f177-628c-4a87-bf8f-a11932fffa23
e64236c3-e2ea-45a1-8a28-c9c596267c97	393058cb-870a-45ee-ab55-5e47c84d10b2
e64236c3-e2ea-45a1-8a28-c9c596267c97	ab832241-5ec1-431f-ae8c-6431218c18e2
e64236c3-e2ea-45a1-8a28-c9c596267c97	7e598367-12d8-46b1-9817-21679731637c
04690b0c-f46f-4bb7-8932-1d3ff3879486	c5dd911f-43d7-4ccd-82cc-afcb1d65602e
ae4d3b3b-f1b9-456d-89b3-84dfdafad01f	deaf2b37-4a4d-4923-873b-1299b4464782
58494593-79cc-411e-ad7f-2b52a7fc6095	cffee650-43db-4f7f-b479-d0b439e61287
47f982fc-369e-473c-9ab0-8096bae012e0	89f6ed60-6465-453c-b485-cc80b9d43e17
9b90fa62-a329-4089-b71f-f9a70c27ac03	cbf427e0-2f1c-422e-b7f1-f6263e1bd831
3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	c78f81ae-bc4e-44f8-9e4b-63a16362ff01
22891480-943a-4cb1-a417-c760205d86bd	7dce6776-3a71-45b5-aa9b-b33ff44e84be
22891480-943a-4cb1-a417-c760205d86bd	11fbd50d-c9f4-4ec0-aa62-58c200b468a3
4a858055-f275-460b-a349-222cc0ca28d7	11fbd50d-c9f4-4ec0-aa62-58c200b468a3
394a96f5-4e6a-4a64-8a13-0184d37063fd	5e280afb-da17-4629-8fea-40d7880580d6
74a8a447-3f16-43a9-b29b-84d2e5d86a88	d35a424b-d4d1-40a6-aed4-7ba6a1167143
f6a2c559-1f7d-4f45-adf1-8c76b2faf147	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5
f6a2c559-1f7d-4f45-adf1-8c76b2faf147	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e
f1f5bad2-246a-446e-877d-e5b956ae77fa	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c
4fb9a210-dd6d-43a4-9c64-bf56bd677a51	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c
a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	cf515827-5393-4ff0-8b0b-17e376c00f3f
2c1ea78c-8ca7-4583-aad4-a6724bc81df1	6eff3b68-53f2-4b85-a10d-dd9559f3879e
5926aed9-9d4d-42b1-9fa8-fd82d5706412	52c95b56-9b87-4b69-b51d-41dd2fca8512
b0956325-1cdb-425f-a9a6-afb752a5485d	7dce6776-3a71-45b5-aa9b-b33ff44e84be
ba85e730-6df5-4e9f-aef0-96577fab369a	ce5137bc-0f9e-49a1-9e27-0aec555f069d
5fdcd179-a83a-4080-a33c-905e3db55dd1	c64363a0-0a95-46c9-a914-7ec718f016cf
5fa57fdf-7ba0-4063-93cf-378d7b8267a9	eb98825a-95f0-4de3-9c74-71c32d9580a7
ad789a69-05f1-43be-8084-cd6a14e6202a	89f6ed60-6465-453c-b485-cc80b9d43e17
555bdc44-a24e-48fc-aa5e-892e666781a5	23f96a10-3b56-4dba-a5a1-bc8e4c071869
9db2d190-6068-4c34-9e5c-36e9994274aa	23f96a10-3b56-4dba-a5a1-bc8e4c071869
e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	aeef2474-4ad6-4585-83cf-9ec480c8d0a9
350475ef-1d3b-4823-8c76-7f190a27f87e	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e
d44fa577-b407-4d90-972b-b3d39ab4b93d	7dce6776-3a71-45b5-aa9b-b33ff44e84be
899344cc-f75d-463b-b1a5-3ecbee85a79d	f270d266-40ec-4e7d-87c2-a39d000c2a0b
14e253d0-ff9a-4614-845a-bd91c1d91ad3	9e8d1ad1-f50e-40b2-a434-18d009de310e
79e610f9-4c9f-4234-9a37-d8e1d11a6af2	c94019d0-8741-4f4b-a6ab-0c2439d77bde
79e610f9-4c9f-4234-9a37-d8e1d11a6af2	64847330-57d7-4e0c-bb60-f1b74673512c
79e610f9-4c9f-4234-9a37-d8e1d11a6af2	3b0407f2-8d98-4a28-b3da-e6fa5ded7728
e1888724-e770-43fe-aa8a-3484bc05a37a	b54d4881-1bc6-4134-9bac-2bc983148ed5
8310c913-dcfd-432e-b330-ee4bb33b7d3e	cf515827-5393-4ff0-8b0b-17e376c00f3f
\.


--
-- Data for Name: course_relations; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.course_relations (id, relation_type, fk_course_a_id, fk_course_b_id) FROM stdin;
\.


--
-- Data for Name: course_timeslots; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.course_timeslots (id, weekday, fk_course_id, fk_timeslot_id) FROM stdin;
6c654568-6cfc-496d-a764-a79f3d8831b7	MONDAY	b8afdaa4-2e1c-47d8-8d15-d1019fa460d5	58845959-0984-4136-a733-b75f5c9057b9
b6bcdfa9-6b8b-4e70-a6c7-e2a26769310a	THURSDAY	426c7cf6-bad8-4360-bb83-33e2afe1fcb6	6f1475e5-0d8a-47c7-855f-ccff802aff85
0411312a-3237-4205-ad4f-a1a41f367375	FRIDAY	426c7cf6-bad8-4360-bb83-33e2afe1fcb6	c33e01ef-be92-474a-9e43-0543649735d5
0af37349-a7eb-43c3-a0e4-c4d78c410936	FRIDAY	6eeec8d4-efd9-4d38-8643-4ac432801489	d54b130a-28a8-4bb1-9030-c474369c2205
44f72ce1-c68f-42ba-b201-b39791b1d319	FRIDAY	6eeec8d4-efd9-4d38-8643-4ac432801489	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
aab9e88f-f423-441f-a83e-48f04619964f	FRIDAY	e63a3e7d-406b-4907-b08d-9256af86a2a6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
06c643ec-1d2b-4c05-91fc-34f4dd0f1c02	FRIDAY	e63a3e7d-406b-4907-b08d-9256af86a2a6	c33e01ef-be92-474a-9e43-0543649735d5
86333e78-e669-4a52-a063-210b170d1d31	TUESDAY	6f115cf8-f0f9-4cce-84ef-5e4f5bb11ba5	c33e01ef-be92-474a-9e43-0543649735d5
4f43d88d-116f-4c3f-8636-57d8bf498350	TUESDAY	bc4e93ce-352f-493d-9089-c72c1eeaf8e4	6f1475e5-0d8a-47c7-855f-ccff802aff85
ec2ef551-cc52-448f-92f7-bdbb2e458879	FRIDAY	279b0444-5ebc-42ce-a01a-d2e4d39b9874	6f1475e5-0d8a-47c7-855f-ccff802aff85
965429b6-3453-4fcb-9168-ac983072bde8	FRIDAY	279b0444-5ebc-42ce-a01a-d2e4d39b9874	58845959-0984-4136-a733-b75f5c9057b9
2450add9-1912-4d8f-8d84-f5cf9137a231	WEDNESDAY	19c4c340-d56b-448a-a9d3-583daa46fefc	6f1475e5-0d8a-47c7-855f-ccff802aff85
d20230da-5cec-4514-b146-01d18f015d90	WEDNESDAY	19c4c340-d56b-448a-a9d3-583daa46fefc	c33e01ef-be92-474a-9e43-0543649735d5
375bd01b-e57d-4321-a432-f94efcdbcd06	THURSDAY	653df30a-8013-4f51-83dd-4d94e429b18d	6f1475e5-0d8a-47c7-855f-ccff802aff85
c060becb-61b1-4687-a990-70b0c375b5f4	THURSDAY	b65a8082-6f76-4f30-b9bc-142d01701d8b	6f1475e5-0d8a-47c7-855f-ccff802aff85
15144195-df5f-4b31-bd80-9d1e535235ed	THURSDAY	b65a8082-6f76-4f30-b9bc-142d01701d8b	58845959-0984-4136-a733-b75f5c9057b9
b1c24727-aae2-4d5a-a1eb-ce8ae780017b	WEDNESDAY	cdcb904d-32c2-4f88-9af8-b7ca23b5bc47	5f76a266-8d18-4da8-9732-b63d4d8f24a0
28fbe367-a726-4e1b-81aa-cf7f17f8c32d	WEDNESDAY	cdcb904d-32c2-4f88-9af8-b7ca23b5bc47	d54b130a-28a8-4bb1-9030-c474369c2205
2efc94c8-4d55-457a-8c2a-fd4b71233036	THURSDAY	97baa833-1f10-4349-85a3-c179959af8ce	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d4f12aef-7f05-47dc-baf3-156b729cc9b5	THURSDAY	97baa833-1f10-4349-85a3-c179959af8ce	d54b130a-28a8-4bb1-9030-c474369c2205
ddf629dd-2b1e-4377-833d-876740efd200	MONDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	440c17ed-5262-4800-aa19-38e5285330c3
79ea344b-8b0b-4c20-8c23-9cc2e109f014	TUESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	440c17ed-5262-4800-aa19-38e5285330c3
6013981e-ba6c-47b6-8baa-8897bdfe02bd	WEDNESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	440c17ed-5262-4800-aa19-38e5285330c3
51a76280-9101-49a8-a483-0ed9a508ee94	THURSDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	440c17ed-5262-4800-aa19-38e5285330c3
756e9e73-d2fb-4f7c-843a-e72a29f79e51	FRIDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	440c17ed-5262-4800-aa19-38e5285330c3
bfba337e-d5ed-4b9d-a460-3115bdf39a5e	MONDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d3cbdd15-625d-48a7-9eac-54a08ff92935	TUESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	5f76a266-8d18-4da8-9732-b63d4d8f24a0
af103dcd-7464-4552-8f45-9226190bcc33	WEDNESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8d4d30ac-d44d-4e83-bcc3-b6ddd2d37a51	THURSDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c44f16bc-b14b-413c-969f-58f801a1561b	FRIDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4bb1421d-e5fe-4bbd-9e5b-bdc11dc6217a	MONDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	d54b130a-28a8-4bb1-9030-c474369c2205
9bd07e3b-8602-4f60-b1d1-55be42a3247d	TUESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	d54b130a-28a8-4bb1-9030-c474369c2205
ee503101-4186-44f4-8f05-54ac9e1f2ac1	WEDNESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	d54b130a-28a8-4bb1-9030-c474369c2205
85d349f3-a201-4bae-bdc4-8722471e624d	THURSDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	d54b130a-28a8-4bb1-9030-c474369c2205
ac6a6774-7094-48a9-a48a-88f7009487f0	FRIDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	d54b130a-28a8-4bb1-9030-c474369c2205
3b99ab04-165e-4123-be8b-96407383f8ff	WEDNESDAY	d1e3dcb1-fa7c-4355-b42f-90c8378679cb	d54b130a-28a8-4bb1-9030-c474369c2205
e3a5c5e1-b778-4363-8d51-fadc080ccfd6	WEDNESDAY	d1e3dcb1-fa7c-4355-b42f-90c8378679cb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
98250eb3-b51b-450f-bd1b-80bbbff719a0	THURSDAY	aedd23d3-b98f-4972-aa2e-acdcb8f2c5df	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d054d793-6808-46e5-917d-8243fb31a0d4	THURSDAY	aedd23d3-b98f-4972-aa2e-acdcb8f2c5df	d54b130a-28a8-4bb1-9030-c474369c2205
2d00c6ba-e7b3-4558-b48e-a98e77f3133a	FRIDAY	e70ced69-5a9d-4ac5-b110-77317e06f368	5f76a266-8d18-4da8-9732-b63d4d8f24a0
51e3b6cb-2563-44e0-a62c-bc251c0e135a	FRIDAY	e70ced69-5a9d-4ac5-b110-77317e06f368	d54b130a-28a8-4bb1-9030-c474369c2205
2bb918b7-4357-48f9-94b5-578098e98bc7	FRIDAY	1e896415-6ebe-443f-b048-30725be095c3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cbd51f17-e4ca-446a-bbce-80b0b7313963	FRIDAY	1e896415-6ebe-443f-b048-30725be095c3	d54b130a-28a8-4bb1-9030-c474369c2205
262c71da-185f-4687-b07b-9885d78b742e	WEDNESDAY	24825786-1517-44ed-a97a-843f2ab2e5cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ee4046f3-0888-47f1-9855-6f307538b3d0	FRIDAY	5885d8cd-49c6-46e6-8a89-36b55de54829	c33e01ef-be92-474a-9e43-0543649735d5
64b38d3c-5ef4-4abc-9276-1924021afd49	WEDNESDAY	af9e38a7-5fea-42ef-b6c1-cd56d0161988	c33e01ef-be92-474a-9e43-0543649735d5
91750f72-06a1-4666-9260-78c974853633	WEDNESDAY	24825786-1517-44ed-a97a-843f2ab2e5cf	c33e01ef-be92-474a-9e43-0543649735d5
0f874423-fdca-4e01-a677-4157567e28e1	SATURDAY	db0c3159-a02b-4e36-86e4-eb3cd4d210ee	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0444929a-c1fd-4033-9412-6f2963a07eda	SATURDAY	db0c3159-a02b-4e36-86e4-eb3cd4d210ee	d54b130a-28a8-4bb1-9030-c474369c2205
741d26d8-f4c2-4244-967f-bfe0f3e3dbcb	WEDNESDAY	04fcd6fc-890b-421d-8137-16fe13250a42	5f76a266-8d18-4da8-9732-b63d4d8f24a0
dfa6529f-cdeb-477d-8e05-453672d012e4	WEDNESDAY	04fcd6fc-890b-421d-8137-16fe13250a42	c33e01ef-be92-474a-9e43-0543649735d5
1cfefc6e-64be-42b5-84bd-f50723088011	WEDNESDAY	04fcd6fc-890b-421d-8137-16fe13250a42	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8d3bd066-64f6-4d46-ab69-356cbf96cb46	WEDNESDAY	04fcd6fc-890b-421d-8137-16fe13250a42	d54b130a-28a8-4bb1-9030-c474369c2205
4edfe151-400e-43de-a022-4cfc5a110208	TUESDAY	a7fd852d-5a48-40c2-9715-afeee84a39e0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6859fc1a-f0c1-43be-8f3d-76e46f2d9dac	TUESDAY	a7fd852d-5a48-40c2-9715-afeee84a39e0	d54b130a-28a8-4bb1-9030-c474369c2205
abe79732-134e-43d9-983c-be287e7ca2ce	MONDAY	9f611bd8-64ed-4cad-9d07-a8fc6184104d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a8480113-96e3-4c27-80dd-9a03733d5683	MONDAY	9f611bd8-64ed-4cad-9d07-a8fc6184104d	d54b130a-28a8-4bb1-9030-c474369c2205
d91aa60b-1dec-41c5-bbf4-ab6a1a10c8ee	WEDNESDAY	05155d0d-89da-4c55-b92c-94a1387ec741	c33e01ef-be92-474a-9e43-0543649735d5
3c6a44aa-4c8a-452c-8aeb-eedeb6a99c98	WEDNESDAY	05155d0d-89da-4c55-b92c-94a1387ec741	6f1475e5-0d8a-47c7-855f-ccff802aff85
ea3577c7-1e45-4b07-8704-afb2696dbf5e	TUESDAY	6b49d198-e1bd-4710-b4e8-d9befa664a83	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6f6163fa-d264-4775-a14c-93b127019bef	TUESDAY	6b49d198-e1bd-4710-b4e8-d9befa664a83	c33e01ef-be92-474a-9e43-0543649735d5
72ec5591-d899-4834-a352-749240846442	FRIDAY	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c	440c17ed-5262-4800-aa19-38e5285330c3
a1060997-2d6b-4a48-8028-e3846da57745	FRIDAY	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b1883126-8178-496d-8b22-45267a304004	THURSDAY	03019641-317d-4b74-adaa-1580ff4c99bc	58845959-0984-4136-a733-b75f5c9057b9
773ae03a-8afb-4476-94bf-64bd5d0ab218	TUESDAY	af9e38a7-5fea-42ef-b6c1-cd56d0161988	440c17ed-5262-4800-aa19-38e5285330c3
66d95dc1-8990-446a-9392-c133b349aeb3	THURSDAY	3e4285d4-8e01-4483-9219-83079f9d3a77	5f76a266-8d18-4da8-9732-b63d4d8f24a0
232f5b31-4675-4e42-841e-c476b79b280c	THURSDAY	3e4285d4-8e01-4483-9219-83079f9d3a77	d54b130a-28a8-4bb1-9030-c474369c2205
ee42f84b-366f-4635-a02f-2a9a3245856c	THURSDAY	3e4285d4-8e01-4483-9219-83079f9d3a77	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e48939a4-ecbd-41b8-875c-07daeb11ccce	MONDAY	1a931c05-4408-4eb6-afdc-905ebda9edc1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4ed94702-7b11-4570-bc90-a704b7d5c9df	MONDAY	1a931c05-4408-4eb6-afdc-905ebda9edc1	d54b130a-28a8-4bb1-9030-c474369c2205
40feaf5f-214a-411c-ab0f-3776fed86f5b	TUESDAY	1a931c05-4408-4eb6-afdc-905ebda9edc1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
15650c19-093b-4120-86ed-a557644f7fba	TUESDAY	1a931c05-4408-4eb6-afdc-905ebda9edc1	d54b130a-28a8-4bb1-9030-c474369c2205
c05d23ba-be5f-436a-816e-f5836e5ffde3	THURSDAY	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7	6f1475e5-0d8a-47c7-855f-ccff802aff85
2379bba1-bf28-42b2-a352-a558d621b5b3	THURSDAY	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7	c33e01ef-be92-474a-9e43-0543649735d5
2a6ac923-54b8-40a1-b272-377509159eb4	MONDAY	5e445afa-73c0-4500-a0d8-697acd7bf58e	c33e01ef-be92-474a-9e43-0543649735d5
09c3cbfe-2a22-4df2-8fb8-2549cad45a94	TUESDAY	5e445afa-73c0-4500-a0d8-697acd7bf58e	c33e01ef-be92-474a-9e43-0543649735d5
c0ada2fe-8e3b-4d8f-9604-5dc16fb0d6b3	THURSDAY	e738acf7-a61b-489a-a6f9-bda344038174	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4617312c-d715-4315-8ae8-93b7220e4d77	TUESDAY	e738acf7-a61b-489a-a6f9-bda344038174	d54b130a-28a8-4bb1-9030-c474369c2205
bdb78f68-eced-46ba-a2d2-c6e226ef505d	TUESDAY	68dd3eea-cea2-4e54-b2e3-2179938239ca	440c17ed-5262-4800-aa19-38e5285330c3
665a2e2e-b669-4756-a656-35eb72dee6cb	TUESDAY	7d48e39c-8f0c-4db0-bd68-3c4b83e2b8d1	6f1475e5-0d8a-47c7-855f-ccff802aff85
9c0f77d5-55c9-4a39-838f-5bd100f8ae47	TUESDAY	3bcee379-cb47-43a8-84f6-933a0b3de0a4	6f1475e5-0d8a-47c7-855f-ccff802aff85
5f4e2063-7dd7-48d1-acda-2090b727bf6e	TUESDAY	6bbb9b79-33ba-422f-9964-a8435805fcbd	6f1475e5-0d8a-47c7-855f-ccff802aff85
11c9b9c3-6463-405f-bc8c-2ad7a5d95b15	MONDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
29a00b47-8d6e-4693-b83e-10daa85e36e5	TUESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
af1b889b-2da0-4bdb-a472-1bcc94cef55a	WEDNESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8764bb8d-65d5-43cd-9121-686ecb8de5ac	THURSDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9a204e42-a6e3-4eec-b519-32d20186026b	FRIDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
27acd8b7-027a-4752-b3c6-5541afc3e59f	MONDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	c33e01ef-be92-474a-9e43-0543649735d5
dae1efd6-6b1f-4b13-9db2-e3fb9abeb53c	TUESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	c33e01ef-be92-474a-9e43-0543649735d5
afbd4bd3-d3a7-4dcf-8049-2e272f63fa94	WEDNESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	c33e01ef-be92-474a-9e43-0543649735d5
8597477f-0243-4488-95ba-483b0356cfd0	THURSDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	c33e01ef-be92-474a-9e43-0543649735d5
ded4ec58-9c8f-4031-982d-6bb9b51410d0	FRIDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	c33e01ef-be92-474a-9e43-0543649735d5
db9c3973-c84a-4a38-9d40-335a20cfeee9	MONDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	6f1475e5-0d8a-47c7-855f-ccff802aff85
c8ff4675-a6ed-4088-8954-690c18f9c933	TUESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	6f1475e5-0d8a-47c7-855f-ccff802aff85
fe38e53d-fa73-468f-ba2c-b3a7d82afdef	WEDNESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	6f1475e5-0d8a-47c7-855f-ccff802aff85
ca1a6be9-87e9-4172-90ac-6ab7b03e4820	THURSDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	6f1475e5-0d8a-47c7-855f-ccff802aff85
8e25eb41-a52e-408b-9692-f0f8b2a691ec	FRIDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	6f1475e5-0d8a-47c7-855f-ccff802aff85
4ec72c0b-eb77-43ff-a05b-f33782b74045	MONDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	58845959-0984-4136-a733-b75f5c9057b9
45764266-afdb-4515-8d64-e435618d5593	TUESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	58845959-0984-4136-a733-b75f5c9057b9
c0e7ce51-13fc-4817-bb5f-5d2368baf082	WEDNESDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	58845959-0984-4136-a733-b75f5c9057b9
dd23b5dc-ee01-4fcd-b63e-c912641e095e	THURSDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	58845959-0984-4136-a733-b75f5c9057b9
c5d873ff-4c38-4115-be58-4eab4eb019f2	FRIDAY	4fb9a210-dd6d-43a4-9c64-bf56bd677a51	58845959-0984-4136-a733-b75f5c9057b9
e6c5fb52-d6e2-4fee-9b66-97f50ec744f2	TUESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	58845959-0984-4136-a733-b75f5c9057b9
df0a843f-56ac-48b1-89d8-7fe2b47c23bd	WEDNESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	58845959-0984-4136-a733-b75f5c9057b9
11540b9b-cf4d-4556-b12e-3c883add6bf2	THURSDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	58845959-0984-4136-a733-b75f5c9057b9
a1523089-5c6f-470b-8ba1-02a41e7ccc4a	FRIDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	58845959-0984-4136-a733-b75f5c9057b9
2d050d20-d9b8-4c95-b19c-a493939888c7	TUESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	d54b130a-28a8-4bb1-9030-c474369c2205
042ec543-7a7f-4734-90b7-adab3d7fac4d	WEDNESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	d54b130a-28a8-4bb1-9030-c474369c2205
8487fc15-5a40-4b2a-a5b1-ffbc09df8ab2	THURSDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	d54b130a-28a8-4bb1-9030-c474369c2205
fa01e22b-3468-4896-be81-7d7dec485f10	FRIDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	d54b130a-28a8-4bb1-9030-c474369c2205
e6c3279c-fb22-47ca-a1cf-bff6ce3460af	MONDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
13913e80-54a8-4185-b760-a8291d5aed70	TUESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5710909d-a5a7-483b-87f1-55359e025182	WEDNESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
46bc8d07-1eea-4380-b758-98d47557e3fb	THURSDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cd761d6b-4ae3-4ff5-a9db-a3399e44b3a1	FRIDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7b4fc062-82ad-43f1-a79b-e789a79a91f7	MONDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	c33e01ef-be92-474a-9e43-0543649735d5
51e7c702-0165-4d6a-b934-b4b8b6dd4ecf	TUESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	c33e01ef-be92-474a-9e43-0543649735d5
56c20bb4-5df3-43f0-a650-cee39533dbf0	WEDNESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	c33e01ef-be92-474a-9e43-0543649735d5
46fd7498-f5d5-4466-b587-68ee4c10b697	THURSDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	c33e01ef-be92-474a-9e43-0543649735d5
c18561c9-57c3-41e8-bdf0-b09b3d3d67e2	FRIDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	c33e01ef-be92-474a-9e43-0543649735d5
4d2a34be-bf86-4458-a26e-feb55d019e46	MONDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	6f1475e5-0d8a-47c7-855f-ccff802aff85
75afc3c8-15a5-458a-bf6c-a41c2f7410f0	TUESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	6f1475e5-0d8a-47c7-855f-ccff802aff85
007a53a0-44f4-44c0-a28d-1698d65e8483	WEDNESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	6f1475e5-0d8a-47c7-855f-ccff802aff85
c41eb53c-fd6f-451a-bcd6-5650de38c97d	THURSDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	6f1475e5-0d8a-47c7-855f-ccff802aff85
734bb1e1-d0be-48e5-8547-a0eef1285313	FRIDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	6f1475e5-0d8a-47c7-855f-ccff802aff85
52aec315-07f9-421c-a4ca-584ae183217e	MONDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	58845959-0984-4136-a733-b75f5c9057b9
95fd37a5-4e3e-4409-af83-d6b52b95890f	TUESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	58845959-0984-4136-a733-b75f5c9057b9
56036fff-a652-405b-b9aa-f03a40f02cc8	WEDNESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	58845959-0984-4136-a733-b75f5c9057b9
a7d454f3-8aa4-4274-af2c-65aa006cf945	THURSDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	58845959-0984-4136-a733-b75f5c9057b9
abe62fff-9327-4185-b873-9ec4aa11cddf	FRIDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	58845959-0984-4136-a733-b75f5c9057b9
668ff880-9b5d-4bd0-88f1-5571a3cace1f	MONDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	440c17ed-5262-4800-aa19-38e5285330c3
19222b8f-3b05-44d1-9c7f-91a07f9b22d0	TUESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	440c17ed-5262-4800-aa19-38e5285330c3
a415abbc-0641-458a-9ad0-d92596467279	WEDNESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	440c17ed-5262-4800-aa19-38e5285330c3
a46153cf-3cc5-4beb-a124-0fee587e7b61	THURSDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	440c17ed-5262-4800-aa19-38e5285330c3
53bfa0d1-985e-46f5-9873-b50974528238	FRIDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	440c17ed-5262-4800-aa19-38e5285330c3
2af700ae-543f-439f-a29b-c6b3c19fa07e	MONDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0e3443e1-078c-4b05-bc5f-04822b55a056	TUESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b2158a4c-b855-4596-9b73-ec8c24e9a498	WEDNESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
53c320ad-3ee6-40ab-a9cf-322dd3398321	THURSDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6549c3d5-90d1-4822-b08b-9a1c68b43ced	FRIDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0d87f505-add4-4ea3-9764-935e34848785	MONDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	d54b130a-28a8-4bb1-9030-c474369c2205
28d8ce92-49ee-4113-b9d2-370c95addb01	TUESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	d54b130a-28a8-4bb1-9030-c474369c2205
3b876ef6-596e-4bab-a452-4ff051813016	WEDNESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	d54b130a-28a8-4bb1-9030-c474369c2205
b4d472e8-7de4-4864-895b-90fe5dd11f8b	THURSDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	d54b130a-28a8-4bb1-9030-c474369c2205
c97b6834-f9de-476b-b43f-7c0058e8df14	FRIDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	d54b130a-28a8-4bb1-9030-c474369c2205
72af5f79-8cd8-45a8-abc0-1389b5bc76b2	MONDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
372aa606-d02c-4654-a67e-73218087ccf0	TUESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8bd22d19-6d48-4e0a-bc20-44759840d994	WEDNESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3a645677-2ded-4899-9b33-7197e570696f	THURSDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b22a1a1f-3685-43ae-9cc2-47eabccdb63a	FRIDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a42dec7b-19f4-48b5-96e7-838b735f8431	MONDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	c33e01ef-be92-474a-9e43-0543649735d5
69737f01-bc42-4296-bc7a-c064387a64e2	TUESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	c33e01ef-be92-474a-9e43-0543649735d5
101eb605-e350-41f5-a0fc-2faa25b9c8ca	WEDNESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	c33e01ef-be92-474a-9e43-0543649735d5
4387e2aa-a694-480b-8da1-67fc86617b3e	THURSDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	c33e01ef-be92-474a-9e43-0543649735d5
67b028a8-8f09-4cd3-9d5b-06b0d57117d4	FRIDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	c33e01ef-be92-474a-9e43-0543649735d5
865c96d5-34ae-4f58-9df3-3098cb6b849f	MONDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	6f1475e5-0d8a-47c7-855f-ccff802aff85
803791e1-4092-40d6-8f13-a2587337a1d0	TUESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	6f1475e5-0d8a-47c7-855f-ccff802aff85
a0f40819-5835-4d8a-993b-dc97d70e3e05	WEDNESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	6f1475e5-0d8a-47c7-855f-ccff802aff85
2a031316-82d5-43d8-8117-3c65994483bb	THURSDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	6f1475e5-0d8a-47c7-855f-ccff802aff85
666c1e25-4e4a-4380-bf07-64d7eeca2c1a	FRIDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	6f1475e5-0d8a-47c7-855f-ccff802aff85
3d50b4fa-3354-47f0-83bc-d62c30f6d83b	MONDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	58845959-0984-4136-a733-b75f5c9057b9
a3502637-f59c-466b-9d97-ff38f45a3db0	TUESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	58845959-0984-4136-a733-b75f5c9057b9
beae566e-3d6e-4e9e-a0aa-42221293344a	WEDNESDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	58845959-0984-4136-a733-b75f5c9057b9
f4fa00ee-6674-4f89-bfc6-f5ee71b787fc	THURSDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	58845959-0984-4136-a733-b75f5c9057b9
61fa779b-5cb6-4280-8fa9-008afe4c559b	FRIDAY	79e610f9-4c9f-4234-9a37-d8e1d11a6af2	58845959-0984-4136-a733-b75f5c9057b9
2964b9ac-b99e-46a1-bbdb-cb4b3816ccf7	MONDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	440c17ed-5262-4800-aa19-38e5285330c3
fe5bf1a5-64fc-47b4-825a-46ea5085eaa9	TUESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	440c17ed-5262-4800-aa19-38e5285330c3
c2b6bbb9-abf0-484f-9876-4fd5402c6171	WEDNESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	440c17ed-5262-4800-aa19-38e5285330c3
a7cbc5f2-52bf-444b-855a-4589dd8d82c6	THURSDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	440c17ed-5262-4800-aa19-38e5285330c3
35f7f3dd-3dc2-42b2-9eb2-d91d6464f6f3	FRIDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	440c17ed-5262-4800-aa19-38e5285330c3
6517c78a-de80-4d70-b630-7355acbbc394	MONDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
846e8382-9b77-42ef-8d23-c77ffe0b5da1	TUESDAY	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9	58845959-0984-4136-a733-b75f5c9057b9
efe03a74-6b2f-4bb7-9447-a0808fd10ec3	THURSDAY	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9	58845959-0984-4136-a733-b75f5c9057b9
68b87248-22d3-4efd-92d7-ecef04ee3100	THURSDAY	8b90882c-0ce3-48a5-8fae-24268e66fed6	c33e01ef-be92-474a-9e43-0543649735d5
47718146-f504-4820-9898-0412f8cbd9e5	THURSDAY	8b90882c-0ce3-48a5-8fae-24268e66fed6	6f1475e5-0d8a-47c7-855f-ccff802aff85
87e2d603-e16b-4bec-949c-73d17059e5ac	TUESDAY	71e325fb-01c5-44fd-a0de-898927b39357	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
31a0681f-2424-4784-8738-83f3ede78467	WEDNESDAY	71e325fb-01c5-44fd-a0de-898927b39357	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e259f8e2-80d2-4704-8230-2091c544c4bd	THURSDAY	6f0d248f-ea9c-4f98-8b75-c7569aa198e1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
470e6dee-0b4b-4dbd-b9e0-824b6549b733	MONDAY	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8aacbb33-4bcc-4aeb-b87e-cbf828037c51	WEDNESDAY	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
c8221b6c-beed-4857-9865-57e6d8cc56b1	TUESDAY	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
46e2c755-1b8c-4af7-830c-9177a8d0cbde	TUESDAY	c0aa58be-47d7-4f72-b017-9125856035e2	6f1475e5-0d8a-47c7-855f-ccff802aff85
2e58bf4e-913f-48c9-b5df-114c0563ecbc	MONDAY	c0aa58be-47d7-4f72-b017-9125856035e2	6f1475e5-0d8a-47c7-855f-ccff802aff85
a681b958-ac31-4f6b-80f1-cbe7e732da2d	WEDNESDAY	a666d323-152f-48af-be42-f59d6cc27deb	c33e01ef-be92-474a-9e43-0543649735d5
7930ceef-3bd0-45f8-9dbc-388469f6a6f3	TUESDAY	7c23bee8-0b73-4720-af87-28c6c180de6a	58845959-0984-4136-a733-b75f5c9057b9
eb886994-967a-4a97-8442-755028e812ee	FRIDAY	7d1eb5d5-dd84-4821-836b-ef6a694965f2	c33e01ef-be92-474a-9e43-0543649735d5
36ea9aa9-5410-4dc1-b938-4971fb76d204	FRIDAY	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc	6f1475e5-0d8a-47c7-855f-ccff802aff85
a3b119ca-6195-43e8-823f-2afe1e5f2ed7	FRIDAY	aea1a914-5bde-4a5c-92c1-27c9794cf738	58845959-0984-4136-a733-b75f5c9057b9
ef49276d-0fea-4ea0-ad71-ad835b638520	WEDNESDAY	a8bdcf92-25a5-49f5-b2d7-57c83ff424f4	d54b130a-28a8-4bb1-9030-c474369c2205
08d45882-2e8d-4727-a983-469fee9a957c	WEDNESDAY	1e7550cf-39bf-4394-a631-6279e4be4997	58845959-0984-4136-a733-b75f5c9057b9
a5700e0e-ea1c-4764-ac0d-51db77460d31	MONDAY	009eb4ee-d905-4f04-bb23-7fb156ee569e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
63cae360-b088-4a3f-a44f-02d3cba0d367	MONDAY	a4dd8404-9c4a-4046-b857-609f4f6bda92	d54b130a-28a8-4bb1-9030-c474369c2205
46511c9c-7d1a-44f8-ba1c-95ddaf27be63	MONDAY	c7955389-a368-4f9a-9a48-cd015c38d841	c33e01ef-be92-474a-9e43-0543649735d5
45f02fbd-8d73-4130-95b4-f75ac461ebcf	FRIDAY	a79d088c-d445-4892-90d5-065cacb9446d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
38396ce6-79f1-4fd8-aa92-2d8046f98478	FRIDAY	737ba05d-dffe-4973-a606-f91a3bb8c0f9	d54b130a-28a8-4bb1-9030-c474369c2205
1500d44d-7d69-4b20-9dd1-9699a2f3d3de	SATURDAY	7ab751ea-9048-417d-b0fa-755328a7f385	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1d7dc636-39c7-404a-bc5a-04d510bc8dcd	WEDNESDAY	b4bebd85-6f9b-4ba2-8ed5-2e4086d6f699	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3cc3c3ec-4823-4587-a5c9-47c748f6c62f	WEDNESDAY	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2	c33e01ef-be92-474a-9e43-0543649735d5
5d6fbeb4-8548-4514-8616-e43f65cf666a	THURSDAY	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2	440c17ed-5262-4800-aa19-38e5285330c3
abfdbd78-af81-4fde-b263-bf0809aa103f	MONDAY	77f1a128-ea8f-466e-8c0d-998c2280c811	440c17ed-5262-4800-aa19-38e5285330c3
bcf6e051-e6fb-41fe-8e10-f16357a36f4d	TUESDAY	77f1a128-ea8f-466e-8c0d-998c2280c811	440c17ed-5262-4800-aa19-38e5285330c3
58bbb92f-9468-49f5-a437-240a0837ec2d	MONDAY	78301a7f-a1d9-494b-8c10-8b11baa5976e	d54b130a-28a8-4bb1-9030-c474369c2205
bdbd0013-78b1-4ec2-9bf0-8d7c9dfe33ca	MONDAY	df82acfb-e4dc-4615-9b1f-c41dcbe4803a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d4541afd-a5b4-4717-9b89-34f8e7db8def	THURSDAY	613282ae-c93a-4cc8-b6c3-fa73f8a5326c	58845959-0984-4136-a733-b75f5c9057b9
d737d28e-6b9a-4b9c-ac70-4af38d4856ea	MONDAY	613282ae-c93a-4cc8-b6c3-fa73f8a5326c	58845959-0984-4136-a733-b75f5c9057b9
2da1a290-2435-4cf6-93ea-8e9feb2ab637	WEDNESDAY	60c9ed1a-a37e-47cd-986f-8b46d91ffe16	d54b130a-28a8-4bb1-9030-c474369c2205
9e8c562b-b6b1-4c9c-a110-95b36ebd00fb	TUESDAY	8d5e74b8-7910-4ee3-b424-3697b919da65	6f1475e5-0d8a-47c7-855f-ccff802aff85
88b01ce4-14f1-4cd9-bfde-2a67147238b3	TUESDAY	8d5e74b8-7910-4ee3-b424-3697b919da65	58845959-0984-4136-a733-b75f5c9057b9
75b86c0a-2eea-4247-b62d-603b33a9d5a8	SUNDAY	fb46ae33-946a-46d4-a4f2-29df8b040cff	6f1475e5-0d8a-47c7-855f-ccff802aff85
e0534fb6-a9d4-4972-b1f4-6dc4a5a291ae	SUNDAY	fb46ae33-946a-46d4-a4f2-29df8b040cff	c33e01ef-be92-474a-9e43-0543649735d5
87a397d9-aa18-4c61-a786-e66600836e53	SUNDAY	fb46ae33-946a-46d4-a4f2-29df8b040cff	58845959-0984-4136-a733-b75f5c9057b9
52f1ca6d-5106-4979-b821-de5acca934ce	MONDAY	803f1d5e-f530-4983-b815-68238a066cbf	6f1475e5-0d8a-47c7-855f-ccff802aff85
abffcecd-99bf-48bd-8c85-29946b707987	MONDAY	803f1d5e-f530-4983-b815-68238a066cbf	58845959-0984-4136-a733-b75f5c9057b9
c442f5f6-80ee-49d5-b6a6-bb13982c2f3f	MONDAY	e211f36c-60a5-47c7-9971-f16df7bf07fc	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6fcc84c2-557a-4c2a-94f8-e20106efe18f	MONDAY	e211f36c-60a5-47c7-9971-f16df7bf07fc	d54b130a-28a8-4bb1-9030-c474369c2205
c1781e40-2085-4389-9579-53385fe35a52	FRIDAY	3d6d21de-bf1b-4fbb-aed0-ee57caa00988	440c17ed-5262-4800-aa19-38e5285330c3
50800799-be0d-4c32-a097-3b7b5907a98e	FRIDAY	3d6d21de-bf1b-4fbb-aed0-ee57caa00988	5f76a266-8d18-4da8-9732-b63d4d8f24a0
defe49ae-5448-41aa-8bc9-79de6a4d1e73	FRIDAY	6b1271eb-54e6-49bd-ab27-c85605ec8576	440c17ed-5262-4800-aa19-38e5285330c3
1a408367-1340-4b45-8d79-87d8eeaeb5e3	FRIDAY	6b1271eb-54e6-49bd-ab27-c85605ec8576	5f76a266-8d18-4da8-9732-b63d4d8f24a0
35438605-b1b4-4fa5-9058-c9f4b817cbde	FRIDAY	6b1271eb-54e6-49bd-ab27-c85605ec8576	d54b130a-28a8-4bb1-9030-c474369c2205
844152b4-da0a-455d-8a2e-d839d5b3fa3e	FRIDAY	6b1271eb-54e6-49bd-ab27-c85605ec8576	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
204b9ad2-e8b0-4e96-a5ce-16f734da0116	FRIDAY	9ab1875d-9ba9-4f1f-8f12-7def153646d1	440c17ed-5262-4800-aa19-38e5285330c3
97bb6899-17ff-44ef-b750-4dc7c7e76625	FRIDAY	9ab1875d-9ba9-4f1f-8f12-7def153646d1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
983267ed-6737-4b25-9347-dee497cdd498	FRIDAY	9ab1875d-9ba9-4f1f-8f12-7def153646d1	d54b130a-28a8-4bb1-9030-c474369c2205
bad99c50-79eb-4b14-8f78-2926e9829b00	FRIDAY	9ab1875d-9ba9-4f1f-8f12-7def153646d1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ce918f89-7f73-4563-912e-86c9408c2725	SATURDAY	9ab1875d-9ba9-4f1f-8f12-7def153646d1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
665af1c2-9ff2-492d-83f0-fe281b3e0a6f	SATURDAY	9ab1875d-9ba9-4f1f-8f12-7def153646d1	d54b130a-28a8-4bb1-9030-c474369c2205
7901edfb-9774-42e8-a82a-24b219e635a0	SATURDAY	9ab1875d-9ba9-4f1f-8f12-7def153646d1	440c17ed-5262-4800-aa19-38e5285330c3
24175c29-0f40-4267-ab58-2f9ce1c5c331	SATURDAY	9ab1875d-9ba9-4f1f-8f12-7def153646d1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c7e39634-3ea3-4a11-9a26-0ccdfbbceabe	WEDNESDAY	e278ea08-a02d-4de2-ab78-d34b856c87d7	c33e01ef-be92-474a-9e43-0543649735d5
fd81e2c2-1577-4936-b0e1-c34b72d5971b	WEDNESDAY	e278ea08-a02d-4de2-ab78-d34b856c87d7	6f1475e5-0d8a-47c7-855f-ccff802aff85
5f88287a-396c-4308-ae5d-24e6de570b47	WEDNESDAY	e278ea08-a02d-4de2-ab78-d34b856c87d7	58845959-0984-4136-a733-b75f5c9057b9
fb6040ec-44de-44af-a252-0a5f25576cef	MONDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	440c17ed-5262-4800-aa19-38e5285330c3
fa19396a-371d-4af0-8b02-6c18d96a0184	WEDNESDAY	47d63157-d363-4e90-bf84-c94e1f61c0ee	d54b130a-28a8-4bb1-9030-c474369c2205
1c2f3f5c-babe-47c3-9f48-d9037f7b29c8	WEDNESDAY	a80a1879-1353-406e-8d2b-74da7a59c64a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f3b7e6bf-f32a-4a30-afe6-0be06b62bfe5	WEDNESDAY	47d63157-d363-4e90-bf84-c94e1f61c0ee	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
83072906-1daa-4b51-bbcf-65e633ca9995	THURSDAY	86961441-94bd-4771-91e3-3580d18a9f40	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
17796b81-f474-463a-a3a3-b330a3412beb	THURSDAY	86961441-94bd-4771-91e3-3580d18a9f40	d54b130a-28a8-4bb1-9030-c474369c2205
9718943b-bbd8-41dc-9a99-b6b7aef89093	TUESDAY	c5273d0a-67cd-4449-aa07-8a9fbc6b3301	c33e01ef-be92-474a-9e43-0543649735d5
342a231a-abc3-4914-83f7-d3896e44a708	WEDNESDAY	397d9d65-bfca-46fd-96b2-d81390b682b2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
75646397-4a4e-47e3-a6c5-4ede420e726f	THURSDAY	397d9d65-bfca-46fd-96b2-d81390b682b2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1f7d0451-06e6-4615-838d-6962d54848e1	FRIDAY	fb02acd0-7388-4507-b25a-49573981c9a1	440c17ed-5262-4800-aa19-38e5285330c3
946f3cec-23b7-447d-b5b1-b5c3554a52c0	FRIDAY	fb02acd0-7388-4507-b25a-49573981c9a1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7d5913a9-eb15-4b92-b0e0-2b26b453ad20	WEDNESDAY	c28a9113-da92-43ba-8123-741f67dc4b89	440c17ed-5262-4800-aa19-38e5285330c3
56ef72eb-ffc8-4ef4-8d02-da4aee20df42	WEDNESDAY	a03a4688-83e6-439a-a101-87bec382d6b0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
228b60ae-c9a5-493c-9992-29c62fd8833e	WEDNESDAY	a03a4688-83e6-439a-a101-87bec382d6b0	440c17ed-5262-4800-aa19-38e5285330c3
a3e40b41-22de-415d-9356-155f6cbc5e45	THURSDAY	d1d75766-4b31-4f28-ad9f-9d7bb7f49570	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
11c7fe6f-6de9-43b3-8cdc-fce04ded8cfd	TUESDAY	bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4bbd5cc5-eefe-46ef-bab0-825ddba9d4b7	TUESDAY	bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
34b1aca9-a885-45e5-97bf-2f9bc943e32c	TUESDAY	bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23	d54b130a-28a8-4bb1-9030-c474369c2205
a9600ddd-f5f3-498c-b5df-94fbd81012ef	TUESDAY	0b2159c6-a703-43bb-b6a1-5392153e01d7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
628d64fe-4ba6-4582-9225-c35339fe9acd	TUESDAY	0b2159c6-a703-43bb-b6a1-5392153e01d7	d54b130a-28a8-4bb1-9030-c474369c2205
d9347372-8d0b-4bea-8005-27d787cc4eab	TUESDAY	3cf8659e-c9c2-4c9f-91d0-897951be0372	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6407247f-927f-414f-b83a-ce3bfe56b5eb	TUESDAY	3cf8659e-c9c2-4c9f-91d0-897951be0372	c33e01ef-be92-474a-9e43-0543649735d5
bfe666a3-f2e0-470e-b428-4ae2c974cb80	THURSDAY	75ac7b94-3abe-4833-994e-110fc95c2ab4	d54b130a-28a8-4bb1-9030-c474369c2205
d8e20466-2116-479c-b58a-449bd36f963f	THURSDAY	75ac7b94-3abe-4833-994e-110fc95c2ab4	5f76a266-8d18-4da8-9732-b63d4d8f24a0
facea02f-40d9-45ad-8139-e50a44519ae8	WEDNESDAY	f9c97c01-a1ad-4ce7-a727-303bcd2342f5	d54b130a-28a8-4bb1-9030-c474369c2205
6d9dc19d-73a0-4f22-9a30-88d896a39f84	THURSDAY	836ee776-ad10-4ca5-8999-0b4cc29a032b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
133bd029-68cd-4d09-84fc-30bb85954531	TUESDAY	839631b1-18ca-4d4a-8105-6ec7c0cf8ffd	c33e01ef-be92-474a-9e43-0543649735d5
4468069c-4b81-4d42-89f8-b9955497e28f	TUESDAY	839631b1-18ca-4d4a-8105-6ec7c0cf8ffd	6f1475e5-0d8a-47c7-855f-ccff802aff85
c7f354ef-cef5-492e-976e-63468effcb60	WEDNESDAY	f8a8bb67-a047-439e-9956-ea4822e56e98	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7b6cf4fe-df08-47c4-803b-68dc9da15ee4	WEDNESDAY	f8a8bb67-a047-439e-9956-ea4822e56e98	c33e01ef-be92-474a-9e43-0543649735d5
c0dfad3f-5bfe-4df1-a57f-f3a1abd986f3	SATURDAY	14fe8296-326b-4aed-ad72-315db46d9504	440c17ed-5262-4800-aa19-38e5285330c3
99bb5b73-7db6-4566-99f0-027a2992f260	SATURDAY	14fe8296-326b-4aed-ad72-315db46d9504	6f1475e5-0d8a-47c7-855f-ccff802aff85
4d4ae831-073b-49ea-95a9-0b6ce7cec835	SATURDAY	14fe8296-326b-4aed-ad72-315db46d9504	c33e01ef-be92-474a-9e43-0543649735d5
45d825b2-98dc-4921-b0e5-d6dca5286391	SATURDAY	14fe8296-326b-4aed-ad72-315db46d9504	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b81e1d1a-b6a1-4ce0-9ad0-15cf7bb66c45	SATURDAY	14fe8296-326b-4aed-ad72-315db46d9504	d54b130a-28a8-4bb1-9030-c474369c2205
2058bd6d-0e03-4333-86d2-22630eebca2f	SATURDAY	14fe8296-326b-4aed-ad72-315db46d9504	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4f79f57d-2166-422c-945b-69bcada9aba7	FRIDAY	4aeb5881-7e48-470e-b134-12236311f9b8	c33e01ef-be92-474a-9e43-0543649735d5
63d3f954-69ee-4797-bb58-b72fef4352aa	FRIDAY	4aeb5881-7e48-470e-b134-12236311f9b8	6f1475e5-0d8a-47c7-855f-ccff802aff85
029a2b9c-f849-4e98-8479-8f2145b08327	FRIDAY	4aeb5881-7e48-470e-b134-12236311f9b8	58845959-0984-4136-a733-b75f5c9057b9
d473b5b5-0910-4fb0-b583-464a1108c5b7	TUESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	440c17ed-5262-4800-aa19-38e5285330c3
457009b8-1c18-45ae-a6cc-5fe42bad6fad	WEDNESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	440c17ed-5262-4800-aa19-38e5285330c3
67a3517f-71d2-4890-88d5-a7acdcdf4836	MONDAY	b3bd0f0e-83df-4b31-8f33-815e6853a3a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
89451cc1-5b74-43fb-adde-1195ece16dc7	MONDAY	b3bd0f0e-83df-4b31-8f33-815e6853a3a9	58845959-0984-4136-a733-b75f5c9057b9
394a47b4-7979-404a-a3cf-4b2b018f79a1	MONDAY	b3bd0f0e-83df-4b31-8f33-815e6853a3a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
b3ef0bb3-e37d-43c0-9e4a-edb9617918f4	MONDAY	b3bd0f0e-83df-4b31-8f33-815e6853a3a9	c33e01ef-be92-474a-9e43-0543649735d5
f9b23823-a6cc-4255-aa1f-6dd2789686ab	TUESDAY	86fa5fad-4673-48bf-94f7-d379e7f73676	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9b21754d-f9df-410c-9323-ed96e17ccc6c	TUESDAY	86fa5fad-4673-48bf-94f7-d379e7f73676	d54b130a-28a8-4bb1-9030-c474369c2205
50a66b25-d073-457b-bcdc-3f22a03c57f7	WEDNESDAY	5f84a25f-32b7-4b0f-bc74-9151f5e9e941	440c17ed-5262-4800-aa19-38e5285330c3
e7bd23df-bbcf-4980-9e5c-93ca6c98ed84	WEDNESDAY	5f84a25f-32b7-4b0f-bc74-9151f5e9e941	5f76a266-8d18-4da8-9732-b63d4d8f24a0
16d0889d-24e1-43c7-abc3-5be508a8f811	MONDAY	a95dcb15-398c-449b-8ef6-4bc0a9c4891a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
43190b02-b237-46df-899f-708aff5a60bf	TUESDAY	68be0a27-ed84-4ac1-a8f2-5b22ef9cc442	5f76a266-8d18-4da8-9732-b63d4d8f24a0
53562e90-2155-49f9-8b18-6f76b86ab152	TUESDAY	68be0a27-ed84-4ac1-a8f2-5b22ef9cc442	d54b130a-28a8-4bb1-9030-c474369c2205
7f2009b9-43c3-4cb8-a54a-d41cbfeeeb11	SATURDAY	733205c0-613d-49bb-aff8-50a53df081c6	440c17ed-5262-4800-aa19-38e5285330c3
de776403-c388-4483-9658-a7147c46c152	SATURDAY	733205c0-613d-49bb-aff8-50a53df081c6	6f1475e5-0d8a-47c7-855f-ccff802aff85
ec51be62-6e3c-4857-a6b1-2efe9ff460e2	SATURDAY	733205c0-613d-49bb-aff8-50a53df081c6	c33e01ef-be92-474a-9e43-0543649735d5
f5784dc9-abeb-4ea9-b8c1-fe723bb39208	SATURDAY	733205c0-613d-49bb-aff8-50a53df081c6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9f297e32-d1dd-4f56-83c1-cd729a945781	SATURDAY	733205c0-613d-49bb-aff8-50a53df081c6	d54b130a-28a8-4bb1-9030-c474369c2205
f855df80-5f90-4179-be8a-9991f7139819	SATURDAY	733205c0-613d-49bb-aff8-50a53df081c6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a6c15017-56a5-42f9-a4df-54cde7d927ec	FRIDAY	c8520c2f-475e-4f88-a3bb-284e7cc58562	d54b130a-28a8-4bb1-9030-c474369c2205
b8155c2f-9dfe-4558-b2b6-fc5e7a8aad00	FRIDAY	c8520c2f-475e-4f88-a3bb-284e7cc58562	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7f5be999-ff02-4874-8860-f1f6a4610c3d	THURSDAY	bf8268d8-5e12-4137-9407-be7988f0eeba	6f1475e5-0d8a-47c7-855f-ccff802aff85
b0cc4678-86e0-446e-ad97-493910687ece	THURSDAY	50402a5d-cc3a-41af-a544-46dfb70ef381	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
52a6a266-b08d-48a0-bead-be0ae05db17b	THURSDAY	50402a5d-cc3a-41af-a544-46dfb70ef381	c33e01ef-be92-474a-9e43-0543649735d5
29685eec-8921-4737-b00b-982169f8eb44	MONDAY	5d617722-8aeb-4e89-b859-01d3ec382190	6f1475e5-0d8a-47c7-855f-ccff802aff85
bf38a930-1c6c-4f37-97f4-da81ff35faf9	MONDAY	d03ce83e-c104-4f7a-9aa7-62d1193d18eb	6f1475e5-0d8a-47c7-855f-ccff802aff85
935e98bd-9b87-4b88-95c8-5a694b3f745f	FRIDAY	df7e7468-87c6-4728-b792-e4058c15908d	c33e01ef-be92-474a-9e43-0543649735d5
86c133ef-6f0a-4f38-b100-1ef2f06b1dfd	FRIDAY	c5611c9c-3d1a-4fae-9999-35acab706abc	6f1475e5-0d8a-47c7-855f-ccff802aff85
806a0bd3-2660-4517-bb11-77a3f1e7aa7b	THURSDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	440c17ed-5262-4800-aa19-38e5285330c3
29e79048-6e48-470c-94a1-b6e80bf5fe3b	MONDAY	7418423f-fa22-418e-86b9-7407485fe284	6f1475e5-0d8a-47c7-855f-ccff802aff85
9f5735a2-ceea-4b0b-bce6-ad6b3fca1b6a	MONDAY	7418423f-fa22-418e-86b9-7407485fe284	c33e01ef-be92-474a-9e43-0543649735d5
aff20581-1444-4584-9e7d-5c2077ccf5a5	TUESDAY	10d0833d-8416-4d03-a831-5a757a9a835c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2ce19939-066d-483f-86fe-63f28aa3d943	TUESDAY	10d0833d-8416-4d03-a831-5a757a9a835c	c33e01ef-be92-474a-9e43-0543649735d5
84cf5f3c-0033-4f5a-9439-02cc1cb19d21	SATURDAY	cea04306-6f20-431d-9904-c58271cf04f7	6f1475e5-0d8a-47c7-855f-ccff802aff85
355f1104-0d88-40da-917c-9f42af2f1d3e	SATURDAY	cea04306-6f20-431d-9904-c58271cf04f7	c33e01ef-be92-474a-9e43-0543649735d5
4adbc2dd-9509-4a2a-8d6a-3194f44a7f84	SATURDAY	cea04306-6f20-431d-9904-c58271cf04f7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
98c604cf-9f37-4755-871c-1b03e3c20459	SATURDAY	cea04306-6f20-431d-9904-c58271cf04f7	d54b130a-28a8-4bb1-9030-c474369c2205
3fd97124-cae7-44ba-8203-049ab735cfab	SATURDAY	cea04306-6f20-431d-9904-c58271cf04f7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
dbb0fc7c-c8be-4d65-b721-75ca11c40e88	THURSDAY	dba6afd0-4e82-476c-b1a4-06555587bffb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5738829b-165a-4c25-b10d-2dd3f495f088	THURSDAY	dba6afd0-4e82-476c-b1a4-06555587bffb	d54b130a-28a8-4bb1-9030-c474369c2205
9ee89dbf-32cf-43fe-b1bf-1bb241ee624e	SATURDAY	b671851f-867f-4d2d-98fd-6f6c5c31c083	440c17ed-5262-4800-aa19-38e5285330c3
7142c42c-026b-4292-98cb-3668937ced32	SATURDAY	b671851f-867f-4d2d-98fd-6f6c5c31c083	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d7df2357-533d-4938-bd9d-9239dd3d5073	SATURDAY	b671851f-867f-4d2d-98fd-6f6c5c31c083	d54b130a-28a8-4bb1-9030-c474369c2205
9d1ee68e-f1ef-4027-83cc-2e0bd4359c28	SATURDAY	b671851f-867f-4d2d-98fd-6f6c5c31c083	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
58bb7573-ee5e-4641-8483-c18b89b24913	SATURDAY	b671851f-867f-4d2d-98fd-6f6c5c31c083	c33e01ef-be92-474a-9e43-0543649735d5
eae0c614-c8d7-43ab-a2d5-b1773d93fc43	SATURDAY	b671851f-867f-4d2d-98fd-6f6c5c31c083	6f1475e5-0d8a-47c7-855f-ccff802aff85
b48eb079-c280-4ff8-b91a-6526aabde824	SATURDAY	b671851f-867f-4d2d-98fd-6f6c5c31c083	58845959-0984-4136-a733-b75f5c9057b9
b4f4155a-3e8e-49be-abab-42d41f0698f7	TUESDAY	31084145-ffa6-4861-9673-13c551ae20ab	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f5e43c27-2cba-4225-a196-b96903a124eb	TUESDAY	31084145-ffa6-4861-9673-13c551ae20ab	d54b130a-28a8-4bb1-9030-c474369c2205
e7a3f86d-db4a-4559-8dfe-d946aea5c9eb	FRIDAY	3979dd92-065c-40db-9c3a-3794752685df	5f76a266-8d18-4da8-9732-b63d4d8f24a0
553953d0-c402-4bee-a4ff-94b44fb18bcb	FRIDAY	3979dd92-065c-40db-9c3a-3794752685df	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
23e42357-bab1-4338-b03d-3d9fbb521d49	FRIDAY	3979dd92-065c-40db-9c3a-3794752685df	d54b130a-28a8-4bb1-9030-c474369c2205
e5ffb780-1679-4224-ad1f-35cc66f23af1	FRIDAY	9fcd1dd4-947e-48ad-a71c-937d87f9a894	6f1475e5-0d8a-47c7-855f-ccff802aff85
b3669768-0bf9-4494-bb64-f447a1f87420	FRIDAY	9fcd1dd4-947e-48ad-a71c-937d87f9a894	58845959-0984-4136-a733-b75f5c9057b9
05f66328-945e-4dc0-8947-ce7b8f62d238	THURSDAY	4bbc1f38-3573-469b-a204-b84123b925a3	c33e01ef-be92-474a-9e43-0543649735d5
b9aaa0d7-8097-486b-8b30-a63ed5aa007a	THURSDAY	4bbc1f38-3573-469b-a204-b84123b925a3	58845959-0984-4136-a733-b75f5c9057b9
0f367bac-99a6-4763-83be-ef196f34bd3c	THURSDAY	4bbc1f38-3573-469b-a204-b84123b925a3	6f1475e5-0d8a-47c7-855f-ccff802aff85
c01a234e-f75c-47b2-96d4-604da85fbd63	MONDAY	d9ded29f-0a3b-4024-b3cd-7a960ce399b7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8e45f868-cb3c-4463-9362-f4167a4d2e32	MONDAY	d9ded29f-0a3b-4024-b3cd-7a960ce399b7	d54b130a-28a8-4bb1-9030-c474369c2205
1f234cb5-808f-4aa7-a395-918f99759b4c	MONDAY	7c512d97-6114-4ef1-ba31-55abaf1f274e	440c17ed-5262-4800-aa19-38e5285330c3
7386018b-e249-4be7-83c5-2ed3ba6df70b	MONDAY	7c512d97-6114-4ef1-ba31-55abaf1f274e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8c23b31f-0e53-4140-a8e1-436d8415c193	TUESDAY	7c512d97-6114-4ef1-ba31-55abaf1f274e	440c17ed-5262-4800-aa19-38e5285330c3
165a1f29-b606-4aa1-ba8f-35ca0c79bac6	TUESDAY	7c512d97-6114-4ef1-ba31-55abaf1f274e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6eb4cdad-418c-42cc-adde-baf566b491dd	FRIDAY	d249752a-4f26-45d6-b9a0-4288c97d2ee1	d54b130a-28a8-4bb1-9030-c474369c2205
f28a853e-b741-43c9-ae4d-92438d37015c	FRIDAY	d249752a-4f26-45d6-b9a0-4288c97d2ee1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1b9cb3ea-cb3b-4073-8528-a004ab23a796	FRIDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	440c17ed-5262-4800-aa19-38e5285330c3
7e2e788d-b543-4729-b78a-5eaf17959145	MONDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f2e8e27c-cf4e-4461-a585-7951f1bbbdbc	TUESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bab90cd6-17f3-4f1b-b310-544280160ebb	WEDNESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5809fc6d-20ad-43d7-9210-ecf5ec53060e	TUESDAY	ee549e95-aa66-4707-b957-26b96b35d068	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b1203af2-6ef5-4066-afce-30dc3c294c2f	THURSDAY	ee549e95-aa66-4707-b957-26b96b35d068	d54b130a-28a8-4bb1-9030-c474369c2205
d03100a2-b86a-4398-8f2c-506728d81658	THURSDAY	26654263-ccb2-4fad-8ecc-342da43c3928	c33e01ef-be92-474a-9e43-0543649735d5
7005beb8-6cea-4d10-a6e8-708416ebcd7b	THURSDAY	26654263-ccb2-4fad-8ecc-342da43c3928	6f1475e5-0d8a-47c7-855f-ccff802aff85
4077ce6d-f3c7-470e-8312-1fed592a75e8	TUESDAY	f7fb4d07-cc8b-449a-a860-2100d2334658	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d4f74e23-d624-4d2e-b375-d869baffc77d	THURSDAY	26654263-ccb2-4fad-8ecc-342da43c3928	58845959-0984-4136-a733-b75f5c9057b9
564d695e-0f97-471e-90cf-370d5af84851	THURSDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0261f024-1f54-491e-8951-c2d1da48944a	FRIDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3d73a4c3-7483-49b1-9b47-0473fcae3cab	MONDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	d54b130a-28a8-4bb1-9030-c474369c2205
b7ed1f4c-f4bc-4259-bd80-69c86ffbd793	TUESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	d54b130a-28a8-4bb1-9030-c474369c2205
383f9c16-84d5-43ac-97d8-66efe76890d1	WEDNESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	d54b130a-28a8-4bb1-9030-c474369c2205
cc0b4001-9083-40ae-97ac-e0f520c96daf	THURSDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	d54b130a-28a8-4bb1-9030-c474369c2205
3715f914-4c3f-4189-b1b1-38d1f3268450	FRIDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	d54b130a-28a8-4bb1-9030-c474369c2205
20c436b8-1d60-4910-9fa2-99565484c759	MONDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c64efc47-ed45-4e87-ad96-107810e258d4	MONDAY	cd5f8554-fec4-4862-80c3-caff5df01ad5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
fc415874-3771-4b9c-a8c0-b457d49b5e33	TUESDAY	cd5f8554-fec4-4862-80c3-caff5df01ad5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b3697bdc-b5ac-4dd7-82f5-5d6d0d3d334d	TUESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7d8e6a09-6436-4959-a51e-d8326d985617	WEDNESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e3de8e36-6b50-4128-bd26-25d0fafe8030	THURSDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
598b1cf9-bc5f-47a7-9c6d-bc2bdc49a380	FRIDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
31b79960-1259-4300-9e23-c51cb9d02309	MONDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	c33e01ef-be92-474a-9e43-0543649735d5
20dc90dd-6a82-4fe1-b757-705061b544c1	TUESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	c33e01ef-be92-474a-9e43-0543649735d5
0b040fb6-c1d2-4255-8d91-c2a75e50baef	WEDNESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	c33e01ef-be92-474a-9e43-0543649735d5
1eef2dfb-9649-4f8c-bb5e-051e96b40a9f	THURSDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	c33e01ef-be92-474a-9e43-0543649735d5
55ebdd9d-ca22-4360-8992-cfb61814416d	FRIDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	c33e01ef-be92-474a-9e43-0543649735d5
513268b9-1e29-48a8-8174-0ff0e8a0c761	MONDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	6f1475e5-0d8a-47c7-855f-ccff802aff85
ecae0aa9-3c7f-4c8d-b6c4-50404dca7f5d	TUESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	6f1475e5-0d8a-47c7-855f-ccff802aff85
bfba3009-af63-41cf-97b1-cfaea125122c	WEDNESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	6f1475e5-0d8a-47c7-855f-ccff802aff85
88d563a8-6aff-4a7b-b2a1-f365e46a7c1c	THURSDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	6f1475e5-0d8a-47c7-855f-ccff802aff85
1d51f607-7821-4517-bfff-2719b6315ee3	FRIDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	6f1475e5-0d8a-47c7-855f-ccff802aff85
15f89bed-3c22-4c1f-9afd-82ecf44648ac	MONDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	58845959-0984-4136-a733-b75f5c9057b9
9d164f16-b28a-4739-8edd-4e76ed8bb96f	TUESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	58845959-0984-4136-a733-b75f5c9057b9
8c0f0cf2-c0c2-47f2-99e3-14e91404a113	WEDNESDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	58845959-0984-4136-a733-b75f5c9057b9
342ca96d-e8b9-4e04-831f-5e9d0f02df77	THURSDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	58845959-0984-4136-a733-b75f5c9057b9
776a44bc-b42f-4ff4-a863-1eeb43aba134	FRIDAY	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	58845959-0984-4136-a733-b75f5c9057b9
040dcf97-64a5-4aaf-8363-3d913dc6af31	MONDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	440c17ed-5262-4800-aa19-38e5285330c3
2115e37f-e334-4f01-80c5-77cc697773a0	TUESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	440c17ed-5262-4800-aa19-38e5285330c3
af9d4bed-974c-45b0-8665-7fbac0feb49a	WEDNESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	440c17ed-5262-4800-aa19-38e5285330c3
9bd55782-2429-4509-9a96-a20244db72ec	THURSDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	440c17ed-5262-4800-aa19-38e5285330c3
7541073c-d4e2-4ae1-873f-ed95e2d2d35a	FRIDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	440c17ed-5262-4800-aa19-38e5285330c3
e86a9c4f-9ba7-4aa2-9a8b-d004ad371fb6	MONDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9b47dc55-6ccb-4307-a6f3-e2410c099fc8	TUESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	5f76a266-8d18-4da8-9732-b63d4d8f24a0
246a6243-4e5d-4d8a-9261-3d5e9c97e9a4	WEDNESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d076d672-6801-412c-8486-9c4e1c29e460	THURSDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d525cd29-4bde-4de0-84aa-762f4dbea28a	FRIDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a362114f-6dc7-43ab-8d41-a880f32bd4db	MONDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	d54b130a-28a8-4bb1-9030-c474369c2205
826363a2-bb43-4f4e-b6e1-32f6bc1a11d9	TUESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	d54b130a-28a8-4bb1-9030-c474369c2205
365dd86f-973d-438f-bbe7-95aa8e7f3ce0	WEDNESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	d54b130a-28a8-4bb1-9030-c474369c2205
088f1c41-f685-4ae2-88e9-0e82ccfdeeb2	THURSDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	d54b130a-28a8-4bb1-9030-c474369c2205
fcbcc967-69b7-4a59-a31c-e50c6c82a311	FRIDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	d54b130a-28a8-4bb1-9030-c474369c2205
73ff138f-921b-43d4-9c51-c37b40a12175	MONDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
eb4bd59f-0a5b-4040-82c9-1f6a80a040b6	TUESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5d893f0b-598d-4e08-8c11-4bd8d8084533	WEDNESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
93f174cd-41c6-4c6d-a442-cb55aded3aa6	THURSDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e9b135c3-4974-4c80-97d1-8dc08906f42c	FRIDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e19d8cd6-8f18-4b55-9018-7d274637950a	MONDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	c33e01ef-be92-474a-9e43-0543649735d5
2f5d8eec-0b0b-49b0-a180-5466fede8816	TUESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	c33e01ef-be92-474a-9e43-0543649735d5
ca75eb09-b121-430b-acd4-7a6a2f92b21e	WEDNESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	c33e01ef-be92-474a-9e43-0543649735d5
3970eb94-1ec6-4ae7-ba2b-4c242b98fa72	THURSDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	c33e01ef-be92-474a-9e43-0543649735d5
51a9820a-0e1f-4bbb-9326-a2a5c61cda46	FRIDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	c33e01ef-be92-474a-9e43-0543649735d5
aad06b06-650f-496a-84a7-bece5377b0d2	MONDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	6f1475e5-0d8a-47c7-855f-ccff802aff85
6cf2fff7-40b3-4843-97e2-7ba20e6760aa	TUESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	6f1475e5-0d8a-47c7-855f-ccff802aff85
8328c3da-559e-4a7d-9b54-29d6523f06fc	WEDNESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	6f1475e5-0d8a-47c7-855f-ccff802aff85
6232db9b-ff51-443e-af60-cb27dd541da6	THURSDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	6f1475e5-0d8a-47c7-855f-ccff802aff85
4532489d-118b-41b0-841b-273360a26865	FRIDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	6f1475e5-0d8a-47c7-855f-ccff802aff85
768fa8b5-382c-411f-97ae-3d83a86cbbf3	MONDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	58845959-0984-4136-a733-b75f5c9057b9
4a9de6f3-abb9-4115-a87e-688231a31859	TUESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	58845959-0984-4136-a733-b75f5c9057b9
ce3eb1a1-0418-4ccf-be53-29aef4870d22	WEDNESDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	58845959-0984-4136-a733-b75f5c9057b9
f6dc475e-3dce-4ac5-8cb6-986b62d1d909	THURSDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	58845959-0984-4136-a733-b75f5c9057b9
ae3d2def-9a58-4fac-9dda-c05a63b545b2	FRIDAY	9db2d190-6068-4c34-9e5c-36e9994274aa	58845959-0984-4136-a733-b75f5c9057b9
2f572417-073d-4567-8885-a5eb54952c92	MONDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	440c17ed-5262-4800-aa19-38e5285330c3
139441ac-3ffa-48b5-98ca-9fdf85db51ef	TUESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	440c17ed-5262-4800-aa19-38e5285330c3
bc5a42b0-6020-4a1b-b107-f324763da33d	WEDNESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	440c17ed-5262-4800-aa19-38e5285330c3
a555dcb5-5681-452a-b077-4d393f902e3c	THURSDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	440c17ed-5262-4800-aa19-38e5285330c3
6ae9e85d-9d57-450f-ad39-55a15f6babe8	FRIDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	440c17ed-5262-4800-aa19-38e5285330c3
973866ed-2288-450e-b646-a96a5096b489	MONDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e05a0844-a976-4d7f-abb1-d260d385eada	TUESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	5f76a266-8d18-4da8-9732-b63d4d8f24a0
df624b87-4754-4770-9117-bd082849a14d	WEDNESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e0487ed2-6bce-4181-b18d-72e80c2dd748	THURSDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4c8b9130-30f2-4ba2-910e-47f8347193ea	FRIDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	5f76a266-8d18-4da8-9732-b63d4d8f24a0
78a17cf0-1710-4669-a229-204c1dedce3b	MONDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	d54b130a-28a8-4bb1-9030-c474369c2205
5e6fdbaa-b26c-4b61-b551-30e788586273	TUESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	d54b130a-28a8-4bb1-9030-c474369c2205
59422861-3b38-457e-81f7-dee97e102c49	WEDNESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	d54b130a-28a8-4bb1-9030-c474369c2205
8bb6be78-7f73-4a19-bb20-2d9bcd40bad8	THURSDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	d54b130a-28a8-4bb1-9030-c474369c2205
6c760342-d176-47a4-b731-53d8b49058e6	FRIDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	d54b130a-28a8-4bb1-9030-c474369c2205
3dca639b-aac3-4147-ba26-4f2617d42a7f	MONDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8ede451c-344d-49e8-a966-5aa4f0f2ee05	TUESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7389a605-1223-44a6-92ce-fb14915eea06	WEDNESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c1c841b7-8915-4dbf-a8d2-b038376d0a25	THURSDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
764fcd35-a016-47b6-895a-d53d9c927ea3	FRIDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1b62d50b-c5c8-4f92-8f3f-8ebef519c6b1	MONDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	c33e01ef-be92-474a-9e43-0543649735d5
8570ef9a-aed6-45e9-9546-58c1407493b9	TUESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	c33e01ef-be92-474a-9e43-0543649735d5
a1ce141f-53b1-4f67-80e5-c479b5bfcfe6	WEDNESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	c33e01ef-be92-474a-9e43-0543649735d5
e3064401-7ca2-4048-8793-4a332b60b9c5	THURSDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	c33e01ef-be92-474a-9e43-0543649735d5
40b4006f-4fce-4692-b149-9bddafc605a2	FRIDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	c33e01ef-be92-474a-9e43-0543649735d5
b69e2d36-cfc7-466b-be6f-4dbfc05e9422	MONDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	6f1475e5-0d8a-47c7-855f-ccff802aff85
05caba31-22f8-42a2-95d0-3e04d7ff090c	TUESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	6f1475e5-0d8a-47c7-855f-ccff802aff85
0fa28f37-41cd-4788-b4c8-13a643b283b6	WEDNESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	6f1475e5-0d8a-47c7-855f-ccff802aff85
da90f05f-1416-46ab-b246-c8c2456b2257	FRIDAY	91283133-2877-4bc0-a90d-f4168d088349	c33e01ef-be92-474a-9e43-0543649735d5
60213c3b-3cd7-4382-974f-de6d320f6034	FRIDAY	91283133-2877-4bc0-a90d-f4168d088349	6f1475e5-0d8a-47c7-855f-ccff802aff85
6a6bcb86-5a7e-4a6a-ac71-1a24b351d066	FRIDAY	91283133-2877-4bc0-a90d-f4168d088349	58845959-0984-4136-a733-b75f5c9057b9
63237c68-9746-4134-9486-d46243349e38	MONDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	440c17ed-5262-4800-aa19-38e5285330c3
52e4a905-7d81-466a-ac23-48cfeef9eab0	TUESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	440c17ed-5262-4800-aa19-38e5285330c3
93f56867-bd79-4261-825c-90ca395bc590	WEDNESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	440c17ed-5262-4800-aa19-38e5285330c3
c47723d7-3ac3-463c-b8a7-045332c827b2	THURSDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	440c17ed-5262-4800-aa19-38e5285330c3
f3e48f74-db1b-453d-a847-641055da9343	FRIDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	440c17ed-5262-4800-aa19-38e5285330c3
4229ba24-7946-4d1d-b26b-32c4eeb04f28	MONDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b5a44c72-7f0d-417c-8535-5bb6ec7cf085	TUESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9ccd4bc5-ba8e-4275-bea5-c69fba20db47	WEDNESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e8db210b-4902-4d0d-9e0c-726d802500b4	THURSDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
eac3e168-221c-4bba-8f9d-91f0bdd6c591	FRIDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fd624ae5-2280-41bc-bac8-7ce4e32d128c	MONDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	d54b130a-28a8-4bb1-9030-c474369c2205
56527056-11ca-44c7-b435-2e648b781265	TUESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	d54b130a-28a8-4bb1-9030-c474369c2205
f2ba7f14-8ef7-471f-b6b1-6757a907c918	WEDNESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	d54b130a-28a8-4bb1-9030-c474369c2205
0674208e-f069-4dbe-a433-5c00acc56663	THURSDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	d54b130a-28a8-4bb1-9030-c474369c2205
302b6f61-c15a-4be6-a370-8964c6f2d4b4	FRIDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	d54b130a-28a8-4bb1-9030-c474369c2205
a86373c3-fa59-44b8-b539-735c5d491d42	MONDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
58d939a3-91fd-4a4f-b0da-5124f4f8c977	TUESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
58c30248-c7df-41ca-ac61-de212e523c70	WEDNESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6f22da2a-e75b-4726-b57e-efe7a0202f8b	THURSDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c91178ed-8ec2-4fd8-a182-2c0fb6c8a531	FRIDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
07d84a4a-7aa9-4557-8e2e-79cbb9a0c9a4	MONDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	c33e01ef-be92-474a-9e43-0543649735d5
7e63dab0-f020-4920-ab44-1eb99d51dac6	TUESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	c33e01ef-be92-474a-9e43-0543649735d5
9d205754-4a8a-41a0-aa7b-8ae310ae818b	WEDNESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	c33e01ef-be92-474a-9e43-0543649735d5
72dd137d-9891-4bbe-8fe3-fef618b97ce8	THURSDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	c33e01ef-be92-474a-9e43-0543649735d5
a35c1184-918c-452f-9136-06767b6ca4e5	FRIDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	c33e01ef-be92-474a-9e43-0543649735d5
6c25ba12-d44f-420f-9388-09a409ab2ff7	MONDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	6f1475e5-0d8a-47c7-855f-ccff802aff85
fb4da395-3b3a-4272-8070-d62d26ec93dd	TUESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	6f1475e5-0d8a-47c7-855f-ccff802aff85
66420548-16e9-4986-b39e-1cdead634e03	WEDNESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	6f1475e5-0d8a-47c7-855f-ccff802aff85
e43e2966-1a01-482f-805b-7f740d585abe	THURSDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	6f1475e5-0d8a-47c7-855f-ccff802aff85
b09956e0-bdbb-448f-95a9-ed0f9576db47	FRIDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	6f1475e5-0d8a-47c7-855f-ccff802aff85
fa98f647-c3b8-482d-ae38-46f7a88ceb0d	MONDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	58845959-0984-4136-a733-b75f5c9057b9
1c2e4c59-91bf-4704-b5b8-cd03f7465161	TUESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	58845959-0984-4136-a733-b75f5c9057b9
d7f6c11d-3525-41c1-8476-3ece3de73961	WEDNESDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	58845959-0984-4136-a733-b75f5c9057b9
e056c967-0a83-42c5-b8d7-f24745e99172	THURSDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	58845959-0984-4136-a733-b75f5c9057b9
5c0113ec-9ce7-4d2d-871e-4ac584fa9b52	FRIDAY	2c1ea78c-8ca7-4583-aad4-a6724bc81df1	58845959-0984-4136-a733-b75f5c9057b9
5f9b2ee9-0d91-4635-ac1f-eb84070b5496	MONDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	440c17ed-5262-4800-aa19-38e5285330c3
e80eb8da-39ab-4f90-985b-401d9c846893	TUESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	440c17ed-5262-4800-aa19-38e5285330c3
f66e4773-3f8e-45aa-8d9e-56ca289a97f3	WEDNESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	440c17ed-5262-4800-aa19-38e5285330c3
967af624-7812-4f1a-83e2-169afb1934a8	THURSDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	440c17ed-5262-4800-aa19-38e5285330c3
d8c82cce-3700-4eca-adf4-540957e13495	FRIDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	440c17ed-5262-4800-aa19-38e5285330c3
dc760546-b558-404f-b6b5-b27c2d16aa33	MONDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b73342ab-85a5-4c09-9851-db8ae43e1a4e	TUESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f4293c75-dddf-4a62-9a88-c201b941707c	WEDNESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	5f76a266-8d18-4da8-9732-b63d4d8f24a0
48b0a7e4-4f03-42ac-a898-c31931b498f5	THURSDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d39d12bc-a90b-4e5a-bb02-ca7680ddf4e4	FRIDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	5f76a266-8d18-4da8-9732-b63d4d8f24a0
23fa56a0-4277-4be2-bd97-59bea56c63b3	MONDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	d54b130a-28a8-4bb1-9030-c474369c2205
7b036a19-9ccd-489b-ae1f-1c7a30520e30	TUESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	d54b130a-28a8-4bb1-9030-c474369c2205
601b3ac9-0f23-46cd-8cda-9a753c07060a	WEDNESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	d54b130a-28a8-4bb1-9030-c474369c2205
2a4755aa-1cbd-4aee-b9f2-9b8de492496f	THURSDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	d54b130a-28a8-4bb1-9030-c474369c2205
128d7698-0a4b-4166-a6de-42f254f1c4e6	FRIDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	d54b130a-28a8-4bb1-9030-c474369c2205
9347a5ba-5de5-491c-a115-6b1eaa71c7fc	MONDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
68f76037-2ccd-462a-841f-c83fd7a42c05	TUESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4e0766b5-f22e-487e-9247-543ccd4d4d49	WEDNESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3e2458df-ac8b-4542-a6d3-64e3ff56702e	THURSDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f71fd7c1-2f24-4591-b324-29145e11946d	FRIDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
eae9a263-b1f1-4711-b0ad-c96772d27ff7	MONDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	c33e01ef-be92-474a-9e43-0543649735d5
fd278b5f-bfed-4361-be90-7d64f84b0aeb	TUESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	c33e01ef-be92-474a-9e43-0543649735d5
6be25f4e-2242-4d48-9092-0821856ea992	WEDNESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	c33e01ef-be92-474a-9e43-0543649735d5
032f62f0-a2c3-4d11-a9f1-932f60c10b83	THURSDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	c33e01ef-be92-474a-9e43-0543649735d5
e4f4f3fc-941d-470f-872d-3bf20089120a	FRIDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	c33e01ef-be92-474a-9e43-0543649735d5
c60df778-e327-436d-932c-d9f36cd7944a	MONDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	6f1475e5-0d8a-47c7-855f-ccff802aff85
fb654ed7-46cb-4288-ac28-04acdb7696aa	TUESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	6f1475e5-0d8a-47c7-855f-ccff802aff85
f005ef69-3e07-4e29-901b-32e5f13c57e5	WEDNESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	6f1475e5-0d8a-47c7-855f-ccff802aff85
30f87895-fff8-4f8c-aaa4-8db18f76e29a	THURSDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	6f1475e5-0d8a-47c7-855f-ccff802aff85
86ad9166-2954-4c05-aa7e-03c9cb91cfa0	FRIDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	6f1475e5-0d8a-47c7-855f-ccff802aff85
a8aab720-daa8-40f9-8ef4-5dc8de9c4db2	MONDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	58845959-0984-4136-a733-b75f5c9057b9
1286e8c5-e25a-43ac-9386-2a90af44cfc9	TUESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	58845959-0984-4136-a733-b75f5c9057b9
9d542d38-068a-47ba-803c-b3eebf5b3079	WEDNESDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	58845959-0984-4136-a733-b75f5c9057b9
e5c8802b-783c-482b-bcab-537337a1814d	THURSDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	58845959-0984-4136-a733-b75f5c9057b9
31a99ff8-4082-4a01-8c06-dac789d3a73c	FRIDAY	5926aed9-9d4d-42b1-9fa8-fd82d5706412	58845959-0984-4136-a733-b75f5c9057b9
67abd6ad-0198-482c-bf86-2062c0949b32	MONDAY	0750aa84-1b51-45b8-aaad-056cbf1b303b	c33e01ef-be92-474a-9e43-0543649735d5
d149e466-66e9-4d95-9b7f-44b52255dff3	MONDAY	0750aa84-1b51-45b8-aaad-056cbf1b303b	58845959-0984-4136-a733-b75f5c9057b9
d177558a-882c-4fb3-9c6d-f6952702587e	MONDAY	0750aa84-1b51-45b8-aaad-056cbf1b303b	6f1475e5-0d8a-47c7-855f-ccff802aff85
f8f8235e-1638-460a-bb91-723480caa3ee	SATURDAY	20651a53-f4c0-415c-a66f-f4cd62a6a3b2	d54b130a-28a8-4bb1-9030-c474369c2205
f52e00a9-3e4d-47a4-90d8-fc8a9d9e98f1	SATURDAY	20651a53-f4c0-415c-a66f-f4cd62a6a3b2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2cc143da-ee47-4b08-9a5b-b5efcff82178	SATURDAY	20651a53-f4c0-415c-a66f-f4cd62a6a3b2	c33e01ef-be92-474a-9e43-0543649735d5
f370ef26-9099-4d84-9765-c9b3ef5f832f	FRIDAY	1337b436-7ff9-4b79-82a8-9981f2e66521	440c17ed-5262-4800-aa19-38e5285330c3
fccb0e4b-18ff-4c1e-b0b7-793409c5a365	FRIDAY	1337b436-7ff9-4b79-82a8-9981f2e66521	d54b130a-28a8-4bb1-9030-c474369c2205
e0ea5b55-79a6-4420-94c1-82270152e9f9	FRIDAY	1337b436-7ff9-4b79-82a8-9981f2e66521	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7f765ce0-5f38-45b1-92ab-54d0a482e791	FRIDAY	1b702945-e962-4778-b9c9-2fce7be4ab6c	440c17ed-5262-4800-aa19-38e5285330c3
cda94630-37c6-495a-864a-423e5ffd51a4	FRIDAY	1b702945-e962-4778-b9c9-2fce7be4ab6c	d54b130a-28a8-4bb1-9030-c474369c2205
e3111790-e659-4031-8b85-b879242c7867	FRIDAY	1b702945-e962-4778-b9c9-2fce7be4ab6c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b5f070a5-fe06-4fda-a778-052d231755d4	THURSDAY	64e7e3c8-c369-4913-be67-a64c7ea29d11	440c17ed-5262-4800-aa19-38e5285330c3
6c2436a0-b8c4-4d35-bedd-397719a03e19	THURSDAY	64e7e3c8-c369-4913-be67-a64c7ea29d11	d54b130a-28a8-4bb1-9030-c474369c2205
737d1d53-59e4-43ba-83de-7a1419eeb004	THURSDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	6f1475e5-0d8a-47c7-855f-ccff802aff85
ad8aaf5f-5bde-4045-b489-99e974c3bc7c	FRIDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	6f1475e5-0d8a-47c7-855f-ccff802aff85
f75503e3-3926-4795-a0fd-89329fb1e8ae	MONDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	58845959-0984-4136-a733-b75f5c9057b9
500329ba-5439-45f0-b6fd-c8a0f1719c4e	TUESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	58845959-0984-4136-a733-b75f5c9057b9
ff36cbad-fa38-44b1-a7a0-6b78bbd09c31	WEDNESDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	58845959-0984-4136-a733-b75f5c9057b9
ab534f2b-96b5-48ae-acbd-0e36f2782480	THURSDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	58845959-0984-4136-a733-b75f5c9057b9
76a43425-a072-4b79-b0ef-76b54e089071	FRIDAY	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	58845959-0984-4136-a733-b75f5c9057b9
082220ef-5937-486b-ad43-b541f0862e94	MONDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	440c17ed-5262-4800-aa19-38e5285330c3
c24170c2-a9d2-42fb-a549-466bdc5f11cd	TUESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	440c17ed-5262-4800-aa19-38e5285330c3
d70d2860-3771-45eb-8b33-fc3c868a7753	WEDNESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	440c17ed-5262-4800-aa19-38e5285330c3
bf005955-dc31-407d-b086-43bca15114dc	THURSDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	440c17ed-5262-4800-aa19-38e5285330c3
236751e0-3938-4d1c-b39d-81a1620abfc9	FRIDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	440c17ed-5262-4800-aa19-38e5285330c3
1a684e28-de38-433b-a00f-368cc818fdf3	MONDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
228a6591-aec7-4d7c-8955-29c0644d2a49	TUESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9fe2ac26-fd08-42bd-aa9f-9818ab0fc4a5	WEDNESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
aea31639-96d2-4012-9bae-93f2eb96d78c	THURSDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e98fa5b5-e042-40e2-9fc7-6ed9e783647c	FRIDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c610fd02-4f65-4117-a952-85a564dc7275	MONDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	d54b130a-28a8-4bb1-9030-c474369c2205
b7108ab5-10a6-4030-b486-0053c7097da8	TUESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	d54b130a-28a8-4bb1-9030-c474369c2205
33aa8309-ac0d-4b10-9b67-d84ea19361b1	WEDNESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	d54b130a-28a8-4bb1-9030-c474369c2205
d9358899-d0ef-4987-b5a3-1db55433f59b	THURSDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	d54b130a-28a8-4bb1-9030-c474369c2205
2ccc6185-15df-404c-b4fb-76c402277d79	FRIDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	d54b130a-28a8-4bb1-9030-c474369c2205
d4f4ae1a-e58e-49ad-80cb-226d5236ce08	MONDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d9353bc1-db99-447d-ab79-3168aa4d3f12	TUESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b5e5b8d8-8a5c-41a6-9e27-99c4b185864e	WEDNESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
08d4049e-ff26-44ef-9d6a-c5a5dcb773cd	THURSDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c6811319-74f1-48e9-abb5-82e74bb581d0	FRIDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e87f4c3b-10e8-4759-a83b-81d25ad47312	MONDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	c33e01ef-be92-474a-9e43-0543649735d5
357e096c-b2e1-47b3-af55-8eb291164c45	TUESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	c33e01ef-be92-474a-9e43-0543649735d5
c9fdc123-2ce8-4781-b6e7-a0792665314c	WEDNESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	c33e01ef-be92-474a-9e43-0543649735d5
d246f797-0fee-4366-ad41-e9615445c206	THURSDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	c33e01ef-be92-474a-9e43-0543649735d5
e3a1207f-36fd-4650-b7e1-d0c5b0961719	FRIDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	c33e01ef-be92-474a-9e43-0543649735d5
e4fa38e6-a0ae-428f-8b4d-b859cac53340	MONDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	6f1475e5-0d8a-47c7-855f-ccff802aff85
8c2abc00-ec69-4f11-a9f3-8ab9057f0b6d	TUESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	6f1475e5-0d8a-47c7-855f-ccff802aff85
f2ac27b0-11c7-4d73-ac55-e6497524b1b9	WEDNESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	6f1475e5-0d8a-47c7-855f-ccff802aff85
0f86855b-2d16-4ea7-a2ac-4833333226c4	THURSDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	6f1475e5-0d8a-47c7-855f-ccff802aff85
be086cde-e215-4d3d-b589-34356dc1468a	FRIDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	6f1475e5-0d8a-47c7-855f-ccff802aff85
2d5aa092-f391-4abd-9d15-228467897f5e	MONDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	58845959-0984-4136-a733-b75f5c9057b9
344e3132-09fa-4ca6-85af-180faa0af3bf	TUESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	58845959-0984-4136-a733-b75f5c9057b9
a839035b-0f29-4f0f-920f-97c1306f85b2	WEDNESDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	58845959-0984-4136-a733-b75f5c9057b9
f629ed89-9c27-469b-b28a-dd34f9c995e8	THURSDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	58845959-0984-4136-a733-b75f5c9057b9
4dd25189-30d9-42ee-ba17-49eab1fb734a	FRIDAY	350475ef-1d3b-4823-8c76-7f190a27f87e	58845959-0984-4136-a733-b75f5c9057b9
76866a81-46b8-4b2f-860f-febc90211f81	MONDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	440c17ed-5262-4800-aa19-38e5285330c3
05a17167-f5e1-4f99-adb9-8f53639c8aa3	TUESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	440c17ed-5262-4800-aa19-38e5285330c3
849104bb-2d8b-41a6-a962-f727a8698c58	WEDNESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	440c17ed-5262-4800-aa19-38e5285330c3
48346ac7-a2b3-4e29-b74f-4c1a0e2ce90e	THURSDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	440c17ed-5262-4800-aa19-38e5285330c3
7d613012-a361-40ff-822c-825fba63a4e8	FRIDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	440c17ed-5262-4800-aa19-38e5285330c3
a753e7a9-7d88-4fae-bdd9-1217840d6fc5	MONDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
100f77f9-5a67-4c86-b5a1-91f5b744eb36	TUESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1779b9e1-27cf-447e-97fc-612fae1f5f18	WEDNESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
70d38031-0809-40d6-9851-d79143bddc85	THURSDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7613d115-d463-4e4e-abf6-314f4227cb0c	FRIDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
41ff250a-86c1-4d6c-a584-2923f2703e9d	MONDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	d54b130a-28a8-4bb1-9030-c474369c2205
aab17d2c-0d6d-4768-b990-c93254cc0a46	TUESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	d54b130a-28a8-4bb1-9030-c474369c2205
641d12d5-c56a-48a0-9b7e-95bc41514602	WEDNESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	d54b130a-28a8-4bb1-9030-c474369c2205
f7608e0d-beae-45ca-885e-5abdabe8a08e	THURSDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	d54b130a-28a8-4bb1-9030-c474369c2205
636a147d-290f-412c-a5c5-81f9fdce6be1	FRIDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	d54b130a-28a8-4bb1-9030-c474369c2205
579b400a-100a-4856-89ad-e25447144063	MONDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0dab7eba-8608-4548-bb27-54a735af58f5	TUESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
153bebbb-9d97-4b05-95b4-3f6a4b9eb075	WEDNESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e14592d7-c211-453f-a400-0e457724a4f1	THURSDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
aeabe108-09c2-4c2c-bfa7-0623e425eb99	FRIDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d8f029fc-c603-42c3-885d-50c02fc16d12	TUESDAY	f7fb4d07-cc8b-449a-a860-2100d2334658	d54b130a-28a8-4bb1-9030-c474369c2205
68b3b04a-9603-4f50-9ae1-73d7cc20c4f9	THURSDAY	a6c524c4-6748-414d-99dc-443680f2ba63	c33e01ef-be92-474a-9e43-0543649735d5
4852b5b1-0e9c-4229-b71f-6d2d135bac04	MONDAY	f7ea46a8-2dd4-4a5a-88b9-9b964beb68e7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1672587f-6513-4184-8d70-c224e2db11e2	MONDAY	f7ea46a8-2dd4-4a5a-88b9-9b964beb68e7	d54b130a-28a8-4bb1-9030-c474369c2205
62ecd946-42c9-493a-b646-89ba45ab2a64	WEDNESDAY	72d02727-c162-43b9-a3ab-9b3e8dc4cbd5	c33e01ef-be92-474a-9e43-0543649735d5
925488ce-f142-4dde-a016-31d3c0012515	FRIDAY	72d02727-c162-43b9-a3ab-9b3e8dc4cbd5	c33e01ef-be92-474a-9e43-0543649735d5
95cfc85a-4530-4a08-82f1-d861e8185d67	WEDNESDAY	62d4edd3-b54c-4f35-aef9-d0850f792260	c33e01ef-be92-474a-9e43-0543649735d5
57f0fdae-8a09-40c3-8538-5de18329eba8	MONDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	440c17ed-5262-4800-aa19-38e5285330c3
c46d8658-f990-4d0f-b625-4638bc60b49c	TUESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	440c17ed-5262-4800-aa19-38e5285330c3
7e51524a-4efb-46a3-be52-813228231828	WEDNESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	440c17ed-5262-4800-aa19-38e5285330c3
6519bcf9-86f3-4f93-ad8e-df38b129105d	THURSDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	440c17ed-5262-4800-aa19-38e5285330c3
540125e3-9c79-4259-98d3-aac6cc29f03d	FRIDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	440c17ed-5262-4800-aa19-38e5285330c3
5f5b140b-a672-4b78-aaf4-823f62685ea9	MONDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cdae1265-6d78-412e-89cd-b626ec1fa5ca	TUESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cc0f1f9f-0b7f-4e74-86c6-61e37c6c4132	WEDNESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6c6ac973-2511-4229-b2db-36186f480c9b	TUESDAY	a466ef46-1050-4081-8191-f15c08a9f6db	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e9a44565-71aa-4d8c-a7ce-4ef011a9230f	TUESDAY	a466ef46-1050-4081-8191-f15c08a9f6db	d54b130a-28a8-4bb1-9030-c474369c2205
73922f4d-58f5-4bf2-becb-8001aaaacc86	MONDAY	99e29aa2-9c26-4d09-9206-6173b7956d7d	6f1475e5-0d8a-47c7-855f-ccff802aff85
f50137fd-c695-4e94-89d0-891b3d19b815	MONDAY	99e29aa2-9c26-4d09-9206-6173b7956d7d	58845959-0984-4136-a733-b75f5c9057b9
802e4fc4-acb5-42ca-b52b-3d1ed786942c	MONDAY	6c6074b4-62fc-4312-91e6-2e8a9196aca7	58845959-0984-4136-a733-b75f5c9057b9
bedd2e98-8e66-4c0d-a9de-eb5518624f20	MONDAY	6c6074b4-62fc-4312-91e6-2e8a9196aca7	6f1475e5-0d8a-47c7-855f-ccff802aff85
25fc7231-9d04-4390-9e74-bddd5259293f	THURSDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ad8790bb-5ef6-4e1d-a0c7-fb3084051cd2	MONDAY	0784ce65-0cea-4000-858f-a5bc87d86e8f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
45393c81-7fad-42c2-aed7-1ce132ca1ceb	MONDAY	6b410de7-f666-40c0-ad19-6e7d74d0a3d7	d54b130a-28a8-4bb1-9030-c474369c2205
eed24ae6-0ff3-4730-b95d-89cdf6b92092	TUESDAY	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904	5f76a266-8d18-4da8-9732-b63d4d8f24a0
74078da4-cc5d-4310-adfa-edf73be3222d	TUESDAY	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904	d54b130a-28a8-4bb1-9030-c474369c2205
e8d9617d-901b-459b-b3a3-0d2e9a535ef0	THURSDAY	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d	58845959-0984-4136-a733-b75f5c9057b9
c99f6b05-e80c-41da-9c47-d577fc381695	MONDAY	8c6942ac-e020-4a00-acc4-8c41409854ab	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e56e9a77-e43f-4815-a8b1-47ad415bda09	MONDAY	8c6942ac-e020-4a00-acc4-8c41409854ab	c33e01ef-be92-474a-9e43-0543649735d5
de186074-95b7-414e-875b-90a218c281e9	FRIDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
826ae327-4427-4ee0-bfb2-e7d94a682bd2	MONDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	d54b130a-28a8-4bb1-9030-c474369c2205
cee016ec-011c-42e8-9121-0dd8d951b9c4	TUESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	d54b130a-28a8-4bb1-9030-c474369c2205
69eed3a3-ca33-4d7e-9210-968601c19e46	WEDNESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	d54b130a-28a8-4bb1-9030-c474369c2205
2409571c-ee0f-4480-9d51-4e86108b85ae	THURSDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	d54b130a-28a8-4bb1-9030-c474369c2205
afc3491d-bb06-4079-aa5e-8acf087057f4	FRIDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	d54b130a-28a8-4bb1-9030-c474369c2205
1cd9583d-db73-44d9-840a-d9d85dfdac31	MONDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a1b8b14f-3ee8-4d64-8d95-1a0305d68a44	TUESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
770d8600-769b-485c-830b-11d7e65cbbbb	WEDNESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4b497e5d-1347-4254-8102-61fb7f1797ab	THURSDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3929bba7-c681-46ba-83b9-cf85263770d4	FRIDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4d65f832-fd4d-4ea8-86da-ffaa4dca69df	MONDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	c33e01ef-be92-474a-9e43-0543649735d5
896ba642-17b4-4276-85df-016df35f2e7f	TUESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	c33e01ef-be92-474a-9e43-0543649735d5
17b00d06-a58b-47a7-9ca1-af0697350c5c	WEDNESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	c33e01ef-be92-474a-9e43-0543649735d5
d9b81c5c-0d39-4603-adbe-1c9913707ef5	THURSDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	c33e01ef-be92-474a-9e43-0543649735d5
a317d1d6-d7cd-4966-ab72-dd62173c643e	TUESDAY	d9ee24ed-b9e2-41d8-b733-bc8f48b7ae0a	c33e01ef-be92-474a-9e43-0543649735d5
2b8c7b1c-5436-4622-8a88-f80269633033	THURSDAY	09be9784-f02e-4c2d-accb-fd49b529e15a	c33e01ef-be92-474a-9e43-0543649735d5
d2fb0720-4529-4a21-9509-55575514dbb2	THURSDAY	320d7753-9513-472c-ac95-cc9567a36cc0	c33e01ef-be92-474a-9e43-0543649735d5
5779c1b9-4d23-4766-bdad-83a22241d0ad	WEDNESDAY	fbd5f933-b196-48bb-b49a-f8f2b1bcf563	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cca697d2-c963-46cc-a820-c5dfe7c8b43e	THURSDAY	e9d67836-a72d-4802-a9f0-25a15e1042e5	6f1475e5-0d8a-47c7-855f-ccff802aff85
efcbbc7f-ee96-4f63-aaa4-c2c1dd5e1939	THURSDAY	e9d67836-a72d-4802-a9f0-25a15e1042e5	c33e01ef-be92-474a-9e43-0543649735d5
2677303f-211f-4dd4-beae-e5d30db797a9	THURSDAY	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed	440c17ed-5262-4800-aa19-38e5285330c3
453e1fa0-82d8-42d6-a659-f62eb8ea9d30	THURSDAY	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7ee6cdae-8f07-47d1-8e8f-574b7b4f7790	WEDNESDAY	e1482fe4-3872-4f46-a51b-9a648b9a5072	d54b130a-28a8-4bb1-9030-c474369c2205
98841080-7753-4a85-9940-4c82becc592a	WEDNESDAY	e1482fe4-3872-4f46-a51b-9a648b9a5072	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
238653c0-4e6f-4193-ad07-16fdb82a4abb	FRIDAY	e65a3a55-59ca-4533-bdf2-ba8511d594ad	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f5c13de4-2f6a-4be3-b8e9-677f73591570	FRIDAY	e65a3a55-59ca-4533-bdf2-ba8511d594ad	d54b130a-28a8-4bb1-9030-c474369c2205
ba681f37-e35e-47ca-8c57-46fdc9c7b643	MONDAY	1c7340c1-7b05-404f-8882-39906697c69c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3d0dd24b-800b-4e0d-b413-e7aca661d491	MONDAY	1c7340c1-7b05-404f-8882-39906697c69c	c33e01ef-be92-474a-9e43-0543649735d5
dfda4fd5-812f-4a35-8c7f-1116b17293dc	THURSDAY	2e103b9f-097b-4d2d-9ee9-8806713621ce	d54b130a-28a8-4bb1-9030-c474369c2205
5588aee8-86f2-4b09-b145-bafea52b0eb8	THURSDAY	2e103b9f-097b-4d2d-9ee9-8806713621ce	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ea748b63-d273-465f-ac0e-db90be1d07a3	MONDAY	885428c2-75f8-47f6-80a6-94337b9fb10f	c33e01ef-be92-474a-9e43-0543649735d5
76cf3a23-9e03-4218-ab3f-8055594dceb7	FRIDAY	b0853a08-38a4-47f5-b9aa-83f315d74acf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
adc72e22-285a-4418-862d-c580b68a7de2	FRIDAY	b0853a08-38a4-47f5-b9aa-83f315d74acf	d54b130a-28a8-4bb1-9030-c474369c2205
479ebfcd-fa6f-4c2d-946b-572463b9b7d1	MONDAY	edcf6bf0-5a1a-41c6-ac00-56a63327adbb	6f1475e5-0d8a-47c7-855f-ccff802aff85
5f96732f-a4aa-4408-982e-167831af1881	MONDAY	edcf6bf0-5a1a-41c6-ac00-56a63327adbb	58845959-0984-4136-a733-b75f5c9057b9
3242e696-732f-4a10-9c78-6fee8afb8608	THURSDAY	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b67743e9-78fa-4b5a-a59e-5359c9a565b8	THURSDAY	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d	440c17ed-5262-4800-aa19-38e5285330c3
d2ed2e90-fb6f-499c-adb8-2f13d1ded722	TUESDAY	f058e2d3-bcd6-46c2-92c0-3e0aa4e71757	5f76a266-8d18-4da8-9732-b63d4d8f24a0
19b0f8e0-af47-4d24-a4c5-2d67269dad0c	TUESDAY	f058e2d3-bcd6-46c2-92c0-3e0aa4e71757	d54b130a-28a8-4bb1-9030-c474369c2205
a60b0d86-dd17-4328-80ba-10f8fca41cc6	THURSDAY	f65faea9-66ad-40ce-8488-753078555a3f	6f1475e5-0d8a-47c7-855f-ccff802aff85
b5a5bf6e-536f-4010-9289-1711fea5d643	THURSDAY	f65faea9-66ad-40ce-8488-753078555a3f	c33e01ef-be92-474a-9e43-0543649735d5
c763bf78-0b21-4301-87bd-b50474552781	WEDNESDAY	c2380f71-ff90-46f3-8438-49f91616f6a6	440c17ed-5262-4800-aa19-38e5285330c3
e7727395-4b24-4eb2-996d-f1aa12effcda	WEDNESDAY	c2380f71-ff90-46f3-8438-49f91616f6a6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
10d8efb3-a369-49cb-bf93-f6e0873e91aa	FRIDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	c33e01ef-be92-474a-9e43-0543649735d5
ba47fe92-572d-44ad-b80a-df45d4827dd1	MONDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	6f1475e5-0d8a-47c7-855f-ccff802aff85
d926e5be-dfa2-42ad-aec9-f0fe5b972dc9	TUESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	6f1475e5-0d8a-47c7-855f-ccff802aff85
7059d9a7-b3a6-4c5e-903f-4aa2bf677abd	WEDNESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	6f1475e5-0d8a-47c7-855f-ccff802aff85
d64e81d7-a4dd-43f5-b3bb-1ebe5aadfd9b	TUESDAY	f4e3291c-758b-47f4-a1d8-75680a84be0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ad5866dc-8cfd-44cd-8270-3ba320b687c2	TUESDAY	f4e3291c-758b-47f4-a1d8-75680a84be0f	d54b130a-28a8-4bb1-9030-c474369c2205
dc9555cc-d363-4870-9214-593969c60547	WEDNESDAY	32e48d76-6004-4a1c-bb22-27ee66e2d672	58845959-0984-4136-a733-b75f5c9057b9
70ba729c-1567-4f89-9ea1-0ac2aa613088	WEDNESDAY	22b88adb-3e66-4b08-86d5-c6e6ecc7399f	440c17ed-5262-4800-aa19-38e5285330c3
99f6b381-2fe7-4301-a1e8-f1148a5cb467	WEDNESDAY	8638f31d-dd3a-41df-8571-bcb665c48554	440c17ed-5262-4800-aa19-38e5285330c3
dfbd643b-6890-49bf-b278-ff4da0e2cfae	WEDNESDAY	8638f31d-dd3a-41df-8571-bcb665c48554	5f76a266-8d18-4da8-9732-b63d4d8f24a0
58d65b5f-c209-4025-872e-b6944d8312f2	SATURDAY	e43a754b-648f-460b-88c6-0d3825b1fa76	440c17ed-5262-4800-aa19-38e5285330c3
f94bbaab-06cf-4c5b-abeb-0c20c7debde0	SATURDAY	e43a754b-648f-460b-88c6-0d3825b1fa76	d54b130a-28a8-4bb1-9030-c474369c2205
f91bbac7-5f0a-4550-892b-2c0158eb63a0	SATURDAY	e43a754b-648f-460b-88c6-0d3825b1fa76	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f738831c-ef74-47f3-b687-ee1ac22fd3c2	SATURDAY	e43a754b-648f-460b-88c6-0d3825b1fa76	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0d0c576e-ee53-498c-9ccc-86352b924b48	THURSDAY	94ad2b6d-975f-4746-9a3b-33c32b34a13f	58845959-0984-4136-a733-b75f5c9057b9
03a391d2-8ddd-410b-a1c3-aeeae17be6ee	MONDAY	a9ca51e9-864d-4d17-b647-ff17ea2c02e6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f307e0d9-ea80-466c-b9d9-fc18bc8e4492	MONDAY	69a94b81-7ed0-428b-a3c9-679a34e47e0a	c33e01ef-be92-474a-9e43-0543649735d5
0bb74bcf-c912-4de8-938e-8fa9e9994196	MONDAY	69a94b81-7ed0-428b-a3c9-679a34e47e0a	6f1475e5-0d8a-47c7-855f-ccff802aff85
8948a6bf-ff94-4fc1-9be1-bf2fbf72a641	TUESDAY	e64236c3-e2ea-45a1-8a28-c9c596267c97	c33e01ef-be92-474a-9e43-0543649735d5
cba08b64-9ccf-4bc7-aaa5-f44fd81d22b9	TUESDAY	e64236c3-e2ea-45a1-8a28-c9c596267c97	58845959-0984-4136-a733-b75f5c9057b9
b2a08b7d-078f-4600-a457-71bd0a36a945	TUESDAY	e64236c3-e2ea-45a1-8a28-c9c596267c97	6f1475e5-0d8a-47c7-855f-ccff802aff85
124b7a17-3487-47b2-9717-5ec15a82fff3	TUESDAY	ae4d3b3b-f1b9-456d-89b3-84dfdafad01f	c33e01ef-be92-474a-9e43-0543649735d5
285efed5-7573-4139-8877-c078e097c6c0	TUESDAY	ae4d3b3b-f1b9-456d-89b3-84dfdafad01f	6f1475e5-0d8a-47c7-855f-ccff802aff85
0da8dabd-991f-4ff5-a449-f4b64e6a43ed	MONDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	440c17ed-5262-4800-aa19-38e5285330c3
d2623ac1-d4d0-446e-aaf6-af9d9972b1b2	TUESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	440c17ed-5262-4800-aa19-38e5285330c3
d8b58b07-0761-4b0d-a1a9-acb7c1523fff	WEDNESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	440c17ed-5262-4800-aa19-38e5285330c3
86c9f0c8-6535-45e1-a967-fa43cf783520	THURSDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	440c17ed-5262-4800-aa19-38e5285330c3
8b76ac6b-274b-4e37-96dc-81784808e296	FRIDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	440c17ed-5262-4800-aa19-38e5285330c3
19094274-7ea5-43ec-81d9-de81c7e1c8d5	MONDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	5f76a266-8d18-4da8-9732-b63d4d8f24a0
58ed423f-8bb3-4383-8225-90ac3096d26b	TUESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	5f76a266-8d18-4da8-9732-b63d4d8f24a0
22b9f12d-92ae-4efc-82b3-8f98bed6d3be	WEDNESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9b1d7e0e-8cfe-45e9-84cf-95c29b20700d	THURSDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0c9a2e64-89d9-4a82-9f92-bb377c44abe4	FRIDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	5f76a266-8d18-4da8-9732-b63d4d8f24a0
42e028a6-01f8-4596-85f4-665048d42c70	MONDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	d54b130a-28a8-4bb1-9030-c474369c2205
87d33f57-405e-4827-80e6-f1c8ba77d14e	TUESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	d54b130a-28a8-4bb1-9030-c474369c2205
dfe24436-b887-4896-88ef-fa225baaa94a	WEDNESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	d54b130a-28a8-4bb1-9030-c474369c2205
ea4849fc-c1ae-4a76-8367-500916579373	THURSDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	d54b130a-28a8-4bb1-9030-c474369c2205
489063b1-821d-4401-a76f-55bee5fa6fb9	FRIDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	d54b130a-28a8-4bb1-9030-c474369c2205
45ba1216-92fe-4f2e-83bf-b46fc999e69b	MONDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	c33e01ef-be92-474a-9e43-0543649735d5
944c405f-3555-4ee3-950c-416e9e344e72	TUESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	c33e01ef-be92-474a-9e43-0543649735d5
a165d35b-19b1-4800-8d61-bac4ea30151c	WEDNESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	c33e01ef-be92-474a-9e43-0543649735d5
e2d05d7b-d210-4750-aead-8502989dcd22	THURSDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	c33e01ef-be92-474a-9e43-0543649735d5
4b64623f-71ba-4087-a9f4-db5fa56564f3	FRIDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	c33e01ef-be92-474a-9e43-0543649735d5
4ff87732-078a-48a6-ae88-97cf5b06fa36	MONDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	6f1475e5-0d8a-47c7-855f-ccff802aff85
48488dba-416f-44a0-80ae-d2748d3e09f0	TUESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	6f1475e5-0d8a-47c7-855f-ccff802aff85
e7f3cc35-0a0e-4ed1-866a-f596e0a4352c	WEDNESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	6f1475e5-0d8a-47c7-855f-ccff802aff85
5322c8b5-6ba0-4148-abc2-d8c8454fdda8	THURSDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	6f1475e5-0d8a-47c7-855f-ccff802aff85
0d07610d-1de4-4086-8bf1-499d5aa44ad1	FRIDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	6f1475e5-0d8a-47c7-855f-ccff802aff85
0ba1aa2a-3ebd-45d7-8e5c-2ad4ab690dd4	MONDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	58845959-0984-4136-a733-b75f5c9057b9
b1cf6e0b-ab4c-47c7-bf3d-22e01fa3f4bb	TUESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	58845959-0984-4136-a733-b75f5c9057b9
485d257c-8d22-47dd-ac13-5eec5fbfc666	WEDNESDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	58845959-0984-4136-a733-b75f5c9057b9
07a5f683-d88c-407e-a69c-760e12d4e9f2	THURSDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	58845959-0984-4136-a733-b75f5c9057b9
b2a2d2df-0c31-447b-900b-ca9858977d34	FRIDAY	58494593-79cc-411e-ad7f-2b52a7fc6095	58845959-0984-4136-a733-b75f5c9057b9
42b58880-d09e-445c-b0c9-b821d8af0500	THURSDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	6f1475e5-0d8a-47c7-855f-ccff802aff85
b587cda3-d4f2-4bad-816b-d9c78a998891	FRIDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	6f1475e5-0d8a-47c7-855f-ccff802aff85
a5592019-b423-4e43-8804-0c170795615b	MONDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	58845959-0984-4136-a733-b75f5c9057b9
e9849390-fe75-4164-b42e-99a0b07bebb6	TUESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	58845959-0984-4136-a733-b75f5c9057b9
c7061d25-0e0e-4ed1-9c11-2a6401c24b88	WEDNESDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	58845959-0984-4136-a733-b75f5c9057b9
75e465a8-86a0-4671-8114-9ec1eb4ca10f	THURSDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	58845959-0984-4136-a733-b75f5c9057b9
e6a48947-aef4-4e53-bfe9-33aa83cf48af	FRIDAY	b0956325-1cdb-425f-a9a6-afb752a5485d	58845959-0984-4136-a733-b75f5c9057b9
5808c465-e4ad-4349-9cf5-a887cbca7e08	MONDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	440c17ed-5262-4800-aa19-38e5285330c3
74e0be13-8ce8-4fbd-9cd4-3e98f2c4d647	TUESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	440c17ed-5262-4800-aa19-38e5285330c3
4e6fd66b-d7c0-41fa-913c-19c7e7af47b2	WEDNESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	440c17ed-5262-4800-aa19-38e5285330c3
e3923fd7-f7bf-4cdf-9313-06f34c862ba1	THURSDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	440c17ed-5262-4800-aa19-38e5285330c3
777a8951-7d49-4f2c-9b98-c490f37cf21e	FRIDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	440c17ed-5262-4800-aa19-38e5285330c3
dc91c3d7-86f3-44f7-b3ca-4a28ec41dda2	MONDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a085cc9c-35f0-47fc-86e7-9a3ed73c8f82	TUESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d1422439-6be9-46e7-9f32-62c0db30821b	WEDNESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4ab2123a-1d59-4a25-b56b-b59ef0019424	THURSDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8eb793b8-fe03-43a3-aaab-f27780f07f2a	FRIDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
de7c246b-19bc-422d-a7ea-381b7709a69b	MONDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	d54b130a-28a8-4bb1-9030-c474369c2205
751fe3ba-06d6-4e1c-bed7-e19a8ed601b9	TUESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	d54b130a-28a8-4bb1-9030-c474369c2205
fa8b4a39-3fbd-45f7-80af-bfcdbcfbfa51	WEDNESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	d54b130a-28a8-4bb1-9030-c474369c2205
e79f5ce2-8c59-4c9b-9d99-cf60ac3bc1f4	THURSDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	d54b130a-28a8-4bb1-9030-c474369c2205
5a04d206-4d8e-4072-9cca-7102168b3407	FRIDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	d54b130a-28a8-4bb1-9030-c474369c2205
e276b510-f6c5-4cc1-b6f8-a90a5446feb4	MONDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6b4ca7c9-2658-4ed6-a5df-485df7f2bd8a	TUESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
16d7ba54-9793-4925-9601-c18bd140d347	WEDNESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
670df1de-0fe3-42f4-a44c-f23b160a5d85	THURSDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
fd3f8caf-34eb-4145-ad8b-7ee61acb713e	FRIDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
26267c75-98e4-41a6-9fcd-cfaa7bc5eafa	MONDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	c33e01ef-be92-474a-9e43-0543649735d5
7d12d273-9fa0-4370-ab7e-8068a1ab35ac	TUESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	c33e01ef-be92-474a-9e43-0543649735d5
94de21ef-c1a1-4395-a624-cf4b1d58d26c	WEDNESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	c33e01ef-be92-474a-9e43-0543649735d5
8ccfb07a-2e0d-43e5-9740-46acab34f2c4	THURSDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	c33e01ef-be92-474a-9e43-0543649735d5
076062b2-173d-42e3-a9ee-9dabd1c04ee5	FRIDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	c33e01ef-be92-474a-9e43-0543649735d5
c02404f2-9655-4684-bc81-31ab7d338796	MONDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	6f1475e5-0d8a-47c7-855f-ccff802aff85
ffd06edd-6d06-4588-82ee-429ef2c577c7	TUESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	6f1475e5-0d8a-47c7-855f-ccff802aff85
12b5cf60-6eb9-446a-99fc-98a7f9dc14e1	WEDNESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	6f1475e5-0d8a-47c7-855f-ccff802aff85
a5f5a136-a522-406f-ac80-417f066bb529	THURSDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	6f1475e5-0d8a-47c7-855f-ccff802aff85
418d3993-cf59-41a7-9784-71f509dcf112	FRIDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	6f1475e5-0d8a-47c7-855f-ccff802aff85
7045cd69-46ea-4d1b-b581-825e5cdb1850	MONDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	58845959-0984-4136-a733-b75f5c9057b9
15a26a21-042c-4bed-9b84-4ec97ea5ab4c	TUESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	58845959-0984-4136-a733-b75f5c9057b9
dae93458-b18d-4763-a788-d727f71d3ad9	MONDAY	47f982fc-369e-473c-9ab0-8096bae012e0	440c17ed-5262-4800-aa19-38e5285330c3
09b56ec6-2fa8-4663-8112-56f83cd931e6	TUESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	440c17ed-5262-4800-aa19-38e5285330c3
88544a7a-21cb-4394-abf5-d2646ef2d9ce	WEDNESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	440c17ed-5262-4800-aa19-38e5285330c3
72eaae08-5958-46b9-91e9-cb288a7cc2ca	THURSDAY	47f982fc-369e-473c-9ab0-8096bae012e0	440c17ed-5262-4800-aa19-38e5285330c3
90b327ae-4c41-460a-9ba6-dd267d692808	FRIDAY	47f982fc-369e-473c-9ab0-8096bae012e0	440c17ed-5262-4800-aa19-38e5285330c3
e4f8b23b-1f6c-43de-9cf9-fc2127069718	MONDAY	47f982fc-369e-473c-9ab0-8096bae012e0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
94cef19a-c967-4645-9e45-7cff1911e09a	TUESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
85a45b89-da4e-4f90-b8bb-4b814570af81	WEDNESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
37e625db-1dad-4309-bbb7-9bfa0daaab95	THURSDAY	47f982fc-369e-473c-9ab0-8096bae012e0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f51e0fd2-48ac-4bf0-94fc-1bc1f1770d39	FRIDAY	47f982fc-369e-473c-9ab0-8096bae012e0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
304be3f6-01a4-4643-84bc-3aec639e7f9b	MONDAY	47f982fc-369e-473c-9ab0-8096bae012e0	d54b130a-28a8-4bb1-9030-c474369c2205
33e8f9b9-d109-4982-80bb-c9a9bed35f4a	TUESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	d54b130a-28a8-4bb1-9030-c474369c2205
876fa986-7111-4e79-8d2f-313998c535c0	WEDNESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	d54b130a-28a8-4bb1-9030-c474369c2205
1ee1978f-def2-4b05-bc31-4980b4595ef6	THURSDAY	47f982fc-369e-473c-9ab0-8096bae012e0	d54b130a-28a8-4bb1-9030-c474369c2205
2c92e71f-f5c2-4222-9fcc-97deaa5f1229	FRIDAY	47f982fc-369e-473c-9ab0-8096bae012e0	d54b130a-28a8-4bb1-9030-c474369c2205
b14843b3-6bd0-4dc3-a3f8-dcfae776086c	MONDAY	47f982fc-369e-473c-9ab0-8096bae012e0	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1d9902f3-5240-42b1-ab3d-9b024e455fea	TUESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1c24bedb-b757-4a42-84a1-80c913a17a79	WEDNESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
df36a412-7f35-45ad-ad66-0e261b1ce83b	THURSDAY	47f982fc-369e-473c-9ab0-8096bae012e0	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7fccafba-4abe-4dc9-8221-fa08b20408d6	FRIDAY	47f982fc-369e-473c-9ab0-8096bae012e0	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
28bcdf2d-5289-4dbf-907b-8fa083cf4cf0	MONDAY	47f982fc-369e-473c-9ab0-8096bae012e0	c33e01ef-be92-474a-9e43-0543649735d5
fc3e1dd8-6888-4f01-b375-5ef75ba61aae	TUESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	c33e01ef-be92-474a-9e43-0543649735d5
7013282e-65da-45c6-adad-c550c8d010e5	WEDNESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	c33e01ef-be92-474a-9e43-0543649735d5
73a64228-8e61-463d-90f3-c73c6e5420ca	THURSDAY	47f982fc-369e-473c-9ab0-8096bae012e0	c33e01ef-be92-474a-9e43-0543649735d5
32c15cf1-8463-48f1-9350-98b5fb97df34	FRIDAY	47f982fc-369e-473c-9ab0-8096bae012e0	c33e01ef-be92-474a-9e43-0543649735d5
ea9921ec-d456-41fc-811b-43be379047a3	MONDAY	47f982fc-369e-473c-9ab0-8096bae012e0	6f1475e5-0d8a-47c7-855f-ccff802aff85
8f000635-0127-43f0-9c27-a47997bf335b	TUESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	6f1475e5-0d8a-47c7-855f-ccff802aff85
3c21fd9c-ddcb-43d1-8f78-dcd9ce405369	WEDNESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	6f1475e5-0d8a-47c7-855f-ccff802aff85
aae58cc1-5ee8-490e-958e-64b07bed8b74	THURSDAY	47f982fc-369e-473c-9ab0-8096bae012e0	6f1475e5-0d8a-47c7-855f-ccff802aff85
e8a2d5a1-cb34-4a50-bf03-0a1735cd3ad9	FRIDAY	47f982fc-369e-473c-9ab0-8096bae012e0	6f1475e5-0d8a-47c7-855f-ccff802aff85
ab247369-13fa-4aae-b155-2e355d066647	MONDAY	47f982fc-369e-473c-9ab0-8096bae012e0	58845959-0984-4136-a733-b75f5c9057b9
dc091b31-0d37-484b-8805-cd537434b9a2	TUESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	58845959-0984-4136-a733-b75f5c9057b9
1d15f7e0-dcad-4e5d-93cf-e3b9261d5295	WEDNESDAY	47f982fc-369e-473c-9ab0-8096bae012e0	58845959-0984-4136-a733-b75f5c9057b9
641bd9dd-b43a-449c-81d9-56cda8c05ec0	THURSDAY	47f982fc-369e-473c-9ab0-8096bae012e0	58845959-0984-4136-a733-b75f5c9057b9
cf56639f-7aa6-4ca7-a5dc-47af5d77d4f4	FRIDAY	47f982fc-369e-473c-9ab0-8096bae012e0	58845959-0984-4136-a733-b75f5c9057b9
8ce572b7-7142-442a-beb6-da6836c497bb	MONDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	440c17ed-5262-4800-aa19-38e5285330c3
5f57d54a-e7ea-46f8-989f-5de81d04b514	TUESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	440c17ed-5262-4800-aa19-38e5285330c3
bce9ebaa-9e41-4e63-b76d-503d56162883	WEDNESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	440c17ed-5262-4800-aa19-38e5285330c3
5a65c146-babd-4427-a29b-e6bc81446da9	THURSDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	440c17ed-5262-4800-aa19-38e5285330c3
ad4152b4-693e-4b3e-b521-eae36ac3a742	FRIDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	440c17ed-5262-4800-aa19-38e5285330c3
2f7b90e5-9b1b-41b7-b64e-da09777c1409	MONDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3f3c5a28-2019-4e19-8ac4-b10e0bc2c4e6	TUESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	5f76a266-8d18-4da8-9732-b63d4d8f24a0
56ef732d-4c10-4ad1-919a-c2461db443ac	WEDNESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4d5f4575-8e57-4986-b530-a748d78052ce	THURSDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2ac6251c-a840-48b0-ae52-ad4b916ad4c0	FRIDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1f715d08-77c4-4675-8f01-eb6f864cc294	MONDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	d54b130a-28a8-4bb1-9030-c474369c2205
50fa39e8-e4b7-48f2-b4ed-9a23472f2b77	TUESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	d54b130a-28a8-4bb1-9030-c474369c2205
5a73fa06-7086-4c2a-bc37-d2c64e321ed5	WEDNESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	d54b130a-28a8-4bb1-9030-c474369c2205
e90bf5bf-7d64-49b2-961b-d85a5b3315ee	THURSDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	d54b130a-28a8-4bb1-9030-c474369c2205
b29b07a1-ae54-49d7-b172-44b2b8423b73	FRIDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	d54b130a-28a8-4bb1-9030-c474369c2205
b263926d-93ef-4e4a-85f0-49b95e541836	MONDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a8b662b7-9818-42c9-9316-e2f92ec1a74b	TUESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cbf9c672-ea63-4ea3-a675-0f1ed118b579	WEDNESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5fc42ec9-0b8a-49f5-bf59-f7d2523d7ff3	THURSDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b178a5f3-bb06-4730-a94c-add42c82d236	FRIDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1dd5304c-1a49-48bb-97c5-e4ccf1b00d29	MONDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	c33e01ef-be92-474a-9e43-0543649735d5
4cd6db80-7a84-4972-82ed-78551632d23c	TUESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	c33e01ef-be92-474a-9e43-0543649735d5
3f22d0f2-30ce-41e9-b59f-1e399ef64141	WEDNESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	c33e01ef-be92-474a-9e43-0543649735d5
bdf942f2-71ec-4d03-b19f-3788312ef094	THURSDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	c33e01ef-be92-474a-9e43-0543649735d5
24c14982-c272-4caf-836e-0693e2eeb2dc	FRIDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	c33e01ef-be92-474a-9e43-0543649735d5
9d9d32d1-a10a-4652-b594-ff8319ccf954	MONDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	6f1475e5-0d8a-47c7-855f-ccff802aff85
9723e352-30c2-45e7-8602-26b55ee69b92	TUESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	6f1475e5-0d8a-47c7-855f-ccff802aff85
41fbe47f-8904-4913-9ca2-2a143ca65b1d	WEDNESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	6f1475e5-0d8a-47c7-855f-ccff802aff85
e0dbd14f-a2ee-4739-a810-76f5d5e0dc87	THURSDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	6f1475e5-0d8a-47c7-855f-ccff802aff85
54d304cf-5761-4484-a5da-1828b4b47b24	FRIDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	6f1475e5-0d8a-47c7-855f-ccff802aff85
55f1e0d0-733f-40e8-9ef3-3796e527a586	MONDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	58845959-0984-4136-a733-b75f5c9057b9
d905cdbd-6e5d-494b-ade6-6af958bdb7a3	TUESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	58845959-0984-4136-a733-b75f5c9057b9
5048854f-91f8-462e-984e-8109cd8a0159	WEDNESDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	58845959-0984-4136-a733-b75f5c9057b9
526adce0-8d9f-4ee1-bbc2-e02c7397e780	THURSDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	58845959-0984-4136-a733-b75f5c9057b9
0d9d1f56-5d58-413b-9ce6-fb30c298eb10	FRIDAY	9b90fa62-a329-4089-b71f-f9a70c27ac03	58845959-0984-4136-a733-b75f5c9057b9
1c409bd6-d8a7-4195-9f80-65749480914a	MONDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	440c17ed-5262-4800-aa19-38e5285330c3
e542abd8-88fa-4aea-a5c9-33571cf3f460	TUESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	440c17ed-5262-4800-aa19-38e5285330c3
bbb0a9d6-f725-4575-b136-36555b0c78e2	WEDNESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	440c17ed-5262-4800-aa19-38e5285330c3
495da70a-fd9a-479e-ae1a-89d7718d8a0d	THURSDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	440c17ed-5262-4800-aa19-38e5285330c3
6349c81d-1a1c-4b25-86a9-ed21ca3619dd	FRIDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	440c17ed-5262-4800-aa19-38e5285330c3
0da27412-e03d-443c-b36e-dabf8b807cf5	MONDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
99c7299f-2d4d-444c-b5ef-22a66b0a9f13	TUESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
12ea946d-488a-4ebd-a1bd-18398cbff604	WEDNESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
aa7b7f3b-efbf-4924-bb34-bd9e3f9adcdc	THURSDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f9244b23-309b-480b-b235-ed77743bd832	FRIDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ffd34f4e-2437-44fa-9bbf-4ea87faf0542	MONDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	d54b130a-28a8-4bb1-9030-c474369c2205
0c3fcc4a-5c91-45f6-881d-a9a9c3e702c3	TUESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	d54b130a-28a8-4bb1-9030-c474369c2205
898486d2-1b50-493d-b80b-143dc114e3c0	WEDNESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	d54b130a-28a8-4bb1-9030-c474369c2205
21c622ea-97a5-4908-9294-fb725a8ee81c	THURSDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	d54b130a-28a8-4bb1-9030-c474369c2205
e07f424b-7853-4d27-90ab-b446260d156a	FRIDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	d54b130a-28a8-4bb1-9030-c474369c2205
3ba61441-2ab5-461a-88be-e5d38017a08a	MONDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
098eac06-894d-4c80-a063-b8f58bbe756d	TUESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2ce5d20d-8eed-4bfb-9fc9-8e8872bebd87	WEDNESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d3e38ac1-4964-4f6c-87c9-01aea001b454	THURSDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2be24f72-b6f1-4ad9-ab34-7bcbceb47800	FRIDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
71a6fc91-f42e-4b54-9225-9dbf54d6a6de	MONDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	c33e01ef-be92-474a-9e43-0543649735d5
777880b6-1379-4471-a4c2-ec97ea612a2f	TUESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	c33e01ef-be92-474a-9e43-0543649735d5
d984c91a-185b-4b13-bb5f-33956d3cbd56	WEDNESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	c33e01ef-be92-474a-9e43-0543649735d5
9f10a124-44f4-4a8b-bdf7-0f7caa636dc7	THURSDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	c33e01ef-be92-474a-9e43-0543649735d5
01dd7180-54f5-4316-9e8d-850f591cd5ed	FRIDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	c33e01ef-be92-474a-9e43-0543649735d5
d9344f5c-2a10-4a17-bf02-e10971758491	MONDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	6f1475e5-0d8a-47c7-855f-ccff802aff85
86e38159-af07-4321-bbd4-eac42a280530	TUESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	6f1475e5-0d8a-47c7-855f-ccff802aff85
02e76b4f-2166-4303-af57-82bca380f8f5	WEDNESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	6f1475e5-0d8a-47c7-855f-ccff802aff85
d56827ce-41fb-445b-9cb5-66b54d0afcd9	THURSDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	6f1475e5-0d8a-47c7-855f-ccff802aff85
45110f7d-7768-44ce-8232-57a07c2f7d8b	FRIDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	6f1475e5-0d8a-47c7-855f-ccff802aff85
a5de7596-b3a3-4ffe-9392-e0815331b9c8	MONDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	58845959-0984-4136-a733-b75f5c9057b9
b4178e15-cb13-4ccc-9254-4f8bf9430f1d	TUESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	58845959-0984-4136-a733-b75f5c9057b9
6be95837-ab8e-4f98-a6ef-0b389205e476	WEDNESDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	58845959-0984-4136-a733-b75f5c9057b9
2f299380-55ca-4171-a5a0-0301c7330a45	THURSDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	58845959-0984-4136-a733-b75f5c9057b9
d7f999b5-993d-4cc4-a56c-18c4136d232a	FRIDAY	ba85e730-6df5-4e9f-aef0-96577fab369a	58845959-0984-4136-a733-b75f5c9057b9
6ca4c9f4-df00-4514-893f-1bda1a33ef8d	MONDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	440c17ed-5262-4800-aa19-38e5285330c3
75b2f4d8-c3e2-4f02-b92e-bc95f3390d70	TUESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	440c17ed-5262-4800-aa19-38e5285330c3
92c16e36-c96f-4ad0-9814-a903dda4555a	WEDNESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	440c17ed-5262-4800-aa19-38e5285330c3
3d68283c-7805-4d23-ae11-6f1a0ebb85c2	THURSDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	440c17ed-5262-4800-aa19-38e5285330c3
804bea02-3a45-4d90-a5ec-e5cbcb0d8ae7	FRIDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	440c17ed-5262-4800-aa19-38e5285330c3
bcdff267-901b-4ab0-94a9-de4eae4f9149	MONDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
edfbbc89-b282-4b77-bb4e-ba20ee0f3bb8	TUESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6fffe420-d3c1-4bec-b2a3-1405636c9cdb	WEDNESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e40f0e93-13b5-40de-8e72-7f74514fbea6	THURSDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
adcf4fc5-c3f0-4e05-af41-bf3dc0310fd8	FRIDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4f43ad48-6c89-4061-8d1d-4155613218f1	MONDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	d54b130a-28a8-4bb1-9030-c474369c2205
14d5a194-a7f1-4922-8255-2418e1e13ffd	TUESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	d54b130a-28a8-4bb1-9030-c474369c2205
b17aa342-1290-4852-81a3-d1a001c9585c	WEDNESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	d54b130a-28a8-4bb1-9030-c474369c2205
0f49b208-6168-487d-b69f-c10fd3d29b52	THURSDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	d54b130a-28a8-4bb1-9030-c474369c2205
39be06cf-9917-4e0e-8e27-075f82b8e96c	FRIDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	d54b130a-28a8-4bb1-9030-c474369c2205
bd213525-7e5c-4861-aa5c-dfcbe25b893d	MONDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
646d4008-cfd6-482c-ab4b-fd2537b739e3	TUESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
13a91b11-426a-451b-96db-418329567088	WEDNESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
23e99f11-872c-4d5d-83a2-e568838a2e6b	THURSDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e6bdf0df-2182-45a1-84d4-e328b6661ffb	FRIDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3fd6716a-6a1d-4263-9167-6b618cc76858	MONDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	c33e01ef-be92-474a-9e43-0543649735d5
fa8e9637-daec-411d-9f45-a810d8107e7c	TUESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	c33e01ef-be92-474a-9e43-0543649735d5
b7245dcb-b108-4b07-952f-47951e39d734	WEDNESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	c33e01ef-be92-474a-9e43-0543649735d5
7ab7acba-92c8-4e1c-8a08-bcebf448e35d	THURSDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	c33e01ef-be92-474a-9e43-0543649735d5
057be67d-6a0d-468c-a4fa-d3d983f7242f	FRIDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	c33e01ef-be92-474a-9e43-0543649735d5
60915e3e-79a9-481b-b735-304d807b8bfd	MONDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	6f1475e5-0d8a-47c7-855f-ccff802aff85
f0331b7d-b2bb-49f9-bb2e-49819c67bb41	TUESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	6f1475e5-0d8a-47c7-855f-ccff802aff85
5bd4de15-62be-4518-9780-29be059ee8cc	WEDNESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	6f1475e5-0d8a-47c7-855f-ccff802aff85
e48298a7-d154-48ff-ae50-f381b5f27df3	THURSDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	6f1475e5-0d8a-47c7-855f-ccff802aff85
fa7c1cbd-a363-4295-9d16-faccdf60b4f0	FRIDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	6f1475e5-0d8a-47c7-855f-ccff802aff85
091000ba-905d-42e9-81d7-5c8cb6a95b4a	MONDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	58845959-0984-4136-a733-b75f5c9057b9
9be4fd74-37c3-47a5-ab23-0054115c8faf	TUESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	58845959-0984-4136-a733-b75f5c9057b9
1e971459-36dd-4b09-9ef5-eeb407bf32f0	WEDNESDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	58845959-0984-4136-a733-b75f5c9057b9
35987c42-9a31-47dd-95b1-1e037d1d7169	THURSDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	58845959-0984-4136-a733-b75f5c9057b9
4aaba03c-1315-49ba-be03-a4b0db0693ac	FRIDAY	d44fa577-b407-4d90-972b-b3d39ab4b93d	58845959-0984-4136-a733-b75f5c9057b9
84a3b89d-5a36-4a9c-9e59-2d620789e03e	MONDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	440c17ed-5262-4800-aa19-38e5285330c3
7e4d2a1d-2056-4b7a-98fe-a4a5ee922996	TUESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	440c17ed-5262-4800-aa19-38e5285330c3
25c8b53c-8c48-41f5-b933-60a291132682	WEDNESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	440c17ed-5262-4800-aa19-38e5285330c3
d6bcd22b-d955-47e1-bd78-1f1a71d36484	MONDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	440c17ed-5262-4800-aa19-38e5285330c3
4b6cc0ca-e9a4-4e93-bd05-d73f60a18093	TUESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	440c17ed-5262-4800-aa19-38e5285330c3
419b6ec3-8155-4f21-90da-ee318abe180b	WEDNESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	440c17ed-5262-4800-aa19-38e5285330c3
5b2894d0-f152-4a61-bb10-c975f19ad91e	THURSDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	440c17ed-5262-4800-aa19-38e5285330c3
ea57f702-c237-4433-b8c6-43cefd69a985	FRIDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	440c17ed-5262-4800-aa19-38e5285330c3
a0658941-0061-497d-9be6-86ecca11129b	MONDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8a637f4a-a458-45eb-9f46-c0347984be30	TUESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c1fe6f47-b488-4846-b1d4-f05a706195bc	WEDNESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
eebd0c64-af5f-4b01-8149-0baa77e25ee6	THURSDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c0cadda9-6bed-41fd-bd6b-cdca84f823a9	FRIDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
91f4dbfe-acb2-4a36-9c47-d75a13fb1600	MONDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	d54b130a-28a8-4bb1-9030-c474369c2205
baae09f2-9670-4e75-bb12-6506ae6073cf	TUESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	d54b130a-28a8-4bb1-9030-c474369c2205
4f90c288-0159-458a-87c1-138d3c165f02	WEDNESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	d54b130a-28a8-4bb1-9030-c474369c2205
af4b5b01-4ffb-46ba-9a00-2ec24ac8a308	THURSDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	d54b130a-28a8-4bb1-9030-c474369c2205
4cdd52c8-4a60-4459-9be5-0f2aa7e5b940	FRIDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	d54b130a-28a8-4bb1-9030-c474369c2205
b7e48952-4658-424e-9a5f-b586d26f134d	MONDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
70a45452-b908-4b1b-9b8b-48ca8a366630	TUESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8e45eca5-f0d1-4d8b-b897-f3baf01bb1d4	WEDNESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a76713fd-0972-4124-b27a-85e67201c7fd	THURSDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5cf3a143-0c8a-4fd9-a648-17d906f3a147	FRIDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9d13373c-f452-4d50-a203-9c2ae6ba9006	MONDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	c33e01ef-be92-474a-9e43-0543649735d5
47107576-029e-4561-8f18-c462fd538b99	TUESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	c33e01ef-be92-474a-9e43-0543649735d5
1ae4da38-fc23-4bc5-87b1-62eaf042bb37	WEDNESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	c33e01ef-be92-474a-9e43-0543649735d5
710f6a7d-b36d-47fb-a602-12ac1f997d7b	THURSDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	c33e01ef-be92-474a-9e43-0543649735d5
2e67dd1b-0252-4745-b577-e94310f7fdab	FRIDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	c33e01ef-be92-474a-9e43-0543649735d5
471cfd40-49d5-426f-a2bd-992eb8bb5eb7	MONDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	6f1475e5-0d8a-47c7-855f-ccff802aff85
57cc7703-09ee-40c1-8d96-e8b027c8de74	TUESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	6f1475e5-0d8a-47c7-855f-ccff802aff85
0c1a511f-ab5f-4464-a3be-e849b4008456	WEDNESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	6f1475e5-0d8a-47c7-855f-ccff802aff85
c7c88db0-ef60-492c-b14f-beb906c07822	THURSDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	6f1475e5-0d8a-47c7-855f-ccff802aff85
f209673d-1b99-4bac-a680-4e2e832c3c00	FRIDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	6f1475e5-0d8a-47c7-855f-ccff802aff85
81f82b10-67ed-4740-a83e-f6de46664610	MONDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	58845959-0984-4136-a733-b75f5c9057b9
de3fa00a-f675-4993-94f4-2bae04c043c6	TUESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	58845959-0984-4136-a733-b75f5c9057b9
482000d3-5c50-42ab-a23b-ee197881d578	WEDNESDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	58845959-0984-4136-a733-b75f5c9057b9
4448d706-b0f6-4193-bb1a-476794ee058b	THURSDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	58845959-0984-4136-a733-b75f5c9057b9
fce7cafb-d119-4367-b00a-2d8f5cb25fc3	FRIDAY	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	58845959-0984-4136-a733-b75f5c9057b9
1083ae45-c2cc-4b9c-b519-94913a06ddbe	MONDAY	22891480-943a-4cb1-a417-c760205d86bd	440c17ed-5262-4800-aa19-38e5285330c3
aa87f7e1-a4ad-435d-9483-8e2749e1ddd5	TUESDAY	22891480-943a-4cb1-a417-c760205d86bd	440c17ed-5262-4800-aa19-38e5285330c3
62606a56-105c-4d1c-971b-6494ffd4233f	WEDNESDAY	22891480-943a-4cb1-a417-c760205d86bd	440c17ed-5262-4800-aa19-38e5285330c3
64527f94-1ecd-49f9-bc74-8665d43ddb7a	THURSDAY	22891480-943a-4cb1-a417-c760205d86bd	440c17ed-5262-4800-aa19-38e5285330c3
c8fcd081-c202-4f4f-89b4-844fcd1901ff	FRIDAY	22891480-943a-4cb1-a417-c760205d86bd	440c17ed-5262-4800-aa19-38e5285330c3
db0fc9a8-48aa-4c1b-92b9-373c60e6d1e9	MONDAY	22891480-943a-4cb1-a417-c760205d86bd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a2a191b0-ca1a-4e0d-a2ac-4f3b887f2182	TUESDAY	22891480-943a-4cb1-a417-c760205d86bd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5106dc0e-45e6-478e-b165-d3c307e38bf9	WEDNESDAY	22891480-943a-4cb1-a417-c760205d86bd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
67bb41e5-5ba8-4088-80ab-681bdabc535d	THURSDAY	22891480-943a-4cb1-a417-c760205d86bd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
dc8bf587-bb47-4911-aaa7-97c13535bb97	FRIDAY	22891480-943a-4cb1-a417-c760205d86bd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0427b626-b78d-41a0-98dd-e6c21f47dd0c	MONDAY	22891480-943a-4cb1-a417-c760205d86bd	d54b130a-28a8-4bb1-9030-c474369c2205
fb8ca862-6ead-49cc-a49d-24d1a4c4c8fa	TUESDAY	22891480-943a-4cb1-a417-c760205d86bd	d54b130a-28a8-4bb1-9030-c474369c2205
f03b2102-58f1-442e-8e3b-d25af324c525	WEDNESDAY	22891480-943a-4cb1-a417-c760205d86bd	d54b130a-28a8-4bb1-9030-c474369c2205
7e7c1eca-586e-4eda-80a0-0e7a676383cd	THURSDAY	22891480-943a-4cb1-a417-c760205d86bd	d54b130a-28a8-4bb1-9030-c474369c2205
adb3b225-fe89-4326-a83d-7e82069067c2	FRIDAY	22891480-943a-4cb1-a417-c760205d86bd	d54b130a-28a8-4bb1-9030-c474369c2205
88a3f8c7-6aa1-4713-84e0-66f2df6fa170	MONDAY	22891480-943a-4cb1-a417-c760205d86bd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
dfc04425-7b0d-422c-8f63-2b8733e05efe	TUESDAY	22891480-943a-4cb1-a417-c760205d86bd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9c5959df-2097-4476-92f4-6dd425a0d7af	WEDNESDAY	22891480-943a-4cb1-a417-c760205d86bd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5685bc4d-0434-49ca-868c-e372742cff7d	THURSDAY	22891480-943a-4cb1-a417-c760205d86bd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0b15332d-5a82-4ccf-818c-6b830e8e79a6	FRIDAY	22891480-943a-4cb1-a417-c760205d86bd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
11405f17-e41c-4399-8d58-58dfdde0bbf5	MONDAY	22891480-943a-4cb1-a417-c760205d86bd	c33e01ef-be92-474a-9e43-0543649735d5
a007cd37-dfd2-4f46-913d-d0e86fdafcbd	TUESDAY	22891480-943a-4cb1-a417-c760205d86bd	c33e01ef-be92-474a-9e43-0543649735d5
665c799a-63e0-437b-813f-b181626c7290	WEDNESDAY	22891480-943a-4cb1-a417-c760205d86bd	c33e01ef-be92-474a-9e43-0543649735d5
14c6e201-be64-4b3b-961b-c213869213f5	THURSDAY	22891480-943a-4cb1-a417-c760205d86bd	c33e01ef-be92-474a-9e43-0543649735d5
0bccd1b5-19c6-4b35-8005-f6a2bfcd774a	FRIDAY	22891480-943a-4cb1-a417-c760205d86bd	c33e01ef-be92-474a-9e43-0543649735d5
923c085d-f4dd-4c5c-9f21-408c637192db	MONDAY	22891480-943a-4cb1-a417-c760205d86bd	6f1475e5-0d8a-47c7-855f-ccff802aff85
04fd966c-bf79-4705-b810-defa5c466a67	TUESDAY	22891480-943a-4cb1-a417-c760205d86bd	6f1475e5-0d8a-47c7-855f-ccff802aff85
b69f8548-9c30-418c-9e8b-c84dce30164f	WEDNESDAY	22891480-943a-4cb1-a417-c760205d86bd	6f1475e5-0d8a-47c7-855f-ccff802aff85
5df8d18b-55be-43b3-925e-0eea5459ab06	THURSDAY	22891480-943a-4cb1-a417-c760205d86bd	6f1475e5-0d8a-47c7-855f-ccff802aff85
3f8269f3-db19-4480-a83c-d6a36935936d	FRIDAY	22891480-943a-4cb1-a417-c760205d86bd	6f1475e5-0d8a-47c7-855f-ccff802aff85
16609d68-2b5a-47ce-8798-008be33c73c2	MONDAY	22891480-943a-4cb1-a417-c760205d86bd	58845959-0984-4136-a733-b75f5c9057b9
6f929948-0cb6-4cef-af65-949801f8f3ec	TUESDAY	22891480-943a-4cb1-a417-c760205d86bd	58845959-0984-4136-a733-b75f5c9057b9
5ab0ae53-fb29-467b-935a-75ec2a90ab99	WEDNESDAY	22891480-943a-4cb1-a417-c760205d86bd	58845959-0984-4136-a733-b75f5c9057b9
2768abaf-cd68-4bc5-bb6f-81c624d34d8c	THURSDAY	22891480-943a-4cb1-a417-c760205d86bd	58845959-0984-4136-a733-b75f5c9057b9
96b8a889-6545-46c1-86a5-ecb4b0f0b25e	FRIDAY	22891480-943a-4cb1-a417-c760205d86bd	58845959-0984-4136-a733-b75f5c9057b9
58ce30d1-2d1f-4e32-95aa-5c37bf1a9fef	MONDAY	4a858055-f275-460b-a349-222cc0ca28d7	440c17ed-5262-4800-aa19-38e5285330c3
7077e96f-e777-46ce-82e9-ba980dfaaaee	TUESDAY	4a858055-f275-460b-a349-222cc0ca28d7	440c17ed-5262-4800-aa19-38e5285330c3
2ae6612c-509d-48c7-9fe2-f4248dcceed5	WEDNESDAY	4a858055-f275-460b-a349-222cc0ca28d7	440c17ed-5262-4800-aa19-38e5285330c3
55bcd94e-f540-4a80-8c15-a48dab5a67b6	THURSDAY	4a858055-f275-460b-a349-222cc0ca28d7	440c17ed-5262-4800-aa19-38e5285330c3
74ab4774-bd0c-430f-b8ed-97897fbdfa43	FRIDAY	4a858055-f275-460b-a349-222cc0ca28d7	440c17ed-5262-4800-aa19-38e5285330c3
480615c6-176d-48c2-b8cf-e769763c0326	MONDAY	4a858055-f275-460b-a349-222cc0ca28d7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9eb06c3b-171d-4174-ad6b-aab48fcc0484	TUESDAY	4a858055-f275-460b-a349-222cc0ca28d7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
359dd400-864e-42a6-acbc-bc210ef84349	WEDNESDAY	4a858055-f275-460b-a349-222cc0ca28d7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
316eedc3-eb28-463a-bfd2-4a5d99a820a8	THURSDAY	4a858055-f275-460b-a349-222cc0ca28d7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6ae49139-7936-4846-9cda-3abf8160ea76	FRIDAY	4a858055-f275-460b-a349-222cc0ca28d7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
08652e55-4986-425c-8f55-2c7fdb08d233	MONDAY	4a858055-f275-460b-a349-222cc0ca28d7	d54b130a-28a8-4bb1-9030-c474369c2205
d35f93a5-21f1-49c4-bd5c-57928f3c0818	TUESDAY	4a858055-f275-460b-a349-222cc0ca28d7	d54b130a-28a8-4bb1-9030-c474369c2205
cc83228b-0bd6-4e6d-b036-3fb08f1a353a	WEDNESDAY	4a858055-f275-460b-a349-222cc0ca28d7	d54b130a-28a8-4bb1-9030-c474369c2205
f34e4ff1-dc04-4040-b60d-2d3c7c089149	THURSDAY	4a858055-f275-460b-a349-222cc0ca28d7	d54b130a-28a8-4bb1-9030-c474369c2205
e8090af0-d33b-403c-8689-d94b1fcdf793	FRIDAY	4a858055-f275-460b-a349-222cc0ca28d7	d54b130a-28a8-4bb1-9030-c474369c2205
8758baed-dd05-4277-af61-42bf0323bc8a	MONDAY	4a858055-f275-460b-a349-222cc0ca28d7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c3ca6443-f014-485c-92c4-2b298982abe6	TUESDAY	4a858055-f275-460b-a349-222cc0ca28d7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
067234b4-aa24-4e3f-8001-1af58e81e582	WEDNESDAY	4a858055-f275-460b-a349-222cc0ca28d7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d5c97cc4-7f10-4526-a8b9-57809409c2ca	THURSDAY	4a858055-f275-460b-a349-222cc0ca28d7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
76b89adf-f4d4-4002-8e1d-5b1ddec8b08d	FRIDAY	4a858055-f275-460b-a349-222cc0ca28d7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
43f9c912-b36f-4979-b208-9226b5d5b6cb	MONDAY	4a858055-f275-460b-a349-222cc0ca28d7	c33e01ef-be92-474a-9e43-0543649735d5
2a0e062a-f2ee-435d-bb9d-397976799657	TUESDAY	4a858055-f275-460b-a349-222cc0ca28d7	c33e01ef-be92-474a-9e43-0543649735d5
15a6e734-de57-481b-b3a4-b360c2cffa5d	WEDNESDAY	4a858055-f275-460b-a349-222cc0ca28d7	c33e01ef-be92-474a-9e43-0543649735d5
c88313dc-bb44-4792-8700-e785a5f0fc80	THURSDAY	4a858055-f275-460b-a349-222cc0ca28d7	c33e01ef-be92-474a-9e43-0543649735d5
0c17359c-83da-498f-825b-e9728717368f	FRIDAY	4a858055-f275-460b-a349-222cc0ca28d7	c33e01ef-be92-474a-9e43-0543649735d5
25a5402b-d9c2-4411-b3d5-17d392160675	MONDAY	4a858055-f275-460b-a349-222cc0ca28d7	6f1475e5-0d8a-47c7-855f-ccff802aff85
c4fdb3ff-d0fd-483d-9723-bb85f9e29945	TUESDAY	4a858055-f275-460b-a349-222cc0ca28d7	6f1475e5-0d8a-47c7-855f-ccff802aff85
d77b5fab-22b2-40b4-a66f-e432e99293b2	WEDNESDAY	4a858055-f275-460b-a349-222cc0ca28d7	6f1475e5-0d8a-47c7-855f-ccff802aff85
72a95ec3-5422-47e3-ab91-8b761a7c2977	THURSDAY	4a858055-f275-460b-a349-222cc0ca28d7	6f1475e5-0d8a-47c7-855f-ccff802aff85
87191634-b196-4516-a6b6-ffb8fe771d85	FRIDAY	4a858055-f275-460b-a349-222cc0ca28d7	6f1475e5-0d8a-47c7-855f-ccff802aff85
5c3c6dbc-f2b6-4b58-adba-cc7dd53162bd	MONDAY	4a858055-f275-460b-a349-222cc0ca28d7	58845959-0984-4136-a733-b75f5c9057b9
62b6376d-838b-41c5-9b27-c233e85e1fc4	TUESDAY	4a858055-f275-460b-a349-222cc0ca28d7	58845959-0984-4136-a733-b75f5c9057b9
f3ea7c46-8a13-4cdb-9d42-18c5aade0dbc	WEDNESDAY	4a858055-f275-460b-a349-222cc0ca28d7	58845959-0984-4136-a733-b75f5c9057b9
b88265ab-a601-4711-9f21-791cad2411a1	THURSDAY	4a858055-f275-460b-a349-222cc0ca28d7	58845959-0984-4136-a733-b75f5c9057b9
6b6819be-ef6d-4768-aca5-63dc10a0b7e5	FRIDAY	4a858055-f275-460b-a349-222cc0ca28d7	58845959-0984-4136-a733-b75f5c9057b9
ff63a3e2-565d-4925-8fdc-eae1a6a782b7	MONDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	440c17ed-5262-4800-aa19-38e5285330c3
893ee06a-2873-4d16-81d1-d825024d24d2	TUESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	440c17ed-5262-4800-aa19-38e5285330c3
bdd24e31-3983-4c3a-ad67-455590ee5e49	WEDNESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	440c17ed-5262-4800-aa19-38e5285330c3
6df16fc3-f0be-45eb-a544-043d8e1a50da	THURSDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	440c17ed-5262-4800-aa19-38e5285330c3
d749293b-9dfc-4d7a-a765-6bb79697bc43	FRIDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	440c17ed-5262-4800-aa19-38e5285330c3
b924dcc9-7468-411f-a6a1-79bb6766e9dc	MONDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ee745020-92fa-4035-8c70-c94bad830484	TUESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4975e3d2-6910-4c0a-8e2a-12d96d1bf5c8	WEDNESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
feb80ccc-967c-499d-88ca-274021c7f41d	THURSDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
43f88ad0-87cf-4c3e-8ff4-0118cde31d4b	FRIDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
557ffa1f-0e97-4570-ad3f-b59ef692caa7	MONDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	d54b130a-28a8-4bb1-9030-c474369c2205
4eb29cbd-c121-47d4-b893-d516b6093d65	TUESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	d54b130a-28a8-4bb1-9030-c474369c2205
0710ffea-0186-4939-90aa-da6d46d898ad	WEDNESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	d54b130a-28a8-4bb1-9030-c474369c2205
02a8fd0c-55e4-4ac9-af6d-a89d1c74a510	THURSDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	d54b130a-28a8-4bb1-9030-c474369c2205
ff965e7f-fbb8-4b56-86f8-158a9892c719	FRIDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	d54b130a-28a8-4bb1-9030-c474369c2205
8f79aab3-1c3c-48a0-9e51-341ad8253c3c	MONDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b030caa8-f5db-48e8-8995-3ad5d3b8ecd0	TUESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
04f9e354-fac2-45b9-ab50-e33514f82ad0	WEDNESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d1296484-ee53-4308-b2b5-9f210545eaec	THURSDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6b973b74-c03f-427a-9ce6-ee9e3120f46f	FRIDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c1b3aa15-dc0c-4825-87c4-7209bd436d77	MONDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	c33e01ef-be92-474a-9e43-0543649735d5
088f1d67-1805-4e12-ab22-89b3b9a365aa	TUESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	c33e01ef-be92-474a-9e43-0543649735d5
4736e6dc-4387-4361-be3d-6c3f20102a80	WEDNESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	c33e01ef-be92-474a-9e43-0543649735d5
c25fac0a-8b74-4661-add7-dc6d36f9473d	THURSDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	c33e01ef-be92-474a-9e43-0543649735d5
6745e644-8f20-43ec-b01d-658c33ecac73	FRIDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	c33e01ef-be92-474a-9e43-0543649735d5
5c226d40-309a-4940-a861-52ff19aac3e1	MONDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	6f1475e5-0d8a-47c7-855f-ccff802aff85
193822f9-1cea-4ce5-ba0d-51e35f9a74df	TUESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	6f1475e5-0d8a-47c7-855f-ccff802aff85
e01fc2c1-7a75-4fc1-935d-f10f507d728c	WEDNESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	6f1475e5-0d8a-47c7-855f-ccff802aff85
0bbbb78e-0fb8-4805-97d0-3714c987292b	THURSDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	6f1475e5-0d8a-47c7-855f-ccff802aff85
476889f9-31e8-4484-9f27-71dd366e10e0	FRIDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	6f1475e5-0d8a-47c7-855f-ccff802aff85
eb5ac30c-5d5c-42b7-ba2f-13d1b4c83410	MONDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	58845959-0984-4136-a733-b75f5c9057b9
98ccf72f-245b-4067-8d4c-d04378df6006	TUESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	58845959-0984-4136-a733-b75f5c9057b9
99773480-8110-4edd-8b75-7590ba9a9e79	WEDNESDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	58845959-0984-4136-a733-b75f5c9057b9
5398b3df-b0b1-4153-b71b-30d9d2fd791d	THURSDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	58845959-0984-4136-a733-b75f5c9057b9
960bb831-8c98-4987-a2f5-3602104e8216	FRIDAY	394a96f5-4e6a-4a64-8a13-0184d37063fd	58845959-0984-4136-a733-b75f5c9057b9
74b48113-79cb-4ee0-b32d-b241e786e587	MONDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	440c17ed-5262-4800-aa19-38e5285330c3
02c0cda3-587f-4b82-abc4-53fbaca32357	TUESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	440c17ed-5262-4800-aa19-38e5285330c3
067e3053-209d-4731-838f-3d34bd13a321	WEDNESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	440c17ed-5262-4800-aa19-38e5285330c3
6c572a72-1d36-4173-848d-bd551ee5f1ec	THURSDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	440c17ed-5262-4800-aa19-38e5285330c3
51846b22-b1ec-41a6-a750-655d4febf9d2	FRIDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	440c17ed-5262-4800-aa19-38e5285330c3
621b42fb-22dc-40de-9d82-3318b66c28c3	MONDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ceae9a86-791b-4e44-8b93-878dfaf7d836	TUESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	5f76a266-8d18-4da8-9732-b63d4d8f24a0
895546da-7d56-4162-85d3-4ded5f1b3471	WEDNESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	5f76a266-8d18-4da8-9732-b63d4d8f24a0
954f797d-70de-404b-9340-71b3d075db26	THURSDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4f50f2cd-55ff-47d7-9d01-303f048ccd25	FRIDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d7a87b3f-3029-4ce0-a37d-e0823a2c283c	MONDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	d54b130a-28a8-4bb1-9030-c474369c2205
bcac9351-e134-4678-be92-26ff231dfd37	TUESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	d54b130a-28a8-4bb1-9030-c474369c2205
b010fa62-050a-42b2-9b21-f527147fc116	WEDNESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	d54b130a-28a8-4bb1-9030-c474369c2205
963fe9f2-93b7-458b-a272-3defe4f8908a	THURSDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	d54b130a-28a8-4bb1-9030-c474369c2205
82037b40-6f59-4358-b51f-950c01fd3af4	FRIDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	d54b130a-28a8-4bb1-9030-c474369c2205
f37750ff-4970-4a27-8060-d1b0fc078388	MONDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b5ef1745-4b33-4f7f-aaa4-ab6d2edf8ba1	TUESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
dee28434-eb50-4aae-9bcb-e53e6f26be49	WEDNESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
800f984e-2498-411d-b9f2-a9d9a70717b8	THURSDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7e70a870-45fb-456a-ab51-3ea27591c905	FRIDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bf69a6e4-c6a1-46a7-9fdf-974c2a9bda68	MONDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	c33e01ef-be92-474a-9e43-0543649735d5
e8b42452-39fd-4da8-80e9-7f0277470e56	TUESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	c33e01ef-be92-474a-9e43-0543649735d5
ebc0026f-898f-4f7b-b927-4385cfa833fa	WEDNESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	c33e01ef-be92-474a-9e43-0543649735d5
7271abf3-11e2-44a8-bb61-0723f923228e	THURSDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	c33e01ef-be92-474a-9e43-0543649735d5
0f6232c0-012d-4d98-b01e-55253b924e13	FRIDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	c33e01ef-be92-474a-9e43-0543649735d5
4fd1c146-135c-4488-81e1-834d562a0f0b	MONDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	6f1475e5-0d8a-47c7-855f-ccff802aff85
ac5fd14e-d170-4ce5-a936-c1c06c8d70bb	TUESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	6f1475e5-0d8a-47c7-855f-ccff802aff85
10315856-6e8f-4769-935d-f046658ac9f7	WEDNESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	6f1475e5-0d8a-47c7-855f-ccff802aff85
9b78b28b-0171-42e0-9ceb-1a4618d112a2	THURSDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	6f1475e5-0d8a-47c7-855f-ccff802aff85
a6588276-7096-4c5b-8401-d8ba42caa5c5	FRIDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	6f1475e5-0d8a-47c7-855f-ccff802aff85
bb42bb1b-2ab2-4d11-85b0-c1e05a3a7a86	MONDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	58845959-0984-4136-a733-b75f5c9057b9
a1b25b0b-f264-4324-bd56-8ca4ccea4357	TUESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	58845959-0984-4136-a733-b75f5c9057b9
e3394e76-78e4-4767-9c1a-c634992cd194	WEDNESDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	58845959-0984-4136-a733-b75f5c9057b9
be0c6577-0988-44b9-a46e-41f0ddc66768	THURSDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	58845959-0984-4136-a733-b75f5c9057b9
cd1c4016-50b3-452a-bd4b-1a2f693b2e79	FRIDAY	74a8a447-3f16-43a9-b29b-84d2e5d86a88	58845959-0984-4136-a733-b75f5c9057b9
365609ca-77f4-40ac-b9b6-6fab0dafaaef	MONDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	440c17ed-5262-4800-aa19-38e5285330c3
3d3c647f-5000-41da-8bb6-08dae06fb363	TUESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	440c17ed-5262-4800-aa19-38e5285330c3
b65e9c1b-5459-402e-892d-47453194da78	WEDNESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	440c17ed-5262-4800-aa19-38e5285330c3
ce26a6b6-7363-4e75-82b0-2410488e5bc9	THURSDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	440c17ed-5262-4800-aa19-38e5285330c3
9b99fd78-188f-4cba-9899-2e7fd50908bb	FRIDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	440c17ed-5262-4800-aa19-38e5285330c3
ad0c300f-224e-4077-9fbc-d4a1aabeb08d	MONDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8eef686c-e86f-4c42-a0a2-6dd6c6272c72	TUESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f79f83ae-8d50-459d-bb09-1362827c3a4d	WEDNESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	5f76a266-8d18-4da8-9732-b63d4d8f24a0
68e02782-7cd5-4720-8abd-c60025ca2a00	THURSDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5c6ea48a-38d6-458b-b027-9b46db1dbd9b	FRIDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1e0518f1-cbdc-4a22-8a07-0733e32af6fd	MONDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	d54b130a-28a8-4bb1-9030-c474369c2205
74599f05-770b-4460-8a4e-7bd0d499f333	TUESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	d54b130a-28a8-4bb1-9030-c474369c2205
e328972d-d8f8-445b-9a7d-29ea40418a18	WEDNESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	d54b130a-28a8-4bb1-9030-c474369c2205
ad7d0b6d-60ac-4c25-9f68-3a4974cb3008	THURSDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	d54b130a-28a8-4bb1-9030-c474369c2205
19323a0f-f9d9-4610-beda-8a590344665f	FRIDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	d54b130a-28a8-4bb1-9030-c474369c2205
44661529-8a3c-4c7d-be84-a6e9ef0fb657	MONDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c6c77e71-3bac-4b7d-87f4-5a2b66a3c30c	TUESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
250b64ae-85a7-4ff3-8cda-e7c96b542da2	WEDNESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e4249e65-d83f-49e7-bef3-27d6b492ec12	THURSDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e8249988-9da9-479a-8687-b61fd8ffa0dc	FRIDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f0685485-52b4-4577-b751-0fe5f0eef7a7	MONDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	c33e01ef-be92-474a-9e43-0543649735d5
4ff91b51-1139-4985-8d1f-545b83f75c2d	TUESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	c33e01ef-be92-474a-9e43-0543649735d5
fcf1b2d2-9a66-4ed5-af25-cf4e5a1fc18a	WEDNESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	c33e01ef-be92-474a-9e43-0543649735d5
2fdc0dc6-98f8-44ad-9190-6aea712f7cab	THURSDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	c33e01ef-be92-474a-9e43-0543649735d5
24f5e952-1b1e-48d8-bbe4-fb52d131660c	FRIDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	c33e01ef-be92-474a-9e43-0543649735d5
b7478021-d741-4ce5-b8f1-1e0e3a36b446	MONDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	6f1475e5-0d8a-47c7-855f-ccff802aff85
0cddbffb-eebb-41ae-a479-bb824107ee9e	TUESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	6f1475e5-0d8a-47c7-855f-ccff802aff85
8828da79-39d0-4625-ab89-8d3dec8c61ab	WEDNESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	6f1475e5-0d8a-47c7-855f-ccff802aff85
fdae63bd-e6f7-4278-9ee1-66489aaa2d63	THURSDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	6f1475e5-0d8a-47c7-855f-ccff802aff85
6d7811a4-2cdf-4f16-9c1a-f87aaf45da85	FRIDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	6f1475e5-0d8a-47c7-855f-ccff802aff85
8d297b99-4584-4258-a596-5aaec08a95b5	MONDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	58845959-0984-4136-a733-b75f5c9057b9
a04509bb-4fa6-4b94-af3f-75fa6b0267b2	TUESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	58845959-0984-4136-a733-b75f5c9057b9
42b52177-5b6a-4d61-876b-2dd848a0aa79	WEDNESDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	58845959-0984-4136-a733-b75f5c9057b9
cc82afe8-18cc-4c6f-a4db-3a0c8c916513	THURSDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	58845959-0984-4136-a733-b75f5c9057b9
fcb9219d-181b-4425-bc33-abf66176bd73	FRIDAY	f6a2c559-1f7d-4f45-adf1-8c76b2faf147	58845959-0984-4136-a733-b75f5c9057b9
9374f853-8771-4a79-94ec-34c7a329f966	WEDNESDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	58845959-0984-4136-a733-b75f5c9057b9
565d937e-279b-4976-8717-86991e4a05a1	THURSDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	58845959-0984-4136-a733-b75f5c9057b9
20479fbe-c2f1-41db-b9b9-40e13d9b3454	FRIDAY	5fdcd179-a83a-4080-a33c-905e3db55dd1	58845959-0984-4136-a733-b75f5c9057b9
3505ab3d-716c-4da6-8464-658de013f651	MONDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	440c17ed-5262-4800-aa19-38e5285330c3
49915322-da69-4581-885f-be88728d0561	TUESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	440c17ed-5262-4800-aa19-38e5285330c3
7a1d8a37-99ee-4970-8395-d0dab41bb173	WEDNESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	440c17ed-5262-4800-aa19-38e5285330c3
1f7dabde-2364-4f28-8292-024da437b223	THURSDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	440c17ed-5262-4800-aa19-38e5285330c3
e866d0e5-6455-4a27-a83e-3185fc18bfbd	FRIDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	440c17ed-5262-4800-aa19-38e5285330c3
120a3156-6db9-491b-81f1-92754b2f4820	MONDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2a2c22bc-1c4d-4bc4-93b7-38d38e68e49f	TUESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	5f76a266-8d18-4da8-9732-b63d4d8f24a0
33e361ad-3f6f-4633-9648-ff6bdf8dbf00	WEDNESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	5f76a266-8d18-4da8-9732-b63d4d8f24a0
58bbf04b-445c-4e40-974a-be6d70454ce7	THURSDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e5e787bf-04f9-489a-b46f-fbeccb267d9f	FRIDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	5f76a266-8d18-4da8-9732-b63d4d8f24a0
34586986-f062-45f7-bf79-8e1570a7b04e	MONDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	d54b130a-28a8-4bb1-9030-c474369c2205
1817a6a7-aaca-40b2-abd1-e1eaaa345bd3	TUESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	d54b130a-28a8-4bb1-9030-c474369c2205
efa37e81-bb13-4228-ac6a-7b01ff8b9784	WEDNESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	d54b130a-28a8-4bb1-9030-c474369c2205
8e0bfa73-5dd6-490f-b283-d8d8adc59bdf	THURSDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	d54b130a-28a8-4bb1-9030-c474369c2205
436c8444-7342-4cd8-bf04-124901c91b7a	FRIDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	d54b130a-28a8-4bb1-9030-c474369c2205
9df66475-829e-4dc9-8905-990e41b5e78e	MONDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b64d54aa-47c0-4251-b9be-1c6f8d7ba21b	TUESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
98801f8e-ea16-489b-8a31-f46713f83f0f	WEDNESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b74d776f-6838-4794-b07b-9453e3a31f0e	THURSDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
99f76aec-0d63-4013-92a2-53736c95a46f	FRIDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
40620d90-29c4-4e12-83a8-f4b526b55e4e	MONDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	c33e01ef-be92-474a-9e43-0543649735d5
fe3ad5b7-2fbe-49e0-91df-c2d39fc94b11	TUESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	c33e01ef-be92-474a-9e43-0543649735d5
c27a0c10-1ac0-419c-a7ce-e5f837e58f87	WEDNESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	c33e01ef-be92-474a-9e43-0543649735d5
d4abb273-0f01-4fa7-88a3-6a8237abafea	THURSDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	c33e01ef-be92-474a-9e43-0543649735d5
40edf002-b2a1-41f4-83e9-45fc9ab6eb42	FRIDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	c33e01ef-be92-474a-9e43-0543649735d5
54d8dcc6-eee5-4f72-8a1c-0ad869ed98ef	MONDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
55812855-1cb2-4991-8c03-2c2ee281821f	TUESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
071eb48e-8fb3-4f78-9e57-94931aa92e68	WEDNESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
5f725ff6-04ab-4879-b327-95e71d7bae8d	THURSDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
9510ed00-0ac6-47a1-ae43-ab033b399a96	FRIDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
999e1749-ae70-4ccd-8a16-23cbc9ece11f	MONDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	58845959-0984-4136-a733-b75f5c9057b9
b09a4274-e39d-4b3d-86f7-7145c3b0dc9b	TUESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	58845959-0984-4136-a733-b75f5c9057b9
23d751e2-1bb4-4960-9ede-fd3a191c2bd8	WEDNESDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	58845959-0984-4136-a733-b75f5c9057b9
7557cd31-fa31-4019-9768-9e86a62e0f5c	THURSDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	58845959-0984-4136-a733-b75f5c9057b9
8d3e2964-9706-4817-9a9f-28e7ea64c02b	FRIDAY	5fa57fdf-7ba0-4063-93cf-378d7b8267a9	58845959-0984-4136-a733-b75f5c9057b9
3b634416-7d5f-49bb-b87d-6f7a621710b4	MONDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	440c17ed-5262-4800-aa19-38e5285330c3
8ba0ed7b-4165-4039-8ffd-75e82039e3ac	TUESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	440c17ed-5262-4800-aa19-38e5285330c3
6f43a696-714c-4e86-88b2-85d9420a6c22	WEDNESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	440c17ed-5262-4800-aa19-38e5285330c3
1130c5a7-637e-4057-a79f-387ff832c23d	THURSDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	440c17ed-5262-4800-aa19-38e5285330c3
00001b38-c89b-49c4-8acd-6634fa675f5b	FRIDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	440c17ed-5262-4800-aa19-38e5285330c3
6ce8f37c-cf6c-4be7-88bb-4e7e93a93211	MONDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a977f73d-9128-4557-9163-a14ac98afc71	TUESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a5806e4c-1ac6-4ca6-a7ec-948fb840efad	WEDNESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
23b9862e-8d28-4afd-8ff5-e78bb74fae07	THURSDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
89bb1267-8004-472c-8083-582224241608	FRIDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
78b73bbd-3afa-41d6-8802-40956631c96d	MONDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	d54b130a-28a8-4bb1-9030-c474369c2205
bbe44bc6-a443-4bc0-8920-63e7519fcf05	TUESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	d54b130a-28a8-4bb1-9030-c474369c2205
b854ec20-9a5b-46a8-9409-877e77a8e76a	WEDNESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	d54b130a-28a8-4bb1-9030-c474369c2205
71755c02-fa41-49a5-8ba2-919e20bd3eb7	THURSDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	d54b130a-28a8-4bb1-9030-c474369c2205
8744eec5-3bc3-40de-ae2c-e6570cd5847c	FRIDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	d54b130a-28a8-4bb1-9030-c474369c2205
1c318562-40db-4b91-ba3b-4a5ac78cb2d6	MONDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5b248da6-9fbc-442a-b261-804c7c2a2f21	TUESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b1e555a2-9226-41a7-9fd7-760d851fc6e8	WEDNESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
967dc291-6573-4486-8f02-b2b984dc563b	THURSDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e3d4164f-42f3-46f7-89f4-3b7b161b6245	FRIDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a3256dc9-9708-45d3-bf7f-bfdcd39af240	MONDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	c33e01ef-be92-474a-9e43-0543649735d5
4479cb1b-27cc-4b4a-9689-2f7f7d4c4d09	TUESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	c33e01ef-be92-474a-9e43-0543649735d5
b190e962-992a-4924-be9a-614b7df987c4	WEDNESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	c33e01ef-be92-474a-9e43-0543649735d5
92f3c575-0ed4-47b9-8384-c3201f2b8090	THURSDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	c33e01ef-be92-474a-9e43-0543649735d5
0c503771-b38f-4ab5-bf23-314e195ef800	FRIDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	c33e01ef-be92-474a-9e43-0543649735d5
3e8b20e5-d4ea-4c27-aa6c-68acb2befcad	MONDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	6f1475e5-0d8a-47c7-855f-ccff802aff85
41f735be-1d20-4c37-a5f0-3a44d87898f9	TUESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	6f1475e5-0d8a-47c7-855f-ccff802aff85
381a7947-cced-4620-8043-e23f5bc83050	WEDNESDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	6f1475e5-0d8a-47c7-855f-ccff802aff85
f0f17821-eccf-482a-8a88-c6f81046db18	THURSDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	6f1475e5-0d8a-47c7-855f-ccff802aff85
44d6de7a-6715-4862-8e2e-fffc88ec9fe0	FRIDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	6f1475e5-0d8a-47c7-855f-ccff802aff85
9b50d33e-bcc8-43ad-92be-817f5c11ad70	MONDAY	555bdc44-a24e-48fc-aa5e-892e666781a5	58845959-0984-4136-a733-b75f5c9057b9
bfef5d53-2c6c-4150-8307-92c73fda7126	MONDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	440c17ed-5262-4800-aa19-38e5285330c3
a19c6a19-814e-483e-8da2-5f73b6dd1a8b	TUESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	440c17ed-5262-4800-aa19-38e5285330c3
6faaff49-491e-4fa4-a619-37423fb65a65	WEDNESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	440c17ed-5262-4800-aa19-38e5285330c3
013c5886-0872-4cd8-83b7-2586bbf456e0	THURSDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	440c17ed-5262-4800-aa19-38e5285330c3
baa6721d-5928-4502-b44c-bc3cd70d0ecb	FRIDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	440c17ed-5262-4800-aa19-38e5285330c3
6d232c75-131e-4143-ab3e-4d5976fd1d7d	MONDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cca50ef4-5c3d-4636-8065-4f1c04332cc4	TUESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fdc7bd4a-e27c-415e-9823-55816a4db828	WEDNESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b8c36cc5-16a0-4b71-b79b-2ebf22485c02	THURSDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6d22b09e-e151-485d-bbb2-5b20466e4b89	FRIDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3b134e1f-dc3a-4dd4-8dca-d218cf8950eb	MONDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	d54b130a-28a8-4bb1-9030-c474369c2205
27eff93e-81f9-4f39-b12a-7e7f9e17f117	TUESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	d54b130a-28a8-4bb1-9030-c474369c2205
74a5389a-cff7-4412-9929-bb48374ffebd	WEDNESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	d54b130a-28a8-4bb1-9030-c474369c2205
c0db72bd-95fe-4cba-8a4b-272fdd167913	THURSDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	d54b130a-28a8-4bb1-9030-c474369c2205
0170fafd-8fe7-4a57-81ff-67dcda8b8023	FRIDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	d54b130a-28a8-4bb1-9030-c474369c2205
cd52ed02-14b9-4b08-b62a-f857abfaba3b	MONDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ceda5cee-c6f3-4d00-bc12-7cf153c81339	TUESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
eeff261d-f858-4317-b769-a83ee23b3b97	WEDNESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ed8ac5fe-09e5-4c34-98db-1adb95f3cde3	THURSDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d2417f97-7ae8-4c18-af9a-75836bda1206	FRIDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a68eb2de-66d8-439c-81ba-b91dd032a9fd	MONDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	c33e01ef-be92-474a-9e43-0543649735d5
72b20d9d-a404-43b7-8c66-5e152c056cad	TUESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	c33e01ef-be92-474a-9e43-0543649735d5
9827ac6b-67bf-47fa-ad5a-e451f756ad88	WEDNESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	c33e01ef-be92-474a-9e43-0543649735d5
7df43c77-1299-4672-84f8-ff648acace9a	THURSDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	c33e01ef-be92-474a-9e43-0543649735d5
47077614-8a39-4475-8129-3a4f663301e1	FRIDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	c33e01ef-be92-474a-9e43-0543649735d5
2e7225c8-0a9a-449b-a472-d12b4f4e8e4b	MONDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	6f1475e5-0d8a-47c7-855f-ccff802aff85
469c11b9-e11b-4a28-a260-eec71e5f5ba6	TUESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	6f1475e5-0d8a-47c7-855f-ccff802aff85
0fad0982-c0b4-4a1b-966a-83b3ae50f930	WEDNESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	6f1475e5-0d8a-47c7-855f-ccff802aff85
fc930cd9-5865-4a38-91d6-47d1e7c97a2a	THURSDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	6f1475e5-0d8a-47c7-855f-ccff802aff85
0089055b-5d67-4e6e-a31a-1ee611fa2124	FRIDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	6f1475e5-0d8a-47c7-855f-ccff802aff85
de87a6bd-3945-4509-971c-2468e74d7bae	MONDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	58845959-0984-4136-a733-b75f5c9057b9
cccb7489-7a8c-4052-b95e-a2c5a31f90cf	TUESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	58845959-0984-4136-a733-b75f5c9057b9
7745f324-5b93-42d5-b44b-bc5839569de3	WEDNESDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	58845959-0984-4136-a733-b75f5c9057b9
3ccee82d-a779-4e98-89d7-7c2caed72aad	THURSDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	58845959-0984-4136-a733-b75f5c9057b9
2eb28326-fac7-43d6-aac9-a7fd655da20b	FRIDAY	f1f5bad2-246a-446e-877d-e5b956ae77fa	58845959-0984-4136-a733-b75f5c9057b9
e78c1df3-9324-4695-a201-d5d53b482127	MONDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	440c17ed-5262-4800-aa19-38e5285330c3
06ef43df-1206-4221-aa79-93b001fb93f7	TUESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	440c17ed-5262-4800-aa19-38e5285330c3
a65d2e2b-d363-4baf-a93a-06978e1aefea	WEDNESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	440c17ed-5262-4800-aa19-38e5285330c3
fa714431-6d9f-4aab-b257-9bfdecf928b6	THURSDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	440c17ed-5262-4800-aa19-38e5285330c3
7e2640c6-8f52-4fd0-bb8d-8e86e489ec76	FRIDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	440c17ed-5262-4800-aa19-38e5285330c3
d4d36da6-f90e-4df1-84b6-6d3311936ebe	MONDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
02fd104b-b2cd-4fc2-91b0-086f8b9abdba	TUESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
89b32c74-60e6-491f-8f9d-01b64a5cd322	WEDNESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
58bcb152-669c-4479-accf-fb11da626acb	THURSDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ea5828fd-ad3f-4561-81e5-9cb82801fee5	FRIDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a057826b-e79d-4355-89e2-4dd66d1a548e	MONDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	d54b130a-28a8-4bb1-9030-c474369c2205
e5ce861e-f7c2-4678-98c2-94829e0ddd7e	TUESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	d54b130a-28a8-4bb1-9030-c474369c2205
1c10e9cb-a4dd-4ec1-b668-e6746ede4a7a	WEDNESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	d54b130a-28a8-4bb1-9030-c474369c2205
625355e9-ca12-44e2-a2ba-57ba9f7816d1	THURSDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	d54b130a-28a8-4bb1-9030-c474369c2205
e354de74-163c-4e4a-8e72-c69489af3b2f	FRIDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	d54b130a-28a8-4bb1-9030-c474369c2205
fcf3081f-a538-451b-9fb1-8d2f61fb944e	MONDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
70c8cb9c-07a9-4286-976b-544780b3124c	TUESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
301e3693-de15-477c-bb59-b297c8bc88f2	WEDNESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bef346a4-a680-4d55-927b-3ed45277feae	THURSDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b7322970-8ff3-41e1-b16c-844cb8d22c17	FRIDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b29cef8e-5c72-4356-b84b-8339217ece95	MONDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	c33e01ef-be92-474a-9e43-0543649735d5
a6160de5-9db0-46e4-aded-49c61ca5a525	TUESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	c33e01ef-be92-474a-9e43-0543649735d5
863b780b-2fe6-4aeb-9339-df6cef50c663	WEDNESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	c33e01ef-be92-474a-9e43-0543649735d5
2a16e37a-0517-4e9f-9e11-f6ed19e48a39	THURSDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	c33e01ef-be92-474a-9e43-0543649735d5
3229e33f-df63-4ac0-846a-f277ec25775b	FRIDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	c33e01ef-be92-474a-9e43-0543649735d5
aeb0c06d-7da3-4ed0-a87a-579e88cb2966	MONDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	6f1475e5-0d8a-47c7-855f-ccff802aff85
8a0c78b0-7d6b-4a4d-bcc2-6248659cdc9f	TUESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	6f1475e5-0d8a-47c7-855f-ccff802aff85
1fb94132-f2b0-40fe-972d-46a5047b3f9c	WEDNESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	6f1475e5-0d8a-47c7-855f-ccff802aff85
97d7a912-1dc2-4371-a739-00d7627f57db	THURSDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	6f1475e5-0d8a-47c7-855f-ccff802aff85
b95430ae-5b02-4907-bf68-ab1ba67116be	FRIDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	6f1475e5-0d8a-47c7-855f-ccff802aff85
4f50800f-61cd-4a25-9193-7309a5b4285e	MONDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	58845959-0984-4136-a733-b75f5c9057b9
e7204b93-bb8a-4037-a667-ccb9f97ac439	TUESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	58845959-0984-4136-a733-b75f5c9057b9
6b53dab4-ca12-4d6b-885d-1605ec581e8f	WEDNESDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	58845959-0984-4136-a733-b75f5c9057b9
9dd6783e-0548-4e3f-918c-a795baba1282	THURSDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	58845959-0984-4136-a733-b75f5c9057b9
6d362c9b-0f34-422d-a428-963b56243723	FRIDAY	ad789a69-05f1-43be-8084-cd6a14e6202a	58845959-0984-4136-a733-b75f5c9057b9
cef37636-f8e2-4c9d-945e-b4cdcf62f19b	MONDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	c33e01ef-be92-474a-9e43-0543649735d5
468fdead-325d-460e-a5b3-5de07b396e62	TUESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	c33e01ef-be92-474a-9e43-0543649735d5
eab2839e-2a70-4223-bff4-eb6df22c62ec	WEDNESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	c33e01ef-be92-474a-9e43-0543649735d5
b23589e9-8740-469a-8604-534bd37e1cf1	THURSDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	c33e01ef-be92-474a-9e43-0543649735d5
af5be4f2-e2e7-47bd-b8b5-8083e2a1f48f	FRIDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	c33e01ef-be92-474a-9e43-0543649735d5
3f984365-999b-4f3b-a742-c123932edb9e	MONDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	6f1475e5-0d8a-47c7-855f-ccff802aff85
3281218f-7dd6-4e31-b29e-c5e7023ce60a	TUESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	6f1475e5-0d8a-47c7-855f-ccff802aff85
cb73afce-2a3f-4694-843b-343174cb7136	WEDNESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	6f1475e5-0d8a-47c7-855f-ccff802aff85
da4558c4-3935-4874-a7ee-4d69ed9b13bf	THURSDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	6f1475e5-0d8a-47c7-855f-ccff802aff85
6a8eeef0-39a8-4875-9b7b-3c563ddc71fa	FRIDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	6f1475e5-0d8a-47c7-855f-ccff802aff85
96b9c420-43dd-49c8-81a5-37aa2790fbab	MONDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	58845959-0984-4136-a733-b75f5c9057b9
a8c055e9-3884-4c0b-8835-f6183203b824	TUESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	58845959-0984-4136-a733-b75f5c9057b9
93267e99-b8f0-46ee-9fd1-c3d83850ba9e	WEDNESDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	58845959-0984-4136-a733-b75f5c9057b9
20637731-e891-4186-9990-cd4630726f0a	THURSDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	58845959-0984-4136-a733-b75f5c9057b9
494043be-8019-4b7b-a27a-b1c29eef780c	FRIDAY	899344cc-f75d-463b-b1a5-3ecbee85a79d	58845959-0984-4136-a733-b75f5c9057b9
a670438e-5259-4edd-8d71-0462361d6a6f	THURSDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	440c17ed-5262-4800-aa19-38e5285330c3
ec6ae3ac-f202-4886-bbe3-006cd83bc93a	FRIDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	440c17ed-5262-4800-aa19-38e5285330c3
985c7e7f-151f-4b35-9cce-146f0781f5a4	MONDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7d7634c5-6a37-4b35-88fc-6dce4111bd43	TUESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0d71fc7c-33da-4082-9f87-a3b234fabd98	WEDNESDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a346e3e9-2c0b-45a0-a5f5-c3257cfb0a79	THURSDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2c13f1d6-f08f-4920-a514-cf187c60603e	FRIDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7e3ae8b7-9099-49b3-9b5f-0cdd55fb6bf8	MONDAY	14e253d0-ff9a-4614-845a-bd91c1d91ad3	d54b130a-28a8-4bb1-9030-c474369c2205
fd2c9532-c199-4155-ae0d-0a17bbf26ed8	TUESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4090e518-7b2b-476e-98c3-a1c6bfe6d0ed	WEDNESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e59c4b93-e4ba-4a24-a5ea-a9fc03b799fc	THURSDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ae6e07cb-d191-4cde-a543-17b2c4ed8d13	FRIDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
efccb375-4ce7-431f-9d47-007d9769b32f	MONDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	d54b130a-28a8-4bb1-9030-c474369c2205
6bc73f2d-5740-43c1-8dc4-9f675c281654	TUESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	d54b130a-28a8-4bb1-9030-c474369c2205
987f46db-51a4-4e77-b3ba-bb1952e98166	WEDNESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	d54b130a-28a8-4bb1-9030-c474369c2205
2202af2d-d391-4ec6-9426-748417fa7d5f	THURSDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	d54b130a-28a8-4bb1-9030-c474369c2205
3f5a71b5-2aee-4e59-84c6-6a41ad6d2e5d	FRIDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	d54b130a-28a8-4bb1-9030-c474369c2205
d4e0a891-162a-4396-9c8f-76f01f83e447	MONDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bb9f15ff-290b-4d12-9265-25f9ebdf235d	TUESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e3615711-65e5-46f7-85b2-36f261723c1a	WEDNESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bb343036-4161-49e5-8934-bb47adfce02b	THURSDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
94a67d44-48ff-4961-adaf-927d963fdd6d	FRIDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
32b17a77-b20f-4492-953c-e4eb7b28ddb3	MONDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	c33e01ef-be92-474a-9e43-0543649735d5
bc86e608-0821-41d5-be21-07f10c241da3	TUESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	c33e01ef-be92-474a-9e43-0543649735d5
9b38aa31-4b63-483c-afce-03615e3f2ebe	WEDNESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	c33e01ef-be92-474a-9e43-0543649735d5
7fb399b0-509b-4162-aa4a-a419eb691d91	THURSDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	c33e01ef-be92-474a-9e43-0543649735d5
01ca310a-dcb7-4e3e-a69c-dcd197ddbdf7	FRIDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	c33e01ef-be92-474a-9e43-0543649735d5
0c89937c-a06a-47c1-812f-f288e4b85db8	MONDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	6f1475e5-0d8a-47c7-855f-ccff802aff85
c7a708cd-cb2e-4c2e-bc46-0981e81ff7fa	TUESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	6f1475e5-0d8a-47c7-855f-ccff802aff85
2e868880-3bfc-4cb5-bd2e-00f83350e5d8	WEDNESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	6f1475e5-0d8a-47c7-855f-ccff802aff85
5419a421-7585-4628-9f13-fe6dbdf61ff3	THURSDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	6f1475e5-0d8a-47c7-855f-ccff802aff85
4963cb2a-b4bd-4176-a822-06f0b6affa67	FRIDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	6f1475e5-0d8a-47c7-855f-ccff802aff85
0f1ebbe7-1026-43b3-a081-20d1be7fdd00	MONDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	58845959-0984-4136-a733-b75f5c9057b9
b6cbc6e3-f62e-4211-b849-d6886bce0d05	TUESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	58845959-0984-4136-a733-b75f5c9057b9
1deb73e6-5eae-45a0-a8c9-799f06a1df3c	WEDNESDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	58845959-0984-4136-a733-b75f5c9057b9
f40b1864-0c13-40dc-aa48-7d15b9ba6104	THURSDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	58845959-0984-4136-a733-b75f5c9057b9
7f515f82-afd4-4bb8-8f09-0214018d7af9	FRIDAY	e1888724-e770-43fe-aa8a-3484bc05a37a	58845959-0984-4136-a733-b75f5c9057b9
\.


--
-- Data for Name: course_types; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.course_types (id, name) FROM stdin;
87047e5d-f24c-4cac-976d-afdec548937b	Vorlesung
b3a64e00-d8e0-4d61-8912-eb53cfa7510f	Übung
bec82c0d-fd47-4adb-8844-6cdd7db48691	Praktikum
c8b0c5dc-522f-4060-bf12-24a704601ec5	Seminar
737b044e-5638-4dbf-a384-8f5733ed9104	Tutorium
\.


--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.courses (id, abbreviation, block_size, casid, description, name, slots_per_week, weeks_per_semester, fk_room_type_id, fk_timetable_id) FROM stdin;
279b0444-5ebc-42ce-a01a-d2e4d39b9874	Wirtschaftsethik 1	2	SA013		Wirtschaftsethik 1	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
19c4c340-d56b-448a-a9d3-583daa46fefc	Vektorrechnung	1	SA009		Vektorrechnung	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
97baa833-1f10-4349-85a3-c179959af8ce	Allgemeine Betriebswirtschaftslehre	1	SA001		Allgemeine Betriebswirtschaftslehre	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5885d8cd-49c6-46e6-8a89-36b55de54829	Gemeinschaftskunde 1	1	SA002		Gemeinschaftskunde 1	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d1e3dcb1-fa7c-4355-b42f-90c8378679cb	Deutsch 1	2	SA004		Deutsch 1	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
24825786-1517-44ed-a97a-843f2ab2e5cf	Englisch 1	2	SA006		Englisch 1	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
db0c3159-a02b-4e36-86e4-eb3cd4d210ee	Programmierung 1	2	SA021		Programmierung 1	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
04fcd6fc-890b-421d-8137-16fe13250a42	Übung Programmierung 1	4	SA022		Übung Programmierung 1	4	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a7fd852d-5a48-40c2-9715-afeee84a39e0	Praktikum Rechnernetze (Gruppe 1)	1	SA032U1		Praktikum Rechnernetze (Gruppe 1)	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9f611bd8-64ed-4cad-9d07-a8fc6184104d	Praktikum Rechnernetze (Gruppe 2)	2	SA032U2		Praktikum Rechnernetze (Gruppe 2)	2	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b8afdaa4-2e1c-47d8-8d15-d1019fa460d5	Geometrie	1	SA037		Geometrie	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
426c7cf6-bad8-4360-bb83-33e2afe1fcb6	Medientechnik 1	1	SA038		Medientechnik 1	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6eeec8d4-efd9-4d38-8643-4ac432801489	Bildbearbeitung	1	SA042		Bildbearbeitung	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e63a3e7d-406b-4907-b08d-9256af86a2a6	Praktikum Bildbearbeitung	2	SA043		Praktikum Bildbearbeitung	2	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6f115cf8-f0f9-4cce-84ef-5e4f5bb11ba5	Übung systemnahe Programmierung (Gruppe 1)	1	SA045U1		Übung systemnahe Programmierung (Gruppe 1)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
bc4e93ce-352f-493d-9089-c72c1eeaf8e4	Übung systemnahe Programmierung (Gruppe 2)	1	SA045U2		Übung systemnahe Programmierung (Gruppe 2)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
653df30a-8013-4f51-83dd-4d94e429b18d	Tutorium Systemnahe Programmierung	1	SA046		Tutorium Systemnahe Programmierung	1	12	737b044e-5638-4dbf-a384-8f5733ed9104	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b65a8082-6f76-4f30-b9bc-142d01701d8b	ERP-Grundlagen 1	1	SA057		ERP-Grundlagen 1	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cdcb904d-32c2-4f88-9af8-b7ca23b5bc47	Labview	2	SA072		Labview	2	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c98823ee-d087-4a9e-b518-2b28faaccd5f	Übung Einführung in die Programmierung	1	SA093		Übung Einführung in die Programmierung	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
aedd23d3-b98f-4972-aa2e-acdcb8f2c5df	Werkstoffprüfung	1	SA109		Werkstoffprüfung	1	12	c8b0c5dc-522f-4060-bf12-24a704601ec5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e70ced69-5a9d-4ac5-b110-77317e06f368	Physikalisch-Naturwissenschaftliches Praktikum	1	SA122		Physikalisch-Naturwissenschaftliches Praktikum	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1e896415-6ebe-443f-b048-30725be095c3	Analysis	1	SB001		Analysis	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
05155d0d-89da-4c55-b92c-94a1387ec741	Übung Analysis	1	SB002		Übung Analysis	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6b49d198-e1bd-4710-b4e8-d9befa664a83	Discrete Mathematics	2	SB003E		Discrete Mathematics	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c	Diskrete Mathematik	2	SB003		Diskrete Mathematik	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
03019641-317d-4b74-adaa-1580ff4c99bc	Übung Diskrete Mathematik	1	SB003U		Übung Diskrete Mathematik	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
af9e38a7-5fea-42ef-b6c1-cd56d0161988	Programmstrukturen 1	1	SB004		Programmstrukturen 1	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3e4285d4-8e01-4483-9219-83079f9d3a77	Übung Programmstrukturen 1 (Abnahmen)	1	SB005A		Übung Programmstrukturen 1 (Abnahmen)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1a931c05-4408-4eb6-afdc-905ebda9edc1	Übung Programmstrukturen 1 (Betreuung)	1	SB005B		Übung Programmstrukturen 1 (Betreuung)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1f56ef6d-19b1-47a8-972d-0a9eb17e56a7	Informationstechnik	2	SB006		Informationstechnik	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5e445afa-73c0-4500-a0d8-697acd7bf58e	Einführung in die Volkswirtschaftslehre	1	SB008		Einführung in die Volkswirtschaftslehre	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e738acf7-a61b-489a-a6f9-bda344038174	Programmstrukturen 2	1	SB010		Programmstrukturen 2	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
68dd3eea-cea2-4e54-b2e3-2179938239ca	Übg. Programmstrukturen 2 (Gruppe 1)	1	SB011U1		Übg. Programmstrukturen 2 (Gruppe 1)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7d48e39c-8f0c-4db0-bd68-3c4b83e2b8d1	Übg. Programmstrukturen 2 (Gruppe 2)	1	SB011U2		Übg. Programmstrukturen 2 (Gruppe 2)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3bcee379-cb47-43a8-84f6-933a0b3de0a4	Übg. Programmstrukturen 2 (Gruppe 3)	1	SB011U3		Übg. Programmstrukturen 2 (Gruppe 3)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6bbb9b79-33ba-422f-9964-a8435805fcbd	Übg. Programmstrukturen 2 (Gruppe 4)	1	SB011U4		Übg. Programmstrukturen 2 (Gruppe 4)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
53dd2ec8-f3ba-46ef-8c66-42bfd18170b9	Rechnernetze	1	SB013		Rechnernetze	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8b90882c-0ce3-48a5-8fae-24268e66fed6	Praktikum Rechnernetze	2	SB014		Praktikum Rechnernetze	2	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
71e325fb-01c5-44fd-a0de-898927b39357	Algorithmen und Datenstrukturen	1	SB015		Algorithmen und Datenstrukturen	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6f0d248f-ea9c-4f98-8b75-c7569aa198e1	Übung Algorithmen und Datenstrukturen (Aufgabenvorstellung)	1	SB016		Übung Algorithmen und Datenstrukturen (Aufgabenvorstellung)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6a7e42da-36f8-454a-81bf-0a8bdb23b8cf	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Betreuung)	1	SB016B074B		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Betreuung)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c0aa58be-47d7-4f72-b017-9125856035e2	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 1)	1	SB016B074U1		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 1)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a666d323-152f-48af-be42-f59d6cc27deb	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 10)	1	SB016B074U10		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 10)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7c23bee8-0b73-4720-af87-28c6c180de6a	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 11)	1	SB016B074U11		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 11)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
58494593-79cc-411e-ad7f-2b52a7fc6095	Algebra	1	SA008		Algebra	5	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7d1eb5d5-dd84-4821-836b-ef6a694965f2	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 2)	1	SB016B074U2		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 2)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a7e6ba0f-a11c-455e-a15d-ab26528d3ffc	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 3)	1	SB016B074U3		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 3)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
009eb4ee-d905-4f04-bb23-7fb156ee569e	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 7)	1	SB016B074U7		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 7)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c7955389-a368-4f9a-9a48-cd015c38d841	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 9)	1	SB016B074U9		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 9)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
aea1a914-5bde-4a5c-92c1-27c9794cf738	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 4)	1	SB016B074U4		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 4)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a8bdcf92-25a5-49f5-b2d7-57c83ff424f4	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 5)	1	SB016B074U5		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 5)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1e7550cf-39bf-4394-a631-6279e4be4997	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 6)	1	SB016B074U6		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 6)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a4dd8404-9c4a-4046-b857-609f4f6bda92	Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 8)	1	SB016B074U8		Übung Algorithmen und Datenstrukturen / Systemnahe Programmierung (Gruppe 8)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a79d088c-d445-4892-90d5-065cacb9446d	Übung Algorithmen und Datenstrukturen (Gruppe 1)	1	SB016U1		Übung Algorithmen und Datenstrukturen (Gruppe 1)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
737ba05d-dffe-4973-a606-f91a3bb8c0f9	Übung Algorithmen und Datenstrukturen (Gruppe 2)	1	SB016U2		Übung Algorithmen und Datenstrukturen (Gruppe 2)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7ab751ea-9048-417d-b0fa-755328a7f385	Übung Algorithmen und Datenstrukturen (Gruppe 3)	1	SB016U3		Übung Algorithmen und Datenstrukturen (Gruppe 3)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b4bebd85-6f9b-4ba2-8ed5-2e4086d6f699	Übung Algorithmen und Datenstrukturen (Gruppe 4)	1	SB016U4		Übung Algorithmen und Datenstrukturen (Gruppe 4)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2	Induktive Statistik	1	SB017		Induktive Statistik	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
77f1a128-ea8f-466e-8c0d-998c2280c811	Einführung in Datenbanken	1	SB020		Einführung in Datenbanken	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
32a84dc4-3b53-437c-a78d-d5f5061f2b4d	Übung Einführung in Datenbanken	1	SB021		Übung Einführung in Datenbanken	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
78301a7f-a1d9-494b-8c10-8b11baa5976e	Grundlagen DLM	1	SB022D		Grundlagen DLM	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
df82acfb-e4dc-4615-9b1f-c41dcbe4803a	Grundlagen Marketing & Medien	1	SB022M		Grundlagen Marketing & Medien	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
613282ae-c93a-4cc8-b6c3-fa73f8a5326c	Operatives Produktionsmanagment	1	SB023		Operatives Produktionsmanagment	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
97e67f12-4879-4dc6-8759-e205bba8b0f9	Übung Operations Research	1	SB030		Übung Operations Research	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
60c9ed1a-a37e-47cd-986f-8b46d91ffe16	Systemanalyse	1	SB032		Systemanalyse	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8d5e74b8-7910-4ee3-b424-3697b919da65	Prozessmodellierung	2	SB033		Prozessmodellierung	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
fb46ae33-946a-46d4-a4f2-29df8b040cff	Übung Prozessmodellierung	3	SB033U		Übung Prozessmodellierung	3	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
803f1d5e-f530-4983-b815-68238a066cbf	Softwarequalität	2	SB034		Softwarequalität	12	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e211f36c-60a5-47c7-9971-f16df7bf07fc	Anwendung der Künstlichen Intelligenz	2	SB036		Anwendung der Künstlichen Intelligenz	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3d6d21de-bf1b-4fbb-aed0-ee57caa00988	Anwendungsentwicklung in ERP-Systemen	2	SB037		Anwendungsentwicklung in ERP-Systemen	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6b1271eb-54e6-49bd-ab27-c85605ec8576	Übung Anwendungsentwicklung in ERP-Systemen	4	SB038		Übung Anwendungsentwicklung in ERP-Systemen	4	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9ab1875d-9ba9-4f1f-8f12-7def153646d1	Communication Skills	4	SB043		Communication Skills	8	12	c8b0c5dc-522f-4060-bf12-24a704601ec5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e278ea08-a02d-4de2-ab78-d34b856c87d7	Entre- und Intrapreneurship	3	SB044		Entre- und Intrapreneurship	3	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7e7a157e-f4fe-49f3-9876-5f6dff88ad3a	Workshop Entre- und Intrapreneurship	1	SB045		Workshop Entre- und Intrapreneurship	1	12	737b044e-5638-4dbf-a384-8f5733ed9104	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a80a1879-1353-406e-8d2b-74da7a59c64a	Softwareprojekt	1	SB047		Softwareprojekt	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
47d63157-d363-4e90-bf84-c94e1f61c0ee	Grundlagen der BWL	2	SB056		Grundlagen der BWL	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
86961441-94bd-4771-91e3-3580d18a9f40	Einführung in die BWL	2	SB064		Einführung in die BWL	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c5273d0a-67cd-4449-aa07-8a9fbc6b3301	Einführung in die Digitaltechnik	1	SB065		Einführung in die Digitaltechnik	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
397d9d65-bfca-46fd-96b2-d81390b682b2	Grundlagen der Funktionalen Programmierung	1	SB067		Grundlagen der Funktionalen Programmierung	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
fb02acd0-7388-4507-b25a-49573981c9a1	Lineare Algebra	2	SB068		Lineare Algebra	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c28a9113-da92-43ba-8123-741f67dc4b89	Systemnahe Programmierung	1	SB071		Systemnahe Programmierung	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a03a4688-83e6-439a-a101-87bec382d6b0	Übung Grundlagen der Funktionalen Programmierung	2	SB073		Übung Grundlagen der Funktionalen Programmierung	2	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d1d75766-4b31-4f28-ad9f-9d7bb7f49570	Übung Systemnahe Programmierung (Aufgabenvorstellung)	1	SB074		Übung Systemnahe Programmierung (Aufgabenvorstellung)	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23	Grundlagen der Mediengestaltung	1	SB075G		Grundlagen der Mediengestaltung	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0b2159c6-a703-43bb-b6a1-5392153e01d7	Grundlagen der Mediengestaltung (Gruppe 1)	2	SB075GU1		Grundlagen der Mediengestaltung (Gruppe 1)	2	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3cf8659e-c9c2-4c9f-91d0-897951be0372	Grundlagen der Mediengestaltung (Gruppe 2)	2	SB075GU2		Grundlagen der Mediengestaltung (Gruppe 2)	2	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
75ac7b94-3abe-4833-994e-110fc95c2ab4	Compositing Projekt	2	SB077		Compositing Projekt	2	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f9c97c01-a1ad-4ce7-a727-303bcd2342f5	Data Visualization, Feature Engineering	1	SB079		Data Visualization, Feature Engineering	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
836ee776-ad10-4ca5-8999-0b4cc29a032b	Einführung in die Robotik	1	SB080		Einführung in die Robotik	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
839631b1-18ca-4d4a-8105-6ec7c0cf8ffd	Geometrische Modellierung und Computeranimation	2	SB081		Geometrische Modellierung und Computeranimation	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f8a8bb67-a047-439e-9956-ea4822e56e98	Praktikum Geometrische Modellierung und Computeranimation	2	SB084		Praktikum Geometrische Modellierung und Computeranimation	2	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
14fe8296-326b-4aed-ad72-315db46d9504	Praktikum Interaktive Geometrische Modellierung	6	SB085		Praktikum Interaktive Geometrische Modellierung	6	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
4aeb5881-7e48-470e-b134-12236311f9b8	Technologie der Mediengestaltung und GUI-Programmierung	3	SB089		Technologie der Mediengestaltung und GUI-Programmierung	3	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b3bd0f0e-83df-4b31-8f33-815e6853a3a9	Workshop Audio-Bearbeitung	1	SB093		Workshop Audio-Bearbeitung	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
86fa5fad-4673-48bf-94f7-d379e7f73676	Digital Marketing	2	SB094		Digital Marketing	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c3c172d0-f316-40db-a6d2-160a54352755	Digital Marketing Projekt	1	SB095		Digital Marketing Projekt	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5f84a25f-32b7-4b0f-bc74-9151f5e9e941	Grundlagen von Computer Games und interaktiven Medien	2	SB098		Grundlagen von Computer Games und interaktiven Medien	2	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a95dcb15-398c-449b-8ef6-4bc0a9c4891a	Physik für Computer Games	1	SB099P		Physik für Computer Games	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
68be0a27-ed84-4ac1-a8f2-5b22ef9cc442	Special Effects und Shaderprogrammierung	2	SB099S		Special Effects und Shaderprogrammierung	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
733205c0-613d-49bb-aff8-50a53df081c6	Praktikum Fortgeschrittene Interaktive Modellierung	6	SB100		Praktikum Fortgeschrittene Interaktive Modellierung	6	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c8520c2f-475e-4f88-a3bb-284e7cc58562	Praktikum Special Effects und Shader Programmierung	2	SB101		Praktikum Special Effects und Shader Programmierung	2	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
bf8268d8-5e12-4137-9407-be7988f0eeba	Data Literacy	1	SB104		Data Literacy	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
50402a5d-cc3a-41af-a544-46dfb70ef381	E-Commerce Grundlagen	2	SB105		E-Commerce Grundlagen	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5d617722-8aeb-4e89-b859-01d3ec382190	Grundlagen Beschaffungsmanagment	1	SB107B164		Grundlagen Beschaffungsmanagment	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d03ce83e-c104-4f7a-9aa7-62d1193d18eb	Grundlagen der Elektrotechnik	1	SB108E		Grundlagen der Elektrotechnik	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
df7e7468-87c6-4728-b792-e4058c15908d	Grundlagen der Mechanik	1	SB108M		Grundlagen der Mechanik	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c5611c9c-3d1a-4fae-9999-35acab706abc	Grundlagen der Mechanik (Tutorium)	1	SB108MT		Grundlagen der Mechanik (Tutorium)	1	12	737b044e-5638-4dbf-a384-8f5733ed9104	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7418423f-fa22-418e-86b9-7407485fe284	Human Resource Managment	2	SB109		Human Resource Managment	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
10d0833d-8416-4d03-a831-5a757a9a835c	Multi Channel Retailing	2	SB113		Multi Channel Retailing	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a65ce936-2e63-47e1-aed9-d8159f2d386b	Projekt Industrie 4.0	1	SB119		Projekt Industrie 4.0	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cea04306-6f20-431d-9904-c58271cf04f7	Übung Data Science	1	SB121		Übung Data Science	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
dba6afd0-4e82-476c-b1a4-06555587bffb	Beratungskompetenz	2	SB128		Beratungskompetenz	2	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b671851f-867f-4d2d-98fd-6f6c5c31c083	Beratungskompetenz	7	SB128B		Beratungskompetenz	7	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
31084145-ffa6-4861-9673-13c551ae20ab	Einführung IT-Managment und -Prüfung	2	SB129		Einführung IT-Managment und -Prüfung	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3979dd92-065c-40db-9c3a-3794752685df	Internationale Rechnungslegung	3	SB130R		Internationale Rechnungslegung	3	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9fcd1dd4-947e-48ad-a71c-937d87f9a894	Unternehmensbesteuerung 1	2	SB130U		Unternehmensbesteuerung 1	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
4bbc1f38-3573-469b-a204-b84123b925a3	IT-Steuerung und IT-gestütztes BPM	3	SB131		IT-Steuerung und IT-gestütztes BPM	3	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d9ded29f-0a3b-4024-b3cd-7a960ce399b7	Lebenszyklus von IT-Systemen	2	SB133		Lebenszyklus von IT-Systemen	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7c512d97-6114-4ef1-ba31-55abaf1f274e	Business Englisch (Gruppe 1)	2	SB138BV1		Business Englisch (Gruppe 1)	4	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d249752a-4f26-45d6-b9a0-4288c97d2ee1	Business Englisch (Gruppe 2)	2	SB138BV2		Business Englisch (Gruppe 2)	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f1f5bad2-246a-446e-877d-e5b956ae77fa	 Commercial English (Gruppe 1)	1	SB138CV1		 Commercial English (Gruppe 1)	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
4fb9a210-dd6d-43a4-9c64-bf56bd677a51	 Commercial English (Gruppe 2)	1	SB138CV2		 Commercial English (Gruppe 2)	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
394a96f5-4e6a-4a64-8a13-0184d37063fd	 Business Planning	1	SB139		 Business Planning	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ee549e95-aa66-4707-b957-26b96b35d068	Einführung in die Programmierung	1	SB142		Einführung in die Programmierung	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
04a38028-135f-4e88-9cfc-12066c8f1df3	Übung Einführung in die Programmierung (Tutorium)	1	SB147T		Übung Einführung in die Programmierung (Tutorium)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
26654263-ccb2-4fad-8ecc-342da43c3928	Übung Einführung in die Programmierung (Gruppe 1)	1	SB147U1		Übung Einführung in die Programmierung (Gruppe 1)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
47f982fc-369e-473c-9ab0-8096bae012e0	 Advanced Digital Marketing	2	SB149		 Advanced Digital Marketing	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f6a2c559-1f7d-4f45-adf1-8c76b2faf147	 Chemie, Chemietechnik	2	 SB161		 Chemie, Chemietechnik	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cd5f8554-fec4-4862-80c3-caff5df01ad5	Chemie, Chemietechnik (Tutorium)	1	SB161T		Chemie, Chemietechnik (Tutorium)	2	12	737b044e-5638-4dbf-a384-8f5733ed9104	52abe312-28b1-4ed3-a833-cc040ac6b8d6
91283133-2877-4bc0-a90d-f4168d088349	Übung Einführung in die Programmierung (Gruppe 2)	1	SB174U2		Übung Einführung in die Programmierung (Gruppe 2)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0750aa84-1b51-45b8-aaad-056cbf1b303b	Übung Einführung in die Programmierung (Gruppe 3)	3	SB147U3		Übung Einführung in die Programmierung (Gruppe 3)	3	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
20651a53-f4c0-415c-a66f-f4cd62a6a3b2	E-Commerce Datenmanagement	3	SB151		E-Commerce Datenmanagement	3	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1337b436-7ff9-4b79-82a8-9981f2e66521	Mobile Commerce	3	SB153M		Mobile Commerce	3	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1b702945-e962-4778-b9c9-2fce7be4ab6c	 User Experience	3	SB153U		 User Experience	3	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
555bdc44-a24e-48fc-aa5e-892e666781a5	 Einführung in die Konstruktion	1	SB163		 Einführung in die Konstruktion	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9db2d190-6068-4c34-9e5c-36e9994274aa	 Einführung in die Konstruktion	1	SB163U		 Einführung in die Konstruktion	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
64e7e3c8-c369-4913-be67-a64c7ea29d11	Ingenieurmathematik	2	SB165		Ingenieurmathematik	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f7fb4d07-cc8b-449a-a860-2100d2334658	 Online-Plattform (Konzeption & Aufbau)	2	 SB155		 Online-Plattform (Konzeption & Aufbau)	2	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2f39f7da-a37f-4ccb-8a80-61b108cc7800	 Praktikum Akustik/REM	1	SB168		 Praktikum Akustik/REM	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
99a22674-5148-4e0e-85c4-409449b8c7e3	 Praktikum Chemie	1	 SB169		 Praktikum Chemie	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a8b61e71-3df5-41bb-9d40-d0fad75e7fbf	 Praktikum Elektrizität	1	SB170		 Praktikum Elektrizität	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7532d5c1-8ffa-4806-9c04-86e7fdccd542	Praktikum Mechanik	1	SB171		Praktikum Mechanik	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
86c4b224-039c-49c4-8bbf-1d2c0d923fc6	Praktikum Optik	1	SB172		Praktikum Optik	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
04497442-8dc0-45b3-93c8-a59832f0f297	Praktikum Verfahrenstechnik	1	SB173		Praktikum Verfahrenstechnik	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
fa24d9c0-130f-499c-ba42-217e905faef6	Praktikum Wärme	1	SB174		Praktikum Wärme	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cafaf289-db67-424b-91fa-fa16a5270eda	Praktikum Werkstoffprüfung	1	SB175		Praktikum Werkstoffprüfung	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a6c524c4-6748-414d-99dc-443680f2ba63	 Produktionstechnisches Projekt	1	SB177		 Produktionstechnisches Projekt	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cfba2139-68f1-4892-8b8e-607fad765801	 Technisches Grundpraktikum	1	SB180		 Technisches Grundpraktikum	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f7ea46a8-2dd4-4a5a-88b9-9b964beb68e7	Elektronik	2	SB185		Elektronik	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
72d02727-c162-43b9-a3ab-9b3e8dc4cbd5	Regelungstechnik	1	SB188		Regelungstechnik	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
62d4edd3-b54c-4f35-aef9-d0850f792260	Übung Regelungstechnik	1	SB188U		Übung Regelungstechnik	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e1888724-e770-43fe-aa8a-3484bc05a37a	 Großintegrierte Systeme	1	 SB194		 Großintegrierte Systeme	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d0ef13bf-359f-4fff-be36-1b34a6cf72e3	Praktikum PCB-Design	1	SB196		Praktikum PCB-Design	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f311df7f-6876-4c1f-84a2-2e4dae62a914	Praktikum Rechnergestützter Entwurf digitaler Systeme	1	SB197P		Praktikum Rechnergestützter Entwurf digitaler Systeme	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
26387a0d-6d8b-41d9-9dd9-3174b23458b4	 Projekt Mikrocontroller	1	SB199		 Projekt Mikrocontroller	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7d3e4aec-1a31-46ef-81ea-0b996f9ce96c	 Workshop Rechnernetze	1	SB203		 Workshop Rechnernetze	1	12	c8b0c5dc-522f-4060-bf12-24a704601ec5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a466ef46-1050-4081-8191-f15c08a9f6db	 Praktikum Wirkprinzipien und Technologie	1	SB207		 Praktikum Wirkprinzipien und Technologie	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6505a4c0-7b39-4751-9e60-be4db3f451f9	 Projekt Intelligente Systeme	1	 SB210		 Projekt Intelligente Systeme	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
14db164d-723c-418f-a4bd-9a6631817122	 Konzepte der Betriebssysteme	1	SB225B		 Konzepte der Betriebssysteme	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
01cce519-4690-41ea-a2d8-d32007b393b4	 Projektstudie Wirtschaftsinformatik	1	SB237		 Projektstudie Wirtschaftsinformatik	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ad789a69-05f1-43be-8084-cd6a14e6202a	 E-Commerce-Praxis	1	SB240		 E-Commerce-Praxis	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
14e253d0-ff9a-4614-845a-bd91c1d91ad3	 Fundamental Programming Structures	2	SB246		 Fundamental Programming Structures	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
79e610f9-4c9f-4234-9a37-d8e1d11a6af2	 Fundamental Programming Structures Lab Course	2	SB247		 Fundamental Programming Structures Lab Course	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a48b5c93-d26c-4e45-a863-60e94af8503c	 Programming Structures Project	1	SB248		 Programming Structures Project	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
99e29aa2-9c26-4d09-9206-6173b7956d7d	 Konzepte der Datenbanktechnologie	2	SM002		 Konzepte der Datenbanktechnologie	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6c6074b4-62fc-4312-91e6-2e8a9196aca7	 Übung Konzepte der Datenbanktechnologie	2	SM003		 Übung Konzepte der Datenbanktechnologie	2	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0784ce65-0cea-4000-858f-a5bc87d86e8f	Organisationslehre	1	SM004O		Organisationslehre	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6b410de7-f666-40c0-ad19-6e7d74d0a3d7	 Strategisches Management	1	 SM004S		 Strategisches Management	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a2f4b42a-b2a8-44a6-b3dd-0c34f146e904	 Methoden der Künstlichen Intelligenz	2	SM005		 Methoden der Künstlichen Intelligenz	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b0956325-1cdb-425f-a9a6-afb752a5485d	 Distributed Systems	1	SM006		 Distributed Systems	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
86cc04fa-7cf7-475b-8404-3bd2df5dfb5d	 Tutorial: Distributed Systems	1	SM007		 Tutorial: Distributed Systems	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8c6942ac-e020-4a00-acc4-8c41409854ab	 Security Management	2	SM008		 Security Management	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4	 Digital Transformation	2	SM015		 Digital Transformation	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8310c913-dcfd-432e-b330-ee4bb33b7d3e	 IT-Consulting Projekt	1	SM017		 IT-Consulting Projekt	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9b90fa62-a329-4089-b71f-f9a70c27ac03	 Agiles Projektmanagement	7	SM018		 Agiles Projektmanagement	7	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
74a8a447-3f16-43a9-b29b-84d2e5d86a88	 Change Management	1	 SM019		 Change Management	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d9ee24ed-b9e2-41d8-b733-bc8f48b7ae0a	Workshop Aktuelle Entwicklungen in der Informatik	1	SM031		Workshop Aktuelle Entwicklungen in der Informatik	1	12	c8b0c5dc-522f-4060-bf12-24a704601ec5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
4a858055-f275-460b-a349-222cc0ca28d7	 Berechenbarkeit und Komplexität	1	SM033B		 Berechenbarkeit und Komplexität	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
22891480-943a-4cb1-a417-c760205d86bd	 Berechenbarkeit und Komplexität, Formale Spezifikation und Verifikation	1	SM033BF		 Berechenbarkeit und Komplexität, Formale Spezifikation und Verifikation	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d44fa577-b407-4d90-972b-b3d39ab4b93d	 Formale Spezifikation und Verifikation	1	 SM033F		 Formale Spezifikation und Verifikation	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2c1ea78c-8ca7-4583-aad4-a6724bc81df1	 Digitale Kommunikationssysteme	1	 SM034		 Digitale Kommunikationssysteme	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
09be9784-f02e-4c2d-accb-fd49b529e15a	 Reconfigurable Computing	1	 SM035		 Reconfigurable Computing	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
899344cc-f75d-463b-b1a5-3ecbee85a79d	 Fotorealismus und Simulation	1	SM036F		 Fotorealismus und Simulation	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
320d7753-9513-472c-ac95-cc9567a36cc0	Visualisierung	1	SM036V		Visualisierung	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
fbd5f933-b196-48bb-b49a-f8f2b1bcf563	 Projekt IT-Sicherheit	1	SM041		 Projekt IT-Sicherheit	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e9d67836-a72d-4802-a9f0-25a15e1042e5	 Innovatives Marketing	1	SM044		 Innovatives Marketing	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7	 Automatisierung in der Fertigung	1	SM047		 Automatisierung in der Fertigung	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1c0cc7bc-a82a-4919-aed2-6d95b6a4baed	 Workshop Steuerungstechnik	2	SM050		 Workshop Steuerungstechnik	2	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5926aed9-9d4d-42b1-9fa8-fd82d5706412	 Digitale Medien	4	SM051		 Digitale Medien	4	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e1482fe4-3872-4f46-a51b-9a648b9a5072	 Projekt E-Commerce	2	SM057		 Projekt E-Commerce	2	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5fdcd179-a83a-4080-a33c-905e3db55dd1	 E- Commerce Plattform-Architektur	2	SM060		 E- Commerce Plattform-Architektur	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5fa57fdf-7ba0-4063-93cf-378d7b8267a9	 E-Commerce Geschäftsmodelle	3	SM061		 E-Commerce Geschäftsmodelle	3	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e65a3a55-59ca-4533-bdf2-ba8511d594ad	 Mergers and Acquisitions	2	 SM065		 Mergers and Acquisitions	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1c7340c1-7b05-404f-8882-39906697c69c	 Marktforschung	2	 SM066		 Marktforschung	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2e103b9f-097b-4d2d-9ee9-8806713621ce	 Internationale Wirtschaft	2	 SM067		 Internationale Wirtschaft	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
885428c2-75f8-47f6-80a6-94337b9fb10f	Sensortechnik	1	  SM076		Sensortechnik	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
84a6556d-48f6-4fec-8468-7c6e9dd9c5ab	 Projekt Beschichtungstechnologie	1	SM077		 Projekt Beschichtungstechnologie	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
350475ef-1d3b-4823-8c76-7f190a27f87e	 Energietechnik	2	SM080		 Energietechnik	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
62d4892c-a2ea-456d-90c8-0eef9df99961	 Project IT Engineering	1	 SM082		 Project IT Engineering	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b0853a08-38a4-47f5-b9aa-83f315d74acf	 Medical Engineering	2	SM083		 Medical Engineering	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8	 Embedded Systems Workshop	3	SM084		 Embedded Systems Workshop	3	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ba85e730-6df5-4e9f-aef0-96577fab369a	 Dynamical Systems	2	SM085		 Dynamical Systems	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c497544b-bd79-4cf2-a63a-8cffacc1fe46	 Startup Strategy & Business Plan - A Simulation Game	1	SM094		 Startup Strategy & Business Plan - A Simulation Game	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
edcf6bf0-5a1a-41c6-ac00-56a63327adbb	 International Service Business: Concepts & Cases	2	 SM095		 International Service Business: Concepts & Cases	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d	 Sustainable Corporate Governance	2	SM100		 Sustainable Corporate Governance	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f058e2d3-bcd6-46c2-92c0-3e0aa4e71757	 Sustainable and Digital Consumer Behaviour	2	SM101		 Sustainable and Digital Consumer Behaviour	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f65faea9-66ad-40ce-8488-753078555a3f	 Sustainability Economics and Digital Platforms	2	 SM102		 Sustainability Economics and Digital Platforms	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c2380f71-ff90-46f3-8438-49f91616f6a6	 Sustainable Technologies and Climate Change	2	SM103		 Sustainable Technologies and Climate Change	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f4e3291c-758b-47f4-a1d8-75680a84be0f	Office-Software	2	SA016		Office-Software	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f68c63a1-4807-47c5-a6d0-063630b700f6	Planspiel	1	SA073		Planspiel	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f83ba309-b71e-455b-8354-5486ab7a76b7	Projekt	1	SA087		Projekt	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
32e48d76-6004-4a1c-bb22-27ee66e2d672	Kolloquium	1	SX001		Kolloquium	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
22b88adb-3e66-4b08-86d5-c6e6ecc7399f	 Systemnahe Programmierung	1	 SB072		 Systemnahe Programmierung	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8638f31d-dd3a-41df-8571-bcb665c48554	Rechnungswesen 1	2	SB007		Rechnungswesen 1	2	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e43a754b-648f-460b-88c6-0d3825b1fa76	Führungskräftetraining	4	SX006		Führungskräftetraining	4	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
94ad2b6d-975f-4746-9a3b-33c32b34a13f	Übung Exploratory Data Analysis	1	SB090		Übung Exploratory Data Analysis	1	12	87047e5d-f24c-4cac-976d-afdec548937b	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a9ca51e9-864d-4d17-b647-ff17ea2c02e6	Übung Programmstrukturen 2 (Aufgabenvorstellung)	1	SB011		Übung Programmstrukturen 2 (Aufgabenvorstellung)	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9a21bb80-9e6f-466d-bee2-96306d08b0ae	Projekt IT-Management, Consulting und Auditing	1	SB134		Projekt IT-Management, Consulting und Auditing	1	12	b3a64e00-d8e0-4d61-8912-eb53cfa7510f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
69a94b81-7ed0-428b-a3c9-679a34e47e0a	Praktikum Digitaltechnik	2	SB069U2		Praktikum Digitaltechnik	2	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e64236c3-e2ea-45a1-8a28-c9c596267c97	 Physikalisches Praktikum	1	SX004		 Physikalisches Praktikum	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
04690b0c-f46f-4bb7-8932-1d3ff3879486	 Projekt Medieninformatik	1	SB088		 Projekt Medieninformatik	1	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ae4d3b3b-f1b9-456d-89b3-84dfdafad01f	 Workshop Mikroprozessor	2	SB202		 Workshop Mikroprozessor	2	12	bec82c0d-fd47-4adb-8844-6cdd7db48691	52abe312-28b1-4ed3-a833-cc040ac6b8d6
\.


--
-- Data for Name: degree_semesters; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.degree_semesters (id, attendees, extension_name, semester_number, fk_degree_id, fk_timetable_id) FROM stdin;
e3aadc3a-56ef-479b-8057-55426fa3cc6e	0	3.0	1	f44588a3-7d6f-4e3d-9f91-d20d2a15fe97	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9ecf41d3-c236-4ad6-bdac-cf91bec9b6fe	0	3.0	5	f44588a3-7d6f-4e3d-9f91-d20d2a15fe97	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	0	3.0	3	f44588a3-7d6f-4e3d-9f91-d20d2a15fe97	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	0	3.0	3	31d1681c-6d36-4681-8d7e-bb3de2d46f2f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
147e83ad-063b-407e-b2c4-a6e564ae3691	0	19.0	1	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
47a0b28f-fa90-46a6-81a0-993f9a5fb25b	0	19.0 - Vertiefung Supply Chain & Operations Managment	3	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b3d26721-1fbf-487e-9203-31d381cdaf71	0	3.0	5	ad175879-dd8c-4ac8-b052-e0ec5c925d7a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ac6be9e3-44d8-4ba4-aa5c-d19f56c6bafa	0	3.0	5	f6def194-20a4-41c4-93bc-ed312f0ab2aa	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8cefe0bd-e356-4670-b4b4-3aba43b33a84	0	3.0	3	4672ef54-a013-44f8-9633-b927bc7e3c82	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2e9244ca-9290-4d72-aec1-bf16f9406270	0	3.0	1	4672ef54-a013-44f8-9633-b927bc7e3c82	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c1656243-932b-4d1d-b556-43115cecaf0f	0	20.0	2	2d41093b-b8b4-49b5-adee-ec8b6f69a274	52abe312-28b1-4ed3-a833-cc040ac6b8d6
874967c2-4fb0-4d6e-923b-bccc347bb267	0	20.0	5	2d41093b-b8b4-49b5-adee-ec8b6f69a274	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1e3fcca0-1abb-4da0-b38e-473794e03a13	0	20.0	4	2d41093b-b8b4-49b5-adee-ec8b6f69a274	52abe312-28b1-4ed3-a833-cc040ac6b8d6
716596fd-b5d1-4438-9f08-bb86a3777d98	0	19.0 - Vertiefung Dienstleistungsmanagment (DLM)	3	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0388459f-a061-47bf-bb0d-0a83c4ae02ec	0	20.0	5	70429d43-f7b1-4c60-9e2e-4d87b37887f2	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3f6daf0e-4bad-4591-a46d-fffac2b870d6	0	19.0	6	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
24096171-f9ef-4dfc-8cab-692e97538709	0	19.0	7	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	0	20.0 - Vertiefung Marketing & Medien	4	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0d619f97-5750-4faa-bfe5-07b744b09e23	0	20.0 - Vertiefung Marketing & Medien	3	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9017763a-8ff3-4945-853d-271f8329d078	0	3.0	1	ad175879-dd8c-4ac8-b052-e0ec5c925d7a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
947f98eb-13bd-4571-bd91-4d41a46e28de	0	20.0	3	ebc8da47-a766-4f64-8088-aae31746d887	52abe312-28b1-4ed3-a833-cc040ac6b8d6
95f3875d-4044-49e4-b45b-1ad34fb32c85	0	19.0 - Vertiefung Marketing & Medien	3	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
78776e0e-160c-4fb0-8f8c-4eea3ac00bdb	0	19.0	2	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
80790104-ae2d-4d08-bc97-2535e4592d37	0	20.0	3	2d41093b-b8b4-49b5-adee-ec8b6f69a274	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1199766d-1d61-43ed-945e-17aeb5c59523	0	20.0	2	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3d856048-3f95-42e1-b82b-be98dd48a9c5	0	20.0 - Vertiefung Marketing & Services	3	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7ad22e0c-a328-40b4-aaef-f93303cf73eb	0	20.0	1	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
06c01acf-ab79-4bd2-bab1-7ca09640ad14	0	20.0 - Vertiefung Informatik	4	23820080-1cba-444b-b04b-19c669570b06	52abe312-28b1-4ed3-a833-cc040ac6b8d6
75c4da4c-9feb-48fb-883e-3272c5215012	0	20.0 - Vertiefung Operations Managment	3	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a6cdd6e7-2239-4bf7-a152-82ff8dafee14	0	20.0	7	23820080-1cba-444b-b04b-19c669570b06	52abe312-28b1-4ed3-a833-cc040ac6b8d6
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	0	20.0 - Vertiefung Industrie 4.0	3	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e59cf090-f2e2-4a36-8a13-a6896e2580c4	0	20.0 - Vertiefung Operations Managment	5	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7191c7a3-df82-40d6-8e17-76aeb799c14a	0	20.0 - Vertiefung Marketing & Services	4	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d2d87f80-ebee-4f62-94f0-ca513d80305b	0	20.0 - Vertiefung Industrie 4.0	4	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2eab7043-67fe-4c13-8ca8-34dc418fd468	0	20.0 - Vertiefung Informatik	5	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
abc937ad-3e27-4500-8151-4f4dee4889d2	0	20.0 - Vertiefung Informatik	2	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9dc5024d-59ae-48e6-8226-338b61231c67	0	20.0 - Vertiefung Informatik	3	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5f060559-2753-47f6-ae5a-0ec0d6f680e1	0	20.0 - Vertiefung Wirtschaft	4	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
aaa83ec9-ca0c-4324-8e97-faa7b7267383	0	20.0 - Vertiefung Wirtschaft	3	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0c77b039-9808-4b26-867a-56ea95f2bd87	0	20.0 - Vertiefung Informatik	1	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
66b8c5ac-96cd-4808-8eee-241536ecee87	0	20.0	5	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
acb96ba1-7b66-41b3-8887-1ef8499ac317	0	20.0 - Vertiefung E-Commerce	4	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8301d955-cc62-430a-a03c-44a628a077d5	0	20.0 - Vertiefung Wirtschaft	1	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0af2fb78-e37f-4e4c-8457-c0296388ecf1	0	20.0	1	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
470273d7-a695-4d41-b325-136c3b427957	0	20.0	3	e815510a-5dc7-4423-9831-4dbc4174e74e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0b501e68-f122-4c68-8347-b1d3f4daddeb	0	19.0	5	38f7d821-de60-453e-8d1a-02214ac44271	52abe312-28b1-4ed3-a833-cc040ac6b8d6
01489cd9-d899-409e-a911-fcbd4f069a59	0	19.0	2	38f7d821-de60-453e-8d1a-02214ac44271	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	0	19.0	1	38f7d821-de60-453e-8d1a-02214ac44271	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3b7d656f-f890-4576-a45c-d6f0f7ea7261	0	19.0 - Vertiefung Accounting, Auditing & Taxation	3	38f7d821-de60-453e-8d1a-02214ac44271	52abe312-28b1-4ed3-a833-cc040ac6b8d6
714add9f-098f-4c0c-8365-ba92b764b0e3	0	19.0	6	38f7d821-de60-453e-8d1a-02214ac44271	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9f1d4d22-e807-4fd3-898e-42adf3388367	0	19.0 - Vertiefung Software-Gestaltung	4	38f7d821-de60-453e-8d1a-02214ac44271	52abe312-28b1-4ed3-a833-cc040ac6b8d6
50245ba2-e8a6-4280-8070-f37b60da1e5a	0	19.0 - Vertiefung Software-Gestaltung	3	38f7d821-de60-453e-8d1a-02214ac44271	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d734b340-626e-4e48-9179-d86e99a2cba7	0	3.0	5	31d1681c-6d36-4681-8d7e-bb3de2d46f2f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	0	20.0 - Vertiefung Informationsmanagement	3	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ad170c3a-fbec-4d46-948d-46b035e8cc5b	0	19.0 - Vertiefung Accounting, Auditing & Taxation	4	38f7d821-de60-453e-8d1a-02214ac44271	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ebc672c8-6533-4103-bf6f-b8d2d079d2f6	0	19.0	7	38f7d821-de60-453e-8d1a-02214ac44271	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	0	20.0	5	8c579b03-e55c-44a9-84f2-0218149a6e54	52abe312-28b1-4ed3-a833-cc040ac6b8d6
91f4d3b9-7312-4713-9814-b4d1608f56c7	0	20.0	2	8c579b03-e55c-44a9-84f2-0218149a6e54	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1ec2765f-67b5-492b-af5a-6c993bb531b7	0	20.0	3	8c579b03-e55c-44a9-84f2-0218149a6e54	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	0	20.0	4	8c579b03-e55c-44a9-84f2-0218149a6e54	52abe312-28b1-4ed3-a833-cc040ac6b8d6
075c9947-638e-4ad8-ae2b-c6183f9c79cb	0	18.0	7	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d8da5d79-1b30-4adb-a2ab-614e529265a5	0	21.3/21.4 Vertiefung Finance & Services	2	9a862683-9ac5-45ba-acb3-51edf9945671	52abe312-28b1-4ed3-a833-cc040ac6b8d6
731134b9-6130-41d9-96a5-2732ca5818e7	0	20.0 - Vertiefung Informatik	2	ebc8da47-a766-4f64-8088-aae31746d887	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f6953bbc-6636-4202-a2cb-480b48881ec4	0	20.0 - Vertiefung Informatik	1	fb5e53d2-1431-4dde-af26-2f02f68eb84f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	0	3.0	1	31d1681c-6d36-4681-8d7e-bb3de2d46f2f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7b21da1f-07a4-49e3-9c63-8713219d8c67	0	19.0 - Vertiefung Accounting, Auditing & Taxation	3	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
bf097acf-8fb0-425b-9ace-bfd6fab05670	0	3.0	3	ad175879-dd8c-4ac8-b052-e0ec5c925d7a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
07493acf-da6b-4999-a618-73248d0facff	0	20.0 - Vertiefung Informatik	3	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6ccf7885-6654-43cc-b1fd-2a03ff172f28	0	20.0 - Vertiefung Informatik	5	e815510a-5dc7-4423-9831-4dbc4174e74e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	0	20.0	1	23820080-1cba-444b-b04b-19c669570b06	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	0	20.0 - Vertiefung Wirtschaft	5	23820080-1cba-444b-b04b-19c669570b06	52abe312-28b1-4ed3-a833-cc040ac6b8d6
db0beade-4024-49c3-a961-7ec9649b98af	0	20.0 - Vertiefung Industrie 4.0	4	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9157eec8-233c-4bc7-8421-c3f6ef9757d6	0	21.0 - Vertiefung Energie- & Umwelttechnik	2	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	0	20.0 - Vertiefung SCM & Operations Management	3	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	0	22.3/22.4 Vertiefung IT-Managment	1	9a862683-9ac5-45ba-acb3-51edf9945671	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9e8d45f8-5c13-402f-bac3-d07f5e554e4e	0	20.0 - Vertiefung Marketing & Medien	1	ebc8da47-a766-4f64-8088-aae31746d887	52abe312-28b1-4ed3-a833-cc040ac6b8d6
499f9d17-42c3-4053-a9a5-c52bc8ca6893	0	20.0	1	67f8be06-9c20-4bfa-b49f-f6a61ae2c676	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b5537bcd-dcc6-4540-84ad-ba7255e3a839	0	20.0 - Vertiefung Medien	2	fb5e53d2-1431-4dde-af26-2f02f68eb84f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c69086e5-52cb-421f-8d1d-ab2dde7b3431	0	22.3/22.4 Vertiefung Buisness	1	9a65c94a-f560-48fa-a63d-303143e231f2	52abe312-28b1-4ed3-a833-cc040ac6b8d6
67582b5a-f3b2-4eac-ba12-f5b0038047a1	0	21.0 - Vertiefung IT-Consulting	1	0f1fb1e1-8cc1-4704-a023-57bb6f5ca49c	52abe312-28b1-4ed3-a833-cc040ac6b8d6
83286727-29f0-4c6b-8fc7-6bb8c9e2c745	0	21.0	3	0f1fb1e1-8cc1-4704-a023-57bb6f5ca49c	52abe312-28b1-4ed3-a833-cc040ac6b8d6
bb0f6d27-fca6-4dec-8bb0-7680a52dc914	0	22.0 - Vertiefung Energie- & Umwelttechnik	1	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	0	21.0 - Vertiefung Marketing & Medien	2	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ca15bd60-f63b-4028-bbc4-038870148105	0	20.0 - Vertiefung Operations Management	4	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2c23534e-864c-40b3-99eb-1524ef8f2901	0	20.0 - Vertiefung Energiesysteme	5	e815510a-5dc7-4423-9831-4dbc4174e74e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a6b369d4-2128-4697-8a18-d4dcdd880ad8	0	20.0 - Vertiefung Wirtschaft	4	23820080-1cba-444b-b04b-19c669570b06	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	0	20.0 - Vertiefung Elektro- & Informationstechnik	4	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	0	22.0 - Vertiefung Informatik	1	ebc8da47-a766-4f64-8088-aae31746d887	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d773c5fd-71c1-44a6-8c8e-012901197735	0	20.0	3	67f8be06-9c20-4bfa-b49f-f6a61ae2c676	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	0	20.0 - Vertiefung Informatik	2	fb5e53d2-1431-4dde-af26-2f02f68eb84f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c7355cdc-4d80-465c-b3f7-95dab9f1ccc7	0	20.0	1	5773aa79-b743-4245-b0e6-a4ba2ae72f9a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ca3915fa-573c-475c-b419-22038c42db7f	0	22.3/22.4 Vertiefung Technologies	1	9a65c94a-f560-48fa-a63d-303143e231f2	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b7be3d84-206b-4934-8882-fd01f2fc0830	0	21.0 - Vertiefung Software-Gestaltung	1	0f1fb1e1-8cc1-4704-a023-57bb6f5ca49c	52abe312-28b1-4ed3-a833-cc040ac6b8d6
fd79c747-918c-417d-b24a-f0a841ae87e0	0	21.0 - Vertiefung Optik & Sensorik	2	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
54583a28-4280-4004-baf8-1f730cf65777	0	19.0 - Vertiefung Accounting, Auditing & Taxation	4	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8b77e75d-c141-4c50-8793-f0d257e3910d	0	20.0 - Vertiefung Informatik	4	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
20282bd2-3196-4b30-99c7-6e59af3df733	0	20.0 - Vertiefung Industrie 4.0	3	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
939cb2db-d154-4e5d-bc36-342983bb35f5	0	20.0 - Vertiefung Elektronik	5	e815510a-5dc7-4423-9831-4dbc4174e74e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
641e67e1-2b27-48d8-bedb-720ae2bcfac0	0	20.0	3	233d0dc4-13d5-4022-90a4-cab25eea1875	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b83eebe3-cce3-42c1-a149-8e2f728fa398	0	20.0 - Vertiefung Informatik	5	23820080-1cba-444b-b04b-19c669570b06	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b083fec4-6264-407a-be55-461f8da6d7c5	0	20.0 - Vertiefung Informationsmanagement	4	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	0	20.0 - Vertiefung SCM & Operations Management	4	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7046e1f1-cd1a-411a-af01-96707541205d	0	20.0	5	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c2e52b2b-e537-4b3c-ad08-8200ca742443	0	20.0 - Vertiefung E-Commerce	2	ebc8da47-a766-4f64-8088-aae31746d887	52abe312-28b1-4ed3-a833-cc040ac6b8d6
da371113-c70c-472d-a07d-329072c2c8b6	0	20.0	2	67f8be06-9c20-4bfa-b49f-f6a61ae2c676	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c9605cdf-16af-4026-9239-43e3ed32fafc	0	20.0	3	fb5e53d2-1431-4dde-af26-2f02f68eb84f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
dfb31c12-2b1c-4192-a764-5dabdb373aab	0	20.0 mit Aufbauleistungen	2	5773aa79-b743-4245-b0e6-a4ba2ae72f9a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	0	19.0	2	63586f93-5388-4d3a-acbe-e4febb69c8d6	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ac28b210-5fd5-41fe-bf29-8ef880fb5746	0	21.0 - Vertiefung IT-Consulting	2	0f1fb1e1-8cc1-4704-a023-57bb6f5ca49c	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	0	22.0 - Vertiefung Marketing & Medien	1	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
98dd4fb3-4389-4bfb-849d-00be228bd6b1	0	19.0	5	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e377ca5b-5f53-457e-9ac9-e4080ec13eaa	0	20.0 - Vertiefung E-Commerce	5	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3439763c-46ac-4544-ace5-7c061841aa7c	0	20.0 - Vertiefung Marketing & Service	5	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6cfc4649-d517-49fa-8da9-7db7ed861453	0	20.0	7	e815510a-5dc7-4423-9831-4dbc4174e74e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a1a765bd-237e-4333-9329-241488469fed	0	20.0	6	23820080-1cba-444b-b04b-19c669570b06	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e9cafca2-ba61-43ab-aba3-06e9fee69d29	0	20.0 - Vertiefung Elektro- & Informationstechnik	3	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
64be1812-8e86-42a4-8914-5bbe53783977	0	22.3/22.4 Vertiefung Marketing & Medien	1	9a862683-9ac5-45ba-acb3-51edf9945671	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f283884c-6b34-4c76-af51-0d1fe3899cf5	0	21.3/21.4 Vertiefung Vertiefung IT-Managment	2	9a862683-9ac5-45ba-acb3-51edf9945671	52abe312-28b1-4ed3-a833-cc040ac6b8d6
db97b66f-359a-485f-82cc-e0d20bc7593d	0	20.0 - Vertiefung Marketing & Medien	2	ebc8da47-a766-4f64-8088-aae31746d887	52abe312-28b1-4ed3-a833-cc040ac6b8d6
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	0	20.0 - Vertiefung Technik	1	fb5e53d2-1431-4dde-af26-2f02f68eb84f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
872619c3-c679-4ef1-99b6-b064cf37ad86	0	21.0 - Vertiefung Software-Gestaltung	2	0f1fb1e1-8cc1-4704-a023-57bb6f5ca49c	52abe312-28b1-4ed3-a833-cc040ac6b8d6
4fffa586-a10d-4b32-bc19-731fc34c5e9f	0	21.0 - Vertiefung IT-Management	2	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3a16c697-73e9-41ee-b415-1663f9a045a3	0	19.0 - Vertiefung Marketing & Medien	4	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
da24248d-571a-4e21-89bf-926e8b5d7263	0	20.0 - Vertiefung Industrie 4.0	5	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7ed5f07c-e2a6-411f-875f-6ebd9a75d0ca	0	22.0 - Vertiefung Marketing & Medien	1	ebc8da47-a766-4f64-8088-aae31746d887	52abe312-28b1-4ed3-a833-cc040ac6b8d6
fc185acb-ee44-463d-b4fd-780e6be0cf55	0	20.0	2	e815510a-5dc7-4423-9831-4dbc4174e74e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
44032965-4f4a-4f70-a03a-2142ee9849d8	0	20.0	5	233d0dc4-13d5-4022-90a4-cab25eea1875	52abe312-28b1-4ed3-a833-cc040ac6b8d6
be5bb5c2-3f21-4378-89a0-939fa7b18283	0	20.0	2	23820080-1cba-444b-b04b-19c669570b06	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	0	20.0	2	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0cc16c71-33e7-453e-9de9-62c266347af2	0	18.0	6	b0b5da77-7e6c-454e-bf9e-941252606f10	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8b294708-6629-45ea-a765-9de69ade3dda	0	22.3/22.4 Vertiefung Finance & Services	1	9a862683-9ac5-45ba-acb3-51edf9945671	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3650ee8e-e074-4d08-89ed-38e9c8ea64e5	0	22.0 - Vertiefung E-Commerce 	1	ebc8da47-a766-4f64-8088-aae31746d887	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d172f5cf-44c6-4612-9ce0-5793c471ac36	0	22.0 - Vertiefung Technik	1	ebc8da47-a766-4f64-8088-aae31746d887	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3e46093f-5800-4616-aef7-d7c9c8e09fa7	0	20.0 - Vertiefung Medien	1	fb5e53d2-1431-4dde-af26-2f02f68eb84f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ac232aed-3930-4f39-92ce-5cc61e8c29f3	0	20.0	2	5773aa79-b743-4245-b0e6-a4ba2ae72f9a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2c30a071-39e9-4e34-a424-00db8169618e	0	19.0	3	63586f93-5388-4d3a-acbe-e4febb69c8d6	52abe312-28b1-4ed3-a833-cc040ac6b8d6
07e022d0-abbd-4e4b-9aea-816a448e8670	0	22.0 - Vertiefung Finance & Services	1	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3d6a1ea9-ee9a-4c08-905b-6878462cb753	0	22.0 - Vertiefung Optik & Sensoren	1	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	0	21.0 - Vertiefung Finance & Services	2	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cb6e846e-b84a-46b9-a736-c65826893923	0	19.0 - Vertiefung Diesntleistungsmanagment (DLM)	4	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e1df54c9-2a72-437f-bcce-cc49104de7f9	0	17.0 - Vertiefung Informatik	7	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
89a92679-48d0-49ee-a093-91d5c29a2bc8	0	20.0	3	23820080-1cba-444b-b04b-19c669570b06	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0bafc06b-3d60-4863-9ba2-59cc57265397	0	21.3/21.4 Vertiefung Marketing & Medien	2	9a862683-9ac5-45ba-acb3-51edf9945671	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8d9ea684-8b0b-4981-a957-e4f354edae56	0	20.0 - Vertiefung Technik	2	ebc8da47-a766-4f64-8088-aae31746d887	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7465210d-8463-403d-8a80-ccd3297bbb8b	0	20.0 - Vertiefung Technik	2	fb5e53d2-1431-4dde-af26-2f02f68eb84f	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d0456dd5-b19f-4bca-a8d2-b45cf7a7ca22	0	20.0	3	5773aa79-b743-4245-b0e6-a4ba2ae72f9a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
590342ba-0584-4734-84d1-7e9fccb5d0d4	0	20.0 mit Aufbauleistungen	1	5773aa79-b743-4245-b0e6-a4ba2ae72f9a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
15883d5f-d750-4251-8748-43d3e584058c	0	19.0	1	63586f93-5388-4d3a-acbe-e4febb69c8d6	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2e095c7c-f9a2-4a88-9780-98262fae50b3	0	22.0 - Vertiefung IT-Management	1	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6680d0c0-aa3f-4c84-8e01-e965d53d7305	0	21.0	3	62c6bc17-f641-4d3d-8952-8d8fc32b4791	52abe312-28b1-4ed3-a833-cc040ac6b8d6
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	0	19.0 - Vertiefung Supply Chain & Operationsmanagment	4	9377dd37-6230-49c1-a2e8-62c531eed572	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c0037556-d981-4811-aebe-531a48b369ef	0	20.0	1	8c579b03-e55c-44a9-84f2-0218149a6e54	52abe312-28b1-4ed3-a833-cc040ac6b8d6
eda1cc47-8a03-45d1-9ea9-a5028da92f8e	0	18.0	6	2d41093b-b8b4-49b5-adee-ec8b6f69a274	52abe312-28b1-4ed3-a833-cc040ac6b8d6
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	0	22.0	1	2d41093b-b8b4-49b5-adee-ec8b6f69a274	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e90073da-2a75-4984-ae01-36af35f3c23c	0	20.0 - Vertiefung E-Commerce	3	d1ed2f44-9cb0-470b-81cf-e9b17218b330	52abe312-28b1-4ed3-a833-cc040ac6b8d6
426bc6bb-fa15-40b7-b275-4df263e9d92f	0	20.0 - Vertiefung Wirtschaft	2	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
816c6b1c-5990-4055-b075-f298a06d3d72	0	17.0 - Vertiefung Wirtschaft	7	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	0	17.0 - Vertiefung Wirtschaft	6	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
77e13d50-94c0-4de5-86fe-38be27996019	0	17.0 - Vertiefung Informatik	6	aef19c7f-5d73-4cdc-977a-33470044df2a	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b6e581f3-b751-429f-ad73-06906cb74f73	0	20.0	1	e815510a-5dc7-4423-9831-4dbc4174e74e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7e412fc5-c9b1-4859-81fa-affc2a13606c	0	14.0	7	8c579b03-e55c-44a9-84f2-0218149a6e54	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b2b774d3-eacc-4f63-a541-c653eccb9f55	0	14.0	6	8c579b03-e55c-44a9-84f2-0218149a6e54	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8a02da81-52b7-43c0-99b4-5850c502df38	0	22.0	1	233d0dc4-13d5-4022-90a4-cab25eea1875	52abe312-28b1-4ed3-a833-cc040ac6b8d6
557c1696-d117-46f7-a42b-0b94508e7591	0	20.0	2	233d0dc4-13d5-4022-90a4-cab25eea1875	52abe312-28b1-4ed3-a833-cc040ac6b8d6
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	0	20.0	4	233d0dc4-13d5-4022-90a4-cab25eea1875	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3d31f80b-eb0d-40b5-b1cb-2e7c411ded97	0	14.0	7	233d0dc4-13d5-4022-90a4-cab25eea1875	52abe312-28b1-4ed3-a833-cc040ac6b8d6
73498071-277d-4fc0-a46d-56a121c9d65c	0	14.0	6	233d0dc4-13d5-4022-90a4-cab25eea1875	52abe312-28b1-4ed3-a833-cc040ac6b8d6
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	0	20.0	2	3e1e1017-1f31-4165-9b58-56346312c841	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	0	20.0	1	3e1e1017-1f31-4165-9b58-56346312c841	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7ab812fc-4a6e-4859-b4ef-b7d17600d0f8	0	16.0	7	3e1e1017-1f31-4165-9b58-56346312c841	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0e1c0a31-68d3-4205-954d-314ad8e74f5e	0	20.0 - Vertiefung Informatik	5	3e1e1017-1f31-4165-9b58-56346312c841	52abe312-28b1-4ed3-a833-cc040ac6b8d6
014d1f09-f00d-45d1-acf5-adee2faa51d2	0	20.0	5	10403801-9dfd-49ff-9b60-c8e0bff9c998	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cfb56050-b577-4948-98bc-3d6e79574ac0	0	20.0	3	10403801-9dfd-49ff-9b60-c8e0bff9c998	52abe312-28b1-4ed3-a833-cc040ac6b8d6
aa548264-ec56-4d08-8dee-bb5c29e3c26f	0	14.0	7	10403801-9dfd-49ff-9b60-c8e0bff9c998	52abe312-28b1-4ed3-a833-cc040ac6b8d6
23b7149f-5270-4843-b96a-b5f603966e82	0	20.0	2	10403801-9dfd-49ff-9b60-c8e0bff9c998	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	0	20.0	1	10403801-9dfd-49ff-9b60-c8e0bff9c998	52abe312-28b1-4ed3-a833-cc040ac6b8d6
\.


--
-- Data for Name: degrees; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.degrees (id, name, semester_amount, short_name, study_regulation, fk_room_type_id, fk_timetable_id) FROM stdin;
d1ed2f44-9cb0-470b-81cf-e9b17218b330	Data Science & Artifical Intelligence	5	B_Dsai	Bachelor	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
aef19c7f-5d73-4cdc-977a-33470044df2a	E-Commerce	7	B_Ecom	Bachelor	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
31d1681c-6d36-4681-8d7e-bb3de2d46f2f	Medieninformatik	5	A_ITAM	BFS	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8c579b03-e55c-44a9-84f2-0218149a6e54	Informatik	7	B_Inf	Bachelor	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
70429d43-f7b1-4c60-9e2e-4d87b37887f2	Physikalische Technik	5	A_Pa	BFS	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e815510a-5dc7-4423-9831-4dbc4174e74e	IT-Ingenieurswesen	7	B_Ite	Bachelor	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ad175879-dd8c-4ac8-b052-e0ec5c925d7a	Kaufmännische/r Assitent/in	5	A_Kai	BFS	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f44588a3-7d6f-4e3d-9f91-d20d2a15fe97	Softwareentwicklung	5	A_Itas	BFS	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f6def194-20a4-41c4-93bc-ed312f0ab2aa	Technische Informatik	5	A_Itat	BFS	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
4672ef54-a013-44f8-9633-b927bc7e3c82	Wirtschaftsinformatik	5	A_Itaw	BFS	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2d41093b-b8b4-49b5-adee-ec8b6f69a274	Computer Games Technology	6	B_Cgt	Bachelor	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5773aa79-b743-4245-b0e6-a4ba2ae72f9a	IT-Engineering	3	M_ITE	Master	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3e1e1017-1f31-4165-9b58-56346312c841	Smart Technology	7	B_STEC	Bachelor	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9a862683-9ac5-45ba-acb3-51edf9945671	Betriebswirtschaftslehre	3	M_BWL	Master	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
38f7d821-de60-453e-8d1a-02214ac44271	IT-Managment/ -Consulting & -Auditing	7	B_Imca	Bachelor	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
fb5e53d2-1431-4dde-af26-2f02f68eb84f	Informatik	3	M_Inf	Master	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
23820080-1cba-444b-b04b-19c669570b06	Wirtschaftsinformatik	7	B_Winf	Bachelor	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b0b5da77-7e6c-454e-bf9e-941252606f10	Wirtschaftsingenieurwesen	7	B_Wing	Bachelor	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0f1fb1e1-8cc1-4704-a023-57bb6f5ca49c	Wirtschaftsinformatik/ IT-Managment	3	M_Wing	Master	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
67f8be06-9c20-4bfa-b49f-f6a61ae2c676	E-Commerce	3	M_ECom	Master	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
63586f93-5388-4d3a-acbe-e4febb69c8d6	IT-Sicherheit	3	M_ITS	Master	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9a65c94a-f560-48fa-a63d-303143e231f2	Sustainable & Digital Business Managment	3	M_SDBM	Master	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ebc8da47-a766-4f64-8088-aae31746d887	Data Science & Artifical Intelligence	3	M_DSAI	Master	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
62c6bc17-f641-4d3d-8952-8d8fc32b4791	Wirschaftsingenieurswesen	3	M_Wing	Master	c04e628b-d72d-4c3f-b901-371ca68d9aea	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9377dd37-6230-49c1-a2e8-62c531eed572	Betriebswirtschaftslehre	7	B_Bwl	Bachelor	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
233d0dc4-13d5-4022-90a4-cab25eea1875	Medieninformatik	7	B_Minf	Bachelor	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
10403801-9dfd-49ff-9b60-c8e0bff9c998	Technische Informatik	7	B_Tinf	Bachelor	842d7750-2e20-41d3-8872-d054f6fe07e5	52abe312-28b1-4ed3-a833-cc040ac6b8d6
\.


--
-- Data for Name: degreesemester_course; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.degreesemester_course (fk_degreesemester_id, fk_course_id) FROM stdin;
e3aadc3a-56ef-479b-8057-55426fa3cc6e	97baa833-1f10-4349-85a3-c179959af8ce
e3aadc3a-56ef-479b-8057-55426fa3cc6e	5885d8cd-49c6-46e6-8a89-36b55de54829
e3aadc3a-56ef-479b-8057-55426fa3cc6e	58494593-79cc-411e-ad7f-2b52a7fc6095
e3aadc3a-56ef-479b-8057-55426fa3cc6e	19c4c340-d56b-448a-a9d3-583daa46fefc
e3aadc3a-56ef-479b-8057-55426fa3cc6e	f4e3291c-758b-47f4-a1d8-75680a84be0f
e3aadc3a-56ef-479b-8057-55426fa3cc6e	db0c3159-a02b-4e36-86e4-eb3cd4d210ee
e3aadc3a-56ef-479b-8057-55426fa3cc6e	04fcd6fc-890b-421d-8137-16fe13250a42
e3aadc3a-56ef-479b-8057-55426fa3cc6e	b65a8082-6f76-4f30-b9bc-142d01701d8b
e3aadc3a-56ef-479b-8057-55426fa3cc6e	c5273d0a-67cd-4449-aa07-8a9fbc6b3301
e3aadc3a-56ef-479b-8057-55426fa3cc6e	32e48d76-6004-4a1c-bb22-27ee66e2d672
0388459f-a061-47bf-bb0d-0a83c4ae02ec	6f115cf8-f0f9-4cce-84ef-5e4f5bb11ba5
0388459f-a061-47bf-bb0d-0a83c4ae02ec	bc4e93ce-352f-493d-9089-c72c1eeaf8e4
0388459f-a061-47bf-bb0d-0a83c4ae02ec	653df30a-8013-4f51-83dd-4d94e429b18d
0388459f-a061-47bf-bb0d-0a83c4ae02ec	cdcb904d-32c2-4f88-9af8-b7ca23b5bc47
0388459f-a061-47bf-bb0d-0a83c4ae02ec	aedd23d3-b98f-4972-aa2e-acdcb8f2c5df
0388459f-a061-47bf-bb0d-0a83c4ae02ec	e70ced69-5a9d-4ac5-b110-77317e06f368
0388459f-a061-47bf-bb0d-0a83c4ae02ec	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
0388459f-a061-47bf-bb0d-0a83c4ae02ec	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
0388459f-a061-47bf-bb0d-0a83c4ae02ec	555bdc44-a24e-48fc-aa5e-892e666781a5
0388459f-a061-47bf-bb0d-0a83c4ae02ec	9db2d190-6068-4c34-9e5c-36e9994274aa
0388459f-a061-47bf-bb0d-0a83c4ae02ec	32e48d76-6004-4a1c-bb22-27ee66e2d672
89a92679-48d0-49ee-a093-91d5c29a2bc8	5e445afa-73c0-4500-a0d8-697acd7bf58e
89a92679-48d0-49ee-a093-91d5c29a2bc8	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
89a92679-48d0-49ee-a093-91d5c29a2bc8	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
89a92679-48d0-49ee-a093-91d5c29a2bc8	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
89a92679-48d0-49ee-a093-91d5c29a2bc8	c0aa58be-47d7-4f72-b017-9125856035e2
89a92679-48d0-49ee-a093-91d5c29a2bc8	7d1eb5d5-dd84-4821-836b-ef6a694965f2
89a92679-48d0-49ee-a093-91d5c29a2bc8	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
abc937ad-3e27-4500-8151-4f4dee4889d2	e738acf7-a61b-489a-a6f9-bda344038174
abc937ad-3e27-4500-8151-4f4dee4889d2	3bcee379-cb47-43a8-84f6-933a0b3de0a4
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	97baa833-1f10-4349-85a3-c179959af8ce
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	5885d8cd-49c6-46e6-8a89-36b55de54829
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	58494593-79cc-411e-ad7f-2b52a7fc6095
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	19c4c340-d56b-448a-a9d3-583daa46fefc
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	f4e3291c-758b-47f4-a1d8-75680a84be0f
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	db0c3159-a02b-4e36-86e4-eb3cd4d210ee
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	04fcd6fc-890b-421d-8137-16fe13250a42
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	b8afdaa4-2e1c-47d8-8d15-d1019fa460d5
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	426c7cf6-bad8-4360-bb83-33e2afe1fcb6
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	c5273d0a-67cd-4449-aa07-8a9fbc6b3301
50c8be55-f158-4864-ad6c-4ac7afd1f8d4	32e48d76-6004-4a1c-bb22-27ee66e2d672
cb6e846e-b84a-46b9-a736-c65826893923	77f1a128-ea8f-466e-8c0d-998c2280c811
abc937ad-3e27-4500-8151-4f4dee4889d2	47d63157-d363-4e90-bf84-c94e1f61c0ee
abc937ad-3e27-4500-8151-4f4dee4889d2	86fa5fad-4673-48bf-94f7-d379e7f73676
abc937ad-3e27-4500-8151-4f4dee4889d2	c3c172d0-f316-40db-a6d2-160a54352755
abc937ad-3e27-4500-8151-4f4dee4889d2	bf8268d8-5e12-4137-9407-be7988f0eeba
abc937ad-3e27-4500-8151-4f4dee4889d2	50402a5d-cc3a-41af-a544-46dfb70ef381
abc937ad-3e27-4500-8151-4f4dee4889d2	cea04306-6f20-431d-9904-c58271cf04f7
abc937ad-3e27-4500-8151-4f4dee4889d2	32e48d76-6004-4a1c-bb22-27ee66e2d672
abc937ad-3e27-4500-8151-4f4dee4889d2	8638f31d-dd3a-41df-8571-bcb665c48554
abc937ad-3e27-4500-8151-4f4dee4889d2	a9ca51e9-864d-4d17-b647-ff17ea2c02e6
89a92679-48d0-49ee-a093-91d5c29a2bc8	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
89a92679-48d0-49ee-a093-91d5c29a2bc8	77f1a128-ea8f-466e-8c0d-998c2280c811
89a92679-48d0-49ee-a093-91d5c29a2bc8	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
89a92679-48d0-49ee-a093-91d5c29a2bc8	32e48d76-6004-4a1c-bb22-27ee66e2d672
cfb56050-b577-4948-98bc-3d6e79574ac0	71e325fb-01c5-44fd-a0de-898927b39357
cfb56050-b577-4948-98bc-3d6e79574ac0	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
cfb56050-b577-4948-98bc-3d6e79574ac0	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
cfb56050-b577-4948-98bc-3d6e79574ac0	a8bdcf92-25a5-49f5-b2d7-57c83ff424f4
cfb56050-b577-4948-98bc-3d6e79574ac0	1e7550cf-39bf-4394-a631-6279e4be4997
cfb56050-b577-4948-98bc-3d6e79574ac0	fb02acd0-7388-4507-b25a-49573981c9a1
cfb56050-b577-4948-98bc-3d6e79574ac0	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
cfb56050-b577-4948-98bc-3d6e79574ac0	64e7e3c8-c369-4913-be67-a64c7ea29d11
cb6e846e-b84a-46b9-a736-c65826893923	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
cb6e846e-b84a-46b9-a736-c65826893923	78301a7f-a1d9-494b-8c10-8b11baa5976e
cb6e846e-b84a-46b9-a736-c65826893923	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
cb6e846e-b84a-46b9-a736-c65826893923	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
cb6e846e-b84a-46b9-a736-c65826893923	9ab1875d-9ba9-4f1f-8f12-7def153646d1
cfb56050-b577-4948-98bc-3d6e79574ac0	f7ea46a8-2dd4-4a5a-88b9-9b964beb68e7
cfb56050-b577-4948-98bc-3d6e79574ac0	32e48d76-6004-4a1c-bb22-27ee66e2d672
cfb56050-b577-4948-98bc-3d6e79574ac0	22b88adb-3e66-4b08-86d5-c6e6ecc7399f
3439763c-46ac-4544-ace5-7c061841aa7c	e211f36c-60a5-47c7-9971-f16df7bf07fc
3439763c-46ac-4544-ace5-7c061841aa7c	86fa5fad-4673-48bf-94f7-d379e7f73676
3439763c-46ac-4544-ace5-7c061841aa7c	c3c172d0-f316-40db-a6d2-160a54352755
3439763c-46ac-4544-ace5-7c061841aa7c	7418423f-fa22-418e-86b9-7407485fe284
3439763c-46ac-4544-ace5-7c061841aa7c	32e48d76-6004-4a1c-bb22-27ee66e2d672
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	279b0444-5ebc-42ce-a01a-d2e4d39b9874
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	d1e3dcb1-fa7c-4355-b42f-90c8378679cb
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	24825786-1517-44ed-a97a-843f2ab2e5cf
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	a7fd852d-5a48-40c2-9715-afeee84a39e0
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	6f115cf8-f0f9-4cce-84ef-5e4f5bb11ba5
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	bc4e93ce-352f-493d-9089-c72c1eeaf8e4
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	653df30a-8013-4f51-83dd-4d94e429b18d
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	03019641-317d-4b74-adaa-1580ff4c99bc
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	77f1a128-ea8f-466e-8c0d-998c2280c811
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
d56fe244-e086-4d0c-86ae-9bb65fdbe7d3	32e48d76-6004-4a1c-bb22-27ee66e2d672
3439763c-46ac-4544-ace5-7c061841aa7c	e43a754b-648f-460b-88c6-0d3825b1fa76
d172f5cf-44c6-4612-9ce0-5793c471ac36	0784ce65-0cea-4000-858f-a5bc87d86e8f
d172f5cf-44c6-4612-9ce0-5793c471ac36	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
d172f5cf-44c6-4612-9ce0-5793c471ac36	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
d172f5cf-44c6-4612-9ce0-5793c471ac36	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
d172f5cf-44c6-4612-9ce0-5793c471ac36	9b90fa62-a329-4089-b71f-f9a70c27ac03
d172f5cf-44c6-4612-9ce0-5793c471ac36	74a8a447-3f16-43a9-b29b-84d2e5d86a88
d172f5cf-44c6-4612-9ce0-5793c471ac36	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
d172f5cf-44c6-4612-9ce0-5793c471ac36	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
d172f5cf-44c6-4612-9ce0-5793c471ac36	32e48d76-6004-4a1c-bb22-27ee66e2d672
d172f5cf-44c6-4612-9ce0-5793c471ac36	e43a754b-648f-460b-88c6-0d3825b1fa76
bf097acf-8fb0-425b-9ace-bfd6fab05670	279b0444-5ebc-42ce-a01a-d2e4d39b9874
bf097acf-8fb0-425b-9ace-bfd6fab05670	a7fd852d-5a48-40c2-9715-afeee84a39e0
bf097acf-8fb0-425b-9ace-bfd6fab05670	c98823ee-d087-4a9e-b518-2b28faaccd5f
bf097acf-8fb0-425b-9ace-bfd6fab05670	5e445afa-73c0-4500-a0d8-697acd7bf58e
bf097acf-8fb0-425b-9ace-bfd6fab05670	77f1a128-ea8f-466e-8c0d-998c2280c811
bf097acf-8fb0-425b-9ace-bfd6fab05670	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
bf097acf-8fb0-425b-9ace-bfd6fab05670	78301a7f-a1d9-494b-8c10-8b11baa5976e
bf097acf-8fb0-425b-9ace-bfd6fab05670	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
bf097acf-8fb0-425b-9ace-bfd6fab05670	9ab1875d-9ba9-4f1f-8f12-7def153646d1
bf097acf-8fb0-425b-9ace-bfd6fab05670	ee549e95-aa66-4707-b957-26b96b35d068
bf097acf-8fb0-425b-9ace-bfd6fab05670	04a38028-135f-4e88-9cfc-12066c8f1df3
bf097acf-8fb0-425b-9ace-bfd6fab05670	f68c63a1-4807-47c5-a6d0-063630b700f6
bf097acf-8fb0-425b-9ace-bfd6fab05670	32e48d76-6004-4a1c-bb22-27ee66e2d672
d734b340-626e-4e48-9179-d86e99a2cba7	32e48d76-6004-4a1c-bb22-27ee66e2d672
91f4d3b9-7312-4713-9814-b4d1608f56c7	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
91f4d3b9-7312-4713-9814-b4d1608f56c7	e738acf7-a61b-489a-a6f9-bda344038174
91f4d3b9-7312-4713-9814-b4d1608f56c7	6bbb9b79-33ba-422f-9964-a8435805fcbd
91f4d3b9-7312-4713-9814-b4d1608f56c7	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
91f4d3b9-7312-4713-9814-b4d1608f56c7	86961441-94bd-4771-91e3-3580d18a9f40
91f4d3b9-7312-4713-9814-b4d1608f56c7	397d9d65-bfca-46fd-96b2-d81390b682b2
91f4d3b9-7312-4713-9814-b4d1608f56c7	a03a4688-83e6-439a-a101-87bec382d6b0
91f4d3b9-7312-4713-9814-b4d1608f56c7	32e48d76-6004-4a1c-bb22-27ee66e2d672
2eab7043-67fe-4c13-8ca8-34dc418fd468	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
2eab7043-67fe-4c13-8ca8-34dc418fd468	8d5e74b8-7910-4ee3-b424-3697b919da65
2eab7043-67fe-4c13-8ca8-34dc418fd468	fb46ae33-946a-46d4-a4f2-29df8b040cff
2eab7043-67fe-4c13-8ca8-34dc418fd468	e211f36c-60a5-47c7-9971-f16df7bf07fc
2eab7043-67fe-4c13-8ca8-34dc418fd468	86fa5fad-4673-48bf-94f7-d379e7f73676
2eab7043-67fe-4c13-8ca8-34dc418fd468	c3c172d0-f316-40db-a6d2-160a54352755
2eab7043-67fe-4c13-8ca8-34dc418fd468	32e48d76-6004-4a1c-bb22-27ee66e2d672
2eab7043-67fe-4c13-8ca8-34dc418fd468	e43a754b-648f-460b-88c6-0d3825b1fa76
8d9ea684-8b0b-4981-a957-e4f354edae56	0784ce65-0cea-4000-858f-a5bc87d86e8f
8d9ea684-8b0b-4981-a957-e4f354edae56	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
8d9ea684-8b0b-4981-a957-e4f354edae56	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
8d9ea684-8b0b-4981-a957-e4f354edae56	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
8d9ea684-8b0b-4981-a957-e4f354edae56	9b90fa62-a329-4089-b71f-f9a70c27ac03
8d9ea684-8b0b-4981-a957-e4f354edae56	74a8a447-3f16-43a9-b29b-84d2e5d86a88
2e9244ca-9290-4d72-aec1-bf16f9406270	97baa833-1f10-4349-85a3-c179959af8ce
2e9244ca-9290-4d72-aec1-bf16f9406270	5885d8cd-49c6-46e6-8a89-36b55de54829
2e9244ca-9290-4d72-aec1-bf16f9406270	58494593-79cc-411e-ad7f-2b52a7fc6095
2e9244ca-9290-4d72-aec1-bf16f9406270	19c4c340-d56b-448a-a9d3-583daa46fefc
2e9244ca-9290-4d72-aec1-bf16f9406270	f4e3291c-758b-47f4-a1d8-75680a84be0f
2e9244ca-9290-4d72-aec1-bf16f9406270	db0c3159-a02b-4e36-86e4-eb3cd4d210ee
2e9244ca-9290-4d72-aec1-bf16f9406270	04fcd6fc-890b-421d-8137-16fe13250a42
2e9244ca-9290-4d72-aec1-bf16f9406270	b65a8082-6f76-4f30-b9bc-142d01701d8b
2e9244ca-9290-4d72-aec1-bf16f9406270	c5273d0a-67cd-4449-aa07-8a9fbc6b3301
2e9244ca-9290-4d72-aec1-bf16f9406270	32e48d76-6004-4a1c-bb22-27ee66e2d672
147e83ad-063b-407e-b2c4-a6e564ae3691	1e896415-6ebe-443f-b048-30725be095c3
147e83ad-063b-407e-b2c4-a6e564ae3691	05155d0d-89da-4c55-b92c-94a1387ec741
147e83ad-063b-407e-b2c4-a6e564ae3691	8638f31d-dd3a-41df-8571-bcb665c48554
147e83ad-063b-407e-b2c4-a6e564ae3691	5e445afa-73c0-4500-a0d8-697acd7bf58e
147e83ad-063b-407e-b2c4-a6e564ae3691	47d63157-d363-4e90-bf84-c94e1f61c0ee
147e83ad-063b-407e-b2c4-a6e564ae3691	7c512d97-6114-4ef1-ba31-55abaf1f274e
147e83ad-063b-407e-b2c4-a6e564ae3691	f1f5bad2-246a-446e-877d-e5b956ae77fa
147e83ad-063b-407e-b2c4-a6e564ae3691	c98823ee-d087-4a9e-b518-2b28faaccd5f
cb6e846e-b84a-46b9-a736-c65826893923	7418423f-fa22-418e-86b9-7407485fe284
cb6e846e-b84a-46b9-a736-c65826893923	394a96f5-4e6a-4a64-8a13-0184d37063fd
cb6e846e-b84a-46b9-a736-c65826893923	32e48d76-6004-4a1c-bb22-27ee66e2d672
cb6e846e-b84a-46b9-a736-c65826893923	97e67f12-4879-4dc6-8759-e205bba8b0f9
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	77f1a128-ea8f-466e-8c0d-998c2280c811
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	78301a7f-a1d9-494b-8c10-8b11baa5976e
54583a28-4280-4004-baf8-1f730cf65777	77f1a128-ea8f-466e-8c0d-998c2280c811
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
54583a28-4280-4004-baf8-1f730cf65777	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	9ab1875d-9ba9-4f1f-8f12-7def153646d1
54583a28-4280-4004-baf8-1f730cf65777	78301a7f-a1d9-494b-8c10-8b11baa5976e
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	7418423f-fa22-418e-86b9-7407485fe284
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	394a96f5-4e6a-4a64-8a13-0184d37063fd
54583a28-4280-4004-baf8-1f730cf65777	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	32e48d76-6004-4a1c-bb22-27ee66e2d672
54583a28-4280-4004-baf8-1f730cf65777	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
36823ddb-98a1-4202-ab2d-4e56e0d8e7d1	97e67f12-4879-4dc6-8759-e205bba8b0f9
54583a28-4280-4004-baf8-1f730cf65777	97e67f12-4879-4dc6-8759-e205bba8b0f9
54583a28-4280-4004-baf8-1f730cf65777	9ab1875d-9ba9-4f1f-8f12-7def153646d1
54583a28-4280-4004-baf8-1f730cf65777	7418423f-fa22-418e-86b9-7407485fe284
54583a28-4280-4004-baf8-1f730cf65777	394a96f5-4e6a-4a64-8a13-0184d37063fd
3a16c697-73e9-41ee-b415-1663f9a045a3	77f1a128-ea8f-466e-8c0d-998c2280c811
54583a28-4280-4004-baf8-1f730cf65777	32e48d76-6004-4a1c-bb22-27ee66e2d672
3a16c697-73e9-41ee-b415-1663f9a045a3	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
3a16c697-73e9-41ee-b415-1663f9a045a3	78301a7f-a1d9-494b-8c10-8b11baa5976e
3a16c697-73e9-41ee-b415-1663f9a045a3	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
3a16c697-73e9-41ee-b415-1663f9a045a3	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
3a16c697-73e9-41ee-b415-1663f9a045a3	9ab1875d-9ba9-4f1f-8f12-7def153646d1
3a16c697-73e9-41ee-b415-1663f9a045a3	7418423f-fa22-418e-86b9-7407485fe284
3a16c697-73e9-41ee-b415-1663f9a045a3	394a96f5-4e6a-4a64-8a13-0184d37063fd
3a16c697-73e9-41ee-b415-1663f9a045a3	32e48d76-6004-4a1c-bb22-27ee66e2d672
3a16c697-73e9-41ee-b415-1663f9a045a3	97e67f12-4879-4dc6-8759-e205bba8b0f9
3f6daf0e-4bad-4591-a46d-fffac2b870d6	e43a754b-648f-460b-88c6-0d3825b1fa76
98dd4fb3-4389-4bfb-849d-00be228bd6b1	32e48d76-6004-4a1c-bb22-27ee66e2d672
3f6daf0e-4bad-4591-a46d-fffac2b870d6	32e48d76-6004-4a1c-bb22-27ee66e2d672
98dd4fb3-4389-4bfb-849d-00be228bd6b1	e43a754b-648f-460b-88c6-0d3825b1fa76
98dd4fb3-4389-4bfb-849d-00be228bd6b1	97e67f12-4879-4dc6-8759-e205bba8b0f9
24096171-f9ef-4dfc-8cab-692e97538709	32e48d76-6004-4a1c-bb22-27ee66e2d672
1e3fcca0-1abb-4da0-b38e-473794e03a13	8b90882c-0ce3-48a5-8fae-24268e66fed6
874967c2-4fb0-4d6e-923b-bccc347bb267	e211f36c-60a5-47c7-9971-f16df7bf07fc
eda1cc47-8a03-45d1-9ea9-a5028da92f8e	14fe8296-326b-4aed-ad72-315db46d9504
eda1cc47-8a03-45d1-9ea9-a5028da92f8e	32e48d76-6004-4a1c-bb22-27ee66e2d672
1e3fcca0-1abb-4da0-b38e-473794e03a13	71e325fb-01c5-44fd-a0de-898927b39357
1e3fcca0-1abb-4da0-b38e-473794e03a13	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
1e3fcca0-1abb-4da0-b38e-473794e03a13	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
80790104-ae2d-4d08-bc97-2535e4592d37	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
80790104-ae2d-4d08-bc97-2535e4592d37	71e325fb-01c5-44fd-a0de-898927b39357
80790104-ae2d-4d08-bc97-2535e4592d37	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
874967c2-4fb0-4d6e-923b-bccc347bb267	839631b1-18ca-4d4a-8105-6ec7c0cf8ffd
1e3fcca0-1abb-4da0-b38e-473794e03a13	c0aa58be-47d7-4f72-b017-9125856035e2
1e3fcca0-1abb-4da0-b38e-473794e03a13	7d1eb5d5-dd84-4821-836b-ef6a694965f2
1e3fcca0-1abb-4da0-b38e-473794e03a13	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
1e3fcca0-1abb-4da0-b38e-473794e03a13	aea1a914-5bde-4a5c-92c1-27c9794cf738
716596fd-b5d1-4438-9f08-bb86a3777d98	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
716596fd-b5d1-4438-9f08-bb86a3777d98	77f1a128-ea8f-466e-8c0d-998c2280c811
716596fd-b5d1-4438-9f08-bb86a3777d98	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
716596fd-b5d1-4438-9f08-bb86a3777d98	78301a7f-a1d9-494b-8c10-8b11baa5976e
716596fd-b5d1-4438-9f08-bb86a3777d98	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
716596fd-b5d1-4438-9f08-bb86a3777d98	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
716596fd-b5d1-4438-9f08-bb86a3777d98	7418423f-fa22-418e-86b9-7407485fe284
716596fd-b5d1-4438-9f08-bb86a3777d98	394a96f5-4e6a-4a64-8a13-0184d37063fd
716596fd-b5d1-4438-9f08-bb86a3777d98	32e48d76-6004-4a1c-bb22-27ee66e2d672
95f3875d-4044-49e4-b45b-1ad34fb32c85	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
95f3875d-4044-49e4-b45b-1ad34fb32c85	77f1a128-ea8f-466e-8c0d-998c2280c811
95f3875d-4044-49e4-b45b-1ad34fb32c85	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
95f3875d-4044-49e4-b45b-1ad34fb32c85	78301a7f-a1d9-494b-8c10-8b11baa5976e
95f3875d-4044-49e4-b45b-1ad34fb32c85	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
95f3875d-4044-49e4-b45b-1ad34fb32c85	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
95f3875d-4044-49e4-b45b-1ad34fb32c85	7418423f-fa22-418e-86b9-7407485fe284
95f3875d-4044-49e4-b45b-1ad34fb32c85	394a96f5-4e6a-4a64-8a13-0184d37063fd
95f3875d-4044-49e4-b45b-1ad34fb32c85	32e48d76-6004-4a1c-bb22-27ee66e2d672
7b21da1f-07a4-49e3-9c63-8713219d8c67	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
7b21da1f-07a4-49e3-9c63-8713219d8c67	77f1a128-ea8f-466e-8c0d-998c2280c811
7b21da1f-07a4-49e3-9c63-8713219d8c67	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
7b21da1f-07a4-49e3-9c63-8713219d8c67	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
7b21da1f-07a4-49e3-9c63-8713219d8c67	7418423f-fa22-418e-86b9-7407485fe284
7b21da1f-07a4-49e3-9c63-8713219d8c67	3979dd92-065c-40db-9c3a-3794752685df
7b21da1f-07a4-49e3-9c63-8713219d8c67	9fcd1dd4-947e-48ad-a71c-937d87f9a894
7b21da1f-07a4-49e3-9c63-8713219d8c67	394a96f5-4e6a-4a64-8a13-0184d37063fd
7b21da1f-07a4-49e3-9c63-8713219d8c67	32e48d76-6004-4a1c-bb22-27ee66e2d672
47a0b28f-fa90-46a6-81a0-993f9a5fb25b	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
47a0b28f-fa90-46a6-81a0-993f9a5fb25b	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
47a0b28f-fa90-46a6-81a0-993f9a5fb25b	77f1a128-ea8f-466e-8c0d-998c2280c811
47a0b28f-fa90-46a6-81a0-993f9a5fb25b	78301a7f-a1d9-494b-8c10-8b11baa5976e
47a0b28f-fa90-46a6-81a0-993f9a5fb25b	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
47a0b28f-fa90-46a6-81a0-993f9a5fb25b	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
47a0b28f-fa90-46a6-81a0-993f9a5fb25b	7418423f-fa22-418e-86b9-7407485fe284
47a0b28f-fa90-46a6-81a0-993f9a5fb25b	394a96f5-4e6a-4a64-8a13-0184d37063fd
47a0b28f-fa90-46a6-81a0-993f9a5fb25b	32e48d76-6004-4a1c-bb22-27ee66e2d672
1e3fcca0-1abb-4da0-b38e-473794e03a13	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
1e3fcca0-1abb-4da0-b38e-473794e03a13	77f1a128-ea8f-466e-8c0d-998c2280c811
1e3fcca0-1abb-4da0-b38e-473794e03a13	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
1e3fcca0-1abb-4da0-b38e-473794e03a13	e211f36c-60a5-47c7-9971-f16df7bf07fc
1e3fcca0-1abb-4da0-b38e-473794e03a13	9ab1875d-9ba9-4f1f-8f12-7def153646d1
1e3fcca0-1abb-4da0-b38e-473794e03a13	c28a9113-da92-43ba-8123-741f67dc4b89
1e3fcca0-1abb-4da0-b38e-473794e03a13	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
1e3fcca0-1abb-4da0-b38e-473794e03a13	86fa5fad-4673-48bf-94f7-d379e7f73676
1e3fcca0-1abb-4da0-b38e-473794e03a13	c3c172d0-f316-40db-a6d2-160a54352755
1e3fcca0-1abb-4da0-b38e-473794e03a13	32e48d76-6004-4a1c-bb22-27ee66e2d672
9ecf41d3-c236-4ad6-bdac-cf91bec9b6fe	3d6d21de-bf1b-4fbb-aed0-ee57caa00988
9ecf41d3-c236-4ad6-bdac-cf91bec9b6fe	6b1271eb-54e6-49bd-ab27-c85605ec8576
9ecf41d3-c236-4ad6-bdac-cf91bec9b6fe	397d9d65-bfca-46fd-96b2-d81390b682b2
9ecf41d3-c236-4ad6-bdac-cf91bec9b6fe	a03a4688-83e6-439a-a101-87bec382d6b0
9ecf41d3-c236-4ad6-bdac-cf91bec9b6fe	32e48d76-6004-4a1c-bb22-27ee66e2d672
da24248d-571a-4e21-89bf-926e8b5d7263	e211f36c-60a5-47c7-9971-f16df7bf07fc
da24248d-571a-4e21-89bf-926e8b5d7263	86fa5fad-4673-48bf-94f7-d379e7f73676
da24248d-571a-4e21-89bf-926e8b5d7263	c3c172d0-f316-40db-a6d2-160a54352755
da24248d-571a-4e21-89bf-926e8b5d7263	a65ce936-2e63-47e1-aed9-d8159f2d386b
da24248d-571a-4e21-89bf-926e8b5d7263	32e48d76-6004-4a1c-bb22-27ee66e2d672
da24248d-571a-4e21-89bf-926e8b5d7263	e43a754b-648f-460b-88c6-0d3825b1fa76
8d9ea684-8b0b-4981-a957-e4f354edae56	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
5f060559-2753-47f6-ae5a-0ec0d6f680e1	5e445afa-73c0-4500-a0d8-697acd7bf58e
5f060559-2753-47f6-ae5a-0ec0d6f680e1	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
5f060559-2753-47f6-ae5a-0ec0d6f680e1	77f1a128-ea8f-466e-8c0d-998c2280c811
5f060559-2753-47f6-ae5a-0ec0d6f680e1	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
5f060559-2753-47f6-ae5a-0ec0d6f680e1	78301a7f-a1d9-494b-8c10-8b11baa5976e
5f060559-2753-47f6-ae5a-0ec0d6f680e1	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
5f060559-2753-47f6-ae5a-0ec0d6f680e1	97e67f12-4879-4dc6-8759-e205bba8b0f9
5f060559-2753-47f6-ae5a-0ec0d6f680e1	1337b436-7ff9-4b79-82a8-9981f2e66521
5f060559-2753-47f6-ae5a-0ec0d6f680e1	1b702945-e962-4778-b9c9-2fce7be4ab6c
5f060559-2753-47f6-ae5a-0ec0d6f680e1	f7fb4d07-cc8b-449a-a860-2100d2334658
5f060559-2753-47f6-ae5a-0ec0d6f680e1	32e48d76-6004-4a1c-bb22-27ee66e2d672
c1656243-932b-4d1d-b556-43115cecaf0f	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
c1656243-932b-4d1d-b556-43115cecaf0f	e738acf7-a61b-489a-a6f9-bda344038174
c1656243-932b-4d1d-b556-43115cecaf0f	68dd3eea-cea2-4e54-b2e3-2179938239ca
c1656243-932b-4d1d-b556-43115cecaf0f	fb02acd0-7388-4507-b25a-49573981c9a1
c1656243-932b-4d1d-b556-43115cecaf0f	bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23
c1656243-932b-4d1d-b556-43115cecaf0f	0b2159c6-a703-43bb-b6a1-5392153e01d7
c1656243-932b-4d1d-b556-43115cecaf0f	3cf8659e-c9c2-4c9f-91d0-897951be0372
c1656243-932b-4d1d-b556-43115cecaf0f	14fe8296-326b-4aed-ad72-315db46d9504
c1656243-932b-4d1d-b556-43115cecaf0f	b3bd0f0e-83df-4b31-8f33-815e6853a3a9
c1656243-932b-4d1d-b556-43115cecaf0f	5f84a25f-32b7-4b0f-bc74-9151f5e9e941
c1656243-932b-4d1d-b556-43115cecaf0f	32e48d76-6004-4a1c-bb22-27ee66e2d672
c1656243-932b-4d1d-b556-43115cecaf0f	a9ca51e9-864d-4d17-b647-ff17ea2c02e6
0b501e68-f122-4c68-8347-b1d3f4daddeb	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
0b501e68-f122-4c68-8347-b1d3f4daddeb	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
0b501e68-f122-4c68-8347-b1d3f4daddeb	8d5e74b8-7910-4ee3-b424-3697b919da65
0b501e68-f122-4c68-8347-b1d3f4daddeb	fb46ae33-946a-46d4-a4f2-29df8b040cff
0b501e68-f122-4c68-8347-b1d3f4daddeb	3d6d21de-bf1b-4fbb-aed0-ee57caa00988
0b501e68-f122-4c68-8347-b1d3f4daddeb	6b1271eb-54e6-49bd-ab27-c85605ec8576
0b501e68-f122-4c68-8347-b1d3f4daddeb	dba6afd0-4e82-476c-b1a4-06555587bffb
0b501e68-f122-4c68-8347-b1d3f4daddeb	b671851f-867f-4d2d-98fd-6f6c5c31c083
0b501e68-f122-4c68-8347-b1d3f4daddeb	4bbc1f38-3573-469b-a204-b84123b925a3
0b501e68-f122-4c68-8347-b1d3f4daddeb	32e48d76-6004-4a1c-bb22-27ee66e2d672
0b501e68-f122-4c68-8347-b1d3f4daddeb	e43a754b-648f-460b-88c6-0d3825b1fa76
8d9ea684-8b0b-4981-a957-e4f354edae56	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
8d9ea684-8b0b-4981-a957-e4f354edae56	32e48d76-6004-4a1c-bb22-27ee66e2d672
8d9ea684-8b0b-4981-a957-e4f354edae56	e43a754b-648f-460b-88c6-0d3825b1fa76
d773c5fd-71c1-44a6-8c8e-012901197735	32e48d76-6004-4a1c-bb22-27ee66e2d672
e59cf090-f2e2-4a36-8a13-a6896e2580c4	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
e59cf090-f2e2-4a36-8a13-a6896e2580c4	e211f36c-60a5-47c7-9971-f16df7bf07fc
e59cf090-f2e2-4a36-8a13-a6896e2580c4	86fa5fad-4673-48bf-94f7-d379e7f73676
e59cf090-f2e2-4a36-8a13-a6896e2580c4	c3c172d0-f316-40db-a6d2-160a54352755
e59cf090-f2e2-4a36-8a13-a6896e2580c4	32e48d76-6004-4a1c-bb22-27ee66e2d672
e59cf090-f2e2-4a36-8a13-a6896e2580c4	e43a754b-648f-460b-88c6-0d3825b1fa76
db97b66f-359a-485f-82cc-e0d20bc7593d	0784ce65-0cea-4000-858f-a5bc87d86e8f
db97b66f-359a-485f-82cc-e0d20bc7593d	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
db97b66f-359a-485f-82cc-e0d20bc7593d	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
db97b66f-359a-485f-82cc-e0d20bc7593d	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
db97b66f-359a-485f-82cc-e0d20bc7593d	9b90fa62-a329-4089-b71f-f9a70c27ac03
db97b66f-359a-485f-82cc-e0d20bc7593d	74a8a447-3f16-43a9-b29b-84d2e5d86a88
db97b66f-359a-485f-82cc-e0d20bc7593d	e9d67836-a72d-4802-a9f0-25a15e1042e5
db97b66f-359a-485f-82cc-e0d20bc7593d	32e48d76-6004-4a1c-bb22-27ee66e2d672
db97b66f-359a-485f-82cc-e0d20bc7593d	e43a754b-648f-460b-88c6-0d3825b1fa76
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	99e29aa2-9c26-4d09-9206-6173b7956d7d
9017763a-8ff3-4945-853d-271f8329d078	19c4c340-d56b-448a-a9d3-583daa46fefc
9017763a-8ff3-4945-853d-271f8329d078	97baa833-1f10-4349-85a3-c179959af8ce
9017763a-8ff3-4945-853d-271f8329d078	5885d8cd-49c6-46e6-8a89-36b55de54829
9017763a-8ff3-4945-853d-271f8329d078	d1e3dcb1-fa7c-4355-b42f-90c8378679cb
9017763a-8ff3-4945-853d-271f8329d078	24825786-1517-44ed-a97a-843f2ab2e5cf
9017763a-8ff3-4945-853d-271f8329d078	b65a8082-6f76-4f30-b9bc-142d01701d8b
9017763a-8ff3-4945-853d-271f8329d078	58494593-79cc-411e-ad7f-2b52a7fc6095
9017763a-8ff3-4945-853d-271f8329d078	c5273d0a-67cd-4449-aa07-8a9fbc6b3301
9017763a-8ff3-4945-853d-271f8329d078	f4e3291c-758b-47f4-a1d8-75680a84be0f
9017763a-8ff3-4945-853d-271f8329d078	32e48d76-6004-4a1c-bb22-27ee66e2d672
0c77b039-9808-4b26-867a-56ea95f2bd87	1e896415-6ebe-443f-b048-30725be095c3
0c77b039-9808-4b26-867a-56ea95f2bd87	05155d0d-89da-4c55-b92c-94a1387ec741
0c77b039-9808-4b26-867a-56ea95f2bd87	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
0c77b039-9808-4b26-867a-56ea95f2bd87	03019641-317d-4b74-adaa-1580ff4c99bc
0c77b039-9808-4b26-867a-56ea95f2bd87	af9e38a7-5fea-42ef-b6c1-cd56d0161988
0c77b039-9808-4b26-867a-56ea95f2bd87	3e4285d4-8e01-4483-9219-83079f9d3a77
0c77b039-9808-4b26-867a-56ea95f2bd87	1a931c05-4408-4eb6-afdc-905ebda9edc1
0c77b039-9808-4b26-867a-56ea95f2bd87	47d63157-d363-4e90-bf84-c94e1f61c0ee
0c77b039-9808-4b26-867a-56ea95f2bd87	50402a5d-cc3a-41af-a544-46dfb70ef381
0c77b039-9808-4b26-867a-56ea95f2bd87	32e48d76-6004-4a1c-bb22-27ee66e2d672
0c77b039-9808-4b26-867a-56ea95f2bd87	8638f31d-dd3a-41df-8571-bcb665c48554
557c1696-d117-46f7-a42b-0b94508e7591	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
557c1696-d117-46f7-a42b-0b94508e7591	e738acf7-a61b-489a-a6f9-bda344038174
557c1696-d117-46f7-a42b-0b94508e7591	68dd3eea-cea2-4e54-b2e3-2179938239ca
557c1696-d117-46f7-a42b-0b94508e7591	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
557c1696-d117-46f7-a42b-0b94508e7591	9ab1875d-9ba9-4f1f-8f12-7def153646d1
557c1696-d117-46f7-a42b-0b94508e7591	bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23
557c1696-d117-46f7-a42b-0b94508e7591	0b2159c6-a703-43bb-b6a1-5392153e01d7
557c1696-d117-46f7-a42b-0b94508e7591	3cf8659e-c9c2-4c9f-91d0-897951be0372
557c1696-d117-46f7-a42b-0b94508e7591	b3bd0f0e-83df-4b31-8f33-815e6853a3a9
557c1696-d117-46f7-a42b-0b94508e7591	32e48d76-6004-4a1c-bb22-27ee66e2d672
557c1696-d117-46f7-a42b-0b94508e7591	a9ca51e9-864d-4d17-b647-ff17ea2c02e6
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	6c6074b4-62fc-4312-91e6-2e8a9196aca7
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	b0956325-1cdb-425f-a9a6-afb752a5485d
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	4a858055-f275-460b-a349-222cc0ca28d7
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	22891480-943a-4cb1-a417-c760205d86bd
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	d44fa577-b407-4d90-972b-b3d39ab4b93d
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	2c1ea78c-8ca7-4583-aad4-a6724bc81df1
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	09be9784-f02e-4c2d-accb-fd49b529e15a
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	32e48d76-6004-4a1c-bb22-27ee66e2d672
07632d3e-b59e-4c10-afa7-ca3f5a6abdb7	e43a754b-648f-460b-88c6-0d3825b1fa76
c9605cdf-16af-4026-9239-43e3ed32fafc	32e48d76-6004-4a1c-bb22-27ee66e2d672
80790104-ae2d-4d08-bc97-2535e4592d37	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
80790104-ae2d-4d08-bc97-2535e4592d37	c0aa58be-47d7-4f72-b017-9125856035e2
80790104-ae2d-4d08-bc97-2535e4592d37	7d1eb5d5-dd84-4821-836b-ef6a694965f2
80790104-ae2d-4d08-bc97-2535e4592d37	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
80790104-ae2d-4d08-bc97-2535e4592d37	aea1a914-5bde-4a5c-92c1-27c9794cf738
80790104-ae2d-4d08-bc97-2535e4592d37	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
80790104-ae2d-4d08-bc97-2535e4592d37	77f1a128-ea8f-466e-8c0d-998c2280c811
80790104-ae2d-4d08-bc97-2535e4592d37	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
80790104-ae2d-4d08-bc97-2535e4592d37	86961441-94bd-4771-91e3-3580d18a9f40
80790104-ae2d-4d08-bc97-2535e4592d37	fb02acd0-7388-4507-b25a-49573981c9a1
80790104-ae2d-4d08-bc97-2535e4592d37	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
80790104-ae2d-4d08-bc97-2535e4592d37	14fe8296-326b-4aed-ad72-315db46d9504
80790104-ae2d-4d08-bc97-2535e4592d37	86fa5fad-4673-48bf-94f7-d379e7f73676
80790104-ae2d-4d08-bc97-2535e4592d37	733205c0-613d-49bb-aff8-50a53df081c6
80790104-ae2d-4d08-bc97-2535e4592d37	32e48d76-6004-4a1c-bb22-27ee66e2d672
ac6be9e3-44d8-4ba4-aa5c-d19f56c6bafa	cdcb904d-32c2-4f88-9af8-b7ca23b5bc47
ac6be9e3-44d8-4ba4-aa5c-d19f56c6bafa	32e48d76-6004-4a1c-bb22-27ee66e2d672
b3d26721-1fbf-487e-9203-31d381cdaf71	6b1271eb-54e6-49bd-ab27-c85605ec8576
b3d26721-1fbf-487e-9203-31d381cdaf71	7418423f-fa22-418e-86b9-7407485fe284
b3d26721-1fbf-487e-9203-31d381cdaf71	7c512d97-6114-4ef1-ba31-55abaf1f274e
b3d26721-1fbf-487e-9203-31d381cdaf71	f68c63a1-4807-47c5-a6d0-063630b700f6
b3d26721-1fbf-487e-9203-31d381cdaf71	f83ba309-b71e-455b-8354-5486ab7a76b7
b3d26721-1fbf-487e-9203-31d381cdaf71	32e48d76-6004-4a1c-bb22-27ee66e2d672
66b8c5ac-96cd-4808-8eee-241536ecee87	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
66b8c5ac-96cd-4808-8eee-241536ecee87	8d5e74b8-7910-4ee3-b424-3697b919da65
66b8c5ac-96cd-4808-8eee-241536ecee87	fb46ae33-946a-46d4-a4f2-29df8b040cff
66b8c5ac-96cd-4808-8eee-241536ecee87	bf8268d8-5e12-4137-9407-be7988f0eeba
66b8c5ac-96cd-4808-8eee-241536ecee87	10d0833d-8416-4d03-a831-5a757a9a835c
66b8c5ac-96cd-4808-8eee-241536ecee87	cea04306-6f20-431d-9904-c58271cf04f7
66b8c5ac-96cd-4808-8eee-241536ecee87	47f982fc-369e-473c-9ab0-8096bae012e0
66b8c5ac-96cd-4808-8eee-241536ecee87	20651a53-f4c0-415c-a66f-f4cd62a6a3b2
66b8c5ac-96cd-4808-8eee-241536ecee87	f7fb4d07-cc8b-449a-a860-2100d2334658
66b8c5ac-96cd-4808-8eee-241536ecee87	32e48d76-6004-4a1c-bb22-27ee66e2d672
66b8c5ac-96cd-4808-8eee-241536ecee87	e43a754b-648f-460b-88c6-0d3825b1fa76
e377ca5b-5f53-457e-9ac9-e4080ec13eaa	e211f36c-60a5-47c7-9971-f16df7bf07fc
e377ca5b-5f53-457e-9ac9-e4080ec13eaa	86fa5fad-4673-48bf-94f7-d379e7f73676
e377ca5b-5f53-457e-9ac9-e4080ec13eaa	c3c172d0-f316-40db-a6d2-160a54352755
e377ca5b-5f53-457e-9ac9-e4080ec13eaa	10d0833d-8416-4d03-a831-5a757a9a835c
e377ca5b-5f53-457e-9ac9-e4080ec13eaa	32e48d76-6004-4a1c-bb22-27ee66e2d672
e377ca5b-5f53-457e-9ac9-e4080ec13eaa	e43a754b-648f-460b-88c6-0d3825b1fa76
c2e52b2b-e537-4b3c-ad08-8200ca742443	0784ce65-0cea-4000-858f-a5bc87d86e8f
c2e52b2b-e537-4b3c-ad08-8200ca742443	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
c2e52b2b-e537-4b3c-ad08-8200ca742443	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
c2e52b2b-e537-4b3c-ad08-8200ca742443	9b90fa62-a329-4089-b71f-f9a70c27ac03
c2e52b2b-e537-4b3c-ad08-8200ca742443	74a8a447-3f16-43a9-b29b-84d2e5d86a88
c2e52b2b-e537-4b3c-ad08-8200ca742443	5fa57fdf-7ba0-4063-93cf-378d7b8267a9
c2e52b2b-e537-4b3c-ad08-8200ca742443	32e48d76-6004-4a1c-bb22-27ee66e2d672
c2e52b2b-e537-4b3c-ad08-8200ca742443	e43a754b-648f-460b-88c6-0d3825b1fa76
3e46093f-5800-4616-aef7-d7c9c8e09fa7	99e29aa2-9c26-4d09-9206-6173b7956d7d
3e46093f-5800-4616-aef7-d7c9c8e09fa7	6c6074b4-62fc-4312-91e6-2e8a9196aca7
3e46093f-5800-4616-aef7-d7c9c8e09fa7	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
3e46093f-5800-4616-aef7-d7c9c8e09fa7	b0956325-1cdb-425f-a9a6-afb752a5485d
3e46093f-5800-4616-aef7-d7c9c8e09fa7	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
3e46093f-5800-4616-aef7-d7c9c8e09fa7	4a858055-f275-460b-a349-222cc0ca28d7
3e46093f-5800-4616-aef7-d7c9c8e09fa7	22891480-943a-4cb1-a417-c760205d86bd
3e46093f-5800-4616-aef7-d7c9c8e09fa7	d44fa577-b407-4d90-972b-b3d39ab4b93d
3e46093f-5800-4616-aef7-d7c9c8e09fa7	899344cc-f75d-463b-b1a5-3ecbee85a79d
3e46093f-5800-4616-aef7-d7c9c8e09fa7	320d7753-9513-472c-ac95-cc9567a36cc0
3e46093f-5800-4616-aef7-d7c9c8e09fa7	32e48d76-6004-4a1c-bb22-27ee66e2d672
3e46093f-5800-4616-aef7-d7c9c8e09fa7	e43a754b-648f-460b-88c6-0d3825b1fa76
874967c2-4fb0-4d6e-923b-bccc347bb267	f8a8bb67-a047-439e-9956-ea4822e56e98
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	1e896415-6ebe-443f-b048-30725be095c3
874967c2-4fb0-4d6e-923b-bccc347bb267	a95dcb15-398c-449b-8ef6-4bc0a9c4891a
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	05155d0d-89da-4c55-b92c-94a1387ec741
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
874967c2-4fb0-4d6e-923b-bccc347bb267	68be0a27-ed84-4ac1-a8f2-5b22ef9cc442
874967c2-4fb0-4d6e-923b-bccc347bb267	c8520c2f-475e-4f88-a3bb-284e7cc58562
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	03019641-317d-4b74-adaa-1580ff4c99bc
874967c2-4fb0-4d6e-923b-bccc347bb267	32e48d76-6004-4a1c-bb22-27ee66e2d672
874967c2-4fb0-4d6e-923b-bccc347bb267	e43a754b-648f-460b-88c6-0d3825b1fa76
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	af9e38a7-5fea-42ef-b6c1-cd56d0161988
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	3e4285d4-8e01-4483-9219-83079f9d3a77
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	1a931c05-4408-4eb6-afdc-905ebda9edc1
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	0b2159c6-a703-43bb-b6a1-5392153e01d7
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	3cf8659e-c9c2-4c9f-91d0-897951be0372
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	b3bd0f0e-83df-4b31-8f33-815e6853a3a9
daee5fe2-8afe-4389-83c0-a2e9e4f50b0d	32e48d76-6004-4a1c-bb22-27ee66e2d672
1199766d-1d61-43ed-945e-17aeb5c59523	5e445afa-73c0-4500-a0d8-697acd7bf58e
1199766d-1d61-43ed-945e-17aeb5c59523	e738acf7-a61b-489a-a6f9-bda344038174
1199766d-1d61-43ed-945e-17aeb5c59523	a9ca51e9-864d-4d17-b647-ff17ea2c02e6
1199766d-1d61-43ed-945e-17aeb5c59523	7d48e39c-8f0c-4db0-bd68-3c4b83e2b8d1
1199766d-1d61-43ed-945e-17aeb5c59523	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
1199766d-1d61-43ed-945e-17aeb5c59523	86961441-94bd-4771-91e3-3580d18a9f40
1199766d-1d61-43ed-945e-17aeb5c59523	fb02acd0-7388-4507-b25a-49573981c9a1
1199766d-1d61-43ed-945e-17aeb5c59523	bf8268d8-5e12-4137-9407-be7988f0eeba
7ad22e0c-a328-40b4-aaef-f93303cf73eb	1e896415-6ebe-443f-b048-30725be095c3
1199766d-1d61-43ed-945e-17aeb5c59523	cea04306-6f20-431d-9904-c58271cf04f7
1199766d-1d61-43ed-945e-17aeb5c59523	32e48d76-6004-4a1c-bb22-27ee66e2d672
7ad22e0c-a328-40b4-aaef-f93303cf73eb	05155d0d-89da-4c55-b92c-94a1387ec741
7ad22e0c-a328-40b4-aaef-f93303cf73eb	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
7ad22e0c-a328-40b4-aaef-f93303cf73eb	03019641-317d-4b74-adaa-1580ff4c99bc
7ad22e0c-a328-40b4-aaef-f93303cf73eb	af9e38a7-5fea-42ef-b6c1-cd56d0161988
7ad22e0c-a328-40b4-aaef-f93303cf73eb	3e4285d4-8e01-4483-9219-83079f9d3a77
7ad22e0c-a328-40b4-aaef-f93303cf73eb	1a931c05-4408-4eb6-afdc-905ebda9edc1
7ad22e0c-a328-40b4-aaef-f93303cf73eb	5e445afa-73c0-4500-a0d8-697acd7bf58e
7ad22e0c-a328-40b4-aaef-f93303cf73eb	86961441-94bd-4771-91e3-3580d18a9f40
7ad22e0c-a328-40b4-aaef-f93303cf73eb	bf8268d8-5e12-4137-9407-be7988f0eeba
7ad22e0c-a328-40b4-aaef-f93303cf73eb	cea04306-6f20-431d-9904-c58271cf04f7
7ad22e0c-a328-40b4-aaef-f93303cf73eb	32e48d76-6004-4a1c-bb22-27ee66e2d672
e90073da-2a75-4984-ae01-36af35f3c23c	71e325fb-01c5-44fd-a0de-898927b39357
e90073da-2a75-4984-ae01-36af35f3c23c	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
e90073da-2a75-4984-ae01-36af35f3c23c	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
e90073da-2a75-4984-ae01-36af35f3c23c	7d1eb5d5-dd84-4821-836b-ef6a694965f2
e90073da-2a75-4984-ae01-36af35f3c23c	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
e90073da-2a75-4984-ae01-36af35f3c23c	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
e90073da-2a75-4984-ae01-36af35f3c23c	77f1a128-ea8f-466e-8c0d-998c2280c811
e90073da-2a75-4984-ae01-36af35f3c23c	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
e90073da-2a75-4984-ae01-36af35f3c23c	fb02acd0-7388-4507-b25a-49573981c9a1
e90073da-2a75-4984-ae01-36af35f3c23c	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
e90073da-2a75-4984-ae01-36af35f3c23c	94ad2b6d-975f-4746-9a3b-33c32b34a13f
e90073da-2a75-4984-ae01-36af35f3c23c	50402a5d-cc3a-41af-a544-46dfb70ef381
e90073da-2a75-4984-ae01-36af35f3c23c	32e48d76-6004-4a1c-bb22-27ee66e2d672
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	71e325fb-01c5-44fd-a0de-898927b39357
acb96ba1-7b66-41b3-8887-1ef8499ac317	71e325fb-01c5-44fd-a0de-898927b39357
07493acf-da6b-4999-a618-73248d0facff	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
07493acf-da6b-4999-a618-73248d0facff	71e325fb-01c5-44fd-a0de-898927b39357
acb96ba1-7b66-41b3-8887-1ef8499ac317	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
07493acf-da6b-4999-a618-73248d0facff	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
acb96ba1-7b66-41b3-8887-1ef8499ac317	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
3d856048-3f95-42e1-b82b-be98dd48a9c5	71e325fb-01c5-44fd-a0de-898927b39357
07493acf-da6b-4999-a618-73248d0facff	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
acb96ba1-7b66-41b3-8887-1ef8499ac317	7d1eb5d5-dd84-4821-836b-ef6a694965f2
3d856048-3f95-42e1-b82b-be98dd48a9c5	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
07493acf-da6b-4999-a618-73248d0facff	7d1eb5d5-dd84-4821-836b-ef6a694965f2
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
acb96ba1-7b66-41b3-8887-1ef8499ac317	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
3d856048-3f95-42e1-b82b-be98dd48a9c5	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
07493acf-da6b-4999-a618-73248d0facff	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
acb96ba1-7b66-41b3-8887-1ef8499ac317	77f1a128-ea8f-466e-8c0d-998c2280c811
3d856048-3f95-42e1-b82b-be98dd48a9c5	7d1eb5d5-dd84-4821-836b-ef6a694965f2
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	7d1eb5d5-dd84-4821-836b-ef6a694965f2
07493acf-da6b-4999-a618-73248d0facff	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
acb96ba1-7b66-41b3-8887-1ef8499ac317	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
3d856048-3f95-42e1-b82b-be98dd48a9c5	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
07493acf-da6b-4999-a618-73248d0facff	77f1a128-ea8f-466e-8c0d-998c2280c811
acb96ba1-7b66-41b3-8887-1ef8499ac317	e211f36c-60a5-47c7-9971-f16df7bf07fc
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
3d856048-3f95-42e1-b82b-be98dd48a9c5	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
07493acf-da6b-4999-a618-73248d0facff	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
acb96ba1-7b66-41b3-8887-1ef8499ac317	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
3d856048-3f95-42e1-b82b-be98dd48a9c5	77f1a128-ea8f-466e-8c0d-998c2280c811
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
07493acf-da6b-4999-a618-73248d0facff	fb02acd0-7388-4507-b25a-49573981c9a1
acb96ba1-7b66-41b3-8887-1ef8499ac317	94ad2b6d-975f-4746-9a3b-33c32b34a13f
3d856048-3f95-42e1-b82b-be98dd48a9c5	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
07493acf-da6b-4999-a618-73248d0facff	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
3d856048-3f95-42e1-b82b-be98dd48a9c5	78301a7f-a1d9-494b-8c10-8b11baa5976e
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	77f1a128-ea8f-466e-8c0d-998c2280c811
3d856048-3f95-42e1-b82b-be98dd48a9c5	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	fb02acd0-7388-4507-b25a-49573981c9a1
07493acf-da6b-4999-a618-73248d0facff	94ad2b6d-975f-4746-9a3b-33c32b34a13f
3d856048-3f95-42e1-b82b-be98dd48a9c5	fb02acd0-7388-4507-b25a-49573981c9a1
acb96ba1-7b66-41b3-8887-1ef8499ac317	86fa5fad-4673-48bf-94f7-d379e7f73676
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
acb96ba1-7b66-41b3-8887-1ef8499ac317	c3c172d0-f316-40db-a6d2-160a54352755
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	94ad2b6d-975f-4746-9a3b-33c32b34a13f
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	df7e7468-87c6-4728-b792-e4058c15908d
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	c5611c9c-3d1a-4fae-9999-35acab706abc
07493acf-da6b-4999-a618-73248d0facff	32e48d76-6004-4a1c-bb22-27ee66e2d672
acb96ba1-7b66-41b3-8887-1ef8499ac317	50402a5d-cc3a-41af-a544-46dfb70ef381
acb96ba1-7b66-41b3-8887-1ef8499ac317	32e48d76-6004-4a1c-bb22-27ee66e2d672
3d856048-3f95-42e1-b82b-be98dd48a9c5	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
22f8ede4-b5b6-4cca-9571-6b44fd6f40d8	32e48d76-6004-4a1c-bb22-27ee66e2d672
3d856048-3f95-42e1-b82b-be98dd48a9c5	94ad2b6d-975f-4746-9a3b-33c32b34a13f
3d856048-3f95-42e1-b82b-be98dd48a9c5	32e48d76-6004-4a1c-bb22-27ee66e2d672
75c4da4c-9feb-48fb-883e-3272c5215012	71e325fb-01c5-44fd-a0de-898927b39357
75c4da4c-9feb-48fb-883e-3272c5215012	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
75c4da4c-9feb-48fb-883e-3272c5215012	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
75c4da4c-9feb-48fb-883e-3272c5215012	7d1eb5d5-dd84-4821-836b-ef6a694965f2
75c4da4c-9feb-48fb-883e-3272c5215012	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
75c4da4c-9feb-48fb-883e-3272c5215012	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
75c4da4c-9feb-48fb-883e-3272c5215012	77f1a128-ea8f-466e-8c0d-998c2280c811
75c4da4c-9feb-48fb-883e-3272c5215012	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
75c4da4c-9feb-48fb-883e-3272c5215012	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
75c4da4c-9feb-48fb-883e-3272c5215012	fb02acd0-7388-4507-b25a-49573981c9a1
75c4da4c-9feb-48fb-883e-3272c5215012	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
75c4da4c-9feb-48fb-883e-3272c5215012	94ad2b6d-975f-4746-9a3b-33c32b34a13f
75c4da4c-9feb-48fb-883e-3272c5215012	5d617722-8aeb-4e89-b859-01d3ec382190
75c4da4c-9feb-48fb-883e-3272c5215012	32e48d76-6004-4a1c-bb22-27ee66e2d672
d2d87f80-ebee-4f62-94f0-ca513d80305b	71e325fb-01c5-44fd-a0de-898927b39357
d2d87f80-ebee-4f62-94f0-ca513d80305b	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
d2d87f80-ebee-4f62-94f0-ca513d80305b	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
d2d87f80-ebee-4f62-94f0-ca513d80305b	7d1eb5d5-dd84-4821-836b-ef6a694965f2
d2d87f80-ebee-4f62-94f0-ca513d80305b	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
d2d87f80-ebee-4f62-94f0-ca513d80305b	77f1a128-ea8f-466e-8c0d-998c2280c811
d2d87f80-ebee-4f62-94f0-ca513d80305b	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
d2d87f80-ebee-4f62-94f0-ca513d80305b	e211f36c-60a5-47c7-9971-f16df7bf07fc
d2d87f80-ebee-4f62-94f0-ca513d80305b	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
d2d87f80-ebee-4f62-94f0-ca513d80305b	94ad2b6d-975f-4746-9a3b-33c32b34a13f
d2d87f80-ebee-4f62-94f0-ca513d80305b	86fa5fad-4673-48bf-94f7-d379e7f73676
d2d87f80-ebee-4f62-94f0-ca513d80305b	c3c172d0-f316-40db-a6d2-160a54352755
d2d87f80-ebee-4f62-94f0-ca513d80305b	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
d2d87f80-ebee-4f62-94f0-ca513d80305b	df7e7468-87c6-4728-b792-e4058c15908d
d2d87f80-ebee-4f62-94f0-ca513d80305b	c5611c9c-3d1a-4fae-9999-35acab706abc
d2d87f80-ebee-4f62-94f0-ca513d80305b	32e48d76-6004-4a1c-bb22-27ee66e2d672
8301d955-cc62-430a-a03c-44a628a077d5	1e896415-6ebe-443f-b048-30725be095c3
8301d955-cc62-430a-a03c-44a628a077d5	05155d0d-89da-4c55-b92c-94a1387ec741
8301d955-cc62-430a-a03c-44a628a077d5	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
8301d955-cc62-430a-a03c-44a628a077d5	03019641-317d-4b74-adaa-1580ff4c99bc
8301d955-cc62-430a-a03c-44a628a077d5	47d63157-d363-4e90-bf84-c94e1f61c0ee
8301d955-cc62-430a-a03c-44a628a077d5	50402a5d-cc3a-41af-a544-46dfb70ef381
8301d955-cc62-430a-a03c-44a628a077d5	ee549e95-aa66-4707-b957-26b96b35d068
8301d955-cc62-430a-a03c-44a628a077d5	04a38028-135f-4e88-9cfc-12066c8f1df3
8301d955-cc62-430a-a03c-44a628a077d5	0750aa84-1b51-45b8-aaad-056cbf1b303b
8301d955-cc62-430a-a03c-44a628a077d5	32e48d76-6004-4a1c-bb22-27ee66e2d672
8301d955-cc62-430a-a03c-44a628a077d5	8638f31d-dd3a-41df-8571-bcb665c48554
0e1c0a31-68d3-4205-954d-314ad8e74f5e	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
0e1c0a31-68d3-4205-954d-314ad8e74f5e	e211f36c-60a5-47c7-9971-f16df7bf07fc
0e1c0a31-68d3-4205-954d-314ad8e74f5e	4aeb5881-7e48-470e-b134-12236311f9b8
0e1c0a31-68d3-4205-954d-314ad8e74f5e	6505a4c0-7b39-4751-9e60-be4db3f451f9
0e1c0a31-68d3-4205-954d-314ad8e74f5e	32e48d76-6004-4a1c-bb22-27ee66e2d672
0e1c0a31-68d3-4205-954d-314ad8e74f5e	e43a754b-648f-460b-88c6-0d3825b1fa76
731134b9-6130-41d9-96a5-2732ca5818e7	99e29aa2-9c26-4d09-9206-6173b7956d7d
731134b9-6130-41d9-96a5-2732ca5818e7	6c6074b4-62fc-4312-91e6-2e8a9196aca7
731134b9-6130-41d9-96a5-2732ca5818e7	0784ce65-0cea-4000-858f-a5bc87d86e8f
731134b9-6130-41d9-96a5-2732ca5818e7	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
731134b9-6130-41d9-96a5-2732ca5818e7	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
731134b9-6130-41d9-96a5-2732ca5818e7	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
731134b9-6130-41d9-96a5-2732ca5818e7	9b90fa62-a329-4089-b71f-f9a70c27ac03
731134b9-6130-41d9-96a5-2732ca5818e7	74a8a447-3f16-43a9-b29b-84d2e5d86a88
731134b9-6130-41d9-96a5-2732ca5818e7	32e48d76-6004-4a1c-bb22-27ee66e2d672
731134b9-6130-41d9-96a5-2732ca5818e7	e43a754b-648f-460b-88c6-0d3825b1fa76
da371113-c70c-472d-a07d-329072c2c8b6	0784ce65-0cea-4000-858f-a5bc87d86e8f
da371113-c70c-472d-a07d-329072c2c8b6	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
da371113-c70c-472d-a07d-329072c2c8b6	9b90fa62-a329-4089-b71f-f9a70c27ac03
da371113-c70c-472d-a07d-329072c2c8b6	74a8a447-3f16-43a9-b29b-84d2e5d86a88
da371113-c70c-472d-a07d-329072c2c8b6	e1482fe4-3872-4f46-a51b-9a648b9a5072
da371113-c70c-472d-a07d-329072c2c8b6	5fdcd179-a83a-4080-a33c-905e3db55dd1
da371113-c70c-472d-a07d-329072c2c8b6	5fa57fdf-7ba0-4063-93cf-378d7b8267a9
da371113-c70c-472d-a07d-329072c2c8b6	32e48d76-6004-4a1c-bb22-27ee66e2d672
da371113-c70c-472d-a07d-329072c2c8b6	e43a754b-648f-460b-88c6-0d3825b1fa76
b5537bcd-dcc6-4540-84ad-ba7255e3a839	99e29aa2-9c26-4d09-9206-6173b7956d7d
b5537bcd-dcc6-4540-84ad-ba7255e3a839	6c6074b4-62fc-4312-91e6-2e8a9196aca7
b5537bcd-dcc6-4540-84ad-ba7255e3a839	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
b5537bcd-dcc6-4540-84ad-ba7255e3a839	b0956325-1cdb-425f-a9a6-afb752a5485d
b5537bcd-dcc6-4540-84ad-ba7255e3a839	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
b5537bcd-dcc6-4540-84ad-ba7255e3a839	4a858055-f275-460b-a349-222cc0ca28d7
b5537bcd-dcc6-4540-84ad-ba7255e3a839	22891480-943a-4cb1-a417-c760205d86bd
b5537bcd-dcc6-4540-84ad-ba7255e3a839	d44fa577-b407-4d90-972b-b3d39ab4b93d
b5537bcd-dcc6-4540-84ad-ba7255e3a839	899344cc-f75d-463b-b1a5-3ecbee85a79d
b5537bcd-dcc6-4540-84ad-ba7255e3a839	320d7753-9513-472c-ac95-cc9567a36cc0
b5537bcd-dcc6-4540-84ad-ba7255e3a839	32e48d76-6004-4a1c-bb22-27ee66e2d672
b5537bcd-dcc6-4540-84ad-ba7255e3a839	e43a754b-648f-460b-88c6-0d3825b1fa76
7191c7a3-df82-40d6-8e17-76aeb799c14a	71e325fb-01c5-44fd-a0de-898927b39357
7191c7a3-df82-40d6-8e17-76aeb799c14a	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
7191c7a3-df82-40d6-8e17-76aeb799c14a	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
7191c7a3-df82-40d6-8e17-76aeb799c14a	7d1eb5d5-dd84-4821-836b-ef6a694965f2
7191c7a3-df82-40d6-8e17-76aeb799c14a	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
7191c7a3-df82-40d6-8e17-76aeb799c14a	77f1a128-ea8f-466e-8c0d-998c2280c811
7191c7a3-df82-40d6-8e17-76aeb799c14a	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
7191c7a3-df82-40d6-8e17-76aeb799c14a	78301a7f-a1d9-494b-8c10-8b11baa5976e
7191c7a3-df82-40d6-8e17-76aeb799c14a	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
7191c7a3-df82-40d6-8e17-76aeb799c14a	e211f36c-60a5-47c7-9971-f16df7bf07fc
7191c7a3-df82-40d6-8e17-76aeb799c14a	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
7191c7a3-df82-40d6-8e17-76aeb799c14a	86fa5fad-4673-48bf-94f7-d379e7f73676
7191c7a3-df82-40d6-8e17-76aeb799c14a	c3c172d0-f316-40db-a6d2-160a54352755
7191c7a3-df82-40d6-8e17-76aeb799c14a	32e48d76-6004-4a1c-bb22-27ee66e2d672
9dc5024d-59ae-48e6-8226-338b61231c67	5e445afa-73c0-4500-a0d8-697acd7bf58e
9dc5024d-59ae-48e6-8226-338b61231c67	71e325fb-01c5-44fd-a0de-898927b39357
9dc5024d-59ae-48e6-8226-338b61231c67	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
9dc5024d-59ae-48e6-8226-338b61231c67	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
9dc5024d-59ae-48e6-8226-338b61231c67	aea1a914-5bde-4a5c-92c1-27c9794cf738
9dc5024d-59ae-48e6-8226-338b61231c67	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
9dc5024d-59ae-48e6-8226-338b61231c67	77f1a128-ea8f-466e-8c0d-998c2280c811
9dc5024d-59ae-48e6-8226-338b61231c67	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
9dc5024d-59ae-48e6-8226-338b61231c67	97e67f12-4879-4dc6-8759-e205bba8b0f9
9dc5024d-59ae-48e6-8226-338b61231c67	86fa5fad-4673-48bf-94f7-d379e7f73676
9dc5024d-59ae-48e6-8226-338b61231c67	c3c172d0-f316-40db-a6d2-160a54352755
9dc5024d-59ae-48e6-8226-338b61231c67	1337b436-7ff9-4b79-82a8-9981f2e66521
9dc5024d-59ae-48e6-8226-338b61231c67	1b702945-e962-4778-b9c9-2fce7be4ab6c
9dc5024d-59ae-48e6-8226-338b61231c67	32e48d76-6004-4a1c-bb22-27ee66e2d672
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	03019641-317d-4b74-adaa-1580ff4c99bc
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	af9e38a7-5fea-42ef-b6c1-cd56d0161988
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	3e4285d4-8e01-4483-9219-83079f9d3a77
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	1a931c05-4408-4eb6-afdc-905ebda9edc1
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	c5273d0a-67cd-4449-aa07-8a9fbc6b3301
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	df7e7468-87c6-4728-b792-e4058c15908d
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	c5611c9c-3d1a-4fae-9999-35acab706abc
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	a466ef46-1050-4081-8191-f15c08a9f6db
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	32e48d76-6004-4a1c-bb22-27ee66e2d672
2419faba-f5a1-4df7-ac26-bcde1b0c8b00	69a94b81-7ed0-428b-a3c9-679a34e47e0a
73498071-277d-4fc0-a46d-56a121c9d65c	78301a7f-a1d9-494b-8c10-8b11baa5976e
73498071-277d-4fc0-a46d-56a121c9d65c	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
73498071-277d-4fc0-a46d-56a121c9d65c	803f1d5e-f530-4983-b815-68238a066cbf
73498071-277d-4fc0-a46d-56a121c9d65c	e211f36c-60a5-47c7-9971-f16df7bf07fc
73498071-277d-4fc0-a46d-56a121c9d65c	a80a1879-1353-406e-8d2b-74da7a59c64a
73498071-277d-4fc0-a46d-56a121c9d65c	839631b1-18ca-4d4a-8105-6ec7c0cf8ffd
73498071-277d-4fc0-a46d-56a121c9d65c	f8a8bb67-a047-439e-9956-ea4822e56e98
73498071-277d-4fc0-a46d-56a121c9d65c	14fe8296-326b-4aed-ad72-315db46d9504
73498071-277d-4fc0-a46d-56a121c9d65c	4aeb5881-7e48-470e-b134-12236311f9b8
73498071-277d-4fc0-a46d-56a121c9d65c	1337b436-7ff9-4b79-82a8-9981f2e66521
73498071-277d-4fc0-a46d-56a121c9d65c	1b702945-e962-4778-b9c9-2fce7be4ab6c
73498071-277d-4fc0-a46d-56a121c9d65c	14db164d-723c-418f-a4bd-9a6631817122
73498071-277d-4fc0-a46d-56a121c9d65c	32e48d76-6004-4a1c-bb22-27ee66e2d672
73498071-277d-4fc0-a46d-56a121c9d65c	8638f31d-dd3a-41df-8571-bcb665c48554
73498071-277d-4fc0-a46d-56a121c9d65c	e43a754b-648f-460b-88c6-0d3825b1fa76
f6953bbc-6636-4202-a2cb-480b48881ec4	99e29aa2-9c26-4d09-9206-6173b7956d7d
f6953bbc-6636-4202-a2cb-480b48881ec4	6c6074b4-62fc-4312-91e6-2e8a9196aca7
f6953bbc-6636-4202-a2cb-480b48881ec4	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
f6953bbc-6636-4202-a2cb-480b48881ec4	b0956325-1cdb-425f-a9a6-afb752a5485d
f6953bbc-6636-4202-a2cb-480b48881ec4	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
f6953bbc-6636-4202-a2cb-480b48881ec4	d9ee24ed-b9e2-41d8-b733-bc8f48b7ae0a
f6953bbc-6636-4202-a2cb-480b48881ec4	4a858055-f275-460b-a349-222cc0ca28d7
f6953bbc-6636-4202-a2cb-480b48881ec4	22891480-943a-4cb1-a417-c760205d86bd
f6953bbc-6636-4202-a2cb-480b48881ec4	d44fa577-b407-4d90-972b-b3d39ab4b93d
f6953bbc-6636-4202-a2cb-480b48881ec4	32e48d76-6004-4a1c-bb22-27ee66e2d672
f6953bbc-6636-4202-a2cb-480b48881ec4	e43a754b-648f-460b-88c6-0d3825b1fa76
ca15bd60-f63b-4028-bbc4-038870148105	71e325fb-01c5-44fd-a0de-898927b39357
ca15bd60-f63b-4028-bbc4-038870148105	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
ca15bd60-f63b-4028-bbc4-038870148105	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
ca15bd60-f63b-4028-bbc4-038870148105	7d1eb5d5-dd84-4821-836b-ef6a694965f2
ca15bd60-f63b-4028-bbc4-038870148105	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
ca15bd60-f63b-4028-bbc4-038870148105	77f1a128-ea8f-466e-8c0d-998c2280c811
ca15bd60-f63b-4028-bbc4-038870148105	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
ca15bd60-f63b-4028-bbc4-038870148105	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
ca15bd60-f63b-4028-bbc4-038870148105	e211f36c-60a5-47c7-9971-f16df7bf07fc
ca15bd60-f63b-4028-bbc4-038870148105	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
ca15bd60-f63b-4028-bbc4-038870148105	94ad2b6d-975f-4746-9a3b-33c32b34a13f
ca15bd60-f63b-4028-bbc4-038870148105	86fa5fad-4673-48bf-94f7-d379e7f73676
ca15bd60-f63b-4028-bbc4-038870148105	c3c172d0-f316-40db-a6d2-160a54352755
ca15bd60-f63b-4028-bbc4-038870148105	5d617722-8aeb-4e89-b859-01d3ec382190
ca15bd60-f63b-4028-bbc4-038870148105	32e48d76-6004-4a1c-bb22-27ee66e2d672
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	1e896415-6ebe-443f-b048-30725be095c3
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	05155d0d-89da-4c55-b92c-94a1387ec741
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	e738acf7-a61b-489a-a6f9-bda344038174
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	6bbb9b79-33ba-422f-9964-a8435805fcbd
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	77f1a128-ea8f-466e-8c0d-998c2280c811
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	df7e7468-87c6-4728-b792-e4058c15908d
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	c5611c9c-3d1a-4fae-9999-35acab706abc
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	64e7e3c8-c369-4913-be67-a64c7ea29d11
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	a466ef46-1050-4081-8191-f15c08a9f6db
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	32e48d76-6004-4a1c-bb22-27ee66e2d672
4f37e82f-cdf1-4115-bb00-31f1e2f1aca0	a9ca51e9-864d-4d17-b647-ff17ea2c02e6
b2b774d3-eacc-4f63-a541-c653eccb9f55	97e67f12-4879-4dc6-8759-e205bba8b0f9
b2b774d3-eacc-4f63-a541-c653eccb9f55	e211f36c-60a5-47c7-9971-f16df7bf07fc
b2b774d3-eacc-4f63-a541-c653eccb9f55	3d6d21de-bf1b-4fbb-aed0-ee57caa00988
b2b774d3-eacc-4f63-a541-c653eccb9f55	6b1271eb-54e6-49bd-ab27-c85605ec8576
b2b774d3-eacc-4f63-a541-c653eccb9f55	a80a1879-1353-406e-8d2b-74da7a59c64a
b2b774d3-eacc-4f63-a541-c653eccb9f55	14db164d-723c-418f-a4bd-9a6631817122
b2b774d3-eacc-4f63-a541-c653eccb9f55	32e48d76-6004-4a1c-bb22-27ee66e2d672
b2b774d3-eacc-4f63-a541-c653eccb9f55	e43a754b-648f-460b-88c6-0d3825b1fa76
7465210d-8463-403d-8a80-ccd3297bbb8b	99e29aa2-9c26-4d09-9206-6173b7956d7d
7465210d-8463-403d-8a80-ccd3297bbb8b	6c6074b4-62fc-4312-91e6-2e8a9196aca7
7465210d-8463-403d-8a80-ccd3297bbb8b	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
7465210d-8463-403d-8a80-ccd3297bbb8b	b0956325-1cdb-425f-a9a6-afb752a5485d
7465210d-8463-403d-8a80-ccd3297bbb8b	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
7465210d-8463-403d-8a80-ccd3297bbb8b	4a858055-f275-460b-a349-222cc0ca28d7
7465210d-8463-403d-8a80-ccd3297bbb8b	22891480-943a-4cb1-a417-c760205d86bd
7465210d-8463-403d-8a80-ccd3297bbb8b	d44fa577-b407-4d90-972b-b3d39ab4b93d
7465210d-8463-403d-8a80-ccd3297bbb8b	2c1ea78c-8ca7-4583-aad4-a6724bc81df1
7465210d-8463-403d-8a80-ccd3297bbb8b	09be9784-f02e-4c2d-accb-fd49b529e15a
7465210d-8463-403d-8a80-ccd3297bbb8b	32e48d76-6004-4a1c-bb22-27ee66e2d672
7465210d-8463-403d-8a80-ccd3297bbb8b	e43a754b-648f-460b-88c6-0d3825b1fa76
8b77e75d-c141-4c50-8793-f0d257e3910d	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
8b77e75d-c141-4c50-8793-f0d257e3910d	71e325fb-01c5-44fd-a0de-898927b39357
8b77e75d-c141-4c50-8793-f0d257e3910d	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
8b77e75d-c141-4c50-8793-f0d257e3910d	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
8b77e75d-c141-4c50-8793-f0d257e3910d	7d1eb5d5-dd84-4821-836b-ef6a694965f2
8b77e75d-c141-4c50-8793-f0d257e3910d	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
8b77e75d-c141-4c50-8793-f0d257e3910d	77f1a128-ea8f-466e-8c0d-998c2280c811
8b77e75d-c141-4c50-8793-f0d257e3910d	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
8b77e75d-c141-4c50-8793-f0d257e3910d	e211f36c-60a5-47c7-9971-f16df7bf07fc
8b77e75d-c141-4c50-8793-f0d257e3910d	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
8b77e75d-c141-4c50-8793-f0d257e3910d	94ad2b6d-975f-4746-9a3b-33c32b34a13f
8b77e75d-c141-4c50-8793-f0d257e3910d	86fa5fad-4673-48bf-94f7-d379e7f73676
8b77e75d-c141-4c50-8793-f0d257e3910d	c3c172d0-f316-40db-a6d2-160a54352755
8b77e75d-c141-4c50-8793-f0d257e3910d	32e48d76-6004-4a1c-bb22-27ee66e2d672
aaa83ec9-ca0c-4324-8e97-faa7b7267383	5e445afa-73c0-4500-a0d8-697acd7bf58e
aaa83ec9-ca0c-4324-8e97-faa7b7267383	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
aaa83ec9-ca0c-4324-8e97-faa7b7267383	77f1a128-ea8f-466e-8c0d-998c2280c811
aaa83ec9-ca0c-4324-8e97-faa7b7267383	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
aaa83ec9-ca0c-4324-8e97-faa7b7267383	78301a7f-a1d9-494b-8c10-8b11baa5976e
aaa83ec9-ca0c-4324-8e97-faa7b7267383	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
aaa83ec9-ca0c-4324-8e97-faa7b7267383	97e67f12-4879-4dc6-8759-e205bba8b0f9
aaa83ec9-ca0c-4324-8e97-faa7b7267383	86fa5fad-4673-48bf-94f7-d379e7f73676
aaa83ec9-ca0c-4324-8e97-faa7b7267383	c3c172d0-f316-40db-a6d2-160a54352755
aaa83ec9-ca0c-4324-8e97-faa7b7267383	1337b436-7ff9-4b79-82a8-9981f2e66521
aaa83ec9-ca0c-4324-8e97-faa7b7267383	1b702945-e962-4778-b9c9-2fce7be4ab6c
aaa83ec9-ca0c-4324-8e97-faa7b7267383	32e48d76-6004-4a1c-bb22-27ee66e2d672
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	99e29aa2-9c26-4d09-9206-6173b7956d7d
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	6c6074b4-62fc-4312-91e6-2e8a9196aca7
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	b0956325-1cdb-425f-a9a6-afb752a5485d
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	d9ee24ed-b9e2-41d8-b733-bc8f48b7ae0a
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	4a858055-f275-460b-a349-222cc0ca28d7
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	22891480-943a-4cb1-a417-c760205d86bd
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	d44fa577-b407-4d90-972b-b3d39ab4b93d
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	32e48d76-6004-4a1c-bb22-27ee66e2d672
0defe0b9-8088-4c0d-9e5d-0f1062e11c6a	e43a754b-648f-460b-88c6-0d3825b1fa76
c7355cdc-4d80-465c-b3f7-95dab9f1ccc7	b0956325-1cdb-425f-a9a6-afb752a5485d
c7355cdc-4d80-465c-b3f7-95dab9f1ccc7	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
c7355cdc-4d80-465c-b3f7-95dab9f1ccc7	8c6942ac-e020-4a00-acc4-8c41409854ab
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	5e445afa-73c0-4500-a0d8-697acd7bf58e
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	8d5e74b8-7910-4ee3-b424-3697b919da65
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	fb46ae33-946a-46d4-a4f2-29df8b040cff
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	9ab1875d-9ba9-4f1f-8f12-7def153646d1
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	d249752a-4f26-45d6-b9a0-4288c97d2ee1
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	4fb9a210-dd6d-43a4-9c64-bf56bd677a51
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	47f982fc-369e-473c-9ab0-8096bae012e0
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	32e48d76-6004-4a1c-bb22-27ee66e2d672
2a6e6997-9eaa-4084-8c90-c75f7f4a37bd	e43a754b-648f-460b-88c6-0d3825b1fa76
c7355cdc-4d80-465c-b3f7-95dab9f1ccc7	62d4892c-a2ea-456d-90c8-0eef9df99961
c7355cdc-4d80-465c-b3f7-95dab9f1ccc7	b0853a08-38a4-47f5-b9aa-83f315d74acf
c7355cdc-4d80-465c-b3f7-95dab9f1ccc7	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8
c7355cdc-4d80-465c-b3f7-95dab9f1ccc7	ba85e730-6df5-4e9f-aef0-96577fab369a
c7355cdc-4d80-465c-b3f7-95dab9f1ccc7	32e48d76-6004-4a1c-bb22-27ee66e2d672
c7355cdc-4d80-465c-b3f7-95dab9f1ccc7	e43a754b-648f-460b-88c6-0d3825b1fa76
939cb2db-d154-4e5d-bc36-342983bb35f5	8b90882c-0ce3-48a5-8fae-24268e66fed6
939cb2db-d154-4e5d-bc36-342983bb35f5	77f1a128-ea8f-466e-8c0d-998c2280c811
939cb2db-d154-4e5d-bc36-342983bb35f5	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
939cb2db-d154-4e5d-bc36-342983bb35f5	e211f36c-60a5-47c7-9971-f16df7bf07fc
939cb2db-d154-4e5d-bc36-342983bb35f5	9ab1875d-9ba9-4f1f-8f12-7def153646d1
939cb2db-d154-4e5d-bc36-342983bb35f5	836ee776-ad10-4ca5-8999-0b4cc29a032b
939cb2db-d154-4e5d-bc36-342983bb35f5	04497442-8dc0-45b3-93c8-a59832f0f297
939cb2db-d154-4e5d-bc36-342983bb35f5	f7ea46a8-2dd4-4a5a-88b9-9b964beb68e7
939cb2db-d154-4e5d-bc36-342983bb35f5	72d02727-c162-43b9-a3ab-9b3e8dc4cbd5
939cb2db-d154-4e5d-bc36-342983bb35f5	62d4edd3-b54c-4f35-aef9-d0850f792260
939cb2db-d154-4e5d-bc36-342983bb35f5	32e48d76-6004-4a1c-bb22-27ee66e2d672
939cb2db-d154-4e5d-bc36-342983bb35f5	e43a754b-648f-460b-88c6-0d3825b1fa76
014d1f09-f00d-45d1-acf5-adee2faa51d2	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
014d1f09-f00d-45d1-acf5-adee2faa51d2	77f1a128-ea8f-466e-8c0d-998c2280c811
014d1f09-f00d-45d1-acf5-adee2faa51d2	e211f36c-60a5-47c7-9971-f16df7bf07fc
014d1f09-f00d-45d1-acf5-adee2faa51d2	86961441-94bd-4771-91e3-3580d18a9f40
014d1f09-f00d-45d1-acf5-adee2faa51d2	836ee776-ad10-4ca5-8999-0b4cc29a032b
014d1f09-f00d-45d1-acf5-adee2faa51d2	72d02727-c162-43b9-a3ab-9b3e8dc4cbd5
014d1f09-f00d-45d1-acf5-adee2faa51d2	62d4edd3-b54c-4f35-aef9-d0850f792260
014d1f09-f00d-45d1-acf5-adee2faa51d2	e1888724-e770-43fe-aa8a-3484bc05a37a
014d1f09-f00d-45d1-acf5-adee2faa51d2	32e48d76-6004-4a1c-bb22-27ee66e2d672
014d1f09-f00d-45d1-acf5-adee2faa51d2	e43a754b-648f-460b-88c6-0d3825b1fa76
014d1f09-f00d-45d1-acf5-adee2faa51d2	ae4d3b3b-f1b9-456d-89b3-84dfdafad01f
d0456dd5-b19f-4bca-a8d2-b45cf7a7ca22	32e48d76-6004-4a1c-bb22-27ee66e2d672
426bc6bb-fa15-40b7-b275-4df263e9d92f	8638f31d-dd3a-41df-8571-bcb665c48554
426bc6bb-fa15-40b7-b275-4df263e9d92f	78301a7f-a1d9-494b-8c10-8b11baa5976e
426bc6bb-fa15-40b7-b275-4df263e9d92f	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
426bc6bb-fa15-40b7-b275-4df263e9d92f	47d63157-d363-4e90-bf84-c94e1f61c0ee
426bc6bb-fa15-40b7-b275-4df263e9d92f	86fa5fad-4673-48bf-94f7-d379e7f73676
426bc6bb-fa15-40b7-b275-4df263e9d92f	c3c172d0-f316-40db-a6d2-160a54352755
426bc6bb-fa15-40b7-b275-4df263e9d92f	bf8268d8-5e12-4137-9407-be7988f0eeba
426bc6bb-fa15-40b7-b275-4df263e9d92f	50402a5d-cc3a-41af-a544-46dfb70ef381
426bc6bb-fa15-40b7-b275-4df263e9d92f	cea04306-6f20-431d-9904-c58271cf04f7
426bc6bb-fa15-40b7-b275-4df263e9d92f	ee549e95-aa66-4707-b957-26b96b35d068
426bc6bb-fa15-40b7-b275-4df263e9d92f	04a38028-135f-4e88-9cfc-12066c8f1df3
426bc6bb-fa15-40b7-b275-4df263e9d92f	0750aa84-1b51-45b8-aaad-056cbf1b303b
426bc6bb-fa15-40b7-b275-4df263e9d92f	32e48d76-6004-4a1c-bb22-27ee66e2d672
816c6b1c-5990-4055-b075-f298a06d3d72	32e48d76-6004-4a1c-bb22-27ee66e2d672
e1df54c9-2a72-437f-bcce-cc49104de7f9	32e48d76-6004-4a1c-bb22-27ee66e2d672
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	1e896415-6ebe-443f-b048-30725be095c3
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	05155d0d-89da-4c55-b92c-94a1387ec741
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	03019641-317d-4b74-adaa-1580ff4c99bc
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	af9e38a7-5fea-42ef-b6c1-cd56d0161988
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	3e4285d4-8e01-4483-9219-83079f9d3a77
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	1a931c05-4408-4eb6-afdc-905ebda9edc1
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	8638f31d-dd3a-41df-8571-bcb665c48554
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	47d63157-d363-4e90-bf84-c94e1f61c0ee
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	31084145-ffa6-4861-9673-13c551ae20ab
ab0e80e9-3ce0-4176-949e-f1b4bf1cf539	32e48d76-6004-4a1c-bb22-27ee66e2d672
01489cd9-d899-409e-a911-fcbd4f069a59	8638f31d-dd3a-41df-8571-bcb665c48554
01489cd9-d899-409e-a911-fcbd4f069a59	5e445afa-73c0-4500-a0d8-697acd7bf58e
01489cd9-d899-409e-a911-fcbd4f069a59	e738acf7-a61b-489a-a6f9-bda344038174
01489cd9-d899-409e-a911-fcbd4f069a59	7d48e39c-8f0c-4db0-bd68-3c4b83e2b8d1
01489cd9-d899-409e-a911-fcbd4f069a59	77f1a128-ea8f-466e-8c0d-998c2280c811
01489cd9-d899-409e-a911-fcbd4f069a59	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
01489cd9-d899-409e-a911-fcbd4f069a59	47d63157-d363-4e90-bf84-c94e1f61c0ee
01489cd9-d899-409e-a911-fcbd4f069a59	31084145-ffa6-4861-9673-13c551ae20ab
01489cd9-d899-409e-a911-fcbd4f069a59	32e48d76-6004-4a1c-bb22-27ee66e2d672
3b7d656f-f890-4576-a45c-d6f0f7ea7261	5e445afa-73c0-4500-a0d8-697acd7bf58e
3b7d656f-f890-4576-a45c-d6f0f7ea7261	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
3b7d656f-f890-4576-a45c-d6f0f7ea7261	71e325fb-01c5-44fd-a0de-898927b39357
3b7d656f-f890-4576-a45c-d6f0f7ea7261	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
3b7d656f-f890-4576-a45c-d6f0f7ea7261	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
3b7d656f-f890-4576-a45c-d6f0f7ea7261	aea1a914-5bde-4a5c-92c1-27c9794cf738
3b7d656f-f890-4576-a45c-d6f0f7ea7261	77f1a128-ea8f-466e-8c0d-998c2280c811
3b7d656f-f890-4576-a45c-d6f0f7ea7261	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
3b7d656f-f890-4576-a45c-d6f0f7ea7261	3979dd92-065c-40db-9c3a-3794752685df
3b7d656f-f890-4576-a45c-d6f0f7ea7261	9fcd1dd4-947e-48ad-a71c-937d87f9a894
3b7d656f-f890-4576-a45c-d6f0f7ea7261	d9ded29f-0a3b-4024-b3cd-7a960ce399b7
3b7d656f-f890-4576-a45c-d6f0f7ea7261	32e48d76-6004-4a1c-bb22-27ee66e2d672
ad170c3a-fbec-4d46-948d-46b035e8cc5b	8b90882c-0ce3-48a5-8fae-24268e66fed6
ad170c3a-fbec-4d46-948d-46b035e8cc5b	71e325fb-01c5-44fd-a0de-898927b39357
ad170c3a-fbec-4d46-948d-46b035e8cc5b	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
ad170c3a-fbec-4d46-948d-46b035e8cc5b	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
ad170c3a-fbec-4d46-948d-46b035e8cc5b	b4bebd85-6f9b-4ba2-8ed5-2e4086d6f699
ad170c3a-fbec-4d46-948d-46b035e8cc5b	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
ad170c3a-fbec-4d46-948d-46b035e8cc5b	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
ad170c3a-fbec-4d46-948d-46b035e8cc5b	8d5e74b8-7910-4ee3-b424-3697b919da65
ad170c3a-fbec-4d46-948d-46b035e8cc5b	fb46ae33-946a-46d4-a4f2-29df8b040cff
ad170c3a-fbec-4d46-948d-46b035e8cc5b	dba6afd0-4e82-476c-b1a4-06555587bffb
ad170c3a-fbec-4d46-948d-46b035e8cc5b	b671851f-867f-4d2d-98fd-6f6c5c31c083
ad170c3a-fbec-4d46-948d-46b035e8cc5b	3979dd92-065c-40db-9c3a-3794752685df
ad170c3a-fbec-4d46-948d-46b035e8cc5b	9fcd1dd4-947e-48ad-a71c-937d87f9a894
ad170c3a-fbec-4d46-948d-46b035e8cc5b	d9ded29f-0a3b-4024-b3cd-7a960ce399b7
ad170c3a-fbec-4d46-948d-46b035e8cc5b	32e48d76-6004-4a1c-bb22-27ee66e2d672
2c23534e-864c-40b3-99eb-1524ef8f2901	8b90882c-0ce3-48a5-8fae-24268e66fed6
2c23534e-864c-40b3-99eb-1524ef8f2901	77f1a128-ea8f-466e-8c0d-998c2280c811
2c23534e-864c-40b3-99eb-1524ef8f2901	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
2c23534e-864c-40b3-99eb-1524ef8f2901	e211f36c-60a5-47c7-9971-f16df7bf07fc
2c23534e-864c-40b3-99eb-1524ef8f2901	9ab1875d-9ba9-4f1f-8f12-7def153646d1
2c23534e-864c-40b3-99eb-1524ef8f2901	04497442-8dc0-45b3-93c8-a59832f0f297
2c23534e-864c-40b3-99eb-1524ef8f2901	72d02727-c162-43b9-a3ab-9b3e8dc4cbd5
2c23534e-864c-40b3-99eb-1524ef8f2901	62d4edd3-b54c-4f35-aef9-d0850f792260
2c23534e-864c-40b3-99eb-1524ef8f2901	350475ef-1d3b-4823-8c76-7f190a27f87e
2c23534e-864c-40b3-99eb-1524ef8f2901	32e48d76-6004-4a1c-bb22-27ee66e2d672
2c23534e-864c-40b3-99eb-1524ef8f2901	e43a754b-648f-460b-88c6-0d3825b1fa76
dfb31c12-2b1c-4192-a764-5dabdb373aab	6b49d198-e1bd-4710-b4e8-d9befa664a83
dfb31c12-2b1c-4192-a764-5dabdb373aab	e211f36c-60a5-47c7-9971-f16df7bf07fc
dfb31c12-2b1c-4192-a764-5dabdb373aab	a48b5c93-d26c-4e45-a863-60e94af8503c
dfb31c12-2b1c-4192-a764-5dabdb373aab	b0956325-1cdb-425f-a9a6-afb752a5485d
dfb31c12-2b1c-4192-a764-5dabdb373aab	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
dfb31c12-2b1c-4192-a764-5dabdb373aab	8c6942ac-e020-4a00-acc4-8c41409854ab
dfb31c12-2b1c-4192-a764-5dabdb373aab	62d4892c-a2ea-456d-90c8-0eef9df99961
dfb31c12-2b1c-4192-a764-5dabdb373aab	b0853a08-38a4-47f5-b9aa-83f315d74acf
dfb31c12-2b1c-4192-a764-5dabdb373aab	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8
dfb31c12-2b1c-4192-a764-5dabdb373aab	ba85e730-6df5-4e9f-aef0-96577fab369a
dfb31c12-2b1c-4192-a764-5dabdb373aab	32e48d76-6004-4a1c-bb22-27ee66e2d672
dfb31c12-2b1c-4192-a764-5dabdb373aab	e43a754b-648f-460b-88c6-0d3825b1fa76
15883d5f-d750-4251-8748-43d3e584058c	6c6074b4-62fc-4312-91e6-2e8a9196aca7
15883d5f-d750-4251-8748-43d3e584058c	99e29aa2-9c26-4d09-9206-6173b7956d7d
15883d5f-d750-4251-8748-43d3e584058c	b0956325-1cdb-425f-a9a6-afb752a5485d
15883d5f-d750-4251-8748-43d3e584058c	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
15883d5f-d750-4251-8748-43d3e584058c	8c6942ac-e020-4a00-acc4-8c41409854ab
15883d5f-d750-4251-8748-43d3e584058c	4a858055-f275-460b-a349-222cc0ca28d7
15883d5f-d750-4251-8748-43d3e584058c	22891480-943a-4cb1-a417-c760205d86bd
15883d5f-d750-4251-8748-43d3e584058c	d44fa577-b407-4d90-972b-b3d39ab4b93d
15883d5f-d750-4251-8748-43d3e584058c	fbd5f933-b196-48bb-b49a-f8f2b1bcf563
15883d5f-d750-4251-8748-43d3e584058c	32e48d76-6004-4a1c-bb22-27ee66e2d672
15883d5f-d750-4251-8748-43d3e584058c	e43a754b-648f-460b-88c6-0d3825b1fa76
c69086e5-52cb-421f-8d1d-ab2dde7b3431	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
c69086e5-52cb-421f-8d1d-ab2dde7b3431	9b90fa62-a329-4089-b71f-f9a70c27ac03
c69086e5-52cb-421f-8d1d-ab2dde7b3431	74a8a447-3f16-43a9-b29b-84d2e5d86a88
c69086e5-52cb-421f-8d1d-ab2dde7b3431	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d
c69086e5-52cb-421f-8d1d-ab2dde7b3431	f058e2d3-bcd6-46c2-92c0-3e0aa4e71757
c69086e5-52cb-421f-8d1d-ab2dde7b3431	f65faea9-66ad-40ce-8488-753078555a3f
c69086e5-52cb-421f-8d1d-ab2dde7b3431	c2380f71-ff90-46f3-8438-49f91616f6a6
c69086e5-52cb-421f-8d1d-ab2dde7b3431	32e48d76-6004-4a1c-bb22-27ee66e2d672
b7be3d84-206b-4934-8882-fd01f2fc0830	99e29aa2-9c26-4d09-9206-6173b7956d7d
b7be3d84-206b-4934-8882-fd01f2fc0830	6c6074b4-62fc-4312-91e6-2e8a9196aca7
b7be3d84-206b-4934-8882-fd01f2fc0830	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
b7be3d84-206b-4934-8882-fd01f2fc0830	b0956325-1cdb-425f-a9a6-afb752a5485d
b7be3d84-206b-4934-8882-fd01f2fc0830	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
b7be3d84-206b-4934-8882-fd01f2fc0830	8c6942ac-e020-4a00-acc4-8c41409854ab
b7be3d84-206b-4934-8882-fd01f2fc0830	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
b7be3d84-206b-4934-8882-fd01f2fc0830	9b90fa62-a329-4089-b71f-f9a70c27ac03
b7be3d84-206b-4934-8882-fd01f2fc0830	74a8a447-3f16-43a9-b29b-84d2e5d86a88
b7be3d84-206b-4934-8882-fd01f2fc0830	32e48d76-6004-4a1c-bb22-27ee66e2d672
b7be3d84-206b-4934-8882-fd01f2fc0830	e43a754b-648f-460b-88c6-0d3825b1fa76
3d6a1ea9-ee9a-4c08-905b-6878462cb753	0784ce65-0cea-4000-858f-a5bc87d86e8f
3d6a1ea9-ee9a-4c08-905b-6878462cb753	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
3d6a1ea9-ee9a-4c08-905b-6878462cb753	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
3d6a1ea9-ee9a-4c08-905b-6878462cb753	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
3d6a1ea9-ee9a-4c08-905b-6878462cb753	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
3d6a1ea9-ee9a-4c08-905b-6878462cb753	2e103b9f-097b-4d2d-9ee9-8806713621ce
3d6a1ea9-ee9a-4c08-905b-6878462cb753	885428c2-75f8-47f6-80a6-94337b9fb10f
3d6a1ea9-ee9a-4c08-905b-6878462cb753	84a6556d-48f6-4fec-8468-7c6e9dd9c5ab
3d6a1ea9-ee9a-4c08-905b-6878462cb753	32e48d76-6004-4a1c-bb22-27ee66e2d672
3d6a1ea9-ee9a-4c08-905b-6878462cb753	e43a754b-648f-460b-88c6-0d3825b1fa76
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	0784ce65-0cea-4000-858f-a5bc87d86e8f
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	e65a3a55-59ca-4533-bdf2-ba8511d594ad
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	2e103b9f-097b-4d2d-9ee9-8806713621ce
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	c497544b-bd79-4cf2-a63a-8cffacc1fe46
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	edcf6bf0-5a1a-41c6-ac00-56a63327adbb
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	32e48d76-6004-4a1c-bb22-27ee66e2d672
bef0994e-7b2f-45f4-9cfa-aceab4d4bc2f	e43a754b-648f-460b-88c6-0d3825b1fa76
50245ba2-e8a6-4280-8070-f37b60da1e5a	5e445afa-73c0-4500-a0d8-697acd7bf58e
50245ba2-e8a6-4280-8070-f37b60da1e5a	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
50245ba2-e8a6-4280-8070-f37b60da1e5a	71e325fb-01c5-44fd-a0de-898927b39357
50245ba2-e8a6-4280-8070-f37b60da1e5a	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
50245ba2-e8a6-4280-8070-f37b60da1e5a	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
50245ba2-e8a6-4280-8070-f37b60da1e5a	aea1a914-5bde-4a5c-92c1-27c9794cf738
50245ba2-e8a6-4280-8070-f37b60da1e5a	77f1a128-ea8f-466e-8c0d-998c2280c811
50245ba2-e8a6-4280-8070-f37b60da1e5a	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
50245ba2-e8a6-4280-8070-f37b60da1e5a	803f1d5e-f530-4983-b815-68238a066cbf
50245ba2-e8a6-4280-8070-f37b60da1e5a	d9ded29f-0a3b-4024-b3cd-7a960ce399b7
50245ba2-e8a6-4280-8070-f37b60da1e5a	32e48d76-6004-4a1c-bb22-27ee66e2d672
aa548264-ec56-4d08-8dee-bb5c29e3c26f	d0ef13bf-359f-4fff-be36-1b34a6cf72e3
aa548264-ec56-4d08-8dee-bb5c29e3c26f	26387a0d-6d8b-41d9-9dd9-3174b23458b4
aa548264-ec56-4d08-8dee-bb5c29e3c26f	32e48d76-6004-4a1c-bb22-27ee66e2d672
ac232aed-3930-4f39-92ce-5cc61e8c29f3	b0956325-1cdb-425f-a9a6-afb752a5485d
ac232aed-3930-4f39-92ce-5cc61e8c29f3	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
ac232aed-3930-4f39-92ce-5cc61e8c29f3	8c6942ac-e020-4a00-acc4-8c41409854ab
ac232aed-3930-4f39-92ce-5cc61e8c29f3	62d4892c-a2ea-456d-90c8-0eef9df99961
ac232aed-3930-4f39-92ce-5cc61e8c29f3	b0853a08-38a4-47f5-b9aa-83f315d74acf
ac232aed-3930-4f39-92ce-5cc61e8c29f3	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8
ac232aed-3930-4f39-92ce-5cc61e8c29f3	ba85e730-6df5-4e9f-aef0-96577fab369a
ac232aed-3930-4f39-92ce-5cc61e8c29f3	32e48d76-6004-4a1c-bb22-27ee66e2d672
ac232aed-3930-4f39-92ce-5cc61e8c29f3	e43a754b-648f-460b-88c6-0d3825b1fa76
6ccf7885-6654-43cc-b1fd-2a03ff172f28	8b90882c-0ce3-48a5-8fae-24268e66fed6
6ccf7885-6654-43cc-b1fd-2a03ff172f28	77f1a128-ea8f-466e-8c0d-998c2280c811
6ccf7885-6654-43cc-b1fd-2a03ff172f28	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
6ccf7885-6654-43cc-b1fd-2a03ff172f28	803f1d5e-f530-4983-b815-68238a066cbf
6ccf7885-6654-43cc-b1fd-2a03ff172f28	e211f36c-60a5-47c7-9971-f16df7bf07fc
6ccf7885-6654-43cc-b1fd-2a03ff172f28	9ab1875d-9ba9-4f1f-8f12-7def153646d1
6ccf7885-6654-43cc-b1fd-2a03ff172f28	836ee776-ad10-4ca5-8999-0b4cc29a032b
6ccf7885-6654-43cc-b1fd-2a03ff172f28	04497442-8dc0-45b3-93c8-a59832f0f297
6ccf7885-6654-43cc-b1fd-2a03ff172f28	72d02727-c162-43b9-a3ab-9b3e8dc4cbd5
6ccf7885-6654-43cc-b1fd-2a03ff172f28	62d4edd3-b54c-4f35-aef9-d0850f792260
6ccf7885-6654-43cc-b1fd-2a03ff172f28	32e48d76-6004-4a1c-bb22-27ee66e2d672
6ccf7885-6654-43cc-b1fd-2a03ff172f28	e43a754b-648f-460b-88c6-0d3825b1fa76
590342ba-0584-4734-84d1-7e9fccb5d0d4	6b49d198-e1bd-4710-b4e8-d9befa664a83
590342ba-0584-4734-84d1-7e9fccb5d0d4	e211f36c-60a5-47c7-9971-f16df7bf07fc
590342ba-0584-4734-84d1-7e9fccb5d0d4	14e253d0-ff9a-4614-845a-bd91c1d91ad3
590342ba-0584-4734-84d1-7e9fccb5d0d4	79e610f9-4c9f-4234-9a37-d8e1d11a6af2
590342ba-0584-4734-84d1-7e9fccb5d0d4	a48b5c93-d26c-4e45-a863-60e94af8503c
590342ba-0584-4734-84d1-7e9fccb5d0d4	b0956325-1cdb-425f-a9a6-afb752a5485d
590342ba-0584-4734-84d1-7e9fccb5d0d4	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
590342ba-0584-4734-84d1-7e9fccb5d0d4	8c6942ac-e020-4a00-acc4-8c41409854ab
590342ba-0584-4734-84d1-7e9fccb5d0d4	62d4892c-a2ea-456d-90c8-0eef9df99961
590342ba-0584-4734-84d1-7e9fccb5d0d4	b0853a08-38a4-47f5-b9aa-83f315d74acf
590342ba-0584-4734-84d1-7e9fccb5d0d4	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8
590342ba-0584-4734-84d1-7e9fccb5d0d4	ba85e730-6df5-4e9f-aef0-96577fab369a
590342ba-0584-4734-84d1-7e9fccb5d0d4	32e48d76-6004-4a1c-bb22-27ee66e2d672
590342ba-0584-4734-84d1-7e9fccb5d0d4	e43a754b-648f-460b-88c6-0d3825b1fa76
9f1d4d22-e807-4fd3-898e-42adf3388367	8b90882c-0ce3-48a5-8fae-24268e66fed6
9f1d4d22-e807-4fd3-898e-42adf3388367	71e325fb-01c5-44fd-a0de-898927b39357
9f1d4d22-e807-4fd3-898e-42adf3388367	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
9f1d4d22-e807-4fd3-898e-42adf3388367	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
9f1d4d22-e807-4fd3-898e-42adf3388367	aea1a914-5bde-4a5c-92c1-27c9794cf738
9f1d4d22-e807-4fd3-898e-42adf3388367	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
9f1d4d22-e807-4fd3-898e-42adf3388367	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
9f1d4d22-e807-4fd3-898e-42adf3388367	8d5e74b8-7910-4ee3-b424-3697b919da65
9f1d4d22-e807-4fd3-898e-42adf3388367	fb46ae33-946a-46d4-a4f2-29df8b040cff
9f1d4d22-e807-4fd3-898e-42adf3388367	803f1d5e-f530-4983-b815-68238a066cbf
9f1d4d22-e807-4fd3-898e-42adf3388367	dba6afd0-4e82-476c-b1a4-06555587bffb
9f1d4d22-e807-4fd3-898e-42adf3388367	b671851f-867f-4d2d-98fd-6f6c5c31c083
9f1d4d22-e807-4fd3-898e-42adf3388367	d9ded29f-0a3b-4024-b3cd-7a960ce399b7
9f1d4d22-e807-4fd3-898e-42adf3388367	32e48d76-6004-4a1c-bb22-27ee66e2d672
ebc672c8-6533-4103-bf6f-b8d2d079d2f6	32e48d76-6004-4a1c-bb22-27ee66e2d672
714add9f-098f-4c0c-8365-ba92b764b0e3	3d6d21de-bf1b-4fbb-aed0-ee57caa00988
714add9f-098f-4c0c-8365-ba92b764b0e3	6b1271eb-54e6-49bd-ab27-c85605ec8576
714add9f-098f-4c0c-8365-ba92b764b0e3	9ab1875d-9ba9-4f1f-8f12-7def153646d1
714add9f-098f-4c0c-8365-ba92b764b0e3	4bbc1f38-3573-469b-a204-b84123b925a3
714add9f-098f-4c0c-8365-ba92b764b0e3	32e48d76-6004-4a1c-bb22-27ee66e2d672
714add9f-098f-4c0c-8365-ba92b764b0e3	e43a754b-648f-460b-88c6-0d3825b1fa76
714add9f-098f-4c0c-8365-ba92b764b0e3	9a21bb80-9e6f-466d-bee2-96306d08b0ae
c0037556-d981-4811-aebe-531a48b369ef	1e896415-6ebe-443f-b048-30725be095c3
c0037556-d981-4811-aebe-531a48b369ef	05155d0d-89da-4c55-b92c-94a1387ec741
c0037556-d981-4811-aebe-531a48b369ef	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
c0037556-d981-4811-aebe-531a48b369ef	03019641-317d-4b74-adaa-1580ff4c99bc
c0037556-d981-4811-aebe-531a48b369ef	af9e38a7-5fea-42ef-b6c1-cd56d0161988
c0037556-d981-4811-aebe-531a48b369ef	3e4285d4-8e01-4483-9219-83079f9d3a77
c0037556-d981-4811-aebe-531a48b369ef	1a931c05-4408-4eb6-afdc-905ebda9edc1
c0037556-d981-4811-aebe-531a48b369ef	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
c0037556-d981-4811-aebe-531a48b369ef	c5273d0a-67cd-4449-aa07-8a9fbc6b3301
c0037556-d981-4811-aebe-531a48b369ef	69a94b81-7ed0-428b-a3c9-679a34e47e0a
c0037556-d981-4811-aebe-531a48b369ef	a03a4688-83e6-439a-a101-87bec382d6b0
c0037556-d981-4811-aebe-531a48b369ef	397d9d65-bfca-46fd-96b2-d81390b682b2
c0037556-d981-4811-aebe-531a48b369ef	32e48d76-6004-4a1c-bb22-27ee66e2d672
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	71e325fb-01c5-44fd-a0de-898927b39357
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	a666d323-152f-48af-be42-f59d6cc27deb
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	009eb4ee-d905-4f04-bb23-7fb156ee569e
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	c7955389-a368-4f9a-9a48-cd015c38d841
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	a4dd8404-9c4a-4046-b857-609f4f6bda92
1ec2765f-67b5-492b-af5a-6c993bb531b7	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	77f1a128-ea8f-466e-8c0d-998c2280c811
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
1ec2765f-67b5-492b-af5a-6c993bb531b7	71e325fb-01c5-44fd-a0de-898927b39357
1ec2765f-67b5-492b-af5a-6c993bb531b7	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
1ec2765f-67b5-492b-af5a-6c993bb531b7	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	8d5e74b8-7910-4ee3-b424-3697b919da65
1ec2765f-67b5-492b-af5a-6c993bb531b7	a666d323-152f-48af-be42-f59d6cc27deb
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	fb46ae33-946a-46d4-a4f2-29df8b040cff
1ec2765f-67b5-492b-af5a-6c993bb531b7	009eb4ee-d905-4f04-bb23-7fb156ee569e
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	803f1d5e-f530-4983-b815-68238a066cbf
1ec2765f-67b5-492b-af5a-6c993bb531b7	c7955389-a368-4f9a-9a48-cd015c38d841
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
1ec2765f-67b5-492b-af5a-6c993bb531b7	a4dd8404-9c4a-4046-b857-609f4f6bda92
8c766cc2-0ce8-4a20-a32d-4bfc556854e9	32e48d76-6004-4a1c-bb22-27ee66e2d672
1ec2765f-67b5-492b-af5a-6c993bb531b7	77f1a128-ea8f-466e-8c0d-998c2280c811
1ec2765f-67b5-492b-af5a-6c993bb531b7	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
1ec2765f-67b5-492b-af5a-6c993bb531b7	86961441-94bd-4771-91e3-3580d18a9f40
1ec2765f-67b5-492b-af5a-6c993bb531b7	fb02acd0-7388-4507-b25a-49573981c9a1
1ec2765f-67b5-492b-af5a-6c993bb531b7	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
1ec2765f-67b5-492b-af5a-6c993bb531b7	32e48d76-6004-4a1c-bb22-27ee66e2d672
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	8d5e74b8-7910-4ee3-b424-3697b919da65
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	fb46ae33-946a-46d4-a4f2-29df8b040cff
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	803f1d5e-f530-4983-b815-68238a066cbf
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	e211f36c-60a5-47c7-9971-f16df7bf07fc
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	3d6d21de-bf1b-4fbb-aed0-ee57caa00988
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	6b1271eb-54e6-49bd-ab27-c85605ec8576
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	32e48d76-6004-4a1c-bb22-27ee66e2d672
cfd2d227-784d-4a3b-b717-5e5b3e7eeadf	e43a754b-648f-460b-88c6-0d3825b1fa76
7e412fc5-c9b1-4859-81fa-affc2a13606c	32e48d76-6004-4a1c-bb22-27ee66e2d672
7e412fc5-c9b1-4859-81fa-affc2a13606c	97e67f12-4879-4dc6-8759-e205bba8b0f9
b6e581f3-b751-429f-ad73-06906cb74f73	1e896415-6ebe-443f-b048-30725be095c3
b6e581f3-b751-429f-ad73-06906cb74f73	05155d0d-89da-4c55-b92c-94a1387ec741
b6e581f3-b751-429f-ad73-06906cb74f73	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
b6e581f3-b751-429f-ad73-06906cb74f73	03019641-317d-4b74-adaa-1580ff4c99bc
b6e581f3-b751-429f-ad73-06906cb74f73	af9e38a7-5fea-42ef-b6c1-cd56d0161988
b6e581f3-b751-429f-ad73-06906cb74f73	3e4285d4-8e01-4483-9219-83079f9d3a77
b6e581f3-b751-429f-ad73-06906cb74f73	1a931c05-4408-4eb6-afdc-905ebda9edc1
b6e581f3-b751-429f-ad73-06906cb74f73	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
b6e581f3-b751-429f-ad73-06906cb74f73	c5273d0a-67cd-4449-aa07-8a9fbc6b3301
b6e581f3-b751-429f-ad73-06906cb74f73	69a94b81-7ed0-428b-a3c9-679a34e47e0a
b6e581f3-b751-429f-ad73-06906cb74f73	f6a2c559-1f7d-4f45-adf1-8c76b2faf147
b6e581f3-b751-429f-ad73-06906cb74f73	cd5f8554-fec4-4862-80c3-caff5df01ad5
b6e581f3-b751-429f-ad73-06906cb74f73	cfba2139-68f1-4892-8b8e-607fad765801
b6e581f3-b751-429f-ad73-06906cb74f73	32e48d76-6004-4a1c-bb22-27ee66e2d672
44032965-4f4a-4f70-a03a-2142ee9849d8	78301a7f-a1d9-494b-8c10-8b11baa5976e
44032965-4f4a-4f70-a03a-2142ee9849d8	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
44032965-4f4a-4f70-a03a-2142ee9849d8	803f1d5e-f530-4983-b815-68238a066cbf
44032965-4f4a-4f70-a03a-2142ee9849d8	e211f36c-60a5-47c7-9971-f16df7bf07fc
44032965-4f4a-4f70-a03a-2142ee9849d8	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
fc185acb-ee44-463d-b4fd-780e6be0cf55	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
fc185acb-ee44-463d-b4fd-780e6be0cf55	e738acf7-a61b-489a-a6f9-bda344038174
fc185acb-ee44-463d-b4fd-780e6be0cf55	3bcee379-cb47-43a8-84f6-933a0b3de0a4
fc185acb-ee44-463d-b4fd-780e6be0cf55	fb02acd0-7388-4507-b25a-49573981c9a1
fc185acb-ee44-463d-b4fd-780e6be0cf55	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
fc185acb-ee44-463d-b4fd-780e6be0cf55	df7e7468-87c6-4728-b792-e4058c15908d
fc185acb-ee44-463d-b4fd-780e6be0cf55	c5611c9c-3d1a-4fae-9999-35acab706abc
fc185acb-ee44-463d-b4fd-780e6be0cf55	f6a2c559-1f7d-4f45-adf1-8c76b2faf147
fc185acb-ee44-463d-b4fd-780e6be0cf55	cd5f8554-fec4-4862-80c3-caff5df01ad5
fc185acb-ee44-463d-b4fd-780e6be0cf55	64e7e3c8-c369-4913-be67-a64c7ea29d11
44032965-4f4a-4f70-a03a-2142ee9849d8	836ee776-ad10-4ca5-8999-0b4cc29a032b
44032965-4f4a-4f70-a03a-2142ee9849d8	839631b1-18ca-4d4a-8105-6ec7c0cf8ffd
44032965-4f4a-4f70-a03a-2142ee9849d8	f8a8bb67-a047-439e-9956-ea4822e56e98
44032965-4f4a-4f70-a03a-2142ee9849d8	14fe8296-326b-4aed-ad72-315db46d9504
44032965-4f4a-4f70-a03a-2142ee9849d8	4aeb5881-7e48-470e-b134-12236311f9b8
44032965-4f4a-4f70-a03a-2142ee9849d8	32e48d76-6004-4a1c-bb22-27ee66e2d672
44032965-4f4a-4f70-a03a-2142ee9849d8	e43a754b-648f-460b-88c6-0d3825b1fa76
44032965-4f4a-4f70-a03a-2142ee9849d8	94ad2b6d-975f-4746-9a3b-33c32b34a13f
44032965-4f4a-4f70-a03a-2142ee9849d8	04690b0c-f46f-4bb7-8932-1d3ff3879486
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	99e29aa2-9c26-4d09-9206-6173b7956d7d
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	6c6074b4-62fc-4312-91e6-2e8a9196aca7
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	b0956325-1cdb-425f-a9a6-afb752a5485d
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	8c6942ac-e020-4a00-acc4-8c41409854ab
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	4a858055-f275-460b-a349-222cc0ca28d7
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	22891480-943a-4cb1-a417-c760205d86bd
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	d44fa577-b407-4d90-972b-b3d39ab4b93d
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	fbd5f933-b196-48bb-b49a-f8f2b1bcf563
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	32e48d76-6004-4a1c-bb22-27ee66e2d672
432dbc60-bfa7-4eee-b4b3-1afbbc863fa6	e43a754b-648f-460b-88c6-0d3825b1fa76
ca3915fa-573c-475c-b419-22038c42db7f	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
ca3915fa-573c-475c-b419-22038c42db7f	9b90fa62-a329-4089-b71f-f9a70c27ac03
ca3915fa-573c-475c-b419-22038c42db7f	74a8a447-3f16-43a9-b29b-84d2e5d86a88
ca3915fa-573c-475c-b419-22038c42db7f	350475ef-1d3b-4823-8c76-7f190a27f87e
ca3915fa-573c-475c-b419-22038c42db7f	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d
ca3915fa-573c-475c-b419-22038c42db7f	f058e2d3-bcd6-46c2-92c0-3e0aa4e71757
ca3915fa-573c-475c-b419-22038c42db7f	f65faea9-66ad-40ce-8488-753078555a3f
ca3915fa-573c-475c-b419-22038c42db7f	c2380f71-ff90-46f3-8438-49f91616f6a6
ca3915fa-573c-475c-b419-22038c42db7f	32e48d76-6004-4a1c-bb22-27ee66e2d672
fc185acb-ee44-463d-b4fd-780e6be0cf55	2f39f7da-a37f-4ccb-8a80-61b108cc7800
fc185acb-ee44-463d-b4fd-780e6be0cf55	99a22674-5148-4e0e-85c4-409449b8c7e3
fc185acb-ee44-463d-b4fd-780e6be0cf55	a8b61e71-3df5-41bb-9d40-d0fad75e7fbf
fc185acb-ee44-463d-b4fd-780e6be0cf55	86c4b224-039c-49c4-8bbf-1d2c0d923fc6
fc185acb-ee44-463d-b4fd-780e6be0cf55	fa24d9c0-130f-499c-ba42-217e905faef6
fc185acb-ee44-463d-b4fd-780e6be0cf55	cafaf289-db67-424b-91fa-fa16a5270eda
fc185acb-ee44-463d-b4fd-780e6be0cf55	32e48d76-6004-4a1c-bb22-27ee66e2d672
fc185acb-ee44-463d-b4fd-780e6be0cf55	a9ca51e9-864d-4d17-b647-ff17ea2c02e6
fc185acb-ee44-463d-b4fd-780e6be0cf55	e64236c3-e2ea-45a1-8a28-c9c596267c97
470273d7-a695-4d41-b325-136c3b427957	71e325fb-01c5-44fd-a0de-898927b39357
470273d7-a695-4d41-b325-136c3b427957	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
470273d7-a695-4d41-b325-136c3b427957	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
470273d7-a695-4d41-b325-136c3b427957	7c23bee8-0b73-4720-af87-28c6c180de6a
470273d7-a695-4d41-b325-136c3b427957	fb02acd0-7388-4507-b25a-49573981c9a1
470273d7-a695-4d41-b325-136c3b427957	c28a9113-da92-43ba-8123-741f67dc4b89
470273d7-a695-4d41-b325-136c3b427957	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
470273d7-a695-4d41-b325-136c3b427957	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
470273d7-a695-4d41-b325-136c3b427957	df7e7468-87c6-4728-b792-e4058c15908d
470273d7-a695-4d41-b325-136c3b427957	c5611c9c-3d1a-4fae-9999-35acab706abc
470273d7-a695-4d41-b325-136c3b427957	555bdc44-a24e-48fc-aa5e-892e666781a5
470273d7-a695-4d41-b325-136c3b427957	9db2d190-6068-4c34-9e5c-36e9994274aa
470273d7-a695-4d41-b325-136c3b427957	64e7e3c8-c369-4913-be67-a64c7ea29d11
470273d7-a695-4d41-b325-136c3b427957	32e48d76-6004-4a1c-bb22-27ee66e2d672
6cfc4649-d517-49fa-8da9-7db7ed861453	32e48d76-6004-4a1c-bb22-27ee66e2d672
8a02da81-52b7-43c0-99b4-5850c502df38	1e896415-6ebe-443f-b048-30725be095c3
8a02da81-52b7-43c0-99b4-5850c502df38	05155d0d-89da-4c55-b92c-94a1387ec741
8a02da81-52b7-43c0-99b4-5850c502df38	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
8a02da81-52b7-43c0-99b4-5850c502df38	03019641-317d-4b74-adaa-1580ff4c99bc
8a02da81-52b7-43c0-99b4-5850c502df38	af9e38a7-5fea-42ef-b6c1-cd56d0161988
8a02da81-52b7-43c0-99b4-5850c502df38	3e4285d4-8e01-4483-9219-83079f9d3a77
8a02da81-52b7-43c0-99b4-5850c502df38	1a931c05-4408-4eb6-afdc-905ebda9edc1
8a02da81-52b7-43c0-99b4-5850c502df38	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
8a02da81-52b7-43c0-99b4-5850c502df38	86961441-94bd-4771-91e3-3580d18a9f40
8a02da81-52b7-43c0-99b4-5850c502df38	bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23
8a02da81-52b7-43c0-99b4-5850c502df38	0b2159c6-a703-43bb-b6a1-5392153e01d7
8a02da81-52b7-43c0-99b4-5850c502df38	3cf8659e-c9c2-4c9f-91d0-897951be0372
8a02da81-52b7-43c0-99b4-5850c502df38	b3bd0f0e-83df-4b31-8f33-815e6853a3a9
8a02da81-52b7-43c0-99b4-5850c502df38	32e48d76-6004-4a1c-bb22-27ee66e2d672
8b294708-6629-45ea-a765-9de69ade3dda	0784ce65-0cea-4000-858f-a5bc87d86e8f
8b294708-6629-45ea-a765-9de69ade3dda	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
8b294708-6629-45ea-a765-9de69ade3dda	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
8b294708-6629-45ea-a765-9de69ade3dda	e65a3a55-59ca-4533-bdf2-ba8511d594ad
8b294708-6629-45ea-a765-9de69ade3dda	2e103b9f-097b-4d2d-9ee9-8806713621ce
8b294708-6629-45ea-a765-9de69ade3dda	c497544b-bd79-4cf2-a63a-8cffacc1fe46
8b294708-6629-45ea-a765-9de69ade3dda	edcf6bf0-5a1a-41c6-ac00-56a63327adbb
8b294708-6629-45ea-a765-9de69ade3dda	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d
8b294708-6629-45ea-a765-9de69ade3dda	32e48d76-6004-4a1c-bb22-27ee66e2d672
8b294708-6629-45ea-a765-9de69ade3dda	e43a754b-648f-460b-88c6-0d3825b1fa76
f283884c-6b34-4c76-af51-0d1fe3899cf5	0784ce65-0cea-4000-858f-a5bc87d86e8f
f283884c-6b34-4c76-af51-0d1fe3899cf5	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
641e67e1-2b27-48d8-bedb-720ae2bcfac0	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
641e67e1-2b27-48d8-bedb-720ae2bcfac0	71e325fb-01c5-44fd-a0de-898927b39357
641e67e1-2b27-48d8-bedb-720ae2bcfac0	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
641e67e1-2b27-48d8-bedb-720ae2bcfac0	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
641e67e1-2b27-48d8-bedb-720ae2bcfac0	a8bdcf92-25a5-49f5-b2d7-57c83ff424f4
641e67e1-2b27-48d8-bedb-720ae2bcfac0	1e7550cf-39bf-4394-a631-6279e4be4997
641e67e1-2b27-48d8-bedb-720ae2bcfac0	77f1a128-ea8f-466e-8c0d-998c2280c811
641e67e1-2b27-48d8-bedb-720ae2bcfac0	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
641e67e1-2b27-48d8-bedb-720ae2bcfac0	fb02acd0-7388-4507-b25a-49573981c9a1
641e67e1-2b27-48d8-bedb-720ae2bcfac0	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
641e67e1-2b27-48d8-bedb-720ae2bcfac0	75ac7b94-3abe-4833-994e-110fc95c2ab4
641e67e1-2b27-48d8-bedb-720ae2bcfac0	32e48d76-6004-4a1c-bb22-27ee66e2d672
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	71e325fb-01c5-44fd-a0de-898927b39357
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	a8bdcf92-25a5-49f5-b2d7-57c83ff424f4
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	1e7550cf-39bf-4394-a631-6279e4be4997
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	77f1a128-ea8f-466e-8c0d-998c2280c811
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	fb02acd0-7388-4507-b25a-49573981c9a1
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	75ac7b94-3abe-4833-994e-110fc95c2ab4
45a3ec40-61f0-42d6-a7d9-dc92ec74de4b	32e48d76-6004-4a1c-bb22-27ee66e2d672
f283884c-6b34-4c76-af51-0d1fe3899cf5	8c6942ac-e020-4a00-acc4-8c41409854ab
f283884c-6b34-4c76-af51-0d1fe3899cf5	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
f283884c-6b34-4c76-af51-0d1fe3899cf5	9b90fa62-a329-4089-b71f-f9a70c27ac03
f283884c-6b34-4c76-af51-0d1fe3899cf5	74a8a447-3f16-43a9-b29b-84d2e5d86a88
f283884c-6b34-4c76-af51-0d1fe3899cf5	2e103b9f-097b-4d2d-9ee9-8806713621ce
f283884c-6b34-4c76-af51-0d1fe3899cf5	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d
f283884c-6b34-4c76-af51-0d1fe3899cf5	32e48d76-6004-4a1c-bb22-27ee66e2d672
f283884c-6b34-4c76-af51-0d1fe3899cf5	e43a754b-648f-460b-88c6-0d3825b1fa76
2c30a071-39e9-4e34-a424-00db8169618e	32e48d76-6004-4a1c-bb22-27ee66e2d672
67582b5a-f3b2-4eac-ba12-f5b0038047a1	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
67582b5a-f3b2-4eac-ba12-f5b0038047a1	8c6942ac-e020-4a00-acc4-8c41409854ab
67582b5a-f3b2-4eac-ba12-f5b0038047a1	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
67582b5a-f3b2-4eac-ba12-f5b0038047a1	8310c913-dcfd-432e-b330-ee4bb33b7d3e
67582b5a-f3b2-4eac-ba12-f5b0038047a1	9b90fa62-a329-4089-b71f-f9a70c27ac03
67582b5a-f3b2-4eac-ba12-f5b0038047a1	74a8a447-3f16-43a9-b29b-84d2e5d86a88
67582b5a-f3b2-4eac-ba12-f5b0038047a1	e65a3a55-59ca-4533-bdf2-ba8511d594ad
67582b5a-f3b2-4eac-ba12-f5b0038047a1	32e48d76-6004-4a1c-bb22-27ee66e2d672
67582b5a-f3b2-4eac-ba12-f5b0038047a1	e43a754b-648f-460b-88c6-0d3825b1fa76
bb0f6d27-fca6-4dec-8bb0-7680a52dc914	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
bb0f6d27-fca6-4dec-8bb0-7680a52dc914	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
bb0f6d27-fca6-4dec-8bb0-7680a52dc914	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
bb0f6d27-fca6-4dec-8bb0-7680a52dc914	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
bb0f6d27-fca6-4dec-8bb0-7680a52dc914	2e103b9f-097b-4d2d-9ee9-8806713621ce
bb0f6d27-fca6-4dec-8bb0-7680a52dc914	350475ef-1d3b-4823-8c76-7f190a27f87e
bb0f6d27-fca6-4dec-8bb0-7680a52dc914	32e48d76-6004-4a1c-bb22-27ee66e2d672
bb0f6d27-fca6-4dec-8bb0-7680a52dc914	e43a754b-648f-460b-88c6-0d3825b1fa76
9157eec8-233c-4bc7-8421-c3f6ef9757d6	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
9157eec8-233c-4bc7-8421-c3f6ef9757d6	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
9157eec8-233c-4bc7-8421-c3f6ef9757d6	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
9157eec8-233c-4bc7-8421-c3f6ef9757d6	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
9157eec8-233c-4bc7-8421-c3f6ef9757d6	2e103b9f-097b-4d2d-9ee9-8806713621ce
9157eec8-233c-4bc7-8421-c3f6ef9757d6	350475ef-1d3b-4823-8c76-7f190a27f87e
9157eec8-233c-4bc7-8421-c3f6ef9757d6	32e48d76-6004-4a1c-bb22-27ee66e2d672
9157eec8-233c-4bc7-8421-c3f6ef9757d6	e43a754b-648f-460b-88c6-0d3825b1fa76
9157eec8-233c-4bc7-8421-c3f6ef9757d6	0784ce65-0cea-4000-858f-a5bc87d86e8f
3d31f80b-eb0d-40b5-b1cb-2e7c411ded97	32e48d76-6004-4a1c-bb22-27ee66e2d672
7ab812fc-4a6e-4859-b4ef-b7d17600d0f8	32e48d76-6004-4a1c-bb22-27ee66e2d672
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	1e896415-6ebe-443f-b048-30725be095c3
23b7149f-5270-4843-b96a-b5f603966e82	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	0784ce65-0cea-4000-858f-a5bc87d86e8f
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	8c6942ac-e020-4a00-acc4-8c41409854ab
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	05155d0d-89da-4c55-b92c-94a1387ec741
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
23b7149f-5270-4843-b96a-b5f603966e82	e738acf7-a61b-489a-a6f9-bda344038174
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	9b90fa62-a329-4089-b71f-f9a70c27ac03
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	74a8a447-3f16-43a9-b29b-84d2e5d86a88
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	2e103b9f-097b-4d2d-9ee9-8806713621ce
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	32e48d76-6004-4a1c-bb22-27ee66e2d672
23b7149f-5270-4843-b96a-b5f603966e82	6bbb9b79-33ba-422f-9964-a8435805fcbd
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	03019641-317d-4b74-adaa-1580ff4c99bc
2adb2a04-af0e-42b7-b5ca-c7dcf183299c	e43a754b-648f-460b-88c6-0d3825b1fa76
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	af9e38a7-5fea-42ef-b6c1-cd56d0161988
ac28b210-5fd5-41fe-bf29-8ef880fb5746	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	3e4285d4-8e01-4483-9219-83079f9d3a77
23b7149f-5270-4843-b96a-b5f603966e82	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
ac28b210-5fd5-41fe-bf29-8ef880fb5746	8c6942ac-e020-4a00-acc4-8c41409854ab
23b7149f-5270-4843-b96a-b5f603966e82	86961441-94bd-4771-91e3-3580d18a9f40
ac28b210-5fd5-41fe-bf29-8ef880fb5746	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	1a931c05-4408-4eb6-afdc-905ebda9edc1
23b7149f-5270-4843-b96a-b5f603966e82	fb02acd0-7388-4507-b25a-49573981c9a1
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
23b7149f-5270-4843-b96a-b5f603966e82	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	c5273d0a-67cd-4449-aa07-8a9fbc6b3301
23b7149f-5270-4843-b96a-b5f603966e82	df7e7468-87c6-4728-b792-e4058c15908d
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	69a94b81-7ed0-428b-a3c9-679a34e47e0a
23b7149f-5270-4843-b96a-b5f603966e82	c5611c9c-3d1a-4fae-9999-35acab706abc
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
23b7149f-5270-4843-b96a-b5f603966e82	32e48d76-6004-4a1c-bb22-27ee66e2d672
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	df7e7468-87c6-4728-b792-e4058c15908d
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	c5611c9c-3d1a-4fae-9999-35acab706abc
a71d1fbe-e27d-417f-91c5-0ceae91ee8eb	32e48d76-6004-4a1c-bb22-27ee66e2d672
ac28b210-5fd5-41fe-bf29-8ef880fb5746	8310c913-dcfd-432e-b330-ee4bb33b7d3e
ac28b210-5fd5-41fe-bf29-8ef880fb5746	9b90fa62-a329-4089-b71f-f9a70c27ac03
ac28b210-5fd5-41fe-bf29-8ef880fb5746	74a8a447-3f16-43a9-b29b-84d2e5d86a88
ac28b210-5fd5-41fe-bf29-8ef880fb5746	e65a3a55-59ca-4533-bdf2-ba8511d594ad
ac28b210-5fd5-41fe-bf29-8ef880fb5746	32e48d76-6004-4a1c-bb22-27ee66e2d672
ac28b210-5fd5-41fe-bf29-8ef880fb5746	e43a754b-648f-460b-88c6-0d3825b1fa76
07e022d0-abbd-4e4b-9aea-816a448e8670	0784ce65-0cea-4000-858f-a5bc87d86e8f
07e022d0-abbd-4e4b-9aea-816a448e8670	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
07e022d0-abbd-4e4b-9aea-816a448e8670	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
07e022d0-abbd-4e4b-9aea-816a448e8670	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
07e022d0-abbd-4e4b-9aea-816a448e8670	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
07e022d0-abbd-4e4b-9aea-816a448e8670	e65a3a55-59ca-4533-bdf2-ba8511d594ad
07e022d0-abbd-4e4b-9aea-816a448e8670	2e103b9f-097b-4d2d-9ee9-8806713621ce
07e022d0-abbd-4e4b-9aea-816a448e8670	c497544b-bd79-4cf2-a63a-8cffacc1fe46
07e022d0-abbd-4e4b-9aea-816a448e8670	edcf6bf0-5a1a-41c6-ac00-56a63327adbb
07e022d0-abbd-4e4b-9aea-816a448e8670	32e48d76-6004-4a1c-bb22-27ee66e2d672
07e022d0-abbd-4e4b-9aea-816a448e8670	e43a754b-648f-460b-88c6-0d3825b1fa76
6680d0c0-aa3f-4c84-8e01-e965d53d7305	32e48d76-6004-4a1c-bb22-27ee66e2d672
be5bb5c2-3f21-4378-89a0-939fa7b18283	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	1e896415-6ebe-443f-b048-30725be095c3
be5bb5c2-3f21-4378-89a0-939fa7b18283	e738acf7-a61b-489a-a6f9-bda344038174
be5bb5c2-3f21-4378-89a0-939fa7b18283	7d48e39c-8f0c-4db0-bd68-3c4b83e2b8d1
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	05155d0d-89da-4c55-b92c-94a1387ec741
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	03019641-317d-4b74-adaa-1580ff4c99bc
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	af9e38a7-5fea-42ef-b6c1-cd56d0161988
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	3e4285d4-8e01-4483-9219-83079f9d3a77
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	1a931c05-4408-4eb6-afdc-905ebda9edc1
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	8638f31d-dd3a-41df-8571-bcb665c48554
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	47d63157-d363-4e90-bf84-c94e1f61c0ee
d5a4edd9-f22c-43d7-99aa-5a294aa3ec71	32e48d76-6004-4a1c-bb22-27ee66e2d672
64be1812-8e86-42a4-8914-5bbe53783977	0784ce65-0cea-4000-858f-a5bc87d86e8f
64be1812-8e86-42a4-8914-5bbe53783977	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
64be1812-8e86-42a4-8914-5bbe53783977	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
64be1812-8e86-42a4-8914-5bbe53783977	5926aed9-9d4d-42b1-9fa8-fd82d5706412
64be1812-8e86-42a4-8914-5bbe53783977	1c7340c1-7b05-404f-8882-39906697c69c
64be1812-8e86-42a4-8914-5bbe53783977	2e103b9f-097b-4d2d-9ee9-8806713621ce
64be1812-8e86-42a4-8914-5bbe53783977	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d
64be1812-8e86-42a4-8914-5bbe53783977	32e48d76-6004-4a1c-bb22-27ee66e2d672
64be1812-8e86-42a4-8914-5bbe53783977	e43a754b-648f-460b-88c6-0d3825b1fa76
0bafc06b-3d60-4863-9ba2-59cc57265397	0784ce65-0cea-4000-858f-a5bc87d86e8f
0bafc06b-3d60-4863-9ba2-59cc57265397	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
0bafc06b-3d60-4863-9ba2-59cc57265397	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
0bafc06b-3d60-4863-9ba2-59cc57265397	e9d67836-a72d-4802-a9f0-25a15e1042e5
0bafc06b-3d60-4863-9ba2-59cc57265397	1c7340c1-7b05-404f-8882-39906697c69c
0bafc06b-3d60-4863-9ba2-59cc57265397	2e103b9f-097b-4d2d-9ee9-8806713621ce
0bafc06b-3d60-4863-9ba2-59cc57265397	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d
0bafc06b-3d60-4863-9ba2-59cc57265397	32e48d76-6004-4a1c-bb22-27ee66e2d672
0bafc06b-3d60-4863-9ba2-59cc57265397	e43a754b-648f-460b-88c6-0d3825b1fa76
83286727-29f0-4c6b-8fc7-6bb8c9e2c745	32e48d76-6004-4a1c-bb22-27ee66e2d672
2e095c7c-f9a2-4a88-9780-98262fae50b3	0784ce65-0cea-4000-858f-a5bc87d86e8f
2e095c7c-f9a2-4a88-9780-98262fae50b3	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
2e095c7c-f9a2-4a88-9780-98262fae50b3	8c6942ac-e020-4a00-acc4-8c41409854ab
2e095c7c-f9a2-4a88-9780-98262fae50b3	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
2e095c7c-f9a2-4a88-9780-98262fae50b3	9b90fa62-a329-4089-b71f-f9a70c27ac03
2e095c7c-f9a2-4a88-9780-98262fae50b3	74a8a447-3f16-43a9-b29b-84d2e5d86a88
2e095c7c-f9a2-4a88-9780-98262fae50b3	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
2e095c7c-f9a2-4a88-9780-98262fae50b3	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
2e095c7c-f9a2-4a88-9780-98262fae50b3	2e103b9f-097b-4d2d-9ee9-8806713621ce
2e095c7c-f9a2-4a88-9780-98262fae50b3	32e48d76-6004-4a1c-bb22-27ee66e2d672
2e095c7c-f9a2-4a88-9780-98262fae50b3	e43a754b-648f-460b-88c6-0d3825b1fa76
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	0784ce65-0cea-4000-858f-a5bc87d86e8f
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	e9d67836-a72d-4802-a9f0-25a15e1042e5
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	1c7340c1-7b05-404f-8882-39906697c69c
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	2e103b9f-097b-4d2d-9ee9-8806713621ce
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	32e48d76-6004-4a1c-bb22-27ee66e2d672
d5e90e46-e150-4d4d-b73a-caadcfeffb5e	e43a754b-648f-460b-88c6-0d3825b1fa76
06c01acf-ab79-4bd2-bab1-7ca09640ad14	5e445afa-73c0-4500-a0d8-697acd7bf58e
06c01acf-ab79-4bd2-bab1-7ca09640ad14	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
06c01acf-ab79-4bd2-bab1-7ca09640ad14	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
06c01acf-ab79-4bd2-bab1-7ca09640ad14	c0aa58be-47d7-4f72-b017-9125856035e2
06c01acf-ab79-4bd2-bab1-7ca09640ad14	7d1eb5d5-dd84-4821-836b-ef6a694965f2
06c01acf-ab79-4bd2-bab1-7ca09640ad14	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
06c01acf-ab79-4bd2-bab1-7ca09640ad14	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
06c01acf-ab79-4bd2-bab1-7ca09640ad14	97e67f12-4879-4dc6-8759-e205bba8b0f9
06c01acf-ab79-4bd2-bab1-7ca09640ad14	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
06c01acf-ab79-4bd2-bab1-7ca09640ad14	8d5e74b8-7910-4ee3-b424-3697b919da65
06c01acf-ab79-4bd2-bab1-7ca09640ad14	fb46ae33-946a-46d4-a4f2-29df8b040cff
06c01acf-ab79-4bd2-bab1-7ca09640ad14	803f1d5e-f530-4983-b815-68238a066cbf
06c01acf-ab79-4bd2-bab1-7ca09640ad14	e211f36c-60a5-47c7-9971-f16df7bf07fc
06c01acf-ab79-4bd2-bab1-7ca09640ad14	32e48d76-6004-4a1c-bb22-27ee66e2d672
d8da5d79-1b30-4adb-a2ab-614e529265a5	0784ce65-0cea-4000-858f-a5bc87d86e8f
d8da5d79-1b30-4adb-a2ab-614e529265a5	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
d8da5d79-1b30-4adb-a2ab-614e529265a5	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
d8da5d79-1b30-4adb-a2ab-614e529265a5	e65a3a55-59ca-4533-bdf2-ba8511d594ad
d8da5d79-1b30-4adb-a2ab-614e529265a5	2e103b9f-097b-4d2d-9ee9-8806713621ce
d8da5d79-1b30-4adb-a2ab-614e529265a5	c497544b-bd79-4cf2-a63a-8cffacc1fe46
d8da5d79-1b30-4adb-a2ab-614e529265a5	edcf6bf0-5a1a-41c6-ac00-56a63327adbb
d8da5d79-1b30-4adb-a2ab-614e529265a5	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d
d8da5d79-1b30-4adb-a2ab-614e529265a5	32e48d76-6004-4a1c-bb22-27ee66e2d672
d8da5d79-1b30-4adb-a2ab-614e529265a5	e43a754b-648f-460b-88c6-0d3825b1fa76
872619c3-c679-4ef1-99b6-b064cf37ad86	99e29aa2-9c26-4d09-9206-6173b7956d7d
872619c3-c679-4ef1-99b6-b064cf37ad86	6c6074b4-62fc-4312-91e6-2e8a9196aca7
872619c3-c679-4ef1-99b6-b064cf37ad86	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
872619c3-c679-4ef1-99b6-b064cf37ad86	b0956325-1cdb-425f-a9a6-afb752a5485d
872619c3-c679-4ef1-99b6-b064cf37ad86	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
872619c3-c679-4ef1-99b6-b064cf37ad86	8c6942ac-e020-4a00-acc4-8c41409854ab
872619c3-c679-4ef1-99b6-b064cf37ad86	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
872619c3-c679-4ef1-99b6-b064cf37ad86	9b90fa62-a329-4089-b71f-f9a70c27ac03
872619c3-c679-4ef1-99b6-b064cf37ad86	74a8a447-3f16-43a9-b29b-84d2e5d86a88
872619c3-c679-4ef1-99b6-b064cf37ad86	32e48d76-6004-4a1c-bb22-27ee66e2d672
872619c3-c679-4ef1-99b6-b064cf37ad86	e43a754b-648f-460b-88c6-0d3825b1fa76
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	0784ce65-0cea-4000-858f-a5bc87d86e8f
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	5926aed9-9d4d-42b1-9fa8-fd82d5706412
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	1c7340c1-7b05-404f-8882-39906697c69c
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	2e103b9f-097b-4d2d-9ee9-8806713621ce
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	32e48d76-6004-4a1c-bb22-27ee66e2d672
cdf0c5c0-8cff-492e-99a7-7c78a19d8b5c	e43a754b-648f-460b-88c6-0d3825b1fa76
4fffa586-a10d-4b32-bc19-731fc34c5e9f	0784ce65-0cea-4000-858f-a5bc87d86e8f
4fffa586-a10d-4b32-bc19-731fc34c5e9f	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
4fffa586-a10d-4b32-bc19-731fc34c5e9f	8c6942ac-e020-4a00-acc4-8c41409854ab
4fffa586-a10d-4b32-bc19-731fc34c5e9f	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
4fffa586-a10d-4b32-bc19-731fc34c5e9f	9b90fa62-a329-4089-b71f-f9a70c27ac03
4fffa586-a10d-4b32-bc19-731fc34c5e9f	74a8a447-3f16-43a9-b29b-84d2e5d86a88
4fffa586-a10d-4b32-bc19-731fc34c5e9f	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
4fffa586-a10d-4b32-bc19-731fc34c5e9f	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
4fffa586-a10d-4b32-bc19-731fc34c5e9f	2e103b9f-097b-4d2d-9ee9-8806713621ce
4fffa586-a10d-4b32-bc19-731fc34c5e9f	32e48d76-6004-4a1c-bb22-27ee66e2d672
4fffa586-a10d-4b32-bc19-731fc34c5e9f	e43a754b-648f-460b-88c6-0d3825b1fa76
be5bb5c2-3f21-4378-89a0-939fa7b18283	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
be5bb5c2-3f21-4378-89a0-939fa7b18283	77f1a128-ea8f-466e-8c0d-998c2280c811
be5bb5c2-3f21-4378-89a0-939fa7b18283	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
be5bb5c2-3f21-4378-89a0-939fa7b18283	47d63157-d363-4e90-bf84-c94e1f61c0ee
be5bb5c2-3f21-4378-89a0-939fa7b18283	32e48d76-6004-4a1c-bb22-27ee66e2d672
be5bb5c2-3f21-4378-89a0-939fa7b18283	8638f31d-dd3a-41df-8571-bcb665c48554
be5bb5c2-3f21-4378-89a0-939fa7b18283	a9ca51e9-864d-4d17-b647-ff17ea2c02e6
b83eebe3-cce3-42c1-a149-8e2f728fa398	97e67f12-4879-4dc6-8759-e205bba8b0f9
b83eebe3-cce3-42c1-a149-8e2f728fa398	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
b83eebe3-cce3-42c1-a149-8e2f728fa398	8d5e74b8-7910-4ee3-b424-3697b919da65
b83eebe3-cce3-42c1-a149-8e2f728fa398	fb46ae33-946a-46d4-a4f2-29df8b040cff
b83eebe3-cce3-42c1-a149-8e2f728fa398	803f1d5e-f530-4983-b815-68238a066cbf
b83eebe3-cce3-42c1-a149-8e2f728fa398	e211f36c-60a5-47c7-9971-f16df7bf07fc
b83eebe3-cce3-42c1-a149-8e2f728fa398	3d6d21de-bf1b-4fbb-aed0-ee57caa00988
b83eebe3-cce3-42c1-a149-8e2f728fa398	6b1271eb-54e6-49bd-ab27-c85605ec8576
b83eebe3-cce3-42c1-a149-8e2f728fa398	e278ea08-a02d-4de2-ab78-d34b856c87d7
b83eebe3-cce3-42c1-a149-8e2f728fa398	32e48d76-6004-4a1c-bb22-27ee66e2d672
b83eebe3-cce3-42c1-a149-8e2f728fa398	e43a754b-648f-460b-88c6-0d3825b1fa76
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	78301a7f-a1d9-494b-8c10-8b11baa5976e
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	8d5e74b8-7910-4ee3-b424-3697b919da65
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	fb46ae33-946a-46d4-a4f2-29df8b040cff
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	3d6d21de-bf1b-4fbb-aed0-ee57caa00988
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	6b1271eb-54e6-49bd-ab27-c85605ec8576
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	e278ea08-a02d-4de2-ab78-d34b856c87d7
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	7e7a157e-f4fe-49f3-9876-5f6dff88ad3a
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	32e48d76-6004-4a1c-bb22-27ee66e2d672
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	e43a754b-648f-460b-88c6-0d3825b1fa76
9d81ec67-308e-4cb8-a1d9-c0f33d11f071	97e67f12-4879-4dc6-8759-e205bba8b0f9
a6cdd6e7-2239-4bf7-a152-82ff8dafee14	32e48d76-6004-4a1c-bb22-27ee66e2d672
a1a765bd-237e-4333-9329-241488469fed	3d6d21de-bf1b-4fbb-aed0-ee57caa00988
a1a765bd-237e-4333-9329-241488469fed	6b1271eb-54e6-49bd-ab27-c85605ec8576
a1a765bd-237e-4333-9329-241488469fed	9ab1875d-9ba9-4f1f-8f12-7def153646d1
a1a765bd-237e-4333-9329-241488469fed	e278ea08-a02d-4de2-ab78-d34b856c87d7
a1a765bd-237e-4333-9329-241488469fed	7e7a157e-f4fe-49f3-9876-5f6dff88ad3a
a1a765bd-237e-4333-9329-241488469fed	a80a1879-1353-406e-8d2b-74da7a59c64a
a1a765bd-237e-4333-9329-241488469fed	32e48d76-6004-4a1c-bb22-27ee66e2d672
a1a765bd-237e-4333-9329-241488469fed	e43a754b-648f-460b-88c6-0d3825b1fa76
a6b369d4-2128-4697-8a18-d4dcdd880ad8	5e445afa-73c0-4500-a0d8-697acd7bf58e
a6b369d4-2128-4697-8a18-d4dcdd880ad8	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
a6b369d4-2128-4697-8a18-d4dcdd880ad8	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
a6b369d4-2128-4697-8a18-d4dcdd880ad8	c0aa58be-47d7-4f72-b017-9125856035e2
a6b369d4-2128-4697-8a18-d4dcdd880ad8	7d1eb5d5-dd84-4821-836b-ef6a694965f2
a6b369d4-2128-4697-8a18-d4dcdd880ad8	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
a6b369d4-2128-4697-8a18-d4dcdd880ad8	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
a6b369d4-2128-4697-8a18-d4dcdd880ad8	78301a7f-a1d9-494b-8c10-8b11baa5976e
a6b369d4-2128-4697-8a18-d4dcdd880ad8	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
a6b369d4-2128-4697-8a18-d4dcdd880ad8	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
a6b369d4-2128-4697-8a18-d4dcdd880ad8	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
a6b369d4-2128-4697-8a18-d4dcdd880ad8	8d5e74b8-7910-4ee3-b424-3697b919da65
a6b369d4-2128-4697-8a18-d4dcdd880ad8	fb46ae33-946a-46d4-a4f2-29df8b040cff
a6b369d4-2128-4697-8a18-d4dcdd880ad8	32e48d76-6004-4a1c-bb22-27ee66e2d672
a6b369d4-2128-4697-8a18-d4dcdd880ad8	97e67f12-4879-4dc6-8759-e205bba8b0f9
e9cafca2-ba61-43ab-aba3-06e9fee69d29	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	5e445afa-73c0-4500-a0d8-697acd7bf58e
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	78301a7f-a1d9-494b-8c10-8b11baa5976e
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	8d5e74b8-7910-4ee3-b424-3697b919da65
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	fb46ae33-946a-46d4-a4f2-29df8b040cff
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	5d617722-8aeb-4e89-b859-01d3ec382190
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	555bdc44-a24e-48fc-aa5e-892e666781a5
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	9db2d190-6068-4c34-9e5c-36e9994274aa
eed93bef-e2df-4cbb-acc8-4ba3f87bcc26	32e48d76-6004-4a1c-bb22-27ee66e2d672
0d619f97-5750-4faa-bfe5-07b744b09e23	5e445afa-73c0-4500-a0d8-697acd7bf58e
0d619f97-5750-4faa-bfe5-07b744b09e23	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
b083fec4-6264-407a-be55-461f8da6d7c5	5e445afa-73c0-4500-a0d8-697acd7bf58e
e9cafca2-ba61-43ab-aba3-06e9fee69d29	5e445afa-73c0-4500-a0d8-697acd7bf58e
b083fec4-6264-407a-be55-461f8da6d7c5	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
0d619f97-5750-4faa-bfe5-07b744b09e23	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
0d619f97-5750-4faa-bfe5-07b744b09e23	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
b083fec4-6264-407a-be55-461f8da6d7c5	77f1a128-ea8f-466e-8c0d-998c2280c811
e9cafca2-ba61-43ab-aba3-06e9fee69d29	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
e9cafca2-ba61-43ab-aba3-06e9fee69d29	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
e9cafca2-ba61-43ab-aba3-06e9fee69d29	8d5e74b8-7910-4ee3-b424-3697b919da65
b083fec4-6264-407a-be55-461f8da6d7c5	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
b083fec4-6264-407a-be55-461f8da6d7c5	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
b083fec4-6264-407a-be55-461f8da6d7c5	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
e9cafca2-ba61-43ab-aba3-06e9fee69d29	fb46ae33-946a-46d4-a4f2-29df8b040cff
e9cafca2-ba61-43ab-aba3-06e9fee69d29	555bdc44-a24e-48fc-aa5e-892e666781a5
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	5e445afa-73c0-4500-a0d8-697acd7bf58e
0d619f97-5750-4faa-bfe5-07b744b09e23	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
e9cafca2-ba61-43ab-aba3-06e9fee69d29	9db2d190-6068-4c34-9e5c-36e9994274aa
b083fec4-6264-407a-be55-461f8da6d7c5	8d5e74b8-7910-4ee3-b424-3697b919da65
e9cafca2-ba61-43ab-aba3-06e9fee69d29	64e7e3c8-c369-4913-be67-a64c7ea29d11
b083fec4-6264-407a-be55-461f8da6d7c5	fb46ae33-946a-46d4-a4f2-29df8b040cff
e9cafca2-ba61-43ab-aba3-06e9fee69d29	32e48d76-6004-4a1c-bb22-27ee66e2d672
b083fec4-6264-407a-be55-461f8da6d7c5	555bdc44-a24e-48fc-aa5e-892e666781a5
b083fec4-6264-407a-be55-461f8da6d7c5	9db2d190-6068-4c34-9e5c-36e9994274aa
b083fec4-6264-407a-be55-461f8da6d7c5	04497442-8dc0-45b3-93c8-a59832f0f297
b083fec4-6264-407a-be55-461f8da6d7c5	32e48d76-6004-4a1c-bb22-27ee66e2d672
b083fec4-6264-407a-be55-461f8da6d7c5	97e67f12-4879-4dc6-8759-e205bba8b0f9
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
0d619f97-5750-4faa-bfe5-07b744b09e23	8d5e74b8-7910-4ee3-b424-3697b919da65
0d619f97-5750-4faa-bfe5-07b744b09e23	fb46ae33-946a-46d4-a4f2-29df8b040cff
db0beade-4024-49c3-a961-7ec9649b98af	5e445afa-73c0-4500-a0d8-697acd7bf58e
0d619f97-5750-4faa-bfe5-07b744b09e23	555bdc44-a24e-48fc-aa5e-892e666781a5
db0beade-4024-49c3-a961-7ec9649b98af	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
0d619f97-5750-4faa-bfe5-07b744b09e23	9db2d190-6068-4c34-9e5c-36e9994274aa
db0beade-4024-49c3-a961-7ec9649b98af	8d5e74b8-7910-4ee3-b424-3697b919da65
0d619f97-5750-4faa-bfe5-07b744b09e23	32e48d76-6004-4a1c-bb22-27ee66e2d672
db0beade-4024-49c3-a961-7ec9649b98af	fb46ae33-946a-46d4-a4f2-29df8b040cff
db0beade-4024-49c3-a961-7ec9649b98af	bf8268d8-5e12-4137-9407-be7988f0eeba
db0beade-4024-49c3-a961-7ec9649b98af	cea04306-6f20-431d-9904-c58271cf04f7
db0beade-4024-49c3-a961-7ec9649b98af	555bdc44-a24e-48fc-aa5e-892e666781a5
db0beade-4024-49c3-a961-7ec9649b98af	9db2d190-6068-4c34-9e5c-36e9994274aa
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	8d5e74b8-7910-4ee3-b424-3697b919da65
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	fb46ae33-946a-46d4-a4f2-29df8b040cff
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	5e445afa-73c0-4500-a0d8-697acd7bf58e
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	555bdc44-a24e-48fc-aa5e-892e666781a5
db0beade-4024-49c3-a961-7ec9649b98af	64e7e3c8-c369-4913-be67-a64c7ea29d11
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
db0beade-4024-49c3-a961-7ec9649b98af	04497442-8dc0-45b3-93c8-a59832f0f297
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	77f1a128-ea8f-466e-8c0d-998c2280c811
20282bd2-3196-4b30-99c7-6e59af3df733	5e445afa-73c0-4500-a0d8-697acd7bf58e
20282bd2-3196-4b30-99c7-6e59af3df733	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
20282bd2-3196-4b30-99c7-6e59af3df733	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
20282bd2-3196-4b30-99c7-6e59af3df733	8d5e74b8-7910-4ee3-b424-3697b919da65
db0beade-4024-49c3-a961-7ec9649b98af	32e48d76-6004-4a1c-bb22-27ee66e2d672
20282bd2-3196-4b30-99c7-6e59af3df733	fb46ae33-946a-46d4-a4f2-29df8b040cff
20282bd2-3196-4b30-99c7-6e59af3df733	bf8268d8-5e12-4137-9407-be7988f0eeba
20282bd2-3196-4b30-99c7-6e59af3df733	cea04306-6f20-431d-9904-c58271cf04f7
db0beade-4024-49c3-a961-7ec9649b98af	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
0af2fb78-e37f-4e4c-8457-c0296388ecf1	c98823ee-d087-4a9e-b518-2b28faaccd5f
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	9db2d190-6068-4c34-9e5c-36e9994274aa
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	64e7e3c8-c369-4913-be67-a64c7ea29d11
20282bd2-3196-4b30-99c7-6e59af3df733	555bdc44-a24e-48fc-aa5e-892e666781a5
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	04497442-8dc0-45b3-93c8-a59832f0f297
db0beade-4024-49c3-a961-7ec9649b98af	97e67f12-4879-4dc6-8759-e205bba8b0f9
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	32e48d76-6004-4a1c-bb22-27ee66e2d672
f62419ff-a568-4e8a-ae9f-4a17e9fd97c6	97e67f12-4879-4dc6-8759-e205bba8b0f9
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	279b0444-5ebc-42ce-a01a-d2e4d39b9874
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	d1e3dcb1-fa7c-4355-b42f-90c8378679cb
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	24825786-1517-44ed-a97a-843f2ab2e5cf
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	9f611bd8-64ed-4cad-9d07-a8fc6184104d
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	6eeec8d4-efd9-4d38-8643-4ac432801489
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	e63a3e7d-406b-4907-b08d-9256af86a2a6
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	6f115cf8-f0f9-4cce-84ef-5e4f5bb11ba5
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	bc4e93ce-352f-493d-9089-c72c1eeaf8e4
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	653df30a-8013-4f51-83dd-4d94e429b18d
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	77f1a128-ea8f-466e-8c0d-998c2280c811
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	c28a9113-da92-43ba-8123-741f67dc4b89
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	0b2159c6-a703-43bb-b6a1-5392153e01d7
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	b3bd0f0e-83df-4b31-8f33-815e6853a3a9
0b5f828b-dd41-4d4e-aca0-5bcb5908e142	32e48d76-6004-4a1c-bb22-27ee66e2d672
3650ee8e-e074-4d08-89ed-38e9c8ea64e5	0784ce65-0cea-4000-858f-a5bc87d86e8f
3650ee8e-e074-4d08-89ed-38e9c8ea64e5	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
3650ee8e-e074-4d08-89ed-38e9c8ea64e5	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
3650ee8e-e074-4d08-89ed-38e9c8ea64e5	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
3650ee8e-e074-4d08-89ed-38e9c8ea64e5	9b90fa62-a329-4089-b71f-f9a70c27ac03
3650ee8e-e074-4d08-89ed-38e9c8ea64e5	74a8a447-3f16-43a9-b29b-84d2e5d86a88
3650ee8e-e074-4d08-89ed-38e9c8ea64e5	5fa57fdf-7ba0-4063-93cf-378d7b8267a9
3650ee8e-e074-4d08-89ed-38e9c8ea64e5	32e48d76-6004-4a1c-bb22-27ee66e2d672
3650ee8e-e074-4d08-89ed-38e9c8ea64e5	e43a754b-648f-460b-88c6-0d3825b1fa76
fd79c747-918c-417d-b24a-f0a841ae87e0	0784ce65-0cea-4000-858f-a5bc87d86e8f
fd79c747-918c-417d-b24a-f0a841ae87e0	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
fd79c747-918c-417d-b24a-f0a841ae87e0	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
fd79c747-918c-417d-b24a-f0a841ae87e0	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
fd79c747-918c-417d-b24a-f0a841ae87e0	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
fd79c747-918c-417d-b24a-f0a841ae87e0	2e103b9f-097b-4d2d-9ee9-8806713621ce
fd79c747-918c-417d-b24a-f0a841ae87e0	885428c2-75f8-47f6-80a6-94337b9fb10f
fd79c747-918c-417d-b24a-f0a841ae87e0	84a6556d-48f6-4fec-8468-7c6e9dd9c5ab
fd79c747-918c-417d-b24a-f0a841ae87e0	32e48d76-6004-4a1c-bb22-27ee66e2d672
fd79c747-918c-417d-b24a-f0a841ae87e0	e43a754b-648f-460b-88c6-0d3825b1fa76
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	8d5e74b8-7910-4ee3-b424-3697b919da65
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	fb46ae33-946a-46d4-a4f2-29df8b040cff
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	555bdc44-a24e-48fc-aa5e-892e666781a5
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	9db2d190-6068-4c34-9e5c-36e9994274aa
47c13ad4-b51d-468a-afb0-f92e4c18cf0b	32e48d76-6004-4a1c-bb22-27ee66e2d672
9e8d45f8-5c13-402f-bac3-d07f5e554e4e	0784ce65-0cea-4000-858f-a5bc87d86e8f
9e8d45f8-5c13-402f-bac3-d07f5e554e4e	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
9e8d45f8-5c13-402f-bac3-d07f5e554e4e	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
9e8d45f8-5c13-402f-bac3-d07f5e554e4e	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
9e8d45f8-5c13-402f-bac3-d07f5e554e4e	9b90fa62-a329-4089-b71f-f9a70c27ac03
9e8d45f8-5c13-402f-bac3-d07f5e554e4e	74a8a447-3f16-43a9-b29b-84d2e5d86a88
9e8d45f8-5c13-402f-bac3-d07f5e554e4e	5926aed9-9d4d-42b1-9fa8-fd82d5706412
9e8d45f8-5c13-402f-bac3-d07f5e554e4e	32e48d76-6004-4a1c-bb22-27ee66e2d672
9e8d45f8-5c13-402f-bac3-d07f5e554e4e	e43a754b-648f-460b-88c6-0d3825b1fa76
0af2fb78-e37f-4e4c-8457-c0296388ecf1	1e896415-6ebe-443f-b048-30725be095c3
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	1e896415-6ebe-443f-b048-30725be095c3
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	05155d0d-89da-4c55-b92c-94a1387ec741
0af2fb78-e37f-4e4c-8457-c0296388ecf1	05155d0d-89da-4c55-b92c-94a1387ec741
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	8638f31d-dd3a-41df-8571-bcb665c48554
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	47d63157-d363-4e90-bf84-c94e1f61c0ee
0af2fb78-e37f-4e4c-8457-c0296388ecf1	47d63157-d363-4e90-bf84-c94e1f61c0ee
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
0af2fb78-e37f-4e4c-8457-c0296388ecf1	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	df7e7468-87c6-4728-b792-e4058c15908d
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	c5611c9c-3d1a-4fae-9999-35acab706abc
0af2fb78-e37f-4e4c-8457-c0296388ecf1	df7e7468-87c6-4728-b792-e4058c15908d
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	ee549e95-aa66-4707-b957-26b96b35d068
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	04a38028-135f-4e88-9cfc-12066c8f1df3
0af2fb78-e37f-4e4c-8457-c0296388ecf1	c5611c9c-3d1a-4fae-9999-35acab706abc
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	26654263-ccb2-4fad-8ecc-342da43c3928
0af2fb78-e37f-4e4c-8457-c0296388ecf1	8638f31d-dd3a-41df-8571-bcb665c48554
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	f6a2c559-1f7d-4f45-adf1-8c76b2faf147
0af2fb78-e37f-4e4c-8457-c0296388ecf1	ee549e95-aa66-4707-b957-26b96b35d068
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	cd5f8554-fec4-4862-80c3-caff5df01ad5
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	2f39f7da-a37f-4ccb-8a80-61b108cc7800
0af2fb78-e37f-4e4c-8457-c0296388ecf1	04a38028-135f-4e88-9cfc-12066c8f1df3
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	99a22674-5148-4e0e-85c4-409449b8c7e3
0af2fb78-e37f-4e4c-8457-c0296388ecf1	26654263-ccb2-4fad-8ecc-342da43c3928
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	a8b61e71-3df5-41bb-9d40-d0fad75e7fbf
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	7532d5c1-8ffa-4806-9c04-86e7fdccd542
0af2fb78-e37f-4e4c-8457-c0296388ecf1	f6a2c559-1f7d-4f45-adf1-8c76b2faf147
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	86c4b224-039c-49c4-8bbf-1d2c0d923fc6
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	fa24d9c0-130f-499c-ba42-217e905faef6
0af2fb78-e37f-4e4c-8457-c0296388ecf1	cd5f8554-fec4-4862-80c3-caff5df01ad5
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	cafaf289-db67-424b-91fa-fa16a5270eda
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	32e48d76-6004-4a1c-bb22-27ee66e2d672
0af2fb78-e37f-4e4c-8457-c0296388ecf1	cfba2139-68f1-4892-8b8e-607fad765801
0e4cd116-e696-4e2c-85c6-7dc92713e2e7	e64236c3-e2ea-45a1-8a28-c9c596267c97
0af2fb78-e37f-4e4c-8457-c0296388ecf1	32e48d76-6004-4a1c-bb22-27ee66e2d672
8cefe0bd-e356-4670-b4b4-3aba43b33a84	279b0444-5ebc-42ce-a01a-d2e4d39b9874
8cefe0bd-e356-4670-b4b4-3aba43b33a84	d1e3dcb1-fa7c-4355-b42f-90c8378679cb
8cefe0bd-e356-4670-b4b4-3aba43b33a84	24825786-1517-44ed-a97a-843f2ab2e5cf
8cefe0bd-e356-4670-b4b4-3aba43b33a84	a7fd852d-5a48-40c2-9715-afeee84a39e0
8cefe0bd-e356-4670-b4b4-3aba43b33a84	77f1a128-ea8f-466e-8c0d-998c2280c811
8cefe0bd-e356-4670-b4b4-3aba43b33a84	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
8cefe0bd-e356-4670-b4b4-3aba43b33a84	78301a7f-a1d9-494b-8c10-8b11baa5976e
8cefe0bd-e356-4670-b4b4-3aba43b33a84	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
8cefe0bd-e356-4670-b4b4-3aba43b33a84	50402a5d-cc3a-41af-a544-46dfb70ef381
8cefe0bd-e356-4670-b4b4-3aba43b33a84	f68c63a1-4807-47c5-a6d0-063630b700f6
8cefe0bd-e356-4670-b4b4-3aba43b33a84	32e48d76-6004-4a1c-bb22-27ee66e2d672
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	99e29aa2-9c26-4d09-9206-6173b7956d7d
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	6c6074b4-62fc-4312-91e6-2e8a9196aca7
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	0784ce65-0cea-4000-858f-a5bc87d86e8f
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	9b90fa62-a329-4089-b71f-f9a70c27ac03
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	74a8a447-3f16-43a9-b29b-84d2e5d86a88
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	32e48d76-6004-4a1c-bb22-27ee66e2d672
7b1ab435-3da4-4af7-acaa-8d5c2ded5643	e43a754b-648f-460b-88c6-0d3825b1fa76
20282bd2-3196-4b30-99c7-6e59af3df733	9db2d190-6068-4c34-9e5c-36e9994274aa
20282bd2-3196-4b30-99c7-6e59af3df733	64e7e3c8-c369-4913-be67-a64c7ea29d11
20282bd2-3196-4b30-99c7-6e59af3df733	32e48d76-6004-4a1c-bb22-27ee66e2d672
075c9947-638e-4ad8-ae2b-c6183f9c79cb	32e48d76-6004-4a1c-bb22-27ee66e2d672
7046e1f1-cd1a-411a-af01-96707541205d	04497442-8dc0-45b3-93c8-a59832f0f297
0cc16c71-33e7-453e-9de9-62c266347af2	a6c524c4-6748-414d-99dc-443680f2ba63
7046e1f1-cd1a-411a-af01-96707541205d	a6c524c4-6748-414d-99dc-443680f2ba63
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	5e445afa-73c0-4500-a0d8-697acd7bf58e
0cc16c71-33e7-453e-9de9-62c266347af2	32e48d76-6004-4a1c-bb22-27ee66e2d672
7046e1f1-cd1a-411a-af01-96707541205d	32e48d76-6004-4a1c-bb22-27ee66e2d672
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
0cc16c71-33e7-453e-9de9-62c266347af2	e43a754b-648f-460b-88c6-0d3825b1fa76
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	78301a7f-a1d9-494b-8c10-8b11baa5976e
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	8d5e74b8-7910-4ee3-b424-3697b919da65
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	fb46ae33-946a-46d4-a4f2-29df8b040cff
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	5d617722-8aeb-4e89-b859-01d3ec382190
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	555bdc44-a24e-48fc-aa5e-892e666781a5
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	9db2d190-6068-4c34-9e5c-36e9994274aa
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	04497442-8dc0-45b3-93c8-a59832f0f297
7046e1f1-cd1a-411a-af01-96707541205d	e43a754b-648f-460b-88c6-0d3825b1fa76
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	32e48d76-6004-4a1c-bb22-27ee66e2d672
05fdc1aa-f0f7-4464-aa6a-76798cc0d131	97e67f12-4879-4dc6-8759-e205bba8b0f9
7046e1f1-cd1a-411a-af01-96707541205d	97e67f12-4879-4dc6-8759-e205bba8b0f9
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	5e445afa-73c0-4500-a0d8-697acd7bf58e
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	78301a7f-a1d9-494b-8c10-8b11baa5976e
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	8d5e74b8-7910-4ee3-b424-3697b919da65
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	fb46ae33-946a-46d4-a4f2-29df8b040cff
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	555bdc44-a24e-48fc-aa5e-892e666781a5
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	9db2d190-6068-4c34-9e5c-36e9994274aa
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	04497442-8dc0-45b3-93c8-a59832f0f297
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	32e48d76-6004-4a1c-bb22-27ee66e2d672
1576dd96-30da-40a1-9ae7-e4fcbbe3a2e4	97e67f12-4879-4dc6-8759-e205bba8b0f9
7ed5f07c-e2a6-411f-875f-6ebd9a75d0ca	0784ce65-0cea-4000-858f-a5bc87d86e8f
7ed5f07c-e2a6-411f-875f-6ebd9a75d0ca	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
7ed5f07c-e2a6-411f-875f-6ebd9a75d0ca	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
7ed5f07c-e2a6-411f-875f-6ebd9a75d0ca	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
7ed5f07c-e2a6-411f-875f-6ebd9a75d0ca	9b90fa62-a329-4089-b71f-f9a70c27ac03
7ed5f07c-e2a6-411f-875f-6ebd9a75d0ca	74a8a447-3f16-43a9-b29b-84d2e5d86a88
7ed5f07c-e2a6-411f-875f-6ebd9a75d0ca	5926aed9-9d4d-42b1-9fa8-fd82d5706412
7ed5f07c-e2a6-411f-875f-6ebd9a75d0ca	32e48d76-6004-4a1c-bb22-27ee66e2d672
7ed5f07c-e2a6-411f-875f-6ebd9a75d0ca	e43a754b-648f-460b-88c6-0d3825b1fa76
947f98eb-13bd-4571-bd91-4d41a46e28de	32e48d76-6004-4a1c-bb22-27ee66e2d672
499f9d17-42c3-4053-a9a5-c52bc8ca6893	0784ce65-0cea-4000-858f-a5bc87d86e8f
499f9d17-42c3-4053-a9a5-c52bc8ca6893	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
499f9d17-42c3-4053-a9a5-c52bc8ca6893	9b90fa62-a329-4089-b71f-f9a70c27ac03
499f9d17-42c3-4053-a9a5-c52bc8ca6893	74a8a447-3f16-43a9-b29b-84d2e5d86a88
499f9d17-42c3-4053-a9a5-c52bc8ca6893	5fdcd179-a83a-4080-a33c-905e3db55dd1
499f9d17-42c3-4053-a9a5-c52bc8ca6893	e1482fe4-3872-4f46-a51b-9a648b9a5072
499f9d17-42c3-4053-a9a5-c52bc8ca6893	5fa57fdf-7ba0-4063-93cf-378d7b8267a9
499f9d17-42c3-4053-a9a5-c52bc8ca6893	32e48d76-6004-4a1c-bb22-27ee66e2d672
499f9d17-42c3-4053-a9a5-c52bc8ca6893	e43a754b-648f-460b-88c6-0d3825b1fa76
\.


--
-- Data for Name: employee_types; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.employee_types (id, name) FROM stdin;
10b41872-7e2b-4bcc-8187-5f279aa71be1	Dozierende
4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	Wissenschaftliches Personal
65a03f1b-40b5-40f3-919c-41ef315eafcc	Lehrbeauftragte(r)
\.


--
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.employees (id, abbreviation, firstname, lastname, fk_employee_type_id, fk_timetable_id) FROM stdin;
54b44371-4415-4b45-abd7-63b86369973c	Ahr	Dirk	Ahrens	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b8ed5474-d7e0-4144-b11d-3cf63cf9440d	afi	Alexander	Fischer	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c78f81ae-bc4e-44f8-9e4b-63a16362ff01	aha	Andreas	Haase	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
05cb8a03-d290-4afb-befb-dac6f0e50004	ann	Hendrik	Annuth	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c64363a0-0a95-46c9-a914-7ec718f016cf	awo	Atilla	Wohllebe	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
23f96a10-3b56-4dba-a5a1-bc8e4c071869	ba	Frank	Bargel	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f270d266-40ec-4e7d-87c2-a39d000c2a0b	bo	Christian-Arved	Bohn	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
13067e0f-5d3e-4828-bab5-4a863408902a	Ahr	Dirk	Ahrens	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
ce5137bc-0f9e-49a1-9e27-0aec555f069d	cbu	Carsten	Burmeister	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b54d4881-1bc6-4134-9bac-2bc983148ed5	dsg	Dennis	Säring	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
51afc696-6082-4237-9687-153737bee18d	eh	Eike	harms	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a38b16b8-3e0a-4b85-89bc-754a4880780a	fbo	Franziska	Bönte	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
645a078a-04d3-41ff-b720-080dd70250b1	gb	Gerd	beuster	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5e280afb-da17-4629-8fea-40d7880580d6	gh	Gunnar	harms	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
587397a4-894b-4a60-ae95-af17f7c71a70	gi	Thorsten	Giersch	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cf515827-5393-4ff0-8b0b-17e376c00f3f	gre	Gerrit	Remané	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7e598367-12d8-46b1-9817-21679731637c	hgl	Hendrik	Glowatzki	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
703da33b-9830-4322-a1b5-2018d76a624f	hs	Andreas	häuslein	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
11fbd50d-c9f4-4ec0-aa62-58c200b468a3	iw	Sebastian	Iwanowski	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
89f6ed60-6465-453c-b485-cc80b9d43e17	jpl	Jan-Paul	Lüdtke	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
bfc2ac28-44fa-479c-bb71-2f858690cdc6	kal	Ilja	kaleck	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
43cbec95-02a1-44b2-bf05-f55d105c75ec	rpo	Ronald	Poppe	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6eff3b68-53f2-4b85-a10d-dd9559f3879e	saw	Sergei	Sawitzki	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	smt	Mike	Schmitt	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7dce6776-3a71-45b5-aa9b-b33ff44e84be	uh	Ulrich	Hoffmann	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
73b3ef2e-becd-45c3-8f7e-9531a83e53fc	web	Stefan Christoph	Weber	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cffee650-43db-4f7f-b479-d0b439e61287	wol	Birger	Wolter	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	aan	Annalisa	Anzioso	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	avh	Alina	von der Heide	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
aeef2474-4ad6-4585-83cf-9ec480c8d0a9	bos	Timm	Bostelmann	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
64847330-57d7-4e0c-bb60-f1b74673512c	cei	Cordula	Eichhorn	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c0fd2eba-50bd-4f7a-8f55-7cfda9643139	cmu	Camilla	Munhoz	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	dmt	Dorothea	mahlstedt	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
19655b1e-03fa-4c82-b2a8-42de4e84b4db	fko	Fikret	Koyuncu	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1fcbcc1a-16ac-4788-a779-76a65b735e4d	hi	Michael	Hink	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3b0407f2-8d98-4a28-b3da-e6fa5ded7728	hoe	Herrmann	Höhne	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	inc	Inga	Nissen Camargo	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
393058cb-870a-45ee-ab55-5e47c84d10b2	jg	Jürgen	Günther	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d63f5f80-19aa-4d82-b825-69beb8129457	kch	Thorben	Koch	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
541c7c37-fe94-4c97-8185-031e0fc7237b	kfe	Katharina	Feindler	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ded06937-76c4-4516-864e-f14d83081e99	klk	Gerit	Kaleck	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	krg	Christian	Krug	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8352510c-ee2b-4a43-b002-62affd865904	mhe	Malte	Heins	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3422b8c1-8982-45cf-9178-f58366ec18fe	mho	Maximilian	Holtkamp	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e785a4ed-af08-4b78-a450-11c1efa76288	mpa	Marco	Pawlowski	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
39b4633f-81a5-46df-944c-b29a91f38b14	mpg	Marian	Gajda	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
011c0cf9-821f-4913-b775-d7191d8b15cf	mre	Melanie	Reißmann	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9e8d1ad1-f50e-40b2-a434-18d009de310e	mri	Marcus	Riemer	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
42b1f177-628c-4a87-bf8f-a11932fffa23	mvi	Markus	Vieregge	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
098646af-2946-4e14-b37b-aae996c5a920	nho	Nicolas	Hollmann	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c94019d0-8741-4f4b-a6ab-0c2439d77bde	nvk	Nils Roald Helmut	van Kan	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ab832241-5ec1-431f-ae8c-6431218c18e2	pf	Michael	Pfeifers	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
fbe60aa6-8d84-426d-adb8-c40638042c9c	tth	Tobias	Thiemann	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3eb87c06-79ea-4b14-8318-741bad302e14	tti	Torben	Tietgen	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
4ec986fc-f097-4405-bbfa-7026fbe4de16	uhl	Christian	Uhlig	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d35a424b-d4d1-40a6-aed4-7ba6a1167143	aah	Afsoon	Alipour-Hoeft	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c17fae98-f027-4d30-ad66-e0c859ad6f40	atl	Aron Daniel	Tal	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
52c95b56-9b87-4b69-b51d-41dd2fca8512	bau	Sabine	Baumann	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	cen	Christian	Entringer	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e84b7f27-2a5c-459e-ba44-b9808e798161	ch	Dirk	Cholewa	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	cmm	Claudia	Marggraf-Micheel	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	dko	Denise	Koch	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7d89d743-b98b-4151-b76f-0fe067629565	dmi	Dominik	Miller	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	ev	Byron	Evans	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8ba32227-a15a-4368-86ee-5dbee74385af	fhe	Florian	Hermsdorf	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
36685604-58cb-4df6-b19a-c4efb2ef3fe7	fre	Felix	Reiche	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1becfcec-0a74-4916-9f58-1f895c07bb28	hw	Gudrun	Hinz-Warnke	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d231a1a5-763b-4332-8c1b-d4353e932af3	jbn	Jochen	Brunnstein	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
52ce8f6b-8a6a-4546-875a-8178a9feb6f0	kil	Emre	Kilic	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
03700b08-395a-4c6b-ad70-d16d7bd2953a	kle	Harriet	Kleiminger	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
21cf5e55-8d00-4266-8327-abc94f05e72b	kru	Jörg	Krüger	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
138903f7-b68d-4991-a566-a6d6fe8e2902	kzo	Kristina	Zöllner	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
229de8cf-dd38-4cf9-99e0-0b086613d2bb	mlo	Michael	Looft	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7dbed3de-9e92-4c15-8533-c434adb916fb	mzo	Mustapha	Zorgati	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
8bba2799-8adb-488b-bee7-4bffc2bb0a0f	ona	Ole	Nass	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	pgo	Panja	Goerke	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
4109ab7a-165b-46ec-a0c0-b479d1defeca	pmb	Paul Maria	Bartusch	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	sbr	Sven	Brose	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
60873c6f-5448-4cc4-b180-a47f9e3f4b82	sed	Roland	Sedlmayer	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cbf427e0-2f1c-422e-b7f1-f6263e1bd831	sla	Stefan	Lange	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f2ae5f8a-4846-4a9d-8892-54d073e61747	ssc	Stephan	Schäfer	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
4a9e587b-b205-471d-950b-fa335e9458f5	swa	Stefan	Wagemann	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
eb98825a-95f0-4de3-9c74-71c32d9580a7	ths	Thomas	Schnieders	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
deaf2b37-4a4d-4923-873b-1299b4464782	voe	Jörg	Völker	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
98883c70-e103-48b8-93ff-910511d0f86d	ysi	Yannik	Sippel	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d6d54125-8b83-4e0f-9cbf-d143bb1d0e7e	afi	Alexander	Fischer	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
79ba1923-1d59-49a5-a970-3bb2f693ac8e	aha	Andreas	Haase	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
3042d18a-0c9b-41b7-9d29-5608f2c3eb4a	ann	Hendrik	Annuth	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
2d4b9bea-2f41-4b21-a7af-68d8951f25db	awo	Atilla	Wohllebe	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
f3c51215-35fd-454d-9bc4-51284f71c0e4	ba	Frank	Bargel	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
61199f40-b866-4f92-93e0-e67dd83d63b7	bo	Christian-Arved	Bohn	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
f3ec8fe3-0896-4310-9731-714d69d728b9	cbu	Carsten	Burmeister	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
0cd0ca9e-487e-4623-9983-e22037e1a374	dsg	Dennis	Säring	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
81d0a658-064f-4f8d-9598-8e9e45b652dd	eh	Eike	harms	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
3dfffe4c-ffcb-40e3-9aa7-4072e77f6199	fbo	Franziska	Bönte	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
d6d9a80f-878f-4790-8816-788fa2fe2bf0	gb	Gerd	beuster	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
f67eae13-c5ec-4238-9730-ef9ebfba455f	gh	Gunnar	harms	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
5afa5e28-175c-44d7-8057-6a6e4a0792ff	gi	Thorsten	Giersch	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
a7d3d6ca-1ae3-44b4-8b7c-a1099a186768	gre	Gerrit	Remané	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
fc588a39-f263-470f-866d-9ff12b55cc3e	hgl	Hendrik	Glowatzki	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
00f1e819-8186-4ccf-9ff8-9fcb2f1f2066	hs	Andreas	häuslein	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
0e2e01c4-1eb9-4581-a6ea-8276b5651c77	iw	Sebastian	Iwanowski	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
27162d80-10ba-4cc3-af5c-aab699ae3ca8	jpl	Jan-Paul	Lüdtke	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
1fd339fa-03eb-4a45-b055-e865526b36b1	kal	Ilja	kaleck	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
be68e6ac-474e-4af9-8dcb-4078bab8d089	rpo	Ronald	Poppe	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
f98a1c66-9d93-496c-9383-512ed8a9ab84	saw	Sergei	Sawitzki	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
7f1f5256-1efd-42fb-8c05-0fa3c9c7a691	smt	Mike	Schmitt	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
f72154ec-c36b-4219-8232-95ce169406a8	uh	Ulrich	Hoffmann	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
9b107afe-735d-4bea-8043-33ebd683d698	web	Stefan Christoph	Weber	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
440c4725-c926-4fdc-8d30-47c2b4b00583	wol	Birger	Wolter	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
ade9a30c-34c8-4a95-b990-00e4889fdbc6	aan	Annalisa	Anzioso	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
cfaabecc-aa6b-4f80-b23e-5463159a66e8	avh	Alina	von der Heide	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
6cb98d98-e3f8-4f41-9449-c86735bbef70	bos	Timm	Bostelmann	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
65952775-2711-447b-81f2-80d59fa16ed6	cei	Cordula	Eichhorn	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
d2e66873-fc15-4819-a0a3-e30b495c6376	cmu	Camilla	Munhoz	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
9377065d-dc9c-4184-b8af-a4318488837a	dmt	Dorothea	mahlstedt	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
aa3a1507-7a11-4ab8-ac3a-3e03febbf932	fko	Fikret	Koyuncu	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
59f31476-f157-4e07-82b6-d1c9d309aa10	hi	Michael	Hink	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
f807d848-b4a9-481b-b45e-7e407974e7e5	hoe	Herrmann	Höhne	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
fa571267-de8f-4215-8126-3c0563d290f5	inc	Inga	Nissen Camargo	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
adc8f15d-ac24-4b9c-b21c-ce9aa6aa5337	jg	Jürgen	Günther	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
e9d2955d-b66c-4cc9-89f1-5855c355077c	kch	Thorben	Koch	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
c9c6d533-6327-4351-bbc9-16bb90fcdeda	kfe	Katharina	Feindler	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
d772cc30-1739-4693-8624-3284434c763f	klk	Gerit	Kaleck	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
6dec9ee4-e4c7-47f2-a6e4-f1c66d40d14e	krg	Christian	Krug	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
a8f0f1fe-bd86-48b7-a82e-fea4b15d781f	mhe	Malte	Heins	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
704b7fd6-227c-4ab7-af6e-faf0d5cb9050	mho	Maximilian	Holtkamp	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
070c2ddf-2e58-464e-9d6b-c39234d49f01	mpa	Marco	Pawlowski	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
e89c37e5-dbde-4b7d-bca6-ddc594910af0	mpg	Marian	Gajda	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
17ccd303-3952-467f-a2fe-f805422fd438	mre	Melanie	Reißmann	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
212e6dfc-fc41-4478-b6c5-491d0259c6f7	mri	Marcus	Riemer	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
d701e76d-34fd-4e21-a01d-362ff6909623	mvi	Markus	Vieregge	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
a4786f82-5c6c-4dca-8920-06e775d57adb	nho	Nicolas	Hollmann	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
e02dee1d-0682-49c3-8ead-522104328d01	nvk	Nils Roald Helmut	van Kan	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
a9021713-ece9-4ab7-8a07-e086361b0ac1	pf	Michael	Pfeifers	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
ccf21eb4-c100-48c9-a180-3585e55d85ca	tth	Tobias	Thiemann	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
adbf95ae-7899-418f-a160-8c9e227d1cd0	tti	Torben	Tietgen	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
ce15f87d-c7b3-41d8-9095-213361f8b254	uhl	Christian	Uhlig	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
fa2b5f6c-58cd-4301-ad06-6e8d272a3097	aah	Afsoon	Alipour-Hoeft	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
4ac97566-8a38-4e2f-88b6-0088e84877c6	atl	Aron Daniel	Tal	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
0efcb093-bd5d-4dd5-961a-5ce08351a22a	bau	Sabine	Baumann	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
4294e025-57d9-4895-a2f6-69b0778b4458	cen	Christian	Entringer	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
9d724caf-4c6a-4241-996c-6701cfc77c30	ch	Dirk	Cholewa	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
89da9f75-08ad-49eb-9a76-268e86b1174a	cmm	Claudia	Marggraf-Micheel	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
0ef220d2-5d9c-4ac8-91da-784e60192819	dko	Denise	Koch	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
d5479b3a-7983-4f7f-be02-57d7c3895e0c	dmi	Dominik	Miller	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
6fb8fc57-b6f4-4148-9002-61f71a402899	ev	Byron	Evans	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
ef5b34fa-5019-4f8b-a804-7b5e430315b4	fhe	Florian	Hermsdorf	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
8ef54c3a-4bed-4525-b7f3-b1db4d005e64	fre	Felix	Reiche	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
d04a2656-faf0-4344-8ad7-eb9d24b9c91d	hw	Gudrun	Hinz-Warnke	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
5858ce46-b333-4745-bcbc-bc2fa14afa83	jbn	Jochen	Brunnstein	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
fb3cf071-dba2-44f4-8824-1fe8d7d30e44	kil	Emre	Kilic	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
243de509-1fc1-4932-98cf-9da7bc589b8f	kle	Harriet	Kleiminger	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
6ce7ffe5-697d-4d4a-898b-22b862376d16	kru	Jörg	Krüger	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
64ac5889-d8da-402a-8127-19f8b6068ba5	kzo	Kristina	Zöllner	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
f83fed7f-cc6f-4b04-89de-965759513a79	mlo	Michael	Looft	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
da937502-efa9-4532-8500-f5e54ba46393	mzo	Mustapha	Zorgati	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
cbe14176-1b70-40ff-ac40-499a4043bdaa	ona	Ole	Nass	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
ef96d700-e4e6-4169-bdb2-769735d62df4	pgo	Panja	Goerke	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
23d561e8-eb0a-4903-9346-249dcceddb1e	pmb	Paul Maria	Bartusch	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
de7c5d68-e846-48d6-a709-46697be3b6c3	sbr	Sven	Brose	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
7e30891c-2f66-492a-bd9a-4bb342c069c3	sed	Roland	Sedlmayer	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
a3d331d0-c0cc-491a-9ec1-9370af72df7d	sla	Stefan	Lange	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
90b96c94-054c-4536-b42a-e4452bfceca6	ssc	Stephan	Schäfer	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
4a3d2b06-0f1d-44e0-8971-237e106d30ab	twe	Tim	Wetzel	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b41edde6-b90c-47f6-a8bb-143a30990513	wa	Andreas	Wand	65a03f1b-40b5-40f3-919c-41ef315eafcc	52abe312-28b1-4ed3-a833-cc040ac6b8d6
0b71b641-82cb-4c00-8c51-5c522188a59e	ne	Lars	Neumann	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c5dd911f-43d7-4ccd-82cc-afcb1d65602e	platzhalter	Vorname	Nachname	10b41872-7e2b-4bcc-8187-5f279aa71be1	52abe312-28b1-4ed3-a833-cc040ac6b8d6
9eb05d24-7958-46ab-ba56-1d2e5bc56fd7	swa	Stefan	Wagemann	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
524c08f1-ab6e-40eb-b55e-734b57c44eb9	ths	Thomas	Schnieders	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
dcc2bd09-2790-47e2-9344-28058fd2d465	voe	Jörg	Völker	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
0899122e-1178-4878-8799-6fc37a081467	ysi	Yannik	Sippel	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
0797d145-edef-4f4c-82dc-0ec62e6e7224	twe	Tim	Wetzel	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
ded7a6c3-5bfc-4b2e-84a3-f86eb20af6ec	wa	Andreas	Wand	65a03f1b-40b5-40f3-919c-41ef315eafcc	1ba2682f-29e0-4cb6-ac59-26f6275e875e
f73cddda-de0e-44c5-baf6-55d04f8daa4b	ne	Lars	Neumann	4db5b5ec-426b-46b1-8c7a-8a1b9d6c58a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
26a26ae3-9409-4acb-b3fb-f358355d4f30	platzhalter	Vorname	Nachname	10b41872-7e2b-4bcc-8187-5f279aa71be1	1ba2682f-29e0-4cb6-ac59-26f6275e875e
\.


--
-- Data for Name: room_types; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.room_types (id, name) FROM stdin;
7090bc56-d4e6-449c-8724-c4d9c0528d61	Hörsaal
806357e8-ae11-4aba-acd6-0bc1f9b770a9	Labor
75b5d955-55de-429d-aabe-5f4bbdb24b9e	Seminarraum
\.


--
-- Data for Name: roomcombinations; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.roomcombinations (id, fk_event_id) FROM stdin;
717ea22e-3900-4215-b503-1d7ff072e40c	19c4c340-d56b-448a-a9d3-583daa46fefc
d6454c67-e931-495c-b410-1afadbce26ef	97baa833-1f10-4349-85a3-c179959af8ce
d71a96c6-5ea5-4838-a33d-55d6b2472a2b	5885d8cd-49c6-46e6-8a89-36b55de54829
6eca1f11-a795-4dae-bc17-f3b668c4f900	d1e3dcb1-fa7c-4355-b42f-90c8378679cb
43a7227b-6e92-43e5-ab4b-5545f894b643	24825786-1517-44ed-a97a-843f2ab2e5cf
35e71f96-20da-4b16-ba7f-35f376c189e6	db0c3159-a02b-4e36-86e4-eb3cd4d210ee
151ce4c6-ad44-4e03-ae9e-fde258b1da5b	04fcd6fc-890b-421d-8137-16fe13250a42
adf499a2-577a-4c8a-9b32-84d36b48e0e0	a7fd852d-5a48-40c2-9715-afeee84a39e0
452b13f1-6d84-40b9-b82c-982395dd49df	9f611bd8-64ed-4cad-9d07-a8fc6184104d
a88ac579-23b2-4c0a-a152-ba09646c42b4	b8afdaa4-2e1c-47d8-8d15-d1019fa460d5
7b9de25a-216d-4038-b562-b96c61f76c3d	426c7cf6-bad8-4360-bb83-33e2afe1fcb6
3bb2ade7-5247-4677-8e9e-0d6c110fc5d8	6eeec8d4-efd9-4d38-8643-4ac432801489
95ce653a-8e85-4329-85de-1254927ed876	e63a3e7d-406b-4907-b08d-9256af86a2a6
18682e1e-350c-4c61-af06-0ce605c2521a	6f115cf8-f0f9-4cce-84ef-5e4f5bb11ba5
6316b271-74b2-41e2-9efe-8152dfc29211	bc4e93ce-352f-493d-9089-c72c1eeaf8e4
5bd0388f-4a0d-4b96-a912-501765872ed0	653df30a-8013-4f51-83dd-4d94e429b18d
2659fb7d-90dd-4a30-b26c-6b56684fe13e	b65a8082-6f76-4f30-b9bc-142d01701d8b
b8cd9e71-b55e-425e-b7b9-e312dac57e45	cdcb904d-32c2-4f88-9af8-b7ca23b5bc47
a4bc1e46-0e2a-4cf1-a0e9-8f8305a9696e	c98823ee-d087-4a9e-b518-2b28faaccd5f
0db98fb8-5b26-4c94-a340-a2b9a75f8202	1e896415-6ebe-443f-b048-30725be095c3
14fb72e8-0d35-4c85-8191-4c5ea65fa75c	05155d0d-89da-4c55-b92c-94a1387ec741
bb2d09b8-6c86-41d2-80a6-b56f9163884c	76cc6bb5-de06-4f1d-8715-1e05d0e1cc5c
66671dae-cc24-487a-9b36-21608517c0bb	03019641-317d-4b74-adaa-1580ff4c99bc
ee0393b7-e10d-4d20-8ad5-2fe809a720cf	af9e38a7-5fea-42ef-b6c1-cd56d0161988
557e3a7d-894b-4fc7-9b79-d5a46616d8c4	af9e38a7-5fea-42ef-b6c1-cd56d0161988
d07ef8cb-18ff-4736-a4cc-70a4c28d8ea4	1a931c05-4408-4eb6-afdc-905ebda9edc1
0d346856-e068-4ec3-899b-1a0846a857a5	1a931c05-4408-4eb6-afdc-905ebda9edc1
78bd2628-c238-44de-b623-8f423f742dc9	1a931c05-4408-4eb6-afdc-905ebda9edc1
e24b2c3d-bfad-4fdf-9e57-4e3833544f6a	1a931c05-4408-4eb6-afdc-905ebda9edc1
44450623-23d1-4b01-bfb9-6bcc8667d46a	1f56ef6d-19b1-47a8-972d-0a9eb17e56a7
3e190b9a-5e8b-4ece-b9d5-b0c4ec016398	5e445afa-73c0-4500-a0d8-697acd7bf58e
997927f7-a432-47f8-a0c5-30ae98a5cef5	e738acf7-a61b-489a-a6f9-bda344038174
87a108b0-28a2-4b5b-8827-a56150af07cb	e738acf7-a61b-489a-a6f9-bda344038174
ce978665-d7dd-4227-9fbb-d2cf8d0c74fd	68dd3eea-cea2-4e54-b2e3-2179938239ca
b8ddd506-79a9-411d-9417-7c76de5847dd	7d48e39c-8f0c-4db0-bd68-3c4b83e2b8d1
e66e5207-adcf-4eef-9ee4-7c5e6687cc23	3bcee379-cb47-43a8-84f6-933a0b3de0a4
9bd383dd-bd4b-4b41-8222-e7b9932a435c	6bbb9b79-33ba-422f-9964-a8435805fcbd
b2d4d557-b76a-4b57-80b8-0e079ba5b517	53dd2ec8-f3ba-46ef-8c66-42bfd18170b9
877f24ab-2bb9-4e27-b915-40902a7e022b	8b90882c-0ce3-48a5-8fae-24268e66fed6
d216d98d-916d-4b7e-b64c-513b4b1fcc26	71e325fb-01c5-44fd-a0de-898927b39357
cbe8401f-1622-41d8-8bba-c2293a1adaf1	71e325fb-01c5-44fd-a0de-898927b39357
345ca1d7-cd97-45a3-9d0e-cbcf13633105	6f0d248f-ea9c-4f98-8b75-c7569aa198e1
f7ba532f-1ffd-4c17-b114-942151239528	6a7e42da-36f8-454a-81bf-0a8bdb23b8cf
47122d37-ef88-479c-a436-4835b6f8f509	c0aa58be-47d7-4f72-b017-9125856035e2
589730f9-957d-41ec-911f-00baa4f51c99	a666d323-152f-48af-be42-f59d6cc27deb
7bfbc293-a94f-4938-b1c7-34242456a0b4	7c23bee8-0b73-4720-af87-28c6c180de6a
b086856b-f5de-4e26-b7a6-17e75a5b4ba2	7d1eb5d5-dd84-4821-836b-ef6a694965f2
ab72068a-c9bd-4a35-9978-5752e220bc09	a7e6ba0f-a11c-455e-a15d-ab26528d3ffc
292e0827-b3b4-4797-af24-1166006c701b	aea1a914-5bde-4a5c-92c1-27c9794cf738
7e5e28ce-8388-42c1-bec2-596dee993a35	a8bdcf92-25a5-49f5-b2d7-57c83ff424f4
7fd4c6dd-fc1f-4d2b-99d6-ef72b9627990	1e7550cf-39bf-4394-a631-6279e4be4997
0bdfc75d-bec7-498c-9e51-f78898911991	009eb4ee-d905-4f04-bb23-7fb156ee569e
4856a702-57a6-4e5b-994d-ceb2784e9fd9	a4dd8404-9c4a-4046-b857-609f4f6bda92
a9175bd1-b577-492b-8d70-abf65daf3101	c7955389-a368-4f9a-9a48-cd015c38d841
dbb90b2b-f652-4e3e-bb08-21787c7d13db	a79d088c-d445-4892-90d5-065cacb9446d
96797381-9b55-4021-8723-3b0783dfb868	737ba05d-dffe-4973-a606-f91a3bb8c0f9
27aa115c-35c1-4e14-8be4-ec1a6744d9c3	7ab751ea-9048-417d-b0fa-755328a7f385
98be10d3-a928-4d41-a313-80427a81117d	b4bebd85-6f9b-4ba2-8ed5-2e4086d6f699
df3ab551-feef-4336-9138-9e5898833c1b	f9ca8c9a-1898-4bd6-befc-eeeec71e1bc2
45ac8a93-c5ee-44ba-84ed-5dd9eb3b594e	77f1a128-ea8f-466e-8c0d-998c2280c811
687558cc-aac1-4693-93b9-7a6b35660b6c	32a84dc4-3b53-437c-a78d-d5f5061f2b4d
0c6b9a84-9899-4065-9dc7-7d57880a5f5d	df82acfb-e4dc-4615-9b1f-c41dcbe4803a
62b0828d-5b00-4c2a-9cb4-b8a60a913ac7	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
23fcf8e2-d9dd-47b6-aa75-66c3ec0c6a8e	613282ae-c93a-4cc8-b6c3-fa73f8a5326c
2ed6518e-10a5-44fb-87dd-715f6c263c08	60c9ed1a-a37e-47cd-986f-8b46d91ffe16
890948c0-971b-4ea6-9bda-dca47d8525fb	8d5e74b8-7910-4ee3-b424-3697b919da65
d0620b2b-fd24-4089-bfc8-2125f832f8ec	fb46ae33-946a-46d4-a4f2-29df8b040cff
ee1e7848-5623-4bfe-8b18-063cc858c2af	803f1d5e-f530-4983-b815-68238a066cbf
259c2e26-15e6-41f4-9132-352e12cfd59f	e211f36c-60a5-47c7-9971-f16df7bf07fc
aa6a5fa5-ea46-40c1-846f-07d91365c586	3d6d21de-bf1b-4fbb-aed0-ee57caa00988
75109fd7-edb6-4d34-8563-a274e0d0fabd	6b1271eb-54e6-49bd-ab27-c85605ec8576
6c7c162a-fc2e-45e2-849c-327df7ce516a	6b1271eb-54e6-49bd-ab27-c85605ec8576
984ca235-5c87-4c13-9733-ee65a5f5dfe4	9ab1875d-9ba9-4f1f-8f12-7def153646d1
4c19d7a2-8b90-4184-99da-60e2463d6ba9	9ab1875d-9ba9-4f1f-8f12-7def153646d1
3751ba33-b7d3-4b33-98c2-92bd47e83989	e278ea08-a02d-4de2-ab78-d34b856c87d7
9ca0e783-f3f5-4fb6-9fd4-b11cd008b4a5	a80a1879-1353-406e-8d2b-74da7a59c64a
78523c1a-4d49-4504-91ad-6de49d5a0044	86961441-94bd-4771-91e3-3580d18a9f40
65bfcd94-96de-4401-a393-ab12379236fc	c5273d0a-67cd-4449-aa07-8a9fbc6b3301
0ea84ab5-b841-4f87-8a29-34202a86e50e	397d9d65-bfca-46fd-96b2-d81390b682b2
18c4da67-8c28-4c56-af2a-0f19723ad223	397d9d65-bfca-46fd-96b2-d81390b682b2
51ce1c8e-dc0f-4078-b009-3c8fd5405ec0	c28a9113-da92-43ba-8123-741f67dc4b89
4151f8b5-89c3-4f07-bdf7-aed1a2554914	d1d75766-4b31-4f28-ad9f-9d7bb7f49570
115d7bb1-a5d3-4ca9-9dd9-5017a0c7f98a	bd3dfd2c-c0d0-47ec-abf8-ba9c12caed23
bbeedc4f-0b59-4f62-83aa-af2a96f61267	0b2159c6-a703-43bb-b6a1-5392153e01d7
c213e96d-1794-47af-b6f6-9949d116ada9	3cf8659e-c9c2-4c9f-91d0-897951be0372
c69a3bb7-7c9b-423b-9691-f09af5019e7d	75ac7b94-3abe-4833-994e-110fc95c2ab4
8c9b3765-ed54-47d1-8ba5-c39c1dbf9c4e	f9c97c01-a1ad-4ce7-a727-303bcd2342f5
2a063aa6-3fed-42f3-b395-79a0614b8fe2	836ee776-ad10-4ca5-8999-0b4cc29a032b
9d3253b6-ed9c-4061-b92f-8896190826c2	839631b1-18ca-4d4a-8105-6ec7c0cf8ffd
343b8821-ac13-45ee-975c-0f25daf0918a	f8a8bb67-a047-439e-9956-ea4822e56e98
e8d85173-8167-448e-b570-ecd686088ec5	14fe8296-326b-4aed-ad72-315db46d9504
df8fda18-150a-41b7-8e47-088b5c9a835c	4aeb5881-7e48-470e-b134-12236311f9b8
330ab496-285b-4c86-b45e-14edbae88b5f	b3bd0f0e-83df-4b31-8f33-815e6853a3a9
963af23d-9bce-4faf-8f36-ded068596eee	b3bd0f0e-83df-4b31-8f33-815e6853a3a9
af420184-beff-4f49-9681-b49e414445cf	5f84a25f-32b7-4b0f-bc74-9151f5e9e941
267ef3bd-edec-4669-9f3f-2afe4f918d5e	a95dcb15-398c-449b-8ef6-4bc0a9c4891a
d2527d45-5515-44a5-8c17-64e322fdfd6a	68be0a27-ed84-4ac1-a8f2-5b22ef9cc442
2c4aa760-9f05-49a3-aa6f-7bec8619e675	733205c0-613d-49bb-aff8-50a53df081c6
c810ac02-a7eb-4e12-845c-117db3fb7279	c8520c2f-475e-4f88-a3bb-284e7cc58562
adeb4755-87c7-427f-a642-b5a2ea161209	bf8268d8-5e12-4137-9407-be7988f0eeba
e1927212-ba2d-4e2b-8347-88708d73d6ef	50402a5d-cc3a-41af-a544-46dfb70ef381
94f5daa0-53de-451f-9b72-3dcbc3025e04	5d617722-8aeb-4e89-b859-01d3ec382190
d1c48ab0-f291-45f7-b56f-beb6772e3359	d03ce83e-c104-4f7a-9aa7-62d1193d18eb
3cd1de53-1d73-4dd6-b46d-8dda0f0278e4	c5611c9c-3d1a-4fae-9999-35acab706abc
ccbed645-7566-4cab-86f6-639db9128793	7418423f-fa22-418e-86b9-7407485fe284
6855b436-925a-441d-ba05-53355c5b7020	10d0833d-8416-4d03-a831-5a757a9a835c
d4ab283d-67fe-4413-a9fa-81eb5437f844	cea04306-6f20-431d-9904-c58271cf04f7
f4d81db0-9025-4586-be52-3e76e4b48019	dba6afd0-4e82-476c-b1a4-06555587bffb
eabd95a5-6869-4364-ad87-eb0cb70e4fa2	b671851f-867f-4d2d-98fd-6f6c5c31c083
7b22afb4-c7ef-4cf2-a25b-960458b4927d	31084145-ffa6-4861-9673-13c551ae20ab
fe0a53a4-0f2b-4c18-8175-f6d09e6aa833	3979dd92-065c-40db-9c3a-3794752685df
b47cf3ea-c182-4759-9362-0b381d452b1d	9fcd1dd4-947e-48ad-a71c-937d87f9a894
2370d2b4-5bdc-4020-8e79-99a606b09a42	4bbc1f38-3573-469b-a204-b84123b925a3
e8ee0a22-f181-4b42-bf79-3ef41f332cfa	d9ded29f-0a3b-4024-b3cd-7a960ce399b7
c4f9bd83-3813-442a-8778-ece3dffc5dc7	7c512d97-6114-4ef1-ba31-55abaf1f274e
32ad64bc-2317-4288-9ac0-9501bdd2710e	d249752a-4f26-45d6-b9a0-4288c97d2ee1
847aab31-4986-499a-a07a-be3dc1818741	ee549e95-aa66-4707-b957-26b96b35d068
06aeb244-2351-4a29-a49c-4eb11d2d4090	26654263-ccb2-4fad-8ecc-342da43c3928
6676ec1a-a901-4cd9-ba9d-0a90fbf12173	91283133-2877-4bc0-a90d-f4168d088349
3d7d29fc-3088-4375-8b70-6b9535380c88	0750aa84-1b51-45b8-aaad-056cbf1b303b
7eaa3de0-b1a2-43a2-9aa6-aa0c7af0db34	1337b436-7ff9-4b79-82a8-9981f2e66521
eb927f44-a850-4ddd-839f-8fa073ff554f	1b702945-e962-4778-b9c9-2fce7be4ab6c
27c18108-81d3-4ed5-8bbc-6015075b4d3f	f7fb4d07-cc8b-449a-a860-2100d2334658
7dce440f-62e4-4bcb-b675-166819ca007b	cd5f8554-fec4-4862-80c3-caff5df01ad5
2c06ffaf-e630-46ac-9dae-3e46769e2ad2	64e7e3c8-c369-4913-be67-a64c7ea29d11
6ec91401-84c9-4692-beef-161a0a6a313b	a6c524c4-6748-414d-99dc-443680f2ba63
09ffe38f-2c55-4bc4-811c-2a9e041d9469	f7ea46a8-2dd4-4a5a-88b9-9b964beb68e7
50bb8824-cd8d-4bc9-8b7a-b2dbd8c0c196	72d02727-c162-43b9-a3ab-9b3e8dc4cbd5
09feec77-a5af-47b1-aae0-99cb3a9f55b2	62d4edd3-b54c-4f35-aef9-d0850f792260
8052d236-129b-4c21-a207-15d119227be2	a466ef46-1050-4081-8191-f15c08a9f6db
cc353b79-721b-4964-989f-96c0f7688e2a	99e29aa2-9c26-4d09-9206-6173b7956d7d
f14112a8-c048-4ed2-b329-15e4a7b0f192	6c6074b4-62fc-4312-91e6-2e8a9196aca7
b16c766c-c387-472f-8787-95e65c1e2af3	0784ce65-0cea-4000-858f-a5bc87d86e8f
0d6b5fb9-31b3-474e-8003-b8231a96b0a2	6b410de7-f666-40c0-ad19-6e7d74d0a3d7
87c25513-32f3-4420-a34c-c8e48ceb3e7f	a2f4b42a-b2a8-44a6-b3dd-0c34f146e904
2686a7bb-de33-43c7-bdd0-4d2e52084599	86cc04fa-7cf7-475b-8404-3bd2df5dfb5d
7f090a59-58bc-4a56-95a7-2a383d392327	8c6942ac-e020-4a00-acc4-8c41409854ab
0e8ba9cf-71c9-425b-83cf-dbe55187da82	d9ee24ed-b9e2-41d8-b733-bc8f48b7ae0a
8f89c758-eced-4d34-afbf-075229daa95d	09be9784-f02e-4c2d-accb-fd49b529e15a
d93b1ed0-2249-4a37-9af8-b208cd2f37ac	320d7753-9513-472c-ac95-cc9567a36cc0
8098ed7f-7c86-420a-8f71-2e4218fe318a	fbd5f933-b196-48bb-b49a-f8f2b1bcf563
50c2a801-8c7a-4704-a784-eed4dca41fe2	e9d67836-a72d-4802-a9f0-25a15e1042e5
d8e404f8-ff17-4c96-a7ab-85c66ecae0e7	1c0cc7bc-a82a-4919-aed2-6d95b6a4baed
ce74b6fe-58c9-4980-a5a3-0af8dab3f6d4	e1482fe4-3872-4f46-a51b-9a648b9a5072
653ec11c-612e-4e48-b51b-0d514a3a2cbc	e65a3a55-59ca-4533-bdf2-ba8511d594ad
a119f6e5-fc5e-438b-91d6-2569cb3e01c2	1c7340c1-7b05-404f-8882-39906697c69c
a4698d40-3c57-43a8-8e6d-899097ae09da	2e103b9f-097b-4d2d-9ee9-8806713621ce
045454d4-bbaa-4e93-a811-1cfe63e9daef	885428c2-75f8-47f6-80a6-94337b9fb10f
d5c362f1-bda0-4092-8f4b-50dfb62ff72c	b0853a08-38a4-47f5-b9aa-83f315d74acf
65652411-bfa1-485b-989d-641141f4ff1c	edcf6bf0-5a1a-41c6-ac00-56a63327adbb
fdb5ba51-8e46-43c8-957e-b953f293e53e	c67ab7e2-0bee-48fe-a8e9-dab0acdcc44d
5c563de7-fd5b-4559-921c-a4d2c95d7eeb	f058e2d3-bcd6-46c2-92c0-3e0aa4e71757
8347f842-c7b7-4d25-a463-6aa6f64bcfb9	f65faea9-66ad-40ce-8488-753078555a3f
ac6e0cd1-edb1-4430-8000-93209b06fe60	c2380f71-ff90-46f3-8438-49f91616f6a6
15b298e2-b42f-4920-a698-50b80fcefcf3	f4e3291c-758b-47f4-a1d8-75680a84be0f
4450ea2d-c3a8-456e-a33f-c7831440df26	32e48d76-6004-4a1c-bb22-27ee66e2d672
931d634f-2fab-44fc-adc9-bfd159cd83ae	22b88adb-3e66-4b08-86d5-c6e6ecc7399f
76d5c5b2-d6ff-4bd6-982a-f5b429ba02ff	8638f31d-dd3a-41df-8571-bcb665c48554
a01bba20-b40c-4d05-b8c4-2c9b6863f9d5	e43a754b-648f-460b-88c6-0d3825b1fa76
4aaf8ecf-66de-48a5-86dd-c27a6f724117	94ad2b6d-975f-4746-9a3b-33c32b34a13f
2da64fd4-c43d-4a30-a4f2-0dc2be35b5b2	a9ca51e9-864d-4d17-b647-ff17ea2c02e6
93772b7d-10a7-458a-9d14-aadf64c8d140	69a94b81-7ed0-428b-a3c9-679a34e47e0a
99c12a5c-aa44-4c92-a00e-1f83f880cad6	ae4d3b3b-f1b9-456d-89b3-84dfdafad01f
c8141dbc-a40e-4b5c-ab6f-6be90723a685	58494593-79cc-411e-ad7f-2b52a7fc6095
cc3f66c4-88c1-420c-b74f-a6b71164e30d	47f982fc-369e-473c-9ab0-8096bae012e0
b7031834-8b7b-4ed0-b65e-c15f65d863b2	9b90fa62-a329-4089-b71f-f9a70c27ac03
e2b5ad2d-56c1-4f24-86b3-cf86d450af0f	3ff1ab96-b31d-4ad0-9967-241d6ed0c0c7
f9cdae1a-3bbf-46fe-b058-a34e9747254a	22891480-943a-4cb1-a417-c760205d86bd
f370a133-d2b7-42c6-8c13-851a82aa9151	4a858055-f275-460b-a349-222cc0ca28d7
a08fb10f-c0d9-494f-bd65-1ca91c6d816a	394a96f5-4e6a-4a64-8a13-0184d37063fd
3b47e122-03e9-4a17-a5f6-772fa533390c	74a8a447-3f16-43a9-b29b-84d2e5d86a88
af521af5-58c3-407a-b04a-642949971a87	f6a2c559-1f7d-4f45-adf1-8c76b2faf147
40be4128-907e-4e38-ac9b-6df8c7900e8e	f1f5bad2-246a-446e-877d-e5b956ae77fa
bc63fd6e-5f29-4855-ade6-819ad40ae669	4fb9a210-dd6d-43a4-9c64-bf56bd677a51
e1371aa2-a7e8-4672-b8bf-1384ae377a53	a5e28c58-1a61-4ece-8a0a-1bd2893c0aa4
d047d63b-b267-44b5-a7a2-4ac8fb740483	2c1ea78c-8ca7-4583-aad4-a6724bc81df1
77cdd615-3ae9-4131-81e9-3fd14683abf8	5926aed9-9d4d-42b1-9fa8-fd82d5706412
2d561e62-3b72-4f1f-928a-9a3d8a96eb4d	79e610f9-4c9f-4234-9a37-d8e1d11a6af2
e2404cc5-f582-4382-8332-0b7c74244edc	b0956325-1cdb-425f-a9a6-afb752a5485d
fe63b0e1-741c-4772-95a1-607058840923	5fdcd179-a83a-4080-a33c-905e3db55dd1
d8d69419-414d-4066-a45f-a50d6b92770e	5fa57fdf-7ba0-4063-93cf-378d7b8267a9
9009caab-2edb-460d-ab9d-10ddea2289d7	555bdc44-a24e-48fc-aa5e-892e666781a5
4fe2c01b-3229-44d4-ab91-a698af37d27b	e1888724-e770-43fe-aa8a-3484bc05a37a
40451349-2101-4424-b5e9-9fea62885fa1	ba85e730-6df5-4e9f-aef0-96577fab369a
85d627eb-0277-40f4-a577-94c0f6a17fa4	9db2d190-6068-4c34-9e5c-36e9994274aa
111d9d0d-3bc2-443e-b2d7-08a96d2470d9	e22fd7de-d39c-43f7-a1a3-fa2f7eec6aa8
26d2cf05-ec3c-4b73-b971-d522b065286f	350475ef-1d3b-4823-8c76-7f190a27f87e
cd8496e4-e884-4c4b-9e7d-a539877374a2	d44fa577-b407-4d90-972b-b3d39ab4b93d
3b94bc06-eba3-4417-b7ae-a1aef4011452	899344cc-f75d-463b-b1a5-3ecbee85a79d
c377a883-508f-453f-874c-dc108b1153a6	14e253d0-ff9a-4614-845a-bd91c1d91ad3
\.


--
-- Data for Name: roomcombinations_rooms; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.roomcombinations_rooms (fk_roomcombination_id, fk_rooms_id) FROM stdin;
717ea22e-3900-4215-b503-1d7ff072e40c	ad90ea9f-03a6-4c01-be4f-185e2e857c37
d6454c67-e931-495c-b410-1afadbce26ef	60f5dc3f-5ee3-4cf1-942d-46f857167557
d71a96c6-5ea5-4838-a33d-55d6b2472a2b	ad90ea9f-03a6-4c01-be4f-185e2e857c37
6eca1f11-a795-4dae-bc17-f3b668c4f900	21344405-4872-4ee8-9888-13f8964aa31b
43a7227b-6e92-43e5-ab4b-5545f894b643	21344405-4872-4ee8-9888-13f8964aa31b
35e71f96-20da-4b16-ba7f-35f376c189e6	85c6d68a-086a-47a0-8f67-558fbc913ce1
151ce4c6-ad44-4e03-ae9e-fde258b1da5b	85c6d68a-086a-47a0-8f67-558fbc913ce1
adf499a2-577a-4c8a-9b32-84d36b48e0e0	54bf6bc4-df73-42ce-9110-ab1a37512bff
452b13f1-6d84-40b9-b82c-982395dd49df	54bf6bc4-df73-42ce-9110-ab1a37512bff
a88ac579-23b2-4c0a-a152-ba09646c42b4	ad90ea9f-03a6-4c01-be4f-185e2e857c37
7b9de25a-216d-4038-b562-b96c61f76c3d	7f1e31eb-994a-4eaa-9907-a84f2762ecc5
7b9de25a-216d-4038-b562-b96c61f76c3d	3536e299-e129-4e7d-8770-ebbdb8a08ff1
7b9de25a-216d-4038-b562-b96c61f76c3d	47a8fa0b-eba4-432d-9311-840714fab82f
3bb2ade7-5247-4677-8e9e-0d6c110fc5d8	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
95ce653a-8e85-4329-85de-1254927ed876	60f5dc3f-5ee3-4cf1-942d-46f857167557
18682e1e-350c-4c61-af06-0ce605c2521a	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
6316b271-74b2-41e2-9efe-8152dfc29211	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
5bd0388f-4a0d-4b96-a912-501765872ed0	194653e3-9f07-40ef-8739-79288cd0c5fb
2659fb7d-90dd-4a30-b26c-6b56684fe13e	60f5dc3f-5ee3-4cf1-942d-46f857167557
b8cd9e71-b55e-425e-b7b9-e312dac57e45	6a4e4c81-7dec-4276-b012-ad29c40135cb
a4bc1e46-0e2a-4cf1-a0e9-8f8305a9696e	85c6d68a-086a-47a0-8f67-558fbc913ce1
0db98fb8-5b26-4c94-a340-a2b9a75f8202	c21974fa-ac26-47fd-8176-c0fafdd8958c
14fb72e8-0d35-4c85-8191-4c5ea65fa75c	c21974fa-ac26-47fd-8176-c0fafdd8958c
bb2d09b8-6c86-41d2-80a6-b56f9163884c	5d649c42-9375-4457-8efb-05a7c5a28d67
66671dae-cc24-487a-9b36-21608517c0bb	5d649c42-9375-4457-8efb-05a7c5a28d67
ee0393b7-e10d-4d20-8ad5-2fe809a720cf	5d649c42-9375-4457-8efb-05a7c5a28d67
557e3a7d-894b-4fc7-9b79-d5a46616d8c4	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
d07ef8cb-18ff-4736-a4cc-70a4c28d8ea4	85c6d68a-086a-47a0-8f67-558fbc913ce1
0d346856-e068-4ec3-899b-1a0846a857a5	60f5dc3f-5ee3-4cf1-942d-46f857167557
78bd2628-c238-44de-b623-8f423f742dc9	bcabbf8e-e7dc-4656-90a6-2bb7142f4cc1
e24b2c3d-bfad-4fdf-9e57-4e3833544f6a	6a4e4c81-7dec-4276-b012-ad29c40135cb
44450623-23d1-4b01-bfb9-6bcc8667d46a	5d649c42-9375-4457-8efb-05a7c5a28d67
3e190b9a-5e8b-4ece-b9d5-b0c4ec016398	c21974fa-ac26-47fd-8176-c0fafdd8958c
997927f7-a432-47f8-a0c5-30ae98a5cef5	c21974fa-ac26-47fd-8176-c0fafdd8958c
87a108b0-28a2-4b5b-8827-a56150af07cb	1db04be9-2c6e-4d20-bf79-0538f7be61ad
ce978665-d7dd-4227-9fbb-d2cf8d0c74fd	85c6d68a-086a-47a0-8f67-558fbc913ce1
b8ddd506-79a9-411d-9417-7c76de5847dd	85c6d68a-086a-47a0-8f67-558fbc913ce1
e66e5207-adcf-4eef-9ee4-7c5e6687cc23	85c6d68a-086a-47a0-8f67-558fbc913ce1
9bd383dd-bd4b-4b41-8222-e7b9932a435c	85c6d68a-086a-47a0-8f67-558fbc913ce1
b2d4d557-b76a-4b57-80b8-0e079ba5b517	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
877f24ab-2bb9-4e27-b915-40902a7e022b	54bf6bc4-df73-42ce-9110-ab1a37512bff
d216d98d-916d-4b7e-b64c-513b4b1fcc26	5d649c42-9375-4457-8efb-05a7c5a28d67
cbe8401f-1622-41d8-8bba-c2293a1adaf1	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
345ca1d7-cd97-45a3-9d0e-cbcf13633105	5d649c42-9375-4457-8efb-05a7c5a28d67
f7ba532f-1ffd-4c17-b114-942151239528	5d649c42-9375-4457-8efb-05a7c5a28d67
47122d37-ef88-479c-a436-4835b6f8f509	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
589730f9-957d-41ec-911f-00baa4f51c99	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
7bfbc293-a94f-4938-b1c7-34242456a0b4	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
b086856b-f5de-4e26-b7a6-17e75a5b4ba2	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
ab72068a-c9bd-4a35-9978-5752e220bc09	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
292e0827-b3b4-4797-af24-1166006c701b	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
7e5e28ce-8388-42c1-bec2-596dee993a35	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
7fd4c6dd-fc1f-4d2b-99d6-ef72b9627990	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
0bdfc75d-bec7-498c-9e51-f78898911991	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
4856a702-57a6-4e5b-994d-ceb2784e9fd9	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
a9175bd1-b577-492b-8d70-abf65daf3101	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
dbb90b2b-f652-4e3e-bb08-21787c7d13db	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
96797381-9b55-4021-8723-3b0783dfb868	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
27aa115c-35c1-4e14-8be4-ec1a6744d9c3	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
98be10d3-a928-4d41-a313-80427a81117d	a3f7c3c2-7a68-42ae-8604-812b7a3394ae
df3ab551-feef-4336-9138-9e5898833c1b	c21974fa-ac26-47fd-8176-c0fafdd8958c
45ac8a93-c5ee-44ba-84ed-5dd9eb3b594e	c21974fa-ac26-47fd-8176-c0fafdd8958c
687558cc-aac1-4693-93b9-7a6b35660b6c	6a4e4c81-7dec-4276-b012-ad29c40135cb
687558cc-aac1-4693-93b9-7a6b35660b6c	bcabbf8e-e7dc-4656-90a6-2bb7142f4cc1
0c6b9a84-9899-4065-9dc7-7d57880a5f5d	c21974fa-ac26-47fd-8176-c0fafdd8958c
62b0828d-5b00-4c2a-9cb4-b8a60a913ac7	5d649c42-9375-4457-8efb-05a7c5a28d67
23fcf8e2-d9dd-47b6-aa75-66c3ec0c6a8e	c21974fa-ac26-47fd-8176-c0fafdd8958c
2ed6518e-10a5-44fb-87dd-715f6c263c08	5d649c42-9375-4457-8efb-05a7c5a28d67
890948c0-971b-4ea6-9bda-dca47d8525fb	5d649c42-9375-4457-8efb-05a7c5a28d67
d0620b2b-fd24-4089-bfc8-2125f832f8ec	60f5dc3f-5ee3-4cf1-942d-46f857167557
d0620b2b-fd24-4089-bfc8-2125f832f8ec	5d649c42-9375-4457-8efb-05a7c5a28d67
ee1e7848-5623-4bfe-8b18-063cc858c2af	c21974fa-ac26-47fd-8176-c0fafdd8958c
259c2e26-15e6-41f4-9132-352e12cfd59f	5d649c42-9375-4457-8efb-05a7c5a28d67
aa6a5fa5-ea46-40c1-846f-07d91365c586	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
75109fd7-edb6-4d34-8563-a274e0d0fabd	6a4e4c81-7dec-4276-b012-ad29c40135cb
6c7c162a-fc2e-45e2-849c-327df7ce516a	bcabbf8e-e7dc-4656-90a6-2bb7142f4cc1
984ca235-5c87-4c13-9733-ee65a5f5dfe4	ad90ea9f-03a6-4c01-be4f-185e2e857c37
4c19d7a2-8b90-4184-99da-60e2463d6ba9	194653e3-9f07-40ef-8739-79288cd0c5fb
3751ba33-b7d3-4b33-98c2-92bd47e83989	47a8fa0b-eba4-432d-9311-840714fab82f
9ca0e783-f3f5-4fb6-9fd4-b11cd008b4a5	ad90ea9f-03a6-4c01-be4f-185e2e857c37
78523c1a-4d49-4504-91ad-6de49d5a0044	47a8fa0b-eba4-432d-9311-840714fab82f
65bfcd94-96de-4401-a393-ab12379236fc	c6f0709f-bda4-4610-893b-7481a67d5aa0
0ea84ab5-b841-4f87-8a29-34202a86e50e	c6f0709f-bda4-4610-893b-7481a67d5aa0
18c4da67-8c28-4c56-af2a-0f19723ad223	1db04be9-2c6e-4d20-bf79-0538f7be61ad
51ce1c8e-dc0f-4078-b009-3c8fd5405ec0	5d649c42-9375-4457-8efb-05a7c5a28d67
4151f8b5-89c3-4f07-bdf7-aed1a2554914	5d649c42-9375-4457-8efb-05a7c5a28d67
115d7bb1-a5d3-4ca9-9dd9-5017a0c7f98a	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
bbeedc4f-0b59-4f62-83aa-af2a96f61267	7f1e31eb-994a-4eaa-9907-a84f2762ecc5
c213e96d-1794-47af-b6f6-9949d116ada9	7f1e31eb-994a-4eaa-9907-a84f2762ecc5
c69a3bb7-7c9b-423b-9691-f09af5019e7d	7f1e31eb-994a-4eaa-9907-a84f2762ecc5
c69a3bb7-7c9b-423b-9691-f09af5019e7d	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
8c9b3765-ed54-47d1-8ba5-c39c1dbf9c4e	1db04be9-2c6e-4d20-bf79-0538f7be61ad
2a063aa6-3fed-42f3-b395-79a0614b8fe2	1db04be9-2c6e-4d20-bf79-0538f7be61ad
9d3253b6-ed9c-4061-b92f-8896190826c2	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
343b8821-ac13-45ee-975c-0f25daf0918a	6a4e4c81-7dec-4276-b012-ad29c40135cb
e8d85173-8167-448e-b570-ecd686088ec5	7f1e31eb-994a-4eaa-9907-a84f2762ecc5
df8fda18-150a-41b7-8e47-088b5c9a835c	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
330ab496-285b-4c86-b45e-14edbae88b5f	7f1e31eb-994a-4eaa-9907-a84f2762ecc5
963af23d-9bce-4faf-8f36-ded068596eee	5550ecf7-8e09-4920-90b7-5216b27cf48a
af420184-beff-4f49-9681-b49e414445cf	47a8fa0b-eba4-432d-9311-840714fab82f
267ef3bd-edec-4669-9f3f-2afe4f918d5e	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
d2527d45-5515-44a5-8c17-64e322fdfd6a	64b72e33-6fb4-438a-95ad-f91d01f319c6
2c4aa760-9f05-49a3-aa6f-7bec8619e675	7f1e31eb-994a-4eaa-9907-a84f2762ecc5
c810ac02-a7eb-4e12-845c-117db3fb7279	7f1e31eb-994a-4eaa-9907-a84f2762ecc5
adeb4755-87c7-427f-a642-b5a2ea161209	1db04be9-2c6e-4d20-bf79-0538f7be61ad
e1927212-ba2d-4e2b-8347-88708d73d6ef	c21974fa-ac26-47fd-8176-c0fafdd8958c
94f5daa0-53de-451f-9b72-3dcbc3025e04	5d649c42-9375-4457-8efb-05a7c5a28d67
d1c48ab0-f291-45f7-b56f-beb6772e3359	1db04be9-2c6e-4d20-bf79-0538f7be61ad
3cd1de53-1d73-4dd6-b46d-8dda0f0278e4	c6f0709f-bda4-4610-893b-7481a67d5aa0
ccbed645-7566-4cab-86f6-639db9128793	c6f0709f-bda4-4610-893b-7481a67d5aa0
6855b436-925a-441d-ba05-53355c5b7020	1db04be9-2c6e-4d20-bf79-0538f7be61ad
d4ab283d-67fe-4413-a9fa-81eb5437f844	85c6d68a-086a-47a0-8f67-558fbc913ce1
d4ab283d-67fe-4413-a9fa-81eb5437f844	5d649c42-9375-4457-8efb-05a7c5a28d67
f4d81db0-9025-4586-be52-3e76e4b48019	c6f0709f-bda4-4610-893b-7481a67d5aa0
eabd95a5-6869-4364-ad87-eb0cb70e4fa2	1db04be9-2c6e-4d20-bf79-0538f7be61ad
7b22afb4-c7ef-4cf2-a25b-960458b4927d	c21974fa-ac26-47fd-8176-c0fafdd8958c
fe0a53a4-0f2b-4c18-8175-f6d09e6aa833	3536e299-e129-4e7d-8770-ebbdb8a08ff1
b47cf3ea-c182-4759-9362-0b381d452b1d	194653e3-9f07-40ef-8739-79288cd0c5fb
2370d2b4-5bdc-4020-8e79-99a606b09a42	c6f0709f-bda4-4610-893b-7481a67d5aa0
e8ee0a22-f181-4b42-bf79-3ef41f332cfa	47a8fa0b-eba4-432d-9311-840714fab82f
c4f9bd83-3813-442a-8778-ece3dffc5dc7	194653e3-9f07-40ef-8739-79288cd0c5fb
32ad64bc-2317-4288-9ac0-9501bdd2710e	bc037e1b-c236-4377-b67a-4289f67123af
847aab31-4986-499a-a07a-be3dc1818741	c21974fa-ac26-47fd-8176-c0fafdd8958c
06aeb244-2351-4a29-a49c-4eb11d2d4090	6a4e4c81-7dec-4276-b012-ad29c40135cb
06aeb244-2351-4a29-a49c-4eb11d2d4090	bcabbf8e-e7dc-4656-90a6-2bb7142f4cc1
7dce440f-62e4-4bcb-b675-166819ca007b	c6f0709f-bda4-4610-893b-7481a67d5aa0
6676ec1a-a901-4cd9-ba9d-0a90fbf12173	6a4e4c81-7dec-4276-b012-ad29c40135cb
6676ec1a-a901-4cd9-ba9d-0a90fbf12173	85c6d68a-086a-47a0-8f67-558fbc913ce1
3d7d29fc-3088-4375-8b70-6b9535380c88	6a4e4c81-7dec-4276-b012-ad29c40135cb
3d7d29fc-3088-4375-8b70-6b9535380c88	85c6d68a-086a-47a0-8f67-558fbc913ce1
3d7d29fc-3088-4375-8b70-6b9535380c88	bcabbf8e-e7dc-4656-90a6-2bb7142f4cc1
7eaa3de0-b1a2-43a2-9aa6-aa0c7af0db34	1db04be9-2c6e-4d20-bf79-0538f7be61ad
eb927f44-a850-4ddd-839f-8fa073ff554f	1db04be9-2c6e-4d20-bf79-0538f7be61ad
2c06ffaf-e630-46ac-9dae-3e46769e2ad2	1db04be9-2c6e-4d20-bf79-0538f7be61ad
2c06ffaf-e630-46ac-9dae-3e46769e2ad2	47a8fa0b-eba4-432d-9311-840714fab82f
27c18108-81d3-4ed5-8bbc-6015075b4d3f	194653e3-9f07-40ef-8739-79288cd0c5fb
6ec91401-84c9-4692-beef-161a0a6a313b	f9aebaa0-8a37-481c-ae4d-b127d360fb99
09ffe38f-2c55-4bc4-811c-2a9e041d9469	c6f0709f-bda4-4610-893b-7481a67d5aa0
50bb8824-cd8d-4bc9-8b7a-b2dbd8c0c196	1db04be9-2c6e-4d20-bf79-0538f7be61ad
09feec77-a5af-47b1-aae0-99cb3a9f55b2	60f5dc3f-5ee3-4cf1-942d-46f857167557
8052d236-129b-4c21-a207-15d119227be2	ad8705ab-5f4d-406a-9173-6dd471b21ac6
cc353b79-721b-4964-989f-96c0f7688e2a	47a8fa0b-eba4-432d-9311-840714fab82f
f14112a8-c048-4ed2-b329-15e4a7b0f192	60f5dc3f-5ee3-4cf1-942d-46f857167557
b16c766c-c387-472f-8787-95e65c1e2af3	5d649c42-9375-4457-8efb-05a7c5a28d67
0d6b5fb9-31b3-474e-8003-b8231a96b0a2	5d649c42-9375-4457-8efb-05a7c5a28d67
87c25513-32f3-4420-a34c-c8e48ceb3e7f	c6f0709f-bda4-4610-893b-7481a67d5aa0
2686a7bb-de33-43c7-bdd0-4d2e52084599	85c6d68a-086a-47a0-8f67-558fbc913ce1
7f090a59-58bc-4a56-95a7-2a383d392327	47a8fa0b-eba4-432d-9311-840714fab82f
0e8ba9cf-71c9-425b-83cf-dbe55187da82	194653e3-9f07-40ef-8739-79288cd0c5fb
8f89c758-eced-4d34-afbf-075229daa95d	3536e299-e129-4e7d-8770-ebbdb8a08ff1
d93b1ed0-2249-4a37-9af8-b208cd2f37ac	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
8098ed7f-7c86-420a-8f71-2e4218fe318a	3536e299-e129-4e7d-8770-ebbdb8a08ff1
50c2a801-8c7a-4704-a784-eed4dca41fe2	21344405-4872-4ee8-9888-13f8964aa31b
d8e404f8-ff17-4c96-a7ab-85c66ecae0e7	b12bd00e-33b2-40dd-9b48-b74689855214
d8e404f8-ff17-4c96-a7ab-85c66ecae0e7	1db04be9-2c6e-4d20-bf79-0538f7be61ad
ce74b6fe-58c9-4980-a5a3-0af8dab3f6d4	194653e3-9f07-40ef-8739-79288cd0c5fb
653ec11c-612e-4e48-b51b-0d514a3a2cbc	47a8fa0b-eba4-432d-9311-840714fab82f
a119f6e5-fc5e-438b-91d6-2569cb3e01c2	5d649c42-9375-4457-8efb-05a7c5a28d67
a4698d40-3c57-43a8-8e6d-899097ae09da	ad90ea9f-03a6-4c01-be4f-185e2e857c37
045454d4-bbaa-4e93-a811-1cfe63e9daef	194653e3-9f07-40ef-8739-79288cd0c5fb
d5c362f1-bda0-4092-8f4b-50dfb62ff72c	60f5dc3f-5ee3-4cf1-942d-46f857167557
d5c362f1-bda0-4092-8f4b-50dfb62ff72c	c6f0709f-bda4-4610-893b-7481a67d5aa0
65652411-bfa1-485b-989d-641141f4ff1c	21344405-4872-4ee8-9888-13f8964aa31b
fdb5ba51-8e46-43c8-957e-b953f293e53e	ad90ea9f-03a6-4c01-be4f-185e2e857c37
5c563de7-fd5b-4559-921c-a4d2c95d7eeb	ad90ea9f-03a6-4c01-be4f-185e2e857c37
8347f842-c7b7-4d25-a463-6aa6f64bcfb9	ad90ea9f-03a6-4c01-be4f-185e2e857c37
ac6e0cd1-edb1-4430-8000-93209b06fe60	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
15b298e2-b42f-4920-a698-50b80fcefcf3	6a4e4c81-7dec-4276-b012-ad29c40135cb
4450ea2d-c3a8-456e-a33f-c7831440df26	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
931d634f-2fab-44fc-adc9-bfd159cd83ae	5d649c42-9375-4457-8efb-05a7c5a28d67
76d5c5b2-d6ff-4bd6-982a-f5b429ba02ff	c21974fa-ac26-47fd-8176-c0fafdd8958c
a01bba20-b40c-4d05-b8c4-2c9b6863f9d5	3536e299-e129-4e7d-8770-ebbdb8a08ff1
4aaf8ecf-66de-48a5-86dd-c27a6f724117	ad90ea9f-03a6-4c01-be4f-185e2e857c37
2da64fd4-c43d-4a30-a4f2-0dc2be35b5b2	1db04be9-2c6e-4d20-bf79-0538f7be61ad
93772b7d-10a7-458a-9d14-aadf64c8d140	b12bd00e-33b2-40dd-9b48-b74689855214
99c12a5c-aa44-4c92-a00e-1f83f880cad6	60f5dc3f-5ee3-4cf1-942d-46f857167557
c8141dbc-a40e-4b5c-ab6f-6be90723a685	ad90ea9f-03a6-4c01-be4f-185e2e857c37
cc3f66c4-88c1-420c-b74f-a6b71164e30d	47a8fa0b-eba4-432d-9311-840714fab82f
b7031834-8b7b-4ed0-b65e-c15f65d863b2	bc037e1b-c236-4377-b67a-4289f67123af
e2b5ad2d-56c1-4f24-86b3-cf86d450af0f	c6f0709f-bda4-4610-893b-7481a67d5aa0
f9cdae1a-3bbf-46fe-b058-a34e9747254a	c6f0709f-bda4-4610-893b-7481a67d5aa0
f370a133-d2b7-42c6-8c13-851a82aa9151	c6f0709f-bda4-4610-893b-7481a67d5aa0
a08fb10f-c0d9-494f-bd65-1ca91c6d816a	85c6d68a-086a-47a0-8f67-558fbc913ce1
3b47e122-03e9-4a17-a5f6-772fa533390c	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
af521af5-58c3-407a-b04a-642949971a87	5d649c42-9375-4457-8efb-05a7c5a28d67
40be4128-907e-4e38-ac9b-6df8c7900e8e	194653e3-9f07-40ef-8739-79288cd0c5fb
bc63fd6e-5f29-4855-ade6-819ad40ae669	194653e3-9f07-40ef-8739-79288cd0c5fb
e1371aa2-a7e8-4672-b8bf-1384ae377a53	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
d047d63b-b267-44b5-a7a2-4ac8fb740483	3536e299-e129-4e7d-8770-ebbdb8a08ff1
77cdd615-3ae9-4131-81e9-3fd14683abf8	21344405-4872-4ee8-9888-13f8964aa31b
e2404cc5-f582-4382-8332-0b7c74244edc	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
40451349-2101-4424-b5e9-9fea62885fa1	1db04be9-2c6e-4d20-bf79-0538f7be61ad
fe63b0e1-741c-4772-95a1-607058840923	194653e3-9f07-40ef-8739-79288cd0c5fb
d8d69419-414d-4066-a45f-a50d6b92770e	ad90ea9f-03a6-4c01-be4f-185e2e857c37
9009caab-2edb-460d-ab9d-10ddea2289d7	47a8fa0b-eba4-432d-9311-840714fab82f
85d627eb-0277-40f4-a577-94c0f6a17fa4	1db04be9-2c6e-4d20-bf79-0538f7be61ad
111d9d0d-3bc2-443e-b2d7-08a96d2470d9	64b72e33-6fb4-438a-95ad-f91d01f319c6
111d9d0d-3bc2-443e-b2d7-08a96d2470d9	e90222f5-b780-4dd3-97d0-3e41883ab59c
26d2cf05-ec3c-4b73-b971-d522b065286f	ad90ea9f-03a6-4c01-be4f-185e2e857c37
cd8496e4-e884-4c4b-9e7d-a539877374a2	c6f0709f-bda4-4610-893b-7481a67d5aa0
3b94bc06-eba3-4417-b7ae-a1aef4011452	cc0b398f-b20c-4041-99f2-64c6a1b0b3dc
c377a883-508f-453f-874c-dc108b1153a6	47a8fa0b-eba4-432d-9311-840714fab82f
2d561e62-3b72-4f1f-928a-9a3d8a96eb4d	60f5dc3f-5ee3-4cf1-942d-46f857167557
4fe2c01b-3229-44d4-ab91-a698af37d27b	c6f0709f-bda4-4610-893b-7481a67d5aa0
\.


--
-- Data for Name: rooms; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.rooms (id, abbreviation, capacity, identifier, name, fk_room_type_id, fk_timetable_id) FROM stdin;
c21974fa-ac26-47fd-8176-c0fafdd8958c	HS07	0	G0.08	Audimax	7090bc56-d4e6-449c-8724-c4d9c0528d61	52abe312-28b1-4ed3-a833-cc040ac6b8d6
1db04be9-2c6e-4d20-bf79-0538f7be61ad	HS01	0	C0.02	Hörsaal 1	7090bc56-d4e6-449c-8724-c4d9c0528d61	52abe312-28b1-4ed3-a833-cc040ac6b8d6
47a8fa0b-eba4-432d-9311-840714fab82f	HS02	0	c0.06	Hörsaal 2	7090bc56-d4e6-449c-8724-c4d9c0528d61	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ad90ea9f-03a6-4c01-be4f-185e2e857c37	HS03	0	D0.03	Hörsaal 3	7090bc56-d4e6-449c-8724-c4d9c0528d61	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5d649c42-9375-4457-8efb-05a7c5a28d67	HS04	0	D0.04	Hörsaal 4	7090bc56-d4e6-449c-8724-c4d9c0528d61	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c6f0709f-bda4-4610-893b-7481a67d5aa0	HS05	0	D2.18	Hörsaal 5	7090bc56-d4e6-449c-8724-c4d9c0528d61	52abe312-28b1-4ed3-a833-cc040ac6b8d6
cc0b398f-b20c-4041-99f2-64c6a1b0b3dc	HS06	0	E0.03	Hörsaal 6	7090bc56-d4e6-449c-8724-c4d9c0528d61	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7b96dff1-c476-48e8-bd98-d48b6517daad	SR03	0	A0.10	Agnesi	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3536e299-e129-4e7d-8770-ebbdb8a08ff1	SR05	0	A1.17	Agnodike	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
64b72e33-6fb4-438a-95ad-f91d01f319c6	SR06	0	A2.06	Ampère	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
21d94a17-0105-410a-8d25-012f4ca1d0a7	SR04	0	A0.13	Archimedes	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
067d6d06-48c4-4141-9d0a-5433aa3c1b07	SR02	0	A0.09	Aristotoeles	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
21344405-4872-4ee8-9888-13f8964aa31b	SR07	0	B0.03	Bassi	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c5c5dda1-f07c-4a03-a261-c46fadda5bdf	SR09	0	DU.05	Da Vinci	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
194653e3-9f07-40ef-8739-79288cd0c5fb	SR08	0	D0.02	Daimler	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
69e379db-288a-496e-afba-e7bfb2b01b47	SR11	0	D2.14	Doppler	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
f9aebaa0-8a37-481c-ae4d-b127d360fb99	SR10	0	D1.03	Doudna	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
2a5e0ed9-6730-459b-aefd-72bdd44bbc50	SR12	0	E1.07	Einstein	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
bc037e1b-c236-4377-b67a-4289f67123af	SR13	0	G0.12	Goodall	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d26177b6-2bb7-49cf-9aaf-9fff3866bf92	SR01	0	A0.06	Konferenzraum	75b5d955-55de-429d-aabe-5f4bbdb24b9e	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5550ecf7-8e09-4920-90b7-5216b27cf48a	LR33	0	G0.04	AV-Technik	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
e90222f5-b780-4dd3-97d0-3e41883ab59c	LR08	0	A2.11	CAE Labor	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b12bd00e-33b2-40dd-9b48-b74689855214	LR05	0	A1.13	Elektroniklabor	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
b1ef5a00-2611-4a35-aeb9-26e9bafc3385	LR26	0	D2.11	Mikrosystemtechnik	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
54bf6bc4-df73-42ce-9110-ab1a37512bff	LR32	0	F0.07	Netzwerkschulungslabor	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
60f5dc3f-5ee3-4cf1-942d-46f857167557	PC01	0	C0.05	PC-Pool 1	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
bcabbf8e-e7dc-4656-90a6-2bb7142f4cc1	PC02	0	E0.04	PC-Pool 2	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6a4e4c81-7dec-4276-b012-ad29c40135cb	PC03	0	E0.05	PC-Pool 3	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
85c6d68a-086a-47a0-8f67-558fbc913ce1	PC04	0	E0.11	PC-Pool 4	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
a3f7c3c2-7a68-42ae-8604-812b7a3394ae	PC05	0	E0.14	PC-Pool 5	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
7f1e31eb-994a-4eaa-9907-a84f2762ecc5	PC06	0	G1.09	PC-Pool 6	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
dc870dd7-4403-4244-aa58-71e74e36a89e	LR09	0	A2.14	Schaltungstechnik	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
ad8705ab-5f4d-406a-9173-6dd471b21ac6	LR04	0	A1.12	Smart Projekt	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6c1761d0-88b7-4026-b808-ee36315cced1	LR01	0	AU.05	Werkstatt	806357e8-ae11-4aba-acd6-0bc1f9b770a9	52abe312-28b1-4ed3-a833-cc040ac6b8d6
3b81ab24-9a7f-4969-9b2a-f209caa0e76e	HS07	0	G0.08	Audimax	7090bc56-d4e6-449c-8724-c4d9c0528d61	1ba2682f-29e0-4cb6-ac59-26f6275e875e
fd12523c-4b62-4ce6-a084-fa71d1599384	HS01	0	C0.02	Hörsaal 1	7090bc56-d4e6-449c-8724-c4d9c0528d61	1ba2682f-29e0-4cb6-ac59-26f6275e875e
57dfd594-4602-4c43-9fa8-927c814f9dd7	HS02	0	c0.06	Hörsaal 2	7090bc56-d4e6-449c-8724-c4d9c0528d61	1ba2682f-29e0-4cb6-ac59-26f6275e875e
4626e24e-76f5-4a5f-a4b3-969eeb65c53c	HS03	0	D0.03	Hörsaal 3	7090bc56-d4e6-449c-8724-c4d9c0528d61	1ba2682f-29e0-4cb6-ac59-26f6275e875e
fddfa368-fdba-4d9b-84c4-f3d3572ffaa6	HS04	0	D0.04	Hörsaal 4	7090bc56-d4e6-449c-8724-c4d9c0528d61	1ba2682f-29e0-4cb6-ac59-26f6275e875e
e9c13897-6f86-4479-931c-907479b82219	HS05	0	D2.18	Hörsaal 5	7090bc56-d4e6-449c-8724-c4d9c0528d61	1ba2682f-29e0-4cb6-ac59-26f6275e875e
2b40917e-17ef-49d1-8213-d7656e552b92	HS06	0	E0.03	Hörsaal 6	7090bc56-d4e6-449c-8724-c4d9c0528d61	1ba2682f-29e0-4cb6-ac59-26f6275e875e
ec634694-297b-47d1-9495-c35009978220	SR03	0	A0.10	Agnesi	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
eac841a7-3860-43c2-ac86-73451ab2fae0	SR05	0	A1.17	Agnodike	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
a9480a9e-2ca6-407e-9bf8-f39850a620eb	SR06	0	A2.06	Ampère	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
02361526-86f3-4f41-baa8-209219a0a77d	SR04	0	A0.13	Archimedes	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
8e8f1d6e-a219-407e-8b82-1c6848c97036	SR02	0	A0.09	Aristotoeles	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
829337e3-23f6-4c0f-ab87-e7556544d216	SR07	0	B0.03	Bassi	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
69bcd3ec-acee-4cf8-8c2d-133146bd419e	SR09	0	DU.05	Da Vinci	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
4dbb99bb-ad3d-4f69-982e-0e37973767ab	SR08	0	D0.02	Daimler	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
d589098a-0f9e-4ec9-ad58-0da5d18a37d7	SR11	0	D2.14	Doppler	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
5d9e569b-6655-48b6-8da0-99f50d099d68	SR10	0	D1.03	Doudna	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
688b3acf-bc39-4243-8048-b79c4052c0c4	SR12	0	E1.07	Einstein	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
3ccde59a-a7c5-4200-b664-a404a09adadc	SR13	0	G0.12	Goodall	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
0c2969e4-db50-4f82-8b12-700a165b20c7	SR01	0	A0.06	Konferenzraum	75b5d955-55de-429d-aabe-5f4bbdb24b9e	1ba2682f-29e0-4cb6-ac59-26f6275e875e
4e3652c6-9b6a-44b7-b5c9-85f5d38cd02b	LR33	0	G0.04	AV-Technik	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
5066da16-2315-4636-9871-972aa64d2d96	LR08	0	A2.11	CAE Labor	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
b5330291-05c8-47eb-bf78-0e738dd2fd70	LR05	0	A1.13	Elektroniklabor	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
78a81b55-7c7a-46a5-8c68-8f2751e6fc28	LR26	0	D2.11	Mikrosystemtechnik	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
09b26e8a-8de7-46a7-9aed-200ed07d9206	LR32	0	F0.07	Netzwerkschulungslabor	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
1cd154aa-13f3-42e7-9143-80d322387a7c	PC01	0	C0.05	PC-Pool 1	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
9c32d041-5994-493c-858c-e6b10972dae0	PC02	0	E0.04	PC-Pool 2	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
c69a69e8-4584-450a-b9b2-19fb203ed36a	PC03	0	E0.05	PC-Pool 3	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
018740cb-9127-4e2b-96c1-08d0f4278382	PC04	0	E0.11	PC-Pool 4	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
ea913e1e-d742-4d17-8e61-f06a04bef259	PC05	0	E0.14	PC-Pool 5	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
e15a4757-fbf3-4aaa-8217-9396829511c0	PC06	0	G1.09	PC-Pool 6	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
25bf5bc4-eec7-4816-a28d-1b0db9bc5e00	LR09	0	A2.14	Schaltungstechnik	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
2fd62e79-a2e9-4a70-9b6a-e27bc2a18f0b	LR04	0	A1.12	Smart Projekt	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
eee360b8-5881-43b6-ad6c-466ee30a5151	LR01	0	AU.05	Werkstatt	806357e8-ae11-4aba-acd6-0bc1f9b770a9	1ba2682f-29e0-4cb6-ac59-26f6275e875e
\.


--
-- Data for Name: school_types; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.school_types (id, name) FROM stdin;
842d7750-2e20-41d3-8872-d054f6fe07e5	Berufsfachschule
c04e628b-d72d-4c3f-b901-371ca68d9aea	Fachhochschule
\.


--
-- Data for Name: semester_types; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.semester_types (id, name) FROM stdin;
68da3786-3d67-4604-8696-65d631db4ed8	Sommersemester
3afe8e39-d98c-449e-aa57-878ce6134ac4	Wintersemester
\.


--
-- Data for Name: special_events; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.special_events (id, end_date, special_event_type, start_date, fk_timetable_id) FROM stdin;
b4e82aa6-d3a4-4a72-b3f4-62da6abba50d	2023-01-01	FREE	2022-12-24	52abe312-28b1-4ed3-a833-cc040ac6b8d6
80bdb2c5-f601-4583-8f1f-1af2ec1de113	2022-10-31	FREE	2022-10-31	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d0b93514-d9e2-4244-b5cc-f0148e6cf470	2022-11-23	FREE	2022-11-23	52abe312-28b1-4ed3-a833-cc040ac6b8d6
807b323a-5136-4d50-9542-a67ab975b4e2	2022-12-09	FREE	2022-12-09	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6d48d31f-d862-46f6-b2ba-b8be42e8720f	2023-01-19	FREE	2023-01-19	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5f8efb0b-2c0d-48dd-a46d-3e1a98f9a14a	2022-11-01	MONDAY_PLAN	2022-11-01	52abe312-28b1-4ed3-a833-cc040ac6b8d6
\.


--
-- Data for Name: timeslots; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.timeslots (id, end_time, index, start_time, fk_timetable_id) FROM stdin;
440c17ed-5262-4800-aa19-38e5285330c3	09:15:00	0	08:00:00	52abe312-28b1-4ed3-a833-cc040ac6b8d6
5f76a266-8d18-4da8-9732-b63d4d8f24a0	10:45:00	1	09:30:00	52abe312-28b1-4ed3-a833-cc040ac6b8d6
d54b130a-28a8-4bb1-9030-c474369c2205	12:15:00	2	11:00:00	52abe312-28b1-4ed3-a833-cc040ac6b8d6
dd55e7cd-195f-4d3c-a88c-2439ed3b855b	13:45:00	3	12:30:00	52abe312-28b1-4ed3-a833-cc040ac6b8d6
c33e01ef-be92-474a-9e43-0543649735d5	15:15:00	4	14:00:00	52abe312-28b1-4ed3-a833-cc040ac6b8d6
6f1475e5-0d8a-47c7-855f-ccff802aff85	16:45:00	5	15:30:00	52abe312-28b1-4ed3-a833-cc040ac6b8d6
58845959-0984-4136-a733-b75f5c9057b9	18:15:00	6	17:00:00	52abe312-28b1-4ed3-a833-cc040ac6b8d6
\.


--
-- Data for Name: timetables; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.timetables (id, end_date, name, number_of_weeks, start_date, fk_semester_type_id) FROM stdin;
52abe312-28b1-4ed3-a833-cc040ac6b8d6	2023-01-20	2022	12	2022-10-18	3afe8e39-d98c-449e-aa57-878ce6134ac4
1ba2682f-29e0-4cb6-ac59-26f6275e875e	2024-01-26	WS Test Raum Mitarbeiter	12	2023-10-02	3afe8e39-d98c-449e-aa57-878ce6134ac4
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.users (id, display_name, password, username) FROM stdin;
c498459c-3ec3-4d83-85cc-91298038bf69	Test	$2a$10$Av8tjzCsxXlrU71FStzCbucKQLnZtAQbj59zInPgqVX3E/687QbQm	test_account
\.


--
-- Data for Name: week_events_rooms; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.week_events_rooms (week_event_id, room_id) FROM stdin;
\.


--
-- Data for Name: week_events_timeslots; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.week_events_timeslots (week_event_id, timeslot_id) FROM stdin;
\.


--
-- Data for Name: worktimes; Type: TABLE DATA; Schema: public; Owner: db_username
--

COPY public.worktimes (id, weekday, fk_employee_id, fk_timeslot_id) FROM stdin;
78b14efc-3153-44e7-b792-5671f3c159e1	MONDAY	54b44371-4415-4b45-abd7-63b86369973c	440c17ed-5262-4800-aa19-38e5285330c3
16207756-1465-4f67-b396-d1b6d796bfe4	TUESDAY	54b44371-4415-4b45-abd7-63b86369973c	440c17ed-5262-4800-aa19-38e5285330c3
16b83e40-f219-4f44-94e6-e5e50a708755	WEDNESDAY	54b44371-4415-4b45-abd7-63b86369973c	440c17ed-5262-4800-aa19-38e5285330c3
960208e7-f346-4087-b6a6-24921eecf443	THURSDAY	54b44371-4415-4b45-abd7-63b86369973c	440c17ed-5262-4800-aa19-38e5285330c3
66439311-9c44-48e3-b10e-a67af50fb096	FRIDAY	54b44371-4415-4b45-abd7-63b86369973c	440c17ed-5262-4800-aa19-38e5285330c3
c62f4ade-5912-484c-b5fd-de58e4e1e8c6	MONDAY	54b44371-4415-4b45-abd7-63b86369973c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f45d20b9-acc7-4530-b941-45fd7f176c19	TUESDAY	54b44371-4415-4b45-abd7-63b86369973c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0d25fa03-97db-431d-9dbe-4a04d436b440	WEDNESDAY	54b44371-4415-4b45-abd7-63b86369973c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6b192790-8055-434c-a069-fca866b4d793	THURSDAY	54b44371-4415-4b45-abd7-63b86369973c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
06ba910f-8751-45ba-bc1a-3a3f35498da5	FRIDAY	54b44371-4415-4b45-abd7-63b86369973c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
67573f01-1cdd-42fb-84a0-19d726d3ca0f	MONDAY	54b44371-4415-4b45-abd7-63b86369973c	d54b130a-28a8-4bb1-9030-c474369c2205
011f1bbf-1911-4bb9-b085-9a137c81ea6f	TUESDAY	54b44371-4415-4b45-abd7-63b86369973c	d54b130a-28a8-4bb1-9030-c474369c2205
65131ba5-47ae-4373-b5a7-84d6510906a2	WEDNESDAY	54b44371-4415-4b45-abd7-63b86369973c	d54b130a-28a8-4bb1-9030-c474369c2205
767425bb-9ddc-4d1b-b66b-ebeea8c21565	THURSDAY	54b44371-4415-4b45-abd7-63b86369973c	d54b130a-28a8-4bb1-9030-c474369c2205
b9a5c373-1d7e-446f-beda-6e6a9be46212	FRIDAY	54b44371-4415-4b45-abd7-63b86369973c	d54b130a-28a8-4bb1-9030-c474369c2205
1e18877a-df75-4f86-8146-5c1b7e4628bb	MONDAY	54b44371-4415-4b45-abd7-63b86369973c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ac25d5d0-ed8d-4a5f-b975-cfb707e7964f	TUESDAY	54b44371-4415-4b45-abd7-63b86369973c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
81ee9584-f5fd-4636-a273-ae6993b2f355	WEDNESDAY	54b44371-4415-4b45-abd7-63b86369973c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6aa659b9-800b-447d-899c-e32a4ffa49c9	THURSDAY	54b44371-4415-4b45-abd7-63b86369973c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
14378f81-e6fb-424f-80ec-53c9794d3672	FRIDAY	54b44371-4415-4b45-abd7-63b86369973c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5b623968-edda-4498-bb3d-c5f5ad4a254e	MONDAY	54b44371-4415-4b45-abd7-63b86369973c	c33e01ef-be92-474a-9e43-0543649735d5
404ba2e4-a58a-4f82-b963-5e7791358f84	TUESDAY	54b44371-4415-4b45-abd7-63b86369973c	c33e01ef-be92-474a-9e43-0543649735d5
66e200fc-3eee-4a96-b625-db861d29b9a1	WEDNESDAY	54b44371-4415-4b45-abd7-63b86369973c	c33e01ef-be92-474a-9e43-0543649735d5
34ba3ac5-686b-4671-8701-f944240b7d81	THURSDAY	54b44371-4415-4b45-abd7-63b86369973c	c33e01ef-be92-474a-9e43-0543649735d5
d9e54df1-6ead-4141-b232-bb522f85d29d	FRIDAY	54b44371-4415-4b45-abd7-63b86369973c	c33e01ef-be92-474a-9e43-0543649735d5
bf42cb1d-79d7-4409-bcde-e251aea749d1	MONDAY	54b44371-4415-4b45-abd7-63b86369973c	6f1475e5-0d8a-47c7-855f-ccff802aff85
e9b67288-89ed-4842-a1e6-cdfe2985e41b	TUESDAY	54b44371-4415-4b45-abd7-63b86369973c	6f1475e5-0d8a-47c7-855f-ccff802aff85
80350ba1-3a2f-4bdb-b557-eb1df90902a0	WEDNESDAY	54b44371-4415-4b45-abd7-63b86369973c	6f1475e5-0d8a-47c7-855f-ccff802aff85
d8974c1c-2ca3-4d2c-b0dd-31c129587951	THURSDAY	54b44371-4415-4b45-abd7-63b86369973c	6f1475e5-0d8a-47c7-855f-ccff802aff85
efba3298-4136-4ffc-8fec-3545e3d723ee	FRIDAY	54b44371-4415-4b45-abd7-63b86369973c	6f1475e5-0d8a-47c7-855f-ccff802aff85
d761111d-3e1f-4892-ae33-4eeeff106158	MONDAY	54b44371-4415-4b45-abd7-63b86369973c	58845959-0984-4136-a733-b75f5c9057b9
070c860d-13a5-4f4f-9ccd-ee9171cd4e12	TUESDAY	54b44371-4415-4b45-abd7-63b86369973c	58845959-0984-4136-a733-b75f5c9057b9
b65c5df7-a814-4d71-a218-d46b3c9c6cc3	WEDNESDAY	54b44371-4415-4b45-abd7-63b86369973c	58845959-0984-4136-a733-b75f5c9057b9
1f989e7a-500f-417f-b054-f1f45e47053a	THURSDAY	54b44371-4415-4b45-abd7-63b86369973c	58845959-0984-4136-a733-b75f5c9057b9
b6f9c503-ddbf-4eae-9f3c-9b38179fda6f	FRIDAY	54b44371-4415-4b45-abd7-63b86369973c	58845959-0984-4136-a733-b75f5c9057b9
c2decc1b-57cf-42b3-a57a-7e9d9271608d	MONDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	440c17ed-5262-4800-aa19-38e5285330c3
0550912b-8d97-4f84-bf03-a405454611c4	TUESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	440c17ed-5262-4800-aa19-38e5285330c3
e284d231-b7e0-415d-b032-152fdc2b5c64	WEDNESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	440c17ed-5262-4800-aa19-38e5285330c3
a51a8ca8-2cf6-421a-9b7a-6c2f16dc8d28	THURSDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	440c17ed-5262-4800-aa19-38e5285330c3
c50fe047-9b5d-43d1-8d7a-b303cd9e69a6	FRIDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	440c17ed-5262-4800-aa19-38e5285330c3
739d32c7-eba3-4d98-bcae-9b8f4ffc19d0	MONDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6c3a28e7-acfe-4223-823b-03a44647dd26	TUESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5551b948-e230-4052-8365-5b1279d96acb	WEDNESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
dae797ca-91f5-489e-96d7-90813fed18c1	THURSDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
30232e9e-7db9-4066-acc8-7311dd06e409	FRIDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5b5ea980-fd42-4ed3-9d99-c864f300bf4a	MONDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	d54b130a-28a8-4bb1-9030-c474369c2205
2b418fe7-e83f-427d-b325-6ae122008f27	TUESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	d54b130a-28a8-4bb1-9030-c474369c2205
28903e48-abfa-4de7-afa6-30ff24fd94a0	WEDNESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	d54b130a-28a8-4bb1-9030-c474369c2205
d4592f1f-e3a9-47ef-bd6e-cdb67b61f075	THURSDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	d54b130a-28a8-4bb1-9030-c474369c2205
e608d2db-4fe8-4497-861f-748afcdb3570	FRIDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	d54b130a-28a8-4bb1-9030-c474369c2205
9ce5ba8f-06fa-4fe7-a33b-93b9b5b49e7a	MONDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0c90e63f-3724-4e42-b5d9-f0f481c9471c	TUESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
eb4ac583-dba7-4a11-88e8-e7769c0f5bc8	WEDNESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e2e99896-8f42-4619-8e58-8905273eab34	THURSDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e2c1fefe-a5fe-49a3-9d01-58c4f14740c3	FRIDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c5b08203-03dd-4533-946a-feab1a532feb	MONDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	c33e01ef-be92-474a-9e43-0543649735d5
b25acc69-039e-4c12-b990-cccbb631a0a3	TUESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	c33e01ef-be92-474a-9e43-0543649735d5
7b74e5e3-6fbb-42b4-b9c9-e32b7fb08dfa	WEDNESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	c33e01ef-be92-474a-9e43-0543649735d5
d28b3de1-4480-452b-afb8-2327def33f32	THURSDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	c33e01ef-be92-474a-9e43-0543649735d5
c1b69a54-4096-45ec-8c39-7f0eb7ade9de	FRIDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	c33e01ef-be92-474a-9e43-0543649735d5
c6e25fcb-4b94-43f7-b4be-06a59e07ece6	MONDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	6f1475e5-0d8a-47c7-855f-ccff802aff85
97f70762-b6a8-462b-a31c-172dc9cc4006	TUESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	6f1475e5-0d8a-47c7-855f-ccff802aff85
264a1c39-f799-47b8-8673-de32c66246da	WEDNESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	6f1475e5-0d8a-47c7-855f-ccff802aff85
5696e349-7c47-4b28-a87e-841f9d19ac9d	THURSDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	6f1475e5-0d8a-47c7-855f-ccff802aff85
36d5759a-5820-4395-a279-9aad239154eb	FRIDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	6f1475e5-0d8a-47c7-855f-ccff802aff85
1e324a97-0e02-4c3b-a117-38479db74bfb	MONDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	58845959-0984-4136-a733-b75f5c9057b9
23202874-1731-4104-ba6e-cf00b455f44a	TUESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	58845959-0984-4136-a733-b75f5c9057b9
46bf23b3-e7ee-4f11-a26b-f09059f6dc9b	WEDNESDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	58845959-0984-4136-a733-b75f5c9057b9
3aa677aa-7205-4687-a66e-bb0031666a71	THURSDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	58845959-0984-4136-a733-b75f5c9057b9
66f41d4a-7482-40d3-af19-f781e6d75107	FRIDAY	b8ed5474-d7e0-4144-b11d-3cf63cf9440d	58845959-0984-4136-a733-b75f5c9057b9
b2666d77-0bfb-419f-9d37-2a253e2cd87c	MONDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	440c17ed-5262-4800-aa19-38e5285330c3
5c013e77-a1ca-4b4d-a3b7-6c11bd43b00b	TUESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	440c17ed-5262-4800-aa19-38e5285330c3
7217804d-b2a0-4adb-8d3c-0a7e4dd0c7ca	WEDNESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	440c17ed-5262-4800-aa19-38e5285330c3
933eea77-3be2-400e-ad45-48f0aab564f3	THURSDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	440c17ed-5262-4800-aa19-38e5285330c3
d607272f-35a3-46b2-9767-6760d04770d6	FRIDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	440c17ed-5262-4800-aa19-38e5285330c3
d6beed7d-5153-457b-bf46-e060872d85f4	MONDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f689419f-d4cf-46cb-a062-17e1d13423a3	TUESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	5f76a266-8d18-4da8-9732-b63d4d8f24a0
15896301-48ba-41fd-8e30-2989b4297d99	WEDNESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1e0f1d45-580e-475f-8d0b-3d31d60d3d56	THURSDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f726e595-2f28-43f8-9748-5bfcfa434320	FRIDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c794e2c2-67fa-4f37-baa0-0fabba4c5574	MONDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	d54b130a-28a8-4bb1-9030-c474369c2205
f731a78a-24f3-470f-92f6-c5d68387c5cb	TUESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	d54b130a-28a8-4bb1-9030-c474369c2205
aeabc9f3-7369-430d-8537-54dfa0b5089d	WEDNESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	d54b130a-28a8-4bb1-9030-c474369c2205
86a316cd-c71f-4b5a-bd07-65e1f25cbaae	THURSDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	d54b130a-28a8-4bb1-9030-c474369c2205
0b297826-4114-4aa9-8394-7dadbd3a66d5	FRIDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	d54b130a-28a8-4bb1-9030-c474369c2205
c8d59cb3-f818-4ecf-9fff-3ec97440957d	MONDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2661c987-f79b-4b4a-99d9-40c717c85465	TUESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b1286f8e-2bc1-4001-9767-09b8f2fe23b3	WEDNESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
642787d0-0003-4603-8797-e4c1612a4af7	THURSDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5b570bdc-b826-4008-992f-36fefda52be7	FRIDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
749f41e2-c848-4c13-938c-6038d4c6a9c4	MONDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	c33e01ef-be92-474a-9e43-0543649735d5
ce514f49-782a-41e6-8931-a69134a7e5e7	TUESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	c33e01ef-be92-474a-9e43-0543649735d5
382d448e-2880-4a34-9d7f-7d9e26d5fe22	WEDNESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	c33e01ef-be92-474a-9e43-0543649735d5
3a5944d8-a205-4e55-b55a-c639f1b85d38	THURSDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	c33e01ef-be92-474a-9e43-0543649735d5
36ecb60d-3580-4e53-a051-4d5383f9b537	FRIDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	c33e01ef-be92-474a-9e43-0543649735d5
73228041-8e1e-485a-afba-9ebc258f18f8	MONDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	6f1475e5-0d8a-47c7-855f-ccff802aff85
18a83832-fdba-4f3a-a2f4-052e4f39fab9	TUESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	6f1475e5-0d8a-47c7-855f-ccff802aff85
7809a1c1-56bf-43e6-b6cb-b265775e7d24	WEDNESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	6f1475e5-0d8a-47c7-855f-ccff802aff85
c90d2a0f-0ede-466f-a2e3-c7b177c42837	THURSDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	6f1475e5-0d8a-47c7-855f-ccff802aff85
e3d339d7-c4b9-42b6-b928-2525cfde55f1	FRIDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	6f1475e5-0d8a-47c7-855f-ccff802aff85
d25d037b-aec7-4c61-b978-eacae5abe5f4	MONDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	58845959-0984-4136-a733-b75f5c9057b9
a6295b4f-fee3-4602-9b52-81cfdbc963dd	TUESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	58845959-0984-4136-a733-b75f5c9057b9
a747bb30-2eab-473f-b6a0-35af84ed1815	WEDNESDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	58845959-0984-4136-a733-b75f5c9057b9
742c6630-a2fd-4f7c-9d91-4a95436bde6e	THURSDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	58845959-0984-4136-a733-b75f5c9057b9
370ed8e2-1eb6-4f23-a3e6-a780438a84c2	FRIDAY	c78f81ae-bc4e-44f8-9e4b-63a16362ff01	58845959-0984-4136-a733-b75f5c9057b9
745b05a3-68fc-45b1-bccc-94faa8417272	MONDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	440c17ed-5262-4800-aa19-38e5285330c3
399ca748-7383-4ff2-a90b-0f3bb5f2787e	TUESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	440c17ed-5262-4800-aa19-38e5285330c3
51c7e689-62d1-4129-910f-32c712c4ee3c	WEDNESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	440c17ed-5262-4800-aa19-38e5285330c3
bf935ca2-52ee-4703-9158-09cc95b69cec	THURSDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	440c17ed-5262-4800-aa19-38e5285330c3
17c712d4-1731-49bb-a67a-faf0a749aaa6	FRIDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	440c17ed-5262-4800-aa19-38e5285330c3
68df6712-54aa-40a9-8e58-24ecdccdb9be	MONDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	5f76a266-8d18-4da8-9732-b63d4d8f24a0
864348f9-7bc8-4460-9b9f-0c3d05b4f5bf	TUESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8d9ea539-6400-498d-a387-289fde4228f3	WEDNESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	5f76a266-8d18-4da8-9732-b63d4d8f24a0
53f4192e-8fad-4cf0-8651-289b3f1371b6	THURSDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	5f76a266-8d18-4da8-9732-b63d4d8f24a0
95fe4fb2-038c-4a6c-bcbd-69302866e76d	FRIDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	5f76a266-8d18-4da8-9732-b63d4d8f24a0
44f62540-5ca7-48bb-8726-0340b5a70515	MONDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	d54b130a-28a8-4bb1-9030-c474369c2205
4b315820-a7da-41d1-8605-5dc68df342c5	TUESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	d54b130a-28a8-4bb1-9030-c474369c2205
429a9261-24fb-4d53-ba0c-5ae577908980	WEDNESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	d54b130a-28a8-4bb1-9030-c474369c2205
bcfdb42b-9662-4c24-887f-baea9ab30ec2	THURSDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	d54b130a-28a8-4bb1-9030-c474369c2205
30c30a21-8181-4803-875e-9b337a1ec7cf	FRIDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	d54b130a-28a8-4bb1-9030-c474369c2205
d98e84d1-30de-4ab1-8983-fc2cba28dc68	MONDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f45bbdfa-4e0c-4525-a248-d3828d3c217b	TUESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
636f75c1-322a-45b6-b29a-e3d6b940f460	WEDNESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a5c5599c-cdfc-4e40-9b2a-e35bb1a0c385	THURSDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
62560ce4-d548-49ed-87e7-45f592d27176	FRIDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8d379154-a167-4824-bdde-c3132391de2f	MONDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	c33e01ef-be92-474a-9e43-0543649735d5
ce1f1f88-fb40-4821-b529-70bd9563b0cd	TUESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	c33e01ef-be92-474a-9e43-0543649735d5
e2b7d6b9-1e07-4005-86ad-040228e9b74d	WEDNESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	c33e01ef-be92-474a-9e43-0543649735d5
55dc6272-5e53-4f53-a7fe-d8590d40d156	THURSDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	c33e01ef-be92-474a-9e43-0543649735d5
90aee21c-a6f9-47b8-a475-72d3e4224798	FRIDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	c33e01ef-be92-474a-9e43-0543649735d5
91448788-35a1-4259-9ef8-c4cb707b7222	MONDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	6f1475e5-0d8a-47c7-855f-ccff802aff85
adfcc54a-ec90-4cc3-8bae-8a0c64867d2e	TUESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	6f1475e5-0d8a-47c7-855f-ccff802aff85
00c9434d-8210-499b-83f6-b1c7cb05bc28	WEDNESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	6f1475e5-0d8a-47c7-855f-ccff802aff85
5a73e611-0595-45dd-8d8c-08ab7c586cbb	THURSDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	6f1475e5-0d8a-47c7-855f-ccff802aff85
6684248c-3f70-4dbe-98c9-e8d38829935b	FRIDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	6f1475e5-0d8a-47c7-855f-ccff802aff85
5bb8f073-6ba3-4633-8bd4-c18b50ce7c50	MONDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	58845959-0984-4136-a733-b75f5c9057b9
ccb361f1-74a8-4913-8362-eb2a3bf10a84	TUESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	58845959-0984-4136-a733-b75f5c9057b9
bf64b0f7-45dd-45c2-923a-368e1778f42c	WEDNESDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	58845959-0984-4136-a733-b75f5c9057b9
8a8cf18c-03bd-4d43-bacb-569f2bc4b506	THURSDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	58845959-0984-4136-a733-b75f5c9057b9
bf14e15e-f9fe-41d4-a898-6231cc576ffd	FRIDAY	05cb8a03-d290-4afb-befb-dac6f0e50004	58845959-0984-4136-a733-b75f5c9057b9
31a48cc8-af2d-4d8e-b8de-51afa92c9a8e	MONDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	440c17ed-5262-4800-aa19-38e5285330c3
736c4681-b00f-4e4c-b9a0-2ac399f0aa52	TUESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	440c17ed-5262-4800-aa19-38e5285330c3
1e17dcfa-cfb5-4e90-91bc-acad2733f06f	WEDNESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	440c17ed-5262-4800-aa19-38e5285330c3
e6b91cc8-0589-4c30-a3a6-65657a81130c	THURSDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	440c17ed-5262-4800-aa19-38e5285330c3
b4eadb90-b4f2-4a45-adb8-78fbb1f3ccae	FRIDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	440c17ed-5262-4800-aa19-38e5285330c3
a2896c5c-ddb3-40cd-83f3-ed7399010933	MONDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
92023217-7993-4f75-9942-1b5730e25854	TUESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
24b229fd-9aaf-4473-9d1d-349ed038f475	WEDNESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
12d62023-4be4-4c03-85f3-7f1e6a15c6a2	THURSDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2c1162de-1d18-45f0-a592-047243cbcb3a	FRIDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
41cc6882-9155-4238-8bf8-efab6da63788	MONDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	d54b130a-28a8-4bb1-9030-c474369c2205
40b8fef2-4ad6-4d1f-a2a3-e1478034799d	TUESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	d54b130a-28a8-4bb1-9030-c474369c2205
afb611b0-20a8-46ec-ae42-54ffde7ab21e	WEDNESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	d54b130a-28a8-4bb1-9030-c474369c2205
07cc5d9f-a16c-4ddf-aa95-cba28d561ef2	THURSDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	d54b130a-28a8-4bb1-9030-c474369c2205
779bfe7d-6a0b-430d-a106-393f78db2576	FRIDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	d54b130a-28a8-4bb1-9030-c474369c2205
f74639e9-fb9d-44a3-ac19-14517a29face	MONDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c79acc50-ff8a-4388-acb4-291b19eb3f3f	TUESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f1f84074-b806-4845-815f-1ec3d4aa968d	WEDNESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ddf82b42-56fb-4d3e-aa98-55d8675cb31f	THURSDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
14f90842-ff34-420a-82a4-fec6666ce591	FRIDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ddd86828-ebb0-4d6b-99e9-e6b9eb4404f5	MONDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	c33e01ef-be92-474a-9e43-0543649735d5
b8622fd4-fd60-46f3-9169-f3cd60689f63	TUESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	c33e01ef-be92-474a-9e43-0543649735d5
55be8e94-f2be-4ad8-9b4b-2adaded72163	WEDNESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	c33e01ef-be92-474a-9e43-0543649735d5
a05f8a8d-f971-451f-a5e8-3f8a59c6de3f	THURSDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	c33e01ef-be92-474a-9e43-0543649735d5
5c41e591-094b-459e-9f3f-101871d8826a	FRIDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	c33e01ef-be92-474a-9e43-0543649735d5
c3a49623-a385-4d97-b817-ba26ae15bcbb	MONDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
26c4491d-d5c4-40cd-a376-265bfeea5d7f	TUESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
433e8445-2f5a-431f-ad4d-79e8f4b60db0	WEDNESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
11524037-8b82-498e-94bc-0124d088bad6	THURSDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
582e5dea-d1ac-4ac3-bcf3-30414b4e2a1f	FRIDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
7cc3ec8b-9e8c-4919-81dc-c244f9e89f04	MONDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	58845959-0984-4136-a733-b75f5c9057b9
565a0702-e744-4a74-a826-b9ee40711b89	TUESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	58845959-0984-4136-a733-b75f5c9057b9
6dc24714-c874-417e-a054-18a76b5905db	WEDNESDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	58845959-0984-4136-a733-b75f5c9057b9
2160cb3d-de69-4720-ab99-47b20ba2459e	THURSDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	58845959-0984-4136-a733-b75f5c9057b9
39b8ae0e-1e2a-4f9f-b921-889e51a2e22b	FRIDAY	c64363a0-0a95-46c9-a914-7ec718f016cf	58845959-0984-4136-a733-b75f5c9057b9
097d7b04-6009-4b0d-9a3e-775615eb49bb	MONDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	440c17ed-5262-4800-aa19-38e5285330c3
2347e42f-03cb-4d9e-bfe9-c7a0362c5443	TUESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	440c17ed-5262-4800-aa19-38e5285330c3
1fe3f8ec-f3ee-4bb9-84fd-fc31e8f7b768	WEDNESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	440c17ed-5262-4800-aa19-38e5285330c3
7deec5e1-e56c-4cd4-b250-74af1fb33361	THURSDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	440c17ed-5262-4800-aa19-38e5285330c3
6879980c-2da7-4673-aaac-019f3c63bec6	FRIDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	440c17ed-5262-4800-aa19-38e5285330c3
a4620dd4-b632-4686-984e-e450ae5ea42b	MONDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	5f76a266-8d18-4da8-9732-b63d4d8f24a0
145359f2-ef7b-4229-b10a-ace2a58e3b47	TUESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	5f76a266-8d18-4da8-9732-b63d4d8f24a0
69d05307-7e9f-4c10-b072-bbfc3563a1db	WEDNESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ec73b298-c00f-455f-a259-b36392ea3bcd	THURSDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f0c62481-d12d-4e8d-8f83-3e09fb5bad65	FRIDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	5f76a266-8d18-4da8-9732-b63d4d8f24a0
911f42e8-9eea-45e0-8a06-9679f3c927a9	MONDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	d54b130a-28a8-4bb1-9030-c474369c2205
9602de0b-42cf-42e1-b553-6e2b080b7972	TUESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	d54b130a-28a8-4bb1-9030-c474369c2205
f24876b7-1f05-48a9-9e2e-3cb42aff3a32	WEDNESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	d54b130a-28a8-4bb1-9030-c474369c2205
60105ffc-298b-47f3-96a9-2a8178328f3c	THURSDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	d54b130a-28a8-4bb1-9030-c474369c2205
42f69184-8f54-497a-be76-0869d925bc17	FRIDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	d54b130a-28a8-4bb1-9030-c474369c2205
2989ec5d-79ee-4add-85c4-a4f661942b4e	MONDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
671dcfb9-a8da-4858-bb64-8a676137d9b7	TUESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0bc8ccb5-908d-40a1-961c-0ae91f7c49f5	WEDNESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6a0323af-147c-4570-a7ea-a610d7c26034	THURSDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
635bb6da-3a4b-4625-b8c1-e976575afce7	FRIDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
13450262-66c0-4c20-8b0e-d99cf12ac2f0	MONDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	c33e01ef-be92-474a-9e43-0543649735d5
6c3b6d40-6971-4641-b92a-4e5e8594ef41	TUESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	c33e01ef-be92-474a-9e43-0543649735d5
fe4ef627-56c2-4ee3-b94d-7a5e6a938ac1	WEDNESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	c33e01ef-be92-474a-9e43-0543649735d5
a3833fbf-5197-4d9f-99a7-92000014badb	THURSDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	c33e01ef-be92-474a-9e43-0543649735d5
959bba57-0338-4fa7-92e9-d187d3f59a03	FRIDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	c33e01ef-be92-474a-9e43-0543649735d5
98fd7b13-7902-41a7-af52-7ff8eb4637e8	MONDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	6f1475e5-0d8a-47c7-855f-ccff802aff85
31dd57e5-6f87-46a0-a222-1311ce47a58c	TUESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	6f1475e5-0d8a-47c7-855f-ccff802aff85
d611a600-4d91-4785-a225-2c56e0e4ca88	WEDNESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	6f1475e5-0d8a-47c7-855f-ccff802aff85
0b3fa155-ccb3-42db-9b24-241fe1a67c18	THURSDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	6f1475e5-0d8a-47c7-855f-ccff802aff85
a9a8d71b-eb0d-4c6e-b6b2-bd0be98cd85d	FRIDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	6f1475e5-0d8a-47c7-855f-ccff802aff85
f6e03e77-c51e-4ce3-bef0-e65eabe71136	MONDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	58845959-0984-4136-a733-b75f5c9057b9
e3d567cd-22c9-4f5c-bcda-4d313d3d2e08	TUESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	58845959-0984-4136-a733-b75f5c9057b9
42905d6f-72ad-4c23-acee-92d2fe0dc62a	WEDNESDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	58845959-0984-4136-a733-b75f5c9057b9
1550cbd1-945f-4aba-9ea5-7695ce470b41	THURSDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	58845959-0984-4136-a733-b75f5c9057b9
75356e7d-623b-41d7-8956-4eb64adf40ca	FRIDAY	23f96a10-3b56-4dba-a5a1-bc8e4c071869	58845959-0984-4136-a733-b75f5c9057b9
be25a6d1-a146-4317-b420-89159d7725de	MONDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	440c17ed-5262-4800-aa19-38e5285330c3
e37cc3ce-ba97-45b1-9691-2ea765ee97d1	TUESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	440c17ed-5262-4800-aa19-38e5285330c3
66e2a023-9164-413a-a7aa-d1acac98c824	WEDNESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	440c17ed-5262-4800-aa19-38e5285330c3
77dcf7bc-8afc-4c70-b568-cb657a7c6c61	THURSDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	440c17ed-5262-4800-aa19-38e5285330c3
68ad6d36-296d-4df8-8236-49e1bf16ae2a	FRIDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	440c17ed-5262-4800-aa19-38e5285330c3
20044742-4c7f-4c4f-b710-ac2bab65dfd8	MONDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4808da48-b380-486d-9b8b-22e4dd287772	TUESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
775d26bc-c3f7-4341-b67b-4341a04a7448	WEDNESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0828cd0e-e2b6-4392-9d51-8dc155eb90f5	THURSDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
15ee9e5a-54bf-48dc-ba0d-823eb98d95ed	FRIDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3ccac26c-4695-4d44-90c9-09cc48b79d83	MONDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	d54b130a-28a8-4bb1-9030-c474369c2205
82cb93c6-d575-4aef-a81a-837af38ff97d	TUESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	d54b130a-28a8-4bb1-9030-c474369c2205
3469dab5-af07-429e-9c0d-c47495344576	WEDNESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	d54b130a-28a8-4bb1-9030-c474369c2205
cd7c5b10-5f85-4bb6-826a-2262643ecea4	THURSDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	d54b130a-28a8-4bb1-9030-c474369c2205
7b425e15-1127-43e0-a9d5-fcff607609a4	FRIDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	d54b130a-28a8-4bb1-9030-c474369c2205
6c5b203c-40f1-4a50-8722-acf78365c874	MONDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f59d53e0-30f6-48a1-8eea-08fb22c85f3e	TUESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3dc40009-d23f-4fa4-9fbf-a7b98f99ce28	WEDNESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1ce3a531-fed3-4c12-bb46-a6743e0f2b79	THURSDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
63e9c0ec-616a-48bb-ad4b-d12ca270760c	FRIDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5c14b9f1-b520-4346-948a-0dca08d924c4	MONDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	c33e01ef-be92-474a-9e43-0543649735d5
069bd50f-615f-47d4-add3-8eb715c2f8c2	TUESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	c33e01ef-be92-474a-9e43-0543649735d5
7315869e-7999-4477-a975-2c313bc01be0	WEDNESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	c33e01ef-be92-474a-9e43-0543649735d5
f40513f5-97b0-48ce-8e16-487d5d6ad40b	THURSDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	c33e01ef-be92-474a-9e43-0543649735d5
82fb873d-77c6-4805-b301-8d72d561b3c0	FRIDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	c33e01ef-be92-474a-9e43-0543649735d5
10b4fd60-406a-4734-a08a-9cae1146216f	MONDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	6f1475e5-0d8a-47c7-855f-ccff802aff85
d2017d09-dd29-44d3-af10-4820a044f73c	TUESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	6f1475e5-0d8a-47c7-855f-ccff802aff85
27bc0f1d-2dbe-46c8-b9fa-e8b7465a8a1a	WEDNESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	6f1475e5-0d8a-47c7-855f-ccff802aff85
086283d4-e5d1-493c-b0ff-3faeffba4b6e	THURSDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	6f1475e5-0d8a-47c7-855f-ccff802aff85
9a5eb05b-ea70-4550-8367-e02c9722d160	FRIDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	6f1475e5-0d8a-47c7-855f-ccff802aff85
ba5a1bc2-3077-4fac-b49c-8d8936a765aa	MONDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	58845959-0984-4136-a733-b75f5c9057b9
91a7163a-7a13-4384-8ada-50fe3b7e980e	TUESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	58845959-0984-4136-a733-b75f5c9057b9
849fa916-9acf-4f27-87e4-89268cf0cd7a	WEDNESDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	58845959-0984-4136-a733-b75f5c9057b9
ad9ae562-e081-4f84-aafc-cb78299dc6d1	THURSDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	58845959-0984-4136-a733-b75f5c9057b9
de9c62a2-dbb7-4315-97e6-ff7f9d8fd169	FRIDAY	393058cb-870a-45ee-ab55-5e47c84d10b2	58845959-0984-4136-a733-b75f5c9057b9
9d9a84b5-c762-49b1-b31f-06ea7d781f2e	MONDAY	d63f5f80-19aa-4d82-b825-69beb8129457	440c17ed-5262-4800-aa19-38e5285330c3
ac4654c8-18fc-41ad-800e-0d617f722ba9	TUESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	440c17ed-5262-4800-aa19-38e5285330c3
4636d765-882a-4581-bdc3-1740c1792914	WEDNESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	440c17ed-5262-4800-aa19-38e5285330c3
1f0086a7-d604-4ff4-81be-32edaed18368	THURSDAY	d63f5f80-19aa-4d82-b825-69beb8129457	440c17ed-5262-4800-aa19-38e5285330c3
bcdd50d1-044c-4a7e-86c2-9d2c648de0b3	FRIDAY	d63f5f80-19aa-4d82-b825-69beb8129457	440c17ed-5262-4800-aa19-38e5285330c3
bb8e11cb-bda7-48bb-a99c-b9db17abeb42	MONDAY	d63f5f80-19aa-4d82-b825-69beb8129457	5f76a266-8d18-4da8-9732-b63d4d8f24a0
53fd0631-e37b-4557-94ed-f0dc3fb62cd7	TUESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5d3be14d-a340-4301-9fbc-47036ad17ebb	WEDNESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1b8274b6-3f5d-48aa-9ccc-6a24a4260131	THURSDAY	d63f5f80-19aa-4d82-b825-69beb8129457	5f76a266-8d18-4da8-9732-b63d4d8f24a0
735017a0-70d0-466e-93fd-3d5b881d5efb	FRIDAY	d63f5f80-19aa-4d82-b825-69beb8129457	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bdc08413-10e0-45d0-8b0a-159316887e2e	MONDAY	d63f5f80-19aa-4d82-b825-69beb8129457	d54b130a-28a8-4bb1-9030-c474369c2205
27ccc8af-5a8d-40fe-90c2-19b6f909b9e7	TUESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	d54b130a-28a8-4bb1-9030-c474369c2205
d1e9090a-6151-4387-9f77-772d5110b2c7	WEDNESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	d54b130a-28a8-4bb1-9030-c474369c2205
34dcb815-4fc5-4026-a9ac-bffdb2da7bf1	THURSDAY	d63f5f80-19aa-4d82-b825-69beb8129457	d54b130a-28a8-4bb1-9030-c474369c2205
03a7a94d-b753-49cc-bd4d-b3be09289c1e	FRIDAY	d63f5f80-19aa-4d82-b825-69beb8129457	d54b130a-28a8-4bb1-9030-c474369c2205
02f028ec-69a5-49fc-9ad7-69e74d96c82c	MONDAY	d63f5f80-19aa-4d82-b825-69beb8129457	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6c325a6b-2a43-4208-aa9d-a2191bebd6d7	TUESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
74f60fdc-158c-4d0c-979c-ff460e8adfb6	WEDNESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e56e22c7-c13c-451b-bb28-5b0b30f943d4	THURSDAY	d63f5f80-19aa-4d82-b825-69beb8129457	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b7aee8c9-880d-48e7-98bf-f3b55d37f689	FRIDAY	d63f5f80-19aa-4d82-b825-69beb8129457	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e77d83e0-c368-4c0e-8f34-024843e074b7	MONDAY	d63f5f80-19aa-4d82-b825-69beb8129457	c33e01ef-be92-474a-9e43-0543649735d5
182619bd-0d82-4b4e-bdf9-731b41cdb8d4	TUESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	c33e01ef-be92-474a-9e43-0543649735d5
268348d1-418e-4cdf-8018-aa91f76a9972	WEDNESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	c33e01ef-be92-474a-9e43-0543649735d5
f4496bb9-781b-4a12-9422-0cecadcf3690	THURSDAY	d63f5f80-19aa-4d82-b825-69beb8129457	c33e01ef-be92-474a-9e43-0543649735d5
a57feb7d-0c34-45ee-8b64-01417fe8e21a	FRIDAY	d63f5f80-19aa-4d82-b825-69beb8129457	c33e01ef-be92-474a-9e43-0543649735d5
3c824361-f325-419b-9f80-cd3be15cb52d	MONDAY	d63f5f80-19aa-4d82-b825-69beb8129457	6f1475e5-0d8a-47c7-855f-ccff802aff85
3ab3df10-479b-4d5d-b9cd-77e4896b0aef	TUESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	6f1475e5-0d8a-47c7-855f-ccff802aff85
b998e34e-282e-4575-a682-a7cb82c1ac44	WEDNESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	6f1475e5-0d8a-47c7-855f-ccff802aff85
453d9d1a-b755-4db1-8990-b2c97dfc1347	THURSDAY	d63f5f80-19aa-4d82-b825-69beb8129457	6f1475e5-0d8a-47c7-855f-ccff802aff85
121d7a66-3325-4b7b-8189-271041e9c69b	FRIDAY	d63f5f80-19aa-4d82-b825-69beb8129457	6f1475e5-0d8a-47c7-855f-ccff802aff85
58548252-2f7a-4235-a1ee-4aff0ae3f0b4	MONDAY	d63f5f80-19aa-4d82-b825-69beb8129457	58845959-0984-4136-a733-b75f5c9057b9
25f8cde7-948e-491f-a088-449684c9d19c	TUESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	58845959-0984-4136-a733-b75f5c9057b9
778f69eb-1161-49eb-959e-2618a633db04	WEDNESDAY	d63f5f80-19aa-4d82-b825-69beb8129457	58845959-0984-4136-a733-b75f5c9057b9
f71ecf86-5191-4d80-8434-07233c99d1ff	THURSDAY	d63f5f80-19aa-4d82-b825-69beb8129457	58845959-0984-4136-a733-b75f5c9057b9
887530d3-aef3-475b-b0f2-4d12869fa876	MONDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	440c17ed-5262-4800-aa19-38e5285330c3
68724175-24d7-44b7-86e8-8ccebc47942a	TUESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	440c17ed-5262-4800-aa19-38e5285330c3
5cc5b986-fd5f-4b66-8c95-247a9d2f18d1	WEDNESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	440c17ed-5262-4800-aa19-38e5285330c3
855e49f9-1950-4122-8ebb-49d4bf24f42b	THURSDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	440c17ed-5262-4800-aa19-38e5285330c3
725eb791-bab8-4e58-9d4b-86f278c61b8e	FRIDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	440c17ed-5262-4800-aa19-38e5285330c3
f6706a30-bf15-4ff9-bfbf-c87f2304ddb9	MONDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ad464b4a-1c6f-4db2-a52d-40d515ded764	TUESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fb4f158e-fd06-40e9-943d-7297ae110fca	WEDNESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c9adefac-ed89-4b33-8aa2-2ac57c243391	THURSDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
809ea1b2-c06e-4fe8-b4ea-cab33ecb6332	FRIDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cc75a4ae-1a3f-4508-92c5-88bd4b782e08	MONDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	d54b130a-28a8-4bb1-9030-c474369c2205
de8796f3-5865-4928-8c6b-45ad707977d9	TUESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	d54b130a-28a8-4bb1-9030-c474369c2205
61618898-d475-4dce-be64-99bf454bb39c	WEDNESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	d54b130a-28a8-4bb1-9030-c474369c2205
4dc6a01c-cfaf-437b-a8f2-3d1f81f464f1	THURSDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	d54b130a-28a8-4bb1-9030-c474369c2205
faecaae4-2f91-47fb-8afa-0e669e1c71b9	FRIDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	d54b130a-28a8-4bb1-9030-c474369c2205
2634067e-2c02-4ac1-bb25-d640cad95d63	MONDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c8958320-fe79-427f-aaca-ed172f8618d3	TUESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ec9f6488-e3b7-4aa7-82cd-7471847732ad	WEDNESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
94865baf-7a62-4a99-b7cb-7e9e2a719e99	THURSDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
aeec5067-a6ca-419e-86af-36cc4f04ccb0	FRIDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b2771e61-e90b-4f81-abe6-0d3d4ae61ca1	MONDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	c33e01ef-be92-474a-9e43-0543649735d5
7a25384c-3fd4-4fe6-9004-59793b437da8	TUESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	c33e01ef-be92-474a-9e43-0543649735d5
a3da4f0b-f204-4046-969d-d14211e7524d	WEDNESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	c33e01ef-be92-474a-9e43-0543649735d5
686ba398-14f6-4d80-ad44-3dea8e5a6069	THURSDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	c33e01ef-be92-474a-9e43-0543649735d5
bd617683-b528-4f05-ab9b-85f1aa82b80d	FRIDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	c33e01ef-be92-474a-9e43-0543649735d5
37ebd417-b1dd-4531-8bac-b48556208c31	MONDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	6f1475e5-0d8a-47c7-855f-ccff802aff85
ccca5218-2c22-4b62-9505-575da413b0b7	TUESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	6f1475e5-0d8a-47c7-855f-ccff802aff85
673a9769-6118-445d-b19d-cb06cd20fa8c	WEDNESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	6f1475e5-0d8a-47c7-855f-ccff802aff85
1652da90-9df2-41e3-a270-475ad875889a	THURSDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	6f1475e5-0d8a-47c7-855f-ccff802aff85
d95f5339-232e-4aed-abc3-cf35ed7221ee	FRIDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	6f1475e5-0d8a-47c7-855f-ccff802aff85
4a17a654-e9f5-41e1-ae27-860ae3b2bc4b	MONDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	58845959-0984-4136-a733-b75f5c9057b9
401b9a86-88fa-47fe-bbec-e03e142544ee	TUESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	58845959-0984-4136-a733-b75f5c9057b9
2eaa9f84-bde0-4ad0-9ed8-86a28f8a5c19	WEDNESDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	58845959-0984-4136-a733-b75f5c9057b9
42f42484-ab72-4736-8bf7-028c034045d7	THURSDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	58845959-0984-4136-a733-b75f5c9057b9
58f9730f-fcde-459d-a5e7-1d8196bd30fd	FRIDAY	f270d266-40ec-4e7d-87c2-a39d000c2a0b	58845959-0984-4136-a733-b75f5c9057b9
b1781819-4f8c-437a-b03a-7357aebc90c3	FRIDAY	d63f5f80-19aa-4d82-b825-69beb8129457	58845959-0984-4136-a733-b75f5c9057b9
29f1a535-058b-4f47-a306-1106fc3b8884	FRIDAY	39b4633f-81a5-46df-944c-b29a91f38b14	58845959-0984-4136-a733-b75f5c9057b9
3c50b0d1-8427-4862-a7d5-490f569e97e0	MONDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	440c17ed-5262-4800-aa19-38e5285330c3
897d2769-b5f9-4181-a455-55177cc54ac1	TUESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	440c17ed-5262-4800-aa19-38e5285330c3
682b5127-e954-43a3-9f58-d77eab766d43	WEDNESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	440c17ed-5262-4800-aa19-38e5285330c3
818a5f28-3837-4ee7-b90d-be25b6474be9	THURSDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	440c17ed-5262-4800-aa19-38e5285330c3
e3d3852b-66cf-4528-b848-ea4717d22a41	FRIDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	440c17ed-5262-4800-aa19-38e5285330c3
786768b5-1f01-45b2-8b41-fcacca0524f4	MONDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fa48ef25-df7b-418a-975a-a65d428fb1d6	TUESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0a94cf2d-8a1b-4666-b21c-107ba9dcc364	WEDNESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
59d5507c-da4f-42d3-849c-93774de805ef	THURSDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
094d1eee-db2b-431c-897f-788c2f930949	FRIDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5401cb88-0052-4104-b1d1-b6467fca02f5	MONDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	d54b130a-28a8-4bb1-9030-c474369c2205
3e6c8c05-0ba3-4879-90f9-60fe82a4e3f4	TUESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	d54b130a-28a8-4bb1-9030-c474369c2205
332240b7-8fd9-4ddc-bceb-1b2a01a9821f	WEDNESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	d54b130a-28a8-4bb1-9030-c474369c2205
113810d4-434f-487b-84b7-84337bd89f8c	THURSDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	d54b130a-28a8-4bb1-9030-c474369c2205
de68b2d4-309c-439e-a6c8-a91f7d27076b	FRIDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	d54b130a-28a8-4bb1-9030-c474369c2205
0ba0249f-24fd-403b-8b6f-6166afcbb71b	MONDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1ee97f9e-625c-4655-aec1-ef6b9a4a64b4	TUESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c3817ebe-f013-4248-857e-fd12f8553a1e	WEDNESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1ceeb818-2901-435d-9563-47f10683af51	THURSDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
99f3049b-bdd9-4ab5-9f61-b44498f814aa	FRIDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bb6a8fa7-1095-4251-bd1c-08ff1d02d5bf	MONDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	c33e01ef-be92-474a-9e43-0543649735d5
0786835d-f65f-498a-8d1e-2b0794356444	TUESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	c33e01ef-be92-474a-9e43-0543649735d5
d5d0634f-0667-4f26-a62f-c88227750887	WEDNESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	c33e01ef-be92-474a-9e43-0543649735d5
a19baeee-3d08-40f9-8496-e2a4b970a3e1	THURSDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	c33e01ef-be92-474a-9e43-0543649735d5
e84b55b3-a400-4f0b-85b9-888c35d47c93	FRIDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	c33e01ef-be92-474a-9e43-0543649735d5
19efcdc4-4e83-403a-a90c-6dde2ca19f91	MONDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
b35433e4-cb58-4432-9ec5-34e640f8f5bf	TUESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
e26e6b94-392e-490b-bfd8-f18fb5ab0806	WEDNESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
ca2a9a6c-6a6d-402c-b065-b9d7315ccbdc	THURSDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
7bb6267f-a5d6-49af-8b2e-a2d0780dcebc	FRIDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	6f1475e5-0d8a-47c7-855f-ccff802aff85
126fe803-df49-431a-91e4-21c7415adf75	MONDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	58845959-0984-4136-a733-b75f5c9057b9
7da61d83-a5a5-45a8-a89e-5697739615b8	TUESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	58845959-0984-4136-a733-b75f5c9057b9
29de362d-8316-42a6-96a7-93781e12d359	WEDNESDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	58845959-0984-4136-a733-b75f5c9057b9
13e78b38-339d-465a-b36f-a3689430537b	MONDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	440c17ed-5262-4800-aa19-38e5285330c3
34f0d213-c77f-4442-9a14-a618175fc15f	TUESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	440c17ed-5262-4800-aa19-38e5285330c3
93d8e14f-e1d1-4564-9e4c-bbdacc97d3a0	WEDNESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	440c17ed-5262-4800-aa19-38e5285330c3
dd51c182-5b99-434f-9767-85f5cba5d857	THURSDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	440c17ed-5262-4800-aa19-38e5285330c3
08ca059b-378c-4646-bb8e-b9c709f2cbab	FRIDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	440c17ed-5262-4800-aa19-38e5285330c3
6cd4e27c-fd7a-4432-9457-c75676235070	MONDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
57d03d67-b577-438b-8214-f2a429b6316c	TUESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a791df16-8322-46cd-9225-ad0e3b7e7032	WEDNESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6a7a9afc-01ff-4110-85d3-5ba60438f141	THURSDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
da7abbdd-5828-4f9e-9366-fd6b13036608	FRIDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ab2f796f-be30-4947-be47-33713f878636	MONDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	d54b130a-28a8-4bb1-9030-c474369c2205
5ff79c81-f9bd-4eb4-831e-89224290cbfd	TUESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	d54b130a-28a8-4bb1-9030-c474369c2205
1f3ab7dd-169c-4837-b8b2-fac7dd773804	WEDNESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	d54b130a-28a8-4bb1-9030-c474369c2205
fae48db7-8920-4714-adfc-542af7c01f90	THURSDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	d54b130a-28a8-4bb1-9030-c474369c2205
823cd538-4bbd-449e-81b7-b787a7cb0a8d	FRIDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	d54b130a-28a8-4bb1-9030-c474369c2205
fc10dfd1-8542-4fac-a527-fbf851f03a64	MONDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ff6368bd-dfe6-4a3f-aef8-0edc3d90185b	TUESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8630cd26-a9a6-4afb-ab74-04a6a380e411	WEDNESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0888f824-b84b-42af-84bf-b6dfc4602b98	THURSDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ee926802-8bd8-4fe1-9daf-509c1a7e9c73	FRIDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ee0ce1e7-d973-42c3-b610-924a229f1f04	MONDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	c33e01ef-be92-474a-9e43-0543649735d5
83e37604-2195-4cc6-b4f9-767d14f11fb9	TUESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	c33e01ef-be92-474a-9e43-0543649735d5
190ee00d-54d5-4ae8-b342-058500af6b1a	WEDNESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	c33e01ef-be92-474a-9e43-0543649735d5
26d92078-478f-4da1-9611-a7cac7c2f5df	THURSDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	c33e01ef-be92-474a-9e43-0543649735d5
89db5823-895a-43fe-a87b-d92188a58352	FRIDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	c33e01ef-be92-474a-9e43-0543649735d5
54e8cf02-9858-4678-a643-c065685bf681	MONDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	6f1475e5-0d8a-47c7-855f-ccff802aff85
11ed79e6-15b8-46f7-948b-a6f700fe357e	TUESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	6f1475e5-0d8a-47c7-855f-ccff802aff85
0e70afe6-2408-4304-9e26-49b68a8799c4	WEDNESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	6f1475e5-0d8a-47c7-855f-ccff802aff85
151601ae-02f1-4758-b2a1-3f3cffed9196	THURSDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	6f1475e5-0d8a-47c7-855f-ccff802aff85
8deefdcf-baf6-4827-bb62-dbbbb79d6d15	FRIDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	6f1475e5-0d8a-47c7-855f-ccff802aff85
63f6d39f-2e50-420e-8f7f-c8ab3f72e05b	MONDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	58845959-0984-4136-a733-b75f5c9057b9
58f8e1d6-3180-493e-b6e5-dd922f4ca575	TUESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	58845959-0984-4136-a733-b75f5c9057b9
13e6b0aa-59b0-46bf-ba2f-8aaefd31bb5f	WEDNESDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	58845959-0984-4136-a733-b75f5c9057b9
acf58fe0-bfc2-47e4-8fed-f862db87f56a	THURSDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	58845959-0984-4136-a733-b75f5c9057b9
f377a1d8-c230-44f9-a391-3c32ad485a5f	FRIDAY	ce5137bc-0f9e-49a1-9e27-0aec555f069d	58845959-0984-4136-a733-b75f5c9057b9
03b5ec08-84e5-48b2-b82f-4abcbebb815f	MONDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	440c17ed-5262-4800-aa19-38e5285330c3
24a72eb4-b3c6-4013-9e1b-f6eec6fda956	TUESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	440c17ed-5262-4800-aa19-38e5285330c3
2089ac9e-a2a6-404b-a894-1494b14e8408	WEDNESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	440c17ed-5262-4800-aa19-38e5285330c3
9a28367f-354c-43d8-932a-324581e2b771	THURSDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	440c17ed-5262-4800-aa19-38e5285330c3
19592882-2da1-4e76-96cc-167fc705967a	FRIDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	440c17ed-5262-4800-aa19-38e5285330c3
994afadb-6c17-416a-a8fa-835157cade2c	MONDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
313ca726-9e16-4f68-83d7-e4363e4d8d93	TUESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3a2c8da8-5374-45b0-acda-83d0a69bf5d4	WEDNESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
25ed4d7d-3be6-4c75-9fc2-701200185a09	THURSDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6269eae3-7e14-455f-a77a-674bf5bb8b91	FRIDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4930a415-ddf7-46b8-812b-07d8d9c72666	MONDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	d54b130a-28a8-4bb1-9030-c474369c2205
e2bebbc3-fd2a-4f29-913e-3964676d1cc1	TUESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	d54b130a-28a8-4bb1-9030-c474369c2205
4a6fdba1-5022-47e2-b535-a50cf4450480	WEDNESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	d54b130a-28a8-4bb1-9030-c474369c2205
5e1e3096-e1ff-4217-94a7-f54acdc60dbc	THURSDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	d54b130a-28a8-4bb1-9030-c474369c2205
1952ac9b-d868-4dcb-9b99-4d7e501e7756	FRIDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	d54b130a-28a8-4bb1-9030-c474369c2205
d395c2b7-952b-4eb2-8be0-8b45a5640597	MONDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1402fcf7-3896-4020-8c54-eafe5a7a5543	TUESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
24bf991d-22ea-47c7-b571-78fb1fb69f19	WEDNESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
230e2ad7-a565-41eb-9e57-4ffae95f02ce	THURSDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8239dfca-f5fb-42e7-9496-e897c2a37571	FRIDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cc573ec1-1c78-4811-991c-368f5466ffe1	MONDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	c33e01ef-be92-474a-9e43-0543649735d5
d760c0ce-9cc2-4354-b068-f878ee8b309b	TUESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	c33e01ef-be92-474a-9e43-0543649735d5
86256a8e-34fb-4f9b-aeab-879661cbc358	WEDNESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	c33e01ef-be92-474a-9e43-0543649735d5
ac9605d1-2a26-42b6-b477-a9928e675051	THURSDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	c33e01ef-be92-474a-9e43-0543649735d5
d7732601-e875-4eba-9f55-1c3c9dafe29c	FRIDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	c33e01ef-be92-474a-9e43-0543649735d5
1e16e5b3-3310-4ffd-ad31-4dbf3132b148	MONDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	6f1475e5-0d8a-47c7-855f-ccff802aff85
a36f4eaa-ecd8-4e23-8948-8fc2e4230d05	TUESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	6f1475e5-0d8a-47c7-855f-ccff802aff85
22f32049-4c06-4611-818d-cac6197501ed	WEDNESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	6f1475e5-0d8a-47c7-855f-ccff802aff85
f92f4520-561b-446b-ace3-5881cf8b28f0	THURSDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	6f1475e5-0d8a-47c7-855f-ccff802aff85
2273cc1b-3ae2-46a9-8353-33fc7c594b66	FRIDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	6f1475e5-0d8a-47c7-855f-ccff802aff85
62192661-9d03-4826-9c38-2c8b10c87270	MONDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	58845959-0984-4136-a733-b75f5c9057b9
80591866-3b4d-4b58-954f-8892c4e25573	TUESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	58845959-0984-4136-a733-b75f5c9057b9
c0edeaa6-c595-4e21-90b4-4703378c0ce0	WEDNESDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	58845959-0984-4136-a733-b75f5c9057b9
7340e199-0682-421c-8f6e-1101e0d38767	THURSDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	58845959-0984-4136-a733-b75f5c9057b9
b15a2335-ab11-4cc4-b2a3-1928b5ccf997	FRIDAY	b54d4881-1bc6-4134-9bac-2bc983148ed5	58845959-0984-4136-a733-b75f5c9057b9
e22faac2-fb56-461a-9324-24485c397ed7	MONDAY	51afc696-6082-4237-9687-153737bee18d	440c17ed-5262-4800-aa19-38e5285330c3
dae764f8-e586-4a67-8b34-052c71d20ecf	TUESDAY	51afc696-6082-4237-9687-153737bee18d	440c17ed-5262-4800-aa19-38e5285330c3
f8e6efdb-5cbe-4b4a-be68-a773c129e44a	WEDNESDAY	51afc696-6082-4237-9687-153737bee18d	440c17ed-5262-4800-aa19-38e5285330c3
39688eed-2177-48f8-89f7-20ef0cfc6fe7	THURSDAY	51afc696-6082-4237-9687-153737bee18d	440c17ed-5262-4800-aa19-38e5285330c3
af490a1a-fe86-42ae-b73c-8fc6ca32f596	FRIDAY	51afc696-6082-4237-9687-153737bee18d	440c17ed-5262-4800-aa19-38e5285330c3
ff4270a7-051c-4a58-9bbe-912193d1ecd0	MONDAY	51afc696-6082-4237-9687-153737bee18d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bbee9c86-f44e-48d7-b771-c17485c8dda1	TUESDAY	51afc696-6082-4237-9687-153737bee18d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
42c6cf42-aac3-4412-a1c4-be3af7bed268	WEDNESDAY	51afc696-6082-4237-9687-153737bee18d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
17905ead-dd64-476a-abe1-666f5a7eeccc	THURSDAY	51afc696-6082-4237-9687-153737bee18d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bd3349b7-2cb4-4104-ba7c-93a92c99075e	FRIDAY	51afc696-6082-4237-9687-153737bee18d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7a2a9319-5cf0-4619-bd35-ff514521a3de	MONDAY	51afc696-6082-4237-9687-153737bee18d	d54b130a-28a8-4bb1-9030-c474369c2205
7a914541-1f30-4727-b47b-cc1361dcf12e	TUESDAY	51afc696-6082-4237-9687-153737bee18d	d54b130a-28a8-4bb1-9030-c474369c2205
8dc695bb-b925-48d4-99b5-27992c203cf9	WEDNESDAY	51afc696-6082-4237-9687-153737bee18d	d54b130a-28a8-4bb1-9030-c474369c2205
18c20e01-851b-4c2d-b52b-ebed98f3af64	THURSDAY	51afc696-6082-4237-9687-153737bee18d	d54b130a-28a8-4bb1-9030-c474369c2205
0cd1add3-98ba-4eab-afa3-c06843ec65a8	FRIDAY	51afc696-6082-4237-9687-153737bee18d	d54b130a-28a8-4bb1-9030-c474369c2205
b42ef58b-3e87-4228-a22a-664b72dd973a	MONDAY	51afc696-6082-4237-9687-153737bee18d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9b4eba5f-4e41-41f0-b18c-f60d0f3744ab	TUESDAY	51afc696-6082-4237-9687-153737bee18d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0eabf67e-d786-49d1-9d08-638761394662	WEDNESDAY	51afc696-6082-4237-9687-153737bee18d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
658ce576-8261-4b87-9677-807c021a5367	THURSDAY	51afc696-6082-4237-9687-153737bee18d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7ccb7da9-e1d5-489a-974b-db3e0b13a3a2	FRIDAY	51afc696-6082-4237-9687-153737bee18d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ff41ce9b-9f94-4273-8748-8ffab5a2aeb5	MONDAY	51afc696-6082-4237-9687-153737bee18d	c33e01ef-be92-474a-9e43-0543649735d5
d0a79321-010b-4d50-acf0-f65a9383d72b	TUESDAY	51afc696-6082-4237-9687-153737bee18d	c33e01ef-be92-474a-9e43-0543649735d5
8a004f9c-350f-4373-adf4-27821376df73	WEDNESDAY	51afc696-6082-4237-9687-153737bee18d	c33e01ef-be92-474a-9e43-0543649735d5
70cdf8e5-1dd5-45fd-8d12-00c1e29022e4	THURSDAY	51afc696-6082-4237-9687-153737bee18d	c33e01ef-be92-474a-9e43-0543649735d5
703550ec-11ac-412b-8e2f-50db6f55c90c	FRIDAY	51afc696-6082-4237-9687-153737bee18d	c33e01ef-be92-474a-9e43-0543649735d5
4ba62f24-b1bb-4a07-bbf5-794c7f306bae	MONDAY	51afc696-6082-4237-9687-153737bee18d	6f1475e5-0d8a-47c7-855f-ccff802aff85
839a617a-c095-4055-bd27-ccf1f1628ad6	TUESDAY	51afc696-6082-4237-9687-153737bee18d	6f1475e5-0d8a-47c7-855f-ccff802aff85
40027b5d-0db7-4f8c-999f-66d931ba40d9	WEDNESDAY	51afc696-6082-4237-9687-153737bee18d	6f1475e5-0d8a-47c7-855f-ccff802aff85
75bd39ee-f1ee-461d-8c76-dc828c07c24e	THURSDAY	51afc696-6082-4237-9687-153737bee18d	6f1475e5-0d8a-47c7-855f-ccff802aff85
69d31b40-766e-451c-9242-d76e1185becb	FRIDAY	51afc696-6082-4237-9687-153737bee18d	6f1475e5-0d8a-47c7-855f-ccff802aff85
6e2ee753-0bbf-4fdc-84cf-31624e49d4f9	MONDAY	51afc696-6082-4237-9687-153737bee18d	58845959-0984-4136-a733-b75f5c9057b9
724a6344-dbcd-4b80-8379-0f60234d2cf7	TUESDAY	51afc696-6082-4237-9687-153737bee18d	58845959-0984-4136-a733-b75f5c9057b9
06d064b7-9ed0-455b-8776-6571a967e0c1	WEDNESDAY	51afc696-6082-4237-9687-153737bee18d	58845959-0984-4136-a733-b75f5c9057b9
2e59fd98-0d29-455d-9f60-05d213c2955c	THURSDAY	51afc696-6082-4237-9687-153737bee18d	58845959-0984-4136-a733-b75f5c9057b9
20d91080-8242-406f-ae47-e8a8b7004e1f	FRIDAY	51afc696-6082-4237-9687-153737bee18d	58845959-0984-4136-a733-b75f5c9057b9
2f026c5e-818d-457b-a225-b7179a220d2e	MONDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	440c17ed-5262-4800-aa19-38e5285330c3
8d5e3c7b-6644-4f8e-ba0f-f400137354ff	TUESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	440c17ed-5262-4800-aa19-38e5285330c3
b2775487-298a-41a8-92dc-e47bd1011e25	WEDNESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	440c17ed-5262-4800-aa19-38e5285330c3
bc3ba5a6-7403-4266-ac54-bbe0ecac43b8	THURSDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	440c17ed-5262-4800-aa19-38e5285330c3
eac13f52-5478-46cd-b44c-437f92b6a59c	FRIDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	440c17ed-5262-4800-aa19-38e5285330c3
226a9f9b-ba21-4349-8957-90fb989358e0	MONDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9f0d6115-35e1-45ea-8a2e-c114bcc67638	TUESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
83c9bfc8-f9d7-42d6-be3d-628e94202e14	WEDNESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1dc3d2ee-4e10-4888-b2f7-a3894419b969	THURSDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e7e41ca5-d00e-4eef-bef9-0e531903da0e	FRIDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
48d9c921-354b-4adc-a3c8-89a6a41c4e5d	MONDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	d54b130a-28a8-4bb1-9030-c474369c2205
061d0886-820f-4cc7-8a8c-412da14280b7	TUESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	d54b130a-28a8-4bb1-9030-c474369c2205
51aa3bbc-9ab9-4071-94f4-8144e17fc4f6	WEDNESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	d54b130a-28a8-4bb1-9030-c474369c2205
6a8ed29d-4fad-4c09-9d12-dbb1f452a0e2	THURSDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	d54b130a-28a8-4bb1-9030-c474369c2205
0c7febac-8f98-4154-8be7-06ae59be0661	FRIDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	d54b130a-28a8-4bb1-9030-c474369c2205
a0b3073f-d6cf-482c-ab5a-d5e2bb846acb	MONDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6bb98b9b-c5a8-4e8b-9c94-8421849dd47f	TUESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f7efc175-c9cf-4a82-abe5-a08319c42ec9	WEDNESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4f5c8616-777e-4343-bd12-d2e8a45e2e45	THURSDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
fe8fc41f-52e5-4f2b-b9dd-8317e786ddf8	FRIDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
75f22047-bd22-4f2c-b6db-34aabd9294a1	MONDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	c33e01ef-be92-474a-9e43-0543649735d5
40b76aa5-e160-4fa8-88a0-fef17ac243a3	TUESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	c33e01ef-be92-474a-9e43-0543649735d5
8ed38aed-545c-4835-94af-06481c7dcedb	WEDNESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	c33e01ef-be92-474a-9e43-0543649735d5
7da6ba67-a8b4-45be-b908-27f2bcf7df2a	THURSDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	c33e01ef-be92-474a-9e43-0543649735d5
2201ab91-8fb8-4614-9a13-831fbca6b228	FRIDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	c33e01ef-be92-474a-9e43-0543649735d5
f52edd3b-77f1-4a9c-a451-110594822d58	MONDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	6f1475e5-0d8a-47c7-855f-ccff802aff85
6b37e526-811a-4d48-9745-ac31339f03b3	TUESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	6f1475e5-0d8a-47c7-855f-ccff802aff85
6fda5b40-48c9-4441-8b3d-fd00f46e0bef	WEDNESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	6f1475e5-0d8a-47c7-855f-ccff802aff85
d0339402-a602-4711-8283-c448ea6013e2	THURSDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	6f1475e5-0d8a-47c7-855f-ccff802aff85
dd35a81f-621c-49e2-b2f0-f7a2ffc49c32	FRIDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	6f1475e5-0d8a-47c7-855f-ccff802aff85
51f27873-54e7-4518-8265-8e4aa12fd60e	MONDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	58845959-0984-4136-a733-b75f5c9057b9
d6ca6b94-6cea-4e7d-8d47-d417ab61ceae	TUESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	58845959-0984-4136-a733-b75f5c9057b9
4e8f227c-98b7-435f-8634-299c49939699	WEDNESDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	58845959-0984-4136-a733-b75f5c9057b9
298e07a3-43ae-4b4b-ac27-1fcaf2667d9c	THURSDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	58845959-0984-4136-a733-b75f5c9057b9
d5cc6b99-27fd-4b3d-ab97-334eb7925093	FRIDAY	a38b16b8-3e0a-4b85-89bc-754a4880780a	58845959-0984-4136-a733-b75f5c9057b9
a29ac70d-438b-4d53-bb17-c9e02e3d1304	MONDAY	645a078a-04d3-41ff-b720-080dd70250b1	440c17ed-5262-4800-aa19-38e5285330c3
38ddce99-457a-42a9-a1d4-126b0d1420b6	TUESDAY	645a078a-04d3-41ff-b720-080dd70250b1	440c17ed-5262-4800-aa19-38e5285330c3
e0852adf-6cfa-44c9-abdc-caae5c5598bb	WEDNESDAY	645a078a-04d3-41ff-b720-080dd70250b1	440c17ed-5262-4800-aa19-38e5285330c3
6d7b3590-035b-465f-b8e1-937305611844	THURSDAY	645a078a-04d3-41ff-b720-080dd70250b1	440c17ed-5262-4800-aa19-38e5285330c3
06597be5-308d-4cf9-a87c-598e2d038082	FRIDAY	645a078a-04d3-41ff-b720-080dd70250b1	440c17ed-5262-4800-aa19-38e5285330c3
c5e6e361-dfa8-4a3f-83c1-422322a4a003	MONDAY	645a078a-04d3-41ff-b720-080dd70250b1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f106d430-67e6-4f6c-9b1c-1b96e433c3b2	TUESDAY	645a078a-04d3-41ff-b720-080dd70250b1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4f4ebcb0-cc8b-42cc-b3e0-249940582c7a	WEDNESDAY	645a078a-04d3-41ff-b720-080dd70250b1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4895713e-98e6-48ae-b2eb-0af8f42d5cc8	THURSDAY	645a078a-04d3-41ff-b720-080dd70250b1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3b1bf373-23c4-4926-b50a-6ccc3c9d93bd	FRIDAY	645a078a-04d3-41ff-b720-080dd70250b1	5f76a266-8d18-4da8-9732-b63d4d8f24a0
806cf4b5-8542-4b4a-b1a0-49b1df69280a	MONDAY	645a078a-04d3-41ff-b720-080dd70250b1	d54b130a-28a8-4bb1-9030-c474369c2205
ec9c35f3-c2e0-47ec-a46d-da4fd6a25239	TUESDAY	645a078a-04d3-41ff-b720-080dd70250b1	d54b130a-28a8-4bb1-9030-c474369c2205
b16d2e14-98d3-434f-9620-c0f269c21eee	WEDNESDAY	645a078a-04d3-41ff-b720-080dd70250b1	d54b130a-28a8-4bb1-9030-c474369c2205
b9318fee-0873-4bcc-a722-da1f51294417	THURSDAY	645a078a-04d3-41ff-b720-080dd70250b1	d54b130a-28a8-4bb1-9030-c474369c2205
9117b960-cb5c-422a-bfae-94ba95a747a7	FRIDAY	645a078a-04d3-41ff-b720-080dd70250b1	d54b130a-28a8-4bb1-9030-c474369c2205
786e61a3-236a-4fff-9d13-f2fcea3ee5fd	MONDAY	645a078a-04d3-41ff-b720-080dd70250b1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
81581fb8-a319-416a-83fb-b92108cc4c77	TUESDAY	645a078a-04d3-41ff-b720-080dd70250b1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b3bcc067-967e-4804-9e62-94cc8ac9c7d5	WEDNESDAY	645a078a-04d3-41ff-b720-080dd70250b1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
14ce34da-850a-4fd5-b1d8-091fd04d09d7	THURSDAY	645a078a-04d3-41ff-b720-080dd70250b1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
99d8febb-85de-4195-ad1a-e47279763324	FRIDAY	645a078a-04d3-41ff-b720-080dd70250b1	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b6f7d62d-834c-4daf-83c5-c6e6b88846aa	MONDAY	645a078a-04d3-41ff-b720-080dd70250b1	c33e01ef-be92-474a-9e43-0543649735d5
63dbf705-9b4e-4412-a96d-adcf50c4f095	TUESDAY	645a078a-04d3-41ff-b720-080dd70250b1	c33e01ef-be92-474a-9e43-0543649735d5
9e013477-77ce-4f04-a819-dff18ad4cae7	WEDNESDAY	645a078a-04d3-41ff-b720-080dd70250b1	c33e01ef-be92-474a-9e43-0543649735d5
23883430-d6a1-4a2f-92e4-effa12efe1a6	THURSDAY	645a078a-04d3-41ff-b720-080dd70250b1	c33e01ef-be92-474a-9e43-0543649735d5
14f11f17-07dc-4f31-827a-b802ad874099	FRIDAY	645a078a-04d3-41ff-b720-080dd70250b1	c33e01ef-be92-474a-9e43-0543649735d5
caba5533-9e75-488b-b84e-616de82a1543	MONDAY	645a078a-04d3-41ff-b720-080dd70250b1	6f1475e5-0d8a-47c7-855f-ccff802aff85
cea269ce-3dd1-478b-aa3d-f5c9da501dab	TUESDAY	645a078a-04d3-41ff-b720-080dd70250b1	6f1475e5-0d8a-47c7-855f-ccff802aff85
7c76596f-a119-4a64-a812-2df2d105d86c	WEDNESDAY	645a078a-04d3-41ff-b720-080dd70250b1	6f1475e5-0d8a-47c7-855f-ccff802aff85
ac64bea4-e079-4fc7-9a16-0c4e43999102	THURSDAY	645a078a-04d3-41ff-b720-080dd70250b1	6f1475e5-0d8a-47c7-855f-ccff802aff85
0591a934-de11-43d6-8132-99fd58022c3d	FRIDAY	645a078a-04d3-41ff-b720-080dd70250b1	6f1475e5-0d8a-47c7-855f-ccff802aff85
00a03931-d2b2-452c-ae98-199438fc6219	MONDAY	645a078a-04d3-41ff-b720-080dd70250b1	58845959-0984-4136-a733-b75f5c9057b9
79dc9257-bcc1-4586-a34b-29315ab3b8ae	TUESDAY	645a078a-04d3-41ff-b720-080dd70250b1	58845959-0984-4136-a733-b75f5c9057b9
456a6a3f-3cf6-4f60-8004-b50a26fd8f85	WEDNESDAY	645a078a-04d3-41ff-b720-080dd70250b1	58845959-0984-4136-a733-b75f5c9057b9
f54eda6f-9eca-466a-b78b-08e799e3a4e8	THURSDAY	645a078a-04d3-41ff-b720-080dd70250b1	58845959-0984-4136-a733-b75f5c9057b9
d19a6031-678a-4dce-a69f-bae93f36bea4	FRIDAY	645a078a-04d3-41ff-b720-080dd70250b1	58845959-0984-4136-a733-b75f5c9057b9
21ad3bc2-59cd-4f1d-b0fc-c1dfd0031a1b	MONDAY	5e280afb-da17-4629-8fea-40d7880580d6	440c17ed-5262-4800-aa19-38e5285330c3
c49198f6-c864-4c64-bb28-4a12d86915c4	TUESDAY	5e280afb-da17-4629-8fea-40d7880580d6	440c17ed-5262-4800-aa19-38e5285330c3
ec911c0a-d309-40ca-831b-2027b91b2fc8	WEDNESDAY	5e280afb-da17-4629-8fea-40d7880580d6	440c17ed-5262-4800-aa19-38e5285330c3
0aeb1525-a549-40db-a844-d0359c8a6205	THURSDAY	5e280afb-da17-4629-8fea-40d7880580d6	440c17ed-5262-4800-aa19-38e5285330c3
5bb785fd-55f1-4e0d-afad-dfcd56290de9	FRIDAY	5e280afb-da17-4629-8fea-40d7880580d6	440c17ed-5262-4800-aa19-38e5285330c3
9036b145-9423-458f-b0e8-0469cd979fe5	MONDAY	5e280afb-da17-4629-8fea-40d7880580d6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c2d30670-ce8f-41f9-a126-992bcb4ace2d	TUESDAY	5e280afb-da17-4629-8fea-40d7880580d6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
146f7476-03e8-4492-9ea4-71ee89112130	WEDNESDAY	5e280afb-da17-4629-8fea-40d7880580d6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
783a25c7-2876-4c1b-9748-bb3f7925c8bc	THURSDAY	5e280afb-da17-4629-8fea-40d7880580d6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7e4e61e0-df99-4ddd-9aa3-e56956ae301e	FRIDAY	5e280afb-da17-4629-8fea-40d7880580d6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
64d46295-af16-44d5-a30c-8fadf577f936	MONDAY	5e280afb-da17-4629-8fea-40d7880580d6	d54b130a-28a8-4bb1-9030-c474369c2205
33d79eb6-d5d0-45f1-bd67-bd99bd9d66fc	TUESDAY	5e280afb-da17-4629-8fea-40d7880580d6	d54b130a-28a8-4bb1-9030-c474369c2205
c5d726ff-9e5e-4d41-a7d4-6df1d11d5f8a	WEDNESDAY	5e280afb-da17-4629-8fea-40d7880580d6	d54b130a-28a8-4bb1-9030-c474369c2205
071a33ba-656c-41de-8146-b423c97d38c9	THURSDAY	5e280afb-da17-4629-8fea-40d7880580d6	d54b130a-28a8-4bb1-9030-c474369c2205
888bedbb-da2c-49ec-9194-b3c0c2725ab8	FRIDAY	5e280afb-da17-4629-8fea-40d7880580d6	d54b130a-28a8-4bb1-9030-c474369c2205
2610d0df-1abc-4228-9683-27a5f1c30c7c	MONDAY	5e280afb-da17-4629-8fea-40d7880580d6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
94122212-dc80-4181-9e56-6b3c3f563bec	TUESDAY	5e280afb-da17-4629-8fea-40d7880580d6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6f771db0-85d1-4fc5-b0b4-f99030df92b9	WEDNESDAY	5e280afb-da17-4629-8fea-40d7880580d6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
baa86fa5-5052-4d1c-b086-866b30c05cf4	THURSDAY	5e280afb-da17-4629-8fea-40d7880580d6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
56e63975-440d-4e30-afdc-76fcec3bd281	FRIDAY	5e280afb-da17-4629-8fea-40d7880580d6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5c9df762-18f9-4067-a28e-ed283997db9c	MONDAY	5e280afb-da17-4629-8fea-40d7880580d6	c33e01ef-be92-474a-9e43-0543649735d5
6aa3a208-3d23-4ff1-b6eb-8d04162af70b	TUESDAY	5e280afb-da17-4629-8fea-40d7880580d6	c33e01ef-be92-474a-9e43-0543649735d5
e7711283-428d-42da-8688-3628a362cfd7	WEDNESDAY	5e280afb-da17-4629-8fea-40d7880580d6	c33e01ef-be92-474a-9e43-0543649735d5
2ccc2288-e307-497a-ad2e-fc826a94fdf6	THURSDAY	5e280afb-da17-4629-8fea-40d7880580d6	c33e01ef-be92-474a-9e43-0543649735d5
58b076c7-e9d3-4e33-b05c-1b0974463ec3	FRIDAY	5e280afb-da17-4629-8fea-40d7880580d6	c33e01ef-be92-474a-9e43-0543649735d5
5932a42e-092b-4518-8420-8a9a06afdd34	MONDAY	5e280afb-da17-4629-8fea-40d7880580d6	6f1475e5-0d8a-47c7-855f-ccff802aff85
ca35a357-22fe-4817-91b8-b8955756ef1c	TUESDAY	5e280afb-da17-4629-8fea-40d7880580d6	6f1475e5-0d8a-47c7-855f-ccff802aff85
e2ddb8d1-27a3-4da3-9780-e2b6aec014c5	WEDNESDAY	5e280afb-da17-4629-8fea-40d7880580d6	6f1475e5-0d8a-47c7-855f-ccff802aff85
633f8114-1644-4812-9032-933472401a44	THURSDAY	5e280afb-da17-4629-8fea-40d7880580d6	6f1475e5-0d8a-47c7-855f-ccff802aff85
79d2f610-0857-4a38-a3a6-e860c8443f15	FRIDAY	5e280afb-da17-4629-8fea-40d7880580d6	6f1475e5-0d8a-47c7-855f-ccff802aff85
7462a9a3-2222-4b3a-b6b4-c5a00702d279	MONDAY	5e280afb-da17-4629-8fea-40d7880580d6	58845959-0984-4136-a733-b75f5c9057b9
bf655308-d513-4fad-a43a-3e923b615bde	TUESDAY	5e280afb-da17-4629-8fea-40d7880580d6	58845959-0984-4136-a733-b75f5c9057b9
c9c37328-2b15-49d4-a2e1-3602aa82a67a	WEDNESDAY	5e280afb-da17-4629-8fea-40d7880580d6	58845959-0984-4136-a733-b75f5c9057b9
7b756db9-995d-4c95-8ef2-a5a8f49d180f	THURSDAY	5e280afb-da17-4629-8fea-40d7880580d6	58845959-0984-4136-a733-b75f5c9057b9
e2939eba-4d4d-4c04-b8c8-d17302f4d3fb	FRIDAY	5e280afb-da17-4629-8fea-40d7880580d6	58845959-0984-4136-a733-b75f5c9057b9
a6966d0a-a4e1-44f5-ad37-6552a594a9c7	MONDAY	587397a4-894b-4a60-ae95-af17f7c71a70	440c17ed-5262-4800-aa19-38e5285330c3
d8d9ad59-4335-4cfa-93b2-abd6addfb001	TUESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	440c17ed-5262-4800-aa19-38e5285330c3
e0448e2f-1e1c-40be-8f7e-b2800ff98ff5	WEDNESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	440c17ed-5262-4800-aa19-38e5285330c3
0353b86f-6ddc-4247-bb9d-b5ca09303b66	THURSDAY	587397a4-894b-4a60-ae95-af17f7c71a70	440c17ed-5262-4800-aa19-38e5285330c3
b9fe94d0-cf9a-49f5-a2ba-4d5000648d90	FRIDAY	587397a4-894b-4a60-ae95-af17f7c71a70	440c17ed-5262-4800-aa19-38e5285330c3
a5f542cf-206e-43e4-bb80-131af8900660	MONDAY	587397a4-894b-4a60-ae95-af17f7c71a70	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6a66303f-8604-4c56-afe2-6e7ef8491ad8	TUESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	5f76a266-8d18-4da8-9732-b63d4d8f24a0
424551ee-561b-4a82-8daa-01bf1f6e3255	WEDNESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f6590cb9-4fa7-4b6a-8799-96175d1500be	THURSDAY	587397a4-894b-4a60-ae95-af17f7c71a70	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0f68eba3-aea4-496b-877d-8ad080de7dc9	FRIDAY	587397a4-894b-4a60-ae95-af17f7c71a70	5f76a266-8d18-4da8-9732-b63d4d8f24a0
74c1865a-3f27-461e-af05-c23c70b82f19	MONDAY	587397a4-894b-4a60-ae95-af17f7c71a70	d54b130a-28a8-4bb1-9030-c474369c2205
29420086-7201-407f-8cf3-7b4f7b14d866	TUESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	d54b130a-28a8-4bb1-9030-c474369c2205
b40d8d7d-8134-4757-ae28-ac5f9e453f3a	WEDNESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	d54b130a-28a8-4bb1-9030-c474369c2205
9de78f24-f79e-4f4f-b567-97f80db89faf	THURSDAY	587397a4-894b-4a60-ae95-af17f7c71a70	d54b130a-28a8-4bb1-9030-c474369c2205
a3490e4a-0c1e-4aea-b93c-88fc50c218d4	FRIDAY	587397a4-894b-4a60-ae95-af17f7c71a70	d54b130a-28a8-4bb1-9030-c474369c2205
7e1d7a02-5822-456d-b6ee-0394eb53d0c6	MONDAY	587397a4-894b-4a60-ae95-af17f7c71a70	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e84a016f-91a5-44b4-b1fd-224073205a6c	TUESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9ee27176-2206-4d33-8275-bea8fc2e1ef6	WEDNESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
af112d1f-710d-4185-972b-09e808deef07	THURSDAY	587397a4-894b-4a60-ae95-af17f7c71a70	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2af79f28-99d8-4cb9-805b-94e908ac17a3	FRIDAY	587397a4-894b-4a60-ae95-af17f7c71a70	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c7fe4a64-f301-4a1c-b6a1-901255fc9450	MONDAY	587397a4-894b-4a60-ae95-af17f7c71a70	c33e01ef-be92-474a-9e43-0543649735d5
1d3c12fd-53cb-4daf-a192-4e77d8051e39	TUESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	c33e01ef-be92-474a-9e43-0543649735d5
a0acb718-25c8-4dfd-a2a2-a2e01bd08c32	WEDNESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	c33e01ef-be92-474a-9e43-0543649735d5
332edf94-b5e2-45e1-9aab-8aa4eba4aba1	THURSDAY	587397a4-894b-4a60-ae95-af17f7c71a70	c33e01ef-be92-474a-9e43-0543649735d5
60fddb13-4bca-4b53-904d-59c25d7ea0c4	FRIDAY	587397a4-894b-4a60-ae95-af17f7c71a70	c33e01ef-be92-474a-9e43-0543649735d5
a890cb7a-6b66-4d6c-8fbf-98bae15f2b3f	MONDAY	587397a4-894b-4a60-ae95-af17f7c71a70	6f1475e5-0d8a-47c7-855f-ccff802aff85
66e2d48d-8d40-440b-b085-d1e52bb5a29b	TUESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	6f1475e5-0d8a-47c7-855f-ccff802aff85
bdc2091a-a113-44ab-8f7b-aaf35a09fe12	WEDNESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	6f1475e5-0d8a-47c7-855f-ccff802aff85
498213c6-0216-4d50-800d-0bc3d8a25cdb	THURSDAY	587397a4-894b-4a60-ae95-af17f7c71a70	6f1475e5-0d8a-47c7-855f-ccff802aff85
5e56470b-a0ef-4bcb-9ef5-1076095d8abc	FRIDAY	587397a4-894b-4a60-ae95-af17f7c71a70	6f1475e5-0d8a-47c7-855f-ccff802aff85
ea4753a7-08d7-4372-8b39-65c61afc7e9a	MONDAY	587397a4-894b-4a60-ae95-af17f7c71a70	58845959-0984-4136-a733-b75f5c9057b9
acca7cda-87db-4e88-96f1-bb5dfb8d117e	TUESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	58845959-0984-4136-a733-b75f5c9057b9
e2f35d9d-d01b-41a4-a4be-aa3b7c2bebbf	WEDNESDAY	587397a4-894b-4a60-ae95-af17f7c71a70	58845959-0984-4136-a733-b75f5c9057b9
befb1a9e-a23f-4201-9ab7-50b2954967e1	THURSDAY	587397a4-894b-4a60-ae95-af17f7c71a70	58845959-0984-4136-a733-b75f5c9057b9
d4e242b0-c592-4879-a19a-8f6c1b5856d2	FRIDAY	587397a4-894b-4a60-ae95-af17f7c71a70	58845959-0984-4136-a733-b75f5c9057b9
d046d0c6-e6c9-4b9e-a1f5-a2234fca3f6c	MONDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	440c17ed-5262-4800-aa19-38e5285330c3
8fb3d954-bb86-419a-b723-f11029f308e1	TUESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	440c17ed-5262-4800-aa19-38e5285330c3
e8709975-694c-4d5f-ac2b-2f4489fca21e	WEDNESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	440c17ed-5262-4800-aa19-38e5285330c3
bf22649b-9f27-40fc-8cf1-667726a39c4d	THURSDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	440c17ed-5262-4800-aa19-38e5285330c3
82ef14ee-1eac-47d6-95d9-b7b77b2e30d7	FRIDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	440c17ed-5262-4800-aa19-38e5285330c3
ac4975fa-ed0d-48b2-b4df-6724fee5ce98	MONDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c3943b1c-04da-4c46-b89e-ad62094cbb19	TUESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0f7b2add-f846-4fec-a3cc-00cbafe3882f	WEDNESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d4cf5fb8-0b7b-4aad-a7a1-87cc4d1e0b80	THURSDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
788c29fb-c0a1-461b-b9c5-cc7db6f670bd	FRIDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fb5e3e5a-cad3-42da-bee4-539450d2065d	MONDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	d54b130a-28a8-4bb1-9030-c474369c2205
c0827196-c857-4385-b5ab-ba0e64d61829	TUESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	d54b130a-28a8-4bb1-9030-c474369c2205
a957333c-bed1-4f22-bac1-44eed19df865	WEDNESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	d54b130a-28a8-4bb1-9030-c474369c2205
87c4cca9-7d2f-46f2-b5a7-1dd706586779	THURSDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	d54b130a-28a8-4bb1-9030-c474369c2205
379384ca-9ed2-4de9-bbfd-50f643ea0dfa	FRIDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	d54b130a-28a8-4bb1-9030-c474369c2205
9cfa502e-1161-4f0e-b737-e873be7a768d	MONDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ed044fbe-292f-40e5-a755-99a378cb1ca3	TUESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
888aa960-2174-4787-bcfc-69314d0864bc	WEDNESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9f8944c0-2a2a-4fb6-a844-edd3444e0f6f	THURSDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bb46f64c-fb3f-4273-8f5e-1c021b797ee2	FRIDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2056338a-4fdc-4644-8b42-92ab2109e5e2	MONDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	c33e01ef-be92-474a-9e43-0543649735d5
8dc8f0aa-6069-4758-8f00-eda9c6eab3c3	TUESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	c33e01ef-be92-474a-9e43-0543649735d5
50edcf8a-0515-4aea-9029-ef9ff8d5b5df	WEDNESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	c33e01ef-be92-474a-9e43-0543649735d5
73137b06-7973-41c0-9a43-9a8c81e597ed	THURSDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	c33e01ef-be92-474a-9e43-0543649735d5
97e173fa-5029-4bdc-8014-55ac7d894c3b	FRIDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	c33e01ef-be92-474a-9e43-0543649735d5
18bb09b3-9e6e-4cb5-92ff-7f3dec1e0390	MONDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	6f1475e5-0d8a-47c7-855f-ccff802aff85
d0b93c90-601d-48bd-b4a8-8b683c20684c	TUESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	6f1475e5-0d8a-47c7-855f-ccff802aff85
21abfb6d-452f-480c-bf68-70a96e1ba6c7	WEDNESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	6f1475e5-0d8a-47c7-855f-ccff802aff85
018bfb70-a6ce-4f60-a079-595804eaaf79	THURSDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	6f1475e5-0d8a-47c7-855f-ccff802aff85
2c9f8383-d259-4f22-a504-2866f92d6be7	FRIDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	6f1475e5-0d8a-47c7-855f-ccff802aff85
91475850-470d-43c2-9052-fa299a3f2ded	MONDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	58845959-0984-4136-a733-b75f5c9057b9
63bf163e-b92d-4bbb-90de-0f382b403914	TUESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	58845959-0984-4136-a733-b75f5c9057b9
fea925cd-8d16-4ad8-a92d-2628d7e0c0f9	WEDNESDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	58845959-0984-4136-a733-b75f5c9057b9
bad989b8-985c-4325-b9bd-5a820e3bf695	THURSDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	58845959-0984-4136-a733-b75f5c9057b9
a067aee8-b5ad-44cf-8dc2-2731241a2793	FRIDAY	cf515827-5393-4ff0-8b0b-17e376c00f3f	58845959-0984-4136-a733-b75f5c9057b9
314ac3f3-e733-4587-8a82-7f442e6b3a51	MONDAY	7e598367-12d8-46b1-9817-21679731637c	440c17ed-5262-4800-aa19-38e5285330c3
37692d86-a66e-446a-9e26-b07047441a12	TUESDAY	7e598367-12d8-46b1-9817-21679731637c	440c17ed-5262-4800-aa19-38e5285330c3
5a9b4b2b-6c7c-494b-b3e6-7b502b8f3f64	WEDNESDAY	7e598367-12d8-46b1-9817-21679731637c	440c17ed-5262-4800-aa19-38e5285330c3
a82d443f-a325-48d3-8d58-dc1e540e07f0	THURSDAY	7e598367-12d8-46b1-9817-21679731637c	440c17ed-5262-4800-aa19-38e5285330c3
a0c38209-48b2-4eee-82ba-26f61d149db1	FRIDAY	7e598367-12d8-46b1-9817-21679731637c	440c17ed-5262-4800-aa19-38e5285330c3
a41f1b6f-165d-4091-a684-d21a8ba8e21b	MONDAY	7e598367-12d8-46b1-9817-21679731637c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f241d12f-f8ee-4656-8027-c70cc40626ff	TUESDAY	7e598367-12d8-46b1-9817-21679731637c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fbd46528-cb91-48d1-b8eb-05bc5393077d	WEDNESDAY	7e598367-12d8-46b1-9817-21679731637c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bc2239c6-ff15-45a1-b066-5fbb86568b59	THURSDAY	7e598367-12d8-46b1-9817-21679731637c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c8ebec7c-0d21-4b92-9e2d-f29c6dda3ecb	FRIDAY	7e598367-12d8-46b1-9817-21679731637c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
586e8f2c-0019-4c46-9b00-fba3492f3284	MONDAY	7e598367-12d8-46b1-9817-21679731637c	d54b130a-28a8-4bb1-9030-c474369c2205
551d1a17-ed58-485c-9eb2-6b908de38310	TUESDAY	7e598367-12d8-46b1-9817-21679731637c	d54b130a-28a8-4bb1-9030-c474369c2205
db98a16d-d7d3-4ddf-b614-5629dfd5e777	WEDNESDAY	7e598367-12d8-46b1-9817-21679731637c	d54b130a-28a8-4bb1-9030-c474369c2205
2b138ea5-0bb7-4ebe-83ba-2f557754c422	THURSDAY	7e598367-12d8-46b1-9817-21679731637c	d54b130a-28a8-4bb1-9030-c474369c2205
92922665-74e8-46aa-8a96-cf4e23c21adf	FRIDAY	7e598367-12d8-46b1-9817-21679731637c	d54b130a-28a8-4bb1-9030-c474369c2205
f899247d-a7b3-488a-9dc6-98eb47b8fa6d	MONDAY	7e598367-12d8-46b1-9817-21679731637c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0829412d-ac85-4a74-a589-03422ccb79e4	TUESDAY	7e598367-12d8-46b1-9817-21679731637c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
62bcb7ff-44ad-4669-b758-f88b761f413d	WEDNESDAY	7e598367-12d8-46b1-9817-21679731637c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cf7cd2a4-fdc2-42e5-ba3a-085dfbfeb7bb	THURSDAY	7e598367-12d8-46b1-9817-21679731637c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
56b6a9c4-fab9-443c-a50e-8098801dbc53	FRIDAY	7e598367-12d8-46b1-9817-21679731637c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9ec865b9-84a5-4f06-9d20-fa32574ff1d6	MONDAY	7e598367-12d8-46b1-9817-21679731637c	c33e01ef-be92-474a-9e43-0543649735d5
5dc65871-e4c6-427b-9bd8-ba7214efc0bf	TUESDAY	7e598367-12d8-46b1-9817-21679731637c	c33e01ef-be92-474a-9e43-0543649735d5
9637113e-f524-441b-8eef-d27f18eae258	WEDNESDAY	7e598367-12d8-46b1-9817-21679731637c	c33e01ef-be92-474a-9e43-0543649735d5
225132fe-b06d-44d1-9976-b912ab3716b8	THURSDAY	7e598367-12d8-46b1-9817-21679731637c	c33e01ef-be92-474a-9e43-0543649735d5
ed7d5743-10eb-4197-bd3f-4103c8ceaba8	FRIDAY	7e598367-12d8-46b1-9817-21679731637c	c33e01ef-be92-474a-9e43-0543649735d5
b135cb1e-5f17-41cf-a125-29f277e62805	MONDAY	7e598367-12d8-46b1-9817-21679731637c	6f1475e5-0d8a-47c7-855f-ccff802aff85
99eb7357-a298-4ba9-9c02-38c6c5038c97	TUESDAY	7e598367-12d8-46b1-9817-21679731637c	6f1475e5-0d8a-47c7-855f-ccff802aff85
1961ed0e-5303-432e-ab2c-c2e4c10e223a	WEDNESDAY	7e598367-12d8-46b1-9817-21679731637c	6f1475e5-0d8a-47c7-855f-ccff802aff85
c3fd04b3-218c-4fde-80a7-78546faebfa0	THURSDAY	7e598367-12d8-46b1-9817-21679731637c	6f1475e5-0d8a-47c7-855f-ccff802aff85
4241d3a9-9ca9-4bcd-8e4f-4aeb5cf14987	FRIDAY	7e598367-12d8-46b1-9817-21679731637c	6f1475e5-0d8a-47c7-855f-ccff802aff85
c3d08f74-12ad-4969-928e-521d827cea67	MONDAY	7e598367-12d8-46b1-9817-21679731637c	58845959-0984-4136-a733-b75f5c9057b9
49b834b3-9f9d-4476-a2a2-a55d66925864	TUESDAY	7e598367-12d8-46b1-9817-21679731637c	58845959-0984-4136-a733-b75f5c9057b9
141ce69a-49a9-40f0-8b9e-f9f708dd9ca9	WEDNESDAY	7e598367-12d8-46b1-9817-21679731637c	58845959-0984-4136-a733-b75f5c9057b9
8a679b3c-6b69-4f98-8b7b-568fa9f421f7	THURSDAY	7e598367-12d8-46b1-9817-21679731637c	58845959-0984-4136-a733-b75f5c9057b9
50e23702-0fdf-4131-9119-c58a8e6fec09	FRIDAY	7e598367-12d8-46b1-9817-21679731637c	58845959-0984-4136-a733-b75f5c9057b9
2654fe5d-be6c-4d82-871e-c9a4b8e66d2b	MONDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	440c17ed-5262-4800-aa19-38e5285330c3
4f19d78a-fb00-40b4-b7d1-d08b8cbf6c31	TUESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	440c17ed-5262-4800-aa19-38e5285330c3
15e1036d-512d-4f1a-a5dc-f30403ea6ee7	WEDNESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	440c17ed-5262-4800-aa19-38e5285330c3
f7825654-3378-4fa3-926d-f320d66b4d3a	THURSDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	440c17ed-5262-4800-aa19-38e5285330c3
d11a2eb8-12f6-4fee-91b7-99de342d758d	FRIDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	440c17ed-5262-4800-aa19-38e5285330c3
d1b9b55b-68b2-4308-b37f-628fceded425	MONDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f14f954a-5205-4684-8e13-3053821c1218	TUESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c3b44dc6-5587-42fa-a736-95c8be696cad	WEDNESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b1ccd382-2336-4495-820c-c2c39d1cf7c9	THURSDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f50b4ebb-9476-4b20-84ce-eb2fee9ed14b	FRIDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
db1e14a1-bc19-4039-826f-3fd9b8ee1eaf	MONDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	d54b130a-28a8-4bb1-9030-c474369c2205
39224d82-776e-47c2-84d9-c6d0d0e034a6	TUESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	d54b130a-28a8-4bb1-9030-c474369c2205
d6c6938f-064f-43ce-90a1-4b6131f56895	WEDNESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	d54b130a-28a8-4bb1-9030-c474369c2205
1022ea79-99ae-4902-9582-b3ea7f767f1e	THURSDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	d54b130a-28a8-4bb1-9030-c474369c2205
7644ff91-57e8-42da-a69e-2dc6ca9f3e02	FRIDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	d54b130a-28a8-4bb1-9030-c474369c2205
6e09f8be-83ad-4b2a-83f0-03fae98f18d8	MONDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
505f9900-9800-406d-a85f-8654edc248ad	TUESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0880e22d-ef0a-4712-b05b-f3ffdbcf70a5	WEDNESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e5c720ca-bda5-4e10-bd2e-16216d46baa5	THURSDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ca065ee0-d38b-4e13-ac2e-f4e168179151	FRIDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5e7a681e-858b-42b9-866b-8d4619d7e04a	MONDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	c33e01ef-be92-474a-9e43-0543649735d5
b1a65714-d863-45ce-9702-5a95c9d76cd7	TUESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	c33e01ef-be92-474a-9e43-0543649735d5
69d99e0f-1d83-43b2-853e-e3d24257e415	WEDNESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	c33e01ef-be92-474a-9e43-0543649735d5
115fd3f1-a77a-4e09-856b-608d136f0952	THURSDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	c33e01ef-be92-474a-9e43-0543649735d5
1692a0f3-18cd-419e-8044-4fd96f917ad9	FRIDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	c33e01ef-be92-474a-9e43-0543649735d5
7160308a-66fd-4765-85f2-dc08d1545ac7	MONDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	6f1475e5-0d8a-47c7-855f-ccff802aff85
990d4c59-3a41-4e74-aea5-e34cc4a902c6	TUESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	6f1475e5-0d8a-47c7-855f-ccff802aff85
ad0e24ba-0b3f-4b08-809e-fd527a89ea42	WEDNESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	6f1475e5-0d8a-47c7-855f-ccff802aff85
5b2c8230-cf83-4274-8322-3c2f5257d9d1	THURSDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	6f1475e5-0d8a-47c7-855f-ccff802aff85
35ddce35-7bec-44a2-b1aa-53617ef7a581	FRIDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	6f1475e5-0d8a-47c7-855f-ccff802aff85
7c5b45d8-68c7-47f6-88d6-2c03355c2c1e	MONDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	58845959-0984-4136-a733-b75f5c9057b9
bee062a6-1814-4a61-b5e5-1007b4ab24ad	TUESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	58845959-0984-4136-a733-b75f5c9057b9
aa319025-1ac3-4fbb-9db0-7b45fbfc3f6d	WEDNESDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	58845959-0984-4136-a733-b75f5c9057b9
d2f89a17-5293-45b3-9e3f-86ea18af6dc0	THURSDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	58845959-0984-4136-a733-b75f5c9057b9
0b0e2de4-4dc4-45b9-b6af-4eed864775e7	FRIDAY	11fbd50d-c9f4-4ec0-aa62-58c200b468a3	58845959-0984-4136-a733-b75f5c9057b9
c6da3ccf-6163-4dd6-99d4-c1baa6fdf093	MONDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	440c17ed-5262-4800-aa19-38e5285330c3
07e00d16-38c1-4b2f-a4c4-db955762ded5	TUESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	440c17ed-5262-4800-aa19-38e5285330c3
3bdbbea9-633d-4190-94f7-5741454a32a5	WEDNESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	440c17ed-5262-4800-aa19-38e5285330c3
d3f889c0-9804-4351-a936-547564219e60	THURSDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	440c17ed-5262-4800-aa19-38e5285330c3
2ce631d5-7988-443e-824b-6599d2791dd1	FRIDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	440c17ed-5262-4800-aa19-38e5285330c3
64f282e2-695f-4c7f-b2fc-fc25f0aa1e88	MONDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c728e6d3-7556-4acb-aef6-48f42e907d7b	TUESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6ef8d3dc-aea1-44e8-bf06-1d12b85f81e8	WEDNESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ce0cae27-628e-47d4-83bd-e2203c4f5782	THURSDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9ccbe9a9-1d5a-4ae7-bb46-77dfc6c5fad9	FRIDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b8d22de2-c76c-4fde-ba5b-ecb1a4d70804	MONDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	d54b130a-28a8-4bb1-9030-c474369c2205
cda2cf5d-4d41-4d5c-bd49-0d0ced0bd2d6	TUESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	d54b130a-28a8-4bb1-9030-c474369c2205
f6480fad-35c1-493a-9f5e-e7381f09e810	WEDNESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	d54b130a-28a8-4bb1-9030-c474369c2205
4076f646-31aa-476a-a4e5-6316213aeee0	THURSDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	d54b130a-28a8-4bb1-9030-c474369c2205
6ca158e7-e3bb-49bf-b759-20682dc28866	FRIDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	d54b130a-28a8-4bb1-9030-c474369c2205
de9a0c7b-0c25-4c2f-8e86-3033b2dc50fc	MONDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
85925179-43d5-4fb2-9035-5d9fdf210c8e	TUESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6d01fdda-ba15-46b8-9096-e0eeca98c343	WEDNESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
15b4abf4-6f3f-4832-a6cf-bb1fbcf76358	THURSDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
02ad623f-cb6f-4575-8db6-3323e7e4ce3e	FRIDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
553c7449-a1fb-456c-a68a-62418bde18b3	MONDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	c33e01ef-be92-474a-9e43-0543649735d5
1a7d3e1b-239c-4bf4-a059-aa8073229eff	TUESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	c33e01ef-be92-474a-9e43-0543649735d5
73d19bb1-4771-4669-b96d-a58cab2f98bb	WEDNESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	c33e01ef-be92-474a-9e43-0543649735d5
5be6d9d9-e287-4768-9c2d-fe0b16eb8554	THURSDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	c33e01ef-be92-474a-9e43-0543649735d5
84fe094f-f76f-4588-a14a-5c58292d1c3d	FRIDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	c33e01ef-be92-474a-9e43-0543649735d5
35a74329-8e2f-4a86-9322-dc85119c9a42	MONDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	6f1475e5-0d8a-47c7-855f-ccff802aff85
e265e32e-b899-4ecb-be6f-415167713446	TUESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	6f1475e5-0d8a-47c7-855f-ccff802aff85
57d3e316-addd-4fdc-861c-25f0890f8dc3	WEDNESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	6f1475e5-0d8a-47c7-855f-ccff802aff85
8c0dfc45-0657-40a7-a5b4-9a8e1756a684	THURSDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	6f1475e5-0d8a-47c7-855f-ccff802aff85
26353814-3506-4c85-b1df-9571e012f410	FRIDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	6f1475e5-0d8a-47c7-855f-ccff802aff85
de17d771-dbce-41b0-b034-cf8397055b5f	MONDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	58845959-0984-4136-a733-b75f5c9057b9
4eb88e5e-c2f9-4455-9a5b-48606bdfcb2c	TUESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	58845959-0984-4136-a733-b75f5c9057b9
2897d418-3eaf-4388-bacd-434a83751c36	WEDNESDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	58845959-0984-4136-a733-b75f5c9057b9
ecf325ac-ec9f-4c3b-a20c-9f60d2d72f39	THURSDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	58845959-0984-4136-a733-b75f5c9057b9
66efbddd-afa8-4bdd-b68f-6b3715de85d7	FRIDAY	89f6ed60-6465-453c-b485-cc80b9d43e17	58845959-0984-4136-a733-b75f5c9057b9
976d3433-93c3-4af6-8dbd-d2b99f481e35	MONDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	440c17ed-5262-4800-aa19-38e5285330c3
3a48d399-73d1-48f9-a9ab-2b083c49b5ea	TUESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	440c17ed-5262-4800-aa19-38e5285330c3
2f69da5a-fbe2-424f-9626-f24313846adf	WEDNESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	440c17ed-5262-4800-aa19-38e5285330c3
da732c80-017c-4052-b983-62c48b0a9cf4	THURSDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	440c17ed-5262-4800-aa19-38e5285330c3
aab3865e-0341-4a33-aee2-9d28963d1844	FRIDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	440c17ed-5262-4800-aa19-38e5285330c3
1fb2d810-e7bf-4a7b-b95e-6d055886cf32	MONDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
70b06afb-29ad-4e8a-9df4-aee77092609f	TUESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d1dafa47-8c9c-431f-91b7-8545ffd2d5c3	WEDNESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c4067caa-474e-4a88-9ee8-d8fe97e72f82	THURSDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
26cf2b7c-97aa-43a6-bace-1e50ea05f32e	FRIDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
504d8fbb-f409-4c86-b360-6578ff76f069	MONDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	d54b130a-28a8-4bb1-9030-c474369c2205
0c85e954-d4f9-41d7-9cc1-4d4c3a13f5c5	TUESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	d54b130a-28a8-4bb1-9030-c474369c2205
6bb15d2c-cb2e-43ed-bf66-5d7e732c1ce8	WEDNESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	d54b130a-28a8-4bb1-9030-c474369c2205
84aa5d72-d432-4f70-bff1-ad974f501123	THURSDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	d54b130a-28a8-4bb1-9030-c474369c2205
ecb8dac3-197a-4a38-b54f-a4496b434db1	FRIDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	d54b130a-28a8-4bb1-9030-c474369c2205
cfdc43f5-308c-4fc8-a1b2-002689e4b409	MONDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2d410b03-c595-44dd-8b97-6782a511c36d	TUESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
08379ba9-2b0f-4da3-b238-51ef7cf24a10	WEDNESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a25a071b-b54f-4c5a-958e-63450b4945f7	THURSDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a9487e21-ba5e-4716-8454-61fa9a04663b	FRIDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
71338379-1872-49c3-987a-68e274a90924	MONDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	c33e01ef-be92-474a-9e43-0543649735d5
954ab3dd-7639-4ac4-94ea-edef595c09d9	TUESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	c33e01ef-be92-474a-9e43-0543649735d5
1532b9ef-aec5-443b-a981-b9c3b06b1664	WEDNESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	c33e01ef-be92-474a-9e43-0543649735d5
23a0caae-95e9-439e-8222-e9f3fa90d0d8	THURSDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	c33e01ef-be92-474a-9e43-0543649735d5
929a80a6-16a4-468f-a52a-2551923bde45	FRIDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	c33e01ef-be92-474a-9e43-0543649735d5
d7039695-6d96-423b-970d-093a1959b058	MONDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	6f1475e5-0d8a-47c7-855f-ccff802aff85
0d47742b-f3cc-4201-8320-419cd32882c6	TUESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	6f1475e5-0d8a-47c7-855f-ccff802aff85
eb9f7fa0-011d-4c1a-a0fc-000fa8f737f9	WEDNESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	6f1475e5-0d8a-47c7-855f-ccff802aff85
1558d183-abd5-442d-9fa3-ea291634a4a6	THURSDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	6f1475e5-0d8a-47c7-855f-ccff802aff85
01849d44-40f4-4b12-900d-427143f2bb97	FRIDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	6f1475e5-0d8a-47c7-855f-ccff802aff85
525d0e82-b16f-464e-b798-c88b07f05a1c	MONDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	58845959-0984-4136-a733-b75f5c9057b9
78f5febe-da4f-49ad-a138-2b03e3049a7d	TUESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	58845959-0984-4136-a733-b75f5c9057b9
56dc7cb5-b2f3-4847-961a-94bae665a2a3	WEDNESDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	58845959-0984-4136-a733-b75f5c9057b9
774d7d0f-9cdb-45fc-a27b-3397db2205d2	THURSDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	58845959-0984-4136-a733-b75f5c9057b9
5a0b4896-ced6-479d-8585-d6de227f874e	FRIDAY	541c7c37-fe94-4c97-8185-031e0fc7237b	58845959-0984-4136-a733-b75f5c9057b9
ec3f5eba-59d8-46bf-8fd5-10de42a615a3	MONDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	440c17ed-5262-4800-aa19-38e5285330c3
e1bf8232-982e-4c42-b061-b7848449521c	TUESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	440c17ed-5262-4800-aa19-38e5285330c3
3e456a14-0460-4720-bc04-3c06e9331385	WEDNESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	440c17ed-5262-4800-aa19-38e5285330c3
36a22557-9dc4-4b23-99ee-5be8533efe96	THURSDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	440c17ed-5262-4800-aa19-38e5285330c3
6bee0f8e-57d8-44d9-aec6-9b11528450b7	FRIDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	440c17ed-5262-4800-aa19-38e5285330c3
8434aa7b-99cb-4f25-ad5c-7373726f698f	MONDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f1eaeffb-44c3-4535-9462-101f7d06ab09	TUESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
039b64e7-4d68-416c-a578-d4f252960019	WEDNESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e6b0ca1d-3ad2-45b4-9563-9e107150e50f	THURSDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b1e44c53-29b3-406d-a535-b6155b9defe5	FRIDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ba091bfb-be32-4dc1-b0c0-cca68643dfc6	MONDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	d54b130a-28a8-4bb1-9030-c474369c2205
0eb249f7-53e2-4872-8c4f-ffc6299c09e0	TUESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	d54b130a-28a8-4bb1-9030-c474369c2205
d063bc3d-d374-495a-8bbb-c596025fd51c	WEDNESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	d54b130a-28a8-4bb1-9030-c474369c2205
4ddf2352-37c1-488e-aeeb-a32c8e1d9487	THURSDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	d54b130a-28a8-4bb1-9030-c474369c2205
a03583b3-b059-4371-a551-92fbd04a6296	FRIDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	d54b130a-28a8-4bb1-9030-c474369c2205
b01bdf80-7274-4fbe-bd25-f4f8f68fd187	MONDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
78851b36-6765-41d7-aaa9-84cb62ee6321	TUESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
33fb17b0-3115-4ed0-a039-b66084d69777	WEDNESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9c9ee581-3460-4d20-8828-6d60a0faebaa	THURSDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
92a9e418-d3c0-429f-a4cf-052a993f58ce	FRIDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f92a6888-3f60-48d7-9f4f-c91c17861e35	MONDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	c33e01ef-be92-474a-9e43-0543649735d5
7b7a77ae-529b-4a07-a0e4-84ce45525bc1	TUESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	c33e01ef-be92-474a-9e43-0543649735d5
b1cdc893-d3d4-4702-b11d-661831f8c4f9	WEDNESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	c33e01ef-be92-474a-9e43-0543649735d5
f8f59c62-a046-447e-9e34-26956dec06d5	THURSDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	c33e01ef-be92-474a-9e43-0543649735d5
8b1b36ff-e39a-49a9-8aea-a843f4cfaeb8	FRIDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	c33e01ef-be92-474a-9e43-0543649735d5
de3d4b6c-28b1-43c9-aced-ac3a91f7fddc	MONDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	6f1475e5-0d8a-47c7-855f-ccff802aff85
7754ba12-e309-498d-8eff-2c995aa67ad7	TUESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	6f1475e5-0d8a-47c7-855f-ccff802aff85
7009902d-ede7-48b8-8b6c-930ad265f765	WEDNESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	6f1475e5-0d8a-47c7-855f-ccff802aff85
5b5ffba9-7ad9-480f-965c-6ad915bc0be6	THURSDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	6f1475e5-0d8a-47c7-855f-ccff802aff85
c3b4efc2-f5c1-4be9-a714-ae57096602de	FRIDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	6f1475e5-0d8a-47c7-855f-ccff802aff85
2bc3f037-ff6e-41ba-a214-e16e0a47b6f1	MONDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	58845959-0984-4136-a733-b75f5c9057b9
3a128fbe-26ee-4478-977b-7dd3121f460c	TUESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	58845959-0984-4136-a733-b75f5c9057b9
0668a2e7-42a3-4dc8-af9e-069449570915	WEDNESDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	58845959-0984-4136-a733-b75f5c9057b9
6775138e-514a-4272-9f5a-3ba3419620c5	MONDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	440c17ed-5262-4800-aa19-38e5285330c3
784b750b-a77c-4733-9802-6db6130d7fc0	TUESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	440c17ed-5262-4800-aa19-38e5285330c3
86c7865f-c09e-4977-9ed0-966730519a86	WEDNESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	440c17ed-5262-4800-aa19-38e5285330c3
c6baac64-f814-43d9-bca0-b49e9a236fe5	THURSDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	440c17ed-5262-4800-aa19-38e5285330c3
c324530d-931b-404a-9f2d-4d5083a14afe	FRIDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	440c17ed-5262-4800-aa19-38e5285330c3
dd9caf1a-2e30-44f7-8247-cae7224ce9b1	MONDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b791a86b-7726-493a-852e-6aa200b17420	TUESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8e2ff29d-b485-449d-b830-a8028bb004ca	WEDNESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fc33eb5a-ed44-4e82-8479-470d79e73fa0	THURSDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b9903b6b-499d-4cd1-a95b-5999f11d4dcb	FRIDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c0418ec7-1939-4544-a68c-a9f9264986b4	MONDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	d54b130a-28a8-4bb1-9030-c474369c2205
ed496b91-9ad9-4c99-b585-6c7556eb8f25	TUESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	d54b130a-28a8-4bb1-9030-c474369c2205
90adda1d-13ad-4239-9e91-e20cdc8f14e4	WEDNESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	d54b130a-28a8-4bb1-9030-c474369c2205
eda25116-e515-4691-bc3e-5c3bfbcddc15	THURSDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	d54b130a-28a8-4bb1-9030-c474369c2205
43665677-bf72-425c-bec5-73c5ba9b392d	FRIDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	d54b130a-28a8-4bb1-9030-c474369c2205
f7d3bb94-9c98-4d2f-8613-b0ea97b0de60	MONDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ce3045a9-d0cf-46a3-80ee-f91719ab42bb	TUESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
72e65abe-c4fb-4eeb-a33a-67e21b0d4e58	WEDNESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
793d44c6-c24e-464a-91c7-70d237ad23eb	THURSDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
389810c4-1e2d-41e7-94ca-81d21279a5bc	FRIDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
76d9f0c2-283c-411d-83a4-ebfbcd05fc1b	MONDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	c33e01ef-be92-474a-9e43-0543649735d5
00534cd1-a78b-4913-a256-06f8afc6bd48	TUESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	c33e01ef-be92-474a-9e43-0543649735d5
38f3b1be-d1ca-4d47-a691-2fb505d32f4a	WEDNESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	c33e01ef-be92-474a-9e43-0543649735d5
17f3fea9-dd5c-447c-b3b7-32b4f049b25d	THURSDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	c33e01ef-be92-474a-9e43-0543649735d5
3e2ccdcb-43b3-4d55-81ed-7caf86cc7333	FRIDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	c33e01ef-be92-474a-9e43-0543649735d5
b9024338-3bf4-4ba2-a590-5f8b05bc0162	MONDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	6f1475e5-0d8a-47c7-855f-ccff802aff85
371552d9-ccf8-4a47-ba7d-842b5ee54016	TUESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	6f1475e5-0d8a-47c7-855f-ccff802aff85
25926433-ec9e-42e0-b516-3d42c5d8ad7d	WEDNESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	6f1475e5-0d8a-47c7-855f-ccff802aff85
9f7cf617-75b3-4f3f-bbdd-bef739b50868	THURSDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	6f1475e5-0d8a-47c7-855f-ccff802aff85
0c82bce7-f5ec-48e6-be3e-6c23488a6e65	FRIDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	6f1475e5-0d8a-47c7-855f-ccff802aff85
6e456f99-8681-4492-bbbf-81dd19d3bb60	MONDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	58845959-0984-4136-a733-b75f5c9057b9
0a55a1bb-e383-4091-9ab2-019aef541886	TUESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	58845959-0984-4136-a733-b75f5c9057b9
e11e9655-0688-4916-8453-dbfb2e4315a5	WEDNESDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	58845959-0984-4136-a733-b75f5c9057b9
80ab29c1-2002-44fa-95c4-d8747fd27371	THURSDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	58845959-0984-4136-a733-b75f5c9057b9
27dc339b-6f02-4c1b-a537-604928f62f3e	FRIDAY	bfc2ac28-44fa-479c-bb71-2f858690cdc6	58845959-0984-4136-a733-b75f5c9057b9
0a07a4ed-955d-44b9-b96e-b06727bfea50	MONDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	440c17ed-5262-4800-aa19-38e5285330c3
e55f9bec-32f4-47b4-986a-a5051b45f8ed	TUESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	440c17ed-5262-4800-aa19-38e5285330c3
eb6c709d-b53a-42bf-a834-8ae378166b1c	WEDNESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	440c17ed-5262-4800-aa19-38e5285330c3
136ac878-f575-44ab-aff7-c10e284dd9ae	THURSDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	440c17ed-5262-4800-aa19-38e5285330c3
4cfe3b8b-57ed-4bdc-8b58-aa1fc5459f89	FRIDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	440c17ed-5262-4800-aa19-38e5285330c3
87089693-e293-4e0a-ad32-84cc7abf0c23	MONDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	5f76a266-8d18-4da8-9732-b63d4d8f24a0
af0e639e-09fb-4f87-bb56-7f27ac67abb8	TUESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4fd96d1e-e9df-4cda-b329-2712d9061653	WEDNESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	5f76a266-8d18-4da8-9732-b63d4d8f24a0
18452237-a3f9-4bdf-8070-d56f68515545	THURSDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1c9d7dc9-8540-4e22-b623-d006e134722b	FRIDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fd1ef0cf-96cd-4ce3-9d52-3f990e32c158	MONDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	d54b130a-28a8-4bb1-9030-c474369c2205
f11e5d54-9d07-451f-84dc-2e51440f08df	TUESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	d54b130a-28a8-4bb1-9030-c474369c2205
167669ed-5646-4510-acd0-a2800fd9756a	WEDNESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	d54b130a-28a8-4bb1-9030-c474369c2205
3c7c7466-fce4-4e13-87ea-26f3771a7b2a	THURSDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	d54b130a-28a8-4bb1-9030-c474369c2205
67f9405c-586c-4daf-8141-bf57df12f1e8	FRIDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	d54b130a-28a8-4bb1-9030-c474369c2205
53321fa6-14b7-4141-a03a-7b7a7eb9d4dd	MONDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b0491549-2991-4df8-8ba5-1c93e0da30c6	TUESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c5e31d70-54f0-4b8a-9437-33a8135cf808	WEDNESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6ff8e5ca-07e2-4e45-87d8-62effa242bb8	THURSDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b18a9bb0-7826-4688-b692-3157a1c8001c	FRIDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
619d1538-13b1-4803-b836-c758ad4845f8	MONDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	c33e01ef-be92-474a-9e43-0543649735d5
2c369a36-9316-41a0-aa23-eb4c7048718c	TUESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	c33e01ef-be92-474a-9e43-0543649735d5
702b1a46-0033-47f5-bfc9-73e7418d23b0	WEDNESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	c33e01ef-be92-474a-9e43-0543649735d5
540ab4d5-11c4-4e1c-8348-b3e22ab59bee	THURSDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	c33e01ef-be92-474a-9e43-0543649735d5
2414c489-931e-4f7d-9d5d-a84bff567668	FRIDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	c33e01ef-be92-474a-9e43-0543649735d5
bdd59388-eacc-443f-9363-e4c85bc49162	MONDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	6f1475e5-0d8a-47c7-855f-ccff802aff85
f2f32c85-f212-4e89-be56-2110e744ad41	TUESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	6f1475e5-0d8a-47c7-855f-ccff802aff85
c6fd18ee-7593-4630-8685-e0955c953793	WEDNESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	6f1475e5-0d8a-47c7-855f-ccff802aff85
36be7c2c-7528-4ef5-a110-d972fa7012a3	THURSDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	6f1475e5-0d8a-47c7-855f-ccff802aff85
19521e46-065c-4ad3-bb04-8fa88722c75b	FRIDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	6f1475e5-0d8a-47c7-855f-ccff802aff85
84c5dd9b-6760-4da0-bc81-6c2685c5e3aa	MONDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	58845959-0984-4136-a733-b75f5c9057b9
2141127a-30c2-4a29-b188-d7bc318850e7	TUESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	58845959-0984-4136-a733-b75f5c9057b9
73a13085-c529-4046-8c50-afedead90da6	WEDNESDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	58845959-0984-4136-a733-b75f5c9057b9
f3ed8336-563f-4e14-8f4e-1d91cbd62845	THURSDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	58845959-0984-4136-a733-b75f5c9057b9
b29d6c01-9869-4a22-a97b-36854d43803b	FRIDAY	43cbec95-02a1-44b2-bf05-f55d105c75ec	58845959-0984-4136-a733-b75f5c9057b9
feeb4d3e-7bf5-4e3c-9261-62a8a11c8f73	MONDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	440c17ed-5262-4800-aa19-38e5285330c3
e57d6d00-23e7-4fc4-a3aa-6d4d4cd18da1	TUESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	440c17ed-5262-4800-aa19-38e5285330c3
70876817-3978-462f-9896-90ed40e8c238	WEDNESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	440c17ed-5262-4800-aa19-38e5285330c3
2a384943-8318-4973-be55-35c4a1a2882b	THURSDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	440c17ed-5262-4800-aa19-38e5285330c3
91d3551b-84cf-4a2f-a340-9b69484c791c	FRIDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	440c17ed-5262-4800-aa19-38e5285330c3
0c43d5ab-fbbd-4649-9d69-f23cb2766cd3	MONDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
46758e04-0fc7-4072-813b-729e50a4b6cb	TUESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ebff4fdc-60b9-4371-8028-760d0b3169d3	WEDNESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1ccbc6fc-0700-46a0-b5da-3f660711e55b	THURSDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2b713a10-5952-4a71-99ee-9172e0144e06	FRIDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
00eb919d-abff-48e0-bbc9-0b85e6b6584e	MONDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	d54b130a-28a8-4bb1-9030-c474369c2205
fb1c2c52-0663-4d61-90fe-280015c39670	TUESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	d54b130a-28a8-4bb1-9030-c474369c2205
a34cd1e9-cd69-45e7-967b-b7c14a5c35b7	WEDNESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	d54b130a-28a8-4bb1-9030-c474369c2205
bb500ee0-efd7-441f-aa23-788b580ee396	THURSDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	d54b130a-28a8-4bb1-9030-c474369c2205
2e0e7628-0ca4-46d7-94f1-9a9bbcdcbdd2	FRIDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	d54b130a-28a8-4bb1-9030-c474369c2205
3a9290d1-d5d2-40bd-ac97-7a968123a974	MONDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
57f19f05-b17b-489e-9071-a2a2332d37b0	TUESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
30cef120-6b49-4e0c-ae74-3eea3ffafaf7	WEDNESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1c7dfd92-4925-454a-81d6-1ab825c7119f	THURSDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1aa187ca-e4ec-4815-890f-fc1252af7249	FRIDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
62c00c74-c43b-4e1b-a46e-c999e3fe6081	MONDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	c33e01ef-be92-474a-9e43-0543649735d5
4a683c47-7760-4d4c-b6b5-2a6bde854ea3	TUESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	c33e01ef-be92-474a-9e43-0543649735d5
be19e17d-23e7-48cc-8013-ec62cf5d8f7a	WEDNESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	c33e01ef-be92-474a-9e43-0543649735d5
8d540dcf-817a-4b01-9149-cf6d4868218c	THURSDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	c33e01ef-be92-474a-9e43-0543649735d5
2af5180a-9f65-4716-9d1b-81628e6f4d51	FRIDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	c33e01ef-be92-474a-9e43-0543649735d5
d7a6fb5c-2255-4378-aaee-8e78f180a2fa	MONDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	6f1475e5-0d8a-47c7-855f-ccff802aff85
67a585dc-521b-4da0-88db-947fd4967be5	TUESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	6f1475e5-0d8a-47c7-855f-ccff802aff85
7571a9d1-e1b7-4ad6-958f-91aff16d8128	WEDNESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	6f1475e5-0d8a-47c7-855f-ccff802aff85
2bc9882f-fc78-44ff-93a2-6258d6b3bcfe	THURSDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	6f1475e5-0d8a-47c7-855f-ccff802aff85
5f11a31f-adde-4da7-bf14-766ca52a2814	FRIDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	6f1475e5-0d8a-47c7-855f-ccff802aff85
27df5f72-591e-423c-849e-7796a3a94196	MONDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	58845959-0984-4136-a733-b75f5c9057b9
1d3ef0d4-a8f3-41ff-9d00-7f7592670bba	TUESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	58845959-0984-4136-a733-b75f5c9057b9
1d4025e8-5ca9-4be9-a913-276494fa9dc3	WEDNESDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	58845959-0984-4136-a733-b75f5c9057b9
32174e33-6a2a-4397-b9cd-3c647053cf9c	THURSDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	58845959-0984-4136-a733-b75f5c9057b9
c67a0c90-d226-4845-9967-743238432578	FRIDAY	6eff3b68-53f2-4b85-a10d-dd9559f3879e	58845959-0984-4136-a733-b75f5c9057b9
69fc7f92-2184-4bad-a892-6f7b2e4d7487	MONDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	440c17ed-5262-4800-aa19-38e5285330c3
8c82f0c0-e519-4834-90e2-946a8b206ae7	TUESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	440c17ed-5262-4800-aa19-38e5285330c3
96613851-942d-4dda-8282-9782bae1b665	WEDNESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	440c17ed-5262-4800-aa19-38e5285330c3
a9de784e-fe77-4797-b78e-0f1f05f777d3	THURSDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	440c17ed-5262-4800-aa19-38e5285330c3
004ea154-5aba-4c6f-833a-5f2c8f70371f	FRIDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	440c17ed-5262-4800-aa19-38e5285330c3
977be6e7-93d0-4ae5-b775-79eb894fb374	MONDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fa8d374a-6def-4f56-b9ff-f2e9231ce7b2	TUESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fca4c5c8-043c-4f5e-b708-1d16e5248689	WEDNESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3196fbdf-a506-4d49-b107-f8d196e39d5a	THURSDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	5f76a266-8d18-4da8-9732-b63d4d8f24a0
79ade95c-f51b-4ce1-a7c1-23273607e03d	FRIDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2af112c8-0925-4e9d-bd28-fe5c40749e46	MONDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	d54b130a-28a8-4bb1-9030-c474369c2205
bc24c583-2570-4e3a-ac1c-cb74d916757b	TUESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	d54b130a-28a8-4bb1-9030-c474369c2205
c793735f-acdf-4211-8c62-86f859835d36	WEDNESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	d54b130a-28a8-4bb1-9030-c474369c2205
a1ea6108-f178-47c8-9c3d-c12597e02bc3	THURSDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	d54b130a-28a8-4bb1-9030-c474369c2205
5e72d9df-8ea5-436f-8349-e0a3e613a8d1	FRIDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	d54b130a-28a8-4bb1-9030-c474369c2205
e8948359-a6ab-48d3-8855-4b69486c3a43	MONDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ab280ded-b393-42b0-8b2e-70287a1d480b	TUESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cd9264df-8671-4277-9837-80b93189478a	WEDNESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2bc3907b-6d40-46f6-ad7c-269f17c00e4f	THURSDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1ba4d74a-35ff-4f8b-95a8-b1253ab06c42	FRIDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9ce0eb17-a6b2-47b5-8191-0b50766238b3	MONDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	c33e01ef-be92-474a-9e43-0543649735d5
b50f2348-c921-4ba4-a458-ef6caf63be6c	TUESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	c33e01ef-be92-474a-9e43-0543649735d5
939f6757-0797-4646-a6b7-b3af2f4a20d7	WEDNESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	c33e01ef-be92-474a-9e43-0543649735d5
da57bcd1-970f-413f-975b-b67e0acd603a	THURSDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	c33e01ef-be92-474a-9e43-0543649735d5
986d0a99-d8af-4bf0-83c1-9fb3924168f1	FRIDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	c33e01ef-be92-474a-9e43-0543649735d5
92c03c33-8921-46fe-a60e-c4f92a3e2b79	MONDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	6f1475e5-0d8a-47c7-855f-ccff802aff85
695bc094-7d83-4552-86cd-88c5ade96e10	TUESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	6f1475e5-0d8a-47c7-855f-ccff802aff85
fe80a25c-e796-4d51-bca7-9e82677e1938	WEDNESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	6f1475e5-0d8a-47c7-855f-ccff802aff85
f8195147-6f28-48b1-8909-184c49479153	THURSDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	6f1475e5-0d8a-47c7-855f-ccff802aff85
73fe7b36-7562-48da-8d7a-5a687916ea48	FRIDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	6f1475e5-0d8a-47c7-855f-ccff802aff85
f5e0eabb-0fde-4581-bb22-578f87c27310	MONDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	58845959-0984-4136-a733-b75f5c9057b9
fcc4671b-0aad-44c1-8b94-2652ddc24725	TUESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	58845959-0984-4136-a733-b75f5c9057b9
d5bf19f6-44f8-456c-b1a2-37fa28cfeae7	WEDNESDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	58845959-0984-4136-a733-b75f5c9057b9
4a1a5209-b70f-47cf-b9a2-b3a7517722f0	THURSDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	58845959-0984-4136-a733-b75f5c9057b9
9034e993-1866-47a2-b562-be20e2753006	FRIDAY	7dce6776-3a71-45b5-aa9b-b33ff44e84be	58845959-0984-4136-a733-b75f5c9057b9
65ff1d72-61c9-46d5-94f1-e42241865b80	THURSDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	58845959-0984-4136-a733-b75f5c9057b9
b92b1b3d-9e92-4a77-8989-e875c38528e0	FRIDAY	5632aa7f-8ff0-4b5d-bc91-77d93c3b9dd5	58845959-0984-4136-a733-b75f5c9057b9
30b43af8-c03e-489d-9b69-412182cd6851	MONDAY	8352510c-ee2b-4a43-b002-62affd865904	440c17ed-5262-4800-aa19-38e5285330c3
b8515315-8c84-48e6-ac7c-520e49440c8b	TUESDAY	8352510c-ee2b-4a43-b002-62affd865904	440c17ed-5262-4800-aa19-38e5285330c3
becefad8-5516-4bad-b462-b069f4fc8dcd	WEDNESDAY	8352510c-ee2b-4a43-b002-62affd865904	440c17ed-5262-4800-aa19-38e5285330c3
0d007079-20d6-406d-9bf0-b92a23b928a9	THURSDAY	8352510c-ee2b-4a43-b002-62affd865904	440c17ed-5262-4800-aa19-38e5285330c3
c08542b5-6adf-4192-9e90-374b576608f6	FRIDAY	8352510c-ee2b-4a43-b002-62affd865904	440c17ed-5262-4800-aa19-38e5285330c3
c2f1c393-a479-45d4-92e9-0975b87f637f	MONDAY	8352510c-ee2b-4a43-b002-62affd865904	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b5a543f7-27d5-4ec6-bbb5-1d4160996bb1	TUESDAY	8352510c-ee2b-4a43-b002-62affd865904	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ab981951-5bec-4165-a448-28e0bb203833	WEDNESDAY	8352510c-ee2b-4a43-b002-62affd865904	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3838c4e2-6f22-49b3-9a88-5996e7746e99	THURSDAY	8352510c-ee2b-4a43-b002-62affd865904	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a4f8372b-7116-41c0-9bfe-c59d51b7d239	FRIDAY	8352510c-ee2b-4a43-b002-62affd865904	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5f76f956-ccae-4d28-bd62-4dd6c63d22fa	MONDAY	8352510c-ee2b-4a43-b002-62affd865904	d54b130a-28a8-4bb1-9030-c474369c2205
7516699c-c6a5-4a07-bbbc-c35fd127a4c4	TUESDAY	8352510c-ee2b-4a43-b002-62affd865904	d54b130a-28a8-4bb1-9030-c474369c2205
caa86cfc-b978-4a6e-9456-6fc7ce99fa4c	WEDNESDAY	8352510c-ee2b-4a43-b002-62affd865904	d54b130a-28a8-4bb1-9030-c474369c2205
cf75aaf8-e95c-42d5-b551-1c9c32381a4b	THURSDAY	8352510c-ee2b-4a43-b002-62affd865904	d54b130a-28a8-4bb1-9030-c474369c2205
c541c7f8-49b1-477c-86f2-3550659416d6	FRIDAY	8352510c-ee2b-4a43-b002-62affd865904	d54b130a-28a8-4bb1-9030-c474369c2205
9923421d-5e11-48bb-a534-2aa133bcf87e	MONDAY	8352510c-ee2b-4a43-b002-62affd865904	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5dc7ef19-c0a1-45bf-89a0-06cba6497789	TUESDAY	8352510c-ee2b-4a43-b002-62affd865904	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
dbc9efe2-423a-431b-af51-2ffbae7545b2	WEDNESDAY	8352510c-ee2b-4a43-b002-62affd865904	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3c2515ef-cab1-495a-b103-1d22fc6f34af	THURSDAY	8352510c-ee2b-4a43-b002-62affd865904	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
463119a2-dff5-46fb-9b5c-b25f8e01fcf0	FRIDAY	8352510c-ee2b-4a43-b002-62affd865904	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b0e9e7f2-aaa9-4efc-a819-5870e9cfa849	MONDAY	8352510c-ee2b-4a43-b002-62affd865904	c33e01ef-be92-474a-9e43-0543649735d5
878f43e4-4127-4785-8573-6d6b631e78d8	TUESDAY	8352510c-ee2b-4a43-b002-62affd865904	c33e01ef-be92-474a-9e43-0543649735d5
03b7dd05-2344-493d-b80c-c7217d9df398	WEDNESDAY	8352510c-ee2b-4a43-b002-62affd865904	c33e01ef-be92-474a-9e43-0543649735d5
01422bda-7938-4f59-ab81-5233204f9d0e	THURSDAY	8352510c-ee2b-4a43-b002-62affd865904	c33e01ef-be92-474a-9e43-0543649735d5
a7fbd921-9d5d-43b8-89a1-0d705bcbe14f	FRIDAY	8352510c-ee2b-4a43-b002-62affd865904	c33e01ef-be92-474a-9e43-0543649735d5
a7a2df8a-f5f1-4527-820e-76cfddfd48f5	MONDAY	8352510c-ee2b-4a43-b002-62affd865904	6f1475e5-0d8a-47c7-855f-ccff802aff85
271ff36f-8705-4001-b496-5865ec9b0f75	TUESDAY	8352510c-ee2b-4a43-b002-62affd865904	6f1475e5-0d8a-47c7-855f-ccff802aff85
b4b46aae-688e-4ec9-98f0-0eb6b761b3a0	WEDNESDAY	8352510c-ee2b-4a43-b002-62affd865904	6f1475e5-0d8a-47c7-855f-ccff802aff85
12908113-199d-47ea-849a-606b2f4bd428	THURSDAY	8352510c-ee2b-4a43-b002-62affd865904	6f1475e5-0d8a-47c7-855f-ccff802aff85
fa375a91-40e5-4805-bc64-ce8400f02217	FRIDAY	8352510c-ee2b-4a43-b002-62affd865904	6f1475e5-0d8a-47c7-855f-ccff802aff85
d63fb475-1fce-4c60-8f92-4527dd4cf264	MONDAY	8352510c-ee2b-4a43-b002-62affd865904	58845959-0984-4136-a733-b75f5c9057b9
a4f82e2f-a6b6-4459-870f-a1eedb92681c	TUESDAY	8352510c-ee2b-4a43-b002-62affd865904	58845959-0984-4136-a733-b75f5c9057b9
162e4f7b-b3f0-47e6-9a70-e40020bf7c97	WEDNESDAY	8352510c-ee2b-4a43-b002-62affd865904	58845959-0984-4136-a733-b75f5c9057b9
2f7166fa-c020-41ae-864d-3e9e8c8a8d16	THURSDAY	8352510c-ee2b-4a43-b002-62affd865904	58845959-0984-4136-a733-b75f5c9057b9
8414047c-cf6a-414b-a2a1-5578212b7274	FRIDAY	8352510c-ee2b-4a43-b002-62affd865904	58845959-0984-4136-a733-b75f5c9057b9
dcda9586-ca41-4175-b853-c68cbccda291	MONDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	440c17ed-5262-4800-aa19-38e5285330c3
df935430-e115-477c-8dbc-00bf9de3dddb	TUESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	440c17ed-5262-4800-aa19-38e5285330c3
99569f07-5ce8-411b-8ebf-81c8d89fdd0c	WEDNESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	440c17ed-5262-4800-aa19-38e5285330c3
2c712c9f-f4ce-4037-9c37-0765b87864be	THURSDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	440c17ed-5262-4800-aa19-38e5285330c3
d7c75f6c-8800-45a7-97fe-c6f1d7ffe233	FRIDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	440c17ed-5262-4800-aa19-38e5285330c3
d2e90301-593f-4f29-a0a7-1ccf87500bde	MONDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b89c82a8-a1b9-4994-a2c3-0a133d9749c3	TUESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6a9cab57-958b-47b4-a7da-3b88adb5c34d	WEDNESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	5f76a266-8d18-4da8-9732-b63d4d8f24a0
55e569b5-349a-497a-a201-1a4cf030a920	THURSDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ec8e33e9-ba2d-42b8-b719-78b8c8f51f6e	MONDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	440c17ed-5262-4800-aa19-38e5285330c3
bff54707-342e-4269-b384-b2269e76e23a	TUESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	440c17ed-5262-4800-aa19-38e5285330c3
75e2272f-1edf-47ed-a841-c532751e7d47	WEDNESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	440c17ed-5262-4800-aa19-38e5285330c3
c1814d03-105b-46f0-859a-53bb2f1b9427	THURSDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	440c17ed-5262-4800-aa19-38e5285330c3
5c61fee3-703b-4a9a-aa94-d8c943ac49a9	FRIDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	440c17ed-5262-4800-aa19-38e5285330c3
06a3c83c-5cd4-4c6a-a4fb-7c8701b0c7d4	MONDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e2756dbb-f59c-4402-b68f-32a032998493	TUESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
87e52ece-c414-4a15-b74b-f514712fd9b7	WEDNESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2fd6cf4e-1ef9-4446-87f7-b628278196da	THURSDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
79b13081-8a50-4c74-bda8-d8182b5296ea	FRIDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6437c0e5-05d1-4f2e-a21e-ebaf6ba38902	MONDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	d54b130a-28a8-4bb1-9030-c474369c2205
b65823b2-6587-4563-b002-d9ad6a64b5c0	TUESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	d54b130a-28a8-4bb1-9030-c474369c2205
7f8d2445-b4ed-42c3-8282-98d9ca674658	WEDNESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	d54b130a-28a8-4bb1-9030-c474369c2205
100d4287-a9ff-41a9-9701-aa0302e717bc	THURSDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	d54b130a-28a8-4bb1-9030-c474369c2205
b1d0bb2f-f091-42ee-971f-a143ae3ca0d3	FRIDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	d54b130a-28a8-4bb1-9030-c474369c2205
f62b9810-9b5a-4174-9fda-bfed49e699b4	MONDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
42d316fa-e6b1-48d0-afcf-14677199c506	TUESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b10e56ac-e85b-4248-9384-24020625d4a2	WEDNESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bbd420b8-8e37-4148-be58-5b0eec54e016	THURSDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
dd5bf978-300a-4f6d-b65f-c9ba280e2a9b	FRIDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9120036e-5979-43a2-99c4-f867c6f17f32	MONDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	c33e01ef-be92-474a-9e43-0543649735d5
a2e861de-1eb0-407f-86e2-60f39c0be603	TUESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	c33e01ef-be92-474a-9e43-0543649735d5
3247ced1-93c6-4adc-8104-79aff4f831ab	WEDNESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	c33e01ef-be92-474a-9e43-0543649735d5
f0c238b9-8a30-453d-9d43-1ba50e166dce	THURSDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	c33e01ef-be92-474a-9e43-0543649735d5
56fdf42f-5435-487e-a942-60db581dfac0	FRIDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	c33e01ef-be92-474a-9e43-0543649735d5
4ca17f1f-7d2d-4f73-afdb-d86769c09f3e	MONDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	6f1475e5-0d8a-47c7-855f-ccff802aff85
b45af17f-81ab-4181-8540-18d31278ef45	TUESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	6f1475e5-0d8a-47c7-855f-ccff802aff85
114ca0b1-4de3-4335-b008-c78ddac0b160	WEDNESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	6f1475e5-0d8a-47c7-855f-ccff802aff85
592f3e09-e910-44c6-9edc-8ef0e5e64a90	THURSDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	6f1475e5-0d8a-47c7-855f-ccff802aff85
2d3f3019-762a-4a27-8955-9db624b46099	FRIDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	6f1475e5-0d8a-47c7-855f-ccff802aff85
8b08d61d-7d2e-4f31-ad49-de17b8a8bad5	MONDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	58845959-0984-4136-a733-b75f5c9057b9
c13b6946-edad-4a32-b559-4d9d414e52ea	TUESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	58845959-0984-4136-a733-b75f5c9057b9
c226e3f5-610d-43b7-ae10-1102d079e674	WEDNESDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	58845959-0984-4136-a733-b75f5c9057b9
50f663af-9313-4a14-a0f2-56fcb3864017	THURSDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	58845959-0984-4136-a733-b75f5c9057b9
6be70135-93bf-49cc-8e9b-e674abbd60a7	FRIDAY	3cdf2cc9-cebd-419e-b0f2-22d7d674f18e	58845959-0984-4136-a733-b75f5c9057b9
bb19e97e-99c8-46e7-9485-16ec3c443705	MONDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	440c17ed-5262-4800-aa19-38e5285330c3
67febf61-42f2-4d07-9402-c92bec6dccaa	TUESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	440c17ed-5262-4800-aa19-38e5285330c3
81312f7b-a670-438a-9f94-8e2d2f53cb3f	WEDNESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	440c17ed-5262-4800-aa19-38e5285330c3
aad0fd17-c657-4e2f-8c96-db8bf5a93cee	THURSDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	440c17ed-5262-4800-aa19-38e5285330c3
09a21b2e-9ea4-45ea-98c1-4b4bb2290844	FRIDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	440c17ed-5262-4800-aa19-38e5285330c3
6dc1a908-703c-4324-ae2c-6e8ec7b3872a	MONDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a18f077b-6690-4f46-b528-263499b073fe	TUESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	5f76a266-8d18-4da8-9732-b63d4d8f24a0
73e025af-7064-4f99-9c33-109f1f59cc0a	WEDNESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	5f76a266-8d18-4da8-9732-b63d4d8f24a0
34ad8025-3b05-4d86-90f4-6b3c5a0ad1d5	THURSDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fb286516-4ff2-4622-91e0-d36bc9bef6a3	FRIDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5e028d2d-b5bd-40e6-bbb5-559e702b45d3	MONDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	d54b130a-28a8-4bb1-9030-c474369c2205
891ff7b5-f00a-42bf-9c16-cfb271328c12	TUESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	d54b130a-28a8-4bb1-9030-c474369c2205
da98f292-a067-4d1a-8f03-5a86ee43914a	WEDNESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	d54b130a-28a8-4bb1-9030-c474369c2205
421f9961-0b9e-4a22-923f-699a285816b7	THURSDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	d54b130a-28a8-4bb1-9030-c474369c2205
7c05180e-c98e-458e-b00d-7336a0c5cf0c	FRIDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	d54b130a-28a8-4bb1-9030-c474369c2205
6a9fa4ed-94a2-4d66-9647-7c1bf24ee223	MONDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f7589bf0-68e4-4e69-a677-be363b76b121	TUESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f75a11ca-81ce-4cd7-980f-4ed6caca65b6	WEDNESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ba59154e-63c4-48ec-b372-e27349237b35	THURSDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8d431cd1-c44e-481e-a6a3-8de30823caa3	FRIDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2d80bc96-99a1-4fd6-ac0b-77cbc55aeb35	MONDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	c33e01ef-be92-474a-9e43-0543649735d5
75350b84-001e-430b-b684-b60d192498c2	TUESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	c33e01ef-be92-474a-9e43-0543649735d5
0c0a7557-2771-4faf-80a1-ec49e8355359	WEDNESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	c33e01ef-be92-474a-9e43-0543649735d5
1bea32f2-149e-44f5-8fe2-8ee7644a6ff7	THURSDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	c33e01ef-be92-474a-9e43-0543649735d5
0b861c05-5b2d-47fc-b745-edf8808c6318	FRIDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	c33e01ef-be92-474a-9e43-0543649735d5
7ffb931d-f608-4cc1-b192-4c9dbe20827a	MONDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	6f1475e5-0d8a-47c7-855f-ccff802aff85
3216f85b-65d8-4f30-9513-9abe3b4e61fc	TUESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	6f1475e5-0d8a-47c7-855f-ccff802aff85
e0abd023-9594-4221-ad0f-d8528102f2ed	WEDNESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	6f1475e5-0d8a-47c7-855f-ccff802aff85
803864ec-d53f-49f4-a6a9-2b88ec2b3175	THURSDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	6f1475e5-0d8a-47c7-855f-ccff802aff85
b8c0bda5-cb51-447c-923d-66de960b1c48	FRIDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	6f1475e5-0d8a-47c7-855f-ccff802aff85
54a363e8-5cf4-4576-ac91-e1906bdbb79c	MONDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	58845959-0984-4136-a733-b75f5c9057b9
8178f022-0db7-4277-bfe1-69bdbd94554c	TUESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	58845959-0984-4136-a733-b75f5c9057b9
9a474629-6eed-450f-a151-ad9c99d30fa6	WEDNESDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	58845959-0984-4136-a733-b75f5c9057b9
81bd99ed-3162-435b-be44-db4bba8cccb6	THURSDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	58845959-0984-4136-a733-b75f5c9057b9
7c9304e2-16eb-49ec-892f-0e994063c54e	FRIDAY	73b3ef2e-becd-45c3-8f7e-9531a83e53fc	58845959-0984-4136-a733-b75f5c9057b9
625d7005-0652-4db2-9008-ecb97741e035	MONDAY	cffee650-43db-4f7f-b479-d0b439e61287	440c17ed-5262-4800-aa19-38e5285330c3
c3e75554-6b32-49ad-8908-5252f57784da	TUESDAY	cffee650-43db-4f7f-b479-d0b439e61287	440c17ed-5262-4800-aa19-38e5285330c3
48d3bc3d-d456-4359-88bd-361d057a6750	WEDNESDAY	cffee650-43db-4f7f-b479-d0b439e61287	440c17ed-5262-4800-aa19-38e5285330c3
070a3a10-4bda-4ef3-98f1-eb50ab3987bd	THURSDAY	cffee650-43db-4f7f-b479-d0b439e61287	440c17ed-5262-4800-aa19-38e5285330c3
32dea826-9d39-4673-8491-95f6b58e50ed	FRIDAY	cffee650-43db-4f7f-b479-d0b439e61287	440c17ed-5262-4800-aa19-38e5285330c3
06db0d80-21ed-4e56-aa4b-2ab9d02a00b8	MONDAY	cffee650-43db-4f7f-b479-d0b439e61287	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8fe3ba2c-24e6-42e0-865e-704e4606e9fe	TUESDAY	cffee650-43db-4f7f-b479-d0b439e61287	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5602e17d-c241-4086-a398-2705af9a80ef	WEDNESDAY	cffee650-43db-4f7f-b479-d0b439e61287	5f76a266-8d18-4da8-9732-b63d4d8f24a0
24799a3d-0102-4fe1-9c47-27832bda12fc	THURSDAY	cffee650-43db-4f7f-b479-d0b439e61287	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ecdf62c3-1f2c-495b-9fca-3acff7d78a59	FRIDAY	cffee650-43db-4f7f-b479-d0b439e61287	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c9ef57a8-d2e6-4069-b884-b88e417727ff	MONDAY	cffee650-43db-4f7f-b479-d0b439e61287	d54b130a-28a8-4bb1-9030-c474369c2205
36410d8b-2239-47c4-8d50-1081a61992fc	TUESDAY	cffee650-43db-4f7f-b479-d0b439e61287	d54b130a-28a8-4bb1-9030-c474369c2205
eafef947-ff37-4177-a0ef-234850991ea0	WEDNESDAY	cffee650-43db-4f7f-b479-d0b439e61287	d54b130a-28a8-4bb1-9030-c474369c2205
b4a885db-57ac-42fb-a808-ee752723d977	THURSDAY	cffee650-43db-4f7f-b479-d0b439e61287	d54b130a-28a8-4bb1-9030-c474369c2205
71f8c249-195e-4082-89b4-7a99b88e8897	FRIDAY	cffee650-43db-4f7f-b479-d0b439e61287	d54b130a-28a8-4bb1-9030-c474369c2205
4d35dafc-e37f-4767-9236-6bc40dbeb69d	MONDAY	cffee650-43db-4f7f-b479-d0b439e61287	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e82e6486-70f1-4e2f-8cb7-3c1b21fa7c06	TUESDAY	cffee650-43db-4f7f-b479-d0b439e61287	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
19c24860-8395-44ba-aa4c-2f35a42e7c86	WEDNESDAY	cffee650-43db-4f7f-b479-d0b439e61287	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
86610142-0941-4ed7-8a3a-9f72c66eb3e7	THURSDAY	cffee650-43db-4f7f-b479-d0b439e61287	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f7e2f01d-f015-4d1a-ad54-3460dd80ddec	FRIDAY	cffee650-43db-4f7f-b479-d0b439e61287	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
55575257-7133-44c1-a57c-823d30fdb601	MONDAY	cffee650-43db-4f7f-b479-d0b439e61287	c33e01ef-be92-474a-9e43-0543649735d5
41c6afe9-349b-43f6-b3e9-c9c73e0bc3a7	TUESDAY	cffee650-43db-4f7f-b479-d0b439e61287	c33e01ef-be92-474a-9e43-0543649735d5
584f3006-3ab4-45f1-a75f-36921d460500	WEDNESDAY	cffee650-43db-4f7f-b479-d0b439e61287	c33e01ef-be92-474a-9e43-0543649735d5
4bca2925-e6c9-49eb-abd4-d01778096bcc	THURSDAY	cffee650-43db-4f7f-b479-d0b439e61287	c33e01ef-be92-474a-9e43-0543649735d5
ff8dec41-beb8-4161-8df2-4701a04d7f88	FRIDAY	cffee650-43db-4f7f-b479-d0b439e61287	c33e01ef-be92-474a-9e43-0543649735d5
7e401c2f-5da0-4ac2-adbb-9a789525f111	MONDAY	cffee650-43db-4f7f-b479-d0b439e61287	6f1475e5-0d8a-47c7-855f-ccff802aff85
ab888392-8cf2-42c1-ab26-46f761633c90	TUESDAY	cffee650-43db-4f7f-b479-d0b439e61287	6f1475e5-0d8a-47c7-855f-ccff802aff85
87d31f35-48bf-4964-83a3-483e910dcd18	WEDNESDAY	cffee650-43db-4f7f-b479-d0b439e61287	6f1475e5-0d8a-47c7-855f-ccff802aff85
8e6b154a-efa3-43ce-9b8e-c31f5d73c544	THURSDAY	cffee650-43db-4f7f-b479-d0b439e61287	6f1475e5-0d8a-47c7-855f-ccff802aff85
9b0ad6be-796d-436c-8bf8-99f5d45221a9	FRIDAY	cffee650-43db-4f7f-b479-d0b439e61287	6f1475e5-0d8a-47c7-855f-ccff802aff85
3f2ba28c-2f9c-4a71-a597-c8ae5c8242ef	MONDAY	cffee650-43db-4f7f-b479-d0b439e61287	58845959-0984-4136-a733-b75f5c9057b9
74ff7b7c-30b0-43a0-b32b-ea253d45b884	TUESDAY	cffee650-43db-4f7f-b479-d0b439e61287	58845959-0984-4136-a733-b75f5c9057b9
25d2b186-8c03-451e-a8e9-384146f3bad5	WEDNESDAY	cffee650-43db-4f7f-b479-d0b439e61287	58845959-0984-4136-a733-b75f5c9057b9
5b91adc9-8396-472c-be53-387135e03394	THURSDAY	cffee650-43db-4f7f-b479-d0b439e61287	58845959-0984-4136-a733-b75f5c9057b9
d2cbdad0-3fe6-4e4f-b226-cfa2e958a21f	FRIDAY	cffee650-43db-4f7f-b479-d0b439e61287	58845959-0984-4136-a733-b75f5c9057b9
4e7d7498-0bc7-49c6-a8fd-2bcec0886ab4	MONDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	440c17ed-5262-4800-aa19-38e5285330c3
09313b3f-114c-4b6e-9e3a-e83e73ac03ab	TUESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	440c17ed-5262-4800-aa19-38e5285330c3
e4ad2faa-6857-4c46-b5f4-3cbb62660cc4	WEDNESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	440c17ed-5262-4800-aa19-38e5285330c3
f3b73a89-40b1-425f-950c-7f78fb9ececd	THURSDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	440c17ed-5262-4800-aa19-38e5285330c3
4efcf20c-1ee4-4094-984e-ffc20fb260d0	FRIDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	440c17ed-5262-4800-aa19-38e5285330c3
9bf8c809-43b7-42a7-ad7e-fd7cf80a5ead	MONDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0bc13a9c-d318-45a5-8057-b0c8c02b1951	TUESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
99055fdc-debf-4e51-961e-f107cf7d363a	WEDNESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cb96088d-efcc-45d1-be8f-8002a4f1bea0	THURSDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
70355c57-b091-456a-abba-1630b041b19c	FRIDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
784ee396-38e7-433e-ae07-266d2f472fdd	MONDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	d54b130a-28a8-4bb1-9030-c474369c2205
bf3f31fc-95f0-42d3-8072-b54f76ddfa4d	TUESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	d54b130a-28a8-4bb1-9030-c474369c2205
516147eb-44f2-4d94-8952-5e54c25d7129	WEDNESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	d54b130a-28a8-4bb1-9030-c474369c2205
046065e5-dc3f-4d26-b0db-55c58e106f61	THURSDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	d54b130a-28a8-4bb1-9030-c474369c2205
ba33b9fa-d660-46cd-b21b-bbe9132e2b59	FRIDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	d54b130a-28a8-4bb1-9030-c474369c2205
3cee54b3-4af4-4383-8939-28fcdd85f6ff	MONDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5b0802f8-fa93-470a-80a4-c34614fec3e9	TUESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2b4d2b5e-cfb5-4852-bfda-65929fb339fc	WEDNESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2eb122b7-5e93-4ee6-a731-5094f0719324	THURSDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
99b17ce8-1746-42a3-b9b6-9d49541bd00d	FRIDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
521a10c4-e42b-41ea-890b-1f956147fd27	MONDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	c33e01ef-be92-474a-9e43-0543649735d5
f12acc86-b239-49a6-86c4-fd45cb31a953	TUESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	c33e01ef-be92-474a-9e43-0543649735d5
31c737ed-adef-4a8a-9404-cfb59e6d14a4	WEDNESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	c33e01ef-be92-474a-9e43-0543649735d5
17733e49-4390-468d-a542-23161ba533a3	THURSDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	c33e01ef-be92-474a-9e43-0543649735d5
ac72a028-7782-4d2b-a15c-2952a3a08be1	FRIDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	c33e01ef-be92-474a-9e43-0543649735d5
f5bbdec1-6144-410b-9f5a-0087aaba3cfd	MONDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	6f1475e5-0d8a-47c7-855f-ccff802aff85
af778194-ef79-4d68-8ee0-a41d3207dbf5	TUESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	6f1475e5-0d8a-47c7-855f-ccff802aff85
f81a75a4-9ae8-4039-b771-98d4144a5a3f	WEDNESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	6f1475e5-0d8a-47c7-855f-ccff802aff85
0fd983be-72da-4fce-b98e-19876894a2b0	THURSDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	6f1475e5-0d8a-47c7-855f-ccff802aff85
c7eadd56-bcdd-4efa-9799-3bbc25697013	FRIDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	6f1475e5-0d8a-47c7-855f-ccff802aff85
8077223f-fe8f-4102-99e4-20e500bdc3be	MONDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	58845959-0984-4136-a733-b75f5c9057b9
4175d689-7ec1-40da-a8a2-eabc10de1910	TUESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	58845959-0984-4136-a733-b75f5c9057b9
c1d4d2dc-d88d-4358-8cb2-515b4abea0b8	WEDNESDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	58845959-0984-4136-a733-b75f5c9057b9
c700a614-faba-4582-adca-b384343b2120	THURSDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	58845959-0984-4136-a733-b75f5c9057b9
c7688ece-d73c-427b-9e5e-cdbebadc1a3d	FRIDAY	a78aa9e9-1c0f-4c8c-bf48-6718c95cab3c	58845959-0984-4136-a733-b75f5c9057b9
2493ba29-16c7-4100-9bc6-8c3b3e34ac6a	MONDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	440c17ed-5262-4800-aa19-38e5285330c3
cf2473a0-2b6a-49d6-8f1d-f29335bc0725	TUESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	440c17ed-5262-4800-aa19-38e5285330c3
d7ee65f7-181a-4b6c-a7c5-007760e4ed35	WEDNESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	440c17ed-5262-4800-aa19-38e5285330c3
9437358a-b84d-4a7f-b4e7-5619e0a77cbd	THURSDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	440c17ed-5262-4800-aa19-38e5285330c3
b0f64491-1fcc-40fc-9820-6efb70878e9d	FRIDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	440c17ed-5262-4800-aa19-38e5285330c3
16b3e376-f63b-4ded-a973-5c760911b7bd	MONDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ecc3f453-46b9-4cd8-b0a7-8868b28f9c98	TUESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
254eecc0-f177-4151-8ff5-dcf2ec96135e	WEDNESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
63472fbc-5a6f-4da3-82bf-2f3dca656d4c	THURSDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2c4323b3-994d-47ef-99e9-41a5f9b7427c	FRIDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
98d9be19-b15f-4fa2-8831-3c12f20099b6	MONDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	d54b130a-28a8-4bb1-9030-c474369c2205
daa7cb65-fe27-4492-8fee-82c24ecb301a	TUESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	d54b130a-28a8-4bb1-9030-c474369c2205
1fb0fff3-18f9-4fba-b75b-0279ba91abcd	WEDNESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	d54b130a-28a8-4bb1-9030-c474369c2205
cf2abe8c-477b-4049-9426-3c025ee1e9e3	THURSDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	d54b130a-28a8-4bb1-9030-c474369c2205
7410abc0-f4eb-4fe8-a829-906a91a5c3ed	FRIDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	d54b130a-28a8-4bb1-9030-c474369c2205
3ec512b6-296b-4dca-85bf-4603296d9b9c	MONDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4876bdbd-3655-40d7-b823-cb569996109e	TUESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c2a153f9-b3ce-437b-bf99-2e7c7a0c14c7	WEDNESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2a2f3b20-15db-43cc-b32b-0cacf8cf1637	THURSDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
593cb5ae-a931-47b3-a7dd-236bd8b6c65b	FRIDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
734edc1e-4804-4710-94f2-f650d1661dda	MONDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	c33e01ef-be92-474a-9e43-0543649735d5
c21f4a23-e0d6-40a3-9223-773188aa3252	TUESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	c33e01ef-be92-474a-9e43-0543649735d5
a2c4fbc6-53ff-4060-806a-1e8d8f2d4b5c	WEDNESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	c33e01ef-be92-474a-9e43-0543649735d5
5fbe8c1a-e92d-422f-bdaa-055b7a9ba4c6	THURSDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	c33e01ef-be92-474a-9e43-0543649735d5
abc3ec7b-2b01-4f47-9895-9efc5dc48aa4	FRIDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	c33e01ef-be92-474a-9e43-0543649735d5
18ed0c20-6db5-4fb2-a261-852c44dbf6cc	MONDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	6f1475e5-0d8a-47c7-855f-ccff802aff85
f65ed02e-0896-4642-9c3a-27aad5b2b5b9	TUESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	6f1475e5-0d8a-47c7-855f-ccff802aff85
2b359094-8dd3-48c3-88db-4de231898d0a	WEDNESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	6f1475e5-0d8a-47c7-855f-ccff802aff85
119f21ad-fd79-4ebd-831d-87ce419ef366	THURSDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	6f1475e5-0d8a-47c7-855f-ccff802aff85
df22663d-9f84-4660-a2eb-41603e924150	FRIDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	6f1475e5-0d8a-47c7-855f-ccff802aff85
0a25dba7-4e24-43aa-989b-d66289c400fd	MONDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	58845959-0984-4136-a733-b75f5c9057b9
0cded6fb-1b4d-4728-abdd-13d08aca80ba	TUESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	58845959-0984-4136-a733-b75f5c9057b9
b1789522-24c2-4e7b-99cc-455597f5d052	WEDNESDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	58845959-0984-4136-a733-b75f5c9057b9
297f00a8-de13-4891-88c4-390b04db220a	THURSDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	58845959-0984-4136-a733-b75f5c9057b9
f98cbcbc-90ca-4d67-81c5-8937d9523031	FRIDAY	3bb3f817-15aa-45a2-b8cc-ac8c3dc73e9a	58845959-0984-4136-a733-b75f5c9057b9
47aa477b-feef-4067-bae6-2d20814df47f	MONDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	440c17ed-5262-4800-aa19-38e5285330c3
f83429dc-46cc-4ea2-9e0b-c6ba73366c20	TUESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	440c17ed-5262-4800-aa19-38e5285330c3
65337f1e-c49e-46f8-a059-b8a2313f9c42	WEDNESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	440c17ed-5262-4800-aa19-38e5285330c3
2c82ed55-0d52-4ff3-880b-bdd2cb3404c6	THURSDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	440c17ed-5262-4800-aa19-38e5285330c3
88bd8bc8-8b25-4697-9293-0d19a7a87196	FRIDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	440c17ed-5262-4800-aa19-38e5285330c3
6ec5f9cd-a2cc-4af7-b2d4-82e2c87e368c	MONDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4b8e9c11-82e8-4d9f-bea5-822bc5568082	TUESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	5f76a266-8d18-4da8-9732-b63d4d8f24a0
82d3b3a8-135e-4f06-b4f8-50a3be362afd	WEDNESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	5f76a266-8d18-4da8-9732-b63d4d8f24a0
43c3253e-6f08-4e7b-8356-811bf9a727ee	THURSDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a278e028-9a6d-42c9-94f8-a4edb989b35d	FRIDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c40f7bc2-85b5-45dd-a238-032ff1295043	MONDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	d54b130a-28a8-4bb1-9030-c474369c2205
348dac0f-b524-410a-93b7-ac0935d391da	TUESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	d54b130a-28a8-4bb1-9030-c474369c2205
208af1a6-c20b-4ecf-bfb2-92ca42393eb2	WEDNESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	d54b130a-28a8-4bb1-9030-c474369c2205
7d4e8ded-409a-4127-8fbf-c498ae83de01	THURSDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	d54b130a-28a8-4bb1-9030-c474369c2205
d9fc65a8-5a7e-4e4c-9383-58c188dbac97	FRIDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	d54b130a-28a8-4bb1-9030-c474369c2205
676abb55-1408-4c5b-8349-11f621a3acb4	MONDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0064ddba-095f-4667-bd47-a9632677dcc0	TUESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
40118023-bca7-4355-b931-12137ef1204e	WEDNESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
96446306-4671-4ce1-82f5-62d3e2fb916f	THURSDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
79687645-0f0f-4e1f-860f-7f9287a5d271	FRIDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d2c91fc7-7111-441c-84c8-d766f5fc2501	MONDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	c33e01ef-be92-474a-9e43-0543649735d5
941a51bd-ea39-4f71-94f5-88ce91358a03	TUESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	c33e01ef-be92-474a-9e43-0543649735d5
f166f739-337d-4dea-84d3-759ab0a0ab83	WEDNESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	c33e01ef-be92-474a-9e43-0543649735d5
e53e14d3-b799-4406-a6b7-f301e9dcf2ba	THURSDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	c33e01ef-be92-474a-9e43-0543649735d5
c3d2f2bc-e447-4177-9e59-c1932cbae17d	FRIDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	c33e01ef-be92-474a-9e43-0543649735d5
81c1dd4a-ba1b-4b09-8981-1a083204a252	MONDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
ef9a02b5-a222-4aec-826b-d3dbc1f8c620	TUESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
fc1b5c13-6b70-4f33-9e81-d301f3e7d048	WEDNESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
37a2c5a4-5a97-45c2-b6d3-c28d321a99a1	THURSDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
53516ec6-ab24-48f9-89d3-f471d5d5dfe3	FRIDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	6f1475e5-0d8a-47c7-855f-ccff802aff85
c122468b-1452-41fd-ab8f-7e4936a8ce23	MONDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	58845959-0984-4136-a733-b75f5c9057b9
a21a5172-b38d-4327-b86a-5fb04efaea41	TUESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	58845959-0984-4136-a733-b75f5c9057b9
48497989-51fd-4490-9fd5-f734a042d6ce	WEDNESDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	58845959-0984-4136-a733-b75f5c9057b9
ee1c723b-dfd0-43a2-898e-9d2048a4f45f	THURSDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	58845959-0984-4136-a733-b75f5c9057b9
e2389f67-1d46-4958-b5b0-9eb9be8a0225	FRIDAY	aeef2474-4ad6-4585-83cf-9ec480c8d0a9	58845959-0984-4136-a733-b75f5c9057b9
6165b703-76e1-4da7-a922-46f61e289e1b	MONDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	440c17ed-5262-4800-aa19-38e5285330c3
20952ffb-e85e-4886-93a4-971771cc96d2	TUESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	440c17ed-5262-4800-aa19-38e5285330c3
9f82df86-d3f3-4deb-b453-4024d93dc8e9	WEDNESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	440c17ed-5262-4800-aa19-38e5285330c3
a7c3346d-5f40-45a9-8930-e49738dd736c	THURSDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	440c17ed-5262-4800-aa19-38e5285330c3
4beff7e4-b79b-4c60-9f62-5546c1ac5863	FRIDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	440c17ed-5262-4800-aa19-38e5285330c3
802893c2-5f0c-4208-8deb-7fb5ac481211	MONDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f6b75551-7c24-4e2e-8cd0-e18d665a93a4	TUESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4a81f15f-f186-43c9-8315-9ced002111b8	WEDNESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	5f76a266-8d18-4da8-9732-b63d4d8f24a0
99ad573d-d5ae-49b3-b187-4cb5c3007643	THURSDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	5f76a266-8d18-4da8-9732-b63d4d8f24a0
efbf907e-a935-45cc-8797-224cdfb6c6ee	FRIDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	5f76a266-8d18-4da8-9732-b63d4d8f24a0
da6f349b-6ef3-4fcf-8c23-468e0e5d539e	MONDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	d54b130a-28a8-4bb1-9030-c474369c2205
17bc08c4-6964-48af-abcf-fbf41d638286	TUESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	d54b130a-28a8-4bb1-9030-c474369c2205
ee57d943-98de-4bd0-a8a6-f40de1044e99	WEDNESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	d54b130a-28a8-4bb1-9030-c474369c2205
af1add9c-fc89-4f88-9945-ffe8dafeb88f	THURSDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	d54b130a-28a8-4bb1-9030-c474369c2205
b58ea454-73b2-4f93-bb77-f4faaafaebc9	FRIDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	d54b130a-28a8-4bb1-9030-c474369c2205
eaa636f6-0fd5-48d7-a861-d3d5d2ba4a33	MONDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0829f07f-f694-49fe-814c-e6991d26d98b	TUESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8a4aebb8-34f1-432d-ac23-ddf3121ccae1	WEDNESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3b3ab801-47e1-4e76-951d-1addd22ccb62	THURSDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c4304d61-89f8-4090-a945-119f8f1f5f82	FRIDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8c8f08f7-fb1e-4e83-a4e6-84c66b2d9846	MONDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	c33e01ef-be92-474a-9e43-0543649735d5
cf70cde9-7b65-4acb-8602-be1a7448d849	TUESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	c33e01ef-be92-474a-9e43-0543649735d5
0d469556-a2ff-48f9-82f3-d130eb85f368	WEDNESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	c33e01ef-be92-474a-9e43-0543649735d5
c1f6ec71-5ba9-4f03-bddc-519a6cca494f	THURSDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	c33e01ef-be92-474a-9e43-0543649735d5
3f80ca7f-1ad9-47f2-a867-c67f016da4e0	FRIDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	c33e01ef-be92-474a-9e43-0543649735d5
3844eb68-28b2-4a58-bdf6-d3885f9d74f7	MONDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	6f1475e5-0d8a-47c7-855f-ccff802aff85
632f213c-c04d-47b0-87a4-a500ce08a7a2	TUESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	6f1475e5-0d8a-47c7-855f-ccff802aff85
04c3eaeb-3df4-4151-a55b-f0093bd43a86	WEDNESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	6f1475e5-0d8a-47c7-855f-ccff802aff85
4d4bac37-9c86-4c84-a707-5d9fbfe9f8d5	THURSDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	6f1475e5-0d8a-47c7-855f-ccff802aff85
0d3e6701-71a1-43a3-957b-742129483524	FRIDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	6f1475e5-0d8a-47c7-855f-ccff802aff85
d18f16a9-1a1a-4166-a82e-e06cf7e8c99d	MONDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	58845959-0984-4136-a733-b75f5c9057b9
e4aafc92-bab5-423b-a828-e6fd8bff92a2	TUESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	58845959-0984-4136-a733-b75f5c9057b9
871fb1aa-466a-4933-ae0b-69543b2dab93	WEDNESDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	58845959-0984-4136-a733-b75f5c9057b9
82eeac8e-a906-4de5-a2c0-80c56fdfce7f	THURSDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	58845959-0984-4136-a733-b75f5c9057b9
1b80a758-f5e9-4c37-80be-7083d15a6659	FRIDAY	c0fd2eba-50bd-4f7a-8f55-7cfda9643139	58845959-0984-4136-a733-b75f5c9057b9
1d872d96-7d40-4e78-afeb-4fd6e20e9903	FRIDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6bb82af7-e922-413b-8fd6-d632dc2c34e7	MONDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	d54b130a-28a8-4bb1-9030-c474369c2205
ebca177c-9afe-4cbd-ba3b-ac9a53700bcc	TUESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	d54b130a-28a8-4bb1-9030-c474369c2205
1c04a337-9d99-4e54-b82b-09e69b1cb07f	WEDNESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	d54b130a-28a8-4bb1-9030-c474369c2205
5a714814-e100-4488-8b02-02e6514f9f9f	THURSDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	d54b130a-28a8-4bb1-9030-c474369c2205
6ccdfc59-06d9-42b7-bed4-35c6d876cb2a	FRIDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	d54b130a-28a8-4bb1-9030-c474369c2205
ceb1bb12-68bb-486e-beaa-ee740e16de4a	MONDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
18d5df15-b993-4763-8e85-46ea3a05ac33	TUESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
dea27cc5-3e2c-4126-8110-8e2d2e96723d	WEDNESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2bf7a3fb-8012-4661-8669-e38f15d047dc	THURSDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
763dcad3-d01e-4217-b369-a26cb8e4b20f	FRIDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7e18dd87-61f0-40b3-b340-6fb924e73480	MONDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	c33e01ef-be92-474a-9e43-0543649735d5
2db8de53-a72a-4a9a-9cda-9f58416db644	TUESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	c33e01ef-be92-474a-9e43-0543649735d5
60889aaa-a3be-4abf-bfb6-f54431941b61	WEDNESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	c33e01ef-be92-474a-9e43-0543649735d5
db3459b8-399d-4329-8f18-cd9a1576a1a0	THURSDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	c33e01ef-be92-474a-9e43-0543649735d5
8ccfed8c-e784-4707-ad75-07b1c541cb80	FRIDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	c33e01ef-be92-474a-9e43-0543649735d5
af4a4b6c-76ab-4f7a-bcfa-3365d8335a47	MONDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	6f1475e5-0d8a-47c7-855f-ccff802aff85
32b7d1ef-a0d6-4f70-bc7b-b630b92c2f9e	TUESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	6f1475e5-0d8a-47c7-855f-ccff802aff85
84a55726-ac78-4db7-8743-13537c4bdb3f	WEDNESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	6f1475e5-0d8a-47c7-855f-ccff802aff85
ada3b43b-1edf-456e-b28e-20bddec677e2	THURSDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	6f1475e5-0d8a-47c7-855f-ccff802aff85
d57ee5be-c7b8-4e62-8e28-da17203b5f08	FRIDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	6f1475e5-0d8a-47c7-855f-ccff802aff85
86b368f7-c58d-4c55-b635-976a157b3e79	MONDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	58845959-0984-4136-a733-b75f5c9057b9
f57a1a8e-68bd-458f-8227-7e7e5675e5b7	TUESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	58845959-0984-4136-a733-b75f5c9057b9
3eddce21-deb7-4f49-b122-2c5025e8f022	WEDNESDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	58845959-0984-4136-a733-b75f5c9057b9
8b35c13f-644d-4289-b410-d2824519f857	THURSDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	58845959-0984-4136-a733-b75f5c9057b9
abe8f3d8-2d64-4b55-b185-2b73eca25523	FRIDAY	3422b8c1-8982-45cf-9178-f58366ec18fe	58845959-0984-4136-a733-b75f5c9057b9
9086dfd7-ac84-41c9-b904-97d5221491e2	THURSDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	58845959-0984-4136-a733-b75f5c9057b9
234f0d99-9c0d-4a8e-9856-35b12cc0a29d	FRIDAY	011c0cf9-821f-4913-b775-d7191d8b15cf	58845959-0984-4136-a733-b75f5c9057b9
6c45d0a0-c5c7-482b-a8f7-df2268076c9a	MONDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	440c17ed-5262-4800-aa19-38e5285330c3
dba9088c-83fc-4561-a2b2-ad6e05f21e35	TUESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	440c17ed-5262-4800-aa19-38e5285330c3
849afa86-98e3-4b62-85c4-ca8c97965408	WEDNESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	440c17ed-5262-4800-aa19-38e5285330c3
64925381-9827-4b6f-8a25-5c19ecb05d32	THURSDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	440c17ed-5262-4800-aa19-38e5285330c3
c77e43d4-d71b-4402-a96b-4d56adc6cc83	FRIDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	440c17ed-5262-4800-aa19-38e5285330c3
e86e0938-3a67-48ca-a002-d9030bb90ce8	MONDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c2816341-b1bc-4202-9d8b-c28d67ac8d68	MONDAY	64847330-57d7-4e0c-bb60-f1b74673512c	440c17ed-5262-4800-aa19-38e5285330c3
346ce76d-a9e1-4741-a181-d201b381714d	TUESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	440c17ed-5262-4800-aa19-38e5285330c3
9f5a5351-003d-4f60-9f20-daa91853c44f	WEDNESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	440c17ed-5262-4800-aa19-38e5285330c3
453b6424-c517-4f9c-9d5f-cb8d7c6353a6	THURSDAY	64847330-57d7-4e0c-bb60-f1b74673512c	440c17ed-5262-4800-aa19-38e5285330c3
c981a6cd-15bd-4689-abdd-f9b3ce77c1d0	FRIDAY	64847330-57d7-4e0c-bb60-f1b74673512c	440c17ed-5262-4800-aa19-38e5285330c3
4cdca9e1-d0fc-4b62-9632-8442cb7c1716	MONDAY	64847330-57d7-4e0c-bb60-f1b74673512c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
30cccc10-893a-4ee2-87e1-7cdba348ba82	TUESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
30f9aafd-8775-48fa-ba5b-70681bdec3d1	WEDNESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0f4c85d5-4ac8-4825-940d-a2e2a71e2a6d	THURSDAY	64847330-57d7-4e0c-bb60-f1b74673512c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d6f0687e-3f7e-4df3-8388-7914aead3cac	FRIDAY	64847330-57d7-4e0c-bb60-f1b74673512c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
21c31435-cee4-435c-8fae-2f8ddb0f6f51	MONDAY	64847330-57d7-4e0c-bb60-f1b74673512c	d54b130a-28a8-4bb1-9030-c474369c2205
c255128d-14f1-46ed-9057-1dfdb0a39a57	TUESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	d54b130a-28a8-4bb1-9030-c474369c2205
eaadbc95-7b88-4025-ade0-66dcc70e15e2	WEDNESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	d54b130a-28a8-4bb1-9030-c474369c2205
1af51c63-d26d-4f16-9f5e-5610c94e2f12	THURSDAY	64847330-57d7-4e0c-bb60-f1b74673512c	d54b130a-28a8-4bb1-9030-c474369c2205
2a83f8d9-3ba6-4c32-bfca-5486a4029246	FRIDAY	64847330-57d7-4e0c-bb60-f1b74673512c	d54b130a-28a8-4bb1-9030-c474369c2205
5a47b6c4-e947-4527-8c8e-03424a2be989	MONDAY	64847330-57d7-4e0c-bb60-f1b74673512c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b18136e8-103e-4c9d-9c1d-213ae844e5ff	TUESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6170125d-19b9-40b2-9d0f-98a717490979	WEDNESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
506a3bed-6994-46b1-ade7-d146719d5ba9	THURSDAY	64847330-57d7-4e0c-bb60-f1b74673512c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5481f6dd-a1fb-4b9a-b3cc-1bcf8332ae4e	FRIDAY	64847330-57d7-4e0c-bb60-f1b74673512c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
43ef9c91-80a5-417f-ba73-d77679c6c43d	MONDAY	64847330-57d7-4e0c-bb60-f1b74673512c	c33e01ef-be92-474a-9e43-0543649735d5
19f1c961-bc0e-41d2-b771-ef58a5bd16dc	TUESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	c33e01ef-be92-474a-9e43-0543649735d5
9f9f5c86-3bae-46b7-8a1a-f3bb34f47885	WEDNESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	c33e01ef-be92-474a-9e43-0543649735d5
bb6fa20c-e212-4471-a02e-0dc00cb15fc0	THURSDAY	64847330-57d7-4e0c-bb60-f1b74673512c	c33e01ef-be92-474a-9e43-0543649735d5
eff4abaf-38c6-4966-a9d0-48fa79ba1637	FRIDAY	64847330-57d7-4e0c-bb60-f1b74673512c	c33e01ef-be92-474a-9e43-0543649735d5
d563c3e8-9d02-40a1-aa4d-dadd68c12ce3	MONDAY	64847330-57d7-4e0c-bb60-f1b74673512c	6f1475e5-0d8a-47c7-855f-ccff802aff85
93e82145-cf2f-45e6-8ab0-c03096469d23	TUESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	6f1475e5-0d8a-47c7-855f-ccff802aff85
2c902b80-9d83-4878-9693-9fb30048c148	WEDNESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	6f1475e5-0d8a-47c7-855f-ccff802aff85
eacddd01-b4c9-44fc-be06-beb516a9d73c	THURSDAY	64847330-57d7-4e0c-bb60-f1b74673512c	6f1475e5-0d8a-47c7-855f-ccff802aff85
79917485-0604-4d27-9899-3ab9151aafc7	FRIDAY	64847330-57d7-4e0c-bb60-f1b74673512c	6f1475e5-0d8a-47c7-855f-ccff802aff85
064c7294-653e-4a52-a55a-f0e65a91a159	MONDAY	64847330-57d7-4e0c-bb60-f1b74673512c	58845959-0984-4136-a733-b75f5c9057b9
c9ab1ec4-ad95-49cb-89aa-9cec333538a1	TUESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	58845959-0984-4136-a733-b75f5c9057b9
8b28d4c2-6d96-4679-9db0-49f564a62969	WEDNESDAY	64847330-57d7-4e0c-bb60-f1b74673512c	58845959-0984-4136-a733-b75f5c9057b9
07b820ce-415a-4214-b0e1-523fff66bdb3	THURSDAY	64847330-57d7-4e0c-bb60-f1b74673512c	58845959-0984-4136-a733-b75f5c9057b9
5fb74f46-e5cc-4dd2-ae38-5bdb43b0a5fe	FRIDAY	64847330-57d7-4e0c-bb60-f1b74673512c	58845959-0984-4136-a733-b75f5c9057b9
1d088b1f-7be8-453b-b6f7-326b496047d5	MONDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	440c17ed-5262-4800-aa19-38e5285330c3
4e592c13-a5b7-4a71-a5a5-71a71a7cb8a7	TUESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	440c17ed-5262-4800-aa19-38e5285330c3
91b4b3c4-9983-437f-a2ba-b875128c787f	WEDNESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	440c17ed-5262-4800-aa19-38e5285330c3
42be0f7b-b069-4185-9d70-f20f9581fa11	THURSDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	440c17ed-5262-4800-aa19-38e5285330c3
cab832be-776e-42fb-ba12-250d40dd7ad4	FRIDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	440c17ed-5262-4800-aa19-38e5285330c3
082ed236-1049-48a8-8552-ed2d8f90cb25	MONDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9ffc2982-0a40-4909-9afd-6125a0b5376d	TUESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
613df50c-971c-4308-bbeb-d715a510837c	WEDNESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
36ebbeb6-48e2-4729-bf1f-fa05b513b0bb	THURSDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7faa0598-74bd-4ccb-895f-2b6c825bf5c6	FRIDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fc7b88b8-09b0-486c-b62d-dfa84527a3c9	MONDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	d54b130a-28a8-4bb1-9030-c474369c2205
984d518e-3a71-4191-9f64-e1f3b4192071	TUESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	d54b130a-28a8-4bb1-9030-c474369c2205
fa7aafd0-5c28-4336-b4c4-f6e2d4e55c85	WEDNESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	d54b130a-28a8-4bb1-9030-c474369c2205
d6e8e54c-f06b-4205-989b-6fdc2a36abf8	THURSDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	d54b130a-28a8-4bb1-9030-c474369c2205
ecc8e3b4-6e12-4174-bb02-a7ab607a228f	FRIDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	d54b130a-28a8-4bb1-9030-c474369c2205
2fa0a332-295f-4cf8-b122-c87f94b52aca	MONDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9b04fb7d-f1e4-423e-8041-ed79e7c6a1a5	TUESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5dddc4a3-3954-491e-9014-7569693bcdd3	WEDNESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
08f53f56-8607-4e3c-a5f1-efcd24b89363	THURSDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
872d2672-092c-4b3d-8998-6f8e0296f4a5	FRIDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8058ee9a-2778-4f55-9e26-c0ceef1b30da	MONDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	c33e01ef-be92-474a-9e43-0543649735d5
c5d0f7c4-ced1-4da2-8997-0e9ad23dd399	TUESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	c33e01ef-be92-474a-9e43-0543649735d5
b9ac6a4a-f5b8-40ea-ba9f-b83ed521b8aa	WEDNESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	c33e01ef-be92-474a-9e43-0543649735d5
5bf913db-81b3-45ae-b85f-1e33279063d4	THURSDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	c33e01ef-be92-474a-9e43-0543649735d5
255bd40f-3f7b-4776-9686-660484ccfa4a	FRIDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	c33e01ef-be92-474a-9e43-0543649735d5
32c2b599-19be-464d-983b-8d7ff419816f	MONDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	6f1475e5-0d8a-47c7-855f-ccff802aff85
19b8c181-9a0a-4c5d-98f6-ced6c40db58d	TUESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	6f1475e5-0d8a-47c7-855f-ccff802aff85
d49408c3-4b35-4df0-9782-21a7f3d3f1a2	WEDNESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	6f1475e5-0d8a-47c7-855f-ccff802aff85
510ebe42-f76b-491b-aedb-374260664c7c	THURSDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	6f1475e5-0d8a-47c7-855f-ccff802aff85
6e3e8825-4610-4303-a83a-dad200ebef2e	FRIDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	6f1475e5-0d8a-47c7-855f-ccff802aff85
94218b10-f6c8-4c36-8126-bee9830ac032	MONDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	58845959-0984-4136-a733-b75f5c9057b9
a01207ee-65b6-4ae2-a520-dcf1b1b2cc2b	TUESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	58845959-0984-4136-a733-b75f5c9057b9
882511de-72a4-4408-b21f-4e95ab7090d4	WEDNESDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	58845959-0984-4136-a733-b75f5c9057b9
4cb85233-fc47-4e4b-91f8-1cfeafb00480	THURSDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	58845959-0984-4136-a733-b75f5c9057b9
a1d5640b-ca32-4148-acb0-bfd064c2b5c0	FRIDAY	fd77eda8-4f7f-44ed-830e-d5ec71dbaf3a	58845959-0984-4136-a733-b75f5c9057b9
7147d322-fa20-4228-8747-a898e9977c63	MONDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	440c17ed-5262-4800-aa19-38e5285330c3
dd3ccf1b-16e9-4bb9-8499-be0cfbaaa929	TUESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	440c17ed-5262-4800-aa19-38e5285330c3
588ea715-b283-4bed-93bd-6894d11f9aa0	WEDNESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	440c17ed-5262-4800-aa19-38e5285330c3
46ca0e06-1faa-4a6a-a46c-fb2ad965e2a8	THURSDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	440c17ed-5262-4800-aa19-38e5285330c3
e060a1ac-0381-4eb7-beaf-2743fc5ea240	FRIDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	440c17ed-5262-4800-aa19-38e5285330c3
68950cff-c574-44ce-8911-c9bdd0f403af	MONDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c4b03896-8f96-4add-96ce-770654fe4362	TUESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5daac0be-56c1-4821-97b4-d3de0ed85039	WEDNESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4bbc87cc-0519-4475-b23a-d54d0763f165	THURSDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	5f76a266-8d18-4da8-9732-b63d4d8f24a0
02880674-a0aa-4af1-a806-94005e5ef9f6	FRIDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	5f76a266-8d18-4da8-9732-b63d4d8f24a0
29abe0be-95ac-4b39-9f32-5e1d5f605d99	MONDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	d54b130a-28a8-4bb1-9030-c474369c2205
04bf0308-bb64-48e8-a6b2-d23aa4795e90	TUESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	d54b130a-28a8-4bb1-9030-c474369c2205
1462ae0f-5c9e-4ea1-8f6e-3e7bb9f4e8af	WEDNESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	d54b130a-28a8-4bb1-9030-c474369c2205
5dabe976-d68b-462a-b45e-9ce98fa217c4	THURSDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	d54b130a-28a8-4bb1-9030-c474369c2205
828bca57-4ba0-4b98-9c25-3f21bf15c899	FRIDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	d54b130a-28a8-4bb1-9030-c474369c2205
f15739ad-f384-417f-aff1-ebc05fdd0785	MONDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2768949d-f7d9-4cf2-9ce2-014db1558372	TUESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
83dea691-b660-42bb-8a03-82bbe18cc11e	WEDNESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
08a268d3-2f9f-49e8-8e82-9cde2a804a5f	THURSDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6df6fe49-5862-4277-a0d8-cb6270080150	FRIDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e715e4ad-d1ec-43e7-b280-0edac67eba0a	MONDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	c33e01ef-be92-474a-9e43-0543649735d5
16d80681-b0b9-411b-a546-ca89023b7dca	TUESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	c33e01ef-be92-474a-9e43-0543649735d5
d117c683-5d6d-43e0-b5c1-c242b548805f	WEDNESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	c33e01ef-be92-474a-9e43-0543649735d5
cc7b93a4-9cb8-4b29-90f9-02e42f9756de	THURSDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	c33e01ef-be92-474a-9e43-0543649735d5
50ce58f8-a04f-4761-b604-8c01b74e6606	FRIDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	c33e01ef-be92-474a-9e43-0543649735d5
5ab913dc-b46d-476a-a7f7-c63c4e4ce198	MONDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	6f1475e5-0d8a-47c7-855f-ccff802aff85
7e8698c1-7d52-4a74-9702-b111b742cca8	TUESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	6f1475e5-0d8a-47c7-855f-ccff802aff85
350c63d0-0806-4c21-b260-11aecfc8393e	WEDNESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	6f1475e5-0d8a-47c7-855f-ccff802aff85
2c034d07-40fa-49b5-8f2e-5bdcf24459b5	THURSDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	6f1475e5-0d8a-47c7-855f-ccff802aff85
2b6aa8d1-ec95-4339-a5a8-b54a9e4c8da9	FRIDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	6f1475e5-0d8a-47c7-855f-ccff802aff85
f7065eb6-cac3-4d7d-83cd-0efba84d113c	MONDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	58845959-0984-4136-a733-b75f5c9057b9
60f14d7d-de30-4e59-b831-4bf650b583b8	TUESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	58845959-0984-4136-a733-b75f5c9057b9
ec59c278-a5a2-4146-b4cc-1709b76e1e9d	WEDNESDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	58845959-0984-4136-a733-b75f5c9057b9
ecc88b02-838e-41af-93df-c683787f1bd6	THURSDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	58845959-0984-4136-a733-b75f5c9057b9
5623da16-c133-4451-b970-4ad2a2e924dd	FRIDAY	19655b1e-03fa-4c82-b2a8-42de4e84b4db	58845959-0984-4136-a733-b75f5c9057b9
9cb12064-93eb-479e-8699-ecf12ef90ccc	MONDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	440c17ed-5262-4800-aa19-38e5285330c3
5638f28d-b477-4735-b0ab-d9b487512c36	TUESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	440c17ed-5262-4800-aa19-38e5285330c3
3d3290d6-803d-4f74-8226-0668c513619b	WEDNESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	440c17ed-5262-4800-aa19-38e5285330c3
9f460c8d-9527-4215-ba3c-671a19112569	THURSDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	440c17ed-5262-4800-aa19-38e5285330c3
8f34fda5-af2e-483a-b165-065dd4f97895	FRIDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	440c17ed-5262-4800-aa19-38e5285330c3
4e82431d-bc25-4a66-897c-4de02dfd68b1	MONDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6a7a0a39-7c7b-4b10-8daf-0a94b955c166	TUESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3e4fcb27-1f96-48ca-b0d4-add10178d7e8	WEDNESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2344daa6-313c-420d-8f04-52131fed2d3c	THURSDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c31301fa-21d8-4a88-ad9b-d5fc284b9718	FRIDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2e0d903e-0be2-4d0c-892b-6ce6a0c32257	MONDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	d54b130a-28a8-4bb1-9030-c474369c2205
9a015285-5a4b-4a61-b034-4162f79398be	TUESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	d54b130a-28a8-4bb1-9030-c474369c2205
d252bc0d-e2da-4b22-b61f-a5f3f66f0977	WEDNESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	d54b130a-28a8-4bb1-9030-c474369c2205
f825fa12-2b9a-4eb6-9c55-b0fb4ef231fd	THURSDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	d54b130a-28a8-4bb1-9030-c474369c2205
0d9f888e-09e4-44bc-9dbe-cbefcaae96c6	FRIDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	d54b130a-28a8-4bb1-9030-c474369c2205
de9859ec-17b7-4f74-86b1-75f582ed36e3	MONDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a9068ff4-5e41-4229-bade-628f6d3af557	TUESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d5310bc0-7242-4b5e-b68b-ef0ceec09bec	WEDNESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4135ccbb-cf0a-4a6f-bfba-f3c9371c42ea	THURSDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d9c9d714-b140-4b52-b41d-596a526f21a5	FRIDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1a0d4625-7e81-4acc-95f1-67011c755740	MONDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	c33e01ef-be92-474a-9e43-0543649735d5
dd473a6f-ab6e-4948-926c-ebe71b3dfb8c	TUESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	c33e01ef-be92-474a-9e43-0543649735d5
d9123f7f-cc27-4bbd-984c-631eeb9d3fe0	WEDNESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	c33e01ef-be92-474a-9e43-0543649735d5
248f9c73-bba2-458b-8d2b-2d44787cbd19	THURSDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	c33e01ef-be92-474a-9e43-0543649735d5
218c2a53-2c35-45b8-8824-67d393b4ad45	FRIDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	c33e01ef-be92-474a-9e43-0543649735d5
9775560e-26ce-4cf3-9523-648a314a6d5d	MONDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	6f1475e5-0d8a-47c7-855f-ccff802aff85
71b0f421-9c87-45a5-97a3-9a33b4142f30	TUESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	6f1475e5-0d8a-47c7-855f-ccff802aff85
4efbc502-0dce-467e-8ef6-32a609c84a3a	WEDNESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	6f1475e5-0d8a-47c7-855f-ccff802aff85
ca4cc063-5acc-4830-bff7-6c88715438eb	THURSDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	6f1475e5-0d8a-47c7-855f-ccff802aff85
63b58c8c-170e-44a5-867e-07f41c4aab25	FRIDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	6f1475e5-0d8a-47c7-855f-ccff802aff85
687d77c0-8d64-4718-9c71-03ee0d680846	MONDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	58845959-0984-4136-a733-b75f5c9057b9
9bda8660-a9f0-4d94-bfab-e4dd9461997b	TUESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	58845959-0984-4136-a733-b75f5c9057b9
48d76153-0fef-43de-b731-d01834819bcb	WEDNESDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	58845959-0984-4136-a733-b75f5c9057b9
8bcc3281-2347-4380-ac48-804d593aba3e	THURSDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	58845959-0984-4136-a733-b75f5c9057b9
644316b6-5d96-4d5b-8cb8-8a3b11dbb751	FRIDAY	1fcbcc1a-16ac-4788-a779-76a65b735e4d	58845959-0984-4136-a733-b75f5c9057b9
4d790879-418d-4e0b-b1b0-7ea29efbaabe	MONDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	440c17ed-5262-4800-aa19-38e5285330c3
7223ee55-0204-4aa8-9281-2affa82c2243	TUESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	440c17ed-5262-4800-aa19-38e5285330c3
c4881e12-ec0f-46be-bf32-1c217ed88880	WEDNESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	440c17ed-5262-4800-aa19-38e5285330c3
598407e9-83a5-4c12-91bf-a7da1def18f3	THURSDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	440c17ed-5262-4800-aa19-38e5285330c3
567d0b40-dc61-41a0-8238-ab1b5b7c7adf	FRIDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	440c17ed-5262-4800-aa19-38e5285330c3
7ed71545-c96e-4062-afd5-651c653af6e6	MONDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	5f76a266-8d18-4da8-9732-b63d4d8f24a0
02fc440b-b650-4dae-afd9-1d8dfc787a95	TUESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	5f76a266-8d18-4da8-9732-b63d4d8f24a0
951f8050-070f-4c73-8c96-1f9fc2e621f8	WEDNESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3aed9a77-df47-4691-9e16-87707aecdcd6	THURSDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d8b5f695-7012-4092-9fd8-2e51c0864217	FRIDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	5f76a266-8d18-4da8-9732-b63d4d8f24a0
eb9425bb-c5b9-4ff1-857e-0dd1a7cd8f4c	MONDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	d54b130a-28a8-4bb1-9030-c474369c2205
c07edd97-4ff3-406e-89d0-f3994cd8dcdf	TUESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	d54b130a-28a8-4bb1-9030-c474369c2205
1bf1bab8-30f3-4078-98a8-6b8043fffdb1	WEDNESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	d54b130a-28a8-4bb1-9030-c474369c2205
16681e1d-7944-44d6-9b41-545df7f8180a	THURSDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	d54b130a-28a8-4bb1-9030-c474369c2205
1a3bad79-a1d3-47af-a4a8-4b797cd4bcea	FRIDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	d54b130a-28a8-4bb1-9030-c474369c2205
4d057a5c-45d7-47a7-893a-63f1f334f51d	MONDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ad2daa83-7317-42c2-9d5c-2c9e0b4a55c2	TUESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4fee7689-de01-4572-b89a-d8dcdb02f052	WEDNESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7a64ecb2-556c-4c2f-bb70-f22c00b1eade	THURSDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c693a25e-9869-4894-9496-aa294d75089c	FRIDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c0fe9c8f-d11e-49ec-b947-68e4314f3b0c	MONDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	c33e01ef-be92-474a-9e43-0543649735d5
71fb75b9-5b82-4568-a598-3cee40c705b7	TUESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	c33e01ef-be92-474a-9e43-0543649735d5
ab5295d4-f0ff-490c-83e5-6bc1568605b2	WEDNESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	c33e01ef-be92-474a-9e43-0543649735d5
7b6412f8-f823-4ee0-90f4-39861c416921	THURSDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	c33e01ef-be92-474a-9e43-0543649735d5
94b5a7f1-7f6a-4362-b3ed-90c09b82e045	FRIDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	c33e01ef-be92-474a-9e43-0543649735d5
9e8b7e9b-3691-44cb-bedd-50a2612735d3	MONDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	6f1475e5-0d8a-47c7-855f-ccff802aff85
fa00db9c-a9b6-439b-816a-495dd3f6cd8e	TUESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	6f1475e5-0d8a-47c7-855f-ccff802aff85
dd871d04-5f60-426b-ab16-e934090ee437	WEDNESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	6f1475e5-0d8a-47c7-855f-ccff802aff85
39f5d318-8119-46e7-a5e5-f6ac71c7a038	THURSDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	6f1475e5-0d8a-47c7-855f-ccff802aff85
168dc948-cc2e-4f96-afcf-5080eb6b753f	FRIDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	6f1475e5-0d8a-47c7-855f-ccff802aff85
d6c88b62-5344-4f57-a44e-483ffd56af85	MONDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	58845959-0984-4136-a733-b75f5c9057b9
a859d162-0c36-480b-af00-2c3f4c0f7bae	TUESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	58845959-0984-4136-a733-b75f5c9057b9
a0b00354-9261-4a39-9f47-f417e9e4a590	WEDNESDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	58845959-0984-4136-a733-b75f5c9057b9
6fbc1d30-ca3e-49d2-a6ad-4bb3727c3d6f	THURSDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	58845959-0984-4136-a733-b75f5c9057b9
23c6dcd4-f5e1-4578-9ed8-c92bb7abb722	FRIDAY	3b0407f2-8d98-4a28-b3da-e6fa5ded7728	58845959-0984-4136-a733-b75f5c9057b9
febd81f1-da4f-468e-a5ac-b5e2f36ad4b7	MONDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	440c17ed-5262-4800-aa19-38e5285330c3
07052f88-78fc-4f43-9502-d4870a784edc	TUESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	440c17ed-5262-4800-aa19-38e5285330c3
df9613a0-3ffe-4558-a9a8-33d37eab8646	WEDNESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	440c17ed-5262-4800-aa19-38e5285330c3
6fa73304-134d-47d3-85fe-e8bced762689	THURSDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	440c17ed-5262-4800-aa19-38e5285330c3
ad423f3f-d186-4183-9628-8e8023815316	FRIDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	440c17ed-5262-4800-aa19-38e5285330c3
905c356a-0133-417b-bfa6-5dcc831431d3	MONDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2fd6b4b9-5cbf-4e18-b1ec-60966afeab06	TUESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	5f76a266-8d18-4da8-9732-b63d4d8f24a0
05f13f82-a7f1-4651-a833-7d21d1bf57eb	WEDNESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	5f76a266-8d18-4da8-9732-b63d4d8f24a0
824d9fb3-99ff-4e42-9722-ae0e6c40fa3f	THURSDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	5f76a266-8d18-4da8-9732-b63d4d8f24a0
44964764-0b06-4e7a-9917-d45679927db4	FRIDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	5f76a266-8d18-4da8-9732-b63d4d8f24a0
16a248b2-a8e3-43d6-b3c0-6be9751a943b	MONDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	d54b130a-28a8-4bb1-9030-c474369c2205
4e3dfc24-e90d-4e70-90e3-6ff9fec1305d	TUESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	d54b130a-28a8-4bb1-9030-c474369c2205
3cb91fa4-5c01-4980-bcd4-954805aaa4c4	WEDNESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	d54b130a-28a8-4bb1-9030-c474369c2205
7662aa97-5948-4c02-85e8-de9ca2eff24e	THURSDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	d54b130a-28a8-4bb1-9030-c474369c2205
d08f8e61-e92c-4711-8cca-e13b19f2419a	FRIDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	d54b130a-28a8-4bb1-9030-c474369c2205
407c9fea-d0fb-4e1d-9b55-071732ca0175	MONDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e28b83e4-091c-4152-b9a2-5cc978916e84	TUESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2db64aa9-4262-4a10-af9f-9dea9e5aa422	WEDNESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
350bcaa4-4e1a-487f-a9dc-fa5c7023003d	THURSDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d2e289d8-b211-4340-8bbf-7aa0295ae6a3	FRIDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ccb2ab2d-a46d-44bd-9117-d4e29e9d2c2f	MONDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	c33e01ef-be92-474a-9e43-0543649735d5
7e1057f9-5bf3-4a8b-80ba-6b6c56c8c57f	TUESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	c33e01ef-be92-474a-9e43-0543649735d5
543b01c1-ccb1-4017-9b52-8421ffe89c07	WEDNESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	c33e01ef-be92-474a-9e43-0543649735d5
1fe4a613-9e17-4022-84cc-cb166f46dc4a	THURSDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	c33e01ef-be92-474a-9e43-0543649735d5
949118c8-7fda-43a5-a913-3edde3c188a8	FRIDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	c33e01ef-be92-474a-9e43-0543649735d5
11dafe1b-8a61-4bdf-a64a-35c30c88f5f1	MONDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	6f1475e5-0d8a-47c7-855f-ccff802aff85
beb58354-6426-42b2-b3ae-3670994a081d	TUESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	6f1475e5-0d8a-47c7-855f-ccff802aff85
3720f19c-3bb6-4c03-a4da-2459b317563d	WEDNESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	6f1475e5-0d8a-47c7-855f-ccff802aff85
9a26aafc-9a3d-493b-9c7a-7277443d1c93	THURSDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	6f1475e5-0d8a-47c7-855f-ccff802aff85
9dce593b-a7c3-43ab-bcb5-d0ab7354440b	FRIDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	6f1475e5-0d8a-47c7-855f-ccff802aff85
eafcf555-6af2-4578-a1ee-25d7906228b0	MONDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	58845959-0984-4136-a733-b75f5c9057b9
6b7d4a76-5d00-4a9d-aa1e-a4984b55cefb	TUESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	58845959-0984-4136-a733-b75f5c9057b9
de160598-049b-4c9a-b8dd-99e10b82f106	WEDNESDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	58845959-0984-4136-a733-b75f5c9057b9
a0348847-0d59-41fd-97a6-db708e495765	THURSDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	58845959-0984-4136-a733-b75f5c9057b9
b595f3d2-8243-45a6-bb11-f647fc1a85c1	FRIDAY	f023c25f-7c1d-4e2b-b7a0-fd6ec10fa472	58845959-0984-4136-a733-b75f5c9057b9
6aacdace-5ad4-4fc3-8f20-2e876c404541	MONDAY	e785a4ed-af08-4b78-a450-11c1efa76288	440c17ed-5262-4800-aa19-38e5285330c3
27c591bc-e1f0-4943-a4f8-254ab8dd5b34	TUESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	440c17ed-5262-4800-aa19-38e5285330c3
6ba5557d-0e22-40f1-9c1b-d3099023d4cb	WEDNESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	440c17ed-5262-4800-aa19-38e5285330c3
ac3b2700-45de-4871-8b88-fa76ccc10ef8	THURSDAY	e785a4ed-af08-4b78-a450-11c1efa76288	440c17ed-5262-4800-aa19-38e5285330c3
3596ee79-ef54-4af0-84eb-500eb17d5d58	FRIDAY	e785a4ed-af08-4b78-a450-11c1efa76288	440c17ed-5262-4800-aa19-38e5285330c3
f7c6870d-b0ba-4a50-8225-3f49dec8e91b	MONDAY	e785a4ed-af08-4b78-a450-11c1efa76288	5f76a266-8d18-4da8-9732-b63d4d8f24a0
765a6474-5263-4557-a1c4-c7221a9fd8ec	TUESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	5f76a266-8d18-4da8-9732-b63d4d8f24a0
29f19622-02d3-4b7a-bd0e-76c5e44f47f8	WEDNESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cd48a944-3392-4d69-9196-3a273c788bde	THURSDAY	e785a4ed-af08-4b78-a450-11c1efa76288	5f76a266-8d18-4da8-9732-b63d4d8f24a0
749cbf0f-1c18-46bc-979f-46adcaa590f7	FRIDAY	e785a4ed-af08-4b78-a450-11c1efa76288	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4b93d5ab-8ab9-45d9-93a0-9513af1dced9	MONDAY	e785a4ed-af08-4b78-a450-11c1efa76288	d54b130a-28a8-4bb1-9030-c474369c2205
22b4e5bf-72ba-45e6-9a0a-6eb3532583fd	TUESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	d54b130a-28a8-4bb1-9030-c474369c2205
e6a8d08b-d046-42ab-a0a9-5a46e49c32b3	WEDNESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	d54b130a-28a8-4bb1-9030-c474369c2205
93e84c31-2875-4b29-8e1e-5a3f1472aa44	THURSDAY	e785a4ed-af08-4b78-a450-11c1efa76288	d54b130a-28a8-4bb1-9030-c474369c2205
9a643c8b-8b23-4b87-b09e-6b20a8b2c8d2	FRIDAY	e785a4ed-af08-4b78-a450-11c1efa76288	d54b130a-28a8-4bb1-9030-c474369c2205
ebe476b8-2176-4ec8-a9af-e9a1d24ba694	MONDAY	e785a4ed-af08-4b78-a450-11c1efa76288	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5a1c632f-5aad-480d-bdc7-cb979585e6ea	TUESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1255aa35-e15c-415f-83c6-78ae5c4f7fa2	WEDNESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8a2ed086-3e83-4d14-9edc-b5d21ea13171	THURSDAY	e785a4ed-af08-4b78-a450-11c1efa76288	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
122fd3c1-c538-46d5-a6a8-980ef3af7896	FRIDAY	e785a4ed-af08-4b78-a450-11c1efa76288	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e560496e-13f1-4af4-8392-0b026a810d07	MONDAY	e785a4ed-af08-4b78-a450-11c1efa76288	c33e01ef-be92-474a-9e43-0543649735d5
ac9436d9-f7a3-446c-9b84-39d53eafd70a	TUESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	c33e01ef-be92-474a-9e43-0543649735d5
cf5e9c56-e0d0-46af-a19b-254ed8b2939e	WEDNESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	c33e01ef-be92-474a-9e43-0543649735d5
adca5516-9cd3-42a3-85ed-242eca2cdc1b	THURSDAY	e785a4ed-af08-4b78-a450-11c1efa76288	c33e01ef-be92-474a-9e43-0543649735d5
ff08745d-1011-4ad2-9724-9831bcaf1a9c	FRIDAY	e785a4ed-af08-4b78-a450-11c1efa76288	c33e01ef-be92-474a-9e43-0543649735d5
6be85ac1-85ee-44eb-9542-24431c2d5018	MONDAY	e785a4ed-af08-4b78-a450-11c1efa76288	6f1475e5-0d8a-47c7-855f-ccff802aff85
e74f5d09-bdda-473d-b2c1-91095924fe5a	TUESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	6f1475e5-0d8a-47c7-855f-ccff802aff85
af746043-9c31-4d63-b5be-728e828a48ee	WEDNESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	6f1475e5-0d8a-47c7-855f-ccff802aff85
ecc0067b-1aa0-4e75-8dfb-9f4b215e47ed	THURSDAY	e785a4ed-af08-4b78-a450-11c1efa76288	6f1475e5-0d8a-47c7-855f-ccff802aff85
c4eeb527-42f3-41b6-a0ca-4b420a6364b7	FRIDAY	e785a4ed-af08-4b78-a450-11c1efa76288	6f1475e5-0d8a-47c7-855f-ccff802aff85
fc3343a9-2b65-4ee0-8eba-e371fffdf162	MONDAY	e785a4ed-af08-4b78-a450-11c1efa76288	58845959-0984-4136-a733-b75f5c9057b9
27948a51-d6fd-4de2-a009-abdcb334710f	TUESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	58845959-0984-4136-a733-b75f5c9057b9
f61eeccc-14ba-4661-bedf-8fe9077e80bd	WEDNESDAY	e785a4ed-af08-4b78-a450-11c1efa76288	58845959-0984-4136-a733-b75f5c9057b9
359e3be9-50cc-41a4-bba2-ab180d9a4cb0	THURSDAY	e785a4ed-af08-4b78-a450-11c1efa76288	58845959-0984-4136-a733-b75f5c9057b9
770d2e27-8ee8-436e-a115-28331545d288	FRIDAY	e785a4ed-af08-4b78-a450-11c1efa76288	58845959-0984-4136-a733-b75f5c9057b9
a83c5bef-c6ed-4b22-a129-2a2c0fd78b31	MONDAY	39b4633f-81a5-46df-944c-b29a91f38b14	440c17ed-5262-4800-aa19-38e5285330c3
98064f1b-1b15-4e5f-b808-9f26a8da7963	TUESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	440c17ed-5262-4800-aa19-38e5285330c3
c2f9ec46-24e1-43b6-96ad-ee412d16b96b	WEDNESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	440c17ed-5262-4800-aa19-38e5285330c3
86e8d109-e197-4bda-a6d7-3ba7255bdf4d	THURSDAY	39b4633f-81a5-46df-944c-b29a91f38b14	440c17ed-5262-4800-aa19-38e5285330c3
5f6f5eae-0159-438b-af34-644e2999f657	FRIDAY	39b4633f-81a5-46df-944c-b29a91f38b14	440c17ed-5262-4800-aa19-38e5285330c3
2901db13-c138-4498-a5df-63b64dd08b4c	MONDAY	39b4633f-81a5-46df-944c-b29a91f38b14	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0cf1cdc3-9605-421b-9d53-d6b575cea7aa	TUESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	5f76a266-8d18-4da8-9732-b63d4d8f24a0
837ffa61-52ed-418c-a094-955d2e352f98	WEDNESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c54a8789-7f4b-4c03-a099-481b40e56080	THURSDAY	39b4633f-81a5-46df-944c-b29a91f38b14	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b9e5d73b-2331-4a43-bbee-4541dd8f75de	FRIDAY	39b4633f-81a5-46df-944c-b29a91f38b14	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cc3a258f-2ffd-4ccd-8f2b-bd5cda91f505	MONDAY	39b4633f-81a5-46df-944c-b29a91f38b14	d54b130a-28a8-4bb1-9030-c474369c2205
f7908d0a-9e71-4974-8537-2422270da16c	TUESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	d54b130a-28a8-4bb1-9030-c474369c2205
20c565f3-65b2-4bb5-9894-3a09eec187b6	WEDNESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	d54b130a-28a8-4bb1-9030-c474369c2205
b6e91a33-6077-4ff7-8f81-51348cec81d8	THURSDAY	39b4633f-81a5-46df-944c-b29a91f38b14	d54b130a-28a8-4bb1-9030-c474369c2205
3f6ad5f9-56a8-4397-a082-f134a4201cf6	FRIDAY	39b4633f-81a5-46df-944c-b29a91f38b14	d54b130a-28a8-4bb1-9030-c474369c2205
40715f3c-7080-4e8c-8dd4-0b695aeb66c5	MONDAY	39b4633f-81a5-46df-944c-b29a91f38b14	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
07c2a42c-0fac-4b67-bbfa-540300bad279	TUESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bbcade32-9259-4039-a726-8cc1defc838a	WEDNESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
39a8fdee-7d3e-4f01-89c8-e52ec52102c2	THURSDAY	39b4633f-81a5-46df-944c-b29a91f38b14	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
23def54f-963e-4a23-aa4e-55403ff7309f	FRIDAY	39b4633f-81a5-46df-944c-b29a91f38b14	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f8f3406e-d0c3-4fea-827b-aee7471b694b	MONDAY	39b4633f-81a5-46df-944c-b29a91f38b14	c33e01ef-be92-474a-9e43-0543649735d5
bdc5a71a-996d-4696-93a8-09d84fb34356	TUESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	c33e01ef-be92-474a-9e43-0543649735d5
279980b9-5871-44fe-9a0b-2ef3bcd4e6b1	WEDNESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	c33e01ef-be92-474a-9e43-0543649735d5
08bc93f1-941a-4cde-a3cb-46e75dfeedfe	THURSDAY	39b4633f-81a5-46df-944c-b29a91f38b14	c33e01ef-be92-474a-9e43-0543649735d5
5b25aec1-4b25-4095-a9f4-94e1261b98b4	FRIDAY	39b4633f-81a5-46df-944c-b29a91f38b14	c33e01ef-be92-474a-9e43-0543649735d5
eb5bc11f-c4a1-4af7-8eb6-ad1f11947b8a	MONDAY	39b4633f-81a5-46df-944c-b29a91f38b14	6f1475e5-0d8a-47c7-855f-ccff802aff85
bbd44f0b-cf4a-41d5-976d-10f43e8f955b	TUESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	6f1475e5-0d8a-47c7-855f-ccff802aff85
fbea74d6-88be-4cea-bf6d-7eb54e91421f	WEDNESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	6f1475e5-0d8a-47c7-855f-ccff802aff85
710b11ac-feab-4c77-8b1f-9bb1cbbad18a	THURSDAY	39b4633f-81a5-46df-944c-b29a91f38b14	6f1475e5-0d8a-47c7-855f-ccff802aff85
60fa7915-5fd3-429d-968d-08560084b5e6	FRIDAY	39b4633f-81a5-46df-944c-b29a91f38b14	6f1475e5-0d8a-47c7-855f-ccff802aff85
03edf7b4-c13e-4c2c-850b-053a0e91c246	MONDAY	39b4633f-81a5-46df-944c-b29a91f38b14	58845959-0984-4136-a733-b75f5c9057b9
a26c715c-a9ac-41ba-9bd9-efd6e878c3d1	TUESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	58845959-0984-4136-a733-b75f5c9057b9
5a6a2a99-daf5-478b-b852-4dbe9e254a85	WEDNESDAY	39b4633f-81a5-46df-944c-b29a91f38b14	58845959-0984-4136-a733-b75f5c9057b9
e8dddec7-4885-418e-8d5f-186135ad52c5	THURSDAY	39b4633f-81a5-46df-944c-b29a91f38b14	58845959-0984-4136-a733-b75f5c9057b9
dd298c92-935d-4ca1-826a-8fbdf5534253	TUESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	5f76a266-8d18-4da8-9732-b63d4d8f24a0
322c2597-01d3-4d77-a4d6-975188ee2245	WEDNESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5399162c-423e-449d-bb42-9daa164acbd3	THURSDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5c64193e-d928-48e4-87fa-5514794c56cc	FRIDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a2907c18-7cc3-4669-8dd3-e5cc17abae88	MONDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	d54b130a-28a8-4bb1-9030-c474369c2205
40b4f15f-2f76-4cba-b689-414e5e0352f9	TUESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	d54b130a-28a8-4bb1-9030-c474369c2205
182dc283-4881-49d5-a198-eff9383703fe	WEDNESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	d54b130a-28a8-4bb1-9030-c474369c2205
8e3d6b31-8164-46b7-bcd0-a29824a40037	THURSDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	d54b130a-28a8-4bb1-9030-c474369c2205
9a8eaf56-5082-4392-9322-088905e1c03f	FRIDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	d54b130a-28a8-4bb1-9030-c474369c2205
f0f09107-47f6-4993-847c-9ebbee994c1c	MONDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
011b5236-7de2-4e7d-9fa1-feb311cd7a31	TUESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2a08ecf1-aeca-441f-a401-dfa565eb48ab	WEDNESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5a91504d-57cc-41a3-ae36-0dddbc9d8bd2	THURSDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ae380e8c-0e48-4fa9-91a8-ae53a8d61439	FRIDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7953750e-3491-40e8-9c53-ca692b220daf	MONDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	c33e01ef-be92-474a-9e43-0543649735d5
37e185a4-0320-4864-8fec-b36ccfbbcecd	TUESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	c33e01ef-be92-474a-9e43-0543649735d5
2991065a-58ee-4a11-a39a-e563bda1be44	WEDNESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	c33e01ef-be92-474a-9e43-0543649735d5
138bc94b-d0b1-4394-b6a2-2580c3b9665b	THURSDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	c33e01ef-be92-474a-9e43-0543649735d5
aae267a1-4319-4328-b67f-981e731bf4cf	FRIDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	c33e01ef-be92-474a-9e43-0543649735d5
afcb1e7c-b51c-45c2-a2cb-290c9ddbb5a2	MONDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	6f1475e5-0d8a-47c7-855f-ccff802aff85
df586f02-ad12-47c7-89cb-7b5f519927f0	TUESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	6f1475e5-0d8a-47c7-855f-ccff802aff85
dbb1c486-87d9-41e4-be83-8418975bd2b2	WEDNESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	6f1475e5-0d8a-47c7-855f-ccff802aff85
9c9da7ad-32d0-4899-b737-53b8f452486d	THURSDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	6f1475e5-0d8a-47c7-855f-ccff802aff85
e07a9200-8326-45b7-bad7-1677346921e5	FRIDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	6f1475e5-0d8a-47c7-855f-ccff802aff85
d777c6e1-624d-4be6-8713-eaeff0026394	MONDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	58845959-0984-4136-a733-b75f5c9057b9
b8471720-dda8-423f-986f-5636e65b6fd6	TUESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	58845959-0984-4136-a733-b75f5c9057b9
1a829ff6-edb2-4867-a048-58c9a02af7b9	WEDNESDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	58845959-0984-4136-a733-b75f5c9057b9
f767bbf0-1192-4d01-96aa-26d18202325e	THURSDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	58845959-0984-4136-a733-b75f5c9057b9
76169076-9ac7-4684-8d3a-19b7a600383a	FRIDAY	cbf427e0-2f1c-422e-b7f1-f6263e1bd831	58845959-0984-4136-a733-b75f5c9057b9
3083b24d-ba0c-45ea-8bb9-4bcd5eb964de	MONDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	440c17ed-5262-4800-aa19-38e5285330c3
82a292b9-378f-45d4-a1ae-926225cd94e1	TUESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	440c17ed-5262-4800-aa19-38e5285330c3
82b39020-7615-4a5b-a673-2acceaed410b	WEDNESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	440c17ed-5262-4800-aa19-38e5285330c3
622613bb-b546-4b5b-a698-8d584081494e	THURSDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	440c17ed-5262-4800-aa19-38e5285330c3
38b14721-9d33-4459-909a-7d8561183cb0	FRIDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	440c17ed-5262-4800-aa19-38e5285330c3
d77e5370-81a2-4b53-90bb-d39484c23cd4	MONDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ea2b2774-676f-4625-b065-1f2e6ce639a8	TUESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
94e25d4c-104f-4232-abe0-adf8c667b3b1	WEDNESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
16c8eae9-a988-4282-ae07-6421ddf43023	THURSDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1856c5ae-a35a-4039-8fda-23aa924a6586	FRIDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0b3969eb-82e0-414e-859e-2ce717293bba	MONDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	d54b130a-28a8-4bb1-9030-c474369c2205
9cc874f7-ab7f-4e5f-ae7a-d3276cece18a	TUESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	d54b130a-28a8-4bb1-9030-c474369c2205
bd6acb8d-3cfb-4682-bf22-67928aa4084a	WEDNESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	d54b130a-28a8-4bb1-9030-c474369c2205
93504a98-9306-44ba-9d9d-c2cff031544a	THURSDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	d54b130a-28a8-4bb1-9030-c474369c2205
4f9125e3-7c13-4adf-9423-468337ab22ad	FRIDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	d54b130a-28a8-4bb1-9030-c474369c2205
964f4587-4ca5-44d8-906f-639ee4beb948	MONDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
947a1d27-ba14-45a2-80ef-4130bb3cfec4	TUESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6ce6892f-4d06-44b2-8bae-b2f16523e22a	WEDNESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2550db2c-44d2-47ac-9295-c377df4f7d16	THURSDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6242c1c6-c2d2-4148-b9f2-4e950efd75c7	FRIDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
57de9945-444a-4a55-be4f-fade18137086	MONDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	c33e01ef-be92-474a-9e43-0543649735d5
b5006085-e283-4f38-86a5-885960b3faa3	TUESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	c33e01ef-be92-474a-9e43-0543649735d5
f3ccd3fa-fbe3-4640-8b02-bf4e00d0b682	WEDNESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	c33e01ef-be92-474a-9e43-0543649735d5
af9c4cd0-5cd8-4fc7-b238-cb68165eef87	THURSDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	c33e01ef-be92-474a-9e43-0543649735d5
65988ce9-5693-4fca-9010-52a3cb6aec3f	FRIDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	c33e01ef-be92-474a-9e43-0543649735d5
2767cffb-7f01-4548-8ead-c3fdcc546e0b	MONDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	6f1475e5-0d8a-47c7-855f-ccff802aff85
ec957ef1-5598-43a7-b74c-a44d1f78b336	TUESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	6f1475e5-0d8a-47c7-855f-ccff802aff85
639f5767-d2fd-44c9-b60e-8e08c26d11e5	WEDNESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	6f1475e5-0d8a-47c7-855f-ccff802aff85
f44b37c0-4849-4788-a5f5-eb528814edd9	THURSDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	6f1475e5-0d8a-47c7-855f-ccff802aff85
4aaa360f-6ead-4454-ae6c-3cc8efeb6c63	FRIDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	6f1475e5-0d8a-47c7-855f-ccff802aff85
633cfcef-7eb1-4959-a638-818e4401f439	MONDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	58845959-0984-4136-a733-b75f5c9057b9
c9ed21dd-68d1-4939-a0b0-95b8e17a2b53	TUESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	58845959-0984-4136-a733-b75f5c9057b9
90ff1c28-b3fc-4077-84e2-abf085449018	WEDNESDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	58845959-0984-4136-a733-b75f5c9057b9
1f9eeed5-0f4f-4817-91cc-f6c1e541d5c9	THURSDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	58845959-0984-4136-a733-b75f5c9057b9
06fe7236-bea0-42d3-abf8-ca723e6a9972	FRIDAY	9e8d1ad1-f50e-40b2-a434-18d009de310e	58845959-0984-4136-a733-b75f5c9057b9
422efc34-967b-44a2-b14f-ada406e4f618	MONDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	440c17ed-5262-4800-aa19-38e5285330c3
0e35868f-959b-4dad-90c0-8636221362ee	TUESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	440c17ed-5262-4800-aa19-38e5285330c3
36b845fc-fab8-4baa-a8b5-5db5f864056b	WEDNESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	440c17ed-5262-4800-aa19-38e5285330c3
5cd74ea6-9fb2-4975-8486-b88ea372b14c	THURSDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	440c17ed-5262-4800-aa19-38e5285330c3
6a4cf7f8-b7da-4fc5-a7ef-d7d7a124bbdf	FRIDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	440c17ed-5262-4800-aa19-38e5285330c3
c8faa6ac-5db8-4b4d-bed5-9b2cc0b67c54	MONDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	5f76a266-8d18-4da8-9732-b63d4d8f24a0
240ca0a7-4c25-42d7-9413-c80a26617ae7	TUESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b55ec1aa-a53d-4960-8831-e83d82172089	WEDNESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	5f76a266-8d18-4da8-9732-b63d4d8f24a0
17f77cdf-2ed8-450b-8a3b-4d87d9bd43ac	THURSDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b8ac8215-66d2-4e91-a322-19dde6ae64b0	FRIDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a4b4c8a5-1fc2-4bb9-ab16-acd235120795	MONDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	d54b130a-28a8-4bb1-9030-c474369c2205
ea9571b3-af0d-4a98-997c-b34f16a2868c	TUESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	d54b130a-28a8-4bb1-9030-c474369c2205
c8191fc0-2f7c-4209-a586-995f7c6ca842	WEDNESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	d54b130a-28a8-4bb1-9030-c474369c2205
95618d67-e3bf-4d77-8029-573fc9e0346c	THURSDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	d54b130a-28a8-4bb1-9030-c474369c2205
90c23e6e-90d4-4b6c-b6fe-3a9d7b12c1fb	FRIDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	d54b130a-28a8-4bb1-9030-c474369c2205
a93b4839-9e1e-4cd0-9a49-bf3b012602da	MONDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5f88df8a-3864-42cf-b999-6c1d2a4c219c	TUESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e2ecd912-fec7-43a6-b12a-94fed071d8f5	WEDNESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1fded428-25e8-4a94-83b7-90d2456c1994	THURSDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d92f9fbf-ec0c-45c2-aad2-a2fcac15503f	FRIDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7801fd73-cac9-45c8-b633-a6cdc78bed5a	MONDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	c33e01ef-be92-474a-9e43-0543649735d5
edb74861-4510-4b56-ab2d-18b414907d45	TUESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	c33e01ef-be92-474a-9e43-0543649735d5
ec642196-8420-49e2-a03b-5d330b13c36c	WEDNESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	c33e01ef-be92-474a-9e43-0543649735d5
e2d3de58-9d1f-4384-b68b-a2d6e022bc08	THURSDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	c33e01ef-be92-474a-9e43-0543649735d5
98fcfdb0-9c0b-4e1f-a29f-22dffff0c6a0	FRIDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	c33e01ef-be92-474a-9e43-0543649735d5
8d0261de-e9d4-4f4a-8c71-329a9942048b	MONDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	6f1475e5-0d8a-47c7-855f-ccff802aff85
3610fd74-8a0f-4e68-ba3a-651b6db4f068	TUESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	6f1475e5-0d8a-47c7-855f-ccff802aff85
9c2fb502-cb66-4098-b3bc-47ceeeb605ab	WEDNESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	6f1475e5-0d8a-47c7-855f-ccff802aff85
04d1fdbf-44b1-480d-b76e-98cfb6d51a1b	THURSDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	6f1475e5-0d8a-47c7-855f-ccff802aff85
4dc2a2df-2ac0-4792-bcbf-4d35287a8416	FRIDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	6f1475e5-0d8a-47c7-855f-ccff802aff85
415a8b61-c415-40f8-b3e0-6d725c2f55e5	MONDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	58845959-0984-4136-a733-b75f5c9057b9
9b9560bc-4860-4403-9fab-d7cb8f11ab2b	TUESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	58845959-0984-4136-a733-b75f5c9057b9
1600c4e6-5779-40f6-844e-ea7117ce5dfc	WEDNESDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	58845959-0984-4136-a733-b75f5c9057b9
9b5a7734-ffdf-4ee9-ba2e-5dd8ba39a164	THURSDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	58845959-0984-4136-a733-b75f5c9057b9
9fdc3863-272b-451f-a298-ecfcd3748378	FRIDAY	42b1f177-628c-4a87-bf8f-a11932fffa23	58845959-0984-4136-a733-b75f5c9057b9
4588f051-5c2d-4820-9f25-e26ad261dcdb	MONDAY	098646af-2946-4e14-b37b-aae996c5a920	440c17ed-5262-4800-aa19-38e5285330c3
81bb1842-f0ec-42a2-813a-b3ea2a07df2b	TUESDAY	098646af-2946-4e14-b37b-aae996c5a920	440c17ed-5262-4800-aa19-38e5285330c3
6b385c4a-4420-4ca1-b08d-e38e7ea1f0a1	WEDNESDAY	098646af-2946-4e14-b37b-aae996c5a920	440c17ed-5262-4800-aa19-38e5285330c3
65a817cb-8427-4d9a-97b8-accac4489e25	THURSDAY	098646af-2946-4e14-b37b-aae996c5a920	440c17ed-5262-4800-aa19-38e5285330c3
07033535-f375-4aa3-a606-947a8b9ba09e	FRIDAY	098646af-2946-4e14-b37b-aae996c5a920	440c17ed-5262-4800-aa19-38e5285330c3
4bf9e59c-3786-4147-b8f2-f5abf1544edb	MONDAY	098646af-2946-4e14-b37b-aae996c5a920	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ebab179a-ab77-41c5-8957-ec8c083171f5	TUESDAY	098646af-2946-4e14-b37b-aae996c5a920	5f76a266-8d18-4da8-9732-b63d4d8f24a0
26b016eb-2658-4e99-b59c-20d7665e859f	WEDNESDAY	098646af-2946-4e14-b37b-aae996c5a920	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d4c25feb-1034-4fda-a810-155960d427aa	THURSDAY	098646af-2946-4e14-b37b-aae996c5a920	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b5028867-0a70-4a1e-bcdd-c79702c93dea	FRIDAY	098646af-2946-4e14-b37b-aae996c5a920	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5a63d6be-b229-46a1-a3a8-28e9f7c9e493	MONDAY	098646af-2946-4e14-b37b-aae996c5a920	d54b130a-28a8-4bb1-9030-c474369c2205
f2a238f0-7f26-4ec7-9f0f-62d2c6928b96	TUESDAY	098646af-2946-4e14-b37b-aae996c5a920	d54b130a-28a8-4bb1-9030-c474369c2205
64aa655e-ac00-4d00-9b2e-ef765b549f9d	WEDNESDAY	098646af-2946-4e14-b37b-aae996c5a920	d54b130a-28a8-4bb1-9030-c474369c2205
3035ac1f-59cb-469d-aeb5-bbf3f7e29360	THURSDAY	098646af-2946-4e14-b37b-aae996c5a920	d54b130a-28a8-4bb1-9030-c474369c2205
d02aa470-06ed-4342-a33f-616c241d2ccd	FRIDAY	098646af-2946-4e14-b37b-aae996c5a920	d54b130a-28a8-4bb1-9030-c474369c2205
fcc26d4e-6979-4604-bbb9-c3a4e0473235	MONDAY	098646af-2946-4e14-b37b-aae996c5a920	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5e9358a9-abf1-47d0-895e-5d2a08b46b8b	TUESDAY	098646af-2946-4e14-b37b-aae996c5a920	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
45d2969a-269d-4cea-a03b-9efd0fdcb98d	WEDNESDAY	098646af-2946-4e14-b37b-aae996c5a920	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
324ccd6e-687a-4c86-b655-7acc03d01afc	THURSDAY	098646af-2946-4e14-b37b-aae996c5a920	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
556f4d60-afe2-44e9-a801-af4404958620	FRIDAY	098646af-2946-4e14-b37b-aae996c5a920	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8fba9630-7738-4c90-86ab-33dc1d14667e	MONDAY	098646af-2946-4e14-b37b-aae996c5a920	c33e01ef-be92-474a-9e43-0543649735d5
29367df7-edc3-411c-8c87-bd95a9e6a56d	TUESDAY	098646af-2946-4e14-b37b-aae996c5a920	c33e01ef-be92-474a-9e43-0543649735d5
5b2382b5-bcd5-4e1e-acba-7aadbe3f4907	WEDNESDAY	098646af-2946-4e14-b37b-aae996c5a920	c33e01ef-be92-474a-9e43-0543649735d5
fbb4728f-eeab-40cf-b70c-e005046ed267	THURSDAY	098646af-2946-4e14-b37b-aae996c5a920	c33e01ef-be92-474a-9e43-0543649735d5
f73d7655-86d7-4c7b-af27-6baee3ee629d	FRIDAY	098646af-2946-4e14-b37b-aae996c5a920	c33e01ef-be92-474a-9e43-0543649735d5
b5e37d01-0f4a-47a5-b367-af7cbe3d7ea9	MONDAY	098646af-2946-4e14-b37b-aae996c5a920	6f1475e5-0d8a-47c7-855f-ccff802aff85
2b380c4d-1ddc-4eb4-b554-64e0e6d88225	TUESDAY	098646af-2946-4e14-b37b-aae996c5a920	6f1475e5-0d8a-47c7-855f-ccff802aff85
2168ca43-2486-4784-bab5-09afac896703	WEDNESDAY	098646af-2946-4e14-b37b-aae996c5a920	6f1475e5-0d8a-47c7-855f-ccff802aff85
9582a960-d3f3-40cc-a27a-e9f6ed6a00a1	THURSDAY	098646af-2946-4e14-b37b-aae996c5a920	6f1475e5-0d8a-47c7-855f-ccff802aff85
64fae0fb-6d16-4307-ba86-0a3f9c77f437	FRIDAY	098646af-2946-4e14-b37b-aae996c5a920	6f1475e5-0d8a-47c7-855f-ccff802aff85
43fc071d-24b2-4b99-8d6a-b1ba79a71306	MONDAY	098646af-2946-4e14-b37b-aae996c5a920	58845959-0984-4136-a733-b75f5c9057b9
41a2b568-7c91-493d-8618-444f4d26fe33	TUESDAY	098646af-2946-4e14-b37b-aae996c5a920	58845959-0984-4136-a733-b75f5c9057b9
4cb1434e-4aec-4a2c-8270-41060f49c9de	WEDNESDAY	098646af-2946-4e14-b37b-aae996c5a920	58845959-0984-4136-a733-b75f5c9057b9
37424e8b-42c7-4945-8e1c-541122bbdd0e	THURSDAY	098646af-2946-4e14-b37b-aae996c5a920	58845959-0984-4136-a733-b75f5c9057b9
813abb5e-e8df-4851-9909-5c8029145121	FRIDAY	098646af-2946-4e14-b37b-aae996c5a920	58845959-0984-4136-a733-b75f5c9057b9
9163b049-9706-4473-8e54-ef336282d4c1	MONDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	440c17ed-5262-4800-aa19-38e5285330c3
cf0d53db-2358-423c-b290-4f7f99310bec	TUESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	440c17ed-5262-4800-aa19-38e5285330c3
70f91e4a-5433-4591-8ba7-c204fde8a598	WEDNESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	440c17ed-5262-4800-aa19-38e5285330c3
f2b5598c-42af-40a7-b0d6-13f3ec3598d7	THURSDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	440c17ed-5262-4800-aa19-38e5285330c3
2959b985-e810-405d-a182-f33db3fdf6e9	FRIDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	440c17ed-5262-4800-aa19-38e5285330c3
8e159e83-7ee9-408b-8754-328f3da01544	MONDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	5f76a266-8d18-4da8-9732-b63d4d8f24a0
09fa9109-8ccf-44b2-b489-303fde75454e	TUESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f3129f59-07a3-43a0-be96-db62914a5aab	WEDNESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1fe2faff-0633-4f61-be6f-20bcaf5aa8c8	THURSDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	5f76a266-8d18-4da8-9732-b63d4d8f24a0
be8a033c-5315-49da-ae7f-f1efba609911	FRIDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	5f76a266-8d18-4da8-9732-b63d4d8f24a0
35e0087e-f457-48ff-b45e-1ec07c64e0b6	MONDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	d54b130a-28a8-4bb1-9030-c474369c2205
e943b59c-ad7f-42f0-83ea-e468e6613428	TUESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	d54b130a-28a8-4bb1-9030-c474369c2205
d0c99bd7-6d53-4cc7-9902-b640e7e6cfff	WEDNESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	d54b130a-28a8-4bb1-9030-c474369c2205
3e99eaea-61f7-4521-8eef-e61bd1efe5a4	THURSDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	d54b130a-28a8-4bb1-9030-c474369c2205
a8112fac-7581-4798-a9cb-3c86214bdf55	FRIDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	d54b130a-28a8-4bb1-9030-c474369c2205
5f22fd7b-7e12-4fd6-8059-0347ffc4f5e0	MONDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
50395bd0-765e-4dae-918c-79cf4a184553	TUESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7a6e86bc-09a2-4b49-b30e-e50a0f5233c3	WEDNESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
247496bc-9d12-4d24-bbfe-66e388297ef0	THURSDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
32acbf2a-32d9-443f-8232-6387b303fec2	FRIDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4a131d5d-0028-4948-8283-c8b853f0449b	MONDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	c33e01ef-be92-474a-9e43-0543649735d5
8b83d25e-748f-4796-a506-1f1c4c3fa345	TUESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	c33e01ef-be92-474a-9e43-0543649735d5
822748cc-2ab5-42c8-a13b-1fd57e934e64	WEDNESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	c33e01ef-be92-474a-9e43-0543649735d5
1eafcd09-a0f1-4054-a6fe-17babac2320c	THURSDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	c33e01ef-be92-474a-9e43-0543649735d5
7753a0f9-4923-44df-8c6f-17586c6a105b	FRIDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	c33e01ef-be92-474a-9e43-0543649735d5
5d84c48d-8668-4ba6-9c50-0426dc220f67	MONDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	6f1475e5-0d8a-47c7-855f-ccff802aff85
4a41494e-b94e-4568-9f10-3894d10b502b	TUESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	6f1475e5-0d8a-47c7-855f-ccff802aff85
ae41c4be-5829-4aaa-88bb-ee5ad9ec6a31	WEDNESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	6f1475e5-0d8a-47c7-855f-ccff802aff85
39435ab6-92e7-4125-baac-c67eac9078cc	THURSDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	6f1475e5-0d8a-47c7-855f-ccff802aff85
6b215d6a-b9e6-424d-b149-440d5f5933a7	FRIDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	6f1475e5-0d8a-47c7-855f-ccff802aff85
e94d0068-592e-47d3-95e2-3e1d1fe01532	MONDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	58845959-0984-4136-a733-b75f5c9057b9
8f2a450a-9949-479d-b59f-a58e9280cd6c	TUESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	58845959-0984-4136-a733-b75f5c9057b9
75e59d01-16a9-4ae9-bfd1-5703d9709aea	WEDNESDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	58845959-0984-4136-a733-b75f5c9057b9
e5e988fa-de8e-4a1a-ac08-9f59604695dc	THURSDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	58845959-0984-4136-a733-b75f5c9057b9
8c394816-b4cc-4e8f-9be8-d8d4f43144ac	FRIDAY	c94019d0-8741-4f4b-a6ab-0c2439d77bde	58845959-0984-4136-a733-b75f5c9057b9
73bf08cb-90e0-4356-8371-57e4f71d7372	MONDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	440c17ed-5262-4800-aa19-38e5285330c3
16f1b57a-978a-4dd2-b544-d81e4563f3aa	TUESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	440c17ed-5262-4800-aa19-38e5285330c3
4704fa6e-fd2b-4a78-b71c-ccac67770aa7	WEDNESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	440c17ed-5262-4800-aa19-38e5285330c3
f267b7e2-31a5-45dc-9a97-cdd57a2a046c	THURSDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	440c17ed-5262-4800-aa19-38e5285330c3
ab294052-ad4d-4678-bd33-87c8e100f744	FRIDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	440c17ed-5262-4800-aa19-38e5285330c3
9f838054-ba70-4acf-a880-6cf77509969c	MONDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8c3cafab-887b-4174-94d5-6b4e57169391	TUESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
82ed71ef-2da3-4ae2-8f9a-83fa8b6db1d1	WEDNESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5432d98b-a061-403c-b96a-a89668b0a35f	THURSDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f54c5325-d2bf-4db1-bd7c-0dc2476b4867	FRIDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8cec2e31-159c-4971-ba66-93296f46a480	MONDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	d54b130a-28a8-4bb1-9030-c474369c2205
5cb40b7a-c9c2-48ab-9fe7-4b371619e44f	TUESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	d54b130a-28a8-4bb1-9030-c474369c2205
af2d3221-d3f8-4974-85b6-81670c3d9489	WEDNESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	d54b130a-28a8-4bb1-9030-c474369c2205
f3c8133e-9547-43f8-8692-a6b605739a09	THURSDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	d54b130a-28a8-4bb1-9030-c474369c2205
3488a540-f658-4417-80ff-2ab100c9c70a	FRIDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	d54b130a-28a8-4bb1-9030-c474369c2205
9929cc20-11f7-4fcf-81dd-6e76ac90fac8	MONDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7e8f1356-d30b-40c6-950d-174ba4924167	TUESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1c8a7369-72bb-4eca-ad66-38debb762162	WEDNESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6635d5ef-bc37-4147-82ac-b5ceeb37fb8e	THURSDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a7f3a249-73fa-44b6-a01b-f973f60be900	FRIDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
63d02f80-3f41-4d51-ad46-6a3237042b28	MONDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	c33e01ef-be92-474a-9e43-0543649735d5
1785f350-d78e-4620-83a8-e13e72e8b0e6	TUESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	c33e01ef-be92-474a-9e43-0543649735d5
cf97bf0d-5b7c-4210-80ff-4d1aab9ac8ac	WEDNESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	c33e01ef-be92-474a-9e43-0543649735d5
1a6ee2b5-3c93-43ea-bedc-43ac88f5fe28	THURSDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	c33e01ef-be92-474a-9e43-0543649735d5
0557b615-c499-40d6-b093-bba2b31078ed	FRIDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	c33e01ef-be92-474a-9e43-0543649735d5
2c0e5412-12b1-44c7-b6de-36fc832565ce	MONDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	6f1475e5-0d8a-47c7-855f-ccff802aff85
8b14a8c7-afb7-4a76-80e8-6840fe28e655	TUESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	6f1475e5-0d8a-47c7-855f-ccff802aff85
c0854f58-236e-481e-af0c-d282b9b7f897	WEDNESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	6f1475e5-0d8a-47c7-855f-ccff802aff85
8b539902-75a2-4cec-aeee-13b413a792fb	THURSDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	6f1475e5-0d8a-47c7-855f-ccff802aff85
8fba356c-b2b8-4a84-9cab-3fa67893df9d	FRIDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	6f1475e5-0d8a-47c7-855f-ccff802aff85
73a2e411-d215-42be-bf66-58fa9ca5c2ec	MONDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	58845959-0984-4136-a733-b75f5c9057b9
b2baf181-ef3c-4386-912e-dcc127932355	TUESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	58845959-0984-4136-a733-b75f5c9057b9
e98e9804-cfa2-4832-9e6a-1aa7903fa6d9	WEDNESDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	58845959-0984-4136-a733-b75f5c9057b9
17d90b98-ab37-4b8c-b095-b65e24575c32	THURSDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	58845959-0984-4136-a733-b75f5c9057b9
fbda6314-0289-4d90-8fb9-0d92a1db0755	FRIDAY	fbe60aa6-8d84-426d-adb8-c40638042c9c	58845959-0984-4136-a733-b75f5c9057b9
70b3f60c-0685-4ff4-a293-e163b0882250	MONDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	440c17ed-5262-4800-aa19-38e5285330c3
3c5a5836-d6bd-4222-9f1c-8cb07d3f6fb6	TUESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	440c17ed-5262-4800-aa19-38e5285330c3
248ea832-0fa3-42ae-a1eb-a81094b9b427	WEDNESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	440c17ed-5262-4800-aa19-38e5285330c3
0ef2a6b9-0ba7-4f86-9a0f-6eab120df27a	THURSDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	440c17ed-5262-4800-aa19-38e5285330c3
61ea0c93-dcaf-489b-91ef-0307bf8a1027	FRIDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	440c17ed-5262-4800-aa19-38e5285330c3
61819aa6-6ca8-4c66-871e-35bd2403a2c5	MONDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bc7727b2-fe87-4bce-a106-a1ed80250851	TUESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6e436996-2e4a-4510-ba1e-0ad7be1feff0	WEDNESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	5f76a266-8d18-4da8-9732-b63d4d8f24a0
44222377-9a41-4d59-84a9-7c5a626aa532	THURSDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e622e5e3-a125-4b55-ba8b-8adc0a276216	FRIDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d0014f86-949e-4da6-93e1-4eb2e50726e6	MONDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	d54b130a-28a8-4bb1-9030-c474369c2205
6c4cd26c-1c92-4e70-8a4e-4594220a9b63	TUESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	d54b130a-28a8-4bb1-9030-c474369c2205
2bf71bc1-1499-4dc3-92e6-2e28187779b0	WEDNESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	d54b130a-28a8-4bb1-9030-c474369c2205
b48804a1-aaa8-42f6-acd8-762d36c903a4	THURSDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	d54b130a-28a8-4bb1-9030-c474369c2205
8744de01-8d41-4107-90bd-cdf895d257b7	FRIDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	d54b130a-28a8-4bb1-9030-c474369c2205
8d453730-62dd-4e5d-8a3f-84f362bcc837	MONDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cc8fa54c-f664-436b-8c90-46f8b5a4308f	TUESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3d707ada-52f1-46df-9abe-46206e40e3ea	WEDNESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
aa4aeef2-92ca-4cb9-9a63-0a44fb1caecf	THURSDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9217197e-3c22-4d3f-a418-f263e7a0bfb2	FRIDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b14202c6-2675-42aa-afa9-4934769339bf	MONDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	c33e01ef-be92-474a-9e43-0543649735d5
246f250c-be37-48bd-9596-d9119ccda12b	TUESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	c33e01ef-be92-474a-9e43-0543649735d5
c965e5a6-4018-43fb-a749-e4c8767eb2c3	WEDNESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	c33e01ef-be92-474a-9e43-0543649735d5
6fd518e5-b3cc-4a2c-9b1e-863162b9befb	THURSDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	c33e01ef-be92-474a-9e43-0543649735d5
f67058b5-5e8a-418e-9078-fad59c5c7e6e	FRIDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	c33e01ef-be92-474a-9e43-0543649735d5
762e2137-3b5f-468f-98b3-dee338d88a98	MONDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	6f1475e5-0d8a-47c7-855f-ccff802aff85
b69b6dab-d9e7-4306-a8ea-fca9507c9b90	TUESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	6f1475e5-0d8a-47c7-855f-ccff802aff85
1033b65d-c669-4e1f-a599-d87fc0082f1d	WEDNESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	6f1475e5-0d8a-47c7-855f-ccff802aff85
7af60fa1-ceaa-4925-b4b5-6de69b6ee4e9	THURSDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	6f1475e5-0d8a-47c7-855f-ccff802aff85
e5e0a14b-79ec-4725-ac9b-24269591791e	FRIDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	6f1475e5-0d8a-47c7-855f-ccff802aff85
f4355fa4-5933-4189-b6b6-942b07c66fda	MONDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	58845959-0984-4136-a733-b75f5c9057b9
f8740fcb-3654-449e-83bf-8aa8f81e9aa2	TUESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	58845959-0984-4136-a733-b75f5c9057b9
7122aae3-e1cb-49d5-90fc-961e55be94f4	WEDNESDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	58845959-0984-4136-a733-b75f5c9057b9
056410bb-a8cb-457b-abff-25d5a09c3db2	THURSDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	58845959-0984-4136-a733-b75f5c9057b9
0b9cefd4-cb35-41de-bd70-e26f8eb82aef	FRIDAY	4ec986fc-f097-4405-bbfa-7026fbe4de16	58845959-0984-4136-a733-b75f5c9057b9
6d483a28-1706-48ae-aae0-60dda5aea40d	MONDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	440c17ed-5262-4800-aa19-38e5285330c3
d8ca2ac3-9ea5-4555-ab80-fc2503d4a15c	TUESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	440c17ed-5262-4800-aa19-38e5285330c3
d35b6af9-49aa-4bd2-938f-9d888c36a6c3	WEDNESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	440c17ed-5262-4800-aa19-38e5285330c3
a705bed4-5e15-4c58-80a3-202ee5293753	THURSDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	440c17ed-5262-4800-aa19-38e5285330c3
af18a3cc-62a5-4865-bd3b-1b835e23972b	FRIDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	440c17ed-5262-4800-aa19-38e5285330c3
a5ba8a98-8567-490e-a9f8-28b36a91812f	MONDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	440c17ed-5262-4800-aa19-38e5285330c3
c74e9a62-bbfb-4ada-b8c4-b480a66b9e3b	TUESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	440c17ed-5262-4800-aa19-38e5285330c3
9da1b744-f7d8-4a29-8a6b-ed64a0589676	WEDNESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	440c17ed-5262-4800-aa19-38e5285330c3
7b4ca6f2-0335-434c-8304-eaf491d16b3c	THURSDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	440c17ed-5262-4800-aa19-38e5285330c3
48d23bc6-cbed-443d-ac06-1c5a2becfebf	FRIDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	440c17ed-5262-4800-aa19-38e5285330c3
20d76fba-6864-43f1-9aba-cc4e50eec9a1	MONDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5e3d24f8-8586-4117-b366-fbbd8d0c2ba7	TUESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
50d26feb-e1de-4e63-b0ee-5eb5349331f0	WEDNESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c6a14284-a250-46ed-94bf-ee5a5d1dc495	THURSDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c0dd0db1-4b52-4e30-94d5-0f11e1f64952	FRIDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	5f76a266-8d18-4da8-9732-b63d4d8f24a0
20949c70-bcb0-4496-94d2-78a1ff3ab271	MONDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	d54b130a-28a8-4bb1-9030-c474369c2205
dfd57b8b-61fb-4c99-af67-317c11e1070b	TUESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	d54b130a-28a8-4bb1-9030-c474369c2205
0200c1a1-2a4b-4ad0-b4f1-2872a5b42211	WEDNESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	d54b130a-28a8-4bb1-9030-c474369c2205
18139050-d942-4596-88d1-a5492a56a5c7	THURSDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	d54b130a-28a8-4bb1-9030-c474369c2205
a1ed47ee-3980-460b-9b88-afeeb859733d	FRIDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	d54b130a-28a8-4bb1-9030-c474369c2205
0189f85b-b075-480c-b423-8670db2deb8e	MONDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
eee2cf64-4243-4fbb-9014-2f1a1a81f548	TUESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
736c6ae9-6f41-411d-a2a6-de4f28007ee9	WEDNESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
062e7107-346e-43e6-a6ca-38fb8dea84cf	THURSDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cadbd11a-759a-4555-812c-3a399c5bdcc9	FRIDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9e667bb4-80de-48d8-b161-39780b5c1335	MONDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	c33e01ef-be92-474a-9e43-0543649735d5
b5fc7f75-beba-4b95-b533-06daa616c82b	TUESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	c33e01ef-be92-474a-9e43-0543649735d5
4c06b1fa-dd45-4552-80f6-75d6edb5810c	WEDNESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	c33e01ef-be92-474a-9e43-0543649735d5
5ba92550-4329-4fbc-be59-7756229d591a	THURSDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	c33e01ef-be92-474a-9e43-0543649735d5
dee03ca8-19d6-4db7-9386-9484c3d62ad5	FRIDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	c33e01ef-be92-474a-9e43-0543649735d5
c8573a5a-ae4b-48ef-ad8f-ab682be5ff62	MONDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	6f1475e5-0d8a-47c7-855f-ccff802aff85
7b1ba259-5f61-4c5a-aae2-be0f69a3d4e5	TUESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	6f1475e5-0d8a-47c7-855f-ccff802aff85
ec663924-9c99-4273-8d15-6b60a00b9139	WEDNESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	6f1475e5-0d8a-47c7-855f-ccff802aff85
55cd8733-0e32-4528-9812-cd9e579102e3	THURSDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	6f1475e5-0d8a-47c7-855f-ccff802aff85
86d2bc90-42f5-4f0a-b138-66ed647e9fca	FRIDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	6f1475e5-0d8a-47c7-855f-ccff802aff85
5d132794-90f4-48b5-9ecd-6455a76c5f78	MONDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	58845959-0984-4136-a733-b75f5c9057b9
911486e3-10d9-4a9d-a020-1196aeaf9f82	TUESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	58845959-0984-4136-a733-b75f5c9057b9
e32ecdf4-f6b0-4a29-950a-9caee7f59cb1	WEDNESDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	58845959-0984-4136-a733-b75f5c9057b9
4ad65e1f-b211-4257-bdf3-c9769d8ac07e	THURSDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	58845959-0984-4136-a733-b75f5c9057b9
47d754f0-8515-421e-91fd-fb4ff3c29b68	FRIDAY	ab832241-5ec1-431f-ae8c-6431218c18e2	58845959-0984-4136-a733-b75f5c9057b9
96051b7e-9bd5-4d2a-8920-5664b0fe773a	MONDAY	3eb87c06-79ea-4b14-8318-741bad302e14	440c17ed-5262-4800-aa19-38e5285330c3
bc174d2c-d90c-487e-8b03-f41f4a5f7d4e	TUESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	440c17ed-5262-4800-aa19-38e5285330c3
cbaff124-af7c-4523-9118-a5c0c6f70731	WEDNESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	440c17ed-5262-4800-aa19-38e5285330c3
28a60b8d-92be-4af8-b825-a4844abe8878	THURSDAY	3eb87c06-79ea-4b14-8318-741bad302e14	440c17ed-5262-4800-aa19-38e5285330c3
f90a3ab8-e05f-40e6-b055-b25b39c12d4a	FRIDAY	3eb87c06-79ea-4b14-8318-741bad302e14	440c17ed-5262-4800-aa19-38e5285330c3
69423041-f21b-41d8-ae5b-a1076b565e6a	MONDAY	3eb87c06-79ea-4b14-8318-741bad302e14	5f76a266-8d18-4da8-9732-b63d4d8f24a0
30d425e8-8bf6-4ac0-91a8-b8e9baf7156f	TUESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ebe6155a-0b52-4e5c-9455-e17ab362263e	WEDNESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e0ea8111-fcb7-44ce-9620-84c388f5f338	THURSDAY	3eb87c06-79ea-4b14-8318-741bad302e14	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e5835000-9388-4f80-8946-ed4ce48a21dc	FRIDAY	3eb87c06-79ea-4b14-8318-741bad302e14	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d722690b-f6f7-42bd-934a-f7647a8ff124	MONDAY	3eb87c06-79ea-4b14-8318-741bad302e14	d54b130a-28a8-4bb1-9030-c474369c2205
e7321d81-0736-4807-a44a-32e4f81b50f4	TUESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	d54b130a-28a8-4bb1-9030-c474369c2205
e480c7ca-01c0-4f8d-adba-d8ca297e2808	WEDNESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	d54b130a-28a8-4bb1-9030-c474369c2205
79801116-b0d4-4d00-a273-f5ae8605dfeb	THURSDAY	3eb87c06-79ea-4b14-8318-741bad302e14	d54b130a-28a8-4bb1-9030-c474369c2205
8b3423d9-8c29-4942-859a-9c17f20e8204	FRIDAY	3eb87c06-79ea-4b14-8318-741bad302e14	d54b130a-28a8-4bb1-9030-c474369c2205
9120ea32-44b8-4523-a520-6d1ef9bbffea	MONDAY	3eb87c06-79ea-4b14-8318-741bad302e14	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4c95eef5-2b66-4d2f-8b08-191fdb8cb079	TUESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1a92ee04-ab48-49e9-870a-46a0f7d4a5cc	WEDNESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c57f93d0-0176-4cf7-8fb5-901b81f5e01e	THURSDAY	3eb87c06-79ea-4b14-8318-741bad302e14	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d1c91c31-a6ca-4ec9-8031-3be6d5c7f8a1	FRIDAY	3eb87c06-79ea-4b14-8318-741bad302e14	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
486b503f-1914-42af-a15c-f62b8e0d5d41	MONDAY	3eb87c06-79ea-4b14-8318-741bad302e14	c33e01ef-be92-474a-9e43-0543649735d5
c7b8978d-5d57-48a6-9dd2-083a266f040f	TUESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	c33e01ef-be92-474a-9e43-0543649735d5
deb93abe-f5ce-4b14-8919-a860313bb72d	WEDNESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	c33e01ef-be92-474a-9e43-0543649735d5
fce27c00-1197-45ba-bc36-749fe941678e	THURSDAY	3eb87c06-79ea-4b14-8318-741bad302e14	c33e01ef-be92-474a-9e43-0543649735d5
f11c8830-8675-470f-a154-aae7515d4db3	FRIDAY	3eb87c06-79ea-4b14-8318-741bad302e14	c33e01ef-be92-474a-9e43-0543649735d5
b4f4ea89-d5b3-4ca1-b101-3cc68a2bd439	MONDAY	3eb87c06-79ea-4b14-8318-741bad302e14	6f1475e5-0d8a-47c7-855f-ccff802aff85
bedef873-f519-446f-91ce-9e250cabf997	TUESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	6f1475e5-0d8a-47c7-855f-ccff802aff85
16a031d0-4a53-4617-af6c-a632328d4d97	WEDNESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	6f1475e5-0d8a-47c7-855f-ccff802aff85
95b1225d-8963-43b9-a337-526f8a22327d	THURSDAY	3eb87c06-79ea-4b14-8318-741bad302e14	6f1475e5-0d8a-47c7-855f-ccff802aff85
8e8ccd06-9927-485c-a6fe-c12a45a287e5	FRIDAY	3eb87c06-79ea-4b14-8318-741bad302e14	6f1475e5-0d8a-47c7-855f-ccff802aff85
023426de-c055-4e94-81cd-ead057f617e6	MONDAY	3eb87c06-79ea-4b14-8318-741bad302e14	58845959-0984-4136-a733-b75f5c9057b9
50fd0bbe-274e-45a3-823d-f2ef3c678f7a	TUESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	58845959-0984-4136-a733-b75f5c9057b9
1f0621c3-d33e-40d5-a93c-92e138a6f5f3	WEDNESDAY	3eb87c06-79ea-4b14-8318-741bad302e14	58845959-0984-4136-a733-b75f5c9057b9
e545764b-93e3-454c-8cfc-8efcae8037ce	THURSDAY	3eb87c06-79ea-4b14-8318-741bad302e14	58845959-0984-4136-a733-b75f5c9057b9
4c9f8748-79eb-46b5-a3ea-0ce971af4aa3	FRIDAY	3eb87c06-79ea-4b14-8318-741bad302e14	58845959-0984-4136-a733-b75f5c9057b9
dd8edde5-7a67-4618-9dba-b5826e9316dc	MONDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	440c17ed-5262-4800-aa19-38e5285330c3
dd55c2b6-4a11-4fb1-a803-4b242774e252	TUESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	440c17ed-5262-4800-aa19-38e5285330c3
9871b763-184b-46dc-9fb3-b5f2e30cb4e9	WEDNESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	440c17ed-5262-4800-aa19-38e5285330c3
48451cb0-a0b3-4e4f-b275-fd58a3b8a28f	THURSDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	440c17ed-5262-4800-aa19-38e5285330c3
3911f8e9-4268-423d-bc35-00649da84d39	FRIDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	440c17ed-5262-4800-aa19-38e5285330c3
4a58218e-b92c-4474-a6fc-71ea713f1cc8	MONDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fc3e979c-6941-4140-8aac-1475f2938638	TUESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	5f76a266-8d18-4da8-9732-b63d4d8f24a0
37f1c4d2-91b0-4ebc-9613-72ffca54e7ae	WEDNESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ede77e1d-76e6-40e2-8494-7c57db87d770	THURSDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a870c70d-1ce9-4e55-808a-93cd8ee197ac	FRIDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0ae4767e-53f2-4ae2-997a-0be47f353b7f	MONDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	d54b130a-28a8-4bb1-9030-c474369c2205
2719e5d3-7b46-4fb7-a5ba-7b9bb3b6adbb	TUESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	d54b130a-28a8-4bb1-9030-c474369c2205
25300510-a859-4430-9d72-d772772ce09e	WEDNESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	d54b130a-28a8-4bb1-9030-c474369c2205
73bb55ba-0643-46f7-856e-bec6d941f7b6	THURSDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	d54b130a-28a8-4bb1-9030-c474369c2205
2e7acbde-b92d-4e56-84a2-644e42265fd5	FRIDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	d54b130a-28a8-4bb1-9030-c474369c2205
58d511fd-e96e-4fda-a3f0-95b172191e54	MONDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f33cc9b6-5873-4b3d-9c38-a45d87ddc8af	TUESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c1de5370-ca14-480b-8133-fcb2a52c161e	WEDNESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e38eb533-d380-4be7-8969-4dfbbcb8d383	THURSDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
67ae8dc3-f258-40eb-b8e1-03b5fb86f047	FRIDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2c5a5edc-330f-457b-8719-1da8dce09438	MONDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	c33e01ef-be92-474a-9e43-0543649735d5
49ed464f-e439-448d-af3b-d200473242b4	TUESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	c33e01ef-be92-474a-9e43-0543649735d5
2705eaab-1e93-445f-a858-5fa9e59f5335	WEDNESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	c33e01ef-be92-474a-9e43-0543649735d5
c8fb29b9-bbe3-425d-94b7-cd36c638fc37	THURSDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	c33e01ef-be92-474a-9e43-0543649735d5
c132f7ad-30f6-4d54-8c5c-3a12a0f06050	FRIDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	c33e01ef-be92-474a-9e43-0543649735d5
4c4fbc4b-465d-43f2-af55-b98f2dfcec77	MONDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	6f1475e5-0d8a-47c7-855f-ccff802aff85
29e58b40-d654-4640-b2f6-5478578b6d1b	TUESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	6f1475e5-0d8a-47c7-855f-ccff802aff85
e8333b25-cdd0-4ce0-b4e1-34efbfc58577	WEDNESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	6f1475e5-0d8a-47c7-855f-ccff802aff85
9bcccafe-fc75-4b7f-a300-067848cb2107	THURSDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	6f1475e5-0d8a-47c7-855f-ccff802aff85
27078d35-4d95-4d48-a942-e72a387bfa9d	FRIDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	6f1475e5-0d8a-47c7-855f-ccff802aff85
82e01f57-8567-4b12-8536-217a6a433d0c	MONDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	58845959-0984-4136-a733-b75f5c9057b9
c61385c0-5377-4e3b-abd8-45e257980620	TUESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	58845959-0984-4136-a733-b75f5c9057b9
c65a535e-fc73-4a50-b4ae-aec1013215cd	WEDNESDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	58845959-0984-4136-a733-b75f5c9057b9
848ac8c5-6e10-4d70-8bdf-6710d03d73ab	THURSDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	58845959-0984-4136-a733-b75f5c9057b9
67621823-0764-4538-be9d-f4daffa02993	FRIDAY	d35a424b-d4d1-40a6-aed4-7ba6a1167143	58845959-0984-4136-a733-b75f5c9057b9
e692d521-24fd-4388-8975-7b0dd5666adf	MONDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	440c17ed-5262-4800-aa19-38e5285330c3
2c9e1cfa-22a9-4b18-9e1d-04bd5fb984dd	TUESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	440c17ed-5262-4800-aa19-38e5285330c3
83c79a3b-f3f7-4137-b6a1-10f1aaa1ff7a	WEDNESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	440c17ed-5262-4800-aa19-38e5285330c3
8b4aa82d-ceaa-4c37-8a18-18ad8cfe8c79	THURSDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	440c17ed-5262-4800-aa19-38e5285330c3
473f5584-56fd-4487-87a8-c80a7115f399	FRIDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	440c17ed-5262-4800-aa19-38e5285330c3
f8a6f1f3-133d-41fe-9c2a-6379eaa99dbf	MONDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	5f76a266-8d18-4da8-9732-b63d4d8f24a0
eef7ff3c-3240-4f2d-9b06-c1f7385216b8	TUESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7bff5627-5fd3-407f-b841-dc5e82d47d12	WEDNESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5b0a7693-c6f6-45ad-8172-af9c9c0417a1	THURSDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c2b598e2-f9d6-4449-beb6-f5854ed4d67b	FRIDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d005424d-4c5b-4925-b7b2-5d24174bd805	MONDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	d54b130a-28a8-4bb1-9030-c474369c2205
ae8ea5e7-7898-4161-8a6b-e248ef922a3e	TUESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	d54b130a-28a8-4bb1-9030-c474369c2205
4555a8b4-10ab-4022-8015-58a935b369fe	WEDNESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	d54b130a-28a8-4bb1-9030-c474369c2205
4340977f-fbe1-4043-a717-ee9418649dd3	THURSDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	d54b130a-28a8-4bb1-9030-c474369c2205
504734cb-8702-49ee-acc0-dcf02d890c0e	FRIDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	d54b130a-28a8-4bb1-9030-c474369c2205
23af463d-605c-4ce6-a51f-adf7e05f68f5	MONDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e2793f8a-73bc-4794-9804-7b4c80686c1e	TUESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0ad87840-502f-48ea-8fd6-29b9a9806880	WEDNESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d6cd3c7e-3942-47a8-bebd-7aeb4fb4302c	THURSDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
84389e83-0753-4c85-9af9-7724e7c2798b	FRIDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ef4a539f-76e6-4617-ba57-9e63480b55c5	MONDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	c33e01ef-be92-474a-9e43-0543649735d5
e97f7ace-a4f1-45ae-aedc-50bb5473013c	TUESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	c33e01ef-be92-474a-9e43-0543649735d5
d6e1ea7f-6296-4cf3-8392-cadea08c401b	WEDNESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	c33e01ef-be92-474a-9e43-0543649735d5
d71141d1-b6ec-4e84-a491-19c61e795ffb	THURSDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	c33e01ef-be92-474a-9e43-0543649735d5
8185ae49-66b9-4638-aa9e-a00b04025878	FRIDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	c33e01ef-be92-474a-9e43-0543649735d5
b23fab84-4011-4344-872b-a07b5d7adb1f	MONDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	6f1475e5-0d8a-47c7-855f-ccff802aff85
ffdbfce7-b955-4444-a48e-fb0fceff151d	TUESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	6f1475e5-0d8a-47c7-855f-ccff802aff85
f2e22342-296b-4cb9-b235-4d61eb347c04	WEDNESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	6f1475e5-0d8a-47c7-855f-ccff802aff85
8c7aae07-30ae-459a-b67b-1e1c3c830c35	THURSDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	6f1475e5-0d8a-47c7-855f-ccff802aff85
6fe3740b-5375-42ca-9b39-7d3b4b7a0219	FRIDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	6f1475e5-0d8a-47c7-855f-ccff802aff85
fe8b472f-e741-4b1c-9466-25278fac9468	MONDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	58845959-0984-4136-a733-b75f5c9057b9
bda36eb1-b53c-4bf6-bfe2-9e86f1bad176	TUESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	58845959-0984-4136-a733-b75f5c9057b9
f5e03f59-306b-4fc0-a25e-8b5d043fd447	WEDNESDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	58845959-0984-4136-a733-b75f5c9057b9
3bdfe879-12e8-43db-bfb3-1344b6e4ca2a	THURSDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	58845959-0984-4136-a733-b75f5c9057b9
1b774ff1-47d4-43b0-84a0-e67b28f3c124	FRIDAY	c17fae98-f027-4d30-ad66-e0c859ad6f40	58845959-0984-4136-a733-b75f5c9057b9
408e5386-973e-42b5-8ea7-ecbea420b36b	MONDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	440c17ed-5262-4800-aa19-38e5285330c3
056d6245-7e04-4495-9e71-157d2377fd27	TUESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	440c17ed-5262-4800-aa19-38e5285330c3
76e2e626-eb64-4c01-964d-fb02ec2b97ae	WEDNESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	440c17ed-5262-4800-aa19-38e5285330c3
12b65262-1eee-49c9-a02d-0d66ab20022c	THURSDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	440c17ed-5262-4800-aa19-38e5285330c3
ae061b2f-f41e-4d8d-aaf1-b184d0d33930	FRIDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	440c17ed-5262-4800-aa19-38e5285330c3
66f215bc-8cc9-4865-85bb-2ba02e8b0bee	MONDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1bbb2d72-d3c0-4e32-8652-19e00bbe3fe4	TUESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	5f76a266-8d18-4da8-9732-b63d4d8f24a0
75092a20-3418-4f7a-a2e4-00fe41885e2f	WEDNESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5b1c122f-3393-4aa6-8723-c4217704f0c2	THURSDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4c467878-62fe-40df-b93b-5e77063c3a0d	FRIDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b007240c-2b9e-4e46-acbf-62fc130131a0	MONDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	d54b130a-28a8-4bb1-9030-c474369c2205
54cac8a1-6d17-4558-905e-3c52fed1bc5e	TUESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	d54b130a-28a8-4bb1-9030-c474369c2205
5100ec77-aed0-4e04-b21c-8a3df35f030a	WEDNESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	d54b130a-28a8-4bb1-9030-c474369c2205
b8946b3f-c6ba-455a-bedb-3f3da9329d4c	THURSDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	d54b130a-28a8-4bb1-9030-c474369c2205
e8be769b-2fab-416a-ab70-d4d534a509b2	FRIDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	d54b130a-28a8-4bb1-9030-c474369c2205
3886e83c-8b2e-4930-8abe-fbd899893790	MONDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d52bcc58-8cee-46f7-b58a-d9de40219f33	TUESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
545e7f6b-2d90-42df-8279-5c33414dde8b	WEDNESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
198d8a0a-6b49-4dab-ac7e-5aeed36a244c	THURSDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9428c040-426e-445f-9161-cb5fb3022b1d	FRIDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
df292a8c-0a68-4aec-9223-a525cc945e9e	MONDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	c33e01ef-be92-474a-9e43-0543649735d5
90776677-a7f9-459e-872d-e1a7750b71ea	TUESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	c33e01ef-be92-474a-9e43-0543649735d5
09d89d82-0853-4b58-b8db-28775c5e8b27	WEDNESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	c33e01ef-be92-474a-9e43-0543649735d5
1fb80e26-6712-4ce3-a2b9-f067a9714aac	THURSDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	c33e01ef-be92-474a-9e43-0543649735d5
d18ce704-270a-4941-99c0-493741d3a2f8	FRIDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	c33e01ef-be92-474a-9e43-0543649735d5
a3504ed8-fbca-4421-b98b-57b29f43aeca	MONDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	6f1475e5-0d8a-47c7-855f-ccff802aff85
3e6f6964-a6c5-4a1d-8db6-c06d05462b92	TUESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	6f1475e5-0d8a-47c7-855f-ccff802aff85
ebcd7304-bf13-4431-8298-22de983b81f7	WEDNESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	6f1475e5-0d8a-47c7-855f-ccff802aff85
e0684ede-2bb5-422a-af0f-5852e215d3a9	THURSDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	6f1475e5-0d8a-47c7-855f-ccff802aff85
db9878b5-a94e-4bee-becf-3d7c423bf847	FRIDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	6f1475e5-0d8a-47c7-855f-ccff802aff85
7e73b36f-8815-4e59-8287-a3e49971e6cd	MONDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	58845959-0984-4136-a733-b75f5c9057b9
0978c850-0c84-4da4-976b-40c37943ba3c	TUESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	58845959-0984-4136-a733-b75f5c9057b9
865d5c56-b5bc-442c-8d8d-ba19f9c87747	WEDNESDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	58845959-0984-4136-a733-b75f5c9057b9
f7380d16-625d-4833-8530-c582100bbc38	THURSDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	58845959-0984-4136-a733-b75f5c9057b9
db21533d-32b9-4d53-a6e4-7af5a782a2ab	FRIDAY	52c95b56-9b87-4b69-b51d-41dd2fca8512	58845959-0984-4136-a733-b75f5c9057b9
b69600b2-f1d3-41db-89e9-b51e144cd60a	MONDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	440c17ed-5262-4800-aa19-38e5285330c3
f96e57a4-acd0-412a-8c64-d9b977fb255c	TUESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	440c17ed-5262-4800-aa19-38e5285330c3
392e942c-065e-4a98-9a8f-f5ae8843d021	WEDNESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	440c17ed-5262-4800-aa19-38e5285330c3
5e045cd4-77af-4ed0-98ee-763a809d0400	THURSDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	440c17ed-5262-4800-aa19-38e5285330c3
c1ac261d-b0c9-4b06-801d-5529fdcd5d12	FRIDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	440c17ed-5262-4800-aa19-38e5285330c3
f4db87c6-ea57-4f21-affb-901a300f0eba	MONDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2859efc6-3f62-4cf4-a347-f84d40a24ab9	TUESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7b1320e0-e57c-48b9-a913-43bddd409ef6	WEDNESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
dc8f604d-43a5-4e9e-a5be-45af8ea53500	THURSDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ef1f080d-37e2-4bde-80ef-289904c3fdb8	FRIDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
41b0b9cc-bd2b-45c2-afa9-ee46bc8586b9	MONDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	d54b130a-28a8-4bb1-9030-c474369c2205
dc504e4e-08f8-4ddc-82ab-4a9e49c5c837	TUESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	d54b130a-28a8-4bb1-9030-c474369c2205
3add6794-0dc8-4888-9c2a-4f52e250608f	WEDNESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	d54b130a-28a8-4bb1-9030-c474369c2205
ca8f3f1d-4418-4694-b8f0-ac087be2de42	THURSDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	d54b130a-28a8-4bb1-9030-c474369c2205
8572fc62-641d-47a8-8443-60c87f573e9e	FRIDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	d54b130a-28a8-4bb1-9030-c474369c2205
1d446e52-459f-421e-a73b-8133e5597d7b	MONDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9ed65517-0366-4a58-ac19-be88aa4ea795	TUESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
31bee8d8-f6db-4fe0-9946-2ab7cd9053bd	WEDNESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
fcfb9d09-bda0-4911-8621-0db597c0ef79	THURSDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
423f85ce-1da0-4ff5-baed-2887495e5e75	FRIDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d233acc5-8c52-498f-a109-f4ed4f8f194a	MONDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	c33e01ef-be92-474a-9e43-0543649735d5
2baf12c9-19f9-47d7-a780-73b476e5f6ef	TUESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	c33e01ef-be92-474a-9e43-0543649735d5
70195f96-f0e3-401d-9a13-ba0516f761bb	WEDNESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	c33e01ef-be92-474a-9e43-0543649735d5
cc0246d2-966b-4e6d-8ef6-104fea01e7c3	THURSDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	c33e01ef-be92-474a-9e43-0543649735d5
c145ae5b-a5f1-431d-9dfa-4d496e3d24ca	FRIDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	c33e01ef-be92-474a-9e43-0543649735d5
976a856c-6b1b-4958-a446-82ff083ac1b1	MONDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	6f1475e5-0d8a-47c7-855f-ccff802aff85
a5738a02-a605-485c-839a-7651cc64265e	TUESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	6f1475e5-0d8a-47c7-855f-ccff802aff85
fa9056a2-d41b-42bf-8f71-11f2b98ac704	WEDNESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	6f1475e5-0d8a-47c7-855f-ccff802aff85
3a400ff4-1305-4302-af1e-e59a25161f99	THURSDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	6f1475e5-0d8a-47c7-855f-ccff802aff85
41b45757-84bc-4ee2-86bf-60fdab69051c	FRIDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	6f1475e5-0d8a-47c7-855f-ccff802aff85
5623e920-bf92-4585-95b6-9d9bf0434811	MONDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	58845959-0984-4136-a733-b75f5c9057b9
5fd0a2d0-f842-46d9-89b4-b4ecd83c13fc	TUESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	58845959-0984-4136-a733-b75f5c9057b9
881d52c2-7e6d-4613-9959-e99edbc8c6a6	WEDNESDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	58845959-0984-4136-a733-b75f5c9057b9
0901b7d3-fb1e-45d0-8919-8f0ac77618a2	THURSDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	58845959-0984-4136-a733-b75f5c9057b9
868cacf5-1ad0-40ee-817c-d04ad76a5cc8	FRIDAY	53387f1f-3b3f-4c98-b0b3-ab5479b9fc0f	58845959-0984-4136-a733-b75f5c9057b9
6fa3ed94-210b-48ec-bff6-04f4492877eb	MONDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	440c17ed-5262-4800-aa19-38e5285330c3
7c79d83a-7885-43d2-b43e-61e41073934c	TUESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	440c17ed-5262-4800-aa19-38e5285330c3
af84091e-0e80-49dc-a2c4-9d8b94f06c2b	WEDNESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	440c17ed-5262-4800-aa19-38e5285330c3
44d38c17-a02c-4a94-8f7b-25203f1a2740	THURSDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	440c17ed-5262-4800-aa19-38e5285330c3
00dcb472-7e0d-45d0-a5ee-b82c0f07fd4c	FRIDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	440c17ed-5262-4800-aa19-38e5285330c3
1ec0f3ea-7507-4507-ada2-fa206805724a	MONDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	5f76a266-8d18-4da8-9732-b63d4d8f24a0
64c37255-951d-484e-b328-e6347d045a2c	TUESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f358a699-efe9-4ef8-8193-0c22ed0b5138	WEDNESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0229f936-d2cc-4a17-b7d2-e6f7ec0ac1a4	THURSDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b8ad0690-04a3-41b7-8334-8e85c1ca8884	FRIDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3d658c47-4de6-4a57-9403-9a4a027b95f9	MONDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	d54b130a-28a8-4bb1-9030-c474369c2205
a1c95780-bfb2-412f-bc3e-b391bea38341	TUESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	d54b130a-28a8-4bb1-9030-c474369c2205
d6bb44ee-5758-4f7a-be1f-281bea65432e	WEDNESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	d54b130a-28a8-4bb1-9030-c474369c2205
e4273328-a491-4962-9101-9f96df8c5009	THURSDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	d54b130a-28a8-4bb1-9030-c474369c2205
07e92876-94ae-417e-ba63-1ba4c6406809	FRIDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	d54b130a-28a8-4bb1-9030-c474369c2205
5a0df4ac-d3c6-4cdd-85ef-150ea35d1701	MONDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
177678cb-562e-442c-b9dc-41fb72260470	TUESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7c5ed5c6-8927-4fb3-9918-943b0098046c	WEDNESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1cb0bc92-7862-4530-b1ad-429ab4caa9c5	THURSDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a896f7a2-c3da-4e68-b75d-606942c5bb57	FRIDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ac069e6a-4e35-46db-95ca-f4a4f633d949	MONDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	c33e01ef-be92-474a-9e43-0543649735d5
8ab5446b-d37d-42d7-b5b2-8a02a1e99791	TUESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	c33e01ef-be92-474a-9e43-0543649735d5
2bc03d1b-1968-4940-8984-eee88ce23db4	WEDNESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	c33e01ef-be92-474a-9e43-0543649735d5
1adf45ce-eaa1-42b1-b4cd-a9df20e0cda7	THURSDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	c33e01ef-be92-474a-9e43-0543649735d5
b7c930c7-cfba-47ad-9d28-24f17d5e5438	FRIDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	c33e01ef-be92-474a-9e43-0543649735d5
000c2db9-56d3-40eb-b070-b1c78c37e1e2	MONDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	6f1475e5-0d8a-47c7-855f-ccff802aff85
92c6d2e7-d57c-4259-ab03-8e67dc12502b	TUESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	6f1475e5-0d8a-47c7-855f-ccff802aff85
38228dd2-7fdb-4a3a-bb43-caaea04e4ae3	WEDNESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	6f1475e5-0d8a-47c7-855f-ccff802aff85
7288b0aa-c8b9-4dc2-80b8-102cf37dde77	THURSDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	6f1475e5-0d8a-47c7-855f-ccff802aff85
ac84c39e-83e8-4bf8-8caf-8d96e1e00895	FRIDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	6f1475e5-0d8a-47c7-855f-ccff802aff85
279d195f-fe5f-4dae-a345-9b3fc6e20782	MONDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	58845959-0984-4136-a733-b75f5c9057b9
145b3f93-d5b0-4409-a10e-25acfc7a94e2	TUESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	58845959-0984-4136-a733-b75f5c9057b9
bde0d55c-00ba-464b-b7fb-be62fe38adf6	WEDNESDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	58845959-0984-4136-a733-b75f5c9057b9
123c5e70-8249-43f2-bd09-096056500179	THURSDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	58845959-0984-4136-a733-b75f5c9057b9
adf49c5d-7a40-4799-a87e-91df1f8fd4a0	FRIDAY	642e9ea4-3ca3-4a5f-8ee9-2c8ca2bca745	58845959-0984-4136-a733-b75f5c9057b9
0001f42f-08ed-4099-8f62-9f76c7aae82f	MONDAY	7d89d743-b98b-4151-b76f-0fe067629565	440c17ed-5262-4800-aa19-38e5285330c3
e7b87798-215d-4e57-be45-2b2bbedbebce	TUESDAY	7d89d743-b98b-4151-b76f-0fe067629565	440c17ed-5262-4800-aa19-38e5285330c3
2d8d1a3b-d80a-4996-bb7c-33de69567862	WEDNESDAY	7d89d743-b98b-4151-b76f-0fe067629565	440c17ed-5262-4800-aa19-38e5285330c3
37a8ccc4-584c-4c41-b03c-2f44726f8e78	THURSDAY	7d89d743-b98b-4151-b76f-0fe067629565	440c17ed-5262-4800-aa19-38e5285330c3
8609b4d4-fcbd-401c-b8aa-144140f0415f	FRIDAY	7d89d743-b98b-4151-b76f-0fe067629565	440c17ed-5262-4800-aa19-38e5285330c3
7a11b3d1-2d83-4885-96a1-3d73d50b0a51	MONDAY	7d89d743-b98b-4151-b76f-0fe067629565	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e1a9d8c0-d57a-4ec2-8428-752b3474e25f	TUESDAY	7d89d743-b98b-4151-b76f-0fe067629565	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d8fa6947-94b9-4b70-aeb9-5c413224e110	WEDNESDAY	7d89d743-b98b-4151-b76f-0fe067629565	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c0a87d98-88e3-48ab-bb91-44f34d64ef53	THURSDAY	7d89d743-b98b-4151-b76f-0fe067629565	5f76a266-8d18-4da8-9732-b63d4d8f24a0
77902df8-5579-423d-9209-18d147a7597c	FRIDAY	7d89d743-b98b-4151-b76f-0fe067629565	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cb4be026-f24d-465f-99fe-9fc2812492e9	MONDAY	7d89d743-b98b-4151-b76f-0fe067629565	d54b130a-28a8-4bb1-9030-c474369c2205
4a83bcdf-c88b-4041-9661-3583e9ed70d2	TUESDAY	7d89d743-b98b-4151-b76f-0fe067629565	d54b130a-28a8-4bb1-9030-c474369c2205
1c682610-e6a7-4d98-a7e1-27b2b2b66770	WEDNESDAY	7d89d743-b98b-4151-b76f-0fe067629565	d54b130a-28a8-4bb1-9030-c474369c2205
ab54e96e-ac1d-4de4-b783-c20e96d1800b	THURSDAY	7d89d743-b98b-4151-b76f-0fe067629565	d54b130a-28a8-4bb1-9030-c474369c2205
6ba6e085-2744-473f-b514-23d4f99f728c	FRIDAY	7d89d743-b98b-4151-b76f-0fe067629565	d54b130a-28a8-4bb1-9030-c474369c2205
b75ec602-c1ac-494f-a611-80c8b5fb8298	MONDAY	7d89d743-b98b-4151-b76f-0fe067629565	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8144f317-6a7b-4414-a522-f2bffa2271e4	TUESDAY	7d89d743-b98b-4151-b76f-0fe067629565	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5745f34d-a103-4a67-8fed-ff34bffd202f	WEDNESDAY	7d89d743-b98b-4151-b76f-0fe067629565	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0cfb9330-d784-43cf-9386-25f4ad7ea368	THURSDAY	7d89d743-b98b-4151-b76f-0fe067629565	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a50ddb22-09b7-4b30-b2cd-1fa050f0536e	FRIDAY	7d89d743-b98b-4151-b76f-0fe067629565	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6f99d545-02ce-43dc-afee-b3966cbc2c96	MONDAY	7d89d743-b98b-4151-b76f-0fe067629565	c33e01ef-be92-474a-9e43-0543649735d5
513da8cd-c036-43ec-bafa-0135d7e56865	TUESDAY	7d89d743-b98b-4151-b76f-0fe067629565	c33e01ef-be92-474a-9e43-0543649735d5
18397349-17f0-4d08-bfd5-35af4d0bc537	WEDNESDAY	7d89d743-b98b-4151-b76f-0fe067629565	c33e01ef-be92-474a-9e43-0543649735d5
cb20d898-e1ab-42ad-b878-deadefd41024	THURSDAY	7d89d743-b98b-4151-b76f-0fe067629565	c33e01ef-be92-474a-9e43-0543649735d5
ef18e928-f7e5-4b02-a212-1a6330c36431	FRIDAY	7d89d743-b98b-4151-b76f-0fe067629565	c33e01ef-be92-474a-9e43-0543649735d5
ba470c0e-5d8e-4e85-aa4d-d3dbbf047499	MONDAY	7d89d743-b98b-4151-b76f-0fe067629565	6f1475e5-0d8a-47c7-855f-ccff802aff85
5d89110e-255e-4867-b90f-964c9e555dae	TUESDAY	7d89d743-b98b-4151-b76f-0fe067629565	6f1475e5-0d8a-47c7-855f-ccff802aff85
1eda89b6-7f7f-4529-b0ae-18301061337f	WEDNESDAY	7d89d743-b98b-4151-b76f-0fe067629565	6f1475e5-0d8a-47c7-855f-ccff802aff85
ce5ae67f-7ecf-4c3a-9fbc-5b5553d6b2c1	THURSDAY	7d89d743-b98b-4151-b76f-0fe067629565	6f1475e5-0d8a-47c7-855f-ccff802aff85
a55ecf0e-0eed-4c9c-86a5-0feba3a75ef8	FRIDAY	7d89d743-b98b-4151-b76f-0fe067629565	6f1475e5-0d8a-47c7-855f-ccff802aff85
4a0ff61b-6622-4b50-9ed1-394d29407f06	MONDAY	7d89d743-b98b-4151-b76f-0fe067629565	58845959-0984-4136-a733-b75f5c9057b9
53e016fa-83e3-4cea-8c37-b9b7d4a6c5e2	TUESDAY	7d89d743-b98b-4151-b76f-0fe067629565	58845959-0984-4136-a733-b75f5c9057b9
8d3e0811-4a62-4e88-99a3-77129cd5f1ae	WEDNESDAY	7d89d743-b98b-4151-b76f-0fe067629565	58845959-0984-4136-a733-b75f5c9057b9
469fbbfa-1361-4415-8504-ed171d5e9e7c	THURSDAY	7d89d743-b98b-4151-b76f-0fe067629565	58845959-0984-4136-a733-b75f5c9057b9
4e2c5ed3-c072-44a6-8143-592325d871f4	MONDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	440c17ed-5262-4800-aa19-38e5285330c3
08a18f7b-8b2b-4ff9-a61f-8d7e5df94554	TUESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	440c17ed-5262-4800-aa19-38e5285330c3
db0f1504-9b1b-40d1-9ade-1f9008e17506	WEDNESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	440c17ed-5262-4800-aa19-38e5285330c3
a1c2585b-2717-45c2-ac13-5a16b7070c12	THURSDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	440c17ed-5262-4800-aa19-38e5285330c3
37c9842d-7e0f-49cb-9e7f-608e47e01b9a	FRIDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	440c17ed-5262-4800-aa19-38e5285330c3
d004ec38-cb72-4e1a-99ef-5ffe72cc8ab6	MONDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	5f76a266-8d18-4da8-9732-b63d4d8f24a0
92b57d22-14be-4f71-9cda-2680d3917087	TUESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	5f76a266-8d18-4da8-9732-b63d4d8f24a0
688705f5-4d72-47e9-91aa-9406e4b2e15a	WEDNESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0f835f8b-6afc-4c5e-ae11-38306275f901	THURSDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0b7a5c29-e6d2-42f2-ba69-7c7d42acbeab	FRIDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a1cc7c8a-4ef7-48ee-bae1-4bfe86717549	MONDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	d54b130a-28a8-4bb1-9030-c474369c2205
71a5e686-9e4f-4cf1-834d-f76c06a61b16	TUESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	d54b130a-28a8-4bb1-9030-c474369c2205
824e53c0-70fa-494e-882b-e4b9d0516c70	WEDNESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	d54b130a-28a8-4bb1-9030-c474369c2205
1e30cd6d-478e-460a-98fa-1b19a41c81fe	THURSDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	d54b130a-28a8-4bb1-9030-c474369c2205
3fbce508-fca1-469e-9c5b-c23a96f53332	FRIDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	d54b130a-28a8-4bb1-9030-c474369c2205
eb367ea3-b385-4374-aa36-e8d9efad01f8	MONDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
52a2a820-ca09-45c0-9285-508bc591aba8	TUESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
04b25609-225f-4399-b04e-553f7e9deb1e	WEDNESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e38cb8e8-e357-405d-8ba3-f38245d529a8	THURSDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
95365b74-cac9-43b2-bf5c-066316311876	FRIDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e3bc931d-6a32-4440-802f-86ce68d8adf5	MONDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	c33e01ef-be92-474a-9e43-0543649735d5
b54f920a-8507-41c0-b97b-8699c4178aeb	TUESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	c33e01ef-be92-474a-9e43-0543649735d5
8d3a77ee-5288-4539-80d9-4b0e29d33d6a	WEDNESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	c33e01ef-be92-474a-9e43-0543649735d5
3bb392a0-ac05-4b06-8d89-b67bf38ee8bf	THURSDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	c33e01ef-be92-474a-9e43-0543649735d5
3192aab7-e23b-4dd6-894d-50421b7a7e82	FRIDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	c33e01ef-be92-474a-9e43-0543649735d5
42bc2c51-4f49-45d4-a50d-4eb846a1364f	MONDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	6f1475e5-0d8a-47c7-855f-ccff802aff85
9987478c-3598-4c7a-9536-a3065ea9f186	TUESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	6f1475e5-0d8a-47c7-855f-ccff802aff85
4a7b534b-d7de-4de5-8d9b-0f44a13b7e55	WEDNESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	6f1475e5-0d8a-47c7-855f-ccff802aff85
b27b919b-71ab-4436-949d-9a74945cab7c	THURSDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	6f1475e5-0d8a-47c7-855f-ccff802aff85
6229ab19-2fa1-43a1-97ed-34265ce8424a	FRIDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	6f1475e5-0d8a-47c7-855f-ccff802aff85
09dc976f-02d5-4d1b-b1d3-f7968ccf81d9	MONDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	58845959-0984-4136-a733-b75f5c9057b9
ee8413cc-7986-4dd2-82c1-812da9484b37	TUESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	58845959-0984-4136-a733-b75f5c9057b9
e2631843-a2ba-497c-a4fd-4cdb650cb632	WEDNESDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	58845959-0984-4136-a733-b75f5c9057b9
d6a50fd2-2af4-435d-8540-45787c65bf04	THURSDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	58845959-0984-4136-a733-b75f5c9057b9
52ff0822-0586-4aac-9479-3dbb8a57748a	FRIDAY	e84b7f27-2a5c-459e-ba44-b9808e798161	58845959-0984-4136-a733-b75f5c9057b9
fd44fc58-3aa1-4492-bd32-201331d24952	MONDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	440c17ed-5262-4800-aa19-38e5285330c3
5a4208c8-fa83-4412-9b36-0df648044fa3	TUESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	440c17ed-5262-4800-aa19-38e5285330c3
1851ea2c-11cd-4510-9486-4b67f1c823de	WEDNESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	440c17ed-5262-4800-aa19-38e5285330c3
809793da-9fd4-49ed-9ce9-f736dc410d88	THURSDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	440c17ed-5262-4800-aa19-38e5285330c3
0dc1d8c3-d965-4bd4-9759-e85f4c27b4fd	FRIDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	440c17ed-5262-4800-aa19-38e5285330c3
c429ed5b-0785-442c-92ab-3c90585eee7a	MONDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
edf91a7a-d832-44b1-861b-a7bc3690c5b6	TUESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
72bd4f51-d427-4868-8364-5f7746a9dedd	WEDNESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
127f3b85-62de-4626-92f6-a7179ee735bb	THURSDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c45ef4e1-c314-47a8-ab95-238a87103091	FRIDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fdba2845-b793-41f6-901d-6925bab5239e	MONDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	d54b130a-28a8-4bb1-9030-c474369c2205
494b7fc5-c68e-4342-b8df-1d9bfec1792c	TUESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	d54b130a-28a8-4bb1-9030-c474369c2205
b2f82ffc-808a-4761-a16b-7393bb7c3817	WEDNESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	d54b130a-28a8-4bb1-9030-c474369c2205
eb216da3-2cb8-4db3-a1ff-72778d949569	THURSDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	d54b130a-28a8-4bb1-9030-c474369c2205
af69a250-04b5-4791-a92b-d1b17016aeea	FRIDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	d54b130a-28a8-4bb1-9030-c474369c2205
f6054314-bbfe-4343-8c74-a2aab94baeb5	MONDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b1a85110-7a44-4a78-8349-caa22f42b27e	TUESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8aa28ddb-5ec6-43e8-aa8a-857e91ad8a95	WEDNESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bbea6350-ec4d-4360-9d66-f6f6bcb82b3b	THURSDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d1f31ec2-477c-46cb-bb9c-bb3cc2605d39	FRIDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3fd5a487-af30-47c4-a4f1-a0a7df898d94	MONDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	c33e01ef-be92-474a-9e43-0543649735d5
c2e7182f-bea7-4667-a5c3-12e8ab5a6e76	TUESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	c33e01ef-be92-474a-9e43-0543649735d5
f6348e57-fe0a-4763-ab5d-ae352a7aa549	WEDNESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	c33e01ef-be92-474a-9e43-0543649735d5
4cf4e825-430b-4145-8ac1-ce5dcd05ed0e	THURSDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	c33e01ef-be92-474a-9e43-0543649735d5
cbf43e8f-913f-4d8b-a794-1892bb9a2a13	FRIDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	c33e01ef-be92-474a-9e43-0543649735d5
7f791b9d-33d1-40e4-96ec-ba1649a42745	MONDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	6f1475e5-0d8a-47c7-855f-ccff802aff85
37f0ab74-8682-4182-aa9e-08e098bef479	TUESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	6f1475e5-0d8a-47c7-855f-ccff802aff85
e032c395-ad7a-4595-b629-f007a3d2cef6	WEDNESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	6f1475e5-0d8a-47c7-855f-ccff802aff85
7565113f-e2c9-4325-8434-7d065a89bcfd	THURSDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	6f1475e5-0d8a-47c7-855f-ccff802aff85
a0c0fa40-a193-4550-99f2-2e12ca41db30	FRIDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	6f1475e5-0d8a-47c7-855f-ccff802aff85
acd75f0e-d349-4e2d-8a6b-13c9982841cb	MONDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	58845959-0984-4136-a733-b75f5c9057b9
1103876e-d92b-4e12-b833-ed0994d12ee8	TUESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	58845959-0984-4136-a733-b75f5c9057b9
05b04508-fa49-49f3-887e-55cf6023c60b	WEDNESDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	58845959-0984-4136-a733-b75f5c9057b9
62c59b6a-aca1-42c7-9b10-67234721bc1e	THURSDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	58845959-0984-4136-a733-b75f5c9057b9
b9b17a7b-18da-46fd-9a26-d5b0e88d79af	FRIDAY	cc6d53ba-ff98-44ca-b3dc-ee21d55d183d	58845959-0984-4136-a733-b75f5c9057b9
d467f7b9-a567-42d9-bf34-354109fc2f5b	MONDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b1fda78d-47e4-4144-b669-e23b51d7c210	TUESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	5f76a266-8d18-4da8-9732-b63d4d8f24a0
57042271-da34-46e2-a867-85c0fe3b0d9d	WEDNESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	5f76a266-8d18-4da8-9732-b63d4d8f24a0
62524fc8-c1d3-4ab0-af5c-d1832af6514d	THURSDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	5f76a266-8d18-4da8-9732-b63d4d8f24a0
5cd4621e-4fe0-49a3-8470-8977e63603f2	FRIDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	5f76a266-8d18-4da8-9732-b63d4d8f24a0
27f140c1-aa15-47ad-ae7e-b1b2dc9d4d7e	MONDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	d54b130a-28a8-4bb1-9030-c474369c2205
9d504e84-bc8d-4b8b-903c-fb813c512a58	TUESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	d54b130a-28a8-4bb1-9030-c474369c2205
4bc602e5-b468-4641-af36-ec7c00303e82	WEDNESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	d54b130a-28a8-4bb1-9030-c474369c2205
29748839-967e-46ae-a85b-f3a90db7f56e	THURSDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	d54b130a-28a8-4bb1-9030-c474369c2205
bd047b7b-8e20-443d-a2c0-c6a2409a56ac	FRIDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	d54b130a-28a8-4bb1-9030-c474369c2205
d7f2e051-663a-4a24-9344-526e0a1090bf	MONDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7060c567-1194-4c64-a3c7-3256bb9c945c	TUESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
172b5092-f85c-42da-a2f6-3081a33e6083	WEDNESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b9fd98a8-b5c8-4d97-a650-399c4e05a5a9	THURSDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ac0191e4-cbb1-444a-95d6-bb6d2b7aedd6	FRIDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3d1b2fb8-e242-4420-98f5-725d775ae29a	MONDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	c33e01ef-be92-474a-9e43-0543649735d5
021476d8-3bcc-419c-ab74-8e0a2a5e38c4	TUESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	c33e01ef-be92-474a-9e43-0543649735d5
47e13a17-fcc4-4d71-9916-8578acecc1ce	WEDNESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	c33e01ef-be92-474a-9e43-0543649735d5
068bc861-b151-49bb-a6df-1583aaa908de	THURSDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	c33e01ef-be92-474a-9e43-0543649735d5
eefbe5fc-5ae5-40ae-8953-a1ebe77a9648	FRIDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	c33e01ef-be92-474a-9e43-0543649735d5
7b9469ef-0e04-45ed-953e-0599a745bc15	MONDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	6f1475e5-0d8a-47c7-855f-ccff802aff85
95875f88-1b60-4826-9716-8bc83f802d7b	TUESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	6f1475e5-0d8a-47c7-855f-ccff802aff85
28ea4e1e-8f39-4adf-b8c1-188c944c3389	WEDNESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	6f1475e5-0d8a-47c7-855f-ccff802aff85
051a5929-b366-46d1-8737-57b8d314a3ed	FRIDAY	7d89d743-b98b-4151-b76f-0fe067629565	58845959-0984-4136-a733-b75f5c9057b9
c3ad88ba-261a-49ef-8fd1-68edeb24e008	MONDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	440c17ed-5262-4800-aa19-38e5285330c3
d24f941e-16a0-4d32-bbea-03af26e66586	TUESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	440c17ed-5262-4800-aa19-38e5285330c3
2569e7b2-e0fa-47e3-87d0-12946d66207e	WEDNESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	440c17ed-5262-4800-aa19-38e5285330c3
26f4db0b-f047-46f4-b010-137e330eda47	THURSDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	440c17ed-5262-4800-aa19-38e5285330c3
3650e671-e609-4534-b2d7-ee6882eb0afd	FRIDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	440c17ed-5262-4800-aa19-38e5285330c3
f1024f2b-5a38-4ece-9298-7d2bcb73532f	MONDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3adebfba-d0d6-4d60-82aa-294e4be959fc	TUESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3bcc1bcc-9eec-4ac1-84eb-57d43ed833d0	WEDNESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
08b14113-1e6a-480b-9b43-1f4a9eaebaeb	THURSDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
04c7d098-2039-49ed-9284-7d5dda6a50da	FRIDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bb7a2839-6c71-4620-a225-710d986b24ea	MONDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	d54b130a-28a8-4bb1-9030-c474369c2205
a0b985a6-620c-4776-9429-13ef8f03290a	TUESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	d54b130a-28a8-4bb1-9030-c474369c2205
49707de9-8469-44aa-90c8-df70e80b9e7f	WEDNESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	d54b130a-28a8-4bb1-9030-c474369c2205
e4b11142-cdc6-424a-8b36-4a29fbedc7d0	THURSDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	d54b130a-28a8-4bb1-9030-c474369c2205
6bbcd331-9b8a-4cb8-952c-cd117c9a4e72	FRIDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	d54b130a-28a8-4bb1-9030-c474369c2205
00b0aac8-f94b-4940-b120-9ebb6013988a	MONDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9dd446df-a98d-4967-a912-c3ff1a1856fa	TUESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c7059190-8fa5-49dc-9078-c859e1394f43	WEDNESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cc357adb-cd10-441f-91d5-5dba97ca8f05	THURSDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ebbf0f61-fec8-4f69-b12e-7cfb559b8dd5	FRIDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ff615506-27b0-43f8-b1a3-25708747553c	MONDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	c33e01ef-be92-474a-9e43-0543649735d5
2909e26d-a720-4aa5-96e9-32182c31a8b2	TUESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	c33e01ef-be92-474a-9e43-0543649735d5
65d9f691-6759-440f-a463-967d58b23577	WEDNESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	c33e01ef-be92-474a-9e43-0543649735d5
ca757d40-66d8-495a-9c6b-56a6b79dfe87	THURSDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	c33e01ef-be92-474a-9e43-0543649735d5
284cac75-25bb-4882-9cbd-dbdbdf369ef9	FRIDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	c33e01ef-be92-474a-9e43-0543649735d5
80d97ca0-ffcc-4439-af46-1f425a510d1b	MONDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	6f1475e5-0d8a-47c7-855f-ccff802aff85
4a014923-aace-45d4-b70f-582423291535	TUESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	6f1475e5-0d8a-47c7-855f-ccff802aff85
788a8173-3c81-4129-b0eb-ef846368314a	WEDNESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	6f1475e5-0d8a-47c7-855f-ccff802aff85
1f52f1ac-b503-4bc3-abd0-046842596d84	THURSDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	6f1475e5-0d8a-47c7-855f-ccff802aff85
c39bfc89-b9b6-4e52-aa9d-d8176208293a	FRIDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	6f1475e5-0d8a-47c7-855f-ccff802aff85
872ed0df-1eb1-4e3d-9b9a-1a32cdd3b298	MONDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	58845959-0984-4136-a733-b75f5c9057b9
eb4ac452-406f-4bef-91b0-0a11eb55069a	TUESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	58845959-0984-4136-a733-b75f5c9057b9
babf8b03-9ae5-412d-8a94-40bbe7d7b7d1	WEDNESDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	58845959-0984-4136-a733-b75f5c9057b9
42dec9c6-5fa0-4022-b1c5-d84f1ed432b2	THURSDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	58845959-0984-4136-a733-b75f5c9057b9
a8d2bbd6-fe0b-4d7d-be4b-02cd42f63dc1	FRIDAY	8ae7cc5f-3abe-4e24-964e-5c5d10c2790c	58845959-0984-4136-a733-b75f5c9057b9
7cd9114e-9a19-4856-bd6e-f91f27896ce3	MONDAY	8ba32227-a15a-4368-86ee-5dbee74385af	440c17ed-5262-4800-aa19-38e5285330c3
129cd3b8-9091-481d-afcb-ed0f5ec00d26	TUESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	440c17ed-5262-4800-aa19-38e5285330c3
3336b53c-a7c7-4203-a26a-dd35be857fef	WEDNESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	440c17ed-5262-4800-aa19-38e5285330c3
2c30d80d-b091-46a0-953c-18a78ab3531c	THURSDAY	8ba32227-a15a-4368-86ee-5dbee74385af	440c17ed-5262-4800-aa19-38e5285330c3
cfb0efd7-8ab8-4be9-9ee2-a58af3496001	FRIDAY	8ba32227-a15a-4368-86ee-5dbee74385af	440c17ed-5262-4800-aa19-38e5285330c3
16081986-bb80-4d60-95aa-38cb2559d32f	MONDAY	8ba32227-a15a-4368-86ee-5dbee74385af	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0c81ca69-0c89-4991-a1bd-4510cd7e0eec	TUESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2866cc5e-2798-43e3-96e9-9fda6ac90a4c	WEDNESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8121da20-4dc8-4188-8ae7-00e69aa27a1c	THURSDAY	8ba32227-a15a-4368-86ee-5dbee74385af	5f76a266-8d18-4da8-9732-b63d4d8f24a0
51823159-ca35-4b5b-b492-60c2e6c76c8f	FRIDAY	8ba32227-a15a-4368-86ee-5dbee74385af	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b4d7e4ee-55a0-4441-bc4b-25dadf5d3584	MONDAY	8ba32227-a15a-4368-86ee-5dbee74385af	d54b130a-28a8-4bb1-9030-c474369c2205
0e332d26-49b9-4c75-aebb-f05bb48ae9c5	TUESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	d54b130a-28a8-4bb1-9030-c474369c2205
25536f0d-f0a6-465e-bb24-4efb5dd8489a	WEDNESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	d54b130a-28a8-4bb1-9030-c474369c2205
0b310eb4-5430-495a-8df1-dc15450cc37e	THURSDAY	8ba32227-a15a-4368-86ee-5dbee74385af	d54b130a-28a8-4bb1-9030-c474369c2205
811e0847-1231-4c0c-912b-1a797c17c76a	FRIDAY	8ba32227-a15a-4368-86ee-5dbee74385af	d54b130a-28a8-4bb1-9030-c474369c2205
ee0d2813-4bbf-4b28-86ea-63cb92a6ef03	MONDAY	8ba32227-a15a-4368-86ee-5dbee74385af	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f6ef659c-1664-4a6a-9245-ece8090a0591	TUESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
af3daf73-7f5f-4c3f-92fb-20955fdeb1dc	WEDNESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0983b971-5ae9-4286-b07f-62ffab0d41e0	THURSDAY	8ba32227-a15a-4368-86ee-5dbee74385af	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6fd5d804-301f-4076-95af-17e59897557b	FRIDAY	8ba32227-a15a-4368-86ee-5dbee74385af	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e6a56e4a-fac5-46ce-8dcc-de8cfde278b7	MONDAY	8ba32227-a15a-4368-86ee-5dbee74385af	c33e01ef-be92-474a-9e43-0543649735d5
fbc90209-bc0c-4fb4-a137-c40057ff6a4c	TUESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	c33e01ef-be92-474a-9e43-0543649735d5
a9defcb6-9545-4479-9fbb-b6bbdbb24e05	WEDNESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	c33e01ef-be92-474a-9e43-0543649735d5
13d3c069-1e8d-4d24-8e31-b8440ab15ab4	THURSDAY	8ba32227-a15a-4368-86ee-5dbee74385af	c33e01ef-be92-474a-9e43-0543649735d5
7e70affc-4627-41fe-979d-44f1837d2caa	FRIDAY	8ba32227-a15a-4368-86ee-5dbee74385af	c33e01ef-be92-474a-9e43-0543649735d5
6f3040ab-c6b6-4577-a583-ce0a85bf5379	MONDAY	8ba32227-a15a-4368-86ee-5dbee74385af	6f1475e5-0d8a-47c7-855f-ccff802aff85
5415a556-aa71-4032-a3ef-7466ee406cbf	TUESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	6f1475e5-0d8a-47c7-855f-ccff802aff85
fe7fffb4-4232-4e02-9e2b-c9e387ee9db9	WEDNESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	6f1475e5-0d8a-47c7-855f-ccff802aff85
5d043d7b-e13e-433a-81b0-f96db5880a7f	THURSDAY	8ba32227-a15a-4368-86ee-5dbee74385af	6f1475e5-0d8a-47c7-855f-ccff802aff85
9e9b15c3-8155-4b1d-a0d8-98df61b351e0	FRIDAY	8ba32227-a15a-4368-86ee-5dbee74385af	6f1475e5-0d8a-47c7-855f-ccff802aff85
10b1ca2e-62a6-400f-bcbc-b99c1915f24b	MONDAY	8ba32227-a15a-4368-86ee-5dbee74385af	58845959-0984-4136-a733-b75f5c9057b9
24e6b018-006c-4333-956f-06d28aa7675b	TUESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	58845959-0984-4136-a733-b75f5c9057b9
9197b295-ba48-48bd-b926-51fac2347950	WEDNESDAY	8ba32227-a15a-4368-86ee-5dbee74385af	58845959-0984-4136-a733-b75f5c9057b9
f01eca92-a4f9-422d-94f8-f863730416ff	THURSDAY	8ba32227-a15a-4368-86ee-5dbee74385af	58845959-0984-4136-a733-b75f5c9057b9
adb7c032-ff75-4799-b4e6-ce9436285648	FRIDAY	8ba32227-a15a-4368-86ee-5dbee74385af	58845959-0984-4136-a733-b75f5c9057b9
9259ddc1-da9f-48ea-998f-295ac2245f0f	MONDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	440c17ed-5262-4800-aa19-38e5285330c3
eb3d933a-aaaf-41f3-8586-b751338cd7ef	TUESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	440c17ed-5262-4800-aa19-38e5285330c3
54a00d58-b1a3-47d6-82c2-50064e3a6112	WEDNESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	440c17ed-5262-4800-aa19-38e5285330c3
b7aecee4-99a4-42e6-9d86-d77727a50569	THURSDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	440c17ed-5262-4800-aa19-38e5285330c3
f19dfc63-65e0-46a0-9b18-62b34e1b67c1	FRIDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	440c17ed-5262-4800-aa19-38e5285330c3
9112cfb4-b934-48a6-a56f-e7ecdef93d6f	MONDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c3ee69e8-891c-4c5c-9379-f9e1686ec526	TUESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bd27c23e-cbe4-4f70-8b77-7dc3b0112399	WEDNESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
6b4218d8-ad6b-4984-be9a-2709a3f7bfe4	THURSDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
22d5d10b-9243-4dee-b995-d5e6dc9b7bb8	FRIDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
530fadb6-952b-452d-ad6b-097489af6172	MONDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	d54b130a-28a8-4bb1-9030-c474369c2205
e0f7a80e-1928-4fa7-899f-a533486da38b	TUESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	d54b130a-28a8-4bb1-9030-c474369c2205
04afb3ae-ba6d-497e-b0e5-3aae3cc06ad6	WEDNESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	d54b130a-28a8-4bb1-9030-c474369c2205
036207f9-49b7-44bf-a7f8-ce4d0a3cf453	THURSDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	d54b130a-28a8-4bb1-9030-c474369c2205
69a6a750-a570-473d-b024-c162c386f5f1	FRIDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	d54b130a-28a8-4bb1-9030-c474369c2205
8d011550-95b4-46f5-b6ca-9974f7c155cc	MONDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f82c9066-071a-4cda-b599-d9d0783d124d	TUESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6d5c29aa-895b-4981-828d-806a8b36eeac	WEDNESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bc6f82e7-52f8-437f-990f-289ea0d8fa35	THURSDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
22cd1a3a-7d3b-40be-bcae-8bf5a9410d64	FRIDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
af0f1112-87ea-4257-9189-0461ff8ae915	MONDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	c33e01ef-be92-474a-9e43-0543649735d5
22487c10-15bb-464f-8957-8dc4b69c489e	TUESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	c33e01ef-be92-474a-9e43-0543649735d5
de7a2b40-8b50-45c3-a096-e95345d0ef86	WEDNESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	c33e01ef-be92-474a-9e43-0543649735d5
b8414e67-da4d-4151-86fc-4329520cb002	THURSDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	c33e01ef-be92-474a-9e43-0543649735d5
aaa8ad31-66e6-4a72-a4a9-5744ac7630e7	FRIDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	c33e01ef-be92-474a-9e43-0543649735d5
6834b13a-4d9a-4b88-b3ab-795f8aa59242	MONDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	6f1475e5-0d8a-47c7-855f-ccff802aff85
1e8030b5-8e4b-44fc-816c-51d4411d5696	TUESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	6f1475e5-0d8a-47c7-855f-ccff802aff85
dd32cd35-ae80-4c59-b6f9-718573d67471	WEDNESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	6f1475e5-0d8a-47c7-855f-ccff802aff85
051622fc-3b55-44e7-bb61-caa1fb731126	THURSDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	6f1475e5-0d8a-47c7-855f-ccff802aff85
b415f102-93bd-4ba1-bc19-d4a38da73794	FRIDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	6f1475e5-0d8a-47c7-855f-ccff802aff85
fb60d752-f6e3-4002-851b-12304a2581bc	MONDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	58845959-0984-4136-a733-b75f5c9057b9
9f7adbf2-ee3a-4bf4-b6c8-97ac92939936	TUESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	58845959-0984-4136-a733-b75f5c9057b9
8207b01e-4ad8-4bb8-adb6-0ecfbbc6e6ba	WEDNESDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	58845959-0984-4136-a733-b75f5c9057b9
ff72cce8-4a8b-4276-b65f-b04cb9ec9cfe	THURSDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	58845959-0984-4136-a733-b75f5c9057b9
0f69337c-faa1-4e02-b22b-33a3aa64fcfc	FRIDAY	36685604-58cb-4df6-b19a-c4efb2ef3fe7	58845959-0984-4136-a733-b75f5c9057b9
0dd3243e-1a8a-41fa-9b78-eb6e975f7023	MONDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	440c17ed-5262-4800-aa19-38e5285330c3
0bbb87e8-a6cd-4464-84aa-119d30bb6166	TUESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	440c17ed-5262-4800-aa19-38e5285330c3
627c2a9d-c05c-4cfd-bfe6-9e072d076007	WEDNESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	440c17ed-5262-4800-aa19-38e5285330c3
7024870d-df12-4e89-aad6-bb6cd98ff51a	THURSDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	440c17ed-5262-4800-aa19-38e5285330c3
5d14f0db-7e1f-465e-934c-863dfb833b7a	FRIDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	440c17ed-5262-4800-aa19-38e5285330c3
07a4d275-082b-498f-a4d3-e3a8ee2d91d5	MONDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
88fb9637-8a5e-44e0-9fbd-11e9ff3cb88f	TUESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a6ce26de-fd89-4662-994d-37ee25d3858b	WEDNESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
46936b8f-7e2a-4aea-b3cc-3ff1a5c2f912	THURSDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b6db74a2-7046-4813-b7b0-06babc1d454d	FRIDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a396b822-a7fc-4f7c-9e03-19622a750d88	MONDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	d54b130a-28a8-4bb1-9030-c474369c2205
9bc9cbbe-c26e-4044-b3bb-dd3d7d432dbc	TUESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	d54b130a-28a8-4bb1-9030-c474369c2205
f060027f-1b05-437f-bd9d-b511c2cb6b1a	WEDNESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	d54b130a-28a8-4bb1-9030-c474369c2205
eeea6f55-ba5e-428d-a52a-e3a9a39a2a82	THURSDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	d54b130a-28a8-4bb1-9030-c474369c2205
4989d812-a43d-4480-b135-f0d9da553fd3	FRIDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	d54b130a-28a8-4bb1-9030-c474369c2205
d96f10b5-032f-4db8-9705-4524971f6df3	MONDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ad73ca58-5695-445f-89e1-51a24ca1c962	TUESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6e05357c-ca47-4409-8342-75f1c9cfdafb	WEDNESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a05756fd-e160-4c66-8f18-84e83f6340dd	THURSDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d5c524b0-a93a-4e76-aa4b-f0e933c6e8ac	FRIDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
94c6e409-e788-4eb6-baf2-ab5e0e424285	MONDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	c33e01ef-be92-474a-9e43-0543649735d5
6ba67087-71b0-483c-9f6e-6171ae0839fd	TUESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	c33e01ef-be92-474a-9e43-0543649735d5
7056a1ca-839b-466d-8349-6c78d4938bf0	WEDNESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	c33e01ef-be92-474a-9e43-0543649735d5
773d2aaa-50bb-4431-8c0f-85cf1fe05a68	THURSDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	c33e01ef-be92-474a-9e43-0543649735d5
696ce65a-b72c-480b-981b-c817e03e74df	FRIDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	c33e01ef-be92-474a-9e43-0543649735d5
bc8e06c4-6aa6-4062-9a20-d0cf2ee9d37f	MONDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	6f1475e5-0d8a-47c7-855f-ccff802aff85
d3966c14-4b1d-40c1-b7e7-074140adf5dd	TUESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	6f1475e5-0d8a-47c7-855f-ccff802aff85
606a9739-9144-4f51-b58e-df043052b183	WEDNESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	6f1475e5-0d8a-47c7-855f-ccff802aff85
7ca0980b-9624-4d19-82ab-15f82cdf836e	THURSDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	6f1475e5-0d8a-47c7-855f-ccff802aff85
133bfa61-64ff-4bd8-b5ae-5624f515fa7b	FRIDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	6f1475e5-0d8a-47c7-855f-ccff802aff85
3687b373-442a-40e8-9025-9f3d8b88b759	MONDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	58845959-0984-4136-a733-b75f5c9057b9
0c63d224-064c-4b5e-b6c1-8421232385a5	TUESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	58845959-0984-4136-a733-b75f5c9057b9
75129647-00f2-41ee-8cec-2f0f7db843c8	WEDNESDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	58845959-0984-4136-a733-b75f5c9057b9
044a4785-fd5a-4c89-bf8e-3d9c74c47402	THURSDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	58845959-0984-4136-a733-b75f5c9057b9
246825e7-d659-427d-9629-b2d2c8cdca47	FRIDAY	d231a1a5-763b-4332-8c1b-d4353e932af3	58845959-0984-4136-a733-b75f5c9057b9
1e0e881e-2e28-4ee7-a77b-dd56fc38a285	MONDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	440c17ed-5262-4800-aa19-38e5285330c3
eed13225-0c28-47c1-900b-b00cdb54f700	TUESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	440c17ed-5262-4800-aa19-38e5285330c3
ac31b8f7-b63a-45af-9b3d-dda437f0fe74	WEDNESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	440c17ed-5262-4800-aa19-38e5285330c3
6d6a75bb-c898-4f19-9e89-765e8b5c94f5	THURSDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	440c17ed-5262-4800-aa19-38e5285330c3
2b7b10d2-d39b-408f-b13c-3c2876b5693a	FRIDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	440c17ed-5262-4800-aa19-38e5285330c3
24dfef2d-a9b7-40f1-b517-51dd99ee6a88	MONDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a56b3ac6-e843-43c6-b948-6d3a9ac18371	TUESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
58f58c69-0647-4b6a-9729-be16d0b00720	WEDNESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f2164aa1-fc64-4dfd-897b-635ec67b2cd3	THURSDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f2f5d1b3-2123-4868-a4e7-341918e84f6d	FRIDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b170e6db-1d7d-441d-807d-4ca5e4f0db11	MONDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	d54b130a-28a8-4bb1-9030-c474369c2205
032f0408-29fe-421a-a28b-66cbf6ecf5df	TUESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	d54b130a-28a8-4bb1-9030-c474369c2205
8097ff82-4b24-443c-abb7-9c15c7bfd503	WEDNESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	d54b130a-28a8-4bb1-9030-c474369c2205
6b98fcc0-9254-4f36-baa3-3eec8ba52b7d	THURSDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	d54b130a-28a8-4bb1-9030-c474369c2205
7db21764-e23e-493b-a595-de720a398ea6	FRIDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	d54b130a-28a8-4bb1-9030-c474369c2205
7d796cec-bed5-4559-a867-51b821185448	MONDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
66a6db0f-49e6-48d4-be4d-430a81c72028	TUESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6767d61e-6c72-4c43-a280-3e21d0eaa570	WEDNESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1df3b3ea-a838-41ca-b643-4606659074ab	THURSDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
66cf7354-5c63-4f39-b39f-5e4d4efbe9f3	FRIDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
72e1eba8-145d-450c-9340-a314b9abf375	MONDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	c33e01ef-be92-474a-9e43-0543649735d5
54e9624e-9093-4ec6-911c-af69d06319df	TUESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	c33e01ef-be92-474a-9e43-0543649735d5
7b2632f6-774c-49d3-819d-219a9bf4fb01	WEDNESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	c33e01ef-be92-474a-9e43-0543649735d5
6365752d-b239-4600-bca3-6d71f74baaa1	THURSDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	c33e01ef-be92-474a-9e43-0543649735d5
7976db5e-b7ab-4161-99d4-21f274130a4b	FRIDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	c33e01ef-be92-474a-9e43-0543649735d5
7b2d8e93-decb-4745-a657-681a042d0ac1	MONDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	6f1475e5-0d8a-47c7-855f-ccff802aff85
cca19cfc-ad4a-4cb3-907a-8a2a59652884	TUESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	6f1475e5-0d8a-47c7-855f-ccff802aff85
07a30ecd-7357-44ee-bc0f-fb361e8742bf	WEDNESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	6f1475e5-0d8a-47c7-855f-ccff802aff85
604224f7-90fe-4a8e-b1cb-9845c52d61bf	THURSDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	6f1475e5-0d8a-47c7-855f-ccff802aff85
4d1c1ade-7793-461a-af55-cad44a327573	FRIDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	6f1475e5-0d8a-47c7-855f-ccff802aff85
fcd776ef-3b04-4ae4-b509-cf585cd40b20	MONDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	58845959-0984-4136-a733-b75f5c9057b9
6d839460-9678-4faf-b510-436183aaac33	TUESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	58845959-0984-4136-a733-b75f5c9057b9
677eee8c-deb9-4ff4-8f5a-85da672f2f60	WEDNESDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	58845959-0984-4136-a733-b75f5c9057b9
cc7ebd08-74ce-4ecf-b63a-8173516a05a1	THURSDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	58845959-0984-4136-a733-b75f5c9057b9
7466d99a-7d66-44c9-9b8e-f842d3cb248c	FRIDAY	03700b08-395a-4c6b-ad70-d16d7bd2953a	58845959-0984-4136-a733-b75f5c9057b9
048ec068-1ec9-43ee-8005-6a4d0d0d3d05	THURSDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	6f1475e5-0d8a-47c7-855f-ccff802aff85
0852ddf4-1915-49a9-a1a2-7be3a8070d1f	FRIDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	6f1475e5-0d8a-47c7-855f-ccff802aff85
eb476ccf-b3ad-41f5-88f6-e217006ea2c2	MONDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	58845959-0984-4136-a733-b75f5c9057b9
6cddbbf9-a72f-4423-8441-f8399e94afee	TUESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	58845959-0984-4136-a733-b75f5c9057b9
9df6de3c-6f0c-459e-a897-29be8cd95028	WEDNESDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	58845959-0984-4136-a733-b75f5c9057b9
5920c38c-3278-4880-a5ef-7fa189bb2c3d	THURSDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	58845959-0984-4136-a733-b75f5c9057b9
ea6f0ff0-1711-4b05-8482-d10a634da96b	FRIDAY	f2ae5f8a-4846-4a9d-8892-54d073e61747	58845959-0984-4136-a733-b75f5c9057b9
bb24c7ff-29f6-4c8e-9fd4-cb1c98356411	MONDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	440c17ed-5262-4800-aa19-38e5285330c3
a7f778a0-4ae3-404a-b938-12ca09a125a4	TUESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	440c17ed-5262-4800-aa19-38e5285330c3
a784eb89-c739-4a47-ae4e-421e60022e86	WEDNESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	440c17ed-5262-4800-aa19-38e5285330c3
f3d9c5ef-137e-42ab-9a4b-da2bc62a2565	MONDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	440c17ed-5262-4800-aa19-38e5285330c3
232d01d7-477d-4041-90cf-aa54ec10a89c	TUESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	440c17ed-5262-4800-aa19-38e5285330c3
cd8de666-901e-464d-8e2e-0ef1483741b5	WEDNESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	440c17ed-5262-4800-aa19-38e5285330c3
e74e621d-5acf-453d-8634-5ae70d1c3f27	THURSDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	440c17ed-5262-4800-aa19-38e5285330c3
e8499e32-a3ba-42dc-84d1-667c9ec2493c	FRIDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	440c17ed-5262-4800-aa19-38e5285330c3
1ade047e-4c72-41d7-8d78-b04720cfbbf1	MONDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3bd46b32-1691-4c07-85b0-e84832bd4253	TUESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	5f76a266-8d18-4da8-9732-b63d4d8f24a0
90ec79a6-23fd-4f6f-bf2a-39530d8bdde1	WEDNESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e6b960f3-1631-468f-afdb-eda92bf1c4c9	THURSDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	5f76a266-8d18-4da8-9732-b63d4d8f24a0
26c130a4-f34e-4d8e-8880-a5d593cba355	FRIDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	5f76a266-8d18-4da8-9732-b63d4d8f24a0
550df9a7-4427-40f5-a7fb-e39fc02428d4	MONDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	d54b130a-28a8-4bb1-9030-c474369c2205
f913e263-8a16-4552-94a1-1e9ebe8b0935	TUESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	d54b130a-28a8-4bb1-9030-c474369c2205
9cedca7b-1256-4d93-bba0-e5bd4e9fde80	WEDNESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	d54b130a-28a8-4bb1-9030-c474369c2205
b9bcc4ab-01d2-4539-8af5-e489fe285181	THURSDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	d54b130a-28a8-4bb1-9030-c474369c2205
212a27df-1a28-4c54-8c89-cd8504632600	FRIDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	d54b130a-28a8-4bb1-9030-c474369c2205
5e703edc-1f92-4609-b626-ec123e638dfe	MONDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
08413878-dd80-4218-8181-73c4c8035b07	TUESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
fc7db08a-ad76-4f0a-8ae0-4a4de0383f36	WEDNESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b5821812-9996-4923-9ecd-24f62c78537b	THURSDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
dbb6195d-ace7-46c1-aaa1-9a5f27e0dbce	FRIDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
338d7221-8125-4c87-96b4-94307a869883	MONDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	c33e01ef-be92-474a-9e43-0543649735d5
202367d4-a3b6-4ede-9ff5-928e85dc5fe7	TUESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	c33e01ef-be92-474a-9e43-0543649735d5
a86bc3bb-f2cb-42d0-bcbc-77ae0c631c5c	WEDNESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	c33e01ef-be92-474a-9e43-0543649735d5
f7f1c19c-340f-4704-b7f2-b0ba47cc7d80	THURSDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	c33e01ef-be92-474a-9e43-0543649735d5
e5796602-47d8-4855-868e-c6ad1e537bdd	FRIDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	c33e01ef-be92-474a-9e43-0543649735d5
ea17f78d-61c8-4ad7-bbc2-f93881741631	MONDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	6f1475e5-0d8a-47c7-855f-ccff802aff85
ca4344a4-49aa-4cea-9e99-7c72ddc9a932	TUESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	6f1475e5-0d8a-47c7-855f-ccff802aff85
9b3227bf-2a44-47cd-920c-d29f2cad50d4	WEDNESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	6f1475e5-0d8a-47c7-855f-ccff802aff85
d5116a08-5b62-4fdb-8f86-ca967d23974f	THURSDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	6f1475e5-0d8a-47c7-855f-ccff802aff85
bebcb5f7-64e3-4644-a8ab-32787490c860	FRIDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	6f1475e5-0d8a-47c7-855f-ccff802aff85
9a5a6d24-e63e-4171-bbfe-3c2d19bdb4fa	MONDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	58845959-0984-4136-a733-b75f5c9057b9
9eb2f4cf-d27d-4d77-a92d-bc0721a27644	TUESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	58845959-0984-4136-a733-b75f5c9057b9
1526805f-e37b-44b0-ba5b-024434a5039d	WEDNESDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	58845959-0984-4136-a733-b75f5c9057b9
27291a41-3d4d-4c5b-9464-b3cafbfbfc3b	THURSDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	58845959-0984-4136-a733-b75f5c9057b9
e4d09598-0b53-422a-8f1f-dfb586aaba39	FRIDAY	1becfcec-0a74-4916-9f58-1f895c07bb28	58845959-0984-4136-a733-b75f5c9057b9
7765dd5c-dc75-424e-8ff8-abcf7e5ad17e	MONDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	440c17ed-5262-4800-aa19-38e5285330c3
ec601723-4a45-459d-8eff-9ccdb72c759f	TUESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	440c17ed-5262-4800-aa19-38e5285330c3
cc0c7d2d-03f9-40d9-bf9f-e0418e05b35a	WEDNESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	440c17ed-5262-4800-aa19-38e5285330c3
a2ed6831-10c0-4c2c-9572-e93d5822614a	THURSDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	440c17ed-5262-4800-aa19-38e5285330c3
d8f7a8dd-13b7-4329-b658-7ed8efed9d9e	FRIDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	440c17ed-5262-4800-aa19-38e5285330c3
d3b77680-1a09-4d73-b6eb-3a0160f1f19a	MONDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
727f09e5-32f0-4426-b54f-972ce1db2182	TUESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e365cf87-8a37-44d0-b90e-f47b30a6a016	WEDNESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9a64b7b5-3675-478b-90b6-ccbf6b22ff59	THURSDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1cb46af6-4bd4-456e-a9b0-72bcd3c71d5a	FRIDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d00b416d-4684-4d8c-a04d-a38d21e2f959	MONDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	d54b130a-28a8-4bb1-9030-c474369c2205
e18fb9b6-f899-4b71-99f9-31059d2b15c7	TUESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	d54b130a-28a8-4bb1-9030-c474369c2205
9358c97f-697c-4328-bb59-11c35ffd2369	WEDNESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	d54b130a-28a8-4bb1-9030-c474369c2205
a478e086-053c-4872-9a0c-1ac865a31609	THURSDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	d54b130a-28a8-4bb1-9030-c474369c2205
45e3a52b-9c57-43b8-9879-8e90218fb207	FRIDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	d54b130a-28a8-4bb1-9030-c474369c2205
45504a9f-6bc5-4ee7-8363-dcc16739de25	MONDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
84cb82b6-96e4-40ae-8fda-f9d34b778ddb	TUESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a1ca927b-e7d2-44a0-813d-294216d20297	WEDNESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ed61e7c7-be15-494a-bb52-2fa94595cb29	THURSDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f2acc704-a820-4275-8a39-723898f88b09	FRIDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6076f264-cf8a-490a-ba59-bb19b2ac8e93	MONDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	c33e01ef-be92-474a-9e43-0543649735d5
7193793d-04b5-41a3-a6c1-d06f47e7be24	TUESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	c33e01ef-be92-474a-9e43-0543649735d5
791db56f-944f-4a6a-a393-a892279d3c62	WEDNESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	c33e01ef-be92-474a-9e43-0543649735d5
236b1960-bc54-4993-80b1-791244520047	THURSDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	c33e01ef-be92-474a-9e43-0543649735d5
9d9bb28b-9929-4f4d-b5f3-50d3ff9ffbcb	FRIDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	c33e01ef-be92-474a-9e43-0543649735d5
d47235b2-fc69-4d24-adfd-323a67838146	MONDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	6f1475e5-0d8a-47c7-855f-ccff802aff85
6e089e32-ffc7-4184-a32e-5c26a1b8df7b	TUESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	6f1475e5-0d8a-47c7-855f-ccff802aff85
c80ac316-c29b-4408-8a87-319c4a4f01c5	WEDNESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	6f1475e5-0d8a-47c7-855f-ccff802aff85
1eb57cfa-2765-4c95-a475-22e752bfaa96	THURSDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	6f1475e5-0d8a-47c7-855f-ccff802aff85
90953bee-533c-4f8d-85c2-2b6217b4ae3c	FRIDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	6f1475e5-0d8a-47c7-855f-ccff802aff85
76e0dbb4-cdb6-41be-b221-c79c6242a15d	MONDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	58845959-0984-4136-a733-b75f5c9057b9
aa2dc41f-46ca-464c-bb25-e39c7c2c084a	TUESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	58845959-0984-4136-a733-b75f5c9057b9
743e7687-8749-4f82-9616-63916286a047	WEDNESDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	58845959-0984-4136-a733-b75f5c9057b9
9107c495-2f99-4cb6-a273-391c9ced1d2d	THURSDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	58845959-0984-4136-a733-b75f5c9057b9
186c4265-4b94-4c7e-8298-4ad5e5692eec	FRIDAY	52ce8f6b-8a6a-4546-875a-8178a9feb6f0	58845959-0984-4136-a733-b75f5c9057b9
d19220b9-fd73-41c8-9960-45007055740b	MONDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	440c17ed-5262-4800-aa19-38e5285330c3
8e782953-fdf9-4b3e-ab96-b7fb9153cc85	TUESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	440c17ed-5262-4800-aa19-38e5285330c3
c049cecd-0754-4ff9-862e-47ccc7633b5b	WEDNESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	440c17ed-5262-4800-aa19-38e5285330c3
f40f0545-452c-42df-826d-3e58792bb1de	THURSDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	440c17ed-5262-4800-aa19-38e5285330c3
19f401f3-d49f-4e86-afdd-51ee969fe3f8	FRIDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	440c17ed-5262-4800-aa19-38e5285330c3
24a529ee-8a26-4c70-93c7-8a85bb38c4af	MONDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4ef282e2-d673-4309-a36c-87794c05e453	TUESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
45ea8244-5cb0-46a8-97ab-ddfb57fbc6f7	WEDNESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
61a47669-10c3-4960-a6af-cd74fbc1a47f	THURSDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
81163cd8-0810-4c75-bb8d-112e971329bb	FRIDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8cf55991-0ed2-4709-ab1f-95d3a018438b	MONDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	d54b130a-28a8-4bb1-9030-c474369c2205
db41c217-608c-4950-93d8-80e77c90c8b1	TUESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	d54b130a-28a8-4bb1-9030-c474369c2205
d8fea217-ba61-4b69-b5a0-4b0207c6def8	WEDNESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	d54b130a-28a8-4bb1-9030-c474369c2205
c1292db7-01d8-45ea-bcfe-16d090731ec3	THURSDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	d54b130a-28a8-4bb1-9030-c474369c2205
71f2ec51-fa9e-4d0f-a3c3-f7fe891cadd8	FRIDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	d54b130a-28a8-4bb1-9030-c474369c2205
ea6a8f07-8cb1-4ccf-8f2b-fdd95b5adcdc	MONDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3c093258-b6f8-4758-8ee5-7f17ea6888b8	TUESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
08140a4b-61a1-4c07-b457-b4abc5e8ae4f	WEDNESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
af38fef8-de32-4015-8f8d-c13fc6fd2a4f	THURSDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
eddb45b8-191b-44b1-a7d1-6dc6cc3ef95a	FRIDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ab738082-d92b-402e-a82f-7983aa4f9909	MONDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	c33e01ef-be92-474a-9e43-0543649735d5
93aa5e44-4847-4e49-bf0a-8463b20b506e	TUESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	c33e01ef-be92-474a-9e43-0543649735d5
f9fbaa76-d88a-4728-a4ad-96f9d32c3f16	WEDNESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	c33e01ef-be92-474a-9e43-0543649735d5
08f2a19e-906a-43c8-8e68-0d18d911a0db	THURSDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	c33e01ef-be92-474a-9e43-0543649735d5
f1a7b164-7f41-437c-ac74-00012464c206	FRIDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	c33e01ef-be92-474a-9e43-0543649735d5
5b7be2b4-ca62-4d62-b4ef-1d8b6406cba5	MONDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	6f1475e5-0d8a-47c7-855f-ccff802aff85
0f9c8d0c-eabc-498f-948c-cf045e91d531	TUESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	6f1475e5-0d8a-47c7-855f-ccff802aff85
2d6d8df8-8a5b-4fc6-9f20-97d1571fcc58	WEDNESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	6f1475e5-0d8a-47c7-855f-ccff802aff85
684f1974-5370-4b2e-bccd-dcf8ab8a2c29	THURSDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	6f1475e5-0d8a-47c7-855f-ccff802aff85
56243f45-ed98-400b-be9c-99b07c570780	FRIDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	6f1475e5-0d8a-47c7-855f-ccff802aff85
fe6c2df2-d6df-4ae9-bc61-534ac3882200	MONDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	58845959-0984-4136-a733-b75f5c9057b9
88cdc2e4-9328-45a5-afb1-7a52ede23b58	TUESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	58845959-0984-4136-a733-b75f5c9057b9
9bd1062d-2110-4b24-8168-c94a86152978	WEDNESDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	58845959-0984-4136-a733-b75f5c9057b9
1bd69069-cb1a-43e0-8836-06bd6e42ec57	THURSDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	58845959-0984-4136-a733-b75f5c9057b9
3043ef88-9f85-4b30-befb-7e577fb863c1	FRIDAY	21cf5e55-8d00-4266-8327-abc94f05e72b	58845959-0984-4136-a733-b75f5c9057b9
10a0f135-2520-4943-8668-4e2592cda876	MONDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	440c17ed-5262-4800-aa19-38e5285330c3
1f61d1f2-1045-4db3-8b53-e90438eabc51	TUESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	440c17ed-5262-4800-aa19-38e5285330c3
1029233a-15bf-4b1e-a1d5-752ff6df272b	WEDNESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	440c17ed-5262-4800-aa19-38e5285330c3
d46b849d-6f1a-4393-94e1-72ba188abbaf	THURSDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	440c17ed-5262-4800-aa19-38e5285330c3
c6025a88-88bb-4aea-aee3-9ae3119e7f1f	FRIDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	440c17ed-5262-4800-aa19-38e5285330c3
1a9d484f-382a-41be-9d34-27292a663d21	MONDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e723fb32-6022-4b4e-af32-5592a14ec1a3	TUESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	5f76a266-8d18-4da8-9732-b63d4d8f24a0
c353c239-44dd-45a0-a91e-b1fe85b7faeb	WEDNESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1621cf60-5f89-4fed-8b6d-82cdebe2467c	THURSDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cb855b5b-3e25-46e6-84d6-91d777de31b9	FRIDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	5f76a266-8d18-4da8-9732-b63d4d8f24a0
93b65a74-e5ab-4718-a749-5c02569349c6	MONDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	d54b130a-28a8-4bb1-9030-c474369c2205
44af595f-6d60-4c9a-9a50-e0c4c9454421	TUESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	d54b130a-28a8-4bb1-9030-c474369c2205
3cffa470-7368-47a0-a975-236da59c26ec	WEDNESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	d54b130a-28a8-4bb1-9030-c474369c2205
08ad9f18-0d87-4276-bdb4-1bd9ba49e297	THURSDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	d54b130a-28a8-4bb1-9030-c474369c2205
cdd0075d-7a28-43e7-bb04-c49c32a78c22	FRIDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	d54b130a-28a8-4bb1-9030-c474369c2205
fa8e4a9d-45e2-401e-a748-c479a1da3be2	MONDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
53553cc0-16c8-43ec-adf4-faa7bea72e85	TUESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
631d4d46-61bb-4fbd-a55b-112b6fb627c7	WEDNESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b2d0c72e-aec2-4cbd-b65b-3deb0410e40f	THURSDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cc225e83-3de9-491d-9692-c9ba914cf24b	FRIDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
64852ad8-ce27-4238-8b17-e14c222f360f	MONDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	c33e01ef-be92-474a-9e43-0543649735d5
88c8dd72-168a-47c8-9806-a331686c2f24	TUESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	c33e01ef-be92-474a-9e43-0543649735d5
42db6299-bfd6-443c-89fe-a5dae83b4d7e	WEDNESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	c33e01ef-be92-474a-9e43-0543649735d5
7ee5f1c3-5a08-4799-af5f-a25ae99121b0	THURSDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	c33e01ef-be92-474a-9e43-0543649735d5
ffe4bfad-bb7d-40cc-ac98-da1daac89106	FRIDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	c33e01ef-be92-474a-9e43-0543649735d5
4b79c9af-d834-4c48-aea9-9d185e188304	MONDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	6f1475e5-0d8a-47c7-855f-ccff802aff85
9f357cc9-a14f-4052-9c80-28a76cb632ab	TUESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	6f1475e5-0d8a-47c7-855f-ccff802aff85
00dd633e-2e27-4256-87e9-ab631795d474	WEDNESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	6f1475e5-0d8a-47c7-855f-ccff802aff85
6eb67570-9a92-45ee-87a0-5d70b37141ba	THURSDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	6f1475e5-0d8a-47c7-855f-ccff802aff85
d254d264-d76f-4a82-81f3-3d226d3f2ae5	FRIDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	6f1475e5-0d8a-47c7-855f-ccff802aff85
8b0f5873-62be-4a5f-8208-52bc83b86085	MONDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	58845959-0984-4136-a733-b75f5c9057b9
3c062514-424e-4154-8d3e-242554487cf4	TUESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	58845959-0984-4136-a733-b75f5c9057b9
62de1a67-ccb0-408f-b317-2b4c1ca1049e	WEDNESDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	58845959-0984-4136-a733-b75f5c9057b9
b1fe7d35-99be-41ed-adb8-272277e5c238	THURSDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	58845959-0984-4136-a733-b75f5c9057b9
d7a8a298-c216-4075-8083-f66e2a8d9432	FRIDAY	138903f7-b68d-4991-a566-a6d6fe8e2902	58845959-0984-4136-a733-b75f5c9057b9
1567dcb5-6934-440c-870d-6781b5724be8	MONDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	440c17ed-5262-4800-aa19-38e5285330c3
5ff7d437-ebd5-4a5f-bd26-382ed2e825cf	TUESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	440c17ed-5262-4800-aa19-38e5285330c3
afe5edc9-65e8-4266-8f3e-fa7e49161b2f	WEDNESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	440c17ed-5262-4800-aa19-38e5285330c3
163466d0-7082-410b-88b9-02a36720f197	THURSDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	440c17ed-5262-4800-aa19-38e5285330c3
5b133c45-07c5-4be6-9730-04b4f34663d2	FRIDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	440c17ed-5262-4800-aa19-38e5285330c3
1df5601b-3e3a-407b-9e77-4b957f8c49ce	MONDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
42d7b938-774b-4ceb-bbff-111bf2bca02e	TUESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
36fcd0b7-bf34-4115-8c7c-ce8889fa86be	WEDNESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
dc56ac7c-495e-48c3-bd5c-4cc040e0e26c	THURSDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3f9045b8-a4d9-4418-9087-41f7b6d1fec2	FRIDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4fd671d0-3c06-4cb0-9af0-420314edb5c3	MONDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	d54b130a-28a8-4bb1-9030-c474369c2205
190a94ce-0df7-4d10-8ba3-1c0106a2002b	TUESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	d54b130a-28a8-4bb1-9030-c474369c2205
b4d8a696-8c9d-41c0-b9e4-02c5fd87703c	WEDNESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	d54b130a-28a8-4bb1-9030-c474369c2205
abfcba0e-c05d-4ae5-a201-11036eadd18f	THURSDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	d54b130a-28a8-4bb1-9030-c474369c2205
768a3abe-3cad-46e3-9257-352e974e69d3	FRIDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	d54b130a-28a8-4bb1-9030-c474369c2205
474b4c95-fbc0-4be3-92df-4f310f6887bf	MONDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
69e4f92f-acd1-47d9-beaf-d70712d6b7d0	TUESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b798d222-bfc7-44c1-af87-4f0fe1fdf981	WEDNESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
90ee8a95-403c-4c47-9ebc-48381486d26e	THURSDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
08d45355-42ac-4d4c-8db7-ce7e7f889bd1	FRIDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
979abdb1-310b-4236-b25e-bd9e05823b3f	MONDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	c33e01ef-be92-474a-9e43-0543649735d5
dfe76053-bcb2-400b-92e5-b233606e0c91	TUESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	c33e01ef-be92-474a-9e43-0543649735d5
108ed30c-86d7-42e4-bdf0-450047bace83	WEDNESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	c33e01ef-be92-474a-9e43-0543649735d5
578e5b60-1dbe-4760-a12d-a95fbed86760	THURSDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	c33e01ef-be92-474a-9e43-0543649735d5
d9c1404f-c10d-4fcd-a2c5-26efdc98c060	FRIDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	c33e01ef-be92-474a-9e43-0543649735d5
438a46e9-3b4f-4a62-ae8e-ea7b883336b1	MONDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	6f1475e5-0d8a-47c7-855f-ccff802aff85
c0a1800f-2ce5-4729-9d87-46dc5f1acabb	TUESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	6f1475e5-0d8a-47c7-855f-ccff802aff85
3d63d060-9ef8-43e9-b287-2cd9a00e6ebf	WEDNESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	6f1475e5-0d8a-47c7-855f-ccff802aff85
6bf8d329-e1ba-4fdd-b877-6c7c440ebf9b	THURSDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	6f1475e5-0d8a-47c7-855f-ccff802aff85
c0ac2792-2149-4da7-9563-8e1cc92d178f	FRIDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	6f1475e5-0d8a-47c7-855f-ccff802aff85
f76869b0-e840-4769-b077-4958aa62eaac	MONDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	58845959-0984-4136-a733-b75f5c9057b9
52cc8a48-c1b2-46d5-9310-2a231dbc662a	TUESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	58845959-0984-4136-a733-b75f5c9057b9
e5d61662-9746-4d68-8370-7cafa17bc31d	WEDNESDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	58845959-0984-4136-a733-b75f5c9057b9
b54d7c5a-965d-4998-b0ce-ed60ed0d0ffb	THURSDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	58845959-0984-4136-a733-b75f5c9057b9
165d4f1e-e775-4059-9659-ee2118065288	FRIDAY	229de8cf-dd38-4cf9-99e0-0b086613d2bb	58845959-0984-4136-a733-b75f5c9057b9
b1233ba5-ef7c-419d-a0b4-bcb83bba535b	MONDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	440c17ed-5262-4800-aa19-38e5285330c3
f90ea34e-08cd-42ee-a801-c5c525984ec6	TUESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	440c17ed-5262-4800-aa19-38e5285330c3
d5d48ae2-8f7f-4f89-b5f0-2b9dc48c2802	WEDNESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	440c17ed-5262-4800-aa19-38e5285330c3
0dce0976-6629-46fd-bc95-d64b3f747f48	THURSDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	440c17ed-5262-4800-aa19-38e5285330c3
3c87eae7-fa63-4e17-ad20-e41e78418dab	FRIDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	440c17ed-5262-4800-aa19-38e5285330c3
d10e89fc-f2ca-4b21-8872-7042c5f65e67	MONDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
583e80c9-37cd-43aa-8ff2-fb61ec6ecce1	TUESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e03bbc01-62a3-4c59-ab1d-a44549539181	WEDNESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f8fff603-d77c-4efd-9152-c4c4a22149b4	THURSDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0f8e6f67-be86-4ab3-b1aa-2e18245cf04e	FRIDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0f3eb358-070c-4e10-a653-afe3e2ee5563	MONDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	d54b130a-28a8-4bb1-9030-c474369c2205
612e3f81-644b-48aa-8d8b-7751e8f15783	TUESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	d54b130a-28a8-4bb1-9030-c474369c2205
b20dfa2e-ce30-4557-a50e-3c49cb5fcc77	WEDNESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	d54b130a-28a8-4bb1-9030-c474369c2205
d1e536cd-3614-4037-80de-d38ddb06508e	THURSDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	d54b130a-28a8-4bb1-9030-c474369c2205
211e3ab5-9e5d-463e-9f98-b202aef4f70e	FRIDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	d54b130a-28a8-4bb1-9030-c474369c2205
1e29e5a7-dfef-4fee-a523-33d530b19b8d	MONDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
277b0cd5-df10-4c49-af17-429a8b3ae165	TUESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0807b4c1-856f-4986-9277-4f88eea6c75f	WEDNESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6ef5334b-39fe-4fd3-83a4-1a7699f0198b	THURSDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
03f8e1d3-750c-4ef0-894f-b2533dd556ad	FRIDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a678601d-44d1-4233-a879-fc9f1cbbbc2c	MONDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	c33e01ef-be92-474a-9e43-0543649735d5
2eafd2ab-e15c-4175-9747-5697b524cd6b	TUESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	c33e01ef-be92-474a-9e43-0543649735d5
fbc16cd1-5b6f-4870-9a21-2051562e2cbb	WEDNESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	c33e01ef-be92-474a-9e43-0543649735d5
df30ba73-5465-484e-8639-e4b1fdf9c247	THURSDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	c33e01ef-be92-474a-9e43-0543649735d5
8d428801-938e-401f-a7f6-4bae03d389c7	FRIDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	c33e01ef-be92-474a-9e43-0543649735d5
11f97e17-28a5-47f7-b3c6-37c89a96ec4f	MONDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	6f1475e5-0d8a-47c7-855f-ccff802aff85
3d061778-296c-4132-8324-d1f869234b9c	TUESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	6f1475e5-0d8a-47c7-855f-ccff802aff85
f75797cd-e8a4-4f82-97a9-bb4817c151fb	WEDNESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	6f1475e5-0d8a-47c7-855f-ccff802aff85
39473922-2ae5-4a9f-b9d4-0bb30ec6e567	THURSDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	6f1475e5-0d8a-47c7-855f-ccff802aff85
6b54b1a2-4c3e-4b1e-9748-d3f2bd4a1cc5	FRIDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	6f1475e5-0d8a-47c7-855f-ccff802aff85
fa495a81-6d9a-456c-9e91-867f967792b8	MONDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	58845959-0984-4136-a733-b75f5c9057b9
72c7d2a1-7bea-4426-b422-08e1389cf2fc	TUESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	58845959-0984-4136-a733-b75f5c9057b9
3ddab22c-5222-4214-941b-cddf9ba912be	WEDNESDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	58845959-0984-4136-a733-b75f5c9057b9
a9b63311-cf1c-484c-b1f1-ce59bb5e967e	THURSDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	58845959-0984-4136-a733-b75f5c9057b9
4ad82938-0ba5-4dd9-ab74-801db9c31c50	FRIDAY	7dbed3de-9e92-4c15-8533-c434adb916fb	58845959-0984-4136-a733-b75f5c9057b9
4f9d8219-9152-4c6a-aaa1-fb54bdc4e9a9	MONDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	440c17ed-5262-4800-aa19-38e5285330c3
88a9d6f8-d161-4fbb-8274-6b672a994a5c	TUESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	440c17ed-5262-4800-aa19-38e5285330c3
56e6c79b-5cee-4e7a-b81d-c0b8330ea2c4	WEDNESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	440c17ed-5262-4800-aa19-38e5285330c3
d00ede70-8b9f-4243-a21a-1694fe57444d	THURSDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	440c17ed-5262-4800-aa19-38e5285330c3
de492d41-b522-49d4-a019-86bbcde027db	FRIDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	440c17ed-5262-4800-aa19-38e5285330c3
4b9ed24f-c28b-4d2a-b386-ba4e3fd5d26d	MONDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a242e7aa-2c34-41ca-bdd4-2923fd564bdc	TUESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a101f388-1d88-4aa8-9168-4bceb7704e51	WEDNESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e063f197-1bbe-4317-a7cf-d57402f01aa3	THURSDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1ff5b429-6a73-47bc-bc7c-d13501c132e8	FRIDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3c3f1c8a-5c8e-4c69-b4f6-9cb78bfc27c1	MONDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	d54b130a-28a8-4bb1-9030-c474369c2205
7b4612c2-4894-442a-af93-05611a18072b	TUESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	d54b130a-28a8-4bb1-9030-c474369c2205
b179186c-8386-49f3-b2b1-fc3332bd8ce3	WEDNESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	d54b130a-28a8-4bb1-9030-c474369c2205
0f8e3120-9d0d-41c4-acf8-4d80cf855768	THURSDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	d54b130a-28a8-4bb1-9030-c474369c2205
ff437c73-3be3-4302-83cf-ecd7726d5380	FRIDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	d54b130a-28a8-4bb1-9030-c474369c2205
777d1a53-e227-4291-ba09-7a6264e18ba6	MONDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
af01166f-d1b7-4449-90c1-66e39d6837e5	TUESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7d4e901c-c981-4972-b27a-ce29535097a5	WEDNESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
fc38e624-38fb-4c38-ac10-7aef5e55b761	THURSDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
11bc4f4c-4725-4922-9daa-06d471160fe8	FRIDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cb14e5ae-d3dd-4219-b9e2-7fef87c598ec	MONDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	c33e01ef-be92-474a-9e43-0543649735d5
76f19f54-833f-41c1-89c0-61eca79f05bd	TUESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	c33e01ef-be92-474a-9e43-0543649735d5
baa6d261-46e0-4ac5-beaf-383e09206598	WEDNESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	c33e01ef-be92-474a-9e43-0543649735d5
5771bf75-c3b4-48a1-8972-ca3b5350007f	THURSDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	c33e01ef-be92-474a-9e43-0543649735d5
d9df78f0-5fb1-4194-8c53-c17ba10fb90a	FRIDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	c33e01ef-be92-474a-9e43-0543649735d5
eb139248-5ea8-44ad-87a6-1447b5a75edd	MONDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	6f1475e5-0d8a-47c7-855f-ccff802aff85
548bb13d-e292-4dd9-bb66-1295c0a4b4b4	TUESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	6f1475e5-0d8a-47c7-855f-ccff802aff85
b466ee5c-6ba9-45dd-8080-7ef67f25e080	WEDNESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	6f1475e5-0d8a-47c7-855f-ccff802aff85
5cfb7c66-35a3-4ac8-92fd-f256b2cb40ec	THURSDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	6f1475e5-0d8a-47c7-855f-ccff802aff85
401ae18c-5bcd-4b46-944e-11aa9e4bab83	FRIDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	6f1475e5-0d8a-47c7-855f-ccff802aff85
d54f1e3a-d28f-45fe-ad48-0c0b435c7987	MONDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	58845959-0984-4136-a733-b75f5c9057b9
3c3e76b1-eb15-4983-993b-01d1fd1799c2	TUESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	58845959-0984-4136-a733-b75f5c9057b9
b7353930-d8ec-468d-a865-8201263a19f2	WEDNESDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	58845959-0984-4136-a733-b75f5c9057b9
e379f60b-b2d8-40b7-b8f6-d9b43260775e	THURSDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	58845959-0984-4136-a733-b75f5c9057b9
e7a1897c-0f0a-4235-bdf1-c8913f335207	FRIDAY	cfb21c78-95ce-4ff5-9d92-3dd9fde3df8f	58845959-0984-4136-a733-b75f5c9057b9
8b947691-f85c-47b5-ab7d-fa5d5f46f89a	MONDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	440c17ed-5262-4800-aa19-38e5285330c3
d7132417-7449-4755-90ff-83a5ecacd8b9	TUESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	440c17ed-5262-4800-aa19-38e5285330c3
42e92323-6d76-4bde-b660-bcda7541a3bd	WEDNESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	440c17ed-5262-4800-aa19-38e5285330c3
135c0fba-5c4e-4e47-b386-d446fdecd562	THURSDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	440c17ed-5262-4800-aa19-38e5285330c3
16637883-5b0a-478b-a9a4-3cbab81bba06	FRIDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	440c17ed-5262-4800-aa19-38e5285330c3
edd35ccd-3111-43ea-aee5-a14537c02ee7	MONDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
918eda70-d05f-4725-87f6-69bf8e0732e2	TUESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
32b6de57-f6ff-49fc-a4f9-6533fe033996	WEDNESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8f20bc5f-4098-4715-b4d5-2beee31a0495	THURSDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
caf7d151-ef9d-4acb-aaa5-c82eb67f3226	FRIDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bf3424e2-11cf-4fdd-8c56-a6cefb8de6ce	MONDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	d54b130a-28a8-4bb1-9030-c474369c2205
2b1674f2-9168-4008-a6b0-93ff321e1120	TUESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	d54b130a-28a8-4bb1-9030-c474369c2205
ebbd89d8-bd2a-4d6c-b1f9-ab4bbbc20c98	WEDNESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	d54b130a-28a8-4bb1-9030-c474369c2205
6bb6ce72-cc4b-483d-8212-3e28f57403dd	THURSDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	d54b130a-28a8-4bb1-9030-c474369c2205
d9331a3a-9a02-42b4-b848-bfacd9194f25	FRIDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	d54b130a-28a8-4bb1-9030-c474369c2205
614be1d5-f46e-4cf9-b2a9-19c884a9e5fa	MONDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
647c471b-854f-4e94-8de4-b43efa83a598	TUESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
54e67eae-c756-410b-bc71-73a8386f68f2	WEDNESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
355f9139-59b4-404c-9f97-3535e6ca3477	THURSDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
a5249b19-4867-40eb-8b30-9c685e0f9e88	FRIDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
12db3529-8fe7-416e-aa35-8d94ae937968	MONDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	c33e01ef-be92-474a-9e43-0543649735d5
b95199b5-4a75-447a-84bb-58f29837a6b3	TUESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	c33e01ef-be92-474a-9e43-0543649735d5
70c5548c-7203-4d84-b536-8e9a14a25583	WEDNESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	c33e01ef-be92-474a-9e43-0543649735d5
3739b1c1-19aa-4df3-8e48-dd8f044593bc	THURSDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	c33e01ef-be92-474a-9e43-0543649735d5
30a85a02-721c-4be2-ab27-8ebd784ecc78	FRIDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	c33e01ef-be92-474a-9e43-0543649735d5
9da9e2a9-9702-48ae-a0c1-3ae88b8b6dfa	MONDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	6f1475e5-0d8a-47c7-855f-ccff802aff85
2d9c861f-ecac-4f46-be05-561f94064fe3	TUESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	6f1475e5-0d8a-47c7-855f-ccff802aff85
213a04dd-4593-46df-8e02-be5864fbe2f9	WEDNESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	6f1475e5-0d8a-47c7-855f-ccff802aff85
d2539db2-5d7b-4f4b-b0de-ae95dec16350	THURSDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	6f1475e5-0d8a-47c7-855f-ccff802aff85
df3e4aee-7f02-4403-a5ba-26c008dbeb25	FRIDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	6f1475e5-0d8a-47c7-855f-ccff802aff85
526d56a4-b1bc-4a74-93f1-9eb0976f21a8	MONDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	58845959-0984-4136-a733-b75f5c9057b9
35aeb787-50d3-41b4-ae3b-f8547f69c28c	TUESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	58845959-0984-4136-a733-b75f5c9057b9
b114d919-cd4f-43e6-abb6-693b88851cf5	WEDNESDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	58845959-0984-4136-a733-b75f5c9057b9
a943a075-9af6-4ccb-9890-91895bff6dfd	THURSDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	58845959-0984-4136-a733-b75f5c9057b9
01b7d8ac-2653-42a8-a6a3-2796396b0e2f	MONDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	440c17ed-5262-4800-aa19-38e5285330c3
b4026f95-fc9d-413f-918d-71b32e6a4d2a	TUESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	440c17ed-5262-4800-aa19-38e5285330c3
4bbeff2b-9162-4201-afbe-9565095bf198	WEDNESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	440c17ed-5262-4800-aa19-38e5285330c3
5234be61-565a-4696-8292-1eb6f7106f28	THURSDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	440c17ed-5262-4800-aa19-38e5285330c3
36c455b6-28a0-4ec5-932a-d1a9e1ecc59a	FRIDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	440c17ed-5262-4800-aa19-38e5285330c3
d1e436bf-03b1-4d5f-bfa5-934642ccfb17	MONDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
45069c65-ab3d-40ca-995c-4d39dcac2e49	TUESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
20817132-9d9c-454a-ad79-08ea8250649f	WEDNESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e2b20284-7c9e-4541-9b4b-515b8aec6de5	THURSDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3b50dc3c-8a5f-4ab8-ace0-76536a7f2149	FRIDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8f2fbcb5-572c-49ee-994d-49ebc8cf8dd5	MONDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	d54b130a-28a8-4bb1-9030-c474369c2205
8dcc41a2-ab51-4d30-8c10-79a34af72f71	TUESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	d54b130a-28a8-4bb1-9030-c474369c2205
36daa64e-718c-4763-bf38-87bc2f0253d0	WEDNESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	d54b130a-28a8-4bb1-9030-c474369c2205
c232b3f2-2bcc-4d80-9f48-7a892870d924	THURSDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	d54b130a-28a8-4bb1-9030-c474369c2205
7c740a27-abbd-4abf-90a7-ee0651150bac	FRIDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	d54b130a-28a8-4bb1-9030-c474369c2205
e74b9801-8cf6-4519-a4b2-88b33b1c6e05	MONDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d1cae114-d7e3-4e8f-a913-ea85c8ed090e	TUESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
559e8dc5-9b71-44cd-b02f-f23e2de32386	WEDNESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7e660072-c968-4aa3-bcd0-1f775a58f983	THURSDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
33e255dc-4a34-4e5d-810c-6d6f478a2c2f	FRIDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
ddb28638-79e0-4e3d-90a8-318abc6949aa	MONDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	c33e01ef-be92-474a-9e43-0543649735d5
7a745552-fe48-408e-aca7-1afa4d510939	TUESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	c33e01ef-be92-474a-9e43-0543649735d5
6d1ae306-d53f-414b-8e79-56af28317c61	WEDNESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	c33e01ef-be92-474a-9e43-0543649735d5
3dd73926-656e-498b-aebf-990f6ad085ab	THURSDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	c33e01ef-be92-474a-9e43-0543649735d5
4856356a-e636-4de6-b677-cc21029108ab	FRIDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	c33e01ef-be92-474a-9e43-0543649735d5
2d3271b3-083e-4fcd-82ab-e24940725a23	MONDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	6f1475e5-0d8a-47c7-855f-ccff802aff85
fc37f4cc-dfe7-409f-bdf2-0d9b80de2a4e	TUESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	6f1475e5-0d8a-47c7-855f-ccff802aff85
ab704f9c-2d36-445c-b00b-85dc6fddec5d	WEDNESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	6f1475e5-0d8a-47c7-855f-ccff802aff85
340f2788-bc52-4f64-9e21-39c400242b22	THURSDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	6f1475e5-0d8a-47c7-855f-ccff802aff85
34069832-3e36-44b6-ba1b-54fd2d9ed50c	FRIDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	6f1475e5-0d8a-47c7-855f-ccff802aff85
81babc8a-711f-46dd-8d97-11c27e0e5d64	MONDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	58845959-0984-4136-a733-b75f5c9057b9
0667ffa7-5c27-4868-bae9-8ccf3b460dbc	TUESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	58845959-0984-4136-a733-b75f5c9057b9
b1651bdc-bdd8-43c1-be1a-6620e2d2d8ff	WEDNESDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	58845959-0984-4136-a733-b75f5c9057b9
d31de6b6-c631-4161-99bc-64ccf0f742ff	THURSDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	58845959-0984-4136-a733-b75f5c9057b9
adb074e4-c9e6-46c8-87df-2f4f0faaedda	FRIDAY	8bba2799-8adb-488b-bee7-4bffc2bb0a0f	58845959-0984-4136-a733-b75f5c9057b9
36de3b48-216e-450f-88db-2aefa207c601	MONDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	440c17ed-5262-4800-aa19-38e5285330c3
3ab18fbe-5c36-4b34-8b3d-656c75e3ecbc	TUESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	440c17ed-5262-4800-aa19-38e5285330c3
2f2c3499-a76c-4da9-8a5f-efdeecad80b3	WEDNESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	440c17ed-5262-4800-aa19-38e5285330c3
8a55e0e8-5fdd-401d-a975-557333746fda	THURSDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	440c17ed-5262-4800-aa19-38e5285330c3
aa3443a7-83a2-435b-bdb9-37035a928f33	FRIDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	440c17ed-5262-4800-aa19-38e5285330c3
311d0847-ef78-4de1-9aea-263501ea5158	MONDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8fc2a771-83d9-4aa6-a011-a122e363b845	TUESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d2948c2f-069e-4a02-85b2-c988f659c479	WEDNESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	5f76a266-8d18-4da8-9732-b63d4d8f24a0
15aa99b4-1e9e-4c90-80f9-178ce6cbcbcd	THURSDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	5f76a266-8d18-4da8-9732-b63d4d8f24a0
54a055d0-0a8c-4883-95a2-cc45711e675f	FRIDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bfb9f9b8-d6b6-46ed-bd0b-952e4c733e2f	MONDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	d54b130a-28a8-4bb1-9030-c474369c2205
a27486c0-5875-4dcc-9aff-1c28123d9887	TUESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	d54b130a-28a8-4bb1-9030-c474369c2205
6ffb7ba4-d244-4b2e-9ca8-2c8187dc25ab	WEDNESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	d54b130a-28a8-4bb1-9030-c474369c2205
606c4c5a-56fd-4644-9c83-32ed407aadb8	THURSDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	d54b130a-28a8-4bb1-9030-c474369c2205
cce603e1-b94c-4aae-9845-c9063324a439	FRIDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	d54b130a-28a8-4bb1-9030-c474369c2205
6e5492fa-7dc8-423b-9de0-77333627970f	MONDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7c0d0668-e0fe-4b30-80f9-7468f2083f3d	TUESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4d6115a9-3306-4636-a7b8-2f458154f04e	WEDNESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c27e542f-4dd4-4db6-a866-fb5c6d325f94	THURSDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
770af0db-951d-4d63-a5e3-0bc15158c2fa	FRIDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
87abdb26-7f54-4d96-a774-e7b907299cd2	MONDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	c33e01ef-be92-474a-9e43-0543649735d5
2e4c13b3-3be2-49a6-b260-449b8a86585e	TUESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	c33e01ef-be92-474a-9e43-0543649735d5
d26ac443-3920-4f39-b1aa-e9a48ba6d184	WEDNESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	c33e01ef-be92-474a-9e43-0543649735d5
fdc04914-5c7d-4969-8f47-3932b10031db	THURSDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	c33e01ef-be92-474a-9e43-0543649735d5
5d409aab-9cf6-4603-ab1f-ac0162a0364f	FRIDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	c33e01ef-be92-474a-9e43-0543649735d5
2373659a-ef38-4497-8822-7178c1f058a4	MONDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	6f1475e5-0d8a-47c7-855f-ccff802aff85
99eaa9c5-eac3-4f7d-9c5a-31ea516394e7	TUESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	6f1475e5-0d8a-47c7-855f-ccff802aff85
ff64e64b-9e65-4d0e-b556-57a2768f0e0f	WEDNESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	6f1475e5-0d8a-47c7-855f-ccff802aff85
5754d03b-bee9-4446-bdb4-2e45c9bf8553	THURSDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	6f1475e5-0d8a-47c7-855f-ccff802aff85
500ce5fe-e481-418d-a361-b53129d663e5	FRIDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	6f1475e5-0d8a-47c7-855f-ccff802aff85
77fd1f82-4666-4a91-9914-cd72ad72fb88	MONDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	58845959-0984-4136-a733-b75f5c9057b9
8b776b1b-1221-48aa-ab9a-28f1495ff359	TUESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	58845959-0984-4136-a733-b75f5c9057b9
e1571bf4-f8f5-45ae-9174-2d0c2f6b71a7	WEDNESDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	58845959-0984-4136-a733-b75f5c9057b9
2db12c98-f8b4-4936-bbb6-4f3c31c9be1b	THURSDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	58845959-0984-4136-a733-b75f5c9057b9
ab6109a2-3496-470a-8a15-15cc0b6e5aea	FRIDAY	4109ab7a-165b-46ec-a0c0-b479d1defeca	58845959-0984-4136-a733-b75f5c9057b9
33570c58-de95-49d9-b7ae-ec2a386fbf77	MONDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	440c17ed-5262-4800-aa19-38e5285330c3
75ab8e3c-2717-4d5f-8e64-26ad59eea37b	TUESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	440c17ed-5262-4800-aa19-38e5285330c3
fa98ad19-5b20-462b-96b4-16177ebd0ff2	WEDNESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	440c17ed-5262-4800-aa19-38e5285330c3
a39a2f2b-fd0b-4f2a-8ec3-e5b6c2ff19b1	THURSDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	440c17ed-5262-4800-aa19-38e5285330c3
03a0b6ef-5cca-41d1-bce6-0ec5d1b0e90e	FRIDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	440c17ed-5262-4800-aa19-38e5285330c3
752f8804-7e50-4ecf-ad6c-b906e2331484	MONDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e870f708-3a75-4348-82b8-92875e844265	TUESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2f637901-f8be-4ed3-903b-cb56fa69a11d	WEDNESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2ac595e3-5840-4b06-ae4e-1d8cc0d1a47b	THURSDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	5f76a266-8d18-4da8-9732-b63d4d8f24a0
46d9b0dd-63bd-4d56-8395-0b787cb23a52	FRIDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	5f76a266-8d18-4da8-9732-b63d4d8f24a0
97478d9e-2347-464e-906e-5602f01ec859	MONDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	d54b130a-28a8-4bb1-9030-c474369c2205
5ba3c717-d009-4cf2-9575-bd86cdc6b836	TUESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	d54b130a-28a8-4bb1-9030-c474369c2205
9491d729-34dc-459d-8775-0210d5fb0797	WEDNESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	d54b130a-28a8-4bb1-9030-c474369c2205
4862b43f-aded-4cb0-84f4-c1dd58c4da0b	THURSDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	d54b130a-28a8-4bb1-9030-c474369c2205
80712abb-1f81-4307-a52a-59d3c960631d	FRIDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	d54b130a-28a8-4bb1-9030-c474369c2205
41f0a98e-1bbf-470f-8706-d63038cdc3c1	MONDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5a0db129-3578-45d6-a6ea-2b8b65bd4f75	TUESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7c795df9-c84f-400f-b3d2-d091f7279fc9	WEDNESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
89b64475-bcce-418e-aa7f-45ec7253b967	THURSDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c22b44e7-867a-4acd-9db7-2bfa9add23a6	FRIDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e06b3262-253d-4560-b1f1-b13ce565cc2c	MONDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	c33e01ef-be92-474a-9e43-0543649735d5
6b3f3246-6a04-4156-87d3-e3d49b828f17	TUESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	c33e01ef-be92-474a-9e43-0543649735d5
f9dbd98a-9538-484a-8a55-4d8f6d68dd2a	WEDNESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	c33e01ef-be92-474a-9e43-0543649735d5
db4d370d-cd0c-404f-bcb9-cbdfde6a93ca	FRIDAY	325e7fb1-947f-498c-b6b0-5a07cbf3f9dd	58845959-0984-4136-a733-b75f5c9057b9
c9a7cfdd-2651-4cdf-886b-e3d524562fdf	MONDAY	4a9e587b-b205-471d-950b-fa335e9458f5	440c17ed-5262-4800-aa19-38e5285330c3
b65dbe06-d7a9-4e69-a3de-c77f8510ad75	TUESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	440c17ed-5262-4800-aa19-38e5285330c3
86d162c8-e93a-493c-953c-b9251d3784f5	WEDNESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	440c17ed-5262-4800-aa19-38e5285330c3
45b2a5c2-536a-4b7c-b27c-01d8f21f3127	THURSDAY	4a9e587b-b205-471d-950b-fa335e9458f5	440c17ed-5262-4800-aa19-38e5285330c3
0ad51dad-2ced-4d99-a1a3-92cd4ea2f0d7	FRIDAY	4a9e587b-b205-471d-950b-fa335e9458f5	440c17ed-5262-4800-aa19-38e5285330c3
0c832f8d-7f12-4384-9273-b2ad552ec059	MONDAY	4a9e587b-b205-471d-950b-fa335e9458f5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
baa0cb6d-bfce-4782-a190-cef113c35051	TUESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e8c34c0c-76ce-403c-9c4c-6c0efc397997	WEDNESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
a7adb577-00d9-4ffd-ad39-719eb9cc3c43	THURSDAY	4a9e587b-b205-471d-950b-fa335e9458f5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
deb0eb8c-6cdf-4fb7-b99a-ba5d1df7db56	FRIDAY	4a9e587b-b205-471d-950b-fa335e9458f5	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f5e66e94-267a-4e40-8328-abfa58576ceb	MONDAY	4a9e587b-b205-471d-950b-fa335e9458f5	d54b130a-28a8-4bb1-9030-c474369c2205
cc4eda1c-f7c5-42e9-8775-fdb229df0492	TUESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	d54b130a-28a8-4bb1-9030-c474369c2205
bb1ad2d3-366f-4644-854e-01f99fb73907	WEDNESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	d54b130a-28a8-4bb1-9030-c474369c2205
c5e707a5-b1e3-4b99-a002-706a015b23f5	THURSDAY	4a9e587b-b205-471d-950b-fa335e9458f5	d54b130a-28a8-4bb1-9030-c474369c2205
52d42b55-5401-4183-8dcc-80b57cb40fb1	FRIDAY	4a9e587b-b205-471d-950b-fa335e9458f5	d54b130a-28a8-4bb1-9030-c474369c2205
d843d2ab-27a9-46b8-9b01-0f6141d92a59	MONDAY	4a9e587b-b205-471d-950b-fa335e9458f5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
abf07f61-e1c3-4668-89d3-4becc3e18d2f	TUESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7b32da78-5c1e-4671-bcd4-32c38c4c244f	WEDNESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
b913b62f-560e-437c-ad6c-42dc89764798	THURSDAY	4a9e587b-b205-471d-950b-fa335e9458f5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
0e758db7-7179-4a95-8db5-e870c89cec10	FRIDAY	4a9e587b-b205-471d-950b-fa335e9458f5	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f5ced1da-2bc0-4fd0-92d1-66a5683523f9	MONDAY	4a9e587b-b205-471d-950b-fa335e9458f5	c33e01ef-be92-474a-9e43-0543649735d5
f332380d-6b2a-4773-8714-3540a05dcfdc	TUESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	c33e01ef-be92-474a-9e43-0543649735d5
8797cf4a-0f3e-43d7-a4a4-70606efe96c1	WEDNESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	c33e01ef-be92-474a-9e43-0543649735d5
d6daae6c-7896-442a-899a-ea21967d8477	THURSDAY	4a9e587b-b205-471d-950b-fa335e9458f5	c33e01ef-be92-474a-9e43-0543649735d5
1b27cba7-d65f-41b0-afba-5f4c5e4e503d	FRIDAY	4a9e587b-b205-471d-950b-fa335e9458f5	c33e01ef-be92-474a-9e43-0543649735d5
5e832a62-4cca-4bd3-9a03-ec7d1ed0a922	MONDAY	4a9e587b-b205-471d-950b-fa335e9458f5	6f1475e5-0d8a-47c7-855f-ccff802aff85
3850c40a-97d4-4875-b7fc-43ca09a70f01	TUESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	6f1475e5-0d8a-47c7-855f-ccff802aff85
1ffee346-55e6-4aae-9aeb-366b32489c8b	WEDNESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	6f1475e5-0d8a-47c7-855f-ccff802aff85
b2937f2b-f37f-4fdc-9887-819448e1f919	THURSDAY	4a9e587b-b205-471d-950b-fa335e9458f5	6f1475e5-0d8a-47c7-855f-ccff802aff85
5b059bd9-bda6-4e51-a5eb-2a3d4b393574	FRIDAY	4a9e587b-b205-471d-950b-fa335e9458f5	6f1475e5-0d8a-47c7-855f-ccff802aff85
94d745e3-46e8-404b-88b2-49acaae93447	MONDAY	4a9e587b-b205-471d-950b-fa335e9458f5	58845959-0984-4136-a733-b75f5c9057b9
7492bd32-0f08-442b-b4fc-912d005d2bd7	TUESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	58845959-0984-4136-a733-b75f5c9057b9
ff8681ea-e06a-4154-a202-2183648650f2	WEDNESDAY	4a9e587b-b205-471d-950b-fa335e9458f5	58845959-0984-4136-a733-b75f5c9057b9
fc86b62a-b67f-47b6-b27e-452967c0e293	THURSDAY	4a9e587b-b205-471d-950b-fa335e9458f5	58845959-0984-4136-a733-b75f5c9057b9
2ae8cd0f-d482-4217-95bb-1c85c213fc0c	FRIDAY	4a9e587b-b205-471d-950b-fa335e9458f5	58845959-0984-4136-a733-b75f5c9057b9
722f2a10-825b-4589-9f74-c0f7df9eaf0d	THURSDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	440c17ed-5262-4800-aa19-38e5285330c3
d254bf28-447d-4621-a181-ae87b94e5a9e	FRIDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	440c17ed-5262-4800-aa19-38e5285330c3
555cf8a8-145c-49d8-9725-e81c7834c9fe	MONDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	5f76a266-8d18-4da8-9732-b63d4d8f24a0
edc5a556-15ea-4c44-8cf2-da0a650ce227	TUESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b90084fb-9319-4d3b-947a-c4f8baa1f30d	WEDNESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	5f76a266-8d18-4da8-9732-b63d4d8f24a0
f06e7d46-1965-4d0b-a6c1-564548db3377	THURSDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	5f76a266-8d18-4da8-9732-b63d4d8f24a0
e2a9206b-66c9-4ecb-b759-fc516db152ca	FRIDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b6fd5729-0f31-400e-bc4c-959e002edf4e	MONDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	d54b130a-28a8-4bb1-9030-c474369c2205
e2e5c2f8-0092-4053-8fa7-d1cdaf9b19ab	TUESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	d54b130a-28a8-4bb1-9030-c474369c2205
1faa8c06-7ef8-4e38-bd36-eb38a5c0f3c0	WEDNESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	d54b130a-28a8-4bb1-9030-c474369c2205
e3628d9a-4a94-40be-8344-876a62bc4f27	THURSDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	d54b130a-28a8-4bb1-9030-c474369c2205
a50010e4-4e72-459b-9b0a-97ab9221e413	FRIDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	d54b130a-28a8-4bb1-9030-c474369c2205
4b4421aa-49d7-4862-aad6-25776d9cb896	MONDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f774098b-d7c0-4903-aa71-fdc404c22b9a	TUESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
815ba866-42bd-4a8d-b463-94f1ba27dc68	WEDNESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
3adbb3a0-57fe-4f36-a16b-200e5b5c5e5f	THURSDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
2c66a5db-0d31-4094-b00e-bb50026c026c	FRIDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1c10f813-fc5c-40cb-9aeb-a0461f6f33ea	MONDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	c33e01ef-be92-474a-9e43-0543649735d5
8ab5948c-9d69-4d72-a13b-7f834584812a	TUESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	c33e01ef-be92-474a-9e43-0543649735d5
4698868c-6af1-479f-b30a-6f212f45fe89	WEDNESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	c33e01ef-be92-474a-9e43-0543649735d5
35b10372-5807-4fab-8e6e-993f532cb6f0	THURSDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	c33e01ef-be92-474a-9e43-0543649735d5
94b2a919-226c-49d7-b10f-951ca5fb9627	FRIDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	c33e01ef-be92-474a-9e43-0543649735d5
45745fa6-6618-475d-b000-5e4f2977a31e	MONDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	6f1475e5-0d8a-47c7-855f-ccff802aff85
de3136e0-3087-44fe-aaf2-cc430c50a37a	TUESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	6f1475e5-0d8a-47c7-855f-ccff802aff85
3da29128-adbc-401b-bec7-aff569a7104f	WEDNESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	6f1475e5-0d8a-47c7-855f-ccff802aff85
ee7bf9f4-4649-497a-848c-deab7aedbc0a	THURSDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	6f1475e5-0d8a-47c7-855f-ccff802aff85
b8cb6659-fccd-4f97-91d6-e5c07b34a3ae	FRIDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	6f1475e5-0d8a-47c7-855f-ccff802aff85
6087e42a-70f0-40e1-bf98-bd618955ab84	MONDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	58845959-0984-4136-a733-b75f5c9057b9
cdfcbdce-30e5-4561-bd04-0af6aa09d73d	TUESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	58845959-0984-4136-a733-b75f5c9057b9
2307785c-167e-41e0-9042-739c62b26da6	WEDNESDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	58845959-0984-4136-a733-b75f5c9057b9
1b4f1659-ef05-4e98-af00-2a16dd6314f7	THURSDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	58845959-0984-4136-a733-b75f5c9057b9
c5c3ef4e-191d-414c-aff2-65c0425f5877	FRIDAY	4a3d2b06-0f1d-44e0-8971-237e106d30ab	58845959-0984-4136-a733-b75f5c9057b9
a662dfc7-010f-4104-af44-c55cbf7540b7	MONDAY	b41edde6-b90c-47f6-a8bb-143a30990513	440c17ed-5262-4800-aa19-38e5285330c3
bb1baa95-a1bd-437a-9c11-2d5e74ec1333	TUESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	440c17ed-5262-4800-aa19-38e5285330c3
76e79f8e-b836-4e7e-9bec-46ebe29d33f9	WEDNESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	440c17ed-5262-4800-aa19-38e5285330c3
a74b3d7e-22e7-4e44-8217-1553a5d72203	THURSDAY	b41edde6-b90c-47f6-a8bb-143a30990513	440c17ed-5262-4800-aa19-38e5285330c3
e2111475-5358-42bb-b8a2-635f900c4ab3	FRIDAY	b41edde6-b90c-47f6-a8bb-143a30990513	440c17ed-5262-4800-aa19-38e5285330c3
deb310ef-561d-4d4d-9f5b-83a00dd187e7	MONDAY	b41edde6-b90c-47f6-a8bb-143a30990513	5f76a266-8d18-4da8-9732-b63d4d8f24a0
d6fa7e48-af3c-4cfd-a568-497ae1ae2923	TUESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1ca45bb0-93fe-48c3-9449-5ff8468f1315	WEDNESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	5f76a266-8d18-4da8-9732-b63d4d8f24a0
05d58287-a5b2-401a-bb2f-bd7876e1af0b	THURSDAY	b41edde6-b90c-47f6-a8bb-143a30990513	5f76a266-8d18-4da8-9732-b63d4d8f24a0
630914a1-9ee4-4523-9732-8eefcbdc2bba	FRIDAY	b41edde6-b90c-47f6-a8bb-143a30990513	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1851213b-5c59-46a4-96f0-36fe5024a794	MONDAY	b41edde6-b90c-47f6-a8bb-143a30990513	d54b130a-28a8-4bb1-9030-c474369c2205
ecc0f9c2-f21a-4aeb-808f-3dc89529c839	TUESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	d54b130a-28a8-4bb1-9030-c474369c2205
132d041d-b34a-4277-a3b9-ccabca0e654a	WEDNESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	d54b130a-28a8-4bb1-9030-c474369c2205
19afdbaa-f915-43a1-a0d1-6ee54aeb442d	THURSDAY	b41edde6-b90c-47f6-a8bb-143a30990513	d54b130a-28a8-4bb1-9030-c474369c2205
78626c70-8d9d-4207-9040-0b10c66430a7	FRIDAY	b41edde6-b90c-47f6-a8bb-143a30990513	d54b130a-28a8-4bb1-9030-c474369c2205
a358e77f-6a0b-4ac4-be95-0da47c80138d	MONDAY	b41edde6-b90c-47f6-a8bb-143a30990513	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
527c1324-431a-4205-a402-364240836888	TUESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
dc7c042d-ae1e-4308-acf2-092bd685ca36	WEDNESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
37210413-5f2c-4ed8-85f2-71a987d9811a	THURSDAY	b41edde6-b90c-47f6-a8bb-143a30990513	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
32707754-4ff7-4810-a39d-5b5b2ed85b72	FRIDAY	b41edde6-b90c-47f6-a8bb-143a30990513	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e052b90c-041f-41b5-9ee6-3defd809aabb	MONDAY	b41edde6-b90c-47f6-a8bb-143a30990513	c33e01ef-be92-474a-9e43-0543649735d5
cff9e45a-5874-461c-a23b-4fae9293aaf3	TUESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	c33e01ef-be92-474a-9e43-0543649735d5
c1a780b9-d2ef-40e1-a587-aabc32190ea0	WEDNESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	c33e01ef-be92-474a-9e43-0543649735d5
d60f8274-c592-4219-8eb2-14934fd8cea8	THURSDAY	b41edde6-b90c-47f6-a8bb-143a30990513	c33e01ef-be92-474a-9e43-0543649735d5
27e57b46-ac3d-4ab5-bd7e-1031b9fb730f	FRIDAY	b41edde6-b90c-47f6-a8bb-143a30990513	c33e01ef-be92-474a-9e43-0543649735d5
657f24f3-78b1-4d95-baf6-1555ecd1abfd	THURSDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	c33e01ef-be92-474a-9e43-0543649735d5
8b9572d5-da7c-4ee2-af47-d33796b97192	FRIDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	c33e01ef-be92-474a-9e43-0543649735d5
d8d5b8e7-0a61-4f4b-bb2d-c305d5d1fc1f	MONDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	6f1475e5-0d8a-47c7-855f-ccff802aff85
bde707c0-7fae-40ef-b89b-64e3b84f5b0b	TUESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	6f1475e5-0d8a-47c7-855f-ccff802aff85
33b6d145-4c2a-4191-a8f0-eebb9802abe7	WEDNESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	6f1475e5-0d8a-47c7-855f-ccff802aff85
c595be58-0f3d-4a2f-bbd7-c722e7987eee	THURSDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	6f1475e5-0d8a-47c7-855f-ccff802aff85
721bdd9d-daad-4d96-9b38-8aa4e3cdaa09	FRIDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	6f1475e5-0d8a-47c7-855f-ccff802aff85
d40ce20b-e117-4f9a-a7a0-90368a2786ea	MONDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	58845959-0984-4136-a733-b75f5c9057b9
747791c3-3128-4fe5-b12d-b80559a57a3f	TUESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	58845959-0984-4136-a733-b75f5c9057b9
73ac7e86-0b8a-46ba-b1d1-41ed7c8daf51	WEDNESDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	58845959-0984-4136-a733-b75f5c9057b9
556a1229-2b0d-4d0b-8edc-521a5a3ae866	THURSDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	58845959-0984-4136-a733-b75f5c9057b9
811e5301-a494-4fbc-a868-7659f5fd2ad6	FRIDAY	60873c6f-5448-4cc4-b180-a47f9e3f4b82	58845959-0984-4136-a733-b75f5c9057b9
880ea501-0511-4d7d-9c25-185a9cb5e8ce	MONDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	440c17ed-5262-4800-aa19-38e5285330c3
03b795ef-9056-4b7b-96cf-6658628ddbef	TUESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	440c17ed-5262-4800-aa19-38e5285330c3
2de5ceb2-f173-4b21-9d4e-c0f93c0c9b38	WEDNESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	440c17ed-5262-4800-aa19-38e5285330c3
3b2f5df5-9141-4852-9f83-a2ffe8f14cea	THURSDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	440c17ed-5262-4800-aa19-38e5285330c3
fdc26175-8017-4da9-b3e1-3a02db034f02	FRIDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	440c17ed-5262-4800-aa19-38e5285330c3
4abbff4d-1d24-4501-907f-53f500d1b3f0	MONDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4e9e7da9-ada0-4b2a-8e92-297271f6b12a	TUESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
b44c1e50-40fd-4803-8781-d3d834825463	WEDNESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
bbcbe969-fa3f-4aa8-80d4-3d74e1c3dc8c	THURSDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
ebc30ca4-8641-415a-a3c5-697f03e624e8	FRIDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1c723f79-e4f8-4b34-b68a-7e94402f1f52	MONDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	d54b130a-28a8-4bb1-9030-c474369c2205
613d099e-b3d5-4078-a24d-8c267ba22e23	TUESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	d54b130a-28a8-4bb1-9030-c474369c2205
baef3cfc-b4ff-4326-a322-090e8d6881ae	WEDNESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	d54b130a-28a8-4bb1-9030-c474369c2205
6cdf6dbd-3ec1-4332-945d-3fb7b5e86600	THURSDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	d54b130a-28a8-4bb1-9030-c474369c2205
b90fd632-f88a-4a53-9434-7d14e8232440	FRIDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	d54b130a-28a8-4bb1-9030-c474369c2205
5d8e9370-a064-47c1-bf2c-a8d68d1f6ef0	MONDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4e207a53-0f86-4e0c-8bcb-64c6a28cb0b4	TUESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
d86b3c4e-d8d5-4ded-8140-3d29f5fb3c51	WEDNESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c8c82bdf-68da-41a9-a927-80e6e367ee22	THURSDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
891ddab8-56a6-49e7-8b7c-e757359da34d	FRIDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
49cd5091-6268-4321-8426-de5f46cf8dc9	MONDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	c33e01ef-be92-474a-9e43-0543649735d5
3144a6d4-2990-4397-88de-caa611cf72fe	TUESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	c33e01ef-be92-474a-9e43-0543649735d5
263e7ab8-883c-4869-94cc-6f16818fe89d	WEDNESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	c33e01ef-be92-474a-9e43-0543649735d5
a06a4a38-37bb-4b8f-b670-9656cb574444	THURSDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	c33e01ef-be92-474a-9e43-0543649735d5
63260947-b823-4c32-bf3a-3af4f16448a4	FRIDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	c33e01ef-be92-474a-9e43-0543649735d5
5ca01ddc-ad55-4ccd-bf5e-f33f83c54edd	MONDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	6f1475e5-0d8a-47c7-855f-ccff802aff85
ce373726-c398-4146-817a-e942c1f584c1	TUESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	6f1475e5-0d8a-47c7-855f-ccff802aff85
9e84100f-765c-45af-b9bd-6d9268afe2e0	WEDNESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	6f1475e5-0d8a-47c7-855f-ccff802aff85
ae1c4364-3cfc-46e2-b933-afba7d167855	THURSDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	6f1475e5-0d8a-47c7-855f-ccff802aff85
2db2aaf7-ac7c-45ec-94ea-67abe7a3720c	FRIDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	6f1475e5-0d8a-47c7-855f-ccff802aff85
91ce2a9d-0dfd-4797-8580-97ac6b942f5c	MONDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	58845959-0984-4136-a733-b75f5c9057b9
5b3ee365-d50e-4225-85e7-73b4739a5df1	TUESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	58845959-0984-4136-a733-b75f5c9057b9
009fb849-84a2-4e97-a98b-3325c4d5f59c	WEDNESDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	58845959-0984-4136-a733-b75f5c9057b9
a87112be-1c1a-4015-bb12-58c9adc6892a	THURSDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	58845959-0984-4136-a733-b75f5c9057b9
165cfcd2-ac94-49a4-8d45-20b37c853c51	FRIDAY	eb98825a-95f0-4de3-9c74-71c32d9580a7	58845959-0984-4136-a733-b75f5c9057b9
90c3fea6-7968-426f-9533-45e014a2940e	MONDAY	deaf2b37-4a4d-4923-873b-1299b4464782	440c17ed-5262-4800-aa19-38e5285330c3
6955413c-e18f-499b-a50b-6a24e8a4553a	TUESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	440c17ed-5262-4800-aa19-38e5285330c3
7a004cc1-94f8-4fa7-8733-fc32b4580834	WEDNESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	440c17ed-5262-4800-aa19-38e5285330c3
06dcb869-f509-4f55-b3a0-e7205c1ecc39	THURSDAY	deaf2b37-4a4d-4923-873b-1299b4464782	440c17ed-5262-4800-aa19-38e5285330c3
fcf7973c-bf6e-4b68-a917-694840527625	FRIDAY	deaf2b37-4a4d-4923-873b-1299b4464782	440c17ed-5262-4800-aa19-38e5285330c3
b24349a0-d55a-4c6b-bca7-161c89ea8fd3	MONDAY	deaf2b37-4a4d-4923-873b-1299b4464782	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cf6448d5-b3c8-480d-81f0-0a4f67f06191	TUESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	5f76a266-8d18-4da8-9732-b63d4d8f24a0
4d740070-90a2-4a10-bf2a-0a664e198049	WEDNESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	5f76a266-8d18-4da8-9732-b63d4d8f24a0
0681211a-db17-45d5-9a36-2329a9d4bd93	THURSDAY	deaf2b37-4a4d-4923-873b-1299b4464782	5f76a266-8d18-4da8-9732-b63d4d8f24a0
db5b3cb5-d7dc-4c30-8b1f-9ad847865ff3	FRIDAY	deaf2b37-4a4d-4923-873b-1299b4464782	5f76a266-8d18-4da8-9732-b63d4d8f24a0
7081a406-a643-43c9-89c3-57d2d1926299	MONDAY	deaf2b37-4a4d-4923-873b-1299b4464782	d54b130a-28a8-4bb1-9030-c474369c2205
df1bc32a-d7b5-46e8-a90e-580cbb4e9264	TUESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	d54b130a-28a8-4bb1-9030-c474369c2205
7b8e1cd5-47fb-4d10-a748-22ab84cb563d	WEDNESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	d54b130a-28a8-4bb1-9030-c474369c2205
bf11c843-526e-4ce2-8a73-193a5bb68dc2	THURSDAY	deaf2b37-4a4d-4923-873b-1299b4464782	d54b130a-28a8-4bb1-9030-c474369c2205
81b79ae6-a713-4be0-b941-b0a4d191fe6d	FRIDAY	deaf2b37-4a4d-4923-873b-1299b4464782	d54b130a-28a8-4bb1-9030-c474369c2205
be528524-97ad-4533-a828-8ce4e1fa19ed	MONDAY	deaf2b37-4a4d-4923-873b-1299b4464782	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
540dc430-9158-4b1e-8fa0-02885dca46e2	TUESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
29ba2809-6355-4928-8706-e9e2e207d217	WEDNESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5fd0d25a-d733-46cc-9836-03f2a4e561f1	THURSDAY	deaf2b37-4a4d-4923-873b-1299b4464782	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
6858f659-4a09-4685-b2df-52aaabfb30c8	FRIDAY	deaf2b37-4a4d-4923-873b-1299b4464782	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
e94aab46-4b32-466e-8956-29b6ead788d6	MONDAY	deaf2b37-4a4d-4923-873b-1299b4464782	c33e01ef-be92-474a-9e43-0543649735d5
24c53ebb-b53b-4eab-8ef8-22c7af281d24	TUESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	c33e01ef-be92-474a-9e43-0543649735d5
3914c87d-aedf-4143-a473-9e9c957dcc4c	WEDNESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	c33e01ef-be92-474a-9e43-0543649735d5
12dc1f4e-ccb3-4c63-8bbf-8964eb2765d3	THURSDAY	deaf2b37-4a4d-4923-873b-1299b4464782	c33e01ef-be92-474a-9e43-0543649735d5
d10298fb-facf-42e3-95e5-86d5e3871e2b	FRIDAY	deaf2b37-4a4d-4923-873b-1299b4464782	c33e01ef-be92-474a-9e43-0543649735d5
e646e7ca-e4ff-4a66-9903-06ba79872f90	MONDAY	deaf2b37-4a4d-4923-873b-1299b4464782	6f1475e5-0d8a-47c7-855f-ccff802aff85
7b9295b3-8ae6-430d-b322-64bddb683084	TUESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	6f1475e5-0d8a-47c7-855f-ccff802aff85
777d7dd2-aaa5-4328-9ea4-8b3ce08df9d1	WEDNESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	6f1475e5-0d8a-47c7-855f-ccff802aff85
f38cb75f-1989-46c4-803c-2af7144384f8	THURSDAY	deaf2b37-4a4d-4923-873b-1299b4464782	6f1475e5-0d8a-47c7-855f-ccff802aff85
334a8a5a-17fd-4f6c-a027-bdc2055f4e04	FRIDAY	deaf2b37-4a4d-4923-873b-1299b4464782	6f1475e5-0d8a-47c7-855f-ccff802aff85
3a7f740b-aa65-4773-b97a-37cba1b858dc	MONDAY	deaf2b37-4a4d-4923-873b-1299b4464782	58845959-0984-4136-a733-b75f5c9057b9
1dd8a1ad-fdcf-4146-884c-b2ee5f40ff6d	TUESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	58845959-0984-4136-a733-b75f5c9057b9
d6f4e595-4339-4911-984c-66922e534766	WEDNESDAY	deaf2b37-4a4d-4923-873b-1299b4464782	58845959-0984-4136-a733-b75f5c9057b9
751bed7a-c57b-4f3c-916a-6336cda53587	THURSDAY	deaf2b37-4a4d-4923-873b-1299b4464782	58845959-0984-4136-a733-b75f5c9057b9
11bdeb14-a057-4a9f-8ddf-ba72c93c4b4c	FRIDAY	deaf2b37-4a4d-4923-873b-1299b4464782	58845959-0984-4136-a733-b75f5c9057b9
45bbbf0d-3d93-4a0b-b11d-3d19fe25a63c	MONDAY	b41edde6-b90c-47f6-a8bb-143a30990513	6f1475e5-0d8a-47c7-855f-ccff802aff85
9da4119e-7324-4263-a7d7-e5d17380fbb9	TUESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	6f1475e5-0d8a-47c7-855f-ccff802aff85
1989cd09-fa84-4526-a4fc-3bfe58c5f0d0	WEDNESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	6f1475e5-0d8a-47c7-855f-ccff802aff85
7cc341b3-6ad9-47ff-bb13-932f6e3bf554	THURSDAY	b41edde6-b90c-47f6-a8bb-143a30990513	6f1475e5-0d8a-47c7-855f-ccff802aff85
998c9dc9-0c38-40b1-b617-6225717d5d34	FRIDAY	b41edde6-b90c-47f6-a8bb-143a30990513	6f1475e5-0d8a-47c7-855f-ccff802aff85
d60f286b-f777-4ae9-88d5-405d6d9b0c1d	MONDAY	b41edde6-b90c-47f6-a8bb-143a30990513	58845959-0984-4136-a733-b75f5c9057b9
9c288c3f-13dd-40db-b0ba-afabad2bf2ee	TUESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	58845959-0984-4136-a733-b75f5c9057b9
dd5a1548-e3dc-4780-adc5-7e815d63e942	WEDNESDAY	b41edde6-b90c-47f6-a8bb-143a30990513	58845959-0984-4136-a733-b75f5c9057b9
d31d7a7e-207f-453d-b91d-9240dd2b07b9	THURSDAY	b41edde6-b90c-47f6-a8bb-143a30990513	58845959-0984-4136-a733-b75f5c9057b9
02870c70-743b-4796-ab3a-2d7bfd829ede	FRIDAY	b41edde6-b90c-47f6-a8bb-143a30990513	58845959-0984-4136-a733-b75f5c9057b9
97e2f0b1-40c9-45f1-b7e0-ce8558edc996	MONDAY	98883c70-e103-48b8-93ff-910511d0f86d	440c17ed-5262-4800-aa19-38e5285330c3
d348387f-3768-416d-9fe5-f24e7fecc2e7	TUESDAY	98883c70-e103-48b8-93ff-910511d0f86d	440c17ed-5262-4800-aa19-38e5285330c3
dc2e68b0-094e-4d29-9de8-6298fea16c51	WEDNESDAY	98883c70-e103-48b8-93ff-910511d0f86d	440c17ed-5262-4800-aa19-38e5285330c3
185ae8b6-ecc6-48a0-ac70-420d555a59aa	THURSDAY	98883c70-e103-48b8-93ff-910511d0f86d	440c17ed-5262-4800-aa19-38e5285330c3
168d2e84-6d57-4afe-b355-8e71ee700684	FRIDAY	98883c70-e103-48b8-93ff-910511d0f86d	440c17ed-5262-4800-aa19-38e5285330c3
6f3d88ff-bf77-4179-a2ae-d0d4ee983f6c	MONDAY	98883c70-e103-48b8-93ff-910511d0f86d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3b4831b5-d98b-4c80-9b76-05951622563b	TUESDAY	98883c70-e103-48b8-93ff-910511d0f86d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
703c2ce7-459c-4551-b139-39dbf2dd2618	WEDNESDAY	98883c70-e103-48b8-93ff-910511d0f86d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2e01ab12-948a-4f06-b83a-e3e81022a301	THURSDAY	98883c70-e103-48b8-93ff-910511d0f86d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
306b5bd3-592e-404e-b1a7-e75e38846967	FRIDAY	98883c70-e103-48b8-93ff-910511d0f86d	5f76a266-8d18-4da8-9732-b63d4d8f24a0
8d9a9625-06d5-4e3e-b2a9-52f84e91777a	MONDAY	98883c70-e103-48b8-93ff-910511d0f86d	d54b130a-28a8-4bb1-9030-c474369c2205
8d3a59dc-c01b-41a7-a4cb-86d1902660f1	TUESDAY	98883c70-e103-48b8-93ff-910511d0f86d	d54b130a-28a8-4bb1-9030-c474369c2205
d22711a9-0052-45a1-8113-b4f8000221c2	WEDNESDAY	98883c70-e103-48b8-93ff-910511d0f86d	d54b130a-28a8-4bb1-9030-c474369c2205
e09360c4-21be-49c6-8079-a2630565fef7	THURSDAY	98883c70-e103-48b8-93ff-910511d0f86d	d54b130a-28a8-4bb1-9030-c474369c2205
be7a7fbd-3812-4aca-aabb-a67c94cce902	FRIDAY	98883c70-e103-48b8-93ff-910511d0f86d	d54b130a-28a8-4bb1-9030-c474369c2205
e54d51f5-45da-4020-b6d3-aab5e95a84a6	MONDAY	98883c70-e103-48b8-93ff-910511d0f86d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
cd678261-d25f-4371-b5f7-97ab793f9ea1	TUESDAY	98883c70-e103-48b8-93ff-910511d0f86d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
9748d366-48a3-45ef-aef4-fd7dfa5a80d0	WEDNESDAY	98883c70-e103-48b8-93ff-910511d0f86d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5571c264-c2bd-44e6-a7dc-81dbc7d69be5	THURSDAY	98883c70-e103-48b8-93ff-910511d0f86d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
8b59e73d-9bcb-4a24-846f-ff5c8e02adff	FRIDAY	98883c70-e103-48b8-93ff-910511d0f86d	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4918230e-c305-4d28-a468-5efc73765601	MONDAY	98883c70-e103-48b8-93ff-910511d0f86d	c33e01ef-be92-474a-9e43-0543649735d5
0ed12bc1-546d-4eb8-b932-78749ec4a99b	TUESDAY	98883c70-e103-48b8-93ff-910511d0f86d	c33e01ef-be92-474a-9e43-0543649735d5
e02dbcee-a145-4186-8a90-4cf26e3c23b2	WEDNESDAY	98883c70-e103-48b8-93ff-910511d0f86d	c33e01ef-be92-474a-9e43-0543649735d5
f6b2d74f-ec0d-4d52-b509-df9b76f5aa69	THURSDAY	98883c70-e103-48b8-93ff-910511d0f86d	c33e01ef-be92-474a-9e43-0543649735d5
61dd497f-3639-43de-8420-b0cb78acfc38	FRIDAY	98883c70-e103-48b8-93ff-910511d0f86d	c33e01ef-be92-474a-9e43-0543649735d5
05121a8d-d9fb-4e40-b675-0ee27f4e0eb9	MONDAY	98883c70-e103-48b8-93ff-910511d0f86d	6f1475e5-0d8a-47c7-855f-ccff802aff85
a3188ddf-9c8b-4a55-b893-5a8570dc8be4	TUESDAY	98883c70-e103-48b8-93ff-910511d0f86d	6f1475e5-0d8a-47c7-855f-ccff802aff85
cae1f2f1-d940-4cdf-902a-a3140c7f0d59	WEDNESDAY	98883c70-e103-48b8-93ff-910511d0f86d	6f1475e5-0d8a-47c7-855f-ccff802aff85
b88ced41-4d94-4ffd-85d0-cc57bde2a448	THURSDAY	98883c70-e103-48b8-93ff-910511d0f86d	6f1475e5-0d8a-47c7-855f-ccff802aff85
b9180690-b833-4bfd-950d-7b640fd74221	FRIDAY	98883c70-e103-48b8-93ff-910511d0f86d	6f1475e5-0d8a-47c7-855f-ccff802aff85
c6ed020c-3ccc-4650-88d3-01e035c86fb5	MONDAY	98883c70-e103-48b8-93ff-910511d0f86d	58845959-0984-4136-a733-b75f5c9057b9
fecef18b-df31-454f-9edb-561ca5c45edd	TUESDAY	98883c70-e103-48b8-93ff-910511d0f86d	58845959-0984-4136-a733-b75f5c9057b9
b2ff4d16-0fd4-48da-b130-69c9ebf0ebb0	WEDNESDAY	98883c70-e103-48b8-93ff-910511d0f86d	58845959-0984-4136-a733-b75f5c9057b9
387eae27-fa40-4d6b-8abd-68bf1171dc7a	THURSDAY	98883c70-e103-48b8-93ff-910511d0f86d	58845959-0984-4136-a733-b75f5c9057b9
f5fc033c-b61d-4f9d-b418-4f89e65ae9ad	FRIDAY	98883c70-e103-48b8-93ff-910511d0f86d	58845959-0984-4136-a733-b75f5c9057b9
bbd4adde-d926-428f-bbe3-44206aba8050	MONDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	440c17ed-5262-4800-aa19-38e5285330c3
829c83ef-9335-4e0b-9ad2-b06b5904225b	TUESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	440c17ed-5262-4800-aa19-38e5285330c3
7066c9d1-0eba-474a-a06c-16edc9419dc3	WEDNESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	440c17ed-5262-4800-aa19-38e5285330c3
a38c43be-1ac9-4f74-be63-6830fcbb0632	THURSDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	440c17ed-5262-4800-aa19-38e5285330c3
08eded82-23a4-4b07-bd9c-d06d2d1dfb24	FRIDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	440c17ed-5262-4800-aa19-38e5285330c3
d3528aa9-ae80-4287-99c7-068d8a20674e	MONDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
2cc357d3-813c-4d39-b59c-65e2fdcd4e1d	TUESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
04875ea3-34dc-4aaf-88cc-80e8fadaf4f9	WEDNESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
85611200-3f05-4fed-95da-36d15f8b3564	THURSDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fc345bd3-9a96-4f9d-a564-1489b2c2f721	FRIDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
839daead-6317-48a7-97df-fa428ea96eff	MONDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	d54b130a-28a8-4bb1-9030-c474369c2205
76139798-f0c7-4f87-b59b-a117a9f81028	TUESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	d54b130a-28a8-4bb1-9030-c474369c2205
403b29f1-1588-4ad6-8c10-0ad960558ccc	WEDNESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	d54b130a-28a8-4bb1-9030-c474369c2205
32b2258c-2aa3-4adc-8971-ac8fc9af9261	THURSDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	d54b130a-28a8-4bb1-9030-c474369c2205
fb3ca98e-cf1d-439e-8e1c-27a25ca9de0b	FRIDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	d54b130a-28a8-4bb1-9030-c474369c2205
730b5153-e305-453d-9ee5-60078cdad699	MONDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
5fed999d-6e36-4ef0-8238-9ce98b923a47	TUESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
95434e81-0aa3-4a9b-b166-c4317af4a54d	WEDNESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
c55f0d6c-901e-4f2e-84a6-f1c1e9f5ae2c	THURSDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
36222eff-fc06-44fb-8387-c825a66a65cf	FRIDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
39075e27-97a0-4c9f-96cd-7fd6addeb90d	MONDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	c33e01ef-be92-474a-9e43-0543649735d5
664a2893-581d-4957-bf9c-2c2fd7b39ffd	TUESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	c33e01ef-be92-474a-9e43-0543649735d5
08ffc968-29e9-43d3-87f8-d0b801738f62	WEDNESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	c33e01ef-be92-474a-9e43-0543649735d5
0f94cc63-02b1-49d6-83a1-4712935fdf15	THURSDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	c33e01ef-be92-474a-9e43-0543649735d5
6e30668d-f3b5-4705-9120-c132d5e49d72	FRIDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	c33e01ef-be92-474a-9e43-0543649735d5
fa04f820-8d65-4f38-9be2-c3cc4b3a91ea	MONDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	6f1475e5-0d8a-47c7-855f-ccff802aff85
5d4c3aad-cabd-4638-aa15-23ef349ab2b6	TUESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	6f1475e5-0d8a-47c7-855f-ccff802aff85
6b35c00a-345c-4559-af08-e562ff1d2c36	WEDNESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	6f1475e5-0d8a-47c7-855f-ccff802aff85
21de8970-d296-459c-b7a5-6c1d1331ca24	THURSDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	6f1475e5-0d8a-47c7-855f-ccff802aff85
bff68595-5a9a-4c04-93a3-00c733dfd06c	FRIDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	6f1475e5-0d8a-47c7-855f-ccff802aff85
1e2d37f5-2ab9-4783-a416-4406b3c4634e	MONDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	58845959-0984-4136-a733-b75f5c9057b9
0027db80-15e0-40f7-8942-5417ee50e934	TUESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	58845959-0984-4136-a733-b75f5c9057b9
7d884d36-75b8-4448-838c-d6bdeb9a35a9	WEDNESDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	58845959-0984-4136-a733-b75f5c9057b9
e987e23d-c565-4170-953b-57f072252c12	THURSDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	58845959-0984-4136-a733-b75f5c9057b9
98d1e1db-f3de-4f14-84c5-5e911559f9da	FRIDAY	0b71b641-82cb-4c00-8c51-5c522188a59e	58845959-0984-4136-a733-b75f5c9057b9
b32fbf62-a5fe-4080-b4c7-f642d29d2efb	MONDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	440c17ed-5262-4800-aa19-38e5285330c3
3c7fd196-07e5-463b-97a0-ef0ed8220b43	TUESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	440c17ed-5262-4800-aa19-38e5285330c3
c57e5555-6362-481f-9d4b-9ba0fa41cc61	WEDNESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	440c17ed-5262-4800-aa19-38e5285330c3
f764067b-8f16-4836-ab79-34abb8c55d9b	THURSDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	440c17ed-5262-4800-aa19-38e5285330c3
b98b8f0e-88a5-47a0-97d6-c54f51147921	FRIDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	440c17ed-5262-4800-aa19-38e5285330c3
81505e0e-289b-41d3-ae7f-9d717d4ab84c	MONDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
1d75c0c5-7429-4b48-bf82-fa45b95020c8	TUESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
cf06f6d6-445e-4bde-9bd6-73d521868540	WEDNESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
69377497-9c72-408a-aa83-e6d665e7e124	THURSDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
3da93bc0-6b76-4beb-b683-f4c8f65c4ad0	FRIDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9565e5f7-da50-4073-a788-884f3bcc5119	MONDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	d54b130a-28a8-4bb1-9030-c474369c2205
eac57f58-c61c-4f1c-8e72-5cff806f97d5	TUESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	d54b130a-28a8-4bb1-9030-c474369c2205
99275070-1a31-47ac-9f6c-812c193e7643	WEDNESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	d54b130a-28a8-4bb1-9030-c474369c2205
45db066a-ac5c-479e-9d75-a81848aac35c	THURSDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	d54b130a-28a8-4bb1-9030-c474369c2205
ef88a978-0379-4c01-bc62-be0d21f88d8b	FRIDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	d54b130a-28a8-4bb1-9030-c474369c2205
29441ba6-7723-4137-a113-4b20ca58c64c	MONDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
f08aba73-f7d9-4fa8-b477-8eb62a11d6a0	TUESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
97f58d19-4d16-4ae2-ba98-731edd1e6f06	WEDNESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
10d6cdda-55bb-4b9e-a31c-819d74eaa2cb	THURSDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
7971218e-fd04-4795-9a89-d8fcd74d22cd	FRIDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
bf54c025-459b-4198-8cc7-ae69d7889522	MONDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	c33e01ef-be92-474a-9e43-0543649735d5
0579a5ba-542c-4c10-a744-be1269bb4634	TUESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	c33e01ef-be92-474a-9e43-0543649735d5
11fa6cbd-4c3d-4dd9-b22c-7eab4b139b11	WEDNESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	c33e01ef-be92-474a-9e43-0543649735d5
ba213dff-a9f5-4528-a350-9829f557ed05	THURSDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	c33e01ef-be92-474a-9e43-0543649735d5
0ea9ca05-7a09-4308-bc32-085918c753af	FRIDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	c33e01ef-be92-474a-9e43-0543649735d5
07a6bbfe-c0bf-4ff3-8eea-e5efcef9a826	MONDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	6f1475e5-0d8a-47c7-855f-ccff802aff85
1c43bbe6-25e6-4a15-a630-6ed52ac17ceb	TUESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	6f1475e5-0d8a-47c7-855f-ccff802aff85
959ff2b2-011a-4b63-91ba-7039f3ab0bd0	WEDNESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	6f1475e5-0d8a-47c7-855f-ccff802aff85
6aa6a8ff-7576-4619-ace8-ed91b0780f0b	THURSDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	6f1475e5-0d8a-47c7-855f-ccff802aff85
d8cb2cca-d9c6-4b93-aa30-30eb0ea03fc3	FRIDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	6f1475e5-0d8a-47c7-855f-ccff802aff85
547f4e1c-ea20-44cf-a9f8-2b12350abd4c	MONDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	58845959-0984-4136-a733-b75f5c9057b9
99f1c1f3-6fae-412e-b75e-f334f145caeb	TUESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	58845959-0984-4136-a733-b75f5c9057b9
13134925-9eb5-4ccc-8479-5c120c641f61	WEDNESDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	58845959-0984-4136-a733-b75f5c9057b9
6ca04b96-9c4e-45e3-825c-2b260c1b6a4f	THURSDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	58845959-0984-4136-a733-b75f5c9057b9
5e04adb8-b21c-48dd-89bf-6cff23e74942	FRIDAY	c5dd911f-43d7-4ccd-82cc-afcb1d65602e	58845959-0984-4136-a733-b75f5c9057b9
fdfa8dcd-fb3f-496d-ba21-fbe00f1c5b5d	MONDAY	703da33b-9830-4322-a1b5-2018d76a624f	440c17ed-5262-4800-aa19-38e5285330c3
d6ce06b2-4ad6-4b9a-b25d-d26db171da30	TUESDAY	703da33b-9830-4322-a1b5-2018d76a624f	440c17ed-5262-4800-aa19-38e5285330c3
bb4ed494-3652-45b4-a8b3-f586ffdac646	WEDNESDAY	703da33b-9830-4322-a1b5-2018d76a624f	440c17ed-5262-4800-aa19-38e5285330c3
974d8873-7cd3-47e7-9a72-9e20611ee365	THURSDAY	703da33b-9830-4322-a1b5-2018d76a624f	440c17ed-5262-4800-aa19-38e5285330c3
ab89e40e-7658-4373-a0b8-82a8587f56d9	FRIDAY	703da33b-9830-4322-a1b5-2018d76a624f	440c17ed-5262-4800-aa19-38e5285330c3
ea0fe03a-7509-4426-a271-5eee485767e9	MONDAY	703da33b-9830-4322-a1b5-2018d76a624f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
fc48390d-025d-4829-84dd-268697c6eebf	TUESDAY	703da33b-9830-4322-a1b5-2018d76a624f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9e975361-1413-474c-bc03-e7ea1aa14332	WEDNESDAY	703da33b-9830-4322-a1b5-2018d76a624f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9611fe62-ae16-4a07-87fc-b246cbee2bee	THURSDAY	703da33b-9830-4322-a1b5-2018d76a624f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
63fb5640-31b1-45ac-b18d-c7865334beb2	FRIDAY	703da33b-9830-4322-a1b5-2018d76a624f	5f76a266-8d18-4da8-9732-b63d4d8f24a0
9333807f-78be-4cf5-84dd-de142023de32	MONDAY	703da33b-9830-4322-a1b5-2018d76a624f	d54b130a-28a8-4bb1-9030-c474369c2205
18d845ea-3e9e-4cab-a55c-2dc29d168079	TUESDAY	703da33b-9830-4322-a1b5-2018d76a624f	d54b130a-28a8-4bb1-9030-c474369c2205
766d49f4-a275-412a-b26f-93beb294fcf1	WEDNESDAY	703da33b-9830-4322-a1b5-2018d76a624f	d54b130a-28a8-4bb1-9030-c474369c2205
7eeda085-9e41-4aa4-aa1e-b074be8aa656	THURSDAY	703da33b-9830-4322-a1b5-2018d76a624f	d54b130a-28a8-4bb1-9030-c474369c2205
43a3f42e-7cb3-437d-8585-2deef0106193	FRIDAY	703da33b-9830-4322-a1b5-2018d76a624f	d54b130a-28a8-4bb1-9030-c474369c2205
79ae4246-41ca-49c6-a4d2-761c23456b8c	MONDAY	703da33b-9830-4322-a1b5-2018d76a624f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
42e77f11-b334-4979-8a6b-d1c144652b93	TUESDAY	703da33b-9830-4322-a1b5-2018d76a624f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
867fb28b-b080-4f61-ac02-a17dad48d742	WEDNESDAY	703da33b-9830-4322-a1b5-2018d76a624f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
4939fa17-765a-4c4f-93a4-dbf5756164c6	THURSDAY	703da33b-9830-4322-a1b5-2018d76a624f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
fd1fe501-f74b-4c59-a42e-124f3f07d77d	FRIDAY	703da33b-9830-4322-a1b5-2018d76a624f	dd55e7cd-195f-4d3c-a88c-2439ed3b855b
1f9cb998-85ae-426e-8a4d-f800a7b4e421	MONDAY	703da33b-9830-4322-a1b5-2018d76a624f	c33e01ef-be92-474a-9e43-0543649735d5
d5072472-bc26-4329-9bc8-8de0bbb0f8b8	TUESDAY	703da33b-9830-4322-a1b5-2018d76a624f	c33e01ef-be92-474a-9e43-0543649735d5
fd360e91-42b4-497f-9dcb-2c0643920e79	WEDNESDAY	703da33b-9830-4322-a1b5-2018d76a624f	c33e01ef-be92-474a-9e43-0543649735d5
c72e6fc2-8a72-4b6f-b7a1-0adb02b62d03	THURSDAY	703da33b-9830-4322-a1b5-2018d76a624f	c33e01ef-be92-474a-9e43-0543649735d5
9fa53048-1e31-45c4-b85e-10f39693451c	FRIDAY	703da33b-9830-4322-a1b5-2018d76a624f	c33e01ef-be92-474a-9e43-0543649735d5
d8c7c248-5e8e-4918-9405-d9412ff86855	MONDAY	703da33b-9830-4322-a1b5-2018d76a624f	6f1475e5-0d8a-47c7-855f-ccff802aff85
18b05731-029b-46e5-9d08-ea0e1848a66f	TUESDAY	703da33b-9830-4322-a1b5-2018d76a624f	6f1475e5-0d8a-47c7-855f-ccff802aff85
fafeb241-1da2-427d-aa77-d148a89e43b6	WEDNESDAY	703da33b-9830-4322-a1b5-2018d76a624f	6f1475e5-0d8a-47c7-855f-ccff802aff85
a2bf907c-bb67-44cd-a8f2-e0007db9cc0f	THURSDAY	703da33b-9830-4322-a1b5-2018d76a624f	6f1475e5-0d8a-47c7-855f-ccff802aff85
78df5c9a-e4b4-40c6-bd83-932a4a557575	FRIDAY	703da33b-9830-4322-a1b5-2018d76a624f	6f1475e5-0d8a-47c7-855f-ccff802aff85
8219869e-aa5a-489d-aef6-aff3280957fb	MONDAY	703da33b-9830-4322-a1b5-2018d76a624f	58845959-0984-4136-a733-b75f5c9057b9
b23421b1-80fd-4f5c-81bf-4acf9cc00af9	TUESDAY	703da33b-9830-4322-a1b5-2018d76a624f	58845959-0984-4136-a733-b75f5c9057b9
0e141fb0-1c1c-46fd-97f5-a73868ab0b43	WEDNESDAY	703da33b-9830-4322-a1b5-2018d76a624f	58845959-0984-4136-a733-b75f5c9057b9
b20866cf-0cba-437f-bfed-5d8164127949	THURSDAY	703da33b-9830-4322-a1b5-2018d76a624f	58845959-0984-4136-a733-b75f5c9057b9
46af868d-6741-4ce1-a88e-5f623aec37e8	FRIDAY	703da33b-9830-4322-a1b5-2018d76a624f	58845959-0984-4136-a733-b75f5c9057b9
\.


--
-- Name: course_events course_events_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_events
    ADD CONSTRAINT course_events_pkey PRIMARY KEY (id);


--
-- Name: course_relations course_relations_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_relations
    ADD CONSTRAINT course_relations_pkey PRIMARY KEY (id);


--
-- Name: course_timeslots course_timeslots_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_timeslots
    ADD CONSTRAINT course_timeslots_pkey PRIMARY KEY (id);


--
-- Name: course_types course_types_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_types
    ADD CONSTRAINT course_types_pkey PRIMARY KEY (id);


--
-- Name: courses courses_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (id);


--
-- Name: degree_semesters degree_semesters_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.degree_semesters
    ADD CONSTRAINT degree_semesters_pkey PRIMARY KEY (id);


--
-- Name: degrees degrees_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.degrees
    ADD CONSTRAINT degrees_pkey PRIMARY KEY (id);


--
-- Name: employee_types employee_types_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.employee_types
    ADD CONSTRAINT employee_types_pkey PRIMARY KEY (id);


--
-- Name: employees employees_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);


--
-- Name: room_types room_types_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.room_types
    ADD CONSTRAINT room_types_pkey PRIMARY KEY (id);


--
-- Name: roomcombinations roomcombinations_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.roomcombinations
    ADD CONSTRAINT roomcombinations_pkey PRIMARY KEY (id);


--
-- Name: rooms rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (id);


--
-- Name: school_types school_types_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.school_types
    ADD CONSTRAINT school_types_pkey PRIMARY KEY (id);


--
-- Name: semester_types semester_types_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.semester_types
    ADD CONSTRAINT semester_types_pkey PRIMARY KEY (id);


--
-- Name: special_events special_events_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.special_events
    ADD CONSTRAINT special_events_pkey PRIMARY KEY (id);


--
-- Name: timeslots timeslots_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.timeslots
    ADD CONSTRAINT timeslots_pkey PRIMARY KEY (id);


--
-- Name: timetables timetables_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.timetables
    ADD CONSTRAINT timetables_pkey PRIMARY KEY (id);


--
-- Name: semester_types uk4m1r77usgewdcpsad1kgouey9; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.semester_types
    ADD CONSTRAINT uk4m1r77usgewdcpsad1kgouey9 UNIQUE (name);


--
-- Name: school_types uk6bi4x3c9chk0aitqjq7kfl9hw; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.school_types
    ADD CONSTRAINT uk6bi4x3c9chk0aitqjq7kfl9hw UNIQUE (name);


--
-- Name: timeslots uk75eefm0emowsbiqkpnl690lp4; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.timeslots
    ADD CONSTRAINT uk75eefm0emowsbiqkpnl690lp4 UNIQUE (fk_timetable_id, index);


--
-- Name: users uk_r43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: courses ukakrmvnqqghuncsig8sd2wfq0r; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT ukakrmvnqqghuncsig8sd2wfq0r UNIQUE (fk_timetable_id, casid);


--
-- Name: room_types ukb70k1tp1aa52elkkxht660u36; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.room_types
    ADD CONSTRAINT ukb70k1tp1aa52elkkxht660u36 UNIQUE (name);


--
-- Name: employees ukcjxhtq02d4uti3dsybw7fcr6n; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT ukcjxhtq02d4uti3dsybw7fcr6n UNIQUE (fk_timetable_id, abbreviation);


--
-- Name: course_relations ukl7x9raotlw28mqgk7oke7qxbw; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_relations
    ADD CONSTRAINT ukl7x9raotlw28mqgk7oke7qxbw UNIQUE (relation_type, fk_course_a_id, fk_course_b_id);


--
-- Name: rooms ukmdohenrdph391nmrwunoehpyc; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT ukmdohenrdph391nmrwunoehpyc UNIQUE (fk_timetable_id, identifier);


--
-- Name: course_types ukovgh66bavcgkuh88gsebdexjk; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_types
    ADD CONSTRAINT ukovgh66bavcgkuh88gsebdexjk UNIQUE (name);


--
-- Name: employee_types ukrl6vfmhhwmdmufp2scrjnj7mp; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.employee_types
    ADD CONSTRAINT ukrl6vfmhhwmdmufp2scrjnj7mp UNIQUE (name);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: worktimes worktimes_pkey; Type: CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.worktimes
    ADD CONSTRAINT worktimes_pkey PRIMARY KEY (id);


--
-- Name: roomcombinations_rooms fk1gkw9rsj89s6x4ibt10kqu67q; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.roomcombinations_rooms
    ADD CONSTRAINT fk1gkw9rsj89s6x4ibt10kqu67q FOREIGN KEY (fk_roomcombination_id) REFERENCES public.roomcombinations(id);


--
-- Name: worktimes fk1me2yf9lmn79f6b25dvq9ct0h; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.worktimes
    ADD CONSTRAINT fk1me2yf9lmn79f6b25dvq9ct0h FOREIGN KEY (fk_timeslot_id) REFERENCES public.timeslots(id);


--
-- Name: course_relations fk4dokgm2ygkf6hnkq39qoa0kci; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_relations
    ADD CONSTRAINT fk4dokgm2ygkf6hnkq39qoa0kci FOREIGN KEY (fk_course_a_id) REFERENCES public.courses(id);


--
-- Name: roomcombinations_rooms fk4hos9w928yog2sx3vka3g0hsb; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.roomcombinations_rooms
    ADD CONSTRAINT fk4hos9w928yog2sx3vka3g0hsb FOREIGN KEY (fk_rooms_id) REFERENCES public.rooms(id);


--
-- Name: degrees fk4sq2h7tgad2i0cp9y4vdvgp2j; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.degrees
    ADD CONSTRAINT fk4sq2h7tgad2i0cp9y4vdvgp2j FOREIGN KEY (fk_room_type_id) REFERENCES public.school_types(id);


--
-- Name: employees fk5tupjc08qgmvystdximd7wnqu; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT fk5tupjc08qgmvystdximd7wnqu FOREIGN KEY (fk_timetable_id) REFERENCES public.timetables(id);


--
-- Name: courses fk68aqexjbca39jxtnj7i4jav59; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT fk68aqexjbca39jxtnj7i4jav59 FOREIGN KEY (fk_room_type_id) REFERENCES public.course_types(id);


--
-- Name: week_events_timeslots fk6wjhqj3412qwmag2idi19b8ud; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.week_events_timeslots
    ADD CONSTRAINT fk6wjhqj3412qwmag2idi19b8ud FOREIGN KEY (week_event_id) REFERENCES public.course_events(id);


--
-- Name: course_events fk9ivtlh0wbaw8fayk0pbdsgtiw; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_events
    ADD CONSTRAINT fk9ivtlh0wbaw8fayk0pbdsgtiw FOREIGN KEY (course_id) REFERENCES public.courses(id);


--
-- Name: course_relations fkatnm3v9rea5r9hqhlk7irvawr; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_relations
    ADD CONSTRAINT fkatnm3v9rea5r9hqhlk7irvawr FOREIGN KEY (fk_course_b_id) REFERENCES public.courses(id);


--
-- Name: worktimes fkcjil9c13wmtx6sjxyajfssmdm; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.worktimes
    ADD CONSTRAINT fkcjil9c13wmtx6sjxyajfssmdm FOREIGN KEY (fk_employee_id) REFERENCES public.employees(id);


--
-- Name: rooms fkdf39hi52lc2mw2yto0stk1r1g; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT fkdf39hi52lc2mw2yto0stk1r1g FOREIGN KEY (fk_room_type_id) REFERENCES public.room_types(id);


--
-- Name: special_events fkdkkhr8b7i4dlsukqs9k8iivpx; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.special_events
    ADD CONSTRAINT fkdkkhr8b7i4dlsukqs9k8iivpx FOREIGN KEY (fk_timetable_id) REFERENCES public.timetables(id);


--
-- Name: week_events_rooms fkdr5q75fijx97s0300nemvk4ve; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.week_events_rooms
    ADD CONSTRAINT fkdr5q75fijx97s0300nemvk4ve FOREIGN KEY (room_id) REFERENCES public.rooms(id);


--
-- Name: course_lecturers fkdu5yu1nafbbtwv7d3fy4pvnh3; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_lecturers
    ADD CONSTRAINT fkdu5yu1nafbbtwv7d3fy4pvnh3 FOREIGN KEY (fk_employee_id) REFERENCES public.employees(id);


--
-- Name: courses fkduxpyovdue3qluanbfxkl9f25; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT fkduxpyovdue3qluanbfxkl9f25 FOREIGN KEY (fk_timetable_id) REFERENCES public.timetables(id);


--
-- Name: course_timeslots fkg36dckkcxye3my27pps3nnr80; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_timeslots
    ADD CONSTRAINT fkg36dckkcxye3my27pps3nnr80 FOREIGN KEY (fk_course_id) REFERENCES public.courses(id);


--
-- Name: degreesemester_course fkgoyvxr4edkke6fp3qc7c38blj; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.degreesemester_course
    ADD CONSTRAINT fkgoyvxr4edkke6fp3qc7c38blj FOREIGN KEY (fk_course_id) REFERENCES public.courses(id);


--
-- Name: roomcombinations fkgvi2ufwlqmvi75dltbfft24va; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.roomcombinations
    ADD CONSTRAINT fkgvi2ufwlqmvi75dltbfft24va FOREIGN KEY (fk_event_id) REFERENCES public.courses(id);


--
-- Name: degrees fkhvdokv184expnnryi3bgtm3j4; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.degrees
    ADD CONSTRAINT fkhvdokv184expnnryi3bgtm3j4 FOREIGN KEY (fk_timetable_id) REFERENCES public.timetables(id);


--
-- Name: degree_semesters fkjijtb3w08368m12kw07yqfhus; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.degree_semesters
    ADD CONSTRAINT fkjijtb3w08368m12kw07yqfhus FOREIGN KEY (fk_degree_id) REFERENCES public.degrees(id);


--
-- Name: timeslots fkjwmripm2velb1gbijrruemfom; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.timeslots
    ADD CONSTRAINT fkjwmripm2velb1gbijrruemfom FOREIGN KEY (fk_timetable_id) REFERENCES public.timetables(id);


--
-- Name: rooms fkl3mlv910gj7yqkq8iqvu67lf2; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT fkl3mlv910gj7yqkq8iqvu67lf2 FOREIGN KEY (fk_timetable_id) REFERENCES public.timetables(id);


--
-- Name: employees fklhkwlyjfyyp43p0a4ddy16p4n; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT fklhkwlyjfyyp43p0a4ddy16p4n FOREIGN KEY (fk_employee_type_id) REFERENCES public.employee_types(id);


--
-- Name: timetables fklijmpt280vox1siag3soua6ab; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.timetables
    ADD CONSTRAINT fklijmpt280vox1siag3soua6ab FOREIGN KEY (fk_semester_type_id) REFERENCES public.semester_types(id);


--
-- Name: course_timeslots fklis9veuh83ikct4e38a3dn5nt; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_timeslots
    ADD CONSTRAINT fklis9veuh83ikct4e38a3dn5nt FOREIGN KEY (fk_timeslot_id) REFERENCES public.timeslots(id);


--
-- Name: week_events_rooms fklyg0l79yhptnyg9lrumvciiw3; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.week_events_rooms
    ADD CONSTRAINT fklyg0l79yhptnyg9lrumvciiw3 FOREIGN KEY (week_event_id) REFERENCES public.course_events(id);


--
-- Name: week_events_timeslots fkm57mdpn4w93o92tncb41yrgh2; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.week_events_timeslots
    ADD CONSTRAINT fkm57mdpn4w93o92tncb41yrgh2 FOREIGN KEY (timeslot_id) REFERENCES public.timeslots(id);


--
-- Name: degreesemester_course fkn48tvo7sc5w7m87xmdnnwubc; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.degreesemester_course
    ADD CONSTRAINT fkn48tvo7sc5w7m87xmdnnwubc FOREIGN KEY (fk_degreesemester_id) REFERENCES public.degree_semesters(id);


--
-- Name: course_lecturers fkng1jvjmdw5y9sm9hx5y8yv1nk; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_lecturers
    ADD CONSTRAINT fkng1jvjmdw5y9sm9hx5y8yv1nk FOREIGN KEY (fk_course_id) REFERENCES public.courses(id);


--
-- Name: course_events fkpf6kask75ucr4xde3bl1wh0q7; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.course_events
    ADD CONSTRAINT fkpf6kask75ucr4xde3bl1wh0q7 FOREIGN KEY (timetable_id) REFERENCES public.timetables(id);


--
-- Name: degree_semesters fktn23bn0xajqkm9v5aq2di3b9s; Type: FK CONSTRAINT; Schema: public; Owner: db_username
--

ALTER TABLE ONLY public.degree_semesters
    ADD CONSTRAINT fktn23bn0xajqkm9v5aq2di3b9s FOREIGN KEY (fk_timetable_id) REFERENCES public.timetables(id);


--
-- PostgreSQL database dump complete
--

