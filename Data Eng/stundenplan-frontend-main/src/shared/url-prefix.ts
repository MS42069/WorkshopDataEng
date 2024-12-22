export const baseDataURLPrefix = "timetable/:timetableId/data";
export const planningURLPrefix = "timetable/:timetableId/planning";

export function getBaseDataURLPref(timetableId: string | undefined) {
  return `/timetable/${timetableId}/data`;
}

export function getPlanningURLPref(timetableId: string | undefined) {
  return `/timetable/${timetableId}/planning`;
}
