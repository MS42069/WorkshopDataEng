import "./single-view-room.css";

import { TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { RoomAPI, RoomType } from "../../../models/room";
import ErrorDisplay from "../../../shared/components/error-display";
import PageLayout from "../../../shared/components/page-layout";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import {
  createRoom,
  fetchRoom,
  patchRoom,
} from "../../../shared/requests/room.requests";
import { fetchRoomTypes } from "../../../shared/requests/types.request";
import { getBaseDataURLPref } from "../../../shared/url-prefix";
import { checkedParseInt } from "../../../shared/parse.util";
import SelectComponent from "../../../shared/components/select-component";

function SingleViewRoom() {
  const [roomTypes, setRoomTypes] = useState<RoomType[]>([]);
  const [name, setName] = useState<string>("");
  const [identifier, setIdentifier] = useState<string>("");
  const [abbreviation, setAbbreviation] = useState<string>("");
  const [capacity, setCapacity] = useState<number>(0);
  const [roomType, setRoomType] = useState<RoomType | null>(null);
  const [savePushed, setSavePushed] = useState<boolean>(false);
  const navigate = useNavigate();
  const { timetableId } = useParams();

  const { id } = useParams();
  const errorInput: boolean =
    name === "" || abbreviation === "" || identifier === "";

  const handleInputChangeRoomType = (value: string) => {
    setRoomType(roomTypes.find(({ name }) => name === value) || null);
  };

  const goBackToOverview = () =>
    navigate(`${getBaseDataURLPref(timetableId)}/room`);

  useEffect(() => {
    fetchRoomTypes().then(({ data }) => {
      setRoomTypes(data);
      setRoomType(data.length ? data[0] : null);
    });
    if (id) {
      fetchRoom(id).then(({ data }) => {
        setName(data.name);
        setAbbreviation(data.abbreviation);
        setCapacity(data.capacity);
        if (typeof data.roomType === "object") {
          setRoomType(data.roomType);
        }
        setIdentifier(data.identifier);
      });
    }
  }, []);

  const getRoomTitle = (): string => {
    const createRoomTitle = "Raum erstellen";
    const editRoomTitle = "Raum bearbeiten";
    if (id) {
      return editRoomTitle;
    }
    return createRoomTitle;
  };

  const getSavingButtonText = (): string => {
    const createRoomText = "Erstellen";
    const editRoomText = "Speichern";
    if (id) {
      return editRoomText;
    }
    return createRoomText;
  };

  const saveRoom = () => {
    const room: RoomAPI = {
      id: id ?? "",
      abbreviation,
      name,
      capacity,
      roomType: roomType?.id || "",
      identifier,
      timetable: timetableId ?? "",
    };

    setSavePushed(true);
    if (!errorInput) {
      if (id) {
        patchRoom(room).then(goBackToOverview).catch(console.error);
      } else {
        createRoom(room).then(goBackToOverview).catch(console.error);
      }
    }
  };

  const actions = (
    <SubmitButtons
      submitText={getSavingButtonText()}
      cancelText="Abbrechen"
      onClickSubmit={saveRoom}
      onClickCancel={goBackToOverview}
      isValid={!errorInput}
    />
  );

  return (
    <PageLayout title={getRoomTitle()} actions={actions}>
      <div className="single-view-room">
        <div className="flex flex-row single-view-room__form-row-widht">
          <TextField
            id="bezeichner"
            label="Bezeichner"
            className="single-view-room__text-field"
            variant="outlined"
            error={name === "" && savePushed}
            helperText={
              name === "" && savePushed
                ? "Der Bezeichner darf nicht leer sein!"
                : ""
            }
            value={name}
            onChange={(event) => {
              setName(event.target.value);
            }}
          />

          <TextField
            id="abbreviation"
            label="Kürzel"
            className="single-view-room__text-field"
            variant="outlined"
            error={abbreviation === "" && savePushed}
            helperText={
              abbreviation === "" && savePushed
                ? "Das Kürzel darf nicht leer sein!"
                : ""
            }
            value={abbreviation}
            onChange={(event) => {
              setAbbreviation(event.target.value);
            }}
          />
        </div>

        <div className="flex flex-row single-view-room__form-row-widht">
          <TextField
            id="identifier"
            label="Raumkennung"
            className="single-view-room__text-field"
            variant="outlined"
            error={identifier === "" && savePushed}
            helperText={
              identifier === "" && savePushed
                ? "Der Identifier darf nicht leer sein!"
                : ""
            }
            value={identifier}
            onChange={(event) => {
              setIdentifier(event.target.value);
            }}
          />

          <TextField
            id="seats"
            label="Plätze"
            variant="outlined"
            className="single-view-room__text-field"
            value={capacity}
            onChange={(event) =>
              setCapacity(checkedParseInt(event.target.value, capacity))
            }
          />
        </div>
        <div>
          <SelectComponent
            selectData={roomTypes}
            tooltip="Es wurden noch keine Raumtypen angelegt."
            className="single-view-room__text-field"
            label="Raumtyp"
            value={roomType?.name || ""}
            onChange={handleInputChangeRoomType}
          ></SelectComponent>
        </div>
      </div>
    </PageLayout>
  );
}

export default SingleViewRoom;
