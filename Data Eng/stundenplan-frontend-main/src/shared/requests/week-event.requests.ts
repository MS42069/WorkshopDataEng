import { resourceAxiosInstance } from "../../config/axios.config";
import { WeekdayValue } from "../../enums/weekday.enum";
import { LectureAPI } from "../../models/lecture";
import {
  WeekEvent,
  ScheduleWeekEvent,
  WeekEventOption,
  WeekEventOptionRaw,
} from "../../models/week-event";
import { groupBy } from "../group";

interface ScheduleWeekEventResponse {
  events: WeekEvent[];
}

export const scheduleWeekEvent = (
  weekEvent: ScheduleWeekEvent,
  force = false
) => {
  const params = new URLSearchParams();

  params.append("force", `${force}`);

  return resourceAxiosInstance.post<ScheduleWeekEventResponse>(
    "/week-events/schedule",
    weekEvent,
    {
      params,
    }
  );
};

export const getWeekEvents = () =>
  resourceAxiosInstance.get<WeekEvent[]>("/week-events", {
    cache: false,
  });

export const getWeekEventsByEmployee = (employeeId: string) =>
  resourceAxiosInstance.get<WeekEvent[]>(
    `/week-events/employee/${employeeId}`,
    { cache: false }
  );

export const getWeekEventsBySemester = (degreeSemesterId: string) =>
  resourceAxiosInstance.get<WeekEvent[]>(
    `/week-events/semester/${degreeSemesterId}`,
    { cache: false }
  );

export const getWeekEventsByRoom = (roomId: string) =>
  resourceAxiosInstance.get<WeekEvent[]>(`/week-events/room/${roomId}`, {
    cache: false,
  });

export const getWeekEvent = (weekEventId: string) =>
  resourceAxiosInstance.get<LectureAPI>(`/week-events/${weekEventId}`, {
    id: "week-event",
    cache: false,
  });

export const deleteWeekEvent = (weekEventId: string) =>
  resourceAxiosInstance.delete(`/week-events/${weekEventId}`, {
    // cache: { update: { "week-events": "delete", "week-event": "delete" } },
  });

export const getWeekEventOptions = async (
  courseIds: string[],
  weekday: WeekdayValue
): Promise<WeekEventOption[]> => {
  const queryParams = courseIds.reduce((acc, curr) => {
    acc.append("courseIds", curr);
    return acc;
  }, new URLSearchParams());

  queryParams.append("weekdays", weekday);
  const response = await resourceAxiosInstance.get<WeekEventOptionRaw[]>(
    `/week-events/options`,
    {
      cache: false,
      params: queryParams,
    }
  );

  const weekEventOptions = response.data;

  return weekEventOptions.flatMap<WeekEventOption>((rawOption) => {
    const groups = groupBy(
      rawOption.options,
      (option) =>
        `${option.weekday}-timeslots-${option.timeslots.join(
          ","
        )}-rooms-${option.rooms.join(",")}`
    );

    const options = Object.values(groups).map((options) => {
      return {
        ...options[0],
        weeks: options.map((option) => option.week),
      };
    });

    return {
      ...rawOption,
      options,
    };
  });
};
