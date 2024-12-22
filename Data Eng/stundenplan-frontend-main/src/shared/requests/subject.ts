import { resourceAxiosInstance } from "../../config/axios.config";
import { SubjectAPI } from "../../models/subject";
import { createTimetablePath } from "../path.util";

export type SubjectAPIData = {
  name: string;
  shortName: string;
  semesterAmount: number;
  schoolType: string;
  studyRegulation: string;
  semesters: string[];
};

export const patchSubject = ({
  subject,
  subjectId,
}: {
  subject: SubjectAPIData;
  subjectId: string | undefined;
}) =>
  resourceAxiosInstance.patch<SubjectAPI>(`/degrees/${subjectId}`, subject, {
    cache: { update: { [createTimetablePath("subject")]: "delete" } },
  });

export const fetchSubjects = () =>
  resourceAxiosInstance.get<SubjectAPI[]>("/degrees", {
    id: createTimetablePath("subject"),
  });

export const createSubject = (subject: SubjectAPIData) =>
  resourceAxiosInstance.post<SubjectAPI>("/degrees", subject, {
    cache: { update: { [createTimetablePath("subject")]: "delete" } },
  });

export const fetchSubject = (subjectId: string) =>
  resourceAxiosInstance.get<SubjectAPI>(`/degrees/${subjectId}`, {
    cache: false,
  });

export const deleteSubject = (subjectId: string) =>
  resourceAxiosInstance.delete(`/degrees/${subjectId}`, {
    cache: { update: { [createTimetablePath("subject")]: "delete" } },
  });
