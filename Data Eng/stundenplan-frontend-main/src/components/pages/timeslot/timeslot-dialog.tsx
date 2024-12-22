import React, { useEffect, useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  TextField,
} from "@mui/material";
import "./timeslot-dialog.css";
import { Theme, useTheme } from "@mui/material";
import { TimeslotAPI } from "../../../models/timeslots";
import {
  createTimeslot,
  fetchTimeslot,
  fetchTimeslots,
  patchTimeslot,
} from "../../../shared/requests/timeslot";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import ErrorDisplay from "../../../shared/components/error-display";

interface TimeslotProps {
  showDialog: boolean;
  setShowDialog: React.Dispatch<React.SetStateAction<boolean>>;
  timeslot: TimeslotAPI | undefined;
  setClickedTimeslot: (_: TimeslotAPI | undefined) => void;
  timeslots: TimeslotAPI[];
  setTimeslots: React.Dispatch<React.SetStateAction<TimeslotAPI[]>>;
  setExternalModified?: React.Dispatch<React.SetStateAction<boolean>>;
}

const TimeslotDialog = ({
  showDialog,
  setShowDialog,
  timeslot,
  setClickedTimeslot,
  timeslots,
  setTimeslots,
  setExternalModified,
}: TimeslotProps) => {
  const theme: Theme = useTheme();

  const [startTime, setStartTime] = useState<string>("00:00");
  const [endTime, setEndTime] = useState<string>("00:00");

  const numberRegex = new RegExp("^(2[0-3]|[01]?[0-9]?):([0-5]?[0-9])?$");

  const handleInputChangeStartTime = (value: string) => {
    if (value === "") {
      setStartTime(":");
    } else if (numberRegex.test(value)) {
      setStartTime(String(value));
    }
  };

  const handleInputChangeEndTime = (value: string) => {
    if (value === "") {
      setEndTime(":");
    } else if (numberRegex.test(value)) {
      setEndTime(String(value));
    }
  };

  const getTitle = (): string => {
    const createTitle = "Zeitslot erstellen";
    const editTitle = "Zeitslot bearbeiten";
    if (timeslot) {
      return editTitle;
    }
    return createTitle;
  };

  const getSavingButtonText = (): string => {
    const createText = "Erstellen";
    const editText = "Speichern";
    if (timeslot) {
      return editText;
    }
    return createText;
  };

  useEffect(() => {
    if (showDialog) {
      if (timeslot) {
        fetchTimeslot(timeslot.id).then((response) => {
          setStartTime(response.startTime);
          setEndTime(response.endTime);
        });
      } else {
        setStartTime("00:00");
        setEndTime("00:00");
      }
    } else {
      setClickedTimeslot(undefined);
    }
  }, [showDialog]);

  const saveTimeslot = () => {
    const updatedTimeslot: TimeslotAPI = {
      id: timeslot?.id ?? "",
      startTime,
      endTime,
      index: Math.floor(Math.random() * 1000), // TODO Muss entfernt werden, sobald das backend das entfernt
    };
    if (timeslot) {
      patchTimeslot(updatedTimeslot).then(() => {
        resetDialog();
        if (setExternalModified) {
          setExternalModified(true);
        }
      });
      setTimeslots([
        updatedTimeslot,
        ...timeslots.filter((current) => current.id !== timeslot.id),
      ]);
    } else {
      createTimeslot(updatedTimeslot)
        .then(() =>
          fetchTimeslots().then((response) => {
            setTimeslots(response);
            if (setExternalModified) {
              setExternalModified(true);
            }
          })
        )
        .then(resetDialog);
    }
  };

  const onSubmit = () => {
    saveTimeslot();
  };

  const resetDialog = () => {
    setShowDialog(false);
  };

  return (
    <Dialog open={showDialog}>
      <DialogTitle
        sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
      >
        {getTitle()}
      </DialogTitle>
      <DialogContent
        sx={{ margin: "1rem", alignItems: "center" }}
        className="DialogContent"
      >
        <ErrorDisplay>
          <DialogContentText className="timeslot-dialog-context-title">
            Startzeit - Endzeit
          </DialogContentText>
          <div className="timeslot-dialog-textfields">
            <TextField
              sx={{ width: "10rem" }}
              id="textfiled-starthour"
              label=""
              className=""
              variant="outlined"
              value={startTime}
              onChange={(e) => handleInputChangeStartTime(e.target.value)}
            />
            <p className="timeslot-dialog-text-middle-divider">-</p>
            <TextField
              sx={{ width: "10rem" }}
              id="textfield-endhour"
              label=""
              className=""
              variant="outlined"
              value={endTime}
              onChange={(e) => handleInputChangeEndTime(e.target.value)}
            />
          </div>
        </ErrorDisplay>
      </DialogContent>
      <DialogActions>
        <SubmitButtons
          submitText={getSavingButtonText()}
          cancelText="Abbrechen"
          onClickSubmit={onSubmit}
          onClickCancel={resetDialog}
        />
      </DialogActions>
    </Dialog>
  );
};

export default TimeslotDialog;
