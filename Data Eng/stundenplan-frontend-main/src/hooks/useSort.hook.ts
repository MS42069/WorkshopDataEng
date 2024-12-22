import { useEffect, useState } from "react";
import { SortingDirection } from "../enums/sort-by";
import { sortAsc, sortDesc } from "../shared/sorting";

export interface Sort<E> {
  key: keyof E;
  direction: SortingDirection;
}

export const useSort = <E>(values: E[], defaultSort?: Sort<E>) => {
  const [initialValues, setInitialValues] = useState<E[]>(values);
  const [sortedValues, setSortedValues] = useState<E[]>([]);

  useEffect(() => {
    if (defaultSort) {
      sort(defaultSort);
    }
  }, [initialValues]);

  const sort = (maybeSortOptions?: Sort<E>) => {
    const sortOptions = (maybeSortOptions ||= defaultSort);

    if (!sortOptions) {
      throw new Error("useSort hook: u need to specify sort options.");
    }

    const sorted = initialValues.sort((a, b) => {
      if (sortOptions.direction === SortingDirection.Ascending) {
        return sortAsc(a[sortOptions.key], b[sortOptions.key]);
      }
      return sortDesc(a[sortOptions.key], b[sortOptions.key]);
    });

    setSortedValues(sorted);
  };

  return { sort, setInitialValues, sortedValues };
};
