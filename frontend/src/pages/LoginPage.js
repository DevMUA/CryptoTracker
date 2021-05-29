import React from "react";
import { useState, useRef, useEffect } from "react";
import { Link, Redirect, useHistory } from "react-router-dom";
import axios from "axios";

import {
  createMuiTheme,
  withStyles,
  makeStyles,
  ThemeProvider,
} from "@material-ui/core/styles";
import PageHeader from "../components/PageHeader";
import { MdPeopleOutline } from "react-icons/md";
import { Button, Grid, Paper, TextField } from "@material-ui/core";
import { green, purple, orange } from "@material-ui/core/colors";
import { useGlobalContext } from "../context";

const initialValues = {
  email: "",
  password: "",
};
export default function LoginPage() {
  const { signedIn, setSignedIn, setGlobalEmail } = useGlobalContext();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const history = useHistory();

  const paperStyle = {
    padding: 20,
    height: "250px",
    width: "100%",
    margin: "20px auto",
  };

  function login() {
    axios({
      method: "post",
      url: "http://localhost:9090/api/v1/login",
      data: {
        email: email,
        password: password,
      },
    }).then((response) => {
      console.log(response);
      if (response.data === true) {
        localStorage.setItem("signedIn", true);
        localStorage.setItem("email", email);
        setGlobalEmail(email);
        setSignedIn(true);
        //setSignedIn(true);
        //setGlobalEmail(email);
        console.log("redirect to feed");
        let path = "/livefeed";
        history.push(path);
        history.go(0);
      }
    });
  }

  const ColorButton = withStyles((theme) => ({
    root: {
      color: theme.palette.getContrastText(purple[500]),
      backgroundColor: orange[500],
      "&:hover": {
        backgroundColor: orange[700],
      },
    },
  }))(Button);
  return (
    <main>
      <section className="section-register">
        <PageHeader
          title="Login"
          subtitle="test"
          icon={<MdPeopleOutline></MdPeopleOutline>}
        />
        <Grid>
          <Paper elevation={10} style={paperStyle}>
            <Grid align="center"></Grid>
            <TextField
              label="Email"
              placeholder="example@gmail.com"
              onChange={(event) => {
                setEmail(event.target.value);
              }}
              fullWidth
              required
            ></TextField>
            <TextField
              label="Password"
              placeholder="123"
              onChange={(event) => {
                setPassword(event.target.value);
              }}
              fullWidth
              required
              type="password"
            ></TextField>
            <div className="register-button">
              <ColorButton
                type="submit"
                fullWidth
                variant="contained"
                onClick={() => login()}
              >
                Login
              </ColorButton>
            </div>
          </Paper>
        </Grid>
      </section>
    </main>
  );
}
