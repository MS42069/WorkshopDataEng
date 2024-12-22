export type PreferenceProfile = {
  lunchBreak: Prioritization;
  freeDaysInWeek: Prioritization;
  importantFreeDay: string;
  breaksBeforeCourse: Prioritization;
  coursesOverTheWeek: Prioritization;
  maxSlotsPerDay: number /**Value between 1 and 7, 7 == egal */;
  slotPriority: number;
};

export type lecturerTimeslotWish = {
  lecture_id: string;
  needed_timeslot_id: string;
};

export type lecturerDayWish = {
  lecture_id: string;
  needed_day_id: string;
};

export type Prioritization = -2 | -1 | 0 | 1 | 2;
