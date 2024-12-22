import { Box } from "@mui/material";
import { useSelector } from "react-redux";

import "./main-layout.css";
import { RootState } from "../../redux/store";
import Header from "./header";
import SideNav from "./side-nav";
import { Outlet } from "react-router";
import { DRAWER_WIDTH } from "../constants/side-nav";

function MainLayout() {
  const isOpen = useSelector(
    ({ sideNavReducer }: RootState) => sideNavReducer.isOpen
  );

  return (
    <div className="full-size">
      <Header />
      <SideNav />

      <Box
        sx={{
          width: "100%",
          flexGrow: 1,
          display: "flex",
          flexDirection: "row",
          justifyContent: "center",
          marginTop: "0.5rem",
          marginLeft: 0,
        }}
      >
        <Outlet />
      </Box>
    </div>
  );
}

export default MainLayout;
