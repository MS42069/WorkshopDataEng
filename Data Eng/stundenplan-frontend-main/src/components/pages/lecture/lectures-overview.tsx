import { useEffect, useRef, useState } from "react";
import { SortingDirection } from "../../../enums/sort-by";
import { LectureAPI, LectureSortingKeys } from "../../../models/lecture";
import {
  fetchLectures,
  deleteLecture,
} from "../../../shared/requests/lecture.requests";
import CrudTable, {
  ColumnDefinitionType,
} from "../../../shared/components/crud-table/crud-table";
import PageLayout from "../../../shared/components/page-layout";
import { Sort } from "../../../hooks/useSort.hook";

function ListLectures() {
  const [sortBy] = useState<Sort<LectureSortingKeys>>({
    key: "name",
    direction: SortingDirection.Descending,
  });

  const [lectures, setLectures] = useState<LectureAPI[]>([]);
  const isInitRender = useRef(true);

  useEffect(() => {
    if (isInitRender.current) {
      fetchLectures().then((response) => setLectures(response.data));
      isInitRender.current = false;
    }
  });

  const columns: ColumnDefinitionType<LectureAPI, keyof LectureAPI>[] = [
    {
      key: "name",
      header: "Name",
    },
    {
      key: "casID",
      header: "CAS-ID",
    },
  ];

  return (
    <PageLayout title={"Übersicht Veranstaltungen"}>
      <CrudTable
        tableData={lectures}
        columns={columns}
        sortBy={sortBy}
        deleteDialogTitle="Löschen einer Veranstaltung"
        fetchElements={fetchLectures}
        deleteElement={deleteLecture}
      />
    </PageLayout>
  );
}

export default ListLectures;
