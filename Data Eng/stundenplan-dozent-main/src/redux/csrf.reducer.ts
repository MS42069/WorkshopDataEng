import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { axiosInstance } from "../config/axios.config";

export interface CSRFStore {
  headerName: string | null;
  token: string | null;
}

const initialState: CSRFStore = {
  headerName: null,
  token: null,
};

const requestCSRFToken = () => {
  return axiosInstance.get<CSRFStore>("/auth/csrf");
};

const csrfTokenThunk = createAsyncThunk("auth/csrfToken", requestCSRFToken);

const csrfSlice = createSlice<CSRFStore, any, "CsrfStore">({
  name: "CsrfStore",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(csrfTokenThunk.fulfilled, (state, action) => {
      state.token = action.payload.data.token;
      state.headerName = action.payload.data.headerName;
    });
  },
});

export { csrfTokenThunk };
export default csrfSlice.reducer;
