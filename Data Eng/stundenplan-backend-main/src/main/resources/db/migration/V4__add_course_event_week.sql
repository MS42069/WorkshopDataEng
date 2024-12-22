begin;

alter table "course_events"
    add column "week" integer not null default 1;

-- Drop default as we only want this for the sake of being able to migrate already existing entries
alter table "course_events"
    alter column "week"  drop default;

with
-- Prepare rows to be inserted in a separate CTE as otherwise we wouldn't have access
-- to the "source_id"s
"ce" as (
    select
        gen_random_uuid() "id",
        ce."weekday",
        ce."course_id",
        ce."timetable_id",
        ce."id" "source_id",
        "week_series" "week"
    from "course_events" ce
    join "timetables" tt on ce."timetable_id" = tt."id"
    join generate_series(2, tt."number_of_weeks") "week_series" on true
),
-- Actually insert those rows
"_ce" as (
    insert into "course_events"
        select
            ce."id",
            ce."weekday",
            ce."course_id",
            ce."timetable_id",
            ce."week"
        from ce
),
-- Insert necessary relations for the rooms
"_wer" as (
    insert into "week_events_rooms"
        select
            ce.id "week_event_id",
            wer."room_id"
        from ce
        join "week_events_rooms" wer on wer."week_event_id" = ce."source_id"
)
-- Insert necessary relations for the timeslots
insert into "week_events_timeslots"
    select
        ce.id "week_event_id",
        wet."timeslot_id"
    from ce
    join "week_events_timeslots" wet on wet."week_event_id" = ce."source_id";

commit;


-- Undo:
-- delete from "week_events_timeslots" wet where wet."week_event_id" in (select id from course_events where "week" <> 1);
-- delete from "week_events_rooms" wet where wet."week_event_id" in (select id from course_events where "week" <> 1);
-- delete from "course_events" where "week" <> 1;
-- alter table "course_events" drop column "week";
-- delete from "flyway_schema_history" where description = 'add course event week';