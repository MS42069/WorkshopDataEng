import { resourceAxiosInstance } from "../../config/axios.config";
import { sortDesc } from "../sorting";
import {
  LectureAPI,
  LectureAPICreate,
  LectureForPlanAPI,
} from "../../models/lecture";
import { LecturesForPlanFilter } from "../../models/lectures-for-plan-filter";
import { createTimetablePath } from "../path.util";

// TODO: rename into courses
export const fetchLectures = () =>
  resourceAxiosInstance.get<LectureAPI[]>("/courses", {
    id: createTimetablePath("courses"),
  });

export const createLecture = (lecture: LectureAPICreate) =>
  resourceAxiosInstance.post<LectureAPI>("/courses", lecture, {
    cache: { update: { [createTimetablePath("courses")]: "delete" } },
  });

export const fetchLecture = (lectureId: string | undefined) =>
  resourceAxiosInstance.get<LectureAPI>(`/courses/${lectureId}`, {
    cache: false,
  });

export const deleteLecture = (lectureId: string) =>
  resourceAxiosInstance.delete(`/courses/${lectureId}`, {
    cache: { update: { [createTimetablePath("courses")]: "delete" } },
  });

export const updateLecture = (lecture: LectureAPICreate, lectureId: string) =>
  resourceAxiosInstance.put<LectureAPICreate>(
    `/courses/${lectureId}`,
    lecture,
    { cache: { update: { [createTimetablePath("courses")]: "delete" } } }
  );

export const getLecturesForPlan = (filterOptions?: LecturesForPlanFilter) =>
  resourceAxiosInstance
    .get<LectureForPlanAPI[]>(`/courses/toPlan`, {
      cache: false,
      params: {
        employeeId: filterOptions?.employee?.id,
        degreeSemesterId: filterOptions?.subject?.id,
      },
    })
    .then((response) => {
      response.data = response.data.sort((a, b) =>
        sortDesc(a.degreeOfFreedom, b.degreeOfFreedom)
      );

      return response;
    });
