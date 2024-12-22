INSERT INTO
	semester_types (
		id,
		"name"
	)
VALUES
	(
		'753e4220-70f7-44c2-af17-1ee287949699',
		'Wintersemester'
	),
	(
		'019abc8f-edf3-4218-9075-3b826c8ee596',
		'Sommersemester'
	) ON CONFLICT DO NOTHING;

INSERT INTO
	timetables (
		id,
		end_date,
		"name",
		number_of_weeks,
		"start_date",
		fk_semester_type_id
	)
VALUES
	(
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0',
		'2023-07-14',
		'Sommersemester 23',
		12,
		'2023-04-12',
		'753e4220-70f7-44c2-af17-1ee287949699'
	) ON CONFLICT DO NOTHING;



-- INSERT INTO
-- 	degrees (
-- 		id,
-- 		name,
-- 		school_type,
-- 		semester_amount,
-- 		short_name,
-- 		study_regulation
-- 	)
-- VALUES
-- 	(
-- 		'88f3a2f9-176b-40d7-0004-010000000000',
-- 		'Bachelor Informatik',
-- 		'fh',
-- 		7,
-- 		'B_Inf',
-- 		'20.0'
-- 	);
-- INSERT INTO
-- 	degree_semesters(
-- 		id,
-- 		attendees,
-- 		extension_name,
-- 		semester_number,
-- 		fk_degree_id
-- 	)
-- VALUES
-- 	(
-- 		'88f3a2f9-176b-40d7-0004-010000000001',
-- 		0,
-- 		'B_Inf-20.0-1',
-- 		1,
-- 		'88f3a2f9-176b-40d7-0004-010000000000'
-- 	),
-- 	(
-- 		'88f3a2f9-176b-40d7-0004-010000000002',
-- 		0,
-- 		'B_Inf-20.0-2',
-- 		2,
-- 		'88f3a2f9-176b-40d7-0004-010000000000'
-- 	),
-- 	(
-- 		'88f3a2f9-176b-40d7-0004-010000000003',
-- 		0,
-- 		'B_Inf-20.0-3',
-- 		3,
-- 		'88f3a2f9-176b-40d7-0004-010000000000'
-- 	),
-- 	(
-- 		'88f3a2f9-176b-40d7-0004-010000000004',
-- 		0,
-- 		'B_Inf-20.0-4',
-- 		4,
-- 		'88f3a2f9-176b-40d7-0004-010000000000'
-- 	),
-- 	(
-- 		'88f3a2f9-176b-40d7-0004-010000000005',
-- 		0,
-- 		'B_Inf-20.0-5',
-- 		5,
-- 		'88f3a2f9-176b-40d7-0004-010000000000'
-- 	),
-- 	(
-- 		'88f3a2f9-176b-40d7-0004-010000000006',
-- 		0,
-- 		'B_Inf-20.0-6',
-- 		6,
-- 		'88f3a2f9-176b-40d7-0004-010000000000'
-- 	),
-- 	(
-- 		'88f3a2f9-176b-40d7-0004-010000000007',
-- 		0,
-- 		'B_Inf-20.0-7',
-- 		7,
-- 		'88f3a2f9-176b-40d7-0004-010000000000'
-- 	);
/*
 rooms: id:uuid abbreviationstring capacity:integer identifier:string name:string room_type:string 
 weekdays: id:uuid index:integer name:string shortname:string
 employees id:uuid abbreviation:string employee_type:string firstname:string lastname:string
 timeslots: id:uuid index:number start_time:date end_time:date
 courses: id:uuid abbreviationstring block_size:integer casid:string course_type:string description:string name:string slots_per_week:integer week_per_semester:integer
 
 
 
 
 degrees: id:uuid name:integer school_type:string semester_amount:integer short_name:string study_regulation:string
 degree_semesters id:uuid attendees:integer extension_name:string semester_number:integer fk_degree_id:uuid
 
 
 course_lecturers: fk_course_id:uuid fk_employee_id:uuid
 
 
 eventrelations: id:uuid relation_type:string fk_coursea_id:uuid fk_courseb_id:uuid
 
 roomcombinations: id:uuid fk_event_id:uuid 
 roomcombinations_rooms: fk_roomcombination_id:uuid fk_rooms_id: uuid
 semester_courses: fk_semester_id :uuid fk_course_id :uuid
 worktimes: id:uuid fk_employee_id:uuid fk_timeslot_id:uuid fk_weekday_id:uuid 
 */