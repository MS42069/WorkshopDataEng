import { useEffect } from "react";
import { Box, Tab, Tabs } from "@mui/material";
import React from "react";
import { WeekdayDescription, getAllWeekdays } from "../../enums/weekday.enum";

interface SelectWeekdayProps {
  children: React.ReactNode;
  value: WeekdayDescription;
  onChange: (weekday: WeekdayDescription) => void;
}

function allyProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

const weekdays = getAllWeekdays();

function SelectWeekday(props: SelectWeekdayProps) {
  useEffect(() => {
    props.onChange(props.value);
  }, []);

  const handleChange = (_: React.SyntheticEvent, newSelectedId: number) => {
    props.onChange(weekdays[newSelectedId]);
  };

  return (
    <Box sx={{ width: "100%" }}>
      <Box
        sx={{ borderBottom: 1, borderColor: "divider", marginBottom: "1rem" }}
      >
        <Tabs value={props.value.ord} onChange={handleChange}>
          {weekdays.map((weekday, idx) => (
            <Tab
              disableRipple
              label={weekday.name}
              {...allyProps(idx)}
              key={weekday.value}
            />
          ))}
        </Tabs>
      </Box>

      {props.children}
    </Box>
  );
}

export default SelectWeekday;
