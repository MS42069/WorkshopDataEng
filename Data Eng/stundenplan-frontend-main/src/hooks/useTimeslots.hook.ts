import { useEffect, useState } from "react";
import { TimeslotAPI } from "../models/timeslots";
import { useSort } from "./useSort.hook";
import { SortingDirection } from "../enums/sort-by";

const useTimeslots = () => {
  const { sortedValues, setInitialValues } = useSort<TimeslotAPI>([], {
    key: "startTime",
    direction: SortingDirection.Descending,
  });

  const [timeslots, setTimeslots] = useState<TimeslotAPI[]>([]);

  useEffect(() => {
    setInitialValues(timeslots);
  }, [timeslots]);

  const getTimeslotAfter = (timeslot: TimeslotAPI) => {
    const idxOfNext =
      sortedValues.findIndex(({ id }) => id === timeslot.id) + 1;

    return idxOfNext >= sortedValues.length ? sortedValues[idxOfNext] : null;
  };

  const getTimeslotBefore = (timeslot: TimeslotAPI) => {
    const idxOfNext =
      sortedValues.findIndex(({ id }) => id === timeslot.id) - 1;

    return idxOfNext < 0 ? null : sortedValues[idxOfNext];
  };

  return {
    getTimeslotAfter,
    timeslots: sortedValues,
    getTimeslotBefore,
    setTimeslots,
  };
};

export default useTimeslots;
