import React, { useState, useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import { useHistory } from "react-router-dom";
import { AiOutlineHeart, AiFillHeart } from "react-icons/ai";

export default function CryptoCurrency({
  id,
  name,
  change24h,
  price,
  marketcap,
  volume24h,
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
    window.alert("favourited");
  }

  function RedirectToCoin(e, name, history) {
    let path = "/crypto/" + name;
    console.log(path);
    if (name === "NAME") return;
    history.push(path);
  }
  return (
    <div
      onClick={(e) => RedirectToCoin(e, name, history)}
      className="crypto-currency-div"
    >
      <div className="crypto-id">{id || "ID_MISS"}</div>
      <div className="crypto-name">{name || "NAME_MISS"}</div>
      <div className="crypto-24h-change">{change24h || "24_MISS"}</div>
      <div className="crypto-price">{price || "PRICE_MISS"}</div>
      <div className="crypto-market-cap">{marketcap || "MARKET_CAP_MISS"}</div>
      <div className="crypto-volume-24h">{volume24h || "VOLUME_MISS"}</div>
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
