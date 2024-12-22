import { WeekdayDescription } from "../../../enums/weekday.enum";
import { TimeslotAPI } from "../../../models/timeslots";
import { BaseEntry } from "./readonly-planning.overview.types";

export const getEntries = <T extends BaseEntry>(
  entries: T[],
  weekday: WeekdayDescription,
  timeslot: TimeslotAPI
): T[] =>
  entries.filter(
    (availableEntry) =>
      availableEntry.weekday === weekday.value &&
      availableEntry.timeslot.id === timeslot.id
  );
