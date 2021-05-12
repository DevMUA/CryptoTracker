import React, { useState, useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import SingleNews from "../components/SingleNews";

export default function AllNews({ News }) {
  return (
    <div className="crypto-news-div">
      <SingleNews />
      <SingleNews />
      <SingleNews />
      <SingleNews />
      <SingleNews />
      <SingleNews />
    </div>
  );
}
