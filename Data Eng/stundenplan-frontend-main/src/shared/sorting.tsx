import ArrowUpwardIcon from "@mui/icons-material/ArrowUpward";
import ArrowDownwardIcon from "@mui/icons-material/ArrowDownward";
import CircleIcon from "@mui/icons-material/Circle";
import { SortingDirection } from "../enums/sort-by";
import { Sort } from "../hooks/useSort.hook";

function isArethmic(value: unknown): value is string | number {
  return typeof value === "number" || typeof value === "string";
}

export const sortAsc = (a: unknown, b: unknown): number => {
  if (isArethmic(a) && isArethmic(b)) {
    if (a < b) {
      return 1;
    }

    if (a > b) {
      return -1;
    }
  }
  return 0;
};

export const sortDesc = (a: unknown, b: unknown): number => {
  if (isArethmic(a) && isArethmic(b)) {
    if (a > b) {
      return 1;
    }
    if (a < b) {
      return -1;
    }
  }
  return 0;
};

export function getNextSortingDirection<E>(
  currentSort: Sort<E>,
  clickedCol: keyof E
): SortingDirection {
  if (currentSort.key !== clickedCol) {
    return SortingDirection.Ascending;
  }

  const sortingDirectionLength = 3;
  return (currentSort.direction + 1) % sortingDirectionLength;
}

export function determineNewSort<E>(
  currentSort: Sort<E>,
  clickedCol: keyof E
): Sort<E> {
  const newDirection = getNextSortingDirection(currentSort, clickedCol);
  return { key: clickedCol, direction: newDirection };
}

export function getSortingIcon<E>(currentSort: Sort<E>, currentCol: keyof E) {
  if (currentSort.key === currentCol) {
    if (currentSort.direction == SortingDirection.Ascending) {
      return <ArrowUpwardIcon />;
    }
    if (currentSort.direction == SortingDirection.Descending) {
      return <ArrowDownwardIcon />;
    }
  }
  return <CircleIcon sx={{ fontSize: "0.5rem", marginRight: "0.25rem" }} />;
}
