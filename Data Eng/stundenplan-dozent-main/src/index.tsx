import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { ThemeProvider } from "@emotion/react";
import { createTheme } from "@mui/material";
import { deDE } from "@mui/x-date-pickers";
import { Provider } from "react-redux";
import { BrowserRouter } from "react-router-dom";
import { TOOLBAR_HEIGHT } from "./components/constants/toolbar";
import { injectStore } from "./config/axios.config";
import { store } from "./redux/store";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

const theme = createTheme(
  {
    typography: {
      fontSize: 12,
    },
    zIndex: {
      drawer: 1,
      appBar: 1000,
    },
    components: {
      MuiListItemButton: {
        styleOverrides: {
          root: () => ({
            ":hover": {
              backgroundColor: "rgba(28,39,100,.55)",
              color: theme.palette.secondary.main,
            },

            "&.Mui-selected": {
              "&:hover": {
                backgroundColor: theme.palette.primary.main,
              },
              backgroundColor: theme.palette.primary.main,
              color: theme.palette.secondary.main,
            },
          }),
        },
      },
      MuiToolbar: {
        styleOverrides: {
          regular: {
            height: TOOLBAR_HEIGHT,
          },
        },
      },
    },
    palette: {
      primary: {
        main: "#1c2764",
      },

      secondary: {
        main: "#fff",
      },
    },
  },
  deDE
);

injectStore(store);

root.render(
  <Provider store={store}>
    <BrowserRouter basename="/dozent">
      <ThemeProvider theme={theme}>
        <App />
      </ThemeProvider>
    </BrowserRouter>
  </Provider>
);
