import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { AlertColor } from "@mui/material/Alert";

interface NotificationStore {
  isVisible: boolean;
  message: string;
  status: AlertColor | undefined;
}

const initialState: NotificationStore = {
  isVisible: false,
  message: "",
  status: undefined,
};

// Then, handle actions in your reducers:
const Notification = createSlice({
  name: "employee",
  initialState,
  reducers: {
    // standard reducer logic, with auto-generated action types per reducer
    showSuccessNotifiction: (state, action: PayloadAction<string>) => {
      state.isVisible = true;
      state.message = action.payload;
      state.status = "success";
    },
    showErrorNotifiction: (state, action: PayloadAction<string>) => {
      state.isVisible = true;
      state.message = action.payload;
      state.status = "error";
    },
    closeNotification: (state) => {
      state.isVisible = false;
    },
  },
  extraReducers: {},
});

export default Notification.reducer;
export const {
  showSuccessNotifiction,
  showErrorNotifiction,
  closeNotification,
} = Notification.actions;
