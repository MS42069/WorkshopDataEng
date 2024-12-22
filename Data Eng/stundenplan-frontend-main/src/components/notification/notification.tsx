import React from "react";
import Snackbar from "@mui/material/Snackbar";
import { useDispatch, useSelector } from "react-redux";
import MuiAlert, { AlertProps } from "@mui/material/Alert";
import Stack from "@mui/material/Stack";
import { RootState } from "../../redux/store";
import { closeNotification } from "../../redux/notification.reducer";

const Alert = React.forwardRef<HTMLDivElement, AlertProps>(function Alert(
  props,
  ref
) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

export function Notification() {
  const { isVisible, message, status } = useSelector(
    ({ notificationReducer }: RootState) => notificationReducer
  );

  const dispatch = useDispatch();

  return (
    <Stack spacing={2} sx={{ width: "100%" }}>
      <Snackbar
        open={isVisible}
        autoHideDuration={5000}
        onClose={() => dispatch(closeNotification())}
        anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
      >
        <Alert
          onClose={() => dispatch(closeNotification())}
          severity={status}
          sx={{ width: "100%" }}
        >
          {message}
        </Alert>
      </Snackbar>
    </Stack>
  );
}
