import React, { useEffect, useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  Button,
  Checkbox,
  FormControlLabel,
  DialogActions,
  TextField,
} from "@mui/material";
import { Theme, useTheme } from "@mui/material";
import {
  createCourseType,
  createEmployeeType,
  createRoomType,
  createSchoolType,
  createSemesterType,
  fetchCourseType,
  fetchEmployeeType,
  fetchRoomType,
  fetchSchoolType,
  fetchSemesterType,
  patchCourseType,
  patchEmployeeType,
  patchRoomType,
  patchSchoolType,
  patchSemesterType,
} from "../../../shared/requests/types.request";
import { RoomType } from "../../../models/room";
import { SchoolType } from "../../../models/subject";
import { CourseType } from "../../../models/lecture";
import { SemesterType } from "../../../models/timetable";
import { EmployeeType } from "../../../models/employee";

interface RoomTypeProps {
  showDialog: boolean;
  setShowDialog: React.Dispatch<React.SetStateAction<boolean>>;
  id: string;
  setClickedId: React.Dispatch<React.SetStateAction<string>>;
  whichType: number;
  setExternalModified?: React.Dispatch<React.SetStateAction<boolean>>;
  hasOnlineCheckBox: boolean;
}

const TypesDialog = ({
  showDialog,
  setShowDialog,
  id,
  setClickedId,
  whichType,
  setExternalModified,
  hasOnlineCheckBox,
}: RoomTypeProps) => {
  const theme: Theme = useTheme();

  const [name, setName] = useState<string>("");
  const [online, setOnline] = useState<boolean>(false);
  const [savePushed, setSavePushed] = useState<boolean>(false);

  const getTitle = (): string => {
    const createTitle = "Typ erstellen";
    const editTitle = "Typ bearbeiten";
    if (id) {
      return editTitle;
    }
    return createTitle;
  };

  const getSavingButtonText = (): string => {
    const createText = "Erstellen";
    const editText = "Speichern";
    if (id) {
      return editText;
    }
    return createText;
  };

  const getText = (): string => {
    const roomTypeTitle = "Raumtyp";
    const employeeTypeTitle = "Mitarbeitertyp";
    const schoolTypeTitle = "Schulform";
    const lectureTypeTitle = "Veranstaltungstyp";
    const semesterTypeTitle = "Semestertyp";
    if (whichType === 1) {
      return roomTypeTitle;
    } else if (whichType === 2) {
      return employeeTypeTitle;
    } else if (whichType === 3) {
      return schoolTypeTitle;
    } else if (whichType === 4) {
      return lectureTypeTitle;
    } else if (whichType === 5) {
      return semesterTypeTitle;
    }
    return "";
  };

  useEffect(() => {
    setSavePushed(false);
    if (id) {
      if (whichType === 1) {
        fetchRoomType(id).then(({ data }) => {
          setName(data.name);
          setOnline(data.online);
        });
      } else if (whichType === 2) {
        fetchEmployeeType(id).then(({ data }) => setName(data.name));
      } else if (whichType === 3) {
        fetchSchoolType(id).then(({ data }) => setName(data.name));
      } else if (whichType === 4) {
        fetchCourseType(id).then(({ data }) => setName(data.name));
      } else if (whichType === 5) {
        fetchSemesterType(id).then(({ data }) => setName(data.name));
      }
    } else {
      setName("");
      setOnline(false);
    }
  }, [showDialog]);

  const saveType = () => {
    if (whichType === 1) {
      const roomType: RoomType = {
        id,
        name,
        online,
      };
      if (id) {
        patchRoomType(roomType)
          .then(() => {
            if (setExternalModified) {
              setExternalModified(true);
            }
          })
          .catch(console.error);
      } else {
        createRoomType(roomType)
          .then(() => {
            if (setExternalModified) {
              setExternalModified(true);
            }
          })
          .catch(console.error);
      }
    } else if (whichType === 2) {
      const employeetype: EmployeeType = {
        id,
        name,
      };
      if (id) {
        patchEmployeeType(employeetype)
          .then(() => {
            if (setExternalModified) {
              setExternalModified(true);
            }
          })
          .catch(console.error);
      } else {
        createEmployeeType(employeetype)
          .then(() => {
            if (setExternalModified) {
              setExternalModified(true);
            }
          })
          .catch(console.error);
      }
    } else if (whichType === 3) {
      const schooltype: SchoolType = {
        id,
        name,
      };
      if (id) {
        patchSchoolType(schooltype)
          .then(() => {
            if (setExternalModified) {
              setExternalModified(true);
            }
          })
          .catch(console.error);
      } else {
        createSchoolType(schooltype)
          .then(() => {
            if (setExternalModified) {
              setExternalModified(true);
            }
          })
          .catch(console.error);
      }
    } else if (whichType === 4) {
      const coursetype: CourseType = {
        id,
        name,
      };
      if (id) {
        patchCourseType(coursetype)
          .then(() => {
            if (setExternalModified) {
              setExternalModified(true);
            }
          })
          .catch(console.error);
      } else {
        createCourseType(coursetype)
          .then(() => {
            if (setExternalModified) {
              setExternalModified(true);
            }
          })
          .catch(console.error);
      }
    } else if (whichType === 5) {
      const semestertype: SemesterType = {
        id,
        name,
      };
      if (id) {
        patchSemesterType(semestertype).then(() => {
          if (setExternalModified) {
            setExternalModified(true);
          }
        });
      } else {
        createSemesterType(semestertype).then(() => {
          if (setExternalModified) {
            setExternalModified(true);
          }
        });
      }
    }
  };

  return (
    <Dialog open={showDialog}>
      <DialogTitle
        sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
      >
        {getTitle()}
      </DialogTitle>
      <DialogContent className="DialogContent" dividers>
        <div className="timeslot-dialog-textfields">
          <TextField
            id="textfiled-starthour"
            label={getText()}
            className=""
            variant="outlined"
            value={name}
            onChange={(e) => setName(e.target.value)}
            helperText={
              name === "" && savePushed ? "Der Name darf nicht leer sein!" : ""
            }
            error={name === "" && savePushed}
          />
        </div>
        {hasOnlineCheckBox ? (
          <div>
            <FormControlLabel
              control={
                <Checkbox
                  checked={online}
                  onChange={(e) => setOnline(e.target.checked)}
                  name="isOnline"
                  color="primary"
                />
              }
              label="Online Kurs"
            />
          </div>
        ) : (
          <></>
        )}
      </DialogContent>

      <DialogActions>
        <Button
          className="ButtonYes"
          onClick={() => {
            setSavePushed(true);
            if (name !== "") {
              setShowDialog(false);
              setClickedId("");
              saveType();
            }
          }}
        >
          {getSavingButtonText()}
        </Button>
        <Button
          className="ButtonNo"
          onClick={() => {
            setShowDialog(false);
            setClickedId("");
          }}
        >
          Abbrechen
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default TypesDialog;
