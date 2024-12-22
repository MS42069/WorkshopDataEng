import { useEffect, useState } from "react";
import { SubjectSemesterAPI } from "../../../models/subjectSemester";
import { fetchSubjectSemesters } from "../../requests/subject-semester";
import { Autocomplete, TextField } from "@mui/material";

interface AutocompleteSubjectProps {
  value?: SubjectSemesterAPI | null;
  onChange: (value: SubjectSemesterAPI | null) => void;
}

const AutocompleteSubject = ({ value, onChange }: AutocompleteSubjectProps) => {
  const [subjectsSemester, setSubjectSemesters] = useState<
    SubjectSemesterAPI[]
  >([]);

  useEffect(() => {
    fetchSubjectSemesters().then((response) =>
      setSubjectSemesters(response.data)
    );
  }, []);

  const getDisplayName = (value: SubjectSemesterAPI): string => {
    return `${value.semesterNumber}. Semester ${value.degree.shortName} ${value.extensionName}`;
  };

  return (
    <Autocomplete
      value={value || null}
      size="small"
      options={subjectsSemester}
      getOptionLabel={(option) => getDisplayName(option)}
      renderInput={(params) => (
        <TextField {...params} label="Fachsemester auswählen..." />
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

export default AutocompleteSubject;
