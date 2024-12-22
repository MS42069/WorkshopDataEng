import axios from "axios";
import { HttpStatusCode } from "../enums/http-status-code";
import { logout } from "../redux/auth.reducer";
import { StoreState } from "../redux/store";
import { setError } from "../redux/error.reducer";
import { createTimetablePath, getTimetableId } from "../shared/path.util";
import { setupCache } from "axios-cache-interceptor";
import { registerRequest, unregisterRequest } from "../redux/spinner.reducer";
import { resolve } from "path";

let store: StoreState;

export const injectStore = (_store: StoreState) => {
  store = _store;
};

const _axiosInstance = axios.create({
  // eslint-disable-next-line no-undef
  baseURL: process.env.REACT_APP_BASE_URL,
  xsrfCookieName: "XSRF-TOKEN",
  xsrfHeaderName: "X-XSRF-TOKEN",
});

const _resourceAxiosInstance = axios.create({
  // eslint-disable-next-line no-undef
  baseURL: process.env.REACT_APP_BASE_URL,
  xsrfCookieName: "XSRF-TOKEN",
  xsrfHeaderName: "X-XSRF-TOKEN",
});

const delayInMs = 130;

const errorHandling = (error: any) => {
  const { status, data } = error.response;
  if (status === HttpStatusCode.Unauthorized) {
    store.dispatch(logout());
  } else {
    store.dispatch(setError(data.message));
  }
  store.dispatch(unregisterRequest());

  return Promise.reject(error);
};

_resourceAxiosInstance.interceptors.request.use((config) => {
  config.baseURL = `${config.baseURL}${createTimetablePath("")}`;
  config.data = { ...config.data, timetable: getTimetableId() };
  // delayed register to avoid loading page for fast requests
  setTimeout(() => store.dispatch(registerRequest()), delayInMs);
  return config;
});

_resourceAxiosInstance.interceptors.response.use((response) => {
  store.dispatch(unregisterRequest());
  return response;
}, errorHandling);

_axiosInstance.interceptors.request.use((config) => {
  // delayed register to avoid loading page for fast requests
  setTimeout(() => store.dispatch(registerRequest()), delayInMs);
  return config;
});

_axiosInstance.interceptors.response.use((response) => {
  store.dispatch(unregisterRequest());
  return response;
}, errorHandling);

const axiosInstance = setupCache(_axiosInstance, {
  debug: console.log,
  interpretHeader: false,
});
const resourceAxiosInstance = setupCache(_resourceAxiosInstance, {
  debug: console.log,
  interpretHeader: false,
});

export { axiosInstance, resourceAxiosInstance };
