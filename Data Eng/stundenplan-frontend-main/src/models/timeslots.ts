import { SortBy, SortingDirection } from "../enums/sort-by";
import { sortAsc, sortDesc } from "../shared/sorting";
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
    ["08:00:00", "09:15:00"],
    ["09:30:00", "10:45:00"],
    ["11:00:00", "12:15:00"],
    ["12:30:00", "13:45:00"],
    ["14:00:00", "15:15:00"],
    ["15:30:00", "16:45:00"],
    ["17:00:00", "18:15:00"],
  ];
  const output = [] as TimeslotAPI[];
  let index = 0;
  const maxTimeslots = timeslotTimes.length;
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
export function sortTimeslots(
  timeslots: TimeslotAPI[],
  sortBy: SortBy<TimeslotAPI>
): TimeslotAPI[] {
  const sortedTimeslots = [...timeslots];
  if (!sortBy.column || sortBy.direction === SortingDirection.NoDirection) {
    return sortedTimeslots.sort((a, b) => sortDesc(a.startTime, b.startTime));
  }

  const sortByColumn = sortBy.column;
  switch (sortBy.direction) {
    case SortingDirection.Ascending:
      sortedTimeslots.sort((a, b) => sortAsc(a[sortByColumn], b[sortByColumn]));
      break;
    case SortingDirection.Descending:
      sortedTimeslots.sort((a, b) =>
        sortDesc(a[sortByColumn], b[sortByColumn])
      );
      break;
  }

  return sortedTimeslots;
}
