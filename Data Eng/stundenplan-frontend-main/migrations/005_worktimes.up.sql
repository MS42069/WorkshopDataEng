-- Assign all timeslots during the week to all employees
INSERT INTO
	worktimes
SELECT
	gen_random_uuid() "id",
	w "weekday",
	e.id "fk_employee_id",
	t.id "fk_timeslot_id"
FROM
	employees e
JOIN
	timeslots t ON true
JOIN
	string_to_table('MONDAY TUESDAY WEDNESDAY THURSDAY FRIDAY', ' ') w ON true;