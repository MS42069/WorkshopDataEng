import { WeekdayValue } from "../../../enums/weekday.enum";
import { LectureBase } from "../../../models/lecture";
import { TimeslotAPI } from "../../../models/timeslots";

export interface BaseEntry {
  week: number;
  weekday: WeekdayValue;
  timeslot: TimeslotAPI;
}

export interface UnavailableEntry extends BaseEntry {
  type: "unavailable";
}

export interface BlockedEntry extends BaseEntry {
  type: "blocked";
  course: LectureBase;
  roomIds: string[];
}

export interface AvailableEntry extends BaseEntry {
  type: "available";
}

export type ReadonlyBlockedEntry = UnavailableEntry | BlockedEntry;

export type ReadonlyPlanningOverviewTableEntry =
  | AvailableEntry
  | ReadonlyBlockedEntry;
