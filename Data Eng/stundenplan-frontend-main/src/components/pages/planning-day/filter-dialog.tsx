import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Theme,
  useTheme,
} from "@mui/material";
import AutocompleteEmployee from "../../../shared/components/autocomplete-employee/autocomplete-employee";
import AutocompleteSubject from "../../../shared/components/autocomplete-subject/autocomplete-subject";
import "./filter-dialog.css";
import { LecturesForPlanFilter } from "../../../models/lectures-for-plan-filter";
import { useEffect, useState } from "react";

interface FilterDialogProps {
  showDialog: boolean;
  closeDialog: () => void;
  applyFilter: (options: LecturesForPlanFilter | undefined) => void;
  filter?: LecturesForPlanFilter;
}

const FilterDialog = ({
  showDialog,
  closeDialog,
  applyFilter,
  filter,
}: FilterDialogProps) => {
  const theme: Theme = useTheme();
  const [filterOptions, setFilterOptions] = useState<LecturesForPlanFilter>();

  useEffect(() => {
    setFilterOptions(filter);
  }, [filter]);

  const submitFilter = () => {
    applyFilter(filterOptions);
    closeDialog();
  };

  return (
    <Dialog open={showDialog}>
      <DialogTitle
        sx={{ backgroundColor: theme.palette.primary.main, color: "white" }}
      >
        Filterauswahl
      </DialogTitle>
      <DialogContent className="DialogContent">
        <p>Beide Filter können kombiniert werden.</p>
        <AutocompleteEmployee
          value={filterOptions?.employee}
          className="filter-dialog__autocomplete--margin"
          onChange={(employee) =>
            setFilterOptions({ ...filterOptions, employee })
          }
        />

        <AutocompleteSubject
          value={filterOptions?.subject}
          onChange={(subject) =>
            setFilterOptions({ ...filterOptions, subject })
          }
        />
      </DialogContent>
      <DialogActions>
        <Button variant="contained" onClick={submitFilter}>
          Anwenden
        </Button>

        <Button variant="text" onClick={closeDialog}>
          Abbruch
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default FilterDialog;
