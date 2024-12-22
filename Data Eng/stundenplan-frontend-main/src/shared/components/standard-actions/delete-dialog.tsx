import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
} from "@mui/material";
import "./delete-dialog.css";
import { Theme, useTheme } from "@mui/material";
import SubmitButtons from "./submit-buttons";
import ErrorDisplay from "../error-display";

export interface DeleteProps {
  showDialog: boolean;
  dialogTitle: string;
  deleteInfo: string | undefined;
  onClickSubmit: () => void;
  onClickCancel: () => void;
}

const DeleteDialog = ({
  showDialog,
  onClickSubmit,
  dialogTitle,
  deleteInfo,
  onClickCancel,
}: DeleteProps) => {
  const theme: Theme = useTheme();

  return (
    <Dialog open={showDialog}>
      <DialogTitle
        sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
      >
        {dialogTitle}
      </DialogTitle>
      <DialogContent sx={{ marginTop: "1rem" }} className="DialogContent">
        <ErrorDisplay>
          <DialogContentText>
            {deleteInfo &&
            deleteInfo !== undefined &&
            !deleteInfo.includes("undefined")
              ? deleteInfo
              : "Entity konnte nicht gelöscht werden! Klicken Sie JA, wenn Sie es erneut versuchen wollen."}
          </DialogContentText>
        </ErrorDisplay>
      </DialogContent>
      <DialogActions>
        <SubmitButtons
          submitText="Ja"
          cancelText="Nein"
          onClickSubmit={onClickSubmit}
          onClickCancel={onClickCancel}
        />
      </DialogActions>
    </Dialog>
  );
};

export default DeleteDialog;
