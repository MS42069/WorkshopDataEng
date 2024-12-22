import { TimeslotAPI, Timeslot } from "../models/timeslots";
import { WeekdayDescription, WeekdayValue } from "../enums/weekday.enum";
import { AvailabilityAPI } from "../shared/components/availability-table/availability-table";
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
  employeeTimeslotConstraints: Constraints[];
};
export type Constraints = {
  employeeAbbreviation: string;
  constraintType: ConstraintType;
  constraintValue: ConstraintValue;
  weekday: WeekdayValue;
  timeslotIndex: number;
  startTime: string;
  endTime: string;
  reason?: string;
};

export enum ConstraintType {
  SOFT = "SOFT",
  HARD = "HARD",
}

export enum ConstraintValue {
  WANTED = "WANTED",
  NOT_WANTED = "NOT_WANTED",
}

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

export type EmployeeCheckConstraint = {
  id: string;
  abbreviation: string;
  firstname: string;
  lastname: string;
  weekDay: string;
  startTime: string;
  endTime: string;
  reason: string;
};

export type EmployeeCheckConstraintGET = {
  id: string;
  abbreviation: string;
  name: {
    firstname: string;
    lastname: string;
  };
  timeslot: {
    startTime: string;
    endTime: string;
  };
  reason: string;
};

export type EmployeeCheckConstraintPOST = {
  id: string;
  accepted: boolean;
  reason: string;
};
