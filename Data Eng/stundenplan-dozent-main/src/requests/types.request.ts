import { axiosInstance } from "../config/axios.config";
import { SemesterType } from "../models/timetable";

//Axios für Semestertyp
export const fetchSemesterTypes = () =>
  axiosInstance.get<SemesterType[]>("semesterTypes", { id: "semester-types" });

