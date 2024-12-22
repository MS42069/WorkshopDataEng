import { sortAsc, sortDesc } from "../shared/sorting";
import { SortBy, SortingDirection } from "../enums/sort-by";

export interface RoomShort {
  // UUID - Value
  id: string;
  abbreviation: string;
  name: string;
}

export type RoomDetail = {
  capacity: number;
  roomType: RoomType;
  identifier: string;
} & RoomShort;

export type RoomAPI = {
  name: string;
  abbreviation: string;
  identifier: string;
  capacity: number;
  roomType: RoomType | string;
  id: string;
  timetable: string;
};

export type RoomType = {
  id: string;
  name: string;
  online: boolean;
};

export type RoomSortingKeys = Pick<RoomAPI, "name" | "abbreviation">;

export type RoomCreateDetail = Partial<RoomDetail>;

export type RoomShortKey = keyof RoomShort;
export type RoomDetailKey = keyof RoomDetail;

export function sortRooms(
  rooms: RoomShort[],
  sortBy: SortBy<RoomShort>
): RoomShort[] {
  const sortedRooms = [...rooms];
  if (!sortBy.column || sortBy.direction === SortingDirection.NoDirection) {
    return sortedRooms.sort((a, b) => sortDesc(a.name, b.name));
  }

  const sortByColumn = sortBy.column;
  switch (sortBy.direction) {
    case SortingDirection.Ascending:
      sortedRooms.sort((a, b) => sortAsc(a[sortByColumn], b[sortByColumn]));
      break;
    case SortingDirection.Descending:
      sortedRooms.sort((a, b) => sortDesc(a[sortByColumn], b[sortByColumn]));
      break;
  }

  return sortedRooms;
}
