import PageLayout from "../../../shared/components/page-layout";
import React, { useEffect, useState } from "react";
import { Divider } from "@mui/material";
import ReadonlyPlanningOverview from "../../../shared/components/readonly-planning-overview/readonly-planning-overview";
import { ReadonlyPlanningOverviewTableEntry } from "../../../shared/components/readonly-planning-overview/readonly-planning.overview.types";
import { SubjectSemesterAPI } from "../../../models/subjectSemester";
import { WeekEvent } from "../../../models/week-event";
import { fetchSubjectSemester } from "../../../shared/requests/subject-semester";
import { getWeekEventsBySemester } from "../../../shared/requests/week-event.requests";
import {
  WeekdayDescription,
  getAllWeekdays,
} from "../../../enums/weekday.enum";
import { TimeslotAPI } from "../../../models/timeslots";
import { fetchTimeslots } from "../../../shared/requests/timeslot";
import AutocompleteSubject from "../../../shared/components/autocomplete-subject/autocomplete-subject";

function PlanningLecture() {
  const [weekdays, setWeekdays] = useState<WeekdayDescription[]>([]);
  const [timeslots, setTimeslots] = useState<TimeslotAPI[]>([]);
  const [weekEvents, setWeekEvents] = useState<WeekEvent[]>([]);
  const [selectedSubjectSemester, setSelectedSubjectSemester] =
    useState<SubjectSemesterAPI | null>();
  const [overviewTableEntries, setOverviewTableEntries] = useState<
    ReadonlyPlanningOverviewTableEntry[]
  >([]);

  const handleLectureSemesterChange = (subject: SubjectSemesterAPI | null) => {
    if (!subject) {
      setSelectedSubjectSemester(null);
      setWeekEvents([]);
    } else {
      fetchSubjectSemester(subject.id).then(({ data }) =>
        setSelectedSubjectSemester(data)
      );
      getWeekEventsBySemester(subject.id).then(({ data }) =>
        setWeekEvents(data)
      );
    }
  };

  useEffect(() => {
    setWeekdays(getAllWeekdays());
    fetchTimeslots().then((response) => setTimeslots(response));
  }, []);

  useEffect(() => {
    let available: ReadonlyPlanningOverviewTableEntry[] = [];
    let blocked: ReadonlyPlanningOverviewTableEntry[] = [];

    if (selectedSubjectSemester) {
      available = timeslots
        .map((timeslot) =>
          weekdays.map(
            (weekday) =>
              ({
                type: "available",
                week: 0,
                timeslot,
                weekday: weekday.value,
              } as ReadonlyPlanningOverviewTableEntry),
            []
          )
        )
        .flat();

      blocked = weekEvents
        .map(({ week, weekday, timeslots, course, rooms }) =>
          timeslots.map(
            (timeslot) =>
              ({
                type: "blocked",
                week,
                roomIds: rooms.map(({ id }) => id),
                course,
                weekday,
                timeslot,
              } as ReadonlyPlanningOverviewTableEntry),
            []
          )
        )
        .flat();
    }

    setOverviewTableEntries([...available, ...blocked]);
  }, [selectedSubjectSemester, weekEvents]);

  return (
    <PageLayout title="Vorlesungsplan Fachsemester">
      <AutocompleteSubject
        value={selectedSubjectSemester}
        onChange={(subject) => handleLectureSemesterChange(subject)}
      />
      <Divider sx={{ marginTop: 1, marginBottom: 1 }} />
      <ReadonlyPlanningOverview items={overviewTableEntries} />
    </PageLayout>
  );
}

export default PlanningLecture;
