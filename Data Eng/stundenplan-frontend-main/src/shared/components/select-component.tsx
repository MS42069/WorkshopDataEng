import {
  Tooltip,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
} from "@mui/material";

export type Types = {
  id: string;
  name: string;
};

type SelectProps = {
  selectData: Array<Types>;
  tooltip: string;
  className: string;
  label: string;
  value: string;
  onChange: (value: string) => void;
};

const SelectComponent = ({
  selectData,
  tooltip,
  className,
  label,
  value,
  onChange,
}: SelectProps) => {
  return (
    <FormControl className={className}>
      <InputLabel id="dropdown">{label}</InputLabel>
      {(!selectData.length && (
        <Tooltip title={tooltip}>
          <Select labelId="dropdown" label={label} value="" disabled />
        </Tooltip>
      )) || (
        <Select
          labelId="dropdown"
          label={label}
          id="dropdown"
          value={value}
          onChange={(event) => {
            onChange(event.target.value);
          }}
        >
          {selectData.map((type) => (
            <MenuItem key={type.id} value={type.name}>
              {type.name}
            </MenuItem>
          ))}
        </Select>
      )}
    </FormControl>
  );
};

export default SelectComponent;
