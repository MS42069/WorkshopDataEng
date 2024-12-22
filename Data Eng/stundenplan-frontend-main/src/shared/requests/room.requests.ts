import { resourceAxiosInstance } from "../../config/axios.config";
import { RoomAPI } from "../../models/room";
import { createTimetablePath } from "../path.util";

//Axios für Raum
export const fetchRooms = () =>
  resourceAxiosInstance.get<RoomAPI[]>("/rooms", {
    id: createTimetablePath("rooms"),
  });

export const fetchRoom = (roomId: string) =>
  resourceAxiosInstance.get<RoomAPI>(`/rooms/${roomId}`, {
    cache: false,
  });

export const createRoom = (room: RoomAPI) =>
  resourceAxiosInstance.post<RoomAPI>("/rooms", room, {
    cache: { update: { [createTimetablePath("rooms")]: "delete" } },
  });

export const patchRoom = (room: RoomAPI) =>
  resourceAxiosInstance.patch<RoomAPI>(`/rooms/${room.id}`, room, {
    cache: { update: { [createTimetablePath("rooms")]: "delete" } },
  });

export const deleteRoom = (roomId: string) =>
  resourceAxiosInstance.delete(`/rooms/${roomId}`, {
    cache: { update: { [createTimetablePath("rooms")]: "delete" } },
  });
