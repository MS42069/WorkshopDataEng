import { WeekdayDescription } from "../../../enums/weekday.enum";
import { LectureBase } from "../../../models/lecture";
import { RoomShort } from "../../../models/room";
import { TimeslotAPI } from "../../../models/timeslots";
import { WeekEvent, WeekEventOptions } from "../../../models/week-event";

export function getPlannedWeekEvents(
  room: RoomShort,
  timeslot: TimeslotAPI,
  weekday: WeekdayDescription,
  week: number,
  plannedEvents: WeekEvent[]
): WeekEvent[] {
  return plannedEvents.filter(
    (planned) =>
      planned.rooms.some(({ id }) => id === room.id) &&
      planned.timeslots.some(({ id }) => id === timeslot.id) &&
      (week === 0 || planned.week === week) &&
      planned.weekday === weekday.value
  );
}

export function indexInSuggestions(
  room: RoomShort,
  timeslot: TimeslotAPI,
  { value }: WeekdayDescription,
  week: number,
  options: WeekEventOptions[] | undefined
) {
  return options?.some(
    ({ rooms, timeslots, weekday, weeks }) =>
      rooms.includes(room.id) &&
      timeslots.includes(timeslot.id) &&
      weeks.includes(week) &&
      weekday === value
  );
}

export function findFirstIdxOfSuggestion(
  room: RoomShort,
  timeslot: TimeslotAPI,
  { value }: WeekdayDescription,
  week: number,
  options: WeekEventOptions[] | undefined
) {
  return (
    options?.findIndex(
      ({ rooms, timeslots, weekday, weeks }) =>
        rooms.includes(room.id) &&
        timeslots[0] === timeslot.id &&
        weeks.includes(week) &&
        weekday === value
    ) ?? -1
  );
}

/** Groups events to their courses and in which weeks they take place */
export function aggregateEvents(
  events: WeekEvent[]
): { course: LectureBase; weeks: number[] }[] {
  if (events.length < 2) {
    return events.map((e) => ({ course: e.course, weeks: [e.week] }));
  }

  const entries = {} as Record<
    string,
    { course: LectureBase; weeks: number[] }
  >;
  for (const event of events) {
    (entries[event.course.id] ??= {
      course: event.course,
      weeks: [],
    }).weeks.push(event.week);
  }

  return Object.values(entries);
}
