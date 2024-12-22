import { sortDesc } from "../sorting";
import { Employee } from "./employee";
import { RoomShort } from "./room";
import { AvailabilityAPI } from "../components/pages/availability-table/availability-table";
import { WeekdayValue } from "../enums/weekday.enum";

export type LectureBase = {
  id: string;
  casID: string;
  name: string;
  courseType: CourseType | string;
};
export type LectureAPI = {
  abbreviation: string;
  description: string;
  blockSize: number;
  weeksPerSemester: number;
  slotsPerWeek: number;
  suitedRooms: [
    {
      rooms: RoomShort[];
    }
  ];
  lecturers: [
    {
      id: string;
      abbreviation: string;
      firstname: string;
      lastname: string;
      employeeType: string;
    }
  ];
  courseRelations: {
    mayBeParallelTo: LectureBase[];
    mustBeHeldBefore: LectureBase[];
    mustBeHeldAfter: LectureBase[];
  };
  courseTimeslots: AvailabilityAPI[];
} & LectureBase;

export type AvailabilityRequest = {
  weekday: WeekdayValue;
  timeslotID: string;
};
export type CourseType = {
  id: string;
  name: string;
};

export type SuitedRooms = {
  rooms: {
    id: string;
    name: string;
    abbreviation: string;
  }[];
}[];
export type LecturersAPI = { id: string }[];
export type CourseRelation = { id: string }[];
export type LectureDetail = {
  needsPlaning: boolean;
  // Total amount of Units this course needs planning for per week
  unitsPerWeek: number;
  // When planning this course needs at least this amount of units back-to-back
  unitsInBlock: number;
  // Amount of weeks this course takes place in a Semester. (Standard is 12 weeks)
  weeksPerSemester: number;
  // Overrides qualification of rooms by neededEquipment
  suitedRooms: RoomCombination[];
  lecturers: Employee[];
  heldBefore: LectureBase[];
  // Overrides qualification of timeslots by availability of rooms, employees and students
  mayBeParallelTo: LectureBase[];
} & LectureBase;
//++++++++++++++++++ RoomCombination ++++++++++++++++++

// A single combination of rooms connects rooms by a logical AND
// Elements of a RoomCombination Array are connected by a logical OR
export type RoomCombination = {
  rooms: RoomShort[];
};

export function roomCombinationAsString(
  roomCombination: RoomCombination | undefined
): string {
  if (!roomCombination) {
    return "Raumkombination nicht gefunden!";
  }

  let res = "";
  roomCombination.rooms.sort((a, b) => sortDesc(a.name, b.name));
  for (let j = 0; j < roomCombination.rooms.length; j++) {
    res = res.concat(roomCombination.rooms[j].name);
    if (j < roomCombination.rooms.length - 1) {
      res = res.concat(" & ");
    }
  }
  return res;
}
