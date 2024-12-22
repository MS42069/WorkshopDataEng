import { TableCell } from "@mui/material";
import { TimeslotAPI } from "../../../models/timeslots";
import {
  BlockedEntry,
  ReadonlyPlanningOverviewTableEntry,
} from "./readonly-planning.overview.types";

import "./readonly-planning-overview-cell.css";
import { fetchRooms } from "../../requests/room.requests";
import { RoomAPI } from "../../../models/room";
import { useEffect, useState } from "react";
import { formatWeeks } from "../../formatter/week.formatter";

interface ReadonlyPlanningOverviewCellProps {
  timeslot: TimeslotAPI;
  entries?: ReadonlyPlanningOverviewTableEntry[];
  timeslots: TimeslotAPI[];
}

const getCellText = (entry: ReadonlyPlanningOverviewTableEntry | undefined) => {
  if (entry?.type !== "blocked") {
    return "";
  }

  return entry.course.name;
};

const getRoomNamesByEntry = (
  rooms: RoomAPI[],
  entry: ReadonlyPlanningOverviewTableEntry | undefined
) => {
  if (entry?.type !== "blocked") {
    return "";
  }

  return rooms
    .filter((room) => entry.roomIds.includes(room.id))
    .map((room) => room.abbreviation)
    .join(",");
};

const getBackgroundColor = (
  entry: ReadonlyPlanningOverviewTableEntry | undefined
) => {
  if (!entry) {
    return "";
  }

  switch (entry?.type) {
    case "unavailable":
      return "";
    case "blocked":
      return "darkgray";
    case "available":
      return "green";
    default:
      return "";
  }
};

function ReadonlyPlanningOverviewCell({
  timeslot,
  entries,
  timeslots,
}: ReadonlyPlanningOverviewCellProps) {
  const [rooms, setRooms] = useState<RoomAPI[]>([]);

  useEffect(() => {
    fetchRooms().then(({ data }) => setRooms(data));
  }, []);

  let content = null;

  if (entries) {
    const groups: Record<
      string,
      { entry: ReadonlyPlanningOverviewTableEntry; weeks: number[] }
    > = {};
    for (const entry of entries) {
      (groups[entry.type === "blocked" ? entry.course.id : entry.type] ??= {
        entry: entry,
        weeks: [],
      }).weeks.push(entry.week);
    }

    content = Object.values(groups).map(({ weeks, entry }) => {
      const text = getCellText(entry);
      if (!text) return null;
      return (
        <div
          key={(entry as BlockedEntry).course.id}
          className="readonly-planning-overview-cell__cell-text-wrapper"
        >
          <span>{text}</span>
          <div className="planning-planning-cell__footer">
            <span>({formatWeeks(weeks)})</span>
            <span>{getRoomNamesByEntry(rooms, entry)}</span>
          </div>
        </div>
      );
    });
  }

  return (
    <TableCell
      className="planning-week__table-border"
      align="left"
      key={timeslot.id}
      sx={{
        height: "5rem",
        width: `calc(100% / ${timeslots.length})`,
        maxWidth: `calc(100% / ${timeslots.length})`,
        backgroundColor: `${getBackgroundColor(entries?.[0])}`,
      }}
    >
      {content}
    </TableCell>
  );
}

export default ReadonlyPlanningOverviewCell;
