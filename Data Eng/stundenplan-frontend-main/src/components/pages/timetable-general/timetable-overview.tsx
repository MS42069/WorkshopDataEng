import { useEffect, useRef, useState } from "react";
import React from "react";
import "./timetable-overview.css";

import { Edit } from "@mui/icons-material";
import ForwardIcon from "@mui/icons-material/Forward";
//import CircleIcon from "@mui/icons-material/Circle";
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
import { Link } from "react-router-dom";
import { SortingDirection } from "../../../enums/sort-by";
import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import SearchIcon from "@mui/icons-material/Search";
import DownloadIcon from "@mui/icons-material/Download";
import PageLayout from "../../../shared/components/page-layout";
import {
  deleteTimetable,
  fetchTimetables,
} from "../../../shared/requests/timetable.requests";
import {
  determineNewSort,
  getSortingIcon,
  sortAsc,
  sortDesc,
} from "../../../shared/sorting";
import DeleteDialog from "../../../shared/components/standard-actions/delete-dialog";
import { stringOfSemester, Timetable } from "../../../models/timetable";
import { getTimeTableInfoByID } from "./helper";
import { axiosInstance } from "../../../config/axios.config";
import { saveAs } from "file-saver";
import { Sort } from "../../../hooks/useSort.hook";

function filterTimetables(
  timetables: Timetable[],
  searchContent: string
): Timetable[] {
  const searchLowerCase = searchContent.toLowerCase();
  return timetables.filter(
    ({ info, semester, start, end }) =>
      stringOfSemester(semester, start, end)
        .toLowerCase()
        .includes(searchLowerCase) ||
      info.toLowerCase().includes(searchLowerCase)
  );
}

function sortTimetables(
  timetables: Timetable[],
  sortBy: Sort<Timetable>
): Timetable[] {
  const sortedTimetables = [...timetables];

  if (sortBy.key && sortBy.direction !== SortingDirection.NoDirection) {
    const sortByColumn = sortBy.key;
    switch (sortBy.direction) {
      case SortingDirection.Ascending:
        if (sortByColumn === "semester") {
          sortedTimetables.sort((a, b) =>
            sortAsc(a[sortByColumn].year, b[sortByColumn].year)
          );
        } else if (sortByColumn === "info") {
          sortedTimetables.sort((a, b) =>
            sortAsc(a[sortByColumn], b[sortByColumn])
          );
        }
        break;
      case SortingDirection.Descending:
        if (sortByColumn === "semester") {
          sortedTimetables.sort((a, b) =>
            sortDesc(a[sortByColumn].year, b[sortByColumn].year)
          );
        } else if (sortByColumn === "info") {
          sortedTimetables.sort((a, b) =>
            sortDesc(a[sortByColumn], b[sortByColumn])
          );
        }
        break;
    }
  }

  return sortedTimetables;
}

/*function completionAsString(isCompleted: boolean): string {
  return isCompleted
    ? "Alle Veranstaltungen verplant"
    : "Nicht alle Veranstaltungen verplant";
}*/

function updateTimetables(
  timetables: Timetable[],
  sortBy: Sort<Timetable>,
  filterBy: string
): Timetable[] {
  const filteredTimetables = [...filterTimetables(timetables, filterBy)];
  return sortTimetables(filteredTimetables, sortBy);
}

