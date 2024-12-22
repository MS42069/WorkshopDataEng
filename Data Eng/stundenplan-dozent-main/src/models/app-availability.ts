import { axiosInstance } from "../config/axios.config";

export type AppAvailabilityDTO = {
  startDate: Date | null;
  endDate: Date | null;
};

export const getAppAvailability = async () => {
  let result = await axiosInstance.get<AppAvailabilityDTO>(
    "/wishes-app-activation",
    { cache: false }
  );
  if (result.data) {
    let startDate = result.data.startDate;
    let endDate = result.data.endDate;

    if (startDate == null || endDate == null) {
      return result.data;
    }

    if (typeof startDate === "string") {
      startDate = new Date(startDate);
    }
    if (typeof endDate === "string") {
      endDate = new Date(endDate);
    }

    startDate.setHours(startDate.getHours() + 1);
    endDate.setHours(endDate.getHours() + 1);

    return { endDate: endDate, startDate: startDate };
  } else {
    return null;
  }
};
