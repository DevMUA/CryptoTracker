import React, { useState, useContext, useEffect } from "react";
import sublinks from "./sublinks";
import { useCallback } from "react";
const AppContext = React.createContext();

const url = "http://localhost:8081/api/coins";

const AppProvider = ({ children }) => {
  const [signedIn, setSignedIn] = useState(false);
  const [email, setGlobalEmail] = useState("Not Signed In");
  const [loading, setLoading] = useState(true);
  const [coins, setCoins] = useState([]);

  useEffect(() => {
    if (localStorage.getItem("signedIn")) {
      setSignedIn(localStorage.getItem("signedIn"));
    }
    if (localStorage.getItem("email")) {
      setGlobalEmail(localStorage.getItem("email"));
    }
  }, []);

  React.useEffect(() => {
    const interval = setInterval(async () => {
      console.log("Called API!");
      //localhost:8080/api/v1/planes/track/snapshots/
      fetchCoins();
    }, 60000);
    return () => clearInterval(interval);
  }, []);

  const fetchCoins = useCallback(async () => {
    setLoading(true);
    try {
      const response = await fetch(`${url}`);
      const data = await response.json();
      const coinsStructure = {
        coins: [],
      };
      for (var i = 0; i < data.length; i++) {
        coinsStructure.coins.push(data[i]);
        //coinsStructure.coins.push(data[i]);
      }
      const coinsData = coinsStructure.coins;

      if (coinsData) {
        const newCoins = coinsData.map((item) => {
          const { id, symbol, name, image, description, market_data } = item;
          return {
            id: id,
            symbol: symbol,
            name: name,
            image: image.large,
            description: description.en,
            current_price: market_data.current_price.usd,
            circulating_supply: market_data.circulating_supply,
            price_change_percentage_24: market_data.price_change_percentage_24h,
            total_supply: market_data.total_supply,
            market_cap_usd: market_data.market_cap.usd,
          };
        });
        setCoins(newCoins);
      } else {
        setCoins([]);
      }
      setLoading(false);
    } catch (error) {
      console.log(error);
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchCoins();
  }, [fetchCoins]);

  return (
    <AppContext.Provider
      value={{ signedIn, setSignedIn, email, setGlobalEmail, loading, coins }}
    >
      {children}
    </AppContext.Provider>
  );
};
// make sure use
export const useGlobalContext = () => {
  return useContext(AppContext);
};

export { AppContext, AppProvider };
