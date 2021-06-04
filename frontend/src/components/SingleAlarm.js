import React, { useState, useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import { useHistory } from "react-router-dom";
import { IoMdRemoveCircleOutline } from "react-icons/io";
import axios from "axios";

export default function SingleAlarm({
  aid,
  coin,
  condition,
  value,
  email,
  alert,
  parentCallback,
}) {
  const [iconHover, setIconHover] = useState(false);

  function deleteEntry() {
    var emailToUse = localStorage.getItem("email");
    var data = {
      account: {
        email: emailToUse,
      },
    };
    var urlToUse = "http://localhost:9090/deleteAlarm=" + aid;
    axios({
      method: "POST",
      url: urlToUse,
      headers: {
        "Content-Type": "application/json",
      },
      data: data,
    }).then((response) => {});
    parentCallback();
  }

  return (
    <div className="crypto-alarm-div">
      <div className="crypto-name">{coin || "COIN_MISS"}</div>
      <div className="crypto-24h-change">{condition || "CONDITION_MISS"}</div>
      <div className="crypto-price">{value || "VALUE_MISS"}</div>
      <div className="crypto-market-cap">
        {"email: " + email.toString() || "EMAIL_MISS"}
      </div>
      <div className="crypto-volume-24h">
        {"alert: " + alert.toString() || "ALERT_MISS"}
      </div>
      <div className="crypto-icon-remove">
        <p onClick={deleteEntry}>
          {<IoMdRemoveCircleOutline size={25}></IoMdRemoveCircleOutline> ||
            "ICON_MISSING"}
        </p>
      </div>
    </div>
  );
}
