import { resourceAxiosInstance } from "../../config/axios.config";
import {
  SubjectSemesterAPI,
  SubjectSemesterInternal,
} from "../../models/subjectSemester";
import { createTimetablePath } from "../path.util";

export const fetchSubjectSemesters = () =>
  resourceAxiosInstance.get<SubjectSemesterAPI[]>("/semesters", {
    id: createTimetablePath("subject-semester"),
  });

export const createSubjectSemester = (
  subjectSemester: SubjectSemesterInternal
) =>
  resourceAxiosInstance.post<SubjectSemesterAPI>(
    "/semesters",
    subjectSemester,
    {
      cache: {
        update: { [createTimetablePath("subject-semester")]: "delete" },
      },
    }
  );

export const fetchSubjectSemester = (subjectSemesterId: string) =>
  resourceAxiosInstance.get<SubjectSemesterAPI>(
    `/semesters/${subjectSemesterId}`,
    { cache: false }
  );

export const deleteSubjectSemester = (subjectSemesterId: string) =>
  resourceAxiosInstance.delete(`/semesters/${subjectSemesterId}`, {
    cache: { update: { [createTimetablePath("subject-semester")]: "delete" } },
  });

export const patchSubjectSemester = (patchData: {
  subjectSemester: SubjectSemesterInternal;
  subjectSemesterId: string | undefined;
}) =>
  resourceAxiosInstance.patch<SubjectSemesterAPI>(
    `/semesters/${patchData.subjectSemesterId}`,
    patchData.subjectSemester,
    {
      cache: {
        update: { [createTimetablePath("subject-semester")]: "delete" },
      },
    }
  );
