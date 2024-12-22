import React from "react";
import { TableCell } from "@mui/material";
import {
  aggregateEvents,
  findFirstIdxOfSuggestion,
  getPlannedWeekEvents,
  indexInSuggestions,
} from "./helper";
import { RoomShort } from "../../../models/room";
import { TimeslotAPI } from "../../../models/timeslots";
import { WeekdayDescription } from "../../../enums/weekday.enum";
import { WeekEvent, WeekEventOptions } from "../../../models/week-event";
import { formatWeeks } from "../../../shared/formatter/week.formatter";

interface CustomTableCellProps {
  room: RoomShort;
  timeslot: TimeslotAPI;
  plannedWeekEvents: WeekEvent[];
  week: number;
  weekday: WeekdayDescription;
  options?: WeekEventOptions[];
  cellWidth: string;
  onClick: (
    room: RoomShort,
    timeslot: TimeslotAPI,
    weekday: WeekdayDescription,
    week: number,
    optionIdx: number
  ) => void;
}

const CustomTableCell = ({
  room,
  timeslot,
  plannedWeekEvents,
  week,
  weekday,
  options = [],
  cellWidth,
  onClick,
}: CustomTableCellProps) => {
  const suggestionIndex = findFirstIdxOfSuggestion(
    room,
    timeslot,
    weekday,
    week,
    options
  );
  const isInSuggestion = indexInSuggestions(
    room,
    timeslot,
    weekday,
    week,
    options
  );

  const relevantEvents = aggregateEvents(
    getPlannedWeekEvents(room, timeslot, weekday, week, plannedWeekEvents)
  );

  return (
    <TableCell
      key={timeslot.id}
      className="planning-week__table-border"
      align="left"
      sx={{
        backgroundColor: isInSuggestion ? "green" : "",
        cursor: isInSuggestion ? "pointer" : "default",
        textAlign: "center",
        color: isInSuggestion ? "white" : "",
        height: "1rem",
        width: cellWidth,
        maxWidth: cellWidth,
      }}
      onClick={() => onClick(room, timeslot, weekday, week, suggestionIndex)}
    >
      {relevantEvents.map((entry) => (
        <div key={entry.course.id}>
          <div>{entry.course.name}</div>
          <div>
            (
            {typeof entry.course.courseType === "string"
              ? entry.course.courseType
              : entry.course.courseType.name}
            )
          </div>
          {week === 0 && <div>({formatWeeks(entry.weeks)})</div>}
        </div>
      ))}
    </TableCell>
  );
};

export default CustomTableCell;
