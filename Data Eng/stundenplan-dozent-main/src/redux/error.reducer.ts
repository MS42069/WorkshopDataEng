import { PayloadAction, createSlice } from "@reduxjs/toolkit";

export interface ErrorStore {
  message: string | null;
  layer: number;
}

const initialState: ErrorStore = {
  message: null,
  layer: 0,
};

const slice = createSlice({
  name: "ErrorStore",
  initialState,
  reducers: {
    resetError: (state: ErrorStore) => {
      state.message = null;
    },
    setError: (state: ErrorStore, action: PayloadAction<string>) => {
      state.message = action.payload;
    },
    setLayer: (state: ErrorStore, action: PayloadAction<number>) => {
      state.layer = action.payload;
      state.message = null;
    },
    increaseLayer: (state: ErrorStore) => {
      state.layer++;
      state.message = null;
    },
    decreaseLayer: (state: ErrorStore) => {
      state.message = null;
      state.layer--;
    },
  },
});

export const { decreaseLayer, increaseLayer, setError, setLayer } =
  slice.actions;
export default slice.reducer;
