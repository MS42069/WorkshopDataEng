import { useEffect, useState } from "react";
import SearchIcon from "@mui/icons-material/Search";
import AddIcon from "@mui/icons-material/Add";
import {
  Divider,
  IconButton,
  InputBase,
  Menu,
  MenuItem,
  Tooltip,
} from "@mui/material";
import React from "react";
import "./drop-down-menu.css";

export interface DropDownMenuProps {
  options: {
    id: string;
    label: string;
  }[];
  // eslint-disable-next-line no-unused-vars
  callback: (name: string) => void;
}

export const DropDownMenuWithSearch = (options: DropDownMenuProps) => {
  const [anchorEl, setAnchorEl] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [selection, setSelection] = useState("");

  useEffect(() => {
    setSelection(options.options[0].label);
  }, []);

  const handleMenuOpen = (event: any) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = (e: any) => {
    if (e.target.innerText !== selection && e.target.innerText !== "") {
      setSelection(e.target.innerText);
    }
    setSearchText("");
    setAnchorEl(null);
  };

  const handleSearchChange = (e: any) => {
    setSearchText(e.target.value);
  };

  return (
    <div>
      <Tooltip title="Hinzufügen">
        <IconButton aria-label="add" onClick={handleMenuOpen}>
          <AddIcon />
        </IconButton>
      </Tooltip>
      {renderDashboardMenu()}
    </div>
  );

  function renderDashboardMenu() {
    const displayOptions: any = options.options
      .map((item: { label: string }) => {
        if (item.label.toLowerCase().includes(searchText.toLowerCase())) {
          return item;
        }
        return undefined;
      })
      .filter((item: any) => item !== undefined);

    function renderOption(value: any) {
      if (selection === value) {
        return <div>{value}</div>;
      }
      return value;
    }

    return (
      <Menu
        anchorEl={anchorEl}
        keepMounted={true}
        open={!!anchorEl}
        onClose={handleClose}
      >
        <MenuItem className="searchBarContainer" disableTouchRipple={true}>
          <div className="search">
            <InputBase
              className="inputSearch"
              placeholder="Suche..."
              onChange={handleSearchChange}
              value={searchText}
            />
            <SearchIcon className="searchIcon" />
          </div>
        </MenuItem>
        <Divider />
        {displayOptions.map((item: any, index: any) => {
          return (
            <div key={index}>
              <MenuItem
                onClick={(e: React.MouseEvent<HTMLLIElement, MouseEvent>) => {
                  handleClose(e);
                  options.callback(item.id);
                }}
              >
                {renderOption(item.label)}
              </MenuItem>
              <Divider className="menuDivider" />
            </div>
          );
        })}
      </Menu>
    );
  }
};
