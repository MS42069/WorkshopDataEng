import { createSlice } from "@reduxjs/toolkit";

interface SideNavStore {
  isOpen: boolean;
}

const initialState: SideNavStore = {
  isOpen: true,
};

export const sideNavSlice = createSlice({
  name: "sideNav",
  initialState,
  reducers: {
    openSideNav: (state) => {
      state.isOpen = true;
    },

    closeSideNav: (state) => {
      state.isOpen = false;
    },

    toggleSideNav: (state) => {
      state.isOpen = !state.isOpen;
    },
  },
});

export const { openSideNav, closeSideNav, toggleSideNav } =
  sideNavSlice.actions;
export default sideNavSlice.reducer;
