import React, { useState, useRef, useEffect } from "react";
import { Link } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import { FaCog } from "react-icons/fa";
import { MdAddCircle } from "react-icons/md";
import Paper from "@material-ui/core/Paper";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import SingleAlarm from "../components/SingleAlarm";
import axios from "axios";
import { useGlobalContext } from "../context";

export default function AccountSettings() {
  const [value, setValue] = React.useState(0);
  const [alarms, setAlarms] = React.useState([]);
  const { signedIn, setSignedIn, email, setGlobalEmail } = useGlobalContext();
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  useEffect(() => {
    var emailToUse = localStorage.getItem("email");
    console.log(emailToUse);
    var data = {
      account: {
        email: emailToUse,
      },
    };
    axios({
      method: "POST",
      url: "http://localhost:9090/getAlarms",
      headers: {
        "Content-Type": "application/json",
      },
      data: data,
    }).then((response) => {
      setAlarms(response.data);
    });
  }, []);

  const deleteAlarm = () => {
    var newAlarms = alarms;
    for (var i = 0; i < newAlarms.length; i++) {
      if (newAlarms[i].aid === value) {
        newAlarms.splice(i, 1);
      }
    }
    setAlarms(newAlarms);
    refreshList();
  };

  function refreshList() {
    window.location.reload();
  }

  function rerouteToAlarmCreation() {}

  function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        id={`scrollable-auto-tabpanel-${index}`}
        aria-labelledby={`scrollable-auto-tab-${index}`}
        {...other}
      >
        {value === index && (
          <Box p={3}>
            <Typography>{children}</Typography>
          </Box>
        )}
      </div>
    );
  }
  return (
    <main>
      <section className="section-title">
        <div className="title">
          <PageHeader
            title="Settings"
            subtitle="test"
            icon={<FaCog color="orange"></FaCog>}
            size="h2"
          />
        </div>
      </section>
      <section className="section-news">
        <div>
          <Paper className="news-tabs">
            <Tabs
              value={value}
              onChange={handleChange}
              indicatorColor="primary"
              textColor="primary"
              centered
            >
              <Tab label="Alerts" />
              <Tab label="Favourite Coins" />
            </Tabs>
          </Paper>
          <TabPanel value={value} index={0}>
            {alarms.map((item, index) => {
              const { aid, coin, condition, value, email, alert } = item;
              console.log(aid);
              return (
                <SingleAlarm
                  key={item.aid}
                  {...item}
                  parentCallback={deleteAlarm}
                ></SingleAlarm>
              );
            })}
            <div className="addAlarm" onClick={rerouteToAlarmCreation}>
              <MdAddCircle size={50} color="white" />
            </div>
          </TabPanel>
          <TabPanel value={value} index={1}>
            test2
          </TabPanel>
        </div>
      </section>
    </main>
  );
}
