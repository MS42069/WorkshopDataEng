import React, { useEffect, useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  FormControl,
  FormControlLabel,
  Radio,
  RadioGroup,
  Autocomplete,
} from "@mui/material";
import "./special-date-dialog.css";
import "./timetable-detail.css";
import { Theme, useTheme } from "@mui/material";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import { isUpdate, isValidDate } from "./helper";
import {
  getSETypeText,
  SpecialDate,
  SpecialEventType,
  dayFormat,
  isFree,
  specialEventTypes,
} from "../../../models/timetable";
import dayjs from "dayjs";
import { Dayjs } from "dayjs";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import "dayjs/locale/de";

interface SpecialDateProps {
  showDialog: boolean;
  setShowDialog: React.Dispatch<React.SetStateAction<boolean>>;
  clickedSpecialDate: string;
  setClickedSpecialDate: React.Dispatch<React.SetStateAction<string>>;
  specialDates: SpecialDate[];
  setSpecialDates: React.Dispatch<React.SetStateAction<SpecialDate[]>>;
}

const SpecialDateDialog = ({
  showDialog,
  setShowDialog,
  clickedSpecialDate,
  setClickedSpecialDate,
  specialDates,
  setSpecialDates,
}: SpecialDateProps) => {
  const theme: Theme = useTheme();
  const [start, setStart] = useState<Dayjs>(dayjs());
  const [end, setEnd] = useState<Dayjs>(dayjs());
  const [specialEventType, setSpecialEventType] =
    useState<SpecialEventType | null>("FREE");
  const [savePushed, setSavePushed] = useState<boolean>(false);
  const checkParameter = "day";

  const isDateOccupied = (
    date: Dayjs,
    filteredDates: SpecialDate[]
  ): boolean => {
    return filteredDates.some(
      (specialDate) =>
        // check for same start and end
        date.isSame(specialDate.from, checkParameter) ||
        date.isSame(specialDate.to, checkParameter) ||
        // check for overlapping dates
        (date.isAfter(specialDate.from, checkParameter) &&
          date.isBefore(specialDate.to, checkParameter))
    );
  };

  const isOccupied = (): boolean => {
    // exclude date itself from occupation check
    const filteredDates = specialDates.filter(
      (date) => date.id !== clickedSpecialDate
    );
    return (
      // Checking if either one of the dates is in between another event
      isDateOccupied(start, filteredDates) ||
      isDateOccupied(end, filteredDates) ||
      // Or another event lies within the new event
      filteredDates.some(
        (specialDate) =>
          start.isBefore(specialDate.from, checkParameter) &&
          end.isAfter(specialDate.to, checkParameter)
      )
    );
  };

  const isValid = (): boolean => {
    return (
      !!specialEventType &&
      !isOccupied() &&
      (!isFree(specialEventType) ||
        (isFree(specialEventType) && isValidDate(start, end)))
    );
  };

  const getErrorText = (): string => {
    const invalidDates = "Startdatum muss vor dem Enddatum liegen";
    const dayIsOccupiedText = "Auf diesem Datum liegt bereits ein Sonderevent";
    if (!isValidDate(start, end)) {
      return invalidDates;
    } else if (isOccupied()) {
      return dayIsOccupiedText;
    }
    return "";
  };

  const logicalExchangeOptions: SpecialEventType[] = [...specialEventTypes];
  // Last entrie is not a logical exchange option
  logicalExchangeOptions.pop();

  const resetDialog = () => {
    setStart(dayjs());
    setEnd(dayjs());
    setSpecialEventType("FREE");
    setSavePushed(false);
  };

  useEffect(() => {
    if (isUpdate(clickedSpecialDate)) {
      const clickedDate = specialDates.find(
        (date) => date.id === clickedSpecialDate
      );
      if (clickedDate) {
        setStart(clickedDate.from);
        setEnd(clickedDate.to);
        setSpecialEventType(clickedDate.specialEventType);
      }
    } else {
      resetDialog();
    }
  }, [showDialog]);

  const handleSave = (): boolean => {
    if (isValid()) {
      if (!isUpdate(clickedSpecialDate)) {
        clickedSpecialDate = Math.floor(Math.random() * 1000).toString();
      }
      const resultDate: SpecialDate = {
        id: clickedSpecialDate,
        from: start,
        to: end,
        // Cast is secure, because specialEventType is checked for null in isValid()
        specialEventType: specialEventType as SpecialEventType,
      };

      const filteredDates = specialDates.filter(
        (date) => date.id !== clickedSpecialDate
      );
      setSpecialDates([...filteredDates, resultDate]);
      return true;
    }
    return false;
  };

  const onSubmit = () => {
    setSavePushed(true);
    if (handleSave()) {
      setShowDialog(false);
      setClickedSpecialDate("");
    }
  };

  const onCancel = () => {
    setShowDialog(false);
    setClickedSpecialDate("");
  };

  const getSavingButtonText = (): string => {
    const createText = "Erstellen";
    const editText = "Speichern";
    if (isUpdate(clickedSpecialDate)) {
      return editText;
    }
    return createText;
  };

  const getLabelFirst = (): string => {
    const holidayText = "Startdatum";
    const logicalExchangeText = "Datum";
    if (isFree(specialEventType)) {
      return holidayText;
    }
    return logicalExchangeText;
  };

  return (
    <Dialog open={showDialog} fullWidth maxWidth="md">
      <DialogTitle
        sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
      >
        Sonderevent
      </DialogTitle>
      <DialogContent className="DialogContent">
        <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="de">
          <div className="timetable-detail-view">
            <FormControl sx={{ marginTop: "1rem" }}>
              <RadioGroup
                aria-labelledby="radio-buttons-free"
                value={isFree(specialEventType) ? "free" : "logicalExchange"}
                name="radio-buttons-free"
                onChange={(event) => {
                  setSpecialEventType(
                    event.target.value === "free" ? "FREE" : "MONDAY_PLAN"
                  );
                  setSavePushed(false);
                }}
              >
                <FormControlLabel
                  value="free"
                  control={<Radio />}
                  label="Vorlesungsfrei"
                />
                <FormControlLabel
                  value="logicalExchange"
                  control={<Radio />}
                  label="Logischer Wochentag"
                />
              </RadioGroup>
            </FormControl>

            <div className="flex flex-row timetable-detail-view__form-row-widht">
              <DatePicker
                className="timetable-detail-view__text-field"
                label={getLabelFirst()}
                inputFormat={dayFormat}
                disableMaskedInput
                value={start}
                disableHighlightToday
                onChange={(changedValue) => {
                  if (changedValue) {
                    setSavePushed(false);
                    setStart(changedValue);
                    if (!isFree(specialEventType)) {
                      setEnd(changedValue);
                    }
                  }
                }}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    sx={{ marginTop: "1.5rem" }}
                    error={
                      savePushed &&
                      (isOccupied() ||
                        (isFree(specialEventType) && !isValidDate(start, end)))
                    }
                    helperText={savePushed ? getErrorText() : ""}
                  />
                )}
              />
              {isFree(specialEventType) ? (
                <DatePicker
                  className="timetable-detail-view__text-field"
                  label={"Enddatum"}
                  inputFormat={dayFormat}
                  disableMaskedInput
                  value={end}
                  disableHighlightToday
                  onChange={(changedValue) => {
                    if (changedValue) {
                      setSavePushed(false);
                      setEnd(changedValue);
                    }
                  }}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      sx={{ marginTop: "1.5rem" }}
                      error={
                        savePushed && (!isValidDate(start, end) || isOccupied())
                      }
                      helperText={savePushed ? getErrorText() : ""}
                    />
                  )}
                />
              ) : (
                <Autocomplete
                  className="timetable-detail-view__text-field"
                  sx={{ marginTop: "1.5rem" }}
                  autoHighlight
                  id="single-select-exchange-tags"
                  options={logicalExchangeOptions}
                  getOptionLabel={(option) => getSETypeText(option)}
                  value={specialEventType}
                  onChange={(_, value) => {
                    if (value) {
                      setSpecialEventType(value);
                    }
                    setSavePushed(false);
                  }}
                  renderInput={(params) => (
                    <TextField {...params} label="Gültiger Plan" />
                  )}
                  renderOption={(props, option) => {
                    return (
                      <li {...props} key={option}>
                        {getSETypeText(option)}
                      </li>
                    );
                  }}
                />
              )}
            </div>
          </div>
        </LocalizationProvider>
      </DialogContent>
      <DialogActions>
        <SubmitButtons
          submitText={getSavingButtonText()}
          cancelText="Abbrechen"
          onClickSubmit={() => onSubmit()}
          onClickCancel={() => onCancel()}
        />
      </DialogActions>
    </Dialog>
  );
};

export default SpecialDateDialog;
