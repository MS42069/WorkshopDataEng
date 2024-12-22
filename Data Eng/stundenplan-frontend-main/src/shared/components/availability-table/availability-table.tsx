import React, { useEffect, useState } from "react";
import {
  Paper,
  Table,
  TableBody,
  TableContainer,
  TableHead,
  TableRow,
  TableCell,
  styled,
  tableCellClasses,
  tableRowClasses,
  Button,
} from "@mui/material";
import { TimeslotAPI } from "../../../models/timeslots";
import { fetchTimeslots } from "../../requests/timeslot";
import {
  WeekdayValue,
  WeekdayDescription,
  getAllWeekdays,
} from "../../../enums/weekday.enum";
import AddIcon from "@mui/icons-material/Add";

export interface AvailabilityAPI {
  weekday: WeekdayValue;
  timeslot: TimeslotAPI;
}

export interface AvailabilityTableProps {
  availability: AvailabilityAPI[];
  setAvailability: React.Dispatch<React.SetStateAction<AvailabilityAPI[]>>;
}

function AvailabilityTable({
  availability,
  setAvailability,
}: AvailabilityTableProps) {
  const weekDays = getAllWeekdays();
  const [timeslots, setTimeslots] = useState<TimeslotAPI[]>([]);

  useEffect(() => {
    fetchTimeslots().then(setTimeslots);
  }, []);

  const isAvailable = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): boolean => !!getAvailability(weekday, timeslot);

  const getAvailability = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): AvailabilityAPI | undefined =>
    availability.find(
      (element) =>
        element.weekday === weekday.value && element.timeslot.id == timeslot.id
    );

  const handleClick = (weekday: WeekdayDescription, timeslot: TimeslotAPI) => {
    let updatedAvailabilty: AvailabilityAPI[] = [];
    let specificTime = getAvailability(weekday, timeslot);
    if (!specificTime) {
      specificTime = { weekday: weekday.value, timeslot };
      updatedAvailabilty = [...availability, specificTime];
    } else {
      updatedAvailabilty = [...availability].filter(
        (element) =>
          element.timeslot.id !== timeslot.id ||
          element.weekday !== weekday.value
      );
    }
    setAvailability(updatedAvailabilty);
  };

  const selectAllEntries = () => {
    const availabilities: AvailabilityAPI[] = [];
    const weekdaysWithoutWeekend = weekDays.filter(
      (weekday) => weekday.value !== "SATURDAY" && weekday.value !== "SUNDAY"
    );

    for (const timeslot of timeslots) {
      for (const weekday of weekdaysWithoutWeekend) {
        availabilities.push({ timeslot, weekday: weekday.value });
      }
    }

    setAvailability(availabilities);
  };

  const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
      backgroundColor: theme.palette.primary.main,
      color: theme.palette.common.white,
    },
  }));

  const StyledTableRow = styled(TableRow)(({ theme }) => ({
    [`&.${tableRowClasses.head}`]: {
      backgroundColor: theme.palette.primary.main,
      color: theme.palette.common.white,
    },
  }));

  return (
    <TableContainer component={Paper} sx={{ marginTop: "1rem" }}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell
              style={{ width: "(100/" + weekDays.length + "%" }}
              className="TableRow"
            >
              <div className="flex-between flex-align">
                Verfügbar
                <Button
                  color="secondary"
                  variant="outlined"
                  size="small"
                  onClick={selectAllEntries}
                >
                  <AddIcon />
                </Button>
              </div>
            </StyledTableCell>
            {weekDays.map((weekday) => (
              <StyledTableCell
                key={weekday.value}
                style={{ width: "(100/" + weekDays.length + "%" }}
                className="TableRow"
              >
                {weekday.name}
              </StyledTableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {timeslots.map((timeslot) => (
            <StyledTableRow key={timeslot.id}>
              <StyledTableCell component="th" scope="row">
                {timeslot.startTime + " - " + timeslot.endTime}
              </StyledTableCell>
              {weekDays.map((weekday) => (
                <StyledTableCell
                  key={weekday.value}
                  className="TableCell"
                  align="left"
                  style={{
                    backgroundColor: isAvailable(weekday, timeslot)
                      ? "green"
                      : "",
                  }}
                  onClick={() => handleClick(weekday, timeslot)}
                ></StyledTableCell>
              ))}
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default AvailabilityTable;
