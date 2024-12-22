import { Box } from "@mui/material";
import { useSelector } from "react-redux";
import { Outlet } from "react-router";
import Header from "../components/header/header";
import SideNav from "../components/side-nav/side-nav";
import { DRAWER_WIDTH } from "../constants/side-nav";
import { RootState } from "../redux/store";
import "./main-layout.css";

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
          marginTop: "0.5rem",
          marginLeft: 0,
          transition: (theme) =>
            theme.transitions.create(["margin", "width"], {
              easing: theme.transitions.easing.sharp,
              duration: theme.transitions.duration.leavingScreen,
            }),
          ...(isOpen && {
            width: `calc(100% - ${DRAWER_WIDTH + 20}px)`,
            marginLeft: `${DRAWER_WIDTH + 20}px`,
            transition: (theme) =>
              theme.transitions.create(["margin", "width"], {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.leavingScreen,
              }),
          }),
        }}
      >
        <Outlet />
      </Box>
    </div>
  );
}

export default MainLayout;
