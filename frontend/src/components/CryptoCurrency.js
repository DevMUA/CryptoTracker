import React, { useState, useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import { useHistory } from "react-router-dom";
import { AiOutlineHeart, AiFillHeart } from "react-icons/ai";
import axios from "axios";

export default function CryptoCurrency({
  id,
  symbol,
  name,
  image,
  description,
  circulating_supply,
  price_change_percentage_24,
  total_supply,
  market_cap_usd,
  current_price,
  icon,
}) {
  const [iconHover, setIconHover] = useState(false);
  const [defaultIcon, setdefaultIcon] = useState(<AiOutlineHeart size={25} />);
  const history = useHistory();

  React.useEffect(() => {
    if (iconHover) {
      setdefaultIcon(<AiFillHeart size={25} />);
    } else {
      setdefaultIcon(<AiOutlineHeart size={25} />);
    }
  }, [iconHover]);

  function toggleHover() {
    if (iconHover) {
      setIconHover(false);
    } else {
      setIconHover(true);
    }
  }
  function OnFavourite(e) {
    e.stopPropagation();
    var emailToUse = localStorage.getItem("email");
    console.log(emailToUse);
    var data = {
      account: {
        email: emailToUse,
        favouriteCoins: [
          {
            coinName: name,
          },
        ],
      },
    };
    axios({
      method: "POST",
      url: "http://localhost:9090/addFavouriteCoins",
      headers: {
        "Content-Type": "application/json",
      },
      data: data,
    }).then((response) => {});
  }

  function RedirectToCoin(e, name, history) {
    let path = "/crypto/" + name;
    console.log(path);
    if (name === "NAME") return;
    history.push(path);
  }
  return (
    <div
      onClick={(e) => RedirectToCoin(e, id, history)}
      className="crypto-currency-div"
    >
      <div className="crypto-id">{id || "--"}</div>
      <div className="crypto-name">{name || "--"}</div>
      <div className="crypto-24h-change">
        {price_change_percentage_24 || "--"}
      </div>
      <div className="crypto-price">{current_price || "--"}</div>
      <div className="crypto-market-cap">{market_cap_usd || "--"}</div>
      <div className="crypto-icon">
        <p
          onClick={OnFavourite}
          onMouseEnter={toggleHover}
          onMouseLeave={toggleHover}
        >
          {icon || defaultIcon}
        </p>
      </div>
    </div>
  );
}
