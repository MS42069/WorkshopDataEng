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
import { ExpandLess, ExpandMore, Home, HorizontalRule, Padding } from "@mui/icons-material";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import AssignmentIcon from "@mui/icons-material/Assignment";
import "./side-nav.css";
import {
  aboutRoute,
  preferenceProfileRoute,
  timetableRoute,
} from "../../config/routes";

interface Route {
  path: string;
  idx: number;
  name: string;
}

function SideNav() {
  const isOpen = useSelector(
    ({ sideNavReducer }: RootState) => sideNavReducer.isOpen
  );
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
    path: aboutRoute,
    idx: 1,
    name: "Startseite",
  };

  const profileRoutes: Route[] = [
    {
      path: preferenceProfileRoute,
      idx: 4,
      name: "Präferenz-Profil",
    },
    {
      path: timetableRoute,
      idx: 5,
      name: "Zeitplan",
    },
  ];

  /* TOOD: Not finished, to be implemented later
  const courseRoutes: Route[] = [
    {
      path: coursePreferenceRoute,
      idx: 2,
      name: "Kurs-Einstellungen",
    },
    {
      path: courseTimetableRoute,
      idx: 3,
      name: "Kurs-Zeitplan",
    },
  ];
*/
  useEffect(() => {
    const idx = [...profileRoutes, returnRoute].find((route) =>
      pathname.includes(route.path)
    )?.idx;
    setSelectedItem(idx);
  }, [pathname, profileRoutes, returnRoute]);

  const list = () => (
    <Box sx={{ width: 250, height: "100%" }}>
      <List className="side-nav__flex-content">
        <>
          <div>
            <Link
              component={RouterLink}
              to={returnRoute.path}
              sx={{ textDecoration: "none" }}
              key={returnRoute.idx}
            >
              <ListItemButton
                selected={selectedItem === returnRoute.idx}
                onClick={() => setSelectedItem(returnRoute.idx)}
              >
                <ListItemIcon>
                  <Home />
                </ListItemIcon>
                <ListItemText primary={returnRoute.name} />
              </ListItemButton>
            </Link>
            <ListItemButton onClick={toggleOpenManagementPlanning}>
              <ListItemIcon>
                <AssignmentIcon />
              </ListItemIcon>
              <ListItemText primary="Persönliche Wünsche" />
              {isManagementOpenPlanning ? <ExpandLess /> : <ExpandMore />}
            </ListItemButton>
            <Collapse
              in={isManagementOpenPlanning}
              timeout="auto"
              unmountOnExit
            >
              <List component="div" disablePadding>
                {profileRoutes.map(({ idx, path, name }) => (
                  <Link
                    component={RouterLink}
                    to={path}
                    sx={{ textDecoration: "none" }}
                    key={idx}
                  >
                    <ListItemButton
                      selected={selectedItem === idx}
                      sx={{ pl: 10 }}
                      onClick={() => setSelectedItem(idx)}
                    >
                      <ListItemIcon sx={{minWidth: 32 }}>
                        <HorizontalRule />
                      </ListItemIcon>
                      <ListItemText primary={name} />
                    </ListItemButton>
                  </Link>
                ))}
              </List>
            </Collapse>
            {/**Not finished, to be implemented later 
              <ListItemButton onClick={toggleOpenManagement}>
                <ListItemIcon>
                  <ConstructionOutlined />
                </ListItemIcon>
                <ListItemText primary="Kursbezogene Wünsche" />
                {isManagementOpen ? <ExpandLess /> : <ExpandMore />}
              </ListItemButton>
              
              <Collapse in={isManagementOpen} timeout="auto" unmountOnExit>
                <List component="div" disablePadding>
                  {courseRoutes.map(({ idx, path, name }) => (
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
              */}
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
              <ListItemText primary="Schließen" />
            </ListItemButton>
          </Link>
        </>
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
