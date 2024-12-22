import { Box, Button } from "@mui/material";
import PageLayout from "../../elements/page-layout";
import {
  getDivider,
  courseSelector,
  courseList,
  getSpacer,
} from "../../shared/shared";
import { useContext, useEffect, useState } from "react";
import AvailabilityTable, {
  AvailabilityAPI,
} from "../availability-table/availability-table";
import { getAllWeekdays } from "../../../enums/weekday.enum";
import { getTimeSlots } from "../../../models/timeslots";
import {
  CustomContextVal,
  currentlyUnlocked,
} from "../../shared/context-provider";

function CourseTimetable() {
  const [selectedCourse, setSelectedCourse] = useState<string>("");
  const [timeslots, setTimeslots] = useState<AvailabilityAPI[]>([]);
  const ctx = useContext(CustomContextVal);
  const appUnlocked = currentlyUnlocked(
    ctx.unlock?.startDate ?? null,
    ctx.unlock?.endDate ?? null
  );

  const weekDays = getAllWeekdays().splice(0, 6);
  const apiTimeslots = getTimeSlots();
  const selectAllEntries = () => {
    const availabilities: AvailabilityAPI[] = [];
    const weekdaysWithoutWeekend = weekDays.filter(
      (weekday) => weekday.value !== "SATURDAY" && weekday.value !== "SUNDAY"
    );

    for (const timeslot of apiTimeslots) {
      for (const weekday of weekdaysWithoutWeekend) {
        availabilities.push({
          timeslot,
          weekday: weekday.value,
          available: "gerne",
          isAccepted: false,
        });
      }
    }

    setTimeslots(availabilities);
  };

  useEffect(() => {
    selectAllEntries();
  });

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
          <p>Hier wird pro Kurs ein Zeitplan definiert.</p>
          <p>
            Bitte beachten Sie, dass sie nur ihre eigenen Veranstaltungen
            auswählen. Es sei darauf hingewiesen, dass hier nur Präferenzen
            eingestellt werden und die Beachtung dieser bei der Erstellung des
            Stundenplans nicht garantiert werden kann.
          </p>
        </Box>
        {getDivider()}
        {courseSelector(
          courseList,
          courseList,
          selectedCourse,
          setSelectedCourse
        )}
        {getDivider()}
        {selectedCourse && (
          <AvailabilityTable
            availability={timeslots}
            setAvailability={setTimeslots}
            useHardConstraint={false}
            availableTitle="Kurs gerne in diesem Zeitslot (grün)"
            notAvailableTitle="Kurs lieber nicht in diesem Zeitslot (orange)"
          />
        )}
        {selectedCourse && (
          <>
            {getSpacer()}
            {
              <Button
                variant="contained"
                aria-label="Erstellten Zeitplan abspeichern"
                disabled={!appUnlocked}
                onClick={() => {
                  /*TODO: Save the profile to the database*/ alert(
                    "Zeitplan für " +
                      selectedCourse +
                      " erfolgreich gespeichert"
                  );
                }}
              >
                Zeitplan speichern
              </Button>
            }
          </>
        )}
      </PageLayout>
    </Box>
  );
}

export default CourseTimetable;
