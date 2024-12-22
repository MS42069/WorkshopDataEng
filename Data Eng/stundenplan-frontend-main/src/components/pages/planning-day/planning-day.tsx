import { useEffect, useState } from "react";
import PageLayout from "../../../shared/components/page-layout";
import { LectureForPlanAPI } from "../../../models/lecture";
import "./planning-day.css";
import TuneIcon from "@mui/icons-material/Tune";
import { FixedSizeList } from "react-window";
import ClearIcon from "@mui/icons-material/Clear";

import {
  Button,
  Divider,
  IconButton,
  ListItem,
  ListItemButton,
  ListItemText,
  Paper,
  TextField,
} from "@mui/material";
import { RoomShort } from "../../../models/room";
import { TimeslotAPI } from "../../../models/timeslots";
import { getLecturesForPlan } from "../../../shared/requests/lecture.requests";
import {
  WeekdayDescription,
  WeekdayValue,
  getAllWeekdays,
} from "../../../enums/weekday.enum";
import {
  deleteWeekEvent,
  getWeekEventOptions,
  getWeekEvents,
  scheduleWeekEvent,
} from "../../../shared/requests/week-event.requests";
import {
  ScheduleWeekEvent,
  WeekEvent,
  WeekEventOption,
} from "../../../models/week-event";
import { useFilter } from "../../../hooks/useFilter.hook";
import { resourceAxiosInstance } from "../../../config/axios.config";
import { saveAs } from "file-saver";
import { unplannedListFormat } from "../../../shared/formatter/lecture.formatter";
import { getPlannedWeekEvents } from "./helper";
import { ConflictError } from "../../../models/conflict-error";
import { AxiosError } from "axios";
import FilterDialog from "./filter-dialog";
import { LecturesForPlanFilter } from "../../../models/lectures-for-plan-filter";
import { PlanningWeeks } from "./planning-weeks";
import { useConflictDialog } from "./conflict-dialog";

