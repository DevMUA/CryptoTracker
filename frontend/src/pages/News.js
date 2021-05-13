import React from "react";
import AllNews from "../components/AllNews";
import Paper from "@material-ui/core/Paper";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import PageHeader from "../components/PageHeader";
import { BiNews } from "react-icons/bi";

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

export default function News() {
  const [value, setValue] = React.useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  return (
    <main>
      <section className="section-title">
        <div className="title">
          <PageHeader
            title="News"
            subtitle="test"
            icon={<BiNews color="orange"></BiNews>}
            size="h2"
          />
        </div>
      </section>
      <section className="section-news">
        <Paper className="news-tabs">
          <Tabs
            value={value}
            onChange={handleChange}
            indicatorColor="primary"
            textColor="primary"
            centered
          >
            <Tab label="General" />
            <Tab label="Favourites" />
          </Tabs>
        </Paper>
        <TabPanel value={value} index={0}>
          <AllNews />
        </TabPanel>
        <TabPanel value={value} index={1}>
          <AllNews />
        </TabPanel>
      </section>
    </main>
  );
}
