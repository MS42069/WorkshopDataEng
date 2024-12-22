import { TimeslotAPI } from "../models/timeslots";

export const timeslotForOverview = ({
  index,
  id,
  startTime,
  endTime,
}: TimeslotAPI): TimeslotAPI => {
  const timeLength = 5;
  return {
    id,
    index,
    startTime: startTime.slice(0, timeLength),
    endTime: endTime.slice(0, timeLength),
  };
};
