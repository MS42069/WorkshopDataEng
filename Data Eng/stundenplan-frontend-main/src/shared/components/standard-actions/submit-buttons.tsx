import React from "react";
import { Button } from "@mui/material";

export interface SubmitProps {
  submitText: string;
  cancelText: string;
  submitLink?: string;
  cancelLink?: string;
  isValid?: boolean;
  onClickSubmit?: () => void;
  onClickCancel?: () => void;
}

function SubmitButtons({
  submitText,
  cancelText,
  onClickCancel,
  onClickSubmit,
}: SubmitProps) {
  return (
    <>
      <Button variant="contained" onClick={onClickSubmit}>
        {submitText}
      </Button>
      <Button variant="text" onClick={onClickCancel}>
        {cancelText}
      </Button>
    </>
  );
}

export default SubmitButtons;
