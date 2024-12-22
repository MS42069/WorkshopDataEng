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
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
  Box,
} from "@mui/material";
import { TimeslotAPI, getTimeSlots } from "../../../models/timeslots";
import {
  WeekdayValue,
  WeekdayDescription,
  getAllWeekdays,
} from "../../../enums/weekday.enum";
import { getDivider } from "../../shared/shared";

export interface AvailabilityAPI {
  weekday: WeekdayValue;
  timeslot: TimeslotAPI;
  available: AvailabilityValue;
  isAccepted?: boolean;
  reason?: string;
}

export interface AvailabilityTableProps {
  availability: AvailabilityAPI[];
  setAvailability: React.Dispatch<React.SetStateAction<AvailabilityAPI[]>>;
  useHardConstraint: boolean;
  availableTitle?: string;
  notAvailableTitle?: string;
}

//TODO check if we can change these types to numbers or at least not some random strings
export type AvailabilityValue = "gerne" | "ungerne" | "nein" | "egal";

function AvailabilityTable({
  availability,
  setAvailability,
  useHardConstraint,
  availableTitle,
  notAvailableTitle,
}: AvailabilityTableProps) {

  const [availabilityType, setAvailablilityType] = useState<AvailabilityValue>("egal");
  const [openReason, setOpenReason] = useState<boolean>(false);
  const [reason, setReason] = useState<string>("");
  const [reasonIsEmpty, setReasonIsEmpty] = useState<boolean>(true);
  const [checkedWeekday, setCheckedWeekday] = useState<WeekdayDescription | null>(null);
  const [checkedTimeslot, setCheckedTimeslot] = useState<TimeslotAPI | null>( null );
  const [checkedAvailability, setCheckedAvailability] = useState<AvailabilityValue | null>(null);
  const weekDays = getAllWeekdays().splice(0, 6);
  const timeslots = getTimeSlots();

  function openPopup(
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI,
    available: AvailabilityValue
  ) {
    setCheckedAvailability(available);
    setCheckedTimeslot(timeslot);
    setCheckedWeekday(weekday);
    setOpenReason(true);
  }

  function closePopup(aborted: boolean) {
    // Do not save an empty reason
    if ( !aborted && reason.trim().length == 0 ){
      return;
    }

      setOpenReason(false);
      
      //The reason has been saved, so we want to insert the availability with the given reason!
      if (!aborted) {
      handleClick(
        checkedWeekday!,
        checkedTimeslot!,
        checkedAvailability!,
        false,
        reason
      );
      setCheckedAvailability(null);
      setCheckedTimeslot(null);
      setCheckedWeekday(null);
    }
    setReason("");
  }

  const getReason = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): string => {
    return getAvailability(weekday, timeslot, "nein")?.reason ?? "";
  };


  // Find element matching weekday, timeslot & availability value
  // Returns the matching element of type AvailabilityAPI 
  // or undefined of no matching element was found
  // If availabilityValue is undefined does find the first element at weekday & timeslot
  const getAvailability = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI,
    availabilityValue: AvailabilityValue | undefined
  ): AvailabilityAPI | undefined => 
    availability.find(
      (element) =>
        element.weekday === weekday.value &&
        element.timeslot.index === timeslot.index &&
        availabilityValue ? (element.available === availabilityValue) : true
  );

  // WTF IS THIS??? -- TODO simplify this mess
  //TODO these functions are basically all equal. Why do we need 5 of each? -- use Parameters
  const isAvailable = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): boolean => !!getAvailability(weekday, timeslot, "gerne");

  const isHardNotAvailable = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): boolean => !!getAvailability(weekday, timeslot, "nein");

  // Really? -- isEgalAvailable -- wtf!
  const isEgalAvailable = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): boolean => !!getAvailability(weekday, timeslot, "egal");

  const isNotAvailable = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): boolean => !!getAvailability(weekday, timeslot, "ungerne");


  // Given a weekday & timeslot find out if any availability is set
  const getAnyAvailability = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI
  ): AvailabilityAPI | undefined =>
      getAvailability(weekday, timeslot, undefined);

  //TODO what does this function do? -- pretty much everything o.O thats bad  
  const handleClick = (
    weekday: WeekdayDescription,
    timeslot: TimeslotAPI,
    available: AvailabilityValue,
    callPopup: boolean,
    reason?: string
  ) => {
    
    let updatedAvailabilty: AvailabilityAPI[] = [];
    let specificTime = getAnyAvailability(weekday, timeslot);

    if (!specificTime) {
      return;
    }

    let localReason: string | undefined;
    localReason = reason;

    /** Hardconstraint --> needs reason */
    if (available === "nein" && callPopup) {
      if (specificTime?.reason != null) {
        setReason(specificTime!.reason!);
      }
      openPopup(weekday, timeslot, available);
      return;
    }

    if (available === specificTime.available) {
      return;
    }

    const i = availability.findIndex(
      (a) => a.timeslot.index === timeslot.index && a.weekday === weekday.value
    );
    const temp: AvailabilityAPI = {
      ...availability[i],
      available: available,
      reason: localReason ? localReason : undefined,
    };

    updatedAvailabilty = availability.filter((_, index) => i !== index);
    updatedAvailabilty.push(temp);

    setAvailability(updatedAvailabilty);
  };

  // Reason has to have atleast one character otherwise display a notification
  // Effect hook gets called everytime something changes on the page but does not block anything
  useEffect(() => {
    if( reason.trim().length == 0){
      setReasonIsEmpty(true);
    } else {
      setReasonIsEmpty(false);
    }
  });

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

  const handleAvailabilityChange = (
    event: React.MouseEvent<HTMLElement>,
    newValue: AvailabilityValue | null
  ) => {
    if (newValue !== null) {
      setAvailablilityType(newValue);
    }
  };

  return (
    <>
      <Dialog open={openReason} onClose={() => closePopup(false)}>
        <DialogTitle>Begründung</DialogTitle>
        <DialogContent>
        <Box
          sx={{
            p: 1,
            m: 1,
            width: "100%",
            paddingX: "20%",
            maxWidth: "1100px",
            display: "flex",
            flexDirection: "column",
            textAlign: "center",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          { reasonIsEmpty ? 
          <DialogContentText
            sx={{
              border: "2px solid red",
              borderRadius: "80px",
              fontWeight: "bold",
              padding: "0.5em",
            }}
          >
            Bitte eine Begründung eingeben.
          </DialogContentText>
        : <></>  
        }
        </Box>
          <DialogContentText>
            Bitte geben Sie hier eine möglichst kurze und aussagekräftige
            Begründung für die Abwesenheit zu diesem Zeitslot an.
          </DialogContentText>
          <TextField
            autoFocus
            fullWidth
            value={reason}
            onChange={(event) => setReason(event.target.value)}
            margin="dense"
            id="name"
            label="Begründung"
            type="text"
            variant="standard"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => closePopup(true)}>Abbrechen</Button>
          <Button onClick={() => closePopup(false)}>Speichern</Button>
        </DialogActions>
      </Dialog>
      <TableContainer component={Paper} sx={{ marginTop: "1rem" }}>
        <ToggleButtonGroup
          sx={{ justifyContent: "center", width: "100%" }}
          value={availabilityType}
          exclusive
          onChange={(event, value) => {
            handleAvailabilityChange(event, value);
          }}
          aria-label="Priorisierung der Zeitslots"
        >
          <ToggleButton value={"gerne"} aria-label="Ja">
            {availableTitle ?? "Zeitslot gerne belegen (grün)"}
          </ToggleButton>
          <ToggleButton value={"egal"} aria-label="Gleichgültig">
            Gleichgültig (grau)
          </ToggleButton>
          <ToggleButton value={"ungerne"} aria-label="Ungerne">
            {notAvailableTitle ?? "Zeitslot lieber freihalten (orange)"}
          </ToggleButton>
          {useHardConstraint && (
            <ToggleButton value={"nein"} aria-label="Nein">
              Zeitslot zwingend freihalten (rot) <br /> - nach Absprache -
            </ToggleButton>
          )}
        </ToggleButtonGroup>
        {getDivider()}
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell
                style={{
                  width: (2 / weekDays.length + 1) * 10 + "%",
                  border: "2px solid white",
                }}
                className="TableRow"
              ></StyledTableCell>
              {weekDays.map((weekday) => (
                <StyledTableCell
                  key={weekday.value}
                  style={{
                    width: (1 / weekDays.length + 1) * 10 + "%",
                    border: "2px solid white",
                    textAlign: "center",
                  }}
                  className="TableRow"
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
                  style={{ border: "2px solid white", background: "lightgrey" }}
                >
                  {timeslot.startTime + " - " + timeslot.endTime}
                </StyledTableCell>
                {weekDays.map((weekday) => {
                  // Check if we are not available for this weekday & timeslot
                  const availablility = getAvailability(weekday, timeslot, "nein");

                  return (
                    <StyledTableCell
                      key={weekday.value}
                      className="TableCell"
                      align="left"
                      style={{
                        cursor: getAnyAvailability(weekday, timeslot)
                          ? "pointer"
                          : "default",
                        border: "2px solid white",
                        maxWidth: "160px",
                        overflow: "hidden",
                        textOverflow: "ellipsis",
                        //TODO o.O -- Could use a separate function instead of 4! ternary operators
                        backgroundColor: isAvailable(weekday, timeslot)
                          ? "green"
                          : isNotAvailable(weekday, timeslot)
                          ? "orange"
                          : isHardNotAvailable(weekday, timeslot)
                          ? "red"
                          : isEgalAvailable(weekday, timeslot)
                          ? "lightgray"
                          : "black",
                      }}
                      onClick={() =>
                        handleClick(weekday, timeslot, availabilityType, true)
                      }
                    >
                      {
                        <>
                          {!getAnyAvailability(weekday, timeslot) ? (
                            <div
                              style={{ color: "white", textAlign: "center" }}
                            >
                              X
                            </div>
                          ) : (
                            <></>
                          )}
                          {getReason(weekday, timeslot)}
                          {!availablility?.isAccepted && availablility?.available === "nein" ? (
                            <div
                              style={{ marginTop: "0.5rem", color: "white" }}
                            >
                              (Noch nicht akzeptiert)
                            </div>
                          ) : (
                            <></>
                          )}
                        </>
                      }
                    </StyledTableCell>
                  );
                })}
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
}

export default AvailabilityTable;
