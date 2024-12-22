import { useEffect, useRef, useState } from "react";
import { SortingDirection } from "../../../enums/sort-by";
import { EmployeeAPI, EmployeeSort } from "../../../models/employee";
import CrudTable, {
  ColumnDefinitionType,
} from "../../../shared/components/crud-table/crud-table";
import {
  deleteEmployee,
  fetchEmployees,
} from "../../../shared/requests/employer.requests";
import PageLayout from "../../../shared/components/page-layout";
import { Sort } from "../../../hooks/useSort.hook";

function ListEmployees() {
  const [sortBy] = useState<Sort<EmployeeSort>>({
    key: "firstname",
    direction: SortingDirection.Descending,
  });

  const [employees, setEmployees] = useState<EmployeeAPI[]>([]);
  const isInitRender = useRef(true);

  useEffect(() => {
    if (isInitRender.current) {
      fetchEmployees().then((response) => setEmployees(response.data));
      isInitRender.current = false;
    }
  });

  const columns: ColumnDefinitionType<EmployeeAPI, keyof EmployeeAPI>[] = [
    {
      key: "firstname",
      header: "Vorname",
    },
    {
      key: "lastname",
      header: "Name",
    },
    {
      key: "abbreviation",
      header: "Kürzel",
    },
  ];
  return (
    <PageLayout title={"Übersicht Mitarbeiter"}>
      <CrudTable
        tableData={employees}
        columns={columns}
        sortBy={sortBy}
        deleteDialogTitle="Löschen eines Mitarbeiters"
        fetchElements={fetchEmployees}
        deleteElement={deleteEmployee}
      />
    </PageLayout>
  );
}

export default ListEmployees;
