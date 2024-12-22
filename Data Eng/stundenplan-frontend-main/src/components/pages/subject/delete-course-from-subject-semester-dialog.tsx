/* eslint-disable no-unused-vars */
import React, { useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  Button,
  DialogContentText,
  DialogActions,
} from "@mui/material";
import "./add-subject-semester-dialog.css";
import { Theme, useTheme } from "@mui/material";

interface DeleteProps {
  showDialog: boolean;
  setShowDialog: React.Dispatch<React.SetStateAction<boolean>>;
  callback: (courseId: string) => void;
  courseId: string;
}

const DeleteCourseFromSubjectSemesterDialog = ({
  showDialog,
  setShowDialog,
  callback,
  courseId,
}: DeleteProps) => {
  const theme: Theme = useTheme();

  return (
    <Dialog open={showDialog}>
      <DialogTitle
        sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
      >
        Kurs aus Fachsemester löschen
      </DialogTitle>
      <DialogContent sx={{ marginTop: "1rem" }} className="DialogContent">
        <DialogContentText>
          Sind Sie sich sicher, dass sie den Kurs aus dem Fachsemester löschen
          wollen?
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button
          className="ButtonYes"
          onClick={() => {
            callback(courseId);
            setShowDialog(false);
          }}
        >
          Ja
        </Button>
        <Button
          className="ButtonNo"
          onClick={() => {
            setShowDialog(false);
          }}
        >
          Nein
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default DeleteCourseFromSubjectSemesterDialog;
