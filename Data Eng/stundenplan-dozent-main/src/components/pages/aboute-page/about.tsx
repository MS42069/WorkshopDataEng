import { Box } from "@mui/material";
import PageLayout from "../../elements/page-layout";
import { useContext, useEffect, useState } from "react";
import {
  CustomContextVal,
  currentlyUnlocked,
  dayFormatWithHour,
} from "../../shared/context-provider";
import {
  AppAvailabilityDTO,
  getAppAvailability,
} from "../../../models/app-availability";
import dayjs from "dayjs";
import { dayFormatShort } from "../../../models/timetable";

export default About;

function About() {
  const [locks, setLocks] = useState<AppAvailabilityDTO | null>();

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

  let unlockedText =
    "Die Eintragung ist derzeit freigegeben." +
    ((locks?.startDate ?? null) != null && (locks?.endDate ?? null) != null
      ? " Der Eintragungszeitraum startet am " +
        dayjs(locks?.startDate).format(dayFormatWithHour).toString() +
        " und endet am " +
        dayjs(locks?.endDate).format(dayFormatWithHour).toString() +
        " Zur rechtzeitigen Erinnerung an die Fristen erhalten Sie per E-Mail entsprechende Benachrichtigungen."
      : "");

  let lockedText =
    "Die Eintragung von neuen Wünschen ist zur Zeit nicht möglich. Sie können nur Ihre aktuell vorliegenden Wünsche ansehen. " +
    (locks?.startDate != null
      ? "Die Eintragung wird am " +
        dayjs(locks?.startDate).format(dayFormatWithHour).toString() +
        " Uhr wieder freigeschaltet."
      : "");

  return (
    <Box
      sx={{
        p: 1,
        m: 1,
        width: "100%",
        maxWidth: "1100px",
        paddingX: "width / 3",
        display: "flex",
        flexDirection: "row",
        textAlign: "center",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <PageLayout title={"Startseite"}>
        <p>Sehr geehrte Mitarbeiterinnen und Mitarbeiter der FH-Wedel,</p>
        <p>
        herzlich willkommen auf dieser Website, auf der Sie die Möglichkeit haben, 
        Ihre bevorzugten Vorlesungstermine sowie andere zeitliche Präferenzen für das kommende Semester einzutragen. 
        Ihre Angaben fließen in die Planung des neuen Stundenplans ein, wobei wir darauf hinweisen möchten, 
        dass eine vollständige Erfüllung aller Wünsche nicht garantiert werden kann.
        </p>
        <p>
          {appUnlocked ? (
            unlockedText
          ) : (
            <Box
              sx={{
                fontWeight: "bold",
                color: "red",
                backgroundColor: "yellow",
              }}
            >
              {lockedText}
            </Box>
          )}
        </p>
        <p>
          Für Fragen und Feedback zu dieser Website und der neuen Software im Allgemeinen steht Ihnen die folgende Mailadresse zur Verfügung: 
        
        </p>
        <a href="mailto:stundenplan-dev@fh-wedel.de">
          stundenplan-dev@fh-wedel.de
        </a>.
        <p>
        Wir bemühen uns, Ihre Anliegen stets zeitnah zu bearbeiten.

        Vielen Dank für Ihre Mitwirkung und freundliche Grüße,
        Das Stundenplan-Development Team
        </p>
      </PageLayout>
    </Box>
  );
}
