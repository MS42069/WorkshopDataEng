import { useEffect, useRef, useState } from "react";
import "./subject-overview.css";
import { SortingDirection } from "../../../enums/sort-by";
import PageLayout from "../../../shared/components/page-layout";
import { SubjectAPI, SubjectSort } from "../../../models/subject";
import CrudTable, {
  ColumnDefinitionType,
} from "../../../shared/components/crud-table/crud-table";
import { deleteSubject, fetchSubjects } from "../../../shared/requests/subject";
import { Sort } from "../../../hooks/useSort.hook";

function ListSubjects() {
  const [sortBy] = useState<Sort<SubjectSort>>({
    key: "name",
    direction: SortingDirection.Descending,
  });

  const [subjects, setSubjects] = useState<SubjectAPI[]>([]);
  const isInitRender = useRef(true);

  useEffect(() => {
    if (isInitRender.current) {
      fetchSubjects().then((response) => setSubjects(response.data));
      isInitRender.current = false;
    }
  });

  const columns: ColumnDefinitionType<SubjectAPI, keyof SubjectAPI>[] = [
    {
      key: "name",
      header: "Bezeichnung",
    },
    {
      key: "studyRegulation",
      header: "Studienordnung",
    },
  ];
  return (
    <PageLayout title="Übersicht Fachrichtungen">
      <CrudTable
        tableData={subjects}
        columns={columns}
        sortBy={sortBy}
        deleteDialogTitle="Löschen einer Fachrichtung"
        fetchElements={fetchSubjects}
        deleteElement={deleteSubject}
      />
    </PageLayout>
  );
}

export default ListSubjects;
