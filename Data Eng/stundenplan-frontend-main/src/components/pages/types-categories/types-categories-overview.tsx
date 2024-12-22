import { useEffect, useRef, useState } from "react";
import PageLayout from "../../../shared/components/page-layout";
import { SortingDirection } from "../../../enums/sort-by";
import TypesDialog from "./types-dialog";
import {
  deleteCourseType,
  deleteEmployeeType,
  deleteRoomType,
  deleteSchoolType,
  deleteSemesterType,
  fetchCourseTypes,
  fetchEmployeeTypes,
  fetchRoomTypes,
  fetchSchoolTypes,
  fetchSemesterTypes,
} from "../../../shared/requests/types.request";
import { RoomType } from "../../../models/room";
import CrudTable, {
  ColumnDefinitionType,
} from "../../../shared/components/crud-table/crud-table";
import { SchoolType } from "../../../models/subject";
import { CourseType } from "../../../models/lecture";
import { SemesterType } from "../../../models/timetable";
import { EmployeeType } from "../../../models/employee";
import { Sort } from "../../../hooks/useSort.hook";

function TypesCategoriesOverview() {
  const [roomTypes, setRoomTypes] = useState<RoomType[]>([]);
  const [employeeTypes, setEmployeeTypes] = useState<EmployeeType[]>([]);
  const [schoolTypes, setSchoolTypes] = useState<SchoolType[]>([]);
  const [courseTypes, setCourseTypes] = useState<CourseType[]>([]);
  const [semesterTypes, setSemesterTypes] = useState<SemesterType[]>([]);

  const [showTypeDialog, setShowTypeDialog] = useState<boolean>(false);
  const [clickedType, setClickedType] = useState<string>("");
  const [whichType, setWhichType] = useState<number>(0);
  const [externalModified, setExternalModified] = useState<boolean>(false);

  const isInitRender = useRef(true);

  useEffect(() => {
    if (isInitRender.current) {
      fetchRoomTypes().then((response) => setRoomTypes(response.data));
      fetchEmployeeTypes().then((response) => setEmployeeTypes(response.data));
      fetchSchoolTypes().then((response) => setSchoolTypes(response.data));
      fetchCourseTypes().then((response) => setCourseTypes(response.data));
      fetchSemesterTypes().then((response) => setSemesterTypes(response.data));
      isInitRender.current = false;
    }
  });

  const onClickAddElementRoomType = () => {
    setShowTypeDialog(true);
    setWhichType(1);
  };

  const onClickEditElementRoomType = (wichType: RoomType) => {
    setClickedType(wichType.id);
    setShowTypeDialog(true);
    setWhichType(1);
  };

  const onClickAddElementEmployeeType = () => {
    setShowTypeDialog(true);
    setWhichType(2);
  };

  const onClickEditElementEmployeeType = (wichType: EmployeeType) => {
    setClickedType(wichType.id);
    setShowTypeDialog(true);
    setWhichType(2);
  };

  const onClickAddElementSchoolType = () => {
    setShowTypeDialog(true);
    setWhichType(3);
  };

  const onClickEditElementSchoolType = (wichType: SchoolType) => {
    setClickedType(wichType.id);
    setShowTypeDialog(true);
    setWhichType(3);
  };

  const onClickAddElementCourseType = () => {
    setShowTypeDialog(true);
    setWhichType(4);
  };

  const onClickEditElementCourseType = (wichType: CourseType) => {
    setClickedType(wichType.id);
    setShowTypeDialog(true);
    setWhichType(4);
  };

  const onClickAddElementSemesterType = () => {
    setShowTypeDialog(true);
    setWhichType(5);
  };

  const onClickEditElementSemesterType = (wichType: SemesterType) => {
    setClickedType(wichType.id);
    setShowTypeDialog(true);
    setWhichType(5);
  };

  const [sortBy] = useState<Sort<RoomType>>({
    key: "name",
    direction: SortingDirection.Descending,
  });

  const columns: ColumnDefinitionType<RoomType, keyof RoomType>[] = [
    {
      key: "name",
      header: "Name",
    },
    {
      key: "online",
      header: "online",
    },
  ];

  const columns_other: ColumnDefinitionType<CourseType, keyof CourseType>[] = [
    {
      key: "name",
      header: "Name",
    },
  ];

  return (
    <PageLayout title="Typen- und Kategorienübersicht">
      <h2>Raumtypen</h2>
      <CrudTable
        tableData={roomTypes}
        columns={columns}
        sortBy={sortBy}
        deleteDialogTitle="Löschen eines RaumTypen"
        fetchElements={fetchRoomTypes}
        deleteElement={deleteRoomType}
        onClickAddElement={onClickAddElementRoomType}
        onClickEditElement={onClickEditElementRoomType}
        externalModified={externalModified}
        setExternalModified={setExternalModified}
      />
      <h2>Mitarbeitertypen</h2>
      <CrudTable
        tableData={employeeTypes}
        columns={columns_other}
        sortBy={sortBy}
        deleteDialogTitle="Löschen eines MitarbeiterTypen"
        fetchElements={fetchEmployeeTypes}
        deleteElement={deleteEmployeeType}
        onClickAddElement={onClickAddElementEmployeeType}
        onClickEditElement={onClickEditElementEmployeeType}
        externalModified={externalModified}
        setExternalModified={setExternalModified}
      />
      <h2>Schulformen</h2>
      <CrudTable
        tableData={schoolTypes}
        columns={columns_other}
        sortBy={sortBy}
        deleteDialogTitle="Löschen eines SchulTypen"
        fetchElements={fetchSchoolTypes}
        deleteElement={deleteSchoolType}
        onClickAddElement={onClickAddElementSchoolType}
        onClickEditElement={onClickEditElementSchoolType}
        externalModified={externalModified}
        setExternalModified={setExternalModified}
      />
      <h2>Veranstaltungstypen</h2>
      <CrudTable
        tableData={courseTypes}
        columns={columns_other}
        sortBy={sortBy}
        deleteDialogTitle="Löschen eines VeranstaltungsTypen"
        fetchElements={fetchCourseTypes}
        deleteElement={deleteCourseType}
        onClickAddElement={onClickAddElementCourseType}
        onClickEditElement={onClickEditElementCourseType}
        externalModified={externalModified}
        setExternalModified={setExternalModified}
      />
      <h2>Semestertypen</h2>
      <CrudTable
        tableData={semesterTypes}
        columns={columns_other}
        sortBy={sortBy}
        deleteDialogTitle="Löschen eines SemesterTypen"
        fetchElements={fetchSemesterTypes}
        deleteElement={deleteSemesterType}
        onClickAddElement={onClickAddElementSemesterType}
        onClickEditElement={onClickEditElementSemesterType}
        externalModified={externalModified}
        setExternalModified={setExternalModified}
      />
      <TypesDialog
        showDialog={showTypeDialog}
        setShowDialog={setShowTypeDialog}
        id={clickedType}
        setClickedId={setClickedType}
        whichType={whichType}
        setExternalModified={setExternalModified}
        hasOnlineCheckBox={whichType === 1}
      />
    </PageLayout>
  );
}

export default TypesCategoriesOverview;
