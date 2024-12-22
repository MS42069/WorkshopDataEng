create table breaks_between_constraints
(
    id                    uuid         not null,
    constraint_type       varchar(255) not null,
    constraint_value      varchar(255) not null,
    employee_abbreviation varchar(255) not null,
    primary key (id)
);

create table course_distribution_constraints
(
    id                    uuid         not null,
    constraint_type       varchar(255) not null,
    constraint_value      varchar(255) not null,
    employee_abbreviation varchar(255) not null,
    primary key (id)
);

create table employee_timeslot_constraints
(
    id                    uuid         not null,
    constraint_type       varchar(255) not null,
    constraint_value      varchar(255) not null,
    employee_abbreviation varchar(255) not null,
    end_time              time         not null,
    reason                varchar(255),
    start_time            time         not null,
    timeslot_index        int4         not null,
    weekday               varchar(255) not null,
    primary key (id)
);

create table free_day_constraints
(
    id                    uuid         not null,
    constraint_type       varchar(255) not null,
    constraint_value      varchar(255) not null,
    employee_abbreviation varchar(255) not null,
    favorite_day          varchar(255),
    primary key (id)
);

create table lunch_break_constraints
(
    id                    uuid         not null,
    constraint_type       varchar(255) not null,
    constraint_value      varchar(255) not null,
    employee_abbreviation varchar(255) not null,
    primary key (id)
);

create table subsequent_timeslots_constraints
(
    id                    uuid         not null,
    constraint_type       varchar(255) not null,
    employee_abbreviation varchar(255) not null,
    timeslot_amount       int4         not null check (timeslot_amount <= 6 AND timeslot_amount >= 1),
    primary key (id)
)
