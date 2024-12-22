import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { AppDispatch, RootState } from "../../redux/store";
import {
  decreaseLayer,
  increaseLayer,
  setLayer,
} from "../../redux/error.reducer";
import { useSelector } from "react-redux";
import { Alert } from "@mui/material";
import React from "react";

interface ErrorDisplayProps {
  children: React.ReactNode;
}

function ErrorDisplay({ children }: ErrorDisplayProps) {
  const dispatch = useDispatch<AppDispatch>();
  const { layer, message } = useSelector(
    (state: RootState) => state.errorReducer
  );

  const [componentLayer, setComponentLayer] = useState<number>();

  useEffect(() => {
    setComponentLayer(1);
    dispatch(setLayer(1));

    return () => {
      dispatch(decreaseLayer());
    };
  }, []);

  return (
    <>
      {componentLayer === layer && message && (
        <Alert severity="error" sx={{ marginBottom: "1rem" }}>
          {message}
        </Alert>
      )}
      {children}
    </>
  );
}

export default ErrorDisplay;
