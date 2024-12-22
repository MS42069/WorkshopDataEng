import { useState, useEffect } from "react";

interface Filter<E> {
  keys: (keyof E)[];
  searchFor: any;
}

export const useFilter = <E>(values: E[]) => {
  const [initialValues, setInitialValues] = useState<E[]>(values);
  const [filteredValues, setFilteredValues] = useState<E[]>(values);
  const [currentFilter, setCurrentFilter] = useState<Filter<E> | null>();

  useEffect(() => {
    if (currentFilter) {
      filter(currentFilter.keys, currentFilter.searchFor);
    } else {
      setFilteredValues(initialValues);
    }
  }, [initialValues]);

  const filter = <K extends keyof E>(keys: K[], searchFor: any) => {
    const newFilteredValues = initialValues.filter((entry) =>
      keys.some((key) => {
        const valueOfEntry = entry[key];
        return (
          (typeof valueOfEntry === "string" ||
            typeof valueOfEntry === "number") &&
          String(valueOfEntry)
            .toLowerCase()
            .includes(`${searchFor}`.toLowerCase())
        );
      })
    );

    setCurrentFilter({ keys, searchFor });
    setFilteredValues(newFilteredValues);
  };

  return { filter, filteredValues, setInitialValues, initialValues };
};
