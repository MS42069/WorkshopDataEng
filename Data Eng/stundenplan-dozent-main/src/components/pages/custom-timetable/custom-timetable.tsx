import { useContext, useEffect, useState } from "react";
import AvailabilityTable, {
  AvailabilityAPI,
} from "../availability-table/availability-table";
import { Box, Button } from "@mui/material";
import PageLayout from "../../elements/page-layout";
import { getDivider } from "../../shared/shared";
import { getAllWeekdays } from "../../../enums/weekday.enum";
import { getTimeSlots } from "../../../models/timeslots";
import {
  fetchWorktimeConstraint,
  saveWorktimeConstraint,
} from "../../../models/worktimeConstraint";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/store";
import { currentlyUnlocked } from "../../shared/context-provider";
import {
  AppAvailabilityDTO,
  getAppAvailability,
} from "../../../models/app-availability";
import PopupDialog from "../../popup/popupDialog";

function CustomTimetable() {
  const [timeslots, setTimeslots] = useState<AvailabilityAPI[]>([]);
  const { user } = useSelector(({ authReducer }: RootState) => authReducer);
  const [locks, setLocks] = useState<AppAvailabilityDTO | null>(null);
  const [isDialogOpen, setDialogOpen] = useState<boolean>(false);

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

  const weekDays = getAllWeekdays().splice(0, 6);
  const apiTimeslots = getTimeSlots();

  const selectAllEntries = async () => {
    const availabilities: AvailabilityAPI[] = [];
    const weekdaysWithoutWeekend = weekDays.filter(
      (weekday) => weekday.value !== "SATURDAY" && weekday.value !== "SUNDAY"
    );

    for (const timeslot of apiTimeslots) {
      for (const weekday of weekdaysWithoutWeekend) {
        const temp: AvailabilityAPI = {
          timeslot,
          weekday: weekday.value,
          available: "gerne",
          isAccepted: false,
        };
        availabilities.push(temp);
      }
    }
    const worktime = await fetchWorktimeConstraint(user!.username);

    setTimeslots(worktime);
  };

  useEffect(() => {
    selectAllEntries().then();
  }, []);

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
      <PopupDialog isOpen={isDialogOpen} setOpen={setDialogOpen}/>

      <PageLayout title={"Zeitplan"}>
        <p>
          Hier kann ein eigener, rudimentärer Zeitplan erstellt werden. Ein
          Zeitslot kann markiert werden, dass dieser entweder zwingend frei
          bleiben (rot), lieber frei bleiben (orange) oder gerne mit einer
          Veranstaltung belegt werden (grün) soll. Gibt es zu einem Zeitslot
          keine Wünsche, kann dieser als "Gleichgültig" markiert werden (grau).
          Wenn Sie Samstags üblicherweise nicht arbeiten, müssen sie den Samstag
          nicht explizit als "zwingend frei bleiben" markieren.
        </p>
        <Box
          sx={{
            fontWeight: "bold",
            color: "red",
            backgroundColor: "yellow",
          }}
        >
          <p>
            Wichtig: Bitte nutzen Sie die Option "Zeitslot zwingend freihalten"
            nicht aus, um Ihren perfekten Wunschzeitplan zu erstellen, dieser ist ausschließlich für
            unüberbrückbare planerische Probleme gedacht!
          </p>
        </Box>
        {getDivider()}
        <AvailabilityTable
          availability={timeslots}
          setAvailability={setTimeslots}
          useHardConstraint={true}
        />
        <>
          <Box sx={{ m: 1 }} />
          {
            <Button
              variant="contained"
              disabled={!appUnlocked}
              aria-label="Erstelltes Präferenz-Profil abspeichern"
              onClick={async () => {
                await saveWorktimeConstraint(timeslots, user!.username);
                setDialogOpen(true)
              }}
            >
              Zeitplan speichern
            </Button>
          }
        </>
        <Box
          sx={{
            fontWeight: "bold",
            color: "red",
            backgroundColor: "yellow",
          }}
        >
          <p>
            Hinweis: Welche Veranstaltungen in die grünen Zeitslots gelegt
            werden, kann nicht bestimmt werden. Hier können bspw. nur die
            Arbeitszeiten eingetragen werden, in denen vorzugsweise
            Vorlesungen/Veranstaltungen stattfinden sollen.
          </p>
        </Box>
      </PageLayout>
    </Box>
  );
}

export default CustomTimetable;
