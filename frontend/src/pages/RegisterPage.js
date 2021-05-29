import React from "react";
import { useState, useRef, useEffect } from "react";
import { Link } from "react-router-dom";
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

const initialValues = {
  email: "",
  password: "",
};
export default function RegisterPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const paperStyle = {
    padding: 20,
    height: "250px",
    width: "100%",
    margin: "20px auto",
  };

  function register() {
    console.log(password);
    axios({
      method: "post",
      url: "http://localhost:9090/api/v1/registration",
      data: {
        firstName: "",
        lastName: "",
        password: password,
        email: email,
      },
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
          title="Register"
          subtitle="test"
          icon={<MdPeopleOutline></MdPeopleOutline>}
          size="h6"
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
                onClick={() => register()}
              >
                Register
              </ColorButton>
            </div>
          </Paper>
        </Grid>
      </section>
    </main>
  );
}
