import { useEffect, useRef, useState } from "react";
import { TimeslotAPI } from "../../../models/timeslots";
import { SortingDirection } from "../../../enums/sort-by";
import {
  deleteTimeslot,
  fetchTimeslots,
  fetchTimeslots2,
} from "../../../shared/requests/timeslot";
import CrudTable, {
  ColumnDefinitionType,
} from "../../../shared/components/crud-table/crud-table";
import { timeslotForOverview } from "../../../shared/formatter/timeslot.formatter";
import TimeslotDialog from "./timeslot-dialog";
import PageLayout from "../../../shared/components/page-layout";
import { Sort } from "../../../hooks/useSort.hook";

function TimeslotOverview() {
  const [timeslots, setTimeSlots] = useState<TimeslotAPI[]>([]);
  const [externalModified, setExternalModified] = useState<boolean>(false);
  const [showTimeslotDialog, setShowTimeslotDialog] = useState<boolean>(false);
  const [clickedTimeslot, setClickedTimeslot] = useState<TimeslotAPI>();
  const [sortBy] = useState<Sort<TimeslotAPI>>({
    key: "startTime",
    direction: SortingDirection.Descending,
  });

  const onClickAddElement = () => {
    setShowTimeslotDialog(true);
  };

  const onClickEditElement = (timeslot: TimeslotAPI) => {
    setClickedTimeslot(timeslot);
    setShowTimeslotDialog(true);
  };

  const isInitRender = useRef(true);

  useEffect(() => {
    if (isInitRender.current) {
      fetchTimeslots().then((timeslots) => setTimeSlots(timeslots));
      isInitRender.current = false;
    }
  });

  const columns: ColumnDefinitionType<TimeslotAPI, keyof TimeslotAPI>[] = [
    {
      key: "startTime",
      header: "START",
    },
    {
      key: "endTime",
      header: "ENDE",
    },
  ];

  return (
    <PageLayout title={"Übersicht Timeslots"}>
      <CrudTable
        tableData={timeslots}
        columns={columns}
        sortBy={sortBy}
        deleteDialogTitle="Löschen eines Zeitslots"
        fetchElements={fetchTimeslots2}
        deleteElement={deleteTimeslot}
        dataFormatter={timeslotForOverview}
        onClickAddElement={onClickAddElement}
        onClickEditElement={onClickEditElement}
        externalModified={externalModified}
        setExternalModified={setExternalModified}
      />
      <TimeslotDialog
        showDialog={showTimeslotDialog}
        setShowDialog={setShowTimeslotDialog}
        timeslot={clickedTimeslot}
        setClickedTimeslot={setClickedTimeslot}
        timeslots={timeslots}
        setTimeslots={setTimeSlots}
        setExternalModified={setExternalModified}
      />
    </PageLayout>
  );
}

export default TimeslotOverview;
