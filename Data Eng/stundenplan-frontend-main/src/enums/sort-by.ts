export enum SortingDirection {
  Ascending,
  Descending,
  NoDirection,
}

/* depricated -- use Sort Datatype from sort hook instead */
export type SortBy<E> = {
  column: keyof E | null;
  direction: SortingDirection;
};
