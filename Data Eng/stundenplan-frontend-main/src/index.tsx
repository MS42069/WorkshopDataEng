import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./app";
import reportWebVitals from "./reportWebVitals";
import { Provider } from "react-redux";
import { store } from "./redux/store";
import { BrowserRouter } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material";
import { TOOLBAR_HEIGHT } from "./constants/toolbar";
import { deDE } from "@mui/material/locale";
import "./variables.css";
import { injectStore } from "./config/axios.config";

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
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <App />
      </ThemeProvider>
    </BrowserRouter>
  </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
