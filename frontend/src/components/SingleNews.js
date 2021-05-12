import React, { useState, useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import { FiExternalLink } from "react-icons/fi";

export default function SingleNews({ Title, Short }) {
  return (
    <div className="crypto-news">
      <div className="news-image">
        <img
          src="https://img.etimg.com/thumb/msid-81773937,width-1070,height-580,imgsize-101309,overlay-etmarkets/photo.jpg"
          alt=""
        />
      </div>
      <div className="news-title">
        bitcoin: Moving beyond Bitcoin to the next crypto revolution in 2021
      </div>
      <div className="news-time">2021-03-31 10:29:00</div>
      <div className="news-link">
        <a href="https://economictimes.indiatimes.com/markets/stocks/news/moving-beyond-bitcoin-to-the-next-crypto-revolution-in-2021/articleshow/81773999.cms">
          <FiExternalLink />
        </a>
      </div>
    </div>
  );
}
