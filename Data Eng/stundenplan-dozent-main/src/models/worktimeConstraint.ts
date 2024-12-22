import {
  AvailabilityAPI,
  AvailabilityValue,
} from "../components/pages/availability-table/availability-table";
import { axiosInstance } from "../config/axios.config";
import { WeekdayValue } from "../enums/weekday.enum";
import { TimeslotAPI } from "./timeslots";

export enum ConstraintType {
  SOFT = "SOFT",
  HARD = "HARD",
}

export enum ConstraintValue {
  WANTED = "WANTED",
  NOT_WANTED = "NOT_WANTED",
}

type TimeslotAPIExtened = TimeslotAPI & {
  timetable: null;
};

type WorktimeConstraints = {
  workTimeResponseDTOS: Worktime[];
  employeeTimeslotConstraintResDTOS: Constraints[];
};

type Constraints = {
  employeeAbbreviation: string;
  constraintType: ConstraintType;
  constraintValue: ConstraintValue;
  weekday: WeekdayValue;
  timeslotIndex: number;
  startTime: string;
  endTime: string;
  reason?: string;
  isAccepted: boolean;
};

type Worktime = {
  weekday: WeekdayValue;
  timeslot: TimeslotAPIExtened;
};

export const fetchWorktimeConstraint = async (
  username: string
): Promise<AvailabilityAPI[]> => {
  const response = await axiosInstance.get<WorktimeConstraints>(
    `/timetable/${username}`,
    { cache: false }
  );

  const data = response.data;

  const workTimeResponseDTOS = data.workTimeResponseDTOS;
  const employeeTimeslotConstraintResDTOS =
    data.employeeTimeslotConstraintResDTOS;

  const worktimes = workTimeResponseDTOS.map<AvailabilityAPI>((wt) => {
    const constraint = employeeTimeslotConstraintResDTOS.find((constraint) => {
      return (
        wt.weekday === constraint.weekday &&
        wt.timeslot.index === constraint.timeslotIndex
      );
    });

    const available: AvailabilityValue = constraint
      ? constraint.constraintValue === ConstraintValue.WANTED &&
        constraint.constraintType === ConstraintType.SOFT
        ? "gerne"
        : constraint.constraintValue === ConstraintValue.NOT_WANTED &&
          constraint.constraintType === ConstraintType.SOFT
        ? "ungerne"
        : constraint.constraintValue === ConstraintValue.NOT_WANTED &&
          constraint.constraintType === ConstraintType.HARD
        ? "nein"
        : "egal"
      : "egal";

    return {
      available: available,
      timeslot: {
        id: wt.timeslot.id,
        startTime: wt.timeslot.startTime,
        endTime: wt.timeslot.endTime,
        index: wt.timeslot.index,
      },
      weekday: wt.weekday,
      reason: constraint?.reason,
      isAccepted: constraint?.isAccepted,
    };
  });

  return worktimes;
};
export const saveWorktimeConstraint = async (
  timeslots: AvailabilityAPI[],
  username: string
): Promise<void> => {
  const validTimeslots = timeslots.map((elem) => {
    return {
      employeeAbbreviation: username,
      constraintType:
        elem.available !== "egal"
          ? elem.available === "gerne" || elem.available === "ungerne"
            ? ConstraintType.SOFT
            : ConstraintType.HARD
          : null,
      constraintValue:
        elem.available !== "egal"
          ? elem.available === "ungerne" || elem.available === "nein"
            ? ConstraintValue.NOT_WANTED
            : ConstraintValue.WANTED
          : null,
      weekday: elem.weekday,
      timeslotIndex: elem.timeslot.index,
      startTime: elem.timeslot.startTime,
      endTime: elem.timeslot.endTime,
      reason: elem.reason,
    };
  });

  await axiosInstance.post(`/timetable/${username}`, validTimeslots, {
    cache: false,
  });
};