function PlanningTableWeek() {
  const { setConflict, ConflictDialog } = useConflictDialog();
  const { filter, filteredValues, setInitialValues } =
    useFilter<LectureForPlanAPI>([]);

  const [filterOptions, setFilterOptions] = useState<LecturesForPlanFilter>();

  const [showFilterDialog, setShowFilterDialog] = useState<boolean>(false);
  const [selectedLectureId, setSelectedLectureId] = useState<string>("");
  const [weekEventOption, setWeekEventOption] = useState<WeekEventOption>();
  const [plannedLectures, setPlannedLectures] = useState<WeekEvent[]>([]);
  const [currentWeekday, setCurrentWeekday] = useState<WeekdayDescription>(
    getAllWeekdays()[0]
  );

  useEffect(() => {
    getWeekEvents().then((response) =>
      setPlannedLectures(response.data.sort((a, b) => a.week - b.week))
    );
  }, []);

  useEffect(() => {
    getLecturesForPlan(filterOptions).then((response) => {
      setInitialValues(response.data);
    });
  }, [filterOptions]);

  const onClickLecture = (lectureId: string) => {
    if (lectureId === selectedLectureId) {
      setSelectedLectureId("");
      setWeekEventOption(undefined);
    } else {
      setSelectedLectureId(lectureId);
      refreshWeekEventOptions(lectureId, currentWeekday.value);
    }
  };

  const refreshWeekEventOptions = (
    lectureId: string,
    weekday: WeekdayValue
  ) => {
    if (lectureId) {
      getWeekEventOptions([lectureId], weekday).then((data) => {
        const weekEventOption = data.find(({ course }) => course === lectureId);
        setWeekEventOption(weekEventOption);
      });
    }
  };

  const updateCurrentWeekday = (weekday: WeekdayDescription) => {
    setCurrentWeekday(weekday);
    refreshWeekEventOptions(selectedLectureId, weekday.value);
  };

  const sendSchedule = async (weekEvent: ScheduleWeekEvent, force = false) => {
    const { data } = await scheduleWeekEvent(weekEvent, force);
    setPlannedLectures(
      [...plannedLectures, ...data.events].sort((a, b) => a.week - b.week)
    );
    getLecturesForPlan(filterOptions).then((lecturesForPlan) => {
      setInitialValues(lecturesForPlan.data);
      const existsSelectedLectureForPlan = lecturesForPlan.data.some(
        (lecturesForPlan_1) => lecturesForPlan_1.id === selectedLectureId
      );
      if (!existsSelectedLectureForPlan) {
        setSelectedLectureId("");
        setWeekEventOption(undefined);
      } else {
        refreshWeekEventOptions(selectedLectureId, currentWeekday.value);
      }
    });
  };

  const addEntryToCell = (
    room: RoomShort,
    timeslot: TimeslotAPI,
    weekday: WeekdayDescription,
    week: number,
    optionIdx: number
  ) => {
    if (weekEventOption) {
      const optionExists = optionIdx >= 0;

      let weekEvent: ScheduleWeekEvent;
      if (optionExists) {
        weekEvent = {
          courseId: weekEventOption.course,
          weekday: weekday.value,
          week: week || null,
          blockOfTimeslots: weekEventOption.options[optionIdx].timeslots,
          takesPlaceInRooms: weekEventOption.options[optionIdx].rooms,
        };
      } else {
        weekEvent = {
          courseId: weekEventOption.course,
          weekday: weekday.value,
          week: week || null,
          blockOfTimeslots: [timeslot.id],
          takesPlaceInRooms: [room.id],
        };
      }

      sendSchedule(weekEvent).catch((error: AxiosError<ConflictError>) => {
        setConflict({
          error: error.response?.data,
          value: weekEvent,
        });
      });
    }
  };

  const removeWeekEventsFromPlan = (weekEvents: WeekEvent[]) => {
    Promise.all(weekEvents.map((event) => deleteWeekEvent(event.id))).then(
      () => {
        const weekEventIds = weekEvents.map((event) => event.id);
        setPlannedLectures(
          plannedLectures.filter((event) => !weekEventIds.includes(event.id))
        );

        refreshWeekEventOptions(selectedLectureId, currentWeekday.value);

        getLecturesForPlan(filterOptions).then((response) => {
          setInitialValues(response.data);
        });
      }
    );
  };

  const openFilterDialog = () => {
    setShowFilterDialog(true);
  };

  const closeFilterDialog = () => {
    setShowFilterDialog(false);
  };

  const onClickTable = (
    room: RoomShort,
    timeslot: TimeslotAPI,
    weekday: WeekdayDescription,
    week: number,
    optionIdx: number
  ) => {
    const weekEventsInCell = getPlannedWeekEvents(
      room,
      timeslot,
      weekday,
      week,
      plannedLectures
    );

    if (weekEventsInCell.length) {
      // Only delete all events in a cell, if they belong to the same lecture
      // It's currently not possible to detect which lecture was clicked, if there are multiple different entries
      if (
        weekEventsInCell.every(
          (event) => weekEventsInCell[0].course.id === event.course.id
        )
      ) {
        removeWeekEventsFromPlan(weekEventsInCell);
      }
    } else {
      addEntryToCell(room, timeslot, weekday, week, optionIdx);
    }
  };

  const onClickDownload = () => {
    resourceAxiosInstance.get<string>("/cas/export").then((response) => {
      const blob = new Blob([response.data], { type: "text/xml" });
      const filename = "CAS-Export.xml";

      saveAs(blob, filename);
    });
  };

  const actions = (
    <Button variant="contained" onClick={onClickDownload}>
      CAS-Export
    </Button>
  );

  return (
    <PageLayout title="Vorlesungsplan Tage" actions={actions}>
      <div className="full-size flex-row planning-day__container--margin">
        <div className="planning-day__list--fixed-size planning-day__list--margin">
          <div className="planning-day__list-header-wrapper">
            Ungeplante Vorlesungen{" "}
            <div className="planning-day__btn-wrapper">
              <IconButton size="small" onClick={openFilterDialog}>
                <TuneIcon />
              </IconButton>
              <IconButton
                size="small"
                onClick={() => setFilterOptions(undefined)}
                disabled={filterOptions === undefined}
              >
                <ClearIcon />
              </IconButton>
            </div>
          </div>

          <TextField
            variant="outlined"
            placeholder="Suche…"
            focused
            fullWidth
            margin="normal"
            size="small"
            onChange={(event) =>
              filter(
                ["name", "courseType", "degreeOfFreedom"],
                event.target.value
              )
            }
          />
          <Paper
            sx={{
              width: "100%",
            }}
          >
            <FixedSizeList
              itemData={filteredValues}
              itemCount={filteredValues.length}
              height={700}
              width="100%"
              itemSize={70}
              overscanCount={5}
            >
              {({ data, index, style }) => (
                <ListItem
                  style={style}
                  key={data[index].id}
                  secondaryAction={
                    <p
                      className={
                        selectedLectureId === data[index].id
                          ? "planning-day__list-item-secondary--selected"
                          : ""
                      }
                    >
                      {data[index].degreeOfFreedom}
                    </p>
                  }
                  component="div"
                  disablePadding
                >
                  <ListItemButton
                    onClick={() => onClickLecture(data[index].id)}
                    selected={selectedLectureId === data[index].id}
                  >
                    <ListItemText primary={unplannedListFormat(data[index])} />
                  </ListItemButton>
                </ListItem>
              )}
            </FixedSizeList>
          </Paper>
        </div>

        <Divider sx={{ marginRight: 2 }} orientation="vertical" />

        <PlanningWeeks
          currentWeekday={currentWeekday}
          updateCurrentWeekday={updateCurrentWeekday}
          plannedLectures={plannedLectures}
          onClickTable={onClickTable}
          weekEventOptions={weekEventOption?.options}
        />
      </div>

      <ConflictDialog onConfirm={(value) => sendSchedule(value, true)} />

      <FilterDialog
        filter={filterOptions}
        applyFilter={setFilterOptions}
        showDialog={showFilterDialog}
        closeDialog={closeFilterDialog}
      />
    </PageLayout>
  );
}

export default PlanningTableWeek;
