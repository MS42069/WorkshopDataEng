ALTER TABLE breaks_between_constraints
    DROP COLUMN constraint_type;

ALTER TABLE course_distribution_constraints
    DROP COLUMN constraint_type;

ALTER TABLE free_day_constraints
    DROP COLUMN constraint_type;

ALTER TABLE lunch_break_constraints
    DROP COLUMN constraint_type;

ALTER TABLE subsequent_timeslots_constraints
    DROP COLUMN constraint_type;

ALTER TABLE breaks_between_constraints
ADD COLUMN is_accepted boolean not null default false;

create table if not exists employee_app_availabilities (
    id uuid not null,
    start_date timestamp not null,
    end_date timestamp not null,
    primary key (id)
);