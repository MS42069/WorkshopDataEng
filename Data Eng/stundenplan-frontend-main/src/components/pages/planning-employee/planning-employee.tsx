import PageLayout from "../../../shared/components/page-layout";
import { useEffect, useState } from "react";
import { Divider } from "@mui/material";
import { fetchEmployee } from "../../../shared/requests/employer.requests";
import { EmployeeAPI } from "../../../models/employee";
import { WeekEvent } from "../../../models/week-event";
import { getWeekEventsByEmployee } from "../../../shared/requests/week-event.requests";
import ReadonlyPlanningOverview from "../../../shared/components/readonly-planning-overview/readonly-planning-overview";
import { ReadonlyPlanningOverviewTableEntry } from "../../../shared/components/readonly-planning-overview/readonly-planning.overview.types";
import AutocompleteEmployee from "../../../shared/components/autocomplete-employee/autocomplete-employee";

function PlanningEmployee() {
  const [weekEvents, setWeekEvents] = useState<WeekEvent[]>([]);
  const [selectedEmployee, setSelectedEmployee] =
    useState<EmployeeAPI | null>();
  const [overviewTableEntries, setOverviewTableEntries] = useState<
    ReadonlyPlanningOverviewTableEntry[]
  >([]);

  const handleEmployeeChange = (employee: EmployeeAPI | null) => {
    if (!employee) {
      setSelectedEmployee(null);
      setWeekEvents([]);
    } else {
      fetchEmployee(employee.id).then(({ data }) => setSelectedEmployee(data));
      getWeekEventsByEmployee(employee.id).then(({ data }) =>
        setWeekEvents(data)
      );
    }
  };

  useEffect(() => {
    let available: ReadonlyPlanningOverviewTableEntry[] = [];
    let blocked: ReadonlyPlanningOverviewTableEntry[] = [];

    if (selectedEmployee) {
      available =
        selectedEmployee?.workTimes.map(({ weekday, timeslot }) => ({
          type: "available",
          week: 0,
          timeslot,
          weekday,
        })) || [];

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
  }, [selectedEmployee, weekEvents]);

  return (
    <PageLayout title="Vorlesungsplan Mitarbeiter">
      <AutocompleteEmployee
        value={selectedEmployee}
        onChange={handleEmployeeChange}
      />
      <Divider sx={{ marginTop: 1, marginBottom: 1 }} />

      <ReadonlyPlanningOverview items={overviewTableEntries} />
    </PageLayout>
  );
}

export default PlanningEmployee;
