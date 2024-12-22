import React, { useEffect, useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  DialogContentText,
  List,
  ListItemButton,
  Checkbox,
  ListItemText,
} from "@mui/material";
import { Theme, useTheme } from "@mui/material";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import { CopyTimetableOptions } from "../../../models/timetable";

const copyOptions = [
  "Mitarbeiter",
  "Raum",
  "Veranstaltung",
  "Fachrichtung",
  "Zeitslot",
  "Verplante Veranstaltungen",
  "Sonderevents",
];

interface ImportOptionsProps {
  showDialog: boolean;
  setShowDialog: React.Dispatch<React.SetStateAction<boolean>>;
  options: CopyTimetableOptions;
  setOptions: React.Dispatch<React.SetStateAction<CopyTimetableOptions>>;
}

const ImportOptionsDialog = ({
  showDialog,
  setShowDialog,
  options,
  setOptions,
}: ImportOptionsProps) => {
  const theme: Theme = useTheme();

  const [employee, setEmployee] = useState<boolean>(true);
  const [room, setRoom] = useState<boolean>(true);
  const [course, setCourse] = useState<boolean>(true);
  const [degree, setDegree] = useState<boolean>(true);
  const [degreesemester, setDegreeSemester] = useState<boolean>(true);
  const [timeslot, setTimeslot] = useState<boolean>(true);
  const [weekevent, setWeekevent] = useState<boolean>(true);
  const [specialevent, setSpecialevent] = useState<boolean>(true);

  useEffect(() => {
    setEmployee(options.employee);
    setRoom(options.room);
    setCourse(options.course);
    setDegree(options.degree);
    setDegreeSemester(options.degreesemester);
    setTimeslot(options.timeslot);
    setWeekevent(options.weekevent);
    setSpecialevent(options.specialevent);
  }, []);

  const onSubmit = () => {
    const Options: CopyTimetableOptions = {
      employee,
      room,
      course,
      degree,
      degreesemester,
      timeslot,
      weekevent,
      specialevent,
    };
    setOptions(Options);
    setShowDialog(false);
  };

  const onCancel = () => {
    setShowDialog(false);
  };

  const isChecked = (value: string) => {
    switch (value) {
      case "Mitarbeiter":
        return employee;
      case "Raum":
        return room;
      case "Veranstaltung":
        return course;
      case "Fachrichtung":
        return degree;
      case "Zeitslot":
        return timeslot;
      case "Verplante Veranstaltungen":
        return weekevent;
      case "Sonderevents":
        return specialevent;
      default:
        break;
    }
  };

  const handleToggle = (value: string) => () => {
    switch (value) {
      case "Mitarbeiter":
        if (employee === true) {
          if (weekevent === true) {
            setWeekevent(false);
          }
        }
        setEmployee(!employee);
        break;
      case "Raum":
        if (room === true) {
          if (weekevent === true) {
            setWeekevent(false);
          }
        }
        setRoom(!room);
        break;
      case "Veranstaltung":
        if (course === true) {
          if (weekevent === true) {
            setWeekevent(false);
          }
        }
        setCourse(!course);
        break;
      case "Fachrichtung":
        if (degree === true) {
          if (weekevent === true) {
            setWeekevent(false);
          }
        }
        setDegree(!degree);
        setDegreeSemester(!degreesemester);
        break;
      case "Zeitslot":
        if (timeslot === true) {
          if (weekevent === true) {
            setWeekevent(false);
          }
        }
        setTimeslot(!timeslot);
        break;
      case "Verplante Veranstaltungen":
        if (weekevent === false) {
          if (employee === false) setEmployee(true);
          if (room === false) setRoom(true);
          if (course === false) setCourse(true);
          if (degree === false) {
            setDegree(true);
            setDegreeSemester(true);
          }
          if (timeslot === false) setTimeslot(true);
        }
        setWeekevent(!weekevent);
        break;
      case "Sonderevents":
        setSpecialevent(!specialevent);
        break;
      default:
        break;
    }
  };

  return (
    <Dialog open={showDialog} fullWidth maxWidth="md">
      <DialogTitle
        sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
      >
        Stammdatenimportoptionen
      </DialogTitle>
      <DialogContent className="DialogContent">
        <DialogContentText sx={{ marginTop: "1rem" }}>
          {"Welche Stammdaten sollen ersetzt werden?"}
        </DialogContentText>
        {/* Warum list mit checkboxen ? */}
        <List>
          {copyOptions.map((value) => {
            return (
              <ListItemButton key={value} onClick={handleToggle(value)}>
                <Checkbox
                  edge="start"
                  checked={isChecked(value)}
                  tabIndex={-1}
                  disableRipple
                />
                <ListItemText id={value} primary={value} />
              </ListItemButton>
            );
          })}
        </List>
      </DialogContent>
      <DialogActions>
        <SubmitButtons
          submitText="Speichern"
          cancelText="Abbrechen"
          onClickSubmit={() => onSubmit()}
          onClickCancel={() => onCancel()}
        />
      </DialogActions>
    </Dialog>
  );
};

export default ImportOptionsDialog;
