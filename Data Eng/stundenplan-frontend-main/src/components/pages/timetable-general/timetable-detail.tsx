import { useEffect, useRef, useState } from "react";
import "./timetable-detail.css";
import {
  Autocomplete,
  Divider,
  IconButton,
  InputAdornment,
  List,
  ListItem,
  ListItemText,
  Paper,
  TextField,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import { Delete, Edit as EditIcon } from "@mui/icons-material";
import PageLayout from "../../../shared/components/page-layout";
import { useNavigate, useParams } from "react-router-dom";
import {
  CopyTimetableOptions,
  dayFormat,
  dayFormatShort,
  isFree,
  parseTimeTableToAPI,
  parseYearToShortFormat,
  Semester,
  SemesterType,
  sortSpecialDates,
  SpecialDate,
  stringOfSemester,
  stringOfSpecialDate,
  stringOfTimetable,
  Timetable,
  TimetableAPI,
} from "../../../models/timetable";
import SpecialDateDialog from "./specialDate-dialog";
import ImportWarningDialog from "./import-warning-dialog";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import DeleteDialog from "../../../shared/components/standard-actions/delete-dialog";
import { isUpdate } from "./helper";
import {
  fetchTimetable,
  updateTimetable,
  createTimetable,
  fetchTimetables,
  copyAllDataTimetable,
  copySelectedDataTimetable,
} from "../../../shared/requests/timetable.requests";
import dayjs from "dayjs";
import { Dayjs } from "dayjs";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import "dayjs/locale/de";
import ImportOptionsDialog from "./import-options-dialog";
import { checkedParseInt } from "../../../shared/parse.util";
import { fetchSemesterTypes } from "../../../shared/requests/types.request";
import SelectComponent from "../../../shared/components/select-component";

function TimetableDetailView() {
  const navigate = useNavigate();
  const { id } = useParams();

  const [info, setInfo] = useState<string>("");
  const [startDate, setStartDate] = useState<Dayjs>(dayjs());
  const [endDate, setEndDate] = useState<Dayjs>(dayjs());
  const [semester, setSemester] = useState<Semester>({
    semesterType: {} as SemesterType,
    // Parsing Year instead of using year() to change format
    year: parseYearToShortFormat(startDate),
  });
  const [specialDates, setSpecialDates] = useState<SpecialDate[]>([]);
  const [weeksPerSemester, setWeeksPerSemester] = useState<number>(12);
  const [showSpecialDateDialog, setShowSpecialDateDialog] =
    useState<boolean>(false);
  const [showSEDeleteDialog, setShowSEDeleteDialog] = useState<boolean>(false);
  const [showImportWarningDialog, setShowImportWarningDialog] =
    useState<boolean>(false);
  const [showImportOptionsDialog, setShowImportOptionsDialog] =
    useState<boolean>(false);
  const [clickedSpecialDate, setClickedSpecialDateId] = useState<string>("");
  const [importFrom, setImportFrom] = useState<Timetable | null>(null);
  const [importOptions, setImportOptions] = useState<Timetable[]>([]);
  const [copyOptions, setCopyOptions] = useState<CopyTimetableOptions>({
    employee: true,
    room: true,
    course: true,
    degree: true,
    degreesemester: true,
    timeslot: true,
    weekevent: true,
    specialevent: true,
  });
  const [savePushed, setSavePushed] = useState<boolean>(false);
  const [semesterTypes, setSemesterTypes] = useState<SemesterType[]>([]);
  const goBackToOverview = () => navigate(returnRoute);
  const returnRoute = "/data/timetables";
  const isInitRender = useRef(true);

  useEffect(() => {
    fetchSemesterTypes().then(({ data }) => {
      setSemesterTypes(data);
      if (data.length) {
        setSemester({ semesterType: data[0], year: semester.year });
      }
    });
    if (isInitRender.current) {
      fetchTimetables().then((response) =>
        setImportOptions(response.filter((timetable) => timetable.id !== id))
      );
      isInitRender.current = false;
    }
    if (isUpdate(id)) {
      fetchTimetable(id!).then((response) => {
        setInfo(response.info);
        setStartDate(response.start);
        setEndDate(response.end);
        setWeeksPerSemester(response.weeksPerSemester);
        setSpecialDates(response.specialDates);
        setSemester(response.semester);
      });
    }
  }, []);

  const handleInputChangeSemesterType = (value: string) => {
    semesterTypes.forEach((element) => {
      if (element.name.localeCompare(value) == 0) {
        setSemester({
          semesterType: element,
          year: semester.year,
        });
      }
    });
  };

  const getTitle = (): string => {
    const createTimetableTitle = "Vorlesungsplan erstellen";
    const editTimetableTitle = "Vorlesungsplan bearbeiten";
    if (isUpdate(id)) {
      return editTimetableTitle;
    }
    return createTimetableTitle;
  };

  const getSavingButtonText = (): string => {
    const createTimetableTitle = "Erstellen";
    const editTimetableTitle = "Speichern";
    if (isUpdate(id)) {
      return editTimetableTitle;
    }
    return createTimetableTitle;
  };

  const onSubmit = () => {
    if (isUpdate(id) && importFrom !== null) {
      setShowImportWarningDialog(true);
    } else {
      saveTimetable();
    }
  };

  const onCancel = () => {
    navigate("/timetables");
  };

  const isStartBeforeEnd = (): boolean => {
    const dateGranularityCheck = "day";
    return startDate.isBefore(endDate, dateGranularityCheck);
  };

  const isValid = (): boolean => {
    return !!info && isStartBeforeEnd();
  };

  const saveTimetable = () => {
    setSavePushed(true);
    if (isValid()) {
      const timetable: TimetableAPI = parseTimeTableToAPI({
        id: id ?? "",
        info,
        semester,
        weeksPerSemester: weeksPerSemester,
        start: startDate,
        end: endDate,
        specialDates,
      });

      if (isUpdate(id)) {
        updateTimetable(timetable).then(goBackToOverview).catch(console.error);
      } else {
        if (importFrom === null) {
          createTimetable(timetable)
            .then(goBackToOverview)
            .catch(console.error);
        } else {
          createTimetable(timetable).then(({ data }) => {
            copySelectedDataTimetable(
              data,
              parseTimeTableToAPI(importFrom),
              copyOptions
            )
              .then(goBackToOverview)
              .catch(console.error);
          });
        }
      }
    }
  };

  const verfiedSave = () => {
    const timetable: TimetableAPI = parseTimeTableToAPI({
      id: id ?? "",
      info,
      semester,
      weeksPerSemester: weeksPerSemester,
      start: startDate,
      end: endDate,
      specialDates,
    });
    if (importFrom !== null) {
      copySelectedDataTimetable(
        timetable,
        parseTimeTableToAPI(importFrom),
        copyOptions
      )
        .then(goBackToOverview)
        .catch(console.error);
      goBackToOverview();
    }
  };

  const onDeleteSubmit = (clickedId: string) => {
    const filtered = specialDates.filter(
      (specialDate) => specialDate.id !== clickedId
    );
    setSpecialDates(filtered);
    setShowSEDeleteDialog(false);
    setClickedSpecialDateId("");
  };

  const onDeleteCancel = () => {
    setShowSEDeleteDialog(false);
    setClickedSpecialDateId("");
  };

  const getSEDeleteInfo = (clickedId: string): string => {
    let text = "";
    const specialDate = specialDates.find(
      (element) => element.id === clickedId
    );
    if (specialDate) {
      if (isFree(specialDate.specialEventType)) {
        text = `das Sonderevent zwischen ${specialDate.from.format(
          dayFormatShort
        )} und ${specialDate.to.format(dayFormatShort)}`;
      } else {
        text = `das Sonderevent an dem ${specialDate.from.format(
          dayFormatShort
        )}`;
      }
    } else {
      text = "undefined";
    }
    return text;
  };

  const actions = (
    <SubmitButtons
      submitText={getSavingButtonText()}
      cancelText="Abbrechen"
      onClickSubmit={onSubmit}
      onClickCancel={onCancel}
    />
  );

  return (
    <PageLayout title={getTitle()} actions={actions}>
      <div className="timetable-detail-view">
        <div className="flex flex-row timetable-detail-view__form-row-widht">
          <TextField
            id="identifier"
            label="Bezeichner"
            className="timetable-detail-view__text-field"
            variant="outlined"
            error={savePushed && !info}
            helperText={
              savePushed && !info ? "Der Bezeichner darf nicht leer sein!" : ""
            }
            value={info}
            onChange={(event) => {
              setInfo(event.target.value);
            }}
          />
        </div>

        <div className="flex flex-row timetable-detail-view__form-row-widht">
          <SelectComponent
            selectData={semesterTypes}
            tooltip="Es wurden noch keine Semestertypen angelegt."
            className="timetable-detail-view__text-field"
            label="Semestertyp"
            value={semester.semesterType.name}
            onChange={handleInputChangeSemesterType}
          ></SelectComponent>
          <TextField
            id="weeksPerSemester"
            label="Vorlesungswochen im Semester"
            className="timetable-detail-view__text-field"
            variant="outlined"
            value={weeksPerSemester}
            onChange={(event) => {
              setWeeksPerSemester(
                checkedParseInt(event.target.value, weeksPerSemester)
              );
            }}
          />
        </div>

        <div className="flex flex-row timetable-detail-view__form-row-widht">
          <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="de">
            <DatePicker
              className="timetable-detail-view__text-field"
              label="Startdatum"
              inputFormat={dayFormat}
              disableMaskedInput
              value={startDate}
              disableHighlightToday
              onChange={(changedValue) => {
                if (changedValue) {
                  setStartDate(changedValue);
                  setSemester({
                    semesterType: semester.semesterType,
                    // Parsing Year instead of using year() to change format
                    year: parseYearToShortFormat(changedValue),
                  });
                }
              }}
              renderInput={(params) => (
                <TextField
                  {...params}
                  error={savePushed && !isStartBeforeEnd()}
                  helperText={
                    savePushed && !isStartBeforeEnd()
                      ? "Das Startdatum muss vor dem Enddatum liegen"
                      : ""
                  }
                />
              )}
            />
            <DatePicker
              className="timetable-detail-view__text-field"
              label="Enddatum"
              inputFormat={dayFormat}
              disableMaskedInput
              value={endDate}
              disableHighlightToday
              onChange={(changedValue) => {
                if (changedValue) {
                  setEndDate(changedValue);
                }
              }}
              renderInput={(params) => (
                <TextField
                  {...params}
                  error={savePushed && !isStartBeforeEnd()}
                  helperText={
                    savePushed && !isStartBeforeEnd()
                      ? "Das Enddatum muss hinter dem Startdatum liegen"
                      : ""
                  }
                />
              )}
            />
          </LocalizationProvider>
        </div>
        <div className="flex flex-row timetable-detail-view__form-row-widht">
          <Autocomplete
            className="timetable-detail-view__text-field"
            autoHighlight
            options={importOptions}
            getOptionLabel={(option: Timetable) => stringOfTimetable(option)}
            value={importFrom}
            isOptionEqualToValue={(option, value) => option.id === value.id}
            onChange={(_, value) => {
              setImportFrom(value);
            }}
            renderInput={(params) => (
              <div>
                <TextField
                  {...params}
                  key={params.id}
                  label="Stammdaten importieren aus"
                  variant="outlined"
                  InputProps={{
                    ...params.InputProps,
                    startAdornment: (
                      <InputAdornment position="start">
                        <IconButton
                          edge="end"
                          aria-label="edit"
                          onClick={() => {
                            setShowImportOptionsDialog(true);
                          }}
                        >
                          <EditIcon />
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                  fullWidth
                />
              </div>
            )}
            renderOption={(props, option) => {
              return (
                <li {...props} key={option.id}>
                  {stringOfTimetable(option)}
                </li>
              );
            }}
          />
        </div>
      </div>

      <Paper elevation={1}>
        <List>
          <ListItem
            secondaryAction={
              <IconButton
                edge="end"
                aria-label="add"
                onClick={() => setShowSpecialDateDialog(true)}
              >
                <AddIcon />
              </IconButton>
            }
          >
            <ListItemText primary="Sonderevents" />
          </ListItem>
          {sortSpecialDates(specialDates).map((specialDate) => (
            <div key={specialDate.id}>
              <ListItem
                sx={{ backgroundColor: "var(--xsoft-grey)" }}
                secondaryAction={
                  <>
                    <IconButton
                      onClick={() => {
                        setShowSpecialDateDialog(true);
                        setClickedSpecialDateId(specialDate.id);
                      }}
                    >
                      <EditIcon />
                    </IconButton>
                    <IconButton
                      onClick={() => {
                        setShowSEDeleteDialog(true);
                        setClickedSpecialDateId(specialDate.id);
                      }}
                    >
                      <Delete />
                    </IconButton>
                  </>
                }
              >
                <ListItemText primary={stringOfSpecialDate(specialDate)} />
              </ListItem>
              <Divider />
            </div>
          ))}
        </List>
      </Paper>
      <SpecialDateDialog
        showDialog={showSpecialDateDialog}
        setShowDialog={setShowSpecialDateDialog}
        clickedSpecialDate={clickedSpecialDate}
        setClickedSpecialDate={setClickedSpecialDateId}
        specialDates={specialDates}
        setSpecialDates={setSpecialDates}
      />

      <DeleteDialog
        showDialog={showSEDeleteDialog}
        dialogTitle="Löschen eines Sonderevents"
        deleteInfo={`Möchten Sie ${getSEDeleteInfo(
          clickedSpecialDate
        )} wirklich löschen?`}
        onClickSubmit={() => onDeleteSubmit(clickedSpecialDate)}
        onClickCancel={() => onDeleteCancel()}
      />

      <ImportWarningDialog
        showDialog={showImportWarningDialog}
        setShowDialog={setShowImportWarningDialog}
        currentTimetableAsString={stringOfSemester(
          semester,
          startDate,
          endDate
        ).concat(` (${info})`)}
        importFrom={importFrom}
        verifiedSave={verfiedSave}
      />

      <ImportOptionsDialog
        showDialog={showImportOptionsDialog}
        setShowDialog={setShowImportOptionsDialog}
        options={copyOptions}
        setOptions={setCopyOptions}
      />
    </PageLayout>
  );
}

export default TimetableDetailView;
