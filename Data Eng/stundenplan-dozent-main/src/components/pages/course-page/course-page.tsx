import { Box, Button, FormControlLabel, Switch } from "@mui/material";
import {
  courseSelector,
  getDivider,
  courseList,
  getSettingComponent,
  getSpacer,
} from "../../shared/shared";
import PageLayout from "../../elements/page-layout";
import { ReactJSXElement } from "@emotion/react/types/jsx-namespace";
import { useEffect, useState } from "react";
import { currentlyUnlocked } from "../../shared/context-provider";
import {
  AppAvailabilityDTO,
  getAppAvailability,
} from "../../../models/app-availability";
import {
  CoursePreferenceProfile,
  saveCourseConstraintProfile,
} from "../../../models/course-profile";
import { LectureBase } from "../../../models/lecture";
import { RoomShort } from "../../../models/room";

function CourseProfilePage() {
  const [courseProfile, setCourseProfile] = useState<CoursePreferenceProfile>(); //TODO: Somehow fill this through a request,
  // then build new object through selected values!
  const [availableCourseList, setAvailableCourseList] = useState<LectureBase[]>(
    []
  ); //TODO: Somehow fill this through a request
  const [availableRoomList, setAvailableRoomList] = useState<RoomShort[]>([]); //TODO: Somehow fill this through a request

  const [selectedCourseName, setSelectedCourse] = useState<string>("");
  const [selectedRoomName, setSelectedRoom] = useState<string>("no");
  const [alternatingSecondRoomName, setAlternatingSecondRoom] =
    useState<string>("no");

  const [locks, setLocks] = useState<AppAvailabilityDTO | null>();

  useEffect(() => {
    getAppAvailability().then((value) => {
      if (value) {
        setLocks(value);
      }
    });
  }, []);

  useEffect(() => {
    const defaultProfile: CoursePreferenceProfile = {
      courseId: "",
      parallelCourseId: undefined,
      predecessorCourseId: undefined,
      preferredRoomId: undefined,
      alternateRoomId: undefined,
      weeklySwitchOfRooms: false,
      preferredBlockSize: 1,
    };

    setCourseProfile(defaultProfile);

    const availableRooms: RoomShort[] = [
      { id: "1", abbreviation: "HS01", name: "Hörsaal 01" },
      { id: "2", abbreviation: "HS02", name: "Hörsaal 02" },
    ];
    setAvailableRoomList(availableRooms);

    const availableCourses: LectureBase[] = [
      { id: "1", casID: "PS1", name: "PS1", courseType: "vlseung" },
      { id: "2", casID: "PS2", name: "PS2", courseType: "vlseung" },
    ];
    setAvailableCourseList(availableCourses);
  }, []); //TODO: Fill all needed elements from requests

  function postCourseProfile() {
    saveCourseConstraintProfile(courseProfile!);
  }

  const appUnlocked = currentlyUnlocked(
    locks?.startDate ?? null,
    locks?.endDate ?? null
  );

  const roomList = [
    "Hörsaal 1",
    "Hörsaal 2",
    "Hörsaal 3",
    "Hörsaal 4",
    "Hörsaal 5",
    "Hörsaal 6",
    "Hörsaal 7",
    "Seminarraum 1",
    "Seminarraum 2",
    "Seminarraum 3",
    "Seminarraum 4",
    "Seminarraum 5",
    "Seminarraum 6",
    "Seminarraum 7",
    "Seminarraum 8",
    "PC-Pool 1",
    "PC-Pool 2",
    "PC-Pool 3",
    "PC-Pool 4",
    "PC-Pool 5",
    "Laborraum",
    "VR-Lab",
  ];
  const profList = [
    "Beispieldozent 1",
    "Beispieldozent 2",
    "Beispieldozent 3",
    "Beispieldozent 4",
    "Beispielmitarbeiter 1",
    "Beispielmitarbeiter 2",
    "Beispielmitarbeiter 3",
    "Beispielmitarbeiter 4",
  ];
  const altText =
    selectedRoomName !== "no" && alternatingSecondRoomName !== "no"
      ? " " + selectedRoomName + " und " + alternatingSecondRoomName
      : "";
  function options(): ReactJSXElement {
    return (
      <>
        {
          /**Soll Parallel zu einem weiteren kurs laufen? */
          courseSelector(
            availableCourseList.map((item) => item.id),
            availableCourseList.map((item) => item.name),
            courseProfile?.parallelCourseId ?? "no",
            (selectedParallelCourseId: string) => {
              let profileCpy = { ...courseProfile! };
              profileCpy.parallelCourseId = selectedParallelCourseId;
              setCourseProfile(profileCpy);
            },
            {
              customTitle:
                "Veranstaltung, welche parallel/gleichzeitig zur gewählten laufen soll",
              noAsOption: true,
            }
          )
        }
        {getDivider()}
        {
          /**Soll in der Woche vor/nach einem bestimmten Kurs laufen? */
          courseSelector(
            availableCourseList.map((item) => item.id),
            availableCourseList.map((item) => item.name),
            courseProfile?.predecessorCourseId ?? "no",
            (selectedAfterCourseId: string) => {
              let profileCpy = { ...courseProfile! };
              profileCpy.predecessorCourseId = selectedAfterCourseId;
              setCourseProfile(profileCpy);
            },
            {
              customTitle:
                "Veranstaltung, die in der Woche nach der gewählten laufen soll",
              noAsOption: true,
            }
          )
        }
        {getDivider()}
        {
          /**Soll bestimmten Raum haben */
          courseSelector(
            availableCourseList.map((item) => item.id),
            availableCourseList.map((item) => item.name),
            selectedRoomName,
            (selectedRoomId: string) => {
              let profileCpy = { ...courseProfile! };
              profileCpy.preferredRoomId = selectedRoomId;
              setSelectedRoom(
                availableRoomList.find((value) => value.id == selectedRoomId)!
                  .name
              );
              setCourseProfile(profileCpy);
            },
            {
              customTitle: "Gewünschter Raum für die Veranstaltung",
              noAsOption: true,
            }
          )
        }
        {getDivider()}
        {
          /**Soll alternierend zwei Räume verwenden */
          courseSelector(
            roomList,
            roomList,
            alternatingSecondRoomName,
            (alternatingSecondRoomId: string) => {
              let profileCpy = { ...courseProfile! };
              profileCpy.alternateRoomId = alternatingSecondRoomId;
              setAlternatingSecondRoom(
                availableRoomList.find(
                  (value) => value.id == alternatingSecondRoomId
                )!.name
              );
              setCourseProfile(profileCpy);
            },
            {
              customTitle:
                "Zweiter Wunschraum, welcher auch reserviert sein soll",
              noAsOption: true,
            }
          )
        }
        {getSpacer()}
        {
          <FormControlLabel
            control={
              <Switch
                value={courseProfile?.weeklySwitchOfRooms ?? false}
                onChange={(event, checked) => {
                  let profileCpy = { ...courseProfile! };
                  profileCpy.weeklySwitchOfRooms = event.target.checked;
                  setCourseProfile(profileCpy);
                }}
              />
            }
            label={"Wöchentlicher wechsel der Räume" + altText + "."}
          />
        }
        {getSettingComponent(
          "Veranstaltung im Block (nur ändern wenn Veranstaltung mehr als 2 SWS hat)",
          "Veranstaltung im Block",
          courseProfile!.preferredBlockSize,
          (blockSize: number) => {
            let profileCpy = { ...courseProfile! };
            profileCpy.preferredBlockSize = blockSize;
            setCourseProfile(profileCpy);
          }
        )}
        {getDivider()}
        {
          <Button
            variant="contained"
            aria-label="Wunschprofil speichern"
            disabled={!appUnlocked}
            onClick={() => {
              postCourseProfile();
              alert(
                "Profil erfolgreich für " + selectedCourseName + " gespeichert"
              );
            }}
          >
            {"Profil speichern"}
          </Button>
        }
      </>
    );
  }

  return (
    <Box
      sx={{
        p: 1,
        m: 1,
        width: "100%",
        paddingX: "20%",
        maxWidth: "1100px",
        display: "flex",
        flexDirection: "column",
        textAlign: "center",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <PageLayout title={"Kursspezifische Einstellungen"}>
        <Box
          sx={{
            textAlign: "center",
          }}
        >
          <p>Hier werden diverse Wünsche je Kurs definiert.</p>
          <p>
            Mit "Veranstaltung" meinen wir jeglichen Kurs, ob nun eine Vorlesung
            (bspw. Analysis) oder eine Übungsveranstaltung (bspw.
            Übung/Betreuung PS1). Mit "Zeitslot" oder "Slot" ist immer ein
            beliebiger 75 Minuten-Teil des Stundenplans gemeint. Mit "Block"
            meinen wir, dass eine Veranstaltung in mehreren Stunden
            hintereinander abläuft, sofern die SWS das erlauben. Für das
            Beispiel Analysis wäre das: Von 08:00 bis 09:15 und von 9:30 bis
            10:45, also über zwei Zeitslots.
          </p>
          <p>
            Bitte beachten Sie, dass sie nur ihre eigenen Kurse auswählen. Es
            sei darauf hingewiesen, dass hier nur Präferenzen eingestellt werden
            und die Beachtung dieser bei der Erstellung des Stundenplans nicht
            garantiert werden kann.
          </p>
        </Box>
        {getDivider()}
        {courseSelector(
          availableCourseList.map((item) => item.id),
          availableCourseList.map((item) => item.name),
          selectedCourseName,
          (selectedCourseId: string) => {
            let profileCpy = { ...courseProfile! };
            profileCpy.courseId = selectedCourseId;
            setSelectedCourse(
              availableCourseList.find((e) => e.id == selectedCourseId)!.name
            );
            setCourseProfile(profileCpy);
          }
        )}
        {getDivider()}
        {courseProfile?.courseId && options()}
      </PageLayout>
    </Box>
  );
}

export default CourseProfilePage;
