import React, { useState, useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import { useHistory } from "react-router-dom";
import { IoMdRemoveCircleOutline } from "react-icons/io";
import axios from "axios";

export default function SingleFavouriteCoin({ aid, coinName, parentCallback }) {
  function deleteEntry() {
    var emailToUse = localStorage.getItem("email");
    var data = {
      account: {
        email: emailToUse,
      },
    };
    var urlToUse = "http://localhost:9090/deleteFavouriteCoin=" + aid;
    axios({
      method: "POST",
      url: urlToUse,
      headers: {
        "Content-Type": "application/json",
      },
      data: data,
    }).then((response) => {});
    parentCallback(aid);
  }

  return (
    <div className="crypto-favourite-div">
      <div className="crypto-name">{coinName || "COIN_MISS"}</div>
      <div className="crypto-icon-remove">
        <p onClick={deleteEntry}>
          {<IoMdRemoveCircleOutline size={25}></IoMdRemoveCircleOutline> ||
            "ICON_MISSING"}
        </p>
      </div>
    </div>
  );
}
