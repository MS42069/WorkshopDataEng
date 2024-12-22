import { useEffect, useRef, useState } from "react";
import { SortingDirection } from "../../../enums/sort-by";
import { RoomAPI, RoomSortingKeys } from "../../../models/room";
import { deleteRoom, fetchRooms } from "../../../shared/requests/room.requests";
import CrudTable, {
  ColumnDefinitionType,
} from "../../../shared/components/crud-table/crud-table";
import PageLayout from "../../../shared/components/page-layout";
import { Sort } from "../../../hooks/useSort.hook";

function ListRooms() {
  const [sortBy] = useState<Sort<RoomSortingKeys>>({
    key: "name",
    direction: SortingDirection.Descending,
  });

  const [rooms, setRooms] = useState<RoomAPI[]>([]);

  useEffect(() => {
    fetchRooms().then((response) => setRooms(response.data));
  }, []);

  const columns: ColumnDefinitionType<RoomAPI, keyof RoomAPI>[] = [
    {
      key: "name",
      header: "Name",
    },
    {
      key: "abbreviation",
      header: "Kürzel",
    },
  ];

  return (
    <PageLayout title={"Übersicht Räume"}>
      <CrudTable
        tableData={rooms}
        columns={columns}
        sortBy={sortBy}
        deleteDialogTitle="Löschen eines Raumes"
        fetchElements={fetchRooms}
        deleteElement={deleteRoom}
      />
    </PageLayout>
  );
}

export default ListRooms;
