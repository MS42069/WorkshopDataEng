import { Autocomplete, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { EmployeeAPI } from "../../../models/employee";
import { fetchEmployees } from "../../requests/employer.requests";

interface AutocompleteEmployeeProps {
  value?: EmployeeAPI | null;
  onChange: (value: EmployeeAPI | null) => void;
  className?: string;
}

const AutocompleteEmployee = ({
  value,
  onChange,
  className,
}: AutocompleteEmployeeProps) => {
  const [employees, setEmployees] = useState<EmployeeAPI[]>([]);

  useEffect(() => {
    fetchEmployees().then(({ data }) => setEmployees(data));
  }, []);

  const getDisplayName = (value: EmployeeAPI) =>
    `${value.firstname} ${value.lastname}`;

  return (
    <Autocomplete
      size="small"
      className={className}
      value={value || null}
      options={employees}
      getOptionLabel={(option) => getDisplayName(option)}
      renderInput={(params) => (
        <TextField {...params} label="Mitarbeiter auswählen..." />
      )}
      onChange={(_, value) => onChange(value)}
      isOptionEqualToValue={(option, value) => option.id === value.id}
      renderOption={(props, option) => {
        return (
          <li {...props} key={option.id}>
            {getDisplayName(option)}
          </li>
        );
      }}
    />
  );
};

export default AutocompleteEmployee;
