export type DegreeSemesterScore = {
  totalScore: number;
  semesterNumber: number;
  weeks: DegreeSemesterWeekScore[];
};

export type DegreeSemesterWeekScore = {
  week: number;
  totalScore: DegreeScoreAndWeight;
  freeDayScore: DegreeScoreAndWeight;
  shortDayScore: DegreeScoreAndWeight;
  onlineToOfflineConnectionScore: DegreeScoreAndWeight;
  firstTimeslotScore: DegreeScoreAndWeight;
  lastTimeslotScore: DegreeScoreAndWeight;
  longGapsScore: DegreeScoreAndWeight;
};

export type DegreeScoreAndWeight = {
  score: number;
  weight: number;
  desc: string;
};

export type DegreeScore = {
  totalDegreeScore: number;
  degreeName: string;
  degreeRegulation: string;
  degreeSemesterScores: DegreeSemesterScore[];
};

export type ScoreAPI = {
  employeeEvaluationScoreDTO: EmployeeScoreAPI;
  studentEvaluationScoreDTO: StudentScoreAPI;
  score: number;
};

export type EmployeeScoreAPI = {
  totalScore: number;
  employeeEvaluations: EmployeeEvaluation[];
};

export type EmployeeEvaluation = {
  score: number;
  abbreviation: string;
  timeslotEvaluationSoft: TimeslotEvaluationSoft[];
  timeslotEvaluationHard: TimeslotEvaluationHard[];
  freeDayEvaluation: FreeDayEvaluation[];
  breaksBetweenEvaluation: BreaksBetweenEvaluation[];
  lunchBreakEvaluation: LunchBreakEvaluation[];
  distributionEvaluation: DistributionEvaluation[];
  subsequentTimeslotsEvaluation: SubsequentTimeslotsEvaluation[];
};

export type TimeslotEvaluationSoft = {
  score: number;
  week: number;
  harmedConstraints: EmployeeTimeslotConstraint[];
};

export type TimeslotEvaluationHard = {
  week: number;
  harmedConstraints: EmployeeTimeslotConstraint[];
};

export type EmployeeTimeslotConstraint = {
  employeeAbbreviation: string;
  constraintType: ConstraintType;
  constraintValue: string;
  weekday: string;
  timeslotIdx: number;
  reason: string;
  startTime: string;
  endTime: string;
};

export type FreeDayEvaluation = {
  score: number;
  week: number;
  constraint: EmployeeFreeDayConstraint;
};

export type EmployeeFreeDayConstraint = {
  employeeAbbreviation: string;
  constraintType: string;
  constraintValue: string;
  favoriteDay: string;
};

export type BreaksBetweenEvaluation = {
  score: number;
  week: number;
  constraint: BasicConstraint;
};

export type BasicConstraint = {
  employeeAbbreviation: string;
  constraintType: string;
  constraintValue: string;
};

export type LunchBreakEvaluation = {
  score: number;
  week: number;
  constraint: BasicConstraint;
  harmedDays: string[];
};

export type DistributionEvaluation = {
  score: number;
  week: number;
  constraint: BasicConstraint;
};

export type SubsequentTimeslotsEvaluation = {
  score: number;
  week: number;
  constraint: SubsequentTimeslotsConstraint;
  harmedDays: string[];
};

export type SubsequentTimeslotsConstraint = {
  employeeAbbreviation: string;
  constraintType: string;
  constraintValue: string;
  timeslotAmount: number;
};

export type StudentScoreAPI = {
  totalScore: number;
  degreeScores: DegreeScore[];
};

export type ScoreDescPair = {
  score: number;
  desc: string;
};

export enum ConstraintType {
  SOFT = "SOFT",
  HARD = "HARD",
}
