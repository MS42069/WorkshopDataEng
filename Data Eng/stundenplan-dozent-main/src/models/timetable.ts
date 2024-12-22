import dayjs, { Dayjs } from "dayjs";
import { fetchSemesterTypes } from "../requests/types.request";
import { useState } from "react";

/*++++++++++++++++++++ Formatting constants ++++++++++++++++++++ */

export const dayFormatShort = "DD.MM.YYYY";
export const dayFormatAPI = "YYYY-MM-DD";

/*++++++++++++++++++++ Models ++++++++++++++++++++ */
export interface TimetableAPI {
  id: string;
  startDate: string;
  endDate: string;
  numberOfWeeks: number;
  semesterType: string | SemesterType;
  name: string;
  specialEvents: SpecialDateAPI[];
}

export const specialEventTypes = [
  "MONDAY_PLAN",
  "TUESDAY_PLAN",
  "WEDNESDAY_PLAN",
  "THURSDAY_PLAN",
  "FRIDAY_PLAN",
  "SATURDAY_PLAN",
  "FREE",
] as const;

export type SpecialEventType = (typeof specialEventTypes)[number];

export type SpecialDateAPI = {
  specialEventType: string;
  startDate: string;
  endDate: string;
};

export interface Timetable {
  id: string;
  semester: Semester;
  info: string;
  start: Dayjs;
  end: Dayjs;
  specialDates: SpecialDate[];
  weeksPerSemester: number;
  complete?: boolean;
}

export interface Semester {
  semesterType: SemesterType;
  year: number;
}

export type SpecialDate = {
  id: string;
  specialEventType: SpecialEventType;
  from: Dayjs;
  to: Dayjs;
};

export type SemesterType = {
  id: string;
  name: string;
};
/*++++++++++++++++++++ Helper functions ++++++++++++++++++++ */
export function isFree(eventType: SpecialEventType | null): boolean {
  return eventType === "FREE";
}
/*++++++++++++++++++++ String represantations ++++++++++++++++++++ */
const specialEventTexts = [
  "Montagsplan",
  "Dienstagsplan",
  "Mittwochsplan",
  "Donnerstagsplan",
  "Freitagsplan",
  "Samstagsplan",
  "Frei",
];

/** SE = Special Event */
export function getSETypeText(specialEventType: SpecialEventType): string {
  let text = "";
  switch (specialEventType) {
    case "MONDAY_PLAN":
      text = specialEventTexts[0];
      break;
    case "TUESDAY_PLAN":
      text = specialEventTexts[1];
      break;
    case "WEDNESDAY_PLAN":
      text = specialEventTexts[2];
      break;
    case "THURSDAY_PLAN":
      text = specialEventTexts[3];
      break;
    case "FRIDAY_PLAN":
      text = specialEventTexts[4];
      break;
    case "SATURDAY_PLAN":
      text = specialEventTexts[5];
      break;
    case "FREE":
      text = specialEventTexts[6];
      break;
  }
  return text;
}
export function stringOfSemester(
  semester: Semester,
  start: Dayjs,
  end: Dayjs
): string {
  // Checking for difference in year to display overlapping semester
  const isWintersemester = !start.isSame(end, "year");

  return `${semester.semesterType.name} ${start.format("YY")}${
    // Add ending year to string, if semester is wintersemester
    isWintersemester ? `/${end.format("YY")}` : ""
  }`;
}
export function parseSpecialDatesToAPI(
  specialDates: SpecialDate[]
): SpecialDateAPI[] {
  const res: SpecialDateAPI[] = [];
  specialDates.forEach((specialDate) => {
    res.push({
      startDate: specialDate.from.format(dayFormatAPI),
      endDate: specialDate.to.format(dayFormatAPI),
      specialEventType: specialDate.specialEventType,
    });
  });
  return res;
}
export function parseFromSpecialDatseAPI(
  specialDates: SpecialDateAPI[]
): SpecialDate[] {
  const res: SpecialDate[] = [];
  specialDates.forEach((specialDate) => {
    res.push({
      // TODO id are not necessarily unique
      id: Math.floor(Math.random() * 10000).toString(),
      from: dayjs(specialDate.startDate),
      to: dayjs(specialDate.endDate),

      specialEventType: specialDate.specialEventType as SpecialEventType,
    });
  });
  return res;
}

export function parseYearToShortFormat(date: Dayjs): number {
  const shortYearFormat = "YY";
  const yearAsString: string = date.format(shortYearFormat);
  return parseInt(yearAsString, 10);
}

export function getSemesterTypeFromID(id: string): SemesterType {
  const [semesterTypes, setSemesterTypes] = useState<SemesterType[]>([]);
  fetchSemesterTypes().then((response) => setSemesterTypes(response.data));
  let tmpSemesterType: SemesterType = {
    id: "1",
    name: "",
  };
  semesterTypes.forEach((element) => {
    if (element.name.localeCompare(id) === 0) {
      tmpSemesterType = element;
    }
  });
  return tmpSemesterType;
}
