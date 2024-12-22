import { useEffect, useState } from "react";
import { Delete, Edit as EditIcon } from "@mui/icons-material";
import "./lecture-detail.css";
import {
  Autocomplete,
  List,
  ListItem,
  ListItemText,
  TextField,
  Divider,
  Paper,
  IconButton,
} from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import AddIcon from "@mui/icons-material/Add";
import { Employee, EmployeeAPI } from "../../../models/employee";
import {
  AvailabilityRequest,
  LectureBase,
  LectureAPI,
  RoomCombination,
  sortCombinations,
  roomCombinationAsString,
  LectureAPICreate,
  CourseType,
} from "../../../models/lecture";
import RoomCombinationDialog from "./roomcombination-dialog";
import {
  createLecture,
  fetchLecture,
  fetchLectures,
  updateLecture,
} from "../../../shared/requests/lecture.requests";
import { RoomAPI } from "../../../models/room";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import { isUpdate } from "./helper";
import { fetchRooms } from "../../../shared/requests/room.requests";
import { getBaseDataURLPref } from "../../../shared/url-prefix";
import { fetchTimetable } from "../../../shared/requests/timetable.requests";
import AvailabilityTable, {
  AvailabilityAPI,
} from "../../../shared/components/availability-table/availability-table";
import PageLayout from "../../../shared/components/page-layout";
import DeleteDialog from "../../../shared/components/standard-actions/delete-dialog";
import { checkedParseInt } from "../../../shared/parse.util";
import { fetchCourseTypes } from "../../../shared/requests/types.request";
import { fetchEmployees } from "../../../shared/requests/employer.requests";
import SelectComponent from "../../../shared/components/select-component";

