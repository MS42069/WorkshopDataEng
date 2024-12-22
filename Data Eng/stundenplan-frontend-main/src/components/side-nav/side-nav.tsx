import {
  Drawer,
  ListItemButton,
  ListItemText,
  Toolbar,
  Collapse,
  Box,
  List,
  Link,
  ListItemIcon,
} from "@mui/material";

import React, { useEffect, useState } from "react";
import { RootState } from "../../redux/store";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { closeSideNav } from "../../redux/side-nav.reducer";
import { Link as RouterLink, useLocation } from "react-router-dom";
import {
  ConstructionOutlined,
  ExpandLess,
  ExpandMore,
} from "@mui/icons-material";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import AssignmentIcon from "@mui/icons-material/Assignment";
import { useParams } from "react-router";
import {
  getBaseDataURLPref,
  getPlanningURLPref,
} from "../../shared/url-prefix";
import "./side-nav.css";

interface Route {
  path: string;
  idx: number;
  name: string;
}

function SideNav() {
  const isOpen = useSelector(
    ({ sideNavReducer }: RootState) => sideNavReducer.isOpen
  );

  const { timetableId } = useParams();
  const { pathname } = useLocation();
  const dispatch = useDispatch();
  const [isManagementOpenPlanning, setIsManagementOpenPlanning] =
    useState<boolean>(true);
  const [isManagementOpen, setIsManagementOpen] = useState<boolean>(true);
  // eslint-disable-next-line no-unused-vars
  const [selectedItem, setSelectedItem] = useState<number>();
  const toggleOpenManagement = () => setIsManagementOpen(!isManagementOpen);
  const toggleOpenManagementPlanning = () =>
    setIsManagementOpenPlanning(!isManagementOpenPlanning);

  const returnRoute: Route = {
    path: "/timetables",
    idx: 1,
    name: "Vorlesungsplan",
  };

  const independentRoutes: Route[] = [
    returnRoute,
    {
      path: "/types-categories",
      idx: 13,
      name: "Typen und Kategorien",
    },
    {
      path: "/wishes-app-activation",
      idx: 12,
      name: "Verwaltung Wunscheintragung",
    },
    {
      path: "/employee-check-worktime",
      idx: 7777,
      name: "Mitarbeiter Bestätigungen",
    },
  ];

  useEffect(() => {
    console.log(selectedItem);
  }, [selectedItem]);

  const planningRoutes: Route[] = [
    {
      path: getPlanningURLPref(timetableId).concat("/day"),
      idx: 8,
      name: "Tage",
    },
    {
      path: getPlanningURLPref(timetableId).concat("/employee"),
      idx: 9,
      name: "Mitarbeiter",
    },
    {
      path: getPlanningURLPref(timetableId).concat("/lecture"),
      idx: 10,
      name: "Fachsemester",
    },
    {
      path: getPlanningURLPref(timetableId).concat("/room"),
      idx: 11,
      name: "Räume",
    },
    {
      path: getPlanningURLPref(timetableId).concat("/score"),
      idx: 12,
      name: "Score",
    },
  ];

  const dataRoutes: Route[] = [
    {
      path: getBaseDataURLPref(timetableId).concat("/room"),
      idx: 2,
      name: "Raum",
    },
    {
      path: getBaseDataURLPref(timetableId).concat("/employee"),
      idx: 3,
      name: "Mitarbeiter",
    },
    {
      path: getBaseDataURLPref(timetableId).concat("/lecture"),
      idx: 4,
      name: "Veranstaltung",
    },
    {
      path: getBaseDataURLPref(timetableId).concat("/timeslot"),
      idx: 5,
      name: "Zeitslot",
    },
    {
      path: getBaseDataURLPref(timetableId).concat("/subject"),
      idx: 6,
      name: "Fachrichtung",
    },
  ];

  useEffect(() => {
    const idx = [...independentRoutes, ...dataRoutes, ...planningRoutes].find(
      (route) => pathname.includes(route.path)
    )?.idx;
    setSelectedItem(idx);
  }, [pathname]);

  const list = () => (
    <Box sx={{ width: 250, height: "100%" }}>
      <List className="side-nav__flex-content">
        {timetableId ? (
          <>
            <div>
              <ListItemButton onClick={toggleOpenManagementPlanning}>
                <ListItemIcon>
                  <AssignmentIcon />
                </ListItemIcon>
                <ListItemText primary="Veranstaltung" />
                {isManagementOpenPlanning ? <ExpandLess /> : <ExpandMore />}
              </ListItemButton>
              <Collapse
                in={isManagementOpenPlanning}
                timeout="auto"
                unmountOnExit
              >
                <List component="div" disablePadding>
                  {planningRoutes.map(({ idx, path, name }) => (
                    <Link
                      component={RouterLink}
                      to={path}
                      sx={{ textDecoration: "none" }}
                      key={idx}
                    >
                      <ListItemButton
                        selected={selectedItem === idx}
                        sx={{ pl: 4 }}
                        onClick={() => setSelectedItem(idx)}
                      >
                        <ListItemText primary={name} />
                      </ListItemButton>
                    </Link>
                  ))}
                </List>
              </Collapse>

              <ListItemButton onClick={toggleOpenManagement}>
                <ListItemIcon>
                  <ConstructionOutlined />
                </ListItemIcon>
                <ListItemText primary="Verwaltung" />
                {isManagementOpen ? <ExpandLess /> : <ExpandMore />}
              </ListItemButton>
              <Collapse in={isManagementOpen} timeout="auto" unmountOnExit>
                <List component="div" disablePadding>
                  {dataRoutes.map(({ idx, path, name }) => {
                    return (
                      <Link
                        component={RouterLink}
                        to={path}
                        sx={{ textDecoration: "none" }}
                        key={idx}
                      >
                        <ListItemButton
                          selected={selectedItem === idx}
                          sx={{ pl: 4 }}
                          onClick={() => {
                            if (idx === 4) {
                              console.log("test");
                            }
                            setSelectedItem(idx);
                          }}
                        >
                          <ListItemText primary={name} />
                        </ListItemButton>
                      </Link>
                    );
                  })}
                </List>
              </Collapse>
            </div>

            <Link
              component={RouterLink}
              to={returnRoute.path}
              sx={{ textDecoration: "none" }}
            >
              <ListItemButton>
                <ListItemIcon>
                  <ArrowBackIcon />
                </ListItemIcon>
                <ListItemText primary="Vorlesungsplan schließen" />
              </ListItemButton>
            </Link>
          </>
        ) : (
          <List component="div" disablePadding>
            {independentRoutes.map(({ idx, path, name }) => (
              <Link
                component={RouterLink}
                to={path}
                sx={{ textDecoration: "none" }}
                key={idx}
              >
                <ListItemButton
                  selected={selectedItem === idx}
                  sx={{ pl: 4 }}
                  onClick={() => setSelectedItem(idx)}
                >
                  <ListItemText primary={name} />
                </ListItemButton>
              </Link>
            ))}
          </List>
        )}
      </List>
    </Box>
  );
  return (
    <div>
      <React.Fragment key="left">
        <Drawer
          variant="persistent"
          anchor="left"
          open={isOpen}
          onClose={() => dispatch(closeSideNav())}
        >
          <Toolbar />
          {list()}
        </Drawer>
      </React.Fragment>
    </div>
  );
}

export default SideNav;
