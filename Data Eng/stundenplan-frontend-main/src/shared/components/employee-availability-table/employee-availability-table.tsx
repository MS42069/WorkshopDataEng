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
  ToggleButton,
  ToggleButtonGroup,
  Typography,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
} from "@mui/material";
import { AvailabilityAPI } from "../availability-table/availability-table";
import { Constraints } from "../../../models/employee";
import {
  getAllWeekdays,
  WeekdayDescription,
} from "../../../enums/weekday.enum";
import { getTimeSlots, TimeslotAPI } from "../../../models/timeslots";
import { fetchTimeslots } from "../../requests/timeslot";
import { log, time } from "console";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.primary.main,
    color: theme.palette.common.white,
  },
}));

export interface EmployeeAvailabilityTableProps {
  availability: AvailabilityAPI[];
  setAvailability: React.Dispatch<React.SetStateAction<AvailabilityAPI[]>>;
  constraints: Constraints[];
}

export type AvailabilityValue = "worktime" | "noWorktime";

function EmployeeAvailabilityTable({
  availability,
  setAvailability,
  constraints,
}: EmployeeAvailabilityTableProps) {
  const weekDays = getAllWeekdays().splice(0, 6);
  const [timeslots, setTimeslots] = useState<TimeslotAPI[]>([]);
  useEffect(() => {
    fetchTimeslots().then(setTimeslots);
  }, []);

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

  function isWorkTime(
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): boolean {
    return !!availability.find(
      (element) =>
        element.weekday === weekday.value &&
        element.timeslot.index === timeslot.index
    );
  }
  function isWanted(
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): boolean {
    return !!constraints.find(
      (element) =>
        element.weekday === weekday.value &&
        element.constraintValue === "WANTED" &&
        element.constraintType === "SOFT" &&
        element.timeslotIndex === timeslot.index
    );
  }

  function isNotWanted(
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): boolean {
    return !!constraints.find(
      (element) =>
        element.weekday === weekday.value &&
        element.constraintValue === "NOT_WANTED" &&
        element.constraintType === "SOFT" &&
        element.timeslotIndex === timeslot.index
    );
  }

  function isHardConstraintPending(
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): boolean {
    return !!constraints.find(
      (element) =>
        element.weekday === weekday.value &&
        element.constraintValue === "NOT_WANTED" &&
        element.constraintType === "HARD" &&
        element.timeslotIndex === timeslot.index
    );
  }

  const getReason = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): string => {
    const find = constraints.find(
      (element) =>
        element.weekday === weekday.value &&
        element.constraintValue === "NOT_WANTED" &&
        element.constraintType === "HARD" &&
        element.timeslotIndex === timeslot.index
    );
    if (find) {
      if (find.reason) {
        return find.reason;
      }
    }
    return "";
  };

  const getAvailability = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): AvailabilityAPI | undefined =>
    availability.find(
      (element) =>
        element.weekday === weekday.value && element.timeslot.id == timeslot.id
    );

  const handleClick = (
    weekday: WeekdayDescription | undefined,
    timeslot: TimeslotAPI | undefined
  ) => {
    let updatedAvailabilty: AvailabilityAPI[] = [];
    let specificTime;
    if (weekday && timeslot) {
      specificTime = getAvailability(weekday, timeslot);
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
    }
    let entries = 0;
    const newElements: AvailabilityAPI[] = [];
    if (!weekday && timeslot) {
      availability.map((element) => {
        if (element.timeslot.index == timeslot.index) {
          entries += 1;
        }
      });
      if (entries == 0) {
        weekDays.forEach((day) => {
          specificTime = { weekday: day.value, timeslot };
          newElements.push(specificTime);
        });
        updatedAvailabilty = [...availability, ...newElements];
      } else {
        updatedAvailabilty = [...availability].filter(
          (element) => element.timeslot.id !== timeslot.id
        );
      }
    }
    if (weekday && !timeslot) {
      availability.map((element) => {
        if (element.weekday == weekday.value) {
          entries += 1;
        }
      });
      if (entries == 0) {
        timeslots.forEach((slot) => {
          specificTime = { weekday: weekday.value, timeslot: slot };
          newElements.push(specificTime);
        });
        updatedAvailabilty = [...availability, ...newElements];
      } else {
        updatedAvailabilty = [...availability].filter(
          (element) => element.weekday !== weekday.value
        );
      }
    }
    setAvailability(updatedAvailabilty);
  };

  return (
    <TableContainer component={Paper} sx={{ marginTop: "1rem" }}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell
              style={{
                width: (2 / weekDays.length + 1) * 10 + "%",
                border: "2px solid white",
              }}
            ></StyledTableCell>
            {weekDays.map((weekday) => (
              <StyledTableCell
                key={weekday.value}
                style={{
                  cursor: "pointer",
                  width: (1 / weekDays.length + 1) * 10 + "%",
                  border: "2px solid white",
                  textAlign: "center",
                }}
                className=""
                onClick={() => handleClick(weekday, undefined)}
              >
                {weekday.name}
              </StyledTableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {timeslots.map((timeslot) => (
            <StyledTableRow key={timeslot.index}>
              <StyledTableCell
                component="th"
                scope="row"
                style={{
                  cursor: "pointer",
                  color: "white",
                  border: "2px solid white",
                  background: "#1c2764",
                  textAlign: "center",
                }}
                onClick={() => handleClick(undefined, timeslot)}
              >
                {timeslot.startTime + " - " + timeslot.endTime}
              </StyledTableCell>
              {weekDays.map((weekday) => (
                <StyledTableCell
                  key={weekday.value}
                  className="TableCell"
                  align="left"
                  style={{
                    cursor: "pointer",
                    border: "2px solid white",
                    maxWidth: "160px",
                    overflow: "hidden",
                    textOverflow: "ellipsis",
                    backgroundColor: !isWorkTime(weekday, timeslot)
                      ? "black"
                      : isWanted(weekday, timeslot)
                      ? "green"
                      : isNotWanted(weekday, timeslot)
                      ? "orange"
                      : isHardConstraintPending(weekday, timeslot)
                      ? "red"
                      : "lightgray",
                  }}
                  onClick={() => handleClick(weekday, timeslot)}
                >
                  {
                    <>
                      {!isWorkTime(weekday, timeslot) ? (
                        <div style={{ color: "white", textAlign: "center" }}>
                          X
                        </div>
                      ) : (
                        <></>
                      )}
                      <div style={{ color: "white", textAlign: "center" }}>
                        {getReason(weekday, timeslot)}
                      </div>
                    </>
                  }
                </StyledTableCell>
              ))}
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
export default EmployeeAvailabilityTable;
