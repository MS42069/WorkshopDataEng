INSERT INTO
	room_types (
		id,
		"name"
	)
VALUES
	(
		'f0a245f4-82a3-4981-9607-8d611bbb1800',
		'LECTURE_HALL'
	),
	(
		'88d06be0-af95-4c39-a809-2523951dddb7',
		'COMPUTING_CENTRE'
	),
	(
		'ed6c1626-82a6-4b07-bd69-33fd5b388318',
		'SEMINAR_ROOM'
	) ON CONFLICT DO NOTHING;


INSERT INTO
	rooms (
		id,
		abbreviation,
		capacity,
		identifier,
		"name",
		fk_room_type_id,
		fk_timetable_id
	)
VALUES
	(
		'88f3a2f9-176b-40d7-0000-000000000001',
		'HS1',
		0,
		'CS.01',
		'Hörsaal 1',
		'f0a245f4-82a3-4981-9607-8d611bbb1800',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-000000000002',
		'HS2',
		0,
		'CS.02',
		'Hörsaal 2',
		'f0a245f4-82a3-4981-9607-8d611bbb1800',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-000000000003',
		'HS3',
		0,
		'CS.03',
		'Hörsaal 3',
		'f0a245f4-82a3-4981-9607-8d611bbb1800',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-000000000004',
		'HS4',
		0,
		'CS.04',
		'Hörsaal 4',
		'f0a245f4-82a3-4981-9607-8d611bbb1800',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-000000000005',
		'HS5',
		20,
		'CS.05',
		'Hörsaal 5',
		'f0a245f4-82a3-4981-9607-8d611bbb1800',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-000000000006',
		'HS6',
		40,
		'CS.06',
		'Hörsaal 6',
		'f0a245f4-82a3-4981-9607-8d611bbb1800',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-100000000000',
		'RZ1',
		0,
		'PC.01',
		'Rechenzentrum 1',
		'88d06be0-af95-4c39-a809-2523951dddb7',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-100000000001',
		'RZ2',
		42,
		'PC.02',
		'Rechenzentum 2',
		'88d06be0-af95-4c39-a809-2523951dddb7',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-100000000002',
		'RZ3',
		32,
		'PC.03',
		'Rechenzentrum 3',
		'88d06be0-af95-4c39-a809-2523951dddb7',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-200000000000',
		'SM1',
		10,
		'SM.01',
		'Seminarraum 1',
		'ed6c1626-82a6-4b07-bd69-33fd5b388318',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-200000000001',
		'SM2',
		10,
		'SM.02',
		'Seminarraum 2',
		'ed6c1626-82a6-4b07-bd69-33fd5b388318',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-200000000002',
		'SM3',
		20,
		'SM.03',
		'Seminarraum 3',
		'ed6c1626-82a6-4b07-bd69-33fd5b388318',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	),
	(
		'88f3a2f9-176b-40d7-0000-300000000000',
		'Online',
		0,
		'OV.00',
		'Onlinevorlesung',
		'ed6c1626-82a6-4b07-bd69-33fd5b388318',
		'37016bdf-07b0-4f9c-aa68-cfdeb49001c0'
	) ON CONFLICT DO NOTHING;