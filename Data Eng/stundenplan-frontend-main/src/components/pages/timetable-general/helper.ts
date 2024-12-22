import { Timetable, stringOfTimetable } from "../../../models/timetable";
import dayjs from "dayjs";

export function isUpdate(id: string | undefined): boolean {
  return !!id;
}

export function isValidDate(start: dayjs.Dayjs, end: dayjs.Dayjs): boolean {
  return !start.isAfter(end);
}

export function getTimeTableInfoByID(options: Timetable[], id: string): string {
  const timetable = options.find((element) => element.id === id);
  if (timetable) {
    return stringOfTimetable(timetable);
  } else {
    return "undefined";
  }
}
