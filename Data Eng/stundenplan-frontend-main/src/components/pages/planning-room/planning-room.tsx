import PageLayout from "../../../shared/components/page-layout";
import React, { useEffect, useState } from "react";
import Autocomplete from "@mui/material/Autocomplete";
import { Divider, TextField } from "@mui/material";
import { fetchRoom, fetchRooms } from "../../../shared/requests/room.requests";
import { TimeslotAPI } from "../../../models/timeslots";
import { RoomAPI } from "../../../models/room";
import { WeekEvent } from "../../../models/week-event";
import { fetchTimeslots } from "../../../shared/requests/timeslot";
import {
  WeekdayDescription,
  getAllWeekdays,
} from "../../../enums/weekday.enum";
import { getWeekEventsByRoom } from "../../../shared/requests/week-event.requests";
import ReadonlyPlanningOverview from "../../../shared/components/readonly-planning-overview/readonly-planning-overview";
import { ReadonlyPlanningOverviewTableEntry } from "../../../shared/components/readonly-planning-overview/readonly-planning.overview.types";

function PlanningRoom() {
  const [rooms, setRooms] = useState<RoomAPI[]>([]);
  const [weekEvents, setWeekEvents] = useState<WeekEvent[]>([]);
  const [selectedRoom, setSelectedRoom] = useState<RoomAPI | null>();
  const [overviewTableEntries, setOverviewTableEntries] = useState<
    ReadonlyPlanningOverviewTableEntry[]
  >([]);
  const [weekdays, setWeekdays] = useState<WeekdayDescription[]>([]);
  const [timeslots, setTimeslots] = useState<TimeslotAPI[]>([]);

  const handleRoomChange = (room: RoomAPI | null) => {
    if (!room) {
      setSelectedRoom(null);
      setWeekEvents([]);
    } else {
      fetchRoom(room.id).then(({ data }) => setSelectedRoom(data));
      getWeekEventsByRoom(room.id).then(({ data }) => setWeekEvents(data));
    }
  };

  useEffect(() => {
    fetchRooms().then((response) => setRooms(response.data));
    setWeekdays(getAllWeekdays());
    fetchTimeslots().then((response) => setTimeslots(response));
  }, []);

  useEffect(() => {
    let available: ReadonlyPlanningOverviewTableEntry[] = [];
    let blocked: ReadonlyPlanningOverviewTableEntry[] = [];

    if (selectedRoom) {
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
  }, [selectedRoom, weekEvents]);

  return (
    <PageLayout title="Vorlesungsplan Räume">
      <Autocomplete
        size="small"
        options={rooms}
        getOptionLabel={(option) => `${option.name}`}
        renderInput={(params) => (
          <TextField {...params} label="Raum auswählen..." />
        )}
        onChange={(_, value) => handleRoomChange(value)}
        isOptionEqualToValue={(option, value) => option.id === value.id}
        renderOption={(props, option) => {
          return (
            <li {...props} key={option.id}>
              {option.name}
            </li>
          );
        }}
      />
      <Divider sx={{ marginTop: 1, marginBottom: 1 }} />

      <ReadonlyPlanningOverview items={overviewTableEntries} />
    </PageLayout>
  );
}

export default PlanningRoom;
