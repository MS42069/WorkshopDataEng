import { useEffect, useState } from "react";
import "./degree-detail.css";
import { TextField } from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import { getBaseDataURLPref } from "../../../shared/url-prefix";
import PageLayout from "../../../shared/components/page-layout";
import { isUpdate } from "./helper";
import {
  SubjectSemesterAPI,
  SubjectSemesterInternal,
} from "../../../models/subjectSemester";
import {
  deleteSubjectSemester,
  fetchSubjectSemesters,
  patchSubjectSemester,
} from "../../../shared/requests/subject-semester";
import {
  SubjectAPIData,
  createSubject,
  fetchSubject,
  patchSubject,
} from "../../../shared/requests/subject";
import SubjectSemesterDetailDialog from "./subject-semester-detail-dialog";
import ListElements from "./detail-crud";
import DeleteDialog from "../../../shared/components/standard-actions/delete-dialog";
import { createSubjectSemester } from "../../../shared/requests/subject-semester";
import { fetchLectures } from "../../../shared/requests/lecture.requests";
import { LectureAPI } from "../../../models/lecture";
import { SchoolType } from "../../../models/subject";
import { fetchSchoolTypes } from "../../../shared/requests/types.request";
import { checkedParseInt } from "../../../shared/parse.util";
import SelectComponent from "../../../shared/components/select-component";

