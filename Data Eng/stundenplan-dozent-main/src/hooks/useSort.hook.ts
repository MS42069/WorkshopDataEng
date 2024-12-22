import { SortingDirection } from "../enums/sort-by";

export interface Sort<E> {
  key: keyof E;
  direction: SortingDirection;
}
