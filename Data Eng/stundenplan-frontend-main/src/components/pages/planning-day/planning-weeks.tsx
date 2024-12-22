import { ExpandMore } from "@mui/icons-material";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { SortingDirection } from "../../../enums/sort-by";
import { WeekdayDescription } from "../../../enums/weekday.enum";
import { RoomShort, sortRooms } from "../../../models/room";
import { TimeslotAPI } from "../../../models/timeslots";
import { WeekEvent, WeekEventOptions } from "../../../models/week-event";
import SelectWeekday from "../../../shared/components/select-weekday";
import { fetchRooms } from "../../../shared/requests/room.requests";
import { fetchTimeslots } from "../../../shared/requests/timeslot";
import { fetchTimetable } from "../../../shared/requests/timetable.requests";
import CustomTableCell from "./custom-table-cell";
import { groupBy } from "../../../shared/group";

/**
 * Renders one timetable for all weeks and another one per week to plan on.
 */
export function PlanningWeeks({
  currentWeekday,
  updateCurrentWeekday,
  plannedLectures,
  onClickTable,
  weekEventOptions,
}: {
  currentWeekday: WeekdayDescription;
  updateCurrentWeekday: (value: WeekdayDescription) => void;
  plannedLectures: WeekEvent[];
  onClickTable: (
    room: RoomShort,
    timeslot: TimeslotAPI,
    weekday: WeekdayDescription,
    week: number,
    optionIdx: number
  ) => void;
  weekEventOptions: WeekEventOptions[] | undefined;
}) {
  const [rooms, setRooms] = useState<RoomShort[]>([]);
  const [timeslots, setTimeslots] = useState<TimeslotAPI[]>([]);
  const [weeksPerSemester, setWeeksPerSemester] = useState<number>(0);

  const { timetableId } = useParams();

  useEffect(() => {
    fetchRooms().then((response) =>
      setRooms(
        sortRooms(response.data, {
          column: "abbreviation",
          direction: SortingDirection.Descending,
        })
      )
    );
    fetchTimeslots().then((response) => setTimeslots(response));
    fetchTimetable(timetableId).then((response) =>
      setWeeksPerSemester(response.weeksPerSemester)
    );
  }, []);

  // Allow lectures that can be planned into all weeks to be planned into the "all weeks" plan
  weekEventOptions?.forEach((option) => {
    if (option.weeks.length === weeksPerSemester) {
      option.weeks.push(0);
    }
  });

  const plannedLecturesByWeek = groupBy(
    plannedLectures,
    (plannedLecture) => plannedLecture.week
  );

  return (
    <div className="full-size">
      {/* + 1 for "All Weeks" */}
      {Array.from({ length: weeksPerSemester + 1 }, (_, idx) => (
        <Accordion
          key={idx}
          TransitionProps={{ unmountOnExit: true }}
          defaultExpanded={idx === 0}
        >
          <AccordionSummary expandIcon={<ExpandMore />}>
            {idx === 0 ? "Alle Wochen" : `Woche ${idx}`}
          </AccordionSummary>
          <AccordionDetails>
            <SelectWeekday
              value={currentWeekday}
              onChange={updateCurrentWeekday}
            >
              <TableContainer sx={{ marginLeft: "0.5rem", height: "100%" }}>
                <Table aria-label="customized table">
                  <TableHead>
                    <TableRow>
                      <TableCell></TableCell>
                      {timeslots.map((timeslot) => (
                        <TableCell
                          className="planning-week__table-border"
                          key={timeslot.id}
                        >
                          {`${timeslot.startTime} - ${timeslot.endTime}`}
                        </TableCell>
                      ))}
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {rooms.map((room) => (
                      <TableRow key={room.id}>
                        <TableCell
                          component="th"
                          scope="row"
                          size="small"
                          sx={{ width: "1.4rem" }}
                        >
                          {room.abbreviation}
                        </TableCell>
                        {timeslots.map((timeslot) => (
                          <CustomTableCell
                            key={`${timeslot.id} ${room.id} ${currentWeekday.value}`}
                            room={room}
                            timeslot={timeslot}
                            plannedWeekEvents={
                              idx === 0
                                ? plannedLectures
                                : plannedLecturesByWeek[idx] ?? []
                            }
                            week={idx}
                            weekday={currentWeekday}
                            options={weekEventOptions}
                            cellWidth={`calc(100% / ${timeslots.length})`}
                            onClick={onClickTable}
                          />
                        ))}
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </SelectWeekday>
          </AccordionDetails>
        </Accordion>
      ))}
    </div>
  );
}
