import { useEffect, useRef, useState } from "react";
import PageLayout from "../../../shared/components/page-layout";
import {
  fetchUnLockDates,
  postUnLockDates,
} from "../../../shared/requests/dates.request";
import { DateTimePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import Button from "@mui/material/Button";
import "dayjs/locale/de";
import dayjs from "dayjs";
import {
  Alert,
  Box,
  CardActions,
  Collapse,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  IconButton,
  TextField,
  Theme,
  useTheme,
} from "@mui/material";
import { WishesDates } from "../../../models/dates";
import { Close } from "@mui/icons-material";

function WishesAppActivation() {
  const [unlockDate, setUnlockDate] = useState<Date | null>();
  const [lockDate, setLockDate] = useState<Date | null>();
  const [showDialog, setShowDialog] = useState<boolean>(false);
  const [errortext, setErrortext] = useState<string | null>();
  const [openAlert, setOpenAlert] = useState<boolean>(false);
  const theme: Theme = useTheme();
  const isInitRender = useRef(true);

  function validDates(): string | null {
    if (!unlockDate || !lockDate) {
      return "Es darf kein leeres Datum gesetzt werden.";
    }

    const now = new Date();

    if (now > unlockDate) {
      return "Das Freischaltdatum darf nicht in der Vergangenheit liegen.";
    } else if (unlockDate > lockDate) {
      return "Das Sperrdatum darf nicht vor dem Freischaltdatum liegen.";
    } else if (unlockDate == lockDate) {
      return "Das Sperrdatum darf nicht dem Freischaltdatum entsprechen.";
    }

    return null;
  }

  function saveDates(): void {
    const error = validDates();
    if (error == null) {
      //Save the dates
      postUnLockDates({
        startDate: unlockDate,
        endDate: lockDate,
      } as WishesDates).then(() => {
        console.log("Daten gespeichert");
        setOpenAlert(true);
      });
    } else {
      // show error message as alert
      setErrortext(error);
      setShowDialog(true);
    }
  }

  function showDatesSavedResponse(): JSX.Element {
    return (
      <Box sx={{ width: "100%" }}>
        <Collapse in={openAlert}>
          <Alert
            action={
              <IconButton
                aria-label="close"
                color="inherit"
                size="small"
                onClick={() => {
                  setOpenAlert(false);
                }}
              >
                <Close fontSize="inherit" />
              </IconButton>
            }
            sx={{ mb: 2 }}
          >
            Die Daten wurden gespeichert.
          </Alert>
        </Collapse>
      </Box>
    );
  }

  function showWarningDialog(): JSX.Element {
    return (
      <Dialog open={showDialog} fullWidth maxWidth="md">
        <DialogTitle
          sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
        >
          Warnung
        </DialogTitle>
        <DialogContent className="DialogContent">
          <DialogContentText sx={{ marginTop: "1rem" }}>
            {errortext}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={(props: any) => {
              setShowDialog(false);
              setErrortext(null);
            }}
          >
            Ok
          </Button>
        </DialogActions>
      </Dialog>
    );
  }

  useEffect(() => {
    if (isInitRender.current) {
      fetchUnLockDates().then((response) => {
        if (response.data) {
          const resultDates: WishesDates = response.data;
          setUnlockDate(
            resultDates.startDate ? new Date(resultDates.startDate) : null
          );
          setLockDate(
            resultDates.endDate ? new Date(resultDates.endDate) : null
          );
        }
      });

      isInitRender.current = false;
    }
  });
  return (
    <PageLayout title="Verwaltung Wunscheintragung">
      {showWarningDialog()}
      <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="de">
        <DateTimePicker
          className="unlock-app__text-field"
          label={"Freischaltdatum"}
          disableMaskedInput
          value={dayjs(unlockDate)}
          onChange={(value) => {
            if (value) setUnlockDate(value.toDate());
          }}
          renderInput={(params) => (
            <TextField {...params} sx={{ marginTop: "1.5rem" }} />
          )}
        />
      </LocalizationProvider>
      <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="de">
        <DateTimePicker
          className="lock-app-view__text-field"
          label={"Sperrdatum"}
          disableMaskedInput
          value={dayjs(lockDate)}
          onChange={(value) => {
            if (value) setLockDate(value.toDate());
          }}
          renderInput={(params) => (
            <TextField {...params} sx={{ marginTop: "1.5rem" }} />
          )}
        />{" "}
      </LocalizationProvider>
      <CardActions className="flex flex-end">
        <Button onClick={(event: any) => saveDates()}>Daten speichern</Button>
      </CardActions>
      {showDatesSavedResponse()}
    </PageLayout>
  );
}

export default WishesAppActivation;
