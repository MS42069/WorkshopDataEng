import {
  Toolbar,
  IconButton,
  AppBar,
  Button,
  ListItemIcon,
  Menu,
  MenuItem,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { toggleSideNav } from "../../redux/side-nav.reducer";
import { useDispatch } from "react-redux";
import "./header.css";
import Banner from "../dev/banner";
import { useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../redux/store";
import { Logout } from "@mui/icons-material";
import { useState, MouseEvent } from "react";
import { logoutRequest } from "../../redux/auth.reducer";

function Header() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const dispatch = useDispatch<AppDispatch>();
  const { user } = useSelector(({ authReducer }: RootState) => authReducer);

  const logout = () => {
    dispatch(logoutRequest());
  };

  return (
    <AppBar
      position="sticky"
      component="nav"
      sx={{
        zIndex: (theme) => theme.zIndex.drawer + 1,
        backgroundColor: "var(--xsoft-white)",
      }}
    >
      <Toolbar className="header__toolbar" variant="dense">
        <IconButton
          size="large"
          edge="start"
          color="inherit"
          aria-label="menu"
          onClick={() => dispatch(toggleSideNav())}
          sx={{ mr: 2, color: "var(--xsoft-blue)" }}
        >
          <MenuIcon />
        </IconButton>
        <div>
          <img
            className="header__img-size"
            src="/fh-wedel-logo-transparent.png"
            alt="img not found"
          />
        </div>

        <Banner />

        <Button
          variant="text"
          className="header__account-section"
          onClick={handleClick}
          endIcon={<AccountCircleIcon />}
        >
          {user?.displayName}
        </Button>
      </Toolbar>
      <Menu
        anchorEl={anchorEl}
        id="account-menu"
        open={open}
        onClose={handleClose}
        onClick={handleClose}
        PaperProps={{
          elevation: 0,
          sx: {
            overflow: "visible",
            filter: "drop-shadow(0px 2px 8px rgba(0,0,0,0.32))",
            mt: 1.5,
            "& .MuiAvatar-root": {
              width: 32,
              height: 32,
              ml: -0.5,
              mr: 1,
            },
            "&:before": {
              content: '""',
              display: "block",
              position: "absolute",
              top: 0,
              right: 14,
              width: 10,
              height: 10,
              bgcolor: "background.paper",
              transform: "translateY(-50%) rotate(45deg)",
              zIndex: 0,
            },
          },
        }}
        transformOrigin={{ horizontal: "right", vertical: "top" }}
        anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
      >
        <MenuItem onClick={logout}>
          <ListItemIcon>
            <Logout fontSize="small" />
          </ListItemIcon>
          Logout
        </MenuItem>
      </Menu>
    </AppBar>
  );
}

export default Header;
