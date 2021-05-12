import React from "react";

export default function CryptoCurrencyPage() {
  return (
    <main>
      <section className="crypto-general-info-div">
        <div className="coin-image">image</div>
        <div className="coin-information">
          <div className="coin-stat">
            <p className="coin-descriptor">Coin Name</p>
            <p className="coin-value">bitcoin</p>
          </div>
          <div className="coin-stat">
            <p className="coin-descriptor">Market Cap</p>
            <p className="coin-value">$1.0T</p>
          </div>
          <div className="coin-stat">
            <p className="coin-descriptor">Available Supply</p>
            <p className="coin-value">18,708,043</p>
          </div>
          <div className="coin-stat">
            <p className="coin-descriptor">Price</p>
            <p className="coin-value">$55,675.50</p>
          </div>
          <div className="coin-stat">
            <p className="coin-descriptor">Volume 24h</p>
            <p className="coin-value">$4.2T</p>
          </div>
          <div className="coin-stat">
            <p className="coin-descriptor">Total Supply</p>
            <p className="coin-value">21,000,000</p>
          </div>
        </div>
      </section>
      <section className="crypto-overview-div">
        <div className="crypto-overview-title">O V E R V I E W</div>
        <div className="crypto-overview-graph">graph</div>
      </section>
      <section className="crypto-description-div">
        <div className="crypto-description-title">D E S C R I P T I O N</div>
        <div className="crypto-description-text">
          Bitcoin is the first successful internet money based on peer-to-peer
          technology; whereby no central bank or authority is involved in the
          transaction and production of the Bitcoin currency. It was created by
          an anonymous individual/group under the name, Satoshi Nakamoto. The
          source code is available publicly as an open source project, anybody
          can look at it and be part of the developmental
          process.\r\n\r\nBitcoin is changing the way we see money as we speak.
          The idea was to produce a means of exchange, independent of any
          central authority, that could be transferred electronically in a
          secure, verifiable and immutable way.
        </div>
      </section>
    </main>
  );
}
