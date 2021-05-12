import React from "react";
import CryptoCurrency from "../components/CryptoCurrency";

export default function Livefeed() {
  const tableInformation = {
    id: "#",
    name: "NAME",
    change24h: "24H CHANGE",
    price: "PRICE",
    marketcap: "MARKET CAP",
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

  return (
    <main>
      <section className="section-title">
        <div className="title">C R Y P T O C U R R E N C I E S</div>
      </section>
      <section className="section-crypto-table">
        <CryptoCurrency {...tableInformation}></CryptoCurrency>
        <hr />
        <CryptoCurrency {...testInformation} />
        <CryptoCurrency />
      </section>
    </main>
  );
}
