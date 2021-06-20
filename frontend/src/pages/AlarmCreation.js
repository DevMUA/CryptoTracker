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
import { BsAlarm } from "react-icons/bs";
import { Button, Grid, Paper, TextField } from "@material-ui/core";
import { green, purple, orange } from "@material-ui/core/colors";
import FormLabel from "@material-ui/core/FormLabel";
import FormControl from "@material-ui/core/FormControl";
import FormGroup from "@material-ui/core/FormGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import FormHelperText from "@material-ui/core/FormHelperText";
import Checkbox from "@material-ui/core/Checkbox";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import Select from "@material-ui/core/Select";

const useStyles = makeStyles((theme) => ({
  root: {
    display: "block",
  },
  formControl: {
    margin: theme.spacing(3),
  },
}));

export default function AlarmCreation() {
  const classes = useStyles();
  const [coin, setCoin] = React.useState("");
  const [value, setValue] = React.useState(0);
  const [condition, setCondition] = React.useState("");
  const history = useHistory();
  const [state, setState] = React.useState({
    alarm: false,
    email: false,
  });

  const handleChange = (event) => {
    setState({ ...state, [event.target.name]: event.target.checked });
  };

  const handleChangeCoin = (event) => {
    setCoin(event.target.value);
  };

  const handleChangeCondition = (event) => {
    setCondition(event.target.value);
  };

  const { alarm, email } = state;
  const error = [alarm, email].filter((v) => v).length !== 2;

  const paperStyle = {
    padding: 20,
    height: "80%",
    width: "500px",
    margin: "20px auto",
  };

  function CreateAlarm() {
    var emailToUse = localStorage.getItem("email");
    var data = {
      account: {
        email: emailToUse,
        alarms: [
          {
            coin: coin,
            condition: condition,
            value: value,
            email: email,
            alert: alarm,
          },
        ],
      },
    };
    var urlToUse = "http://localhost:9090/addAlarm";
    axios({
      method: "POST",
      url: urlToUse,
      headers: {
        "Content-Type": "application/json",
      },
      data: data,
    }).then((response) => {
      let path = "/settings";
      history.push(path);
      history.go(0);
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
          title="Alarm Creation"
          subtitle="test"
          icon={<BsAlarm></BsAlarm>}
          size="h6"
        />
        <Grid>
          <Paper elevation={10} style={paperStyle}>
            <Grid align="center" className>
              <FormControl
                className={classes.formControl}
                style={{ minWidth: 400 }}
              >
                <form
                  className={classes.root}
                  noValidate
                  autoComplete="off"
                  autoWidth
                  onChange={handleChangeCoin}
                >
                  <TextField id="standard-basic" label="Standard" />
                </form>
              </FormControl>
              <FormControl
                className={classes.formControl}
                style={{ minWidth: 400 }}
              >
                <InputLabel id="demo-simple-select-label">Condition</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={condition}
                  onChange={handleChangeCondition}
                  autoWidth
                >
                  <MenuItem value={"Above"}>Above</MenuItem>
                  <MenuItem value={"Below"}>Below</MenuItem>
                </Select>
              </FormControl>
              <TextField
                label="Value"
                placeholder="100"
                onChange={(event) => {
                  setValue(event.target.value);
                }}
                autoWidth
                required
              ></TextField>
            </Grid>
            <FormControlLabel
              control={
                <Checkbox
                  checked={email}
                  onChange={handleChange}
                  name="email"
                />
              }
              fullWidth
              label="Alert by email"
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={alarm}
                  onChange={handleChange}
                  name="alarm"
                />
              }
              fullWidth
              label="Alert by notification"
            />
            <div className="register-button">
              <ColorButton
                type="submit"
                fullWidth
                variant="contained"
                onClick={() => CreateAlarm()}
              >
                Create Alarm
              </ColorButton>
            </div>
          </Paper>
        </Grid>
      </section>
    </main>
  );
}
