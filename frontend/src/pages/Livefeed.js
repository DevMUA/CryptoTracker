import React from "react";
import CryptoCurrency from "../components/CryptoCurrency";
import PageHeader from "../components/PageHeader";
import { FaCoins } from "react-icons/fa";
import { useGlobalContext } from "../context";
import Loading from "../components/Loading";

export default function Livefeed() {
  const { coins, loading } = useGlobalContext();
  const tableInformation = {
    id: "ID",
    name: "NAME",
    price_change_percentage_24: "24H CHANGE",
    current_price: "PRICE",
    market_cap_usd: "MARKET CAP",
    volume24h: "VOLUME 24H",
    icon: "FAVOURITE",
  };

  const testInformation = {
    id: "1",
    name: "bitcoin",
    change24h: "200",
    price: "200",
    marketcap: "200",
    volume24h: "200",
    icon: "",
  };
  if (loading) {
    return <Loading />;
  }
  if (coins.length === 0) {
    return <h2 className="section-title">no coins found</h2>;
  }
  return (
    <main>
      <section className="section-title">
        <div className="title">
          <PageHeader
            title="CryptoCurrencies"
            subtitle="test"
            icon={<FaCoins color="orange"></FaCoins>}
            size="h2"
          />
        </div>
      </section>
      <section className="section-crypto-table">
        <CryptoCurrency {...tableInformation}></CryptoCurrency>
        <hr />
        {coins.map((item) => {
          return <CryptoCurrency key={item.id} {...item} />;
        })}
      </section>
    </main>
  );
}