function DegreeDetail() {
  const [schoolTypes, setSchoolTypes] = useState<SchoolType[]>([]);
  const standardSemesterAmount = 7;
  const { id } = useParams();
  const [name, setName] = useState<string>("");
  const [shortName, setShortName] = useState<string>("");
  const [semesterAmount, setSemesterAmount] = useState<number>(
    standardSemesterAmount
  );
  const [schoolType, setSchoolType] = useState<SchoolType | null>(null);
  const [studyRegulation, setStudyRegulation] = useState<string>("");
  const [savePushed, setSavePushed] = useState<boolean>(false);
  const [possibleCourses, setPossibleCourses] = useState<LectureAPI[]>([]);

  const { timetableId } = useParams();
  const navigate = useNavigate();

  /************* Degree *************/
  useEffect(() => {
    fetchSchoolTypes().then(({ data }) => {
      setSchoolTypes(data);
      setSchoolType(data.length ? data[0] : null);
    });
    if (isUpdate(id)) {
      fetchSubject(id!).then(({ data }) => {
        setName(data.name);
        setShortName(data.shortName);
        setSemesterAmount(data.semesterAmount);
        if (typeof data.schoolType === "object") {
          setSchoolType(data.schoolType);
        }
        setStudyRegulation(data.studyRegulation);
      });
      fetchSubjectSemesters().then(({ data }) => {
        setSemesters(parseSemesterFromApi(data));
      });
    }
    fetchLectures().then(({ data }) => setPossibleCourses(data));
  }, []);

  const getTitle = (): string => {
    const createDegreeTitle = "Studiengang erstellen";
    const editDegreeTitle = "Studiengang bearbeiten";
    if (isUpdate(id)) {
      return editDegreeTitle;
    }
    return createDegreeTitle;
  };

  const getSavingButtonText = (): string => {
    const createDegree = "Erstellen";
    const editDegree = "Speichern";
    if (isUpdate(id)) {
      return editDegree;
    }
    return createDegree;
  };

  const getHighestUsedSemesterNumber = (): number => {
    return Math.max(...semesters.map((semester) => semester.semesterNumber));
  };

  const isValidInput = (): boolean => {
    return (
      !!name &&
      !!shortName &&
      semesterAmount > 0 &&
      semesterAmount >= getHighestUsedSemesterNumber() &&
      !!studyRegulation
    );
  };

  const currentAsDegree = (): SubjectAPIData => {
    return {
      name,
      shortName,
      semesterAmount,
      schoolType: schoolType?.id || "",
      studyRegulation,
      semesters: semestersToAPI(),
    };
  };

  /************* Semester *************/
  const [showDetailDialog, setShowDetailDialog] = useState<boolean>(false);
  const [showDeleteDialog, setShowDeleteDialog] = useState<boolean>(false);
  const [semesters, setSemesters] = useState<SubjectSemesterInternal[]>([]);
  const [clickedSemester, setClickedSemester] =
    useState<SubjectSemesterInternal>();
  const [deletedSemesters, setDeletedSemesters] = useState<
    SubjectSemesterInternal[]
  >([]);
  const semestersToAPI = (): string[] => {
    return [];
  };

  const getDeleteSemesterInfo = (
    semester: SubjectSemesterInternal | undefined
  ): string => {
    return `Möchten Sie ${semester?.semesterNumber || ""}. Semester ${name} ${
      semester?.extensionName || ""
    } entfernen?
    Diese Aktion wird erst mit dem Speichern des Studiengangs persistiert!`;
  };

  const parseSemesterFromApi = (
    data: SubjectSemesterAPI[]
  ): SubjectSemesterInternal[] => {
    const subjectSemesters: SubjectSemesterInternal[] = [];
    data.forEach(
      ({ id, attendees, courses, degree, extensionName, semesterNumber }) =>
        subjectSemesters.push({
          id,
          attendees,
          courses: courses.map((course) => course.id),
          degree: degree.id,
          extensionName,
          semesterNumber,
        })
    );
    return subjectSemesters.filter(({ degree }) => degree === id);
  };

  /************* Actions *************/
  useEffect(() => {
    if (!showDeleteDialog) {
      setClickedSemester(undefined);
    }
  }, [showDeleteDialog]);
  const onClickAddElement = () => {
    setShowDetailDialog(true);
  };

  const onClickEditElement = (semester: SubjectSemesterInternal) => {
    setClickedSemester(semester);
    setShowDetailDialog(true);
  };

  const onClickDeleteElement = (semester: SubjectSemesterInternal) => {
    setClickedSemester(semester);
    setShowDeleteDialog(true);
  };

  const onCancelDelete = () => {
    setShowDeleteDialog(false);
  };

  const onSubmitDelete = () => {
    if (clickedSemester) {
      setSemesters(semesters.filter((sem) => sem.id !== clickedSemester.id));
      /* New Semesters are not persisted in backend storage before saving, so they don't need to marked
        for deletion */
      if (!clickedSemester.new) {
        setDeletedSemesters([
          ...semesters.filter((sem) => sem.id === clickedSemester.id),
          ...deletedSemesters,
        ]);
      }
    }
    setShowDeleteDialog(false);
  };

  const handleInputChangeSchoolType = (value: string) => {
    setSchoolType(schoolTypes.find(({ name }) => name === value) || null);
  };

  const goBackToOverview = () =>
    navigate(`${getBaseDataURLPref(timetableId)}/subject`);

  const saveDegree = () => {
    setSavePushed(true);
    if (isValidInput()) {
      // Saving details of degree
      const asDegree = currentAsDegree();
      if (isUpdate(id)) {
        patchSubject({ subject: asDegree, subjectId: id! })
          .then(() => {
            Promise.all(saveSemesters(id!)).then(() => goBackToOverview());
          })
          .catch(console.error);
      } else {
        createSubject(asDegree)
          .then((response) => {
            Promise.all(saveSemesters(response.data.id)).then(() =>
              goBackToOverview()
            );
          })
          .catch(console.error);
      }
    }
  };

  const saveSemesters = (degreeId: string) => {
    const requests = [];
    semesters.forEach((semester) => {
      if (semester.new) {
        semester.degree = degreeId;
        requests.push(createSubjectSemester(semester));
      } else if (semester.edited) {
        semester.degree = degreeId;
        requests.push(
          patchSubjectSemester({
            subjectSemester: semester,
            subjectSemesterId: semester.id,
          })
        );
      }
    });
    requests.push(
      deletedSemesters.forEach((semester) => {
        deleteSubjectSemester(semester.id);
      })
    );
    return requests;
  };

  const actions = (
    <SubmitButtons
      submitText={getSavingButtonText()}
      cancelText="Abbrechen"
      onClickSubmit={saveDegree}
      onClickCancel={goBackToOverview}
      isValid={isValidInput()}
    />
  );

  return (
    <PageLayout title={getTitle()} actions={actions}>
      <div className="degree-detail-view">
        <div className="flex flex-row degree-detail-view__form-row-widht">
          <TextField
            id="identifier"
            label="Bezeichner"
            className="degree-detail-view__text-field"
            variant="outlined"
            helperText={
              savePushed && !name ? "Der Bezeichner darf nicht leer sein!" : ""
            }
            value={name}
            error={savePushed && name === ""}
            onChange={(event) => {
              setName(event.target.value);
              setSavePushed(false);
            }}
          />
          <TextField
            id="shortName"
            label="Kürzel"
            className="degree-detail-view__text-field"
            variant="outlined"
            value={shortName}
            error={savePushed && !shortName}
            helperText={
              savePushed && !shortName ? "Das Kürzel darf nicht leer sein!" : ""
            }
            onChange={(event) => {
              setShortName(event.target.value);
              setSavePushed(false);
            }}
          />
        </div>

        <div className="flex flex-row degree-detail-view__form-row-widht">
          <TextField
            id="SemesterAmount"
            label="Semesterzahl"
            className="degree-detail-view__text-field"
            variant="outlined"
            error={
              savePushed &&
              (semesterAmount <= 0 ||
                semesterAmount < getHighestUsedSemesterNumber())
            }
            helperText={
              savePushed &&
              (semesterAmount <= 0 ||
                semesterAmount < getHighestUsedSemesterNumber())
                ? "Die Anzahl an Semestern muss größer als 0 und dem höchsten Fachsemester sein!"
                : ""
            }
            value={semesterAmount}
            onChange={(event) => {
              setSemesterAmount(
                checkedParseInt(event.target.value, semesterAmount)
              );
              setSavePushed(false);
            }}
          />
          <SelectComponent
            selectData={schoolTypes}
            tooltip="Es wurden noch keine Schulformen angelegt."
            className="degree-detail-view__text-field"
            label="Schulform"
            value={schoolType?.name || ""}
            onChange={handleInputChangeSchoolType}
          ></SelectComponent>
        </div>
        <div className="flex flex-row degree-detail-view__form-row-widht">
          <TextField
            id="studyRegulation"
            label="Studienordnung"
            className="degree-detail-view__text-field"
            variant="outlined"
            error={savePushed && !studyRegulation}
            helperText={
              savePushed && !shortName
                ? "Die Studieordnung darf nicht leer sein!"
                : ""
            }
            value={studyRegulation}
            onChange={(event) => {
              setStudyRegulation(event.target.value);
              setSavePushed(false);
            }}
          />
        </div>
      </div>
      <ListElements
        elements={semesters}
        columns={[
          { key: "semesterNumber", displayName: "Semester" },
          { key: "extensionName", displayName: "Zusatz" },
        ]}
        addEvent={onClickAddElement}
        editEvent={onClickEditElement}
        deleteEvent={onClickDeleteElement}
      />
      <SubjectSemesterDetailDialog
        showDialog={showDetailDialog}
        setShowDialog={setShowDetailDialog}
        degree={id || ""}
        possibleCourses={possibleCourses}
        semesters={semesters}
        setSemesters={setSemesters}
        highestSemester={semesterAmount}
        clickedSemester={clickedSemester}
        setClickedSemester={setClickedSemester}
      />
      <DeleteDialog
        showDialog={showDeleteDialog}
        dialogTitle="Löschen eines Fachsemesters"
        deleteInfo={getDeleteSemesterInfo(clickedSemester)}
        onClickCancel={onCancelDelete}
        onClickSubmit={onSubmitDelete}
      />
    </PageLayout>
  );
}

export default DegreeDetail;
