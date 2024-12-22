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
import "./crud-table.css";
import { SortingDirection } from "../../../enums/sort-by";
import DeleteDialog from "../standard-actions/delete-dialog";
import { Fragment, useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import { Edit } from "@mui/icons-material";
import DeleteIcon from "@mui/icons-material/Delete";
import SearchIcon from "@mui/icons-material/Search";
import AddIcon from "@mui/icons-material/Add";
import {
  determineNewSort,
  getSortingIcon,
  sortAsc,
  sortDesc,
} from "../../sorting";
import { AxiosResponse } from "axios";
import { Sort } from "../../../hooks/useSort.hook";

/*
  This type defines the table colums. Where T is
  the data type of the table data and K is an attribute of T.
  The key 'header' can be used to specify any string which
  represents the column name for the column of the corresponding key.
*/
export type ColumnDefinitionType<T, K extends keyof T> = {
  key: K;
  header: string;
};

const paginationOptions: number[] = [15, 25, 50];

type TableProps<T, K extends keyof T> = {
  /*
    An array of data of type T, which is represented in the table.
  */
  tableData: Array<T>;
  /*
   * Array of column definitons.
   */
  columns: Array<ColumnDefinitionType<T, K>>;
  /*
    Sort type that is used for sorting the data.
  */
  sortBy: Sort<any>;
  /*
    Title of delete dialog.
  */
  deleteDialogTitle: string;
  /*
    Function to update table data.
  */
  fetchElements: () => Promise<any>;
  /*
    Funciton to delete table data.
  */
  deleteElement: (roomId: string) => Promise<AxiosResponse<any, any>>;
  /*
   (Optional) Funtion to format data that is fetched by the fetchElements() function.
  */
  dataFormatter?: (...params: any[]) => T;

  /*
    (Optional) Function to initially filter fetched values.
  */
  filterData?: (...params: any[]) => boolean;

  /*
    (Optional) Function that is called when a user clicks on the 'add' button to
    override the default implementation.
  */
  onClickAddElement?: () => void;
  /*
    (Optional) Function that is called when a user clicks on the 'edit' button to
    override the default implementation.
  */
  onClickEditElement?: (...params: any[]) => void;
  /*
    (Optional) A  state variable used to react to changes in table data from
    external components. External components must change this variable when
    changing the table data so that the data in the table is updated.
  */
  externalModified?: boolean;
  /*
    (Optional) Setter for externalModified state variable.
  */
  setExternalModified?: React.Dispatch<React.SetStateAction<boolean>>;
};

/*
  The generic table currently only supports data types
  that have at least one attribute 'name'.
*/
const CrudTable = <T, K extends keyof T>({
  tableData,
  columns,
  sortBy,
  deleteDialogTitle,
  fetchElements,
  deleteElement,
  dataFormatter,
  filterData,
  onClickAddElement,
  onClickEditElement,
  externalModified,
  setExternalModified,
}: TableProps<T, K>): JSX.Element => {
  const [clickedRowElement, setClickedRowElement] = useState<any>();
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(paginationOptions[0]);
  const theme: Theme = useTheme();
  const [elements, setElements] = useState<T[]>(tableData);
  const [sortedElements, setSortedElements] = useState<T[]>([]);
  const [searchContent, setSearchContent] = useState<string>("");
  const [sortByState, setSortBy] = useState<Sort<any>>(sortBy);
  const [showDeleteDialog, setShowDeleteDialog] = useState<boolean>(false);

  useEffect(() => {
    if (!showDeleteDialog) {
      setClickedRowElement(undefined);
    }
  }, [showDeleteDialog]);

  const onDeleteCancel = () => {
    setShowDeleteDialog(false);
  };

  const onDeleteSubmit = (clickedId: string | undefined) => {
    deleteElement(clickedId ?? "").then((response) => {
      if (response.statusText === "No Content") {
        setElements(
          elements.filter((element) => getValue(element, "id") !== clickedId)
        );
        setShowDeleteDialog(false);
      }
    });
  };

  const containsFirstAndLastname = (
    firstname: string | undefined,
    lastname: string | undefined
  ): boolean => {
    return firstname != undefined && lastname != undefined;
  };

  const containsCasId = (casId: string | undefined): boolean => {
    return casId != undefined;
  };

  const containsName = (name: string | undefined): boolean => {
    return name != undefined;
  };

  const containsStudyRegulation = (
    studyRegulation: string | undefined
  ): boolean => {
    return studyRegulation != undefined;
  };

  const containsAbbreviation = (abbtreviation: string | undefined): boolean => {
    return abbtreviation != undefined;
  };

  const containsStartAndEndTime = (
    startTime: string | undefined,
    endTime: string | undefined
  ): boolean => {
    return startTime != undefined && endTime != undefined;
  };

  function filterElements(elements: any[], searchContent: string): T[] {
    const searchLowerCase = searchContent.toLowerCase();
    return elements.filter(
      ({
        abbreviation,
        name,
        firstname,
        lastname,
        casID,
        startTime,
        endTime,
        studyRegulation,
      }) => {
        if (
          containsFirstAndLastname(firstname, lastname) &&
          containsAbbreviation(abbreviation)
        ) {
          return (
            abbreviation.toLowerCase().includes(searchLowerCase) ||
            firstname.toLowerCase().includes(searchLowerCase) ||
            lastname.toLowerCase().includes(searchLowerCase)
          );
        }
        if (containsCasId(casID) && containsName(name)) {
          return (
            name.toLowerCase().includes(searchLowerCase) ||
            casID.toLowerCase().includes(searchLowerCase)
          );
        }
        if (containsStartAndEndTime(startTime, endTime)) {
          return (
            startTime.toLowerCase().includes(searchLowerCase) ||
            endTime.toLowerCase().includes(searchLowerCase)
          );
        }
        if (containsStudyRegulation(studyRegulation) && containsName(name)) {
          return (
            name.toLowerCase().includes(searchLowerCase) ||
            studyRegulation.toLowerCase().includes(searchLowerCase)
          );
        }
        if (containsName(name) && containsAbbreviation(abbreviation)) {
          return (
            name.toLowerCase().includes(searchLowerCase) ||
            abbreviation.toLowerCase().includes(searchLowerCase)
          );
        }
        if (containsName(name)) {
          return name.toLowerCase().includes(searchLowerCase);
        }
        return true;
      }
    );
  }

  function sortElements(elements: any[], sortBy: Sort<Pick<any, any>>): T[] {
    const sortedElements = [...elements];
    if (!sortBy.key || sortBy.direction === SortingDirection.NoDirection) {
      return sortedElements.sort((a, b) =>
        sortDesc(getValue(a, "name"), getValue(b, "name"))
      );
    }

    const sortByColumn = sortBy.key;
    switch (sortBy.direction) {
      case SortingDirection.Ascending:
        sortedElements.sort((a, b) =>
          sortAsc(a[sortByColumn], b[sortByColumn])
        );
        break;
      case SortingDirection.Descending:
        sortedElements.sort((a, b) =>
          sortDesc(a[sortByColumn], b[sortByColumn])
        );
        break;
    }

    return sortedElements;
  }

  function updateElements(
    elements: any[],
    sortBy: Sort<Pick<any, any>>,
    filterBy: string
  ): T[] {
    const filteredElements = [...filterElements(elements, filterBy)];
    return sortElements(filteredElements, sortBy);
  }

  const isInitRender = useRef(true);

  useEffect(() => {
    if (isInitRender.current) {
      fetchElements().then((response) => {
        let filteredResponse: T[] = response.data;
        if (filterData) {
          filteredResponse = filteredResponse.filter(filterData);
        }
        if (dataFormatter) {
          setElements(filteredResponse.map(dataFormatter));
        } else {
          setElements(filteredResponse);
        }
      });
      isInitRender.current = false;
    } else if (externalModified && setExternalModified) {
      fetchElements().then((response) => {
        let filteredResponse: T[] = response.data;
        if (filterData) {
          filteredResponse = filteredResponse.filter(filterData);
        }
        if (dataFormatter) {
          setElements(filteredResponse.map(dataFormatter));
        } else {
          setElements(filteredResponse);
        }
        setSortedElements(updateElements(elements, sortByState, searchContent));
        setExternalModified(false);
      });
    } else {
      setSortedElements(updateElements(elements, sortByState, searchContent));
    }
  }, [elements, sortByState, searchContent, externalModified]);

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

  // ************** Header **************
  type TableHeaderProps<T, K extends keyof T> = {
    columns: Array<ColumnDefinitionType<T, K>>;
  };

  const TableHeader = <T, K extends keyof T>({
    columns,
  }: TableHeaderProps<T, K>): JSX.Element => {
    const getSearchBarAndAddIconElement = (): JSX.Element => {
      return (
        <Fragment key={`headCell-Fragment`}>
          <TableCell>
            <div className="searchbar">
              <SearchIcon />
              <TextField
                variant="standard"
                placeholder="Suche…"
                value={searchContent}
                focused
                autoFocus
                sx={{ width: "30%" }}
                onChange={(e) => {
                  setSearchContent(e.target.value);
                  setPage(0);
                }}
              />
            </div>
          </TableCell>
          <TableCell className="TableCellAddIcon">
            {onClickAddElement ? (
              <IconButton
                onClick={() => {
                  onClickAddElement();
                }}
              >
                <AddIcon />
              </IconButton>
            ) : (
              <Link to="add">
                <IconButton>
                  <AddIcon />
                </IconButton>
              </Link>
            )}
          </TableCell>
        </Fragment>
      );
    };

    const headers = columns.map((column, index) => {
      return (
        <TableCell key={`headCell-${index}`} align="center">
          <Button
            onClick={() => {
              setSortBy(
                determineNewSort<Pick<any, any>>(sortByState, column.key)
              );
            }}
          >
            {getSortingIcon(sortByState, column.key)}
            {column.header}
          </Button>
        </TableCell>
      );
    });
    headers.push(getSearchBarAndAddIconElement());

    return (
      <TableHead>
        <TableRow>{headers}</TableRow>
      </TableHead>
    );
  };

  // ************** Rows *************
  type TableRowsProps<T, K extends keyof T> = {
    data: Array<T>;
    columns: Array<ColumnDefinitionType<T, K>>;
  };

  function getValue(item: any, key: string): any {
    let actualResult: string | unknown = "";
    Object.entries(item).forEach(([tmpKey, value]) => {
      if (tmpKey === key) {
        actualResult = value;
      }
    });
    return actualResult;
  }

  const TableRows = <T, K extends keyof T>({
    data,
    columns,
  }: TableRowsProps<T, K>): JSX.Element => {
    const rows = data
      .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
      .map((row, index) => {
        console.log(row);

        return (
          <TableRow key={`row-${index}`}>
            {columns.map((column, index2) => {
              if (column.key === "online") {
                return (
                  <TableCell
                    key={`cell-${index2}`}
                    align="center"
                    scope="row"
                    component="th"
                  >
                    {row[column.key] ? "Ja" : "Nein"}
                  </TableCell>
                );
              } else {
                return (
                  <TableCell
                    key={`cell-${index2}`}
                    align="center"
                    scope="row"
                    component="th"
                  >
                    {String(row[column.key])}
                  </TableCell>
                );
              }
            })}
            <Fragment>
              <TableCell />
              <TableCell>
                {onClickEditElement ? (
                  <IconButton
                    edge="end"
                    aria-label="edit"
                    sx={{
                      color: theme.palette.primary.main,
                    }}
                    onClick={() => {
                      onClickEditElement(row);
                    }}
                  >
                    <Edit />
                  </IconButton>
                ) : (
                  <Link to={`edit/${getValue(row, "id")}`}>
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
                )}
                <IconButton
                  edge="end"
                  aria-label="delete"
                  sx={{
                    color: theme.palette.primary.main,
                  }}
                  onClick={() => {
                    setShowDeleteDialog(true);
                    setClickedRowElement(row);
                  }}
                >
                  <DeleteIcon />
                </IconButton>
              </TableCell>
            </Fragment>
          </TableRow>
        );
      });

    return (
      <TableBody className="table__table-body TableBody">{rows}</TableBody>
    );
  };

  // ********************************

  return (
    <TableContainer className="table__table-container">
      <Table size="small" className="table-overview">
        <TableHeader columns={columns} />
        <TableRows data={sortedElements} columns={columns} />
      </Table>
      <DeleteDialog
        showDialog={showDeleteDialog}
        dialogTitle={deleteDialogTitle}
        deleteInfo={`Möchten Sie ${
          containsFirstAndLastname(
            clickedRowElement?.firstname,
            clickedRowElement?.lastname
          )
            ? String(clickedRowElement.firstname)
                .concat(" ")
                .concat(clickedRowElement.lastname)
            : containsStartAndEndTime(
                clickedRowElement?.startTime,
                clickedRowElement?.endTime
              )
            ? String(clickedRowElement.startTime)
                .concat("-")
                .concat(clickedRowElement.endTime)
            : clickedRowElement?.name
        } wirklich löschen?`}
        onClickCancel={onDeleteCancel}
        onClickSubmit={() => onDeleteSubmit(clickedRowElement.id)}
      />
      <TablePagination
        component="div"
        rowsPerPageOptions={paginationOptions}
        count={sortedElements.length}
        page={page}
        onPageChange={handleChangePage}
        rowsPerPage={rowsPerPage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </TableContainer>
  );
};

export default CrudTable;
