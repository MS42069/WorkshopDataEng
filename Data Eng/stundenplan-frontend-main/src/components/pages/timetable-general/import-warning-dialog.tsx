import React, { useEffect } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  DialogContentText,
} from "@mui/material";
import { Theme, useTheme } from "@mui/material";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import { Timetable, stringOfTimetable } from "../../../models/timetable";

interface ImportWarningProps {
  showDialog: boolean;
  setShowDialog: React.Dispatch<React.SetStateAction<boolean>>;
  currentTimetableAsString: string;
  importFrom: Timetable | null;
  verifiedSave: () => void;
}

const ImportWarningDialog = ({
  showDialog,
  setShowDialog,
  currentTimetableAsString,
  importFrom,
  verifiedSave,
}: ImportWarningProps) => {
  const theme: Theme = useTheme();

  const onSubmit = () => {
    verifiedSave();
    setShowDialog(false);
  };

  const onCancel = () => {
    setShowDialog(false);
  };

  return (
    <Dialog open={showDialog} fullWidth maxWidth="md">
      <DialogTitle
        sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
      >
        Stammdatenimportwarnung
      </DialogTitle>
      <DialogContent className="DialogContent">
        <DialogContentText sx={{ marginTop: "1rem" }}>
          {`Möchten Sie die Stammdaten aus ${currentTimetableAsString} wirklich mit ${
            importFrom === null
              ? "ERROR IMPORT PLAN NICHT GEFUNDEN"
              : stringOfTimetable(importFrom)
          } ersetzen? `}
          <br />
          {`Jegliche Stammdaten aus ${currentTimetableAsString} sind danach nicht mehr zugreifbar.`}
          <br />
          Diese Aktion kann nicht rückgängig gemacht werden.
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <SubmitButtons
          submitText="Stammdaten ersetzen"
          cancelText="Abbrechen"
          onClickSubmit={() => onSubmit()}
          onClickCancel={() => onCancel()}
        />
      </DialogActions>
    </Dialog>
  );
};

export default ImportWarningDialog;
