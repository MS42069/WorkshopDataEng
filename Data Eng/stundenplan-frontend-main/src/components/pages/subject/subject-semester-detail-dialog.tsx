import React, { useEffect, useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Autocomplete,
} from "@mui/material";
import "./subject-detail.css";
import { Theme, useTheme } from "@mui/material";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import { SubjectSemesterInternal } from "../../../models/subjectSemester";
import { LectureAPI } from "../../../models/lecture";
import { checkedParseInt } from "../../../shared/parse.util";

interface DialogProps {
  showDialog: boolean;
  setShowDialog: React.Dispatch<React.SetStateAction<boolean>>;
  degree: string;
  possibleCourses: LectureAPI[];
  highestSemester: number;
  semesters: SubjectSemesterInternal[];
  setSemesters: React.Dispatch<React.SetStateAction<SubjectSemesterInternal[]>>;
  clickedSemester: SubjectSemesterInternal | undefined;
  setClickedSemester: React.Dispatch<
    React.SetStateAction<SubjectSemesterInternal | undefined>
  >;
}

const SubjectSemesterDetailDialog = ({
  showDialog,
  setShowDialog,
  degree,
  possibleCourses,
  highestSemester,
  semesters,
  setSemesters,
  clickedSemester,
  setClickedSemester,
}: DialogProps) => {
  const [extensionName, setExtensionName] = useState<string>("");
  const [semesterNumber, setSemesterNumber] = useState<number>(1);
  const [attendees, setAttendees] = useState<number>(0);
  const [selectedCourses, setSelectedCourses] = useState<LectureAPI[]>([]);

  const [isUniqueCombination, setIsUniqeCombination] = useState<boolean>(true);
  const [savePushed, setSavePushed] = useState<boolean>(false);
  const theme: Theme = useTheme();
  const notUniqueErrorText =
    "Kombination aus Semester und Zusatz existiert bereits";

  const shortenCourses = (courses: LectureAPI[]): string[] => {
    return courses.map((course) => course.id);
  };

  const getSemesterNumberErrorText = (): string => {
    let errorText = "";
    if (savePushed) {
      if (!isUniqueCombination) {
        errorText = notUniqueErrorText;
      } else if (!semesterNumberInRange()) {
        errorText = `Das Semester muss zwischen 0 und der angegebenen Semesterzahl liegen!`;
      }
    }
    return errorText;
  };

  // Checks if combination of semesternumber, extensionName and degree is unique
  const combinationUnique = (): boolean => {
    const filtered = semesters.filter(
      (semester) => semester.id !== clickedSemester?.id || ""
    );
    return !filtered.some(
      (semester) =>
        semester.extensionName.toLowerCase() === extensionName.toLowerCase() &&
        semester.semesterNumber === semesterNumber &&
        semester.degree === degree
    );
  };

  const semesterNumberInRange = (): boolean => {
    return semesterNumber > 0 && semesterNumber <= highestSemester;
  };

  const onClickSubmit = () => {
    setSavePushed(true);
    const isUnique = combinationUnique();
    setIsUniqeCombination(isUnique);
    if (isUnique && semesterNumberInRange()) {
      const isInDatabank = !!clickedSemester && !clickedSemester?.new;
      /* creating local id CRUD-handling purposes; combination of semesterNumber,
         degree and extensionName is uniqe */
      const localId = `${semesterNumber} ${degree} ${extensionName}`;
      // check if update
      if (clickedSemester) {
        if (!isInDatabank) {
          clickedSemester.id = localId;
        }
        clickedSemester.attendees = attendees;
        clickedSemester.semesterNumber = semesterNumber;
        clickedSemester.extensionName = extensionName;
        clickedSemester.courses = shortenCourses(selectedCourses);
        clickedSemester.edited = true;

        setSemesters([
          ...semesters.filter((semester) => semester.id !== clickedSemester.id),
          clickedSemester,
        ]);
      } else {
        setSemesters([
          ...semesters,
          {
            id: localId,
            attendees,
            semesterNumber,
            extensionName,
            degree,
            courses: shortenCourses(selectedCourses),
            new: true,
          },
        ]);
      }
      setShowDialog(false);
    }
  };

  useEffect(() => {
    if (showDialog) {
      if (clickedSemester) {
        setExtensionName(clickedSemester.extensionName);
        setSemesterNumber(clickedSemester.semesterNumber);
        setAttendees(clickedSemester.attendees);
        setSelectedCourses(
          possibleCourses.filter((course) =>
            clickedSemester.courses.some(
              (semesterCourse) => semesterCourse === course.id
            )
          )
        );
      } else {
        setSemesterNumber(
          semesters.length
            ? Math.max(...semesters.map((semester) => semester.semesterNumber))
            : 1
        );
      }
    } else {
      resetDialog();
    }
  }, [showDialog]);

  const resetDialog = () => {
    setClickedSemester(undefined);
    setIsUniqeCombination(true);
    setSavePushed(false);
    setSelectedCourses([]);
    setExtensionName("");
    setSemesterNumber(1);
    setAttendees(0);
  };

  return (
    <Dialog open={showDialog}>
      <DialogTitle
        sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
      >
        Erstellen eines Fachsemesters
      </DialogTitle>
      <DialogContent className="DialogContent">
        <div className="single-view-subject">
          <div className="flex flex-row single-view-subject__form-row-widht">
            <TextField
              className="single-view-subject__text-field"
              label="Semester"
              variant="outlined"
              error={
                savePushed && (!isUniqueCombination || !semesterNumberInRange())
              }
              helperText={getSemesterNumberErrorText()}
              value={semesterNumber}
              onChange={(event) =>
                setSemesterNumber(
                  checkedParseInt(event.target.value, semesterNumber)
                )
              }
            />
            <TextField
              className="single-view-subject__text-field"
              label="Teilnehmeranzahl"
              variant="outlined"
              sx={isUniqueCombination ? { marginTop: "1rem" } : {}}
              onChange={(event) =>
                setAttendees(checkedParseInt(event.target.value, attendees))
              }
              value={attendees}
            />
          </div>
          <div className="flex flex-row single-view-subject__form-row-widht">
            <TextField
              className="single-view-subject__text-field"
              label="Zusatz"
              variant="outlined"
              error={savePushed && !isUniqueCombination}
              helperText={
                savePushed && !isUniqueCombination ? notUniqueErrorText : ""
              }
              size="medium"
              value={extensionName}
              onChange={(newValue) => setExtensionName(newValue.target.value)}
            />
          </div>
          <div className="flex flex-row single-view-subject__form-row-widht">
            <Autocomplete
              id="multiple-limit-tags"
              className="single-view-subject__text-field"
              multiple
              autoHighlight
              disableCloseOnSelect
              options={possibleCourses}
              getOptionLabel={(option: LectureAPI) => option.name}
              value={selectedCourses}
              isOptionEqualToValue={(option, value) => option.id === value.id}
              onChange={(_, value) => {
                setSelectedCourses([...value]);
              }}
              renderInput={(params) => (
                <TextField {...params} label="Veranstaltungen" />
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
      </DialogContent>
      <DialogActions>
        <SubmitButtons
          submitText="Speichern"
          cancelText="Abbrechen"
          onClickSubmit={onClickSubmit}
          onClickCancel={() => {
            setShowDialog(false);
          }}
        />
      </DialogActions>
    </Dialog>
  );
};

export default SubjectSemesterDetailDialog;
