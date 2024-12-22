import { axiosInstance } from "../../config/axios.config";
import { EmployeeType } from "../../models/employee";
import { CourseType } from "../../models/lecture";
import { RoomType } from "../../models/room";
import { SchoolType } from "../../models/subject";
import { SemesterType } from "../../models/timetable";

//Axios für Raumtyp
export const fetchRoomTypes = () =>
  axiosInstance.get<RoomType[]>("roomTypes", { id: "room-types" });

export const fetchRoomType = (roomTypeId: string) =>
  axiosInstance.get<RoomType>(`/roomTypes/${roomTypeId}`, { cache: false });

export const createRoomType = (roomType: RoomType) =>
  axiosInstance.post<RoomType>("/roomTypes", roomType, {
    cache: { update: { "room-types": "delete" } },
  });

export const patchRoomType = (roomType: RoomType) =>
  axiosInstance.patch<RoomType>(`/roomTypes/${roomType.id}`, roomType, {
    cache: { update: { "room-types": "delete" } },
  });

export const deleteRoomType = (roomTypeId: string) =>
  axiosInstance.delete(`/roomTypes/${roomTypeId}`, {
    cache: { update: { "room-types": "delete" } },
  });

//Axios für Schultyp
export const fetchSchoolTypes = () =>
  axiosInstance.get<SchoolType[]>("schoolTypes", { id: "school-types" });

export const fetchSchoolType = (schoolTypeId: string) =>
  axiosInstance.get<SchoolType>(`/schoolTypes/${schoolTypeId}`, {
    cache: false,
  });

export const createSchoolType = (schoolType: SchoolType) =>
  axiosInstance.post<SchoolType>("/schoolTypes", schoolType, {
    cache: { update: { "school-types": "delete" } },
  });

export const patchSchoolType = (schoolType: SchoolType) =>
  axiosInstance.patch<SchoolType>(`/schoolTypes/${schoolType.id}`, schoolType, {
    cache: { update: { "school-types": "delete" } },
  });

export const deleteSchoolType = (schoolTypeId: string) =>
  axiosInstance.delete(`/schoolTypes/${schoolTypeId}`, {
    cache: { update: { "school-types": "delete" } },
  });

//Axios für Veranstaltungstyp
export const fetchCourseTypes = () =>
  axiosInstance.get<CourseType[]>("courseTypes", { id: "course-types" });

export const fetchCourseType = (courseTypeId: string) =>
  axiosInstance.get<CourseType>(`/courseTypes/${courseTypeId}`, {
    cache: false,
  });

export const createCourseType = (courseType: CourseType) =>
  axiosInstance.post<CourseType>("/courseTypes", courseType, {
    cache: { update: { "course-types": "delete" } },
  });

export const patchCourseType = (courseType: CourseType) =>
  axiosInstance.patch<CourseType>(`/courseTypes/${courseType.id}`, courseType, {
    cache: { update: { "course-types": "delete" } },
  });

export const deleteCourseType = (courseTypeId: string) =>
  axiosInstance.delete(`/courseTypes/${courseTypeId}`, {
    cache: { update: { "course-types": "delete" } },
  });

//Axios für Semestertyp
export const fetchSemesterTypes = () =>
  axiosInstance.get<SemesterType[]>("semesterTypes", { id: "semester-types" });

export const fetchSemesterType = (semesterTypeId: string) =>
  axiosInstance.get<SemesterType>(`/semesterTypes/${semesterTypeId}`, {
    cache: false,
  });

export const createSemesterType = (semesterType: SemesterType) =>
  axiosInstance.post<SemesterType>("/semesterTypes", semesterType, {
    cache: { update: { "semester-types": "delete" } },
  });

export const patchSemesterType = (semesterType: SemesterType) =>
  axiosInstance.patch<SemesterType>(
    `/semesterTypes/${semesterType.id}`,
    semesterType,
    { cache: { update: { "semester-types": "delete" } } }
  );

export const deleteSemesterType = (semesterTypeId: string) =>
  axiosInstance.delete(`/semesterTypes/${semesterTypeId}`, {
    cache: { update: { "semester-types": "delete" } },
  });

//Axios für Mitarbeitertyp
export const fetchEmployeeTypes = () =>
  axiosInstance.get<EmployeeType[]>("employeeTypes", { id: "employee-types" });

export const fetchEmployeeType = (employeeTypeId: string) =>
  axiosInstance.get<EmployeeType>(`/employeeTypes/${employeeTypeId}`, {
    cache: false,
  });

export const createEmployeeType = (employeeType: EmployeeType) =>
  axiosInstance.post<EmployeeType>("/employeeTypes", employeeType, {
    cache: { update: { "employee-types": "delete" } },
  });

export const patchEmployeeType = (employeeType: EmployeeType) =>
  axiosInstance.patch<EmployeeType>(
    `/employeeTypes/${employeeType.id}`,
    employeeType,
    { cache: { update: { "employee-types": "delete" } } }
  );

export const deleteEmployeeType = (employeeTypeId: string) =>
  axiosInstance.delete(`/employeeTypes/${employeeTypeId}`, {
    cache: { update: { "employee-types": "delete" } },
  });
