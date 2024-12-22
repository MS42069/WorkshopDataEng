import { getAllWeekdays } from "../enums/weekday.enum";

export interface Timeslot {
  id: string;
  starthour: number;
  startminute: number;
  endhour: number;
  endminute: number;
}

export type TimeslotAPI = {
  id: string;
  startTime: string;
  endTime: string;
  index: number;
};
export function getTimeSlots(): TimeslotAPI[] {
  const timeslotTimes = [
    ["08:00", "09:15"],
    ["09:30", "10:45"],
    ["11:00", "12:15"],
    ["12:30", "13:45"],
    ["14:00", "15:15"],
    ["15:30", "16:45"],
    ["17:00", "18:15"],
  ];
  let output = [] as TimeslotAPI[];
  let index = 0;
  let maxTimeslots = timeslotTimes.length;
  getAllWeekdays().forEach((_) => {
    while (index < maxTimeslots) {
      const newTimeslot = {
        id: index.toString(),
        startTime: timeslotTimes[index][0],
        endTime: timeslotTimes[index][1],
        index: index,
      } as TimeslotAPI;
      output.push(newTimeslot);
      index++;
    }
  });
  return output;
}
