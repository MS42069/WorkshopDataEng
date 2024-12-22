import { useEffect, useState } from "react";
import React from "react";
import "./detail-crud.css";

import { Edit } from "@mui/icons-material";
import {
  Button,
  IconButton,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow,
  TextField,
  Theme,
  useTheme,
} from "@mui/material";
import { SortingDirection } from "../../../enums/sort-by";
import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import SearchIcon from "@mui/icons-material/Search";
import { determineNewSort, getSortingIcon } from "../../../shared/sorting";
import { Sort, useSort } from "../../../hooks/useSort.hook";
import { useFilter } from "../../../hooks/useFilter.hook";

type ListElementsProps<T, K extends keyof T> = {
  elements: T[];
  columns: { displayName: string; key: K }[];
  addEvent: () => void;
  editEvent: (clickedElement: T) => void;
  deleteEvent: (clickedElement: T) => void;
};

const ListElements = <T, K extends keyof T>({
  elements,
  columns,
  addEvent,
  editEvent,
  deleteEvent,
}: ListElementsProps<T, K>): JSX.Element => {
  const [searchContent, setSearchContent] = useState<string>("");
  const [sortBy, setSortBy] = useState<Sort<T>>({
    key: columns[0].key,
    direction: SortingDirection.Descending,
  });
  const { sortedValues, sort, setInitialValues } = useSort<T>([], sortBy);
  const {
    filteredValues,
    filter,
    setInitialValues: setFilterElements,
  } = useFilter<T>([]);

  function sortElements(valuesForSorting: T[], sortBy: Sort<T>) {
    setInitialValues(valuesForSorting);
    sort(sortBy);
  }

  function filterElements(valuesForFiltering: T[], filterBy: string) {
    setFilterElements(valuesForFiltering);
    filter([...columns.map((col) => col.key)], filterBy);
  }

  useEffect(() => {
    sortElements(filteredValues, sortBy);
  }, [filteredValues]);

  useEffect(() => {
    filterElements(elements, searchContent);
  }, [elements, sortBy, searchContent]);

  const theme: Theme = useTheme();

  // ********** Pagination **********
  const paginationOptions: number[] = [15, 25, 50];

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(paginationOptions[0]);

  const handleChangePage = (
    _: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number
  ) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - sortedValues.length) : 0;

  return (
    <TableContainer className="detail-crud-overview__table-container">
      <Table size="small" className="Table-detail-crud-overview">
        <TableHead>
          <TableRow>
            {columns.map((column) => (
              <TableCell align="center" key={column.displayName}>
                <Button
                  onClick={() => {
                    setSortBy(determineNewSort<T>(sortBy, column.key));
                  }}
                >
                  {getSortingIcon(sortBy, column.key)}
                  {column.displayName}
                </Button>
              </TableCell>
            ))}
            <TableCell>
              <div className="searchbar">
                <SearchIcon />
                <TextField
                  variant="standard"
                  placeholder="Suche…"
                  focused
                  sx={{ width: "30%" }}
                  onChange={(e) => {
                    setSearchContent(e.target.value);
                    setPage(0);
                  }}
                />
              </div>
            </TableCell>
            <TableCell className="TableCellAddIcon">
              <IconButton onClick={addEvent}>
                <AddIcon />
              </IconButton>
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody className="detail-crud-overview__table-body TableBody">
          {sortedValues
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((current, rowIdx) => (
              <TableRow key={rowIdx}>
                {columns.map((column, colIdx) => (
                  <TableCell
                    key={`row:${rowIdx} col:${colIdx}`}
                    component="th"
                    scope="row"
                    align="center"
                  >
                    {String(current[column.key])}
                  </TableCell>
                ))}
                <TableCell />
                <TableCell>
                  <IconButton
                    onClick={() => editEvent(current)}
                    edge="end"
                    aria-label="edit"
                    sx={{
                      color: theme.palette.primary.main,
                    }}
                  >
                    <Edit />
                  </IconButton>
                  <IconButton
                    edge="end"
                    aria-label="delete"
                    sx={{
                      color: theme.palette.primary.main,
                    }}
                    onClick={() => deleteEvent(current)}
                  >
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          {emptyRows > 0 && (
            <TableRow
              style={{
                height: 53 * emptyRows,
              }}
            >
              <TableCell colSpan={6} />
            </TableRow>
          )}
        </TableBody>
      </Table>

      <TablePagination
        component="div"
        rowsPerPageOptions={paginationOptions}
        count={sortedValues.length}
        page={page}
        onPageChange={handleChangePage}
        rowsPerPage={rowsPerPage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </TableContainer>
  );
};

export default ListElements;
