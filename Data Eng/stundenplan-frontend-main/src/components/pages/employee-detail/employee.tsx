import { useEffect } from "react";
import { TextField } from "@mui/material";
import PageLayout from "../../../shared/components/page-layout";
import "./employee.css";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  Constraints,
  EmployeeAPIRequest,
  EmployeeType,
  WorkTimeRequest,
} from "../../../models/employee";
import SubmitButtons from "../../../shared/components/standard-actions/submit-buttons";
import { getBaseDataURLPref } from "../../../shared/url-prefix";
import AvailabilityTable, {
  AvailabilityAPI,
} from "../../../shared/components/availability-table/availability-table";
import { fetchEmployeeTypes } from "../../../shared/requests/types.request";
import ErrorDisplay from "../../../shared/components/error-display";
import {
  createEmployee,
  fetchEmployee,
  updateEmployee,
} from "../../../shared/requests/employer.requests";
import SelectComponent from "../../../shared/components/select-component";
import EmployeeAvailabilityTable from "../../../shared/components/employee-availability-table/employee-availability-table";

function Employee() {
  const [employeeTypes, setEmployeeTypes] = useState<EmployeeType[]>([]);
  const [firstname, setFirstName] = useState<string>("");
  const [name, setName] = useState<string>("");
  const [abbreviation, setAbbreviation] = useState<string>("");
  const [employeeType, setEmployeeType] = useState<EmployeeType | null>(null);

  const [workTimes, setWorkTimes] = useState<AvailabilityAPI[]>([]);
  const [timeslotConstraints, setTimeslotConstraints] = useState<Constraints[]>(
    []
  );
  const [savePushed, setSavePushed] = useState<boolean>(false);
  const errorInput: boolean =
    firstname === "" || name === "" || abbreviation === "";

  const navigate = useNavigate();
  const { id, timetableId } = useParams();

  const goBackToOverview = () =>
    navigate(`${getBaseDataURLPref(timetableId)}/employee`);

  useEffect(() => {
    fetchEmployeeTypes().then(({ data }) => {
      setEmployeeTypes(data);
      setEmployeeType(data.length ? data[0] : null);
    });
    if (id) {
      fetchEmployee(id).then((response) => {
        setAbbreviation(response.data.abbreviation);
        setFirstName(response.data.firstname);
        setName(response.data.lastname);
        if (typeof response.data.employeeType === "object") {
          setEmployeeType(response.data.employeeType);
        }
        setWorkTimes(response.data.workTimes);
        console.log(response.data.employeeTimeslotConstraints);
        setTimeslotConstraints(response.data.employeeTimeslotConstraints);
      });
    }
  }, []);

  const getEmployeeTitle = (): string => {
    const createEmployeeTitle = "Mitarbeiter erstellen";
    const editEmployeeTitle = "Mitarbeiter bearbeiten";
    if (id) {
      return editEmployeeTitle;
    }
    return createEmployeeTitle;
  };

  const getSavingButtonText = (): string => {
    const createEmployeeText = "Erstellen";
    const editEmployeeText = "Speichern";
    if (id) {
      return editEmployeeText;
    }
    return createEmployeeText;
  };

  const shortenWorktimes = (): WorkTimeRequest[] => {
    const shortened: WorkTimeRequest[] = [];
    workTimes.forEach((workTime) => {
      shortened.push({
        weekday: workTime.weekday,
        timeslotID: workTime.timeslot.id,
      });
    });
    return shortened;
  };

  const handleInputChangeEmployeeType = (value: string) => {
    setEmployeeType(employeeTypes.find(({ name }) => name === value) || null);
  };

  const saveEmployee = () => {
    const employee: EmployeeAPIRequest = {
      id: id ? id : "",
      abbreviation,
      firstname,
      lastname: name,
      employeeType: employeeType?.id || "",
      workTimes: shortenWorktimes(),
    };

    setSavePushed(true);
    if (!errorInput) {
      if (id) {
        updateEmployee(employee).then(goBackToOverview).catch(console.error);
      } else {
        createEmployee(employee).then(goBackToOverview).catch(console.error);
      }
    }
  };

  const actions = (
    <SubmitButtons
      submitText={getSavingButtonText()}
      cancelText="Abbrechen"
      onClickSubmit={saveEmployee}
      onClickCancel={goBackToOverview}
    />
  );

  return (
    <PageLayout title={getEmployeeTitle()} actions={actions}>
      <div className="single-view-employee">
        <div className="flex flex-row single-view-employee__form-row-width">
          <TextField
            id="firstname"
            label="Vorname"
            value={firstname}
            className="single-view-employee__text-field"
            variant="outlined"
            error={firstname === "" && savePushed}
            helperText={
              firstname === "" && savePushed
                ? "Der Vorname darf nicht leer sein!"
                : ""
            }
            onChange={(event) => {
              setFirstName(event.target.value);
            }}
          />
          <TextField
            id="name"
            label="Nachname"
            value={name}
            className="single-view-employee__text-field"
            variant="outlined"
            error={name === "" && savePushed}
            helperText={
              name === "" && savePushed
                ? "Der Nachname darf nicht leer sein!"
                : ""
            }
            onChange={(event) => {
              setName(event.target.value);
            }}
          />
        </div>
        <div className="flex flex-row single-view-employee__form-row-width">
          <TextField
            id="abbreviation"
            label="Kürzel"
            value={abbreviation}
            className="single-view-employee__text-field"
            variant="outlined"
            error={abbreviation === "" && savePushed}
            helperText={
              abbreviation === "" && savePushed
                ? "Das Kürzel darf nicht leer sein!"
                : ""
            }
            onChange={(event) => {
              setAbbreviation(event.target.value);
            }}
          />
          <SelectComponent
            selectData={employeeTypes}
            tooltip="Es wurden noch keine Mitarbeitertypen angelegt."
            className="single-view-employee__text-field"
            label="Kategorie"
            value={employeeType?.name || ""}
            onChange={handleInputChangeEmployeeType}
          ></SelectComponent>
        </div>
        <EmployeeAvailabilityTable
          availability={workTimes}
          setAvailability={setWorkTimes}
          constraints={timeslotConstraints}
        />
      </div>
    </PageLayout>
  );
}

export default Employee;
