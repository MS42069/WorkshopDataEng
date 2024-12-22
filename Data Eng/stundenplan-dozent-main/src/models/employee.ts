import { TimeslotAPI, Timeslot } from "./timeslots";
import { WeekdayDescription, WeekdayValue } from "../enums/weekday.enum";
import { AvailabilityAPI } from "../components/pages/availability-table/availability-table";
export interface Employee {
  // UUID - Value
  id: string;
  abbreviation: string;
  surname: string;
  firstname: string;
}

export interface Availability {
  weekday: WeekdayDescription;
  timeslot: Timeslot;
}

export interface WorkTimeAPI {
  weekday: WeekdayValue;
  timeslot: TimeslotAPI;
}

export type EmployeeDetail = {
  employeeType: EmployeeType;
  availability: Availability[];
} & Employee;

export type EmployeeAPI = {
  id: string;
  abbreviation: string;
  firstname: string;
  lastname: string;
  employeeType: string;
  workTimes: AvailabilityAPI[];
};

export type EmployeeAPIRequest = {
  id: string;
  abbreviation: string;
  firstname: string;
  lastname: string;
  employeeType: string;
  workTimes: WorkTimeRequest[];
};

export type EmployeeType = {
  id: string;
  name: string;
};

export type WorkTimeRequest = {
  weekday: WeekdayValue;
  timeslotID: string;
};

export type EmployeeSort = Pick<
  EmployeeAPI,
  "firstname" | "lastname" | "abbreviation"
>;

export type EmployeeCreateDetail = Partial<EmployeeDetail>;
export type EmployeeShortKey = keyof Employee;
export type EmployeeDetailKey = keyof EmployeeDetail;
