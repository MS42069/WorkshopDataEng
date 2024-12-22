import { PayloadAction, createSlice } from "@reduxjs/toolkit";

interface SpinnerStore {
  pendingRequests: number;
}

const initialState: SpinnerStore = {
  pendingRequests: 1,
};

export const spinnerSlice = createSlice({
  name: "spinner",
  initialState,
  reducers: {
    registerRequest: (state) => {
      state.pendingRequests++;
    },
    unregisterRequest: (state) => {
      state.pendingRequests--;
    },
  },
});

export const { registerRequest, unregisterRequest } = spinnerSlice.actions;
export default spinnerSlice.reducer;
