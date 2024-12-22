import { axiosInstance } from "../config/axios.config";
import { getAllFreeDays } from "../enums/weekday.enum";
import { PreferenceProfile, Prioritization } from "./employee-wishes";

export type ConstraintReqDTO = {
  lunchBreak: LunchBreakConstraint;
  freeDay: FreeDayConstraint;
  breaksBetween: BreaksBetweenConstraint;
  courseDistribution: CourseDistributionConstraint;
  subsequentTimeslots: SubsequentTimeslotsConstraint;
};

export enum ConstraintType {
  SOFT = "SOFT",
  HARD = "HARD",
}

export enum ConstraintValue {
  WANTED = "WANTED",
  NOT_WANTED = "NOT_WANTED",
}

export type SimpleConstraint = {
  employeeAbbreviation: string;
  constraintValue: ConstraintValue | null;
};

export type ExtendedSimpleConstraint = SimpleConstraint & {
  timeslotAmount: number | null;
};

export type LunchBreakConstraint = SimpleConstraint;
export type FreeDayConstraint = SimpleConstraint & {
  favoriteDay: string | null;
};
export type BreaksBetweenConstraint = SimpleConstraint;
export type CourseDistributionConstraint = SimpleConstraint;
export type SubsequentTimeslotsConstraint = ExtendedSimpleConstraint;

export const saveConstraintProfile = async (
  username: string,
  profil: PreferenceProfile
) => {
  const constraintReqDTO: ConstraintReqDTO = {
    breaksBetween: {
      constraintValue:
        profil.breaksBeforeCourse >= 1
          ? ConstraintValue.WANTED
          : profil.breaksBeforeCourse <= -1
          ? ConstraintValue.NOT_WANTED
          : null,
      employeeAbbreviation: username,
    },
    courseDistribution: {
      constraintValue:
        profil.coursesOverTheWeek >= 1
          ? ConstraintValue.WANTED
          : profil.coursesOverTheWeek <= -1
          ? ConstraintValue.NOT_WANTED
          : null,
      employeeAbbreviation: username,
    },
    freeDay: {
      constraintValue:
        profil.freeDaysInWeek >= 1
          ? ConstraintValue.WANTED
          : profil.freeDaysInWeek <= -1
          ? ConstraintValue.NOT_WANTED
          : null,
      employeeAbbreviation: username,
      favoriteDay:
        profil.importantFreeDay === "EGAL" || profil.freeDaysInWeek <= 0
          ? null
          : getAllFreeDays().find((e) => e.value === profil.importantFreeDay)!
              .eng,
    },
    lunchBreak: {
      constraintValue:
        profil.lunchBreak >= 1
          ? ConstraintValue.WANTED
          : profil.lunchBreak <= -1
          ? ConstraintValue.NOT_WANTED
          : null,
      employeeAbbreviation: username,
    },
    subsequentTimeslots: {
      constraintValue:
        profil.slotPriority === 1 ? null : ConstraintValue.WANTED,
      employeeAbbreviation: username,
      timeslotAmount: profil.slotPriority === 1 ? null : profil.maxSlotsPerDay,
    },
  };

  await axiosInstance.post<ConstraintReqDTO>(
    `/preference-profile/save`,
    constraintReqDTO,
    { cache: false }
  );
};

export const fetchContraintProfil = async (
  username: string
): Promise<PreferenceProfile> => {
  const response = await axiosInstance.get<ConstraintReqDTO>(
    `/preference-profile/${username}`,
    { cache: false }
  );
  const data = response.data;

  const preferenceProfile: PreferenceProfile = {
    lunchBreak: convertToSliderNumber(data.lunchBreak),
    freeDaysInWeek: convertToSliderNumber(data.freeDay),
    importantFreeDay:
      data.freeDay && data.freeDay.favoriteDay
        ? getAllFreeDays().find((e) => e.eng === data.freeDay.favoriteDay)!
            .value
        : "EGAL",
    breaksBeforeCourse: convertToSliderNumber(data.breaksBetween),
    coursesOverTheWeek: convertToSliderNumber(data.courseDistribution),
    maxSlotsPerDay: data.subsequentTimeslots
      ? data.subsequentTimeslots.timeslotAmount
        ? data.subsequentTimeslots.timeslotAmount
        : 3
      : 3,
    slotPriority: data.subsequentTimeslots
      ? data.subsequentTimeslots.constraintValue === ConstraintValue.WANTED
        ? 2
        : 1
      : 1,
  };

  return preferenceProfile;
};

const convertToSliderNumber = (
  simpleConstraint: SimpleConstraint
): Prioritization => {
  if (simpleConstraint.constraintValue === ConstraintValue.NOT_WANTED) {
    return -1;
  } else if (simpleConstraint.constraintValue === ConstraintValue.WANTED) {
    return 1;
  } else {
    return 0;
  }
};
