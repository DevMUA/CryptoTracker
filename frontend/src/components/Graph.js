import Loading from "../components/Loading";
import { Line } from "react-chartjs-2";

export default function Graph({ graphPoints }) {
  const moment = require("moment");
  var data = {
    labels: [],
    datasets: [
      {
        label: "USD",
        data: [],
        fill: true,
        backgroundColor: "rgba(75,192,192,0.2)",
        borderColor: "rgba(75,192,192,1)",
      },
    ],
  };
  for (var i = 0; i < graphPoints.length; i++) {
    data.datasets[0].data.push(graphPoints[i].value);
    //var newDate = Moment(Date(graphPoints[i].time)).format("MM/DD/YYYY hh:MM");

    data.labels.push(
      String(moment(graphPoints[i].time).format("dddd, MMMM Do"))
    );
  }

  if (graphPoints.length === 0) {
    return <Loading />;
  } else {
    return <Line data={data} />;
  }
}
