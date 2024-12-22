import { axiosInstance } from "../../config/axios.config";
import { WishesDates } from "../../models/dates";

//Axios für Raumtyp
export const fetchUnLockDates = () =>
  axiosInstance.get<WishesDates>("/wishes-app-activation", {});

export const postUnLockDates = (dates: WishesDates) =>
  axiosInstance.post<WishesDates>("/wishes-app-activation", dates, {});
