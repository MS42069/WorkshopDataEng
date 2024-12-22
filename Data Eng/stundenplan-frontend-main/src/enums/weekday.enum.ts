export type WeekdayValue =
  | "MONDAY"
  | "TUESDAY"
  | "WEDNESDAY"
  | "THURSDAY"
  | "FRIDAY"
  | "SATURDAY"
  | "SUNDAY";

export interface WeekdayDescription {
  name: string;
  value: WeekdayValue;
  ord: number;
  abbr: string;
}

export const getAllWeekdays = (): WeekdayDescription[] => [
  {
    name: "Montag",
    value: "MONDAY",
    ord: 0,
    abbr: "Mo",
  },
  {
    name: "Dienstag",
    value: "TUESDAY",
    ord: 1,
    abbr: "Di",
  },
  {
    name: "Mittwoch",
    value: "WEDNESDAY",
    ord: 2,
    abbr: "Mi",
  },
  {
    name: "Donnerstag",
    value: "THURSDAY",
    ord: 3,
    abbr: "Do",
  },
  {
    name: "Freitag",
    value: "FRIDAY",
    ord: 4,
    abbr: "Fr",
  },
  {
    name: "Samstag",
    value: "SATURDAY",
    ord: 5,
    abbr: "Sa",
  },
  {
    name: "Sonntag",
    value: "SUNDAY",
    ord: 6,
    abbr: "So",
  },
];

export const getWeekdayTextOfString = (str: string): string => {
  return getAllWeekdays().find((value) => value.value == str)?.name ?? "";
};

export const getAllFreeDays = () => [
  {
    name: "Egal",
    value: "EGAL",
    eng: "EGAL",
  },
  {
    name: "Montag",
    value: "MONTAG",
    eng: "MONDAY",
  },
  {
    name: "Dienstag",
    value: "DIENSTAG",
    eng: "TUESDAY",
  },
  {
    name: "Mittwoch",
    value: "WEDNESDAY",
    eng: "WEDNESDAY",
  },
  {
    name: "Donnerstag",
    value: "DONNERSTAG",
    eng: "THURSDAY",
  },
  {
    name: "Freitag",
    value: "FRIDAY",
    eng: "FRIDAY",
  },
];
