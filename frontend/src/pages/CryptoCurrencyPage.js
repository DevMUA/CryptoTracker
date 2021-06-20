import React from "react";
import { useGlobalContext } from "../context";
import { useParams, Link } from "react-router-dom";
import Loading from "../components/Loading";
import Graph from "../components/Graph";

export default function CryptoCurrencyPage() {
  const { name } = useParams();
  const { coins, loading } = useGlobalContext();
  const [graphPoints, setGraphPoints] = React.useState([]);
  const ids = [];
  ids.push(name);
  //var filteredArray = coins.filter((i) => ids.includes(i.id));
  //var filteredArray = coins.filter((i) => ids.includes(i.id));
  var coin;
  coins.forEach((element) => {
    if (element.id === name) {
      coin = element;
      return;
    }
  });
  //if (filteredArray[0].image === undefined) filteredArray[0].image = "";

  React.useEffect(() => {
    const interval = setInterval(async () => {
      console.log("Called API!");
      //localhost:8080/api/v1/planes/track/snapshots/
      try {
        console.log(`http://localhost:8081/api/historic=${name}`);
        const response = await fetch(
          `http://localhost:8081/api/historic=${name}`
        );
        const data = await response.json();
        if (data === null) clearInterval(interval);
        const graphInfo = {
          graphPoints: [],
        };
        for (var i = 0; i < data.plotPoints.length; i++) {
          graphInfo.graphPoints.push(data.plotPoints[i]);
        }
        setGraphPoints(graphInfo.graphPoints);
      } catch (error) {
        console.log(error);
      }
    }, 15000);
    return () => clearInterval(interval);
  }, []);
  if (loading) {
    return <Loading />;
  }
  return (
    <main>
      <section className="crypto-general-info-div">
        <div className="coin-image">
          <img src={coin.image || ""} alt="" />
        </div>
        <div className="coin-information">
          <div className="coin-stat">
            <p className="coin-descriptor">Coin Name</p>
            <p className="coin-value">{coin.id || "Name missing"}</p>
          </div>
          <div className="coin-stat">
            <p className="coin-descriptor">Market Cap</p>
            <p className="coin-value">
              {coin.market_cap_usd || "Market cap missing"}
            </p>
          </div>
          <div className="coin-stat">
            <p className="coin-descriptor">Available Supply</p>
            <p className="coin-value">
              {coin.total_supply - coin.circulating_supply ||
                "Available Supply missing"}
            </p>
          </div>
          <div className="coin-stat">
            <p className="coin-descriptor">Price</p>
            <p className="coin-value">
              {coin.current_price || "Current price missing"}
            </p>
          </div>
          <div className="coin-stat">
            <p className="coin-descriptor">Volume 24h</p>
            <p className="coin-value">$4.2T</p>
          </div>
          <div className="coin-stat">
            <p className="coin-descriptor">Total Supply</p>
            <p className="coin-value">
              {coin.total_supply || "Total supply missing"}
            </p>
          </div>
        </div>
      </section>
      <section className="crypto-overview-div">
        <div className="crypto-overview-title">O V E R V I E W</div>
        <div className="crypto-overview-graph">
          <Graph graphPoints={graphPoints} />
        </div>
      </section>
      <section className="crypto-description-div">
        <div className="crypto-description-title">D E S C R I P T I O N</div>
        <div className="crypto-description-text">
          {coin.description || "This coin has no description available"}
        </div>
      </section>
    </main>
  );
}
