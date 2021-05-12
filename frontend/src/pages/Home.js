import React from "react";
import { BsGraphDown } from "react-icons/bs";
import { ImCogs } from "react-icons/im";
import { BiAlarmAdd } from "react-icons/bi";
import logoimage from "../images/LOGO-IMAGE.svg";

export default function Home() {
  return (
    <main>
      <section className="section-title">
        <div className="title">
          <img src={logoimage} alt="" />
        </div>
      </section>
      <section className="section-description">
        <div className="description">
          CryptoTracker is an application that receives its information from
          CoinGecko API and over time stores the information in a Kafka Broker
          that is later consumed by the backend to be stored in a database. The
          system will then use that information to pass it to the machine
          learning module that will make predictions, that information will then
          be exposed in a REST API to be used by the frontend interface.
        </div>
      </section>
      <section className="section-features">
        <div className="features-images">
          <p className="feature-image">
            <BsGraphDown size={140} />
          </p>
          <p className="feature-image">
            <ImCogs size={140} />
          </p>
          <p className="feature-image">
            <BiAlarmAdd size={140} />
          </p>
        </div>
        <div className="features">
          <p className="feature-info">
            Live information about cryptocurrencies price
          </p>
          <p className="feature-info">
            ML model assistance with actions on market
          </p>
          <p className="feature-info">
            Alarm notification on the front-end if the crypto prices reaches a
            certain threshold
          </p>
        </div>
      </section>
    </main>
  );
}
