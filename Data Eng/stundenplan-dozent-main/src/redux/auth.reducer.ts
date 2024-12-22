import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { axiosInstance } from "../config/axios.config";
import { Credentials } from "../models/credentials";
import { Loadable } from "../models/loadable";
import { User } from "../models/user";

export type AuthStore = (LoggedInState | LoggedOutState) & Loadable;

type LoggedOutState = {
  isAuthenticated: false;
  user: null;
};

type LoggedInState = {
  isAuthenticated: true;
  user: User;
};

const initialState: AuthStore = {
  isAuthenticated: false,
  user: null,
};

const loginRequest = createAsyncThunk(
  "auth/login",
  (credentials: Credentials) =>
    axiosInstance.post<User>("/auth/login", credentials)
);

const logoutRequest = createAsyncThunk("auth/logout", () =>
  axiosInstance.delete<User>("/auth/logout")
);

const userInformationRequest = createAsyncThunk("auth/currentUser", () =>
  axiosInstance.get<User>("/auth/user")
);

const slice = createSlice<AuthStore, { logout: (state: AuthStore) => void }>({
  name: "AuthStore",
  initialState,
  reducers: {
    logout: (state: AuthStore) => {
      state.isAuthenticated = false;
      state.isLoading = false;
      state.user = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginRequest.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(loginRequest.fulfilled, (state, action) => {
        state.isAuthenticated = true;
        state.isLoading = false;
        state.user = action.payload.data;
      })
      .addCase(userInformationRequest.fulfilled, (state, action) => {
        state.isAuthenticated = true;
        state.isLoading = false;
        state.user = action.payload.data;
      })
      .addCase(userInformationRequest.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(logoutRequest.fulfilled, (state) => {
        state.isAuthenticated = false;
        state.user = null;
      });
  },
});

export const { logout } = slice.actions;
export { loginRequest as login, userInformationRequest, logoutRequest };
export default slice.reducer;
