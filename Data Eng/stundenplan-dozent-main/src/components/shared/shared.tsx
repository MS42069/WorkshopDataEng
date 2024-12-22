import { ReactJSXElement } from "@emotion/react/types/jsx-namespace";
import { Box, Divider, MenuItem, Select, Slider } from "@mui/material";
import { Typography } from "@mui/material";
import { Mark } from "@mui/base";

function getDivider(): ReactJSXElement {
  return (
    <>
      {getSpacer()}
      <Divider />
      <Box sx={{ m: 4 }} />
    </>
  );
}
function getSpacer(): ReactJSXElement {
  return <Box sx={{ m: 2 }} />;
}

function getSmallSpacer(): ReactJSXElement {
  return <Box sx={{ m: 1 }} />;
}
function listSelector(
  itemIds: string[],
  itemNames: string[],
  currentItemId: string,
  setItem: Function,
  optional?: { noAsOption: boolean; customTitle: string }
): ReactJSXElement {
  const showNo = optional?.noAsOption ?? false;
  return (
    <>
      <Typography component="legend">
        {optional?.customTitle ?? "Ihre Veranstaltung wählen"}
      </Typography>
      <Select
        labelId="simple-select-label"
        id={"select-" + currentItemId}
        value={currentItemId}
        onChange={(e, _) => setItem(e.target.value)}
      >
        {itemIds.map((itemId, idx) => {
          return (
            <MenuItem key={itemId} value={itemId}>
              {itemNames[idx]}
            </MenuItem>
          );
        })}
        {showNo && (
          <MenuItem key={"no" + currentItemId + Math.random()} value="no">
            Keine Auswahl
          </MenuItem>
        )}
      </Select>
    </>
  );
}

function getWidthLimiter(children: ReactJSXElement[]): ReactJSXElement {
  return <Box sx={{ paddingX: "8%" }}>{children}</Box>;
}

function getSettingComponent(
  title: string,
  ariaLabel: string,
  variable: number,
  changeVariable: Function,
  additions?: {
    additionalLabel1?: string;
    additionalLabel2?: string;
    additionalLabel3?: string;
    additionalLabel4?: string;
    additionalComponent?: ReactJSXElement;
  }
): ReactJSXElement {
  additions = additions ?? {};
  const marks: Mark[] = [
    {
      value: 0,
      label: "Ist mir egal",
    },
    {
      value: 1,
      label: additions.additionalLabel1 ?? "Gewünscht",
    },
    {
      value: -1,
      label: additions.additionalLabel3 ?? "Nicht gewünscht",
    },
  ];
  return getWidthLimiter([
    getDivider(),
    <Typography component="legend">{title}</Typography>,
    <Slider
      sx={{
        "& .MuiSlider-track": {
          display: "none", // ausblenden ausgefülltre bereich
        },
      }}
      aria-label={ariaLabel}
      defaultValue={0}
      value={variable}
      onChange={(e, value, _) => {
        changeVariable(value);
      }}
      valueLabelDisplay="off"
      step={1}
      marks={marks}
      min={-1}
      max={1}
    />,
    additions.additionalComponent ?? <></>,
  ]);
}

const courseList = [
  "Programmstrukturen 1",
  "Übg. Programmstrukturen 1",
  "Programmstrukturen 2",
  "Übg. Programmstrukturen 2",
  "Digitaltechnik 1",
  "Digitaltechnik 2",
  "Fortgeschrittene Objektorientierte Programmierung",
  "Übg. Fortgeschrittene Objektorientierte Programmierung",
  "Seminar",
  "IT-Sicherheit",
  "Operations Research",
  "Diskrete Mathematik",
  "Grundlagen der Computergrafik",
  "Web-Analytics",
  "Laser Engineering",
  "Machine Learning",
];

export {
  getDivider,
  listSelector as courseSelector,
  getSettingComponent,
  getWidthLimiter,
  courseList,
  getSpacer,
  getSmallSpacer,
};