function ListTimetables() {
  const [showDeleteDialog, setShowDeleteDialog] = useState<boolean>(false);
  const [clickedRowInTable, setClickedTableRow] = useState<string>("");
  const [searchContent, setSearchContent] = useState<string>("");
  const [sortBy, setSortBy] = useState<Sort<Timetable>>({
    key: "semester",
    direction: SortingDirection.Ascending,
  });

  const [timetables, setTimetables] = useState<Timetable[]>([]);
  const [sortedTimetables, setSortedTimetables] = useState<Timetable[]>([]);
  const isInitRender = useRef(true);

  useEffect(() => {
    if (isInitRender.current) {
      fetchTimetables().then((response) => setTimetables(response));
      isInitRender.current = false;
    } else {
      setSortedTimetables(updateTimetables(timetables, sortBy, searchContent));
    }
  }, [timetables, sortBy, searchContent]);

  useEffect(() => {
    if (!showDeleteDialog) {
      setClickedTableRow("");
    }
  }, [showDeleteDialog]);

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

  const onDeleteSubmit = (clickedId: string) => {
    deleteTimetable(clickedId).then(() => {
      setTimetables(timetables.filter(({ id }) => id !== clickedId));
      setShowDeleteDialog(false);
    });
  };

  const onDeleteCancel = () => {
    setShowDeleteDialog(false);
  };

  const onClickDownload = (id: string) => {
    axiosInstance
      .get<string>(`/timetable/${id}/cas/export`)
      .then((response) => {
        const blob = new Blob([response.data], { type: "text/xml" });
        const filename = "CAS-Export.xml";

        saveAs(blob, filename);
      });
  };

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0
      ? Math.max(0, (1 + page) * rowsPerPage - sortedTimetables.length)
      : 0;

  return (
    <PageLayout title="Vorlesungspläne">
      <TableContainer className="timetables-overview__table-container">
        <Table size="small" className="Table-timetables-overview">
          <TableHead>
            <TableRow>
              <TableCell align="center">
                <Button
                  onClick={() => {
                    setSortBy(determineNewSort<Timetable>(sortBy, "semester"));
                  }}
                >
                  {getSortingIcon(sortBy, "semester")}
                  Semester
                </Button>
              </TableCell>
              <TableCell align="center">
                <Button
                  onClick={() => {
                    setSortBy(determineNewSort(sortBy, "info"));
                  }}
                >
                  {getSortingIcon(sortBy, "info")}
                  Info
                </Button>
              </TableCell>
              <TableCell />
              <TableCell>
                <div className="searchbar">
                  <SearchIcon />
                  <TextField
                    variant="standard"
                    placeholder="Search…"
                    focused
                    sx={{ width: "50%" }}
                    onChange={(e) => {
                      setSearchContent(e.target.value);
                      setPage(0);
                    }}
                  />
                </div>
              </TableCell>
              <TableCell className="TableCellAddIcon">
                <Link to="/timetables/add">
                  <IconButton>
                    <AddIcon />
                  </IconButton>
                </Link>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody className="timetables-overview__table-body TableBody">
            {sortedTimetables
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((current) => (
                <TableRow key={current.id}>
                  <TableCell component="th" scope="row" align="center">
                    {stringOfSemester(
                      current.semester,
                      current.start,
                      current.end
                    )}
                  </TableCell>
                  <TableCell align="center">{current.info}</TableCell>
                  <TableCell />
                  <TableCell align="center">
                    {/*<div className="timetables-overview-completion__flex">
                      {completionAsString(current.complete ?? false)}
                      <CircleIcon
                        fontSize="small"
                        sx={{
                          color: current.complete ? "green" : "gray",
                          marginLeft: "0.5rem",
                        }}
                      />
                      </div>*/}
                  </TableCell>
                  <TableCell>
                    <Link to={`/timetables/edit/${current.id}`}>
                      <IconButton
                        edge="end"
                        aria-label="edit"
                        sx={{
                          color: theme.palette.primary.main,
                        }}
                      >
                        <Edit />
                      </IconButton>
                    </Link>
                    <IconButton
                      edge="end"
                      aria-label="edit"
                      sx={{
                        color: theme.palette.primary.main,
                      }}
                      onClick={() => {
                        onClickDownload(current.id);
                      }}
                    >
                      <DownloadIcon />
                    </IconButton>
                    <IconButton
                      edge="end"
                      aria-label="delete"
                      sx={{
                        color: theme.palette.primary.main,
                      }}
                      onClick={() => {
                        setShowDeleteDialog(true);
                        setClickedTableRow(current.id);
                      }}
                    >
                      <DeleteIcon />
                    </IconButton>
                    <Link to={`/timetable/${current.id}/planning/day`}>
                      <IconButton
                        edge="end"
                        aria-label="select"
                        sx={{
                          marginLeft: "1.5rem",
                          color: theme.palette.primary.main,
                        }}
                      >
                        <ForwardIcon />
                      </IconButton>
                    </Link>
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

        <DeleteDialog
          showDialog={showDeleteDialog}
          dialogTitle="Löschen eines Vorlesungsplans"
          deleteInfo={`Möchten Sie ${getTimeTableInfoByID(
            sortedTimetables,
            clickedRowInTable
          )} wirklich löschen?`}
          onClickSubmit={() => onDeleteSubmit(clickedRowInTable)}
          onClickCancel={() => onDeleteCancel()}
        />
        <TablePagination
          component="div"
          rowsPerPageOptions={paginationOptions}
          count={sortedTimetables.length}
          page={page}
          onPageChange={handleChangePage}
          rowsPerPage={rowsPerPage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </TableContainer>
    </PageLayout>
  );
}

export default ListTimetables;
