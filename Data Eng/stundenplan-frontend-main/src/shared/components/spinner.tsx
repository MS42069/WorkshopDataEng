import { Backdrop, CircularProgress } from "@mui/material";
import React from "react";

interface SpinnerProps {
  isLoading: boolean;
}

function Spinner({ isLoading }: SpinnerProps) {
  return (
    <Backdrop
      sx={{
        color: "fff",
        zIndex: (theme) => theme.zIndex.tooltip + 1,
      }}
      open={isLoading}
    >
      <CircularProgress color="secondary" />
    </Backdrop>
  );
}

export default Spinner;