function LectureDetailView() {
  const { id } = useParams();

  const [name, setName] = useState<string>("");
  const [lecturers, setLecturers] = useState<Employee[]>([]);
  const [courseTypes, setCourseTypes] = useState<CourseType[]>([]);
  const [courseType, setCourseType] = useState<CourseType | null>(null);
  const [casID, setCasId] = useState<string>("");
  const [unitsPerWeek, setUnitsPerWeek] = useState<number>(1);
  const [unitsInBlock, setUnitsInBlock] = useState<number>(1);
  const [maxWPS, setMaxWPS] = useState<number>(0);
  const [weeksPerSemester, setWeeksPerSemester] = useState<number>(0);
  const [suitedRooms, setSuitedRooms] = useState<RoomCombination[]>([]);
  const [mustBeHeldBefore, setMustBeHeldBefore] = useState<LectureBase[]>([]);
  const [mustBeHeldAfter, setMustBeHeldAfter] = useState<LectureBase[]>([]);
  const [mayBeParralelTo, setMayBeParralelTo] = useState<LectureBase[]>([]);
  const [availability, setAvailability] = useState<AvailabilityAPI[]>([]);
  const [clickedRoomComb, setClickedRoomComb] = useState<RoomCombination>();
  const [showRoomCombDialog, setShowRoomCombDialog] = useState<boolean>(false);
  const [showDeleteDialog, setShowDeleteDialog] = useState<boolean>(false);
  const [savePushed, setSavePushed] = useState<boolean>(false);
  const [rooms, setRooms] = useState<RoomAPI[]>([]);
  const [lectures, setLectures] = useState<LectureAPI[]>();
  const [employees, setEmployees] = useState<Employee[]>([]);

  // All possible lectures, that could be used for relations
  const [lecturesForConnecting, setLecturesForConnecting] = useState<
    LectureBase[]
  >([]);

  const { timetableId } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    fetchCourseTypes().then(({ data }) => {
      setCourseTypes(data);
      setCourseType(data.length ? data[0] : null);
    });
    fetchTimetable(timetableId).then((response) => {
      setMaxWPS(response.weeksPerSemester);
      setWeeksPerSemester(response.weeksPerSemester);
    });
    if (isUpdate(id)) {
      fetchLecture(id).then(({ data }) => {
        setName(data.name);
        setLecturers(
          data.lecturers.map(({ id, abbreviation, firstname, lastname }) => ({
            id,
            abbreviation,
            firstname,
            surname: lastname,
          }))
        );
        setCasId(data.casID);
        if (typeof data.courseType === "object") {
          setCourseType(data.courseType);
        }
        setUnitsPerWeek(data.slotsPerWeek);
        setUnitsInBlock(data.blockSize);
        setWeeksPerSemester(data.weeksPerSemester);
        setSuitedRooms(data.suitedRooms);
        setMustBeHeldBefore(
          data.courseRelations.mustBeHeldBefore.map(
            ({ id, name, casID, courseType }) => ({
              id,
              name,
              casID,
              courseType,
            })
          )
        );
        setMustBeHeldAfter(
          data.courseRelations.mustBeHeldAfter.map(
            ({ id, name, casID, courseType }) => ({
              id,
              name,
              casID,
              courseType,
            })
          )
        );
        setMayBeParralelTo(
          data.courseRelations.mayBeParallelTo.map(
            ({ id, name, casID, courseType }) => ({
              id,
              name,
              casID,
              courseType,
            })
          )
        );
        setAvailability(data.courseTimeslots);
      });
    }
    fetchRooms().then((response) => setRooms(response.data));
    fetchLectures().then((response) => {
      setLectures(response.data);
      setLecturesForConnecting(
        response.data
          .filter((lecture) => lecture.id !== id)
          .map(({ id, name, casID, courseType }) => ({
            id,
            name,
            casID,
            courseType,
          }))
      );
    });
    fetchEmployees().then(({ data }) => {
      setEmployees(
        data.map(({ id, firstname, abbreviation, lastname }) => ({
          id,
          abbreviation,
          firstname,
          surname: lastname,
        }))
      );
    });
  }, []);

  const getOptionDisplayEmployees = ({
    abbreviation,
    firstname,
    surname,
  }: Employee): string => {
    return `${abbreviation} ${firstname} ${surname}`;
  };

  const getTitle = (): string => {
    const createLectureTitle = "Veranstaltung erstellen";
    const editLectureTitle = "Veranstaltung bearbeiten";
    if (isUpdate(id)) {
      return editLectureTitle;
    }
    return createLectureTitle;
  };

  const getSavingButtonText = (): string => {
    const createLecture = "Erstellen";
    const editLecture = "Speichern";
    if (isUpdate(id)) {
      return editLecture;
    }
    return createLecture;
  };

  const handleInputChangeCourseType = (value: string) => {
    setCourseType(courseTypes.find(({ name }) => name === value) || null);
  };

  const isValidInput = (): boolean => {
    return name !== "" && isCasIdValid() && lecturers.length > 0;
  };

  const isCasIdValid = (): boolean => {
    const isEmpty: boolean = casID === "";
    // if there are other lectures, ID needs to be checked for uniqueness
    if (lectures && lectures.length > 0) {
      const filtered = lectures.filter((e) => e.id !== id);
      return !isEmpty && !filtered.some((lecture) => lecture.casID === casID);
    }
    return !isEmpty;
  };

  const shortenAvailability = (): AvailabilityRequest[] => {
    const shortened: AvailabilityRequest[] = [];

    availability.forEach(({ weekday, timeslot }) => {
      shortened.push({
        weekday: weekday,
        timeslotID: timeslot.id,
      });
    });

    return shortened;
  };

  const saveLecture = () => {
    setSavePushed(true);

    if (isValidInput()) {
      const asLecture = currentAsLecture();
      if (isUpdate(id)) {
        updateLecture(asLecture, id!)
          .then(goBackToOverview)
          .catch(console.error);
      } else {
        createLecture(asLecture).then(goBackToOverview).catch(console.error);
      }
    }
  };

  const currentAsLecture = (): LectureAPICreate => {
    return {
      casID,
      name,
      abbreviation: "",
      description: "",
      blockSize: unitsInBlock,
      weeksPerSemester,
      slotsPerWeek: unitsPerWeek,
      courseType: courseType?.id || "",
      suitedRooms: suitedRooms.map((suitedRoom) => ({
        rooms: suitedRoom.rooms.map(({ id, name, abbreviation }) => ({
          id,
          name,
          abbreviation,
        })),
      })),
      lecturers: lecturers.map((lecturer) => ({ id: lecturer.id })),
      courseRelations: {
        mayBeParallelTo: mayBeParralelTo.map((lecture) => ({
          id: lecture.id,
        })),
        mustBeHeldBefore: mustBeHeldBefore.map((lecture) => ({
          id: lecture.id,
        })),
        mustBeHeldAfter: mustBeHeldAfter.map((lecture) => ({
          id: lecture.id,
        })),
      },
      courseTimeslots: shortenAvailability(),
    };
  };

  const onDeleteSubmit = (clicked: RoomCombination | undefined) => {
    setSuitedRooms(
      suitedRooms.filter((roomCombination) => roomCombination !== clicked)
    );
    setShowDeleteDialog(false);
    setClickedRoomComb(undefined);
  };

  const onDeleteCancel = () => {
    setShowDeleteDialog(false);
    setClickedRoomComb(undefined);
  };

  const goBackToOverview = () =>
    navigate(`${getBaseDataURLPref(timetableId)}/lecture`);

  const actions = (
    <SubmitButtons
      submitText={getSavingButtonText()}
      cancelText="Abbrechen"
      onClickSubmit={saveLecture}
      onClickCancel={goBackToOverview}
      isValid={isValidInput()}
    />
  );

  return (
    <PageLayout title={getTitle()} actions={actions}>
      <div className="lecture-detail-view">
        <div className="flex flex-row lecture-detail-view__form-row-widht">
          <TextField
            id="identifier"
            label="Bezeichner"
            className="lecture-detail-view__text-field"
            variant="outlined"
            helperText={
              savePushed && name === ""
                ? "Der Bezeichner darf nicht leer sein!"
                : ""
            }
            value={name}
            error={savePushed && name === ""}
            onChange={(event) => {
              setName(event.target.value);
            }}
          />
          <TextField
            id="abbreviation"
            label="CAS-ID"
            className="lecture-detail-view__text-field"
            variant="outlined"
            helperText={
              savePushed && !isCasIdValid()
                ? "Die CAS-ID muss einzigartig und darf nicht leer sein!"
                : ""
            }
            value={casID}
            error={savePushed && !isCasIdValid()}
            onChange={(event) => {
              setCasId(event.target.value);
            }}
          />
        </div>

        <div className="flex flex-row lecture-detail-view__form-row-widht">
          <SelectComponent
            selectData={courseTypes}
            tooltip="Es wurden noch keine Veranstaltungstypen angelegt."
            className="lecture-detail-view__text-field"
            label="Veranstaltungstyp"
            value={courseType?.name || ""}
            onChange={handleInputChangeCourseType}
          ></SelectComponent>

          <TextField
            id="weeksPerSemester"
            label="Semesterwochen"
            className="lecture-detail-view__text-field"
            error={savePushed && weeksPerSemester > maxWPS}
            helperText={
              savePushed && weeksPerSemester > maxWPS
                ? `Die Semesterwochen können nicht höher sein als die maximale Anzahl von Wochen eines Semesters. Hier: ${maxWPS}`
                : ""
            }
            variant="outlined"
            value={weeksPerSemester}
            onChange={(event) => {
              setWeeksPerSemester(
                checkedParseInt(event.target.value, weeksPerSemester)
              );
            }}
          />
        </div>

        <div className="flex flex-row lecture-detail-view__form-row-widht">
          <TextField
            id="unitsPerWeek"
            label="Stunden pro Woche"
            className="lecture-detail-view__text-field"
            variant="outlined"
            value={unitsPerWeek}
            onChange={(event) =>
              setUnitsPerWeek(checkedParseInt(event.target.value, unitsPerWeek))
            }
          />
          <TextField
            id="unitsInBlock"
            label="Stunden am Stück"
            className="lecture-detail-view__text-field"
            variant="outlined"
            value={unitsInBlock}
            onChange={(event) =>
              setUnitsInBlock(checkedParseInt(event.target.value, unitsInBlock))
            }
          />
        </div>

        <div className="flex flex-row lecture-detail-view__form-row-widht">
          <Autocomplete
            className="lecture-detail-view__text-field"
            multiple
            autoHighlight
            limitTags={5}
            id="multiple-limit-tags"
            options={employees}
            getOptionLabel={(option) => getOptionDisplayEmployees(option)}
            value={lecturers}
            isOptionEqualToValue={(option, value) => option.id === value.id}
            onChange={(_, value) => {
              setLecturers(value);
            }}
            renderInput={(params) => (
              <TextField
                {...params}
                label="Dozierende"
                helperText={
                  savePushed && lecturers.length <= 0
                    ? "Es muss eine dozierende Person geben"
                    : ""
                }
                error={savePushed && lecturers.length <= 0}
              />
            )}
            renderOption={(props, option) => {
              return (
                <li {...props} key={option.id}>
                  {getOptionDisplayEmployees(option)}
                </li>
              );
            }}
          />
        </div>
        <div className="flex flex-row lecture-detail-view__form-row-widht">
          <Autocomplete
            className="lecture-detail-view__text-field"
            id="multiple-limit-tags"
            multiple
            autoHighlight
            limitTags={5}
            options={lecturesForConnecting}
            getOptionLabel={(option: LectureBase) => option.name}
            value={mustBeHeldBefore}
            isOptionEqualToValue={(option, value) => option.id === value.id}
            onChange={(_, value) => setMustBeHeldBefore([...value])}
            renderInput={(params) => (
              <TextField
                {...params}
                label={`${name} muss vor folgenden Kursen stattfinden`}
              />
            )}
            renderOption={(props, option) => {
              return (
                <li {...props} key={option.id}>
                  {option.name}
                </li>
              );
            }}
          />
          <Autocomplete
            className="lecture-detail-view__text-field"
            multiple
            autoHighlight
            limitTags={5}
            options={lecturesForConnecting}
            getOptionLabel={(option: LectureBase) => option.name}
            value={mustBeHeldAfter}
            isOptionEqualToValue={(option, value) => option.id === value.id}
            onChange={(_, value) => setMustBeHeldAfter([...value])}
            renderInput={(params) => (
              <TextField
                {...params}
                label={`${name} muss nach folgenden Kursen stattfinden`}
              />
            )}
            renderOption={(props, option) => {
              return (
                <li {...props} key={option.id}>
                  {option.name}
                </li>
              );
            }}
          />
        </div>
        <div className="flex flex-row lecture-detail-view__form-row-widht">
          <Autocomplete
            className="lecture-detail-view__text-field"
            multiple
            autoHighlight
            limitTags={5}
            options={lecturesForConnecting}
            getOptionLabel={(option: LectureBase) => option.name}
            value={mayBeParralelTo}
            isOptionEqualToValue={(option, value) => option.id === value.id}
            onChange={(_, value) => setMayBeParralelTo([...value])}
            renderInput={(params) => (
              <TextField {...params} label="Darf parallel liegen zu" />
            )}
            renderOption={(props, option) => {
              return (
                <li {...props} key={option.id}>
                  {option.name}
                </li>
              );
            }}
          />
        </div>
      </div>
      <div>
        <Paper elevation={1}>
          <List>
            <ListItem
              secondaryAction={
                <IconButton
                  edge="end"
                  aria-label="add"
                  onClick={() => setShowRoomCombDialog(true)}
                >
                  <AddIcon />
                </IconButton>
              }
            >
              <ListItemText primary="Raumkombinationen" />
            </ListItem>
            {sortCombinations(suitedRooms).map((roomCombination, index) => (
              <div key={index}>
                <ListItem
                  sx={{ backgroundColor: "var(--xsoft-grey)" }}
                  secondaryAction={
                    <>
                      <IconButton
                        onClick={() => {
                          setShowRoomCombDialog(true);
                          setClickedRoomComb(roomCombination);
                        }}
                      >
                        <EditIcon />
                      </IconButton>
                      <IconButton
                        onClick={() => {
                          setShowDeleteDialog(true);
                          setClickedRoomComb(roomCombination);
                        }}
                      >
                        <Delete />
                      </IconButton>
                    </>
                  }
                >
                  <ListItemText
                    primary={roomCombinationAsString(roomCombination)}
                  />
                </ListItem>
                <Divider />
              </div>
            ))}
          </List>
        </Paper>
      </div>
      <AvailabilityTable
        availability={availability}
        setAvailability={setAvailability}
      />

      <DeleteDialog
        showDialog={showDeleteDialog}
        dialogTitle="Löschen einer Raumkombination"
        deleteInfo={`Möchten Sie ${roomCombinationAsString(
          clickedRoomComb
        )} wirklich löschen?`}
        onClickCancel={onDeleteCancel}
        onClickSubmit={() => onDeleteSubmit(clickedRoomComb)}
      />

      <RoomCombinationDialog
        showDialog={showRoomCombDialog}
        setShowDialog={setShowRoomCombDialog}
        options={rooms}
        clickedRoomComb={clickedRoomComb}
        setClickedRoomComb={setClickedRoomComb}
        suitedRooms={suitedRooms}
        setSuitedRooms={setSuitedRooms}
      />
    </PageLayout>
  );
}

export default LectureDetailView;
