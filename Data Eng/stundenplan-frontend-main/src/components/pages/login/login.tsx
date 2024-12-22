import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  TextField,
} from "@mui/material";
import { useState } from "react";
import React from "react";
import { useDispatch } from "react-redux";

import { Credentials } from "../../../models/credentials";
import { login } from "../../../redux/auth.reducer";
import { AppDispatch } from "../../../redux/store";

import "./login.css";
import { AccountCircle, Key } from "@mui/icons-material";
import ErrorDisplay from "../../../shared/components/error-display";

function Login() {
  const dispatch = useDispatch<AppDispatch>();

  const [credentials, setCredentials] = useState<Credentials>({
    username: "",
    password: "",
  });

  // Handle login via 'enter'
  // If enter key is pressed call login to log user in
  // All other keys should NOT be assigned an event because some ppl may navigate with tab
  const handleKeyDown = (
    event: React.KeyboardEvent<HTMLDivElement | HTMLInputElement>
  ) => {
    if (event.key == "Enter") {
      signIn();
    }
  };

  const handleUsernameChange = (
    event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>
  ) => {
    const username = event.target.value;
    setCredentials({ username, password: credentials.password });
  };

  const handlePasswordChange = (
    event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>
  ) => {
    const password = event.target.value;
    setCredentials({ username: credentials.username, password });
  };

  const signIn = () => {
    dispatch(login(credentials));
  };

  return (
    <>
      <Card className="login-bg">
        <CardHeader title="Anmeldung Stundenplan Planungstool Marco" />
        <CardContent className="flex flex-column login-width content-padding">
          <ErrorDisplay>
            <Box
              sx={{
                display: "flex",
                alignItems: "flex-end",
                marginBottom: "0.5rem",
              }}
              onKeyDown={handleKeyDown}
            >
              <AccountCircle sx={{ color: "action.active", mr: 1, my: 0.5 }} />
              <TextField
                sx={{ width: "100%" }}
                label="Benutzername"
                variant="standard"
                onChange={handleUsernameChange}
              />
            </Box>
            <Box sx={{ display: "flex", alignItems: "flex-end" }}>
              <Key sx={{ color: "action.active", mr: 1, my: 0.5 }} />
              <TextField
                sx={{ width: "100%" }}
                label="Passwort"
                variant="standard"
                type="password"
                onChange={handlePasswordChange}
              />
            </Box>
          </ErrorDisplay>
        </CardContent>
        <CardActions className="flex flex-end">
          <Button variant="contained" onClick={signIn}>
            Anmelden
          </Button>
        </CardActions>
      </Card>
    </>
  );
}

export default Login;
