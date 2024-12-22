import * as React from "react";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import ListItemText from "@mui/material/ListItemText";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import Checkbox from "@mui/material/Checkbox";

interface SelectProps {
  title: string;
  options: string[];
  selectedOptions: string[];
  setSelect: React.Dispatch<React.SetStateAction<string[]>>;
}

export default function MultipleSelectCheckmarks({
  title,
  options,
  selectedOptions: equipment,
  setSelect: setEquipment,
}: SelectProps) {
  const handleChange = (event: SelectChangeEvent<typeof equipment>) => {
    const {
      target: { value },
    } = event;
    setEquipment(() => {
      let selectedValues: string[] = [];
      if (value !== undefined) {
        selectedValues = typeof value === "string" ? value.split(",") : value;
      }
      return selectedValues;
    });
  };

  return (
    <>
      <FormControl className="DropdownEquipment">
        <InputLabel id="dropdown-multiple-equipment">{title}</InputLabel>
        <Select
          labelId="ddropdown-multiple-equipment-label"
          id="dropdown-multiple-equipment"
          multiple
          value={equipment}
          onChange={(e) => handleChange(e)}
          input={<OutlinedInput label={title} />}
          renderValue={(selected) => selected.join(", ")}
        >
          {options.map((type) => (
            <MenuItem key={type} value={type}>
              <Checkbox checked={equipment?.indexOf(type) !== -1} />
              <ListItemText primary={type} />
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </>
  );
}
