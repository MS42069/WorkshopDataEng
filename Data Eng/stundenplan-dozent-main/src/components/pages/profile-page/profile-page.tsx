import {
  Box,
  Button,
  FormGroup,
  InputLabel,
  MenuItem,
  Select,
  Slider,
  Tooltip,
  Typography,
} from "@mui/material";
import { useContext, useEffect, useState } from "react";
import {
  PreferenceProfile,
  Prioritization,
} from "../../../models/employee-wishes";
import PageLayout from "../../elements/page-layout";
import { ReactJSXElement } from "@emotion/react/types/jsx-namespace";
import {
  getDivider,
  getSettingComponent,
  getSpacer,
  getSmallSpacer,
  getWidthLimiter,
} from "../../shared/shared";
import { getAllFreeDays } from "../../../enums/weekday.enum";
import { Mark } from "@mui/base";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/store";
import {
  fetchContraintProfil,
  saveConstraintProfile,
} from "../../../models/constraints";
import {
  CustomContextVal,
  currentlyUnlocked,
} from "../../shared/context-provider";
import {
  AppAvailabilityDTO,
  getAppAvailability,
} from "../../../models/app-availability";
import PopupDialog from "../../popup/popupDialog";

function ProfilePage() {
  const { user } = useSelector(({ authReducer }: RootState) => authReducer);
  const [isLoading, setLoading] = useState<boolean>(true);
  const [profile, setNewProfile] = useState<PreferenceProfile>();
  const [locks, setLocks] = useState<AppAvailabilityDTO | null>(null);

  const [isDialogOpen, setDialogOpen] =
  useState<boolean>(false);

  
  useEffect(() => {
    getAppAvailability().then((value) => {
      if (value) {
        setLocks(value);
      }
    });
  }, []);

  const appUnlocked = currentlyUnlocked(
    locks?.startDate ?? null,
    locks?.endDate ?? null
  );

  useEffect(() => {
    if (isLoading) {
      fetchContraintProfil(user!.username)
        .then((preferenceProfile) => {
          setNewProfile(preferenceProfile);
          setLoading(false);
        })
        .catch(() => {
          setLoading(false);
        });
    }
  }, [isLoading, user]);

  const marksForMaxSlots: Mark[] = [
    {
      value: 1,
      label: "egal",
    },
    {
      value: 2,
      label: "gewünscht",
    },
  ];
  const weekdays = getAllFreeDays();

  /**Actual Content of the Form */
  function getFormContent(employeeProfil: PreferenceProfile): ReactJSXElement {
    return (
      <>
        <FormGroup>
          <PopupDialog isOpen={isDialogOpen} setOpen={setDialogOpen}/>

          {getSettingComponent(
            "Eine freie Mittagspause",
            "Priorisierung der Mittagspause",
            employeeProfil.lunchBreak,
            (newValue: Prioritization) => {
              let profileCpy = { ...employeeProfil };
              profileCpy.lunchBreak = newValue;
              setNewProfile(profileCpy);
            }
          )}
          {getSettingComponent(
            "Einen vorlesungsfreien Tag in der Woche, oder mehr",
            "Priorisierung ders vorlesungsfreien Tages in der Woche, oder mehr",
            employeeProfil.freeDaysInWeek,
            (newValue: Prioritization) => {
              let profileCpy = { ...employeeProfil };
              profileCpy.freeDaysInWeek = newValue;
              setNewProfile(profileCpy);
            }
          )}
          {employeeProfil.freeDaysInWeek > 0 && (
            <>
              {getSmallSpacer()}
              <InputLabel id="simple-select-day-label">
                Wichtigster freier Tag
              </InputLabel>
              {getSmallSpacer()}
              <Select
                labelId="select day"
                id="simple-select-day"
                sx={{ width: "40%", alignSelf: "center" }}
                value={employeeProfil.importantFreeDay}
                //defaultValue="FRIDAY"
                onChange={(e) => {
                  if (e.target.value != null) {
                    // @ts-ignore
                    setNewProfile({
                      ...profile,
                      importantFreeDay: e.target.value,
                    });
                  }
                }}
              >
                {weekdays.map((weekday, index) => (
                  <MenuItem key={index} value={weekday.value}>
                    {weekday.name}
                  </MenuItem>
                ))}
              </Select>{" "}
            </>
          )}
          {getSettingComponent(
            'Pausen (Einen "75 Minuten"-Slot oder mehr) zwischen Veranstaltungen bzw. Veranstaltungen nicht direkt hintereinander gelegt',
            "Priorisierung der Pausen zwischen Veranstaltungen",
            employeeProfil.breaksBeforeCourse,
            (newValue: Prioritization) => {
              let profileCpy = { ...employeeProfil };
              profileCpy.breaksBeforeCourse = newValue;
              setNewProfile(profileCpy);
            }
          )}
          {getSettingComponent(
            "Bündelung der Veranstaltungen in einer Woche zu veranstaltungsintensiveren Tagen statt einer gleichmäßigen Verteilung",
            "Priorisierung der Legung der Veranstaltungen innerhalb einer Woche",       
            employeeProfil.coursesOverTheWeek,
            (newValue: Prioritization) => {
              // an dieser Stelle wird der Wert umgedreht beim auslesen, da er ursprünglich auch in umgekehrter Logik abgefragt wurde 
              // diese Lösung umgeht die Gefahr bei einer späteren Logikumkehrung eine Stelle zu übersehen
              const invertedValue: Prioritization = newValue === 0 ? newValue : -newValue as Prioritization;
              let profileCpy = { ...employeeProfil };
              profileCpy.coursesOverTheWeek = newValue;
              setNewProfile(profileCpy);
            }
          )}
          {getWidthLimiter([
            getDivider(),
            <Typography component="legend">
              Maximale Anzahl von Zeitslots ("75 Minuten"), die
              aufeinanderfolgend mit Veranstaltungen belegt werden
            </Typography>,
            <Tooltip
              title="Hier wird die ausgewählte Maximalanzahl priorisiert"
              placement="bottom"
            >
              <Slider
                sx={{ width: "70%" }}
                aria-label="Wichtigkeit von maximaler Anzahl von Blöcken hintereinander"
                defaultValue={2}
                value={employeeProfil.slotPriority}
                onChange={(_, value) => {
                  let profileCpy = { ...employeeProfil };
                  profileCpy.slotPriority = value as number;
                  setNewProfile(profileCpy);
                }}
                valueLabelDisplay="off"
                step={1}
                marks={marksForMaxSlots}
                min={1}
                max={2}
              />
            </Tooltip>,
            getSpacer(),
            employeeProfil.slotPriority === 2 ? (
              <Tooltip
                title="Hier wird die maximale Anzahl von Vorlesungsstunden ausgewählt"
                placement="left-start"
              >
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  sx={{ width: "40%", alignSelf: "center" }}
                  value={employeeProfil.maxSlotsPerDay}
                  onChange={(e, _) => {
                    if (e.target.value != null) {
                      let profileCpy = { ...employeeProfil };
                      profileCpy.maxSlotsPerDay = e.target.value as number;
                      setNewProfile(profileCpy);
                    }
                  }}
                >
                  <MenuItem value={1}>1</MenuItem>
                  <MenuItem value={2}>2</MenuItem>
                  <MenuItem value={3}>3</MenuItem>
                  <MenuItem value={4}>4</MenuItem>
                  <MenuItem value={5}>5</MenuItem>
                  <MenuItem value={6}>6</MenuItem>
                </Select>
              </Tooltip>
            ) : (
              <></>
            ),
          ])}
        </FormGroup>

        {
          <>
            {getDivider()}
            {
              <Button
                variant="contained"
                disabled={!appUnlocked}
                aria-label="Erstelltes Präferenz-Profil abspeichern"
                onClick={async () => {
                  await saveConstraintProfile(user!.username, employeeProfil);
                  setDialogOpen(true)
                }}
              >
                Profil speichern
              </Button>
            }
          </>
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
      <PageLayout title={"Präferenz-Profil"}>
        <Box
          sx={{
            textAlign: "center",
          }}
        >
          <p>
            Hier werden diverse Wünsche an den Stundenplan priorisiert. Die
            Priorisierung entspricht dabei der Benenung der Slider und Buttons.
          </p>{" "}
          <p>
            Mit "Veranstaltung" meinen wir jeglichen Kurs, ob nun eine Vorlesung
            (bspw. Analysis) oder eine Übungsveranstaltung (bspw.
            Übung/Betreuung PS1). Mit "Zeitslot" oder "Slot" ist immer ein
            beliebiger 75 Minuten-Teil des Stundenplans gemeint.
          </p>{" "}
          <p>
            Priorisiert man z.B. "Pausen zwischen Veranstaltungen gewünscht" mit
            "Nicht gewünscht", so heißt das implizit dass "Keine Pausen zwischen
            Veranstaltungen" gewünscht sind. Etwas als unerwünscht zu
            priorisieren heißt also gleichzeitig das Gegenteil als gewünscht zu
            priorisieren.
          </p>{" "}
          <p>
            Es sei darauf hingewiesen, dass hier nur Präferenzen eingestellt
            werden und die Beachtung dieser bei der Erstellung des Stundenplans
            nicht garantiert werden kann.
          </p>
        </Box>
        {getDivider()}
        {profile ? getFormContent(profile) : <></>}
      </PageLayout>
    </Box>
  );
}

export default ProfilePage;
