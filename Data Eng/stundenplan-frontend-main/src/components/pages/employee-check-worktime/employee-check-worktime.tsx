import { Fragment, useEffect, useRef, useState } from "react";
import { SortingDirection } from "../../../enums/sort-by";
import {
  EmployeeAPI,
  EmployeeCheckConstraint,
  EmployeeSort,
} from "../../../models/employee";
import CrudTable, {
  ColumnDefinitionType,
} from "../../../shared/components/crud-table/crud-table";
import {
  deleteEmployee,
  fetchEmployees,
  fetchEmployeesCheckWorktime,
  updateEmployeesCheckWorktime,
} from "../../../shared/requests/employer.requests";
import PageLayout from "../../../shared/components/page-layout";
import { Sort } from "../../../hooks/useSort.hook";
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
import SearchIcon from "@mui/icons-material/Search";

function ListEmployeesCheck() {
  const [sortBy] = useState<Sort<EmployeeSort>>({
    key: "firstname",
    direction: SortingDirection.Descending,
  });

  const [employees, setEmployees] = useState<EmployeeCheckConstraint[]>([]);

  const [showPopUp, setShowPopUp] = useState<boolean>(false);
  const [showPopUpText, setShowPopUpText] = useState<string>("");
  const [selectedConstraint, setSelectedContraint] = useState<number>(0);
  const [accepted, setAccepted] = useState<boolean>(false);
  const [reason, setReason] = useState<string>("");

  const isInitRender = useRef(true);

  useEffect(() => {
    if (isInitRender.current) {
      fetchEmployeesCheckWorktime().then((response) => setEmployees(response));
    }
  }, []);

  const columns: ColumnDefinitionType<any, keyof any>[] = [
    {
      key: "firstname",
      header: "Vorname",
    },
    {
      key: "lastname",
      header: "Nachname",
    },
    {
      key: "abbreviation",
      header: "Kürzel",
    },
    {
      key: "startTime",
      header: "Start Zeit",
    },
    {
      key: "endTime",
      header: "End Zeit",
    },
    {
      key: "weekDay",
      header: "Wochentag",
    },
    {
      key: "reason",
      header: "Begründung",
    },
  ];

  const headers = () => {
    return (
      <Fragment key={`headCell-Fragment`}>
        {columns.map((column, index) => {
          return (
            <TableCell key={`headCell-${index}`} align="center">
              <div>{column.header}</div>
            </TableCell>
          );
        })}
      </Fragment>
    );
  };

  const AgreePopup = () => {
    return (
      <div
        style={{
          zIndex: 10,
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          position: "absolute",
          left: "0px",
          right: "0px",
          top: "0px",
          bottom: "0px",
          backgroundColor: "#252121a6",
        }}
      >
        <div
          style={{
            width: "fit-content",
            padding: "2rem",
            backgroundColor: "white",
            opacity: "100%",
            borderRadius: "1rem",
          }}
        >
          <div style={{ marginBottom: "1rem" }}>{showPopUpText}</div>
          {!accepted ? (
            <TextField
              id="filled-multiline-static"
              label="Ablehnungsgrund"
              multiline
              rows={4}
              placeholder="Hier kommt schöner text"
              variant="filled"
              onChange={(event) => setReason(event.currentTarget.value)}
            />
          ) : (
            <></>
          )}
          <div style={{ marginTop: "1rem" }}>
            <Button
              style={{ marginRight: "1rem" }}
              variant="contained"
              onClick={async () => {
                setShowPopUp(false);
                await sendPost();
              }}
            >
              Okay
            </Button>
            <Button
              variant="outlined"
              onClick={async () => {
                setShowPopUp(false);
              }}
            >
              Abbrechen
            </Button>
          </div>
        </div>
      </div>
    );
  };

  const sendPost = async () => {
    await updateEmployeesCheckWorktime({
      id: employees[selectedConstraint].id,
      accepted,
      reason,
    });
    setEmployees(
      employees.filter((employee, index) => index != selectedConstraint)
    );
  };

  return (
    <Fragment>
      <PageLayout title={"Übersicht Mitarbeiter Bestätigungen"}>
        <TableContainer className="table__table-container">
          <Table size="small" className="table-overview">
            <TableHead>
              <TableRow>{headers()}</TableRow>
            </TableHead>
            <TableBody className="table__table-body TableBody">
              {employees.map((row, index) => {
                return (
                  <TableRow key={`row-${index}`}>
                    {columns.map((column, index2) => {
                      console.log(column);

                      return (
                        <TableCell
                          key={`cell-${index2}`}
                          align="center"
                          scope="row"
                          component="th"
                        >
                          {(row as any)[column.key]}
                        </TableCell>
                      );
                    })}
                    <TableCell align="center" scope="row" component="th">
                      <Button
                        style={{ marginRight: "1rem" }}
                        variant="contained"
                        onClick={() => {
                          setShowPopUp(true);
                          setAccepted(true);
                          setShowPopUpText("Wirklich annehmen?");
                          setSelectedContraint(index);
                        }}
                      >
                        Annehmen
                      </Button>
                      <Button
                        variant="outlined"
                        onClick={() => {
                          setShowPopUp(true);
                          setAccepted(false);
                          setShowPopUpText("Wirklich ablehnen?");
                          setSelectedContraint(index);
                        }}
                      >
                        Ablehnen
                      </Button>
                    </TableCell>
                  </TableRow>
                );
              })}
            </TableBody>
          </Table>
        </TableContainer>
      </PageLayout>
      {showPopUp ? AgreePopup() : <></>}
    </Fragment>
  );
}

export default ListEmployeesCheck;
