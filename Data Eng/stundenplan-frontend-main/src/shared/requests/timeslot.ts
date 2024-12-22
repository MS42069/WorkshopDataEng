import { resourceAxiosInstance } from "../../config/axios.config";
import { TimeslotAPI } from "../../models/timeslots";
import { timeslotForOverview } from "../formatter/timeslot.formatter";
import { createTimetablePath } from "../path.util";

export const fetchTimeslots = () =>
  resourceAxiosInstance
    .get<TimeslotAPI[]>("/timeslots", { id: createTimetablePath("timeslots") })
    .then(({ data }) => data.map(timeslotForOverview));

// TODO: hääää, kekw??????
export const fetchTimeslots2 = () =>
  resourceAxiosInstance.get<TimeslotAPI[]>("/timeslots", {
    id: createTimetablePath("timeslots"),
  });

export const createTimeslot = (timeslot: TimeslotAPI) =>
  resourceAxiosInstance.post<TimeslotAPI>("/timeslots", timeslot, {
    cache: { update: { [createTimetablePath("subject")]: "delete" } },
  });

export const fetchTimeslot = (timeslotId: string) =>
  resourceAxiosInstance
    .get<TimeslotAPI>(`/timeslots/${timeslotId}`, { cache: false })
    .then(({ data }) => timeslotForOverview(data));

export const deleteTimeslot = (timeslotId: string) =>
  resourceAxiosInstance.delete(`/timeslots/${timeslotId}`, {
    cache: { update: { [createTimetablePath("subject")]: "delete" } },
  });

export const patchTimeslot = (timeslot: TimeslotAPI) =>
  resourceAxiosInstance.patch<TimeslotAPI>(
    `/timeslots/${timeslot.id}`,
    timeslot,
    {
      cache: { update: { [createTimetablePath("subject")]: "delete" } },
    }
  );
