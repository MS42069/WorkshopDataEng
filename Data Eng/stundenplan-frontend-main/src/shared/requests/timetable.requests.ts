import { axiosInstance } from "../../config/axios.config";
import {
  CopyTimetableOptions,
  TimetableAPI,
  parseTimeTableFromAPI,
} from "../../models/timetable";

export const fetchTimetables = () =>
  axiosInstance
    .get<TimetableAPI[]>("/timetables", { id: "timetables" })
    .then(({ data }) => data.map(parseTimeTableFromAPI));

export const fetchTimetable = (timetableId: string | undefined) =>
  axiosInstance
    .get<TimetableAPI>(`/timetables/${timetableId}`, { cache: false })
    .then(({ data }) => parseTimeTableFromAPI(data));

export const createTimetable = (timetable: TimetableAPI) =>
  axiosInstance.post<TimetableAPI>("/timetables", timetable, {
    cache: { update: { timetables: "delete" } },
  });

export const deleteTimetable = (timetableId: string) =>
  axiosInstance.delete(`/timetables/${timetableId}`, {
    cache: { update: { timetables: "delete" } },
  });

export const updateTimetable = (data: TimetableAPI) =>
  axiosInstance.put<TimetableAPI>(`/timetables/${data.id}`, data, {
    cache: { update: { timetables: "delete" } },
  });

export const copyAllDataTimetable = (data: TimetableAPI, from: TimetableAPI) =>
  axiosInstance.put<TimetableAPI>(
    `/timetables/${data.id}/copyAll/${from.id}`,
    data,
    {
      cache: { update: { timetables: "delete" } },
    }
  );

export const copySelectedDataTimetable = (
  data: TimetableAPI,
  from: TimetableAPI,
  options: CopyTimetableOptions
) =>
  axiosInstance.put<TimetableAPI>(
    `/timetables/${data.id}/copyfrom/${from.id}?employee=${options.employee}&room=${options.room}
    &course=${options.course}&degree=${options.degree}&degreesemester=${options.degreesemester}
    &timeslot=${options.timeslot}&weekevent=${options.weekevent}&specialevent=${options.specialevent}`,
    data,
    {
      cache: { update: { timetables: "delete" } },
    }
  );
