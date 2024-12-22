import { useEffect } from "react";
import { TimeslotAPI } from "../../../models/timeslots";
import {
  WeekdayDescription,
  getAllWeekdays,
} from "../../../enums/weekday.enum";
import { fetchTimeslots } from "../../requests/timeslot";
import {
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
} from "@mui/material";

import "./readonly-planning-overview.css";
import { ReadonlyPlanningOverviewTableEntry } from "./readonly-planning.overview.types";
import { getEntries } from "./readonly-planning.util";
import ReadonlyPlanningOverviewCell from "./readonly-planning-overview-cell";
import useTimeslots from "../../../hooks/useTimeslots.hook";
import { groupBy } from "../../group";

interface ReadonlyPlanningOverviewProps {
  items: ReadonlyPlanningOverviewTableEntry[];
}

function ReadonlyPlanningOverview({ items }: ReadonlyPlanningOverviewProps) {
  const { timeslots, setTimeslots } = useTimeslots();

  const { available: availableEntries, blocked: blockedEntries } = groupBy(
    items,
    (item) => (item.type === "available" ? item.type : "blocked")
  );
  const getRelevantEntries = (
    timeslot: TimeslotAPI,
    weekday: WeekdayDescription
  ) => {
    const entries = getEntries(blockedEntries ?? [], weekday, timeslot);
    if (entries.length) return entries;
    return getEntries(availableEntries ?? [], weekday, timeslot);
  };

  useEffect(() => {
    fetchTimeslots().then((data) => setTimeslots(data));
  }, []);

  return (
    <TableContainer sx={{ marginLeft: "0.5rem", height: "100%" }}>
      <Table aria-label="customized table">
        <TableHead>
          <TableRow>
            <TableCell></TableCell>
            {timeslots.map((timeslot) => (
              <TableCell
                className="readonly-planning-overview__table-border"
                key={timeslot.id}
              >
                {`${timeslot.startTime} - ${timeslot.endTime}`}
              </TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {getAllWeekdays().map((weekday) => (
            <TableRow key={weekday.ord}>
              <TableCell
                component="th"
                scope="row"
                size="small"
                className="readonly-planning-overview__table-border"
                sx={{ width: "20px" }}
              >
                {weekday.abbr}
              </TableCell>
              {timeslots.map((timeslot) => (
                <ReadonlyPlanningOverviewCell
                  key={timeslot.id}
                  timeslot={timeslot}
                  timeslots={timeslots}
                  entries={getRelevantEntries(timeslot, weekday)}
                />
              ))}
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default ReadonlyPlanningOverview;
