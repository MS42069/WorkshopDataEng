import React, { useEffect, useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Autocomplete,
  TextField,
} from "@mui/material";
import "./dialog.css";
import { RoomAPI, RoomShort } from "../../../models/room";
import { Theme, useTheme } from "@mui/material";
import { areCombinationsEqual, RoomCombination } from "../../../models/lecture";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import { isUpdate } from "./helper";

interface RoomCombProps {
  showDialog: boolean;
  setShowDialog: React.Dispatch<React.SetStateAction<boolean>>;
  options: RoomAPI[];
  clickedRoomComb: RoomCombination | undefined;
  setClickedRoomComb: React.Dispatch<
    React.SetStateAction<RoomCombination | undefined>
  >;
  suitedRooms: RoomCombination[];
  setSuitedRooms: React.Dispatch<React.SetStateAction<RoomCombination[]>>;
}

const RoomCombinationDialog = ({
  showDialog,
  setShowDialog,
  options,
  clickedRoomComb,
  setClickedRoomComb,
  suitedRooms,
  setSuitedRooms,
}: RoomCombProps) => {
  const theme: Theme = useTheme();
  const [rooms, setRooms] = useState<RoomShort[]>([]);

  useEffect(() => {
    if (clickedRoomComb) {
      setRooms([...clickedRoomComb.rooms]);
    } else {
      setRooms([]);
    }
  }, [showDialog]);

  const handleSave = (
    combinations: RoomCombination[],
    setCombinations: React.Dispatch<React.SetStateAction<RoomCombination[]>>,
    updatedRooms: RoomShort[]
  ) => {
    // delete clicked combination
    const currentCombinations = combinations.filter(
      (originalComb) => !areCombinationsEqual(originalComb, clickedRoomComb)
    );
    const roomsAsCombination: RoomCombination = { rooms: updatedRooms };
    if (
      roomsAsCombination.rooms.length !== 0 &&
      !currentCombinations.some((originalComb) =>
        areCombinationsEqual(originalComb, roomsAsCombination)
      )
    ) {
      currentCombinations.push(roomsAsCombination);
    }
    setCombinations([...currentCombinations]);
  };

  const onSubmit = () => {
    handleSave(suitedRooms, setSuitedRooms, rooms);
    setRooms([]);
    setClickedRoomComb(undefined);
    if (isUpdate(clickedRoomComb)) {
      setShowDialog(false);
    }
  };

  const onCancel = () => {
    setShowDialog(false);
    setClickedRoomComb(undefined);
  };

  const getSavingButtonText = (): string => {
    const createText = "Hinzufügen";
    const editText = "Speichern";
    if (isUpdate(clickedRoomComb)) {
      return editText;
    }
    return createText;
  };

  const getAutocompleteDisplayName = (value: RoomShort) =>
    `${value.abbreviation} (${value.name})`;

  return (
    <Dialog open={showDialog} fullWidth maxWidth="sm">
      <DialogTitle
        sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
      >
        Raumkombination
      </DialogTitle>
      <DialogContent className="DialogContent">
        <Autocomplete
          className="lecture-detail-view__text-field"
          sx={{ marginTop: "1.25rem", borderColor: "red" }}
          multiple
          autoHighlight
          id="multiple-limit-tags"
          options={options}
          getOptionLabel={(option: RoomShort) =>
            getAutocompleteDisplayName(option)
          }
          value={rooms}
          onChange={(_, value) => {
            setRooms([...value]);
          }}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          renderInput={(params) => (
            <TextField {...params} label="Raumkombination" />
          )}
          renderOption={(props, option) => {
            return (
              <li {...props} key={option.id}>
                {getAutocompleteDisplayName(option)}
              </li>
            );
          }}
        />
      </DialogContent>
      <DialogActions>
        <SubmitButtons
          submitText={getSavingButtonText()}
          cancelText="Schliessen"
          onClickSubmit={onSubmit}
          onClickCancel={onCancel}
        />
      </DialogActions>
    </Dialog>
  );
};

export default RoomCombinationDialog;
