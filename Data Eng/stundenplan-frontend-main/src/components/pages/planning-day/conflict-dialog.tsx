import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Button,
} from "@mui/material";
import WarningIcon from "@mui/icons-material/Warning";
import { useState } from "react";
import { ConflictError } from "../../../models/conflict-error";
import { ScheduleWeekEvent } from "../../../models/week-event";
import ConflictProblemDisplay from "./conflict-error";

export function useConflictDialog() {
  const [conflict, setConflict] = useState<{
    error: ConflictError | undefined;
    value: ScheduleWeekEvent;
  }>();

  const handleClose = () => {
    setConflict(undefined);
  };

  return {
    setConflict,
    ConflictDialog({
      onConfirm,
    }: {
      onConfirm: (value: ScheduleWeekEvent) => Promise<void>;
    }) {
      if (conflict === undefined) return null;
      return (
        <Dialog open={true} onClose={handleClose}>
          <DialogTitle>
            <div className="flex flex-between flex-align">
              Konflikt entstanden <WarningIcon />
            </div>
          </DialogTitle>

          <DialogContent>
            <DialogContentText>
              {conflict?.error?.problems.map((problem, idx) => (
                <ConflictProblemDisplay key={idx} problem={problem} />
              ))}
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>Abbrechen</Button>
            <Button
              onClick={() => {
                onConfirm(conflict.value).then(handleClose, handleClose);
              }}
            >
              Planen
            </Button>
          </DialogActions>
        </Dialog>
      );
    },
  };
}
