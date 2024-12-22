import { WeekdayValue } from "../enums/weekday.enum";
import { LectureBase } from "./lecture";

export interface ScheduleWeekEvent {
  courseId: string;
  week: number | null;
  weekday: WeekdayValue;
  blockOfTimeslots: string[];
  takesPlaceInRooms: string[];
}

export interface WeekEventOption {
  course: string;
  degreeOfFreedom: number;
  options: WeekEventOptions[];
}

export interface WeekEventOptionRaw {
  course: string;
  degreeOfFreedom: number;
  options: WeekEventOptionsRaw[];
}

export interface WeekEvent {
  id: string;
  course: LectureBase;
  week: number;
  weekday: WeekdayValue;
  timeslots: {
    id: string;
  }[];
  rooms: {
    id: string;
  }[];
}

export type WeekEventOptions = {
  weekday: WeekdayValue;
  timeslots: string[];
  rooms: string[];
  weeks: number[];
};

export type WeekEventOptionsRaw = {
  weekday: WeekdayValue;
  timeslots: string[];
  rooms: string[];
  week: number;
};
