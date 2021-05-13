import React from "react";
import { Link } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import { FaCog } from "react-icons/fa";
import Paper from "@material-ui/core/Paper";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";

export default function AccountSettings() {
  const [value, setValue] = React.useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
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
            test1
          </TabPanel>
          <TabPanel value={value} index={1}>
            test2
          </TabPanel>
        </div>
      </section>
    </main>
  );
}
